package com.growtogether.myrestaurant;

/*
 * Design & Developed by Ali Reza (Iron Man)
 */

import android.graphics.Bitmap;

public class Item {

    private int itemSerialNo;
    private String itemImage;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemStatus;
    Base64CODEC codec;


    public Item(){
        codec = new Base64CODEC();
    }

/*
    public Item(String image, String itemName, String itemDescription, double itemPrice) {
        new Item();
        this.itemImage = image;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }
*/
    public Item(Bitmap bitmap, String itemName, String itemDescription, double itemPrice) {
        new Item();
        setItemImage(bitmap);
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public Item(byte[] image, String itemName, String itemDescription, double itemPrice) {
        new Item();
        setItemImage(image);
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

    public Bitmap getItemBitmapImage() {
        return codec.convertToBitmap(itemImage);
    }

    public byte[] getItemByteImage() {
        return codec.convertToByte(itemImage);
    }

    public void setItemImage(Bitmap bitmap) {
        this.itemImage = codec.convertToBase64(bitmap);
    }

    public void setItemImage(byte[] image) {
        this.itemImage = codec.convertByteToBase64(image);
    }

    public void setItemImage(String image) {
        this.itemImage = image;
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
