package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Erik on 2015-10-17.
 */
public class MedalFragment extends Fragment {
    private Activity currentActivity;
    private GlobalMedal global;
    private ProgressBar co2TotBar;
    private ImageView co2TotStar;
    private View view;

    private ProgressBar peopleBar;
    private ImageView peopleImage;

    public MedalFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.medal_global, container, false);

        currentActivity = getActivity();
        global = GlobalMedal.getInstance();
        co2Medal();
        peopleMedal();

        setProgressBars();

        return view;
    }

    private void co2Medal(){
        co2TotBar = (ProgressBar)view.findViewById(R.id.co2TotBar);
        co2TotStar = (ImageView)view.findViewById(R.id.co2TotStar);
        DecimalFormat df = new DecimalFormat("#.00");

        String current = df.format(global.getCurrentCO2Value()/1000000) + "kg CO2";
        String full = df.format(global.getFullCO2Value()/1000000) + "kg CO2";
        ((TextView)view.findViewById(R.id.currentCO2Text)).setText(current);
        ((TextView)view.findViewById(R.id.maxCO2Text)).setText(full);
    }

    private void peopleMedal(){
        peopleBar = (ProgressBar)view.findViewById(R.id.peopleProgress);
        peopleImage = (ImageView)view.findViewById(R.id.peopleImage);

        String current = Integer.toString(global.getCurrentPeople()) + " människor";
        String max = Integer.toString(global.getMaxPeople()) + " människor";
        ((TextView)view.findViewById(R.id.currentPeopleText)).setText(current);
        ((TextView)view.findViewById(R.id.maxPeopleText)).setText(max);

    }

    private void setProgressBars() {
        int co2TotPer= global.getCO2TotPercentage();
        if(co2TotPer >=100){
            co2TotStar.setImageResource(R.drawable.star);
        }
        co2TotBar.setProgress(co2TotPer);

        int peoplePer = global.getPeoplePercantage();
        System.out.println(peoplePer);
        if(peoplePer>=100){
            peopleImage.setImageResource(R.drawable.peoplemedal);
        }
        peopleBar.setProgress(peoplePer);
    }

}
