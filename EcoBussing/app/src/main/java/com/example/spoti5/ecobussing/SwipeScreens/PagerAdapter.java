package com.example.spoti5.ecobussing.SwipeScreens;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.Activites.ToplistFragment;

/**
 * Created by emilaxelsson on 03/10/15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private String range;
    private ToplistFragment theFragment;

    public PagerAdapter(FragmentManager fragment, String range) {
        super(fragment);

        this.range = range;
    }


    public void setRange(String range){

        ((ToplistFragment)this.getItem(0)).setRange(range);
        ((ToplistFragment)this.getItem(1)).setRange(range);

    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 0:
                fragment = new ToplistFragment();

                ((ToplistFragment)fragment).setRange(range);
                ((ToplistFragment)fragment).setisCompany(false);
                break;

            case 1:
                fragment = new ToplistFragment();

                ((ToplistFragment)fragment).setRange(range);
                ((ToplistFragment)fragment).setisCompany(true);
                break;
        }
        //System.out.println("get item called");

        return fragment;
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
        return category; //dummy title
    }

}
