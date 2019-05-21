package com.growtogether.myrestaurant.ordermanagement;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.growtogether.myrestaurant.utils.ApiClient;
import com.growtogether.myrestaurant.pojo.MenuResponse;
import com.growtogether.myrestaurant.R;
import com.growtogether.myrestaurant.adapters.MenuAdapter;
import com.growtogether.myrestaurant.pojo.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderingActivity extends AppCompatActivity implements MenuAdapter.OnAdapterItemListener{
    ImageView imageView;
    TextView nameTV, phoneTV, addressTV;
    public static int userSerialNumber;
    public static int restaurantSerialNo;
    public final static String TAG = "fragment";
    public static ArrayList<OrderItem> selectedItems;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        selectedItems = new ArrayList<>();

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
        userSerialNumber = getIntent().getIntExtra("CustomerSerialNo", 0);

        restaurantSerialNo  = bundle.getInt("RestaurantSerialNo", 0);
        String type = (" [ " + bundle.getString("Type") + " ]");
        nameTV.setText(bundle.getString("Name") + type);
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

    @Override
    public void addItemToOrderList(MenuResponse.Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(item.getName());
        orderItem.setItemSerialNo(item.getSerialno());
        orderItem.setItemPrice(item.getPrice());
        orderItem.setItemQuantity(1);
        selectedItems.add(orderItem);
    }

    @Override
    public int addItemCountOnList(MenuResponse.Item item) {
        int num = item.getSerialno();
        Log.i("fragment", " <- Item -> " + num);
        for(int i = 0; i < selectedItems.size(); i++){
            if(selectedItems.get(i).getItemSerialNo() == num) {
                selectedItems.get(i).setItemQuantity(selectedItems.get(i).getItemQuantity() + 1);
                return selectedItems.get(i).getItemQuantity();
            }
        }
        return 0;
    }

    @Override
    public int decreaseItemCountOnList(MenuResponse.Item item) {
        int num = item.getSerialno();
        Log.i("fragment", " <- Item -> " + num);
        for(int i = 0; i < selectedItems.size(); i++){
            if(selectedItems.get(i).getItemSerialNo() == num) {
                int count = selectedItems.get(i).getItemQuantity();
                if (count == 1){
                    selectedItems.remove(i);
                    return 0;
                }
                else if (count > 1){
                    selectedItems.get(i).setItemQuantity(count - 1);
                    return selectedItems.get(i).getItemQuantity();
                }
            }
        }
        return 0;
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
