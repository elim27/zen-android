package com.MADAPPS.zen.ui.home;

import android.app.Activity;
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
import com.MADAPPS.zen.R;
import com.MADAPPS.zen.Prefs;


/**
 * A simple {@link Fragment} subclass.
 */
//public class HomeFragment extends Fragment implements View.OnClickListener


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    static Activity activity;
    //keys for Preferences in Zen class
    protected static final String KEY_TIMER = Prefs.KEY_MILLIS_LEFT;
    protected static final String KEY_FIRSTSETUP = Prefs.KEY_FIRSTSETUP;
    protected static final String KEY_CLICKSTART = Prefs.KEY_CLICKSTARTTIME;
    protected static final String KEY_RUNNING = Prefs.KEY_RUNNING;
    protected static final String KEY_TRACKER = Prefs.KEY_TRACKER;
    protected static final String KEY_DIFF = Prefs.KEY_STARTPAUSEDIFF;
    protected static final String KEY_HASPAUSED = Prefs.KEY_HASPAUSED;
    protected static final String KEY_DONE = Prefs.KEY_DONE;
    protected static final String KEY_STREAK = Prefs.KEY_STREAK;
    protected static final String KEY_TOTALRECORD = Prefs.KEY_TOTALRECORD;
    protected static final String KEY_DAILYCOMP = Prefs.KEY_DAILYCOMP;
    protected static String KEY_TOTALTIME = Prefs.KEY_RUNTIME;
    protected static final String KEY_TOTALCOMP = Prefs.KEY_TOTALCOMP;
    //widgets
    protected static Button homeBtn;
    protected static TextView timer;
    protected static TextView title;
    protected static CountDownTimer countDown;
    protected static ProgressBar progress;
    //class variables
    protected static boolean isReady;
    protected static boolean daily;
    protected static boolean isFinished;
    protected static boolean isRunning;
    protected static boolean firstStart;
    protected static long millsLeft;
    protected static long TOTAL_RUNTIME;
    protected static int tracker;
    protected static int threeStrikes;


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = this.getActivity();
        assert activity != null;

        daily = Prefs.getBoolVal(activity, KEY_DAILYCOMP);
        isFinished = Prefs.getBoolVal(activity, KEY_DONE);
        isRunning = Prefs.getBoolVal(activity, KEY_RUNNING);
        firstStart = Prefs.getBoolVal(activity, KEY_FIRSTSETUP);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = view.findViewById(R.id.zen_header);

        //assign static variables to corresponding widgets
        timer = (TextView) view.findViewById(R.id.zen_timer);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        title = view.findViewById(R.id.zen_header);
        homeBtn = (Button) view.findViewById(R.id.medBtn);
        homeBtn.setOnClickListener(this);


        //sets up runtime of timer
        Timer.setRunTime();
        TOTAL_RUNTIME = Prefs.getLongVal(activity, KEY_TOTALTIME);

        TimerViews.setUpHome();

        return view;
    }


    /**
     *Saves certain Preferences based on click/click order
     * @param v
     */
    @Override
    public void onClick(View v){
        //if button is clicked after countdown timer is finished
        //reset timer

        boolean isFinished = Prefs.getBoolVal(activity, KEY_DONE);
        Log.i("isFinished", "Value: " + isFinished);
        if(isFinished){
           TimerViews.restartView();
        }else if (isReady){
           TimerViews.pausedView();
        }  else {
            TimerViews.unpausedView();;
        }

    }


    /**
     * Updates timer text
     */
    public static void update(){
        boolean daily = Prefs.getBoolVal(activity, KEY_DAILYCOMP);
        TOTAL_RUNTIME = Prefs.getLongVal(activity, KEY_TOTALTIME);
        String currTimer;
        millsLeft = Prefs.getLongVal(activity, KEY_TIMER);
        int hour = (int) millsLeft / 36000000;
        int min;
        int sec = (int) millsLeft % 60000 / 1000;

        if(millsLeft < 0){
            currTimer = "0" + ":" + "00";
            progress.setProgress(100);
        }
        else if(hour != 0){
            min = (int) millsLeft / 600000;
            currTimer = hour + ":" + min + ":" + sec;
        } else {
            min = (int) millsLeft / 60000;
            currTimer = min + ":";
            if (sec < 10) {
                currTimer = currTimer + "0";
            }
            currTimer = currTimer + sec;

        }
        if(Prefs.getBoolVal(activity, KEY_RUNNING)) {
            tracker = Prefs.getIntVal(activity, KEY_TRACKER);
        }
        long calcProgress = (tracker*100/(TOTAL_RUNTIME /1000));
        progress.setProgress((int) calcProgress);

        if(daily){
            timer.setTextColor(activity.getResources().getColor(R.color.finished));
        }
        timer.setText(currTimer);
    }



    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(countDown != null) {
            Timer.stopTimer();
        }
    }





}