package com.example.spoti5.ecobussing.view;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Activites.ActivityController;
import com.example.spoti5.ecobussing.controller.bardiagram.XAxisFormatter;
import com.example.spoti5.ecobussing.controller.bardiagram.YAxisFormatter;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.model.profile.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Emil Axelsson on 16/10/15.
 *
 * Used to show diagrams in the profile-views.
 *
 */
public class BarDiagram extends Fragment {

    private double highestValue;
    private View view;
    private BarChart chart;
    private IProfile profile;
    private String nameOfChart;
    private boolean isCompany;

    private BarData diagramBarData;

    //if the diagram should be in points(company), money(user)
    private boolean moneyPoints;

    private int range;

    public final static int LAST_SEVEN_DAYS = 0;
    public final static int LAST_SEVEN_WEEKS = 1;
    public final static int LAST_SEVEN_MONTHS = 2;

    public BarDiagram() {
        // Required empty public constructor
    }

    /**
     * Used to get a new instance
     * @param profile, the profile the diagrams should get its values from
     * @param range, which range it should return
     * @param moneyPoints, if company: should it be points or co2, if user: should it be money or co2
     * @return, Bardiagram-object
     */
    public final static BarDiagram newInstance(IProfile profile, int range, boolean moneyPoints){
        BarDiagram bd = new BarDiagram();

        bd.setProfile(profile);
        bd.setRange(range);
        bd.moneyPoints = moneyPoints;


        return bd;

    }

    public void setProfile(IProfile profile){
        try{
            this.profile = (IUser) profile;
            isCompany = false;

        }catch (ClassCastException e){
            this.profile = (Company) profile;
            isCompany = true;
        }
    }


    public void setRange(int range){
        this.range = range;
        if(range == LAST_SEVEN_DAYS) {
            nameOfChart = ActivityController.getContext().getResources().getString(R.string.barchart_lastSevenDays);
        }else if(range == LAST_SEVEN_WEEKS) {
            nameOfChart = ActivityController.getContext().getResources().getString(R.string.barchart_lastSevenWeeks);
        }else if(range == LAST_SEVEN_MONTHS){
            nameOfChart = ActivityController.getContext().getResources().getString(R.string.barchart_lastSevenMonths);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_barchart_holder, container, false);
        TextView rangeText = (TextView) view.findViewById(R.id.txtvChartRange);

        rangeText.setText(nameOfChart);


        LayoutInflater lInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        chart = (BarChart) view.findViewById(R.id.chart);

        // There are much code in this. The reason is that we are using a diagram-api where you
        // are not able to use the xml-file to set different settings that you want...

        // No grids, no right axis
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
        chart.setMaxVisibleValueCount(0);


        // X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(getResources().getColor(R.color.diagram_lines));
        //xAxis.setDrawLabels(false);
        xAxis.setEnabled(true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setValueFormatter(new XAxisFormatter());


        // Y-axis
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextSize(10f); // set the textsize
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawAxisLine(true);
        yAxis.setAxisLineColor(getResources().getColor(R.color.diagram_lines));
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(getResources().getColor(R.color.diagram_lines));
        yAxis.setValueFormatter(new YAxisFormatter(isCompany, moneyPoints));
        yAxis.setAxisLineWidth(1f);
        yAxis.setLabelCount(3, true);

        //setProfile(SaveHandler.getCurrentUser());
        setChartBarData(range);

        return view;
    }



    private void setChartBarData(int range) {

        if(range == LAST_SEVEN_DAYS){
            chart.setData(getBarDataLastSevenDays());
        } else if(range == LAST_SEVEN_WEEKS){
            chart.setData(getBarDataLastSevenWeeks());
        }else if(range == LAST_SEVEN_MONTHS){
            chart.setData(getBarDataLastSevenMonths());
        }

    }

    /**
     * Used to get the diagrams for the last seven days.
     *
     * @return a BarData-object with all the values for the days
     */
    public BarData getBarDataLastSevenDays(){


        //Date date = calendar.getTime();
        Calendar calendar = Calendar.getInstance();


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();


        //IUser currentUser = SaveHandler.getCurrentUser();

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
                if(isCompany){

                    if(moneyPoints){
                        value = ((Company)profile).getPointsSavedDate(year, month, day);
                    }else{
                        value = ((Company)profile).getPointsSavedDate(year, month, day);
                    }

                }else{

                    if(moneyPoints){
                        value = ((User)profile).getMoneySavedDate(year, month, day);
                    }else{
                        value = ((IUser)profile).getCO2SavedDate(year, month, day);
                    }

                }

            }catch (Exception e){
                value = 0;
            }


            if(value > highestValue) highestValue = value;

            System.out.println(year + ", " + month + ", " + day);
            System.out.println(getWeekDayName(dayOfWeek) + ": " + value);


            barEntryList.add(new BarEntry((float) value, 6 - i));


        }
        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(getResources().getColor(R.color.secondary));
        //bds.setColor(getResources().getColor(R.color.secondary));
        dataSets.add(bds);

        setMaxYAxis();

