package com.growtogether.myrestaurant;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    EditText nameET, emailET, phoneET, passwardET;
    Button registerBtn;
    public final static String TAG = "freagment";
    private ApiInterface apiInterface;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        nameET =  view.findViewById(R.id.etrusername);
        emailET =  view.findViewById(R.id.etremailaddress);
        phoneET =  view.findViewById(R.id.etrphone);
        passwardET =  view.findViewById(R.id.etrpassword);
        registerBtn =  view.findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegistration();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
    }

    public void performRegistration(){
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        String password = passwardET.getText().toString();

        User user = new User();
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserPhone(phone);
        user.setUserPassword(password);

        Log.i(TAG, "User Name : "+user.getUserName());
        Log.i(TAG, "User Email : "+user.getUserEmail());
        Log.i(TAG, "User Phone : "+user.getUserPhone());


        Call<UserResponse> registerResponse = apiInterface.getRegisterResponse(user);

        registerResponse.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                Log.i(TAG, userResponse.getResponse());
                Log.i(TAG, userResponse.getName());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.i(TAG, "error in connection : " + t.getMessage());
            }
        });

    }

}
