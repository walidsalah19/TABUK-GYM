package com.tabukgym.tabukgym.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.tabukgym.tabukgym.Customer.Club.ClubDetailsTabs;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.ActivityMainCustomerBinding;

public class MainCustomerActivity extends AppCompatActivity {
    private ActivityMainCustomerBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityMainCustomerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        changeFragment(new ClubFragment());
       bottomNavigation();
    }
    private void bottomNavigation()
    {
        mBinding.customerBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.customerHome) {
                    changeFragment(new ClubFragment());
                }
                else if (item.getItemId() == R.id.customerSubscriptions)
                   changeFragment(new CustomerSubscription());
                else if (item.getItemId() == R.id.customerDelivery) {
                   changeFragment(new CustomerDelivery());
                }
                else if (item.getItemId() == R.id.customerProfile) {
                    changeFragment(new CustomerProfile());
                }
                return true;
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.customerLayout,fragment).commit();

    }
}