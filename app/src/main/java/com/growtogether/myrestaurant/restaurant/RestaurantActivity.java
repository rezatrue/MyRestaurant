package com.growtogether.myrestaurant.restaurant;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.managerestaurant.ManageRestaurantsActivity;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse;
import com.growtogether.myrestaurant.ordermanagement.OrderingActivity;
import com.growtogether.myrestaurant.starter.MainActivity;

public class RestaurantActivity extends AppCompatActivity
        implements CreateRestaurantFragment.OnRestaurantCreateListener, MyRestaurantListFragment.OnRestaurantListItemListener,
        AllRestaurantListFragment.OnAllRestaurantListItemListener {
    public static int userid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        userid = intent.getIntExtra(MainActivity.USERID, 0);
        int switchTo = intent.getIntExtra(MainActivity.SWITCH, 0);


        Toast.makeText(this, "User ID : "+ userid + " switchTo : " + switchTo, Toast.LENGTH_LONG).show();

        if(switchTo == 0)
        getSupportFragmentManager().beginTransaction().add(R.id.restaurant_fragment_container, new MyRestaurantListFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        if(switchTo == 1)
        getSupportFragmentManager().beginTransaction().add(R.id.restaurant_fragment_container, new CreateRestaurantFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        if(switchTo == 2)
            getSupportFragmentManager().beginTransaction().add(R.id.restaurant_fragment_container, new AllRestaurantListFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        if(switchTo == 3){
            Bundle bundle = new Bundle();
            bundle.putInt("orderid", intent.getIntExtra("oid", 0) );
            Log.i("fragment", "c RA oid: " + intent.getIntExtra("oid", 0));

            bundle.putInt("userid", intent.getIntExtra("uid", 0) );
            Log.i("fragment", "c RA uid: " + intent.getIntExtra("uid", 0));

            int phone = intent.getIntExtra("cphone", 0);
                    Log.i("fragment", "c RA phone-: " + phone);
            bundle.putInt("cphone", phone);


            bundle.putString("cname", intent.getStringExtra("cname") );
            Log.i("fragment", "c RA name-: " + intent.getIntExtra("cname", 0));

            bundle.putString("date", intent.getStringExtra("date") );
            Log.i("fragment", "c RA date: " + intent.getIntExtra("date", 0));

            bundle.putInt("status", intent.getIntExtra("status", 0) );
            Log.i("fragment", "c RA status: " + intent.getIntExtra("status", 0));

            ResOrderDetailsFragment resOrderDetailsFragment = new ResOrderDetailsFragment();
            resOrderDetailsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().add(R.id.restaurant_fragment_container, resOrderDetailsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }


    }


    @Override
    public void switchToRestaurantList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment_container, new MyRestaurantListFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void afterDeleteRestaurant() {
        switchToRestaurantList();
    }

    @Override
    public void switchToEditRestaurant(RestaurantListResponse.Restaurant restaurant) {
        Bundle bundle = new Bundle();

        bundle.putInt("serialno", restaurant.getSerialno());
        bundle.putString("name", restaurant.getName() );
        bundle.putString("imageurl", restaurant.getImageurl() );
        bundle.putString("type", restaurant.getType() );
        bundle.putString("address", restaurant.getAddress() );
        bundle.putString("phone", restaurant.getPhone() );
        bundle.putDouble("latitude", restaurant.getLatitude() );
        bundle.putDouble("longitude", restaurant.getLongitude() );
        bundle.putInt("userid", restaurant.getUserid());
        bundle.putString("created", restaurant.getCreated() );

        CreateRestaurantFragment createRestaurantFragment = new CreateRestaurantFragment();
        createRestaurantFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment_container, createRestaurantFragment)
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    @Override
    public void switchToOrderManageRestaurant(RestaurantListResponse.Restaurant restaurant) {
        Log.i("fragment", "item no : " + restaurant.getSerialno());

        Intent intent = new Intent(this, OrderingActivity.class);
        intent.putExtra("RestaurantSerialNo", restaurant.getSerialno());
        //intent.putExtra("UserSerialNo", restaurant.getUserid());
        intent.putExtra("CustomerSerialNo", userid); // Customer is the user who is browsing
        intent.putExtra("Name", restaurant.getName());
        intent.putExtra("Type", restaurant.getType());
        intent.putExtra("Phone", restaurant.getPhone());
        intent.putExtra("Address", restaurant.getAddress());
        intent.putExtra("ImageUrl", restaurant.getImageurl());

        startActivity(intent);
    }

    @Override
    public void switchToManageRestaurant(RestaurantListResponse.Restaurant restaurant) {

        Intent intent = new Intent(this, ManageRestaurantsActivity.class);
        intent.putExtra("RestaurantSerialNo", restaurant.getSerialno());
        //intent.putExtra("RestaurantSerialNo", restaurants.get(i).getSerialno());
        //intent.putExtra("UserSerialNo", 12);
        intent.putExtra("UserSerialNo", restaurant.getUserid());
        intent.putExtra("Name", restaurant.getName());
        intent.putExtra("Type", restaurant.getType());
        intent.putExtra("Phone", restaurant.getPhone());
        intent.putExtra("Address", restaurant.getAddress());
        intent.putExtra("ImageUrl", restaurant.getImageurl());
        intent.putExtra("viewpager_position", 0);
        startActivity(intent);
    }


}
