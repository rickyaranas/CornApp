package Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.corn.R;

import java.util.ArrayList;

import Domains.DiseaseList_Domain;

public class DiseaseList_Adapter extends RecyclerView.Adapter<DiseaseList_Adapter.ViewHolder> {

    ArrayList<DiseaseList_Domain> items;

    public DiseaseList_Adapter(ArrayList<DiseaseList_Domain>items){
        this.items = items;
    }
    @NonNull
    @Override
    public DiseaseList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_list,parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseList_Adapter.ViewHolder holder, int position) {
        holder.disease_name.setText(items.get(position).getDisease_name());
        holder.severity.setText(items.get(position).getSeverity());
        holder.location.setText(items.get(position).getLocation());
        holder.date.setText(items.get(position).getDate());

        String imageUrl = "http://192.168.100.10/LoginRegister/images/disease_list/" + items.get(position).getImage();
        Glide.with(holder.disease_name.getContext())
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(40))
                .into(holder.list_pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Activities.diseaselist_details.class);
            intent.putExtra("object", items.get(position));
            intent.putExtra("image_url", items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView disease_name,severity,location,date;
        ImageView list_pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disease_name=itemView.findViewById(R.id.disease_name);
            severity=itemView.findViewById(R.id.severity);
            location=itemView.findViewById(R.id.location);
            date=itemView.findViewById(R.id.date);

            list_pic=itemView.findViewById(R.id.list_pic);

        }
    }
}
