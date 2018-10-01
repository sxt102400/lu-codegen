package com.rats.lu.generator.table;

import com.rats.lu.generator.config.TableConfiguration;
import com.rats.lu.generator.utils.DbUtils;
import com.rats.lu.generator.utils.StringTools;
import com.rats.lu.generator.utils.MsgFmt;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class DatabaseIntrospector {

    private Connection connection;
    private DatabaseMetaData databaseMetaData;
    private TableConfiguration tableConfiguration;

    public DatabaseIntrospector(Connection connection, TableConfiguration tableConfiguration) throws SQLException {
        this.connection = connection;
        this.databaseMetaData = connection.getMetaData();
        this.tableConfiguration = tableConfiguration;
    }

    public IntrospectedTable introspectTable() throws SQLException {

        String tableName = tableConfiguration.getTableName();
        String className = tableConfiguration.getClassName();
        String catalog = tableConfiguration.getCatalog();
        String schema = tableConfiguration.getSchema();
        String subPackageName = tableConfiguration.getSubPackageName();

        assert StringUtils.isNotBlank(tableName) : "tableName can't null";
        assert databaseMetaData != null : "dbMetaData can't null";

        IntrospectedTable table = null;


        System.out.println(MsgFmt.getString("[start] 开始解析table信息：{0}", tableName));
        try (ResultSet rs = databaseMetaData.getTables(catalog, schema, tableName, null)) {
            if (rs.next()) {
                table= new IntrospectedTable();
                table.setClassName(StringUtils.defaultIfBlank(className, StringTools.toCamel(tableName)));
                table.setSubPackageName(subPackageName);
                table.setTableName(tableName);
                String actTableName = rs.getString("TABLE_NAME");
                String actTableType = rs.getString("TABLE_TYPE");
                String actSchemaName = StringUtils.defaultIfBlank(rs.getString("TABLE_SCHEM"), "");
                String actRemarks = DbUtils.getTableRemarks(rs, databaseMetaData, actTableName);
                table.setRemark(actRemarks);
            } else {
                System.err.println(MsgFmt.getString("\t\t[error!] 当前数据库中查询不到表信息:{0}", tableName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (table != null) {
            this.introspectColumns(databaseMetaData, table);
        }
        return table;
    }

    public List<Column> applyColumnOverrides(List<Column> introspectedColumns) {
        List<ColumnOverride> columnOverrides = new ArrayList<>();
        List<Column> columnList =  new ArrayList<>();
        for(Column introspectedColumn: introspectedColumns){
            ColumnOverride columnOverride = tableConfiguration.getColumnOverride(introspectedColumn.getColumnName());
            if (columnOverride != null) {
                if (StringUtils.isNotBlank(columnOverride.getFieldName())) {
                    introspectedColumn.setFieldName(columnOverride.getFieldName());
                }
                if (StringUtils.isNotBlank(columnOverride.getJavaType())) {
                    introspectedColumn.setJavaType(columnOverride.getJavaType());
                }
                if (StringUtils.isNotBlank(columnOverride.getJdbcType())) {
                    introspectedColumn.setJdbcType(columnOverride.getJdbcType());
                }
                if (columnOverride.isSerialize()) {
                   //NOTHING
                }
                if (!columnOverride.isIgnore()) {
                    columnList.add(introspectedColumn);
                }
            }else{
                columnList.add(introspectedColumn);
            }
        }
        return columnList;
    }

    public void introspectColumns(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {
        List<Column> columns = geColumns(databaseMetaData, introspectedTable);
        columns = this.applyColumnOverrides(columns);
        List<Column> pkColumns = new ArrayList<Column>();
        List<Column> notPkColumns = new ArrayList<Column>();
        for (Column cl : columns) {
            if (cl.isPrimaryKey()) {
                pkColumns.add(cl);
            } else {
                notPkColumns.add(cl);
            }
        }
        introspectedTable.setColumns(columns);
        introspectedTable.setPkColumns(pkColumns);
        introspectedTable.setNotPkColumns(notPkColumns);
        introspectedTable.setPkCount(pkColumns.size());
    }


    /**
     * 获取列信息
     *
     * @param databaseMetaData
     * @param introspectedTable
     * @return
     */
    public List<Column> geColumns(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {


        List<Column> columns = new ArrayList<Column>();

        List<String> primaryKeys = this.getPrimaryKeys(databaseMetaData, introspectedTable);
        List<String> indexInfos = this.getIndexInfoAll(databaseMetaData, introspectedTable);
        List<String> indexUniques = this.getIndexInfoUnique(databaseMetaData, introspectedTable);

        try (ResultSet rs = databaseMetaData.getColumns(introspectedTable.getCatalog(), introspectedTable.getSchema(), introspectedTable.getTableName(), null)) {
            System.out.println(MsgFmt.getString("[table] -> catalog:{0}\tschema:{1}\ttable:{2}", introspectedTable.getCatalog(), introspectedTable.getSchema(), introspectedTable.getTableName()));
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");  //字段名称
                String typeName = rs.getString("TYPE_NAME");      //类型名称，数据源依赖
                int dataType = rs.getInt("DATA_TYPE");            //SQL类型,来自java.sql.Types
                int columnSize = rs.getInt("COLUMN_SIZE");        //列大小
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");  //小数部分位数

                String defaultValue = rs.getString("COLUMN_DEF");
                String remarks = rs.getString("REMARKS");

                if (remarks == null) {
                    remarks = DbUtils.getColumnRemarks(rs, databaseMetaData, introspectedTable.getTableName(), columnName);     //备注，列描述
                }
                if (defaultValue == null) {
                    defaultValue = DbUtils.getColumnDefault(rs, databaseMetaData);   //该列默认值
                }
                boolean nullable = DatabaseMetaData.columnNullable == rs.getInt("NULLABLE");
                boolean autoIncrement =  DbUtils.isColumnAutoincrement( rs,  databaseMetaData);
                //boolean autoIncrement = "YES".equals(rs.getString("IS_AUTOINCREMENT"));
                //boolean generatedColumn = "YES".equals(rs.getString("IS_GENERATEDCOLUMN"));
                boolean pk = primaryKeys.contains(columnName);                // 是否主键
                boolean isIndexInfo = indexInfos.contains(columnName);              // 是否索引
                boolean indexUnique = indexUniques.contains(columnName);      // 是否唯一索引

                Column column = new Column();
                column.setColumnName(columnName);
                column.setSqlType(dataType);
                column.setJdbcType(typeName);
                column.setColumnSize(columnSize);
                column.setDecimalDigits(decimalDigits);
                column.setDefaultValue(defaultValue);
                column.setRemark(remarks);
                column.setNullable(nullable);
                column.setPrimaryKey(pk);
                column.setIndexInfo(isIndexInfo);
                column.setIndexUnique(indexUnique);
                column.setAutoIncrement(autoIncrement);
                column.initialize();
                columns.add(column);
                System.out.println("[column] -> 解析字段:" + columnName);
            }
            System.out.println("[table resolve success] 解析数据库表信息成功");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return columns;
    }

    /**
     * 获取主键信息
     *
     * @param databaseMetaData
     * @param introspectedTable
     * @return
     */
    public List<String> getPrimaryKeys(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {
        List<String> primaryKeys = new ArrayList<String>();
        try (ResultSet keyRs = databaseMetaData.getPrimaryKeys(introspectedTable.getCatalog(), introspectedTable.getSchema(), introspectedTable.getTableName())) {
            while (keyRs.next()) {
                int keySeq = keyRs.getInt("KEY_SEQ"); //主键序列
                String pkName = keyRs.getString("PK_NAME"); //主键名称
                String columnName = keyRs.getString("COLUMN_NAME"); //列名称
                primaryKeys.add(columnName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return primaryKeys;
    }

    /**
     * 获取索引信息
     *
     * @param databaseMetaData
     * @param introspectedTable
     * @return
     */
    public List<String> getIndexInfoAll(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {
        return getIndexInfo(databaseMetaData, introspectedTable, false);
    }

    /**
     * 获取唯一约束索引信息
     *
     * @param databaseMetaData
     * @param introspectedTable
     * @return
     */
    public List<String> getIndexInfoUnique(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {
        return getIndexInfo(databaseMetaData, introspectedTable, true);
    }

    /**
     * 获取主键-外键信息，根据给定表的主键列，获取对应外键列信息
     *
     * @param databaseMetaData
     * @param introspectedTable
     * @return
     */
    public List<String> getExportedKeys(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {

        List<String> exportedKeys = new ArrayList<String>();
        try (ResultSet keyRs = databaseMetaData.getExportedKeys(introspectedTable.getCatalog(), introspectedTable.getSchema(), introspectedTable.getTableName())) {
            while (keyRs.next()) {
                int keySeq = keyRs.getInt("KEY_SEQ"); //外键序号
                String pktableName = keyRs.getString("PKTABLE_NAME"); //引用的主键表名称
                String pkcolumnName = keyRs.getString("PKCOLUMN_NAME"); //引用的主键列名称
                String fktableName = keyRs.getString("FKTABLE_NAME"); //引用的外键表名称
                String fkcolumnName = keyRs.getString("FKCOLUMN_NAME"); //引用的外键表名称
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return exportedKeys;
    }

    /**
     * 获取外键-主键信息，根据给定表的外键列，获取对应主键列信息
     *
     * @param databaseMetaData
     * @param introspectedTable
     * @return
     */
    public List<String> getImportedKeys(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable) {

        List<String> importeKeys = new ArrayList<String>();
        try (ResultSet keyRs = databaseMetaData.getExportedKeys(introspectedTable.getCatalog(), introspectedTable.getSchema(), introspectedTable.getTableName())) {
            while (keyRs.next()) {//ResultSet - 每一行都是一个主键列描述
                int keySeq = keyRs.getInt("KEY_SEQ"); //外键序号
                String pktableName = keyRs.getString("PKTABLE_NAME"); //引用的主键表名称
                String pkcolumnName = keyRs.getString("PKCOLUMN_NAME"); //引用的主键列名称
                String fktableName = keyRs.getString("FKTABLE_NAME"); //引用的外键表名称
                String fkcolumnName = keyRs.getString("FKCOLUMN_NAME"); //引用的外键表名称

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return importeKeys;
    }


    /**
     * getIndexInfo(String catalog,String schema,String introspectedTable,boolean unique,boolean approximate)
     * unique - 该参数为 true 时，仅返回惟一值的索引；该参数为 false 时，返回所有索引
     */
    public List<String> getIndexInfo(DatabaseMetaData databaseMetaData, IntrospectedTable introspectedTable, boolean unique) {

        List<String> indexInfos = new ArrayList<String>();
        try (ResultSet keyRs = databaseMetaData.getExportedKeys(introspectedTable.getCatalog(), introspectedTable.getSchema(), introspectedTable.getTableName())) {
            while (keyRs.next()) {
                String indexName = keyRs.getString("INDEX_NAME");     //索引名称
                String columnName = keyRs.getString("COLUMN_NAME");   //列名称
                boolean nonUnique = keyRs.getBoolean("NON_UNIQUE");  //索引值是否不唯一
                if (columnName != null) indexInfos.add(columnName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return indexInfos;
    }


}
