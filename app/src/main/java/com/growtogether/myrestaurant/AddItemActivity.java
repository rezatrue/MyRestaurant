package com.growtogether.myrestaurant;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    EditText item_name, item_price, item_description;
    Button btn_add;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        item_name = findViewById(R.id.et_food_item);
        item_price = findViewById(R.id.et_food_price);
        item_description = findViewById(R.id.et_food_description);
        btn_add = findViewById(R.id.btn_add);
        mImageView = findViewById(R.id.imageView);

        btn_add.setOnClickListener(this);
        item_description.setOnFocusChangeListener(this);
        mImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.btn_add:
                Log.i("data", item_name.getText().toString());
                Log.i("data", item_price.getText().toString());
                Log.i("data", item_description.getText().toString());
                break;

            case R.id.imageView:
                Toast.makeText(getApplicationContext(), "Image icon clicked" , Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            //Toast.makeText(getApplicationContext(), hasFocus + " " , Toast.LENGTH_LONG).show();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
