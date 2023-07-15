package com.tabukgym.tabukgym.UserAccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.tabukgym.tabukgym.R;

public class MainUserAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_access);
        getSupportFragmentManager().beginTransaction().replace(R.id.userAccessLayout,new SelectUserType()).commit();
    }
}