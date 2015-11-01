package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZBOOK 15 on 10/30/2015.
 */
public class ModelLatLng {

    @SerializedName("location_lat")
    public Double location_lat;

    @SerializedName("location_lng")
    public Double location_lng;



    public Double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat (Double location_lat){
        this.location_lat = location_lat;
    }

    public Double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng (Double location_lng){
        this.location_lng = location_lng;
    }
}
