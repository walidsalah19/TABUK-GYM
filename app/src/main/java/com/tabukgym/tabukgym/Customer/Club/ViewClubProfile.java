package com.tabukgym.tabukgym.Customer.Club;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentViewClubProfileBinding;

public class ViewClubProfile extends Fragment {
    private FragmentViewClubProfileBinding mBinding;
    private String logo,longitude,latitude;
    private ClubModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding=FragmentViewClubProfileBinding.inflate(inflater,container,false);
        ViewDialog.startLoading(getActivity());
        subscription();
        getClubData();
        showLocation();
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
    private void showLocation()
    {
        mBinding.SelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "geo:" + model.getLatitude() + "," + model.getLongitude() + "?q=" + model.getLatitude() + "," + model.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }
    private void getClubData() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.clubTable);
        databaseReference.child(CommonData.clubId).addValueEventListener(new ValueEventListener() {
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
        String status = snapshot.child("status").getValue().toString();
        String name = snapshot.child("name").getValue().toString();
        String email = snapshot.child("email").getValue().toString();
        longitude = snapshot.child("longitude").getValue().toString();
        latitude = snapshot.child("latitude").getValue().toString();
        logo = snapshot.child("logo").getValue().toString();
        String subPrice = snapshot.child("subPrice").getValue().toString();
        String phone = snapshot.child("phone").getValue().toString();
        String comRegister = snapshot.child("comRegister").getValue().toString();
        String id = snapshot.child("id").getValue().toString();
        model=new ClubModel(name,email,longitude,latitude,logo,subPrice,phone,comRegister,id,status);
        mBinding.clubName.setText(name);
        mBinding.phone.setText("Club Phone : "+phone);
        mBinding.packagePrice.setText("Subscription price : "+subPrice);
        mBinding.phone.setText(phone);
        Glide.with(getActivity()).load(logo).into(mBinding.image);
        ViewDialog.loading.dismiss();

    }
}