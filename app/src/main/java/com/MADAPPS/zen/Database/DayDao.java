package com.MADAPPS.zen.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DayDao {
    @Insert
    void insert(Day day);

    @Query("SELECT*FROM time_table ORDER BY id DESC LIMIT 3")
   LiveData<List<Day>> getPastThreeDays();

    @Query("SELECT*FROM time_table ORDER BY id DESC LIMIT 7")
    LiveData<List<Day>> getPastWeek();

    @Query("SELECT*FROM time_table ORDER BY id DESC LIMIT 30")
    LiveData<List<Day>> getPastMonth();

    @Query("SELECT*FROM time_table ORDER BY id DESC LIMIT 365")
    LiveData<List<Day>> getPastYear();

    @Query("DELETE FROM time_table")
    void deleteAll();

}
