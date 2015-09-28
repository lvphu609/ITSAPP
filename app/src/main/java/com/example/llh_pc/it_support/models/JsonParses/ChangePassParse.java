package com.example.llh_pc.it_support.models.JsonParses;

/**
 * Created by ZBOOK 15 on 9/28/2015.
 */
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChangePassParse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

//        @SerializedName("results")
//        private ArrayList<GetAccount> results;

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
    public void setDKResults (DKResults results){this.results =results;}
    public DKResults getDKresults(){return results; }
    public static class DKResults {

        @SerializedName("id")
        private String id;

        @SerializedName("password")
        private String password;

        @SerializedName("cfpassword")
        private String cfpassword;

        @SerializedName("phone_number")
        private String oldpassword;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPass() {
            return password ;
        }

        public void setPass(String password) {
            this.password = password;
        }

        public String getCfpassword() {
            return cfpassword;
        }

        public void setCfpassword(String cfpassword) {
            this.cfpassword = cfpassword;
        }

        public String getOldpassword() {
            return oldpassword;
        }

        public void setOldpassword(String oldpassword) {
            this.oldpassword = oldpassword;
        }


    }
}

