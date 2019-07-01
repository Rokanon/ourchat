/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.user.dao;

import app.core.db.dao.GenericDao;
import app.ourchat.user.User;

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
