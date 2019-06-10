package ourchat.ourchat.user;

import ourchat.ourchat.base.Model;
import ourchat.ourchat.dao.annotations.Column;
import ourchat.ourchat.dao.annotations.Table;
import ourchat.ourchat.dao.enums.FieldType;

@Table(name = "user")
public class User extends Model {

    @Column(name = "mail", type = FieldType.STRING)
    private String email;
    @Column(name = "password", type = FieldType.STRING)
    private String password;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
