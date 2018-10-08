package ${@tpl_dao.packageName};

<#include "global.ftl">

import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ${example};
import ${@tpl_entity.packageName}.${className};
import ${@tpl_mapper.packageName}.${className}Mapper;

/** ${copyright}
 *
 * ${className}Dao
 * Description: ${table.tableName}表对应dao操作接口实现类
 *
 * @version : v1.0
 * @author : ${author!}
 * @since :${now!}
 */
@Repository
public class ${className}Dao{
    /**
     * Field LOGGER : 日志操作类
     */
    private static final Logger logger = LoggerFactory.getLogger(${className}Dao.class);

    /**
     * Field ${className?uncap_first}Mapper : ${className}表的Mybatis Mapper操作映射类
     */
    @Resource
    private ${className}Mapper ${className?uncap_first}Mapper;

    /**
     * ${className}Dao.countByExample
     * Description: 根据查询条件，计算${table.tableName}个数
     *
     * @param example 通用查询条件类
     * @return int 结果个数
     */
    public int countByExample(Example example) {
        int count = this.${className?uncap_first}Mapper.countByExample(example);
        return count;
    }

    /**
     * ${className}Dao.deleteByExample
     * Description: 根据查询条件，删除${table.tableName}
     *
     * @param example 通用查询条件类
     * @return int  删除个数
     */
    public int deleteByExample(Example example) {
        return this.${className?uncap_first}Mapper.deleteByExample(example);
    }

<#if table.pkCount == 1>
    /**
     * ${className}Dao.deleteByPrimaryKey
     * Description: 根据属性名称，删除${table.tableName}
     *
     * @param <@pkField/> 主键
     * @return int  删除个数
     */
    public int deleteByPrimaryKey(<@pkTypeAndField/>) {
        return this.${className?uncap_first}Mapper.deleteByPrimaryKey(<@pkField/>);
    }

</#if>
    /**
     * ${className}Dao.insert
     * Description: 插入一个${table.tableName}
     *
     * @param record ${table.tableName}的bean对象
     * @return int  插入个数
     */
    public int insert(${className} record) {
        return this.${className?uncap_first}Mapper.insert(record);
    }

    /**
     * ${className}Dao.insertSelective
     * Description: 插入一个只有部分字段的${table.tableName}
     *
     * @param record 只含部分字段的${table.tableName}的bean对象
     * @return int  插入个数
     */
    public int insertSelective(${className} record) {
        return this.${className?uncap_first}Mapper.insertSelective(record);
    }

    /**
     * ${className}Dao.selectAllByExample
     * Description: 根据查询条件类，返回${table.tableName}结果集
     *
     * @param example 通用查询条件类
     * @return List<${className}>${table.tableName}结果集
     */
    public List<${className}> selectByExample(Example example) {
        return this.${className?uncap_first}Mapper.selectByExample(example);
    }

    /**
     * ${className}Dao.selectByExample
     * Description: 根据查询条件类，返回${table.tableName}结果集[分页]
     *
     * @param example 通用查询条件类
     * @param rowBounds mybatis分页参数
     * @return List<${className}>${table.tableName}结果集[分页]
     */
    public List<${className}> selectByExample(Example example, RowBounds rowBounds) {
        return this.${className?uncap_first}Mapper.selectByExample(example, rowBounds);
    }

<#if table.pkCount == 1>
    /**
     * ${className}Dao.selectByPrimaryKey
     * Description: 根据主键类，返回${table.tableName}
     *
     * @param <@pkField/> 主键
     * @return ${className} bean对象
     */
    public ${className} selectByPrimaryKey(<@pkTypeAndField/>) {
        return this.${className?uncap_first}Mapper.selectByPrimaryKey(<@pkField/>);
    }

</#if>

    /**
     * ${className}Dao.updateByExample
     * Description: 根据查询条件更新${table.tableName}全表字段
     *
     * @param record 要更新的${className}对象
     * @param example 更新记录的查询条件
     * @return int 更新记录数
     */
    public int updateByExample(${className} record, Example example) {
        return this.${className?uncap_first}Mapper.updateByExample(record, example);
    }

    /**
     * ${className}Dao.updateByExampleSelective
     * Description: 根据查询条件更新${table.tableName}部分字段
     *
     * @param record 要更新的${className}对象
     * @param example 更新记录的查询条件
     * @return int 更新记录数
     */
    public int updateByExampleSelective(${className} record, Example example) {
        return this.${className?uncap_first}Mapper.updateByExampleSelective(record, example);
    }

<#if table.pkCount == 1>
    /**
     * ${className}Dao.updateByPrimaryKeySelective
     * Description: 根据主键更新${table.tableName}部分字段
     *
     * @param record 要更新成为的${className}对象
     * @return int 更新记录数
     */
    public int updateByPrimaryKeySelective(${className} record) {
        return this.${className?uncap_first}Mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * ${className}Dao.updateByPrimaryKey
     * Description: 根据主键更新${table.tableName}全部字段
     *
     * @param record 要更新成为的${className}对象
     * @return int 更新记录数
     */
    public int updateByPrimaryKey(${className} record) {
        return this.${className?uncap_first}Mapper.updateByPrimaryKey(record);
    }

</#if>
}