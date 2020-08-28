package com.MADAPPS.zen.ui.stats;

import android.util.Log;
import android.view.View;

import com.MADAPPS.zen.R;

public class AnalysisHandler extends AnalysisFragment implements View.OnClickListener  {
        public AnalysisHandler(View view){
            threeDay = view.findViewById(R.id.threeDayBtn);
            weekBtn = view.findViewById(R.id.weekBtn);
            monthBtn = view.findViewById(R.id.monthBtn);
            yearBtn = view.findViewById(R.id.yearBtn);
            threeDay.setOnClickListener(this);
            weekBtn.setOnClickListener(this);
            monthBtn.setOnClickListener(this);
           yearBtn.setOnClickListener(this);
        }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.threeDayBtn){
            Log.i("3day", "clicked");
            data = threeDayValues();
            sparkView.setAdapter(new GraphAdapter(data));
        } else if(view.getId() == R.id.weekBtn){
            data = weekValues();
            sparkView.setAdapter(new GraphAdapter(data));
        } else if (view.getId() == R.id.monthBtn){
            Log.i("insert", "donje");
            data = monthValues();
            sparkView.setAdapter(new GraphAdapter(data));
        }else if(view.getId() == R.id.yearBtn){



        }
    }

    private float[] threeDayValues(){
        data = new float[3];
        Log.i("threeDayValues", "size: " + threeDays.size());
        for(int i = 0; i <threeDays.size(); i++){
            data[i] = (float) threeDays.get(i).getDailyTotal()/60000;
            Log.i("values: ", ""+ data[i]);
        }

        return data;
    }

    private float[] weekValues(){
        data = new float[7];
        Log.i("weekValues", "size: " + week.size());
        for(int i = 0; i < week.size(); i++){
            data[i] = (float) week.get(i).getDailyTotal()/60000;
            Log.i("values: ", ""+ data[i]);
        }
        return data;
    }

    private float[] monthValues(){
        data = new float[30];
        for(int i =0; i < 30; i++){
            if(i >= month.size()){
                data[i] = (float) 0;
            } else {
                data[i] = (float) month.get(i).getDailyTotal() / 60000;
            }
            Log.i("values[" + i + "]: ", ""+ data[i]);
        }
        return data;
    }
}
