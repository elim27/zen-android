package com.example.zen.ui.stats;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.zen.R;
import com.example.zen.Preferences;

public class StatsFragment extends Fragment {
    private ValueAnimator anim;
    private StatsViewModel statsViewModel;
    private final String KEY_TOTALRECORD = "rec";
    private final String KEY_TOTALCOMP = "comp";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                ViewModelProviders.of(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        final TextView textView = root.findViewById(R.id.stats_title);
        statsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        setUpHome(root);
        return root;
    }


    /**
     * Sets up home page
     * @param view
     */
    private void setUpHome(View view) {
        int count;
        int totalMin;

        count = Preferences.getIntVal(getContext(), KEY_TOTALCOMP);

        //sets total days text view value
        TextView value = (TextView) view.findViewById(R.id.statsT_counter3);
        startAnimateInt(count, value, "x");

        value = (TextView) view.findViewById(R.id.statsT_counter1);
        totalMin = (int) Math.ceil(Preferences.getIntVal(getContext(), KEY_TOTALRECORD)/60);
        startAnimate(totalMin, value, "minute");
//
//        //determines current streak of self isolation
//        count = DailyActivity.(getContext(), DailyActivity.getStreakKey());
//
//        value = (TextView) view.findViewById(R.id.text_homeStreak);
//        startAnimate(count, value);

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
     * This animates a specified TextView (num counting)
     */
    private void startAnimateDouble(int limit, final TextView text){
        anim = ValueAnimator.ofInt(0, limit);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            public void onAnimationUpdate(ValueAnimator animation) {

                text.setText(animation.getAnimatedValue().toString() + "x");
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
            rightTime = time;
        } else {
            rightTime = time+"s";
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
