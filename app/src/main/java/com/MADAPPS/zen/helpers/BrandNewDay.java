package com.MADAPPS.zen.helpers;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MADAPPS.zen.Database.Day;
import com.MADAPPS.zen.activities.startup.DayModel;
import com.MADAPPS.zen.activities.startup.StartupActivity;
import com.MADAPPS.zen.helpers.Prefs;
import com.MADAPPS.zen.helpers.TimerPrefs;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 */
public class BrandNewDay extends AppCompatActivity {
    private static final String KEY_CURRDAY = Prefs.KEY_CURRDAY; //key where our currDate is stored
    private static final String KEY_DAILY_TASK_COMPLETE = TimerPrefs.KEY_DAILY_TASK_COMPLETE;
    private static final String KEY_STREAK = TimerPrefs.KEY_STREAK;
    private static Activity activity;

    public BrandNewDay()

    {

    }

    /**
     *
     * @return true if main activity should be started, false indicated deadstreakactivity
     */
    public boolean checkDailyStreak(Activity theActivity){
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
            insertDay();
            boolean comp = TimerPrefs.getBoolVal(activity, KEY_DAILY_TASK_COMPLETE);  //checks to see if daily activity completed
            curr = Prefs.getIntVal(activity, KEY_CURRDAY); //gets time when the app was opened
            //checks if the day is different
            if (test != curr) {
                curr = test;
                Prefs.setVal(activity, KEY_CURRDAY, curr);
                long total = TimerPrefs.getLongVal(activity,TimerPrefs.KEY_TOTAL_TIME);
                TimerPrefs.setVal(activity, TimerPrefs.KEY_MILLIS_LEFT, total);
                TimerPrefs.setVal(activity, KEY_DAILY_TASK_COMPLETE, false);
                TimerPrefs.setVal(activity, TimerPrefs.KEY_DAILY_TOTAL, (long) 0);

                //if daily task not completed AND streak is not 0
                if (!comp && TimerPrefs.getIntVal(activity, KEY_STREAK) != 0) {
                    return false;
                } else {
                    TimerPrefs.setVal(activity, KEY_DAILY_TASK_COMPLETE, false);
                   return true;
                }
            } else {
                return true;
            }
        }
    }

    private void insertDay(){
        DayModel day = new DayModel(activity.getApplication());
        //stores days total into database
        Day newDay = new Day(TimerPrefs.getLongVal(activity, TimerPrefs.KEY_DAILY_TOTAL));
        day.insert(newDay);

    }
}


