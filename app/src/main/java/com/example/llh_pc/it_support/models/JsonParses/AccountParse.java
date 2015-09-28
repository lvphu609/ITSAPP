package com.example.llh_pc.it_support.models.JsonParses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ZBOOK 15 on 9/22/2015.
 */
public class AccountParse {

        @SerializedName("status")
        private String status;

        @SerializedName("message")
        private String message;

        @SerializedName("results")
        private DKResults results;

        @SerializedName("validation")
        private String validation;

        public String getValidation() {
            return validation;
        }

        public void setValidation(String validation) {
            this.validation = validation;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

//        public ArrayList<GetAccount> getResults() {
//            return results;
//        }
//
//        public void setResults(ArrayList<GetAccount> results) {
//            this.results = results;
//        }

    public void setDKResults (DKResults results){this.results =results;}
    public DKResults getDKresults(){return results; }
    public static class DKResults{

        @SerializedName("id")
        private String id;

        @SerializedName("email")
        private String email;

        @SerializedName("full_name")
        private String full_name;

        @SerializedName("phone_number")
        private String phone_number;

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("address")
        private String address;

        @SerializedName("account_type")
        private ArrayList<String> account_type;

        public String getId() {return id;}
        public void setId(String id) {this.id=id;}
        public String getEmail() {return email;}
        public void setEmail(String email) {this.email = email;}
        public String getFull_name() {return full_name;}
        public void setFull_name(String full_name) {this.full_name=full_name;}
        public String getPhone_number() {return phone_number;}
        public void setPhone_number(String phone_number) {this.phone_number =phone_number;}
        public String getAvatar () {return avatar;}
        public void setAvatar(String avatar) {this.avatar=avatar;}
        public String getAddress() {return address;}
        public void setAddress(String address) {
            this.address = address;
        }
        public ArrayList<String> getAccount_type(){return account_type;}
        public void setAccount_type(ArrayList<String> account_type){this.account_type = account_type;}
    }

}
