package com.rats.lu.generator.config;

import java.util.Map;
import java.util.Properties;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public abstract class PropertyHolder<T> {

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

    private static String parsePropertyTokens(String string, Map<String, Object> context) {
        String OPEN = "${";
        String CLOSE = "}";
        String newString = string;
        if (string != null) {
            int start = string.indexOf("${");
            for (int end = string.indexOf("}"); start > -1 && end > start; end = newString.indexOf("}", end)) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + "}".length());
                String propName = newString.substring(start + "${".length(), end);
                String propValue = resolveProperty(propName,context);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }

                start = newString.indexOf("${", end);
            }
        }
        return newString;
    }

    private static String resolveProperty(String key,Map<String, Object> context) {
        String property = null;
        property = System.getProperty(key);
        if (property == null && context.get(key)!= null) {
            property = context.get(key).toString();
        }
        return property;
    }
}
