package com.growtogether.myrestaurant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment{

    private ListView restaurantListView;
    private RestaurantAdapter restaurantAdapter;
    private ArrayList<RestaurantListResponse.Restaurant> restaurants;

    Context context;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        restaurantListView = view.findViewById(R.id.res_list_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
        restaurants = new ArrayList<>();

        Call<RestaurantListResponse> restaurantListResponseCall = apiInterface.getRestaurantList();

        restaurantListResponseCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {

                if(response.isSuccessful()){
                    Log.i("fragment", "Success  " );
                    restaurants = response.body().getRestaurants();
                    restaurantAdapter = new RestaurantAdapter(context, restaurants);
                    restaurantListView.setAdapter(restaurantAdapter);
                }
            }

            @Override
            public void onFailure(Call<RestaurantListResponse> call, Throwable t) {
                //Toast.makeText(context, "failed : " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "failed : " + t.getMessage());

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
