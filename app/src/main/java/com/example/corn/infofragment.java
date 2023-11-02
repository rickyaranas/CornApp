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
        items.add(new PopularDomain("Common Rust", "Left", "is a foliar disease of corn favored by moist and cool conditions. The fungal pathogen does not overwinter in the Corn Belt;" +
                " windblown spores bring it north from the Southern U.S. Common rust is less likely to" +
                " cause significant yield loss than southern rust.",2, true, 4.8, "pic1", true));
        items.add(new PopularDomain("Northern Leaf Blight", "Right", "Northern corn leaf blight (NCLB) or Turcicum leaf blight (TLB) is a foliar disease of corn caused by Exserohilum turcicum, the anamorph of the ascomycete Setosphaeria turcica. With its " +
                "characteristic cigar-shaped lesions, this disease can cause significant yield loss in susceptible " +
                "corn hybrids.",1, true, 4.8, "pic2", true));
        items.add(new PopularDomain("Edwin Gwapo", "Center", "edwin povadora 1",2, true, 4.8, "pic3", true));


        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterPopular = new PopularAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);

        ArrayList<CategoryDomain> catlist = new ArrayList<>();

        catlist.add(new CategoryDomain("Disease1","cat1"));
        catlist.add(new CategoryDomain("Disease2","cat2"));
        catlist.add(new CategoryDomain("Disease3","cat3"));
        catlist.add(new CategoryDomain("Disease4","cat4"));

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategory = new category_Adapters(catlist);
        recyclerViewCategory.setAdapter(adapterCategory);



    }
}