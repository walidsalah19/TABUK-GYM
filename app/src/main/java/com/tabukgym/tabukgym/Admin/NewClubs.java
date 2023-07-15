package com.tabukgym.tabukgym.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.Customer.Club.ClubDetailsTabs;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentNewClubsBinding;

public class NewClubs extends Fragment {
    private FragmentNewClubsBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentNewClubsBinding.inflate(inflater,container,false);


        details();
        return mBinding.getRoot();
    }
    private void details()
    {
        mBinding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.adminLayout,new ManageClubs()).addToBackStack(null).commit();
            }
        });
    }
}