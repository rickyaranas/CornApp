package com.example.corn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class homefragment extends Fragment {

    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        // Find the button in the inflated layout
        button = view.findViewById(R.id.button);

        // Set up click listener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment newFragment = new Scan_Fragment();
//
//                // Get the FragmentManager
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//
//                // Begin a FragmentTransaction
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//                // Replace the current fragment with the new fragment
//                transaction.replace(R.id.scan_fragment, newFragment);
//
//                // Add the transaction to the back stack
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commit();
            }
        });


        return view;
    }
}