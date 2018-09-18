package com.sxt.generate.desert.table;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName : Table
 * Description : 表描述
 *
 * @author : hanbing
 * @version : v1.1
 * @since : 2015/5/29
 */
public class Table {

    /**
     * 类名称
     */
    public String className;
    public Template clazz;

    /**
     * catalog
     */
    private String catalog;

    /**
     * schema
     */
    private String schema;

    /**
     * 表名
     */
    public String tableName;

    /**
     * 表描述
     */
    public String remark;
    public String tableNameAlias;

    /**
     * 是否存在唯一主键
     */
    boolean hasUniquePk;
    /**
     * 表中所有列
     */
    public List<Column> columns = new ArrayList<Column>();
    /**
     * 主键列表
     */
    public List<Column> pkColumns = new ArrayList<Column>();

    /**
     * 主非键列表
     */
    public List<Column> notPkColumns = new ArrayList<Column>();


    /**
     * 拥有主键。//拥有唯一主键
     *
     * @return
     */
    public boolean isHasUniquePk() {
        return  pkColumns.size() == 1;
    }

    public void setHasUniquePk(boolean hasUniquePk) {
        this.hasUniquePk = hasUniquePk;
    }

    public Template getClazz() {
        return clazz;
    }

    public void setClazz(Template clazz) {
        this.clazz = clazz;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTableNameAlias() {
        return tableNameAlias;
    }

    public void setTableNameAlias(String tableNameAlias) {
        this.tableNameAlias = tableNameAlias;
    }

    public List<Column> getPkColumns() {
        return pkColumns;
    }

    public void setPkColumns(List<Column> pkColumns) {
        this.pkColumns = pkColumns;
    }

    public List<Column> getNotPkColumns() {
        return notPkColumns;
    }

    public void setNotPkColumns(List<Column> notPkColumns) {
        this.notPkColumns = notPkColumns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
