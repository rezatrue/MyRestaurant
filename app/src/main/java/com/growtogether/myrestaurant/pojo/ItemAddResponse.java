package com.growtogether.myrestaurant.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAddResponse {

    @SerializedName("response")
    @Expose
    public String response;
    @SerializedName("name")
    @Expose
    public String name;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
