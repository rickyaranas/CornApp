package Domains;

import java.io.Serializable;

public class AnalysisData implements Serializable {
    private String date;
    private int count;

    public AnalysisData(String date, int count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}