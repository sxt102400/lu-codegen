package ${@tpl_serviceImpl.packageName};

<#include "global.ftl">

import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ${@tpl_entity.packageName}.${className};
import ${@tpl_mapper.packageName}.${className}Mapper;
import ${@tpl_service.packageName}.${className}Service;
import ${page};
import ${example};

/** ${copyright}
 *
 * ${className}ServiceImpl
 * Description: ${table.remark!}
 *
 * @version : v1.0
 * @author : ${author!}
 * @since : ${now!}
 */
@Service(value = "${className?uncap_first}Service")
public class ${className}ServiceImpl implements ${className}Service {

    /**
     * Field LOGGER : 日志操作类
     */
    private static final Logger logger = LoggerFactory.getLogger(${className}Service.class);

    /**
     * Field ${className?uncap_first}Mapper : ${className?uncap_first}Mapper
     */
    @Resource
    private ${className}Mapper ${className?uncap_first}Mapper;

    /**
     * ${className?uncap_first}Service.count
     * Description: 根据查询条件，计算${className}个数
     *
     * @param condition ${className}参数
     * @return int 结果个数
     */
    @Override
    public int count(${className} condition ) {
        Example example = buildExample();
        return this.${className?uncap_first}Mapper.countByExample(example);
    }

    /**
     * ${className?uncap_first}Service.delete
     * Description: 根据查询条件，删除${className?uncap_first}
     *
     * @param condition ${className}参数，查询条件
     * @return void
     */
    @Override
    public int delete(${className} condition ) {
        Example example = buildExample();
        return this.${className?uncap_first}Mapper.deleteByExample(example);
    }

<#if table.pkCount == 1>
    /**
     * ${className}Service.deleteByPrimaryKey
     * Description: 修改${className}
     *
     * @param <@pkField/> 主键
     * @return void
     */
    @Override
    public int deleteByPrimaryKey(<@pkTypeAndField/>) {
        return this.${className?uncap_first}Mapper.deleteByPrimaryKey(<@pkField/>);
    }

</#if>
    /**
     * ${className}Service.save
     * Description: 保存${className},
     *
     * @param ${className?uncap_first} ${className}参数
     * @return void
     */
    @Override
    public int save(${className} ${className?uncap_first} ) {
        return  this.${className?uncap_first}Mapper.insert(${className?uncap_first} );
    }

    /**
     * ${className}Service.saveSelective
     * Description: 保存${className}, 只插入${className}的非空字段
     *
     * @param ${className?uncap_first} ${className}参数
     * @return void
     */
    public int saveSelective(${className} ${className?uncap_first} ) {
        return this.${className?uncap_first}Mapper.insertSelective( ${className?uncap_first} );
    }

    /**
     * ${className}Service.select
     * Description: 根据查询条件类，返回${className}结果集
     *
     * @param condition ${className}参数，查询条件
     * @return List<${className}> ${className}结果集 [不分页]
     */
    @Override
    public List<${className}> select(${className} condition ) {
        Example example = buildExample();
        return this.${className?uncap_first}Mapper.selectByExample(example);
    }

    /**
     * ${className}Service.select
     * Description: 根据查询条件类，返回${className}结果集 [分页]
     *
     * @param condition ${className}参数，查询条件
     * @param page Page参数，分页条件
     * @return Page<${className}> ${className}结果集
     */
    @Override
    public Page<${className}> select(${className} condition, Page page) {
        Example example = buildExample();
        page.setTotalCount(${className?uncap_first}Mapper.countByExample(example));
        RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getMaxResults());
        page.setList(${className?uncap_first}Mapper.selectByExample(example, rowBounds));
        return page;
    }

<#if table.pkCount == 1>
    /**
     * ${className}Service.selectByPrimaryKey
     * Description: 根据主键，返回${className}
     *
     * @param <@pkField/> 主键
     * @return ${className}参数
     */
    @Override
    public ${className} selectByPrimaryKey(<@pkTypeAndField/> ) {
        return ${className?uncap_first}Mapper.selectByPrimaryKey(<@pkField/> );
    }

</#if>

    /**
     * ${className}Service.update
     * Description: 修改${className}, 根据查询条件修改
     *
     * @param update    ${className}参数需改的值
     * @param condition ${className}查询条件
     * @return void

     */
    @Override
    public int update(${className} update, ${className}  condition ) {
        Example example = buildExample();
        return this.${className?uncap_first}Mapper.updateByExample(update, example);
    }

    /**
     * ${className}Service.updateSelective
     * Description: 修改${className}, 根据查询条件修改
     *
     * @param update    ${className}参数需改的值
     * @param condition ${className}查询条件
     * @return void
     */
    @Override
    public int updateSelective(${className} update, ${className}  condition ) {
        Example example = buildExample();
        return this.${className?uncap_first}Mapper.updateByExampleSelective(update, example);
    }

<#if table.pkCount == 1>
    /**
     * ${className}Service.updateByPrimaryKey
     * Description: 修改${className}, 根据主键修改
     *
     * @param ${className?uncap_first} ${className}参数
     * @return void
     */
    @Override
    public int updateByPrimaryKey(${className} ${className?uncap_first} ) {
        return this.${className?uncap_first}Mapper.updateByPrimaryKey(${className?uncap_first} );
    }

    /**
     * ${className}Service.updateByPrimaryKeySelective
     * Description: 修改${className}, 根据主键修改，只修改非空参数字段
     *
     * @param ${className?uncap_first} ${className}参数
     * @return void
     */
    @Override
    public int updateByPrimaryKeySelective(${className} ${className?uncap_first} ) {
        return this.${className?uncap_first}Mapper.updateByPrimaryKeySelective(${className?uncap_first} );
    }

</#if>

    public Example buildExample(){
        Example example = new Example();
        Example.Criteria criteria = example.createCriteria();
<#list table.columns as column>
        if (condition.get${column.fieldName?cap_first}() != null) {
            criteria.andEqualTo( "{column.columnName}",condition.get${column.fieldName?cap_first}() );
        }
</#list>
        return example;
    }
}



