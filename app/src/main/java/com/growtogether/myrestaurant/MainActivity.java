package com.growtogether.myrestaurant;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements LoginFragment.OnLoginFromActivityListener {

    public static PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefConfig = new PrefConfig(this);

        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new LoginFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        /*


        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new WelcomeFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        */

    }


    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new RegisterFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void performLogin(String name) {

    }
}
