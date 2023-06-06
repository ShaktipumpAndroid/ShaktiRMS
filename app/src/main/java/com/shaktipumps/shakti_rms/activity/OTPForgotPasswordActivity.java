package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

public class OTPForgotPasswordActivity extends AppCompatActivity {

    private Context mContext = null;
    private Toolbar mToolbar;

    private TextView txtSaveBTNID, btn_send_otp;
    private EditText edtNewPassID, edtConfrimPassID, edtOTPID ,edtUserID;

    private String mUserNameStr,OTP,mobile;
    private String mNewPassStr;
    private String mOTPPassStr;
    private String mConfirmPassStr;
    private BaseRequest baseRequest;

    private CountDownTimer countDownTimer;
    private TextView txtTimerCounterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpforgot_password);
        mContext = this;

        Bundle bundle = getIntent().getExtras();
        mobile = bundle.getString("USER_MOBILE");
        OTP = bundle.getString("OTP");

        inotView();
    }

    private void inotView() {


        baseRequest = new BaseRequest(this);
     /*   mUserNameStr = getIntent().getStringExtra("USER_NAME");
        OTP = getIntent().getStringExtra("OTP");*/
        mToolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_forgotPassword);

        txtTimerCounterID =  findViewById(R.id.txtTimerCounterID);
        btn_send_otp =  findViewById(R.id.btn_send_otp);
        txtSaveBTNID =  findViewById(R.id.txtSaveBTNID);
        edtNewPassID =  findViewById(R.id.edtNewPassID);
        edtConfrimPassID =  findViewById(R.id.edtConfrimPassID);
        edtOTPID =  findViewById(R.id.edtOTPID);
        edtUserID = findViewById(R.id.edtUserID);

        txtSaveBTNID.setOnClickListener(v -> {
            if (checkValidation()) {
                callSaveForgetPassAPI();
            }

        });

        btn_send_otp.setOnClickListener(view -> {

            callSendOTPAPI();
            changeButtonVisibilityRLV(false, 0.5f,btn_send_otp);

            startTimer(30000);

        });

    }


    private Boolean checkValidation() {

        mUserNameStr = edtUserID.getText().toString().trim();
        mNewPassStr = edtNewPassID.getText().toString().trim();
        mConfirmPassStr = edtConfrimPassID.getText().toString().trim();
        mOTPPassStr = edtOTPID.getText().toString().trim();

        Log.e("Enter","OTP++>"+mOTPPassStr);


        try {
            if (mNewPassStr.equalsIgnoreCase("")) {
                //  Toast.makeText(mContext, "Please enter username.", Toast.LENGTH_SHORT).show();
                edtNewPassID.setError("Enter new passowrd.");
                edtNewPassID.requestFocus();
                return false;
            } else if (mConfirmPassStr.equalsIgnoreCase("")) {
                //  Toast.makeText(mContext, "Please enter username.", Toast.LENGTH_SHORT).show();
                edtConfrimPassID.setError("Enter Confirm passowrd.");
                edtConfrimPassID.requestFocus();
                return false;
            } else if (!mConfirmPassStr.equalsIgnoreCase(mNewPassStr)) {
                //  Toast.makeText(mContext, "Please enter username.", Toast.LENGTH_SHORT).show();
                edtNewPassID.setError("Password not match.");
                edtNewPassID.requestFocus();
                return false;
            } else if (mOTPPassStr.equalsIgnoreCase("")) {
                //  Toast.makeText(mContext, "Please enter username.", Toast.LENGTH_SHORT).show();
                edtOTPID.setError("Enter OTP.");
                edtOTPID.requestFocus();
                return false;
            }else if(!OTP.equalsIgnoreCase(mOTPPassStr)){
                edtOTPID.setError("Wrong OTP ,Check and write again or resend OTP");
                edtOTPID.requestFocus();
                return false;
            } else if (mOTPPassStr.length() < 4) {
                //  Toast.makeText(mContext, "Please enter username.", Toast.LENGTH_SHORT).show();
                edtOTPID.setError("Enter Valid 4 digit OTP.");
                edtOTPID.requestFocus();
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private void callSaveForgetPassAPI() {
        baseRequest.showLoader();

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;

                baseRequest.hideLoader();

                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    ForgotPassModelView mForgotPassModelView = gson.fromJson(Json, ForgotPassModelView.class);
                    if (mForgotPassModelView.getStatus()) {
                        Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                        ///password reset sucessfully
                        Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE = 2;////finish old screen
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
                baseRequest.hideLoader();
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                baseRequest.hideLoader();
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {

            ////Put input parameter here
            jsonObject.addProperty("MUserName", mUserNameStr);
            jsonObject.addProperty("Password", mNewPassStr);
            jsonObject.addProperty("OTP", "0");


        } catch (Exception e) {
            e.printStackTrace();
        }
        //  baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_RESET_FORGOTPASS);/////
         //baseRequest.callAPIPut(1, jsonObject, NewSolarVFD.ORG_RESET_FORGOTPASS);/////
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE = 0;// //not to stop
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE = 1;// //finish old screen
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void changeButtonVisibilityRLV(boolean state, float alphaRate, TextView txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
        //  hideBTN();
    }
    private void startTimer(long totalTimeCountInMilliseconds) {
        txtTimerCounterID.setVisibility(View.VISIBLE);
        //btn_send_otp.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

//
                txtTimerCounterID.setVisibility(View.VISIBLE);
                txtTimerCounterID.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                txtTimerCounterID.setText("Time up!");
                txtTimerCounterID.setVisibility(View.GONE);
                changeButtonVisibilityRLV(true, 1.0f,btn_send_otp);
            }
        }.start();

    }

    private void callSendOTPAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                Toast.makeText(mContext, "Re-Send OTP ", Toast.LENGTH_LONG).show();

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
            jsonObject.addProperty("MUserName", mUserNameStr);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
      //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_SEND_OTP_FORGOTPASS);/////

        baseRequest.callAPIGETDirectURL(1,   "http://control.yourbulksms.com/api/sendhttp.php?authkey=393770756d707334373701&mobiles="+mobile+"&message=Please%20Enter%20Following%20OTP%20To%20Reset%20Your%20Password%20"+OTP+"%20SHAKTI%20GROUP&sender=SHAKTl&route=2&unicode=0&country=91&DLT_TE_ID=1707161726018508169");

    }

}
