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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Models.DeviceModel;
import com.tabukgym.tabukgym.Models.SportModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.UserAccess.SelectUserType;
import com.tabukgym.tabukgym.ViewDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubAddSportBinding;

import java.util.UUID;

public class ClubAddSport extends Fragment {
    private FragmentClubAddSportBinding mBinding;
    private DatabaseReference database;
    private String image,clubId;
    public static final int PICK_IMAGE = 1;
    private UploadTask uploadTask;
    private StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubAddSportBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance().getReference(CommonData.sportTable);
        clubId= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        clickAdd();
        addImage();
        back();
        return mBinding.getRoot();
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
                    image=downloadUri.toString();
                    Glide.with(getActivity()).load(downloadUri).into(mBinding.image);
                    ViewDialog.loading.dismiss();
                }
            }
        });

    }
    private void clickAdd()
    {
        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        String name=mBinding.name.getText().toString();
        String des=mBinding.description.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            mBinding.name.setError("Please enter Sport name");
        }
        else if (TextUtils.isEmpty(des))
        {
            mBinding.description.setError("Please enter Sport description");
        }
        else if (TextUtils.isEmpty(image))
        {
            Toast.makeText(getActivity(), "Please select Sport photo", Toast.LENGTH_SHORT).show();
        }
        else {
            String id= UUID.randomUUID().toString();
            SportModel model=new SportModel(name,des,image,id);
            ViewDialog.startLoading(getContext());
            addToDatabase(model);

        }
    }

    private void addToDatabase(SportModel model) {

        database.child(clubId).child(model.getId()).setValue(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ViewDialog.loading.dismiss();
                if (task.isSuccessful())
                {
                    ViewDialog.funSuccessfully("Add Sport Successfully",getContext());
                }
                else {
                    ViewDialog.funFailed("Failed to Add Sport Successfully",getContext());
                }
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ClubSport());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.clubLayout,fragment).addToBackStack(null).commit();

    }
}