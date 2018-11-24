package com.growtogether.myrestaurant.adapters;
/*
 * Design & Developed by Ali Ahmed Reza (Iron Man)
 */

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

import com.growtogether.myrestaurant.ApiClient;
import com.growtogether.myrestaurant.MenuResponse.Item;
import com.growtogether.myrestaurant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item>{
    private Context context;
    private ArrayList<Item> items;

    public ItemAdapter(@NonNull Context context, ArrayList<Item> items) {
        super(context, R.layout.item_single_row, items);
        this.context = context;
        this.items = items;
    }

    class ViewHolder{
        ImageView itemImageview;
        TextView itemName;
        TextView itemPrice;
        TextView itemDescription;
        Button btn;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_single_row, parent, false);
            viewHolder.itemName = convertView.findViewById(R.id.item_name);
            viewHolder.itemImageview = convertView.findViewById(R.id.item_image);
            viewHolder.itemPrice = convertView.findViewById(R.id.item_price);
            viewHolder.itemDescription = convertView.findViewById(R.id.item_description);
            viewHolder.btn = convertView.findViewById(R.id.btn_item_edit);
            convertView.setTag(viewHolder);
            Log.i("fragment", "adepter 0" );

        }
        else{
            Log.i("fragment", "adepter 1" );
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String urltxt = ApiClient.BASE_URL + items.get(position).getItemImageUrl();
        Log.e("fragment", urltxt + " <- URL ->");
        Picasso.get().load(urltxt).into(viewHolder.itemImageview);

        Log.e("fragment","Name "+ items.get(position).getName() + "- test ->");

        viewHolder.itemName.setText(items.get(position).getName());
        viewHolder.itemPrice.setText(items.get(position).getPrice()+"");
        viewHolder.itemDescription.setText(items.get(position).getDescription());

        return convertView;
    }

}
