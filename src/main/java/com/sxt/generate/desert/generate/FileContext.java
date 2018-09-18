package com.sxt.generate.desert.generate;

import com.sxt.generate.desert.table.Table;
import com.sxt.generate.desert.util.TemplateUtil;
import com.sxt.generate.desert.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shangxiutao on 17/2/22.
 */
public class FileContext {

    private static final String CURRENT_DIR = System.getProperty("user.dir");

    private boolean override = false;

    private String templateDir ;

    private String author ;

    private String projectName;

    private String moduleName;

    private String sourceFolder ;

    private String resourceFolder ;

    private String sourceBasePackage;

    private String resourceBasePackage;

    private String subPackage;

    private String templateName;

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public String getResourceFolder() {
        return resourceFolder;
    }

    public void setResourceFolder(String resourceFolder) {
        this.resourceFolder = resourceFolder;
    }

    public String getSourceBasePackage() {
        return sourceBasePackage;
    }

    public void setSourceBasePackage(String sourceBasePackage) {
        this.sourceBasePackage = sourceBasePackage;
    }

    public String getResourceBasePackage() {
        return resourceBasePackage;
    }

    public void setResourceBasePackage(String resourceBasePackage) {
        this.resourceBasePackage = resourceBasePackage;
    }

    public String getSubPackage() {
        return subPackage;
    }

    public void setSubPackage(String subPackage) {
        this.subPackage = subPackage;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void renderSourceTemplate( Table table) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("table", table);
        context.put("className", table.getClassName());
        context.put("beanName", StringUtils.uncapitalize(table.getClassName()));

        context.put("folder", sourceFolder);
        context.put("basePackage", sourceBasePackage);
        context.put("resource.folder", resourceFolder);
        context.put("resource.basePackage", resourceBasePackage);

        String packageName = StringUtils.join(new String[]{ sourceBasePackage,subPackage},".");
        String packageDir = StringUtils.lowerCase(packageName).replaceAll("\\.", "/");

        String templateFileName = StringUtils.join(new String[]{templateDir, templateName }, "/");
        String contextFileName= TokenUtils.parsePropertyTokens(context, templateName);


        String outputFileName = StringUtils.join(new String[]{
                this.CURRENT_DIR + "/..",
                StringUtils.defaultString(this.projectName, ""),
                StringUtils.defaultString(this.moduleName, ""),
                StringUtils.defaultString(this.sourceFolder, ""),
                packageDir,
                contextFileName
        }, "/");
        TemplateUtil.renderFreemarker(context, templateFileName, outputFileName, override);
    }

    public void renderResourceTemplate( Table table) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("table", table);
        context.put("className", table.getClassName());
        context.put("beanName", StringUtils.uncapitalize(table.getClassName()));

        context.put("folder", sourceFolder);
        context.put("basePackage", sourceBasePackage);
        context.put("resource.folder", resourceFolder);
        context.put("resource.basePackage", resourceBasePackage);

        context.put("subPackage", subPackage);
        context.put("author", StringUtils.defaultString(author, System.getProperty( "user.name" )));

        String packageName = StringUtils.join(new String[]{ resourceBasePackage,subPackage},".");
        String packageDir = StringUtils.lowerCase(packageName).replaceAll("\\.", "/");

        String templateFileName = StringUtils.join(new String[]{templateDir, templateName }, "/");
        String contextFileName= TokenUtils.parsePropertyTokens(context, templateName);

        String outputFileName = StringUtils.join(new String[]{
                this.CURRENT_DIR + "/..",
                StringUtils.defaultString(this.projectName, ""),
                StringUtils.defaultString(this.moduleName, ""),
                StringUtils.defaultString(this.resourceFolder, ""),
                packageDir,
                contextFileName
        }, "/");
        TemplateUtil.renderFreemarker(context, templateFileName, outputFileName, override);
    }
}
