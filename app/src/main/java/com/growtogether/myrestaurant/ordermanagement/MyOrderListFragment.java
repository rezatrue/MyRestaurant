package com.growtogether.myrestaurant.ordermanagement;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.OrderItemAdapter;
import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.pojo.OrderItem;
import com.growtogether.myrestaurant.pojo.OrderResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderListFragment extends Fragment {
    ListView listView;
    TextView textView;
    Activity activity;
    private OrderItemAdapter orderItemAdapter;
    private ApiInterface apiInterface;
    private ArrayList<OrderItem> selectedItems;
    FloatingActionButton fab;
    View view;

    public MyOrderListFragment() {
        // Required empty public constructor
        Log.i("frag", "MyOrderListFragment : ");
        selectedItems = new ArrayList<>();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(selectedItems!=null)
        Log.i("frag", "setUserVisibleHint : "+ selectedItems.size());

        //selectedItems = OrderingActivity.selectedItems;

        if(selectedItems.size() > 0){
            Log.i("frag", "setUserVisibleHint -:- "+ selectedItems.size());
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            orderItemAdapter = new OrderItemAdapter(activity, selectedItems);
            listView.setAdapter(orderItemAdapter);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order_list, container, false);
        listView = view.findViewById(R.id.menuLV);
        textView = view.findViewById(R.id.msgTV);

        fab = view.findViewById(R.id.fab);
        fab.setImageBitmap(textAsBitmap("Order Now", 40, Color.WHITE));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view ; // for Snackbar Msg
        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

        selectedItems = OrderingActivity.selectedItems;
        if(selectedItems.size() > 0){
            orderItemAdapter = new OrderItemAdapter(activity, selectedItems);
            listView.setAdapter(orderItemAdapter);
        }else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedItems.size() > 0)
                    addOrder();


            }
        });

    }

    public void snackbarMsg(String msg){
        if(msg == "success")
            Snackbar.make(view, "You Order Has been submitted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        if(msg == "failed")
            Snackbar.make(view, "Failed to submit Order", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
    }
    //public String msg = "failed";
    public void addOrder(){

        Order order = new Order();
        order.setOrderItems(selectedItems);
        order.setRestaurantSerialNo(OrderingActivity.restaurantSerialNo);
        order.setUserSerialNo(OrderingActivity.userSerialNumber);
        order.setOrderStatus(-2); // not yet decline & view ........
        order.setDeliverAddress("AB");


        Call<OrderResponse> orderResponseCall = apiInterface.createOrderResponse(order);
        orderResponseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                Log.i("MyOrder", response.message());
                Log.i("MyOrder", response.body().response.toString());
                if(response.code()==200)
                    if(response.body().response.toString().equalsIgnoreCase("ok")){
                        snackbarMsg("success");
                        selectedItems = new ArrayList<>();
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);

                    }
                    else
                        snackbarMsg("failed");

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.i("MyOrder", t.getMessage());
                snackbarMsg("failed");
            }
        });

    }


    //method to convert your text to image
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
