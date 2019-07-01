/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.user.dao;

import app.core.dao.GenericDao;
import ourchat.user.User;

/**
 *
 * @author dark
 */
public class UserDao extends GenericDao<User> {

    @Override
    protected User newDto() {
        return new User();
    }

    public UserDao() {
        super();
    }

}
