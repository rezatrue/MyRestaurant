package com.growtogether.myrestaurant.restaurant;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.growtogether.myrestaurant.utils.ImageProcessor;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.managerestaurant.ManageRestaurantsActivity;
import com.growtogether.myrestaurant.pojo.Item;
import com.growtogether.myrestaurant.pojo.ItemAddResponse;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    EditText item_name, item_price, item_description;
    Spinner spinner_category;
    Button btn_add;
    ImageView mImageView;
    private ApiInterface apiInterface;
    public final static String TAG = "fragment";
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    String itemCat[] = {"Main course", "Dessert", "Drinks",  "Snacks"};
    int category = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

        mImageView = findViewById(R.id.iv_item_image);
        item_name = findViewById(R.id.et_item_name);
        item_price = findViewById(R.id.et_item_price);
        item_description = findViewById(R.id.et_item_description);
        spinner_category = findViewById(R.id.spinner_category);
        btn_add = findViewById(R.id.btn_add_item);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemCat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(dataAdapter);


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category =  i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button clicked ");
                addItem();
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "image clicked ");
                dispatchTakePictureIntent();
            }
        });
    }

    public void addItem(){
        Log.i(TAG, "add item ");

        String name = item_name.getText().toString();
        String description = item_description.getText().toString();
        double price = Double.parseDouble(item_price.getText().toString());
        int restaurant_id = ManageRestaurantsActivity.restaurantSerialNo;
        ImageProcessor imageProcessor = new ImageProcessor();

        Item item = new Item();
        item.setItemImage(imageProcessor.compressImage(mCurrentPhotoPath));
        item.setName(name);
        item.setCategory(category);
        item.setDescription(description);
        item.setPrice(price);
        item.setRestaurantSerialNo(restaurant_id);

        Log.i(TAG, "item a name : "+ name);
        Log.i(TAG, "item a id : "+ restaurant_id);
        Log.i(TAG, "item a price : "+ price);
        Log.i(TAG, "item a category : "+ category);

        Call<ItemAddResponse> menuResponseCall = apiInterface.addItem(item);
        menuResponseCall.enqueue(new Callback<ItemAddResponse>() {
            @Override
            public void onResponse(Call<ItemAddResponse> call, Response<ItemAddResponse> response) {
                Log.i(TAG, "respo succ :-- "+ response.isSuccessful());
                Log.i(TAG, "respo code :-- "+ response.code());

                if(response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "" + response.body().getName(), Toast.LENGTH_SHORT).show();

                    item_name.setText("");
                    item_price.setText("");
                    item_description.setText("");
//
//                    Intent i = new Intent(AddItemActivity.this, ManageRestaurantsActivity.class);
//                    i.putExtra("UserSerialNo", ManageRestaurantsActivity.userSerialNumber);
//                    i.putExtra("RestaurantSerialNo", ManageRestaurantsActivity.restaurantSerialNo); //
//                    i.putExtra("Type", ManageRestaurantsActivity.type);
//                    i.putExtra("viewpager_position", 0); //
//                    i.putExtra("Name", ManageRestaurantsActivity.name);
//                    i.putExtra("Phone", ManageRestaurantsActivity.phone);
//                    i.putExtra("Address", ManageRestaurantsActivity.address);
//
//                    startActivity(i);

                    finish();

                }
            }

            @Override
            public void onFailure(Call<ItemAddResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "failed : " + t.getMessage());
            }
        });

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
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


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


}