        return new BarData(xVals, dataSets);

    }


    /**
     * Used to get the diagrams for the last seven weeks.
     *
     * @return a BarData-object with all the values for the last seven weeks
     */
    public BarData getBarDataLastSevenWeeks(){
        Calendar calendar = Calendar.getInstance(Locale.GERMAN);


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        IUser currentUser = SaveHandler.getCurrentUser();
        ArrayList<BarEntry> barEntryList = new ArrayList<BarEntry>();

        highestValue = 0;

        int startWeeknumber = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
        double value = 0;
        //boolean firstWeek = true;
        int place = 0;
        int i = startWeeknumber;
        while(i > startWeeknumber - 7 ){

            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            int year = calendar.get(Calendar.YEAR);
            int month = 1 + calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            //System.out.println(weekNumber);
            //System.out.println(month);
            //System.out.println(day);
            if(weekNumber < i){
                xVals.add(0, Integer.toString(calendar.get(Calendar.WEEK_OF_YEAR) + 1));
                barEntryList.add(new BarEntry((float) value, 6 - place));
                System.out.println(value);
                value = 0;
                place++;
                i--;
            }


            try{
                if(isCompany){
                    if(moneyPoints){
                        value += ((Company)profile).getPointsSavedDate(year, month, day);
                    }else{
                        value += ((Company)profile).getPointsSavedDate(year, month, day);
                    }
                }else{
                    if(moneyPoints){
                        value += ((User)profile).getMoneySavedDate(year, month, day);
                    }else{
                        value += ((IUser)profile).getCO2SavedDate(year, month, day);
                    }
                }

            }catch (Exception e){
                value += 0;
            }

            if(value > highestValue) highestValue = value;
            

            calendar.add(Calendar.DAY_OF_MONTH, -1);

        }

        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(getResources().getColor(R.color.secondary));
        dataSets.add(bds);

        setMaxYAxis();

        return new BarData(xVals, dataSets);

    }

    /**
     * Used to get the diagrams for the last seven months.
     *
     * @return a BarData-object with all the values for the last seven months
     */
    public BarData getBarDataLastSevenMonths(){
        Calendar calendar = Calendar.getInstance();


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        IUser currentUser = SaveHandler.getCurrentUser();
        ArrayList<BarEntry> barEntryList = new ArrayList<BarEntry>();

        highestValue = 0;

        calendar.add(Calendar.MONTH, + 1);

        for(int i = 0; i < 7; i++){

            calendar.add(Calendar.MONTH, -1);
            int year = calendar.get(Calendar.YEAR);
            int month = 1 + calendar.get(Calendar.MONTH);

            double value = 0;

            try{
                if(isCompany){
                    if(moneyPoints){
                        value = ((Company)profile).getPointsSavedMonth(year, month);
                        System.out.println("month value " + i+ ": " + value);
                    }else{
                        value = ((Company)profile).getPointsSavedMonth(year, month);
                    }
                }else{
                    if(moneyPoints){
                        value = ((User)profile).getMoneySavedMonth(year, month);
                    }else{
                        value = ((IUser)profile).getCO2SavedMonth(year, month);
                    }

                }

            }catch (Exception e){
                value = 0;
            }

            xVals.add(0, getMonthName(month));

            if(value > highestValue) highestValue = value;

            barEntryList.add(new BarEntry((float) value, 6 - i));

        }

        BarDataSet bds = new BarDataSet(barEntryList, "");
        bds.setColor(getResources().getColor(R.color.secondary));
        dataSets.add(bds);

        setMaxYAxis();

        return new BarData(xVals, dataSets);

    }

    /**
     * Sets the y-axis max when all the values in the diagram are set. Makes it dynamic.
     */
    public void setMaxYAxis(){
        chart.getAxisLeft().setAxisMaxValue((float) (Math.ceil(highestValue * 2) / 2));
    }

    /**
     * Gets the month name first letters
     * @param month, the month you want to get the name on
     * @return, a string with the first two letters of the month-name
     */
    public String getMonthName(int month){

        String monthName = "";

        switch (month-1){
            case Calendar.JANUARY:
                monthName = "Ja";
                break;
            case Calendar.FEBRUARY:
                monthName = "Fe";
                break;
            case Calendar.MARCH:
                monthName = "Ma";
                break;
            case Calendar.APRIL:
                monthName = "Ap";
                break;
            case Calendar.MAY:
                monthName = "Ma";
                break;
            case Calendar.JUNE:
                monthName = "Ju";
                break;
            case Calendar.JULY:
                monthName = "Ju";
                break;
            case Calendar.AUGUST:
                monthName = "Au";
                break;
            case Calendar.SEPTEMBER:
                monthName = "Se";
                break;
            case Calendar.OCTOBER:
                monthName = "Oc";
                break;
            case Calendar.NOVEMBER:
                monthName = "No";
                break;
            case Calendar.DECEMBER:
                monthName = "De";
                break;

        }

        return monthName;
    }

    /**
     * Used to get the two first days of the weekday.
     *
     * @param weekDay, the day in week
     * @return, two letters of the weekday-name
     */
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
