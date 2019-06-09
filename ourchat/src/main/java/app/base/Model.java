/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.base;

import app.dao.annotations.Column;
import app.dao.enums.FieldType;
import java.io.Serializable;
import org.json.JSONObject;
import utils.Transformable;

/**
 * Base superclass
 *
 * @author dark
 */
public class Model implements Serializable, Transformable {

    @Column(name = "id", type = FieldType.LONG)
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
