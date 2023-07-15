package com.tabukgym.tabukgym.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentCustomerSubscriptionBinding;

public class CustomerSubscription extends Fragment {
    private FragmentCustomerSubscriptionBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerSubscriptionBinding.inflate(inflater,container,false);



        return  mBinding.getRoot();
    }
}