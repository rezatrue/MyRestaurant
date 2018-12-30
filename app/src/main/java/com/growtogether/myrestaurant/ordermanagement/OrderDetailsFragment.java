package com.growtogether.myrestaurant.ordermanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.OrderDetailsAdapter;
import com.growtogether.myrestaurant.pojo.OrderItem;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsFragment extends Fragment {
    ListView listView;
    TextView tvName, tvDateTime, tvStatus, tvCost;
    private ApiInterface apiInterface;
    Context context;
    private OrderDetailsAdapter myOrderDetailsAdapter;
    public static int totalCost;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        tvName = view.findViewById(R.id.cname);
        tvDateTime = view.findViewById(R.id.datetime);
        tvStatus = view.findViewById(R.id.status);
        tvCost = view.findViewById(R.id.totalCostTV);
        listView = view.findViewById(R.id.orderLV);

        String name = getArguments().getString("rname");
        String date = getArguments().getString("date");
        int stat = getArguments().getInt("status");

        int num = stat + 2; // as status start with -2 , -1 & so on

        tvName.setText(name);
        tvDateTime.setText(date);
        tvStatus.setText(PostOrderActivity.status[num]);

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(PostOrderActivity.userid != 0) {
            ApiClient apiClient = new ApiClient();
            apiInterface = apiClient.getApiInterface();
            Call<ArrayList<OrderItem>> orderDetails = apiInterface.getOrderDetails(PostOrderActivity.orderid,PostOrderActivity.userid);
            orderDetails.enqueue(new Callback<ArrayList<OrderItem>>() {
                @Override
                public void onResponse(Call<ArrayList<OrderItem>> call, Response<ArrayList<OrderItem>> response) {
                    if(response.isSuccessful()){
                        ArrayList<OrderItem> list = response.body();

                        if (list.size() > 0) {
                            myOrderDetailsAdapter = new OrderDetailsAdapter(context,list);
                            listView.setAdapter(myOrderDetailsAdapter);
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
