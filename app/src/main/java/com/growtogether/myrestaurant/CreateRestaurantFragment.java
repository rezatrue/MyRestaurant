package com.growtogether.myrestaurant;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRestaurantFragment extends Fragment {


    double latitude,longitude;
    //Context context;
    Activity activity;

    ImageView imageIV;
    EditText nameET,phoneET, addressET, longitudeET, latitudeET;
    Button btn;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    OnRestaurantCreateListener onRestaurantCreateListener;

    public interface OnRestaurantCreateListener{
        public void switchToRestaurantList();
    }

    public CreateRestaurantFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant, container, false);

        imageIV = view.findViewById(R.id.iv_res_image);
        nameET = view.findViewById(R.id.et_res_name);
        phoneET = view.findViewById(R.id.et_res_phone);
        addressET = view.findViewById(R.id.et_res_address);
        longitudeET = view.findViewById(R.id.et_res_longitude);
        latitudeET = view.findViewById(R.id.et_res_latitude);
        btn = view.findViewById(R.id.btn_res_register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button clicked ");
                createRestaurant();
            }
        });

        imageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "image clicked ");
                dispatchTakePictureIntent();
                setPic();
            }
        });

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.context = context;
        this.activity = (Activity) context;
        onRestaurantCreateListener = (OnRestaurantCreateListener) activity;
    }


    private void createRestaurant() {

        String name = nameET.getText().toString();
        String address = addressET.getText().toString();
        String phone = phoneET.getText().toString();
        String longitude = longitudeET.getText().toString();
        String latitude = latitudeET.getText().toString();


        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(name);
        restaurant.setRestaurantAddress(address);
        restaurant.setRestaurantPhone(Integer.parseInt(phone));
        restaurant.setRestaurantLatitude(Double.parseDouble(longitude));
        restaurant.setRestaurantLongitude(Double.parseDouble(latitude));
        restaurant.setUserSerialNo(12); // need to pass id from DB

        ImageProcessor imageProcessor = new ImageProcessor();
        restaurant.setRestaurantImage(imageProcessor.getEncodedImage(mCurrentPhotoPath));

        Call<RestaurantResponse> restaurantResponse = apiInterface.getRestaurantResponse(restaurant);

        restaurantResponse.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                Log.i(TAG, "response ->: "+ response.code());
                if(response.isSuccessful()) {
                    nameET.setText(""); addressET.setText(""); phoneET.setText(""); longitudeET.setText(""); latitudeET.setText("");
                    onRestaurantCreateListener.switchToRestaurantList();
                }

            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                Log.i(TAG, "failed : "+t.getMessage());
            }
        });

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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageIV.getWidth();
        int targetH = imageIV.getHeight();

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
        imageIV.setImageBitmap(bitmap);
    }


}
