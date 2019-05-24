package com.growtogether.myrestaurant.restaurant;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse.Restaurant;
import com.growtogether.myrestaurant.adapters.RestaurantAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRestaurantListFragment extends Fragment{
    TextView textView;
    private ListView restaurantListView;
    private RestaurantAdapter restaurantAdapter;
    private ArrayList<Restaurant> restaurants;
    ConstraintLayout constraintLayout;
    //Context context;
    Activity activity;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;


    OnRestaurantListItemListener onRestaurantListItemListener;

    public interface OnRestaurantListItemListener{
        public void switchToEditRestaurant(Restaurant restaurant);
        public void switchToManageRestaurant(Restaurant restaurant);
        public void afterDeleteRestaurant();
    }

    public MyRestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        restaurantListView = view.findViewById(R.id.res_list_view);
        textView = view.findViewById(R.id.resMsgTV);
        constraintLayout = view.findViewById(R.id.search_tab);
        constraintLayout.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
        restaurants = new ArrayList<>();



        Call<RestaurantListResponse> restaurantListResponseCall = apiInterface.getRestaurantList(RestaurantActivity.userid);

        restaurantListResponseCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {
                if(response.isSuccessful()){
                    Log.i("fragment", "Success  " );
                    Log.i("fragment", "id  " + RestaurantActivity.userid);

                    if(response.code() == 200) {
                        restaurants = response.body().getRestaurants();
                        if (restaurants != null)
                            if (restaurants.size() > 0) {
                                restaurantAdapter = new RestaurantAdapter(activity, restaurants);
                                restaurantListView.setAdapter(restaurantAdapter);
                            } else {
                                restaurantListView.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);
                            }
                        else {
                            restaurantListView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
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
                RestaurantListResponse.Restaurant restaurant = restaurants.get(i);
                onRestaurantListItemListener.switchToManageRestaurant(restaurant);
            }
        });

        restaurantListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(i);
                return true;
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

    private void showDialog(int resId){
        final int id = resId;
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        //pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Edit Restaurant",
                "Delete" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Log.i("fragment", "Edit : "+ id + "size : " + restaurants.size());
                                RestaurantListResponse.Restaurant restaurant = restaurants.get(id);
                                onRestaurantListItemListener.switchToEditRestaurant(restaurant);
                                break;
                            case 1:
                                Log.i("fragment", "Delete : ");
                                // delete the restaurant with the id id

                                onRestaurantListItemListener.afterDeleteRestaurant();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

}
