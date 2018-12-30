package com.growtogether.myrestaurant.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {


    @SerializedName("restaurantid")
    @Expose
    public int restaurantSerialNo;
    @SerializedName("base64encodedImage")
    @Expose
    public Object restaurantImage;
    @SerializedName("name")
    @Expose
    public String restaurantName;
    @SerializedName("type")
    @Expose
    public String restaurantType;
    @SerializedName("address")
    @Expose
    public String restaurantAddress;
    @SerializedName("phone")
    @Expose
    public int restaurantPhone;
    @SerializedName("latitude")
    @Expose
    public double restaurantLatitude;
    @SerializedName("longitude")
    @Expose
    public double restaurantLongitude;
    @SerializedName("userid")
    @Expose
    public int userSerialNo;
    @SerializedName("created")
    @Expose
    public String restaurantCreated;



    public Restaurant() {
    }

    public int getRestaurantSerialNo() {
        return restaurantSerialNo;
    }

    public void setRestaurantSerialNo(int restaurantSerialNo) {
        this.restaurantSerialNo = restaurantSerialNo;
    }

    public String getRestaurantCreated() {
        return restaurantCreated;
    }

    public void setRestaurantCreated(String restaurantCreated) {
        this.restaurantCreated = restaurantCreated;
    }

    public Object getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(Object restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public int getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(int restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public double getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public void setRestaurantLatitude(double restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    public double getRestaurantLongitude() {
        return restaurantLongitude;
    }

    public void setRestaurantLongitude(double restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

    public int getUserSerialNo() {
        return userSerialNo;
    }

    public void setUserSerialNo(int userSerialNo) {
        this.userSerialNo = userSerialNo;
    }
}
