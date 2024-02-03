package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

public class customlistActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customlist);
        // Retrieve the data (disease name) from the Intent
        String diseaseName = getIntent().getStringExtra("disease_name");

        // Now you can use diseaseName as needed in your CustomActivity
        // For example, you can set it to a TextView in your layout
        TextView textView = findViewById(R.id.textView);
        backbtn = findViewById(R.id.backbtn);
        textView.setText("" + diseaseName);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customlistActivity.this, home.class);
                startActivity(intent);
            }
        });

    }


}