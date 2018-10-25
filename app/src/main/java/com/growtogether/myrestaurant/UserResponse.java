package com.growtogether.myrestaurant;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("response")
    public String response;
    @SerializedName("name")
    public String name;

    @SerializedName("userid")
    public int userSerialNo;

    public int getUserSerialNo() {
        return userSerialNo;
    }

    public void setUserSerialNo(int userSerialNo) {
        this.userSerialNo = userSerialNo;
    }


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
