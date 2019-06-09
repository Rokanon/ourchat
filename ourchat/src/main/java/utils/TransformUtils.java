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
import java.util.Date;
import java.util.HashMap;
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

        HashMap<String, Field> classFieldsMap = ClassUtils.fieldMap(ClassUtils.classAllPrivateFields(clazz));
        HashMap<String, Method> classMethodsMap = ClassUtils.methodMap(ClassUtils.classAllMethods(clazz));

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

                if (ClassUtils.isPrimitiveOrWrapperOrString(field.getType())) { // primitive, wrapper or String
                    stringAppend(stringBuilder, field.getType().getSimpleName(), fieldValue);
                    stringBuilder.append("\n");
                } else if (ClassUtils.isDate(field.getType())) { // java.util.Date class
                    stringAppend(stringBuilder, field.getType().getSimpleName(), fieldValue);
                    stringBuilder.append("\n");
                } else if (ClassUtils.isList(field.getType())) { // java.util.List
                    for (Object listElement : (ArrayList) field.get(object)) {
                        stringAppend(stringBuilder, listElement.getClass().getSimpleName(), toString(listElement, listElement.getClass(), indentFactor + 2));
                    }
                } else if (ClassUtils.isMap(field.getType())) { // java.util.Map
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

    /**
     * Creates JSONObject from object
     *
     * @param object
     * @return JSONObject
     * @see JSONObject
     */
    public static final JSONObject objectToJson(Object object) {
        if (null == object) {
            return new JSONObject().put("object", JSONObject.NULL);
        }
        return objectToJson(object, object.getClass());
    }

    private static JSONObject objectToJson(Object object, Class clazz) {
        JSONObject returnObject = new JSONObject();
        HashMap<String, Field> classFieldsMap = ClassUtils.fieldMap(ClassUtils.classAllPrivateFields(clazz));
        HashMap<String, Method> classMethodsMap = ClassUtils.methodMap(ClassUtils.classAllMethods(clazz));

        returnObject.put(clazz.getSimpleName(), clazz.cast(object).hashCode());

        for (Map.Entry<String, Field> entry : classFieldsMap.entrySet()) {

            String fieldName = entry.getKey();
            Field field = entry.getValue();

            field.setAccessible(Boolean.TRUE);
            try {
                if (null == classMethodsMap.get(fieldName).invoke(clazz.cast(object))) {
                    returnObject.put(fieldName, JSONObject.NULL);
                    continue;
                }
                if (ClassUtils.isPrimitiveOrWrapperOrString(field.getType())) { // primitive, wrapper or String
                    returnObject.put(fieldName, classMethodsMap.get(fieldName).invoke(clazz.cast(object)));
                } else if (ClassUtils.isDate(field.getType())) { // java.util.Date class
                    returnObject.put(fieldName, ((Date) classMethodsMap.get(fieldName).invoke(clazz.cast(object))).getTime()); // parse to milliseconds@!
                } else if (ClassUtils.isList(field.getType())) { // java.util.List
                    JSONArray list = new JSONArray();
                    for (Object listElement : (ArrayList) field.get(object)) {
                        list.put(objectToJson(listElement));
                    }
                    returnObject.put(fieldName, list);
                } else if (ClassUtils.isMap(field.getType())) { // java.util.Map - keys are wrapper or string
                    JSONArray mapArray = new JSONArray();
                    for (Object innerEntry : ((Map) field.get(object)).entrySet()) {
                        JSONObject mapElement = new JSONObject();
                        Object key = ((Map.Entry) innerEntry).getKey();
                        Object value = ((Map.Entry) innerEntry).getValue();
                        if (ClassUtils.isPrimitiveOrWrapperOrString(key.getClass())) {
                            mapElement.put("key", key.toString());
                            if (ClassUtils.isPrimitiveOrWrapperOrString(value.getClass())) {
                                mapElement.put("value", value.toString());
                            } else {
                                mapElement.put("value", objectToJson(value));
                            }
                            mapArray.put(mapElement);
                        }
                    }
                    returnObject.put(fieldName, mapArray);
                } else { // other value toString
                    returnObject.put(fieldName, objectToJson(field.get(object)));
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TransformUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return returnObject;
    }

    private static void stringAppend(StringBuilder stringBuilder, String name, String value) {
        stringBuilder.append(" (").append(name).append("): ").append(value);
    }

}
