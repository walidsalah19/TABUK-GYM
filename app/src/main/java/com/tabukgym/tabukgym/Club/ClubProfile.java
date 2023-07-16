package com.tabukgym.tabukgym.Club;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.UserAccess.LoginAllUsers;
import com.tabukgym.tabukgym.UserAccess.MainUserAccess;
import com.tabukgym.tabukgym.databinding.FragmentClubProfileBinding;

public class ClubProfile extends Fragment {
    private FragmentClubProfileBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubProfileBinding.inflate(inflater,container,false);


        logout();
        return  mBinding.getRoot();
    }
    private void logout()
    {
        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainUserAccess.class));
            }
        });
    }
}