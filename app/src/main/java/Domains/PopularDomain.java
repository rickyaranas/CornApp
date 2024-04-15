package Domains;

import java.io.Serializable;

public class PopularDomain implements Serializable {

    private String id;
    private String disease_name;
    private String location;
    private String description;
    private String treatment;
    private String image;
    private String image_links;


    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public PopularDomain(String id, String disease_name, String location, String description, String treatment, String image_links) {
        this.id = id;
        this.disease_name = disease_name;
        this.location = location;
        this.description = description;
        this.image = image;
        this.image_links = image_links;
        this.treatment = treatment;


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

    public void setTitle(String title) {
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

    public String getPic() {
        return image;
    }

    public void setPic(String pic) {
        this.image = image;
    }

    public String getImage_links() {
        return image_links;
    }

    public void setImage_links(String image_links) {
        this.image_links = image_links;
    }


}
