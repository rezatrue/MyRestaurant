package com.growtogether.myrestaurant;

public class Item {

    private String itemImage;
    private String itemName;
    private int itemPrice;

    public Item(){

    }

    public Item(String itemImage, String itemName, int itemPrice ){
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemImage(){
        return itemImage;
    }

    public String getItemName(){
        return itemName;
    }

    public int getItemPrice(){
        return itemPrice;
    }

}
