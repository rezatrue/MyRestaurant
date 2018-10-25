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


/**
 * Design & developed by ALi Reza (Iron Man)
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText userEmailET, passwordET;
    Button submitBtn;
    TextView registerTV;

    private ApiInterface apiInterface;

    public final String TAG = "fragment";

    OnLoginFromActivityListener loginFromActivityListener;

    public interface OnLoginFromActivityListener{
        void performRegister();
        void performLogin(String name, int id);
    }


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userEmailET = view.findViewById(R.id.etluseremail);
        passwordET =  view.findViewById(R.id.etluserpassword);
        submitBtn =  view.findViewById(R.id.btnlsubmit);
        registerTV = view.findViewById(R.id.tvlregister);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFromActivityListener = (OnLoginFromActivityListener) activity;
    }


    public void performLogin(){

        String uEmail = userEmailET.getText().toString();
        String uPassword = passwordET.getText().toString();

        Toast.makeText(getContext(),"user : " + uEmail + " pas : " + uPassword, Toast.LENGTH_LONG).show();

        Call<UserResponse> userResponseCall = apiInterface.getLoginResponse(uEmail, uPassword);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                Log.i(TAG, userResponse.getResponse());
                Log.i(TAG, userResponse.getName());
                if(userResponse.getResponse().equals("ok")){
                    userEmailET.setText(""); passwordET.setText("");
                    String name = userResponse.getName();
                    int id = userResponse.getUserSerialNo();
                    Toast.makeText(getContext(), " welcome " + name + "(" + id +")", Toast.LENGTH_LONG).show();

                    loginFromActivityListener.performLogin(name, id);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.i(TAG, "error in connection : " + t.getMessage());
            }
        });
    }



}
