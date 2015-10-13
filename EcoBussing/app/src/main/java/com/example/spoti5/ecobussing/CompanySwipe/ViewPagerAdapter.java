package com.example.spoti5.ecobussing.CompanySwipe;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.TestSwipe;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private CharSequence titles[];
    private int nbrTabs;
    private int tabGroup;


    public ViewPagerAdapter(FragmentManager fm, CharSequence titles[], int nbrTabs, int tabGroup){
        super(fm);
        this.titles = titles;
        this.nbrTabs = nbrTabs;
        this.tabGroup = tabGroup;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        if(tabGroup == 0) {
            if (position == 0) {
                fragment = new ConnectCompanyFragmant();
            } else {
                fragment = new CreateCompanyFragment();
            }
        }else if(tabGroup == 1){
            fragment = new EditCompanyFragment();
        }else{
            fragment = new EditCompanyFragment();
        }

        return fragment;
    }

    public CharSequence getPageTitle(int position) {

        CharSequence tabTitle = "";

        if(tabGroup == 0) {
            switch (position) {
                case 0:
                    tabTitle = titles[0];
                    break;
                case 1:
                    tabTitle = titles[1];
                    break;
            }
        }else if(tabGroup == 1){
            tabTitle = titles[0];
        }else{
            tabTitle = titles[0];
        }
        return tabTitle;
    }

    @Override
    public int getCount() {
        return nbrTabs;
    }
}
