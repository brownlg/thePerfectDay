package com.thePerfectDay.brown.test;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.List;

/**
 * Created by brown on 4/21/2018.
 */

public class DataRepository {
    private daoUserData mDoa;
    private LiveData<List<userTables>> mUserTable;

    lbcDateHandler dateHandler = new lbcDateHandler();

    DataRepository(Application application) {
        wordRoomDatabase db = wordRoomDatabase.getDatabase(application);

        mDoa = db.myDao();
        mUserTable = mDoa.getAllWords();
    }

    LiveData<List<userTables>> getAllWords() {
        return mUserTable;
    }

    public userTables getDataByDate(String queryDay) {

        for (userTables dbData :  mUserTable.getValue()) {
            Calendar calendarForDb = dateHandler.calendarFromString(dbData.date);
            Calendar calendarForToday = Calendar.getInstance();

            if (dateHandler.isSameDay(calendarForDb, calendarForToday)) {
                // return the database entry
                return dbData;
            }
        }

        // date not found
        return null;
    }


    public void reset() {
        new deleteAsyncTask(mDoa).execute();
    }

    public void insert (userTables myData) {
        new insertAsyncTask(mDoa).execute(myData);
    }

    public void update (userTables myData) {
        new updateAsyncTask(mDoa).execute(myData);
    }

    private static class deleteAsyncTask extends AsyncTask<userTables, Void, Void> {
        private daoUserData mAsyncTaskDao;

        deleteAsyncTask(daoUserData dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final userTables... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<userTables, Void, Void> {
        private daoUserData mAsyncTaskDao;

        updateAsyncTask(daoUserData dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final userTables... params) {
            mAsyncTaskDao.updateUserTables(params);
            return null;
        }

    }

    private static class insertAsyncTask extends AsyncTask<userTables, Void, Void> {

        private daoUserData mAsyncTaskDao;

        insertAsyncTask(daoUserData dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final userTables... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
