package com.growtogether.myrestaurant;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements LoginFragment.OnLoginFromActivityListener, WelcomeFragment.OnLogOutListener {

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
                    .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        }else {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new LoginFragment())
                    .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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
        intent.putExtra(SWITCH, 1);
        startActivity(intent);
    }

    @Override
    public void switchToRestaurantList() {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra(USERID, prefConfig.readUserId());
        intent.putExtra(SWITCH, 0);
        startActivity(intent);
    }
}
