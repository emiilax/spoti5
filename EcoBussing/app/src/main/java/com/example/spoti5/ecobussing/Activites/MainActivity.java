package com.example.spoti5.ecobussing.Activites;

import android.annotation.TargetApi;

import android.support.v4.app.Fragment;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.spoti5.ecobussing.controller.Tools;
import com.example.spoti5.ecobussing.view.fragments.EditCompanyFragment;
import com.example.spoti5.ecobussing.controller.swipers.MedalViewSwiper;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.DrawerListAdapter;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.SearchAdapter;
import com.example.spoti5.ecobussing.view.fragments.CompanySwipeFragment;
import com.example.spoti5.ecobussing.view.fragments.ConnectedCompanyFragment;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.view.fragments.EditInfoFragment;
import com.example.spoti5.ecobussing.controller.listeners.NetworkStateChangeReciever;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.view.fragments.ProfileView;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.controller.swipers.ToplistSwiper;
import com.example.spoti5.ecobussing.view.fragments.WifiFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Emil Axelsson on 16/09/15.
 * Edited by all
 */
public class MainActivity extends ActivityController implements AdapterView.OnItemClickListener, View.OnClickListener {

    private String[] planetTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerListLeft;
    private FrameLayout drawerListRight;
    private ListView searchListView;
    private Tools tools;

    private ImageView searchImage;
    private EditText searchText;

    private IDatabase database;


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentTransaction fragmentTransaction;

    private DrawerListAdapter listAdapter;
    private SearchAdapter searchAdapter;

    private List<String> fragmentsVisitedName;

    private IUser currentUser;
    private boolean connected;

    String title;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = SaveHandler.getCurrentUser();
        database = DatabaseHolder.getDatabase();
        connected = currentUser.getCompany().equals("");

        fragmentsVisitedName = new ArrayList<>();

        setContentView(R.layout.activity_drawer);
        System.out.println("Start activity");


        listAdapter = new DrawerListAdapter(this, connected);
        searchAdapter = new SearchAdapter(this, "---");

        //planetTitles = getResources().getStringArray(R.array.planets_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListLeft = (ListView) findViewById(R.id.left_drawer);
        drawerListRight = (FrameLayout) findViewById(R.id.right_drawer);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout myView = (LinearLayout) inflater.inflate(R.layout.search_view, null);

        searchImage = (ImageView) myView.findViewById(R.id.button_search);
        searchText = (EditText) myView.findViewById(R.id.searchText);
        searchText.setOnKeyListener(autoSearch);

        searchImage.setOnClickListener(this);

        searchListView = (ListView) myView.findViewById(R.id.search_result_list);
        searchListView.setAdapter(searchAdapter);
        searchListView.setOnItemClickListener(this);

        drawerListRight.addView(myView);
        drawerListLeft.setAdapter(listAdapter);
        drawerListLeft.setOnItemClickListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        tools = Tools.getInstance();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Toolbar");
        ab.setIcon(rezizedDrawable());
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        loadSelection(0);

