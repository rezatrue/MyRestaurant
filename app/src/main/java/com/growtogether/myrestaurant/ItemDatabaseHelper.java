package com.growtogether.myrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Restaurant Database";
    public static final int DATABASE_VERSION = 1;

    public static final String ITEM_TABLE = "tbl_item";
    public static final String TABLE_COL_SERIAL_NO = "tbl_serial";
    public static final String TABLE_COL_ITEM_NAME = "tbl_item_name";
    public static final String TABLE_COL_ITEM_IMAGE = "tbl_item_image";
    public static final String TABLE_COL_ITEM_DESCRIPTION = "tbl_item_description";
    public static final String TABLE_COL_ITEM_PRICE = "tbl_item_price";
    public static final String TABLE_COL_ITEM_STATUS = "tbl_item_status";


    public static final String CREATE_TABLE_ITEM = "CREATE TABLE " + ITEM_TABLE + "( " +
            TABLE_COL_SERIAL_NO +  "INTEGER PRIMARY KEY, " +
            TABLE_COL_ITEM_NAME + " TEXT, " +
            TABLE_COL_ITEM_IMAGE + " TEXT, " +
            TABLE_COL_ITEM_DESCRIPTION + " TEXT, " +
            TABLE_COL_ITEM_PRICE + " TEXT, " +
            TABLE_COL_ITEM_STATUS + " INTEGER);" ;


    public ItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ITEM_TABLE);
        onCreate(db);
    }
}
