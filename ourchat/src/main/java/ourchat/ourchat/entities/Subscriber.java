/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.ourchat.entities;

import ourchat.ourchat.base.Model;

/**
 * Connects profile to group
 *
 * @author dark
 */
public class Subscriber extends Model {

    private Long profileId;
    private Long groupId;

    /**
     * @return the profileId
     */
    public Long getProfileId() {
        return profileId;
    }

    /**
     * @param profileId the profileId to set
     */
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    /**
     * @return the groupId
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
