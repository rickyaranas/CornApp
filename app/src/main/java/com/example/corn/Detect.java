package com.example.corn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import Domains.ScannedDisease_Domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Detect extends AppCompatActivity {

    Button button;
    ImageView detectImage;
    TextView C_level, titletxt, loc, description, rectxt, dateTV;
    ScrollView scrollView;
    int imageSize = 640;
    Bitmap bitmap;
    Bitmap scaledBitmap;
    private ScannedDisease_Domain item;
    Blob image;
    private Geocoder geocoder;
    boolean has_predicted = false;
    String userId, cityName;
    String currentDate;
    String finalConfidence;
    YOLOv5TFLiteDetector yolOv5TFLiteDetector;
    Paint boxpaint = new Paint();
    Paint texpaint = new Paint();
    private RecyclerView.Adapter scanned_disease_adapter;
    private RecyclerView recycler_View;
    base_url url = base_url.getInstance();
    id_Holder id = id_Holder.getInstance();
    location_Tracker locationa = location_Tracker.getInstance();
    holdBitmap hold = holdBitmap.getInstance();
    String location;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationClient;

    RelativeLayout loading;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        detectImage = findViewById(R.id.imagetodetect);
        button = findViewById(R.id.button);
        titletxt = findViewById(R.id.titleTxt);
        loc = findViewById(R.id.locationTxt_1);
        description = findViewById(R.id.desctxt);
        rectxt = findViewById(R.id.rec_txt);
        dateTV = findViewById(R.id.date_1);
        C_level = findViewById(R.id.C_level);


        scrollView =findViewById(R.id.scroll_View);
        geocoder = new Geocoder(this);

        userId = getIntent().getStringExtra("userID");

        yolOv5TFLiteDetector = new YOLOv5TFLiteDetector();
        yolOv5TFLiteDetector.setModelFile("best-fp16.tflite");
        yolOv5TFLiteDetector.initialModel(this);

        //for the bounding box
        boxpaint.setStrokeWidth(2);
        boxpaint.setStyle(Paint.Style.STROKE);
        boxpaint.setColor(Color.GREEN);

        //for the class label and confidence level
        texpaint.setTextSize(30);
        texpaint.setColor(Color.GREEN);
        texpaint.setStyle(Paint.Style.FILL);

        bitmap = hold.getImage();
        scaledBitmap = Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);

        predict();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detect.this, home.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }


    public void predict() {

        ArrayList<Recognition> recognitions = yolOv5TFLiteDetector.detect(scaledBitmap);
        Bitmap mutableBitmap = scaledBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);


        String Id = null;
        String confidence = null;
