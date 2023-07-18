package com.tabukgym.tabukgym.Customer.Club;

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
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubTrainersBinding;

public class ViewClubTrainingSchedule extends Fragment {
    private FragmentClubTrainersBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubTrainersBinding.inflate(inflater,container,false);
        getSchedule();
        return mBinding.getRoot();
    }
    private void getSchedule()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.scheduleTable);
        databaseReference.child(CommonData.clubId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String sch=dataSnapshot.child("schedule").getValue().toString();
                    Glide.with(getActivity()).load(sch).into(mBinding.image);
                }
                ViewDialog.loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}