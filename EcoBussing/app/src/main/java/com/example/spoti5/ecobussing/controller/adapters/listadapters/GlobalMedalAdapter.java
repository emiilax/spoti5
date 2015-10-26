package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.medals.GlobalMedal;
import com.example.spoti5.ecobussing.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 2015-10-13.
 */
public class GlobalMedalAdapter extends BaseAdapter {
    private List<String> medalNames;
    private GlobalMedal global;
    private TextView title;
    private TextView currentText;
    private TextView maxText;
    private ImageView medalImage;
    private ProgressBar progBar;
    private Context context;

    public GlobalMedalAdapter(Context context){
        this.context = context;
        medalNames = new ArrayList<>();
        global = GlobalMedal.getInstance();

        for(String item: context.getResources().getStringArray(R.array.global_medals)){
            medalNames.add(item);
        }
    }

    @Override
    public int getCount() {
        return medalNames.size();
    }

    @Override
    public Object getItem(int position) {
        return medalNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = null;

        if(convertView == null){

            row = inflater.inflate(R.layout.medal_view, parent, false);

        }else{
            row = convertView;
        }

        title = (TextView)row.findViewById(R.id.medalTitle);
        currentText = (TextView)row.findViewById(R.id.currentText);
        maxText = (TextView)row.findViewById(R.id.maxText);
        medalImage = (ImageView)row.findViewById(R.id.medalImage);
        progBar = (ProgressBar)row.findViewById(R.id.medalProgress);

        title.setText(medalNames.get(position));
        if(position == 0){
            return saveTogheterMedal(row);
        } else if(position == 1){
            return peopleMedal(row);
        }

        return row;
    }

    private View saveTogheterMedal(View row) {

        DecimalFormat df = new DecimalFormat("#.00");

        String current = df.format(global.getCurrentCO2Value()) + "kg CO2";
        df.setMinimumFractionDigits(0);
        String full = df.format(global.getFullCO2Value()/10) + "ton CO2";

        currentText.setText(current);
        maxText.setText(full);

        int co2TotPer= global.getCO2TotPercentage();
        if(co2TotPer >=100){
            medalImage.setImageResource(R.drawable.star);
        }else {
            medalImage.setImageResource(R.drawable.stargrey);
        }
        progBar.setProgress(co2TotPer);
        return row;
    }

    private View peopleMedal(View row) {
        String current = Integer.toString(global.getCurrentPeople()) + " människor";
        String max = Integer.toString(global.getMaxPeople()) + " människor";

        currentText.setText(current);
        maxText.setText(max);

        int peoplePer = global.getPeoplePercantage();
        if(peoplePer>=100){
            medalImage.setImageResource(R.drawable.peoplemedal);
        }else {
            medalImage.setImageResource(R.drawable.peoplemedalgreysmall);
        }
        progBar.setProgress(peoplePer);

        return row;
    }
}
