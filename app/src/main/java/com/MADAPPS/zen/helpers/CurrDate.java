package com.MADAPPS.zen.helpers;

import java.util.Calendar;
import java.util.TimeZone;



public class CurrDate {
    private Calendar calendar;
    TimeZone t;

    public CurrDate(){
        t = TimeZone.getDefault();

        if (t == null) {
            t = TimeZone.getTimeZone("CST");
        }
        calendar = Calendar.getInstance(t);
    }

    public int getDate(){
        return calendar.get(Calendar.MINUTE);

    }
}
