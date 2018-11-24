package com.growtogether.myrestaurant.ordermanagement;

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

import com.growtogether.myrestaurant.ApiClient;
import com.growtogether.myrestaurant.ItemListFragment;
import com.growtogether.myrestaurant.OrdersListFragment;
import com.growtogether.myrestaurant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderingActivity extends AppCompatActivity {
    ImageView imageView;
    TextView nameTV, phoneTV, addressTV;
    public static int userSerialNumber;
    public static int restaurantSerialNo;
    public final static String TAG = "fragment";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager_fragment_container);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        imageView = findViewById(R.id.ivorestaurant);
        nameTV = findViewById(R.id.tvresname);
        phoneTV = findViewById(R.id.tvresphone);
        addressTV = findViewById(R.id.tvresaddress);

        Bundle bundle = getIntent().getExtras();
        userSerialNumber = getIntent().getIntExtra("UserSerialNo", 0);

        restaurantSerialNo  = bundle.getInt("RestaurantSerialNo", 0);
        nameTV.setText(bundle.getString("Name"));
        phoneTV.setText(bundle.getString("Phone"));
        addressTV.setText(bundle.getString("Address"));

        Log.e(TAG, "OrderingActivity -> userSerialNumber : " + userSerialNumber);
        Log.e(TAG, "OrderingActivity -> Name : " + bundle.getString("Name"));
        Log.e(TAG, "OrderingActivity -> restaurantSerialNo : " + restaurantSerialNo);

        String urltxt = ApiClient.BASE_URL + bundle.getString("ImageUrl");
        Picasso.get().load(urltxt).into(imageView);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment(), "Menu");
        adapter.addFragment(new MyOrderListFragment(), "My List");
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




}
