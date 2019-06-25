package ourchat.ourchat.user;

import app.core.dao.annotations.Column;
import app.core.dao.annotations.Table;
import app.core.dao.enums.ConnectionType;
import app.core.dao.enums.FieldType;
import ourchat.ourchat.base.Model;

@Table(name = "user", connectionType = ConnectionType.PRODUCER)
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
