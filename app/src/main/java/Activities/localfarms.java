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

import java.util.ArrayList;

import Adapters.DiseaseList_Adapter;
import Adapters.PopularAdapter;
import Adapters.local_farms_adapter;
import Domains.CategoryDomain;
import Domains.DiseaseList_Domain;
import Domains.PopularDomain;
import Domains.local_farms_domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class localfarms extends AppCompatActivity {
    private TextView desctxt, titletxt, bedtxt, guidetxt, wifitxt, titletext, diseasename, seecat;
    private CategoryDomain item;
    private ImageView backbtn, picImg, locbtn;
    private RecyclerView local_recycler;
    private RecyclerView.Adapter local_farms_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localfarms);

        local_recycler = findViewById(R.id.local_recycler);
        local_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));



        initView();
        setVariableC();
        processdata();
    }

    private void setVariableC() {
        item = (CategoryDomain) getIntent().getSerializableExtra("category_object");

        titletext.setText(item.getMunicipality());
        desctxt.setText(item.getDescription());
        String imageUrl = "http://192.168.100.5/LoginRegister/images/local_farms/" + item.getLogo();

        Glide.with(this)
                .load(imageUrl)
                .into(picImg);

        backbtn.setOnClickListener(v -> finish());

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
    }

    private void processdata() {

        String municipality = String.valueOf(item.getMunicipality());

        String apiUrl = "http://192.168.100.5/LoginRegister/fetch_local_farms.php?municipality=" +municipality;
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