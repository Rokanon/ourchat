/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.JSONObject;
import app.utils.Transformable;

/**
 *
 * @author dark
 */
public class TestClass implements Serializable, Transformable {

    private Boolean fieldBoolean;
    private Integer fieldInteger;
    private Long fieldLong;
    private String fieldString;
    private Date fieldDate;
    private List<TestClass2> fieldList;
    private LinkedHashMap<String, TestClass2> fieldMap;

    @Override
    public String toString() {
        return stringify();
    }

    public JSONObject toJson() {
        return jsonfy();
    }

    /**
     * @return the fieldBoolean
     */
    public Boolean getFieldBoolean() {
        return fieldBoolean;
    }

    /**
     * @param fieldBoolean the fieldBoolean to set
     */
    public void setFieldBoolean(Boolean fieldBoolean) {
        this.fieldBoolean = fieldBoolean;
    }

    /**
     * @return the fieldInteger
     */
    public Integer getFieldInteger() {
        return fieldInteger;
    }

    /**
     * @param fieldInteger the fieldInteger to set
     */
    public void setFieldInteger(Integer fieldInteger) {
        this.fieldInteger = fieldInteger;
    }

    /**
     * @return the fieldLong
     */
    public Long getFieldLong() {
        return fieldLong;
    }

    /**
     * @param fieldLong the fieldLong to set
     */
    public void setFieldLong(Long fieldLong) {
        this.fieldLong = fieldLong;
    }

    /**
     * @return the fieldString
     */
    public String getFieldString() {
        return fieldString;
    }

    /**
     * @param fieldString the fieldString to set
     */
    public void setFieldString(String fieldString) {
        this.fieldString = fieldString;
    }

    /**
     * @return the fieldDate
     */
    public Date getFieldDate() {
        return fieldDate;
    }

    /**
     * @param fieldDate the fieldDate to set
     */
    public void setFieldDate(Date fieldDate) {
        this.fieldDate = fieldDate;
    }

    /**
     * @return the fieldList
     */
    public List<TestClass2> getFieldList() {
        return fieldList;
    }

    /**
     * @param fieldList the fieldList to set
     */
    public void setFieldList(List<TestClass2> fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * @return the fieldMap
     */
    public LinkedHashMap<String, TestClass2> getFieldMap() {
        return fieldMap;
    }

    /**
     * @param fieldMap the fieldMap to set
     */
    public void setFieldMap(LinkedHashMap<String, TestClass2> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
