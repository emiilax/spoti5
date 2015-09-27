package com.example.spoti5.ecobussing.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Erik on 2015-09-27.
 */
public abstract class ActivityController extends AppCompatActivity {

    /**
     * Starts the overview activity. Is not reverseable
     */
    protected void startRegisterActivity() {
        Intent registerActivity = new Intent(ActivityController.this, RegisterActivity.class);
        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityController.this.startActivity(registerActivity);
    }

    /**
     * Starts the login activity.
     */
    protected void startLoginActivity() {
        Intent loginActivity = new Intent(ActivityController.this, LoginActivity.class);
        ActivityController.this.startActivity(loginActivity);
    }

    /**
     * Starts the overview activity. Is not reverseable
     */
    protected void startOverviewActivity() {
        Intent overviewActivity = new Intent(ActivityController.this, OverviewActivity.class);
        overviewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityController.this.startActivity(overviewActivity);
    }

    /**
     * Starts the overview activity.
     */
    protected void startMainActivity() {
        Intent MainActivity = new Intent(ActivityController.this, MainActivity.class);
        ActivityController.this.startActivity(MainActivity);
    }
}
