package com.tabukgym.tabukgym.UserAccess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentSelectUserTypeBinding;

public class SelectUserType extends Fragment {
    private FragmentSelectUserTypeBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding =FragmentSelectUserTypeBinding.inflate(inflater,container,false);
        customer();
        club();
        return mBinding.getRoot();
    }
    private void customer()
    {
        mBinding.customerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonData.type=0;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.userAccessLayout,new LoginAllUsers()).addToBackStack(null).commit();
            }
        });
    }
    private void club()
    {
        mBinding.club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonData.type=1;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.userAccessLayout,new LoginAllUsers()).addToBackStack(null).commit();
            }
        });
    }
}