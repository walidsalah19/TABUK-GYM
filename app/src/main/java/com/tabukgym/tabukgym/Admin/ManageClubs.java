package com.tabukgym.tabukgym.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.Customer.Club.MackSubscription;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentManageClubsBinding;

public class ManageClubs extends Fragment {
    private FragmentManageClubsBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentManageClubsBinding.inflate(inflater,container,false);


        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new NewClubs());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.adminLayout,fragment).addToBackStack(null).commit();

    }
}