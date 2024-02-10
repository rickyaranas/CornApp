package Domains;

import java.io.Serializable;

public class CategoryDomain implements Serializable {

    private String title;
    private String picImgl;
    private String description;
    private String location;

    public CategoryDomain(String title, String picImg, String description, String location) {
        this.title = title;
        this.picImgl = picImg;
        this. description = description;
        this. location = location;
    }

    public String getTitle()  {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicPath() {
        return picImgl;
    }

    public void setPicPath(String picPath) {
        this.picImgl = picPath;
    }


    public String getPic() { return picImgl;
    }

    public String getDescription() {   return description;
    }

    public String getLocation() {
        return location;
    }
}
