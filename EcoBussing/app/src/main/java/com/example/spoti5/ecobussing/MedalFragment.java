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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Activites.GlobalMedalAdapter;

import java.text.DecimalFormat;

/**
 * Created by Erik on 2015-10-17.
 */
public class MedalFragment extends Fragment {
    private Activity currentActivity;
    private View view;
    private ListView globalList;

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
        view = inflater.inflate(R.layout.medal_list, container, false);

        currentActivity = getActivity();
        globalList = (ListView)view.findViewById(R.id.medalList);
        GlobalMedalAdapter globalAdapter = new GlobalMedalAdapter(currentActivity);
        globalList.setAdapter(globalAdapter);

        return view;
    }


}
