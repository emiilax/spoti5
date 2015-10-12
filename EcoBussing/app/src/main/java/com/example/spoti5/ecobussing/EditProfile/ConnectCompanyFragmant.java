package com.example.spoti5.ecobussing.EditProfile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.R;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ConnectCompanyFragmant extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_connect_company,container,false);
        return v;
    }

}
