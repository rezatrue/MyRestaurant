package com.growtogether.myrestaurant.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //public static final String BASE_URL = "http://10.100.100.254/api/";
    public static final String BASE_URL = "http://192.168.0.105/api/";
    //public static final String BASE_URL = "http://192.168.0.100/api/";

    private static Retrofit retrofit = null;
    private static ApiInterface apiInterface = null;

    private static Retrofit getApiClient(){
        if(retrofit == null){
            Gson gson = new GsonBuilder().setLenient().create();
            OkHttpClient client = new OkHttpClient();
            retrofit =  new Retrofit.Builder().baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }

    public static ApiInterface getApiInterface(){
        if(apiInterface == null){
            apiInterface = getApiClient().create(ApiInterface.class);
        }
        return apiInterface;
    }

}
