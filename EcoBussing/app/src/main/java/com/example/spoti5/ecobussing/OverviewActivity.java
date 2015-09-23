package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        getOverviewText();

        TextView overviewTextView = (TextView) findViewById(R.id.overviewtextview);
        overviewTextView.setText(carbonSaved);
    }

    private void getOverviewText() {
        double co2Saved = Calculator.getCalculator().getCarbonSaved();

        if (co2Saved > 0) {
            carbonSaved = "Du har sparat " + Double.toString(co2Saved) + "g koldioxid sen senaste starten!";
        } else {
            carbonSaved = "Du har inte sparat någonting din tölp.";
        }
    }

    public void onScreenTouch(View v) {
        if(v.getId() == R.id.overviewBackground) {
            this.finish();
        }
    }
}