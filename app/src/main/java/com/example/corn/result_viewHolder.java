package com.example.corn;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class result_viewHolder extends RecyclerView.ViewHolder {

     TextView name;
     TextView scientificName;
     ImageView Image;
     View view;

     result_viewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.pest_name);
        scientificName = (TextView) itemView.findViewById(R.id.pest_scientific_name);
        Image = (ImageView) itemView.findViewById(R.id.pest_image);
        view = itemView;

    }
}
