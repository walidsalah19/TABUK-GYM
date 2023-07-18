package com.tabukgym.tabukgym.UserAccess;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.MapsFragment;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.databinding.FragmentClubRegistrationBinding;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ClubRegistration extends Fragment {
    private FragmentClubRegistrationBinding mBinding;
    private String longitude="",latitude="",image="";
    private int pStatus=0;
    private SweetAlertDialog loading;
    public static final int PICK_IMAGE = 1;
    UploadTask uploadTask;
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentClubRegistrationBinding.inflate(inflater,container,false);
        clickCreateAccount();
        showPassword();
        login();
        back();
        getLogo();
        addLocation();
        return  mBinding.getRoot();
    }
    private void startLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void getLogo()
    {
        mBinding.SelectImage.setOnClickListener(new View.OnClickListener() {
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
                startLoading();
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
                    loading.dismiss();
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
    private void clickCreateAccount()
    {
        mBinding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnteredData();
            }
        });
    }

    private void checkEnteredData() {
        String clubName= mBinding.clubName.getText().toString();
        String email= mBinding.edittextEmail.getText().toString();
        String subPrice= mBinding.subscriptionPrice.getText().toString();
        String phoneNum=mBinding.clubPhone.getText().toString();
        String clubCom=mBinding.clubComer.getText().toString();
        String password=mBinding.edittextPassword.getText().toString();
        longitude= CommonData.longitude;
        latitude=CommonData.latitude;
        CommonData.longitude="";
        CommonData.latitude="";
        if (TextUtils.isEmpty(clubName))
        {
            mBinding.clubName.setError("Please enter your name");
        }

        else if (TextUtils.isEmpty(email))
        {
            mBinding.edittextEmail.setError("please enter your email");
        }
        else if (! validatePassword(password))
        {
            mBinding.edittextPassword.setError("Please enter Strong Password contain small,capital,spacial character and digits");
        }
        else if (TextUtils.isEmpty(subPrice))
        {
            mBinding.subscriptionPrice.setError("please enter subscription price ");
        }
        else if (TextUtils.isEmpty(clubCom))
        {
            mBinding.clubComer.setError("please enter Commercial register ");
        }
        else if (TextUtils.isEmpty(image))
        {
            Toast.makeText(getActivity(), "please select club logo", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(longitude))
        {
            Toast.makeText(getActivity(), "please select club location", Toast.LENGTH_LONG).show();
        }
        else {
            startLoading();
            ClubModel model=new ClubModel(clubName,email,longitude,latitude,image,subPrice
            ,phoneNum,clubCom,null,"new");
            createAccount(model,email,password);
        }
    }
    private void createAccount( ClubModel model,String email,String password)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String id=task.getResult().getUser().getUid().toString();
                    model.setId(id);
                    sendRequest(model,id);
                }
                else
                {
                    loading.dismiss();
                    funField("please change your email");
                }
            }
        });
    }
    private void sendRequest( ClubModel model,String id)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("clubs");
        database.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                    funSuccessfully();
                }
                else
                {
                    funField("failed to create account");
                }
            }
        });
    }
    private void funSuccessfully()
    {
        SweetAlertDialog dialog= SweetDialog.success(getContext(),"Create Account Successfully");
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
    public static boolean validatePassword(String password) {
        // Minimum length of 8 characters
        if (password.length() < 8) {
            return false;
        }

        // At least one lowercase letter
        Pattern lowercasePattern = Pattern.compile("[a-z]");
        Matcher lowercaseMatcher = lowercasePattern.matcher(password);
        if (!lowercaseMatcher.find()) {
            return false;
        }

        // At least one uppercase letter
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        if (!uppercaseMatcher.find()) {
            return false;
        }

        // At least one special character
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*()\\-_+=<>?/{}~]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (!specialCharMatcher.find()) {
            return false;
        }

        // At least one digit
        Pattern digitPattern = Pattern.compile("\\d");
        Matcher digitMatcher = digitPattern.matcher(password);
        if (!digitMatcher.find()) {
            return false;
        }

        // All criteria met, password is valid
        return true;
    }
    private void showPassword() {
        mBinding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pStatus==0)
                {
                    Glide.with(ClubRegistration.this)
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.edittextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(ClubRegistration.this)
                            .load(R.drawable.baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=0;
                    mBinding.edittextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
    private void login()
    {
        mBinding.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LoginAllUsers());
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LoginAllUsers());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.userAccessLayout,fragment).addToBackStack(null).commit();

    }
}