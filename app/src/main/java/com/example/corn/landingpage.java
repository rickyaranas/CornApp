package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class landingpage extends AppCompatActivity {
    Button signinC;
    EditText landinganchor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);


        signinC = findViewById(R.id.signinC);
        landinganchor = findViewById(R.id.landinganchor);

        signinC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this, register.class);
                startActivity(intent);

            }
        });

        landinganchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(landingpage.this, login.class);
                startActivity(intent);

            }
        });
    }
}