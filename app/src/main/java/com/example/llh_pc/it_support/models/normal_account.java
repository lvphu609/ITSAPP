package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/29/2015.
 */
public class Normal_account {
    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;

    @SerializedName("full_name")
    public String full_name;

    @SerializedName("phone_number")
    public String phone_number;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("address")
    public String address;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("location_lat")
    public String location_lat;

    @SerializedName("location_lng")
    public String location_lng;

    @SerializedName("reg_id")
    public String reg_id;

    @SerializedName("account_type")
    public ArrayList<String> account_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public ArrayList<String> getAccount_type() {
        return account_type;
    }

    public void setAccount_type(ArrayList<String> account_type) {
        this.account_type = account_type;
    }
}
