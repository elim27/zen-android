package com.MADAPPS.zen.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Shared preferences for other objects (i.e. the date, spinner positions, etc.)
 */
public class Prefs {
    private static SharedPreferences zenPrefs;
    private static String ZEN_PREF = "pref1";

    //TimeCheckActivity Keys
    public static final String KEY_CURRDAY = "check";

    //More Fragment
    public static final String KEY_DURATION_POS = "pos";
    public static final String KEY_AUDIO_POS = "audio1";

    public Prefs(){

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



    public static void setVal(Context context, String key, float value){
        zenPrefs =  context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = zenPrefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    /**
     * @param context
     * @param key
     * @param value
     */
    public static void setVal(Context context, String key, String value){
        zenPrefs =  context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = zenPrefs.edit();
        editor.putString(key, value);
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

    public static String getStringVal(Context context, String key){
        zenPrefs = context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        return zenPrefs.getString(key, "silence");
    }


    public static float getFloatVal(Context context, String key){
        zenPrefs = context.getSharedPreferences(ZEN_PREF, Context.MODE_PRIVATE);
        return zenPrefs.getFloat(key, 0);
    }



}
