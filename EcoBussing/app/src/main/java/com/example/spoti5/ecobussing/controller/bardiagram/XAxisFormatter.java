package com.example.spoti5.ecobussing.controller.bardiagram;

import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by Emil Axelsson on 16/10/15.
 *
 * The x-axis in the BarDiagram needs a "XAxisValueFormatter". This class does not do anything really.
 * Just needed.
 */
public class XAxisFormatter implements XAxisValueFormatter {

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        return original; // just return original, no adjustments
    }
}
