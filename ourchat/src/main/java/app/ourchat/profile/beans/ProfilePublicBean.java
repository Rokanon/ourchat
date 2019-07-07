package app.ourchat.profile.beans;

import app.ourchat.profile.model.Profile;
import app.ourchat.profile.dao.ProfileDao;
import app.ourchat.user.publicBeans.UserPublicBean;

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
