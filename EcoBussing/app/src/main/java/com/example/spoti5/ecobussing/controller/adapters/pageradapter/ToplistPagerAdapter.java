package com.example.spoti5.ecobussing.controller.adapters.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.ToplistFragment;

/**
 * Created by Emil Axelsson on 03/10/15.
 *
 * Pager adapter for the top list.
 */
public class ToplistPagerAdapter extends FragmentStatePagerAdapter {

    private String range;


    public ToplistPagerAdapter(FragmentManager fragment, String range) {
        super(fragment);

        this.range = range;

    }


    public void setRange(String range) {
        this.range = range;
        this.notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:

                return ToplistFragment.newInstance(false, range);

            case 1:
                return ToplistFragment.newInstance(true, range);

        }
        return null;
    }

    // Used to update the views, not a "ugly" solution.. But could not find any other solution
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String category = "";
        switch(position){
            case 0:
                category = "Personer";
                break;

            case 1:
                category = "Företag";
                break;
        }
        return category;
    }

}
