package com.growtogether.myrestaurant.pojo;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("response")
    public String response;
    @SerializedName("orderid")
    public int orderID;

    public OrderResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
