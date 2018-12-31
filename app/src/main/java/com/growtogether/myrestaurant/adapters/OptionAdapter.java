package com.growtogether.myrestaurant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.Option;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private Context context;
    private ArrayList<Option> options;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    // click is developed using the following links
    // codinginflow.com/tutorials/android/recyclerview-cardview/part-4-onitemclicklistener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public OptionAdapter(Context context, ArrayList<Option> options){
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.options_single_row,parent,false);
        OptionViewHolder ovh = new OptionViewHolder(v, mListener);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, final int position) {
        holder.optionIV.setImageResource(options.get(position).getOptionImage());
        holder.optionNameTV.setText(options.get(position).getOptionName());
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder{
        private ImageView optionIV;
        private TextView optionNameTV;
        LinearLayout linearLayout;
        public OptionViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            linearLayout =  itemView.findViewById(R.id.parentLayout);
            optionIV =  itemView.findViewById(R.id.optionImageView);
            optionNameTV = itemView.findViewById(R.id.optionNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }
}
