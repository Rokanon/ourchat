/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.utils;

import org.json.JSONObject;

/**
 *
 * @author dark
 */
public interface Transformable {

    /**
     *
     * @return
     */
    default String stringify() {
//        return TransformUtils.toString(this);
        return "";
    }

    default JSONObject jsonfy() {
        return TransformUtils.objectToJson(this);
    }
}
