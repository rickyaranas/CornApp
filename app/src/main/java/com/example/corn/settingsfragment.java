package com.example.corn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class settingsfragment extends Fragment {

  private ImageView aboutusbtn, contactusbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_settingsfragment, container, false);

        // Find the button in the inflated layout
        aboutusbtn = view.findViewById(R.id.aboutusbtn);
        contactusbtn = view.findViewById(R.id.contactusbtn);
        // Set up click listener for the button
        aboutusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), about_us.class);
                startActivity(intent);

//                FragmentManager fragmentManager = getSupportFragmentManager();
//                  Intent intent = new Intent(login.this, home.class);
//                // Begin a FragmentTransaction
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//                // Replace the current fragment with ScanFragment
//                transaction.replace(R.id.scanfragment, new scanfragment());
//
//                // Commit the transaction
//                transaction.commit();
            }
        });
        contactusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),contactus.class);
                startActivity(intent);
            }
        });


        return view;
    }
}