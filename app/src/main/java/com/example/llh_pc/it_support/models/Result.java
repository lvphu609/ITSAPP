package com.example.llh_pc.it_support.models;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public class Result<T> {
    private ResultStatus key;
    private T value;
    private String message;

    public Result(ResultStatus key, T value) {
        this.key = key;
        this.value = value;
    }

    public Result(ResultStatus key, T value, String message) {
        this.key = key;
        this.value = value;
        this.message = message;
    }

    public ResultStatus getKey() {
        return key;
    }

    public void setKey(ResultStatus key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

