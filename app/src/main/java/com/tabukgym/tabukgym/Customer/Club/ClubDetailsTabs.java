package com.tabukgym.tabukgym.Customer.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tabukgym.tabukgym.Customer.Adapters.TabsLayoutAdapter;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentClubDetailsTabsBinding;

public class ClubDetailsTabs extends Fragment {
    private FragmentClubDetailsTabsBinding mBinding;
    private TabsLayoutAdapter tabs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubDetailsTabsBinding.inflate(inflater,container,false);
        mBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        tabs=new TabsLayoutAdapter(getActivity().getSupportFragmentManager());
        mBinding.viewPager.setAdapter(tabs);
        mBinding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return mBinding.getRoot();

    }
}