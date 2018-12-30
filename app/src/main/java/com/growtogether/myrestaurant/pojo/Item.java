package com.growtogether.myrestaurant.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("serialno")
    @Expose
    private int serialno;
    @SerializedName("base64encodedImage")
    @Expose
    public Object itemImage;
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
    @SerializedName("restaurantid")
    @Expose
    private int restaurantSerialNo;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public Item() {
    }

    public Item(int serialno, Object itemImage, String name, int category, String description, double price, int restaurantSerialNo, String created, String modified) {
        this.serialno = serialno;
        this.itemImage = itemImage;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.restaurantSerialNo = restaurantSerialNo;
        this.created = created;
        this.modified = modified;
    }

    public Item(Object itemImage, String name, int category, String description, double price, int restaurantSerialNo) {
        this.itemImage = itemImage;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.restaurantSerialNo = restaurantSerialNo;
    }

    public int getSerialno() {
        return serialno;
    }

    public void setSerialno(int serialno) {
        this.serialno = serialno;
    }

    public Object getItemImage() {
        return itemImage;
    }

    public void setItemImage(Object itemImage) {
        this.itemImage = itemImage;
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

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


}
