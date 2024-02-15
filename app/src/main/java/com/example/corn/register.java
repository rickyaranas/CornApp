package com.example.corn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class register extends AppCompatActivity {
    Button signup;
    EditText anchor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signup = findViewById(R.id.signup);
        anchor = findViewById(R.id.anchor);

        //Start ProgressBar first (Set visibility VISIBLE)
        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                //Starting Write and Read data with URL
//                //Creating array for parameters
//                String[] field = new String[2];
//                field[0] = "param-1";
//                field[1] = "param-2";
//                //Creating array for data
//                String[] data = new String[2];
//                data[0] = "data-1";
//                data[1] = "data-2";
//                PutData = new PutData("https://projects.vishnusivadas.com/AdvancedHttpURLConnection/putDataTest.php", "POST", field, data);
//                if (putData.startPut()) {
//                    if (putData.onComplete()) {
//                        String result = putData.getResult();
//                        //End ProgressBar (Set visibility to GONE)
//                        Log.i("PutData", result);
//
//
//                    }
//                }
//                //End Write and Read data with URL
//            }
//        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, home.class);
                startActivity(intent);

            }
        });
        anchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }
}