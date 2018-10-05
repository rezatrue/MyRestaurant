package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Ahmed Reza (Iron Man)
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface UserServiceApi {

    @GET("login.php")
    Call<User> getLoginResponse(@Query("email") String uemail, @Query("password") String upassword);



}
