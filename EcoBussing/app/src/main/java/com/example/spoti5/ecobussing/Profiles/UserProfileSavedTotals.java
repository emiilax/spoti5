package com.example.spoti5.ecobussing.Profiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

public class UserProfileSavedTotals extends Fragment {

    Calculator calc = Calculator.getCalculator();

    private IUser currentUser;

    public UserProfileSavedTotals() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentUser = SaveHandler.getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_user_profile_saved_totals, container, false);

        setDataStrings(view);
        return view;
    }

    private void setDataStrings(View view){
        int co2 = (int)currentUser.getCo2Tot();
        int distance = (int)calc.calculateDistanceFromCO2(co2);

        TextView co2View = (TextView)view.findViewById(R.id.textCo2);
        co2View.setText(Integer.toString(co2) + " g");

        TextView distanceView = (TextView) view.findViewById(R.id.textDistance);
        distanceView.setText(Integer.toString(distance) + " m");

        TextView moneyView = (TextView)view.findViewById(R.id.textMoney);
        moneyView.setText(Double.toString(currentUser.getMoneySaved(true)) + " kr");
    }

}
