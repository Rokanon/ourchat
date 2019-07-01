package app.ourchat.user.publicBeans;

import app.core.beans.AbstractList;
import java.util.List;
import app.ourchat.user.Profile;
import app.ourchat.user.dao.ProfileDao;

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
