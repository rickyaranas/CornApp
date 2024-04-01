package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startActivity(new Intent(SplashScreen.this, landingpage.class));
                    finish();

                }catch (Exception e){

                }
            }
        };thread.start();
    }
//    private void checkActiveStatus() {
//        // Make a request to your PHP API to check active status
//        String url = "http://your_api_endpoint/check_active_status.php";
//        PutData putData = new PutData(url, "GET",field, data);
//
//        if (putData.startPut()) {
//            if (putData.onComplete()) {
//                String activeStatus = putData.getResult();
//                handleActiveStatus(activeStatus);
//            }
//        }
//    }
//
//    private void handleActiveStatus(String activeStatus) {
//        // Check if active status is 1
//        if ("1".equals(activeStatus)) {
//            // If active status is 1, redirect to landing page
//            startActivity(new Intent(SplashScreen.this, home.class));
//        } else {
//            // If active status is not 1, redirect to login page
//            startActivity(new Intent(SplashScreen.this, landingpage.class));
//        }
//
//        // Finish the splash screen activity to prevent going back to it
//        finish();
//    }
}