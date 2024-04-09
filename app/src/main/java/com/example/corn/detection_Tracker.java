package com.example.corn;

import java.util.ArrayList;

public class detection_Tracker {
    private static detection_Tracker instance;
    private ArrayList<String> PestList = new ArrayList<>();
    String pest = null;

    boolean has_changed_value = false;

    // hold the detected pest id
    // reject the id if it is already saved
    public void set_pest(String pest){
        if (!PestList.contains(pest)){
            this.pest = pest;
            PestList.add(pest); // Value added to the list
            has_changed_value = true;
        }
    }

    // reset to false after recording the Id
    public void reset_to_false(){
        has_changed_value = false;
    }

    public String get_pest(){
        return pest;
    }

    public static synchronized detection_Tracker getInstance() {
        if (instance == null) {
            instance = new detection_Tracker();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

}
