package com.example.spoti5.ecobussing.Activites;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.spoti5.ecobussing.BusinessFragment;
import com.example.spoti5.ecobussing.CompanySwipe.CompanySwipeFragment;
import com.example.spoti5.ecobussing.EditInfoFragment;
import com.example.spoti5.ecobussing.Profiles.UserProfileView;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.example.spoti5.ecobussing.SwipeScreens.ToplistSwiper;

/**
 * Created by emilaxelsson on 16/09/15.
 */
public class MainActivity extends ActivityController implements AdapterView.OnItemClickListener {

    private String[] planetTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerListLeft;
    private FrameLayout drawerListRight;


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentTransaction fragmentTransaction;

    private DrawerListAdapter listAdapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        System.out.println("Start activity");

        //intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //registerReceiver(wifiReciever, intentFilter);
        //addWifiChangeHandler();

        listAdapter = new DrawerListAdapter(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //planetTitles = getResources().getStringArray(R.array.planets_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListLeft = (ListView) findViewById(R.id.left_drawer);
        drawerListRight = (FrameLayout) findViewById(R.id.right_drawer);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout myView = (LinearLayout) inflater.inflate(R.layout.search_view, null);


        drawerListRight.addView(myView);


        drawerListLeft.setAdapter(listAdapter);

        drawerListLeft.setOnItemClickListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Toolbar");
        ab.setIcon(rezizedDrawable());
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        loadSelection(0);
    }

    protected void setUpDrawer(){

    }

    private Drawable rezizedDrawable(){
        Drawable logo = getResources().getDrawable(R.drawable.logo_compact);
        Bitmap mp = ((BitmapDrawable)logo).getBitmap();
        Drawable smallLogo = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mp, 100, 100, true));
        return smallLogo;
        /*Drawable logo = getResources().getDrawable(R.drawable.logo_compact);
        logo.setBounds(0,0,16,16);
        return logo;*/
    }

    private void loadSelection(int i){
        drawerListLeft.setItemChecked(i, true);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            drawerLayout.openDrawer(drawerListLeft);
        }
        return super.onOptionsItemSelected(item);
    }
    View prevView = null;
    Boolean wifi = false;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(prevView != null ) prevView.setBackgroundResource(R.color.clear_white);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch(position){
            case 0:
                getSupportActionBar().setTitle(SaveHandler.getCurrentUser().getName());
                view.setBackgroundResource(R.color.clicked);
                UserProfileView userProfileView = new UserProfileView();

                fragmentTransaction.replace(R.id.container, userProfileView);

                break;
            case 1:
                getSupportActionBar().setTitle("Fragment 2");
                view.setBackgroundResource(R.color.clicked);
                BusinessFragment businessFragment = new BusinessFragment();

                fragmentTransaction.replace(R.id.container, businessFragment);
                break;
            case 2:
                getSupportActionBar().setTitle("Topplistor");
                view.setBackgroundResource(R.color.third);

                ToplistSwiper test = new ToplistSwiper();

                fragmentTransaction.replace(R.id.container, test);
                break;

            case 3:
                getSupportActionBar().setTitle("Wifi-detect");


                WifiFragment wfrag = new WifiFragment();

                fragmentTransaction.replace(R.id.container, wfrag);

                /*

                 
                wifi = true;
                fragmentTransaction.replace(R.id.container, wifiDetect);
                if(wifiReciever.getBssid() != null){
                    setConnected(wifiReciever.getBssid());
                }
                */

                break;
            case 4:
                getSupportActionBar().setTitle("Företagsinställningar");
                view.setBackgroundResource(R.color.clicked);
                CompanySwipeFragment fragment = new CompanySwipeFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                break;
            case 5:
                getSupportActionBar().setTitle("Redigera profil");
                view.setBackgroundResource(R.color.clicked);
                EditInfoFragment editInfoFragment = new EditInfoFragment();
                fragmentTransaction.replace(R.id.container, editInfoFragment);
                break;
            case 6:
                logout();
                break;

        }



        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        prevView = view;
        drawerLayout.closeDrawer(drawerListLeft);
        //Toast.makeText(this, planetTitles[position] + " was selected", Toast.LENGTH_LONG).show();
    }

    /*
    public void setConnected(String bssid){
        if(wifi){
            TextView con = (TextView) findViewById(R.id.connected);
            TextView dwg = (TextView) findViewById(R.id.dwg);
            TextView vinnr = (TextView) findViewById(R.id.vinnr);
            TextView regnr = (TextView) findViewById(R.id.regnr);
            TextView mac = (TextView) findViewById(R.id.mac);
            for(Bus b: Busses.theBusses){
                if(bssid.equals(b.getMacAdress())){
                    dwg.setText(b.getDwg());
                    vinnr.setText(b.getVIN());
                    regnr.setText(b.getRegNr());
                    mac.setText(b.getMacAdress());
                }
            }
            mac.setText(bssid);

            con.setText("Connected");
        }

    }
    public void setDisconnected(){
        if(wifi){
            TextView con = (TextView) findViewById(R.id.connected);
            TextView dwg = (TextView) findViewById(R.id.dwg);
            TextView vinnr = (TextView) findViewById(R.id.vinnr);
            TextView regnr = (TextView) findViewById(R.id.regnr);
            TextView mac = (TextView) findViewById(R.id.mac);

            dwg.setText("");
            vinnr.setText("");
            regnr.setText("");
            mac.setText("");
            con.setText("Disconnected");
        }

    }

    */




    private void logout() {
        startRegisterActivity();
        SaveHandler.changeUser(null);
    }
}
