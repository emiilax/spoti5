package com.example.spoti5.ecobussing;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.spoti5.ecobussing.Database.Database;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarDiagram extends Fragment {


    private View view;

    public BarDiagram() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.diagram_test, container, false);


        //BarChart chart = (BarChart) view.findViewById(R.id.chart);

        LayoutInflater lInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BarChart chart = (BarChart) view.findViewById(R.id.chart);
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setDrawLabels(false);

        chart.setDescription("");
        chart.setData(getBarDataLastSevenDays());

        // X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new XAxisFormatter());


        YAxis yAxis = chart.getAxisLeft();
        //yAxis.setTypeface(...); // set a different font
        yAxis.setTextSize(12f); // set the textsize
        yAxis.setAxisMaxValue(10f); // the axis maximum is 100
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawGridLines(false);

        yAxis.setValueFormatter(new YAxisFormatter());
        //yAxis.setLabelCount(6, false);

        return view;
    }

    public BarData getBarDataLastSevenDays(){


        //Date date = calendar.getTime();
        Calendar calendar = Calendar.getInstance();


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        IUser currentUser = SaveHandler.getCurrentUser();
        ArrayList<BarEntry> barEntryList = new ArrayList<BarEntry>();

        System.out.println(currentUser);

        calendar.add(Calendar.DAY_OF_MONTH, + 1);
        for(int i = 0; i < 7; i++){

            calendar.add(Calendar.DAY_OF_MONTH, -1);

            int year = calendar.get(Calendar.YEAR);
            int month = 1 + calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            xVals.add(0, getWeekDayName(dayOfWeek));
            double value = 0;
            try{
                value = currentUser.getCO2SavedDate(year, month, day);

            } catch(Exception e){
                //e.printStackTrace();
                value = 0;
            }
            System.out.println(year + ", " + month + ", " + day);
            System.out.println(getWeekDayName(dayOfWeek) + ": " + value);

            //yVals.add(new BarEntry((float) value, i));

            barEntryList.add(new BarEntry((float) value, 6-i));


        }
        BarDataSet bds = new BarDataSet(barEntryList, "");
        dataSets.add(bds);
        return new BarData(xVals, dataSets);

    }

    public String getWeekDayName(int weekDay){
        String dayName = "";

        switch(weekDay) {
            case Calendar.MONDAY:
                dayName = "Mån";
                break;
            case Calendar.TUESDAY:
                dayName = "Tis";
                break;
            case Calendar.WEDNESDAY:
                dayName = "Ons";
                break;
            case Calendar.THURSDAY:
                dayName = "Tors";
                break;
            case Calendar.FRIDAY:
                dayName = "Fre";
                break;
            case Calendar.SATURDAY:
                dayName = "Lör";
                break;
            case Calendar.SUNDAY:
                dayName = "Sön";
                break;
        }
        return dayName;
    }


}
