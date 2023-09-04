package com.tabukgym.tabukgym.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.databinding.FragmentManageClubsBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ManageClubs extends Fragment {
    private FragmentManageClubsBinding mBinding;
    private ClubModel model;
    private DatabaseReference database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentManageClubsBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference(CommonData.clubTable);
        model=getArguments().getParcelable("club");
        addToView();
        showLocation();
        acceptClub();
        rejectClub();
        back();
        return mBinding.getRoot();
    }
    private void addToView()
    {
        mBinding.clubName.setText(model.getName());
        mBinding.clubEmail.setText(model.getEmail());
        mBinding.phone.setText(model.getPhone());
        mBinding.packagePrice.setText(model.getSubPrice());
    }
    private void showLocation()
    {
        mBinding.selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "geo:" + model.getLatitude() + "," + model.getLongitude() + "?q=" + model.getLatitude() + "," + model.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }
    private void acceptClub()
    {
        mBinding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child(model.getId()).child("status").setValue("accept")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            funSuccessfully("Accept club");
                        else
                            funField("Error occur,Can't accept club");
                    }
                });
            }
        });
    }
    private void rejectClub()
    {
        mBinding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child(model.getId()).child("status").setValue("reject")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            funSuccessfully("Reject club");
                        else
                            funField("Error occur,Can't reject club");
                    }
                });
            }
        });
    }
    private void funSuccessfully(String title)
    {
        SweetAlertDialog success= SweetDialog.success(getContext(),title);
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
            }
        });
    }
    private void funField(String title)
    {
        SweetAlertDialog field=SweetDialog.failed(getContext(),title);
        field.show();
        field.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                field.dismiss();
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new NewClubs());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.adminLayout,fragment).addToBackStack(null).commit();

    }
}