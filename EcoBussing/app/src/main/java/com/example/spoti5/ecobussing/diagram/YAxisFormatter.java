package com.example.spoti5.ecobussing.diagram;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by emilaxelsson on 16/10/15.
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
