package com.example.spoti5.ecobussing.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;


/**
 * Fragment shown in the ViewPager in ProfileView. This Fragment contains some user info and
 * a profile picture.
 */
public class UserProfileInfo extends Fragment {

    private IUser currentUser;

    public UserProfileInfo() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentUser = SaveHandler.getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_user_profile_info, container, false);

        setDataStrings(view);
        return view;
    }

    private void setDataStrings(View view){
        TextView nameView = (TextView)view.findViewById(R.id.nameView);
        nameView.setText(currentUser.getName());
    }
}
