package com.thePerfectDay.brown.test;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by brown on 4/21/2018.
 */

public class WastedViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private LiveData<List<userTables>> mAllData;

    public WastedViewModel(Application application) {
        super(application);

        mRepository = new DataRepository(application);
        mAllData = mRepository.getAllWords();

    }

    LiveData<List<userTables>> getmAllData() {
        return mAllData;
    }



    public void insert(userTables myWord) {
        mRepository.insert(myWord);
    }

    public void resetAll() {
        mRepository.reset();
    }

    public int addTime(userTables dataRow, float time) {
        userTables myDataRow = mRepository.getDataByDate(dataRow.date);

        if (myDataRow != null) {
            float newTime = myDataRow.hours + time;

            if ((newTime <= 24) && ((newTime >= 0))) {
                myDataRow.hours = newTime;
                mRepository.update(myDataRow);
            }


        } else
        {
            // first entry for the day
            this.insert(dataRow);
        }

        return -1;
    }
}
