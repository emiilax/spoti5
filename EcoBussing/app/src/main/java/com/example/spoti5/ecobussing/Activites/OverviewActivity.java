package com.example.spoti5.ecobussing.Activites;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.calculations.Calculator;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

/**
 * Created by hilden on 2015-09-17.
 * A class to store the animations that displays your increase in saved carbondioxide.
 *
 */
public class OverviewActivity extends ActivityController {

    private double currentDistance;

    private TextView textViewDistance;
    private TextView textViewCarbon;
    private TextView textViewTotal;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        textViewDistance = (TextView) findViewById(R.id.distanceTraveled);
        textViewCarbon = (TextView) findViewById(R.id.carbonSaved);
        textViewTotal = (TextView) findViewById(R.id.totalCarbon);

        /*
            Just to pre-init the database
         */
        DatabaseHolder.getDatabase();

       setOverviewText();
    }

    private void setOverviewText() {
        System.out.println("-------------------------------------------------------------------------- " + currentDistance);
        currentDistance = SaveHandler.getCurrentUser().getCurrentDistance();
        double currentCO2Saved = Calculator.getCalculator().calculateCarbonSaved(currentDistance);
        double totCO2Saved = SaveHandler.getCurrentUser().getCO2Saved();

        textViewDistance.setText(Integer.toString((int)currentDistance) + " m");
        textViewCarbon.setText("+ " + Double.toString(transformValue(currentCO2Saved)) + " kg");
        animateTextView((totCO2Saved - currentCO2Saved)*1000, (totCO2Saved)*1000, textViewTotal);
    }

    private double transformValue(double value) {
        value = value * 1000;
        int tempValue = (int)value;
        value = (double)tempValue/1000;
        return value;
    }

    /**
     * The animation of increasing a number until it reaches another number.
     * Should probably be moved, maybe?
     */
    private void animateTextView(double initialValue, double finalValue, final TextView  textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int)initialValue, (int)finalValue);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                String tempString = "";
                int valueLength = valueAnimator.getAnimatedValue().toString().length();
                for (int i = 0; i < valueLength; i++) {
                    tempString = tempString + valueAnimator.getAnimatedValue().toString().charAt(i);
                    if (i == valueLength - 4) {
                        tempString = tempString + ".";
                    }
                }
                textview.setText(tempString + " kg");
            }
        });
        valueAnimator.start();
    }

    public void onScreenTouch(View v) {
        if(v.getId() == R.id.overviewBackground) {
            SaveHandler.getCurrentUser().resetCurrentDistance();
            SaveHandler.SaveUser();
            startMainActivity();
        }
    }
}