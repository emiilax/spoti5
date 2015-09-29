package com.example.spoti5.ecobussing;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private User currentUser;
    private View currentView;

    public ProfileFragment() {

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setDataStrings(view);
        return view;
    }

    private void setDataStrings(View view){
        TextView nameView = (TextView)view.findViewById(R.id.nameView);
        TextView emailView = (TextView)view.findViewById(R.id.emailView);
        TextView usernameView = (TextView)view.findViewById(R.id.usernameView);

        nameView.setText(currentUser.getName());
        emailView.setText(currentUser.getEmail());
        usernameView.setText(currentUser.getUsername());
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
