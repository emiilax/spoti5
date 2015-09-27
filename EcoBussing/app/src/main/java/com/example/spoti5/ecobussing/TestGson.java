package com.example.spoti5.ecobussing;

import android.widget.TextView;

/**
 * Created by emilaxelsson on 27/09/15.
 */
public class TestGson {
    private String value;

    public TestGson(String value){
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;


    }

    public String toString(){
        return "Busstop: " + value;
    }
}
