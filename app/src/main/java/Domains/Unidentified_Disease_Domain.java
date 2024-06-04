package Domains;

import java.io.Serializable;

public class Unidentified_Disease_Domain implements Serializable {

    private String id;

    private String location;
    private String date;
    private String image;

    public Unidentified_Disease_Domain(String id, String disease_name, String location, String description, String treatment, String image) {
        this.id = id;
        ;
        this.location = location;

        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
