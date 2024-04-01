package Adapters;

import android.content.Intent;
import android.media.Image;
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
import com.bumptech.glide.request.target.Target;
import com.example.corn.R;

import java.util.ArrayList;

import Domains.CategoryDomain;

public class category_Adapters extends RecyclerView.Adapter<category_Adapters.ViewHolders> {

    ArrayList<CategoryDomain> items;

    public category_Adapters(ArrayList<CategoryDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public category_Adapters.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent, false);
        return new ViewHolders(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull category_Adapters.ViewHolders holder, int position) {
    holder.titleTxt.setText(items.get(position).getMunicipality());


//    int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicPath()
//            , "drawable", holder.itemView.getContext().getPackageName());
//
//    Glide.with(holder.itemView.getContext())
//            .load(drawableResourceId)
//            .override(Target.SIZE_ORIGINAL)
//            .into(holder.picImgl);
//    holder.itemView.setOnClickListener(v -> {
//            Intent intent=new Intent(holder.itemView.getContext(), Activities.localfarms.class);
//            intent.putExtra("object", items.get(position));
//            holder.itemView.getContext().startActivity(intent);
        String imageUrl = "http://192.168.100.5/LoginRegister/images/local_farms/" + items.get(position).getLogo();
        Glide.with(holder.itemView.getContext())
            .load(imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(holder.picImgl);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Activities.localfarms.class);
            intent.putExtra("category_object", items.get(position));
            intent.putExtra("category_image_url", items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView picImgl;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titletxt);
            picImgl=itemView.findViewById(R.id.catImg);

        }
    }
}
