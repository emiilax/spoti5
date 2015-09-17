package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hilden on 2015-09-17.
 * A class to store the animations that displays your increase in saved carbondioxid and cash.
 *
 */
public class SaveAnimation extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saveanimation);
    }

    public void onScreenTouch(View v) {
        if(v.getId() == R.id.saveAnimationBackground) {
            this.finish();
        }
    }
}