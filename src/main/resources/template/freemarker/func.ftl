<#-- 变量定义=================== -->
<#assign copyright="Copyright (C) 2013 aisainfo">
<#-- <#assign author="Lu generator"> -->

<#assign example= "com.asiainfo.framework.common.persistence.Example" >
<#assign page= "com.asiainfo.framework.common.persistence.page.Page" >

<#-- entity函数定义=================== -->
<#-- Filed列名 -->
<#macro generateColumnNames>
    <#list table.columns as column>

    public static final String COLUMN_${column.columnName?upper_case} = "${column.columnName}";

    </#list>
</#macro>


<#-- Filed字段 -->
<#macro generateColumns>
<#list table.columns as column>
    /**
    * Field ${column.fieldName} : ${column.remark!}
    */
    private ${column.javaType} ${column.fieldName};

</#list>
</#macro>
<#-- Constructor方法 -->
<#macro generateConstructor>
    public ${className}(){}

</#macro>
<#-- getter和setter方法 -->
<#macro generateJavaSetterGetter>
<#list table.columns as column>
    public void set${column.fieldName?cap_first}( ${column.javaType} ${column.fieldName} ) {
        this.${column.fieldName} = ${column.fieldName};
    }

    public ${column.javaType} get${column.fieldName?cap_first}() {
        return this.${column.fieldName};
    }

</#list>
<#-- service函数定义=================== -->
</#macro>
<#-- 主键类型 -->
<#macro pkType><#list table.pkColumns as column>${column.javaType}<#if column_has_next>,</#if></#list></#macro>
<#-- 主键字段 -->
<#macro pkField><#list table.pkColumns as column>${column.fieldName}<#if column_has_next>,</#if></#list></#macro>
<#-- 主键类型和字段 -->
<#macro pkTypeAndField><#list table.pkColumns as column>${column.javaType} ${column.fieldName}<#if column_has_next>,</#if></#list> </#macro>
<#-- 设置example方法 -->
<#macro generateExample>
<#list table.columns as column>
        if (condition.get${column.fieldName?cap_first}() != null) {
            criteria.andEqualTo( ${className}.COLUMN_${column.columnName?upper_case},condition.get${column.fieldName?cap_first}() );
        }
</#list>
</#macro>
<#-- XML函数=================== -->
<#-- resultMapList -->
<#macro resultMapList>
<#list table.pkColumns as column>
        <id column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.sqlTypeName}" />
</#list>
<#list table.notPkColumns as column>
        <result column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.sqlTypeName}" />
</#list>
</#macro>
<#-- columnList -->
<#macro columnList>
            <#list table.columns as column> ${column.columnName} <#if column_has_next>,</#if> </#list>
</#macro>
<#-- columnParamList -->
<#macro columnParamList>
            <#list table.columns as column> ${"#"}{${column.fieldName}, jdbcType=${column.sqlTypeName}} <#if column_has_next>,</#if> </#list>
</#macro>
<#-- pkCondition -->
<#macro pkCondition>
<#list table.pkColumns as column> ${column.columnName} = ${"#"}{${column.fieldName} ,jdbcType=${column.sqlTypeName}} <#if column_has_next> and </#if> </#list>
</#macro>
<#-- testColumnList -->
<#macro testColumnList>
<#list table.columns as column>
        <if test="${column.fieldName} != null">
            ${column.columnName},
        </if>
</#list>
</#macro>
<#-- testColumnParamList -->
<#macro testColumnParameList>
<#list table.columns as column>
        <if test="${column.fieldName} != null">
            ${"#"}{${column.fieldName}, jdbcType=${column.sqlTypeName}},
        </if>
</#list>
</#macro>
<#-- updateColumnList -->
<#macro updateColumnList>
<#list table.columns as column>
            ${column.columnName} = ${"#"}{${column.fieldName}, jdbcType=${column.sqlTypeName}} <#if column_has_next>,</#if>
</#list>
</#macro>
<#-- updateColumnListWithMap -->
<#macro updateColumnListWithMap>
<#list table.columns as column>
            ${column.columnName} = ${"#"}{record.${column.fieldName}, jdbcType=${column.sqlTypeName}} <#if column_has_next>,</#if>
</#list>
</#macro>
<#-- testUpdateColumnList -->
<#macro testUpdateColumnListWithMap>
<#list table.columns as column>
        <if test="record.${column.fieldName} != null">
            ${column.columnName} = ${"#"}{record.${column.fieldName}, jdbcType=${column.sqlTypeName}} <#if column_has_next>,</#if>
        </if>
</#list>
</#macro>
<#-- updateColumnSelectiveList -->
<#macro testUpdateColumnList>
<#list table.notPkColumns as column>
        <if test="${column.fieldName} != null">
            ${column.columnName} =${"#"}{${column.fieldName}, jdbcType=${column.sqlTypeName}} <#if column_has_next>,</#if>
        </if>
</#list>
</#macro>