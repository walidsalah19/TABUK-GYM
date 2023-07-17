package com.tabukgym.tabukgym.Club;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Models.DeviceModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubAddDeviceBinding;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ClubAddDevice extends Fragment {
    private FragmentClubAddDeviceBinding mBinding;
    private DatabaseReference database;
    private String image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubAddDeviceBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference(CommonData.deviceTable);
        clickAdd();
        back();
        return mBinding.getRoot();
    }
    private void clickAdd()
    {
        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        String name=mBinding.name.getText().toString();
        String number=mBinding.deviceNumber.getText().toString();
        String des=mBinding.subscription.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            mBinding.name.setError("Please enter Device name");
        }
        else if (TextUtils.isEmpty(number))
        {
            mBinding.deviceNumber.setError("Please enter Devices number");
        }
        else if (TextUtils.isEmpty(des))
        {
            mBinding.subscription.setError("Please enter Devices description");
        }
        else if (TextUtils.isEmpty(image))
        {
            Toast.makeText(getActivity(), "Please select Devices photo", Toast.LENGTH_SHORT).show();
        }
        else {
            String id= UUID.randomUUID().toString();
            DeviceModel model=new DeviceModel(name,number,des,id,image);
            ViewDialog.startLoading(getContext());
            addToDatabase(model);

        }
    }

    private void addToDatabase(DeviceModel model) {

        database.child(model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    ViewDialog.funSuccessfully("Add Devices Successfully",getContext());
                }
                else {
                    ViewDialog.funFailed("Failed Add Devices Successfully",getContext());
                }
            }
        });
    }

    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubDevices());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.clubLayout,fragment).addToBackStack(null).commit();

    }
}