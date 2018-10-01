package com.rats.lu.generator.table;

import com.rats.lu.generator.config.JdbcConnectionConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class JdbcConnectionFactory {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public JdbcConnectionFactory(JdbcConnectionConfiguration config) {
        this.driverClassName = config.getDriverClassName();
        this.url = config.getUrl();
        this.username = config.getUsername();
        this.password = config.getPassword();
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection conn;
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url, username, password);
        if (conn == null) {
            throw new SQLException("database connection error:" + this.url);
        } else {
            return conn;
        }
    }

    public synchronized void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
