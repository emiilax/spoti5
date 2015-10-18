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
import android.widget.Toast;

import com.example.spoti5.ecobussing.Medals.MedalFragment;
import com.example.spoti5.ecobussing.Medals.MedalViewSwiper;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.diagram.BarDiagram;
import com.example.spoti5.ecobussing.CompanySwipe.CompanySwipeFragment;
import com.example.spoti5.ecobussing.ConnectedCompanyFragment;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.EditInfoFragment;
import com.example.spoti5.ecobussing.NetworkStateChangeReciever;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.ProfileView;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.example.spoti5.ecobussing.SwipeScreens.ToplistSwiper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by emilaxelsson on 16/09/15.
 */
public class MainActivity extends ActivityController implements AdapterView.OnItemClickListener, View.OnClickListener {

    private String[] planetTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerListLeft;
    private FrameLayout drawerListRight;
    private ListView searchListView;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = SaveHandler.getCurrentUser();
        database = DatabaseHolder.getDatabase();

        fragmentsVisited = new ArrayList<>();
        fragmentsVisitedName = new ArrayList<>();

        setContentView(R.layout.activity_drawer);
        System.out.println("Start activity");


        //intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //registerReceiver(wifiReciever, intentFilter);
        //addWifiChangeHandler();

        listAdapter = new DrawerListAdapter(this);
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
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text;

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Object item = searchAdapter.getItem(position);
                if (!(item instanceof Company)) {
                    IUser user = (IUser) item;
                    try {
                        String title = user.getName();
                        getSupportActionBar().setTitle(title);
                        ProfileView profileView = ProfileView.newInstance(user);
                        fragmentsVisitedName.add(title);
                        fragmentsVisited.add(profileView);
                        fragmentTransaction.replace(R.id.container, profileView);
                    } catch (IndexOutOfBoundsException e) {
                        text = "Ingen kontakt med databasen, försök igen";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawer(drawerListRight);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                } else {
                    text = "nja, vi har ju inte implementerat detta för företag än";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }


        });

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

        startFirstFragemnt();
    }

    private void startFirstFragemnt(){
        IUser user = SaveHandler.getCurrentUser();
        String title = user.getName();
        getSupportActionBar().setTitle(title);
        ProfileView profileView = ProfileView.newInstance(user);
        fragmentsVisitedName.add(title);
        fragmentsVisited.add(profileView);
        fragmentTransaction.replace(R.id.container, profileView);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private Drawable rezizedDrawable(){
        Drawable logo = getResources().getDrawable(R.drawable.logo_compact);
        Bitmap mp = ((BitmapDrawable)logo).getBitmap();
        Drawable smallLogo = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mp, 100, 100, true));
        return smallLogo;
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
        }else if(id == android.R.id.home){
            drawerLayout.openDrawer(drawerListLeft);
        }else if(id == R.id.action_search){
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
        String title;
        if(prevView != null ) prevView.setBackgroundResource(R.color.clear_white);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch(position){
            case 0:
                IUser user = SaveHandler.getCurrentUser();
                title ="Min profil";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                ProfileView profileView = ProfileView.newInstance(user);
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(profileView);
                fragmentTransaction.replace(R.id.container, profileView);

                break;
            case 1:
                IProfile company = database.getCompanies().get(0);
                title = "Mitt företag";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                ProfileView companyView = ProfileView.newInstance(company);
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(companyView);
                fragmentTransaction.replace(R.id.container, companyView);

                break;
           /* case 1:
                title = "fragment 2";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                BusinessFragment businessFragment = new BusinessFragment();
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(businessFragment);

                fragmentTransaction.replace(R.id.container, businessFragment);
                break;
                */
            case 2:
                title = "Topplistor";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.third);

                ToplistSwiper test = new ToplistSwiper();
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(test);


                fragmentTransaction.replace(R.id.container, test);
                break;

            case 3:
                title = "Medaljer";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                MedalViewSwiper medalFragment = new MedalViewSwiper();
                fragmentsVisited.add(medalFragment);
                fragmentsVisitedName.add(title);

                fragmentTransaction.replace(R.id.container, medalFragment);

                break;
            case 4:
                title = "Företagsinställningar";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                if(currentUser.getCompany().equals("") || currentUser.getCompany().equals(null)) {
                    //Om man inte är connctad till ett företag
                    CompanySwipeFragment fragment = new CompanySwipeFragment();
                    fragmentsVisitedName.add(title);
                    fragmentsVisited.add(fragment);
                    fragmentTransaction.replace(R.id.container, fragment);
                }else{
                    //Om man är connectad till företag, borde finnas en till beroende på om man är moderator
                    //if()
                    ConnectedCompanyFragment connectedCompanyFragment = new ConnectedCompanyFragment();
                    fragmentsVisitedName.add(title);
                    fragmentsVisited.add(connectedCompanyFragment);
                    fragmentTransaction.replace(R.id.container, connectedCompanyFragment);
                }
                break;
            case 5:
                title = "Redigera profil";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                EditInfoFragment editInfoFragment = new EditInfoFragment();
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(editInfoFragment);
                fragmentTransaction.replace(R.id.container, editInfoFragment);
                break;
            case 6:
                logout();
                break;

            case 7:
                title = "Diagram";
                getSupportActionBar().setTitle(title);
                view.setBackgroundResource(R.color.clicked);
                BarDiagram bd = new BarDiagram();
                fragmentTransaction.replace(R.id.container, bd);
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(bd);
                break;
            case 8:

                title = "WiFi Detect";
                getSupportActionBar().setTitle(title);
                WifiFragment wfrag = new WifiFragment();

                fragmentTransaction.replace(R.id.container, wfrag);
                fragmentsVisitedName.add(title);
                fragmentsVisited.add(wfrag);
                break;

        }



        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        prevView = view;
        drawerLayout.closeDrawer(drawerListLeft);
        //Toast.makeText(this, planetTitles[position] + " was selected", Toast.LENGTH_LONG).show();
    }

    @Override
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


    private void logout() {
        NetworkStateChangeReciever.getInstance().endJourney();
        startRegisterActivity();
        SaveHandler.changeUser(null);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(searchImage)) {
            search();
        }
    }

    private void search(){
        searchAdapter = new SearchAdapter(this, searchText.getText().toString());
        searchListView.setAdapter(searchAdapter);
    }

    boolean timerRunning = false;
    View.OnKeyListener autoSearch = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            final Timer t = new Timer();

            if (keyCode == event.KEYCODE_ENTER && !timerRunning) {
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
