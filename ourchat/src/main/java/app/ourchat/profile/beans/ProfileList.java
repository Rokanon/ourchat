package app.ourchat.profile.beans;

import app.core.beans.AbstractList;
import java.util.List;
import app.ourchat.profile.model.Profile;
import app.ourchat.profile.dao.ProfileDao;

/**
 *
 * @author dark
 */
public class ProfileList extends AbstractList<Profile> {

    @Override
    public List<Profile> getList() {
        return new ProfileDao().loadList("");
    }

}
