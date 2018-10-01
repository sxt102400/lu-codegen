package com.rats.lu.generator.table;


import com.rats.lu.generator.config.TableConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class IntrospectedTable  extends TableConfiguration {

    protected String remark;
    protected String tableType;
    protected String tableNameAlias;
    protected int pkCount;
    protected String fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
