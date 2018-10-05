package com.growtogether.myrestaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    EditText nameET, phoneET, passwardET;
    Button registerBtn;



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        nameET =  view.findViewById(R.id.etusername);
        phoneET =  view.findViewById(R.id.etrphone);
        passwardET =  view.findViewById(R.id.etrpassword);
        registerBtn =  view.findViewById(R.id.registerBtn);

        return view;
    }

}
