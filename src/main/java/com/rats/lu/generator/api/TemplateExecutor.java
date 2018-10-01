package com.rats.lu.generator.api;


import com.rats.lu.generator.config.*;
import com.rats.lu.generator.table.IntrospectedTable;
import com.rats.lu.generator.template.TemplateRender;
import com.rats.lu.generator.template.TemplateRenderFactory;
import com.rats.lu.generator.utils.ObjectFactory;
import com.rats.lu.generator.utils.PathUtils;
import com.rats.lu.generator.utils.MsgFmt;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class TemplateExecutor {

    private final String USER_DIR = System.getProperty("user.dir");
    private String classesDir;
    private Configuration configuration;
    IntrospectedTable introspectedTable;
    File projectPath;
    File templatePath;

    TemplateExecutor(Configuration configuration, IntrospectedTable introspectedTable) {
        this.configuration = configuration;
        this.introspectedTable = introspectedTable;
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            classesDir = new URI(path).getPath();
            classesDir = new File(classesDir).getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void generate() throws IOException {
        String projectDir = configuration.getProperties().getProperty("projectDir");

        if (PathUtils.isAbsolutePath(projectDir)) {
            projectPath = Paths.get(projectDir).toFile();
        } else {
            projectPath = Paths.get(classesDir, "../../", projectDir).toFile();
        }
        if (!projectPath.exists()) {
            System.err.print(MsgFmt.getString("\t\t[error!] 当前配置的工程目录不存在:{0}\n", projectPath.getAbsolutePath()));
            return;
        }

        /**
         *  templateFile
         */
        String templateDirName = configuration.getProperties().getProperty("templateDir");
        templatePath = Paths.get(classesDir, templateDirName).toFile();

        List<ModuleConfiguration> moduleConfiguration = configuration.getModuleConfigurations();
        for (ModuleConfiguration module : moduleConfiguration) {
            List<TemplateConfiguration> moduleTemplates = getModuleTemplates(module);
            for (TemplateConfiguration template : moduleTemplates) {
                generateFile(module, template, introspectedTable);
            }
        }
    }

    private List<TemplateConfiguration> getModuleTemplates(ModuleConfiguration module) {
        String templates = module.getTemplates();
        List<TemplateConfiguration> templateList = new ArrayList<TemplateConfiguration>();
        List<TemplateConfiguration> templateConfigurations = configuration.getTemplateConfigurations();
        String[] arr = templates.split(",");
        for (String tpl : arr) {
            if ("*".equals(tpl)) {
                templateList.addAll(templateConfigurations);
            } else {
                for (TemplateConfiguration template : templateConfigurations) {
                    if (template.getName().trim().equals(tpl))
                        templateList.add(template);
                }
            }
        }
        return templateList;
    }


    public void generateFile(ModuleConfiguration module, TemplateConfiguration template, IntrospectedTable introspectedTable) {


        Map context = initContext();

        /**
         *  templateFile
         */

        String templateFile = template.getFileName();

        /**
         *  outputFile
         */
        List<String> pathList = new ArrayList<String>();
        String moduleDir = module.getModuleDir();
        if (StringUtils.isNotBlank(moduleDir)) {
            pathList.add(moduleDir);
        }

        //String resource = module.getResource();
        if (ConstantConfig.TYPE_XML.equalsIgnoreCase(template.getType()) ||
                ConstantConfig.TYPE_XMLMAPPER.equalsIgnoreCase(template.getType())) {
            String resource = module.getResources();
            if (StringUtils.isNotBlank(resource)) {
                pathList.add(resource);
            }
        } else {
            String source = module.getSources();
            if (StringUtils.isNotBlank(source)) {
                pathList.add(source);
            }
        }

        String packageName = template.getPackageName();
        if (StringUtils.isNotBlank(packageName)) {
            packageName = PathUtils.resolveFileName(packageName, context);
            packageName = PathUtils.getPathOfPackage(packageName);
            pathList.add(packageName);
        }

        String outFileName = template.getFileName();
        if (StringUtils.isNotBlank(outFileName)) {
            outFileName = PathUtils.resolveFileName(outFileName, context);
            pathList.add(outFileName);
        }

        String[] arr = new String[pathList.size()];
        String[] pathArr = pathList.toArray(arr);
        String outputFile = Paths.get("", pathArr).toFile().getPath();

        String overrideStr = configuration.getProperties().getProperty("override");
        overrideStr = StringUtils.defaultIfBlank(overrideStr, "false");
        boolean override = Boolean.valueOf(overrideStr);

        String render = configuration.getProperties().getProperty("render");
        render = StringUtils.defaultIfBlank(render, "freemarker");
        TemplateRender templateRender = TemplateRenderFactory.createInstance(render);
        templateRender.config(projectPath, templatePath);
        templateRender.render(context, templateFile, outputFile, override);

    }

    private Map initContext() {
        Map<String, Object> context = new HashMap<String, Object>();


        context.put("table", introspectedTable);
        context.put("tableName", introspectedTable.getTableName());
        context.put("className", introspectedTable.getClassName());
        context.put("subPackageName", introspectedTable.getSubPackageName());
        context.put("catalog", introspectedTable.getCatalog());
        context.put("schema", introspectedTable.getSchema());
        context.put("entityName", StringUtils.uncapitalize(introspectedTable.getClassName()));
        String author = configuration.getProperties().getProperty("author");
        context.put("author", StringUtils.defaultIfBlank(author, System.getProperty("user.name")));

        Properties properties = configuration.getProperties();
        for (Object keyObj : properties.keySet()) {
            String key = (String) keyObj;
            context.put("@prop_" + key.trim(), properties.getProperty(key));
        }
        List<ModuleConfiguration> moduleConfiguration = configuration.getModuleConfigurations();
        for (ModuleConfiguration module : moduleConfiguration) {
            context.put("@mod_" + module.getName().trim(), ObjectFactory.resolveParams(module ,context));
        }
        List<TemplateConfiguration> templateConfigurations = configuration.getTemplateConfigurations();
        for (TemplateConfiguration template : templateConfigurations) {
            context.put("@tpl_" + template.getName().trim(), ObjectFactory.resolveParams(template ,context));
        }
        return context;
    }
}
