package com.MADAPPS.zen.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.Prefs;
import com.MADAPPS.zen.StreakCheck;

import java.util.Calendar;

public class StartupActivity extends AppCompatActivity {


    private static int curr;
    private static Calendar calendar;

    private static final String KEY_CURRDAY = Prefs.KEY_CURRDAY; //key where our currDate is stored
    private static final String KEY_DAILYCOMP = Prefs.KEY_DAILYCOMP;
    private static final String KEY_STREAK = Prefs.KEY_STREAK;

    public static Activity activity;
    public static Context startContext;
    public static TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        startContext = getApplicationContext();
        timer = (TextView) findViewById(R.id.zen_timer);
        boolean check = StreakCheck.checkDailyStreak();

        if(check){
            startMain();
        } else {
            startDeadStreak();
        }


    }

    /**
     * Starts MainActivity
     */
    private void startMain(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    /**
     * Starts DeadStreakActivity
     */
   private void startDeadStreak(){
       Intent deadStreakIntent = new Intent(this, DeadStreakActivity.class);
       startActivity(deadStreakIntent);
       finish();
    }


}





