package com.example.spoti5.ecobussing.Profiles;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.R;

import java.text.DecimalFormat;

/**
 * This is the view for the user profile. It contains a ViewPager. The ViewPager can show different
 * fragments and if a fragment is added more cases needs to be added to the method OnPageSelected().
 * New fragments are added in the ProfilePagerAdapter-class.
 *
 *  Created by Hampus on 2015-10-12.
 */
public class ProfileView extends Fragment implements ViewPager.OnPageChangeListener{
    private FragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private static View view;
    private ImageView dot1, dot2, dot3;
    private static IProfile thisProfile;
    private static Calculator calc = Calculator.getCalculator();

    public ProfileView() {
        // Required empty public constructor
    }

    public static final ProfileView newInstance(IProfile ip)
    {
        ProfileView f = new ProfileView();
        f.setThisProfile(ip);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        setMPagerAdapter();


        dot1 = (ImageView) view.findViewById(R.id.img_dot1);
        dot2 = (ImageView) view.findViewById(R.id.img_dot2);
        dot3 = (ImageView) view.findViewById(R.id.img_dot3);

        setDataStrings(view);

        return view;
    }

    private void setMPagerAdapter() {
        if(thisProfile instanceof IUser) {
            mPagerAdapter = new ProfilePagerAdapter(getActivity().getSupportFragmentManager());
        }else {
            mPagerAdapter = new CompanyPagerAdapter(getActivity().getSupportFragmentManager());
        }
        mViewPager = (ViewPager)view.findViewById(R.id.profile_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    public static void setDataStrings(View view) {
        DecimalFormat df2 = new DecimalFormat("#.00");
        DecimalFormat df0 = new DecimalFormat("#");

        TextView nameView = (TextView)view.findViewById(R.id.profile_name);
        TextView companyNameView = (TextView)view.findViewById(R.id.user_company);
        TextView distanceView = (TextView) view.findViewById(R.id.textDistance);
        TextView co2View = (TextView)view.findViewById(R.id.textCo2);
        TextView moneyView = (TextView)view.findViewById(R.id.textMoney);

        double co2;
        double distance;
        double money = 0;

        nameView.setText(thisProfile.getName());

        /**
         * there is a better way of doing this w/o using instanceof, I don't remember how, but I remeber
         * you should stay away from using instanceof in this manner, so this should be fixed.
         */
        if(thisProfile instanceof IUser){
            IUser currentUser = (IUser) thisProfile;
            co2 = currentUser.getCO2Saved(true);
            distance = calc.calculateDistanceFromCO2(co2);
            money = currentUser.getMoneySaved(true);

            if(((IUser) thisProfile).getCompany().length() > 0) {
                companyNameView.setText(((IUser) thisProfile).getCompany());
            }else{
                companyNameView.setText("Ej ansluten till något företag");
            }
        }else{
            Company currentCompany = (Company)thisProfile;
            co2 = currentCompany.getCO2Saved(true);
            distance = calc.calculateDistanceFromCO2(co2);
            //Maybe money shouldn't be displayed in a company profile? Then there should only be 2
            //fields visible there.
            money = 23475;
            companyNameView.setText(null);
        }


        if(distance > 1000){
            distance = distance/1000;
            String distanceS = df2.format(distance);
            distanceView.setText(distanceS + " km");
        }else {
            String d = df0.format(distance);
            distanceView.setText(d + " m");
        }


        String co2S = df2.format(co2);
        co2View.setText(co2S + " kg");

        String moneyS = df0.format(money);
        moneyView.setText(moneyS + " kr");
    }

    @Override
    public void onPageSelected(int position) {
        switch(position){
            case 0:
                setBigDot(dot1);
                setSmallDot(dot2);
                setSmallDot(dot3);
                break;

            case 1:
                setSmallDot(dot1);
                setBigDot(dot2);
                setSmallDot(dot3);
                break;
            case 2:
                setSmallDot(dot1);
                setSmallDot(dot2);
                setBigDot(dot3);
                break;
        }
    }

    private void setBigDot (ImageView dot){
        dot.setImageResource(R.drawable.dot_grey_big);
        dot.setAlpha(1f);
    }

    private void setSmallDot (ImageView dot){
        dot.setImageResource(R.drawable.dot_grey_small);
        dot.setAlpha(0.7f);
    }

    public void setThisProfile(IProfile ip){
        thisProfile = ip;
    }

    public interface OnFragmentInteractionListener {public void onFragmentInteraction(Uri uri);}

    // Theses methods are not used but needed because of the OnPageChangeListener implementation
    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

}

