package com.tabukgym.tabukgym.Club;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.tabukgym.tabukgym.Customer.ClubFragment;
import com.tabukgym.tabukgym.Customer.CustomerDelivery;
import com.tabukgym.tabukgym.Customer.CustomerProfile;
import com.tabukgym.tabukgym.Customer.CustomerSubscription;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.ActivityClubMainBinding;

public class ClubMainActivity extends AppCompatActivity {
    private ActivityClubMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityClubMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        changeFragment(new ClubSubscriptions());
        bottomNavigation();
    }
    private void bottomNavigation()
    {
        mBinding.clubBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.clubSubscriptions) {
                    changeFragment(new ClubSubscriptions());
                }
                else if (item.getItemId() == R.id.clubSports)
                    changeFragment(new ClubSport());
                else if (item.getItemId() == R.id.clubDevices) {
                    changeFragment(new ClubDevices());
                }
                else if (item.getItemId() == R.id.clubProfile) {
                    changeFragment(new ClubProfile());
                }
                else if (item.getItemId() == R.id.Schedule) {
                    changeFragment(new ClubTrainingSchedule());
                }
                return true;
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.clubLayout,fragment).commit();

    }
}