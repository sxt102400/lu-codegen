

## lu-generator

### 目的

其实目前mybatis已经有官方的生成器 mybatis-generator，再造一个轮子的目的是为了方便修改，可拓展定制功能。

1. 不同于mybatis，是一个基于模板引擎的生成器，方便自己定制
2. 模板文件订制自由，可以编写任意的模块模板并生成到项目中
3. 参数配置自由，可以根据自己当前项目定制模板和参数



缺点是

1. 功能演进中，许多配置待完善
2. 很多地方需要手动配置，适配性待完善
3. 可能存在很多bug



## 配置说明

**1. 工程导入**

为方便使用，此生成器项目下载后，可将生成器作为当前开发项目的一个maven子模块使用。



**2. 配置文件**

配置文件位于 src/main/resource/generatorConfig.xml

配置文件示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <properties resource="jdbc.properties"/>
    
	<properties>
		<!-- 工程和模板配置 -->
		<property name="projectName" value="icy-web"></property>
		<property name="projectDir" value="e://myproject"></property>
        <property name="templateDir" value="template/my"></property>
		<property name="override" value="true"></property>
    </properties>
	
    <!-- jdbc配置 -->
	<jdbcConnection driverClassName="${jdbc.driverClassName}"
					url="${jdbc.url}"
					username="${jdbc.username}"
					password="${jdbc.password}">
	</jdbcConnection>
	
    <!-- 模板文件配置 -->
	<templates >
		<template name="mapper" packageName="com.sxt.mapper" fileName="${className}mapper.java"     />
		<template name="entity" packageName="com.sxt.entity" fileName="${className}.java"     />
		<template name="xmlMapper" packageName="com.sxt.mapper" fileName="${className}_Mapper.xml" type="xml"  />
	</templates>
	
  	<!-- 模块配置 -->
    <modules>
		<module name="myIn"
			source ="src/main/java"
			resource ="src/main/resource"
			templates="entity,mapper,xmlMapper"
		/>
	</modules>
	
    <!-- 配置需要生成代码的表，tableName：表名; className：类名 -->
    <tables>
        <table  tableName="t_sys_user" className="SysUser" ></table>
        <table tableName="icy_role" className="Role" ></table>
        <table tableName="icy_funcright" className="Funcright" ></table>
        <table tableName="icy_menu" className="Menu" ></table>-->
        <table tableName="icy_user" className="User" ></table>
        <table tableName="icy_depart" className="Depart" ></table>
    </tables>



  </configuration>
```



**3. Java调用生成器**

直接执行ShellRunner类。或者编写自己的调用类。

```java
 public static void main(String[] args) {
        String configFile= "generator.xml";
        List<String> warnings = new ArrayList<String>();
        InputStream in  = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
        ConfigurationParser cp = new ConfigurationParser();
        try {
            Configuration configuration = cp.parse(in);
            LuGenarator genarator = new LuGenarator(configuration,warnings);
            genarator.generate();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```



### 配置文件说明

**properties**

1) properties可以从文件读取配置，设置属性 url，source。例如

`<properties resource="jdbc.properties"/>`

2) properties 可以直接在配置文件中设置变量

```xml
<properties>
	<property name="projectName" value="icy-web"></property>
<properties>
```

通用变量设置如下

- projectDir ：工程目录路径。生成文件以此路径为根路径。

- templateDir：模板目录路径。默认从生成器的resource目录开始查找。

- override： 生成模板时，如果存在文件是否覆盖

- templateEngine:  模板引擎配置，目前默认freemarker。其他引擎待完善。



**jdbcConnection**

配置数据库连接参数。无需多说

```xml
<jdbcConnection driverClassName="${jdbc.driverClassName}"
	url="${jdbc.url}"
	username="${jdbc.username}"
	password="${jdbc.password}">
</jdbcConnection>
```



**templates**
模板配置，数量不固定，可以在此处定义自己的模板。
默认模板使用freemarker引擎编写。

通用参数如下:

- name： 模板名称
- packageName：生成类的包名称
- fileName：模板文件名称
- type：模板类型，例如 type=xml ,会生成文件到 resource目录
    > 模板文件名可以使用参数变量，生成时会根据当前的context，同时转换模板文件名
    >
    > 例如：${className}Mapper.java



**modules**

模块配置。每个模块对应maven工程的一个子模块。

通用参数如下:

- name： 模块名称
- source：source目录路径, 若无配置默认src/main/java
- resource：resource目录路径, 若无配置默认src/main/resource
- templates：此模块关联的模板。
    >  只有在此声明的模板才会在当前模块中生成。模板可以包含多个，使用`,`分隔
    >
    >  例如：templates="controller,service,serviceImpl,dao,mapper,entity,xmlMapper"



**tables**

数据库表配置。

生成模板时，首先需要手动在数据库中创建表。生成器会根据 table 标签中配置的“表名和类名”生成模板。

通用参数如下:

- tableName:  表名

- className： 类名

- catalog： catalog

- schema： schema



### Context

Context是指在模板中可以直接使用的变量



**1. properties Context**

在properties标签中定义的变量。

使用方式，使用prop@前缀

``` 
${prop@<attr>}   
<name>为properties的key，例如  ${prop@override} 
```

**2. template Context**

在templates标签中定义的模板变量。

使用方式，使用tpl@前缀

```
${tpl@<name>.<attr>}   
<name>为template标签的name，attr为module标签的属性key，例如  ${tpl@mapper.fileName}  
```

**3. modules Context**

在modules 标签中定义的模板变量。

使用方式，使用mod@前缀

```
${mod@<name>.<attr>}   
<name>为module标签的name，attr为module标签的属性key，例如  ${mod@hello.source}  
```

**4. table Context**

生成的表的定义。

使用方式，无需前缀

通用定义如下：

- tableName ：  表名 ，例如 ： ${tableName}   
- className：  类名  ，例如 ： ${className} 


table 描述

| table       | 说明 | 类型  | 示例  |
| --------- | ---- | ---- | ---- |
| table |  表信息    | IntrospectedTable  | ${table}   |
| table.columns |  列信息    | List[Column] | ${ table.columns}   |
| table.pkColumns |  主键列表    | List[Column] | ${ table.pkColumns}   |
| table.notPkColumns |  非主键列列表    | List[Column] | ${ table.notPkColumns}   |



Column属性：

| context       | 说明 | 类型  | 示例  |
| --------- | ---- | ---- | ---- |
| columnName |      | String  |   |
| fieldName |    | String  | |
| defaultValue |      | String |    |
| remark |      | String |    |
| columnSize |      | int |    |
| decimalDigits |      |  |   |
| sqlType |      | String |   |
| javaType |      | String |   |
| sqlTypeName |      | String |   |
| javaTypeName |      | String |   |
| primaryKey |      | boolean |   |
| nullable |      | boolean |   |
| autoIncrement |      | boolean |   |
| foreignkey |      | boolean |   |




例如，生成bean中的所有field字段

```
<#list table.columns as column>
    /**
    * Field ${column.fieldName} : ${column.remark!}
    */
    @column(" ${column.columnName}")
    private ${column.javaType} ${column.fieldName};

</#list>
```





### 下一版本计划

**lu-generator代码生成器**

版本：1.0

作者:   寒冰

1. 添加web页面，可以通过页面配置生成
2. 添加maven插件配置
