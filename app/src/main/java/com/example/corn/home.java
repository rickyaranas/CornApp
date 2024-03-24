package com.example.corn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.corn.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

}