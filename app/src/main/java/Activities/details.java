package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.corn.R;
import com.example.corn.apiController;
import com.example.corn.apiset;
import com.example.corn.base_url;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;

import Domains.CategoryDomain;
import Domains.PopularDomain;
import Domains.disease_images_domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class details extends AppCompatActivity {
    private TextView desctxt, titletxt, bedtxt,guidetxt,wifitxt, titletext, treatment;
    private PopularDomain items;
//    private disease_images_domain items;
    private ImageView backbtn,  picImg;
    private EditText googleanchor;
    Button button2;

    base_url url = base_url.getInstance();
    CarouselView carouselView;

        @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        setVariable();
    }

    private void setVariable() {
        items = (PopularDomain) getIntent().getSerializableExtra("object");

        titletext.setText(items.getDisease_name());
        desctxt.setText(items.getDescription());
        treatment.setText(items.getTreatment());


        String imageUrl = url.getBase_url()+"LoginRegister/images/disease_images/" + items.getPic();

        Glide.with(this)
                .load(imageUrl)
                .dontTransform()
                .into(picImg);
//        String disease_name = item.getDisease_name();
//
//        String imageUrl =  url.getBase_url() + "LoginRegister/fetch_for_disease_image.php?disease_name=" + disease_name;
//        apiset apiService = apiController.getInstance().getapi();
//        Call<ArrayList<disease_images_domain>> call = apiService.getDiseaseImages(disease_name);
//
//        call.enqueue(new Callback<ArrayList<disease_images_domain>>() {
//            @Override
//            public void onResponse(Call<ArrayList<disease_images_domain>> call, Response<ArrayList<disease_images_domain>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ArrayList<disease_images_domain> diseaseImages = response.body();
//                    ArrayList<String> imageList = new ArrayList<>();
//
//                    // Iterate through the list of disease images
//                    for (disease_images_domain disease_images_domain : diseaseImages) {
//                        // Assuming your disease_images_domain class has a method to get the image URL
//                        String imageUrl = url.getBase_url()+"LoginRegister/images/disease_images/" + items.getImage();
//                        imageList.add(imageUrl);
//
//                    }
//                    for (String imageUrl : imageList) {
//                        // Create a new ImageView for each image
//                        ImageView imageView = new ImageView(getApplicationContext());
//
//                        // Set layout parameters for the ImageView
//                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT));
//
//                        // Add the ImageView to the CarouselView
//                        carouselView.addView(imageView);
//
//                        // Load the image into the ImageView using Glide
//                        Glide.with(getApplicationContext())
//                                .load(imageUrl)
//                                .into(imageView);
//
//
//
//                    }
//                    Log.d("imageUrl", imageList.toString());
//
//
//                } else {
//                    // Handle unsuccessful response
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<disease_images_domain>> call, Throwable t) {
//                // Handle failure
//            }
//        });

        backbtn.setOnClickListener(v -> finish());

        googleanchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the URL directly in the code
                String url = "https://car.da.gov.ph/wp-content/uploads/2023/06/CORN-Pests-and-Diseases.pdf";
                gotoUrl(""+url);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url= items.getImage_links()+"";
                gotoUrl(""+url);

            }
        });


    }

    private void initView() {
            titletext = findViewById(R.id.titletext);
            desctxt=findViewById(R.id.desctxt);
            picImg=findViewById(R.id.picImg);
            backbtn=findViewById(R.id.backbtn);
            googleanchor=findViewById(R.id.googleanchor);
        button2 = findViewById(R.id.button2);
        treatment = findViewById(R.id.treatment);
    }

    private void gotoUrl(String s){
            Uri uri = Uri.parse(s);
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}