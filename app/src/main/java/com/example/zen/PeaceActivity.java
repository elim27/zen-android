package com.example.zen;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PeaceActivity extends AppCompatActivity {
    public PeaceActivity() {

    }
    private ValueAnimator anim;
   private  String KEY_STREAK = Preferences.KEY_STREAK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peace);

      //  final int streak = HomePreferences.getInt(getApplicationContext(), );
       final TextView value = (TextView) findViewById(R.id.peace_streak);
        final int streak = Preferences.getIntVal(getApplicationContext(), KEY_STREAK);
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
                finish();
            }
        }, 2500);
    }


    @Override
    public void onBackPressed(){

    }

}
