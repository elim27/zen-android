package com.MADAPPS.zen.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.MADAPPS.zen.R;
import com.MADAPPS.zen.helpers.TimerPrefs;
import com.MADAPPS.zen.ui.more.AudioSelector;



//public class HomeFragment extends Fragment implements View.OnClickListener


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // new keys
    protected static final String KEY_TOTAL_TIME = TimerPrefs.KEY_TOTAL_TIME;
    protected static final String KEY_MILLIS_LEFT = TimerPrefs.KEY_MILLIS_LEFT;
    protected static final String KEY_EXIT_TIME = TimerPrefs.KEY_EXIT_TIME;
    protected static final String KEY_RUNNING = TimerPrefs.KEY_RUNNING;
    protected static final String KEY_MILLIS_ALL_TIME = TimerPrefs.KEY_MILLIS_ALL_TIME;
    protected static final String KEY_DAILY_TASK_COMPLETE = TimerPrefs.KEY_DAILY_TASK_COMPLETE;
    protected static final String KEY_STREAK = TimerPrefs.KEY_STREAK;
    protected static final String KEY_COMPLETIONS = TimerPrefs.KEY_COMPLETIONS;

    //widgets
    protected static Button homeBtn;
    protected static TextView timer;
    protected static TextView title;
    protected static CountDownTimer countDown;
    protected static ProgressBar progress;

    //class variables
    AudioSelector player;
    public static long runtime;
    protected static boolean isReady;
    protected static boolean dailyTaskComplete;
    static boolean isFinished;
    static boolean hasPaused;
    static boolean isRunning;
    protected static boolean isInitialized; //checks if v
    static long millsLeft;
    protected static int tracker;
    static Activity activity;


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = this.getActivity();
        assert activity != null;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = view.findViewById(R.id.zen_header);
        millsLeft = TimerPrefs.getLongVal(activity, KEY_MILLIS_LEFT);
        runtime = TimerPrefs.getLongVal(activity, KEY_TOTAL_TIME); //must get run time from shared preferences in case user changes duration in MoreFragment

        if (runtime == 0) {
            runtime = 10000 * 5;
            TimerPrefs.setVal(activity, KEY_TOTAL_TIME, runtime);
        }
        Log.i("onCreateView", "runtime: " + runtime);
        //assign variables to corresponding widgets
        timer = view.findViewById(R.id.zen_timer);
        progress = view.findViewById(R.id.progressBar);
        title = view.findViewById(R.id.zen_header);
        homeBtn = view.findViewById(R.id.medBtn);
        homeBtn.setOnClickListener(this);

        setMillisLeft();  //ensures clock shows correct time

        Log.i("postMillisLeft", "value: " + millsLeft);
        updateButtons();

        return view;
    }


    @Override
    public void onClick(View v) {
        if(isFinished){
            //TimerViews.restartView();
            isFinished = false;
            isReady = true;
            millsLeft = runtime;
            updateButtons();
        }else if (isReady){
            player = new AudioSelector();
            Log.i("Timer sTarted", "reacehd");
            isFinished = false;
            player.startAudio();
            isRunning = true;
            TimerPrefs.setVal(activity, KEY_RUNNING, true);
            isReady = false;
            updateButtons();
            startTimer();
        }  else {
            Log.i("Timerpaused", "paused");
            isReady = true;
            isRunning = false;
            hasPaused = true;
            TimerPrefs.setVal(activity, KEY_RUNNING, false);
            if(player != null) {
                player.pauseAudio();
            }
            updateButtons();
            stopTimer();
        }
    }

    /**
     * Correctly sets the amount of time left (in milliseconds)
     */
    private void setMillisLeft() {
        if (isRunning) {
            long currTime = System.currentTimeMillis();
            long exitTime = TimerPrefs.getLongVal(activity, KEY_EXIT_TIME);
            millsLeft = millsLeft - (currTime - exitTime);
            startTimer();
        } else if (!isInitialized) {
            millsLeft = TimerPrefs.getLongVal(activity, KEY_TOTAL_TIME);
            isReady = true;
            isInitialized = true;
        } else if (isInitialized && !hasPaused){
            millsLeft = TimerPrefs.getLongVal(activity, KEY_TOTAL_TIME);
        }
        else {
            millsLeft = TimerPrefs.getLongVal(activity, KEY_MILLIS_LEFT);
        }

    }

    /**
     * Ensures the timer's buttons say the correct things
     */
    protected static void updateButtons() {
        if(dailyTaskComplete){
            timer.setTextColor(R.color.finished);
        }
        if (isRunning) {
            homeBtn.setText(R.string.button_pause);
            homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button1));
        } else if (isFinished) {
            homeBtn.setText(R.string.button_finished);
            homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button2));
            timer.setText(R.string.timer_finished);
            startTextAnim();
            timer.setTextColor((R.color.finished));
            title.setTextColor((R.color.finished));
        } else {
            homeBtn.setText(R.string.button_ready);
            homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button));
            progress.setProgressDrawable(activity.getResources().getDrawable(R.drawable.progress_circle));
            isReady = true;
            update();
        }
    }


    /**
     * //     * Stops timer
     * //
     */
    public static void stopTimer() {
        countDown.cancel();
    }


    void startTimer() {
        countDown = new CountDownTimer(millsLeft, 1000) {
            public void onTick(long millisTillFinished) {
                //updates all time record
                long addMe = TimerPrefs.getLongVal(activity, KEY_MILLIS_ALL_TIME);
                addMe = 1000 + addMe;
                TimerPrefs.setVal(activity, KEY_MILLIS_ALL_TIME, addMe);

                addMe = TimerPrefs.getLongVal(activity, TimerPrefs.KEY_DAILY_TOTAL);
                addMe = 1000 + addMe;
                TimerPrefs.setVal(activity, TimerPrefs.KEY_DAILY_TOTAL, addMe);
                tracker++;
                Log.i("onTick", "Tracker: " + tracker);

                millsLeft = millisTillFinished;
                TimerPrefs.setVal(activity, KEY_MILLIS_LEFT, millsLeft);
                update();
            }

            //resets timer on finish
            public void onFinish() {
                finishTimer();
            }
        }.start();
    }


    /**
     * Updates timer text
     */
    public static void update() {
        runtime = TimerPrefs.getLongVal(activity, KEY_TOTAL_TIME);
        String currTimer;
        //   millsLeft = TimerPrefs.getLongVal(activity, KEY_MILLIS_LEFT);

        Log.i("runtime", "" + runtime);

        long newCalcProgress = ((((runtime-millsLeft)/1000)/(runtime/1000)))*100;
        progress.setProgress((int) newCalcProgress);

        int hour = (int) millsLeft / 36000000;
        int min;
        int sec = (int) millsLeft % 60000 / 1000;

        if (millsLeft <= 0) {
            currTimer = "00" + ":" + "00";
            progress.setProgress(100);
        } else {
            min = (int) millsLeft / 60000;
            currTimer = min + ":";
            if (sec < 10) {
                currTimer = currTimer + "0";
            }
            currTimer = currTimer + sec;
        }



        Log.i("timerSetText", currTimer);
        timer.setText(currTimer);
    }


    protected static void startTextAnim() {
        Animation animator1 = AnimationUtils.loadAnimation(activity, R.anim.fade_out_text);
        Animation animator2 = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
        timer.startAnimation(animator1);
        timer.startAnimation(animator2);
    }

    /**
     * Once Fragment is destroyed, the time that is left on the timer must be saved
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        long exitTime = System.currentTimeMillis();
        TimerPrefs.setVal(activity, KEY_EXIT_TIME, exitTime);
        if (countDown != null) {
            stopTimer();
        }

    }


    private void finishTimer() {
        progress.setProgress(0);
        isInitialized = false;
        isFinished = true;
        hasPaused = false;
        isRunning = false;
        TimerPrefs.setVal(activity, KEY_RUNNING, false);
        if(player != null) {
            player.stopAudio();
        }

        TimerPrefs.setVal(activity, KEY_MILLIS_LEFT, runtime);
        tracker = 0;

        // Prefs.setVal(activity, KEY_TOTALCOMP, record);
//        Prefs.setVal(activity, KEY_DONE, true);

        updateButtons();
        //checks if daily task has been completed
        if (!dailyTaskComplete) {
            isInitialized = false;
            dailyTaskComplete = true;
            TimerPrefs.setVal(activity, KEY_DAILY_TASK_COMPLETE, true);
            //increment streak and completion value
            int newValue = TimerPrefs.getIntVal(activity, KEY_COMPLETIONS) + 1;
            TimerPrefs.setVal(activity, KEY_COMPLETIONS, newValue);
            newValue = TimerPrefs.getIntVal(activity, KEY_STREAK) + 1;
            TimerPrefs.setVal(activity, KEY_STREAK, newValue);

            Intent peaceAchievedIntent = new Intent(activity, PeaceActivity.class);
            activity.startActivity(peaceAchievedIntent);
            activity.overridePendingTransition(0, R.anim.fade_out_short);
        }


    }
}