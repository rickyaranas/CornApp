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

                            PutData putData = new PutData("http://192.168.100.9/LoginRegister/login.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    String result = putData.getResult();


                                    if(result.equals("Login Successfully")) {

                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Log.i("PutData", result);
                                        Intent intent = new Intent(getApplicationContext(), home.class);
                                        startActivity(intent);
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
                    Toast.makeText(getApplicationContext(), "wa gyapon", Toast.LENGTH_SHORT).show();
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