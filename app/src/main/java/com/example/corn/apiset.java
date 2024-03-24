package com.example.corn;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Domains.CategoryDomain;
import Domains.DiseaseList_Domain;
import Domains.PopularDomain;
import Domains.ScannedDisease_Domain;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiset {

    @GET("corn_disease_fetch.php")

    Call<ArrayList<PopularDomain>> getdata();
    @GET("local_farms_fetch.php")
    Call<ArrayList<CategoryDomain>> getCategoryData();
    @GET("disease_list_fetch.php")
    Call<ArrayList<DiseaseList_Domain>> getDiseaseData();

//    @GET("fetch_data.php")
//    Call<ArrayList<ScannedDisease_Domain>> getDisease();
    @GET("fetch_data.php")
    Call<ArrayList<ScannedDisease_Domain>> getDisease(@Query("id") String id);
}
