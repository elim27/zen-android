package com.MADAPPS.zen;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preferences {
    private static SharedPreferences zenPrefs;
    private static String ZEN_PREF = "pref1";


    //timer preferences needed for functionality
    public static final String KEY_MILLIS_LEFT = "time";
    public static final String KEY_FIRSTSETUP = "start";
    public static final String KEY_CLICKSTARTTIME = " oof";
    public static final String KEY_RUNNING = "run";
    public static final String KEY_TRACKER = "progress";
    public static final String KEY_DIFF = "diff";
    public static final String KEY_PAUSED = "pause";
    public static final String KEY_MOREZEN = "yeet";


    //stats key
    public static final String KEY_STREAK = "fire";
    public static final String KEY_TOTALRECORD = "rec";
    public static final String KEY_TOTALCOMP = "comp";


    //TimeCheckActivity Keys
    public static final String KEY_CURRDAY = "check";
    public static final String KEY_DAILYCOMP = "ye";

    //More Fragment
    public static final String KEY_TOTALTIME = "Totaltimeidiotmadek";
    public static final String KEY_SELECTORPOS = "pos";


    public Preferences(){

    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void setVal(Context context, String key, long value){
        zenPrefs =  context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = zenPrefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static void setVal(Context context, String key, boolean value){
        zenPrefs = context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = zenPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setVal(Context context, String key, int value){
        zenPrefs =  context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = zenPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static long getLongVal(Context context, String key){
        zenPrefs =  context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        long ret =  zenPrefs.getLong(key, 0);
        Log.i("RETURN LONG", "VALUE: " + ret);
        return ret;
    }


    public static boolean getBoolVal(Context context, String key){
        zenPrefs = context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        return zenPrefs.getBoolean(key, false);
    }

    public static int getIntVal(Context context, String key){
        zenPrefs = context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        return zenPrefs.getInt(key, 0);
    }


}
