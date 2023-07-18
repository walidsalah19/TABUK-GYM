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
import com.tabukgym.tabukgym.CommonAdapter.SubscriptionAdapter;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Customer.Adapters.CustomerSubscriptionsAdapter;
import com.tabukgym.tabukgym.Models.SubscriptionModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentCustomerSubscriptionBinding;

import java.util.ArrayList;

public class CustomerSubscription extends Fragment {
    private FragmentCustomerSubscriptionBinding mBinding;
    private ArrayList<SubscriptionModel> subscriptions;
    private CustomerSubscriptionsAdapter adapter;
    private String customerId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerSubscriptionBinding.inflate(inflater,container,false);


        customerId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ViewDialog.startLoading(getActivity());
        recyclerViewComponent();
        return mBinding.getRoot();
    }
    private void recyclerViewComponent()
    {
        subscriptions =new ArrayList<>();
        adapter=new CustomerSubscriptionsAdapter(subscriptions,getActivity());
        mBinding.subscription.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.subscription.setAdapter(adapter);
        getNewClubs();
    }
    private void getNewClubs() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.subTable);
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
            String subPrice = snapshot.child("subPrice").getValue().toString();
            String date = snapshot.child("date").getValue().toString();
            String subStatus = snapshot.child("subStatus").getValue().toString();
            String custPhone = snapshot.child("custPhone").getValue().toString();
            String custAge = snapshot.child("custAge").getValue().toString();
            String custHeight = snapshot.child("custHeight").getValue().toString();
            String custWeight = snapshot.child("custWeight").getValue().toString();
            String subPeriod = snapshot.child("subPeriod").getValue().toString();
            String trainingPeriod = snapshot.child("trainingPeriod").getValue().toString();
            String cId = snapshot.child("clubId").getValue().toString();
            String subId = snapshot.child("subId").getValue().toString();
            SubscriptionModel model=new SubscriptionModel(clubName,custName,subPrice,date,subStatus,custPhone,
                    custAge,custHeight,custWeight,subPeriod,trainingPeriod,custId,cId,subId);
            subscriptions.add(model);
        }
    }
}