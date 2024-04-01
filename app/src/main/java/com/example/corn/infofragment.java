package com.example.corn;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.PopularAdapter;
import Adapters.category_Adapters;
import Domains.CategoryDomain;
import Domains.PopularDomain;
import Domains.ScannedDisease_Domain;
import Domains.user_info_domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class infofragment extends Fragment {

    private RecyclerView.Adapter adapterPopular, adapterCategory;
    private RecyclerView recyclerViewPopular, recyclerViewCategory;
    private user_info_domain item;
    TextView username, username_tv;

    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_infofragment, container, false);

        recyclerViewPopular = rootView.findViewById(R.id.view_pop);
        recyclerViewCategory = rootView.findViewById(R.id.view_cat);
        username_tv = rootView.findViewById(R.id.textView7);

        username = rootView.findViewById(R.id.textView7);


        userId = getArguments().getString("userId");
        get_userID();

        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));

        processdata();
        farms();
        return rootView;

    }
    public void get_userID(){
        String apiUrl = "http://192.168.100.5/LoginRegister/fetch_user_data.php?user_id=" + userId;
        Log.d("Generated URL", apiUrl); // Print the generated URL in Logcat

        apiset apiService = apiController.getInstance().getapi();
        Call<ArrayList<user_info_domain>> call = apiService.getUserInfo(userId);

        call.enqueue(new Callback<ArrayList<user_info_domain>>() {
            @Override
            public void onResponse(Call<ArrayList<user_info_domain>> call, Response<ArrayList<user_info_domain>> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    ArrayList<user_info_domain> userinfos = response.body();

                    StringBuilder stringBuilder = new StringBuilder();
                    for (user_info_domain userinfo : userinfos) {
                        // Append each fetched data to the StringBuilder
                        username.setText(userinfo.getFullname());
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
    public void processdata(){
        Call<ArrayList<PopularDomain>> call = apiController
                .getInstance()
                .getapi()
                .getdata();

        Call<ArrayList<CategoryDomain>> categoryCall = apiController
                .getInstance()
                .getapi()
                .getCategoryData();



        call.enqueue(new Callback<ArrayList<PopularDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<PopularDomain>> call, Response<ArrayList<PopularDomain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PopularDomain> items = response.body();
                    if (items != null) {

                        adapterPopular = new PopularAdapter(items);
                        recyclerViewPopular.setAdapter(adapterPopular);


                        Log.d("AdapterDebug", "Adapter is set successfully");
                    } else {
                        // Handle null response body
                    }
                } else {
                    Log.d("AdapterDebug", "Response unsuccessful");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PopularDomain>> call, Throwable t) {
                // Handle network failure
                Log.d("AdapterDebug", "Network failure occurred: " + t.getMessage());

                // Log statement here after handling the failure
                Log.d("AdapterDebug", "processdata() method completed with failure");
            }
        });
        categoryCall.enqueue(new Callback<ArrayList<CategoryDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDomain>> categoryCall, Response<ArrayList<CategoryDomain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<CategoryDomain> items = response.body();
                    if (items != null) {

                        adapterCategory = new category_Adapters(items);
                        recyclerViewCategory.setAdapter(adapterCategory);


                        Log.d("AdapterDebug", "Adapter is set successfully");
                    } else {
                        // Handle null response body
                    }
                } else {
                    Log.d("AdapterDebug", "Response unsuccessful");

                }


            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDomain>> call, Throwable t) {
                Log.d("AdapterDebug", "Category Network failure occurred: " + t.getMessage());

                // Log statement here after handling the failure
                Log.d("AdapterDebug", "processdata() Category method completed with failure");

            }
        });
    }
    public void farms() {
        Call<ArrayList<CategoryDomain>> categoryCall = apiController
                .getInstance()
                .getapi()
                .getCategoryData();
        categoryCall.enqueue(new Callback<ArrayList<CategoryDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDomain>> categoryCall, Response<ArrayList<CategoryDomain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<CategoryDomain> items = response.body();
                    if (items != null) {

                        adapterCategory = new category_Adapters(items);
                        recyclerViewCategory.setAdapter(adapterCategory);


                        Log.d("AdapterDebug", "Adapter is set successfully");
                    } else {
                        // Handle null response body
                    }
                } else {
                    Log.d("AdapterDebug", "Response unsuccessful");

                }


            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDomain>> call, Throwable t) {
                Log.d("AdapterDebug", "Category Network failure occurred: " + t.getMessage());

                // Log statement here after handling the failure
                Log.d("AdapterDebug", "processdata() Category method completed with failure");

            }
        });
    }
