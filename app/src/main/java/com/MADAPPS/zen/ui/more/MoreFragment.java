package com.MADAPPS.zen.ui.more;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.MADAPPS.zen.Preferences;
import com.MADAPPS.zen.R;

import java.lang.ref.PhantomReference;

public class MoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MoreViewModel moreViewModel;
    private String KEY_TOTALTIME = Preferences.KEY_TOTALTIME;
    private String KEY_SELECTORPOS = Preferences.KEY_SELECTORPOS;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moreViewModel =
                ViewModelProviders.of(this).get(MoreViewModel.class);
        View view = inflater.inflate(R.layout.fragment_more, container, false);


        Spinner spinner = (Spinner) view.findViewById(R.id.more_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_choices, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int pos = Preferences.getIntVal(getContext(), KEY_SELECTORPOS);
        spinner.setSelection(pos);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String [] placeHolder = new String[10];
        int value = 5;
        long storedValue;
        String pous = parent.getItemAtPosition(position).toString();
        //String pous = parent.getSelectedItem().toString();
        placeHolder = pous.split(" ");
        try {
            value = Integer.parseInt(placeHolder[0]);
            Log.i("Item Selected Value: ", "" + value);
        }catch (NumberFormatException e){
            Log.i("NumberFormatException", "see MoreFragment.java, onItemSelected()");
            e.printStackTrace();
        }
        Log.i("placeHolder[1]", placeHolder[1]);
        if(placeHolder[1].contains("Hour")){
            Log.i("HOUR", "reach");
            storedValue = (long) value*3600000;
        } else {
            storedValue = (long) value*1000;
        }

        if(!Preferences.getBoolVal(getContext(), Preferences.KEY_MOREZEN) || Preferences.getBoolVal(getContext(), Preferences.KEY_PAUSED)) {
            long left = Preferences.getLongVal(getContext(), Preferences.KEY_TOTALTIME) - Preferences.getLongVal(getContext(), Preferences.KEY_MILLIS_LEFT);
            left = storedValue - left;
            Preferences.setVal(getContext(), Preferences.KEY_MILLIS_LEFT, left);
//
//        }
        }
        Log.i("storedValue", "" + storedValue);

        Preferences.setVal(getContext(), KEY_TOTALTIME, (long) storedValue);
        Preferences.setVal(getContext(), KEY_SELECTORPOS, position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        long total = Preferences.getLongVal(getContext(), KEY_TOTALTIME);
        Preferences.setVal(getContext(), KEY_TOTALTIME, (long) total);

    }


}
