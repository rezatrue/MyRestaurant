package com.growtogether.myrestaurant.starter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.pojo.User;
import com.growtogether.myrestaurant.pojo.UserResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        phoneET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(R.id.etrphone == view.getId() && !b){
                    String phoneText = phoneET.getText().toString();
                    if(!(phoneText.length() == 11) || !phoneText.startsWith("01")) {
                        Toast.makeText(getContext(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        phoneET.setText("");
                    }
                }

            }
        });

        emailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(R.id.etremailaddress == view.getId() && !b){
                    String phoneText = emailET.getText().toString();
                    if(!isEmailValid(phoneText)) {
                        Toast.makeText(getContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                        emailET.setText("");
                    }
                }

            }
        });
    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void performRegistration(){

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        String password = passwardET.getText().toString();

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()){
            Toast.makeText(getContext(), "Insufficient information", Toast.LENGTH_SHORT).show();
            return;
        }

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
                if(userResponse.getResponse().equals("ok")){
                    nameET.setText(""); emailET.setText(""); phoneET.setText("");   passwardET.setText("");
                    String name = userResponse.getName();
                    Toast.makeText(getContext(), name + "'s registration is successfully completed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.i(TAG, "error in connection : " + t.getMessage());
            }
        });

    }

}
