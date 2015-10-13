package com.example.spoti5.ecobussing.Activites;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by hilden on 2015-09-17.
 * A class to store the animations that displays your increase in saved carbondioxid and cash.
 *
 */
public class OverviewActivity extends ActivityController {

    private String carbonSaved;

    private double CO2Saved;
    private double currentDistance;
    private double totalDistance;

    private boolean tappedBefore;
    private TextView overviewTextView1;
    private TextView overviewTextView2;
    private TextView overViewTextView3;
    private TextView overViewTextView4;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        overviewTextView1 = (TextView) findViewById(R.id.overviewtextview1);
        overviewTextView2 = (TextView) findViewById(R.id.overviewtextview2);
        overViewTextView3 = (TextView) findViewById(R.id.overviewtextview3);
        overViewTextView4 = (TextView) findViewById(R.id.overviewtextview4);

        CO2Saved = SaveHandler.getCurrentUser().getCO2Saved(true);
        currentDistance = SaveHandler.getCurrentUser().getCurrentDistance();

        if (currentDistance > 0) {
            setOverviewText1();
            tappedBefore = false;
        } else {
            setOverviewText2();
            tappedBefore = true;
        }
    }

    private void updateSaveHandler() {
       // SaveHandler.getCurrentUser().incCO2Saved(Calculator.getCalculator().getCurrentCarbonSaved());
       // SaveHandler.getCurrentUser().incMoneySaved(Calculator.getCalculator().getCurrentMoneySaved());
       // SaveHandler.getCurrentUser().updateDistance();
       // SaveHandler.getCurrentUser().resetCurrentDistance();
    }

    private void setOverviewText1() {
        double CO2Saved = SaveHandler.getCurrentUser().getCO2Saved(true);
        overviewTextView1.setText("Du har åkt " + Double.toString(currentDistance) + " km kollektivt");
        overViewTextView4.setText("Genom att göra det har du sparat " + carbonSaved + " mg koldioxid!");
    }

    private void setOverviewText2() {
        double totCO2Saved = SaveHandler.getCurrentUser().getCO2Saved(true);

       // overviewTextView1.setVisibility(View.INVISIBLE);
        animateTextView((int) totCO2Saved, (int) (CO2Saved + totCO2Saved), overViewTextView3);
        overviewTextView2.setText("+" + Integer.toString((int) CO2Saved));
    }

    /**
     * The animation of increasing a number until it reaches another number.
     * Should probably be moved, maybe?
     */
    private void animateTextView(int initialValue, int finalValue, final TextView  textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) initialValue, (int) finalValue);
        valueAnimator.setDuration(1200);
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
            if (!tappedBefore) {
                setOverviewText2();
                tappedBefore = true;
            } else {
                updateSaveHandler();
                startMainActivity();
            }
        }
    }
}