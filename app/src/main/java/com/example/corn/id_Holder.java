package com.example.corn;

public class id_Holder {
    private static id_Holder instance;
    int id = 0;


    public void hold_id(int id){
        this.id = id;
    }

    public boolean has_value(){
        if(id == 0){
            return false;
        }else{
            return true;
        }

    }
    public int retrieve_id(){
        return id;
    }


    public static synchronized id_Holder getInstance() {
        if (instance == null) {
            instance = new id_Holder();
        }
        return instance;
    }
}
