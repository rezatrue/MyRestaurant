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

public interface ApiInterface {

    @POST("user/register.php")
    Call<UserResponse> getRegisterResponse(@Body User user);

    @GET("user/login.php")
    Call<UserResponse> getLoginResponse(@Query("user_email") String useremail, @Query("user_password") String userpassword);



}
