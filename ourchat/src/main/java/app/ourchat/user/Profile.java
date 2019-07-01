/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.user;

import app.core.db.dao.annotations.Column;
import app.core.db.dao.annotations.Table;
import app.core.db.dao.enums.ConnectionType;
import app.core.db.dao.enums.FieldType;
import app.core.domain.Model;

/**
 *
 * @author dark
 */
@Table(name = "profile", connectionType = ConnectionType.PRODUCER)
public class Profile extends Model {
    
    @Column(name = "user_id", type = FieldType.LONG)
    private Long userId;
    @Column(name = "name", type = FieldType.STRING)
    private String name;

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
