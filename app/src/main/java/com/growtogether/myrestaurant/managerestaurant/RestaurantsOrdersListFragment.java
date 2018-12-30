package com.growtogether.myrestaurant.managerestaurant;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.growtogether.myrestaurant.restaurant.RestaurantActivity;
import com.growtogether.myrestaurant.starter.MainActivity;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.RestaurantsOrdersListAdapter;
import com.growtogether.myrestaurant.pojo.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsOrdersListFragment extends Fragment {
    ListView listView;
    TextView textView;
    static Activity activity;
    private ApiInterface apiInterface;
    RestaurantsOrdersListAdapter ordersListAdapter;

    OnRestaurantsOrdersListListener onRestaurantsOrdersListListener;


    public interface OnRestaurantsOrdersListListener{
        void switchToOrderDetailList(int i, int value);
        void changeOrderStatus(int i, int value);
    }

    public RestaurantsOrdersListFragment() {
        // Required empty public constructor
        ManageRestaurantsActivity.list = new ArrayList<>();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(ManageRestaurantsActivity.list != null)
            if(ManageRestaurantsActivity.list.size() > 0){
                textView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                ordersListAdapter = new RestaurantsOrdersListAdapter(activity,ManageRestaurantsActivity.list);
                listView.setAdapter(ordersListAdapter);
            }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("fragment", ManageRestaurantsActivity.list.size()+"<<-");
        View view =  inflater.inflate(R.layout.fragment_orders_list, container, false);
        listView = view.findViewById(R.id.orderListLV);
        textView = view.findViewById(R.id.orderMsgTV);
        return  view ;

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        onRestaurantsOrdersListListener = (OnRestaurantsOrdersListListener) context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getOrdersData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("fragment", ManageRestaurantsActivity.list.get(i).getOrderID()+"<<-");
                dialogMenu(i);
            }
        });


    }

    public void getOrdersData(){

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

        int restaurant_id = ManageRestaurantsActivity.restaurantSerialNo;
        if (restaurant_id > 0);
        Log.i("OrdersListFragment", restaurant_id +" : restaurant_id");

        Call<ArrayList<Order>> arrayListCall = apiInterface.getOrderList(restaurant_id);

        arrayListCall.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                Log.i("OrdersListFragment", response.code() +"");

                if(response.code() == 200) {

                    ManageRestaurantsActivity.list.addAll(response.body());

                    if (ManageRestaurantsActivity.list != null)
                        if (ManageRestaurantsActivity.list.size() > 0) {
                            ordersListAdapter = new RestaurantsOrdersListAdapter(activity,ManageRestaurantsActivity.list);
                            listView.setAdapter(ordersListAdapter);

                        } else {
                            listView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }
                    else {
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Log.i("OrdersListFragment", " error :: "+ t.getMessage() +"");

            }
        });


    }


    public void dialogMenu(final int i){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(ManageRestaurantsActivity.options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("fragment", "which : "+ which);
                if(which == 1){
                    onRestaurantsOrdersListListener.switchToOrderDetailList(i, (which - 1));


                }else {
                    onRestaurantsOrdersListListener.changeOrderStatus(i, (which - 1));
                    //getFragmentManager().beginTransaction().detach(RestaurantsOrdersListFragment.this).attach(RestaurantsOrdersListFragment.this).commit();
                }
            }
        });
        builder.show();
    }
}
