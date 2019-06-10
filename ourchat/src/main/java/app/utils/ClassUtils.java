/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author dark
 */
public class ClassUtils {

    //<editor-fold defaultstate="collapsed" desc="Boolean is methods">
    /**
     *
     * @param clazz
     * @return true if clazz is wrapper class
     */
    public static Boolean isWrapper(Class clazz) {
        return clazz == Double.class || clazz == Float.class || clazz == Long.class
                || clazz == Integer.class || clazz == Short.class || clazz == Character.class
                || clazz == Byte.class || clazz == Boolean.class;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is primitive
     */
    public static Boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive();
    }

    /**
     *
     * @param clazz
     * @return true if clazz is String class
     */
    public static Boolean isString(Class clazz) {
        return String.class == clazz;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is String, primitive or wrapper class, false
     * otherwise
     */
    public static Boolean isPrimitiveOrWrapperOrString(Class clazz) {
        return isPrimitive(clazz) || isWrapper(clazz) || isString(clazz);
    }

    /**
     *
     * @param clazz
     * @return true if clazz is java.util.Date
     * @see Date
     */
    public static Boolean isDate(Class clazz) {
        return clazz == Date.class;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is custom object
     */
    public static Boolean isCustomObject(Class clazz) {
        return String.class == clazz;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is java.util.List class
     * @see List
     */
    public static Boolean isList(Class clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    /**
     *
     * @param clazz
     * @return true if clazz is java.util.Map class
     * @see Map
     */
    public static Boolean isMap(Class clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Fields and methods">
    /**
     *
     * @param clazz
     * @return HashSet of all class methods
     */
    public static HashSet<Method> classAllMethods(Class clazz) {
        HashSet<Method> methodSet = new HashSet<>();
        Class tmpClazz = clazz;
        while (!tmpClazz.equals(Object.class)) {
            methodSet.addAll(Arrays.asList(tmpClazz.getDeclaredMethods()));
            tmpClazz = tmpClazz.getSuperclass();
        }

        return methodSet;
    }

    /**
     *
     * @param classMethods
     * @return
     */
    public static HashMap<String, Method> methodMap(HashSet<Method> classMethods) {
        HashMap<String, Method> classMethodsMap = new HashMap<>();

        for (Method classMethod : classMethods) {
            if (!classMethodsMap.containsKey(stripGet(classMethod.getName()))) {
                classMethodsMap.put(stripGet(classMethod.getName()), classMethod);
            }
        }
        return classMethodsMap;
    }

    /**
     *
     * @param clazz
     * @return HashSet of all public fields
     */
    public static HashSet<Field> classAllPrivateFields(Class clazz) {
        HashSet<Field> methodSet = new HashSet<>();
        Class tmpClazz = clazz;
        while (!tmpClazz.equals(Object.class)) {
            methodSet.addAll(Arrays.asList(tmpClazz.getDeclaredFields()));
            tmpClazz = tmpClazz.getSuperclass();
        }
        return methodSet;
    }

    /**
     *
     * @param classFields
     * @return HashMap: keys as field names, values as fields
     */
    public static HashMap<String, Field> fieldMap(HashSet<Field> classFields) {
        HashMap<String, Field> classFieldsMap = new HashMap<>();

        for (Field classField : classFields) {
            if (!classFieldsMap.containsKey(classField.getName())) {
                classFieldsMap.put(classField.getName(), classField);
            }
        }
        return classFieldsMap;
    }
    //</editor-fold>

    private static String stripGet(String methodName) {
        if (methodName.startsWith("get")) {
            methodName = methodName.replaceFirst("get", "");
            methodName = methodName.toLowerCase().charAt(0) + methodName.substring(1);
        }
        return methodName;
    }

    /**
     *
     * @param clazz
     * @return List of all class get methods
     */
    public static HashSet<Method> classGetMethods(Class clazz) {
        HashSet<Method> methodList = new HashSet<>();

        if (clazz.equals(Object.class)) {
            return methodList;
        }
        methodList.addAll(classGetMethods(clazz.getSuperclass()));

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("get")) {
                methodList.add(declaredMethod);
            }
        }

        return methodList;
    }

    /**
     *
     * @param classMethods
     * @return
     */
    public static LinkedHashMap<String, Method> methodLHMap(Set<Method> classMethods) {
        LinkedHashMap<String, Method> classMethodsMap = new LinkedHashMap<>();
        for (Method classMethod : classMethods) { // faster is imperative than functional
            if (!classMethodsMap.containsKey(stripGet(classMethod.getName()))) {
                classMethodsMap.put(stripGet(classMethod.getName()), classMethod);
            }
        }
        return classMethodsMap;
    }

    /**
     *
     * @param clazz
     * @return List of all fields
     */
    public static HashSet<Field> classFields(Class clazz) {
        HashSet<Field> fieldList = new HashSet<>();

        if (clazz.equals(Object.class)) {
            return fieldList;
        }
        fieldList.addAll(classFields(clazz.getSuperclass()));
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));

        return fieldList;
    }

    /**
     *
     * @param classFields
     * @return HashMap: keys as field names, values as fields
     */
    public static LinkedHashMap<String, Field> fieldLHMap(Set<Field> classFields) {
        LinkedHashMap<String, Field> classFieldsMap = new LinkedHashMap<>();

        for (Field classField : classFields) {
            if (!classFieldsMap.containsKey(classField.getName())) {
                classFieldsMap.put(classField.getName(), classField);
            }
        }
        return classFieldsMap;
    }

}
