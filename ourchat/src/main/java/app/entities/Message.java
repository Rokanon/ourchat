/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entities;

import app.base.Model;
import app.dao.annotations.Column;
import app.dao.annotations.Table;
import app.dao.enums.FieldType;
import java.util.Date;

/**
 * Single message sent or received
 *
 * @author dark
 */
@Table(name = "message")
public class Message extends Model {

    @Column(name = "message", type = FieldType.STRING)
    private String message;
    @Column(name = "seen", type = FieldType.BOOLEAN)
    private Boolean seen;
    @Column(name = "create_date", type = FieldType.DATE)
    private Date createDate;
    @Column(name = "update_date", type = FieldType.DATE)
    private Date updateDate;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the seen
     */
    public Boolean getSeen() {
        return seen;
    }

    /**
     * @param seen the seen to set
     */
    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
