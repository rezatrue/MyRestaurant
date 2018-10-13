package com.growtogether.myrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {


    @SerializedName("base64encodedImage")
    @Expose
    public String restaurantImage;
    @SerializedName("name")
    @Expose
    public String restaurantName;
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

    public Restaurant() {
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImageUrl) {
        this.restaurantImage = restaurantImageUrl;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
