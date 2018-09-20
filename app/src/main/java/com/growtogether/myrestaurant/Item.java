package com.growtogether.myrestaurant;

/*
 * Design & Developed by Ali Reza (Iron Man)
 */

public class Item {

    private int itemSerialNo;
    private String itemImage;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemStatus;

    public Item(){

    }

    public Item(int itemSerialNo, String itemImage, String itemName, String itemDescription, double itemPrice, int itemStatus) {
        this.itemSerialNo = itemSerialNo;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemStatus = itemStatus;
    }

    public Item(String itemImage, String itemName, String itemDescription, double itemPrice) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public int getItemSerialNo() {
        return itemSerialNo;
    }

    public void setItemSerialNo(int itemSerialNo) {
        this.itemSerialNo = itemSerialNo;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int isItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }
}
