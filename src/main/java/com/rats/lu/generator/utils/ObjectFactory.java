package com.rats.lu.generator.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


}