package com.tabukgym.tabukgym.Customer.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.databinding.FragmentClubSportBinding;
import com.tabukgym.tabukgym.databinding.FragmentViewClubSportBinding;

public class ViewClubSport extends Fragment {
    private FragmentViewClubSportBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding=FragmentViewClubSportBinding.inflate(inflater,container,false);


        return  mBinding.getRoot();
    }
}