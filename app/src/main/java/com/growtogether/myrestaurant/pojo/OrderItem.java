package com.growtogether.myrestaurant.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("id")
    @Expose
    private int itemSerialNo;
    @SerializedName("name")
    @Expose
    private String itemName;
    @SerializedName("quantity")
    @Expose
    private int itemQuantity;
    @SerializedName("price")
    @Expose
    private double itemPrice;

    public OrderItem() {
    }

    public OrderItem(int itemSerialNo, String itemName, int itemQuantity, double itemPrice) {
        this.itemSerialNo = itemSerialNo;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }


    public int getItemSerialNo() {
        return itemSerialNo;
    }

    public void setItemSerialNo(int itemSerialNo) {
        this.itemSerialNo = itemSerialNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
