package com.MADAPPS.zen.ui.home;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.MADAPPS.zen.helpers.Prefs;
import com.MADAPPS.zen.R;
import com.MADAPPS.zen.helpers.TimerPrefs;
import com.MADAPPS.zen.ui.more.MoreFragment;

public class TotalTimeSelector extends MoreFragment implements AdapterView.OnItemSelectedListener {

        private static final String KEY_TOTAL_TIME = TimerPrefs.KEY_TOTAL_TIME;
        private static final String KEY_MILLIS_LEFT = TimerPrefs.KEY_MILLIS_LEFT;


    /**
     * Constructor
      */
    public TotalTimeSelector(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(moreActivity,
                R.array.spinner_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(moreActivity,
                R.array.music_choices, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        Log.i("selector Constructor", "durationPos: " + durationPos + "audiopos: " + audioPos);


        spinner.setSelection(durationPos);
        spinner2.setSelection(audioPos);

    }

    /***
     *
     * Determines what to do when user selects certain item on spinner.
     *
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.more_spinner) {
            long newRunTimeVal = determineStoreVal(parent, position);
            Log.i("onItemSelected", "Value: " + newRunTimeVal);
           TimerPrefs.setVal(moreActivity, KEY_TOTAL_TIME, newRunTimeVal);
            durationPos = position;
            Prefs.setVal(moreActivity, KEY_SELECTOR_POS, durationPos);
        } else if (parent.getId() == R.id.more_spinner2) {
            audioPos = position;
            Prefs.setVal(moreActivity, Prefs.KEY_AUDIO_POS, audioPos);
        }

    }

    /**
     * Determines what to do when nothing is selected
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        long total = TimerPrefs.getLongVal(moreActivity, KEY_TOTAL_TIME);
        TimerPrefs.setVal(moreActivity, KEY_TOTAL_TIME, total);
    }
    /**
     * Determines what value to be stored based on user selection
     *
     *
     */
    private long determineStoreVal(AdapterView<?> parent, int position) {
        String[] placeHolder = new String[3];
        int value = 5;
        long storedValue;
        String pous = parent.getItemAtPosition(position).toString();
        placeHolder = pous.split(" ");
        try {
            value = Integer.parseInt(placeHolder[0]);
            Log.i("Item Selected Value: ", "" + value);
        } catch (NumberFormatException e) {
            Log.i("NumberFormatException", "see MoreFragment.java, onItemSelected()");
            e.printStackTrace();
        }
        if (placeHolder[1].contains("Hour")) {
            Log.i("HOUR", "reach");
            storedValue = (long) value * 3600000;
        } else {
            storedValue = (long) value * 1000;
        }

        //correctly determiens millsLeft if user makes a change to run time.
        if(TimerPrefs.getBoolVal(moreActivity, TimerPrefs.KEY_RUNNING)|| HomeFragment.hasPaused){
            long left = TimerPrefs.getLongVal(moreActivity, KEY_TOTAL_TIME) - TimerPrefs.getLongVal(moreActivity, KEY_MILLIS_LEFT);
            left = storedValue - left;
            TimerPrefs.setVal(moreActivity, KEY_MILLIS_LEFT, left);
        }
        return storedValue;

    }
}