/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.ourchat.user.dao;

import app.core.dao.GenericDao;
import ourchat.ourchat.user.User;
import java.sql.Connection;

/**
 *
 * @author dark
 */
public class UserDao extends GenericDao<User> {

    public UserDao(Connection connection) {
        super();
    }

    public UserDao() {
        super();
    }
    
    
    @Override
    protected User newDto() {
        return new User();
    }
    
}
