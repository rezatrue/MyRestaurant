package com.growtogether.myrestaurant;


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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.growtogether.myrestaurant.MenuResponse.Item;
/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {

    ListView listView;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> items;
    Activity activity;
    private ApiInterface apiInterface;


    public ItemListFragment() {
        // Required empty public constructor
        items = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_item_list, container, false);
        listView = view.findViewById(R.id.itemListLV);
        return  view ;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
        // need to add retaurant_id with this string
        int restaurant_id = ManageRestaurantsActivity.restaurantSerialNo;
        String urlString = String.format("item/read.php");
        if (restaurant_id > 0)
            urlString = String.format("item/read.php?restaurant_id="+ restaurant_id);

        Call<MenuResponse> itemsResponseCall = apiInterface.getItemsResponse(urlString);

        itemsResponseCall.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                Log.i("fragment"," <- Frag 4 ->" + response.code());

                if(response.code() == 200){
                    MenuResponse menuResponse = response.body();
                    items  = menuResponse.getItems();
                    itemAdapter = new ItemAdapter(activity,items);
                    listView.setAdapter(itemAdapter);
                }

            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
