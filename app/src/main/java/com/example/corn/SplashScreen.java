package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    location_Tracker track = location_Tracker.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Check if the user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permissions are granted, you can call getLastLocation()
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations, this can be null.
                    if (location != null) {
                        // Logic to handle location object
//                        locationStr = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
//                        System.out.println("LOCATION: "+locationStr);

                        Geocoder geocoder = new Geocoder(SplashScreen.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        assert addresses != null;
                        Address address = addresses.get(0);
                        track.set_location(address.getAddressLine(0), address.getLocality(), address.getAdminArea(), address.getCountryName());


                    }
                }
            });
        }
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
        }, 3500); // 2000 milliseconds = 2 seconds delay
    }
}