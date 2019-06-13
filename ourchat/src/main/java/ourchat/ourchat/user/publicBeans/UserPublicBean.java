/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.ourchat.user.publicBeans;

import ourchat.ourchat.user.User;
import ourchat.ourchat.user.dao.UserDao;

/**
 *
 * @author dark
 */
public class UserPublicBean {

    private User user;
    
    public UserPublicBean() {
        user = new UserDao().getById(1);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
}
