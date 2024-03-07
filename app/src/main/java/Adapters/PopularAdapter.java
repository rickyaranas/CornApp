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
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.corn.R;

import java.util.ArrayList;

import Domains.PopularDomain;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    ArrayList<PopularDomain> items;
//    DecimalFormat formatter;

    public PopularAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
//        formatter = new DecimalFormat("#,###.##");
    }


    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_popular,parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        holder.titleTxt.setText(items.get(position).getDisease_name());
        holder.locationTxt.setText(items.get(position).getLocation());

//        int drawableResId = holder.itemView.getResources().getIdentifier(items.get(position).getPic()
//                , "http://192.168.100.10/LoginRegister/images/", holder.itemView.getContext().getPackageName());
//
//        Glide.with(holder.itemView.getContext()).load(drawableResId)
//                .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
//                .into(holder.pic);
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent=new Intent(holder.itemView.getContext(), Activities.details.class);
//            intent.putExtra("object",items.get(position));
//            holder.itemView.getContext().startActivity(intent);

        String imageUrl = "http://192.168.100.10/LoginRegister/images/disease_images/" + items.get(position).getPic();
        Glide.with(holder.titleTxt.getContext())
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(40))
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Activities.details.class);
            intent.putExtra("object", items.get(position));
            intent.putExtra("image_url", items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }


    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt,locationTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titleTxt);
            locationTxt=itemView.findViewById(R.id.locationTxt);

            pic=itemView.findViewById(R.id.picImg);

        }
    }
}
