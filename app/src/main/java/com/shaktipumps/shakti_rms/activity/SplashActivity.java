package com.shaktipumps.shakti_rms.activity;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.multidex.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.plattysoft.leonids.ParticleSystem;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.CheckUpdateModel.CheckAppUpdateModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.Constant;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    Vibrator vibe;

    private static final int REQUEST_CODE_PERMISSION = 2;
    Intent i ;
    public static int SPLASH_TIME_OUT = 1000;

    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    Context mContex ;
    ImageView imgLogoID;


    @Override
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContex = this;
        imgLogoID = (ImageView) findViewById(R.id.imgLogoID);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        if(checkPermission()){
            openNextActivity();
        }
        else {
            requestPermission();
        }
        }

    private void openNextActivity()     {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    SharedPreferencesUtil.setData(mContex, Constant.USER_APP_OPEN_STATUS, "true");
                    if (SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE) == null || SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase("")) {
                        SharedPreferencesUtil.setData(mContex, Constant.LANGUAGE_NAME_CODE, "en");
                        SharedPreferencesUtil.setData(mContex, Constant.LANGUAGE_NAME_SAVE, "English");
                        Constant.setLocale(mContex, "en");
                    } else {
                        String hhhhh = SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE);
                        Constant.setLocale(mContex, hhhhh);
                    }

                    if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y")) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent =  new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }
        }, SPLASH_TIME_OUT);
    }


    private boolean checkPermission() {
        int FineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int CoarseLocation = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int Bluetooth = ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH);
        int PhoneState = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int Camera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int ReadExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int WriteExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ReadMediaImages = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_IMAGES);
        int ReadMediaAudio = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_AUDIO);
        int BluetoothConnect =  ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH_CONNECT);
        int BluetoothScan =  ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH_SCAN);


        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return CoarseLocation == PackageManager.PERMISSION_GRANTED && Bluetooth == PackageManager.PERMISSION_GRANTED
                    && Camera == PackageManager.PERMISSION_GRANTED  && ReadMediaImages == PackageManager.PERMISSION_GRANTED
                    && ReadMediaAudio == PackageManager.PERMISSION_GRANTED && BluetoothConnect == PackageManager.PERMISSION_GRANTED
                    && BluetoothScan == PackageManager.PERMISSION_GRANTED;
        }else  if  ( SDK_INT == Build.VERSION_CODES.S)  {
            return  Camera == PackageManager.PERMISSION_GRANTED
                    && CoarseLocation == PackageManager.PERMISSION_GRANTED  && PhoneState == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED   && BluetoothScan == PackageManager.PERMISSION_GRANTED
                    && BluetoothConnect == PackageManager.PERMISSION_GRANTED;
        } else  if  (SDK_INT == Build.VERSION_CODES.R)  {
            return  Camera == PackageManager.PERMISSION_GRANTED
                    && CoarseLocation == PackageManager.PERMISSION_GRANTED  && PhoneState == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED;
        }else {
            return FineLocation == PackageManager.PERMISSION_GRANTED && CoarseLocation == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED && PhoneState == PackageManager.PERMISSION_GRANTED
                    && Camera == PackageManager.PERMISSION_GRANTED && ReadExternalStorage == PackageManager.PERMISSION_GRANTED
                    && WriteExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,  Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.BLUETOOTH ,
                            Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION);
        } else if  ( SDK_INT == Build.VERSION_CODES.S )  {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,  Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_CONNECT , Manifest.permission.BLUETOOTH_SCAN },
                    REQUEST_CODE_PERMISSION);
        }else if  ( SDK_INT == Build.VERSION_CODES.R)  {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,},
                    REQUEST_CODE_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0) {

                    if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                        boolean CoarseLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean  Camera = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadMediaImages = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadMediaAudio = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothConnect = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothScan =grantResults[6] == PackageManager.PERMISSION_GRANTED;

                        if (BluetoothConnect && BluetoothScan && CoarseLocationAccepted && Bluetooth  && Camera && ReadMediaImages && ReadMediaAudio) {
                            // perform action when allow permission success
                            openNextActivity();
                        }else {
                            requestPermission();
                        }
                    } else  if ( SDK_INT == Build.VERSION_CODES.S ) {
                        boolean Camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean AccessCoarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothConnect = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothScan = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadPhoneState = grantResults[5] == PackageManager.PERMISSION_GRANTED;

                        if (Camera && ReadPhoneState && AccessCoarseLocation && Bluetooth &&  BluetoothConnect && BluetoothScan) {
                            openNextActivity();
                        } else {
                            requestPermission();
                        }
                    }else  if (  SDK_INT == Build.VERSION_CODES.R) {
                        boolean Camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean AccessCoarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadPhoneState = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                        if (Camera && ReadPhoneState && AccessCoarseLocation && Bluetooth) {
                            openNextActivity();
                        } else {
                            requestPermission();
                        }
                    }else  if ( SDK_INT <= Build.VERSION_CODES.Q) {
                        boolean FineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean CoarseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean ReadPhoneState = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        boolean Camera = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                        boolean ReadPhoneStorage = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                        boolean WritePhoneStorage = grantResults[6] == PackageManager.PERMISSION_GRANTED;


                        if(FineLocationAccepted && CoarseLocationAccepted && Bluetooth && ReadPhoneState && Camera && ReadPhoneStorage && WritePhoneStorage ){
                            openNextActivity();
                        }else {
                            requestPermission();
                        }
                    }
                }
                break;
        }
    }
}