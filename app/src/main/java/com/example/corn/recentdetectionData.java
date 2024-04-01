package com.example.corn;

import android.graphics.Bitmap;

public class recentdetectionData {

    String name;
    String confidence;
    Bitmap Image;
    String time;
    String date;

    recentdetectionData(String name, String confidence, Bitmap Image, String time, String date){

        this.name = name;
        this.confidence = confidence;
        this.Image = Image;
        this.time = time;
        this.date = date;


    }
}
