package com.example.corn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class realtime_resultAdapter extends RecyclerView.Adapter<realtime_result_viewHolder> {


    List<realtime_resultData> list = Collections.emptyList();
    Context context;
    realtime_resultClicklistener listener;

    public realtime_resultAdapter(List<realtime_resultData> list, Context context, realtime_resultClicklistener listener){
        this.list = list;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public realtime_result_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View photoView = inflater.inflate(R.layout.realtime_result, parent, false);
        realtime_result_viewHolder viewHolder = new realtime_result_viewHolder(photoView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final realtime_result_viewHolder sviewHolder,final int position) {
        final int index = sviewHolder.getAdapterPosition();
        sviewHolder.id.setText(list.get(position).result_id);
        sviewHolder.name.setText(list.get(position).result_name);
        sviewHolder.confidence.setText(list.get(position).result_confidence);
        sviewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
