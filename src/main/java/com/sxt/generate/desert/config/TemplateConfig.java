package com.sxt.generate.desert.config;

/**
 * ClassName : TemplateConfiguration
 * Description : TemplateConfiguration
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class TemplateConfig {

    private String templateDir;

    private String override;

    private String author;

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getOverride() {
        return override;
    }

    public void setOverride(String override) {
        this.override = override;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
