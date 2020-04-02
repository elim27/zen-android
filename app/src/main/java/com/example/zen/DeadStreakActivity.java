package com.example.zen;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DeadStreakActivity extends AppCompatActivity {
    ValueAnimator anim;
    private String KEY_STREAK = Preferences.KEY_STREAK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadstreak);

        Log.i("DEADSTREAKACTIVITY", "REACHED");

        final TextView value = (TextView) findViewById(R.id.dead_streak);
        final int streak = Preferences.getIntVal(getApplicationContext(), KEY_STREAK);
        Preferences.setVal(getApplicationContext(), Preferences.KEY_STREAK, 0);

        anim = ValueAnimator.ofInt(streak, 0);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            public void onAnimationUpdate(ValueAnimator animation) {
                String day = "day";
                if(streak != 1){
                    day = "days";
                }
                value.setText(animation.getAnimatedValue().toString() + " " + day);
            }
        });
        anim.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("DEAD STREAK", "REACHED");
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2500);
    }


    @Override
    public void onBackPressed(){

    }
}
