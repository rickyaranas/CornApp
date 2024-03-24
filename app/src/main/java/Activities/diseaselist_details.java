package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corn.R;

import Domains.CategoryDomain;
import Domains.DiseaseList_Domain;
import Domains.PopularDomain;

public class diseaselist_details extends AppCompatActivity {

    private TextView disease_name, location,date, severity, description;
    private DiseaseList_Domain items;
    private ImageView list_pic, locbtn, backbtn;
    private EditText googleanchor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseaselist_details);

        initView();
        setVariable();
    }
    private void setVariable() {
        items = (DiseaseList_Domain) getIntent().getSerializableExtra("object");

        disease_name.setText(items.getDisease_name());
        description.setText(items.getDescription());
        location.setText("Location: "+items.getLocation());
        date.setText("Date: "+items.getDate());
        severity.setText("Disease Severity Level: "+items.getSeverity());

        String imageUrl = "http://192.168.100.8/LoginRegister/images/disease_list/" + items.getImage();

        Glide.with(this)
                .load(imageUrl)
                .into(list_pic);
        backbtn.setOnClickListener(v -> finish());

        googleanchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the URL directly in the code
                String url = "https://car.da.gov.ph/wp-content/uploads/2023/06/CORN-Pests-and-Diseases.pdf";
                gotoUrl(""+url);

            }
        });


    }

    private void initView() {
        disease_name = findViewById(R.id.disease_name);
        location=findViewById(R.id.location);
        date=findViewById(R.id.date);
        severity=findViewById(R.id.severity);
        description=findViewById(R.id.desctxt);

        list_pic=findViewById(R.id.list_pic);

        backbtn=findViewById(R.id.backbtn);
        googleanchor=findViewById(R.id.googleanchor);
    }

    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
