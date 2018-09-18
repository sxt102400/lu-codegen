package com.sxt.generate.util;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName : StringUtil
 * Description : StringUtil类
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class StringUtil {

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
