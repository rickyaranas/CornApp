package Domains;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private String title;
    private String location;
    private String description;
    private String pic;
    public PopularDomain(String title, String location, String description, int bed, boolean guide, double score, String pic, boolean wifi) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.pic = pic;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isGuide() {
        return false;
    }

    public boolean isWifi() {

        return false;
    }


}
