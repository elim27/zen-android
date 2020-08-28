package com.MADAPPS.zen.activities.startup;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.MADAPPS.zen.Database.Day;
import com.MADAPPS.zen.Database.Repository;
import com.MADAPPS.zen.ui.stats.DayViewModel;

import java.util.List;

public class DayModel {
    Repository dayRepo;
    float[] data;
    LiveData<List<Day>> threeDays;
    LiveData<List<Day>> week;
    DayViewModel dayViewModel;
    public DayModel(@NonNull Application application) {
        dayRepo = new Repository(application);
    }

    public void insert(Day day){
        dayRepo.insert(day);
        Log.i("insertion", "success! " + day.getDailyTotal());
    }
}
