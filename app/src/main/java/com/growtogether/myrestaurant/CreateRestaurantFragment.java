package com.growtogether.myrestaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRestaurantFragment extends Fragment {

    ImageView imageIV;
    EditText nameET,phoneET, addressET, longitudeET, latitudeET;
    Button btn;

    public CreateRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant, container, false);

        imageIV = view.findViewById(R.id.iv_res_image);
        nameET = view.findViewById(R.id.et_res_name);
        phoneET = view.findViewById(R.id.et_res_phone);
        addressET = view.findViewById(R.id.et_res_address);
        longitudeET = view.findViewById(R.id.et_res_longitude);
        latitudeET = view.findViewById(R.id.et_res_latitude);
        btn = view.findViewById(R.id.btn_res_create);

        return view;
    }

}
