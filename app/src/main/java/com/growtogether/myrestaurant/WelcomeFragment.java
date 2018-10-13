package com.growtogether.myrestaurant;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    TextView msg;
    Button btnLogout, btnCreateRes;
    int userId;
    OnLogOutListener onLogOutListener;

    public interface OnLogOutListener{
        public void logoutPerformed();
        public void switchToCreateRestaurant();
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

        msg.setText("Welcome "+ MainActivity.prefConfig.readName());

//        if((userId = MainActivity.prefConfig.readUserId()) != 0) // need to concentrate later

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogOutListener.logoutPerformed();
            }
        });

        btnCreateRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogOutListener.switchToCreateRestaurant();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onLogOutListener = (OnLogOutListener) activity;
    }



}
