package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.IDOther;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 10/28/2015.
 */
public class OtherParse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private IDOther results;

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

    public IDOther getResults() {
        return results;
    }

    public void setResults(IDOther results) {
        this.results = results;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
