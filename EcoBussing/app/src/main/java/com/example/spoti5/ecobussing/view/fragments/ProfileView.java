package com.example.spoti5.ecobussing.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.spoti5.ecobussing.Activites.MainActivity;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.controller.calculations.Calculator;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.listeners.ProfilePagerListener;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.adapters.pageradapter.ProfilePagerAdapter;

import java.text.DecimalFormat;

/**
 * This is the view for the user profile. It contains a ViewPager. The ViewPager can show different
 * fragments and if a fragment is added more cases needs to be added to the method OnPageSelected().
 * New fragments are added in the ProfilePagerAdapter-class.
 *
 *  Created by Hampus on 2015-10-12.
 */
public class ProfileView extends Fragment{
    private static View view;
    private static IProfile thisProfile;
    private static final Calculator calc = Calculator.getCalculator();
    private Button connectCompanyButton;

    public ProfileView() {
        // Required empty public constructor
    }

    public static ProfileView newInstance(IProfile ip)
    {
        ProfileView f = new ProfileView();
        f.setThisProfile(ip);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        view = setMPagerAdapter(view);
        connectCompanyButton = (Button)view.findViewById(R.id.connectButton_company);
        connectCompanyButton.setOnClickListener(connectToCompany);

        setDataStrings(view);

        return view;
    }

    private final View.OnClickListener connectToCompany = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IUser currentUser = SaveHandler.getCurrentUser();

            Company company = (Company)thisProfile;
            company.addMember(currentUser);
            currentUser.setCompany(company.getName());
            SaveHandler.changeUser(currentUser);
            DatabaseHolder.getDatabase().updateCompany(company);
            MainActivity currentActivity = (MainActivity)getActivity();
            currentActivity.changeToProfileFragment(thisProfile, thisProfile.getName());
            currentActivity.updateList(false);
        }
    };

    //This should be changed a bit to look better, Hampus fix
    private View setMPagerAdapter(View viewen) {
        PagerAdapter pagerAdapter1, pagerAdapter2;
        if(thisProfile instanceof IUser) {
            pagerAdapter1 = new ProfilePagerAdapter(getActivity().getSupportFragmentManager(), thisProfile, false);
            pagerAdapter2 = new ProfilePagerAdapter(getActivity().getSupportFragmentManager(), thisProfile, true);


        }else {
            pagerAdapter1 = new ProfilePagerAdapter(getActivity().getSupportFragmentManager(), thisProfile, false);
            pagerAdapter2 = new ProfilePagerAdapter(getActivity().getSupportFragmentManager(), thisProfile, true);
        }

        ViewPager viewPager1 = (ViewPager)view.findViewById(R.id.profilePager);
        viewPager1.setAdapter(pagerAdapter1);
        viewPager1.addOnPageChangeListener(new ProfilePagerListener(viewen, 1));

        ViewPager viewPager2 = (ViewPager)view.findViewById(R.id.profilePager2);
        viewPager2.setAdapter(pagerAdapter2);
        viewPager2.addOnPageChangeListener(new ProfilePagerListener(viewen, 2));
        return viewen;
    }

    private void setDataStrings(View view) {
        DecimalFormat df2 = new DecimalFormat("#.00");
        DecimalFormat df0 = new DecimalFormat("#");

        TextView nameView = (TextView)view.findViewById(R.id.profile_name);
        TextView companyNameView = (TextView)view.findViewById(R.id.user_company);
        TextView distanceView = (TextView) view.findViewById(R.id.textDistance);
        TextView co2View = (TextView)view.findViewById(R.id.textCo2);
        TextView moneyView = (TextView)view.findViewById(R.id.textMoney);
        TextView topListPosView = (TextView)view.findViewById(R.id.positionOrEmployedNbr);
        TextView infoText = (TextView)view.findViewById(R.id.companyInfOTextView);

        IDatabase db = DatabaseHolder.getDatabase();

        double co2;
        double distance;
        double money;
        int position;

        nameView.setText(thisProfile.getName());

        //Does stuff if the profile is for a user
        if(thisProfile instanceof IUser){
            IUser currentUser = (IUser) thisProfile;
            co2 = currentUser.getCO2Saved();
            distance = calc.calculateDistanceFromCO2(co2);
            money = currentUser.getMoneySaved();
            position = db.getPosition(currentUser);

            String moneyS = df0.format(money);
            moneyView.setText(moneyS + " kr");

            if(distance > 1000){
                distance = distance/1000;
                String distanceS = df2.format(distance);
                distanceView.setText(distanceS + " km");
            }else {
                String d = df0.format(distance);
                distanceView.setText(d + " m");
            }

            if(((IUser) thisProfile).getCompany().length() > 0) {
                companyNameView.setText(((IUser) thisProfile).getCompany());
            }else{
                companyNameView.setText("Ej ansluten till något företag");
            }

            connectCompanyButton.setVisibility(View.GONE);
            infoText.setVisibility(View.GONE);
        }
        //Does other stuff if the profile is for a company
        else{
            Company currentCompany = (Company)thisProfile;
            System.out.println(currentCompany.getName());
            co2 = currentCompany.getCO2Saved();

            infoText.setVisibility(View.VISIBLE);

            /*
            view.findViewById(R.id.profilePager2).setVisibility(View.GONE);
            view.findViewById(R.id.dotRow2).setVisibility(View.GONE);
            view.findViewById(R.id.dividerGraph1).setVisibility(View.GONE);*/

            IUser currentUser = SaveHandler.getCurrentUser();
            if(!(currentUser.getCompany().equals(""))){
                connectCompanyButton.setVisibility(View.GONE);
            }else{
                connectCompanyButton.setVisibility(View.VISIBLE);
            }

            ((ImageView) view.findViewById(R.id.imageMoney)).setImageResource(R.drawable.business_point);
            ((ImageView)view.findViewById(R.id.imageDistance)).setImageResource(R.drawable.employees);
            infoText.setText(currentCompany.getCompanyInfo());

            //There's no intrest in showing a company's traveled distance, so here we show number of
            //employees instead.
            distance = currentCompany.getNbrEmployees();
            String distanceS = df0.format(distance);
            distanceView.setText(distanceS + " anställda");

            //A company doesn't have any money saved so points will be displayed instead.
            money = currentCompany.getpointTot();
            String moneyS = df0.format(money);
            moneyView.setText(moneyS + " pt");

            position = db.getPosition(currentCompany);

            companyNameView.setText(null);


        }


        String pos = Integer.toString(position);
        topListPosView.setText("#" + pos);

        String co2S = df2.format(co2);
        co2View.setText(co2S + " kg");


    }

    private void setThisProfile(IProfile ip){
        thisProfile = ip;
    }
}

