package com.example.corn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Adapters.PopularAdapter;
import Adapters.category_Adapters;
import Domains.CategoryDomain;
import Domains.PopularDomain;

public class infofragment extends Fragment {

    private RecyclerView.Adapter adapterPopular, adapterCategory;
    private RecyclerView recyclerViewPopular, recyclerViewCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_infofragment, container, false);

        recyclerViewPopular = rootView.findViewById(R.id.view_pop);
        recyclerViewCategory = rootView.findViewById(R.id.view_cat);
         initRecyclerView();



        return rootView;

    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Common Rust", "Bohol", "is a foliar disease of corn favored by moist and cool conditions. The fungal pathogen does not overwinter in the Corn Belt;" +
                " windblown spores bring it north from the Southern U.S. Common rust is less likely to" +
                " cause significant yield loss than southern rust.",2, true, 4.8, "common_rust", true));
        items.add(new PopularDomain(" Leaf Blight", "Bohol", "Northern corn leaf blight (NCLB) or Turcicum leaf blight (TLB) is a foliar disease of corn caused by Exserohilum turcicum, the anamorph of the ascomycete Setosphaeria turcica. With its " +
                "characteristic cigar-shaped lesions, this disease can cause significant yield loss in susceptible " +
                "corn hybrids.",1, true, 4.8, "leaf_blight", true));
        items.add(new PopularDomain("Gray Leaf Spot", "Bohol", "caused by the fungus Cercospora zeae-maydis, occurs virtually every growing season. If conditions favor disease development, economic losses can occur. Symptoms first appear on lower leaves about two to three weeks before tasseling.",2, true, 4.8, "leaf_spot", true));


        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterPopular = new PopularAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);

        ArrayList<CategoryDomain> catlist = new ArrayList<>();

        catlist.add(new CategoryDomain("Sagbayan","sagbayan","Visit the farms for an immersive experience " +
                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
                " in the fresh aroma of golden corn as you stroll through " +
                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","V3JF+2X4, Sta. Catalina, Sagbayan, 6331 Bohol"));
        catlist.add(new CategoryDomain("Carmen","cat2", "Visit the farms for an immersive experience " +
                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
                " in the fresh aroma of golden corn as you stroll through " +
                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","Q5R5+54X, Loay Interior Road, Batuan, Bohol"));
        catlist.add(new CategoryDomain("Bilar","cat3", "Visit the farms for an immersive experience " +
                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
                " in the fresh aroma of golden corn as you stroll through " +
                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.", "Bilar"));
        catlist.add(new CategoryDomain("Valencia","cat4", "Visit the farms for an immersive experience " +
                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
                " in the fresh aroma of golden corn as you stroll through " +
                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","J665+3V9, Valencia, Bohol"));
        catlist.add(new CategoryDomain("Garcia","cat4", "Visit the farms for an immersive experience " +
                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
                " in the fresh aroma of golden corn as you stroll through " +
                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","J665+3V9, Valencia, Bohol"));
        catlist.add(new CategoryDomain("Ubay","cat4", "Visit the farms for an immersive experience " +
                "into the world of corn cultivation. Explore the lush fields, witness the meticulous care given to each stalk, and" +
                " learn about the journey from seed to harvest. Discover the secrets of sustainable farming practices and indulge" +
                " in the fresh aroma of golden corn as you stroll through " +
                "our local farms. Come, embrace the essence of agriculture and connect with nature at our corn farms today.","J665+3V9, Valencia, Bohol"));

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategory = new category_Adapters(catlist);
        recyclerViewCategory.setAdapter(adapterCategory);



    }
}