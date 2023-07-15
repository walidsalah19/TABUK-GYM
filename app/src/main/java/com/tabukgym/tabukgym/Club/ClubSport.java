package com.tabukgym.tabukgym.Club;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.FragmentClubSportBinding;

public class ClubSport extends Fragment {
    private FragmentClubSportBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= FragmentClubSportBinding.inflate(inflater,container,false);


        addSport();
        return mBinding.getRoot();
    }
    private void addSport()
    {
        mBinding.addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubAddSport());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.clubLayout,fragment).addToBackStack(null).commit();

    }
}