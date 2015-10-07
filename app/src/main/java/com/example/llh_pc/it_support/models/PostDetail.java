package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LLH-PC on 9/28/2015.
 */
public class PostDetail {

    @SerializedName("post_type_id")
    private String post_type_id;

    @SerializedName("location_name")
    private String location_name;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("avatar")
    private String avatar;

    private PostType post_type;

    public PostType getPost_type() {
        return post_type;
    }

    public void setPost_type(PostType post_type) {
        this.post_type = post_type;
    }

    public String getPost_type_id() {
        return post_type_id;
    }

    public void setPost_type_id(String post_type_id) {
        this.post_type_id = post_type_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
