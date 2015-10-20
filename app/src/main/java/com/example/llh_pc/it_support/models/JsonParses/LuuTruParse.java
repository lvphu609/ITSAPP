package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 10/7/2015.
 */
public class LuuTruParse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<LuuTruModel> results;

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

    public ArrayList<LuuTruModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<LuuTruModel> results) {
        this.results = results;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }


}
