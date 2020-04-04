package com.MADAPPS.zen.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;

import com.MADAPPS.zen.R;
import com.MADAPPS.zen.activities.PeaceActivity;
import com.MADAPPS.zen.Prefs;

public class Timer extends HomeFragment {
    private static Activity activity = HomeFragment.activity;




    /**
     *Sets the run time of the timer
     */
    protected static void setRunTime(){
        long TOTAL_RUNTIME = Prefs.getLongVal(activity, Prefs.KEY_RUNTIME);

        if(TOTAL_RUNTIME == 0){
            TOTAL_RUNTIME = 5*60000;
            Prefs.setVal(activity, Prefs.KEY_RUNTIME, TOTAL_RUNTIME);
        }

    }
    /**
     *Starts the timer
     */
    public static void startTimer(){
        progress.setProgress(tracker);
        countDown =  new CountDownTimer(millsLeft, 1000){
            public void onTick(long millisTillFinished){
                long allTimeRunTime = Prefs.getLongVal(activity, KEY_TOTALRECORD);
                allTimeRunTime = 1000 + allTimeRunTime;
                Prefs.setVal(activity, KEY_TOTALRECORD, allTimeRunTime);
                Log.i("onTick", "reached");
                tracker++;
                Log.i("onTick", "Tracker: " + tracker);
                millsLeft = millisTillFinished;
                Prefs.setVal(activity, KEY_TIMER, millsLeft);
                Prefs.setVal(activity, KEY_TRACKER, tracker);
                update();
            }
            //resets timer on finish
            public void onFinish(){
               finishTimer();
            }
        }.start();
    }

    /**
     * Stops timer
     */
    public static void stopTimer(){
        countDown.cancel();
    }


    /**
     * Once timer is finished, this is the state timer should return too.
     * @return
     */
    public static void finishTimer() {
        boolean daily = Prefs.getBoolVal(activity, KEY_DAILYCOMP);
        progress.setProgress(0);
        firstStart = false;
        long millsLeft = Prefs.getLongVal(activity, Prefs.KEY_RUNTIME);   //assign millsLeft to total time
        int record = Prefs.getIntVal(activity, KEY_TOTALCOMP) + 1; //increase all time record

        Prefs.setVal(activity, KEY_HASPAUSED, false);   //paused is true because application
        Prefs.setVal(activity, KEY_TIMER, millsLeft);
        Prefs.setVal(activity, KEY_DIFF, (long) 0);
        Prefs.setVal(activity, KEY_TRACKER, (int) 0);
        Prefs.setVal(activity, KEY_FIRSTSETUP, false);
        Prefs.setVal(activity, KEY_RUNNING, false);
        Prefs.setVal(activity, KEY_TOTALCOMP, record);
        Prefs.setVal(activity, KEY_DONE, true);

        //checks if daily task has been completed
        if (!daily) {
            int streak = Prefs.getIntVal(activity, KEY_STREAK) + 1;
            Prefs.setVal(activity, KEY_STREAK, streak);
            Prefs.setVal(activity, KEY_DAILYCOMP, true);
//            progress = new android.widget.ProgressBar(activity,null, android.R.attr.progressBarStyle);
//            progress.getProgressDrawable().setColorFilter(R.color.finished, android.graphics.PorterDuff.Mode.MULTIPLY);
            Intent peaceAchievedIntent = new Intent(activity, PeaceActivity.class);
            activity.startActivity(peaceAchievedIntent);
            activity.overridePendingTransition(0, R.anim.fade_out_short);
        } else {
            TimerViews.finishView(); //default finish view
        }
    }

    /**
     *Recalculates how many milliseconds are left on the timer and the correct value of the tracker.
     */
    public static void reCalculateMillsAndTracker(){
        Log.i("reCalculateMillsAndTracker", "REACHED);");
        long currTime = System.currentTimeMillis();
        long clickStartTime = Prefs.getLongVal(activity, KEY_CLICKSTART);

        TOTAL_RUNTIME = Prefs.getLongVal(activity, Prefs.KEY_RUNTIME);

        long diff = (currTime - clickStartTime);
        int holder = (int) diff;
        //progress.setProgress(0);
        tracker = holder/1000;

        if(Prefs.getBoolVal(activity, KEY_HASPAUSED)) {
            diff = diff + Prefs.getLongVal(activity, KEY_DIFF);
            tracker =  (int) diff/1000;
        }
        // setMode(context, "KEY_TRACKER", tracker);
        millsLeft = TOTAL_RUNTIME - diff;
        Log.i("reCalculatedMillsLeft", "millsLeft VALUE: " + millsLeft);
        //we want to store the total amount of time elapsed between clicks (SEE ONCLICK PAUSE)
        Prefs.setVal(activity, KEY_TIMER, millsLeft);
        Prefs.setVal(activity, KEY_TRACKER, (int) diff);

        //update tracker
        //tracker =
        Log.i("currTime", "Value: " + currTime/1000);
        Log.i("clickStartTime", "Value: " + clickStartTime/1000);
        Log.i("millsLeftDifference", "Value: " + diff/1000);
        Log.i("millsLeftRun", "Value: " + millsLeft);

    }





}
