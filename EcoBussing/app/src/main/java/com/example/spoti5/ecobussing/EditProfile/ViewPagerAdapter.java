package com.example.spoti5.ecobussing.EditProfile;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    CharSequence titles[];
    int nbrOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence titles[], int nbrOfTabs){
        super(fm);
        this.titles = titles;
        this.nbrOfTabs = nbrOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            EditInfoFragment editInfo = new EditInfoFragment();
            return editInfo;
        }else{
            ConnectCompanyFragmant connectCompany = new ConnectCompanyFragmant();
            return connectCompany;
        }
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return nbrOfTabs;
    }
}
