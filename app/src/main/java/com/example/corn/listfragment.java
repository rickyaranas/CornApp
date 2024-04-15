package com.example.corn;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Adapters.DiseaseList_Adapter;
import Domains.AnalysisData;
import Domains.DiseaseList_Domain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listfragment extends Fragment {

//    String [] disease = {"Corn Rust","Leaf Blight","Leaf Spot"};
//    int [] images = {R.drawable.common_rust, R.drawable.leaf_blight, R.drawable.leaf_spot};
//
//    ListView customlistView;
    private RecyclerView list_recycler;
    private RecyclerView.Adapter DiseaseList_Adapter;
    LineChart linechart;
    private List<String> xValues;
    private List<Entry>Yentries;
    id_Holder id = id_Holder.getInstance();

    base_url url = base_url.getInstance();
    int count;

    String user_id;
    XAxis xAxis;
    int highestCount=0;

    @SuppressLint("MissingInflatedId")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
                list_recycler = view.findViewById(R.id.list_recycler);
                linechart = view.findViewById(R.id.lineChart);
                list_recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        Description description = new Description();
        description.setText("");
        description.setPosition(250f,15f);
        description.setTextSize(15f);

        linechart.setDescription(description);
        linechart.getAxisRight().setDrawLabels(false);
        // Initialize the chart with empty data
       updateChart(new ArrayList<>(), new ArrayList<>());

       getxValues();

        processdata();

        return view;
    }
    private void getxValues() {
        String user_id = String.valueOf(id.retrieve_id());
        String apiUrl = url.getBase_url() + "LoginRegister/fetch_for_analysis.php?user_id=" + user_id;
        Log.d("Generated URL farms", apiUrl); // Print the generated URL in Logcat

        apiset apiService = apiController.getInstance().getapi();
        Call<ArrayList<AnalysisData>> call = apiService.getDiseaseAnalysis(user_id);
        call.enqueue(new Callback<ArrayList<AnalysisData>>() {
            @Override
            public void onResponse(Call<ArrayList<AnalysisData>> call, Response<ArrayList<AnalysisData>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AnalysisData> analysisData = response.body();
                    if (analysisData != null ) {
                        xValues = new ArrayList<>();
                        Yentries = new ArrayList<>();
                        for (AnalysisData item : analysisData) {
                            Log.d("AnalysisData", "Date: " + item.getDate() + ", Count: " + item.getCount());
                            String date = item.getDate();
                            Log.d("xValues", date);
                            count = item.getCount(); // Assuming count is stored in AnalysisData
                            xValues.add(date);
                            if(count>highestCount){
                                highestCount=count;
                            }
                            //int dateCount = xValues.size() +1;

                            Yentries.add(new Entry(Yentries.size(), count)); // Add entry to match the index of the date
                        }
//                        xValues.add(new String("0"));
//                        xValues.add(new String("2024-04-11"));
//                        xValues.add(new String("2024-04-12"));
//                        Yentries.add(new Entry(0,0f));
//                        Yentries.add(new Entry(1,4f));
//                        Yentries.add(new Entry(2,1f));
                        Log.d("xValues", xValues.toString());
                        Log.d("Yentries", Yentries.toString());
                        Log.d("AnalysisData", "The list is either null or empty");
                        // After populating xValues and Yentries, update the chart
                        String description = "Analysis Data\nTotal Count: " + count;
                        updateChart((ArrayList<String>) xValues, (ArrayList<Entry>) Yentries);
                    } else {
                        // Handle empty response body
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnalysisData>> call, Throwable t) {
                // Handle failure
            }
        });
    }


    private void updateChart(ArrayList<String> dates, ArrayList<Entry> values) {
        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setGranularity(.5f); // Set the granularity to 1 to display all labels
        xAxis.setLabelRotationAngle(-45f); // Rotate the

        // Update Y axis values
        //Yentries.add(new Entry(0,0f));
        YAxis yAxis = linechart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum((float)highestCount +1f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(highestCount+1);
        yAxis.setGranularity(highestCount>12?3f:1f);

        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        // Check if values list is empty
        if (!values.isEmpty()) {
            float maxValue = Collections.max(values, new EntryComparator()).getY();
            yAxis.setAxisMaximum(maxValue);
        } else {
            // Handle empty values list
            yAxis.setAxisMaximum(0f); // Set a default maximum value
        }

        // Update chart with new data
        LineDataSet dataSet = new LineDataSet(values, "Analysis Data");
        LineData lineData = new LineData(dataSet);
        linechart.setData(lineData);
        linechart.invalidate(); // Refresh chart
    }


    // Comparator class to compare Entry objects
    class EntryComparator implements Comparator<Entry> {
        @Override
        public int compare(Entry e1, Entry e2) {
            return Float.compare(e1.getY(), e2.getY());
        }
    }
    private void processdata() {
        Call<ArrayList<DiseaseList_Domain>> call = apiController
                .getInstance()
                .getapi()
                .getDiseaseData();

        call.enqueue(new Callback<ArrayList<DiseaseList_Domain>>() {
            @Override
            public void onResponse(Call<ArrayList<DiseaseList_Domain>> call, Response<ArrayList<DiseaseList_Domain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DiseaseList_Domain> items = response.body();
                    if (items != null) {
                        DiseaseList_Adapter = new DiseaseList_Adapter(items);
                        list_recycler.setAdapter(DiseaseList_Adapter);
                        Log.d("AdapterDebug", "Adapter is set successfully");
                    } else {
                        // Handle null response body
                    }
                } else {
                    Log.d("AdapterDebug", "Response unsuccessful");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<DiseaseList_Domain>> call, Throwable t) {

            }
        });
    }


}