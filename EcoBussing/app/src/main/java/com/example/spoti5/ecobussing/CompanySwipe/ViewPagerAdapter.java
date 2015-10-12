package com.example.spoti5.ecobussing.CompanySwipe;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.TestSwipe;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private CharSequence titles;
    private int nbrTabs;
    private int tabGroup;


    public ViewPagerAdapter(FragmentManager fm, CharSequence titles[], int nbrTabs, int tabGroup){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            TestSwipe editInfo = new TestSwipe();
            return editInfo;
        }else{
            ConnectCompanyFragmant connectCompany = new ConnectCompanyFragmant();
            return connectCompany;
        }
    }

    public CharSequence getPageTitle(int position) {

        CharSequence tabTitle = "";
        switch(position){
            case 0: tabTitle = "Redigera profil";
                break;
            case 1: tabTitle = "Anslut till företag";
                break;
        }
        return tabTitle;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
