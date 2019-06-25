/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dark
 */
@WebServlet(name = "AbstractServlet")
public abstract class AbstractServlet extends HttpServlet {

    private Map<String, String> parameterMap = new HashMap<>();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected abstract void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected abstract void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(fillParameterMap(req), resp);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Abstract servlet";
    }
    // </editor-fold>

    public String getParameter(String parameterName) {

        if (parameterMap.containsKey(parameterName)) {
            return parameterMap.get(parameterName);
        }
        return null;
    }

    public <T> T readParameter(String parameterName, Class<T> clazz, T defaultValue) {
        if (parameterMap.containsKey(parameterName)) {
            try {
                return (T) clazz.getMethod("valueOf", String.class).invoke(clazz, parameterMap.get(parameterName));
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    private HttpServletRequest fillParameterMap(HttpServletRequest request) {
        System.out.println("method: " + request.getMethod());
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
        return request;
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

            for (String keyValuePair : bufferedReader.readLine().split("&")) {
                String[] pairs = keyValuePair.trim().split("=");
                if (!parameterMap.containsKey(pairs[0])) {
                    parameterMap.put(pairs[0], pairs[1]);

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AbstractServlet.class.getName()).log(Level.SEVERE, null, ex);
            parameterMap = new HashMap<>();
        }
    }

    public void printParams() {
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }

    }
}
