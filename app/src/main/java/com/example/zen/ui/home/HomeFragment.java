package com.example.zen.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zen.Preferences;
import com.example.zen.PeaceActivity;
import com.example.zen.R;


/**
 * A simple {@link Fragment} subclass.
 */
//public class HomeFragment extends Fragment implements View.OnClickListener


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static SharedPreferences zenPrefs;
    private Button medBtn;
    private boolean firstClick;
    private boolean isFinished;
    private boolean firstPause;
    private static final String ZEN_PREF = "zen";

    //keys for preferences in Zen class
    private final String KEY_TIMER = "time";
    private final String KEY_FIRSTSETUP = "start";
    private final String KEY_CLICKSTART = " oof";
    private final String KEY_RUNNING = "run";
    private final String KEY_TRACKER = "progress";
    private final String KEY_DIFF = "diff";
    private final String KEY_PAUSED = "pause";
    public final String KEY_STREAK = "fire";

    public static final String KEY_TOTALRECORD = "rec";
    public static final String KEY_TOTALCOMP = "comp";


    ///timer
    private TextView timer;
    public CountDownTimer countDown;
    private long millsLeft;
    private final long TOTAL_RUNTIME = 10000;
    private ProgressBar progress;
    private int tracker;

    public HomeFragment() {
        // Required empty public constructor
        firstPause = true;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(countDown != null) {
            stopTimer();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = view.findViewById(R.id.zen_title);

        timer = (TextView) view.findViewById(R.id.zen_timer);

        //checks if a current timer is running or not
        //-1 signifies no current timer is running and millsLeft must be initializes
        //if the value is not -1, signifies there is a current timer running and millsLeft should be assigned to that stored value.
        boolean isRunning = Preferences.getBoolVal(getContext(), KEY_RUNNING);
        boolean firstStart = Preferences.getBoolVal(getContext(), KEY_FIRSTSETUP);
//
        if(!firstStart){
            millsLeft = TOTAL_RUNTIME;
            Preferences.setVal(getContext(), KEY_FIRSTSETUP, true);
            Preferences.setVal(getContext(), KEY_TIMER, millsLeft);
        }else {
            millsLeft = Preferences.getLongVal(getContext(), KEY_TIMER);
            tracker = Preferences.getIntVal(getContext(), KEY_TRACKER);
            Log.i("millsLeft", "Value: " + millsLeft);
            if(isRunning) {
                long currTime = System.currentTimeMillis();
                long clickStartTime = Preferences.getLongVal(getContext(), KEY_CLICKSTART);
                long diff = (currTime - clickStartTime);
                int holder = (int) diff;
                tracker = holder/1000;
                if(Preferences.getBoolVal(getContext(), KEY_PAUSED)) {
                    diff = diff + Preferences.getLongVal(getContext(), KEY_DIFF);
                    tracker =  (int) diff/1000;
                }
                // setMode(getContext(), "KEY_TRACKER", tracker);
                millsLeft = TOTAL_RUNTIME - diff;
                //we want to store the total amount of time elapsed between clicks (SEE ONCLICK PAUSE)
              Preferences.setVal(getContext(), KEY_TIMER, millsLeft);
              Preferences.setVal(getContext(), KEY_TRACKER, diff);



                //update tracker
                //tracker =
                Log.i("currTime", "Value: " + currTime/1000);
                Log.i("clickStartTime", "Value: " + clickStartTime/1000);
                Log.i("millsLeftDifference", "Value: " + diff/1000);
                Log.i("millsLeftRun", "Value: " + millsLeft);

            }
        }
        progress = (ProgressBar) view.findViewById(R.id.progressBar);



        //reupdate button view based on zenPrefs
        medBtn = (Button) view.findViewById(R.id.medBtn);
        if(isRunning && firstStart){
            medBtn.setText(R.string.button_pause);
            medBtn.setBackground(getResources().getDrawable(R.drawable.timer_button1));
        } else if (!isRunning && !firstStart && millsLeft != TOTAL_RUNTIME){
            medBtn.setText("more zen");
        } else {
            medBtn.setText(R.string.button_ready);
            medBtn.setBackground(getResources().getDrawable(R.drawable.timer_button));
            progress.setProgressDrawable(getResources().getDrawable(R.drawable.progress_circle));
            firstClick = true;
        }
        medBtn.setOnClickListener(this);

        if(isRunning) {
            startTimer();
        } else {
            update();
        }
        return view;
    }

    private void calculateMillsLeft(){

    }

    private void resetView(){

    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v){
        //if button is clicked after countdown timer is finished
        //reset timer
        if(isFinished){
            Preferences.setVal(getContext(), KEY_FIRSTSETUP, true);
            medBtn.setText(R.string.button_ready);
            medBtn.setBackground(getResources().getDrawable(R.drawable.timer_button));
            timer.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            progress.setProgress(0);
            tracker = 0;
            update();
            isFinished = false;
        }else if (firstClick){
            progress.setProgress(0);
            medBtn.setText(R.string.button_pause);
            medBtn.setBackground(getResources().getDrawable(R.drawable.timer_button1));
            long startTime = System.currentTimeMillis();
            Preferences.setVal(getContext(), KEY_CLICKSTART, startTime);
            Preferences.setVal(getContext(), KEY_RUNNING, true);
            startTimer();
            firstClick=false;
            //HomePreferences.setVal(getContext(), KEY_PAUSED, false);
        }  else {
            medBtn.setText(R.string.button_ready);
            medBtn.setBackground(getResources().getDrawable(R.drawable.timer_button));
            Preferences.setVal(getContext(), KEY_RUNNING, false);
            Preferences.setVal(getContext(), KEY_TRACKER, tracker);
            long clickStart = Preferences.getLongVal(getContext(), KEY_CLICKSTART);
            long diff = (Preferences.getLongVal(getContext(), KEY_DIFF));
            diff = System.currentTimeMillis() - clickStart + diff;
            Log.i("Diff", "Value: " + diff);
            long storeDiff = (long) diff;
            Preferences.setVal(getContext(), KEY_DIFF, storeDiff);
            firstClick = true;
            Preferences.setVal(getContext(), KEY_PAUSED, true);
            stopTimer();

        }
    }


    /**
     *
     */
    private void startTimer(){
        progress.setProgress(tracker);
        countDown =  new CountDownTimer(millsLeft, 1000){
            public void onTick(long millisTillFinished){
              //  long allTimeRunTime = HomePreferences.getLongVal(getContext(), KEY_TOTALRECORD);
               // allTimeRunTime = TOTAL_RUNTIME - millsLeft + allTimeRunTime;
               // HomePreferences.setVal(getContext(), KEY_TOTALRECORD, allTimeRunTime);
                Log.i("onTick", "reached");
                tracker++;
                Log.i("onTick", "Tracker: " + tracker);
                millsLeft = millisTillFinished;
                Preferences.setVal(getContext(), KEY_TIMER, millsLeft);
                Preferences.setVal(getContext(), KEY_TRACKER, tracker);
                update();
            }
            //resets timer on finish
            public void onFinish(){
                finishTimer();
            }
        }.start();
    }



    private void finishTimer(){
        firstClick = true;
        isFinished = true;
        millsLeft = TOTAL_RUNTIME;
        int record = Preferences.getIntVal(getContext(), KEY_TOTALCOMP) + 1;
        Preferences.setVal(getContext(), KEY_PAUSED, true);
        Preferences.setVal(getContext(), KEY_TIMER, millsLeft);
        Preferences.setVal(getContext(), KEY_DIFF,  (long) 0);
        Preferences.setVal(getContext(), KEY_TRACKER, (int) 0);
        Preferences.setVal(getContext(), KEY_FIRSTSETUP, false);
        Preferences.setVal(getContext(), KEY_RUNNING, false);
        Preferences.setVal(getContext(), KEY_TOTALCOMP, record);

        progress.setProgress(0);

        Intent peaceAchievedIntent = new Intent(getContext(), PeaceActivity.class);
        startActivity(peaceAchievedIntent);

    }
    /**
     *Stops the timer
     */
    public void stopTimer(){
        countDown.cancel();
    }


    /**
     * Updates timer text
     */
    public void update(){
        String currTimer;
        millsLeft = Preferences.getLongVal(getContext(), KEY_TIMER);
        int min = (int) millsLeft / 60000;
        int sec = (int) millsLeft % 60000 / 1000;
        currTimer = min + ":";

        if(sec < 10){
            currTimer = currTimer + "0";
        }
        currTimer = currTimer + sec;
        if(Preferences.getBoolVal(getContext(), KEY_RUNNING)) {
            tracker = Preferences.getIntVal(getContext(), KEY_TRACKER);
        }
        long calcProgress = (tracker*100/(TOTAL_RUNTIME /1000));
        progress.setProgress((int) calcProgress);
        timer.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        timer.setText(currTimer);
    }





}

