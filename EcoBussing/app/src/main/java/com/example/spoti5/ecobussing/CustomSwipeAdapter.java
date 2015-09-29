package com.example.spoti5.ecobussing;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Erik on 2015-09-29.
 */
public class CustomSwipeAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.profile_slider, container,false);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);
    }
}
