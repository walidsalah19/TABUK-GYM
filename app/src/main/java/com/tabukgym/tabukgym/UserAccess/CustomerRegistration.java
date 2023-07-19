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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tabukgym.tabukgym.CommonData;
import com.tabukgym.tabukgym.Customer.MainCustomerActivity;
import com.tabukgym.tabukgym.MapsFragment;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.Models.CustomerModel;
import com.tabukgym.tabukgym.R;
import com.tabukgym.tabukgym.SweetDialog;
import com.tabukgym.tabukgym.databinding.FragmentCustomerRegistrationBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerRegistration extends Fragment {
    private FragmentCustomerRegistrationBinding mBinding;
    private String longitude="",latitude="";
    private int pStatus=0;
    private SweetAlertDialog loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentCustomerRegistrationBinding.inflate(inflater,container,false);
        clickCreateAccount();
        showPassword();
        login();
        back();
        addLocation();
        return  mBinding.getRoot();
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
        String name= mBinding.edittextUserName.getText().toString();
        String email= mBinding.edittextEmail.getText().toString();
        String phoneNum=mBinding.phone.getText().toString();
        String age=mBinding.userAge.getText().toString();
        String height=mBinding.userheight.getText().toString();
        String weight=mBinding.userWeight.getText().toString();
        String password=mBinding.edittextPassword.getText().toString();
        longitude=CommonData.longitude;
        latitude=CommonData.latitude;
        if (TextUtils.isEmpty(name))
        {
            mBinding.edittextUserName.setError("Please enter your name");
        }

        else if (TextUtils.isEmpty(email))
        {
            mBinding.edittextEmail.setError("please enter your email");
        }
        else if (! validatePassword(password))
        {
            mBinding.edittextPassword.setError("Please enter Strong Password contain small,capital,spacial character and digits");
        }
        else if (TextUtils.isEmpty(phoneNum))
        {
            mBinding.phone.setError("please enter your phone number ");
        }
        else if (TextUtils.isEmpty(age))
        {
            mBinding.userAge.setError("please enter your age ");
        }
        else if (TextUtils.isEmpty(height))
        {
            mBinding.userheight.setError("please enter your height ");
        }
        else if (TextUtils.isEmpty(weight))
        {
            mBinding.userWeight.setError("please enter your weight ");
        }

        else if (TextUtils.isEmpty(longitude))
        {
            Toast.makeText(getActivity(), "please select club location", Toast.LENGTH_LONG).show();
        }
        else {
            loading= SweetDialog.loading(getContext());
            loading.show();
            CustomerModel model=new CustomerModel(name,email,phoneNum,age,height,weight,longitude,latitude,"");
            createAccount(model,email,password);
        }
    }
    private void createAccount(CustomerModel model,String email,String password)
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
    private void sendRequest(CustomerModel model,String id)
    {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference(CommonData.customerTable);
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
                startActivity(new Intent(getActivity(), MainCustomerActivity.class));
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
                    Glide.with(CustomerRegistration.this)
                            .load(R.drawable.baseline_visibility_off_24)
                            .centerCrop()
                            .into(mBinding.showPassword);
                    pStatus=1;
                    mBinding.edittextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                {
                    Glide.with(CustomerRegistration.this)
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