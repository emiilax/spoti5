package com.example.spoti5.ecobussing.Profiles;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.R;

/**
 * This is the view for the user profile. It contains a ViewPager. The ViewPager can show different
 * fragments and if a fragment is added more cases needs to be added to the method OnPageSelected().
 * New fragments are added in the ProfilePagerAdapter-class.
 *
 *  Created by Hampus on 2015-10-12.
 */
public class ProfileView extends Fragment implements ViewPager.OnPageChangeListener{
    private FragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private static View view;
    private ImageView dot1, dot2;
    private static IProfile thisProfile;

    public ProfileView() {
        // Required empty public constructor
    }

    public static final ProfileView newInstance(IProfile ip)
    {
        ProfileView f = new ProfileView();
        f.setThisProfile(ip);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        mPagerAdapter = new ProfilePagerAdapter(getActivity().getSupportFragmentManager());

        mViewPager = (ViewPager)view.findViewById(R.id.profile_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        dot1 = (ImageView) view.findViewById(R.id.img_dot1);
        dot2 = (ImageView) view.findViewById(R.id.img_dot2);

        setDataStrings(view);

        return view;
    }

    public static void setDataStrings(View view) {
        TextView nameView = (TextView)view.findViewById(R.id.profile_name);
        nameView.setText(thisProfile.getName());
        TextView companyNameView = (TextView)view.findViewById(R.id.user_company);
        if(thisProfile instanceof IUser){
            if(((IUser) thisProfile).getCompany() != null) {
                companyNameView.setText(((IUser) thisProfile).getCompany());
            }else{
                companyNameView.setText("Den här användaren har inte anslutit sig till något företag");
            }
        }else{
            companyNameView.setText(null);
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch(position){
            case 0:
                setBigDot(dot1);
                setSmallDot(dot2);
                break;

            case 1:
                setSmallDot(dot1);
                setBigDot(dot2);
        }
    }

    private void setBigDot (ImageView dot){
        dot.setImageResource(R.drawable.dot_grey_big);
        dot.setAlpha(1f);
    }

    private void setSmallDot (ImageView dot){
        dot.setImageResource(R.drawable.dot_grey_small);
        dot.setAlpha(0.7f);
    }

    public void setThisProfile(IProfile ip){
        thisProfile = ip;
    }

    public interface OnFragmentInteractionListener {public void onFragmentInteraction(Uri uri);}

    // Theses methods are not used but needed because of the OnPageChangeListener implementation
    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

}

