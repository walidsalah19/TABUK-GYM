package com.tabukgym.tabukgym.Club;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabukgym.tabukgym.CommonAdapter.DevicesAdapter;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Models.DeviceModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubDevicesBinding;

import java.util.ArrayList;

public class ClubDevices extends Fragment {
    private FragmentClubDevicesBinding mBinding;
    private ArrayList<DeviceModel> devices;
    private DevicesAdapter adapter;
    private String clubId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubDevicesBinding.inflate(inflater,container,false);
        clubId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        ViewDialog.startLoading(getActivity());
        addDevice();
        recyclerViewComponent();
        return mBinding.getRoot();
    }
    private void recyclerViewComponent()
    {
        devices =new ArrayList<>();
        adapter=new DevicesAdapter(devices,getActivity());
        mBinding.devices.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.devices.setAdapter(adapter);
        getDevices();
    }
    private void getDevices() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.deviceTable);
        databaseReference.child(clubId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        receiveData(snapshot);
                    }
                    adapter.notifyDataSetChanged();
                    ViewDialog.loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void receiveData(DataSnapshot snapshot){
            String name = snapshot.child("name").getValue().toString();
            String number = snapshot.child("number").getValue().toString();
            String description = snapshot.child("description").getValue().toString();
            String image = snapshot.child("image").getValue().toString();
            String id = snapshot.child("id").getValue().toString();
            DeviceModel model=new DeviceModel(name,number,description,id,image);
            devices.add(model);
    }
    private void addDevice()
    {
        mBinding.addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubAddDevice());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.clubLayout,fragment).addToBackStack(null).commit();

    }
}