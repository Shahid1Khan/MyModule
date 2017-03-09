package com.mymodule.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.mymodule.R;
import com.mymodule.mysharedprefrence.MyPrefData;
import com.mymodule.utils.SplashTask;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        String myID = new MyPrefData(this).getUserId();
        new SplashTask(this).movetoNext(3000, myID.equals("") ? LoginActivity.class : DashboardActivity.class).start();
    }
}
