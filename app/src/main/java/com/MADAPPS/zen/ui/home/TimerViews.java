package com.MADAPPS.zen.ui.home;

import android.app.Activity;
import android.util.Log;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.Prefs;

public class TimerViews extends HomeFragment {
     private static Activity activity = HomeFragment.activity;

    /**
     * Private helper method to calculate the correct progress of the progress bar (i.e. in the event user spams start/pause)
     */
    private static void updateProgress(){
        long TOTAL_RUNTIME = Prefs.getLongVal(activity, Prefs.KEY_RUNTIME);
        long calcProgress = (tracker*100/(TOTAL_RUNTIME /1000));
        progress.setProgress((int) calcProgress);

    }

    /**
     *
     */
    public static void finishView(){
        homeBtn.setText(R.string.button_finished);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button2));
        timer.setText(R.string.timer_finished);
        timer.setTextColor(activity.getResources().getColor(R.color.finished));
        title.setText(R.string.home_headerFinish);
        title.setTextColor(activity.getResources().getColor(R.color.finished));
    }


    /**
     *
     */
    public static void pausedView(){
        long startTime = System.currentTimeMillis();

        isReady =false;

        progress.setProgress(0);
        homeBtn.setText(R.string.button_pause);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button1));

        Prefs.setVal(activity,KEY_CLICKSTART, startTime);
        Prefs.setVal(activity, KEY_RUNNING, true);
        Timer.startTimer();
    }

    /**
     * The view when the timer is unpaused
     */
    public static void unpausedView(){
        long clickStart = Prefs.getLongVal(activity, KEY_CLICKSTART);
        long diff = (Prefs.getLongVal(activity, KEY_DIFF));
        long startPauseTime = System.currentTimeMillis() - clickStart;

        if(startPauseTime < 1000){
            Timer.reCalculateMillsAndTracker();
        }
        diff = System.currentTimeMillis() - clickStart + diff;
        Log.i("Diff", "Value: " + diff);
//        long storeDiff = (long) diff;

        isReady = true;

        homeBtn.setText(R.string.button_ready);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button));

        Prefs.setVal(activity, KEY_DIFF, diff);
        Prefs.setVal(activity, KEY_PAUSED, true);
        Prefs.setVal(activity, KEY_RUNNING, false);
        Prefs.setVal(activity, KEY_TRACKER, tracker);
        Timer.stopTimer();

    }


    /**
     * Called when timer has finished and
     */
    public static void restartView(){
        homeBtn.setText(R.string.button_ready);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button));
        progress.setProgress(0);

        tracker = 0;

        Prefs.setVal(activity, KEY_DONE, false);
        Prefs.setVal(activity, KEY_FIRSTSETUP, true);
        update();


    }


    /**
     *Sets up view of HomeFragment when returning to fragment (since fragment is destroyed upon leaving)
     */
    public static void setUpHome(){
        if(!firstStart){
            millsLeft = TOTAL_RUNTIME;
            isReady = true;
            Prefs.setVal(activity, KEY_FIRSTSETUP, true);
            Prefs.setVal(activity, KEY_TIMER, millsLeft);
        }else {
            millsLeft = Prefs.getLongVal(activity, KEY_TIMER);
            tracker = Prefs.getIntVal(activity, KEY_TRACKER);
            Log.i("millsLeft", "Value: " + millsLeft);
            updateProgress();
            if(isRunning) {
                Timer.reCalculateMillsAndTracker();
            }
        }

        if(isRunning && firstStart){
            homeBtn.setText(R.string.button_pause);
            homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button1));
        } else if (!isRunning && !firstStart && millsLeft != TOTAL_RUNTIME){
            homeBtn.setText(R.string.button_finished);
        } else {
            homeBtn.setText(R.string.button_ready);
            homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button));
            progress.setProgressDrawable(activity.getResources().getDrawable(R.drawable.progress_circle));
            isReady = true;
        }
        //determines what view to seelct
        if(isRunning) {
            Timer.startTimer();
        } else if (isFinished && daily) {
            finishView();
        } else if (!isFinished && daily){
            update();
        } else {
            update();
        }
    }
}
