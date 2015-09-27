package com.example.spoti5.ecobussing;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by hilden on 2015-09-17.
 * A class to store the animations that displays your increase in saved carbondioxid and cash.
 *
 */
public class OverviewActivity extends Activity {

    private String carbonSaved;
    private boolean tappedBefore;
    private TextView overviewTextView1;
    private TextView overviewTextView2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        overviewTextView1 = (TextView) findViewById(R.id.overviewtextview1);
        overviewTextView2 = (TextView) findViewById(R.id.overviewtextview2);

        setOverviewText1();
        tappedBefore = false;
    }

    private void updateSaveHandler() {
        SaveHandler.getCurrentUser().incCO2Saved(Calculator.getCalculator().getCurrentCarbonSaved());
        SaveHandler.getCurrentUser().incMoneySaved(Calculator.getCalculator().getCurrentMoneySaved());
        SaveHandler.getCurrentUser().updateDistance();
        SaveHandler.getCurrentUser().resetCurrentDistance();
    }

    private void setOverviewText1() {
        double CO2Saved = Calculator.getCalculator().getCurrentCarbonSaved();

        if (CO2Saved > 0) {
            overviewTextView1.setText("Du har sparat " + Integer.toString((int)CO2Saved) + "mg koldioxid sen senaste starten!");
        } else {
            overviewTextView1.setText("Du har inte sparat någonting din tölp.");
        }
    }

    private void setOverviewText2() {
        double totCO2Saved = SaveHandler.getCurrentUser().getCO2Saved();
        double CO2Saved = Calculator.getCalculator().getCurrentCarbonSaved();

        animateTextView((int)totCO2Saved, (int)(CO2Saved+totCO2Saved), overviewTextView1);
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
                textview.setText("Totalt: " + valueAnimator.getAnimatedValue().toString() + " mg");
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
                this.finish();
            }
        }
    }
}