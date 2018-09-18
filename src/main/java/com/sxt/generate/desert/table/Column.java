package com.sxt.generate.desert.table;

import com.sxt.generate.desert.util.SqlUtil;
import com.sxt.generate.desert.util.TokenUtils;

/**
 * ClassName : Column
 * Description : 表的列描述
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class Column {
    private String columnName;
    private String fieldName;
    private String defaultValue;
    private String remark;

    private int columnSize;
    private int decimalDigits;
    private int sqlType;

    private boolean indexInfo;
    private boolean indexUnique;
    private boolean nullable;
    private boolean primaryKey;
    private boolean foreignkey;
    private boolean autoIncrement;

    private String javaType;
    private String sqlTypeName;
    private String javaTypeName;


    public void initialize() {
        this.fieldName = TokenUtils.toCamel(columnName);
        this.javaType = SqlUtil.convertToJavaType(getSqlType(), getColumnSize());
        this.javaTypeName = SqlUtil.convertToJavaTypeName(getSqlType(), getColumnSize());
        this.sqlTypeName = SqlUtil.convertToSqlType(getSqlType(), getColumnSize());
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

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
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

    public String getSqlTypeName() {
        return sqlTypeName;
    }

    public void setSqlTypeName(String sqlTypeName) {
        this.sqlTypeName = sqlTypeName;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }
}
