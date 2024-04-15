package Domains;

import java.io.Serializable;

public class MunicipalityAnalysis implements Serializable {
    private String date;
    private String disease_name;
    private int count;

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public MunicipalityAnalysis(String date, int count, String disease_name) {
        this.date = date;
        this.count = count;
        this.disease_name = disease_name;
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