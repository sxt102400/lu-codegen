package com.rats.lu.generator.config;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class ModuleConfiguration extends PropertyHolder{

    private String name;
    private String moduleDir;
    private String sources;
    private String resources;
    private String templates;
    private boolean sub;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }

    public String getModuleDir() {
        return moduleDir;
    }

    public void setModuleDir(String moduleDir) {
        this.moduleDir = moduleDir;
    }
}
