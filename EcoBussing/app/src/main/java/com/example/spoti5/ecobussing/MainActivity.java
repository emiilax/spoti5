package com.example.spoti5.ecobussing;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

/**
 * Created by emilaxelsson on 16/09/15.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] planetTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    //private ActionBar actionBar;

    private ActionBarDrawerToggle actionBarDrawerToggle;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        planetTitles = getResources().getStringArray(R.array.planets_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);


        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, planetTitles));

        drawerList.setOnItemClickListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar1));

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Toolbar");
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        loadSelection(0);

        playOverview(); //Starts the animation activity
    }

    /**
     * Starts the animation activity if any CO2 has been gathered since the last launch.
     * Set the anyCO2SavedSinceLaunch to true to try it out.
     * Temporary location.
     */
    private void playOverview() {
        boolean anyCO2SavedSinceLastLaunch = false;
        if (anyCO2SavedSinceLastLaunch) {
            Intent overview = new Intent(MainActivity.this, SaveAnimation.class);
            startActivity(overview);
        }
    }

    private void loadSelection(int i){
        drawerList.setItemChecked(i,true);
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
            drawerLayout.openDrawer(drawerList);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;

        }
        drawerLayout.closeDrawer(drawerList);
        //Toast.makeText(this, planetTitles[position] + " was selected", Toast.LENGTH_LONG).show();
    }
}
