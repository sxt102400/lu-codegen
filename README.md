```xml
  <!-- 约定优于配置 -->
  
  <configuration>

    <properties resource="jdbc.properties"/>
	<properties resource="jdbc.properties2"/>
	
	<properties	 >
        <!-- 工程和模板配置 -->
     
        <property  name="projectName" value="icy-web"></property >		
        <property  name="projectDir" value="e://myproject"></property > //../
        <property  name="templateDir" value="src/main/resources/template/"> </property >  //"src/main/resources/lu/template/
        <property  name="exportMode;" value="in"></property > //in [out,zipout]
        <property  name="exportDir" value="e:/out"></property > //home 
        <property  name="override" value="true"></property >	//false 
      </properties>
      
      
        <!-- 数据库连接配置 -->
        <jdbcConnection driverClassName="${jdbc.driverClassName}" 
                        url="${jdbc.url}" 
                        username="${jdbc.username}" 
                        password="${jdbc.password}">
        </jdbcConnection>

	
	<templates>	//package="" file=="{{name}}"    />
		<template 
                  name="controller" 
                  package="com.sxt.controller” 
                  file="${className}Controller.java"     />
		<template name="service" package="com.sxt.service file="${className}Service.java"     />
		<template name="serviceImpl" package="com.sxt.service.impl file="${className}ServiceImpl.java"     />
		<template name="dao" package="com.sxt.dao" file="${className}dao.java"     />
		<template name="mapper" package="com.sxt.mapper" file="${className}mapper.java"     />		
		<template name="entity" package="com.sxt.entity" file="${className}.java"     />
     <template name="custom" package="com.sxt.custom" file="${className}Custom.java"     />                                                                            
	</templates>	

    <modules>
		<!-- 如果isSub为false，或者moduleName为空，表示不是子模块WW -->
		<module isSub="false" 				//false
			moduleName="myIn" 
			sourceDir="src/main/java" 			//src/main/java
			resourceDir="src/main/resource" 	//src/main/resource
			templates="controller,service,serviceImpl,dao,mapper,entity"  //*
		/>
	<modules>

    <tables>
			<!-- 如果不配置moduleName,默认使用modules的第一个module配置 -->
			<!-- 配置需要生成代码的类，tableName：表名; className：类名 -->
            <table tableName="icy_user" className="User" ></table>
            <table tableName="icy_role" className="Role" ></table>
            <table tableName="icy_funcright" className="Funcright" ></table>
            <table tableName="icy_menu" className="Menu" ></table>-->
            <table tableName="icy_user" className="User" ></table>
            <table tableName="icy_depart" className="Depart" ></table>

    </tables>
                                                            
 
</configuration>                                                           
```



所有



当前table下

tableConfig                 [key=propName , value= propValue]



propertiesConfig    [key=prop@propName,value= propVelue ]

jdbcConnectionConfig	 [key=jdbc@PropName,value= jdbcPropValue]

templateConfig		 [key=tpl@{tplName}.key,value= value]

moduleConfig		 [key=mod@{tplName}.key,value= value]





config			

 [key=prop@allKey , value= allValue]

 [key=jdbc@allKey , value= allValue]

 [key=tpl@allKey , value= allValue]

 [key=mod@allKey , value= allValue]



## 模板变量


tableName

className

entityName

**table**

table.tableName   	表名

table.catalog;

table.schema;

table.comment

table.remark;

table.hasPk

table.columns

table.pkColumns

table.notPkColumns

table.className  	类名，首字母大写

table.entityName 	bean名，首字母小写



**package**

controller.package

service.package

mapper.package

dao.package

entity.package






column 属性

- columnName
- fieldName
- columnName?upper_case
- remark
- decimalDigits;
- javaType
- sqlType
- javaTypeName
- sqlTypeName
- defaultValue
- comment 
- remark
- columnSize
- isIndex
- isUnique
- isNullable
- isPk
- siFk
- autoIncrement



other

- date  时间
- author 作者

## 模板配置


** properties**



**template**

name

file

package

type = 1:source ,2:  rescource 

默认
template name=controller file="${table}.java"   package=“com.sxt”
template name=service 
template name=ServiceImpl
template name=mapper
template name=dao
template name=entity


自定义template： 
template name= MyTemplate  type=resource package=“com.mytempalte”



template自动生成参数：

module参数

service.package

mappe.rpackage

dao.package

entity.package

MyTemplate.package


自定义参数：

MyTemplate.package




**common**

genType 	生成方式: 1生成到工程， 2生成下载文件 .3 生成到页面

override

projectName

projectDir

templateDir

jdbc.driverClassName

jdbc.url

jdbc.username

jdbc.password

自定义属性

**module**

isSubModule

moduleName

sourceDir

resourceDir

templates = [ 
ServiceImpl.package , 
controllerTemplate  
serviceTemplate , 
ServiceImplTemplate ,
mapperTemplate,
myTemplate,

]



**table**  

moduleName

tableName

className



