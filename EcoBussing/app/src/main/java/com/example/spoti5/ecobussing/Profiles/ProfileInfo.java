package com.example.spoti5.ecobussing.Profiles;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileInfo extends Fragment {

    private IUser currentUser;

    public ProfileInfo() {

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
        currentUser = SaveHandler.getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        setDataStrings(view);
        return view;
    }

    private void setDataStrings(View view){
        TextView nameView = (TextView)view.findViewById(R.id.nameView);
        TextView ageView = (TextView)view.findViewById(R.id.ageView);
        TextView positionView = (TextView)view.findViewById(R.id.positionView);

        nameView.setText(currentUser.getName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
