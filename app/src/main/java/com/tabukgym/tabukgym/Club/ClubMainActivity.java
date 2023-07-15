package com.tabukgym.tabukgym.Club;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.databinding.ActivityClubMainBinding;

public class ClubMainActivity extends AppCompatActivity {
    private ActivityClubMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityClubMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}