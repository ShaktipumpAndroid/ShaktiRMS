package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.io.File;
import java.util.ArrayList;

public class ListXLsxFileBTActivity extends AppCompatActivity {

    private Context mContext = null;
    private Toolbar mToolbar;



    private BaseRequest baseRequest;



    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String mUsernamePrefSTR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_xlsx_file);

        mContext = this;


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        inotView();
    }

    private void inotView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Extracted File list");
        baseRequest = new BaseRequest(this);

        //key_login_username
        mUsernamePrefSTR = pref.getString("key_login_username", "login_status");

        getSupportActionBar().setTitle(R.string.action_chnagePassword);



    }


    public static ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".xlsx") ||
                        file.getName().endsWith(".XLSX")) {
                    if (!inFiles.contains(file))
                        inFiles.add(file);

                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


    private void callSendOTPAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    ForgotPassModelView mForgotPassModelView = gson.fromJson(Json, ForgotPassModelView.class);

                    if (mForgotPassModelView.getStatus()) {
                        /*Intent mIntent = new Intent(mContext,OTPForgotPasswordActivity.class);
                        mIntent.putExtra("USER_NAME",mUsernameStr);
                        startActivity(mIntent);*/
                        Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //  getDeviceSettingListResponse(mSettingModelView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("MUserName", mUsernamePrefSTR);
           // jsonObject.addProperty("NewPassword", mNewPassStr);
           // jsonObject.addProperty("OldPassword", mOldPassStr);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.CHANGE_PASSWORD_API);/////
    }



}
