package com.rats.lu.generator.db;

public class JDBCConnectionFactory {


    /**
     * This constructor is called when there is a JDBCConnectionConfiguration
     * specified in the configuration.
     *
     * @param config the configuration
     */
    public JDBCConnectionFactory(JDBCConnectionConfiguration config) {
        super();
        userId = config.getUserId();
        password = config.getPassword();
        connectionURL = config.getConnectionURL();
        driverClass = config.getDriverClass();
        otherProperties = config.getProperties();
    }

    /**
     * This constructor is called when this connection factory is specified
     * as the type in a ConnectionFactory configuration element.
     */
    public JDBCConnectionFactory() {
        super();
    }

    @Override
    public Connection getConnection() throws SQLException {

        Properties props = new Properties();

        if (stringHasValue(userId)) {
            props.setProperty("user", userId); //$NON-NLS-1$
        }

        if (stringHasValue(password)) {
            props.setProperty("password", password); //$NON-NLS-1$
        }

        props.putAll(otherProperties);

        Driver driver = getDriver();
        Connection conn = driver.connect(connectionURL, props);

        if (conn == null) {
            throw new SQLException(getString("RuntimeError.7")); //$NON-NLS-1$
        }

        return conn;
    }

    private Driver getDriver() {
        Driver driver;

        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.8"), e); //$NON-NLS-1$
        }

        return driver;
    }
}
