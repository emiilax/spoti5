package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.ActivityController;
import com.example.spoti5.ecobussing.controller.bardiagram.DiagramBarData;
import com.example.spoti5.ecobussing.controller.bardiagram.XAxisFormatter;
import com.example.spoti5.ecobussing.controller.bardiagram.YAxisFormatter;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;


/**
 * Created by Emil Axelsson on 16/10/15.
 *
 * Used to show diagrams in the profile-views.
 *
 */
public class BarDiagramFragment extends Fragment {

    //private double highestValue;
    private BarChart chart;
    private IProfile profile;
    private String nameOfChart;
    private boolean isCompany;
    private DiagramBarData diagramBarData;

    //if the diagram should be in points(company), money(user)
    private boolean moneyPoints;

    private int range;

    public final static int LAST_SEVEN_DAYS = 0;
    public final static int LAST_SEVEN_WEEKS = 1;
    public final static int LAST_SEVEN_MONTHS = 2;

    public BarDiagramFragment() {
        // Required empty public constructor
        diagramBarData = new DiagramBarData(this);
    }

    /**
     * Used to get a new instance
     * @param profile, the profile the diagrams should get its values from
     * @param range, which range it should return
     * @param moneyPoints, if company: should it be points or co2, if user: should it be money or co2
     * @return Bardiagram-object
     */
    public static BarDiagramFragment newInstance(IProfile profile, int range, boolean moneyPoints){
        BarDiagramFragment bd = new BarDiagramFragment();

        bd.setProfile(profile);
        bd.moneyPoints = moneyPoints;
        bd.setRange(range);



        return bd;

    }

    public void setProfile(IProfile profile){
        try{
            this.profile = (IUser)profile;
            isCompany = false;

        }catch (ClassCastException e){
            this.profile = profile;
            isCompany = true;
        }
    }


    public void setRange(int range){
        this.range = range;
        String unit = "";
        if(moneyPoints){
            if(isCompany){
                unit = "Poäng ";
            }else{
                unit = "Pengar ";
            }
        }else{
            unit = "CO2 ";
        }

        if(range == LAST_SEVEN_DAYS) {
            nameOfChart = unit + ActivityController.getContext().getResources().getString(R.string.barchart_lastSevenDays);
        }else if(range == LAST_SEVEN_WEEKS) {
            nameOfChart = unit + ActivityController.getContext().getResources().getString(R.string.barchart_lastSevenWeeks);
        }else if(range == LAST_SEVEN_MONTHS){
            nameOfChart = unit + ActivityController.getContext().getResources().getString(R.string.barchart_lastSevenMonths);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barchart_holder, container, false);
        TextView rangeText = (TextView) view.findViewById(R.id.txtvChartRange);
        TextView unitText = (TextView) view.findViewById(R.id.txtvUnit);

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

        String unit = "";

        if(moneyPoints){
            if(isCompany){
                unit = "Poäng";
            }else{
                unit = "Kr";
            }
        }else{
            unit = "Kg CO2";
        }
        unitText.setText(unit);
        setChartBarData(range);

        return view;
    }



    private void setChartBarData(int range) {

        if(range == LAST_SEVEN_DAYS){
            chart.setData(diagramBarData.getBarDataLastSevenDays(isCompany, moneyPoints, profile));
        } else if(range == LAST_SEVEN_WEEKS){
            chart.setData(diagramBarData.getBarDataLastSevenWeeks(isCompany, moneyPoints, profile));
        }else if(range == LAST_SEVEN_MONTHS){
            chart.setData(diagramBarData.getBarDataLastSevenMonths(isCompany, moneyPoints, profile));
        }
        setMaxYAxis();

    }

    /**
     * Sets the y-axis max when all the values in the diagram are set. Makes it dynamic.
     */
    public void setMaxYAxis(){
        chart.getAxisLeft().setAxisMaxValue((float)
                (Math.ceil(diagramBarData.getHighestValue() * 2) / 2));
    }
    public void redrawDiagram(){
        chart.invalidate();
    }


}
