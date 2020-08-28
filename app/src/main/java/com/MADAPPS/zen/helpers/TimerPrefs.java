package com.MADAPPS.zen.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Shared preferences specifically for the timer
 */
public class TimerPrefs {
    private static SharedPreferences timerPrefs;
    private static String TIMER_PREF = "com.MADAPPS.zen.TIMER_PREF";
    //Keys
    public static final String KEY_TOTAL_TIME =
            "com.MADAPPS.zen.KEY_TOTAL_TIME"; //total run time of timer
    public static final String KEY_MILLIS_LEFT=
            "com.MADAPPS.zen.KEY_MILLIS_LEFT"; //millis left on timer
    public static final String KEY_EXIT_TIME =
            "com.MADAPPS.zen.KEY_EXIT_TIME";
    public static final String KEY_MILLIS_ALL_TIME=
            "com.MADAPPS.zen.KEY_MILLIS_ALL_TIME";
    public static final String KEY_RUNNING =
            "com.MADAPPS.zen.KEY_RUNNING";
    //checks if user completed daily task
    public static final String KEY_DAILY_TASK_COMPLETE =
            "com.MADAPPS.zen.KEY_DAILY_TASK_COMPLETE";
    public static final String KEY_COMPLETIONS =
            "com.MADAPPS.zen.KEY_TOTAL_COMPLETIONS";
    public static final String KEY_STREAK=
            "com.MADAPPS.zen.KEY_STREAK";
    public static final String KEY_DAILY_TOTAL =
            "com.MADAPPS.zen.KEY_DAILY_TOTAL";



    public static long getLongVal(Context context, String key){
        timerPrefs =  context.getSharedPreferences(TIMER_PREF, Context.MODE_PRIVATE);
        return timerPrefs.getLong(key, 0);
    }

    public static void setVal(Context context, String key, long value){
        timerPrefs =  context.getSharedPreferences(TIMER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = timerPrefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static boolean getBoolVal(Context context, String key){
        timerPrefs =  context.getSharedPreferences(TIMER_PREF, Context.MODE_PRIVATE);
        return timerPrefs.getBoolean(key, false);
    }

    public static void setVal(Context context, String key, boolean value){
        timerPrefs =  context.getSharedPreferences(TIMER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = timerPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static int getIntVal(Context context, String key){
        timerPrefs =  context.getSharedPreferences(TIMER_PREF, Context.MODE_PRIVATE);
        return timerPrefs.getInt(key, 0);
    }

    public static void setVal(Context context, String key, int value){
        timerPrefs =  context.getSharedPreferences(TIMER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = timerPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }








}
