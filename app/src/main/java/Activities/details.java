package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.corn.R;

import Domains.CategoryDomain;
import Domains.PopularDomain;

public class details extends AppCompatActivity {
    private TextView desctxt, titletxt, bedtxt,guidetxt,wifitxt, titletext, diseasename;
    private PopularDomain item;
    private ImageView backbtn,  picImg;
    private EditText googleanchor;
        @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        setVariable();
    }

    private void setVariable() {
        item = (PopularDomain) getIntent().getSerializableExtra("object");

        titletext.setText(item.getDisease_name());
        desctxt.setText(item.getDescription());

        String imageUrl = "http://192.168.100.5/LoginRegister/images/disease_images/" + item.getPic();

        Glide.with(this)
                .load(imageUrl)
                .into(picImg);
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
            titletext = findViewById(R.id.titletext);
            desctxt=findViewById(R.id.desctxt);
            picImg=findViewById(R.id.picImg);
            backbtn=findViewById(R.id.backbtn);
            googleanchor=findViewById(R.id.googleanchor);
    }

    private void gotoUrl(String s){
            Uri uri = Uri.parse(s);
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}