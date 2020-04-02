package com.MADAPPS.zen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class StartupActivity extends AppCompatActivity {


    private static int curr;
    private static Calendar calendar;

    private static final String KEY_CURRDAY = Preferences.KEY_CURRDAY; //key where our currDate is stored
    private static final String KEY_DAILYCOMP = Preferences.KEY_DAILYCOMP;
    private static final String KEY_STREAK = Preferences.KEY_STREAK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView timer = (TextView) findViewById(R.id.zen_timer);
        TimeZone t = TimeZone.getDefault();

        if (t == null) {
            t = TimeZone.getTimeZone("CST");
        }
        calendar = Calendar.getInstance(t);

        //checks if this is the first time starting up
        int firstTime = Preferences.getIntVal(getApplicationContext(), KEY_CURRDAY);

        if (firstTime == 0) {
            curr = calendar.get(Calendar.MINUTE);
            Preferences.setVal(getApplicationContext(), KEY_CURRDAY, curr);
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            int test = calendar.get(Calendar.MINUTE);
            boolean comp = Preferences.getBoolVal(getApplicationContext(), KEY_DAILYCOMP);
            curr = Preferences.getIntVal(getApplicationContext(), KEY_CURRDAY);
            if (test != curr) {
                curr = test;
                Preferences.setVal(getApplicationContext(), KEY_CURRDAY, curr);
                long total = Preferences.getLongVal(getApplicationContext(), Preferences.KEY_TOTALTIME);
                Preferences.setVal(getApplicationContext(), Preferences.KEY_MILLIS_LEFT, total);

                if(!comp && Preferences.getIntVal(getApplicationContext(), KEY_STREAK) != 0){
                    //DeadStreakActivity
                    Log.i("COMP FALSE", "REACHED");
                    Intent deadIntent = new Intent(getApplicationContext(), DeadStreakActivity.class);
                    startActivity(deadIntent);
                    finish();
                } else {
                    Preferences.setVal(getApplicationContext(), KEY_DAILYCOMP, false);
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            } else {
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }
    }


}





