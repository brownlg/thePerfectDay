package com.thePerfectDay.brown.test;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private WastedViewModel mWordViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button Listeners:
        Button btnAddTime = findViewById(R.id.btnAddHour);
        btnAddTime.setOnClickListener(onClickAddHour);

        Button btnAdd30Min = findViewById(R.id.btnAdd30Min);
        btnAdd30Min.setOnClickListener(onClickAdd0Min);

        Button btnSub30Min = findViewById(R.id.btnSub30Min);
        btnSub30Min.setOnClickListener(onClickSub30Min);

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(onClickReset);



        // ------




        final WastedListAdapter adapter = new WastedListAdapter(this);

        mWordViewModel = ViewModelProviders.of(this).get(WastedViewModel.class);

        mWordViewModel.getmAllData().observe(this, new Observer<List<userTables>>() {
            @Override
            public void onChanged(@Nullable final List<userTables> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setmWords(words);

                updateChart(words);
            }
        });


        // updateChart();
    }

    private void updateChart(List<userTables> userData)
    {
        // setup chart
        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        List<String> xAxisValues = new ArrayList<>();


        lbcDateHandler myDateHandler = new lbcDateHandler();


        entries.add(new Entry(0, 0));   // need a starting point for the line chart
        xAxisValues.add("Start");

        int cc = 1;
        for (userTables row : userData){
            entries.add(new Entry(cc, row.hours));

            String monthString = myDateHandler.getMonthString(row.date);
            String dayString = myDateHandler.getDayString(row.date);
            xAxisValues.add(dayString + "-" + monthString);
            cc++;
        }

        // no data to show!
        if(cc == 1) return;

        // what is this?
        for (int i = cc; i < 0; i++) {
            entries.add(new Entry(i, 4));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Time wasted"); // add entries to dataset
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(4.0F);
        dataSet.setCircleColor(Color.RED);

        //dataSet.setValueTextColor(Color.RED); // styling, ...
        //dataSet.setValueTextSize(18);


        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(45.0F);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1.0F);
        //xAxis.setAxisMinimum(7.0F);

        String[] values = xAxisValues.toArray(new String[0]);


        // Y-AXIS ////////////////////////////////////////////////////

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0.0F);
        leftAxis.setGranularity(1.0F);
        leftAxis.setAxisMaximum(12.0F);





        /////////////////////////////////////////////////////////////




        xAxis.setValueFormatter(new MyYAxisValueFormatter(values));


        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        // ---------------------------------------------------------------
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            userTables word = new userTables(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(), R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


    View.OnClickListener onClickAddHour = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Get or Generate Date
            Date todayDate = new Date();

            //Get an instance of the formatter
            DateFormat dateFormat = DateFormat.getDateTimeInstance();

            //If you want to show only the date then you will use
            //DateFormat dateFormat = DateFormat.getD ateInstance();

            //Format date
            String dateString  = dateFormat.format(todayDate);

            userTables dataRow = new userTables(dateString);

            // see if there is an entry for today
            mWordViewModel.getmAllData();

            // mWordViewModel.insert(dataRow);
            mWordViewModel.addTime(dataRow, 1);

            //

            Toast.makeText(getApplicationContext(),
                    "+1 hour wasted :(", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener onClickSubHour = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Get or Generate Date
            Date todayDate = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String dateString  = dateFormat.format(todayDate);

            userTables dataRow = new userTables(dateString);

            // see if there is an entry for today
            mWordViewModel.getmAllData();

            // mWordViewModel.insert(dataRow);
            mWordViewModel.addTime(dataRow, -1);

            Toast.makeText(getApplicationContext(),
                    "Oh less wasted time, good job!", Toast.LENGTH_SHORT).show();


        }
    };

    View.OnClickListener onClickSub30Min = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Get or Generate Date
            Date todayDate = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String dateString  = dateFormat.format(todayDate);

            userTables dataRow = new userTables(dateString);

            // see if there is an entry for today
            mWordViewModel.getmAllData();

            // mWordViewModel.insert(dataRow);
            mWordViewModel.addTime(dataRow, -0.5F);

            Toast.makeText(getApplicationContext(),
                    "Oh less wasted time, good job!", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener onClickAdd0Min = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Get or Generate Date
            Date todayDate = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String dateString  = dateFormat.format(todayDate);

            userTables dataRow = new userTables(dateString);

            // see if there is an entry for today
            mWordViewModel.getmAllData();

            // mWordViewModel.insert(dataRow);
            mWordViewModel.addTime(dataRow, +0.5F);

            Toast.makeText(getApplicationContext(),
                    "stop wasting time!", Toast.LENGTH_SHORT).show();
        }
    };


    View.OnClickListener onClickReset = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWordViewModel.resetAll();
        }
    };


    View.OnClickListener onClickAddWord = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        }
    };
}
