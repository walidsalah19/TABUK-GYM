package com.tabukgym.tabukgym.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.MapsFragment;
import com.tabukgym.tabukgym.Models.CustomerModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.UserAccess.LoginAllUsers;
import com.tabukgym.tabukgym.UserAccess.MainUserAccess;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentCustomerProfileBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerProfile extends Fragment {
    private FragmentCustomerProfileBinding mBinding;
    private CustomerModel model;
    private String custId,longitude,latitude;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerProfileBinding.inflate(inflater,container,false);
        custId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ViewDialog.startLoading(getActivity());
        getCustData();
        logout();
        addLocation();
        clickUpdate();
        return  mBinding.getRoot();
    }
    private void getCustData() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.customerTable);
        databaseReference.child(custId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    receiveData(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void receiveData(DataSnapshot snapshot){
        String name = snapshot.child("name").getValue().toString();
        String email = snapshot.child("email").getValue().toString();
        longitude = snapshot.child("longitude").getValue().toString();
        latitude = snapshot.child("latitude").getValue().toString();
        String age = snapshot.child("age").getValue().toString();
        String phone = snapshot.child("phone").getValue().toString();
        String height = snapshot.child("height").getValue().toString();
        String weight = snapshot.child("weight").getValue().toString();
        String id = snapshot.child("id").getValue().toString();
        model=new CustomerModel(name,email,phone,age,height,weight,longitude,latitude,id);
        mBinding.edittextUserName.setText(name);
        mBinding.edittextEmail.setText(email);
        mBinding.phone.setText(phone);
        mBinding.userAge.setText(age);
        mBinding.userheight.setText(height);
        mBinding.userWeight.setText(weight);
        ViewDialog.loading.dismiss();
    }
    private void addLocation()
    {
        mBinding.SelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.userAccessLayout, new MapsFragment()).commit();
            }
        });
    }
    private void clickUpdate()
    {
        mBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnteredData();
            }
        });
    }

    private void checkEnteredData() {
        String name= mBinding.edittextUserName.getText().toString();
        String age= mBinding.userAge.getText().toString();
        String phoneNum=mBinding.phone.getText().toString();
        String height=mBinding.userheight.getText().toString();
        String weight=mBinding.userWeight.getText().toString();

        if (!CommonData.latitude.isEmpty()) {
            longitude = CommonData.longitude;
            latitude = CommonData.latitude;
            CommonData.latitude="";
            CommonData.longitude="";
        }
        if (TextUtils.isEmpty(name))
        {
            mBinding.edittextUserName.setError("Please enter your name");
        }
        else if (TextUtils.isEmpty(phoneNum))
        {
            mBinding.phone.setError("please enter your phone number ");
        }
        else if (TextUtils.isEmpty(age))
        {
            mBinding.userAge.setError("please enter your age");
        }
        else if (TextUtils.isEmpty(height))
        {
            mBinding.userheight.setError("please enter your height ");
        }
        else if (TextUtils.isEmpty(weight))
        {
            mBinding.userWeight.setError("please enter your weight ");
        }
        else {
            model.setName(name);
            model.setPhone(phoneNum);
            model.setAge(age);
            model.setLatitude(latitude);
            model.setLongitude(longitude);
            model.setHeight(height);
            model.setWeight(weight);
            updateProfile();
        }
    }
    private void updateProfile()
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference(CommonData.customerTable);
        database.child(model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    funSuccessfully();
                }
                else
                {
                    funField("failed to  update Account");
                }
            }
        });
    }
    private void funSuccessfully()
    {
        SweetAlertDialog dialog= SweetDialog.success(getContext(),"Update Account Successfully");
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
    }
    private void funField(String title)
    {
        SweetAlertDialog dialog= SweetDialog.failed(getContext(),title);
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
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