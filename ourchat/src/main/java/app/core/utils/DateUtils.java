/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dark
 */
public class DateUtils {

    private static String DATETIME_SQL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     * @param date
     * @return format date in pattern (yyyy-MM-dd HH:mm:ss)
     */
    public static String formatDate(Date date) {
        return formatDate(date, DATETIME_SQL_FORMAT);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);

    }
}
