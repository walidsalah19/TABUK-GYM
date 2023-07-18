package com.tabukgym.tabukgym.Club;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubTrainingScheduleBinding;

import java.util.UUID;

public class ClubTrainingSchedule extends Fragment {
    private FragmentClubTrainingScheduleBinding mBinding;
    private String clubId;
    public static final int PICK_IMAGE = 1;
    private UploadTask uploadTask;
    private StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubTrainingScheduleBinding.inflate(inflater,container,false);
        clubId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ViewDialog.startLoading(getActivity());
        getSchedule();
        addImage();
        return mBinding.getRoot();
    }
    private void getSchedule()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.scheduleTable);
        databaseReference.child(clubId).addValueEventListener(new ValueEventListener() {
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
    private void addImage()
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
                Glide.with(getActivity()).load(data.getData().toString()).into(mBinding.image);
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
                    addToDatabase(downloadUri.toString());

                }
            }
        });

    }

    private void addToDatabase(String toString) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(CommonData.scheduleTable);
        databaseReference.child(clubId).child("schedule").setValue(toString).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ViewDialog.loading.dismiss();
                if (task.isSuccessful())
                {
                    ViewDialog.funSuccessfully("Add Schedule Successfully",getActivity());
                }

            }
        });
    }
}