package com.example.corn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
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

public class Detect extends AppCompatActivity {

    Button button;
    ImageView detectImage;
    TextView prediction, titletxt, loc, description, rectxt;
    ScrollView scrollView;
    int imageSize = 640;
    Bitmap bitmap;
    private ScannedDisease_Domain item;
    Blob image;
    private Geocoder geocoder;

    YOLOv5TFLiteDetector yolOv5TFLiteDetector;
    Paint boxpaint = new Paint();
    Paint texpaint = new Paint();
    private RecyclerView.Adapter scanned_disease_adapter;
    private RecyclerView recycler_View;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        detectImage = findViewById(R.id.imagetodetect);
        prediction = findViewById(R.id.detection_name);
        button = findViewById(R.id.button);
        titletxt = findViewById(R.id.titleTxt);
        loc = findViewById(R.id.locationTxt_1);
        description = findViewById(R.id.desctxt);
        rectxt = findViewById(R.id.rec_txt);

        scrollView =findViewById(R.id.scroll_View);
        geocoder = new Geocoder(this);

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission granted, get the last known location
            getLastLocation();
        }


        if (getIntent().hasExtra("imageUri")) {
            //receiving the URI path as String
            String dat = getIntent().getStringExtra("imageUri");
            //Converting the String back to Uri
            Uri imageUri = Uri.parse(dat);
                try {
                    // Locating the image path using URI and store the image in bitmap
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            //Resize image according to the expected image size of the model
            bitmap = Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);

        } else if (getIntent().hasExtra("byteArray")) {
            // receive captured image as bytearray and then converting bytearray to bitmap
            bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);

            //Resize image according to the expected image size of the model
            bitmap = Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);
        }

        // Calling the predict function to process image and get results.
        predict();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detect.this, home.class);
                startActivity(intent);
            }
        });
    }


    public void predict() {

        ArrayList<Recognition> recognitions = yolOv5TFLiteDetector.detect(bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        String Id = null;
        String pestname = null;
        String confidence = null;


        for (Recognition recognition : recognitions) {
            if (recognition.getConfidence() > 0.10
            ) {
                RectF location = recognition.getLocation();
                canvas.drawRect(location, boxpaint);

                confidence = String.format("(%.0f%%) ", recognition.getConfidence() * 100.0f);
                canvas.drawText(recognition.getLabelName() + ": " + confidence, location.left, location.top, texpaint);

                // getting the ID of the class
                confidence = String.valueOf(recognition.getConfidence());
                Id = String.valueOf(recognition.getLabelId());
                int idInt = Integer.parseInt(Id);
                idInt++; // Increment the id by 1
                Id = String.valueOf(idInt);

                String apiUrl = "http://192.168.100.8/LoginRegister/fetch_data.php?id=" + Id;
                Log.d("Generated URL", apiUrl); // Print the generated URL in Logcat

                apiset apiService = apiController.getInstance().getapi();
                Call<ArrayList<ScannedDisease_Domain>> call = apiService.getDisease(Id);

                call.enqueue(new Callback<ArrayList<ScannedDisease_Domain>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ScannedDisease_Domain>> call, Response<ArrayList<ScannedDisease_Domain>> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response here
                            ArrayList<ScannedDisease_Domain> diseases = response.body();

                            StringBuilder stringBuilder = new StringBuilder();
                            for (ScannedDisease_Domain disease : diseases) {
                                // Append each fetched data to the StringBuilder
                                TextView d = findViewById(R.id.d);
//                            d.setText(stringBuilder.toString());
                                titletxt.setText("Disease Name: " + disease.getDisease_name());
                             //   loc.setText("Location: " + disease.getLocation());
                                description.setText("" + disease.getDescription());
                                rectxt.setText("" + disease.getTreatment());
                            }

                            scrollView.scrollTo(0, 0);
                        } else {
                            // Handle error response here
                            // You can check response.errorBody() for more details
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ScannedDisease_Domain>> call, Throwable t) {
                        // Handle failure here
                        t.printStackTrace();
                    }
                });


                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    // Handle the response (read data from the API if needed)

                    // Close the connection
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        detectImage.setImageBitmap(mutableBitmap);
        prediction.setText(Id);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
    }


        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, get the last known location
                    getLastLocation();
                } else {
                    // Permission denied, handle accordingly
                }
            }
        }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                                if (loc != null) {
                                    loc.setText("Latitude: " + latitude + ", Longitude: " + longitude);
                                    reverseGeocode(latitude, longitude);
                                } else {
                                    Log.e("Location", "TextView 'loc' is null");
                                }
                            } else {
                                Log.e("Location", "Last known location is null");
                            }
                        }
                    });
        } else {
            Log.e("Location", "Permission ACCESS_FINE_LOCATION not granted");
        }
    }
    private void reverseGeocode(double latitude, double longitude) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String baranggayName = address.getSubLocality(); // Try to get baranggay name
                if (baranggayName == null || baranggayName.isEmpty()) {
                    baranggayName = address.getLocality(); // Use city name as baranggay name
                }
                String cityName = address.getLocality(); // Get the city name
                String countryName = address.getAdminArea(); // Get the country name

                // Display the address information in your app
                Log.d("Address Info", "Baranggay: " + baranggayName + ", City: " + cityName + ", Country: " + countryName);
            } else {
                Log.e("Reverse Geocoding", "No address found for the given coordinates");
            }
        } catch (IOException e) {
            Log.e("Reverse Geocoding", "Error: " + e.getMessage());
        }

    }
    }