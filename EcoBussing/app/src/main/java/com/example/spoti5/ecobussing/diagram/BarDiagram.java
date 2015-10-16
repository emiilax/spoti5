package com.example.spoti5.ecobussing.diagram;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarDiagram extends Fragment {

    private double highestValue;
    private View view;

    public BarDiagram() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_barchart_holder, container, false);


        //BarChart chart = (BarChart) view.findViewById(R.id.chart);

        LayoutInflater lInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BarChart chart = (BarChart) view.findViewById(R.id.chart);

        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.setScaleEnabled(false);

        // No lines in
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawLimitLinesBehindData(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawLimitLinesBehindData(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLimitLinesBehindData(false);


        chart.setDescription("");

        chart.setData(getBarDataLastSevenDays());


        // X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);

        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(getResources().getColor(R.color.diagram_lines));

        xAxis.setValueFormatter(new XAxisFormatter());


        YAxis yAxis = chart.getAxisLeft();
        //yAxis.setTypeface(...); // set a different font
        yAxis.setTextSize(10f); // set the textsize

        //yAxis.setAxisMaxValue((float) (highestValue + 0.2));
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawAxisLine(true);
        yAxis.setAxisLineColor(getResources().getColor(R.color.diagram_lines));
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(getResources().getColor(R.color.diagram_lines));
        //yAxis.setDrawLimitLinesBehindData(false);w
        yAxis.setAxisMaxValue((float) (Math.ceil(highestValue * 2) / 2));
        chart.setY((float) (Math.ceil(highestValue * 2) / 2));
        chart.setVisibleYRangeMaximum(((float) (Math.ceil(highestValue * 2) / 2)), YAxis.AxisDependency.LEFT);
        yAxis.setValueFormatter(new YAxisFormatter());
        yAxis.setLabelCount(3, true);


        chart.invalidate();


        return view;
    }

    public BarData getBarDataLastSevenDays(){


        //Date date = calendar.getTime();
        Calendar calendar = Calendar.getInstance();


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        IUser currentUser = SaveHandler.getCurrentUser();
        ArrayList<BarEntry> barEntryList = new ArrayList<BarEntry>();

        calendar.add(Calendar.DAY_OF_MONTH, + 1);

        highestValue = 0;

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

            if(value > highestValue) highestValue = value;
            //System.out.println(highestValue);

            System.out.println(year + ", " + month + ", " + day);
            System.out.println(getWeekDayName(dayOfWeek) + ": " + value);

            //yVals.add(new BarEntry((float) value, i));

            barEntryList.add(new BarEntry((float) value, 6-i));


        }
        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(getResources().getColor(R.color.secondary));
        dataSets.add(bds);

        return new BarData(xVals, dataSets);

    }

    public String getWeekDayName(int weekDay){
        String dayName = "";

        switch(weekDay) {
            case Calendar.MONDAY:
                dayName = "Må";
                break;
            case Calendar.TUESDAY:
                dayName = "Ti";
                break;
            case Calendar.WEDNESDAY:
                dayName = "On";
                break;
            case Calendar.THURSDAY:
                dayName = "To";
                break;
            case Calendar.FRIDAY:
                dayName = "Fr";
                break;
            case Calendar.SATURDAY:
                dayName = "Lö";
                break;
            case Calendar.SUNDAY:
                dayName = "Sö";
                break;
        }
        return dayName;
    }


}
