package com.thePerfectDay.brown.test;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by brown on 5/25/2018.
 */

public class MyYAxisValueFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public MyYAxisValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value];
    }


}
