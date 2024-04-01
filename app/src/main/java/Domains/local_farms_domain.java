package Domains;

import java.io.Serializable;

public class local_farms_domain implements Serializable {

    private String location_id;
    private String location_name;
    private String address;
    private String google_map;

    public local_farms_domain(String location_id, String location_name, String address, String google_map) {
        this.location_id = location_id;
        this.location_name = location_name;
        this.address = address;
        this.google_map = google_map;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogle_map() {
        return google_map;
    }

    public void setGoogle_map(String google_map) {
        this.google_map = google_map;
    }
}
