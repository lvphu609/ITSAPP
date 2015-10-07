package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LLH-PC on 10/7/2015.
 */
public class LuuTruParse {
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

    public LuuTruModel[] getResults() {
        return results;
    }

    public void setResults(LuuTruModel[] results) {
        this.results = results;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private LuuTruModel[] results;

    @SerializedName("validation")
    private String validation;
}