//
//    private void initRecyclerView() {
//        ArrayList<PopularDomain> items = new ArrayList<>();
//        items.add(new PopularDomain("1", "Common Rust", "Bohol", "is a foliar disease of corn favored by moist and cool conditions. The fungal pathogen does not overwinter in the Corn Belt;" +
//                " windblown spores bring it north from the Southern U.S. Common rust is less likely to" +
//                " cause significant yield loss than southern rust.", 2, true, 4.8, "common_rust", true));
//        items.add(new PopularDomain("1", " Leaf Blight", "Bohol", "Northern corn leaf blight (NCLB) or Turcicum leaf blight (TLB) is a foliar disease of corn caused by Exserohilum turcicum, the anamorph of the ascomycete Setosphaeria turcica. With its " +
//                "characteristic cigar-shaped lesions, this disease can cause significant yield loss in susceptible " +
//                "corn hybrids.", 1, true, 4.8, "leaf_blight", true));
//        items.add(new PopularDomain("1", "Gray Leaf Spot", "Bohol", "caused by the fungus Cercospora zeae-maydis, occurs virtually every growing season. If conditions favor disease development, economic losses can occur. Symptoms first appear on lower leaves about two to three weeks before tasseling.", 2, true, 4.8, "leaf_spot", true));
//
//
//        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        adapterPopular = new PopularAdapter(items);
//        recyclerViewPopular.setAdapter(adapterPopular);
//
//
//    }
//    private void locationrecycler() {
//
//        ArrayList<CategoryDomain> catlist = new ArrayList<>();
//
//        catlist.add(new CategoryDomain("1","Sagbayan","saglogo","Visit the farms for an immersive experience " +
//                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
//                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
//                " in the fresh aroma of golden corn as you stroll through " +
//                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","V3JF+2X4, Sta. Catalina, Sagbayan, 6331 Bohol"));
//        catlist.add(new CategoryDomain("1","Carmen","carmen", "Visit the farms for an immersive experience " +
//                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
//                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
//                " in the fresh aroma of golden corn as you stroll through " +
//                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","Q5R5+54X, Loay Interior Road, Batuan, Bohol"));
//        catlist.add(new CategoryDomain("1","Bilar","bilarlogo", "Visit the farms for an immersive experience " +
//                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
//                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
//                " in the fresh aroma of golden corn as you stroll through " +
//                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.", "Bilar"));
//        catlist.add(new CategoryDomain("1","Valencia","valencia", "Visit the farms for an immersive experience " +
//                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
//                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
//                " in the fresh aroma of golden corn as you stroll through " +
//                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","J665+3V9, Valencia, Bohol"));
//        catlist.add(new CategoryDomain("1","Garcia","garcia", "Visit the farms for an immersive experience " +
//                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
//                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
//                " in the fresh aroma of golden corn as you stroll through " +
//                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","J665+3V9, Valencia, Bohol"));
//        catlist.add(new CategoryDomain("1","Ubay","cat4", "Visit the farms for an immersive experience " +
//                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
//                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
//                " in the fresh aroma of golden corn as you stroll through " +
//                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","J665+3V9, Valencia, Bohol"));
//
//        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        adapterCategory = new category_Adapters(catlist);
//        recyclerViewCategory.setAdapter(adapterCategory);
//
//
//
//    }
}