package com.growtogether.myrestaurant;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.growtogether.myrestaurant.RestaurantListResponse.Restaurant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllRestaurantListFragment extends Fragment{

    private ListView restaurantListView;
    private RestaurantAdapter restaurantAdapter;
    private ArrayList<Restaurant> restaurants;

    //Context context;
    Activity activity;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;


    OnAllRestaurantListItemListener onAllRestaurantListItemListener;

    public interface OnAllRestaurantListItemListener{
        public void switchToOrderManageRestaurant(Restaurant restaurant);
    }

    public AllRestaurantListFragment() {
        Log.i("fragment", "AllRestaurantListFragment" );
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


        Call<RestaurantListResponse> restaurantListResponseCall = apiInterface.getAllRestaurantList();

        restaurantListResponseCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {
                if(response.isSuccessful()){
                    Log.i("fragment", "Success  : - " );

                    restaurants = response.body().getRestaurants();
                    restaurantAdapter = new RestaurantAdapter(activity, restaurants); // name a change context to activity
                    restaurantListView.setAdapter(restaurantAdapter);

                }
            }

            @Override
            public void onFailure(Call<RestaurantListResponse> call, Throwable t) {
                //Toast.makeText(context, "failed : " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "failed : " + t.getMessage());

            }
        });


        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                onAllRestaurantListItemListener.switchToOrderManageRestaurant(restaurants.get(i));
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.context = context;
        this.activity = (Activity) context;
        onAllRestaurantListItemListener = (OnAllRestaurantListItemListener) activity;
    }
}
