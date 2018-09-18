package com.sxt.generate.desert.util;

import java.sql.*;

/**
 * ClassName : JdbcUtil
 * Description : jdbc操作类
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class JdbcUtil {

    private static ThreadLocal<Connection> currentConnection = new ThreadLocal<Connection>();

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
     * 获取连接
     *
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static Connection getConnection(String driver, String url, String username, String password) throws SQLException {

        Connection conn = null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    /**
     * 获取数据库信息
     *
     * @param conn
     * @return
     */
    public static DatabaseMetaData getMetaData(Connection conn) {

        DatabaseMetaData metaData = null;
        if (conn != null) {
            try {
                metaData = conn.getMetaData();
            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
            }
        }
        return metaData;
    }

    /**
     * close数据库连接
     *
     * @throws SQLException
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close数据库结果集
     *
     * @throws SQLException
     */
    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close数据库结果集
     *
     * @throws SQLException
     */
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    /**
     * oralce数据库的表注释
     *
     * @param conn
     * @param tableName
     * @return
     */
    public static String getOracleTableRemarks(Connection conn, String tableName) {
        String sql = "SELECT comments FROM user_tab_comments WHERE table_name='" + tableName + "'";
        return queryForString(conn, sql);
    }

    /*
   */

    /**
     * oralce数据库的字段注释
     *
     * @param conn
     * @param tableName
     * @param columnName
     * @return
     */
    public static String getOracleColumnRemarks(Connection conn, String tableName, String columnName) {
        String sql = "SELECT comments FROM user_col_comments WHERE table_name='" + tableName + "' AND column_name = '" + columnName + "'";
        return queryForString(conn, sql);
    }

    /**
     * 执行查询
     *
     * @param conn
     * @param sql
     * @return
     */
    public static String queryForString(Connection conn, String sql) {
        Statement stmt = null;
        ResultSet rs = null;
        String result = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result= rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);

        }
        return result;
    }
}
