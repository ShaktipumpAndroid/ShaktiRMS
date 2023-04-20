package com.shaktipumps.shakti_rms.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;
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

    private static int SPLASH_TIME_OUT = 1000;
    ImageView imageView;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    String current_date = "null" ,current_time;
    Context mContex ;

    ImageView imgLogoID;


    @Override
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContex = this;
        imgLogoID = (ImageView) findViewById(R.id.imgLogoID);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        {
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
                    } else {
                        Intent intent =  new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }



                }
            }, SPLASH_TIME_OUT);
        }
    }

}