package com.example.spoti5.ecobussing.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spoti5.ecobussing.controller.apirequest.BusConnection;
import com.example.spoti5.ecobussing.Activites.ActivityController;
import com.example.spoti5.ecobussing.model.bus.Busses;
import com.example.spoti5.ecobussing.R;

import java.io.IOException;

/**
 * Created by emilaxelsson on 21/10/15.
 */
public class SimulatedTripFragment extends Fragment implements View.OnClickListener {

    private Button startStop;
    private TextView start;
    private TextView stop;
    private TextView status;
    private TextView distance;

    private BusConnection busC = new BusConnection();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_simulate_trip,container,false);

        startStop = (Button)view.findViewById(R.id.btnStartTrip);
        start = (TextView) view.findViewById(R.id.txtvStart);
        stop = (TextView) view.findViewById(R.id.txtvStop);
        status = (TextView) view.findViewById(R.id.txtvStatus);
        distance = (TextView) view.findViewById(R.id.txtvDistance);

        startStop.setOnClickListener(this);


        return view;
    }

    public void showToast(){
        Toast toast = Toast.makeText(ActivityController.getContext(), "Bussen befinner sig mellan två stationer, försök igen om en liten stund",
                Toast.LENGTH_LONG);
        TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);

        if( tv != null) tv.setGravity(Gravity.CENTER);
        toast.show();
    }

    boolean hasStarted;
    @Override
    public void onClick(View v) {
        if(v == startStop){
            if(!hasStarted){
                hasStarted = true;
                start.setText("not set");
                stop.setText("not set");
                status.setText("not set");
                distance.setText("not set");
                try {
                    busC.beginJourey(Busses.simulated);
                    start.setText(busC.getStartLoc());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("null");
                    hasStarted = false;
                    showToast();
                    return;
                } catch (NullPointerException e) {
                    System.out.println("null");
                    hasStarted = false;
                    showToast();
                    return;
                }
                startStop.setText("Stoppa resan");
                showToast();

                status.setText("Journey start");

            }else{
                hasStarted = false;
                try {
                    busC.endJourney();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startStop.setText("Starta resan");
                stop.setText(busC.getEndLoc());
                status.setText("Journey end");
                distance.setText(Double.toString(busC.getDistance()/1000) + " km");


            }
        }
    }
}
