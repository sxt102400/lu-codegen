package com.sxt.generate.config;

import java.util.List;

/**
 * ClassName : TemplateConfiguration
 * Description : TemplateConfiguration
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class TemplateConfiguration {

    private String generate_module;

    private String source_dir;

    private String resource_dir;

    private String template_dir;

    private String override;

    private String basePackage;

    public String getGenerate_module() {
        return generate_module;
    }

    public void setGenerate_module(String generate_module) {
        this.generate_module = generate_module;
    }

    public String getSource_dir() {
        return source_dir;
    }

    public void setSource_dir(String source_dir) {
        this.source_dir = source_dir;
    }

    public String getResource_dir() {
        return resource_dir;
    }

    public void setResource_dir(String resource_dir) {
        this.resource_dir = resource_dir;
    }

    public String getTemplate_dir() {
        return template_dir;
    }

    public void setTemplate_dir(String template_dir) {
        this.template_dir = template_dir;
    }

    public String getOverride() {
        return override;
    }

    public void setOverride(String override) {
        this.override = override;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
