/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dark
 */
public interface Restable {

    public void select(HttpServletRequest request, HttpServletResponse response);

    public void insert(HttpServletRequest request, HttpServletResponse response);

    public void update(HttpServletRequest request, HttpServletResponse response);

    public void delete(HttpServletRequest request, HttpServletResponse response);
}
