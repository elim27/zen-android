package com.MADAPPS.zen.ui.stats;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.MADAPPS.zen.Database.Day;
import com.MADAPPS.zen.Database.Repository;

import java.util.List;

/**
 * This class is the view model for the AnalysisFragment
 */
public class DayViewModel extends AndroidViewModel {
    Repository dayRepo;
    float[] data;
    LiveData<List<Day>>threeDays;
    LiveData<List<Day>> week;
    LiveData<List<Day>> month;

    public DayViewModel(@NonNull Application application){
        super(application);
        dayRepo = new Repository(application);
        threeDays = dayRepo.getPastThreeDays();
        week = dayRepo.getPastWeek();
        month = dayRepo.getPastMonth();
    }


    public LiveData<List<Day>> getPastThreeDays(){
        return threeDays;
    }

    public LiveData<List<Day>> getPastWeek(){
        return week;
    }

    public LiveData<List<Day>> getPastMonth(){
        return month;
    }
//    public float[] getPastWeek(){
//        LiveData<List<Day>> week = dayRepo.getPastWeek();
//        data = new float[7];
//        if(week.getValue() != null) {
//            for (int i = 0; i < week.getValue().size(); i++) {
//                data[i] = week.getValue().get(i).getDailyTotal();
//            }
//        }
//        return data;
//    }

    public void insert(Day day){
        dayRepo.insert(day);
    }


    public void getPastYear(){

    }
}
