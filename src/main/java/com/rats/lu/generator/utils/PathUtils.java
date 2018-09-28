package com.rats.lu.generator.utils;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Properties;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class PathUtils {

    public static boolean isAbsolutePath(String path) {
        if (path.startsWith("/")) return true;
        if (isWinOS()) {// windows
            if (path.contains(":") || path.startsWith("\\")) {
                return true;
            }
        } else {// not windows, just unix compatible
            if (path.startsWith("~")) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWinOS() {
        boolean isWinOS = false;
        String osName = System.getProperty("os.name").toLowerCase();
        String sharpOsName = osName.replaceAll("windows", "{windows}")
                .replaceAll("^win([^a-z])", "{windows}$1")
                .replaceAll("([^a-z])win([^a-z])", "$1{windows}$2");
        if (sharpOsName != null) {
            isWinOS = sharpOsName.contains("{windows}");
        }

        return isWinOS;
    }

    public static String getPath(String... paths) {
        return StringUtils.join(paths, "/");
    }

    public static String getPathOfPackage(String packageName) {
        return  RegExUtils.replaceAll(packageName,"\\.", "/");
    }

    public static String resolveFileName(String templateName, Map<String, Object> context){
        return parsePropertyTokens(templateName,context);
    }


    private static String parsePropertyTokens(String string,Map<String, Object> context) {
        String OPEN = "${";
        String CLOSE = "}";
        String newString = string;
        if (string != null) {
            int start = string.indexOf("${");
            for (int end = string.indexOf("}"); start > -1 && end > start; end = newString.indexOf("}", end)) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + "}".length());
                String propName = newString.substring(start + "${".length(), end);
                String propValue = resolveProperty(propName,context);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }

                start = newString.indexOf("${", end);
            }
        }
        return newString;
    }

    private static String resolveProperty(String key,Map<String, Object> context) {
        String property = null;
        property = System.getProperty(key);
        if (property == null && context.get(key)!= null) {
            property = context.get(key).toString();
        }
        return property;
    }
}
