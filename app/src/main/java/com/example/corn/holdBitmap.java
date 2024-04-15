package com.example.corn;

import android.graphics.Bitmap;

public class holdBitmap {
    private static holdBitmap instance;
    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public static synchronized holdBitmap getInstance() {
        if (instance == null) {
            instance = new holdBitmap();
        }
        return instance;
    }
}
