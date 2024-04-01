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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    TextView prediction, titletxt, loc, description, rectxt, dateTV;
    ScrollView scrollView;
    int imageSize = 640;
    Bitmap bitmap;
    private ScannedDisease_Domain item;
    Blob image;
    private Geocoder geocoder;
    String userId, cityName;
    String currentDate;
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
        dateTV = findViewById(R.id.date_1);

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

        ArrayList<Recognition> recognitions = yolOv5TFLiteDetector.detect(bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        String Id = null;
        String confidence = null;


        for (Recognition recognition : recognitions) {
            if (recognition.getConfidence() > 0.50
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
                String apiUrl = "http://192.168.100.5/LoginRegister/fetch_data.php?id=" + Id;
                Log.d("Generated URL", apiUrl); // Print the generated URL in Logcat

                apiset apiService = apiController.getInstance().getapi();
                Call<ArrayList<ScannedDisease_Domain>> call = apiService.getDisease(Id);

                String finalConfidence = confidence;
                call.enqueue(new Callback<ArrayList<ScannedDisease_Domain>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ScannedDisease_Domain>> call, Response<ArrayList<ScannedDisease_Domain>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<ScannedDisease_Domain> diseases = response.body();

                            for (ScannedDisease_Domain disease : diseases) {
                                TextView d = findViewById(R.id.d);
                                d.setText(""+userId);
                                titletxt.setText("Disease Name: " + disease.getDisease_name());
                                description.setText("" + disease.getDescription());
                                rectxt.setText("" + disease.getTreatment());

                                Date date_1 = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                currentDate = dateFormat.format(date_1);
                                dateTV.setText("Date: "+currentDate);

                                byte[] imageBytes = imageViewToBy(mutableBitmap);

                                final String user_id, disease_name, location, date, image, confidence_d;
                                user_id = d.getText().toString();
                                disease_name = disease.getDisease_name();

                                location = loc.getText().toString();
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
                                        data[2] = location;
                                        data[3] = date;
                                        data[4] = Base64.encodeToString(imageBytes, Base64.DEFAULT);;
                                        data[5] = finalConfidence;

                                        Log.d("EditTextDebug", "fullname: " + data[0]+data[1]+data[2]+data[3]);
                                        Log.d("edwin,","user_id: " + data[0]);
                                        Log.d("edwin,","image_name: " + data[2]);
                                        Log.d("edwin,","image_name: " + mutableBitmap);

                                        PutData putData = new PutData("http://192.168.100.5/LoginRegister/madam.php", "POST", field, data);
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
                                    //loc.setText("Latitude: " + latitude + ", Longitude: " + longitude);
                                    reverseGeocode(latitude, longitude);
                                    loc.setText(""+cityName);

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
    public void reverseGeocode(double latitude, double longitude) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String baranggayName = address.getSubLocality(); // Try to get baranggay name
                if (baranggayName == null || baranggayName.isEmpty()) {
                    baranggayName = address.getLocality(); // Use city name as baranggay name
                }
                 cityName = address.getLocality(); // Get the city name
                String countryName = address.getAdminArea(); // Get the country name
                loc.setText(""+cityName);

                // Display the address information in your app
                Log.d("Address Info", "Baranggay: " + baranggayName + ", City: " + cityName + ", Country: " + countryName);
            } else {
                Log.e("Reverse Geocoding", "No address found for the given coordinates");
            }
        } catch (IOException e) {
            Log.e("Reverse Geocoding", "Error: " + e.getMessage());
        }

    }
    public static byte[] imageViewToBy(Bitmap mutableBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }
    }