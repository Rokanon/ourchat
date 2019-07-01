package ourchat.user.publicBeans;

import ourchat.user.User;
import ourchat.user.dao.UserDao;

/**
 *
 * @author dark
 */
public class UserPublicBean {

    private Long userId;
    private User user;

    public UserPublicBean() {

    }

    /**
     * @return the user
     */
    public User getUser() {
        if (null == user && userId != null && userId > 0) {
            user = new UserDao().getById(userId);
        }
        return user;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
