package com.growtogether.myrestaurant.restaurant;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.growtogether.myrestaurant.pojo.Type;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.utils.ImageProcessor;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.Restaurant;
import com.growtogether.myrestaurant.pojo.RestaurantResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRestaurantFragment extends Fragment {
    Spinner spinner;
    double latitude, longitude;
    ArrayList<String> types;
    String type;
    Activity activity;

    ImageView imageIV;
    EditText nameET, phoneET, addressET, longitudeET, latitudeET;
    Button btn;
    boolean userEdit = false;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int RESULT_LOAD_IMAGE = 2;

    OnRestaurantCreateListener onRestaurantCreateListener;

    private FusedLocationProviderClient mFusedLocationClient;


    public interface OnRestaurantCreateListener {
        public void switchToRestaurantList();
    }

    public CreateRestaurantFragment() {
        types = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant, container, false);

        imageIV = view.findViewById(R.id.iv_item_image);
        nameET = view.findViewById(R.id.et_item_name);
        phoneET = view.findViewById(R.id.et_item_price);
        addressET = view.findViewById(R.id.et_item_description);
        longitudeET = view.findViewById(R.id.et_res_longitude);
        latitudeET = view.findViewById(R.id.et_res_latitude);
        btn = view.findViewById(R.id.btn_res_register);


        spinner = view.findViewById(R.id.spinner_type);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "button clicked ");
                if (!userEdit) createRestaurant();
                else editRestaurant();
            }
        });

        imageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "image clicked ");
                showPictureDialog();
            }
        });


        longitudeET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    latitudeET.setText(String.valueOf(latitude));
                    longitudeET.setText(String.valueOf(longitude));
                }

            }
        });

        latitudeET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    latitudeET.setText(String.valueOf(latitude));
                    longitudeET.setText(String.valueOf(longitude));
                }

            }
        });

        Log.i(TAG, "Lat :-> " + latitude  + " Lng:-> "+ longitude);

        Bundle bundle = this.getArguments();

        if (null != bundle) userEdit = true;
        // load data
        if (userEdit){
            btn.setText("Edit");

            String name = bundle.getString("name");
            nameET.setText(name);

            type = bundle.getString("type");

            String imageurl = ApiClient.BASE_URL + bundle.getString("imageurl");
            Log.i("fragment", "imageurl :" + imageurl);
            Picasso.get().load(imageurl).into(picassoImageTarget());

            String address = bundle.getString("address");
            addressET.setText(address);
            String phone= bundle.getString("phone");
            phoneET.setText(phone);
            double latitude = bundle.getDouble("latitude");
            longitudeET.setText(String.valueOf(latitude));
            double longitude = bundle.getDouble("longitude");
            latitudeET.setText(String.valueOf(longitude));

            int serialno = bundle.getInt("serialno");
            int userId = bundle.getInt("userid");
            String created = bundle.getString("created");
        }

        return view;
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Open Gallery",
                "Open Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dispatchSelectPictureIntent();
                                break;
                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

        getListOfTypes();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                type = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
        onRestaurantCreateListener = (OnRestaurantCreateListener) activity;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i(TAG, "Lat :" + location.getLatitude()  + " Lng: "+ location.getLongitude());
                        }else{
                            Log.i(TAG, "Lat : null Lng: null");

                        }
                    }
                });

    }


    private void getListOfTypes(){
        Call<ArrayList<Type>> callTypes = apiInterface.getResTypeList();
        callTypes.enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                if(response.code() == 200 ){
                    ArrayList<Type> arrtypes = response.body();
                    for (Type type : arrtypes) {
                        types.add(type.getResType());
                    }
                    //types.add(0, "ALL");
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, types);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinner.setAdapter(dataAdapter);
                    // when editing then spinner item need to be selected
                    if(userEdit)
                        if(type.length() > 1) spinner.setSelection(types.indexOf(type));

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {

            }
        });

    }

    // edit restaurant info
    private void editRestaurant() {
        String name = nameET.getText().toString();
        String address = addressET.getText().toString();
        String phone = phoneET.getText().toString();
        String longitude = longitudeET.getText().toString();
        String latitude = latitudeET.getText().toString();

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(name);
        restaurant.setRestaurantAddress(address);
        restaurant.setRestaurantType(type);
        restaurant.setRestaurantPhone(Integer.parseInt(phone));
        restaurant.setRestaurantLatitude(Double.parseDouble(longitude));
        restaurant.setRestaurantLongitude(Double.parseDouble(latitude));
        restaurant.setUserSerialNo(RestaurantActivity.userid);

        ImageProcessor imageProcessor = new ImageProcessor();
        restaurant.setRestaurantImage(imageProcessor.compressImage(mCurrentPhotoPath));

        Call<RestaurantResponse> restaurantResponse = apiInterface.getEditRestaurantResponse(restaurant);

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

    private void createRestaurant() {

        String name = nameET.getText().toString();
        String address = addressET.getText().toString();
        String phone = phoneET.getText().toString();
        String longitude = longitudeET.getText().toString();
        String latitude = latitudeET.getText().toString();

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(name);
        restaurant.setRestaurantAddress(address);
        restaurant.setRestaurantType(type);
        restaurant.setRestaurantPhone(Integer.parseInt(phone));
        restaurant.setRestaurantLatitude(Double.parseDouble(longitude));
        restaurant.setRestaurantLongitude(Double.parseDouble(latitude));
        restaurant.setUserSerialNo(RestaurantActivity.userid);

        ImageProcessor imageProcessor = new ImageProcessor();
        restaurant.setRestaurantImage(imageProcessor.compressImage(mCurrentPhotoPath));

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

    private void dispatchSelectPictureIntent(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == activity.RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
        }
        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = activity.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mCurrentPhotoPath = cursor.getString(columnIndex);
            cursor.close();

            imageIV.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
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

    private Target picassoImageTarget() {
        Log.d("picassoImageTarget", " picassoImageTarget");

        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                final Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FileOutputStream fos = null;
                        try {
                            File myImageFile = createImageFile();
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        galleryAddPic();
                        setPic();
                        Log.i("image", "image saved to >>>" + mCurrentPhotoPath);
                   }
                });

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };

    }
}
