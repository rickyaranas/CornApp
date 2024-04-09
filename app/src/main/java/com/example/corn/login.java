package com.example.corn;

import static android.os.Build.VERSION_CODES.O;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.security.NoSuchAlgorithmException;

public class login extends AppCompatActivity {
    Button loginC;
    EditText anchor, Temail, Tpassword;
    String userId;
    String password;
    id_Holder id = id_Holder.getInstance();

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);


        loginC = findViewById(R.id.loginC);
        anchor = findViewById(R.id.anchor);
        Temail=findViewById(R.id.email);
        Tpassword=findViewById(R.id.password);


        loginC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email, Hpassword;
                email= Temail.getText().toString();
                Hpassword = Tpassword.getText().toString();

                try {
                    password = hash.hashString(Hpassword);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                if(!email.equals("") && !Hpassword.equals("")){
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

                            PutData putData = new PutData("http://192.168.100.9/LoginRegister/login.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    String result = putData.getResult();

                                    if(result.contains("Successfully")) {

                                        Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_SHORT).show();
                                        Log.i("PutData", result);


                                        String[] parts = result.split(":");
                                        String successMessage = parts[0];
                                         userId = parts[1];

                                        // Pass user ID to the next activity or fragment
                                        Intent intent = new Intent(getApplicationContext(), home.class);

                                        intent.putExtra("userId", userId);
                                        id.hold_id(Integer.parseInt(userId));
                                        setUserLoggedIn(true);
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
    private void setUserLoggedIn(boolean loggedIn) {
        // Ensure sharedPreferences is not null
        if (sharedPreferences != null) {
            // Store the value of loggedIn in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_LOGGED_IN, loggedIn);
            editor.putString("userId", userId);
            editor.apply();
        } else {
            Log.e("LoginActivity", "SharedPreferences is null");
        }
    }
}