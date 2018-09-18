package com.sxt.generate.table;

import com.sxt.generate.util.JdbcUtil;
import org.apache.commons.lang.StringUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName : TableFactory
 * Description : 解析数据库并生成Table
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class TableFactory {

    private String driver;
    private String url;
    private String username;
    private String password;


    public static TableFactory instance;

    private TableFactory(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * TableFactory实例
     *
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static TableFactory getInstance(String driver, String url, String username, String password) {
        if (instance == null) {
            instance = new TableFactory(driver, url, username, password);
        }
        return instance;

    }


    public Table introspectTable(Table table) {

        System.out.println("[连接数据库] 开始连接数据库：" + url);

        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";
        ResultSet rs = null;
        try {

            Connection conn = JdbcUtil.getConnection(driver, url, username, password);
            assert conn != null : "connection can't null";
            DatabaseMetaData dbMetaData = JdbcUtil.getMetaData(conn);
            assert dbMetaData != null : "dbMetaData can't null";

            System.out.println("[连接数据库] 数据库连接成功，获取table信息：" + table.getTableName());
            rs = dbMetaData.getTables(table.getCatalog(), table.getSchema(), table.getTableName(), null);
            if (rs.next()) {
                String table_Name = rs.getString("TABLE_NAME");
                String table_Type = rs.getString("TABLE_TYPE");
                String schema_Name = StringUtils.defaultIfEmpty(rs.getString("TABLE_SCHEM"), "");
                String remarks = rs.getString("REMARKS");

                if (remarks == null && JdbcUtil.isOracleDataBase(dbMetaData)) {
                    remarks = JdbcUtil.getOracleTableRemarks(conn, table_Name);
                }
                table.setRemark(remarks);

                initTableColumns(conn,table);
            } else {
                System.out.println("can not find table:" +  table.getTableName());
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            JdbcUtil.closeResultSet(rs);
        }
        return table;
    }

    public void initTableColumns(Connection conn, Table table) {

        assert conn != null : "connection can't null";
        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";

        List<Column> columns = geColumns(conn,table);
        List<Column> pkColumns = new ArrayList<Column>();
        List<Column> notPkColumns = new ArrayList<Column>();

        for(Column cl : columns) {
            if(cl.isPrimaryKey()) {
                pkColumns.add(cl);
            }else{
                notPkColumns.add(cl);
            }

        }
        table.setColumns(columns);
        table.setPkColumns(pkColumns);
        table.setNotPkColumns(notPkColumns);
    }


    /**
     * 获取列信息
     *
     * @param conn
     * @param table
     * @return
     */
    public List<Column> geColumns(Connection conn, Table table) {
        assert conn != null : "connection can't null";
        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";

        List<Column> columns = new ArrayList<Column>();

        List<String> primaryKeys = this.getPrimaryKeys(conn, table);
        List<String> indexInfos = this.getIndexInfoAll(conn, table);
        List<String> indexUniques = this.getIndexInfoUnique(conn, table);


        DatabaseMetaData dbMetaData = JdbcUtil.getMetaData(conn);
        ResultSet columnRs = null;
        try {
            columnRs = dbMetaData.getColumns(table.getCatalog(), table.getSchema(), table.getTableName(), null);
            System.out.println("[解析table信息] <catalog:" + table.getCatalog() + ",schema:" + table.getSchema() + ",table:" + table.getTableName() +" >");
            while (columnRs.next()) {
                String columnName = columnRs.getString("COLUMN_NAME");  //字段名称
                int dataType = columnRs.getInt("DATA_TYPE");            //SQL类型,来自java.sql.Types
                String typeName = columnRs.getString("TYPE_NAME");      //类型名称，数据源依赖
                int columnSize = columnRs.getInt("COLUMN_SIZE");        //列大小
                int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");  //小数部分位数
                // DatabaseMetaData.columnNullable或者DatabaseMetaData.columnNoNull或者DatabaseMetaData.columnNullableUnknown
                int nullable = columnRs.getInt("NULLABLE");             //是否允许可以为NULL
                String isNullable_v = columnRs.getString("IS_NULLABLE");  //是否可以为 NULL，YES--可以，NO--不可以，空字符串不确定

                String isAutoincrement_v = "NO";
                if(!JdbcUtil.isOracleDataBase(dbMetaData)) {
                   isAutoincrement_v = columnRs.getString("IS_AUTOINCREMENT");   //是否自增,YES---是，NO--否，空字符串不确定
                }
                String remarks = columnRs.getString("REMARKS");         //备注，列描述
                String columnDef = "";   //该列默认值
                if(!JdbcUtil.isOracleDataBase(dbMetaData)) {
                    columnDef = columnRs.getString("COLUMN_DEF");
                }
                if (remarks == null && JdbcUtil.isOracleDataBase(dbMetaData)) {
                    remarks = JdbcUtil.getOracleColumnRemarks(conn, table.getTableName(), columnName); //备注
                }
                boolean isNullable = (DatabaseMetaData.columnNullable == nullable); // 是否可以非空
                boolean isAutoincrement = ("YES".equals(isAutoincrement_v));    // 是否自增
                boolean isPk = primaryKeys.contains(columnName);                // 是否主键
                boolean isIndexInfo = indexInfos.contains(columnName);              // 是否索引
                boolean isIndexUnique = indexUniques.contains(columnName);      // 是否唯一索引
                Column column = new Column();
                column.setColumnName(columnName);
                column.setSqlType(dataType);
                column.setSqlTypeName(typeName);
                column.setColumnSize(columnSize);
                column.setDecimalDigits(decimalDigits);
                column.setDefaultValue(columnDef);
                column.setRemark(remarks);
                column.setNullable(isNullable);
                column.setPrimaryKey(isPk);
                column.setIndexInfo(isIndexInfo);
                column.setIndexUnique(isIndexUnique);
                column.setAutoIncrement(isAutoincrement);
                column.initialize();
                columns.add(column);
                System.out.println("[解析table信息] >>>> 解析字段:"+columnName);
            }
            System.out.println("[解析table信息] 解析数据库表信息成功");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JdbcUtil.closeResultSet(columnRs);
        }
        return columns;
    }

    /**
     * 获取主键信息
     *
     * @param conn
     * @param table
     * @return
     */
    public List<String> getPrimaryKeys(Connection conn, Table table) {

        assert conn != null : "connection can't null";
        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";

        List<String> primaryKeys = new ArrayList<String>();
        ResultSet keyRs = null;
        try {
            DatabaseMetaData dbMetaData = JdbcUtil.getMetaData(conn);
            keyRs = dbMetaData.getPrimaryKeys(table.getCatalog(), table.getSchema(), table.getTableName());
            while (keyRs.next()) {
                int keySeq = keyRs.getInt("KEY_SEQ"); //主键序列
                String pkName = keyRs.getString("PK_NAME"); //主键名称
                String columnName = keyRs.getString("COLUMN_NAME"); //列名称
                primaryKeys.add(columnName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JdbcUtil.closeResultSet(keyRs);
        }
        return primaryKeys;
    }

    /**
     * 获取主键-外键信息，根据给定表的主键列，获取对应外键列信息
     *
     * @param conn
     * @param table
     * @return
     */
    public List<String> getExportedKeys(Connection conn, Table table) {
        //TODO
        assert conn != null : "connection can't null";
        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";

        List<String> exportedKeys = new ArrayList<String>();
        ResultSet keyRs = null;
        try {
            DatabaseMetaData dbMetaData = JdbcUtil.getMetaData(conn);
            keyRs = dbMetaData.getExportedKeys(table.getCatalog(), table.getSchema(), table.getTableName());
            while (keyRs.next()) {
                int keySeq = keyRs.getInt("KEY_SEQ"); //外键序号
                String pktableName = keyRs.getString("PKTABLE_NAME"); //引用的主键表名称
                String pkcolumnName = keyRs.getString("PKCOLUMN_NAME"); //引用的主键列名称
                String fktableName = keyRs.getString("FKTABLE_NAME"); //引用的外键表名称
                String fkcolumnName = keyRs.getString("FKCOLUMN_NAME"); //引用的外键表名称
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JdbcUtil.closeResultSet(keyRs);
        }
        return exportedKeys;
    }

    /**
     * 获取外键-主键信息，根据给定表的外键列，获取对应主键列信息
     *
     * @param conn
     * @param table
     * @return
     */
    public List<String> getImportedKeys(Connection conn, Table table) {
        //TODO
        assert conn != null : "connection can't null";
        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";

        List<String> importeKeys = new ArrayList<String>();
        ResultSet keyRs = null;
        try {
            DatabaseMetaData dbMetaData = JdbcUtil.getMetaData(conn);
            keyRs = dbMetaData.getImportedKeys(table.getCatalog(), table.getSchema(), table.getTableName());
            while (keyRs.next()) {//ResultSet - 每一行都是一个主键列描述
                int keySeq = keyRs.getInt("KEY_SEQ"); //外键序号
                String pktableName = keyRs.getString("PKTABLE_NAME"); //引用的主键表名称
                String pkcolumnName = keyRs.getString("PKCOLUMN_NAME"); //引用的主键列名称
                String fktableName = keyRs.getString("FKTABLE_NAME"); //引用的外键表名称
                String fkcolumnName = keyRs.getString("FKCOLUMN_NAME"); //引用的外键表名称

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JdbcUtil.closeResultSet(keyRs);
        }
        return importeKeys;
    }


    /**
     * 获取索引信息
     *
     * @param conn
     * @param table
     * @return
     */
    public List<String> getIndexInfoAll(Connection conn, Table table) {
        return getIndexInfo(conn, table, false);
    }


    /**
     * 获取唯一约束索引信息
     *
     * @param conn
     * @param table
     * @return
     */
    public List<String> getIndexInfoUnique(Connection conn, Table table) {
        return getIndexInfo(conn, table, true);
    }

    /**
     * getIndexInfo(String catalog,String schema,String table,boolean unique,boolean approximate)
     * unique - 该参数为 true 时，仅返回惟一值的索引；该参数为 false 时，返回所有索引
     */
    public List<String> getIndexInfo(Connection conn, Table table, boolean unique) {

        assert conn != null : "connection can't null";
        assert StringUtils.isNotBlank(table.getTableName()) : "tableName can't null";

        List<String> indexInfos = new ArrayList<String>();
        ResultSet indexRs = null;   //每一行都是一个索引列描述
        try {
            DatabaseMetaData dbMetaData = JdbcUtil.getMetaData(conn);
            indexRs = dbMetaData.getIndexInfo(table.getCatalog(), table.getSchema(), table.getTableName(), unique, true);
            while (indexRs.next()) {

                String indexName = indexRs.getString("INDEX_NAME");     //索引名称
                String columnName = indexRs.getString("COLUMN_NAME");   //列名称
                boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");  //索引值是否不唯一
                if (columnName != null) indexInfos.add(columnName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JdbcUtil.closeResultSet(indexRs);
        }
        return indexInfos;
    }


}
