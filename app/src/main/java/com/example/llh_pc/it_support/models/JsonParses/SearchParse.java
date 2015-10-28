package com.example.llh_pc.it_support.models.JsonParses;

import com.example.llh_pc.it_support.models.LuuTruModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ZBOOK 15 on 10/12/2015.
 */
public class SearchParse {
    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private LuuTruModel[] results;

    @SerializedName("message")
    private String message;

//    @SerializedName("results")
//    private ArrayList<Post> results;

    @SerializedName("validation")
    private String validation;

    @SerializedName("location_lat")
    private String location_lat;

    @SerializedName("location_lng")
    private String location_lng;

    @SerializedName("query")
    private String query;

    @SerializedName("page")
    private int page;

    @SerializedName("row_per_page")
    private int row_per_page;

    public void setRow_per_page(int row_per_page)
    {this.row_per_page = row_per_page;}
    public int getRow_per_page(){
        return row_per_page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getPage(){
        return page;
    }
    public void setQuery(String query){
        this.query=query;
    }
    public String getQuery(){
        return query;
    }

    public String getLocation_lat(){
        return location_lat;
    }
    public void setLocation_lat(String location_lat){
        this.location_lat = location_lat;
    }

    public String getLocation_lng()
    {return location_lng;}
    public void setLocation_lng(String location_lng)
    {this.location_lng = location_lng;}

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


}
