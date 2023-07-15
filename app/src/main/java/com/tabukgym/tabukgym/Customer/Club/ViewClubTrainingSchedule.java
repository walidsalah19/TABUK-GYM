package com.tabukgym.tabukgym.Customer.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.databinding.FragmentClubTrainersBinding;

public class ViewClubTrainingSchedule extends Fragment {
    private FragmentClubTrainersBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubTrainersBinding.inflate(inflater,container,false);




        return mBinding.getRoot();
    }
}