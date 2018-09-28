package com.rats.lu.generator.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class ObjectFactory {

    private static List<ClassLoader> externalClassLoaders = new ArrayList();

    private ObjectFactory() {
    }

    public static void reset() {
        externalClassLoaders.clear();
    }

    public static synchronized void addExternalClassLoader(ClassLoader classLoader) {
        externalClassLoaders.add(classLoader);
    }

    public static Class<?> externalClassForName(String type) throws ClassNotFoundException {
        Iterator var2 = externalClassLoaders.iterator();

        while (var2.hasNext()) {
            ClassLoader classLoader = (ClassLoader) var2.next();

            try {
                Class<?> clazz = Class.forName(type, true, classLoader);
                return clazz;
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return internalClassForName(type);
    }

    public static Object createExternalObject(String type) {
        try {
            Class<?> clazz = externalClassForName(type);
            Object answer = clazz.newInstance();
            return answer;
        } catch (Exception var3) {
            throw new RuntimeException("createExternalObject error", var3);
        }
    }

    public static Class<?> internalClassForName(String type) throws ClassNotFoundException {
        Class clazz = null;

        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(type, true, cl);
        } catch (Exception var3) {
            ;
        }

        if (clazz == null) {
            clazz = Class.forName(type, true, ObjectFactory.class.getClassLoader());
        }

        return clazz;
    }

    public static URL getResource(String resource) {
        Iterator var2 = externalClassLoaders.iterator();

        URL url;
        do {
            if (!var2.hasNext()) {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                url = cl.getResource(resource);
                if (url == null) {
                    url = ObjectFactory.class.getClassLoader().getResource(resource);
                }

                return url;
            }

            ClassLoader classLoader = (ClassLoader) var2.next();
            url = classLoader.getResource(resource);
        } while (url == null);

        return url;
    }


    public static Object resolveParams(Object beanObj, Map<String, Object> context) {
        Class<?> classType = beanObj.getClass();
        Object newInstance = null;
        try {
            newInstance = classType.newInstance();
            Field[] fields = beanObj.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                String type = field.getType().getTypeName();
                String firstLetter = name.substring(0, 1).toUpperCase();
                String getMethodName;
                String setMethodName;
                if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
                    getMethodName = "is" + firstLetter + name.substring(1);
                    setMethodName = "set" + firstLetter + name.substring(1);
                } else {
                    getMethodName = "get" + firstLetter + name.substring(1);
                    setMethodName = "set" + firstLetter + name.substring(1);
                }
                Method getMethod = classType.getMethod(getMethodName, new Class[]{});
                Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});

                if ("java.lang.String".equals(type)) {
                    Object value = getMethod.invoke(beanObj, new Object[]{});
                    String valueStr = (String) value;
                    valueStr = parsePropertyTokens(valueStr, context);
                    setMethod.invoke(newInstance, new Object[]{valueStr});
                } else {
                    Object value = getMethod.invoke(beanObj, new Object[]{});
                    setMethod.invoke(newInstance, new Object[]{value});
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newInstance;

    }


    private static String parsePropertyTokens(String string, Map<String, Object> context) {
        String OPEN = "${";
        String CLOSE = "}";
        String newString = string;
        if (string != null) {
            int start = string.indexOf("${");
            for (int end = string.indexOf("}"); start > -1 && end > start; end = newString.indexOf("}", end)) {
                String prepend = newString.substring(0, start);
                String append = newString.substring(end + "}".length());
                String propName = newString.substring(start + "${".length(), end);
                String propValue = resolveProperty(propName, context);
                if (propValue != null) {
                    newString = prepend + propValue + append;
                }

                start = newString.indexOf("${", end);
            }
        }
        return newString;
    }

    private static String resolveProperty(String key, Map<String, Object> context) {
        String property = null;
        property = System.getProperty(key);
        if (property == null && context.get(key) != null) {
            property = context.get(key).toString();
        }
        return property;
    }

}