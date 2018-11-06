package com.growtogether.myrestaurant;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ManageRestaurantsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView nameTV, phoneTV, addressTV;
    Button button;
    public static int userSerialNumber;
    public static int restaurantSerialNo;

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
        userSerialNumber  = bundle.getInt("UserSerialNo", 0);
        restaurantSerialNo  = bundle.getInt("RestaurantSerialNo", 0);
        nameTV.setText(bundle.getString("Name"));
        phoneTV.setText(bundle.getString("Phone"));
        addressTV.setText(bundle.getString("Address"));

        String urltxt = ApiClient.BASE_URL + bundle.getString("ImageUrl");
        Picasso.get().load(urltxt).into(imageView);

        getSupportFragmentManager().beginTransaction().add(R.id.manage_restaurant_fragment_container, new ItemListFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

//        getSupportFragmentManager().beginTransaction().add(R.id.manage_restaurant_fragment_container, new AddItemFragment())
//                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }



}
