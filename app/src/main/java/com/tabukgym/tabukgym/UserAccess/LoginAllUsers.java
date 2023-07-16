package com.tabukgym.tabukgym.UserAccess;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabukgym.tabukgym.Admin.MainAdminActivity;
import com.tabukgym.tabukgym.Club.ClubMainActivity;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Customer.MainCustomerActivity;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.databinding.FragmentLoginAllUsersBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginAllUsers extends Fragment {

    private FragmentLoginAllUsersBinding mBinding;
    private SweetAlertDialog loading;
    private int pStatus=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentLoginAllUsersBinding.inflate(inflater,container,false);
        createAccount();
        login();
        back();
        showPassword();
        return  mBinding.getRoot();
    }
    private void funLoading()
    {
        loading= SweetDialog.loading(getContext());
        loading.show();
    }
    private void login()
    {
        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mBinding.Email.getText().toString();
                String password=mBinding.password.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    mBinding.Email.setError("Please enter your email");
                }
                else if(TextUtils.isEmpty(password))
                {
                    mBinding.password.setError(" Please enter your password");
                }
                else if(mBinding.Email.getText().toString().equals("admin@gmail.com"))
                {
                    startActivity(new Intent(getActivity(), MainAdminActivity.class));
                }
                else
                {
                    funLoading();
                    checkAccount(email,password);
                }

            }
        });
    }

    private void checkAccount(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loading.dismiss();
                if (task.isSuccessful())
                {
                    if (CommonData.type==0) {
                        checkCustomerCorrectly(task.getResult().getUser().getUid().toString());
                    }
                    else
                    {
                        checkClubCorrectly(task.getResult().getUser().getUid().toString());

                    }
                }
                else
                {
                    funLoginField("Failed to sign in to your account does not exist");
                }
            }
        });
    }

    private void checkClubCorrectly(String id) {
        DatabaseReference club= FirebaseDatabase.getInstance().getReference(CommonData.clubTable);
        club.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String status=snapshot.child("status").getValue().toString();
                    if (status.equals("accept"))
                    {
                        funLoginSuccessfully();
                    }
                    else {
                        funLoginField("Failed to sign in to your account,Admin Don't accept your account yet");
                    }
                }
                else
                {
                    funLoginField("Failed to sign in to your account does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkCustomerCorrectly(String id) {
        DatabaseReference clintDatabase= FirebaseDatabase.getInstance().getReference(CommonData.customerTable);
        clintDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    funLoginSuccessfully();
                }
                else
                {
                    funLoginField("Failed to sign in to your account does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void funLoginSuccessfully()
    {
        SweetAlertDialog success=SweetDialog.success(getContext(),"تم تسجيل الدخول بنجاح");
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
                if (CommonData.type==0)
                {
                    startActivity(new Intent(getActivity(), MainCustomerActivity.class));
                }
                else
                {
                    startActivity(new Intent(getActivity(), ClubMainActivity.class));
                }
            }
        });
    }
    private void funLoginField(String message)
    {
        FirebaseAuth.getInstance().signOut();
        SweetAlertDialog field=SweetDialog.failed(getContext(),message);
        field.show();
        field.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                field.dismiss();
            }
        });
    }
    private void showPassword() {
        mBinding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pStatus==0)
                {
                    Glide.with(LoginAllUsers.this)
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(LoginAllUsers.this)
                            .load(R.drawable.baseline_visibility_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=0;
                    mBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
    private void createAccount()
    {
        mBinding.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonData.type == 0) {
                   changeFragment(new CustomerRegistration());
                }
                else if(CommonData.type==1)
                {
                    changeFragment(new ClubRegistration());
                }
            }
        });
    }
    private void back()
    {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SelectUserType());
            }
        });
    }
    private void changeFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.userAccessLayout,fragment).addToBackStack(null).commit();

    }
}