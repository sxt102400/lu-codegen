package ${@tpl_service.packageName};

<#include "global.ftl">
import java.util.List;

import ${@tpl_entity.packageName}.${className};
import ${page};

/**
* ${className}Service
* Description:  ${table.remark!}
*
* @version : v1.0
* @author : ${author!}
* @since : ${now!}
*/
public interface ${className}Service {

    int count(${className} ${className?uncap_first} );

    int delete(${className}  ${className?uncap_first} );

<#if table.pkCount == 1>
    int deleteByPrimaryKey(<@pkTypeAndField/> );

</#if>
    int save(${className}  ${className?uncap_first} );

    int saveSelective(${className}  ${className?uncap_first} );

    List<${className}> select(${className} ${className?uncap_first} );

    Page<${className}> select(${className} ${className?uncap_first}, Page page);

<#if table.pkCount == 1>
    ${className} selectByPrimaryKey(<@pkTypeAndField/> );

</#if>
    int update(${className} update, ${className} conditon);

    int updateSelective(${className} update, ${className} conditon);

<#if table.pkCount == 1>
    int updateByPrimaryKey(${className} ${className?uncap_first} );

    int updateByPrimaryKeySelective(${className} ${className?uncap_first} );

</#if>
}

