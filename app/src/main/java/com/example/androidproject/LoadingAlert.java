package com.example.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingAlert {

    Activity activity;
    AlertDialog dialog;

    public LoadingAlert(Activity activity) {
        this.activity = activity;
    }

    void startDialog(Activity activity){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(true);

        dialog=builder.create();
        dialog.show();
    }

    void  dismissdialog(){
        dialog.dismiss();
    }

    public void startDialog(Runnable runnable) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(true);

        dialog=builder.create();
        dialog.show();
    }
}
