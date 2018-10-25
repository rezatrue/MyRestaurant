package com.growtogether.myrestaurant;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RestaurantActivity extends AppCompatActivity
        implements CreateRestaurantFragment.OnRestaurantCreateListener, RestaurantListFragment.OnRestaurantListItemListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        int userid = intent.getIntExtra(MainActivity.USERID, 0);
        int switchTo = intent.getIntExtra(MainActivity.SWITCH, 0);

        Toast.makeText(this, "User ID : "+ userid , Toast.LENGTH_LONG).show();
        if(switchTo == 0)
        getSupportFragmentManager().beginTransaction().add(R.id.restaurant_fragment_container, new RestaurantListFragment())
                    .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        if(switchTo == 1)
        getSupportFragmentManager().beginTransaction().add(R.id.restaurant_fragment_container, new CreateRestaurantFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }


    @Override
    public void switchToRestaurantList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment_container, new RestaurantListFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void switchToEditRestaurant() {
        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment_container, new CreateRestaurantFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