        startFirstFragemnt();
    }

    private void startFirstFragemnt() {
        IUser user = SaveHandler.getCurrentUser();
        String title = "Min profil";
        getSupportActionBar().setTitle(title);
        ProfileView profileView = ProfileView.newInstance(user);
        fragmentsVisitedName.add(title);
        fragmentTransaction.replace(R.id.container, profileView);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private Drawable rezizedDrawable() {
        Drawable logo = getResources().getDrawable(R.drawable.logo_compact);
        Bitmap mp = ((BitmapDrawable) logo).getBitmap();

        return new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mp, 100, 100, true));
    }

    private void loadSelection(int i) {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(drawerListLeft);
        } else if (id == R.id.action_search) {
            drawerLayout.openDrawer(drawerListRight);
            drawerLayout.clearFocus();
            searchText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
        }
        return super.onOptionsItemSelected(item);
    }

    View prevView = null;
    Boolean wifi = false;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (prevView != null) prevView.setBackgroundResource(R.color.clear_white);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        String usrCompanyString = currentUser.getCompany();
        Company company = (Company) database.getCompany(usrCompanyString);
        switch (position) {
            case 0:
                changeToProfileFragment(currentUser, "Min profil");
                break;
            case 1:
                CompanySwipeFragment fragment = new CompanySwipeFragment();
                title = "Mitt företag";
                changeFragment(title, fragment);
                break;
            case 2:
                ToplistSwiper test = new ToplistSwiper();
                title = "Topplistor";
                changeFragment(title, test);
                break;
            case 3:
                MedalViewSwiper medalFragment = new MedalViewSwiper();
                title = "Medaljer";
                changeFragment(title, medalFragment);
                break;
            case 4:
                if (usrCompanyString.equals("")) {
                    EditInfoFragment editInfoFragment = new EditInfoFragment();
                    title = "Redigera profil";
                    changeFragment(title, editInfoFragment);
                } else {
                    title = "Företagsinställningar";
                    if (company.userIsModerator(currentUser)) {
                        EditCompanyFragment editCompanyFragment = new EditCompanyFragment();
                        changeFragment(title, editCompanyFragment);
                    } else {
                        ConnectedCompanyFragment connectedCompanyFragment = new ConnectedCompanyFragment();
                        changeFragment(title, connectedCompanyFragment);
                    }
                }
                break;
            case 5:
                if (usrCompanyString.equals("")) {
                    logout();
                } else {
                    EditInfoFragment editInfoFragment = new EditInfoFragment();
                    title = "Redigera profil";
                    changeFragment(title, editInfoFragment);
                }
                break;
            case 6:
                if (usrCompanyString.equals("")) {
                    title = "WiFi Detect";
                    getSupportActionBar().setTitle(title);
                    WifiFragment wfrag = new WifiFragment();
                    changeFragment(title, wfrag);

                } else {
                    logout();
                }
                break;
            case 7:
                title = "WiFi Detect";
                WifiFragment wfrag = new WifiFragment();
                changeFragment(title, wfrag);
                break;
        }
        view.setBackgroundResource(R.color.third);
        prevView = view;
        drawerLayout.closeDrawer(drawerListLeft);
    }



/*    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(drawerListLeft)){
            drawerLayout.closeDrawer(drawerListLeft);
        } else if(drawerLayout.isDrawerOpen(drawerListRight)){
            drawerLayout.closeDrawer(drawerListRight);
        } else if(fragmentsVisitedName.size() > 2){
            int last = fragmentsVisitedName.size() - 2;
            getSupportActionBar().setTitle(fragmentsVisitedName.get(last));
            fragmentsVisitedName.remove(last + 1);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }
    }*/


    private void changeFragment(String title, Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportActionBar().setTitle(title);
        fragmentsVisitedName.add(title);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void logout() {
        NetworkStateChangeReciever.getInstance().tryEndJourney();
        startRegisterActivity();
        SaveHandler.changeUser(null);
    }

    public void changeToProfileFragment(IProfile profile, String t) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        title = t;
        getSupportActionBar().setTitle(title);
        ProfileView profileView = ProfileView.newInstance(profile);
        fragmentsVisitedName.add(title);
        fragmentTransaction.replace(R.id.container, profileView);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        if (v.equals(searchImage)) {
            search();
        }
    }

    public void updateList(boolean connected) {
        listAdapter.changeLayout(connected);
        drawerListLeft.setAdapter(listAdapter);
    }

    private void search() {
        searchAdapter = new SearchAdapter(this, searchText.getText().toString());
        searchListView.setAdapter(searchAdapter);
    }

boolean timerRunning = false;
View.OnKeyListener autoSearch = new View.OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        final Timer t = new Timer();

        if (keyCode == KeyEvent.KEYCODE_ENTER && !timerRunning) {
            search();
        }

        /**
         * Timer, otherwise it calls twice
         */
        timerRunning = true;
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                timerRunning = false;
                t.cancel();
            }
        }, 1000);

        return true;
    }
};
}
