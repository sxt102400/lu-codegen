package ${entityPackage};

<#include "func.ftl">

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

<#-- 生成列名 -->
<@generateColumnNames/>
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
