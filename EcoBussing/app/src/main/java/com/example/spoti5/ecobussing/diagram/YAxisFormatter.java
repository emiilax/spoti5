package com.example.spoti5.ecobussing.diagram;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by emilaxelsson on 16/10/15.
 */
public class YAxisFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public YAxisFormatter () {
        mFormat = new DecimalFormat("###,###,##0.00"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // write your logic here
        // access the YAxis object to get more information
        return mFormat.format(value) + "\n kg CO2";
    }
}
