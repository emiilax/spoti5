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
 * A class to store the animations that displays your increase in saved carbondioxid and cash.
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

        SaveHandler.getCurrentUser().resetCurrentDistance();
        SaveHandler.getCurrentUser().incCurrentDistance(2);
        currentDistance = SaveHandler.getCurrentUser().getCurrentDistance();

        if (currentDistance > 0) {
            setOverviewText();
        } else {
            startMainActivity();
        }
    }

    private void setOverviewText() {
        //double CO2Saved = SaveHandler.getCurrentUser().getCO2Saved(true);
        double currentCO2Saved = Calculator.getCalculator().calculateCarbonSaved(currentDistance);
        textViewDistance.setText(Integer.toString((int)currentDistance) + " km");
        textViewCarbon.setText("+" + Integer.toString((int)currentCO2Saved) + " mg");
        double totCO2Saved = SaveHandler.getCurrentUser().getCO2Saved(true);
        animateTextView((int) totCO2Saved, (int) (currentCO2Saved + totCO2Saved), textViewTotal);
    }

    /**
     * The animation of increasing a number until it reaches another number.
     * Should probably be moved, maybe?
     */
    private void animateTextView(int initialValue, int finalValue, final TextView  textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) initialValue, (int) finalValue);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString() + " mg");
            }
        });
        valueAnimator.start();
    }

    public void onScreenTouch(View v) {
        if(v.getId() == R.id.overviewBackground) {
            startMainActivity();
            SaveHandler.getCurrentUser().resetCurrentDistance();
        }
    }
}