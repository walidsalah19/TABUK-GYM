package com.tabukgym.tabukgym.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentAdminDeliveryBinding;

public class AdminDelivery extends Fragment {
    private FragmentAdminDeliveryBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentAdminDeliveryBinding.inflate(inflater,container,false);



        return mBinding.getRoot();
    }
}