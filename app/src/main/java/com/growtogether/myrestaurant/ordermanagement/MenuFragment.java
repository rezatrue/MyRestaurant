package com.growtogether.myrestaurant.ordermanagement;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.growtogether.myrestaurant.ApiClient;
import com.growtogether.myrestaurant.ApiInterface;
import com.growtogether.myrestaurant.adapters.ItemAdapter;
import com.growtogether.myrestaurant.MenuResponse;
import com.growtogether.myrestaurant.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    ListView listView;
    TextView textView;
    private ItemAdapter itemAdapter;
    private ArrayList<MenuResponse.Item> items;
    Activity activity;
    private ApiInterface apiInterface;


    public MenuFragment() {
        items = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = view.findViewById(R.id.menuLV);
        textView = view.findViewById(R.id.msgTV);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int restaurant_id = OrderingActivity.restaurantSerialNo;
        if (restaurant_id > 0) {
            String urlString = String.format("item/read.php?restaurant_id=" + restaurant_id);
            Log.i("fragment", " <- request url ->" + urlString);

            Call<MenuResponse> itemsResponseCall = apiInterface.getItemsResponse(urlString);

            itemsResponseCall.enqueue(new Callback<MenuResponse>() {
                @Override
                public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                    Log.i("fragment", " <- Frag 4 ->" + response.code());

                    if (response.code() == 200) {
                        MenuResponse menuResponse = response.body();
                        items = menuResponse.getItems();
                        if(items != null) {
                            itemAdapter = new ItemAdapter(activity, items);
                            listView.setAdapter(itemAdapter);
                        }else {
                            listView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MenuResponse> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            listView.setVisibility(View.GONE);
            textView.setText("Error : Restaurant ID not found ");
            textView.setVisibility(View.VISIBLE);
        }

    }
}
