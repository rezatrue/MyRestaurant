package com.growtogether.myrestaurant;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.growtogether.myrestaurant.ordermanagement.OrderingActivity;

public class RestaurantActivity extends AppCompatActivity
        implements CreateRestaurantFragment.OnRestaurantCreateListener, MyRestaurantListFragment.OnRestaurantListItemListener, AllRestaurantListFragment.OnAllRestaurantListItemListener {
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

    }


    @Override
    public void switchToRestaurantList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment_container, new MyRestaurantListFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void switchToEditRestaurant() {
        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment_container, new CreateRestaurantFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void switchToOrderManageRestaurant(RestaurantListResponse.Restaurant restaurant) {
        Log.i("fragment", "item no : " + restaurant.getSerialno());

        Intent intent = new Intent(this, OrderingActivity.class);
        intent.putExtra("RestaurantSerialNo", restaurant.getSerialno());
        intent.putExtra("UserSerialNo", restaurant.getUserid());
        intent.putExtra("Name", restaurant.getName());
        intent.putExtra("Phone", restaurant.getPhone());
        intent.putExtra("Address", restaurant.getAddress());
        intent.putExtra("ImageUrl", restaurant.getImageurl());

        startActivity(intent);
    }
}
