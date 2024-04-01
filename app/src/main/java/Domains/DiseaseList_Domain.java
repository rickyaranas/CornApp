package Domains;

import java.io.Serializable;

public class DiseaseList_Domain implements Serializable {
    private String id;
    private String disease_name;
    private String date;
    private String severity;
    private String location;
    private String description;
    private String image_name;

    public DiseaseList_Domain(String id, String disease_name, String date, String severity, String location, String description, String image_name) {
        this.id = id;
        this.disease_name = disease_name;
        this.date = date;
        this.severity = severity;
        this.location = location;
        this.description = description;
        this.image_name = image_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image_name;
    }

    public void setImage(String image) {
        this.image_name = image;
    }
}
