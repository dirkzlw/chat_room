package com.chat.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Intel-Meeting
 * @create 2019-09-03 21:37
 */
public class DateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     *
     * @return
     */
    public static String getDate() {

        try {
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
