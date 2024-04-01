package com.example.corn;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class recentViewholder extends RecyclerView.ViewHolder {

    TextView name;
    TextView confidence;
    ImageView Image;
    TextView date;
    TextView time;
    View view;

    recentViewholder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.pest_name);
        confidence = itemView.findViewById(R.id.pest_confidence);
        Image = itemView.findViewById(R.id.pest_image);
        date = itemView.findViewById(R.id.date);
        time = itemView.findViewById(R.id.time);
        view = itemView;

    }
}
