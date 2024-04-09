package Adapters;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.corn.R;

import java.util.ArrayList;

import Domains.DiseaseList_Domain;
import Domains.Disease_info;

public class disease_RT_adapter extends RecyclerView.Adapter<disease_RT_adapter.ViewHolder> {

    ArrayList<Disease_info> items;

    public disease_RT_adapter(ArrayList<Disease_info>items){
        this.items = items;
    }
    @NonNull
    @Override
    public disease_RT_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_rt,parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull disease_RT_adapter.ViewHolder holder, int position) {
        holder.disease_name.setText(items.get(position).getDisease_name());
//        holder.severity.setText(items.get(position).getSeverity());
//        holder.location.setText(items.get(position).getLocation());
//        holder.date.setText(items.get(position).getDate());

        String imageUrl = "http://192.168.100.9/LoginRegister/images/disease_images/" + items.get(position).getImage();
        Log.d("edwin",imageUrl);
        Glide.with(holder.disease_name.getContext())
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(40))
                .into(holder.list_pics);
//
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.itemView.getContext(), Activities.diseaselist_details.class);
//            intent.putExtra("object", items.get(position));
//            intent.putExtra("image_url", items.get(position));
//            holder.itemView.getContext().startActivity(intent);
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater0 = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView0 = inflater0.inflate(R.layout.dummy_popup, null);

                int windth0 = WindowManager.LayoutParams.MATCH_PARENT;
                int height0 = WindowManager.LayoutParams.MATCH_PARENT;
                PopupWindow dummyPopup = new PopupWindow(popupView0,windth0,height0,true);
                dummyPopup.showAtLocation(view, Gravity.CENTER, 0, 0);


                // ACTUAL POPUP WINDOW
                //Inflating the customlayout
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.pop_pest_info, null);

                //Instantiating the views
                ImageView image = popupView.findViewById(R.id.pestImage);
                TextView name = popupView.findViewById(R.id.pestName);
                TextView scientific = popupView.findViewById(R.id.pestScientific);
                TextView order = popupView.findViewById(R.id.pestOrder);
                TextView family = popupView.findViewById(R.id.pestFamily);
                TextView description = popupView.findViewById(R.id.pestDescription);
                TextView intervention = popupView.findViewById(R.id.pestIntervention);


//                image.setImageBitmap(selectedItem.Image);
//                name.setText(selectedItem.name);
//                scientific.setText(selectedItem.scientific);
//                order.setText(selectedItem.order);
//                family.setText(selectedItem.family);
//                description.setText(selectedItem.description);
//                intervention.setText(selectedItem.intervention);



                //Creating the pop up window
                int width = ScrollView.LayoutParams.WRAP_CONTENT;
                int height = ScrollView.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, 1022, 1636, true);
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

    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView disease_name,severity,location,date;
        ImageView list_pics;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disease_name=itemView.findViewById(R.id.disease_name);
//            severity=itemView.findViewById(R.id.severity);
//            location=itemView.findViewById(R.id.location);
//            date=itemView.findViewById(R.id.date);

            list_pics=itemView.findViewById(R.id.list_pics);

        }
    }
}
