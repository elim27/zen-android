package com.MADAPPS.zen.ui.home;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.helpers.TimerPrefs;
import com.MADAPPS.zen.activities.MainActivity;

public class PeaceActivity extends AppCompatActivity {
    private ValueAnimator anim;
    private  String KEY_STREAK = TimerPrefs.KEY_STREAK;

    public PeaceActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peace);

      //  final int streak = HomePreferences.getInt(getApplicationContext(), );
       final TextView value = findViewById(R.id.peace_streak);
        final int streak = TimerPrefs.getIntVal(getApplicationContext(), KEY_STREAK);
        anim = ValueAnimator.ofInt(0, streak);
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
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("ACTIVITY_YES", "y");
                startActivity(mainIntent);
                overridePendingTransition(0, R.anim.fade_out_short);
                finish();
            }
        }, 2500);
    }


    @Override
    public void onBackPressed(){

    }

}
