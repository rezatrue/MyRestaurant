package com.growtogether.myrestaurant;
/*
 * Design & Developed by Ali Ahmed Reza (Iron Man)
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("user/register.php")
    Call<UserResponse> getRegisterResponse(@Body User user);

    @GET("user/login.php")
    Call<UserResponse> getLoginResponse(@Query("user_email") String useremail, @Query("user_password") String userpassword);

    @POST("restaurant/create.php")
    Call<RestaurantResponse> getRestaurantResponse(@Body Restaurant restaurant);

    @GET("restaurant/list.php")
    Call<RestaurantListResponse> getRestaurantList();

    @GET("restaurant/list.php")
    Call<RestaurantListResponse> getRestaurantList(@Query("user_id") int id);


}
