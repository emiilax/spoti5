package com.example.spoti5.ecobussing;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
    private ViewPager viewPager;
    CustomSwipeAdapter swipeAdapter;

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
        TextView ageView = (TextView)view.findViewById(R.id.ageView);
        TextView usernameView = (TextView)view.findViewById(R.id.usernameView);
        TextView positionView = (TextView)view.findViewById(R.id.positionView);

        nameView.setText(currentUser.getName());
        ageView.setText(Integer.toString(currentUser.getAge()) + " år");
        positionView.setText("Plats: " + Integer.toString(currentUser.getPosition()));
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
