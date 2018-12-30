package com.growtogether.myrestaurant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<RestaurantListResponse.Restaurant> {
    private Context context;
    private ArrayList<RestaurantListResponse.Restaurant> restaurants;

    public RestaurantAdapter(@NonNull Context context, ArrayList<RestaurantListResponse.Restaurant> restaurants) {
        super(context, R.layout.restaurant_single_row, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }


    class ViewHolder{
        ImageView restaurantImageview;
        TextView restaurantName;
        TextView restaurantAddress;
        TextView restaurantPhone;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("fragment", "getViewed res called " + position);

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.restaurant_single_row, parent, false);

            holder.restaurantImageview =  convertView.findViewById(R.id.iv_res_list);
            holder.restaurantName =  convertView.findViewById(R.id.tv_res_list_name);
            holder.restaurantAddress =  convertView.findViewById(R.id.tv_res_list_address);
            holder.restaurantPhone =  convertView.findViewById(R.id.tv_res_list_phone);
            convertView.setTag(holder);
            Log.i("fragment", "adepter 0" );
        }else {
            Log.i("fragment", "adepter 1" );
            holder = (ViewHolder) convertView.getTag();
        }

        /*
        String imagePath = ApiClient.BASE_URL + restaurants.get(position).getImageurl();
        Log.i("fragment", "URL : " + imagePath );
        Uri uri = Uri.fromFile(new File(imagePath));
        Picasso.get().load(imagePath).into(holder.restaurantImageview);

        */

        String urltxt = ApiClient.BASE_URL + restaurants.get(position).getImageurl();
        Log.e("fragment", urltxt + " <- URL ->");
        Picasso.get().load(urltxt).into(holder.restaurantImageview);

        holder.restaurantName.setText(restaurants.get(position).getName());
        holder.restaurantAddress.setText(restaurants.get(position).getAddress());
        holder.restaurantPhone.setText(restaurants.get(position).getPhone());

        return convertView;

    }
}
