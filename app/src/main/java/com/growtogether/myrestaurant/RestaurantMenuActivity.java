package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Reza (Iron Man)
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantMenuActivity extends AppCompatActivity {

    private ListView itemListView;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> items;
    private ItemDatabaseOperation itemDatabaseOperation;

    private static final String BASE_URL = "http://10.0.0.2/api/product/";
    private String urlString;
    private MenuServiceApi menuServiceApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        urlString = String.format("read.php");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        menuServiceApi = retrofit.create(MenuServiceApi.class);

        items = new ArrayList<>();
        itemListView = findViewById(R.id.item_list_view);
        //itemDatabaseOperation = new ItemDatabaseOperation(this);
        //items = itemDatabaseOperation.getAllItems();


        Call<MenuResponse> menuResponseCall = menuServiceApi.getMenuResponse(urlString);
        menuResponseCall.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                if(response.code() == 200){
                    MenuResponse menuResponse = response.body();
                    List<MenuResponse.Item> menuItems = menuResponse.getItems();

                    Iterator<MenuResponse.Item> it = menuItems.iterator();
                    while(it.hasNext()){
                        MenuResponse.Item item = it.next();
                        Item item1 = new Item(Integer.parseInt(item.getSerialno()), item.getImageurl(), item.getName(), Integer.parseInt(item.getCategory()), item.getDescription(), Double.parseDouble(item.getPrice()), item.getCreated());
                        items.add(item1);
                    }

                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Toast.makeText(RestaurantMenuActivity.this, "Api call failed.", Toast.LENGTH_LONG).show();
            }
        });

        itemAdapter = new ItemAdapter(this,items);
        itemListView.setAdapter(itemAdapter);

    }


}
