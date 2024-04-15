package com.example.corn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class register extends AppCompatActivity {
    Button signup;
   EditText  TIfullname, TIemail, TIpassword, Tconfirmpass ;
    EditText anchor;
    ProgressBar progress;
    ImageView profile_pic;

    Bitmap bitmap;
    Bitmap reducedSize;

    String password;
    base_url url = base_url.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = findViewById(R.id.signup);
        anchor = findViewById(R.id.anchor);
        TIfullname = findViewById(R.id.fullname);
        TIemail = findViewById(R.id.email);
        TIpassword = findViewById(R.id.password);
        progress=findViewById(R.id.progress);
        Tconfirmpass=findViewById(R.id.confirmpass);
        profile_pic = findViewById(R.id.profile_pic);


        progress.setVisibility(View.GONE);


        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                final String fullname, email, Tpassword,confirmpass;
                fullname = TIfullname.getText().toString();
                email = TIemail.getText().toString();
                Tpassword = TIpassword.getText().toString();
                confirmpass = Tconfirmpass.getText().toString();

                 password = null;
                try {
                    password = hash.hashString(Tpassword);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }


                Log.d("EditTextDebug", "fullname: " + fullname);
                Log.d("EditTextDebug", "email: " + email);
                Log.d("EditTextDebug", "password: " + Tpassword);

                if(!fullname.equals("") && !email.equals("") && !Tpassword.equals("")) {
                    if (isValidEmail(email)) {
                        if (isStrongPassword(Tpassword)) {
                        if (Tpassword.equals(confirmpass)) {
                            if (bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
                                byte [] image = imageViewToBy(bitmap);

                                progress.setVisibility(View.VISIBLE);

                                Handler handler = new Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] field = new String[4];
                                        field[0] = "fullname";
                                        field[1] = "email";
                                        field[2] = "password";
                                        field[3] = "user_image";

                                        Log.d("EditTextDebug", "fullname: " + field[0]);

                                        String[] data = new String[4];
                                        data[0] = fullname;
                                        data[1] = email;
                                        data[2] = password;
                                        data[3] = Base64.encodeToString(image, Base64.DEFAULT);
                                        ;

                                        Log.d("EditTextDebug", "fullname: " + data[0]);

                                        PutData putData = new PutData(url.getBase_url()+"LoginRegister/signup.php", "POST", field, data);

                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                progress.setVisibility(View.GONE);

                                                String result = putData.getResult();

                                                if (result.contains("Successfully")) {
                                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                    Log.i("PutData", result);
                                                    Intent ed = new Intent(getApplicationContext(), login.class);
                                                    startActivity(ed);


                                                } else {
                                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                    Log.d("EditTextDebug", "last: " + result);


                                                }

                                            }
                                        }
                                        //End Write and Read data with URL
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select you Profile Image", Toast.LENGTH_SHORT).show();

                            }

                        } else {

                            Toast.makeText(getApplicationContext(), "The passwords aren't the same!", Toast.LENGTH_SHORT).show();
                            Tconfirmpass.setError("Passwords do not match");
                            Tconfirmpass.setText("");
                        }
                        } else {
                            // Password is not strong
                            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long and contains at least one digit", Toast.LENGTH_LONG).show();
                            TIpassword.setError("Password must be at least 8 characters and one digit");
                            TIpassword.setText("");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        TIemail.setError("Invalid email address");
                        TIemail.setText("");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
        anchor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+(?:com|ph)$";
        return email.matches(emailRegex);
//        ^: Asserts the start of the line.
//          [a-zA-Z0-9_+&*-]+: Matches one or more characters from the set a-z, A-Z, 0-9, _, +, &, *, and - before the "@" symbol.
//        (?:\\.[a-zA-Z0-9_+&*-]+)*: Allows for zero or more occurrences of a dot followed by one or more characters from the set a-z, A-Z, 0-9, _, +, &, and *.
//        @: Matches the "@" symbol.
//        (?:[a-zA-Z0-9-]+\\.)+: Matches one or more occurrences of characters followed by a dot, allowing for subdomains.
//        (?:com|ph): Matches either "com" or "ph" as the top-level domain.
//                $: Asserts the end of the line.
//
    }
    private boolean isStrongPassword(String password) {
        // Define the criteria for a strong password
        String passwordPattern = "^(?=.*[0-9])(?=\\S+$).{8,}$";
        return password.matches(passwordPattern);
//        ^ asserts the start of the line.
//        (?=.*[0-9]) asserts that the password must contain at least one digit.
//        (?=.*[a-z]) asserts that the password must contain at least one lowercase letter.
//        (?=.*[A-Z]) asserts that the password must contain at least one uppercase letter.
//        (?=.*[@#$%^&+=!]) asserts that the password must contain at least one special character.
//        (?=\S+$) asserts that the password must not contain any whitespace characters.
//        .{8,} specifies that the password must be at least 8 characters long.
//                $ asserts the end of the line.
  }
    public void selectImage(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (resultCode == RESULT_OK) {

            if (requestCode == 2) {

                // Get the selected image and store it in a bitmap
                Uri dat = data.getData();
                try {
                    // Locating the image path using URI and store the image in bitmap
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            reducedSize = reduceImageSize(bitmap,240,240);
            profile_pic.setImageBitmap(reducedSize);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap reduceImageSize(Bitmap originalBitmap, int maxWidth, int maxHeight) {
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();

        // Calculate the correct scale size
        float scale = Math.min(((float)maxWidth / originalWidth), ((float)maxHeight / originalHeight));

        // Create a matrix for manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap with the same color model as the original bitmap, but scaled down.
        Bitmap scaledBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalWidth, originalHeight, matrix, true);

        return scaledBitmap;
    }
    public static byte[] imageViewToBy(Bitmap mutableBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

}

