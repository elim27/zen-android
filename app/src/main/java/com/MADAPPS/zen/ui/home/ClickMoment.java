package com.MADAPPS.zen.ui.home;

import android.util.Log;
import android.view.View;

import com.MADAPPS.zen.Prefs;

public class ClickMoment extends HomeFragment  implements View.OnClickListener  {


    public static void asdf(){}

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



}
