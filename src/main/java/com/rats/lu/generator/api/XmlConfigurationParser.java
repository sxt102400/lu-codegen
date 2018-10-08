package com.rats.lu.generator.api;

import com.rats.lu.generator.config.*;
import com.rats.lu.generator.exception.XMLParserException;
import com.rats.lu.generator.table.ColumnOverride;
import com.rats.lu.generator.utils.ObjectFactory;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Copyright (C) 2016
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class XmlConfigurationParser {

    Properties prop;

    public XmlConfigurationParser() {
        prop = new Properties();
    }

    public Configuration parse(Element rootNode) throws XMLParserException {
        Configuration configuration = new Configuration();
        NodeList nodeList = rootNode.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("properties".equals(childNode.getNodeName())) {
                    this.parseProperties(configuration, childNode);
                } else if ("jdbcConnection".equals(childNode.getNodeName())) {
                    this.parseJdbcConnection(configuration, childNode);
                } else if ("templates".equals(childNode.getNodeName())) {
                    this.parseTemplates(configuration, childNode);
                } else if ("modules".equals(childNode.getNodeName())) {
                    this.parseModules(configuration, childNode);
                } else if ("tables".equals(childNode.getNodeName())) {
                    this.parseTables(configuration, childNode);
                }
            }
        }

        return configuration;
    }

    protected void parseJdbcConnection(Configuration configuration, Node node) throws XMLParserException {

        JdbcConnectionConfiguration jdbcConnection = new JdbcConnectionConfiguration();
        configuration.setJdbcConnectionConfiguration(jdbcConnection);

        Properties attributes = this.parseAttributes(node);

        String driverClassName = attributes.getProperty("driverClassName");
        String url = attributes.getProperty("url");
        String username = attributes.getProperty("username");
        String password = attributes.getProperty("password");

        jdbcConnection.setDriverClassName(driverClassName);
        jdbcConnection.setUrl(url);
        jdbcConnection.setUsername(username);
        jdbcConnection.setPassword(password);
        configuration.setJdbcConnectionConfiguration(jdbcConnection);

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1 && "property".equals(childNode.getNodeName())) {
                this.parseProperty(jdbcConnection, childNode);
            }
        }
    }

    protected void parseTemplates(Configuration configuration, Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("template".equals(childNode.getNodeName())) {
                    TemplateConfiguration templateConfiguration = new TemplateConfiguration();
                    Properties attributes = this.parseAttributes(childNode);
                    String name = attributes.getProperty("name");
                    String packageName = attributes.getProperty("packageName");
                    String fileName = attributes.getProperty("fileName");
                    String type = attributes.getProperty("type");

                    templateConfiguration.setName(name);
                    templateConfiguration.setPackageName(packageName);
                    templateConfiguration.setFileName(fileName);
                    templateConfiguration.setType(type);
                    configuration.addTemplateConfiguration(templateConfiguration);
                }
            }
        }
    }

    protected void parseModules(Configuration configuration, Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("module".equals(childNode.getNodeName())) {
                    ModuleConfiguration moduleConfiguration = new ModuleConfiguration();
                    Properties attributes = this.parseAttributes(childNode);
                    String name = attributes.getProperty("name");
                    String moduleDir = attributes.getProperty("moduleDir");
                    String sources = StringUtils.defaultIfBlank(attributes.getProperty("sources"), ConstantConfig.DEFAULT_SOURCES);
                    String resources = StringUtils.defaultIfBlank(attributes.getProperty("resources"), ConstantConfig.DEFAULT_RESOURCES);
                    String templates = attributes.getProperty("templates");
                    String isSub = attributes.getProperty("isSub");

                    moduleConfiguration.setName(name);
                    moduleConfiguration.setModuleDir(moduleDir);
                    moduleConfiguration.setSources(sources);
                    moduleConfiguration.setResources(resources);
                    moduleConfiguration.setTemplates(templates);
                    moduleConfiguration.setSub(Boolean.valueOf(isSub));
                    configuration.addModuleConfiguration(moduleConfiguration);
                }
            }
        }
    }

    protected void parseTables(Configuration configuration, Node node) throws XMLParserException {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("table".equals(childNode.getNodeName())) {
                    TableConfiguration tableConfiguration = new TableConfiguration();
                    Properties attributes = this.parseAttributes(childNode);
                    String tableName = attributes.getProperty("tableName");
                    String className = attributes.getProperty("className");
                    String subPackageName = attributes.getProperty("subPackageName");
                    String catalog = attributes.getProperty("catalog");
                    String schema = attributes.getProperty("schema");

                    tableConfiguration.setTableName(tableName);
                    tableConfiguration.setClassName(className);
                    tableConfiguration.setSubPackageName(subPackageName);
                    tableConfiguration.setCatalog(catalog);
                    tableConfiguration.setSchema(schema);
                    parseColumns(tableConfiguration, childNode);
                    configuration.addTableConfiguration(tableConfiguration);

                }
            }
        }
    }

    protected void parseColumns(TableConfiguration tableConfiguration, Node node) throws XMLParserException {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == 1) {
                if ("columnOverride".equalsIgnoreCase(childNode.getNodeName())) {
                    this.parseColumnOverride(tableConfiguration, childNode);
                }
                if ("columnIgnore".equalsIgnoreCase(childNode.getNodeName())) {
                    this.parsColumnIgnore(tableConfiguration, childNode);
                }
            }
        }

    }

    protected void parseColumnOverride(TableConfiguration tableConfiguration, Node node) throws XMLParserException {
        Properties attributes = this.parseAttributes(node);
        String column = attributes.getProperty("column");
        String field = attributes.getProperty("field");
        String javaType = attributes.getProperty("javaType");
        String jdbcType = attributes.getProperty("jdbcType");
        String ignore = attributes.getProperty("ignore");
        String serialize = attributes.getProperty("serialize");

        ColumnOverride columnOverride = new ColumnOverride();
        columnOverride.setColumnName(column);
        columnOverride.setFieldName(field);
        columnOverride.setJavaType(javaType);
        columnOverride.setJdbcType(jdbcType);
        if (StringUtils.isNotBlank(ignore)) {
            columnOverride.setIgnore(Boolean.valueOf(ignore));
        }
        if (StringUtils.isNotBlank(serialize)) {
            columnOverride.setIgnore(Boolean.valueOf(serialize));
        }
        if (Boolean.valueOf(ignore)) {
            tableConfiguration.addColumnIgnore(columnOverride);
        } else {
            tableConfiguration.addColumnOverride(columnOverride);
        }

    }


    protected void parsColumnIgnore(TableConfiguration tableConfiguration, Node node) throws XMLParserException {
        Properties attributes = this.parseAttributes(node);
        String column = attributes.getProperty("column");
        ColumnOverride columnOverride = new ColumnOverride();
        columnOverride.setColumnName(column);
        columnOverride.setIgnore(true);
        tableConfiguration.addColumnIgnore(columnOverride);
    }

    /**
     * parseProperties
     * <p>
     * 1. <properties url=""></properties>
     * 2. <properties source=""></properties>
     * 3. <properties><property name="" value=""></property></properties>
     *
     * @param configuration
     * @param node
     * @throws XMLParserException
     */
    protected void parseProperties(Configuration configuration, Node node) throws XMLParserException {
        Properties attributes = this.parseAttributes(node);

        String resource = attributes.getProperty("resource");
        String url = attributes.getProperty("url");
        String property = attributes.getProperty("property");
        try {
            if (StringUtils.isNotBlank(resource)) {         // parse  resource
                URL resourceUrl = ObjectFactory.getResource(resource);
                if (resourceUrl == null) {
                    throw new XMLParserException("parse properties resource error:" + resource);
                }
                InputStream inputStream = null;
                inputStream = resourceUrl.openConnection().getInputStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                this.prop.putAll(properties);
                inputStream.close();
            } else if (StringUtils.isNotBlank(url)) {              // parse  url
                URL resourceUrl = new URL(url);
                if (resourceUrl == null) {
                    throw new XMLParserException("parse properties url error:" + resource);
                }
                InputStream inputStream = resourceUrl.openConnection().getInputStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                this.prop.putAll(properties);
                inputStream.close();
            } else {                                           // parse  property
                NodeList nodeList = node.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node childNode = nodeList.item(i);
                    if (childNode.getNodeType() == 1) {
                        Properties attr = this.parseAttributes(childNode);
                        String name = attr.getProperty("name");
                        String value = attr.getProperty("value");
                        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                            this.prop.put(name, value);
                        } else {
                            throw new XMLParserException("parse property error:" + name + ":" + value);
                        }
                    }
                }
            }
            configuration.setProperties(this.prop);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 以下参考mybatis实现，org.mybatis.generator.config.xml.ConfigurationParser
     *
     * @param node
     * @return
     */
    protected void parseProperty(PropertyHolder propertyHolder, Node node) {
        Properties attributes = this.parseAttributes(node);
        String name = attributes.getProperty("name");
        String value = attributes.getProperty("value");
        propertyHolder.addProperty(name, value);
    }

    /**
     * 以下参考mybatis实现，org.mybatis.generator.config.xml.ConfigurationParser
     *
     * @param node
     * @return
     */
    protected Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap nnm = node.getAttributes();
        for (int i = 0; i < nnm.getLength(); ++i) {
            Node attribute = nnm.item(i);
            String value = this.parsePropertyTokens(attribute.getNodeValue());
            attributes.put(attribute.getNodeName(), value);
        }
        return attributes;
    }

    private String parsePropertyTokens(String string) {
        String OPEN = "${";
        String CLOSE = "}";
        String newString = string;
        if (string != null) {
            int start = string.indexOf("${");
            for (int end = string.indexOf("}"); start > -1 && end > start; end = newString.indexOf("}", end)) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + "}".length());
                String propName = newString.substring(start + "${".length(), end);
                String propValue = this.resolveProperty(propName);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }

                start = newString.indexOf("${", end);
            }
        }
        return newString;
    }

    private String resolveProperty(String key) {
        String property = null;
        property = System.getProperty(key);
        if (property == null) {
            property = this.prop.getProperty(key);
        }
        return property;
    }
}
