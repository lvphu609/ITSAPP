package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.Post;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/14/2015.
 */
public class PostParse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<Post> results;

    @SerializedName("validation")
    private String validation;

    @SerializedName("is_expire")
    private String is_expire;

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

    public ArrayList<Post> getResults() {
        return results;
    }

    public void setResults(ArrayList<Post> results) {
        this.results = results;
    }

    public String getIs_expire() {
        return is_expire;
    }

    public void setIs_expire(String is_expire) {
        this.is_expire = is_expire;
    }
}
