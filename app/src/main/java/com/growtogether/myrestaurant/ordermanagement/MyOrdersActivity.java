package com.growtogether.myrestaurant.ordermanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.RestaurantsOrdersListAdapter;
import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.starter.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// replaced by PostOrderActivity class
// replaced by PostOrderActivity class
// replaced by PostOrderActivity class
// replaced by PostOrderActivity class
// replaced by PostOrderActivity class


public class MyOrdersActivity extends AppCompatActivity { // replaced by PostOrderActivity class
    private int userid;
    ListView listView;
    TextView textView;
    private ApiInterface apiInterface;
    private RestaurantsOrdersListAdapter myOrderAdapter; // using same adapter for my & restaurant list
    private ArrayList<Order> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        listView = findViewById(R.id.orderLV);
        textView = findViewById(R.id.msgTV);

        orders = new ArrayList<>();
        userid = getIntent().getIntExtra(MainActivity.USERID, 0);
        Log.i("fragment", "userid : "+ userid);

        if(userid != 0) {
            ApiClient apiClient = new ApiClient();
            apiInterface = apiClient.getApiInterface();

            Call<ArrayList<Order>> listCall = apiInterface.getMyOrderList(userid);
            listCall.enqueue(new Callback<ArrayList<Order>>() {
                @Override
                public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                    Log.i("fragment", "response.code : "+ response.code());

                    if(response.code() == 200) {
                        orders = response.body();
                        Log.i("fragment", "orders : "+ orders.size());

                        if (orders.size() > 0) {
                            myOrderAdapter = new RestaurantsOrdersListAdapter(MyOrdersActivity.this,orders);
                            listView.setAdapter(myOrderAdapter);
                        }

                        if(userid <= 0 || orders.size() <= 0) {
                            listView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                    Log.i("fragment", "t : "+ t.getMessage());
                    if(userid <= 0 || orders.size() <= 0) {
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }



    }
}
