package com.example.hengky.proiftraintracker;

/**
 * Created by adrian stefanus on 01/04/2018.
 */

public class Lokasi {
    public String longitude;
    public String latitude;
    public Lokasi(String latitude,String longitude){
        this.longitude=longitude;
        this.latitude=latitude;
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
    public void setLongitude(String longitude){
        this.longitude=longitude;
    }
}
