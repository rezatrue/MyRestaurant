package com.growtogether.myrestaurant.ordermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.restaurant.CreateRestaurantFragment;
import com.growtogether.myrestaurant.restaurant.MyRestaurantListFragment;
import com.growtogether.myrestaurant.starter.MainActivity;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostOrderActivity extends AppCompatActivity implements OrderStatusFragment.OnOrderStatusListener {
    protected static int userid;
    protected static int orderid;
    protected static ArrayList<Order> orders;
    public static ArrayList<String> restaurantNames;
    public static String[] status = {"Unviewed","Declined", "Viewed", "Accepted", "Processing", "Ready", "Delivered"};

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order);
        orders = new ArrayList<>();
        restaurantNames = new ArrayList<>();

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

        userid = getIntent().getIntExtra(MainActivity.USERID, 0);

        getSupportFragmentManager().beginTransaction().add(R.id.res_order_fragment_container, new OrderStatusFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    @Override
    public void switchToDetailedList(int i) {
        Log.i("fragment", "switchToDetailedList : "+ i);
        orderid = orders.get(i).getOrderID();

        Bundle bundle = new Bundle();
        bundle.putString("rname", restaurantNames.get(i));
        bundle.putString("date", orders.get(i).getOrderDate());
        bundle.putInt("status", orders.get(i).getOrderStatus());

        OrderDetailsFragment myOrderDetailsFragment = new OrderDetailsFragment();
        myOrderDetailsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.res_order_fragment_container, myOrderDetailsFragment)
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }


}
