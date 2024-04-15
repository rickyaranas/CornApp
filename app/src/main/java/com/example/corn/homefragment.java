package com.example.corn;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class homefragment extends Fragment {

    private Button button;
    ImageButton GalleryButton, CameraButton;
    Bitmap capturedImage;
     TextView username;
     String userId;
    int REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;

    holdBitmap hold = holdBitmap.getInstance();
    RelativeLayout loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        // Find the button in the inflated layout
//        button = view.findViewById(R.id.button);
        GalleryButton = view.findViewById(R.id.togallery);
        CameraButton = view.findViewById(R.id.to_camera);

        loading = view.findViewById(R.id.loading);
        userId = getArguments().getString("userId");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());



        GalleryButton.setOnClickListener(v ->selectGallery());
        CameraButton.setOnClickListener(v ->openCamera());
        return view;
    }

    private void selectGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
    }
    private void openCamera(){

        if (checkCameraPermission()) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 3);
        } else {
            requestCameraPermission();
        }
    }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            if(resultCode == RESULT_OK){


                loading.setVisibility(View.VISIBLE);
                //Use 3 for RequestCode in capturing image.
                if(requestCode == 1) {

                    // Get the selected image and store it in a bitmap
                    assert data != null;
                    Uri dat = data.getData();
                    Bitmap bitmap;
                    try {
                        // Locating the image path using URI and store the image in bitmap
                        bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), dat);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                    hold.setImage(bitmap);


                    // Pass the image Uri to Detect.java
                    Intent intent = new Intent(requireContext(), Detect.class);
//                    assert dat != null;
//                    intent.putExtra("imageUri", dat.toString());
                    intent.putExtra("userID",userId);
//                    getLastKnownLocation(intent);
                    startActivity(intent);

                }

                else if (requestCode == 3) {
                    // Get the Captured image and store it in a Bitmap Variable
                    assert data != null;
                    capturedImage =(Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    assert capturedImage != null;
                    int dimension = Math.min(capturedImage.getWidth(), capturedImage.getHeight());
                    capturedImage = ThumbnailUtils.extractThumbnail(capturedImage, dimension, dimension);

                    hold.setImage(capturedImage);

                    Intent i = new Intent(requireContext(), Detect.class);
//                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                    capturedImage.compress(Bitmap.CompressFormat.PNG, 100, bs);
//                    i.putExtra("byteArray", bs.toByteArray());
                    i.putExtra("userID",userId);
                    getLastKnownLocation(i);
                    startActivity(i);

//                    Intent intent = new Intent(requireContext(),Detect.class);
//                    i.putExtra("userID",userId);
//                    getLastKnownLocation(intent);
//                    startActivity(intent);

                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }



    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    // Request Permission for the Camera
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
    }
    private void getLastKnownLocation(Intent intent) {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            getAddressFromLocation(latitude, longitude, intent);
                        }
                    });
        }
    }

    private void getAddressFromLocation(double latitude, double longitude, Intent intent) {
        Geocoder geocoder = new Geocoder(requireContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String cityName = address.getLocality();
                String streetName = address.getAddressLine(0); // Get the first line of address
                String fullAddress = address.getAddressLine(0); // You can also get the full address
                intent.putExtra("cityName", cityName);
                intent.putExtra("streetName", streetName);
                intent.putExtra("fullAddress", fullAddress);
                Log.d("Address Info", "Baranggay: " + cityName + ", City: " + streetName + ", Country: " + fullAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

}