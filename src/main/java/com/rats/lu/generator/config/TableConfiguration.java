package com.rats.lu.generator.config;

import com.rats.lu.generator.table.ColumnOverride;

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
public class TableConfiguration extends PropertyHolder {

    protected String tableName;

    protected String className;

    protected String subPackageName;

    protected String catalog;

    protected String schema;

    protected List<ColumnOverride> columnOverrides;

    public TableConfiguration() {
        this.columnOverrides = new ArrayList();
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

    public String getSubPackageName() {
        return subPackageName;
    }

    public void setSubPackageName(String subPackageName) {
        this.subPackageName = subPackageName;
    }

    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    public void addColumnOverride(ColumnOverride columnOverride) {
        this.columnOverrides.add(columnOverride);
    }

    public ColumnOverride getColumnOverride(String columnName) {
        for (ColumnOverride columnOverride : columnOverrides) {
            if (columnName != null && columnName.equalsIgnoreCase(columnOverride.getColumnName())) {
                return columnOverride;
            }
        }
        return null;
    }


}
