package com.example.spoti5.ecobussing.controller.swipers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.adapters.pageradapter.ToplistPagerAdapter;

/**
 * Created by Emil Axelsson on 03/10/15.
 *
 * Used to show the toplists. Its a swiper where you can swipe between persons and companys.
 */
public class ToplistSwiper extends Fragment implements View.OnClickListener {

    private FragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Button btnMonth;
    private Button btnYear;
    private Button btnTotal;
    private TabLayout tabLayout;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toplist_swipe, container, false);

        mPagerAdapter =
                new ToplistPagerAdapter(
                        getActivity().getSupportFragmentManager(), "month");
        mViewPager = (ViewPager)view.findViewById(R.id.pager_toplist);
        mViewPager.setAdapter(mPagerAdapter);

        // Set up the tab-layout
        fixTabLayout();

        // Find buttons
        btnMonth = (Button) view.findViewById(R.id.swipe_btnMonth);
        btnYear = (Button) view.findViewById(R.id.swipe_btnYear);
        btnTotal = (Button) view.findViewById(R.id.swipe_btnTotal);
        
        // Add clicklisteners
        btnMonth.setOnClickListener(this);
        btnYear.setOnClickListener(this);
        btnTotal.setOnClickListener(this);



        btnMonth.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.secondary));

        return view;
    }

    /**
     * Sets up the tablayout so it is synced with the pager.
     */
    public void fixTabLayout(){
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs_toplist);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
    }

    @Override
    public void onClick(View v) {
        btnMonth.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.third));
        btnYear.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.third));
        btnTotal.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.third));



        if(v == btnMonth){
            btnMonth.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.secondary));

            ((ToplistPagerAdapter)mPagerAdapter).setRange("month");

            // Ugly solution to update the view in the pageradapter. But it works
            mPagerAdapter.getItemPosition(new Object());
        }
        if(v == btnYear){
            btnYear.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.secondary));

            ((ToplistPagerAdapter)mPagerAdapter).setRange("year");

            // Ugly solution to update the view in the pageradapter. But it works
            mPagerAdapter.getItemPosition(new Object());
        }

        if(v == btnTotal){

            ((ToplistPagerAdapter)mPagerAdapter).setRange("total");

            // Ugly solution to update the view in the pageradapter. But it works
            mPagerAdapter.getItemPosition(new Object());
            btnTotal.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.secondary));

        }

    }
}

