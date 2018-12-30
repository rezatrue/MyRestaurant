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
import com.growtogether.myrestaurant.managerestaurant.ManageRestaurantsActivity;
import com.growtogether.myrestaurant.pojo.Order;

import java.util.ArrayList;

public class RestaurantsOrdersListAdapter extends ArrayAdapter<Order> {
    private Context context;
    private ArrayList<Order> list;

    public RestaurantsOrdersListAdapter(@NonNull Context context, ArrayList<Order> list) {
        super(context, R.layout.res_order_single_row, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder{
        TextView orderId, orderDate, orderAddress, orderStatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new RestaurantsOrdersListAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.res_order_single_row, parent, false);

            viewHolder.orderId = convertView.findViewById(R.id.tv_order_id);
            viewHolder.orderDate = convertView.findViewById(R.id.tv_order_date);
            viewHolder.orderAddress = convertView.findViewById(R.id.tv_res_name);
            viewHolder.orderStatus = convertView.findViewById(R.id.tv_order_status);
            convertView.setTag(viewHolder);
            Log.i("fragment", "adepter 0" );
        }
        else{
            Log.i("fragment", "adepter 1" );
            viewHolder = (RestaurantsOrdersListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.orderId.setText(list.get(position).getOrderID()+"");
        viewHolder.orderDate.setText(list.get(position).getOrderDate()+"");
        viewHolder.orderAddress.setText(list.get(position).getDeliverAddress()+"");
        int num = list.get(position).getOrderStatus();
        if(num < -1) viewHolder.orderStatus.setText("Unviewed");
        else viewHolder.orderStatus.setText(ManageRestaurantsActivity.options[num + 1]);

        return convertView;
    }
}
