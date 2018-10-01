

# lu-generator

## 目的

其实目前mybatis已经有官方的生成器 mybatis-generator，再造一个轮子的目的是为了方便自己拓展定制功能。

1. 不同于mybatis，是一个基于模板引擎的生成器，可根据不同的项目单独定制
2. 生成不限于数据库层，从view层到ben层都可以定制自己的生成文件
3. 模板文件订制自由，可以编写任意的模块模板并生成到项目中
4. 参数配置自由，可以根据自己当前项目定制模板和参数



缺点是

1. 功能演进中，许多配置待完善
2. 很多地方需要手动配置，适配性待完善
3. 不谦虚的说，可能存在许多bug



## 配置说明

**1. 工程导入**

为方便使用，此生成器项目下载后，可将生成器工程配置为当前工程的子模块。作为一个maven子模块使用。



**2. 配置文件**

配置文件位于 src/main/resource/generatorConfig.xml

配置文件示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//rats.org//DTD Lu Generator Configuration 1.0//EN"
        "http://rats.org/dtd/lu-generator-config_1_0.dtd">
<configuration>

    <properties resource="jdbc.properties"/>

    <properties>

        <!-- 工程和模板配置 -->
        <property name="projectName" value="myproject"></property>
        <property name="projectDir" value="../lu-generator"></property>
        <property name="templateDir" value="template/test"></property>
        <property name="override" value="true"></property>
    </properties>

    <!-- jdbc配置 -->
    <jdbcConnection driverClassName="${jdbc.driverClassName}"
                    url="${jdbc.url}"
                    username="${jdbc.username}"
                    password="${jdbc.password}">
    </jdbcConnection>


    <!-- 模板文件配置，此处按需自己定制编写 -->
    <templates>
        <template name="controller" packageName="com.sxt.controller" fileName="${className}Controller.java"/>
        <template name="service" packageName="com.sxt.service" fileName="${className}Service.java"/>
        <template name="serviceImpl" packageName="com.sxt.service.impl" fileName="${className}ServiceImpl.java"/>
        <template name="dao" packageName="com.sxt.dao" fileName="${className}Dao.java"/>
        <template name="mapper" packageName="gen.mapper" fileName="${className}mapper.java"/>
        <template name="entity" packageName="gen.entity" fileName="${className}.java"/>
        <template name="xmlMapper" packageName="gen.xml" fileName="${className}Mapper.xml" type="xml"/>
    </templates>

    <!-- 模块配置 -->
    <modules>
        <module name="lu"
                moduleDir=""
                sources="src/main/java"
                resources="src/main/resources"
                templates="entity,mapper,dao,service,serviceImpl,controller,xmlMapper"
        />
    </modules>


    <!-- 配置需要生成代码的表，tableName：表名; className：类名 ,subPackageName: 子包名称-->
    <tables>
        <table tableName="t_sys_user" className="SysUser">
        </table>
        <table tableName="t_sys_user_compk" className="SysUserCompk"></table>
        <table tableName="test" className="Test">
            <!-- all -->
            <columnOverride column="testName"
                            field="username"
                            javaType="java.lang.Long"
                            jdbcType="VARCHAR"
                            ignore="true"/>
            
            <!-- Override，覆盖的字段 -->
            <columnOverride column="id" field="id"/> 
			<columnOverride column="type" field="type"/>
			<columnOverride column="username" field="username"/>
            <columnOverride column="age" field="age"/>
            
            <!-- ignore，忽略的字段 -->
			<columnOverride column="password"  ignore="true"/>   
            <columnOverride column="status" ignore="true"/> 
          
        </table>
    </tables>

