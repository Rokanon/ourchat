/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dark
 */
public class TransformUtils {

    /**
     * Prints object fields with values
     *
     * @param object - to print values from
     * @return string representation of object
     */
    protected static final String toString(Object object) {
        if (null == object) {
            return "Object: is null";
        }
        return toString(object, object.getClass(), 0);
    }

    /**
     * Prints object fields with values
     *
     * @param object - to print values from
     * @param clazz - object class
     * @param indentFactor - indentation of values depending on depth
     * @return
     */
    private static String toString(Object object, Class clazz, int indentFactor) {

        HashMap<String, Field> classFieldsMap = fieldMap(classAllPrivateFields(clazz));
        HashMap<String, Method> classMethodsMap = methodMap(classAllMethods(clazz));

        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < indentFactor; i++) {
            stringBuilder.append(" ");
        }

        stringBuilder.append(clazz.getSimpleName()).append(": ").append(clazz.cast(object).hashCode()).append("\n");

        for (Map.Entry<String, Field> entry : classFieldsMap.entrySet()) {

            for (int i = 0; i < indentFactor; i++) {
                stringBuilder.append(" ");
            }

            String fieldName = entry.getKey();
            Field field = entry.getValue();

            field.setAccessible(Boolean.TRUE);
            try {
                String fieldValue;
                try {
                    fieldValue = classMethodsMap.get(fieldName).invoke(clazz.cast(object)).toString();
                } catch (NullPointerException exception) {
                    stringBuilder.append(fieldName).append(" (").append(field.getType().getSimpleName()).append("): ").append(" null").append("\n");
                    continue;
                }

                stringBuilder.append(fieldName);

                if (isPrimitiveOrWrapperOrString(field.getType())) { // primitive, wrapper or String
                    stringAppend(stringBuilder, field.getType().getSimpleName(), fieldValue);
                    stringBuilder.append("\n");
                } else if (isDate(field.getType())) { // java.util.Date class
                    stringAppend(stringBuilder, field.getType().getSimpleName(), fieldValue);
                    stringBuilder.append("\n");
                } else if (isList(field.getType())) { // java.util.List
                    for (Object listElement : (ArrayList) field.get(object)) {
                        stringAppend(stringBuilder, listElement.getClass().getSimpleName(), toString(listElement, listElement.getClass(), indentFactor + 2));
                    }
                } else if (isMap(field.getType())) { // java.util.Map
                    for (Object innerEntry : ((Map) field.get(object)).entrySet()) {
                        Field key = (Field) ((Map.Entry) innerEntry).getKey();
                        Field value = (Field) ((Map.Entry) innerEntry).getValue();
                        stringAppend(stringBuilder, key.getType().getSimpleName(), toString(key.get(object), key.get(object).getClass(), indentFactor + 2));
                        stringAppend(stringBuilder, value.getType().getSimpleName(), toString(value.get(object), value.get(object).getClass(), indentFactor + 2));
                    }
                } else { // other value toString
                    stringAppend(stringBuilder, field.getType().getSimpleName(), toString(field.get(object), field.get(object).getClass(), indentFactor + 2));
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TransformUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stringBuilder.toString();
    }

    public static final JSONObject toJson(Object object) {
        if (null == object) {
            return new JSONObject().put("object", "null");
        }
        return toJson(object, object.getClass());
    }

    private static JSONObject toJson(Object object, Class clazz) {
        JSONObject returnObject = new JSONObject();
        HashMap<String, Field> classFieldsMap = fieldMap(classAllPrivateFields(clazz));
        HashMap<String, Method> classMethodsMap = methodMap(classAllMethods(clazz));

        returnObject.put(clazz.getSimpleName(), clazz.cast(object).hashCode());

        for (Map.Entry<String, Field> entry : classFieldsMap.entrySet()) {

            String fieldName = entry.getKey();
            Field field = entry.getValue();

            field.setAccessible(Boolean.TRUE);
            try {
                String fieldValue;
                try {
                    fieldValue = classMethodsMap.get(fieldName).invoke(clazz.cast(object)).toString();
                } catch (NullPointerException exception) {
                    returnObject.put(fieldName, "null");
                    continue;
                }

                if (isPrimitiveOrWrapperOrString(field.getType())) { // primitive, wrapper or String
                    returnObject.put(fieldName, fieldValue);

                } else if (isDate(field.getType())) { // java.util.Date class
                    returnObject.put(fieldName, fieldValue); // parse to milliseconds@!
                } else if (isList(field.getType())) { // java.util.List
                    JSONArray list = new JSONArray();
                    for (Object listElement : (ArrayList) field.get(object)) {
                        list.put(toJson(listElement));
                    }
                    returnObject.put(fieldName, list);
                } else if (isMap(field.getType())) { // java.util.Map
                    for (Object innerEntry : ((Map) field.get(object)).entrySet()) {
                        Field key = (Field) ((Map.Entry) innerEntry).getKey();
                        Field value = (Field) ((Map.Entry) innerEntry).getValue();
                        JSONObject mapElement = new JSONObject();
//                        if (isPrimitiveOrWrapperOrString(key.getClass())) {
//                            mapElement.put(classMethodsMap.get(fieldName).invoke(clazz.cast(object)).toString(), toJson(value.get(object), value.get(object).getClass()));
//                        }

//                        stringAppend(stringBuilder, key.getType().getSimpleName(), toString(key.get(object), key.get(object).getClass(), indentFactor + 2));
//                        stringAppend(stringBuilder, value.getType().getSimpleName(), toString(value.get(object), value.get(object).getClass(), indentFactor + 2));
                    }
                } else { // other value toString
                    returnObject.put(fieldName, toJson(field.get(object)));
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TransformUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new JSONObject();
    }

    private static void stringAppend(StringBuilder stringBuilder, String name, String value) {
        stringBuilder.append(" (").append(name).append("): ").append(value);
    }

    private static String stripGet(String methodName) {
        if (methodName.startsWith("get")) {
            methodName = methodName.replaceFirst("get", "");
            methodName = methodName.toLowerCase().charAt(0) + methodName.substring(1);
        }
        return methodName;
    }

    //<editor-fold defaultstate="collapsed" desc="Fields and methods">
    /**
     *
     * @param clazz
     * @return HashSet of all class methods
     */
    private static HashSet<Method> classAllMethods(Class clazz) {
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
    private static HashMap<String, Method> methodMap(HashSet<Method> classMethods) {
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
     * @return HashSet of all private fields
     */
    private static HashSet<Field> classAllPrivateFields(Class clazz) {
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
    private static HashMap<String, Field> fieldMap(HashSet<Field> classFields) {
        HashMap<String, Field> classFieldsMap = new HashMap<>();

        for (Field classField : classFields) {
            if (!classFieldsMap.containsKey(classField.getName())) {
                classFieldsMap.put(classField.getName(), classField);
            }
        }
        return classFieldsMap;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Boolean is methods">
    /**
     *
     * @param clazz
     * @return true if clazz is wrapper class
     */
    private static Boolean isWrapper(Class clazz) {
        return clazz == Double.class || clazz == Float.class || clazz == Long.class
                || clazz == Integer.class || clazz == Short.class || clazz == Character.class
                || clazz == Byte.class || clazz == Boolean.class;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is primitive
     */
    private static Boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive();
    }

    /**
     *
     * @param clazz
     * @return true if clazz is String class
     */
    private static Boolean isString(Class clazz) {
        return String.class == clazz;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is String, primitive or wrapper class, false
     * otherwise
     */
    private static Boolean isPrimitiveOrWrapperOrString(Class clazz) {
        return isPrimitive(clazz) || isWrapper(clazz) || isString(clazz);
    }

    /**
     *
     * @param clazz
     * @return true if clazz is java.util.Date
     * @see Date
     */
    private static Boolean isDate(Class clazz) {
        return clazz == Date.class;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is custom object
     */
    private static Boolean isCustomObject(Class clazz) {
        return String.class == clazz;
    }

    /**
     *
     * @param clazz
     * @return true if clazz is java.util.List class
     * @see List
     */
    private static Boolean isList(Class clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    /**
     *
     * @param clazz
     * @return true if clazz is java.util.Map class
     * @see Map
     */
    private static Boolean isMap(Class clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
    //</editor-fold>
}
