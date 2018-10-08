package ${@tpl_entity.packageName};
<#include "global.ftl">
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;


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