package ourchat.user.publicBeans;

import ourchat.user.Profile;
import ourchat.user.dao.ProfileDao;

/**
 *
 * @author dark
 */
public class ProfilePublicBean extends UserPublicBean {

    private Profile profile;

    public ProfilePublicBean() {
        super();
    }

    /**
     * @return the profile
     */
    public Profile getProfile() {
        if (null == profile && null != getUser() && null != getUser().getId() && getUser().getId() > 0) {
            profile = new ProfileDao().getById(getUser().getId());
        }
        return profile;
    }

}
