package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.spoti5.ecobussing.controller.viewcontroller.activities.MainActivity;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.io.net.apirequest.Calculator;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.profile.Company;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
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
public class ProfileViewFragment extends Fragment{
    private View view;
    private ViewPager viewPager1;
    private ViewPager viewPager2;
    private IProfile thisProfile;
    private static final Calculator calc = Calculator.getCalculator();

    public ProfileViewFragment() {
        // Required empty public constructor
    }

    public static ProfileViewFragment newInstance(IProfile ip)
    {
        ProfileViewFragment f = new ProfileViewFragment();
        f.setThisProfile(ip);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        view = setMPagerAdapter(view);
        Button connectCompanyButton = (Button)view.findViewById(R.id.connectButton_company);
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

    private View setMPagerAdapter(View viewen) {

        setupPager(viewen, (ViewPager)view.findViewById(R.id.profilePager), 1, false);
        setupPager(viewen, (ViewPager)view.findViewById(R.id.profilePager2), 2, true);
        return viewen;
    }

    private void setupPager(View viewen, ViewPager vp, int n, boolean isMoney){
        ProfilePagerAdapter pa = new ProfilePagerAdapter(getChildFragmentManager(), thisProfile, isMoney);
        vp.setAdapter(pa);
        vp.addOnPageChangeListener(new ProfilePagerListener(viewen, n));
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

        TableRow botRow = (TableRow)view.findViewById(R.id.profile_bot_row);

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

            botRow.setVisibility(View.GONE);
            infoText.setVisibility(View.GONE);
        }
        //Does other stuff if the profile is for a company
        else{
            Company currentCompany = (Company)thisProfile;
            System.out.println(currentCompany.getName());
            co2 = currentCompany.getCO2Saved();

            infoText.setVisibility(View.VISIBLE);


            IUser currentUser = SaveHandler.getCurrentUser();
            if(!(currentUser.getCompany().equals(""))){
                botRow.setVisibility(View.GONE);
            }else{
                botRow.setVisibility(View.VISIBLE);
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

    @Override
    public void onResume() {
        setMPagerAdapter(view);
        super.onResume();
    }

    private void setThisProfile(IProfile ip){
        thisProfile = ip;
    }
}

