package com.example.spoti5.ecobussing.Activites;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.BusData.Bus;
import com.example.spoti5.ecobussing.BusData.Busses;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.NetworkStateChangeReciever;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WifiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WifiFragment extends Fragment implements PropertyChangeListener {

    private OnFragmentInteractionListener mListener;
    private TextView con;
    private TextView dwg;
    private TextView vinnr;
    private TextView regnr;
    private TextView mac;
    private TextView onBus;

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

        NetworkStateChangeReciever wifiReciever = NetworkStateChangeReciever.getInstance();
        wifiReciever.addPropertyChangeListener(this);

        if(wifiReciever.isConnectedToWifi()){
            setConnected(wifiReciever.getBssid());
        }else{
            setDisconnected();
        }


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
