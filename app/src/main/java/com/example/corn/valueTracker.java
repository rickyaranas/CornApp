package com.example.corn;

public class valueTracker {
    private String id;
    private String name;
    private float confidence;

    private boolean hasChanged = false;


//Get Value
    public String get_idValue() {
        return id;
    }
    public String get_nameValue() {
        return name;
    }
    public float get_confidenceValue() {
        return confidence;
    }

    public void set_id(String new_idValue) {
        if (this.id != new_idValue) {
            this.hasChanged = true;
        }
        this.id = new_idValue;
    }



//Set Value
    public void set_name(String new_nameValue) {
        if (this.name != new_nameValue) {
            this.hasChanged = true;
        }
        this.name = new_nameValue;
    }
    public void set_confidence(float new_confidenceValue) {
        if (this.confidence != new_confidenceValue) {
            this.hasChanged = true;
        }
        this.confidence = new_confidenceValue;
    }

//Tracks the Value Change
    public boolean hasValueChanged() {
        return hasChanged;
    }
}
