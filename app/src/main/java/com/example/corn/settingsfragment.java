package com.example.corn;

import static android.app.Activity.RESULT_OK;

import static androidx.camera.core.impl.utils.ContextUtil.getApplicationContext;
import static com.example.corn.register.imageViewToBy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Domains.user_info_domain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class settingsfragment extends Fragment {

  private ImageView aboutbtns, contact, profile_pic, logout, profile;
    private AlertDialog alertDialog;
    ProgressBar progress;
    EditText fullname;
    EditText email;
    EditText password;
    EditText confirmpassword;
    String emailD;

       TextView profile_username, emailtv;
      Button edit,save;
    Bitmap bitmap;
    Bitmap reducedSize;
    private SharedPreferences sharedPreferences;
    base_url url = base_url.getInstance();


    private user_info_domain item;
  String userId;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settingsfragment, container, false);
//        aboutusbtn = view.findViewById(R.id.aboutusbtn);
        contact = view.findViewById(R.id.contact);
        profile_pic = view.findViewById(R.id.profile_pic);
        profile_username = view.findViewById(R.id.profile_username);
        emailtv = view.findViewById(R.id.emailtv);
        logout = view.findViewById(R.id.logout);
        edit = view.findViewById(R.id.edit);
        aboutbtns = view.findViewById(R.id.aboutbtns);

        userId = getArguments().getString("userId");
        get_userID();
//
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();

            }
        });
        aboutbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), about_us.class);
                startActivity(intent);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),contactus.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm Logout");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform logout action here
                        // For example, clear SharedPreferences or perform any other necessary tasks
                        // Then redirect the user to the login screen or any other appropriate screen
                        // For demonstration purpose, let's redirect to LoginActivity
                        setUserLoggedIn(false);
                        Intent intent = new Intent(getActivity(), login.class);
                        startActivity(intent);
                        getActivity().finish();// Finish the current activity to prevent going back
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog and do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }

    private void setUserLoggedIn(boolean loggedIn) {
        // Ensure sharedPreferences is not null
        if (sharedPreferences != null) {
            // Store the value of loggedIn in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", loggedIn);
            editor.apply();
        } else {
            Log.e("LoginActivity", "SharedPreferences is null");
        }
    }

    public void get_userID(){
        String apiUrl = url.getBase_url()+"LoginRegister/fetch_user_data.php?usr_id=" + userId;
        Log.d("Generated URL", apiUrl); // Print the generated URL in Logcat

        apiset apiService = apiController.getInstance().getapi();
        Call<ArrayList<user_info_domain>> call = apiService.getUserInfo(userId);
        Log.d("Generated URL1", apiUrl);
        call.enqueue(new Callback<ArrayList<user_info_domain>>() {
            @Override
            public void onResponse(Call<ArrayList<user_info_domain>> call, Response<ArrayList<user_info_domain>> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    ArrayList<user_info_domain> userinfos = response.body();
                    Log.d("Generated URL2", apiUrl);

                    StringBuilder stringBuilder = new StringBuilder();
                    for (user_info_domain userinfo : userinfos) {
                        String imageName = userinfo.getImage();
                        String imageUrl = url.getBase_url()+"LoginRegister/images/user_images/"+imageName; // Adjust this URL according to your server configuration
                        Log.d("Generated URL3", apiUrl);
                        profile_username.setText(userinfo.getFullname());
                        emailtv.setText(userinfo.getEmail());

                        Glide.with(requireContext())
                                .load(imageUrl)
                                .circleCrop()
                                .into(profile_pic);

                    }


                } else {
                    // Handle error response here
                    // You can check response.errorBody() for more details
                }

            }

            @Override
            public void onFailure(Call<ArrayList<user_info_domain>> call, Throwable t) {

            }
        });




    }
    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.edit_profile, null);
        builder.setView(dialogView);

        fullname = dialogView.findViewById(R.id.fullname);
