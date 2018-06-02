package com.example.brown.test;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by brown on 4/21/2018.
 */

@Database(entities = {userTables.class}, version = 4)
public abstract class wordRoomDatabase extends RoomDatabase {

    public abstract daoUserData myDao();

    // make this a singelton:
    private static wordRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback() {

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

                new PopulateDbAsync(INSTANCE).execute();
            }
        };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final daoUserData mDao;

        PopulateDbAsync(wordRoomDatabase db) {
            mDao = db.myDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
          //  mDao.deleteAll();
          //  userTables word = new userTables("Hello");
          //  mDao.insert(word);
            return null;
        }
    }

    static wordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (wordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            wordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
