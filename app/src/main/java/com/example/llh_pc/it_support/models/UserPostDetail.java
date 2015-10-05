package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LLH-PC on 9/29/2015.
 */
public class UserPostDetail {

    @SerializedName("id")
    public String id;

    @SerializedName("content")
    public String content;

    @SerializedName("created_by")
    public String created_by;

    @SerializedName("location_lat")
    public String location_lat;

    @SerializedName("location_lng")
    public String location_lng;

    @SerializedName("is_emergency")
    public String is_emergency;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("location_name")
    public String location_name;

    @SerializedName("is_delete")
    public String is_delete;

    @SerializedName("picked_by")
    public String picked_by;

    @SerializedName("picked_at")
    public String picked_at;

    @SerializedName("completed_at")
    public String completed_at;

    @SerializedName("status")
    public String status;

    @SerializedName("post_type")
    public post_type post_type;

    @SerializedName("normal_account")
    public normal_account normal_account;

    @SerializedName("provider_account")
    public String provider_account;
}
