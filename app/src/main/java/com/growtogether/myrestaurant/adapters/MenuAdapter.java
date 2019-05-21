package com.growtogether.myrestaurant.adapters;
/*
 * Design & Developed by Ali Ahmed Reza (Iron Man)
 */

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.pojo.MenuResponse.Item;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.ordermanagement.OrderingActivity;
import com.growtogether.myrestaurant.pojo.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<Item>{
    private Context context;
    private ArrayList<Item> items;
    OnAdapterItemListener onAdapterItemListener;
    ViewHolder viewHolder;

    public MenuAdapter(@NonNull Context context, ArrayList<Item> items) {
        super(context, R.layout.menu_single_row, items);
        this.context = context;
        this.items = items;
        onAdapterItemListener = (OnAdapterItemListener) context;
    }

    public interface OnAdapterItemListener{
        public void addItemToOrderList(Item item);
        public int addItemCountOnList(Item item);
        public int decreaseItemCountOnList(Item item);
    }


    class ViewHolder{
        ImageView itemImageview;
        TextView itemName;
        TextView itemPrice;
        TextView itemDescription;
        ImageButton add, minus;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.menu_single_row, parent, false);
            viewHolder.itemName = convertView.findViewById(R.id.item_name);
            viewHolder.itemImageview = convertView.findViewById(R.id.item_image);
            viewHolder.itemPrice = convertView.findViewById(R.id.item_price);
            viewHolder.itemDescription = convertView.findViewById(R.id.item_description);
            viewHolder.add = convertView.findViewById(R.id.ibtn_add);
            viewHolder.minus = convertView.findViewById(R.id.ibtn_minus);
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

        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = isItNewItem(items.get(position).getSerialno());
                if(status) {
                    onAdapterItemListener.addItemToOrderList(items.get(position));
                    showToast(1);
                }
                else{
                    int num = onAdapterItemListener.addItemCountOnList(items.get(position));
                    showToast(num);
                }
                //Log.i("fragment", " <- Item selected  . -> " + viewHolder.itemName.getText());
                Log.i("fragment", " <- Item selected  . -> " + items.get(position).getName() + " - "  + items.get(position).getSerialno());

            }
        });

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = isItNewItem(items.get(position).getSerialno());
                if(!status){
                    int num = onAdapterItemListener.decreaseItemCountOnList(items.get(position));
                    showToast(num);
                }
            }
        });

        return convertView;
    }

    // making shirt toast
    private void showToast(int number){
        final Toast toast = Toast.makeText(context, String.valueOf(number), Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);


    }

    private boolean isItNewItem(int serial){
        ArrayList<OrderItem> selitems = OrderingActivity.selectedItems;
        int lsz = selitems.size();
        if(lsz > 0){
            for(int i = 0; i < lsz ; i++){
                if(selitems.get(i).getItemSerialNo() == serial) return false;
            }
        }
        return true;
    }

}
