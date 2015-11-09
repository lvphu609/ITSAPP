package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LLH-PC on 11/3/2015.
 */
public class NotificationDetail {

    @SerializedName("provider_account")
    private Provider_Account provider_account;

    @SerializedName("type_id")
    private String type_id;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("created_by")
    private String created_by;

    @SerializedName("content")
    private String content;

    @SerializedName("is_emergency")
    private String is_emergency;

    @SerializedName("notify")
    private Notify notify;

    @SerializedName("is_delete")
    private String is_delete;

    @SerializedName("location_lng")
    private String location_lng;

    @SerializedName("processing")
    private String processing;

    @SerializedName("completed_at")
    private String completed_at;

    @SerializedName("location_name")
    private String location_name;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("post_type")
    private Post_Type post_type;

    @SerializedName("id")
    private String id;

    @SerializedName("picked_at")
    private String picked_at;

    @SerializedName("normal_account")
    private Normal_account normal_account;

    @SerializedName("location_lat")
    private String location_lat;

    @SerializedName("picked_by")
    private String picked_by;

    @SerializedName("status")
    private String status;

    public Provider_Account getProvider_account() {
        return provider_account;
    }

    public void setProvider_account(Provider_Account provider_account) {
        this.provider_account = provider_account;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_emergency() {
        return is_emergency;
    }

    public void setIs_emergency(String is_emergency) {
        this.is_emergency = is_emergency;
    }

    public Notify getNotify() {
        return notify;
    }

    public void setNotify(Notify notify) {
        this.notify = notify;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Post_Type getPost_type() {
        return post_type;
    }

    public void setPost_type(Post_Type post_type) {
        this.post_type = post_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicked_at() {
        return picked_at;
    }

    public void setPicked_at(String picked_at) {
        this.picked_at = picked_at;
    }

    public Normal_account getNormal_account() {
        return normal_account;
    }

    public void setNormal_account(Normal_account normal_account) {
        this.normal_account = normal_account;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getPicked_by() {
        return picked_by;
    }

    public void setPicked_by(String picked_by) {
        this.picked_by = picked_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

}
