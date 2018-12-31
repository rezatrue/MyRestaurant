package com.growtogether.myrestaurant.restaurant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.growtogether.myrestaurant.pojo.Type;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.RestaurantAdapter;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse;
import com.growtogether.myrestaurant.pojo.RestaurantListResponse.Restaurant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllRestaurantListFragment extends Fragment{
    Spinner spinner;
    Button button;
    TextView textView;
    private ListView restaurantListView;
    private RestaurantAdapter restaurantAdapter;
    private ArrayList<Restaurant> restaurants;
    ArrayList<String> types;
    String type;
    Activity activity;

    public final static String TAG = "fragment";
    private ApiInterface apiInterface;


    OnAllRestaurantListItemListener onAllRestaurantListItemListener;

    public interface OnAllRestaurantListItemListener{
        public void switchToOrderManageRestaurant(Restaurant restaurant);
    }

    public AllRestaurantListFragment() {
        Log.i("fragment", "AllRestaurantListFragment" );
        types = new ArrayList<>();
        restaurants = new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        spinner = view.findViewById(R.id.spinner_type);
        button = view.findViewById(R.id.btn_search);
        textView = view.findViewById(R.id.resMsgTV);
        restaurantListView = view.findViewById(R.id.res_list_view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getRestaurantList("ALL",RestaurantActivity.userid);
        getListOfTypes();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                type = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRestaurantList(type,RestaurantActivity.userid);
            }
        });


        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onAllRestaurantListItemListener.switchToOrderManageRestaurant(restaurants.get(i));
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.context = context;
        this.activity = (Activity) context;
        onAllRestaurantListItemListener = (OnAllRestaurantListItemListener) activity;

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();
    }

    private void getRestaurantList(String type, int id){
        Call<RestaurantListResponse> restaurantListResponseCall = apiInterface.getTypeRestaurantList(type, id);

        restaurantListResponseCall.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {
                if(response.isSuccessful()){
                    Log.i("fragment", "Success  : - " );

                    restaurants = response.body().getRestaurants();

                    if(restaurants != null) {
                        if (restaurants.size() > 0) {
                            restaurantAdapter = new RestaurantAdapter(activity, restaurants); // name a change context to activity
                            restaurantListView.setAdapter(restaurantAdapter);
                            textView.setVisibility(View.GONE);
                            restaurantListView.setVisibility(View.VISIBLE);
                        }
                    }else{
                        restaurantListView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantListResponse> call, Throwable t) {
                //Toast.makeText(context, "failed : " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "failed : " + t.getMessage());

            }
        });
    }

    private void getListOfTypes(){
        Call<ArrayList<Type>> callTypes = apiInterface.getResTypeList();
        callTypes.enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                if(response.code() == 200 ){
                    ArrayList<Type> arrtypes = response.body();
                    for (Type type : arrtypes) {
                        types.add(type.getResType());
                    }
                    types.add(0, "ALL");
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, types);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinner.setAdapter(dataAdapter);


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {

            }
        });

    }

}
