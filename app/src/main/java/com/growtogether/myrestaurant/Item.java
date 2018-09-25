package com.growtogether.myrestaurant;

/*
 * Design & Developed by Ali Reza (Iron Man)
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Item {

    private int itemSerialNo;
    private String itemImageUrl;
    private byte[] itemImage;
    private String itemName;
    private int itemCatId;
    private String itemDescription;
    private double itemPrice;
    private String itemCreated;
    private int itemStatus;

    public void setItemCatId(int itemCatId) {
        this.itemCatId = itemCatId;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public Item(){
    }

    public Item(int itemSerialNo, String itemImageUrl,String itemName, int itemCatId, String itemDescription, double itemPrice, String itemCreated) {
        this.itemSerialNo = itemSerialNo;
        this.itemImageUrl = itemImageUrl;
        this.itemName = itemName;
        this.itemCatId = itemCatId;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCreated = itemCreated;
    }


    public Item(Bitmap bitmap, String itemName, String itemDescription, double itemPrice) {
        setItemBitmapImage(bitmap);
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public Item(byte[] image, String itemName, String itemDescription, double itemPrice) {
        setItemImage(image);
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public int getItemCatId() {
        return itemCatId;
    }

    public String getItemCreated() {
        return itemCreated;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public int getItemSerialNo() {
        return itemSerialNo;
    }

    public void setItemSerialNo(int itemSerialNo) {
        this.itemSerialNo = itemSerialNo;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public Bitmap getItemBitmapImage() {
        return BitmapFactory.decodeByteArray(itemImage, 0, itemImage.length);
    }

    public void setItemBitmapImage(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
        byte[] byteArray = os.toByteArray();
        this.itemImage = byteArray;
        Log.i("data" , "image set");

    }

    public void setItemImage(byte[] image) {
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
