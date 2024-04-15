package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corn.R;
import com.example.corn.base_url;

import java.util.ArrayList;

import Domains.local_farms_domain;

public class local_farms_adapter  extends RecyclerView.Adapter<local_farms_adapter.ViewHolders> {
    ArrayList<local_farms_domain> items;
    base_url url = base_url.getInstance();
    public local_farms_adapter(ArrayList<local_farms_domain> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public local_farms_adapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_farms_category,parent, false);
        return new ViewHolders(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull local_farms_adapter.ViewHolders holder, int position) {
        holder.address.setText(items.get(position).getAddress());
        holder.municipality.setText(items.get(position).getLocation_name());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView address, municipality;
        ImageView imageBusttons3;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.M_disease);
            municipality=itemView.findViewById(R.id.municipality);
            imageBusttons3=itemView.findViewById(R.id.imageBusttons3);
        }
    }
}