</configuration>
```



**3. Java调用生成器**

直接执行AppRunner类。主要调用内容如下。

```java
public static void main(String[] args) {

        try {
            String configFile= "generator.xml";
            InputStream in  = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            ConfigurationParser cp = new ConfigurationParser();
            Configuration configuration = cp.parse(in);
            LuGenarator generator = new LuGenarator(configuration);
            generator.generate();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
```



## 配置文件说明

**properties**

1) properties可以从文件读取配置，设置属性 url，source。例如

`<properties resource="jdbc.properties"/>`

2) properties 可以直接在配置文件中设置变量

```xml
<properties>
        <!-- 工程和模板配置 -->
        <property name="projectName" value="myproject"></property>
        <property name="projectDir" value="../lu-generator"></property>
        <property name="templateDir" value="template/test"></property>
        <property name="override" value="true"></property>
 </properties>
```

通用变量设置如下

- projectName ：工程名称。没有什么用处。

- projectDir ：工程目录路径。生成文件以此路径为根路径。

- templateDir：模板目录路径。默认从生成器的resources目录开始定位模板文件。

- override： 生成模板时，如果存在文件是否覆盖




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
- type：模板类型，例如 `type=xml` ,会生成文件到 `src/main/resources`目录
    > 模板文件名可以使用参数变量，生成时会根据当前的context，同时转换模板文件名
    >
    > 例如：${className}Mapper.java



**modules**

模块配置。每个模块对应maven工程的一个子模块。

通用参数如下:

- name： 模块名称

- moduleDir：模块目录。如果不配置则默认为父工程目录。

- sources：sources目录路径, 若无配置默认src/main/java

- resources：resources目录路径, 若无配置默认src/main/resource

- templates：此模块关联的模板，可以有多个值。
    >  只有在此声明的模板才会在当前模块中生成。模板可以包含多个，使用`,`分隔
    >
    >  例如：`templates="controller,service,serviceImpl,dao,mapper,entity,xmlMapper"`



**tables**

数据库表配置。

生成模板时，首先需要手动在数据库中创建表。生成器会根据 table 标签中配置的“表名和类名”生成模板。

通用参数如下:

- tableName:  表名

- className： 类名

- catalog： catalog

- schema： schema

- subPackageName：子包名。
> 实际生成文件的包目录由[ template.packageName + table.subPackageName ] 最终合并获得


**columnOverride**

数据库字段覆写配置
- column：数据库中字段名（建议下划线分割单词）
- field： 对应Java Bean的属性名，可不做设置，默认根据字段自动生成
- javaType： java的默认类型，可不做设置，自动识别
- jdbcType： jdbc的字段类型，可不做设置，自动识别
- ignore：是否忽略字段，默认false，如果设置为true则表示忽略，将不会在xml和Java Bean中生成此column内容




## 模板Context变量

Context是指在模板中可以直接使用的变量


**1. properties Context**

在properties标签中定义的变量。

使用方式，使用@prop_前缀

``` 
${ @prop_<attr> }   
<attr>为properties的name属性，例如 : ${ @prop_override} 
```

**2. template Context**

在templates标签中定义的模板变量。

使用方式，使用@tpl_前缀

```
${ @tpl_<name>.<attr>}   
<name>为template标签的name名称，attr为template标签的属性，例如: ${  @tpl_mapper.fileName}  
```

**3. modules Context**

在modules 标签中定义的模板变量。

使用方式，使用@mod_前缀

```
$  @mode_<name>.<attr>}   
<name>为module标签的name，attr为module标签的属性，例如: ${ @mode_my-module.sources}  
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
| table.pkColumns |  主键字段列表  | List[Column] | ${ table.pkColumns}   |
| table.notPkColumns |  非主键字段列表  | List[Column] | ${ table.notPkColumns}   |



Column属性：

| context       | 说明 | 类型  | 示例  |
| --------- | ---- | ---- | ---- |
| columnName | 表中字段名称 | String  | "username" |
| fieldName | bean中属性名称 | String  | "username" |
| defaultValue | 默认值 | String | "guest" |
| remark | 备注 | String | "用户名" |
| columnSize | 大小 | int | 20 |
| decimalDigits |      |  |   |
| sqlType | sql类型 | int | 11 |
| javaType | java类型 | String | String |
| jdbcType | jdbc类型 | String | VARCHAR |
| fullJavaType | java类型（全名称） | String | java.lang.String |
| primaryKey | 是否主键 | boolean | false |
| nullable | 是否可空 | boolean | false |
| autoIncrement | 是否自增 | boolean | false |
| foreignkey | 是否外键 | boolean | false |





## 模板文件编写



### 1. 模板和生成文件的位置

如果当前模块名称为:modName，模板名称为：tplName，表名称为<tableName>

模板文件的实际路径为：

>  ```
>  lu-generator的resources目录  +  @prop_templateDir + @tpl_<tplName>.fileName
>  ```

生成文件的路径为：

>  ```
>  @prop_projectDir + @mod_<modName>.moduleDir + @tpl_<tplName>.packageName + table.subPackageName +  @tpl_<tplName>.fileName
> ```



### 2. Freemark模板编写

Freemark编写，就是普通的Freemark模板了，其中可以使用的模板context变量，见上节：模板Context变量



例如，生成bean中的所有field字段，模板内容如下：

```
<#list table.columns as column>
    /**
    * Field ${column.fieldName} : ${column.remark!}
    */
    @column(" ${column.columnName}")
    private ${column.javaType} ${column.fieldName};

</#list>
```







## 下一版本计划

**lu-generator代码生成器**

版本：1.0

作者:   寒冰

1. 添加web页面，可以通过页面配置生成
2. 添加maven插件配置
