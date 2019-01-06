package com.growtogether.myrestaurant.managerestaurant;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.growtogether.myrestaurant.pojo.Order;
import com.growtogether.myrestaurant.pojo.User;
import com.growtogether.myrestaurant.restaurant.AddItemActivity;
import com.growtogether.myrestaurant.restaurant.RestaurantActivity;
import com.growtogether.myrestaurant.starter.MainActivity;
import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.utils.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageRestaurantsActivity extends AppCompatActivity
        implements ItemListFragment.OnItemListActivityListener,
        RestaurantsOrdersListFragment.OnRestaurantsOrdersListListener {
    ImageView imageView;
    TextView nameTV, phoneTV, addressTV;
    public static int userSerialNumber;
    public static int restaurantSerialNo;
    public static String name, phone, address, type ;
    public static String[] options = {"Declined", "Viewed", "Accepted", "Processing", "Ready", "Delivered"};
    private ApiInterface apiInterface;

    public final static String TAG = "fragment";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static ArrayList<Order> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_restaurants);

        ApiClient apiClient = new ApiClient();
        apiInterface = apiClient.getApiInterface();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager_fragment_container);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        imageView = findViewById(R.id.ivrestaurant);
        nameTV = findViewById(R.id.tvrname);
        phoneTV = findViewById(R.id.tvrphone);
        addressTV = findViewById(R.id.tvraddress);

        Bundle bundle = getIntent().getExtras();
        userSerialNumber = getIntent().getIntExtra("UserSerialNo", 0);
        restaurantSerialNo  = bundle.getInt("RestaurantSerialNo", 0);

        type = (bundle.getString("Type"));
        int position = bundle.getInt("viewpager_position");

        name = bundle.getString("Name");
        phone = bundle.getString("Phone");
        address = bundle.getString("Address");

        nameTV.setText( name + " [ " + type + " ]");
        phoneTV.setText(phone);
        addressTV.setText(address);

        Log.e(TAG, "ManageRestaurantActivity -> userSerialNumber : " + userSerialNumber);
        Log.e(TAG, "ManageRestaurantActivity -> Name : " + bundle.getString("Name"));
        Log.e(TAG, "ManageRestaurantActivity -> restaurantSerialNo : " + restaurantSerialNo);

        String urltxt = ApiClient.BASE_URL + bundle.getString("ImageUrl");
        Picasso.get().load(urltxt).into(imageView);
        viewPager.setCurrentItem(position);
        getSupportFragmentManager().beginTransaction().add(R.id.viewpager_fragment_container, new ItemListFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ItemListFragment(), "Menu");
        adapter.addFragment(new RestaurantsOrdersListFragment(), "Orders");
        viewPager.setAdapter(adapter);
    }



    @Override
    public void switchToOrderDetailList(int i, int value) {
        Log.i("fragment", "switchToOrderDetailList : "+ i);
        updateStatus(i, value, 1);
        final Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra(MainActivity.SWITCH, 3);  // "Switch"
        intent.putExtra("oid", list.get(i).getOrderID());
        intent.putExtra("date", list.get(i).getOrderDate());
        intent.putExtra("status", list.get(i).getOrderStatus());
        intent.putExtra("uid", list.get(i).getUserSerialNo());
        Log.i("fragment", "sear : " + list.get(i).getUserSerialNo() );

        Call<User> call = apiInterface.getUserInfo(list.get(i).getUserSerialNo());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user != null){

                        Log.i("fragment", " c sear namr: " + user.getUserName());
                        Log.i("fragment", "c sear phone: " + user.getUserPhone() );
                        intent.putExtra("cname", user.getUserName());
                        intent.putExtra("cphone", Integer.parseInt(user.getUserPhone()));
                        startActivity(intent);
                    }else {
                        Log.i("fragment", " result: " + null );
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("fragment", " error : " + t.getMessage() );
                startActivity(intent);
            }
        });

    }

    public void updateStatus(int i, int value, final int destination){

        Log.i("fragment", "switchToOrderDetailList : "+ i + " : "+value);
        int orderid = list.get(i).getOrderID();
        int userid = list.get(i).getUserSerialNo();
        Call<Integer> call = apiInterface.setStatus(orderid, userid, value);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.i("fragment", "response : "+ response.body());
                if(response.isSuccessful()){
                    if(destination < 1) reOpen();
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i("fragment", "response : "+ t.getMessage());
            }
        });

    }

    public void reOpen(){
        Intent intent = getIntent();
        /*    don't need to add extra data these will get from the intent
        intent.putExtra("RestaurantSerialNo", restaurantSerialNo);
        intent.putExtra("UserSerialNo", userSerialNumber);
        intent.putExtra("Name", name);
        intent.putExtra("Type", type);
        intent.putExtra("Phone", phone);
        intent.putExtra("Address", address);
        intent.putExtra("ImageUrl", restaurant.getImageurl());
        */
        intent.putExtra("viewpager_position", 1);
        finish();
        startActivity(intent);
    }

    @Override
    public void changeOrderStatus(int i, int value) {

        updateStatus(i, value, 0); // 1 < will open the list again

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void switchToAddItemFragment() {

        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("restaurantSerialNo", restaurantSerialNo);
        startActivity(intent);

    }



}
