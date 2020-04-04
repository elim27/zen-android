package com.MADAPPS.zen.ui.home;

import android.app.Activity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.Prefs;
import com.MADAPPS.zen.StreakCheck;

import java.util.concurrent.TimeUnit;

public class TimerViews extends HomeFragment {
     private static Activity activity = HomeFragment.activity;






    /**
     *Sets up view of HomeFragment when returning to fragment (since fragment is destroyed upon leaving)
     */
    public static void setUpHome(){

//        //sets progress ring to correct color (if daily task completed, ring set to "finished" color
//        if(Prefs.getBoolVal(activity, KEY_DAILYCOMP)){
//            progress = new android.widget.ProgressBar(activity,null, android.R.attr.progressBarStyle);
//            progress.getProgressDrawable().setColorFilter(R.color.finished, android.graphics.PorterDuff.Mode.MULTIPLY);
//        } else {
//            progress = new android.widget.ProgressBar(activity,null, android.R.attr.progressBarStyle);
//            progress.getProgressDrawable().setColorFilter(R.color.ring, android.graphics.PorterDuff.Mode.MULTIPLY);
//
//        }


        //determines the correct value of millsLeft
        Log.i("setUpHome", "firstStart value: " + firstStart);
        if(!firstStart){
            Log.i("setUpHome()", "first start achieved");
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


        //ensures buttons and background are set to the correct color
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

        //determines what view to select upon user returning to fragment (ie. if timer is still "running", timer should be started)
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
    /**
     * Private helper method to calculate the correct progress of the progress bar (i.e. in the event user spams start/pause)
     */
    private static void updateProgress() {
        Log.i("updateProgress", "reached");
        TOTAL_RUNTIME = Prefs.getLongVal(activity, Prefs.KEY_RUNTIME);
        tracker = Prefs.getIntVal(activity, KEY_TRACKER);

        long calcProgress = (tracker * 100 / (TOTAL_RUNTIME / 1000));
        progress.setProgress((int) calcProgress - 10);

        Prefs.setVal(activity, KEY_TRACKER, tracker);
    }

    /**
     *
     */
    public static void finishView(){
        homeBtn.setText(R.string.button_finished);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button2));
        timer.setText(R.string.timer_finished);
        startTextAnim();
        timer.setTextColor(activity.getResources().getColor(R.color.finished));
        title.setTextColor(activity.getResources().getColor(R.color.finished));
    }

    /**
     * Animates message after daily task is completed
     */
    private static void startTextAnim(){
        Animation animator1 = AnimationUtils.loadAnimation(activity, R.anim.fade_out_text);
        Animation animator2 = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
        timer.startAnimation(animator1);
        timer.startAnimation(animator2);
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
        isReady = true;

        homeBtn.setText(R.string.button_ready);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button));



        diff = System.currentTimeMillis() - clickStart + diff;
        Log.i("Diff", "Value: " + diff);
//        long storeDiff = (long) diff;
        if(startPauseTime < 1000){
            Timer.reCalculateMillsAndTracker();
//            threeStrikes++;
//            if(threeStrikes == 5){
//                Toast toast = Toast.makeText(activity, "Woah there cowboy! Let's take a breather!", Toast.LENGTH_LONG);
//                toast.show();
//
//                timeOut();
//            }
        }

        Prefs.setVal(activity, KEY_DIFF, diff);
        Prefs.setVal(activity, KEY_HASPAUSED, true);
        Prefs.setVal(activity, KEY_RUNNING, false);
        Prefs.setVal(activity, KEY_TRACKER, tracker);

        Timer.stopTimer();


    }

    /**
     * Called when the user is being bad and spamming our pause button!
     */
    private static void timeOut()
    {

        threeStrikes = 0;
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e){
            Log.i("timeOut()", "InterruptedException caught");
        }

    }


    /**
     * Called when timer has finished and
     */
    public static void restartView(){
        homeBtn.setText(R.string.button_ready);
        homeBtn.setBackground(activity.getResources().getDrawable(R.drawable.timer_button));
        progress.setProgress(0);
        firstStart = false;
        tracker = 0;
        isReady = true;

        Prefs.setVal(activity, KEY_DONE, false);
        Prefs.setVal(activity, KEY_FIRSTSETUP, true);
        update();


    }


}
