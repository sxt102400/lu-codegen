package com.sxt.generate.desert.config;

import com.sxt.generate.desert.Exception.GenerateException;
import com.sxt.generate.desert.util.TokenUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * ClassName : ConfigurationParser
 * Description : Configuration解析类
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class ConfigParser {

    private Properties properties = new Properties();

    public GenConfig parse(String file) {

        GenConfig genConfig = new GenConfig();

        try {
            XMLConfiguration xmlConfig = new XMLConfiguration(file);
            xmlConfig.setExpressionEngine(new XPathExpressionEngine());

            String location = xmlConfig.getString("properties/@location");
            if (location != null) {
                parseProperties(location);
            }

            Configuration jdbcXmlConfig =  xmlConfig.configurationAt("jdbc");
            JdbcConfig jdbcConfig = parseJdbc(jdbcXmlConfig);


            Configuration templateXmlConfig =  xmlConfig.configurationAt("template");
            TemplateConfig templateConfig = parseTemplate(templateXmlConfig);

            SubnodeConfiguration contextXmlConfig =  xmlConfig.configurationAt("context");
            List<ProjectConfig> projectConfigs = parseProject(contextXmlConfig);

            SubnodeConfiguration generateXmlConfig =  xmlConfig.configurationAt("generates");
            List<GenerateConfig> generateConfigs = parseGenerates(generateXmlConfig);

            genConfig.setJdbcConfig(jdbcConfig);
            genConfig.setTemplateConfig(templateConfig);
            genConfig.setProjectConfigs(projectConfigs);
            genConfig.setGenerateConfigs(generateConfigs);

        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (GenerateException e) {
            e.printStackTrace();
        }
        return genConfig;
    }

    /**
     * 解析jdbc
     *
     * @param
     * @return JdbcConfiguration
     */
    private JdbcConfig parseJdbc(Configuration config)  {
        JdbcConfig jdbcConfig = new JdbcConfig();

        String driverClassName = config.getString("/property[@name='driverClassName']/@value");
        driverClassName = TokenUtils.parsePropertyTokens(this.properties, driverClassName);
        jdbcConfig.setDriverClassName(driverClassName);

        String url = config.getString("/property[@name='url']/@value");
        url = TokenUtils.parsePropertyTokens(this.properties, url);
        jdbcConfig.setUrl(url);

        String username = config.getString("/property[@name='username']/@value");
        username = TokenUtils.parsePropertyTokens(this.properties, username);
        jdbcConfig.setUsername(username);

        String password = config.getString("/property[@name='password']/@value");
        password = TokenUtils.parsePropertyTokens(this.properties, password);
        jdbcConfig.setPassword(password);

        return jdbcConfig;
    }

    /**
     * 解析Template
     *
     * @param
     * @return TemplateConfiguration
     */
    private TemplateConfig parseTemplate(Configuration config)  {

        TemplateConfig templateConfig = new TemplateConfig();

        String generateModule = config.getString("/property[@name='generate.module']/@value");

        String templateDir = config.getString("/property[@name='template.dir']/@value");
        templateConfig.setTemplateDir(templateDir);

        String override = config.getString("/property[@name='override']/@value");
        templateConfig.setOverride(override);

        String author = config.getString("/property[@name='author']/@value");
        templateConfig.setAuthor(author);

        return templateConfig;

    }


    private List<ProjectConfig> parseProject(SubnodeConfiguration config)  {

        List<ProjectConfig> projectConfigs= new ArrayList<>();

        List projectXmlConfigs = config.configurationsAt("project");

        for(Object objc: projectXmlConfigs){

            SubnodeConfiguration projectXmlConfig = (SubnodeConfiguration)objc;
            ProjectConfig projectConfig = new ProjectConfig();

            String projectName = projectXmlConfig.getString("/@name");
            projectConfig.setName(projectName);

            String module = projectXmlConfig.getString("/@module");
            projectConfig.setModule(module);

            List<ConfigurationNode>  tpnodes = projectXmlConfig.getRootNode().getChildren("sources");
            if(tpnodes.size()>0) {
                SubnodeConfiguration sourceXmlConfig = projectXmlConfig.configurationAt("sources");
                SourceConfig sourceConfig = parseSource(sourceXmlConfig);
                projectConfig.setSourceConfig(sourceConfig);
            }

             tpnodes = projectXmlConfig.getRootNode().getChildren("resources");
            if(tpnodes.size()>0) {
                SubnodeConfiguration resourceXmlConfig = projectXmlConfig.configurationAt("resources");
                ResourceConfig resourceConfig = parseResource(resourceXmlConfig);
                projectConfig.setResourceConfig(resourceConfig);
            }


            projectConfigs.add(projectConfig);
        }

        return projectConfigs;
    }

    private SourceConfig parseSource(SubnodeConfiguration config)  {
        SourceConfig sourceConfig = new SourceConfig();
        String folder = config.getString("/@folder");
        String basePackage = config.getString("/@basePackage");
        sourceConfig.setFolder(folder);
        sourceConfig.setBasePackage(basePackage);

        List fileXmlConfigs = config.configurationsAt("file");
        for(Object moduleConfig : fileXmlConfigs){
            SubnodeConfiguration fileXmlConfig = (SubnodeConfiguration)moduleConfig;
            FileConfig fileConfig = parseFile(fileXmlConfig);
            sourceConfig.getFileConfigs().add(fileConfig);
        }

        return sourceConfig;
    }

    private ResourceConfig parseResource(SubnodeConfiguration config)  {
        ResourceConfig resourceConfig = new ResourceConfig();
        String folder = config.getString("/@folder");
        String basePackage = config.getString("/@basePackage");
        resourceConfig.setFolder(folder);
        resourceConfig.setBasePackage(basePackage);

        List fileXmlConfigs = config.configurationsAt("file");
        for(Object moduleConfig : fileXmlConfigs){
            SubnodeConfiguration fileXmlConfig = (SubnodeConfiguration)moduleConfig;
            FileConfig fileConfig = parseFile(fileXmlConfig);
            resourceConfig.getFileConfigs().add(fileConfig);
        }

        return resourceConfig;
    }

    private FileConfig parseFile(SubnodeConfiguration config)  {
        FileConfig fileConfig = new FileConfig();
        String subPackage = config.getString("/@subPackage");
        String template = config.getString("/@template");
        fileConfig.setSubPackage(subPackage);
        fileConfig.setTemplate(template);
        return fileConfig;
    }


    private  List<GenerateConfig> parseGenerates(SubnodeConfiguration config)  {

        List<GenerateConfig> generates = new ArrayList<GenerateConfig>();
        List tableXmlConfigs = config.configurationsAt("table");
        for(Object objc : tableXmlConfigs){
            SubnodeConfiguration tableXmlConfig = (SubnodeConfiguration)objc;
            String tableName = tableXmlConfig.getString("/@tableName");
            String className = tableXmlConfig.getString("/@className");

            GenerateConfig  generate = new GenerateConfig();
            generate.setTableName(tableName);
            generate.setClassName(className);
            generates.add(generate);
        }
        return generates;
    }


    private Properties parseProperty(Node node) {
        return this.parseAttributes(node);
    }

    /**
     * 参考mybatis实现，org.mybatis.generator.config.xml.ConfigurationParser
     *
     * @param node
     * @return
     */
    private Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        if (node != null) {
            Element elem = (Element) node;
            for (int i = 0; i < ((Element) node).attributeCount(); ++i) {
                String value = TokenUtils.parsePropertyTokens(this.properties, elem.attribute(i).getText().trim());
                attributes.put(elem.attribute(i).getName(), value);
            }
        }
        return attributes;
    }


    /**
     * 解析Properties
     *
     * @param
     * @throws GenerateException
     */
    private void parseProperties(String location) throws GenerateException {

        if (StringUtils.isBlank(location)) {
            throw new GenerateException("properties 属性配置错误");
        } else {
            try {
                URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(location);
                if (resourceUrl == null) {
                    throw new GenerateException("properties 属性配置错误:获取resource配置文件失败");
                }
                InputStream e = resourceUrl.openConnection().getInputStream();
                this.properties.load(e);
                e.close();
            } catch (IOException e) {
                throw new GenerateException("properties属性配置错误:IO异常=" + e.getMessage());
            }
        }
    }



}
