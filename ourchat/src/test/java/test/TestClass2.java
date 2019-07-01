/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import app.core.utils.Transformable;
import java.io.Serializable;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author dark
 */
public class TestClass2 implements Serializable, Transformable {

    private Boolean fieldBoolean2;
    private Integer fieldInteger2;
    private Long fieldLong2;
    private String fieldString2;
    private Date fieldDate2;

    @Override
    public String toString() {
        return stringify();
    }

    public JSONObject toJson() {
        return jsonfy();
    }

    /**
     * @return the fieldBoolean2
     */
    public Boolean getFieldBoolean2() {
        return fieldBoolean2;
    }

    /**
     * @param fieldBoolean2 the fieldBoolean2 to set
     */
    public void setFieldBoolean2(Boolean fieldBoolean2) {
        this.fieldBoolean2 = fieldBoolean2;
    }

    /**
     * @return the fieldInteger2
     */
    public Integer getFieldInteger2() {
        return fieldInteger2;
    }

    /**
     * @param fieldInteger2 the fieldInteger2 to set
     */
    public void setFieldInteger2(Integer fieldInteger2) {
        this.fieldInteger2 = fieldInteger2;
    }

    /**
     * @return the fieldLong2
     */
    public Long getFieldLong2() {
        return fieldLong2;
    }

    /**
     * @param fieldLong2 the fieldLong2 to set
     */
    public void setFieldLong2(Long fieldLong2) {
        this.fieldLong2 = fieldLong2;
    }

    /**
     * @return the fieldString2
     */
    public String getFieldString2() {
        return fieldString2;
    }

    /**
     * @param fieldString2 the fieldString2 to set
     */
    public void setFieldString2(String fieldString2) {
        this.fieldString2 = fieldString2;
    }

    /**
     * @return the fieldDate2
     */
    public Date getFieldDate2() {
        return fieldDate2;
    }

    /**
     * @param fieldDate2 the fieldDate2 to set
     */
    public void setFieldDate2(Date fieldDate2) {
        this.fieldDate2 = fieldDate2;
    }
}
