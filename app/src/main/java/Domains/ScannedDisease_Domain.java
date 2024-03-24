package Domains;

import java.io.Serializable;

public class ScannedDisease_Domain implements Serializable {

    private String id;
    private String disease_name;
    private String location;
    private String description;
    private String treatment;
    private String image;

    public ScannedDisease_Domain(String id, String disease_name, String location, String description, String treatment, String image) {
        this.id = id;
        this.disease_name = disease_name;
        this.location = location;
        this.description = description;
        this.treatment = treatment;
        this.image = image;
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

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
