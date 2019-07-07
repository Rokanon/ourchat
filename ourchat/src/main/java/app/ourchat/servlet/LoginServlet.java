/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.servlet;

import app.core.utils.RequestUtils;
import app.ourchat.profile.beans.ProfileSessionBean;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

/**
 *
 * @author dark
 */
@WebServlet (name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        RequestUtils requestUtils = new RequestUtils(request);
        
        String mail = requestUtils.readParameter("mail", String.class, null);
        String password = requestUtils.readParameter("password", String.class, null);
        
        JSONObject returnObject = new JSONObject();
        if (ProfileSessionBean.getInstance().login(mail, password)) {
            response.setStatus(HttpServletResponse.SC_OK);
            
            returnObject.put("notification", "Logged in");
            returnObject.put("redirectUrl", "/");
            
            response.getWriter().write(returnObject.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            
            returnObject.put("notification", "Wrong mail or password");
            returnObject.put("redirectUrl", "/");
            
            response.getWriter().write(returnObject.toString());
        }
        
    }
    
}
