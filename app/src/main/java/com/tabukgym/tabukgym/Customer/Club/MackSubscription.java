package com.tabukgym.tabukgym.Customer.Club;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.tabukgym.tabukgym.Models.CustomerModel;
import com.tabukgym.tabukgym.Models.DeliveryModel;
import com.tabukgym.tabukgym.Models.SubscriptionModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentMackSubscribtionBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MackSubscription extends Fragment {
    private FragmentMackSubscribtionBinding mBinding;
    private CustomerModel custmodel;
    private String custId , period;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentMackSubscribtionBinding.inflate(inflater,container,false);
        ViewDialog.startLoading(getActivity());
        custId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        getCustData();
        sendRequest();
        back();
        return mBinding.getRoot();
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
        String longitude = snapshot.child("longitude").getValue().toString();
        String latitude = snapshot.child("latitude").getValue().toString();
        String age = snapshot.child("age").getValue().toString();
        String phone = snapshot.child("phone").getValue().toString();
        String height = snapshot.child("height").getValue().toString();
        String weight = snapshot.child("weight").getValue().toString();
        String id = snapshot.child("id").getValue().toString();
        custmodel=new CustomerModel(name,email,phone,age,height,weight,longitude,latitude,id);
        ViewDialog.loading.dismiss();
    }
    private void sendRequest()
    {
        mBinding.btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        String subId= UUID.randomUUID().toString();
        period=mBinding.subPeriod.getSelectedItem().toString();
        String month=mBinding.months.getSelectedItem().toString();
        String delivery=mBinding.delivery.getSelectedItem().toString();
        int subPrice=Integer.parseInt(month)*Integer.parseInt(CommonData.clubModel.getSubPrice());
        CommonData.price=subPrice;
        SubscriptionModel model=new SubscriptionModel(CommonData.clubModel.getName(),custmodel.getName(),
               String.valueOf(subPrice), date(),"start",custmodel.getPhone(),custmodel.getAge(),custmodel.getHeight(),
                custmodel.getWeight(),month+" Month",period,custId,CommonData.clubModel.getId(),subId);
        addToSubscription(model,delivery);
    }

    private void addToSubscription(SubscriptionModel model, String delivery) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.subTable);
        databaseReference.child(model.getSubId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    ViewDialog.funSuccessfully("Add Subscription Successfully",getContext());
                    if (delivery.equals("Yes"))
                    {
                        addToDelivery();
                        CommonData.delivery=300;
                    }
                    else{
                        CommonData.delivery=0;
                        payment();
                    }
                }
                else {
                    ViewDialog.funFailed("Failed to Add Subscription Successfully",getContext());
                }
            }
        });
    }

    private void addToDelivery() {
        String id=UUID.randomUUID().toString();
        DeliveryModel m=new DeliveryModel(CommonData.clubModel.getName(),custmodel.getName(),date(),period,"start,",id);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.deliverTable);
        databaseReference.child(id).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    payment();
                }
            }
        });
    }

    private String date()
    {
      return   new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
    private void payment()
    {
        mBinding.btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new Payment());
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubDetailsTabs());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.customerLayout,fragment).commit();

    }
}