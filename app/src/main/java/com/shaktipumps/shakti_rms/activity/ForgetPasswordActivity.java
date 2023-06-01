package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.ForgetOTPBean.ForgotOTPPassModel;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Context mContext = null;

    private Toolbar mToolbar;

    private TextView txtNextBTNID;
    private EditText edtUserNameID;
    private EditText edtnewPassID;
    private EditText edtOldPassID;
    private String mORG_OTP_VALUE = "";
    private BaseRequest baseRequest;

    private String mUsernameStr;
    private String mConfirmStr;
    private String mOldPassStr;
    private String mNewPassStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mContext = this;

        inotView();
    }

    private void inotView() {

        mToolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseRequest = new BaseRequest(this);

        getSupportActionBar().setTitle(R.string.action_forgotPassword);

        txtNextBTNID =  findViewById(R.id.txtNextBTNID);
        edtUserNameID =  findViewById(R.id.edtUserNameID);



        txtNextBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(checkValidation())
               {
                   callSendOTPAPI();
               }
               else
               {
                   Toast.makeText(mContext, "Please enter username.", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    private Boolean  checkValidation() {

        mUsernameStr= edtUserNameID.getText().toString().trim();

        try {
            if(!mUsernameStr.isEmpty())
            {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void callSendOTPAPI() {
        baseRequest.showLoader();
        mORG_OTP_VALUE = getRandomNumberString();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    ForgotOTPPassModel mForgotOTPPassModel = gson.fromJson(Json, ForgotOTPPassModel.class);

                    if(mForgotOTPPassModel.getStatus())
                    {
                        baseRequest.hideLoader();
                        Intent mIntent = new Intent(mContext,OTPForgotPasswordActivity.class);
                        mIntent.putExtra("USER_MOBILE",mForgotOTPPassModel.getResponse().getMobileNo());
                        mIntent.putExtra("USER_ID",mForgotOTPPassModel.getResponse().getMUserId());
                        mIntent.putExtra("USER_NAME",mForgotOTPPassModel.getResponse().getMUserName());
                        mIntent.putExtra("OTP",mORG_OTP_VALUE);
                        startActivity(mIntent);

                    }
                    else
                    {
                        Toast.makeText(mContext, mForgotOTPPassModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                  //  getDeviceSettingListResponse(mSettingModelView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

              //  Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("MUserName", mUsernameStr);


        } catch (Exception e) {
            e.printStackTrace();
        }

        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_SEND_OTP_FORGOTPASS);
     //   baseRequest.callAPIGETDirectURL(1,   "http://control.yourbulksms.com/api/sendhttp.php?authkey=393770756d707334373701&mobiles="+mUsernameStr+"&message=Please%20Enter%20Following%20OTP%20To%20Reset%20Your%20Password%20"+mORG_OTP_VALUE+"%20SHAKTI%20GROUP&sender=SHAKTl&route=2&unicode=0&country=91&DLT_TE_ID=1707161726018508169");

    }


    @SuppressLint("DefaultLocale")
    private String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(9999);

        // this will convert any number sequence into 6 character.
        return String.format("%04d", number);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE == 2)
        {
            finish();
        }
        else
        {

        }
    }
}
