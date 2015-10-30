package com.example.spoti5.ecobussing.controller.viewcontroller.activities;

import android.annotation.TargetApi;

import android.content.Context;
import android.support.v4.app.Fragment;
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
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.SettingsAdminCompanyFragment;
import com.example.spoti5.ecobussing.controller.swipers.MedalViewSwiper;
import com.example.spoti5.ecobussing.controller.profile.Company;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.DrawerListAdapter;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.SearchAdapter;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.CompanySwipeFragment;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.SettingsCompanyFragment;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.EditInfoFragment;
import com.example.spoti5.ecobussing.controller.listeners.NetworkStateChangeReciever;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.ProfileViewFragment;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.controller.swipers.ToplistSwiper;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.WifiFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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

    private Stack<Fragment> prevFragments;

    private ImageView searchImage;
    private EditText searchText;

    private IDatabase database;


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentTransaction fragmentTransaction;

    private DrawerListAdapter listAdapter;
    private SearchAdapter searchAdapter;

    private List<String> fragmentsVisitedName;
    private List<? super Fragment> fragmentsVisited;

    private IUser currentUser;

    private String title;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = SaveHandler.getCurrentUser();
        database = DatabaseHolder.getDatabase();
        Boolean connected = currentUser.getCompany().equals("");

        fragmentsVisitedName = new ArrayList<>();
        fragmentsVisited = new ArrayList<>();

        prevFragments = new Stack<>();

        setContentView(R.layout.activity_drawer);
        System.out.println("Start activity");


        listAdapter = new DrawerListAdapter(this, connected);
        searchAdapter = new SearchAdapter(this, "---");

        //planetTitles = getResources().getStringArray(R.array.planets_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListLeft = (ListView) findViewById(R.id.left_drawer);
        drawerListRight = (FrameLayout) findViewById(R.id.right_drawer);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
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

        Tools tools = Tools.getInstance();

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
        ProfileViewFragment profileView = ProfileViewFragment.newInstance(user);
        changeFragment(title, profileView);
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
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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

        // this switch does different thing depending on the parent, since both the menu drawer
        // and the search drawer uses the same onClickListener.
        switch (parent.getId()) {
            case R.id.left_drawer:
                switch (position) {
                    case 0:
                        ProfileViewFragment pv = ProfileViewFragment.newInstance(currentUser);
                        changeFragment("Min profil", pv);
                        break;
                    case 1:
                        Fragment fragment = null;
                        try {
                            IProfile usercompany = database.getCompany(currentUser.getCompany());
                            if (usercompany != null) {
                                fragment = ProfileViewFragment.newInstance(usercompany);
                            } else {
                                throw new NullPointerException();
                            }
                        } catch (NullPointerException e) {
                            fragment = new CompanySwipeFragment();
                        }

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
                                SettingsAdminCompanyFragment settingsAdminCompanyFragment = new SettingsAdminCompanyFragment();
                                changeFragment(title, settingsAdminCompanyFragment);
                            } else {
                                SettingsCompanyFragment settingsCompanyFragment = new SettingsCompanyFragment();
                                changeFragment(title, settingsCompanyFragment);
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
                break;
            case R.id.search_result_list:
                IProfile profile = (IProfile)searchAdapter.getItem(position);
                title = profile.getName();
                changeToProfileFragment(profile, title);
                // the following lines close the search drawer and hides the input keyboard
                drawerLayout.closeDrawer(drawerListRight);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                break;
        }
        view.setBackgroundResource(R.color.third);
        prevView = view;
        drawerLayout.closeDrawer(drawerListLeft);
    }



    @Override
    public void onBackPressed(){

        int count = prevFragments.size();


        System.out.println("Stack size: " + count );
        if(drawerLayout.isDrawerOpen(drawerListLeft)){

            drawerLayout.closeDrawer(drawerListLeft);

        } else if(drawerLayout.isDrawerOpen(drawerListRight)){

            drawerLayout.closeDrawer(drawerListRight);

        }else if (count > 1) {
            //super.onBackPressed();
            //additional code
            int last = fragmentsVisitedName.size() - 2;
            prevFragments.pop();

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = prevFragments.peek();
            getSupportActionBar().setTitle(fragmentsVisitedName.get(last));
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //fragment.onResume();


            fragmentsVisitedName.remove(last + 1);

        }
    }



    private void changeFragment(String title, Fragment fragment) {
        prevFragments.push(fragment);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportActionBar().setTitle(title);
        fragmentsVisitedName.add(title);
        fragmentsVisited.add(fragment);
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
        ProfileViewFragment profileView = ProfileViewFragment.newInstance(profile);
        changeFragment(t, profileView);
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
