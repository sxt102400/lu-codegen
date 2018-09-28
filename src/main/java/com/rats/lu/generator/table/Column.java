package com.rats.lu.generator.table;



import com.rats.lu.generator.utils.StringTools;
import com.rats.lu.generator.utils.TypeResolver;


/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */

public class Column {
    private String columnName;
    private String fieldName;
    private String defaultValue;
    private String remark;

    private int columnSize;
    private int decimalDigits;

    private int pkCount;

    private boolean indexInfo;
    private boolean indexUnique;
    private boolean nullable;
    private boolean primaryKey;
    private boolean foreignkey;
    private boolean autoIncrement;

    private String javaType;
    private String javaTypeName;
    private int jdbcType;
    private String jdbcTypeName;



    public void initialize() {
        this.fieldName = StringTools.toCamel(columnName);
        this.javaType = TypeResolver.convertToJavaType(getJdbcType(), getColumnSize());
        this.javaTypeName = TypeResolver.convertToJavaTypeName(getJdbcType(), getColumnSize());
        this.jdbcTypeName = TypeResolver.convertToSqlType(getJdbcType(), getColumnSize());

    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isIndexInfo() {
        return indexInfo;
    }

    public void setIndexInfo(boolean indexInfo) {
        this.indexInfo = indexInfo;
    }

    public boolean isIndexUnique() {
        return indexUnique;
    }

    public void setIndexUnique(boolean indexUnique) {
        this.indexUnique = indexUnique;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isForeignkey() {
        return foreignkey;
    }

    public void setForeignkey(boolean foreignkey) {
        this.foreignkey = foreignkey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public int getPkCount() {
        return pkCount;
    }

    public void setPkCount(int pkCount) {
        this.pkCount = pkCount;
    }

    public String getJdbcTypeName() {
        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }
}
