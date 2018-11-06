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

    //Context context;
    Activity activity;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;


    OnRestaurantListItemListener onRestaurantListItemListener;

    public interface OnRestaurantListItemListener{
        public void switchToEditRestaurant();
    }

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



        /*
        Call<RestaurantListResponse> restaurantListResponseCall = apiInterface.getRestaurantList();

        restaurantListResponseCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {

                if(response.isSuccessful()){
                    Log.i("fragment", "Success  " );
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
        */




        // why RestaurantActivity.userid always Zero & also need to check list size greater than zero before set adopter

        Call<RestaurantListResponse> restaurantListResponseCall = apiInterface.getRestaurantList(RestaurantActivity.userid);

        restaurantListResponseCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {
                if(response.isSuccessful()){
                    Log.i("fragment", "Success  " );
                    Log.i("fragment", "id  " + RestaurantActivity.userid);

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
                Log.i("fragment", "item no : " + restaurants.get(i).getSerialno());
                Log.i("fragment", "item clicked : ");
                //RestaurantListResponse.Restaurant restaurant = restaurants.get(i);

                Intent intent = new Intent(activity, ManageRestaurantsActivity.class);
                intent.putExtra("RestaurantSerialNo", restaurants.get(i).getSerialno());
                intent.putExtra("UserSerialNo", restaurants.get(i).getUserid());
                intent.putExtra("Name", restaurants.get(i).getName());
                intent.putExtra("Phone", restaurants.get(i).getPhone());
                intent.putExtra("Address", restaurants.get(i).getAddress());
                intent.putExtra("ImageUrl", restaurants.get(i).getImageurl());

                startActivity(intent);
                //onRestaurantListItemListener.switchToEditRestaurant();
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.context = context;
        this.activity = (Activity) context;
        onRestaurantListItemListener = (OnRestaurantListItemListener) activity;
    }
}
