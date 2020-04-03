package com.MADAPPS.zen.ui.more;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.MADAPPS.zen.Prefs;
import com.MADAPPS.zen.R;

public class MoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MoreViewModel moreViewModel;
    private String KEY_TOTALTIME = Prefs.KEY_RUNTIME;
    private String KEY_SELECTORPOS = Prefs.KEY_SELECTORPOS;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moreViewModel =
                ViewModelProviders.of(this).get(MoreViewModel.class);
        View view = inflater.inflate(R.layout.fragment_more, container, false);


        Spinner spinner = (Spinner) view.findViewById(R.id.more_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinner_choices, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int pos = Prefs.getIntVal(getActivity(), KEY_SELECTORPOS);
        spinner.setSelection(pos);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        long valueToStore = determineStoreVal(parent, position);

        Prefs.setVal(getActivity(), KEY_TOTALTIME, valueToStore);
        Prefs.setVal(getActivity(), KEY_SELECTORPOS, position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        long total = Prefs.getLongVal(getActivity(), KEY_TOTALTIME);
        Prefs.setVal(getActivity(), KEY_TOTALTIME, (long) total);

    }

    /**
     * Determines what value to be stored based on user selection
     * @param parent
     * @param position
     * @return
     */
    private long determineStoreVal(AdapterView<?> parent,  int position){
        String [] placeHolder = new String[10];
        int value = 5;
        long storedValue;
        String pous = parent.getItemAtPosition(position).toString();
        placeHolder = pous.split(" ");
        try {
            value = Integer.parseInt(placeHolder[0]);
            Log.i("Item Selected Value: ", "" + value);
        }catch (NumberFormatException e){
            Log.i("NumberFormatException", "see MoreFragment.java, onItemSelected()");
            e.printStackTrace();
        }

        if(placeHolder[1].contains("Hour")){
            Log.i("HOUR", "reach");
            storedValue = (long) value*3600000;
        } else {
            storedValue = (long) value*1000;
        }

        //Must update MillsLeft when timer is paused or finished
        if(!Prefs.getBoolVal(getActivity(), Prefs.KEY_DONE) || Prefs.getBoolVal(getActivity(), Prefs.KEY_HASPAUSED)) {
            long left = Prefs.getLongVal(getActivity(), Prefs.KEY_RUNTIME) - Prefs.getLongVal(getActivity(), Prefs.KEY_MILLIS_LEFT);
            left = storedValue - left;
            Prefs.setVal(getActivity(), Prefs.KEY_MILLIS_LEFT, left);
//        }
        }
        return storedValue;

    }


}
