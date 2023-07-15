package com.tabukgym.tabukgym.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.Customer.Club.ClubDetailsTabs;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentClubBinding;

public class ClubFragment extends Fragment {
    FragmentClubBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubBinding.inflate(inflater,container,false);
        details();
        return mBinding.getRoot();
    }
    private void details()
    {
        mBinding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.customerLayout,new ClubDetailsTabs()).addToBackStack(null).commit();
            }
        });
    }
}