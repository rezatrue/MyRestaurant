package com.growtogether.myrestaurant.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {
    @SerializedName("orderid")
    @Expose
    private int orderID;
    @SerializedName("restaurantid")
    @Expose
    private int restaurantSerialNo;
    @SerializedName("userid")
    @Expose
    private int userSerialNo;
    @SerializedName("items")
    @Expose
    private ArrayList<OrderItem> orderItems;
    @SerializedName("date")  // need to set this when order is sent to server
    @Expose
    private String orderDate;
    @SerializedName("status") //  when this is created it must set to ZERO status
    @Expose
    private int orderStatus;
    @SerializedName("phone")
    @Expose
    private int deliverPhone;
    @SerializedName("address")
    @Expose
    private String deliverAddress;
    @SerializedName("latitude")
    @Expose
    private double deliverLat;
    @SerializedName("longitude")
    @Expose
    private double deliverLng;

    public Order() {
        orderItems = new ArrayList<>();
    }


    public Order(int orderID, int restaurantSerialNo, int userSerialNo, ArrayList<OrderItem> orderItems, String orderDate,int orderStatus, int deliverPhone, String deliverAddress, double deliverLat, double deliverLng) {
        this.orderItems = new ArrayList<>();

        this.orderID = orderID;
        this.restaurantSerialNo = restaurantSerialNo;
        this.userSerialNo = userSerialNo;
        this.orderItems.addAll(orderItems);
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliverPhone = deliverPhone;
        this.deliverAddress = deliverAddress;
        this.deliverLat = deliverLat;
        this.deliverLng = deliverLng;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getRestaurantSerialNo() {
        return restaurantSerialNo;
    }

    public void setRestaurantSerialNo(int restaurantSerialNo) {
        this.restaurantSerialNo = restaurantSerialNo;
    }

    public int getUserSerialNo() {
        return userSerialNo;
    }

    public void setUserSerialNo(int userSerialNo) {
        this.userSerialNo = userSerialNo;
    }


    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(int deliverPhone) {
        this.deliverPhone = deliverPhone;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public double getDeliverLat() {
        return deliverLat;
    }

    public void setDeliverLat(double deliverLat) {
        this.deliverLat = deliverLat;
    }

    public double getDeliverLng() {
        return deliverLng;
    }

    public void setDeliverLng(double deliverLng) {
        this.deliverLng = deliverLng;
    }
}
