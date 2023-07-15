package com.tabukgym.tabukgym.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentClubDevicesBinding;

public class ClubDevices extends Fragment {
    private FragmentClubDevicesBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubDevicesBinding.inflate(inflater,container,false);


        return mBinding.getRoot();
    }
}