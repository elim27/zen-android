package com.MADAPPS.zen.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.Database.StreakCheck;


public class StartupActivity extends AppCompatActivity {

    public static Activity activity;
    public static Context startContext;
    public static TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        activity = this;
        startContext = getApplicationContext();
        timer = (TextView) findViewById(R.id.zen_timer);


        boolean check = StreakCheck.checkDailyStreak(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(check){
                    startMain();
                } else {
                    startDeadStreak();
                }

                finish();
            }
        }, 1750);


    }



    private void sendMe(){


    }
    /**
     * Starts MainActivity
     */
    private void startMain(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out_short);
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





