package com.growtogether.myrestaurant;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ManageRestaurantsActivity extends AppCompatActivity implements ItemListFragment.OnItemListActivityListener, AddItemFragment.OnAddItemActivityListener {
    ImageView imageView;
    TextView nameTV, phoneTV, addressTV;
    Button button;
    public static int userSerialNumber;
    public static int restaurantSerialNo;
    public final static String TAG = "fragment";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_restaurants);

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
        button = findViewById(R.id.btnrestaurant);

        Bundle bundle = getIntent().getExtras();
        //userSerialNumber  = bundle.getInt("UserSerialNo", 0);
        userSerialNumber = getIntent().getIntExtra("UserSerialNo", 0);

        restaurantSerialNo  = bundle.getInt("RestaurantSerialNo", 0);
        nameTV.setText(bundle.getString("Name"));
        phoneTV.setText(bundle.getString("Phone"));
        addressTV.setText(bundle.getString("Address"));

        Log.e(TAG, "ManageRestaurantActivity -> userSerialNumber : " + userSerialNumber);
        Log.e(TAG, "ManageRestaurantActivity -> Name : " + bundle.getString("Name"));
        Log.e(TAG, "ManageRestaurantActivity -> restaurantSerialNo : " + restaurantSerialNo);


        String urltxt = ApiClient.BASE_URL + bundle.getString("ImageUrl");
        Picasso.get().load(urltxt).into(imageView);

        getSupportFragmentManager().beginTransaction().add(R.id.viewpager_fragment_container, new ItemListFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ItemListFragment(), "Menu");
        adapter.addFragment(new OrdersListFragment(), "Orders");
        viewPager.setAdapter(adapter);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager_fragment_container, new AddItemFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void switchToItemListFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager_fragment_container, new ItemListFragment())
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
