package com.example.spoti5.ecobussing.Profiles;

import android.net.IpPrefix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.diagram.BarDiagram;

/**
 * This is the pager adapter for the ViewPager in ProfileView. New fragments are added in getItem().
 * When this is done there also need to be more cases added in onPageSelected() in ProfileView.
 * Also getCount needs a change.
 *
 * Created by Hampus on 2015-10-12.
 */
public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private IProfile thisProfile;

    public ProfilePagerAdapter(FragmentManager fragment, IProfile profile) {
        super(fragment);
        thisProfile = profile;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 0:
                fragment = BarDiagram.newInstance(thisProfile, 0);
                break;
            case 1:
                fragment = new BarDiagram();
                break;
            case 2:
                fragment = BarDiagram.newInstance(thisProfile, 2);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

