package com.example.llh_pc.it_support.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by MinhKhanh on 6/17/2015.
 */
public class Account implements Serializable {

    public static final String ID="id";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String OLD_PASSWORD="old_password";
    public static final String NEW_PASSWORD="new_password";
    public static final String CONFIRM_PASSWORD="confirm_password";
    public static final String EMAIL="email";
    public static final String FULL_NAME="full_name";
    public static final String DATE_OF_BIRTH="date_of_birth";
    public static final String GENDER="gender";
    public static final String IDENTITY_CARD_ID="identity_card_id";
    public static final String PHONE_NUMBER="phone_number";
    public static final String BLOOD_GROUP_ID="blood_group_id";
    public static final String BLOOD_GROUP_RH_ID="blood_group_rh_id";
    public static final String AVATAR="avatar";
    public static final String ACCOUNT_TYPE="account_type";
    public static final String ADDRESS="address";
    public static final String CONTACT_NAME="contact_name";
    public static final String CONTACT_PHONE="contact_phone";
    public static final String CODE_RESET_PASSWORD="code";
    public static final String REG_ID="reg_id";

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("confirm_password")
    private String confirm_password;

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("date_of_birth")
    private String date_of_birth;

    @SerializedName("gender")
    private String gender;

    @SerializedName("identity_card_id")
    private String identity_card_id;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("email")
    private String email;

    @SerializedName("blood_group_id")
    private String blood_group_id;

    @SerializedName("blood_group_rh_id")
    private String blood_group_rh_id;

    @SerializedName("account_type")
    private String account_type;

    @SerializedName("address")
    private String address;

    @SerializedName("contact_name")
    private String contact_name;

    @SerializedName("contact_phone")
    private String contact_phone;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("location_lat")
    private String location_lat;

    @SerializedName("location_lng")
    private String location_lng;

    @SerializedName("reg_id")
    private String reg_id;

    public Account() {
        new Account("","","","","","","","","","","","","","","","");
    }

    public Account(String id, String full_name, String location_lat, String location_lng){
        this.id = id;
        this.full_name = full_name;
        this.location_lat = location_lat;
        this.location_lng = location_lng;
    }
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String password, String confirm_password, String email) {
        this.password = password;
        this.confirm_password = confirm_password;
        this.email = email;
    }

    public Account(String test) {
        this.id = null;
        this.username = "khanhvo";
        this.password = "hdafjkldasjfjasklfklsdjfjklasdf";
        this.confirm_password = "hdafjkldasjfjasklfklsdjfjklasdf";
        this.full_name = "Vo Minh Khanh";
        this.date_of_birth = "06-04-1991";
        this.gender = "1";
        this.identity_card_id = "3213131313";
        this.phone_number = "0909090909088";
        this.email = "idkhanhvo@gmail.com";
        this.blood_group_id = "1";
        this.blood_group_rh_id = "1";
        this.address = "22/8c, Trung Son";
        this.contact_name = "Nguyen Van A";
        this.contact_phone = "090090909";
        this.avatar = "";
    }

    public Account(String id, String username, String password, String confirm_password,
                   String full_name, String date_of_birth, String gender, String identity_card_id,
                   String phone_number, String email, String blood_group_id, String blood_group_rh_id,
                   String address, String contact_name, String contact_phone, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.identity_card_id = identity_card_id;
        this.phone_number = phone_number;
        this.email = email;
        this.blood_group_id = blood_group_id;
        this.blood_group_rh_id = blood_group_rh_id;
        this.address = address;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.avatar = avatar;
    }

    public Account(Account account){
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.confirm_password = account.getConfirm_password();
        this.full_name = account.getFull_name();
        this.date_of_birth = account.getDate_of_birth();
        this.gender = account.getGender();
        this.identity_card_id = account.getIdentity_card_id();
        this.phone_number = account.getPhone_number();
        this.email = account.getEmail();
        this.blood_group_id = account.getBlood_group_id();
        this.blood_group_rh_id = account.getBlood_group_rh_id();
        this.address = account.getAddress();
        this.contact_name = account.getContact_name();
        this.contact_phone = account.getContact_phone();
        this.avatar = account.getAvatar();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentity_card_id() {
        return identity_card_id;
    }

    public void setIdentity_card_id(String identity_card_id) {
        this.identity_card_id = identity_card_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlood_group_id() {
        return blood_group_id;
    }

    public void setBlood_group_id(String blood_group_id) {
        this.blood_group_id = blood_group_id;
    }

    public String getBlood_group_rh_id() {
        return blood_group_rh_id;
    }

    public void setBlood_group_rh_id(String blood_group_rh_id) {
        this.blood_group_rh_id = blood_group_rh_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
}
