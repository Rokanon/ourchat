/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.profile.beans;

import app.ourchat.profile.dao.ProfileDao;
import app.ourchat.profile.logic.Sessionable;
import app.ourchat.profile.model.Profile;
import app.ourchat.user.User;
import app.ourchat.user.dao.UserDao;
import javax.ejb.Stateful;

/**
 *
 * @author dark
 */
@Stateful(mappedName = "", description = "Logged in profile")
public class ProfileSessionBean implements Sessionable {

    private static ProfileSessionBean instance;

    private Profile profile;
    private Boolean loggedIn = Boolean.FALSE;

    public ProfileSessionBean() {
        

    }

    public static ProfileSessionBean getInstance() {
        if (null == instance) {
            instance = new ProfileSessionBean();
        }
        return instance;
    }

    @Override
    public boolean login(String mail, String password) {
        if (!getLoggedIn()) {
            User user = new UserDao().getByWhereCondition("mail = '" + mail + "'");
            if (null != user) {
                if (validate(user.getPassword(), password)) {
                    profile = new ProfileDao().getByWhereCondition("user_id = " + user.getId());
                    if (null != profile) {
                        loggedIn = Boolean.TRUE;
                    }
                }
            }
        } else {
            loggedIn = Boolean.TRUE;
        }
        return getLoggedIn();

    }

    @Override
    public boolean validate(String realPassword, String loginPassword) {
        System.out.println("VALIDATE");
        // encrypt real password 1st
        return realPassword.equals(loginPassword);
    }

    @Override
    public void logout() {
        profile = null;
        loggedIn = Boolean.FALSE;
    }

    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @return the loggedIn
     */
    public Boolean getLoggedIn() {
        return loggedIn;
    }

}
