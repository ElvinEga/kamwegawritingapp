package com.twisac.kamwegawritings.components;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Elvin Ambasa on 3/3/17.
 */

public class AlertPopup {

    public void alertConnectError(final Context context) {
        final SweetAlertDialog failedAlert = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        failedAlert.setTitleText("Connectivity Error")
                .setContentText("Failed,Check Your Internet Connection!")
                .setCancelText("Retry")
                .setConfirmText("Settings")
                .showCancelButton(true).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
                failedAlert.dismiss();

            }
        })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        failedAlert.dismiss();
                    }
                }).show();


    }

    public void alertError(Context context, String title, String message) {
        SweetAlertDialog failedAlert = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        failedAlert.setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void alertSuccess(Context context, String title, String message) {
        SweetAlertDialog failedAlert = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        failedAlert.setTitleText(title)
                .setContentText(message)
                .show();
    }

}


