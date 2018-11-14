package com.growtogether.myrestaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageProcessor {

    public ImageProcessor() {
    }

    public String getEncodedImage(String path){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path,bmOptions);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
        byte[] byteArray = os.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.i("fragment", encodedImage.length() +" <- request base64 length code ->");
        return  encodedImage;
    }


}
