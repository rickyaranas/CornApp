package Domains;

import android.graphics.Bitmap;

import java.io.Serializable;

public class user_info_domain implements Serializable {

    private String id;
    private String fullname;
    private String user_image;
    private String email;


    public user_info_domain(String id, String fullname, String user_image, String email) {
        this.id = id;
        this.fullname = fullname;
        this.user_image = user_image;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public String getImage() {
        return user_image;
    }

    public void setImage(String user_image) {
        this.user_image = user_image;
    }
}
