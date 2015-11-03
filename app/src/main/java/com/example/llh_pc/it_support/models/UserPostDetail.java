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
    public PostType post_type;

    @SerializedName("normal_account")
    public Normal_account normal_account;

    @SerializedName("provider_account")
    public Provider_Account provider_account;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }

    public String getIs_emergency() {
        return is_emergency;
    }

    public void setIs_emergency(String is_emergency) {
        this.is_emergency = is_emergency;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getPicked_by() {
        return picked_by;
    }

    public void setPicked_by(String picked_by) {
        this.picked_by = picked_by;
    }

    public String getPicked_at() {
        return picked_at;
    }

    public void setPicked_at(String picked_at) {
        this.picked_at = picked_at;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Normal_account getNormal_account() {
        return normal_account;
    }

    public void setNormal_account(Normal_account normal_account) {
        this.normal_account = normal_account;
    }

    public Provider_Account getProvider_account() {
        return provider_account;
    }

    public void setProvider_account(Provider_Account provider_account) {
        this.provider_account = provider_account;
    }

    public PostType getPost_type() {
        return post_type;
    }

    public void setPost_type(PostType post_type) {
        this.post_type = post_type;
    }
}
