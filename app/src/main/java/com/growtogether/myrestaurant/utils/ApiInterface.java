package com.growtogether.myrestaurant.utils;
/*
 * Design & Developed by Ali Ahmed Reza (Iron Man)
 */
import com.growtogether.myrestaurant.pojo.Item;
import com.growtogether.myrestaurant.pojo.ItemAddResponse;
import com.growtogether.myrestaurant.pojo.MenuResponse;
import com.growtogether.myrestaurant.pojo.OrderItem;
import com.growtogether.myrestaurant.pojo.User;
import com.growtogether.myrestaurant.pojo.UserResponse;
import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.pojo.OrderResponse;
import com.growtogether.myrestaurant.pojo.Restaurant;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse;
import com.growtogether.myrestaurant.pojo.RestaurantResponse;
import com.growtogether.myrestaurant.pojo.Type;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("user/register.php")
    Call<UserResponse> getRegisterResponse(@Body User user);

    @GET("user/login.php")
    Call<UserResponse> getLoginResponse(@Query("user_email") String useremail, @Query("user_password") String userpassword);

    @GET("user/search.php")
    Call<User> getUserInfo(@Query("user_id") int id);


    @POST("restaurant/resnames.php")
    Call<ArrayList<Restaurant>> getRestaurantName(@Body int list[]);


    @POST("restaurant/create.php")
    Call<RestaurantResponse> getRestaurantResponse(@Body Restaurant restaurant);

    @POST("restaurant/edit.php")
    Call<RestaurantResponse> getEditRestaurantResponse(@Body Restaurant restaurant);

    @GET("restaurant/list.php")
    Call<RestaurantListResponse> getAllRestaurantList();

//    @GET("restaurant/list.php")
//    Call<RestaurantListResponse> getTypeRestaurantList(@Query("res_type") String type);

    @GET("restaurant/list.php")
    Call<RestaurantListResponse> getTypeRestaurantList(@Query("res_type") String type, @Query("not_user_id") int id);

    @GET("restaurant/list.php")
    Call<RestaurantListResponse> getRestaurantList(@Query("user_id") int id);

    @GET()
    Call<MenuResponse> getItemsResponse(@Url String stringUrl);

    @POST("item/add.php")
    Call<ItemAddResponse> addItem (@Body Item item);

    @POST("order/create.php")
    Call<OrderResponse> createOrderResponse(@Body Order order);

    @GET("order/list.php")
    Call<ArrayList<Order>> getOrderList(@Query("restaurant_id") int id);

    @GET("order/itemlist.php")
    Call<ArrayList<OrderItem>> getOrderDetails(@Query("order_id") int oid, @Query("user_id") int uid);

    @GET("order/list.php")
    Call<ArrayList<Order>> getMyOrderList(@Query("user_id") int id);

    @GET("order/update.php")
    Call<Integer> setStatus(@Query("order_id") int oid, @Query("user_id") int uid, @Query("status") int stat);

    @GET("type/read.php")
    Call<ArrayList<Type>> getResTypeList();


}
