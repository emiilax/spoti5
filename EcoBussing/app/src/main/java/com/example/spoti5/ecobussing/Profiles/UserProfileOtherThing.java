package com.example.spoti5.ecobussing.Profiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.spoti5.ecobussing.R;

/**
 * Fragment shown in the ViewPager in ProfileView. This Fragment contains a graph atm.
 * UserProfileOtherThing should be renamed at some point.
 */
public class UserProfileOtherThing extends Fragment {

    public UserProfileOtherThing() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_other_thing, container, false);
        ImageView graph = (ImageView)view.findViewById(R.id.graphView);
        graph.setImageResource(R.drawable.graph);
        return view;
    }
}

