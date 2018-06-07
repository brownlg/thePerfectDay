package com.thePerfectDay.brown.test;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;



/**
 * Created by brown on 4/19/2018.
 */

@Dao
public interface daoUserData {

    @Insert
    void insert(userTables userTable);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY date(date) ASC")
    LiveData<List<userTables>> getAllWords();

    @Query("SELECT * FROM word_table where date = :dateString LIMIT 1")
    userTables getDataByDate(String dateString);

    @Update
    int updateUserTables(userTables... userDataRow);
}
