package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Reza (Iron Man)
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends Fragment {

    EditText item_name, item_price, item_description, item_category;
    Button btn_add;
    ImageView mImageView;
    Activity activity;
    private ApiInterface apiInterface;
    public final static String TAG = "fragment";
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    OnAddItemActivityListener onAddItemActivityListener;

    public interface OnAddItemActivityListener{
        void switchToItemListFragment();
    }


    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        mImageView = view.findViewById(R.id.iv_item_image);
        item_name = view.findViewById(R.id.et_item_name);
        item_price = view.findViewById(R.id.et_item_price);
        item_description = view.findViewById(R.id.et_item_desc);
        item_category = view.findViewById(R.id.et_item_category);
        btn_add = view.findViewById(R.id.btn_add_item);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        onAddItemActivityListener = (OnAddItemActivityListener) activity;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

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
        int category = Integer.parseInt(item_category.getText().toString());
        String description = item_description.getText().toString();
        double price = Double.parseDouble(item_price.getText().toString());
        int restaurant_id = ManageRestaurantsActivity.restaurantSerialNo;
        ImageProcessor imageProcessor = new ImageProcessor();

        Item item = new Item();
        item.setItemImage(imageProcessor.getEncodedImage(mCurrentPhotoPath));
        item.setName(name);
        item.setCategory(category);
        item.setDescription(description);
        item.setPrice(price);
        item.setRestaurantSerialNo(restaurant_id);

        Log.i(TAG, "item name : "+ name);

        Call<ItemAddResponse> menuResponseCall = apiInterface.addItem(item);
        menuResponseCall.enqueue(new Callback<ItemAddResponse>() {
            @Override
            public void onResponse(Call<ItemAddResponse> call, Response<ItemAddResponse> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(activity, "" + response.body().getName(), Toast.LENGTH_SHORT).show();

                    item_name.setText("");
                    item_price.setText("");
                    item_description.setText("");
                    item_category.setText("");

                    onAddItemActivityListener.switchToItemListFragment();
                }
            }

            @Override
            public void onFailure(Call<ItemAddResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
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
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        activity.sendBroadcast(mediaScanIntent);
    }



}
