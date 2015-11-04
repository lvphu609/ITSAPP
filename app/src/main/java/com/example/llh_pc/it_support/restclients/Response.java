package com.example.llh_pc.it_support.restclients;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khanhvo@innoria.com on 6/23/2015.
 * Contact phonne: 0932 811 291
 */
public class Response  <S,T>  {
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FALSE = "failure";
    public static final String STAUS_FALSE = "false";
    public static final String STATUS_NEW = "0";
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private S results;

    @SerializedName("validation")
    private T validation;

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

    public S getResults() {
        return results;
    }

    public void setResults(S results) {
        this.results = results;
    }

    public T getValidation() {
        return validation;
    }

    public void setValidation(T validation) {
        this.validation = validation;
    }
}
