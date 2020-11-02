package com.example.boxandwificleaned;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import petrov.kristiyan.colorpicker.ColorPicker;
import presenter.Presenter;
import viewPackage.ViewActions;


public class textActivity extends AppCompatActivity implements View.OnClickListener, ViewActions {
    Button butBACKGROUNDcolor;
    /////////////////////   WIDGETS
    Button butSaveTextSetting;
    TextInputEditText textContext;
    TextInputLayout textContextLayout;
    ProgressDialog progressDialogText;
    private GeneralObjects generalObjects;
    ImageView imageViewGif;

    ////////////////////  SAVE SETTING
    public static SharedPreferences shPref;
    public static final String MyPref = "MyPrefers";
    public static final String Text = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        defineWidgets();
        Presenter presenter=new Presenter(textActivity.this);
        presenter.configure();

        /////////////////// MOVE GIF
        /*from raw folder*/
        Glide.with(this).load(R.raw.wifigif).into(imageViewGif);

        butBACKGROUNDcolor.setOnClickListener(this);
        butSaveTextSetting.setOnClickListener(this);

        //////////////////  SHOW LAST SETTING
        shPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        setLastSetting();

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

    private void setLastSetting() {

        if (shPref.contains(Text)) {
            textContext.setText(shPref.getString(Text, null));
        }
    }

    private void saveSetting( String text) {
        SharedPreferences.Editor sEdit = shPref.edit();
        sEdit.putString(Text, text);
        sEdit.apply();
    }

    private void defineWidgets() {

        butBACKGROUNDcolor = findViewById(R.id.butBackgroundColor);
        textContext = findViewById(R.id.edtText);
        textContextLayout =findViewById(R.id.edtTextLAYOUT);
        butSaveTextSetting = findViewById(R.id.saveTEXTsetting);
        imageViewGif = findViewById(R.id.imgGif);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.butBackgroundColor)
        {
            makeColorPicker("back");
        }
        if(view.getId()==R.id.saveTEXTsetting)
        {
            sendTextSetting();
            saveSetting(textContext.getText().toString());
        }
    }
    public void sendTextSetting()
    {

        IMEI imei = new IMEI(textActivity.this,textActivity.this);
        imei.getIMEINumber();

        progressDialogText.setMessage(textActivity.this.getString(R.string.connectingStr)); // Setting Message
        progressDialogText.show(); // Display Progress Dialog

        String sentStrTowifiModule=imei.IMEINumber+textContext.getText().toString();

        GeneralObjects.socketToWifiModule=new SocketToWifiModule("192.168.4.1",80,sentStrTowifiModule,textActivity.this);
        GeneralObjects.socketSampleUsed=true;
        GeneralObjects.socketToWifiModule.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("aaa","######     connecing again     ######");

                progressDialogText.setMessage(textActivity.this.getString(R.string.connectingStr)); // Setting Message
                GeneralObjects.socketToWifiModule=new SocketToWifiModule("192.168.4.1",80,sentStrTowifiModule,textActivity.this);
                GeneralObjects.socketToWifiModule.execute();
            }
        }, 600L);

    }

    public void makeColorPicker(final String fontORback)
    {
        Log.d("aaa","^^^    makeColorPicker   ^^^");


        ////////////////////////////////////////////////////////////////////////   new colorpicker
        ColorPicker colorPicker = new ColorPicker(textActivity.this);
        colorPicker.setColors(0xffff0000,0xff00ff00,0xff0000ff,0xffffff00,0xffff00ff,0xff00ffff,0xffffffff, Color.BLACK);

        colorPicker.setColumns(4);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                // put code
                Toast.makeText(textActivity.this,""+Integer.toHexString(color)+","+color,Toast.LENGTH_SHORT).show();
                Log.d("aaa","______ textContextLayout set   _____");

                butBACKGROUNDcolor.setBackgroundColor(color);
                textContextLayout.setBackgroundColor(color);

            }

            @Override
            public void onCancel(){
                // put code
            }
        });

        colorPicker.show();

    }

    @Override
    public void defineProgressDialog(Context context) {
        progressDialogText = new ProgressDialog(context);
        progressDialogText.setTitle("ProgressDialog"); // Setting Title
        progressDialogText.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialogText.setIcon(R.drawable.stopwatch4);
        progressDialogText.setCancelable(false);
    }

    @Override
    public void onTaskCompleted(String response) {
        progressDialogText.dismiss();

    }
}