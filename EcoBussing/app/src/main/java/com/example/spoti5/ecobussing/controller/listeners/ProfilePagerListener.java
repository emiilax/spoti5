package com.example.spoti5.ecobussing.controller.listeners;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.spoti5.ecobussing.R;

/**
 * Created by Hampus on 2015-10-17.
 */
public class ProfilePagerListener implements ViewPager.OnPageChangeListener {
    private ImageView dot1, dot2, dot3;

    //Should probably do this with a list of some sort instead
    public ProfilePagerListener(View view, int n){
        switch(n){
            case 1:
                dot1 = (ImageView) view.findViewById(R.id.imgDot1_1);
                dot2 = (ImageView) view.findViewById(R.id.imgDot2_1);
                dot3 = (ImageView) view.findViewById(R.id.imgDot3_1);
                break;

            case 2:
                dot1 = (ImageView) view.findViewById(R.id.imgDot1_2);
                dot2 = (ImageView) view.findViewById(R.id.imgDot2_2);
                dot3 = (ImageView) view.findViewById(R.id.imgDot3_2);
                break;
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Should probably do this with a list of some sort instead
        switch(position){
            case 0:
                setBigDot(dot1);
                setSmallDot(dot2);
                setSmallDot(dot3);
                break;

            case 1:
                setSmallDot(dot1);
                setBigDot(dot2);
                setSmallDot(dot3);
                break;
            case 2:
                setSmallDot(dot1);
                setSmallDot(dot2);
                setBigDot(dot3);
                break;
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

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
