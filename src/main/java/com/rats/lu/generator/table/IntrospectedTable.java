package com.rats.lu.generator.table;


import java.util.ArrayList;
import java.util.List;

/**
 * ClassName : IntrospectedTable
 * Description : 表描述
 *
 * @author : hanbing
 * @version : v1.1
 * @since : 2015/5/29
 */
public class IntrospectedTable {
    protected String tableName;
    protected String className;
    protected String catalog;
    protected String schema;

    protected String remark;
    protected String tableType;
    protected String tableNameAlias;
    protected int pkCount;
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

    public List<Column> blobColumns = new ArrayList<Column>();

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableNameAlias() {
        return tableNameAlias;
    }

    public void setTableNameAlias(String tableNameAlias) {
        this.tableNameAlias = tableNameAlias;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
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

    public List<Column> getBlobColumns() {
        return blobColumns;
    }

    public void setBlobColumns(List<Column> blobColumns) {
        this.blobColumns = blobColumns;
    }

    public int getPkCount() {
        return pkCount;
    }

    public void setPkCount(int pkCount) {
        this.pkCount = pkCount;
    }
}
