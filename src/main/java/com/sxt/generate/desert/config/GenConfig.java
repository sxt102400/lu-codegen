package com.sxt.generate.desert.config;


import java.util.List;

/**
 * ClassName :Configuration
 * Description : Configuration类
 *
 * @version:v1.0.0
 * @author: hanbing
 * @date:
 */
public class GenConfig {

    private TemplateConfig templateConfig;

    private JdbcConfig jdbcConfig;

    private List<ProjectConfig>  ProjectConfigs;

    private List<GenerateConfig> generateConfigs;

    public TemplateConfig getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    public JdbcConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public void setJdbcConfig(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public List<ProjectConfig> getProjectConfigs() {
        return ProjectConfigs;
    }

    public void setProjectConfigs(List<ProjectConfig> projectConfigs) {
        ProjectConfigs = projectConfigs;
    }

    public List<GenerateConfig> getGenerateConfigs() {
        return generateConfigs;
    }

    public void setGenerateConfigs(List<GenerateConfig> generateConfigs) {
        this.generateConfigs = generateConfigs;
    }
}
