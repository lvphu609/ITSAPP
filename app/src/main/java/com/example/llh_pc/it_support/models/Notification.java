package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public class Notification {
    public static final String ID = "id";
    public static final String ACCOUNT_ID = "account_id";

    //paging
    public static final int ROW_PER_PAGE = 10;
    public static final int PAGE_NUMBER = 1;

    @SerializedName("id")
    private String id;

    @SerializedName("post_id")
    private String post_id;

    @SerializedName("title")
    private String title;

    @SerializedName("read_at")
    private String read_at;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("post_status")
    private String post_status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }
}
