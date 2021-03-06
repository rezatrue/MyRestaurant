package com.growtogether.myrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    EditText userName, userEmial, userPhone, userAddress;
    ImageView userImage;
    Button btnSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        userName = findViewById(R.id.et_item_name);
        userEmial = findViewById(R.id.et_res_longitude);
        userPhone = findViewById(R.id.et_item_price);
        userAddress = findViewById(R.id.et_item_description);
        userImage = findViewById(R.id.iv_item_image);
        btnSubmit = findViewById(R.id.btn_register);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
