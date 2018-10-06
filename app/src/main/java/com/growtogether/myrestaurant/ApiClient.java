package com.growtogether.myrestaurant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //private static final String BASE_URL = "http://192.168.40.215/api/";
    private static final String BASE_URL = "http://192.168.0.101/api/";
    private static Retrofit retrofit = null;
    private static ApiInterface apiInterface = null;

    private static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit =  new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
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
