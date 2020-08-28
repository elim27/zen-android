package com.MADAPPS.zen.ui.more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.MADAPPS.zen.helpers.CurrDate;
import com.MADAPPS.zen.helpers.Prefs;
import com.MADAPPS.zen.R;

import com.MADAPPS.zen.ui.home.TotalTimeSelector;

import java.util.ArrayList;

public class MoreFragment extends Fragment  {
    protected static Activity moreActivity;
    protected static final String KEY_SELECTOR_POS = Prefs.KEY_DURATION_POS;
    private static final String KEY_AUDIO_POS = Prefs.KEY_AUDIO_POS;

    ArrayList<String> authors;
    protected static Spinner spinner;
    protected static Spinner spinner2;
    protected static int durationPos;
    protected static int audioPos;
    private TextView quote;
    private TextView author;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        moreViewModel =
//                ViewModelProviders.of(this).get(MoreViewModel.class);


        View view = inflater.inflate(R.layout.fragment_more, container, false);

        moreActivity = this.getActivity();
        quote = view.findViewById(R.id.quote);
        author = view.findViewById(R.id.quote_author);

        //sets up quotes if the day is different
        CurrDate check = new CurrDate();
        int theDay = check.getDate();

//        if(theDay != Prefs.getIntVal(moreActivity, Prefs.KEY_CURRDAY)) {
            QuoteSelector setUpper = new QuoteSelector();
            setUpper.setUpQuotes();
            String theQuote = setUpper.getQuote();
            String theAuthor = setUpper.getAuthor();

            quote.setText(theQuote);
            author.setText(theAuthor);
//        }


        //first spinner
        spinner =  view.findViewById(R.id.more_spinner);
        //second spinner
        spinner2 = view.findViewById(R.id.more_spinner2);

        durationPos = Prefs.getIntVal(moreActivity, KEY_SELECTOR_POS);
        audioPos = Prefs.getIntVal(moreActivity, KEY_AUDIO_POS);

        TotalTimeSelector selector = new TotalTimeSelector(); //creates a new selector

        AudioSelector.setAudioArray();


        return view;
    }








}
