package Domains;

import java.io.Serializable;

public class disease_images_domain implements Serializable {
    private String id;

    private String image_name;

    public disease_images_domain(String id, String image_name) {
        this.id = id;

        this.image_name = image_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image_name;
    }

    public void setImage(String image) {
        this.image_name = image;
    }
}
