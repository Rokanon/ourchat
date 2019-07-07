/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.profile.beans;

import app.ourchat.profile.model.Profile;

/**
 *
 * @author dark
 */
public class ProfileSessionHandler {

    /**
     * @return the profileSession
     */
    private ProfileSessionBean profileSession() {
        return ProfileSessionBean.getInstance();
    }

    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profileSession().getProfile();
    }

    /**
     * @return the loggedIn
     */
    public Boolean getLoggedIn() {
        return profileSession().getLoggedIn();
    }
}
