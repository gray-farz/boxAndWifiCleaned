package com.example.boxandwificleaned;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import presenter.Presenter;
import viewPackage.ViewActions;

public class UpdateWifiModuleCode extends AppCompatActivity implements ViewActions {
    public static CardView butSendWIFIsettingForUpdateCode;
    public static Button butGETIP;
    EditText edtSSIDupdateCODE,edtPASSupdateCODE;
    public static Button edtLINKupdateCode;
    public static Context context;
    public static Activity activity;

    ProgressDialog progressDialogUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_wifi_module_code);
//        activity =updateCodeActivity.this;

        defineWidgets();
        Presenter presenter=new Presenter(UpdateWifiModuleCode.this);
        presenter.configure();

        edtLINKupdateCode.setEnabled(false);

        butSendWIFIsettingForUpdateCodeAction();
        edtLinkUpdateAction();

    }

    @Override
    protected void onPause() {
        if(GeneralObjects.socketSampleUsed)
        {
            GeneralObjects.socketToWifiModule.cancel(true);
            GeneralObjects.socketSampleUsed = false;
        }
        super.onPause();
    }

    private void edtLinkUpdateAction() {
        edtLINKupdateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaa","link clicked");


                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivityForResult(intent,1);
            }
        });
    }

    private void butSendWIFIsettingForUpdateCodeAction() {
        butSendWIFIsettingForUpdateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaa","------ but save clicked");

                progressDialogUpdate.setMessage(UpdateWifiModuleCode.this.getString(R.string.connectingStr)); // Setting Message
                progressDialogUpdate.show(); // Display Progress Dialog

                String sentStrTowifiModule="UPDATECODE";
                String whButton="UPDATECODE";

                GeneralObjects.socketToWifiModule=new SocketToWifiModule("192.168.4.1",80,sentStrTowifiModule,UpdateWifiModuleCode.this);
                GeneralObjects.socketSampleUsed=true;
                GeneralObjects.socketToWifiModule.execute();
            }
        });
    }

    private void defineWidgets() {
        butSendWIFIsettingForUpdateCode = findViewById(R.id.butSendWifiSettingForUpdateCode);
        edtLINKupdateCode = findViewById(R.id.edtUpdateCodeLink);
        context = UpdateWifiModuleCode.this;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Intent intent;
        switch(requestCode)
        {

            case 1:
                Log.d("aaa","++++++ return from WifiPage to updatecCodeActivity   ++++++");

                openWebPageChooseCodeBinFile();

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openWebPageChooseCodeBinFile() {
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://192.168.4.1"));
        Log.d("aaa","atartactivity");
        startActivity(intent);
    }


    @Override
    public void defineProgressDialog(Context context) {
        progressDialogUpdate = new ProgressDialog(context);
        progressDialogUpdate.setTitle("ProgressDialog"); // Setting Title
        progressDialogUpdate.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialogUpdate.setIcon(R.drawable.stopwatch4);
        progressDialogUpdate.setCancelable(false);
    }

    @Override
    public void onTaskCompleted(String response) {
        edtLINKupdateCode.setEnabled(true);
        butSendWIFIsettingForUpdateCode.setVisibility(View.INVISIBLE);
    }
}