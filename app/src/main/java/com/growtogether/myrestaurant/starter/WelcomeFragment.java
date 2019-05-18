package com.growtogether.myrestaurant.starter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.OptionAdapter;
import com.growtogether.myrestaurant.pojo.Option;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    TextView msg;
    private ArrayList<Option> options;
    private RecyclerView recyclerView;
    private OptionAdapter optionAdapter;
    int userId;
    Activity activity;
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
        msg.setText("Welcome "+ MainActivity.prefConfig.readName());
//        if((userId = MainActivity.prefConfig.readUserId()) != 0) // need to concentrate later
        createOptions();

        recyclerView = view.findViewById(R.id.optionRecyclerView);
        optionAdapter = new OptionAdapter(activity,options);
        GridLayoutManager glm = new GridLayoutManager(activity,2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(optionAdapter);

        optionAdapter.setOnItemClickListener(new OptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position == 0 ) welcomeListener.switchToCreateRestaurant();
                if(position == 1 ) welcomeListener.switchToMyRestaurantList();
                if(position == 2 ) welcomeListener.switchToAllRestaurantList();
                if(position == 3 ) welcomeListener.switchToCheckOrdersStatus();
                if(position == 4 ) welcomeListener.openMap();
                if(position == 5 ) welcomeListener.logoutPerformed();
                Log.i("fragemnt", "Recycle view position "+ position);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
        welcomeListener = (WelcomeListener) activity;
    }

    private void createOptions() {
        options = new ArrayList<>();
        options.add(new Option(R.drawable.ic_business_black_24dp,"Create A Restaurant"));
        options.add(new Option(R.drawable.ic_content_paste_black_24dp,"Manage Restautants"));
        options.add(new Option(R.drawable.ic_search_black_24dp,"Search Restaurant"));
        options.add(new Option(R.drawable.ic_format_list_numbered_black_24dp,"Check Orders Status"));
        options.add(new Option(R.drawable.ic_my_location_black_24dp,"Map"));
        options.add(new Option(R.drawable.ic_phonelink_erase_black_24dp,"Logout"));

    }


}
