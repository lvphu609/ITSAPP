package com.example.llh_pc.it_support.models.JsonParses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khanhvo@innoria.com on 6/27/2015.
 * Contact phonne: 0932 811 291
 */
public class LoginParse <T>{

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private LoginResult results;

    @SerializedName("validation")
    private String validation;

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

    public LoginResult getResults() {
        return results;
    }

    public void setResults(LoginResult results) {
        this.results = results;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public static class LoginResult{

        @SerializedName("access_token")
        private String access_token;

        @SerializedName("account_id")
        private String account_id;

        @SerializedName("account_type")
        private String account_type;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getAccount_id() {
            return account_id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }
    }
}

