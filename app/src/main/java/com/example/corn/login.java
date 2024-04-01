package com.example.corn;

import static android.os.Build.VERSION_CODES.O;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class login extends AppCompatActivity {
    Button loginC;
    EditText anchor, Temail, Tpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginC = findViewById(R.id.loginC);
        anchor = findViewById(R.id.anchor);
        Temail=findViewById(R.id.email);
        Tpassword=findViewById(R.id.password);


        loginC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email, password;
                email= Temail.getText().toString();
                password = Tpassword.getText().toString();

                if(!email.equals("") && !password.equals("")){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";

                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;

                            PutData putData = new PutData("http://192.168.100.5/LoginRegister/login.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    String result = putData.getResult();

                                    if(result.contains("Successfully")) {

                                        Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_SHORT).show();
                                        Log.i("PutData", result);

                                        String[] parts = result.split(":");
                                        String successMessage = parts[0];
                                        String userId = parts[1];

                                        // Pass user ID to the next activity or fragment
                                        Intent intent = new Intent(getApplicationContext(), home.class);
                                        intent.putExtra("userId", userId);
                                        startActivity(intent);
                                        Log.i("User ID", userId);


                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Log.d("EditTextDebug", "last: " + result);
                                    }

                                }
                            }

                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(), "wa sud ag koan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        anchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }
}