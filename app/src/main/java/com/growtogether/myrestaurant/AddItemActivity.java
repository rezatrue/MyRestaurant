package com.growtogether.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* Design & Developed by Ali Reza (Iron Man)
*/


public class AddItemActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    EditText item_name, item_price, item_description;
    Button btn_add, btn_viewlist;
    ImageView mImageView;

    private ItemDatabaseOperation itemDatabaseOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        item_name = findViewById(R.id.et_food_item);
        item_price = findViewById(R.id.et_food_price);
        item_description = findViewById(R.id.et_food_description);
        btn_add = findViewById(R.id.btn_add);
        btn_viewlist = findViewById(R.id.btn_viewlist);
        mImageView = findViewById(R.id.imageView);

        itemDatabaseOperation = new ItemDatabaseOperation(this);
        btn_add.setOnClickListener(this);
        btn_viewlist.setOnClickListener(this);
        item_description.setOnFocusChangeListener(this);
        mImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.btn_add:
                addItem();
                Log.i("data", item_name.getText().toString());
                Log.i("data", item_price.getText().toString());
                Log.i("data", item_description.getText().toString());
                break;

            case R.id.btn_viewlist:
                Log.i("data", "new activity button pressed".toString());
                startActivity(new Intent(AddItemActivity.this,ItemListActivity.class));
                break;

            case R.id.imageView:
                dispatchTakePictureIntent();
                Toast.makeText(getApplicationContext(), "Image icon clicked" , Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void addItem(){
        String image = mCurrentPhotoPath;
        //Base64CODEC codec  = new Base64CODEC();
        //Bitmap bitmap = codec.Base64ImageFromURL(image);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);


        String name = item_name.getText().toString();
        String pricetxt = item_price.getText().toString();

        double price = Double.parseDouble(pricetxt);

        String description = item_description.getText().toString();

        if(name.length() > 0 && pricetxt.length() > 0) {
            Item item = new Item(bitmap, name, description, price);
            Log.i("data" , "item created ");

            itemDatabaseOperation.addItem(item);
            Log.i("data" , "item inserted inti db ");

            item_name.setText("");
            item_price.setText("");
            item_description.setText("");
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            //Toast.makeText(getApplicationContext(), hasFocus + " " , Toast.LENGTH_LONG).show();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);
        }
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.growtogether.myrestaurant.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
    /*
    File sd = Environment.getExternalStorageDirectory();
    File image = new File(sd+filePath, imageName);
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
    bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
    imageView.setImageBitmap(bitmap);
    */
}