//        String address = location.getTown();


        Call<ArrayList<ScannedDisease_Domain>> call = null;

        for (Recognition recognition : recognitions) {
            if (recognition.getConfidence() > 0.50
            ) {
                has_predicted = true;
                RectF location = recognition.getLocation();
                canvas.drawRect(location, boxpaint);

                confidence = String.format(" %.0f%% ", recognition.getConfidence() * 100.0f);
                canvas.drawText(recognition.getLabelName() + ": " + confidence, location.left, location.top, texpaint);

                Id = String.valueOf(recognition.getLabelId());
                int idInt = Integer.parseInt(Id);
                idInt++; // Increment the id by 1
                Id = String.valueOf(idInt);
                String apiUrl = url.getBase_url() + "LoginRegister/fetch_data.php?id=" + Id;
                Log.d("Generated URL", apiUrl); // Print the generated URL in Logcat

                apiset apiService = apiController.getInstance().getapi();
                call = apiService.getDisease(Id);

                 finalConfidence = confidence;
//                call.enqueue(new Callback<ArrayList<ScannedDisease_Domain>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<ScannedDisease_Domain>> call, Response<ArrayList<ScannedDisease_Domain>> response) {
//                        if (response.isSuccessful()) {
//                            ArrayList<ScannedDisease_Domain> diseases = response.body();
//
//                            for (ScannedDisease_Domain disease : diseases) {
//                                TextView d = findViewById(R.id.d);
//                                d.setText(""+userId);
//                                titletxt.setText("Disease Name: " + disease.getDisease_name());
//                                description.setText("" + disease.getDescription());
//                                rectxt.setText("" + disease.getTreatment());
//
//                                Date date_1 = new Date();
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                                currentDate = dateFormat.format(date_1);
//                                dateTV.setText("Date: "+currentDate);
//
//                                byte[] imageBytes = imageViewToBy(mutableBitmap);
//
//                                final String user_id, disease_name, location, date, image, confidence_d;
//                                user_id = d.getText().toString();
//                                disease_name = disease.getDisease_name();
//
//                                location = address;
//                                date = currentDate;
//                                confidence_d = finalConfidence.toString();
//
//                                Handler handler = new Handler();
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        String[] field = new String[6];
//                                        field[0] = "user_id";
//                                        field[1] = "disease_name";
//                                        field[2] = "location";
//                                        field[3] = "date";
//                                        field[4] = "image_name";
//                                        field[5] = "confidence_level";
//
//                                        String[] data = new String[6];
//                                        data[0] = user_id;
//                                        data[1] = disease_name;
//                                        data[2] = location;
//                                        data[3] = date;
//                                        data[4] = Base64.encodeToString(imageBytes, Base64.DEFAULT);;
//                                        data[5] = finalConfidence;
//
//                                        Log.d("EditTextDebug", "fullname: " + data[0]+data[1]+data[2]+data[3]);
//                                        Log.d("edwin,","user_id: " + data[0]);
//                                        Log.d("edwin,","image_name: " + data[2]);
//                                        Log.d("edwin,","image_name: " + mutableBitmap);
//
//                                        PutData putData = new PutData(url.getBase_url()+"LoginRegister/madam.php", "POST", field, data);
//                                        if (putData.startPut()) {
//                                            if (putData.onComplete()) {
//                                                String result = putData.getResult();
//
//                                                if (result.contains("Sign Up Success")) {
//                                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                                                    Log.i("PutData", result);
////                                                    Intent ed = new Intent(getApplicationContext(),login.class);
////                                                    startActivity(ed);
//
//
//                                                } else {
//                                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                                                    Log.d("EditTextDebug", "last: " + result);
//                                                }
//
//                                            }
//                                        }
//
//                                    }
//                                });
//
//                            }
//                            scrollView.scrollTo(0, 0);
//                        } else {
//                        }
//                    }

                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

        if(has_predicted) {
            call.enqueue(new Callback<ArrayList<ScannedDisease_Domain>>() {
                @Override
                public void onResponse(Call<ArrayList<ScannedDisease_Domain>> call, Response<ArrayList<ScannedDisease_Domain>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<ScannedDisease_Domain> diseases = response.body();

                        for (ScannedDisease_Domain disease : diseases) {

//                            d.setText("" + userId);
                            titletxt.setText("Disease Name: " + disease.getDisease_name());
                            description.setText("" + disease.getDescription());
                            rectxt.setText("" + disease.getTreatment());
                            location = locationa.getTown();
                            C_level.setText("Confidence Level: "+ finalConfidence);
                            loc.setText(location);
                            Log.d("edwin,", "locations: " + location);

                            Date date_1 = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            currentDate = dateFormat.format(date_1);
                            dateTV.setText("Date: " + currentDate);

                            byte[] imageBytes = imageViewToBy(mutableBitmap);

                            final String user_id, disease_name, locations, date, image, confidence_d;
                            user_id = String.valueOf(id.retrieve_id());
                            disease_name = disease.getDisease_name();

                            locations = location;
                            date = currentDate;
                            confidence_d = finalConfidence.toString();

                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[6];
                                    field[0] = "user_id";
                                    field[1] = "disease_name";
                                    field[2] = "location";
                                    field[3] = "date";
                                    field[4] = "image_name";
                                    field[5] = "confidence_level";

                                    String[] data = new String[6];
                                    data[0] = user_id;
                                    data[1] = disease_name;
                                    data[2] = locations;
                                    data[3] = date;
                                    data[4] = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                                    data[5] = finalConfidence;

                                    Log.d("EditTextDebug", "fullname: " + data[0] + data[1] + data[2] + data[3]);
                                    Log.d("edwin,", "user_id: " + data[0]);
                                    Log.d("edwin,", "locations: " + data[2]);
                                    Log.d("edwin,", "image_name: " + mutableBitmap);

                                    PutData putData = new PutData(url.getBase_url() + "LoginRegister/madam.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();

                                            if (result.contains("Sign Up Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Log.i("PutData", result);
//                                                    Intent ed = new Intent(getApplicationContext(),login.class);
//                                                    startActivity(ed);


                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Log.d("EditTextDebug", "last: " + result);
                                            }

                                        }
                                    }

                                }
                            });

                        }
                        scrollView.scrollTo(0, 0);
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ScannedDisease_Domain>> call, Throwable t) {
                    // Handle failure here
                    t.printStackTrace();
                }
            });
            has_predicted = false;

        }

//        if (!has_predicted) {
//            // Display error dialogue that no disease was detected
//            Intent i = new Intent(getApplicationContext(),home.class);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("No disease detected")
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // Close the dialogue if OK is clicked
//                            startActivity(i);
//                            dialog.dismiss();
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }

        detectImage.setImageBitmap(mutableBitmap);
//        prediction.setText(Id);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
    }
    public static byte[] imageViewToBy(Bitmap mutableBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }
    public void onBackPressed() {
        // Perform your desired action when the back button is pressed
        Intent i = new Intent(getApplicationContext(),home.class);
        startActivity(i);
        super.onBackPressed();
    }
    }