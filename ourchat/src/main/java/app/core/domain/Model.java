/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.domain;

import app.core.db.dao.annotations.Column;
import app.core.db.dao.enums.FieldType;
import java.io.Serializable;
import org.json.JSONObject;
import app.core.utils.Transformable;

/**
 * Base superclass
 *
 * @author dark
 */
public class Model implements Serializable, Transformable {

    @Column(name = "id", type = FieldType.LONG, primaryKey = true)
    private Long id;

    @Override
    public String toString() {
        return stringify();
    }
    
    public JSONObject toJson() {
        return jsonfy();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
