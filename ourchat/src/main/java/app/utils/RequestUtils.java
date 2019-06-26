/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dark
 */
public class RequestUtils {

    private Map<String, String> parameterMap = new HashMap<>();

    public RequestUtils(HttpServletRequest request) {
        fillParameterMap(request);
    }

    public String getParameter(String parameterName) {
        if (parameterMap.containsKey(parameterName)) {
            return parameterMap.get(parameterName);
        }
        return null;
    }

    public <T> T readParameter(String parameterName, Class<T> clazz, T defaultValue) {
        if (parameterMap.containsKey(parameterName)) {
            if (clazz.equals(String.class)) {
                return (T) getParameter(parameterName);
            } else {
                try {
                    return (T) clazz.getMethod("valueOf", String.class).invoke(clazz, parameterMap.get(parameterName));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    return defaultValue;
                }
            }
        } else {
            return defaultValue;
        }
    }

    private void fillParameterMap(HttpServletRequest request) {
        if (parameterMap.isEmpty()) {
            switch (request.getMethod()) {
                case "POST":
                case "GET":
                    fillParamMapRegular(request);
                    break;
                case "PUT":
                case "DELETE":
                    fillParamMapInputStream(request);
                    break;
                default:
            }
        }
    }

    /**
     * Fill parameter map from request parameter map
     *
     * @param request
     */
    private void fillParamMapRegular(HttpServletRequest request) {
        request.getParameterMap().entrySet().stream().filter((entry) -> (!parameterMap.containsKey(entry.getKey()))).forEachOrdered((entry) -> {
            parameterMap.put(entry.getKey(), entry.getValue()[0]);
        });
    }

    /**
     * fill parameter map from request input stream
     *
     * @param request
     */
    private void fillParamMapInputStream(HttpServletRequest request) {
        try (InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String[] ketValueParams = bufferedReader.readLine().split("&");
            for (String keyValuePair : ketValueParams) {
                String[] pairs = keyValuePair.trim().split("=");
                if (!parameterMap.containsKey(pairs[0])) {
                    parameterMap.put(pairs[0], pairs[1]);

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestUtils.class.getName()).log(Level.SEVERE, null, ex);
            parameterMap = new HashMap<>();
        }
    }

    public void printParams() {
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }

    }
}
