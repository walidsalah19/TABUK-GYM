package com.tabukgym.tabukgym.Customer.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentViewClubProfileBinding;

public class ViewClubProfile extends Fragment {
    private FragmentViewClubProfileBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding=FragmentViewClubProfileBinding.inflate(inflater,container,false);
        subscription();
        return mBinding.getRoot();
    }
    private void subscription()
    {
        mBinding.btnOrderPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.customerLayout,new MackSubscription()).addToBackStack(null).commit();

            }
        });
    }
}