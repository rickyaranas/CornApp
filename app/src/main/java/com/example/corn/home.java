package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.corn.databinding.ActivityHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class home extends AppCompatActivity {
    ActivityHomeBinding binding;
    FloatingActionButton scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new homefragment());

        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Scan_Fragment.class); // Replace ScanFragment with the actual class name
                startActivity(intent);
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment scanFragment = new Scan_Fragment(); // Replace ScanFragment with the actual class name
                replaceFragment(scanFragment);
            }
        });

        binding.bottomnavigationview.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.home:
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
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

    }

}