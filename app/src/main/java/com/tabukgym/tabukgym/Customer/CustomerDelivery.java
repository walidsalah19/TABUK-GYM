package com.tabukgym.tabukgym.Customer;

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
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Customer.Adapters.CustomerDeliveryAdapter;
import com.tabukgym.tabukgym.Models.DeliveryModel;
import com.tabukgym.tabukgym.Models.SubscriptionModel;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentCustomerDeliveryBinding;

import java.util.ArrayList;

public class CustomerDelivery extends Fragment {
    private FragmentCustomerDeliveryBinding mBinding;
    private ArrayList<DeliveryModel> delivery;
    private CustomerDeliveryAdapter adapter;
    private String customerId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerDeliveryBinding.inflate(inflater,container,false);
        customerId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ViewDialog.startLoading(getActivity());
        recyclerViewComponent();
        return mBinding.getRoot();
    }
    private void recyclerViewComponent()
    {
        delivery =new ArrayList<>();
        adapter=new CustomerDeliveryAdapter(delivery,getActivity());
        mBinding.delivery.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.delivery.setAdapter(adapter);
        getNewClubs();
    }
    private void getNewClubs() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.deliverTable);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        receiveData(snapshot);
                    }
                    adapter.notifyDataSetChanged();
                }
                ViewDialog.loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void receiveData(DataSnapshot snapshot){
        String custId = snapshot.child("custId").getValue().toString();
        if (custId.equals(customerId)) {
            String clubName = snapshot.child("clubName").getValue().toString();
            String custName = snapshot.child("custName").getValue().toString();
            String duration = snapshot.child("duration").getValue().toString();
            String date = snapshot.child("date").getValue().toString();
            String status = snapshot.child("status").getValue().toString();
            String id = snapshot.child("id").getValue().toString();
            DeliveryModel model=new DeliveryModel(clubName,custName,date,duration,status,id,custId);
            delivery.add(model);
        }
    }
}