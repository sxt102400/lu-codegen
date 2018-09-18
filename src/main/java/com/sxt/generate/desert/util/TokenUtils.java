package com.sxt.generate.desert.util;

import java.util.Map;

/**
 * Created by shangxiutao on 17/2/22.
 */
public class TokenUtils {

    /**
     * 参考mybatis实现，org.mybatis.generator.config.xml.ConfigurationParser
     *
     * @param string
     * @return
     */
    public static  String parsePropertyTokens(Map map,String string) {
        String OPEN = "${";
        String CLOSE = "}";
        String newString = string;
        if (string != null) {
            int start = string.indexOf("${");

            for (int end = string.indexOf("}"); start > -1 && end > start; end = newString.indexOf("}", end)) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + "}".length());
                String propName = newString.substring(start + "${".length(), end);
                String propValue = (String)map.get(propName);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }
                start = newString.indexOf("${", end);
            }
        }
        return newString;
    }

    /*
 * _单词转为驼峰单词
 */
    public static String toCamel(String text) {

        StringBuilder textarr = new StringBuilder(text.toLowerCase());
        for (int i = 0; i < textarr.length() - 1; i++) {
            if (textarr.charAt(i) == '_') {
                int j = i + 1;
                if (j < textarr.length() && textarr.charAt(j) >= 'a' && textarr.charAt(j) <= 'z') {
                    int charInt = (int) textarr.charAt(j) - 32;
                    textarr.setCharAt(j, (char) charInt);
                }

            }
        }
        return textarr.toString().replaceAll("_", "");
    }

    /**
     * 获取数据库类型描述
     *
     * @param driver
     * @return
     */

    public static String getDatabaseType(String driver) {
        if(driver!=null &&! "".equals(driver) ) {
            if(driver.toLowerCase().contains("mysql")) {
                return "mysql";
            }else if(driver.toLowerCase().contains("oracle")){
                return "oracle";
            }
        }
        return "";
    }
}
