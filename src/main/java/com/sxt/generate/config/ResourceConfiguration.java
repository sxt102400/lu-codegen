package com.sxt.generate.config;

/**
 * ClassName : ResourceConfiguration
 * Description : ResourceConfiguration
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class ResourceConfiguration {

    private String basePackage;

    private String folder;

    private MapperConfiguration mapperConfiguration;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public MapperConfiguration getMapperConfiguration() {
        return mapperConfiguration;
    }

    public void setMapperConfiguration(MapperConfiguration mapperConfiguration) {
        this.mapperConfiguration = mapperConfiguration;
    }
}
