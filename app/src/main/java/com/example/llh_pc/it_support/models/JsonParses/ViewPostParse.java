package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.models.PostDetail;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/26/2015.
 */
public class ViewPostParse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<PostDetail> results;

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

    public ArrayList<PostDetail> getResults() {
        return results;
    }

    public void setResults(ArrayList<PostDetail> results) {
        this.results = results;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
