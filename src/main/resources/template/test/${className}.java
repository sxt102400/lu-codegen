package ${tpl@entity.packageName};
<#include "global.ftl">
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** ${copyright}
 *
 * ${className}
 * Description: ${table.remark!}
 *
 * @version : v1.0
 * @author : ${author!}
 * @since : ${now!}
 */
public class ${className} implements Serializable {
    /**
     * Field serialVersionUID
     */
    private static final long serialVersionUID = 1L;

<#-- 生成属性 -->
<@generateColumns/>
<#-- 构造方法 -->
<@generateConstructor/>
<#-- 生成get,set方法 -->
<@generateJavaSetterGetter/>

    /*
     * (非 Javadoc) <p>Title: ${className}.equals</p>
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /*
     * (非 Javadoc) <p>Title: ${className}.hashCode</p>
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /*
     * (非 Javadoc) <p>Title: ${className}.toString</p>
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}

<#-- entity函数定义=================== -->
<#-- Filed字段 -->
<#macro generateColumns>
<#list table.columns as column>
    /**
     * Field ${column.fieldName} : ${column.remark!}
     */
    @Column("${column.columnName}")
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
</#macro>