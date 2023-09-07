package com.venkatakrishnan.netmeds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPageAdapter viewPageAdapter;
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    private Cart cartFragment;

    List<String> cartItems; // List to store products in the cart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Initialize the cart
        cartItems = new ArrayList<>();
        viewPageAdapter = new ViewPageAdapter(this);
        viewPageAdapter.addFragment(new Home()); // Add HomeFragment
        cartFragment = new Cart(); // Create an instance of CartFragment
        viewPageAdapter.addFragment(cartFragment); // Add CartFragment
        viewPager2 = findViewById(R.id.frame_layout);
        viewPager2.setAdapter(viewPageAdapter);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPageAdapter.addFragment(new Profile());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home_page) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.cart) {
                    viewPager2.setCurrentItem(1);
                    cartFragment.updateUI(); // Update the UI in CartFragment
                } else if (id == R.id.profile) {
                    viewPager2.setCurrentItem(2);
                }
                return false;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home_page).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true);
                        cartFragment.updateUI();
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

    public void addToCart(String product) {
        Log.d("MainActivity","Product received into Main Activity is: "+product);
        Log.d("MainActivity", "addToCart() method called in MainActivity");
        Log.d("MainActivity", "Cart Fragment: " + cartFragment);
        if (cartFragment != null) {
            Log.d("TAG", "addToCart() inside  of addToCart in MainActivity");
            cartFragment.updateCart(product); // Call the addToCart() method in the CartFragment
        }
    }

    // Add any additional methods related to managing the cart if needed
}
