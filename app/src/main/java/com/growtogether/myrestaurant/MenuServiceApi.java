package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Ahmed Reza (Iron Man)
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MenuServiceApi {
    @GET()
    Call<MenuResponse> getMenuResponse(@Url String stringUrl);

    @POST("create.php")
    Call<MenuResponse> createItem (@Body SendItem sendItem);



}
