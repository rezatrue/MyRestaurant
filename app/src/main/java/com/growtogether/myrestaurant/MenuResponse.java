package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Reza (Iron Man)
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuResponse {

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items = null;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public static class Item {

        @SerializedName("serialno")
        @Expose
        private int serialno;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("category")
        @Expose
        private int category;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private double price;
        @SerializedName("restaurantSerialNo")
        @Expose
        private int restaurantSerialNo;
        @SerializedName("created")
        @Expose
        private String created;

        public Item() {
        }

        public Item(int serialno, String imageurl, String name, int category, String description, double price, int restaurantSerialNo, String created) {
            this.serialno = serialno;
            this.imageurl = imageurl;
            this.name = name;
            this.category = category;
            this.description = description;
            this.price = price;
            this.restaurantSerialNo = restaurantSerialNo;
            this.created = created;
        }

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

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getRestaurantSerialNo() {
            return restaurantSerialNo;
        }

        public void setRestaurantSerialNo(int restaurantSerialNo) {
            this.restaurantSerialNo = restaurantSerialNo;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }


}
