package com.growtogether.myrestaurant.starter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.growtogether.myrestaurant.MapActivity;
import com.growtogether.myrestaurant.ordermanagement.MyOrdersActivity;
import com.growtogether.myrestaurant.ordermanagement.PostOrderActivity;
import com.growtogether.myrestaurant.utils.PrefConfig;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.restaurant.RestaurantActivity;

public class MainActivity extends AppCompatActivity  implements LoginFragment.OnLoginFromActivityListener, WelcomeFragment.WelcomeListener {

    public static PrefConfig prefConfig;
    public static final String USERID = "userid";
    public static final String SWITCH = "Switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefConfig = new PrefConfig(this);

        if(savedInstanceState != null) return;

        if (prefConfig.readLoginStatus()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new WelcomeFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }else {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new LoginFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }


    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new RegisterFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void performLogin(String name, int id) {
        prefConfig.writeName(name);
        prefConfig.writeLoginStatus(true);
        prefConfig.writeUserId(id); // for user id
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new WelcomeFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        prefConfig.writeUserId(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new LoginFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void switchToCreateRestaurant() {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra(USERID, prefConfig.readUserId());
        intent.putExtra(SWITCH, 1); // CreateRestaurantFragment
        startActivity(intent);
    }

    @Override
    public void switchToMyRestaurantList() {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra(USERID, prefConfig.readUserId());
        intent.putExtra(SWITCH, 0);   // MyRestaurantListFragment
        startActivity(intent);
    }

    @Override
    public void switchToAllRestaurantList() {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra(USERID, prefConfig.readUserId());
        intent.putExtra(SWITCH, 2); // AllRestaurantListFragment
        startActivity(intent);
    }


    @Override
    public void switchToCheckOrdersStatus() {
        Intent intent = new Intent(this, PostOrderActivity.class);
        intent.putExtra(USERID, prefConfig.readUserId());
        startActivity(intent);
    }

    @Override
    public void openMap(){
        if(isServicesOK()){
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }

    }

    private static final String TAG = "MapActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



}
