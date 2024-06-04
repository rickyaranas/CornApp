package com.example.corn;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//import com.c.meowbottomnavigation.MeowBottomNavigation;
import com.example.corn.customview.OverlayView;
import com.example.corn.env.ImageUtils;
import com.example.corn.env.Logger;
import com.example.corn.tflite.Classifier;
import com.example.corn.tflite.YoloV5Classifier;
import com.example.corn.tracking.MultiBoxTracker;

import com.example.corn.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    FloatingActionButton scanButton;
    String userId;
    id_Holder id = id_Holder.getInstance();
    private static final long DOUBLE_TAP_INTERVAL = 2000; // 2 seconds interval for double tap
    private long lastBackPressedTime = 0;

    private static final String PREF_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);
        id.hold_id(Integer.parseInt(userId));

        replaceFragment(new homefragment());

        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(),DetectorActivity.class);
               i.putExtra("user_Id",userId);
               startActivity(i);
            }
        });

        binding.bottomnavigationview.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {


                case R.id.home:
                    homefragment homeFragment = new homefragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", userId); // userId is the variable holding the user id
                    homeFragment.setArguments(bundle);
                    replaceFragment(new homefragment());
                    break;

                case R.id.info:

                    replaceFragment(new infofragment());

                    break;

                case R.id.list:
                    replaceFragment(new listfragment());
                    break;
                case R.id.settings:
                    replaceFragment(new settingsfragment());
                    break;

                case R.id.scan:
                    replaceFragment(new scanfragment());
                    break;
            }
            return true;
        });

    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressedTime < DOUBLE_TAP_INTERVAL) {
            // If the interval between taps is less than the predefined interval,
            // exit the app
            super.onBackPressed();
        } else {
            // Otherwise, update the lastBackPressedTime and show a Toast message
            lastBackPressedTime = currentTime;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId); // Put userId into the bundle
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

    }
    public void deselect() {
        BottomNavigationView bottomNavigationView = binding.bottomnavigationview; // Replace with your ID
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (!item.isChecked()) { // Only deselect if the item is not already selected
                item.setChecked(false);
            }
        }

    }

    private static final Logger LOGGER = new Logger();

    public static final int TF_OD_API_INPUT_SIZE = 640;

    private static final boolean TF_OD_API_IS_QUANTIZED = false;

    private static final String TF_OD_API_MODEL_FILE = "best-fp16_old_1.tflite";
    private static final String TF_OD_API_LABELS_FILE = "customclasses.txt";
    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = true;
    private Integer sensorOrientation = 90;
    private Classifier detector;
    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private MultiBoxTracker tracker;
    private OverlayView trackingOverlay;
    protected int previewWidth = 0;
    protected int previewHeight = 0;
    //    private Bitmap sourceBitmap;
//    private Bitmap cropBitmap;
//    private Button cameraButton, detectButton;
//    private ImageView imageView;
    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;



    private void initBox() {
        previewHeight = TF_OD_API_INPUT_SIZE;
        previewWidth = TF_OD_API_INPUT_SIZE;
        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE,
                        sensorOrientation, MAINTAIN_ASPECT);

        cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);

        tracker = new MultiBoxTracker(this);
        trackingOverlay = findViewById(R.id.tracking_overlay);
        trackingOverlay.addCallback(
                canvas -> tracker.draw(canvas));

        tracker.setFrameConfiguration(TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, sensorOrientation);

        try {
            detector =
                    YoloV5Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED,
                            TF_OD_API_INPUT_SIZE);
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }


    private void handleResult(Bitmap bitmap, List<Classifier.Recognition> results) {
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);

        final List<Classifier.Recognition> mappedRecognitions =
                new LinkedList<Classifier.Recognition>();

        for (final Classifier.Recognition result : results) {
            final RectF location = result.getLocation();
            if (location != null && result.getConfidence() >= MINIMUM_CONFIDENCE_TF_OD_API) {
                canvas.drawRect(location, paint);
//                cropToFrameTransform.mapRect(location);
//
//                result.setLocation(location);
//                mappedRecognitions.add(result);
            }
        }
//        tracker.trackResults(mappedRecognitions, new Random().nextInt());
//        trackingOverlay.postInvalidate();
//        imageView.setImageBitmap(bitmap);
    }


}