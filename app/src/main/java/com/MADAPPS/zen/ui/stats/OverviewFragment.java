package com.MADAPPS.zen.ui.stats;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.helpers.TimerPrefs;

public class OverviewFragment extends Fragment {
    private ValueAnimator anim;

    private long allTime;
    private int streak;
    private int completions;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        Activity activity = this.getActivity();
        allTime = TimerPrefs.getLongVal(activity, TimerPrefs.KEY_MILLIS_ALL_TIME);
        streak = TimerPrefs.getIntVal(activity, TimerPrefs.KEY_STREAK);
        completions = TimerPrefs.getIntVal(activity, TimerPrefs.KEY_COMPLETIONS);

        setUpHome(view);
        return view;
    }



    /**
     * Sets up home page
     * @param view
     */
    private void setUpHome(View view) {
        int totalMin;
        //sets total days text view value
        TextView value =  view.findViewById(R.id.counter3);
        startAnimateInt(completions, value, "x");

        value = view.findViewById(R.id.counter1);
        totalMin = (int) (allTime/60000);
        startAnimate(totalMin, value, "minute");

        value =  view.findViewById(R.id.counter2);
        startAnimate(streak, value, "day");
    }

    /**
     * This animates a specified TextView (num counting)
     */
    private void startAnimateInt(int limit, final TextView text, final String symbol){
        anim = ValueAnimator.ofInt(0, limit);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            public void onAnimationUpdate(ValueAnimator animation) {
                text.setText(animation.getAnimatedValue().toString() + symbol);
            }
        });
        anim.start();
    }

    /**
     * This animates a specified TextView but also appends the word "day(s)"
     * @param limit
     * @param text
     */
    private void startAnimate(int limit, final TextView text, String time){
        final String rightTime;
        if(limit != 1){
            rightTime = time+"s";
        } else {
            rightTime = time;
        }
        anim = ValueAnimator.ofInt(0, limit);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            public void onAnimationUpdate(ValueAnimator animation) {
                text.setText(animation.getAnimatedValue().toString() + " " + rightTime);
            }
        });
        anim.start();
    }
}
