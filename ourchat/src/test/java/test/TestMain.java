/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dark
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestClass testClass = new TestClass();

        testClass.setFieldBoolean(Boolean.FALSE);
        testClass.setFieldDate(new Date());
        testClass.setFieldInteger(23);
        testClass.setFieldLong(10L);
        testClass.setFieldString("Oktorar");
        testClass.setFieldList(fillList());
        testClass.setFieldMap(fillMap());

//        System.out.println("TestClass:\n" + testClass.toString());
        System.out.println("TestClass:\n" + testClass.toJson().toString(2));
    }

    public static List<TestClass2> fillList() {
        List<TestClass2> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TestClass2 testClass = new TestClass2();
            testClass.setFieldBoolean2(Boolean.FALSE);
            testClass.setFieldDate2(new Date());
            testClass.setFieldInteger2(23 + i);
            testClass.setFieldLong2(10L + i);
            testClass.setFieldString2("Oktorar" + i);
            list.add(testClass);
        }
        return list;
    }

    public static LinkedHashMap<String, TestClass2> fillMap() {
        LinkedHashMap<String, TestClass2> map = new LinkedHashMap<>();
        for (int i = 0; i < 3; i++) {
            TestClass2 testClass = new TestClass2();
            testClass.setFieldBoolean2(Boolean.TRUE);
            testClass.setFieldDate2(new Date());
            testClass.setFieldInteger2(23 + i);
            testClass.setFieldLong2(10L + i);
            testClass.setFieldString2("Oktorar" + i);
            map.put("" + i, testClass);
        }
        return map;
    }

}
