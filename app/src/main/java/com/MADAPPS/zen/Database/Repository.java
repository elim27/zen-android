package com.MADAPPS.zen.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.airbnb.lottie.L;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
//    private JournalDao journalDao;
//    private LiveData<List<Journal>> allEntries;

    private DayDao dayDao;
    private  LiveData<List<Day>> threeDays;
    private LiveData<List<Day>> week;
    private LiveData<List<Day>> month;
    private LiveData<List<Day>> year;

    public Repository(Application application) {
        ZenDatabase database = ZenDatabase.getInstance(application);
        //day dao
        dayDao = database.dayDao();
        threeDays = dayDao.getPastThreeDays();
        week = dayDao.getPastWeek();
        month = dayDao.getPastMonth();
        year = dayDao.getPastYear();
    }




    //Time
    public void insert(Day day){
        new InsertDayAsyncTask(dayDao).execute(day);
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



    //ensures these tasks do not occur on main thread
    private static class InsertDayAsyncTask extends AsyncTask<Day, Void, Void>{
        private DayDao dayDao;

        private InsertDayAsyncTask(DayDao dayDao){
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(Day... days) {
            dayDao.insert(days[0]);
            return null;
        }
    }




//    //ensures these tasks do not occur on main thread
//    private static class InsertJournalAsyncTask extends AsyncTask<Journal, Void, Void>{
//        private JournalDao journalDao;
//
//        private InsertJournalAsyncTask(JournalDao journalDao){
//            this.journalDao = journalDao;
//        }
//
//        @Override
//        protected Void doInBackground(Journal... journals) {
//            journalDao.insert(journals[0]);
//            return null;
//        }
//    }
//
//    private static class UpdateJournalAsyncTask extends AsyncTask<Journal, Void, Void>{
//        private JournalDao journalDao;
//
//        private UpdateJournalAsyncTask(JournalDao journalDao){
//            this.journalDao = journalDao;
//        }
//
//        @Override
//        protected Void doInBackground(Journal... journals) {
//            journalDao.update(journals[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteJournalAsyncTask extends android.os.AsyncTask<Journal, Void, Void> {
//        private JournalDao journalDao;
//
//        private DeleteJournalAsyncTask(JournalDao journalDao){
//            this.journalDao = journalDao;
//        }
//
//        @Override
//        protected Void doInBackground(Journal... journals) {
//            journalDao.delete(journals[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteAllJournalsAsyncTask extends AsyncTask<Journal, Void, Void>{
//        private JournalDao journalDao;
//
//        private DeleteAllJournalsAsyncTask(JournalDao journalDao){
//            this.journalDao = journalDao;
//        }
//
//        @Override
//        protected Void doInBackground(Journal... journals) {
//            journalDao.deleteAll();
//            return null;
//        }
  // }
}
