package com.example.boxandwificleaned;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

public class IMEI {

    Context contextthis;
    Activity activityThis;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=10;
    String IMEINumber = "";

    IMEI(Context inputContext, Activity inputActivity)
    {
        contextthis = inputContext;
        activityThis =inputActivity;
    }
    public void getIMEINumber() {


        if (ContextCompat.checkSelfPermission(contextthis,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("aaa","decline");

            // نمایش توضیحات در صورت نیاز
            if (ActivityCompat.shouldShowRequestPermissionRationale(activityThis,
                    Manifest.permission.READ_PHONE_STATE)) {
                Log.d("aaa","with explanation");
                ActivityCompat.requestPermissions(activityThis,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // محلی که می توان برای کاربر توضیحات فرستاد
            } else {

                // No explanation needed, we can request the permission.
                Log.d("aaa","no explanation");
                ActivityCompat.requestPermissions(activityThis,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        else
        {
            Log.d("aaa","^^^    IMEI CLASS  ^^^");
            Log.d("aaa","accepted before");
            Log.d("aaa","^^^    IMEI CLASS  ^^^");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
            {

                SubscriptionManager subsManager = (SubscriptionManager) contextthis.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

                List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();

                if (subsList!=null) {
                    for (SubscriptionInfo subsInfo : subsList) {
                        if (subsInfo != null) {
                            IMEINumber  = subsInfo.getIccId();
                        }
                    }
                }
            }
            else
            {
                TelephonyManager tMgr = (TelephonyManager) contextthis.getSystemService(Context.TELEPHONY_SERVICE);
                IMEINumber = tMgr.getSimSerialNumber();
            }
        }


        Log.d("aaa","return IMEI "+IMEINumber);

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("aaa","request");
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                Log.d("aaa","case READ CONTACTS");
                // اگر درخواست مجوز کنسل شده باشد نتیجه خالی خواهد بود
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // مجوز دریافت شده است
                    Log.d("aaa","allow");

                    if (ActivityCompat.checkSelfPermission(contextthis, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        TelephonyManager telephonyMgr = (TelephonyManager) contextthis.getSystemService(TELEPHONY_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            IMEINumber = telephonyMgr.getImei();
                        } else {
                            IMEINumber = telephonyMgr.getDeviceId();
                        }
                    }

                    Toast.makeText(contextthis, "حالا می توانی دکمه login را دوباره امتحان کنی", Toast.LENGTH_SHORT).show();

                } else {

                    // مجوز دریافت نشده است.
                    Log.d("aaa","deny");

                }
                return;
            }

        }
    }

}

