package com.growtogether.myrestaurant;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class ItemAdapter extends ArrayAdapter<Item>{
    private Context context;
    private ArrayList<Item> items;

    public ItemAdapter(@NonNull Context context, ArrayList<Item> items) {
        super(context,R.layout.item_single_row ,items);
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
            convertView = layoutInflater.inflate(R.layout.item_single_row,parent, false);
            viewHolder.itemName = convertView.findViewById(R.id.name);
            viewHolder.itemImageview = convertView.findViewById(R.id.image);
            viewHolder.itemPrice = convertView.findViewById(R.id.price);
            viewHolder.itemDescription = convertView.findViewById(R.id.description);
            viewHolder.btn = convertView.findViewById(R.id.btn_edit);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //String baseUrl = "http://192.168.0.104/api/";
        //String baseUrl = "http://192.168.40.215/api/";
        String baseUrl = "http://192.168.0.100/api/";
        String urltxt = baseUrl + items.get(position).getItemImageUrl();
        Log.e("data", urltxt + " <- URL ->");
        Picasso.get().load(urltxt).into(viewHolder.itemImageview);

        // this section needed for add list
        /*
        try {
            viewHolder.itemImageview.setImageBitmap(items.get(position).getItemBitmapImage());
        }catch(Exception e ){
            e.getMessage();
        }
        */
        viewHolder.itemName.setText(items.get(position).getItemName());
        viewHolder.itemPrice.setText(items.get(position).getItemPrice()+"");
        viewHolder.itemDescription.setText(items.get(position).getItemDescription());

        return convertView;
    }
}
