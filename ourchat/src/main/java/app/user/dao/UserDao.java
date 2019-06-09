/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.user.dao;

import app.dao.GenericDao;
import app.user.User;
import java.sql.Connection;

/**
 *
 * @author dark
 */
public class UserDao extends GenericDao<User> {

    public UserDao(Connection connection) {
        super(connection);
    }
    
//    private static UserDao instance;
//    
//    private static UserDao getInstance() {
//        if (null == instance) {
//            instance = new UserDao();
//        }
//        return instance;
//    }
    
    @Override
    protected User newDto() {
        return new User();
    }
    
}
