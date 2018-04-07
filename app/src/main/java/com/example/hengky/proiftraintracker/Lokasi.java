package com.example.hengky.proiftraintracker;

/**
 * Created by adrian stefanus on 07/04/2018.
 */

public class Lokasi {

    String latitude;
    String longitude;
    public Lokasi(String latitude,String longitude){

        this.latitude=latitude;
        this.longitude=longitude;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public String getLongitude(){
        return this.longitude;
    }


    public void setLatitude(String latitude){
        this.latitude=latitude;
    }
    public void setLongitude(String Longitude){
        this.longitude=Longitude;
    }
}
