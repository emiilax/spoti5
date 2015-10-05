package com.example.spoti5.ecobussing.SwipeScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.R;

/**
 * Created by emilaxelsson on 03/10/15.
 */
public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Fragment created");
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);
        return view;
    }
}
