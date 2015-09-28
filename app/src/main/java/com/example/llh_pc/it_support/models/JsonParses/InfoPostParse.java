package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.Post;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/26/2015.
 */
public class InfoPostParse {

    @SerializedName("id")
    private String id;

    @SerializedName("type_id")
    private String type_id;

    @SerializedName("content")
    private String content;

    @SerializedName("created_by")
    private String created_by;

    @SerializedName("location_lat")
    private String location_lat;

    @SerializedName("location_lng")
    private String location_lng;

    @SerializedName("is_emergency")
    private String is_emergency;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("location_name")
    private String location_name;

    @SerializedName("is_delete")
    private String is_delete;

    @SerializedName("picked_by")
    private String picked_by;

    @SerializedName("picked_at")
    private String picked_at;

    @SerializedName("completed_at")
    private String completed_at;

    @SerializedName("status")
    private String status;

    @SerializedName("post_type")
    private ArrayList<Post> post_type;
}
