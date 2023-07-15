package com.tabukgym.tabukgym.UserAccess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentCustomerRegistrationBinding;

public class CustomerRegistration extends Fragment {
    private FragmentCustomerRegistrationBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerRegistrationBinding.inflate(inflater,container,false);
        login();
        back();
        return  mBinding.getRoot();
    }
    private void login()
    {
        mBinding.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LoginAllUsers());
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LoginAllUsers());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.userAccessLayout,fragment).addToBackStack(null).commit();

    }
}