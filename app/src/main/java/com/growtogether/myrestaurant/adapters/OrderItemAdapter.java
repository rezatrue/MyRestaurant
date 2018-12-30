package com.growtogether.myrestaurant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> {
    private Context context;
    private ArrayList<OrderItem> orderItem;

    public OrderItemAdapter(@NonNull Context context, ArrayList<OrderItem> orderItem) {
        super(context, R.layout.order_item_single_row, orderItem);
        this.context = context;
        this.orderItem = orderItem;
    }

    class ViewHolder{
        TextView itemNameTV, itemCostTV, itemQuantityTV;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.order_item_single_row, parent, false);

            viewHolder.itemNameTV = convertView.findViewById(R.id.oiname);
            viewHolder.itemCostTV = convertView.findViewById(R.id.oicost);
            viewHolder.itemQuantityTV = convertView.findViewById(R.id.oicount);

            convertView.setTag(viewHolder);
            Log.i("fragment", "adepter 0" );
        }
        else{
            Log.i("fragment", "adepter 1" );
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.i("fragment", "adepter setting" );

        viewHolder.itemNameTV.setText(orderItem.get(position).getItemName());
        int qunatity = orderItem.get(position).getItemQuantity();
        viewHolder.itemQuantityTV.setText(String.valueOf(qunatity));
        double price = orderItem.get(position).getItemPrice();
        double total = qunatity * price;
        viewHolder.itemCostTV.setText(String.valueOf(total) + " TK");

        return convertView;
    }
}
