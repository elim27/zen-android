package com.MADAPPS.zen.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "time_table")
public class Day {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long dailyTotal;


    public Day(long dailyTotal){
        this.dailyTotal = dailyTotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getDailyTotal() {
        return dailyTotal;
    }
}
