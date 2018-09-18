package com.sxt.generate.config;
/**
 * ClassName : SourceConfiguration
 * Description : SourceConfiguration
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class SourceConfiguration {

    private String basePackage;

    private String folder;

    private EntityConfiguration entityConfiguration;

    private MapperConfiguration mapperConfiguration;

    private DaoConfiguration daoConfiguration;

    private ServiceConfiguration serviceConfiguration;

    private WebConfiguration webConfiguration;

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

    public EntityConfiguration getEntityConfiguration() {
        return entityConfiguration;
    }

    public void setEntityConfiguration(EntityConfiguration entityConfiguration) {
        this.entityConfiguration = entityConfiguration;
    }

    public WebConfiguration getWebConfiguration() {
        return webConfiguration;
    }

    public void setWebConfiguration(WebConfiguration webConfiguration) {
        this.webConfiguration = webConfiguration;
    }

    public ServiceConfiguration getServiceConfiguration() {

        return serviceConfiguration;
    }

    public void setServiceConfiguration(ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }

    public DaoConfiguration getDaoConfiguration() {
        return daoConfiguration;
    }

    public void setDaoConfiguration(DaoConfiguration daoConfiguration) {
        this.daoConfiguration = daoConfiguration;
    }

    public MapperConfiguration getMapperConfiguration() {
        return mapperConfiguration;
    }

    public void setMapperConfiguration(MapperConfiguration mapperConfiguration) {
        this.mapperConfiguration = mapperConfiguration;
    }
}
