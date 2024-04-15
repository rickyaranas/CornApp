package com.example.corn;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class Analysis extends AppCompatActivity {

    RecyclerView recyclerView;
//    Ranking_Adapter adapter;
//    Ranking_Clicklistener clickListener;
    LineChart linechart;
    private List<String>xValues;
    private List<Entry>Yentries;
    TextView most_detected_pest, total_pest, recommendation;
//    DatabaseHandler db;
    String pest;
    String intervention;
    int count;

    String str_id;
    RelativeLayout errormsg;
    id_Holder id = id_Holder.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        linechart = findViewById(R.id.lineChart);
        most_detected_pest = findViewById(R.id.most_detected);
        total_pest = findViewById(R.id.total_pest);
        recommendation = findViewById(R.id.recommendation);
        errormsg = findViewById(R.id.errormsg);

//        db = new DatabaseHandler(Analysis.this);
        str_id = String.valueOf(id.retrieve_id());


        // Line Chart
        Description description = new Description();
        description.setText("");
        description.setPosition(300f,15f);
        description.setTextSize(15f);
//
        linechart.setDescription(description);
        linechart.getAxisRight().setDrawLabels(false);

//        xValues = Arrays.asList("08-Apr","09-Apr","10-Apr","11-Apr");
        xValues = new ArrayList<>();
        xValues.add(new String("0"));
        xValues.add(new String("2024-04-11"));
        xValues.add(new String("2024-04-12"));

//        xValues = getxValues();



        int dateCount = xValues.size() +1;

//        xValues.add(new String("08-Apr"));
//        xValues.add(new String("09-Apr"));
//        xValues.add(new String("10-Apr"));
//        xValues.add(new String("12-Apr"));

        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(dateCount);
        xAxis.setGranularity(1f);


        YAxis yAxis = linechart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        Yentries = new ArrayList<>();
        Yentries.add(new Entry(0,0f));
        Yentries.add(new Entry(0,4f));
        Yentries.add(new Entry(0,1f));
//        Yentries = getYentries();
//        entries1.add(new Entry(1,3f));


//        List<Entry> entries2 = new ArrayList<>();
//        entries2.add(new Entry(0,0f));
//        entries2.add(new Entry(1,8f));


//        LineDataSet dataSet1 = new LineDataSet(entries1,"Stemborer");
//        dataSet1.setColor(Color.GREEN);

        LineDataSet dataSet = new LineDataSet(Yentries,"Total Detection");
        dataSet.setColor(Color.BLUE);

        LineData lineData = new LineData(dataSet);
        linechart.setData(lineData);
        linechart.invalidate();




//        if(db.check_DataAvailable(String.valueOf(id.retrieve_id()))){
//
//            pest = db.get_MostDetectedPest(String.valueOf(id.retrieve_id()));
//            count = db.get_TotalPestCount(String.valueOf(id.retrieve_id()));
//            most_detected_pest.setText(pest);
//            total_pest.setText(""+count);
//            intervention = db.get_Recommendation(pest);
//            recommendation.setText(intervention);
//        }


//        //Recyclers View
//        List<Ranking_Data> list = new ArrayList<>();
//        list = getData();
//
//        recyclerView = findViewById(R.id.ranking_recyclerview);
//        clickListener = new Ranking_Clicklistener() {
//            @Override
//            public void click(int index){
//                Toast.makeText(Analysis.this,"clicked item index is "+index, Toast.LENGTH_SHORT).show();
//            }
//        };
//        adapter = new Ranking_Adapter(list, this.getApplication(),clickListener);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(this));
//
//        if(list.isEmpty()){
//            errormsg.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private List<String> getxValues(){
//        List<String> list = new ArrayList<>();
//        list.add(new String("0"));

//        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT Date FROM Detected_Pest WHERE User_Id = ? GROUP BY Date ORDER BY Date ASC",new String[]{str_id});
//        if(cursor.moveToFirst()){
//            do {
//                String date = cursor.getString(0);
//
//                list.add(new String(date));
//            }while(cursor.moveToNext());
//        }
//
//        cursor.close();


//        return list;
//    }
//
//    private List<Entry> getYentries(){
//        List<Entry> list = new ArrayList<>();
//        list.add(new Entry(0,0f));
//        int i = 0;
//        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT COUNT(Date) FROM Detected_Pest WHERE User_Id = ? GROUP BY Date ORDER BY Date ASC",new String[]{str_id});
//        if(cursor.moveToFirst()){
//            do {
//                float detection_bydate_Count = cursor.getFloat(0);
//                i+=1;
//                list.add(new Entry(i,detection_bydate_Count));
//            }while(cursor.moveToNext());
//        }
//
//        cursor.close();
//
//
//        return list;
//    }
//
//    private List<Ranking_Data> getData()
//    {
////        int current_id = Integer.parseInt(Id);
////        current_id += 1;
////        System.out.println("CURRENT ID: "+Id);
//        List<Ranking_Data> list = new ArrayList<>();
////        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM Detected_Pest WHERE User_Id = ? GROUP BY Pest_Name ORDER BY COUNT(Pest_Name) DESC LIMIT 1", new String[]{String.valueOf(id.retrieve_id())});
//
//        Cursor cursor = db.getReadableDatabase().rawQuery(
//                "SELECT Pest_Name, COUNT(Pest_Name) AS Pest_Count " +
//                        "FROM Detected_Pest " +
//                        "WHERE User_Id = ? " +
//                        "GROUP BY Pest_Name " +
//                        "ORDER BY Pest_Count DESC ",
//                new String[]{str_id}
//        );
//
//        if(cursor.moveToFirst()){
//            do {
////                String name = getString(cursor.getColumnIndex("Pest_Name"));
////                int frequency = cursor.getInt(cursor.getColumnIndexOrThrow("Pest_Count"));
//                String name = cursor.getString(0);
//                int frequency = cursor.getInt(1);
//
//                // adding all the data to the list array
//                list.add(new Ranking_Data(name,frequency));
//            }while(cursor.moveToNext());
//        }
//
//        cursor.close();
//        return list;
   }



}