/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.ourchat.user.dao;

import app.core.dao.GenericDao;
import ourchat.ourchat.user.Profile;

/**
 *
 * @author dark
 */
public class ProfileDao extends GenericDao<Profile> {

    @Override
    protected Profile newDto() {
        return new Profile();
    }

    public ProfileDao() {
        super();
    }

}
