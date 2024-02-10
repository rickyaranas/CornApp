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
    holder.titleTxt.setText(items.get(position).getTitle());


    int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicPath()
            , "drawable", holder.itemView.getContext().getPackageName());

    Glide.with(holder.itemView.getContext())
            .load(drawableResourceId)
            .into(holder.picImgl);
    holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), Activities.localfarms.class);
            intent.putExtra("object", items.get(position));
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
