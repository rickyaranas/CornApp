package com.example.corn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Domains.user_info_domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class settingsfragment extends Fragment {

  private ImageView aboutusbtn, contactusbtn, profile_pic;
  TextView profile_username;
    private user_info_domain item;
  String userId;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settingsfragment, container, false);
        aboutusbtn = view.findViewById(R.id.aboutusbtn);
        contactusbtn = view.findViewById(R.id.contactusbtn);
        profile_pic = view.findViewById(R.id.profile_pic);
        profile_username = view.findViewById(R.id.profile_username);

        userId = getArguments().getString("userId");
        get_userID();
        aboutusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), about_us.class);
                startActivity(intent);
            }
        });
        contactusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),contactus.class);
                startActivity(intent);
            }
        });


        return view;
    }
    public void get_userID(){
        String apiUrl = "http://192.168.100.5/LoginRegister/fetch_user_data.php?usr_id=" + userId;
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
                        String imageUrl = "http://192.168.100.5/LoginRegister/images/user_images/"+imageName; // Adjust this URL according to your server configuration
                        Log.d("Generated URL3", apiUrl);
                        profile_username.setText(userinfo.getFullname());

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
}