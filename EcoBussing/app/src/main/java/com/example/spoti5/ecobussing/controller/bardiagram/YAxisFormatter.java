package com.example.spoti5.ecobussing.controller.bardiagram;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Emil Axelsson on 16/10/15.
 *
 * This is the Formatter for the Y-axis in the BarDiaram. Sets the format, and name
 * of the y-axis values
 */
public class YAxisFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;
    private boolean isCompany;
    private boolean isPointsMoney;

    public YAxisFormatter (boolean isCompany, boolean isPointsMoney) {

        this.isCompany = isCompany;
        this.isPointsMoney = isPointsMoney;

        if(isPointsMoney){
            mFormat = new DecimalFormat("###,###,##0");
        }else {
            mFormat = new DecimalFormat("###,###,##0.00");
        }
    }


    @Override
    public String getFormattedValue(float value, YAxis yAxis) {

        if(isPointsMoney){
            if(isCompany){
               return mFormat.format(value) + "poäng";
            }else{
                return mFormat.format(value) + "kr";
            }

        }
        return mFormat.format(value) + "\n kg CO2";
    }
}
