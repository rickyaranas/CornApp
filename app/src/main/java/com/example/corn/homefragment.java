package com.example.corn;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;


public class homefragment extends Fragment {

    private Button button;
    ImageButton GalleryButton, CameraButton;
    Bitmap capturedImage;
    int REQUEST_CODE = 100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        // Find the button in the inflated layout
//        button = view.findViewById(R.id.button);
        GalleryButton = view.findViewById(R.id.togallery);
        CameraButton = view.findViewById(R.id.to_camera);


        GalleryButton.setOnClickListener(v ->selectGallery());
        CameraButton.setOnClickListener(v ->openCamera());

        // Set up click listener for the button
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Fragment newFragment = new Scan_Fragment();
////
////                // Get the FragmentManager
////                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
////
////                // Begin a FragmentTransaction
////                FragmentTransaction transaction = fragmentManager.beginTransaction();
////
////                // Replace the current fragment with the new fragment
////                transaction.replace(R.id.scan_fragment, newFragment);
////
////                // Add the transaction to the back stack
////                transaction.addToBackStack(null);
////
////                // Commit the transaction
////                transaction.commit();
//            }
//        });


        return view;
    }

    private void selectGallery() {
        // function to open gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);

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

                //Use 3 for RequestCode in capturing image.
                if(requestCode == 1) {

                    // Get the selected image and store it in a bitmap
                    Uri dat = data.getData();

                    // Pass the image Uri to Detect.java
                    Intent intent = new Intent(requireContext(), Detect.class);
                    intent.putExtra("imageUri", dat.toString());
                    startActivity(intent);
                }

                else if (requestCode == 3) {
                    // Get the Captured image and store it in a Bitmap Variable
                    capturedImage = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(capturedImage.getWidth(), capturedImage.getHeight());
                    capturedImage = ThumbnailUtils.extractThumbnail(capturedImage, dimension, dimension);

                    Intent i = new Intent(requireContext(), Detect.class);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    capturedImage.compress(Bitmap.CompressFormat.PNG, 50, bs);
                    i.putExtra("byteArray", bs.toByteArray());
                    startActivity(i);

                    Intent intent = new Intent(requireContext(),Detect.class);
                    startActivity(intent);

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

}