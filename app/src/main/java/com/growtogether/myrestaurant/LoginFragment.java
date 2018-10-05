package com.growtogether.myrestaurant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText userEmailET, passwordET;
    Button submitBtn;
    TextView registerTV;

    private static final String BASE_URL = "http://192.168.0.104/api/user/";
    private UserServiceApi userServiceApi;


    public final String TAG = "fragment";

    OnLoginFromActivityListener loginFromActivityListener;

    public interface OnLoginFromActivityListener{
        public void performRegister();
        public void performLogin(String name);
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        userEmailET =  view.findViewById(R.id.etusername);
        passwordET =  view.findViewById(R.id.etpassword);
        submitBtn =  view.findViewById(R.id.submitBtn);
        registerTV =  view.findViewById(R.id.tvregister);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        userServiceApi = retrofit.create(UserServiceApi.class);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"submit button", Toast.LENGTH_LONG).show();
                performLogin();
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"register button", Toast.LENGTH_LONG).show();
                loginFromActivityListener.performRegister();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFromActivityListener = (OnLoginFromActivityListener) activity;
    }

    public void performLogin(){
        String email = userEmailET.getText().toString();
        String password = passwordET.getText().toString();


//        Log.i(TAG, "Login in success");
//        loginFromActivityListener.performLogin("Hello");
//        Log.i(TAG, "Login in failed : " + t.getMessage());
        Call<User> userCall = userServiceApi.getLoginResponse(email, password);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "Login in success");
                loginFromActivityListener.performLogin("Hello");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, "Login in failed : " + t.getMessage());
            }
        });
    }

}
