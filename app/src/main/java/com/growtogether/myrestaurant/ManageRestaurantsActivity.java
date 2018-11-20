package com.growtogether.myrestaurant;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ManageRestaurantsActivity extends AppCompatActivity implements ItemListFragment.OnItemListActivityListener, AddItemFragment.OnAddItemActivityListener {
    ImageView imageView;
    TextView nameTV, phoneTV, addressTV;
    Button button;
    public static int userSerialNumber;
    public static int restaurantSerialNo;
    public final static String TAG = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_restaurants);

        imageView = findViewById(R.id.ivrestaurant);
        nameTV = findViewById(R.id.tvrname);
        phoneTV = findViewById(R.id.tvrphone);
        addressTV = findViewById(R.id.tvraddress);
        button = findViewById(R.id.btnrestaurant);

        Bundle bundle = getIntent().getExtras();
        //userSerialNumber  = bundle.getInt("UserSerialNo", 0);
        userSerialNumber = getIntent().getIntExtra("UserSerialNo", 0);

        restaurantSerialNo  = bundle.getInt("RestaurantSerialNo", 0);
        nameTV.setText(bundle.getString("Name"));
        phoneTV.setText(bundle.getString("Phone"));
        addressTV.setText(bundle.getString("Address"));

        Log.e(TAG, "ManageRestaurantActivity -> userSerialNumber : " + userSerialNumber);
        Log.e(TAG, "ManageRestaurantActivity -> Name : " + bundle.getString("Name"));
        Log.e(TAG, "ManageRestaurantActivity -> restaurantSerialNo : " + restaurantSerialNo);


        String urltxt = ApiClient.BASE_URL + bundle.getString("ImageUrl");
        Picasso.get().load(urltxt).into(imageView);

        getSupportFragmentManager().beginTransaction().add(R.id.manage_restaurant_fragment_container, new ItemListFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


    }


    @Override
    public void switchToAddItemFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.manage_restaurant_fragment_container, new AddItemFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    @Override
    public void switchToItemListFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.manage_restaurant_fragment_container, new ItemListFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }
}
