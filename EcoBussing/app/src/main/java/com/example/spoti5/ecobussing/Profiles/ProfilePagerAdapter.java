package com.example.spoti5.ecobussing.Profiles;

        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Hampus on 2015-10-12.
 */
public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    public ProfilePagerAdapter(FragmentManager fragment) {
        super(fragment);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 0:
                fragment = new ProfileInfo();
                break;

            case 1:
                fragment = new ProfileOtherThing();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

