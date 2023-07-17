package com.tabukgym.tabukgym;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewDialog {
    public static SweetAlertDialog loading;

    public static void funSuccessfully(String title, Context con)
    {
        SweetAlertDialog success= SweetDialog.success(con,title);
        success.show();
        success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                success.dismiss();
            }
        });
    }
    public static void funFailed(String title,Context con)
    {
        SweetAlertDialog field=SweetDialog.failed(con,title);
        field.show();
        field.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                field.dismiss();
            }
        });
    }
    public static void startLoading(Context con)
    {
        loading= SweetDialog.loading(con);
        loading.show();
    }
}
