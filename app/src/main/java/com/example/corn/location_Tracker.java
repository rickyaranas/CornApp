package com.example.corn;

public class location_Tracker {
    private static location_Tracker instance;
    String town;
    String Province;
    String Country;
    String Addressline;
    boolean has_changed_value = false;


    public void set_location(String addressline, String town, String province, String country){
    this.town = town;
    this.Province = province;
    this.Country = country;
    this.Addressline = addressline;
    }

    public String getTown(){
        return town;
    }
    public String getProvince(){
        return Province;
    }
    public String getCountry(){
        return Country;
    }
    public String getAddressline(){
        return Addressline;
    }
    public static synchronized location_Tracker getInstance() {
        if (instance == null) {
            instance = new location_Tracker();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

}
