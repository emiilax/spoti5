package com.example.spoti5.ecobussing;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by hilden on 2015-09-21.
 */
public class Main extends Activity {

    private User currentUser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SWAG SWAG SWAG SWAG SWAG SWAG SWAG SWAG SWAG SWAG SWAG SWAG");

       // currentUser = new User();

       // startMainActivity();
        startOverviewActivity();
        startLoginActivity();
    }

    /**
     * Starts the login activity.
     */
    private void startLoginActivity() {
        Intent loginActivity = new Intent(Main.this, LoginActivity.class);
        Main.this.startActivity(loginActivity);
    }

    /**
     * Starts the overview activity.
     */
    private void startOverviewActivity() {
        Intent overviewActivity = new Intent(Main.this, Overview.class);
        Main.this.startActivity(overviewActivity);
    }

    /**
     * Starts the main activity, does not work for some reason.
     */
    private void startMainActivity() {
        Intent mainActivity = new Intent(Main.this, MainActivity.class);
        startActivity(mainActivity);
    }
}