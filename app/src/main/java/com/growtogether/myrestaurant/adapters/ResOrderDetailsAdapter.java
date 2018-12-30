package com.growtogether.myrestaurant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.ordermanagement.OrderDetailsFragment;
import com.growtogether.myrestaurant.pojo.OrderItem;

import java.util.ArrayList;

public class ResOrderDetailsAdapter extends ArrayAdapter<OrderItem> {
    private Context context;
    private ArrayList<OrderItem> orderItem;

    public ResOrderDetailsAdapter(@NonNull Context context, ArrayList<OrderItem> orderItem) {
        super(context, R.layout.order_details_single_row, orderItem);
        this.context = context;
        this.orderItem = orderItem;
        OrderDetailsFragment.totalCost = 0;
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
            convertView = layoutInflater.inflate(R.layout.order_details_single_row, parent, false);

            viewHolder.itemNameTV = convertView.findViewById(R.id.rname);
            viewHolder.itemCostTV = convertView.findViewById(R.id.tcost);
            viewHolder.itemQuantityTV = convertView.findViewById(R.id.quantity);

            convertView.setTag(viewHolder);
            Log.i("fragment", "adepter 0" );
        }
        else{
            Log.i("fragment", "adepter 1" );
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.i("fragment", "adepter setting" );

        viewHolder.itemNameTV.setText(orderItem.get(position).getItemName());
        int quty = orderItem.get(position).getItemQuantity();
        double cost = orderItem.get(position).getItemPrice();
        double total = quty * cost;
        OrderDetailsFragment.totalCost += total;
        viewHolder.itemQuantityTV.setText(String.valueOf(quty));
        viewHolder.itemCostTV.setText(String.valueOf(total));

        return convertView;
    }
}
