package ourchat.ourchat.user.publicBeans;

import app.core.beans.AbstractList;
import java.util.List;
import ourchat.ourchat.user.Profile;
import ourchat.ourchat.user.dao.ProfileDao;

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
