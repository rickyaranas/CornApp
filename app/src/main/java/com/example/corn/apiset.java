package com.example.corn;

import java.util.ArrayList;

import Domains.AnalysisData;
import Domains.CategoryDomain;
import Domains.DiseaseList_Domain;
import Domains.Disease_info;
import Domains.MunicipalityAnalysis;
import Domains.PopularDomain;
import Domains.ScannedDisease_Domain;
import Domains.disease_images_domain;
import Domains.local_farms_domain;
import Domains.user_info_domain;
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

    @GET("fetch_user_data.php")
    Call<ArrayList<user_info_domain>> getUserInfo(@Query("user_id") String user_id);

    @GET("update_user.php")
    Call<ArrayList<user_info_domain>> Update(@Query("user_id") String user_id);

    @GET("fetch_local_farms.php")
    Call<ArrayList<local_farms_domain>> getFarminfo(@Query("municipality") String municipality);

    @GET("fetch_disease_info.php")
    Call<ArrayList<Disease_info>> getDisease_info(@Query("disease_name") String disease_name);

    @GET("fetch_for_analysis.php")
    Call<ArrayList<AnalysisData>> getDiseaseAnalysis(@Query("user_id") String user_id);

    @GET("fetch_for_disease_image.php")
    Call<ArrayList<disease_images_domain>> getDiseaseImages(@Query("disease_name_fetch") String disease_name_fetch);

    @GET("fetch_for_municipality_analysis.php")
    Call<ArrayList<MunicipalityAnalysis>> getMunicipalityAnalysis(@Query("location") String municipality);
}
