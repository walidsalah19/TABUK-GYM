package com.tabukgym.tabukgym.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.UserAccess.SelectUserType;
import com.tabukgym.tabukgym.databinding.FragmentClubAddSportBinding;

public class ClubAddSport extends Fragment {
    private FragmentClubAddSportBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubAddSportBinding.inflate(inflater,container,false);
        back();
        return mBinding.getRoot();
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubSport());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.clubLayout,fragment).addToBackStack(null).commit();

    }
}