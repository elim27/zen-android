package com.MADAPPS.zen;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MADAPPS.zen.Prefs;
import com.MADAPPS.zen.activities.StartupActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class StreakCheck extends AppCompatActivity {
    private static final String KEY_CURRDAY = Prefs.KEY_CURRDAY; //key where our currDate is stored
    private static final String KEY_DAILYCOMP = Prefs.KEY_DAILYCOMP;
    private static final String KEY_STREAK = Prefs.KEY_STREAK;
    private static Activity activity;

    public StreakCheck()

    {

    }

    /**
     *
     * @return true if main activity should be started, false indicated deadstreakactivity
     */
    public static boolean checkDailyStreak(Activity theActivity){
        activity = theActivity;
        int curr;
        Calendar calendar;
        TextView timer = StartupActivity.timer;
        TimeZone t = TimeZone.getDefault();

        if (t == null) {
            t = TimeZone.getTimeZone("CST");
        }
        calendar = Calendar.getInstance(t);
        //checks if this is the first time starting up
        int firstStartup = Prefs.getIntVal(activity, KEY_CURRDAY);
        Log.i("firstStartup Value: ", "" + firstStartup);
        if (firstStartup == 0) {
            Log.i("firstStartup", "reached");
            curr = calendar.get(Calendar.MINUTE);
            Prefs.setVal(activity, KEY_CURRDAY, curr);
          return true;
        } else {
            int test = calendar.get(Calendar.MINUTE); //gets current day
            boolean comp = Prefs.getBoolVal(activity, KEY_DAILYCOMP);  //checks to see if daily activity completed
            curr = Prefs.getIntVal(activity, KEY_CURRDAY); //gets time when the app was opened
            //checks if the day is different
            if (test != curr) {
                curr = test;
                Prefs.setVal(activity, KEY_CURRDAY, curr);
                long total = Prefs.getLongVal(activity, Prefs.KEY_RUNTIME);
                Prefs.setVal(activity, Prefs.KEY_MILLIS_LEFT, total);

                //if daily task not completed AND streak is not 0
                if (!comp && Prefs.getIntVal(activity, KEY_STREAK) != 0) {
                    //DeadStreakActivity
                    Log.i("COMP FALSE", "REACHED");
                    return false;
                } else {
                    Prefs.setVal(activity, KEY_DAILYCOMP, false);
                   return true;
                }
            } else {
                return true;
            }
        }
    }
}


