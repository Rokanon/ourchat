/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
        LinkedHashMap<String, Field> classFieldsMap = ClassUtils.fieldLHMap(ClassUtils.classFields(clazz));
        LinkedHashMap<String, Method> classMethodsMap = ClassUtils.methodLHMap(ClassUtils.classGetMethods(clazz));

        returnObject.put("class", clazz.getName());

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
