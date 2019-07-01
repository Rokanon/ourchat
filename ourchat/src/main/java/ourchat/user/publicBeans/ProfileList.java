package ourchat.user.publicBeans;

import app.core.beans.AbstractList;
import java.util.List;
import ourchat.user.Profile;
import ourchat.user.dao.ProfileDao;

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
