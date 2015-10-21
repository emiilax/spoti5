package com.example.spoti5.ecobussing.Activites;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.spoti5.ecobussing.APIRequest.BusConnection;
import com.example.spoti5.ecobussing.BusData.Bus;
import com.example.spoti5.ecobussing.BusData.Busses;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.NetworkStateChangeReciever;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WifiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WifiFragment extends Fragment implements PropertyChangeListener, View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private TextView con;
    private TextView dwg;
    private TextView vinnr;
    private TextView regnr;
    private TextView mac;
    private TextView onBus;

    private Button startStop;
    private TextView start;
    private TextView stop;
    private TextView status;
    private TextView distance;
    private BusConnection busC = new BusConnection();

    public WifiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wifi_detect, container, false);

        con = (TextView) view.findViewById(R.id.connected);
        dwg = (TextView) view.findViewById(R.id.dwg);
        vinnr = (TextView) view.findViewById(R.id.vinnr);
        regnr = (TextView) view.findViewById(R.id.regnr);
        mac = (TextView) view.findViewById(R.id.mac);
        onBus = (TextView) view.findViewById(R.id.onbus);

        startStop = (Button)view.findViewById(R.id.btnStartStop);
        start = (TextView) view.findViewById(R.id.txtvStart);
        stop = (TextView) view.findViewById(R.id.txtvStop);
        status = (TextView) view.findViewById(R.id.txtvStatus);
        distance = (TextView) view.findViewById(R.id.txtvDistance);

        startStop.setOnClickListener(this);

        NetworkStateChangeReciever wifiReciever = NetworkStateChangeReciever.getInstance();
        wifiReciever.addPropertyChangeListener(this);

        if(wifiReciever.isConnectedToWifi()){
            setConnected(wifiReciever.getBssid());
        }else{
            setDisconnected();
        }

        /*
        IDatabase curDatab = DatabaseHolder.getDatabase();

        IUser usr = curDatab.getUser("et@mannen.se");
        System.out.println(usr.toString());


        Random rnd = new Random();
        Calendar cal = Calendar.getInstance();
        for(int i = 0; i < 210; i++){

            int distance = rnd.nextInt(5000);


            ((User)usr).updateSpecDate(distance, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));

            cal.add(Calendar.DAY_OF_MONTH, -1);

        }
        SaveHandler.changeUser(usr);
        */



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event.getPropertyName().equals("connected")){
            setConnected((String)event.getNewValue());
        }
        if(event.getPropertyName().equals("disconnected")){
            setDisconnected();
        }

    }

    public void setConnected(String bssid){
        boolean onTheBus = false;
        for(Bus b: Busses.theBusses){
            if(bssid.equals(b.getMacAdress())){
                dwg.setText(b.getDwg());
                vinnr.setText(b.getVIN());
                regnr.setText(b.getRegNr());
                mac.setText(b.getMacAdress());
                onTheBus = true;

            }
        }
        mac.setText(bssid);
        if(onTheBus){
            onBus.setText("On bus!");
        }else{
            onBus.setText("Not on bus!");
        }

        con.setText("Connected");
        con.setTextColor(Color.GREEN);


    }

    public void setDisconnected(){

        dwg.setText("no dwg");
        vinnr.setText("no vinnr");
        regnr.setText("no regnr");
        mac.setText("no mac");

        onBus.setText("Not on bus!");
        con.setText("Disconnected");
        con.setTextColor(Color.RED);

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
                    return;
                } catch (NullPointerException e) {
                    System.out.println("null");
                    hasStarted = false;
                    return;
                }
                startStop.setText("Stop");

                status.setText("Journey start");

            }else{
                hasStarted = false;
                try {
                    busC.endJourney();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startStop.setText("Start");
                stop.setText(busC.getEndLoc());
                status.setText("Journey end");
                distance.setText(Double.toString(busC.getDistance()));


            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
