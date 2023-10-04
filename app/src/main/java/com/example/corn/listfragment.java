package com.example.corn;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class listfragment extends Fragment {

    String [] disease = {"disease1","disease2"};
    int [] images = {R.drawable.ic_baseline_account_circle_24, R.drawable.ic_baseline_home_24};

    ListView customlistView;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listfragment, container, false);

        customlistView = view.findViewById(R.id.customlistView);

        CustomListAdapter adapter = new CustomListAdapter(getContext(), images, disease);
        customlistView.setAdapter(adapter);

        return view;
//        return inflater.inflate(R.layout.fragment_listfragment, container, false);


    }


    class CustomListAdapter extends ArrayAdapter {
      int [] image;
      String [] disease;
        public CustomListAdapter(Context context, int []image, String[] disease) {
            super(context, R.layout.list_item, R.id.text, listfragment.this.disease);
            this.image = image;
            this.disease = disease;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item, parent, false);

            TextView textView = rowView.findViewById(R.id.text);
            ImageView imageView = rowView.findViewById(R.id.imageView);

            imageView.setImageResource(image[position]);
            textView.setText(disease[position]);

            return rowView;
        }
    }

}