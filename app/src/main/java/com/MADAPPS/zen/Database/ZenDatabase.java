package com.MADAPPS.zen.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Day.class}, version = 1)
public abstract class ZenDatabase extends RoomDatabase {

    private static ZenDatabase instance;


    public abstract DayDao dayDao();

    public static synchronized ZenDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ZenDatabase.class, "zen_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }


}
