package com.rats.lu.generator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class Configuration {

    private Properties properties = new Properties();

    private JdbcConnectionConfiguration jdbcConnectionConfiguration;

    private List<TemplateConfiguration> templateConfigurations;

    private List<ModuleConfiguration> moduleConfigurations;

    private List<TableConfiguration>  tableConfigurations;

    public Configuration(){
        if(templateConfigurations == null) {
            templateConfigurations = new ArrayList<>();
        }
        if(moduleConfigurations == null) {
            moduleConfigurations = new ArrayList<>();
        }
        if(tableConfigurations == null) {
            tableConfigurations = new ArrayList<>();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public JdbcConnectionConfiguration getJdbcConnectionConfiguration() {
        return jdbcConnectionConfiguration;
    }

    public void setJdbcConnectionConfiguration(JdbcConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public List<TemplateConfiguration> getTemplateConfigurations() {
        return templateConfigurations;
    }

    public void addTemplateConfiguration(TemplateConfiguration templateConfiguration) {
        this.templateConfigurations.add(templateConfiguration);
    }

    public List<ModuleConfiguration> getModuleConfigurations() {
        return moduleConfigurations;
    }

    public void addModuleConfiguration(ModuleConfiguration moduleConfiguration) {
        this.moduleConfigurations.add(moduleConfiguration);
    }

    public List<TableConfiguration> getTableConfigurations() {
        return tableConfigurations;
    }

    public void addTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfigurations.add(tableConfiguration);
    }

}
