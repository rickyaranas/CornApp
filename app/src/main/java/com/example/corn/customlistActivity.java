package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class customlistActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customlist);
        // Retrieve the data (disease name) from the Intent
        String diseaseName = getIntent().getStringExtra("disease_name");

        // Now you can use diseaseName as needed in your CustomActivity
        // For example, you can set it to a TextView in your layout
        TextView textView = findViewById(R.id.textView);
        textView.setText("Disease Name: " + diseaseName);
        viewFlipper = findViewById(R.id.viewFlipper);

    }
}