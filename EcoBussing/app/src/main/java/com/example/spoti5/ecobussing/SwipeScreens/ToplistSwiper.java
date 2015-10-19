package com.example.spoti5.ecobussing.SwipeScreens;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spoti5.ecobussing.APIRequest.BusConnection;
import com.example.spoti5.ecobussing.R;

/**
 * Created by emilaxelsson on 03/10/15.
 */
public class ToplistSwiper extends Fragment implements View.OnClickListener {

    //

    private FragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Button btnMonth;
    private Button btnYear;
    private Button btnTotal;
    private TabLayout tabLayout;
    private View view;

    private BusConnection busC = new BusConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toplist_swipe, container, false);
        System.out.println("Create");
        //this will be our swipe/tab view populated with each checkout
        //piece fragment
        mPagerAdapter =
                new ToplistPagerAdapter(
                        getActivity().getSupportFragmentManager(), "month");
        mViewPager = (ViewPager)view.findViewById(R.id.pager_toplist);
        mViewPager.setAdapter(mPagerAdapter);
        
        fixTabLayout();

        // Find buttons
        btnMonth = (Button) view.findViewById(R.id.swipe_btnMonth);
        btnYear = (Button) view.findViewById(R.id.swipe_btnYear);
        btnTotal = (Button) view.findViewById(R.id.swipe_btnTotal);
        
        // Add clicklisteners
        btnMonth.setOnClickListener(this);
        btnYear.setOnClickListener(this);
        btnTotal.setOnClickListener(this);

        btnMonth.setBackgroundColor(getResources().getColor(R.color.secondary));

        return view;
    }
    
    public void fixTabLayout(){
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs_toplist);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
    }

    @Override
    public void onClick(View v) {
        btnMonth.setBackgroundColor(getResources().getColor(R.color.third));
        btnYear.setBackgroundColor(getResources().getColor(R.color.third));
        btnTotal.setBackgroundColor(getResources().getColor(R.color.third));



        if(v == btnMonth){
            btnMonth.setBackgroundColor(getResources().getColor(R.color.secondary));

            //((ToplistPagerAdapter)mPagerAdapter).setRange("month");

            ((ToplistPagerAdapter)mPagerAdapter).setRange("month");
            //mPagerAdapter = new ToplistPagerAdapter(getActivity().getSupportFragmentManager(), "month");
            /*try {
                busC.beginJourey(Busses.simulated);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        if(v == btnYear){
            btnYear.setBackgroundColor(getResources().getColor(R.color.secondary));

            ((ToplistPagerAdapter)mPagerAdapter).setRange("year");

            //mPagerAdapter = new ToplistPagerAdapter(getActivity().getSupportFragmentManager(), "year");

            //((ToplistPagerAdapter)mPagerAdapter).setRange("month");

            /*
            try {
                busC.endJourney();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        if(v == btnTotal){
            mPagerAdapter = new ToplistPagerAdapter(getActivity().getSupportFragmentManager(), "total");
            //((ToplistPagerAdapter)mPagerAdapter).setRange("total");
            btnTotal.setBackgroundColor(getResources().getColor(R.color.secondary));

        }
        mPagerAdapter.notifyDataSetChanged();

        //mViewPager.setAdapter(mPagerAdapter);

    }
}

