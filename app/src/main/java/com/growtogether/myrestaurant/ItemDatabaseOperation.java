package com.growtogether.myrestaurant;

/*
 * Design & Developed by Ali Reza (Iron Man)
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ItemDatabaseOperation {
    private ItemDatabaseHelper itemDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public ItemDatabaseOperation(Context context) {
        itemDatabaseHelper = new ItemDatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = itemDatabaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public boolean addItem(Item item){
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(itemDatabaseHelper.TABLE_COL_ITEM_NAME, item.getItemName());
        contentValues.put(itemDatabaseHelper.TABLE_COL_ITEM_IMAGE, item.getItemByteImage());
        contentValues.put(itemDatabaseHelper.TABLE_COL_ITEM_DESCRIPTION, item.getItemDescription());
        contentValues.put(itemDatabaseHelper.TABLE_COL_ITEM_PRICE, item.getItemPrice());
        contentValues.put(itemDatabaseHelper.TABLE_COL_ITEM_STATUS, 0); // default not published
        long id = sqLiteDatabase.insert(itemDatabaseHelper.ITEM_TABLE, null, contentValues);
        this.close();

        return id > 0 ? true : false;
    }


    public ArrayList<Item> getAllItems(){
        ArrayList<Item> items = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.query(itemDatabaseHelper.ITEM_TABLE,null,null,null, null, null,null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for(int i = 0 ; i < cursor.getCount() ; i++){
                byte[] byteImage =  cursor.getBlob(cursor.getColumnIndex(itemDatabaseHelper.TABLE_COL_ITEM_IMAGE));
                String name = cursor.getString(cursor.getColumnIndex(itemDatabaseHelper.TABLE_COL_ITEM_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(itemDatabaseHelper.TABLE_COL_ITEM_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(itemDatabaseHelper.TABLE_COL_ITEM_DESCRIPTION));
                int status = cursor.getInt(cursor.getColumnIndex(itemDatabaseHelper.TABLE_COL_ITEM_STATUS));
                Item item = new Item(byteImage,name,description,price);
                items.add(item);
                cursor.moveToNext();
            }
        }
        this.close();
        return items;
    }
}
