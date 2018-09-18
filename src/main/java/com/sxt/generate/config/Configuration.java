package com.sxt.generate.config;


import java.util.ArrayList;
import java.util.List;

/**
 * ClassName :Configuration
 * Description : Configuration类
 *
 * @version:v1.0.0
 * @author: hanbing
 * @date:
 */
public class Configuration {

    private List<String> renders = new ArrayList<String>();

    private TemplateConfiguration templateConfiguration;

    private ProjectConfiguration projectConfiguration;

    private JdbcConfiguration jdbcConfiguration;

    private SourceConfiguration sourceConfiguration;

    private ResourceConfiguration resourceConfiguration;

    private List<GenerateConfiguration> generateConfigurations;

    public List<String> getRenders() {
        return renders;
    }

    public void setRenders(List<String> renders) {
        this.renders = renders;
    }

    public TemplateConfiguration getTemplateConfiguration() {
        return templateConfiguration;
    }

    public void setTemplateConfiguration(TemplateConfiguration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }

    public ProjectConfiguration getProjectConfiguration() {
        return projectConfiguration;
    }

    public void setProjectConfiguration(ProjectConfiguration projectConfiguration) {
        this.projectConfiguration = projectConfiguration;
    }

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public SourceConfiguration getSourceConfiguration() {
        return sourceConfiguration;
    }

    public void setSourceConfiguration(SourceConfiguration sourceConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
    }

    public ResourceConfiguration getResourceConfiguration() {
        return resourceConfiguration;
    }

    public void setResourceConfiguration(ResourceConfiguration resourceConfiguration) {
        this.resourceConfiguration = resourceConfiguration;
    }

    public List<GenerateConfiguration> getGenerateConfigurations() {
        return generateConfigurations;
    }

    public void setGenerateConfigurations(List<GenerateConfiguration> generateConfigurations) {
        this.generateConfigurations = generateConfigurations;
    }
}
