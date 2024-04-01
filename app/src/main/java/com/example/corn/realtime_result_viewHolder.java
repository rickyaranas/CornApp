package com.example.corn;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class realtime_result_viewHolder extends RecyclerView.ViewHolder {

     TextView id;
     TextView name;
     TextView confidence;
     View view;

     realtime_result_viewHolder(View itemView) {
        super(itemView);

        id = (TextView) itemView.findViewById(R.id.pest_ID);
        name = (TextView) itemView.findViewById(R.id.pest_NAME);
        confidence = (TextView) itemView.findViewById(R.id.pest_CONFIDENCE);
        view = itemView;

    }
}
