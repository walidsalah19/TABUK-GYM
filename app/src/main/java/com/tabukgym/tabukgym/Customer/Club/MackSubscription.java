package com.tabukgym.tabukgym.Customer.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.UserAccess.SelectUserType;
import com.tabukgym.tabukgym.databinding.FragmentMackSubscribtionBinding;

public class MackSubscription extends Fragment {
    private FragmentMackSubscribtionBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentMackSubscribtionBinding.inflate(inflater,container,false);
        payment();
        back();
        return mBinding.getRoot();
    }
    private void payment()
    {
        mBinding.btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new Payment());
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubDetailsTabs());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.customerLayout,fragment).commit();

    }
}