//        email = dialogView.findViewById(R.id.email);
        //password = dialogView.findViewById(R.id.password);
       // confirmpassword = dialogView.findViewById(R.id.confirmpass);
        progress=dialogView.findViewById(R.id.progress);
        progress.setVisibility(dialogView.GONE);
        // Initialize other EditText fields

        // Set up the Save button click listener
        save = dialogView.findViewById(R.id.signup);
        profile = dialogView.findViewById(R.id.profile_edit);

        String apiUrl = url.getBase_url()+"LoginRegister/fetch_user_data.php?usr_id=" + userId;
        Log.d("Generated URL", apiUrl); // Print the generated URL in Logcat

        apiset apiService = apiController.getInstance().getapi();
        Call<ArrayList<user_info_domain>> call = apiService.getUserInfo(userId);
        Log.d("Generated URL1", apiUrl);
        call.enqueue(new Callback<ArrayList<user_info_domain>>() {
            @Override
            public void onResponse(Call<ArrayList<user_info_domain>> call, Response<ArrayList<user_info_domain>> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    ArrayList<user_info_domain> userinfos = response.body();
                    Log.d("Generated URL2", apiUrl);

                    StringBuilder stringBuilder = new StringBuilder();
                    for (user_info_domain userinfo : userinfos) {
                        String imageName = userinfo.getImage();
                        String imageUrl = url.getBase_url()+"LoginRegister/images/user_images/" + imageName; // Adjust this URL according to your server configuration
                        Log.d("Generated URL3", apiUrl);
                        fullname.setText(userinfo.getFullname());
//                        email.setText(userinfo.getEmail());
                        emailD = (userinfo.getEmail());
                    }


                } else {
                    // Handle error response here
                    // You can check response.errorBody() for more details
                }

            }

            @Override
            public void onFailure(Call<ArrayList<user_info_domain>> call, Throwable t) {

            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Save button click
                final String Tfullname, Temail, Tpassword,Tconfirmpass;
                Tfullname = fullname.getText().toString();
                Temail = emailD;
//                Tpassword = password.getText().toString();
//
//                Log.d("EditTextDebug", "fullname: " + Tfullname);
//                Log.d("EditTextDebug", "email: " + Temail);
//                Log.d("EditTextDebug", "password: " + Tpassword);

//                if(!Tfullname.equals("") && !password.equals("")){
//                       if (bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
//                           byte[] image = imageViewToBy(bitmap);
//
//                           Handler handler = new Handler();
//                           handler.post(new Runnable() {
//                               @Override
//                               public void run() {
//                                   String[] field = new String[2];
//
//                                    field[0] = "email";
//                                    field[1] = "password";
//
//                                    String[] data = new String[2];
//                                    data[0] = Temail;
//                                   data[1] = Tpassword;
//                                   Log.d("EditTextDebug", "last: email ni : " + Temail);
//
//                                   PutData putData = new PutData(url.getBase_url()+"LoginRegister/login.php", "POST", field, data);
//
//                                   if (putData.startPut()) {
//                                       if (putData.onComplete()) {
//
//                                           String result = putData.getResult();
//
//                                           if(result.contains("Successfully")) {
//                                               Toast.makeText(getActivity(),"Okay sysaaa"+result,Toast.LENGTH_SHORT).show();
//                                               Log.d("EditTextDebug", "last: OKiiii login" + result);
//
//                                           }
//                                           else {
//                                               Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//                                               Log.d("EditTextDebug", "last else: " + result);
//                                           }
//
//                                       }
//                                   }
//
//
//
//                               }
//                           });
//                       }else{
//                           Toast.makeText(getActivity(), "Please Select you Profile Image", Toast.LENGTH_SHORT).show();
//                       }
//                }else{
//                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
//                }


                if(!Tfullname.equals("")) {
//                    if (isStrongPassword(Tpassword)) {
//                    if (Tpassword.equals(Tconfirmpass)) {
                        if (bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
                            byte[] image = imageViewToBy(bitmap);

                            progress.setVisibility(View.VISIBLE);

                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[2];
                                    field[0] = "fullname";
//                                    field[1] = "email";
//                                    field[2] = "password";
                                    field[1] = "user_image";

                                    Log.d("EditTextDebug", "fullname: " + field[0]);

                                    String[] data = new String[2];
                                    data[0] = Tfullname;
//                                    data[1] = Temail;
//                                    data[2] = Tpassword;
                                    data[1] = Base64.encodeToString(image, Base64.DEFAULT);
                                    ;

                                    Log.d("EditTextDebug", "fullname: " + userId);
                                    PutData putData = new PutData(url.getBase_url()+"LoginRegister/update_user.php?user_id=" + userId, "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progress.setVisibility(View.GONE);

                                            String result = putData.getResult();

                                            if (result.contains("Successfully")) {
                                                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                                Log.i("PutData", result);
                                                Intent ed = new Intent(getActivity(), home.class);
                                                startActivity(ed);



                                            } else {
                                                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                                Log.d("EditTextDebug", "last: " + result);


                                            }

                                        }
                                    }
                                    //End Write and Read data with URL
                                }
                            });
                        }
                         else {
                                Toast.makeText(getActivity(), "Please Select you Profile Image", Toast.LENGTH_SHORT).show();

                            }


                        }
//                    else {
//                        Toast.makeText(getActivity(), "The passwords aren't the same!", Toast.LENGTH_SHORT).show();
//                        confirmpassword.setError("Passwords do not match");
//                        confirmpassword.setText("");
//                    }
//                    } else {
//                        // Password is not strong
//                        Toast.makeText(getActivity(), "Password must be at least 8 characters long and contains at least one digit", Toast.LENGTH_LONG).show();
//                        password.setError("Password must be at least 8 characters and one digit");
//                        password.setText("");
//
//                      }


                else {
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }




            }
        });

        // Create and show the dialog
        alertDialog = builder.create();
        alertDialog.show();
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

    public void selectImage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                // Get the selected image and store it in a bitmap
                Uri dat = data.getData();
                try {
                    // Pass the activity context to the method
                    bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), dat);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            reducedSize = reduceImageSize(bitmap, 240, 240);
            profile.setImageBitmap(reducedSize);
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