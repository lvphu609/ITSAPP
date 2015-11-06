package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.ListNotificationModel;
import com.example.llh_pc.it_support.models.NotificationDetail;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LLH-PC on 11/4/2015.
 */
public class ListNotificationParse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ListNotificationModel[] results;

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

    public ListNotificationModel[] getResults() {
        return results;
    }

    public void setResults(ListNotificationModel[] results) {
        this.results = results;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
