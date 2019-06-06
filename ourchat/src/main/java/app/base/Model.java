/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.base;

import java.io.Serializable;
import utils.Transformable;

/**
 * Base superclass
 *
 * @author dark
 */
public class Model implements Serializable, Transformable {

    private Long id;

    @Override
    public String toString() {
        return stringify();
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
