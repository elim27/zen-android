package com.MADAPPS.zen.ui.more;
import android.util.Log;

import com.MADAPPS.zen.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class QuoteSelector extends MoreFragment{
    static private Random rand = new Random(420);
    private static ArrayList<String> quotes;
    private static int pos;
    static private Scanner scan;

    public QuoteSelector(){
    }



    public void setUpQuotes(){
        setQuoteArray();
        setAuthorArray();
        pos = rand.nextInt(quotes.size()-1); //author and quotes should be the same size if done correctly
    }

    /**
     * Sets the quote text view
     */
    public String getQuote(){
        String theQuote;
        theQuote = "\"" + quotes.get(pos) + "\"";
        return theQuote;
    }


    /**
     * Sets the corresponding author view
     */
    public String getAuthor(){
        String theAuthor;
        theAuthor= "-"+authors.get(pos);
        return theAuthor;
    }


    /**
     * Initializes our author array
     */
    private void setAuthorArray(){
        authors = new ArrayList<String>();
        String addMe;
        scan = new Scanner(moreActivity.getResources().openRawResource(R.raw.authors));
        while(scan.hasNextLine()){
            Log.i("scanner", "Added!");
            addMe = scan.nextLine();
            authors.add(addMe);
            Log.i("addMe", addMe);
        }
        Log.i("Author size", "" + authors.size());
        Log.i("Author isEmpty", ""+ authors.isEmpty());
    }

    /**
     * Initializes our quote array
     */
    private static void setQuoteArray(){
        quotes = new ArrayList<String>();
        String addMe;
        scan = new Scanner(moreActivity.getResources().openRawResource(R.raw.quotes));
        while(scan.hasNextLine()){
            addMe = scan.nextLine();
            quotes.add(addMe);
        }


    }

    }







