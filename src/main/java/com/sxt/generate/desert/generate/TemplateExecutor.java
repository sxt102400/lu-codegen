package com.sxt.generate.desert.generate;

import com.sxt.generate.desert.config.*;
import com.sxt.generate.desert.table.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * ClassName : TemplateExecutor
 * Description :TemplateExecutor模版输出类
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class TemplateExecutor {



    /** eclipse **/
    //private final String  USER_DIR = System.getProperty("user.dir") + "/..";

    /**
     * idea
     **/
    // private final String  USER_DIR = System.getProperty("user.dir");


    private boolean override = false;

    private String templateDir ;

    private String author;

    private String projectName;

    private String moduleName;

    private String sourceFolder ;

    private String sourceBasePackage ;

    private String resourceFolder ;

    private String resourceBasePackage ;

    private String subPackage;

    private GenConfig configuration;

    public TemplateExecutor(){}

    public TemplateExecutor configure(GenConfig configuration){
        this.configuration = configuration;
        return this;

    }

    public void renderTemplate( Table table) {

        TemplateConfig templateConfig =  configuration.getTemplateConfig();

        if(StringUtils.isNotEmpty(templateConfig.getOverride())) {
            override = Boolean.parseBoolean(templateConfig.getOverride());
        }

        if(StringUtils.isNotEmpty(templateConfig.getTemplateDir())) {
            templateDir = templateConfig.getTemplateDir();
        }

        if(StringUtils.isNotEmpty(templateConfig.getAuthor())) {
            author = templateConfig.getAuthor();
        }

        List<ProjectConfig> projectConfigs = configuration.getProjectConfigs();

        for(ProjectConfig  projectConfig: projectConfigs){

            projectName = projectConfig.getName();
            moduleName = projectConfig.getModule();

            SourceConfig sourceConfig = projectConfig.getSourceConfig();

            ResourceConfig resourceConfig = projectConfig.getResourceConfig();

            if(sourceConfig!=null){
                sourceFolder = sourceConfig.getFolder();
                sourceBasePackage = sourceConfig.getBasePackage();
            }

            if(resourceConfig!=null) {
                resourceFolder = resourceConfig.getFolder();
                resourceBasePackage = resourceConfig.getBasePackage();

            }
            if(sourceConfig!=null){
                List<FileConfig> fileConfigs= sourceConfig.getFileConfigs();

                for(FileConfig fileConfig: fileConfigs){
                    FileContext context = new FileContext();
                    context.setTemplateDir(templateDir);
                    context.setOverride(override);
                    context.setProjectName(projectName);
                    context.setModuleName(moduleName);

                    context.setSourceFolder(sourceFolder);
                    context.setSourceBasePackage(sourceBasePackage);
                    context.setResourceFolder(resourceFolder);
                    context.setResourceBasePackage(resourceBasePackage);

                    context.setSubPackage(fileConfig.getSubPackage());
                    context.setTemplateName(fileConfig.getTemplate());
                    context.setAuthor(author);
                    context.renderSourceTemplate(table);
                }
            }

            if(resourceConfig!=null) {

                List<FileConfig> rfileConfigs= sourceConfig.getFileConfigs();
                for(FileConfig fileConfig: rfileConfigs){
                    FileContext context = new FileContext();
                    context.setTemplateDir(templateDir);
                    context.setOverride(override);
                    context.setProjectName(projectName);
                    context.setModuleName(moduleName);

                    context.setSourceFolder(sourceFolder);
                    context.setSourceBasePackage(sourceBasePackage);
                    context.setResourceFolder(resourceFolder);
                    context.setResourceBasePackage(resourceBasePackage);

                    context.setSubPackage(fileConfig.getSubPackage());
                    context.setTemplateName(fileConfig.getTemplate());
                    context.setAuthor(author);
                    context.renderResourceTemplate(table);
                }
            }
        }



    }
}
