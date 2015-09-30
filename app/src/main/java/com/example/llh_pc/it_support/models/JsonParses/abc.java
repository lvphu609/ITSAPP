package com.example.llh_pc.it_support.models.JsonParses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LLH-PC on 9/29/2015.
 */
public class abc {

    @SerializedName("id")
    private String id;

    @SerializedName("type_id")
    private String type_id;

    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
