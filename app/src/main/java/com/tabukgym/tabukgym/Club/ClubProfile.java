package com.tabukgym.tabukgym.Club;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.MapsFragment;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.UserAccess.LoginAllUsers;
import com.tabukgym.tabukgym.UserAccess.MainUserAccess;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubProfileBinding;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ClubProfile extends Fragment {
    private FragmentClubProfileBinding mBinding;
    private ClubModel model;
    private String clubId,logo,longitude,latitude;
    public static final int PICK_IMAGE = 1;
    private UploadTask uploadTask;
    private StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubProfileBinding.inflate(inflater,container,false);
        clubId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ViewDialog.startLoading(getActivity());
        getClubData();
        logout();
        getLogo();
        addLocation();
        clickUpdate();
        return  mBinding.getRoot();
    }
    private void getClubData() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.clubTable);
        databaseReference.child(clubId).addValueEventListener(new ValueEventListener() {
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
            mBinding.clubComer.setText(comRegister);
            mBinding.clubName.setText(name);
            mBinding.subscriptionPrice.setText(subPrice);
            mBinding.phone.setText(phone);
            Glide.with(getActivity()).load(logo).into(mBinding.image);

    }
    private void getLogo()
    {
        mBinding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&& resultCode== Activity.RESULT_OK)
        {
            if (data !=null)
            {
                ViewDialog.startLoading(getActivity());
                saveImage(data.getData());
            }
        }
    }
    private void saveImage(Uri data) {
        storageReference= FirebaseStorage.getInstance().getReference("images");
        StorageReference reference=storageReference.child(UUID.randomUUID().toString());
        uploadTask= reference.putFile(data);

        Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful())
                {
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri=task.getResult();
                    logo=downloadUri.toString();
                    ViewDialog.loading.dismiss();
                }
            }
        });

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
        String clubName= mBinding.clubName.getText().toString();
        String subPrice= mBinding.subscriptionPrice.getText().toString();
        String phoneNum=mBinding.phone.getText().toString();
        String clubCom=mBinding.clubComer.getText().toString();
        if (!CommonData.latitude.isEmpty()) {
            longitude = CommonData.longitude;
            latitude = CommonData.latitude;
            CommonData.latitude="";
            CommonData.longitude="";
        }
        if (TextUtils.isEmpty(clubName))
        {
            mBinding.clubName.setError("Please enter your name");
        }
        else if (TextUtils.isEmpty(subPrice))
        {
            mBinding.subscriptionPrice.setError("please enter subscription price ");
        }
        else if (TextUtils.isEmpty(clubCom))
        {
            mBinding.clubComer.setError("please enter Commercial register ");
        }
        else if (TextUtils.isEmpty(longitude))
        {
            Toast.makeText(getActivity(), "please select club location", Toast.LENGTH_LONG).show();
        }
        else {
            model.setLogo(logo);
            model.setName(clubName);
            model.setSubPrice(subPrice);
            model.setLatitude(latitude);
            model.setLongitude(longitude);
            model.setPhone(phoneNum);
            model.setComRegister(clubCom);
            updateProfile();
        }
    }
    private void updateProfile()
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("clubs");
        database.child(model.getId()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ViewDialog.startLoading(getActivity());
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
                startActivity(new Intent(getActivity(), LoginAllUsers.class));
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
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainUserAccess.class));
            }
        });
    }
}