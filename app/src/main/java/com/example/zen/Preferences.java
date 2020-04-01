package com.example.zen;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preferences {
    private static SharedPreferences zenPrefs;
    private static String ZEN_PREF = "pref1";


    public Preferences(){

    }

    /**
     * TODO// TRANSFER SHAREDPREFERENCES TO A GENERIC CLASS
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
        long ret =  zenPrefs.getLong(key, (long)0);
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
