package com.sxt.generate.config;

import com.sxt.generate.Exception.GenerateException;
import com.sxt.generate.util.StringUtil;
import com.sxt.generate.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
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
public class ConfigurationParser {


    private Properties properties = new Properties();

    public Configuration parse(String fileName) {
        Configuration configuration = new Configuration();
        Document root = XmlUtil.readXml(fileName);
        try {
            Node properties = XmlUtil.readNode(root, "/configuration/properties");
            if (properties != null) {
                parseProperties(properties);
            }



            Node templates = XmlUtil.readNode(root, "/configuration/template");
            if (templates != null) {
                configuration.setTemplateConfiguration( parseTemplates(templates));
            }

            Node project = XmlUtil.readNode(root, "/configuration/project");
            if (project != null) {
                configuration.setProjectConfiguration(parseProject(project));;
            }

            Node jdbc = XmlUtil.readNode(root, "/configuration/jdbc");
            if (jdbc != null) {
                configuration.setJdbcConfiguration(parseJdbc(jdbc));;
            }

            Node sources = XmlUtil.readNode(root, "/configuration/sources");
            if (sources != null) {
                configuration.setSourceConfiguration(parseSources(sources));
            }

            Node resources = XmlUtil.readNode(root, "/configuration/resources");
            if (resources != null) {
                configuration.setResourceConfiguration(parseResources(resources));
            }

            Node generates = XmlUtil.readNode(root, "/configuration/generates");
            if (resources != null) {
                configuration.setGenerateConfigurations(parseGenerates(generates));;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuration;

    }


    /**
     * 解析Properties
     *
     * @param node
     * @throws GenerateException
     */
    private void parseProperties(Node node) throws GenerateException {
        Properties attributes = this.parseAttributes(node);
        String resource = attributes.getProperty("resource");
        String url = attributes.getProperty("url");
        if (StringUtils.isNoneEmpty(resource) && StringUtils.isNoneEmpty(url)) {
            throw new GenerateException("properties 属性配置错误");
        } else {
            try {
                URL resourceUrl;
                if (StringUtils.isNotBlank(resource)) {
                    resourceUrl = Thread.currentThread().getContextClassLoader().getResource(resource);
                    if (resourceUrl == null) {
                        throw new GenerateException("properties 属性配置错误:获取resource配置文件失败");
                    }
                } else {
                    resourceUrl = new URL(url);
                }

                InputStream e = resourceUrl.openConnection().getInputStream();
                this.properties.load(e);
                e.close();
            } catch (IOException e) {
                throw new GenerateException("properties属性配置错误:IO异常=" + e.getMessage());
            }
        }
    }

    /**
     * 解析Template
     *
     * @param node
     * @return TemplateConfiguration
     */
    private TemplateConfiguration parseTemplates(Node node)  {
        TemplateConfiguration templateConfiguration = new TemplateConfiguration();
        List<Node> nodes = node.selectNodes("property");
        for (Node elem : nodes) {
            Properties attributes = parseProperty(elem);
            if ("generate.module".equals(attributes.getProperty("name"))) {
                templateConfiguration.setGenerate_module(attributes.getProperty("value"));
            } else if ("template.dir".equals(attributes.getProperty("name"))) {
                templateConfiguration.setTemplate_dir(attributes.getProperty("value"));
            } else if ("override".equals(attributes.getProperty("name"))) {
                templateConfiguration.setOverride(attributes.getProperty("value"));
            }

        }
        return templateConfiguration;
    }

    private ProjectConfiguration parseProject(Node node)  {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration();
        List<Node> nodes = node.selectNodes("property");
        for (Node elem : nodes) {
            Properties attributes = parseProperty(elem);
            if ("project.name".equals(attributes.getProperty("name"))) {
                projectConfiguration.setProjectName(attributes.getProperty("value"));
            }
        }
        return projectConfiguration;
    }


    /**
     * 解析jdbc
     *
     * @param node
     * @return JdbcConfiguration
     */
    private JdbcConfiguration parseJdbc(Node node)  {
        JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();
        List<Node> nodes = node.selectNodes("property");
        for (Node elem : nodes) {
            Properties attributes = parseProperty(elem);
            if ("driverClassName".equals(attributes.getProperty("name"))) {
                jdbcConfiguration.setDriverClassName(attributes.getProperty("value"));
            } else if ("url".equals(attributes.getProperty("name"))) {
                jdbcConfiguration.setUrl(attributes.getProperty("value"));
            } else if ("username".equals(attributes.getProperty("name"))) {
                jdbcConfiguration.setUsername(attributes.getProperty("value"));
            } else if ("password".equals(attributes.getProperty("name"))) {
                jdbcConfiguration.setPassword(attributes.getProperty("value"));
            }

        }
        return jdbcConfiguration;
    }

    /**
     * 解析source
     *
     * @param node
     * @return
     */
    private SourceConfiguration parseSources(Node node)  {
        SourceConfiguration sourceConfiguration = new SourceConfiguration();
        EntityConfiguration entityConfiguration = null;
        MapperConfiguration mapperConfiguration = null;
        DaoConfiguration daoConfiguration = null;
        ServiceConfiguration serviceConfiguration = null;
        WebConfiguration webConfiguration = null;

        String basePackage = ((Element) node).attribute("basePackage").getValue();
        if (StringUtils.isNotBlank(basePackage)) {
            sourceConfiguration.setBasePackage(basePackage);
        }

        String folder = ((Element) node).attribute("folder").getValue();
        if (StringUtils.isNotBlank(basePackage)) {
            sourceConfiguration.setFolder(folder);
        }

        Node entity = node.selectSingleNode("model");
        if (entity != null) {
            Properties attributes = this.parseAttributes(entity);
            entityConfiguration = new EntityConfiguration();
            entityConfiguration.setSubPackage(attributes.getProperty("subPackage"));
            entityConfiguration.setModule(attributes.getProperty("module"));

        }

        Node mapper = node.selectSingleNode("mapper");
        if (mapper != null) {
            Properties attributes = this.parseAttributes(mapper);
            mapperConfiguration = new MapperConfiguration();
            mapperConfiguration.setSubPackage(attributes.getProperty("subPackage"));
            mapperConfiguration.setModule(attributes.getProperty("module"));
        }

        Node dao = node.selectSingleNode("dao");
        if (dao != null) {
            Properties attributes = this.parseAttributes(dao);
            daoConfiguration =new DaoConfiguration();
            daoConfiguration.setSubPackage(attributes.getProperty("subPackage"));
            daoConfiguration.setModule(attributes.getProperty("module"));
        }

        Node service = node.selectSingleNode("service");
        if (service != null) {
            Properties attributes = this.parseAttributes(service);
            serviceConfiguration = new ServiceConfiguration();
            serviceConfiguration.setSubPackage(attributes.getProperty("subPackage"));
            serviceConfiguration.setModule(attributes.getProperty("module"));
        }

        Node web = node.selectSingleNode("web");
        if (web != null) {
            Properties attributes = this.parseAttributes(web);
            webConfiguration = new WebConfiguration();
            webConfiguration.setSubPackage(attributes.getProperty("subPackage"));
            webConfiguration.setModule(attributes.getProperty("module"));

        }
        sourceConfiguration.setEntityConfiguration(entityConfiguration);
        sourceConfiguration.setMapperConfiguration(mapperConfiguration);
        sourceConfiguration.setDaoConfiguration(daoConfiguration);
        sourceConfiguration.setServiceConfiguration(serviceConfiguration);
        sourceConfiguration.setWebConfiguration(webConfiguration);
        return sourceConfiguration;
    }

    /**
     * 解析sourcere
     *
     * @param node
     * @return ResourceConfiguration
     */
    private ResourceConfiguration parseResources(Node node) {
        ResourceConfiguration resourceConfiguration = new ResourceConfiguration();
        MapperConfiguration mapperConfiguration = new MapperConfiguration();
        resourceConfiguration.setMapperConfiguration(mapperConfiguration);

        String basePackage = ((Element) node).attribute("basePackage").getValue();
        if (StringUtils.isNotBlank(basePackage)) {
            resourceConfiguration.setBasePackage(basePackage);
        }

        String folder = ((Element) node).attribute("folder").getValue();
        if (StringUtils.isNotBlank(basePackage)) {
            resourceConfiguration.setFolder(folder);
        }
        Node mapper = node.selectSingleNode("mapper");
        if (mapper != null) {
            Properties attributes = this.parseAttributes(mapper);
            mapperConfiguration.setSubPackage(attributes.getProperty("subPackage"));
            mapperConfiguration.setModule(attributes.getProperty("module"));
        }
        return resourceConfiguration;
    }

    private  List<GenerateConfiguration> parseGenerates(Node node) throws GenerateException {

        List<GenerateConfiguration> generates = new ArrayList<GenerateConfiguration>();
        List<Node> nodes = node.selectNodes("table");
        for (Node elem : nodes) {
            GenerateConfiguration generateConfiguration = new GenerateConfiguration();
            Properties attributes = parseProperty(elem);

            assert StringUtils.isNotBlank(attributes.getProperty("className")) : "generator table className can't be null";
            generateConfiguration.setTableName(attributes.getProperty("tableName"));
            if(StringUtils.isNotBlank(attributes.getProperty("className"))) {
                generateConfiguration.setClassName(attributes.getProperty("className"));
            }else{
                String className = StringUtils.capitalize(StringUtil.toCamel(attributes.getProperty("tableName")))  ;
                generateConfiguration.setClassName(className);
            }

            generates.add(generateConfiguration);
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
                String value = this.parsePropertyTokens(elem.attribute(i).getText().trim());
                attributes.put(elem.attribute(i).getName(), value);
            }
        }
        return attributes;
    }

    /**
     * 参考mybatis实现，org.mybatis.generator.config.xml.ConfigurationParser
     *
     * @param string
     * @return
     */
    private String parsePropertyTokens(String string) {
        String OPEN = "${";
        String CLOSE = "}";
        String newString = string;
        if (string != null) {
            int start = string.indexOf("${");

            for (int end = string.indexOf("}"); start > -1 && end > start; end = newString.indexOf("}", end)) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + "}".length());
                String propName = newString.substring(start + "${".length(), end);
                String propValue = this.properties.getProperty(propName);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }
                start = newString.indexOf("${", end);
            }
        }
        return newString;
    }

}
