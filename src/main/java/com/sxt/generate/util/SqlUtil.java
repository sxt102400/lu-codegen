package com.sxt.generate.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName : sql操作类
 * Description : sql操作类
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class SqlUtil {



    /**
     * sql类型转为java的类型名称（不包含包前缀）
     */
    public static String convertToJavaTypeName(int sqlType, int size) {
        String key = convertToJavaType(sqlType, size);
        Map<String, String> javaTypeMap = new HashMap<String, String>();
        javaTypeMap.put("Byte", "byte");
        javaTypeMap.put("Short", "short");
        javaTypeMap.put("Integer", "int");
        javaTypeMap.put("Float", "float");
        javaTypeMap.put("Double", "double");
        javaTypeMap.put("Long", "long");
        javaTypeMap.put("Boolean", "boolean");
        javaTypeMap.put("String", "String");
        javaTypeMap.put("byte[]", "byte[]");
        javaTypeMap.put("java.sql.Date", "java.util.Date");
        javaTypeMap.put("java.sql.Time", "java.util.Date");
        javaTypeMap.put("java.sql.Timestamp", "java.util.Date");
        javaTypeMap.put("java.sql.Clob", "Object");
        javaTypeMap.put("java.sql.Blob", "byte[]");
        javaTypeMap.put("java.sql.Array", "Object[]");
        javaTypeMap.put("java.sql.Ref", "Object");
        javaTypeMap.put("Object", "Object");
        javaTypeMap.put("void", "void");
        if (javaTypeMap.containsKey(key)) {
            return javaTypeMap.get(javaTypeMap);
        } else {
            return "Object";
        }
    }

    public static String convertToSqlType(int sqlType, int size) {

        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC)) {
            switch (size) {
                case 1:
                    return "TINYINT";
                case 3:
                    return "TINYINT";
                case 5:
                    return "SMALLINT";
                case 10:
                    return "INTEGER";
                case 19:
                    return "BIGINT";
                default:
                    return "BIGINT";
            }
        } else {
            Map<Integer, String> javaTypeForSqlType = new HashMap<Integer, String>();
            javaTypeForSqlType.put(Types.TINYINT, "TINYINT");
            javaTypeForSqlType.put(Types.SMALLINT, "SMALLINT");
            javaTypeForSqlType.put(Types.INTEGER, "INTEGER");
            javaTypeForSqlType.put(Types.BIGINT, "BIGINT");
            javaTypeForSqlType.put(Types.REAL, "REAL");
            javaTypeForSqlType.put(Types.FLOAT, "FLOAT");
            javaTypeForSqlType.put(Types.DOUBLE, "DOUBLE");
            javaTypeForSqlType.put(Types.DECIMAL, "DECIMAL");
            javaTypeForSqlType.put(Types.NUMERIC, "NUMERIC");
            javaTypeForSqlType.put(Types.BIT, "BIT");
            javaTypeForSqlType.put(Types.BOOLEAN, "BOOLEAN");
            javaTypeForSqlType.put(Types.CHAR, "CHAR");
            javaTypeForSqlType.put(Types.VARCHAR, "VARCHAR");
            javaTypeForSqlType.put(Types.LONGVARCHAR, "LONGVARCHAR");
            javaTypeForSqlType.put(Types.BINARY, "BINARY");
            javaTypeForSqlType.put(Types.VARBINARY, "VARBINARY");
            javaTypeForSqlType.put(Types.LONGVARBINARY, "LONGVARBINARY");
            javaTypeForSqlType.put(Types.DATE, "DATE");
            javaTypeForSqlType.put(Types.TIME, "TIME");
            javaTypeForSqlType.put(Types.TIMESTAMP, "TIMESTAMP");
            javaTypeForSqlType.put(Types.CLOB, "CLOB");
            javaTypeForSqlType.put(Types.BLOB, "BLOB");
            javaTypeForSqlType.put(Types.ARRAY, "ARRAY");
            javaTypeForSqlType.put(Types.REF, "REF");
            javaTypeForSqlType.put(Types.STRUCT, "STRUCT");
            javaTypeForSqlType.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
            javaTypeForSqlType.put(Types.NULL, "NULL");

            String result = javaTypeForSqlType.get(sqlType);
            if (result == null) {
                return "JAVA_OBJECT";
            } else {
                return result;
            }
        }
    }

    /**
     * sql类型转为java类型（不包含包前缀）
     */
    public static String convertToJavaType(int sqlType, int size) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC)) {
            switch (size) {
                case 1:
                    return "Short";
                case 3:
                    return "Short";
                case 5:
                    return "Short";
                case 10:
                    return "Integer";
                case 19:
                    return "Long";
                default:
                    return "Long";
            }
        } else {
            Map<Integer, String> javaTypeForSqlType = new HashMap<Integer, String>();
            javaTypeForSqlType.put(Types.TINYINT, "Short");
            javaTypeForSqlType.put(Types.SMALLINT, "Short");
            javaTypeForSqlType.put(Types.INTEGER, "Integer");
            javaTypeForSqlType.put(Types.BIGINT, "Long");
            javaTypeForSqlType.put(Types.REAL, "Float");
            javaTypeForSqlType.put(Types.FLOAT, "Double");
            javaTypeForSqlType.put(Types.DOUBLE, "Double");
            javaTypeForSqlType.put(Types.DECIMAL, "Long");
            javaTypeForSqlType.put(Types.NUMERIC, "Long");
            javaTypeForSqlType.put(Types.BIT, "Boolean");
            javaTypeForSqlType.put(Types.BOOLEAN, "Boolean");
            javaTypeForSqlType.put(Types.CHAR, "String");
            javaTypeForSqlType.put(Types.VARCHAR, "String");
            javaTypeForSqlType.put(Types.LONGVARCHAR, "String");
            javaTypeForSqlType.put(Types.BINARY, "byte[]");
            javaTypeForSqlType.put(Types.VARBINARY, "byte[]");
            javaTypeForSqlType.put(Types.LONGVARBINARY, "byte[]");
            javaTypeForSqlType.put(Types.DATE, "java.util.Date");
            javaTypeForSqlType.put(Types.TIME, "java.util.Date");
            javaTypeForSqlType.put(Types.TIMESTAMP, "java.util.Date");
            javaTypeForSqlType.put(Types.CLOB, "Object");
            javaTypeForSqlType.put(Types.BLOB, "byte[]");
            javaTypeForSqlType.put(Types.ARRAY, "Object[]");
            javaTypeForSqlType.put(Types.REF, "Object");
            javaTypeForSqlType.put(Types.STRUCT, "Object");
            javaTypeForSqlType.put(Types.JAVA_OBJECT, "Object");
            javaTypeForSqlType.put(Types.NULL, "void");

            String result = javaTypeForSqlType.get(sqlType);
            if (result == null) {
                return "Object";
            } else {
                return result;
            }
        }
    }

}
