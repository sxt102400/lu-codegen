package ${@tpl_mapper.packageName};

<#include "global.ftl">

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import ${example};
import ${@tpl_entity.packageName}.${className};

/** ${copyright}
 *
 * ${className}Mapper
 * Description: ${table.remark!}表对应的操作Mapper映射类
 *
 * @version : v1.0
 * @author : ${author!}
 * @since : ${now!}
 */
public interface ${className}Mapper {

    /**
     * Description: 根据查询条件，获取${table.tableName}记录总数
     *
     * @param example 查询条件
     * @return long 结果个数
     */
    long countByExample(Example example);

    /**
     * Description: 根据查询条件，删除${table.tableName}中记录
     *
     * @param example 通用查询条件类
     * @return int 删除个数
     */
    int deleteByExample(Example example);

<#if table.pkCount == 1>
    /**
     * Description: 根据主键删除${table.tableName}中记录
     *
     * @param <@pkField/> 主键
     * @return int  删除个数
     */
    int deleteByPrimaryKey(<@pkTypeAndField/>);

</#if>
    /**
     * Description: 插入一个记录到${table.tableName}
     *
     * @param record 插入的bean对象
     * @return int  插入个数
     */
    int insert(${className} record);

    /**
     * Description: 插入一个记录到${table.tableName}：忽略null字段
     *
     * @param record 插入的bean对象
     * @return int  插入个数
     */
    int insertSelective(${className} record);

    /**
     * Description: 根据查询条件，返回${table.tableName}结果集
     *
     * @param example 查询条件
     * @return List<${className}>${table.tableName}结果集
     */
    List<${className}> selectByExample(Example example);

<#if table.pkCount == 1>
    /**
     * ${className}Mapper.selectByPrimaryKey
     * Description: 根据主键类，返回${table.tableName}
     *
     * @param <@pkField/> 主键
     * @return ${className} ${table.tableName}对象
     */
    ${className} selectByPrimaryKey(<@pkTypeAndField/>);

</#if>
    /**
     * ${className}Mapper.updateByExampleSelective
     * Description: 根据查询条件更新${table.tableName}表记录的部分字段
     *
     * @param record 要更新成为的${className}对象
     * @param example 更新记录的查询条件
     * @return int 更新记录数
     */
    int updateByExampleSelective(@Param("record") ${className} record, @Param("example") Example example);

    /**
     * ${className}Mapper.updateByExample
     * Description: 根据查询条件更新${table.tableName}表记录全部字段
     *
     * @param record 要更新成为的${className}对象
     * @param example 更新记录的查询条件
     * @return int 更新记录数
     */
    int updateByExample(@Param("record") ${className} record, @Param("example") Example example);

    /**
     * ${className}Mapper.updateByPrimaryKeySelective
     * Description: 根据主键更新${table.tableName}部分字段
     *
     * @param record 要更新成为的${className}对象
     * @return int 更新记录数
     */
    int updateByPrimaryKeySelective(${className} record);

<#if table.pkCount == 1>
    /**
     * ${className}Mapper.updateByPrimaryKey
     * Description: 根据主键更新${table.tableName}全部字段
     *
     * @param record 要更新成为的${className}对象
     * @return int 更新记录数
     */
    int updateByPrimaryKey(${className} record);

    /**
     * Description: 根据查询条件，返回${table.tableName}结果集，[分页查询]
     *
     * @param example 查询条件
     * @param rowBounds mybatis分页条件
     * @return List<${className}>${table.tableName}结果集
     */
    List<${className}> selectByExample(Example example,RowBounds rowBounds);

</#if>
}