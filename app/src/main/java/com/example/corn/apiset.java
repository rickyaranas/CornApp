package com.example.corn;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Domains.CategoryDomain;
import Domains.PopularDomain;
import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("corn_disease_fetch.php")

    Call<ArrayList<PopularDomain>> getdata();
    @GET("local_farms_fetch.php")
    Call<ArrayList<CategoryDomain>> getCategoryData();


}
