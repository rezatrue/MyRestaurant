package com.growtogether.myrestaurant;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements LoginFragment.OnLoginFromActivityListener, WelcomeFragment.OnLogOutListener {

    public static PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefConfig = new PrefConfig(this);

        if(savedInstanceState != null) return;

        if (prefConfig.readLoginStatus()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new LoginFragment())
                    .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }else {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new WelcomeFragment())
                    .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }


    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new RegisterFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void performLogin(String name) {
        prefConfig.writeName(name);
        prefConfig.writeLoginStatus(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new WelcomeFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new LoginFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void switchToCreateRestaurant() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new CreateRestaurantFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
