package com.MADAPPS.zen.ui.stats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.MADAPPS.zen.Database.Day;
import com.MADAPPS.zen.Database.Repository;
import com.MADAPPS.zen.R;
import com.robinhood.spark.SparkView;

import java.util.List;

public class AnalysisFragment extends Fragment implements View.OnClickListener {
    float []data;
    SparkView sparkView;
    Repository dayRepo;
    private DayViewModel dayViewModel;
    List<Day> threeDays;
    List<Day> week;
    List<Day> month;

    Button threeDay;
    Button weekBtn;
    Button monthBtn;
    Button yearBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        statsViewModel =
//                ViewModelProviders.of(this).get(StatsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        dayViewModel = ViewModelProviders.of(this.getActivity()).get(DayViewModel.class);

        setObservables();


        data = new float[100];
        sparkView = view.findViewById(R.id.sparkview);
        sparkView.setAdapter(new GraphAdapter(data));

        threeDay = view.findViewById(R.id.threeDayBtn);
        weekBtn = view.findViewById(R.id.weekBtn);
        monthBtn = view.findViewById(R.id.monthBtn);
        yearBtn = view.findViewById(R.id.yearBtn);
        threeDay.setOnClickListener(this);
        weekBtn.setOnClickListener(this);
        monthBtn.setOnClickListener(this);
        yearBtn.setOnClickListener(this);


        return view;
    }


    private void setObservables(){
        dayViewModel.getPastThreeDays().observe(this, new Observer<List<Day>>() {
            @Override
            public void onChanged(List<Day> days) {
                threeDays = days;
            }
        });

        dayViewModel.getPastWeek().observe(this, new Observer<List<Day>>() {
            @Override
            public void onChanged(List<Day> days) {
                week = days;
            }
        });

        dayViewModel.getPastMonth().observe(this, new Observer<List<Day>>() {
            @Override
            public void onChanged(List<Day> days) {
                month = days;
            }
        });

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
