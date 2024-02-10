package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corn.R;

import Domains.PopularDomain;

public class details extends AppCompatActivity {
    private TextView desctxt, titletxt, bedtxt,guidetxt,wifitxt, titletext, diseasename;
    private PopularDomain item;
    private ImageView backbtn,  picImg;
        @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        setVariable();
    }

    private void setVariable() {
        item = (PopularDomain) getIntent().getSerializableExtra("object");

        titletext.setText(item.getTitle());
        desctxt.setText(item.getDescription());

        int drawableResId = getResources().getIdentifier(item.getPic(), "drawable", getPackageName());

        Glide.with(this)
                .load(drawableResId)
                .into(picImg);
        backbtn.setOnClickListener(v -> finish());


    }

    private void initView() {
            titletext = findViewById(R.id.titletext);
            desctxt=findViewById(R.id.desctxt);
            picImg=findViewById(R.id.picImg);
            backbtn=findViewById(R.id.backbtn);
    }
}