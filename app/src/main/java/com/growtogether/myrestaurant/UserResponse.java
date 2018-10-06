package com.growtogether.myrestaurant;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("response")
    public String response;
    @SerializedName("name")
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
