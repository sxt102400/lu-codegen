package com.rats.lu.generator.config;

import java.util.Properties;

public abstract class PropertyHolder {

    private Properties properties = new Properties();

    public PropertyHolder() {
    }

    public void addProperty(String name, String value) {
        this.properties.setProperty(name, value);
    }

    public String getProperty(String name) {
        return this.properties.getProperty(name);
    }

    public Properties getProperties() {
        return this.properties;
    }
}
