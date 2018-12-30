package com.growtogether.myrestaurant.restaurant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.OrderDetailsAdapter;
import com.growtogether.myrestaurant.adapters.ResOrderDetailsAdapter;
import com.growtogether.myrestaurant.ordermanagement.PostOrderActivity;
import com.growtogether.myrestaurant.pojo.OrderItem;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResOrderDetailsFragment extends Fragment {
    ListView listView;
    TextView tvCusName, tvDateTime, tvStatus,tvPhone, tvCost;
    private ApiInterface apiInterface;
    Context context;
    private ResOrderDetailsAdapter myResOrderDetailsAdapter;
    public static int totalCost;
    public static String[] status = {"Unviewed","Declined", "Viewed", "Accepted", "Processing", "Ready", "Delivered"};
    private int orderid, userid;

    public ResOrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_order_details, container, false);
        tvCusName = view.findViewById(R.id.cname);
        tvDateTime = view.findViewById(R.id.datetime);
        tvPhone = view.findViewById(R.id.cphone);
        tvStatus = view.findViewById(R.id.status);
        tvCost = view.findViewById(R.id.totalCostTV);
        listView = view.findViewById(R.id.orderLV);

        orderid  = getArguments().getInt("orderid");
        userid = getArguments().getInt("userid");
        int phn = getArguments().getInt("cphone");
        String name = getArguments().getString("cname");
        Log.i("fragment", "Cname : " + name );
        Log.i("fragment", "cphone : " + phn );

        String date = getArguments().getString("date");
        int stat = getArguments().getInt("status");

        int num = stat + 2; // as status start with -2 , -1 & so on

        tvCusName.setText(name);
        tvPhone.setText(phn+"");
        tvDateTime.setText(date);
        tvStatus.setText(status[num]);

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(userid != 0) {
            ApiClient apiClient = new ApiClient();
            apiInterface = apiClient.getApiInterface();
            Call<ArrayList<OrderItem>> orderDetails = apiInterface.getOrderDetails(orderid,userid);
            orderDetails.enqueue(new Callback<ArrayList<OrderItem>>() {
                @Override
                public void onResponse(Call<ArrayList<OrderItem>> call, Response<ArrayList<OrderItem>> response) {
                    if(response.isSuccessful()){
                        ArrayList<OrderItem> list = response.body();

                        if (list.size() > 0) {
                            myResOrderDetailsAdapter = new ResOrderDetailsAdapter(context,list);
                            listView.setAdapter(myResOrderDetailsAdapter);
                            tvCost.setText("Total : "+totalCost + " TK"); // not working
                            tvCost.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<OrderItem>> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


}
