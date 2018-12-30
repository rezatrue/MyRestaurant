package com.growtogether.myrestaurant.ordermanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.OrderStatusListAdapter;
import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.pojo.Restaurant;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderStatusFragment extends Fragment {
    ListView listView;
    TextView textView;
    private ApiInterface apiInterface;
    Context context;
    private OrderStatusListAdapter myOrderStatusAdapter;
    OnOrderStatusListener onOrderStatusListener;


    public interface OnOrderStatusListener{
        void switchToDetailedList(int i);
    }

    public OrderStatusFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        listView = view.findViewById(R.id.orderLV);
        textView = view.findViewById(R.id.msgTV);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("fragment", "line : "+ i);
                onOrderStatusListener.switchToDetailedList(i);
            }
        });


        if(PostOrderActivity.userid != 0) {

            Call<ArrayList<Order>> listCall = apiInterface.getMyOrderList(PostOrderActivity.userid);
            listCall.enqueue(new Callback<ArrayList<Order>>() {
                @Override
                public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                    Log.i("fragment", "response.code : "+ response.code());

                    if(response.code() == 200) {
                        PostOrderActivity.orders = response.body();
                        int list[] = new int[PostOrderActivity.orders.size()];
                        //ArrayList<Integer> slList = new ArrayList<>();
                        for(int i = 0; i < list.length; i++){
                            Log.i("fragment", "loop : "+ i);
                            list[i] = PostOrderActivity.orders.get(i).getRestaurantSerialNo();
                        }
                        getResNames(list);


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                    Log.i("fragment", "t : "+ t.getMessage());
                    if(PostOrderActivity.userid <= 0 || PostOrderActivity.orders.size() <= 0) {
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }

    }


    public void getResNames(int list[]){

        Call<ArrayList<Restaurant>> arrayListCall = apiInterface.getRestaurantName(list);
        arrayListCall.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if(response.isSuccessful()){
                    ArrayList<Restaurant> resNames = response.body();
                    for(int i = 0 ; i < resNames.size() ; i++){
                        PostOrderActivity.restaurantNames.add(resNames.get(i).getRestaurantName());
                        Log.i("fragment", "get name  : "+ i +" : "+ resNames.get(i).getRestaurantName());
                    }

                    if (PostOrderActivity.orders.size() > 0) {
                        myOrderStatusAdapter = new OrderStatusListAdapter(context,PostOrderActivity.orders);
                        listView.setAdapter(myOrderStatusAdapter);
                    }

                    if(PostOrderActivity.userid <= 0 || PostOrderActivity.orders.size() <= 0) {
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.i("fragment", "get name failed : "+ t.getMessage());

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        onOrderStatusListener = (OnOrderStatusListener) context;
        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
    }


}
