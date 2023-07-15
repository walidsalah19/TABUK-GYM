package com.tabukgym.tabukgym.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.Admin.MainAdminActivity;
import com.tabukgym.tabukgym.Club.ClubMainActivity;
import com.tabukgym.tabukgym.Customer.CommonData;
import com.tabukgym.tabukgym.Customer.MainCustomerActivity;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentLoginAllUsersBinding;

public class LoginAllUsers extends Fragment {

    private FragmentLoginAllUsersBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentLoginAllUsersBinding.inflate(inflater,container,false);
        customer();
        login();
        back();
        return  mBinding.getRoot();
    }
    private void login()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.Email.getText().toString().equals("customer")) {
                 startActivity(new Intent(getActivity(), MainCustomerActivity.class));
                }
               else if (mBinding.Email.getText().toString().equals("admin")) {
                    startActivity(new Intent(getActivity(), MainAdminActivity.class));
                }
                else if (mBinding.Email.getText().toString().equals("club")) {
                    startActivity(new Intent(getActivity(), ClubMainActivity.class));
                }
            }
        });
    }
    private void customer()
    {
        mBinding.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonData.type == 0) {
                   changeFragment(new CustomerRegistration());
                }
                else if(CommonData.type==1)
                {
                    changeFragment(new ClubRegistration());
                }
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SelectUserType());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.userAccessLayout,fragment).addToBackStack(null).commit();

    }
}