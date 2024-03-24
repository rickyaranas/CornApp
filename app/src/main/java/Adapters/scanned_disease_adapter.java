package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corn.R;

import java.util.ArrayList;

import Domains.PopularDomain;
import Domains.ScannedDisease_Domain;

public class scanned_disease_adapter extends RecyclerView.Adapter<scanned_disease_adapter.ViewHolder> {

    ArrayList<ScannedDisease_Domain> items;

    public scanned_disease_adapter(ArrayList<ScannedDisease_Domain> items) {
        this.items = items;
//        formatter = new DecimalFormat("#,###.##");
    }


    @NonNull
    @Override
    public scanned_disease_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_popular,parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull scanned_disease_adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
