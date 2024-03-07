package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corn.R;

import Domains.CategoryDomain;
import Domains.PopularDomain;

public class localfarms extends AppCompatActivity {
    private TextView desctxt, titletxt, bedtxt,guidetxt,wifitxt, titletext, diseasename, seecat;
    private CategoryDomain item;
    private ImageView backbtn,  picImg, locbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localfarms);

        initView();
        setVariableC();
    }

    private void setVariableC() {
        item = (CategoryDomain) getIntent().getSerializableExtra("category_object");

        titletext.setText(item.getMunicipality());
        desctxt.setText(item.getDescription());
        String imageUrl = "http://192.168.100.10/LoginRegister/images/local_farms/" + item.getLogo();

        Glide.with(this)
                .load(imageUrl)
                .into(picImg);

        backbtn.setOnClickListener(v -> finish());

      locbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Uri gmmIntentUri = Uri.parse("geo:0,0?q="+Uri.encode(item.getLocation()));

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
        picImg=findViewById(R.id.picImg);
        backbtn=findViewById(R.id.backbtn);
        desctxt=findViewById(R.id.desctxt);
        locbtn=findViewById(R.id.locbtn);
        seecat=findViewById(R.id.seecat);
    }
}