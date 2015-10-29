package com.example.spoti5.ecobussing.controller.adapters.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.BarDiagramFragment;

/**
 * This is the pager adapter for the ViewPager in ProfileViewFragment. New fragments are added in getItem().
 * When this is done there also need to be more cases added in onPageSelected() in ProfileViewFragment.
 * Also getCount needs a change.
 *
 * Created by Hampus on 2015-10-12.
 */
public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private IProfile thisProfile;
    private boolean isPointsMoney;
    private Fragment frag1;
    private Fragment frag2;
    private Fragment frag3;


    public ProfilePagerAdapter(FragmentManager fragment, IProfile profile, boolean isPointsMoney) {
        super(fragment);
        thisProfile = profile;
        this.isPointsMoney = isPointsMoney;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 0:
                frag1 = BarDiagramFragment.newInstance(thisProfile, BarDiagramFragment.LAST_SEVEN_DAYS, isPointsMoney);
                fragment = frag1;
                break;
            case 1:
                frag2 = BarDiagramFragment.newInstance(thisProfile, BarDiagramFragment.LAST_SEVEN_WEEKS, isPointsMoney);
                fragment = frag2;
                break;
            case 2:
                frag3 = BarDiagramFragment.newInstance(thisProfile, BarDiagramFragment.LAST_SEVEN_MONTHS, isPointsMoney);
                fragment = frag3;
                break;
        }

        return fragment;
    }
    // Used to update the views
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
        //return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return 3;
    }
}

