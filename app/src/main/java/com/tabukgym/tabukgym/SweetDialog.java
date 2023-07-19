package com.tabukgym.tabukgym;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialog {
    public static SweetAlertDialog success(Context context,String title)
    {
        SweetAlertDialog success = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setTitleText(title);
        success.setConfirmText("Ok");
        success.setCancelable(false);
        return success;
    }
    public static SweetAlertDialog failed(Context context, String title)
    {
        SweetAlertDialog failed = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        failed.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        failed.setTitleText(title);
        failed.setConfirmText("Ok");
        failed.setCancelable(false);
        return failed;
    }
    public static SweetAlertDialog loading(Context context)
    {
        SweetAlertDialog success = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        success.getProgressHelper().setBarColor(Color.parseColor("#1f8d28"));
        success.setCancelable(false);
        return success;
    }

}
