package Activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corn.R;
import com.example.corn.apiController;
import com.example.corn.apiset;
import com.example.corn.base_url;

import java.util.ArrayList;

import Adapters.DiseaseList_Adapter;
import Adapters.PopularAdapter;
import Adapters.local_farms_adapter;
import Adapters.municipalty_local_farms_adapter;
import Domains.AnalysisData;
import Domains.CategoryDomain;
import Domains.DiseaseList_Domain;
import Domains.MunicipalityAnalysis;
import Domains.PopularDomain;
import Domains.local_farms_domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class localfarms extends AppCompatActivity {
    private TextView desctxt, total_detected, bedtxt, guidetxt, wifitxt, titletext, diseasename, seecat;
    private CategoryDomain item;
    private ImageView backbtn, picImg, locbtn;
    private RecyclerView local_recycler;
    private RecyclerView.Adapter local_farms_adapter;
    private RecyclerView.Adapter municipality_local_farms_adapter;
    base_url url = base_url.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localfarms);

        local_recycler = findViewById(R.id.local_recycler);
        local_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));



        initView();
        setVariableC();
        //processdata();
    }

    private void setVariableC() {
        item = (CategoryDomain) getIntent().getSerializableExtra("category_object");

        titletext.setText(item.getMunicipality());
        desctxt.setText(item.getDescription());
        String imageUrl = url.getBase_url()+"LoginRegister/images/local_farms/" + item.getLogo();

        Glide.with(this)
                .load(imageUrl)
                .into(picImg);

        backbtn.setOnClickListener(v -> finish());

        String municipality = String.valueOf(item.getMunicipality());
        String apiUrl = url.getBase_url() + "LoginRegister/fetch_for_municipality_analysis.php?location=" + municipality;
        Log.d("Generated URL farms", apiUrl); // Print the generated URL in Logcat

        apiset apiService = apiController.getInstance().getapi();
        Call<ArrayList<MunicipalityAnalysis>> call = apiService.getMunicipalityAnalysis(municipality);

        call.enqueue(new Callback<ArrayList<MunicipalityAnalysis>>() {
            @Override
            public void onResponse(Call<ArrayList<MunicipalityAnalysis>> call, Response<ArrayList<MunicipalityAnalysis>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MunicipalityAnalysis> municipalityAnalysisList = response.body();
                    if (municipalityAnalysisList != null) {
                        // Assuming you want to set data from the first item in the list


                        // Assuming you have a TextView named textViewResult
                        int totalCount = 0;
                        for (MunicipalityAnalysis analysis : municipalityAnalysisList) {
                            totalCount += analysis.getCount();
                        }
                        total_detected.setText("Total Disease Detected: " + totalCount);
                        municipality_local_farms_adapter = new municipalty_local_farms_adapter(municipalityAnalysisList);
                        local_recycler.setAdapter(municipality_local_farms_adapter);
                        // ...
                    } else {
                        // Handle empty response or list
                        total_detected.setText("No data available");
                    }
                } else {
                    // Handle unsuccessful response
                    total_detected.setText("Failed to retrieve data");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MunicipalityAnalysis>> call, Throwable t) {
                // Handle failure
                total_detected.setText("Failed: " + t.getMessage());
            }

        });

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(item.getLocation()));

                // Create an Intent with the ACTION_VIEW action and the Uri object
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set the package for the intent to Google Maps
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start the Intent
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });


    }

    private void initView() {
        titletext = findViewById(R.id.titletext);
        picImg = findViewById(R.id.picImg);
        backbtn = findViewById(R.id.backbtn);
        desctxt = findViewById(R.id.desctxt);
        locbtn = findViewById(R.id.locbtn);
        seecat = findViewById(R.id.seecat);
        total_detected = findViewById(R.id.total_detected);
    }

    private void processdata() {

        String municipality = String.valueOf(item.getMunicipality());

        String apiUrl = url.getBase_url()+"LoginRegister/fetch_local_farms.php?municipality=" +municipality;
        Log.d("Generated URL farms", apiUrl); // Print the generated URL in Logcat
        Call<ArrayList<local_farms_domain>> call = apiController
                .getInstance()
                .getapi()
                .getFarminfo(municipality);

        call.enqueue(new Callback<ArrayList<local_farms_domain>>() {
            @Override
            public void onResponse(Call<ArrayList<local_farms_domain>> call, Response<ArrayList<local_farms_domain>> response) {
                ArrayList<local_farms_domain> items = response.body();
                if (items != null) {

                    local_farms_adapter = new local_farms_adapter(items);
                    local_recycler.setAdapter(local_farms_adapter);


                    Log.d("AdapterDebug", "Adapter is set successfully");
                } else {
                    // Handle null response body
                }

            }

            @Override
            public void onFailure(Call<ArrayList<local_farms_domain>> call, Throwable t) {

            }
        });

    }
}