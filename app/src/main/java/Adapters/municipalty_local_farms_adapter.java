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

import Domains.MunicipalityAnalysis;

public class municipalty_local_farms_adapter extends RecyclerView.Adapter<municipalty_local_farms_adapter.ViewHolders> {
    ArrayList<MunicipalityAnalysis> items;
    base_url url = base_url.getInstance();
    public municipalty_local_farms_adapter(ArrayList<MunicipalityAnalysis> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public municipalty_local_farms_adapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_farms_municipakity,parent, false);
        return new ViewHolders(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull municipalty_local_farms_adapter.ViewHolders holder, int position) {
        holder.address.setText(items.get(position).getDisease_name());
        holder.municipality.setText(String.valueOf(items.get(position).getCount()));

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
            municipality=itemView.findViewById(R.id.count);
            //imageBusttons3=itemView.findViewById(R.id.imageBusttons3);
        }
    }
}
