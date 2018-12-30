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
import com.growtogether.myrestaurant.ordermanagement.PostOrderActivity;
import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.pojo.Restaurant;

import java.util.ArrayList;

public class OrderStatusListAdapter extends ArrayAdapter<Order> {
    private Context context;
    private ArrayList<Order> list;
    public OrderStatusListAdapter(@NonNull Context context, ArrayList<Order> list) {
        super(context, R.layout.order_status_single_row, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder{
        TextView orderId, orderDate, resName, orderStatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new OrderStatusListAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.order_status_single_row, parent, false);

            viewHolder.orderId = convertView.findViewById(R.id.tv_order_id);
            viewHolder.orderDate = convertView.findViewById(R.id.tv_order_date);
            viewHolder.resName = convertView.findViewById(R.id.tv_res_name);
            viewHolder.orderStatus = convertView.findViewById(R.id.tv_order_status);
            convertView.setTag(viewHolder);
            Log.i("fragment", "adepter 0" );
        }
        else{
            Log.i("fragment", "adepter 1" );
            viewHolder = (OrderStatusListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.orderId.setText(list.get(position).getOrderID()+"");
        viewHolder.orderDate.setText(list.get(position).getOrderDate()+"");
        viewHolder.resName.setText(PostOrderActivity.restaurantNames.get(position));
        int num = list.get(position).getOrderStatus() + 2; // as status start with -2 , -1 & so on

        viewHolder.orderStatus.setText(PostOrderActivity.status[num]);

        return convertView;
    }
}
