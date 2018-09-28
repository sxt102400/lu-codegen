package com.rats.lu.generator.utils;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class StringTools {

    /**
     *  underline to camel
     * @param text
     * @return
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
     *  camel to  underline
     * @param text
     * @return
     */
    public static String toUnderLine(String text) {

        StringBuilder textarr = new StringBuilder(text);
        int temp=0;//定位
        for(int i=0;i<textarr.length();i++){
            if(Character.isUpperCase(textarr.charAt(i))){
                textarr.insert(i+temp, "_");
                temp+=1;
            }
        }
        return textarr.toString().toLowerCase();
    }


}
