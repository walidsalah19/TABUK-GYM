package com.tabukgym.tabukgym.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.tabukgym.tabukgym.Customer.ClubFragment;
import com.tabukgym.tabukgym.Customer.CustomerDelivery;
import com.tabukgym.tabukgym.Customer.CustomerProfile;
import com.tabukgym.tabukgym.Customer.CustomerSubscription;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.UserAccess.LoginAllUsers;
import com.tabukgym.tabukgym.databinding.ActivityMainAdminBinding;

public class MainAdminActivity extends AppCompatActivity {
    private ActivityMainAdminBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        changeFragment(new ClubFragment());
        bottomNavigation();
    }
    private void bottomNavigation()
    {
        mBinding.adminBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Clubs) {
                    changeFragment(new NewClubs());
                }
                else if (item.getItemId() == R.id.customerDelivery) {
                    changeFragment(new AdminDelivery());
                }
                else if (item.getItemId() == R.id.logout) {
                    startActivity(new Intent(MainAdminActivity.this, LoginAllUsers.class));
                }
                return true;
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.adminLayout,fragment).commit();

    }
}