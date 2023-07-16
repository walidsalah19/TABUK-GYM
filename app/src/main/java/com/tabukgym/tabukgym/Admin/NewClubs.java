package com.tabukgym.tabukgym.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabukgym.tabukgym.Admin.Adapter.NewClubAdapter;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.databinding.FragmentNewClubsBinding;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewClubs extends Fragment {
    private FragmentNewClubsBinding mBinding;
    private ArrayList<ClubModel> clubs;
    private NewClubAdapter adapter;
    private SweetAlertDialog loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentNewClubsBinding.inflate(inflater,container,false);
        startDialog();
        recyclerViewComponent();
        viewDetails();
        return mBinding.getRoot();
    }
    private void startDialog()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void recyclerViewComponent()
    {
        clubs =new ArrayList<>();
        adapter=new NewClubAdapter(clubs);
        mBinding.newClubs.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.newClubs.setAdapter(adapter);
        getNewClubs();
    }
    private void getNewClubs() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.clubTable);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                   for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                      receiveData(snapshot);
                   }
                   adapter.notifyDataSetChanged();
                   loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void receiveData(DataSnapshot snapshot){
        String status = snapshot.child("status").getValue().toString();
        if (status.equals("new")) {
            String name = snapshot.child("name").getValue().toString();
            String email = snapshot.child("email").getValue().toString();
            String longitude = snapshot.child("longitude").getValue().toString();
            String latitude = snapshot.child("latitude").getValue().toString();
            String logo = snapshot.child("logo").getValue().toString();
            String subPrice = snapshot.child("subPrice").getValue().toString();
            String phone = snapshot.child("phone").getValue().toString();
            String comRegister = snapshot.child("comRegister").getValue().toString();
            String id = snapshot.child("id").getValue().toString();
            ClubModel model=new ClubModel(name,email,longitude,latitude,logo,subPrice,phone,comRegister,id,status);
            clubs.add(model);
        }
    }
    private void viewDetails()
    {
       adapter.setOnItemClickListener(new NewClubAdapter.OnItemClickListener() {
           @Override
           public void onItemClicked(int pos) {
               getActivity().getSupportFragmentManager().beginTransaction()
                       .replace(R.id.adminLayout,new ManageClubs()).addToBackStack(null).commit();
           }
       });
    }
}