package com.growtogether.myrestaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MenuServiceApi {
    @GET()
    Call<MenuResponse> getMenuResponse(@Url String stringUrl);
}
