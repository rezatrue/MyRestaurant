package com.growtogether.myrestaurant.managerestaurant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.adapters.ItemAdapter;
import com.growtogether.myrestaurant.pojo.MenuResponse;
import com.growtogether.myrestaurant.pojo.MenuResponse.Item;
import com.growtogether.myrestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {
    ListView listView;
    TextView textView;
    FloatingActionButton fab;

    private ItemAdapter itemAdapter;
    private ArrayList<Item> items;
    Activity activity;
    private ApiInterface apiInterface;

    OnItemListActivityListener onItemListActivityListener;

    public interface OnItemListActivityListener{
        void switchToAddItemFragment();
    }

    public ItemListFragment() {
        items = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_item_list, container, false);
        listView = view.findViewById(R.id.itemListLV);
        textView = view.findViewById(R.id.itemMsgTV);

        fab = view.findViewById(R.id.fab);
        return  view ;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        onItemListActivityListener = (OnItemListActivityListener) activity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(items != null)
            if(items.size() > 0){
                textView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                itemAdapter = new ItemAdapter(activity,items);
                listView.setAdapter(itemAdapter);
            }

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListActivityListener.switchToAddItemFragment();
            }
        });

        getItemData();

    }

    @Override
    public void onResume() {
        super.onResume();
        getItemData();
    }

    public void getItemData(){


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

                    if(items != null)
                        if(items.size() > 0){
                            itemAdapter = new ItemAdapter(activity,items);
                            listView.setAdapter(itemAdapter);
                        }else {
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
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Log.i("Fragment", " error :: "+ t.getMessage() +"");
            }
        });



    }
}
