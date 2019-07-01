/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.user.logic;

import app.core.servlet.Restable;
import app.utils.RequestUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ourchat.user.Profile;
import ourchat.user.dao.ProfileDao;

/**
 *
 * @author dark
 */
public class ProfileServletLogic implements Restable {

    @Override
    public void insert(HttpServletRequest request, HttpServletResponse response) {

        RequestUtils requestUtils = new RequestUtils(request);

        Long userId = requestUtils.readParameter("userId", Long.class, 0L);
        String name = requestUtils.readParameter("name", String.class, null);

        Profile dto = new Profile();

        dto.setName(name);
        dto.setUserId(userId);

        ProfileDao dao = new ProfileDao();
        dto.setId(dao.insert(dto));

        System.out.println(dto.jsonfy().toString(2));

    }

    @Override
    public void update(HttpServletRequest request, HttpServletResponse response) {
        RequestUtils requestUtils = new RequestUtils(request);

        Long id = requestUtils.readParameter("id", Long.class, 0L);
        String name = requestUtils.readParameter("name", String.class, null);
        
        ProfileDao dao = new ProfileDao();
        Profile dto = dao.getById(id);
    
        dto.setName(name);
        
        dao.update(dto);
        
        System.out.println(dto.jsonfy().toString(2));
    }

    @Override
    public void select(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        RequestUtils requestUtils = new RequestUtils(request);

        Long id = requestUtils.readParameter("id", Long.class, 0L);
        ProfileDao dao = new ProfileDao();
        
        // #TODO DAO delete implementation
        
    }

}
