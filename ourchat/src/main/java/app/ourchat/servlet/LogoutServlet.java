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
@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());

        JSONObject returnObject = new JSONObject();
        ProfileSessionBean.getInstance().logout();
        response.setStatus(HttpServletResponse.SC_OK);

        returnObject.put("notification", "Logged out");
        returnObject.put("redirectUrl", "/");

        response.getWriter().write(returnObject.toString());
    }

}
