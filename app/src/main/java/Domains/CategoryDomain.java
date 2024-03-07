package Domains;

import java.io.Serializable;

public class CategoryDomain implements Serializable {

    private String id;
    private String Municipality;
    private String logo;
    private String description;
    private String location;

    public CategoryDomain(String id,String Municipality, String logo, String description, String location) {
        this.id = id;
        this.Municipality = Municipality;
        this.logo = logo;
        this. description = description;
        this. location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMunicipality() {
        return Municipality;
    }

    public void setMunicipality(String municipality) {
        this.Municipality = municipality;
    }

    public String getPicPath() {
        return logo;
    }

    public void setPicPath(String picPath) {
        this.logo = picPath;
    }


    public String getLogo() { return logo;
    }

    public String getDescription() {   return description;
    }

    public String getLocation() {
        return location;
    }
}
