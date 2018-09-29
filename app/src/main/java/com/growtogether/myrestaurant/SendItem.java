package com.growtogether.myrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendItem {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("base64encodedImage")
    @Expose
    private String imagebase64encode;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;


    public String getImagebase64encode() {
        return imagebase64encode;
    }

    public void setImagebase64encode(String imagebase64encode) {
        this.imagebase64encode = imagebase64encode;
    }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
