package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Reza (Iron Man)
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    private ListView itemListView;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> items;
    private ItemDatabaseOperation itemDatabaseOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        items = new ArrayList<>();
        itemListView = findViewById(R.id.item_list_view);
        itemDatabaseOperation = new ItemDatabaseOperation(this);
        items = itemDatabaseOperation.getAllItems();


        itemAdapter = new ItemAdapter(this,items);
        itemListView.setAdapter(itemAdapter);
    }
}
