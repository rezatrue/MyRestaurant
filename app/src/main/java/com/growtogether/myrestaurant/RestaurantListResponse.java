package com.growtogether.myrestaurant;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RestaurantListResponse {

    @SerializedName("restaurants")
    @Expose
    private ArrayList<Restaurant> restaurants = null;

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    class Restaurant {

        @SerializedName("serialno")
        @Expose
        private int serialno;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("latitude")
        @Expose
        private double latitude;
        @SerializedName("longitude")
        @Expose
        private double longitude;
        @SerializedName("userid")
        @Expose
        private int userid;
        @SerializedName("created")
        @Expose
        private String created;

        public int getSerialno() {
            return serialno;
        }

        public void setSerialno(int serialno) {
            this.serialno = serialno;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

    }

}