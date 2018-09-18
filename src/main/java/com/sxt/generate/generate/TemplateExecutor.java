package com.sxt.generate.generate;

import com.sxt.generate.config.Configuration;
import com.sxt.generate.table.Table;
import com.sxt.generate.table.Template;
import com.sxt.generate.util.StringUtil;
import com.sxt.generate.util.TemplateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
    private final String CURRENT_DIR = System.getProperty("user.dir");

    private final String entityTemplateName = "${className}.java";
    private final String mapperTemplateName = "${className}Mapper.java";
    private final String daoTemplateName = "${className}Dao.java";
    private final String serviceTemplateName = "${className}Service.java";
    private final String serviceImplTemplateName = "${className}ServiceImpl.java";
    private final String webTemplateName = "${className}Controller.java";
    private final String r_mapperTempaleName = "${className}_Mapper.xml";

    private  String generate_module = "generate";

    private boolean override = false;
    private String projectDir = ".";
    private String folder = "";
    private String templateDir = "";
    private String basePackage = "";

    private String entityModule = "";
    private String mapperModule = "";
    private String daoModule = "";
    private String serviceModule = "";
    private String serviceImplModule = "";
    private String webModule = "";

    private String entityPackage = "";
    private String mapperPackage = "";
    private String daoPackage = "";
    private String servicePackage = "";
    private String serviceImplPackage = "";
    private String webPackage = "";


    private String r_folder = "";
    private String r_basePackage = "";
    private String r_mapperModule = "";
    private String r_mapperPackage = "";

    private Configuration configuration;
    public TemplateExecutor(){}
    public TemplateExecutor configure(Configuration configuration){
        this.configuration = configuration;
        //获取template信息
        if (StringUtils.isNoneBlank(configuration.getTemplateConfiguration().getGenerate_module())) {
            generate_module = configuration.getTemplateConfiguration().getGenerate_module();
        }

        if (StringUtils.isNoneBlank(configuration.getTemplateConfiguration().getOverride())) {
            override = Boolean.parseBoolean(configuration.getTemplateConfiguration().getOverride());
        }
        if (StringUtils.isNoneBlank(configuration.getSourceConfiguration().getFolder())) {
            folder = configuration.getSourceConfiguration().getFolder();
        }
        if (StringUtils.isNoneBlank(configuration.getTemplateConfiguration().getTemplate_dir())) {
            templateDir = configuration.getTemplateConfiguration().getTemplate_dir();
        }
        if (StringUtils.isNoneBlank(configuration.getSourceConfiguration().getBasePackage())) {
            basePackage = configuration.getSourceConfiguration().getBasePackage();
        }
        //获取project信息

        if (StringUtils.isNoneBlank(configuration.getProjectConfiguration().getProjectName())) {
            projectDir = configuration.getProjectConfiguration().getProjectName();
        }
        //获取source信息
        if (configuration.getSourceConfiguration().getEntityConfiguration() != null) {
            entityModule = configuration.getSourceConfiguration().getEntityConfiguration().getModule();
            entityPackage = basePackage + "." + configuration.getSourceConfiguration().getEntityConfiguration().getSubPackage();
        }

        if (configuration.getSourceConfiguration().getMapperConfiguration() != null) {
            mapperModule = configuration.getSourceConfiguration().getMapperConfiguration().getModule();
            mapperPackage = basePackage + "." + configuration.getSourceConfiguration().getMapperConfiguration().getSubPackage();
        }

        if (configuration.getSourceConfiguration().getDaoConfiguration() != null) {
            daoModule = configuration.getSourceConfiguration().getDaoConfiguration().getModule();
            daoPackage = basePackage + "." + configuration.getSourceConfiguration().getDaoConfiguration().getSubPackage();
        }

        if (configuration.getSourceConfiguration().getServiceConfiguration() != null) {
            serviceModule = configuration.getSourceConfiguration().getServiceConfiguration().getModule();
            servicePackage = basePackage + "." + configuration.getSourceConfiguration().getServiceConfiguration().getSubPackage();

            serviceImplModule = configuration.getSourceConfiguration().getServiceConfiguration().getModule();
            serviceImplPackage = basePackage + "." + configuration.getSourceConfiguration().getServiceConfiguration().getSubPackage() + ".impl";
        }

        if (configuration.getSourceConfiguration().getWebConfiguration() != null) {
            webModule = configuration.getSourceConfiguration().getWebConfiguration().getModule();
            webPackage = basePackage + "." + configuration.getSourceConfiguration().getWebConfiguration().getSubPackage();
        }

        //获取resource信息
        if (StringUtils.isNoneBlank(configuration.getResourceConfiguration().getFolder())) {
            r_folder = configuration.getResourceConfiguration().getFolder();
        }

        if (StringUtils.isNoneBlank(configuration.getResourceConfiguration().getBasePackage())) {
            r_basePackage = configuration.getResourceConfiguration().getBasePackage();
        }

        if (configuration.getResourceConfiguration().getMapperConfiguration() != null) {
            r_mapperModule = configuration.getResourceConfiguration().getMapperConfiguration().getModule();
            r_mapperPackage =  r_basePackage + "." + configuration.getResourceConfiguration().getMapperConfiguration().getSubPackage();
        }

        return this;
    }
    public void renderTemplate( Table table) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("table", table);
        context.put("className", table.getClassName());
        context.put("beanName", StringUtils.uncapitalize(table.getClassName()));
        context.put("basePackage", basePackage);
        context.put("entityPackage", entityPackage);
        context.put("mapperPackage", mapperPackage);
        context.put("daoPackage", daoPackage);
        context.put("servicePackage", servicePackage);
        context.put("serviceImplPackage", serviceImplPackage);
        context.put("controllerPackage", webPackage);
        context.put("author",  System.getProperty( "user.name" ));

        renderEntity(table,context);
        renderMapper(table,context);
        renderMapperXml(table, context);
        if(StringUtils.isNotEmpty(servicePackage)){
            renderService(table,context);
        }
        if(StringUtils.isNotEmpty(webPackage)){
            renderWeb(table,context);
        }
    }


    private void renderMapperXml(Table table, Map<String, Object> context) {
        Template mapperTemplate = new Template();
        mapperTemplate.setModule(r_mapperModule);
        mapperTemplate.setTemplateName(r_mapperTempaleName);
        mapperTemplate.setPackageName(r_mapperPackage);
        mapperTemplate.setFileName(table.getClassName() + "_Mapper");
        mapperTemplate.setTemplateDir(templateDir);
        mapperTemplate.setFolder(r_folder);
        mapperTemplate.setOverride(override);
        mapperTemplate.setFileType("xml");
        executeFile(mapperTemplate, table, context);
    }

    private void renderEntity(Table table, Map<String, Object> context) {
        Template entityTemplate = new Template();
        entityTemplate.setModule(entityModule);
        entityTemplate.setTemplateName(entityTemplateName);
        entityTemplate.setPackageName(entityPackage);
        entityTemplate.setFileName(table.getClassName());
        entityTemplate.setTemplateDir(templateDir);
        entityTemplate.setFolder(folder);
        entityTemplate.setOverride(override);
        entityTemplate.setFileType("java");
        executeFile(entityTemplate, table, context);
    }

    private void renderMapper(Table table, Map<String, Object> context) {
        Template mapperTemplate = new Template();
        mapperTemplate.setModule(mapperModule);
        mapperTemplate.setTemplateName(mapperTemplateName);
        mapperTemplate.setPackageName(mapperPackage);
        mapperTemplate.setFileName(table.getClassName() + "Mapper");
        mapperTemplate.setTemplateDir(templateDir);
        mapperTemplate.setFolder(folder);
        mapperTemplate.setOverride(override);
        mapperTemplate.setFileType("java");
        executeFile(mapperTemplate, table, context);
    }


    private void renderDao(Table table, Map<String, Object> context) {
        Template daoTemplate = new Template();
        daoTemplate.setModule(daoModule);
        daoTemplate.setTemplateName(daoTemplateName);
        daoTemplate.setPackageName(daoPackage);
        daoTemplate.setFileName(table.getClassName() + "Dao");
        daoTemplate.setTemplateDir(templateDir);
        daoTemplate.setFolder(folder);
        daoTemplate.setOverride(override);
        daoTemplate.setFileType("java");
        executeFile(daoTemplate, table, context);
    }

    private void renderService(Table table, Map<String, Object> context) {
        Template serviceTemplate = new Template();
        serviceTemplate.setModule(serviceModule);
        serviceTemplate.setTemplateName(serviceTemplateName);
        serviceTemplate.setPackageName(servicePackage);
        serviceTemplate.setFileName(table.getClassName() + "Service");
        serviceTemplate.setTemplateDir(templateDir);
        serviceTemplate.setFolder(folder);
        serviceTemplate.setOverride(override);
        serviceTemplate.setFileType("java");
        executeFile(serviceTemplate, table, context);

        Template serviceImplTemplate = new Template();
        serviceImplTemplate.setModule(serviceModule);
        serviceImplTemplate.setTemplateName(serviceImplTemplateName);
        serviceImplTemplate.setPackageName(serviceImplPackage);
        serviceImplTemplate.setFileName(table.getClassName() + "ServiceImpl");
        serviceImplTemplate.setTemplateDir(templateDir);
        serviceImplTemplate.setFolder(folder);
        serviceImplTemplate.setOverride(override);
        serviceImplTemplate.setFileType("java");
        executeFile(serviceImplTemplate, table, context);
    }

    private void renderWeb(Table table, Map<String, Object> context) {
        Template webTemplate = new Template();
        webTemplate.setModule(webModule);
        webTemplate.setTemplateName(webTemplateName);
        webTemplate.setPackageName(webPackage);
        webTemplate.setTemplateDir(templateDir);
        webTemplate.setFolder(folder);
        webTemplate.setOverride(override);
        webTemplate.setFileName(table.getClassName() + "Controller");
        webTemplate.setFileType("java");
        executeFile(webTemplate, table, context);
    }


    public void executeFile( Template template, Table table, Map<String, Object> context) {
        String templateFileName = StringUtils.join(new String[]{template.getTemplateDir(), template.getTemplateName()}, "/");
        String outputFileName = StringUtils.join(new String[]{
                this.CURRENT_DIR + "/.." ,
                this.projectDir,
                template.getModule(),
                template.getFolder(),
                StringUtils.lowerCase(template.getPackageName()).replaceAll("\\.", "/"),
                template.getFileName() + "." + template.getFileType()}, "/");
        TemplateUtil.renderFreemarker(context, templateFileName, outputFileName,template.isOverride());
    }

}
