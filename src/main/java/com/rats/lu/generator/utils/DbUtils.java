package com.rats.lu.generator.utils;

import java.sql.*;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class DbUtils {

    public static String getTableRemarks(ResultSet rs, DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
        String remarks;
        if (isOracleDataBase(databaseMetaData)) {
            remarks = getOracleTableRemarks(databaseMetaData, tableName);
        } else {
            remarks = rs.getString("REMARKS");
        }
        return remarks;
    }

    public static boolean isColumnAutoincrement(ResultSet rs, DatabaseMetaData databaseMetaData) throws SQLException {
        boolean isAutoincrement = false;
        if (isMysqlDataBase(databaseMetaData)) {
            String autoincrementStr = rs.getString("IS_AUTOINCREMENT");   //是否自增,YES---是，NO--否，空字符串不确定
            if ("YES".equalsIgnoreCase(autoincrementStr)) {
                isAutoincrement = true;
            }
        }
        return isAutoincrement;
    }

    public static String getColumnDefault(ResultSet rs, DatabaseMetaData databaseMetaData) throws SQLException {
        String columnDef = null;
        if (isMysqlDataBase(databaseMetaData)) {
            columnDef = rs.getString("COLUMN_DEF");
        }
        return columnDef;
    }


    public static String getColumnRemarks(ResultSet rs, DatabaseMetaData databaseMetaData, String tableName, String columnName) throws SQLException {

        String remarks;
        if (isOracleDataBase(databaseMetaData)) {
            remarks = getOracleColumnRemarks(databaseMetaData, tableName, columnName);
        } else {
            remarks = rs.getString("REMARKS");
        }
        return remarks;
    }

    /**
     * 获取数据库类型描述
     *
     * @param metaData
     * @return
     */

    public static String getDatabaseType(DatabaseMetaData metaData) {

        String type = "";
        try {
            type = metaData.getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 是否oralce数据库
     *
     * @param metaData
     * @return
     */
    public static boolean isOracleDataBase(DatabaseMetaData metaData) {
        boolean isOracle = false;

        try {
            isOracle = getDatabaseType(metaData).toLowerCase().indexOf("oracle") != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOracle;
    }

    /**
     * 是否oralce数据库
     *
     * @param metaData
     * @return
     */
    public static boolean isMysqlDataBase(DatabaseMetaData metaData) {
        boolean isOracle = false;

        try {
            isOracle = getDatabaseType(metaData).toLowerCase().indexOf("mysql") != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOracle;
    }

    /**
     * oralce数据库的表注释
     *
     * @param metaData
     * @param tableName
     * @return
     */
    public static String getOracleTableRemarks(DatabaseMetaData metaData, String tableName) {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name=?";
        return queryForString(metaData, sql, tableName);
    }

    /*
     */

    /**
     * oralce数据库的字段注释
     *
     * @param metaData
     * @param tableName
     * @param columnName
     * @return
     */
    public static String getOracleColumnRemarks(DatabaseMetaData metaData, String tableName, String columnName) {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name= ? AND column_name = ?";
        return queryForString(metaData, sql, tableName, columnName);
    }

    /**
     * 执行sql查询
     *
     * @param metaData
     * @param sql
     * @return
     */
    public static String queryForString(DatabaseMetaData metaData, String sql, String... params) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = null;
        try {
            Connection conn = metaData.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject((i+1), params[i]);
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
