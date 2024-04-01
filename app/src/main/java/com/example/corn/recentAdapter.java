package com.example.corn;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class recentAdapter extends RecyclerView.Adapter<recentViewholder> {


    List<recentdetectionData> list = Collections.emptyList();
    Context context;
    recentClicklistener listener;

    public recentAdapter(List<recentdetectionData> list, Context context, recentClicklistener listener){
        this.list = list;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public recentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View photoView = inflater.inflate(R.layout.recent_detection_layoute, parent, false);
        recentViewholder recent = new recentViewholder(photoView);
        return recent;
    }

    @Override
    public void onBindViewHolder(@NonNull final recentViewholder viewHolder,final int position) {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.name.setText(list.get(position).name);
        viewHolder.confidence.setText(list.get(position).confidence);
        viewHolder.Image.setImageBitmap(list.get(position).Image);
        viewHolder.time.setText(list.get(position).time);
        viewHolder.date.setText(list.get(position).date);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                listener.click(index);
                recentdetectionData selectedItem = list.get(position);

                //Dummy PopUp to darken the background when the actual popup is displayed
                //INflating the customlayout
                LayoutInflater inflater0 = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView0 = inflater0.inflate(R.layout.dummy_popup, null);

                int windth0 = WindowManager.LayoutParams.MATCH_PARENT;
                int height0 = WindowManager.LayoutParams.MATCH_PARENT;
                PopupWindow dummyPopup = new PopupWindow(popupView0,windth0,height0,true);
                dummyPopup.showAtLocation(view, Gravity.CENTER, 0, 0);



                // ACTUAL POPUP WINDOW
                //Inflating the customlayout
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.pop_up_layout1, null);

                //Instantiating the views
                final RelativeLayout rootLayout = popupView.findViewById(R.id.root_layout);
                ImageView image = popupView.findViewById(R.id.pop_image);
                TextView pestname = popupView.findViewById(R.id.pop_name);
                TextView confidence = popupView.findViewById(R.id.pop_confidence);
                TextView date_time = popupView.findViewById(R.id.pop_time_detected);

                image.setImageBitmap(selectedItem.Image);
                pestname.setText(selectedItem.name);
                confidence.setText(selectedItem.confidence);
                date_time.setText("Date & Time detected: "+selectedItem.date+" / "+selectedItem.time);

                //Creating the pop up window
                int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                //Show the popUpwindow
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if(dummyPopup.isShowing()){
                            dummyPopup.dismiss();
                        }
                    }
                });

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
