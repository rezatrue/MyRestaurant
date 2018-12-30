package com.growtogether.myrestaurant.starter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    TextView msg;
    Button btnLogout, btnCreateRes, btnMyResList, btnMap, btnMyOrders;
    Button searchRes;
    int userId;
    WelcomeListener welcomeListener;

    public interface WelcomeListener {
        public void logoutPerformed();
        public void switchToCreateRestaurant();
        public void switchToMyRestaurantList();
        public void switchToAllRestaurantList();
        public void switchToCheckOrdersStatus();
        public void openMap();
    }


    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("fragemnt", "Welcome onCreateView ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        msg = view.findViewById(R.id.welcomemsg);
        btnLogout =  view.findViewById(R.id.btnLogout);
        btnCreateRes =  view.findViewById(R.id.btn_res_create);
        btnMap =  view.findViewById(R.id.btn_map);
        btnMyOrders =  view.findViewById(R.id.btn_my_orders);
        btnMyResList =  view.findViewById(R.id.btn_res_manage);
        searchRes =  view.findViewById(R.id.btn_res_search);

        msg.setText("Welcome "+ MainActivity.prefConfig.readName());

//        if((userId = MainActivity.prefConfig.readUserId()) != 0) // need to concentrate later

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeListener.logoutPerformed();
            }
        });

        searchRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeListener.switchToAllRestaurantList();
            }
        });

        btnCreateRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeListener.switchToCreateRestaurant();
            }
        });

        btnMyResList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeListener.switchToMyRestaurantList();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeListener.openMap();
            }
        });

        btnMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeListener.switchToCheckOrdersStatus();
            }
        });
        
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        welcomeListener = (WelcomeListener) activity;
    }



}
