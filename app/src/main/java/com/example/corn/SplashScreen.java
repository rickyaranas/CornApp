package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SplashScreen extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Check if the user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_LOGGED_IN, false);

        // Delay for 2 seconds before redirecting
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    // If logged in, redirect to home activity
                    startActivity(new Intent(SplashScreen.this, home.class));
                } else {
                    // If not logged in, redirect to login activity
                    startActivity(new Intent(SplashScreen.this, landingpage.class));
                }
                finish();
            }
        }, 2500); // 2000 milliseconds = 2 seconds delay
    }
}