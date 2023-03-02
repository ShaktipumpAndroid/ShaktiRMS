package com.shaktipumps.shakti_rms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.UserBeanOTP.DeviceAuthOTPModelView;
import com.shaktipumps.shakti_rms.bean.UserBeanOTP.DeviceAuthResponse;
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class DeviceOTPActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView btn_send_otp,btn_verify_otp;
    private TextInputLayout inputLayoutOTP;
    private EditText inputOTP;
    Context mContext;
    SharedPreferences.Editor editor;
    String otp = "null",sms_url = "null",mobile_number = "null",MuserId = "null" ,MDeviceId = "null";

    String otp_status = "false" ;
    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    private ProgressDialog progressDialog;
    private BaseRequest baseRequest;

    Timer T=new Timer();
    private CountDownTimer countDownTimer;
    private TextView txtTimerCounterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        DeviceOTPActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mContext =  this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        baseRequest = new BaseRequest(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_verify_device);

        inputLayoutOTP  = (TextInputLayout) findViewById(R.id.input_layout_otp);
        inputOTP        = (EditText) findViewById(R.id.et_otp);
        btn_send_otp    = (TextView) findViewById(R.id.btn_send_otp);
        btn_verify_otp  = (TextView) findViewById(R.id.btn_verify_otp);
        txtTimerCounterID  = (TextView) findViewById(R.id.txtTimerCounterID);

        txtTimerCounterID.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();

        mobile_number = bundle.getString("Mobile");
        MuserId = bundle.getString("MUserId");
        MDeviceId = bundle.getString("MDeviceId");

        //Log.d("intent",MuserId +"--"+  MDeviceId +"--"+ mobile_number);

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyOTP();
            }
        });


        btn_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    reSendOTP();
                callSendOTPAPI();
                changeButtonVisibilityRLV(false, 0.5f,btn_send_otp);
                startTimer(30000);

            }
        });

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

    private void verifyOTP() {

        if (!validateOTP()) {
            return;
        }
           // updateStatusOTP();

        callUpdateOTPAPI();

           // CustomUtility.isErrorDialog(DeviceOTPActivity.this,getString(R.string.error),getString(R.string.err_invaild_otp));
    }

    private boolean validateOTP() {
        if (inputOTP.getText().toString().isEmpty()) {
            inputLayoutOTP.setError(getString(R.string.err_msg_otp));
            requestFocus(inputOTP);
            return false;
        } else {
            inputLayoutOTP.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
            jsonObject.addProperty("MUserId", MuserId);
            jsonObject.addProperty("MDeviceId", MDeviceId);
            jsonObject.addProperty("MobileNo", mobile_number);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.SEND_ADD_DEVICE_OTP);/////
        //baseRequest.callAPIPostLocal(1, jsonObject, NewSolarVFD.SEND_ADD_DEVICE_OTP);/////
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

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                txtTimerCounterID.setText("Time up!");
                txtTimerCounterID.setVisibility(View.GONE);
                changeButtonVisibilityRLV(true, 1.0f,btn_send_otp);

                // textViewShowTime.setVisibility(View.VISIBLE);
                // buttonStartTime.setVisibility(View.VISIBLE);
                //  buttonStopTime.setVisibility(View.GONE);
                //  edtTimerValue.setVisibility(View.VISIBLE);
            }

        }.start();

    }

    private void callUpdateOTPAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    DeviceAuthOTPModelView mDeviceAuthOTPModelView = gson.fromJson(Json, DeviceAuthOTPModelView.class);
                    getUpdatedOTPResponse(mDeviceAuthOTPModelView);

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

        Map<String, String> wordsByKey = new HashMap<>();

        wordsByKey.put("MUserId", MuserId);
        wordsByKey.put("MDeviceId", MDeviceId);
        wordsByKey.put("OTP", inputOTP.getText().toString().trim());
        //   wordsByKey.put("IMEI","38648723487236487264");

        baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.UPDATE_DEVICE_OTP1);/////

    }

    private void getUpdatedOTPResponse(DeviceAuthOTPModelView mDeviceAuthOTPModelView) {

        if(mDeviceAuthOTPModelView.getStatus())
        {
            DeviceAuthResponse mDeviceAuthResponse = mDeviceAuthOTPModelView.getResponse();

            if (mDeviceAuthResponse.getStatus())
            {
                progressDialog.dismiss();
                //  CustomUtility.isSuccessDialog(DeviceOTPActivity.this,getString(R.string.success),getString(R.string.success_device));
                //Toast.makeText(mContext,"Device verification is successfully done!" ,Toast.LENGTH_LONG).show();
                 finish();
                // Toast.makeText(mContext,R.string.success_device ,Toast.LENGTH_LONG).show();
                Message   msg2 = new Message();
                msg2.obj = "Device verification is successfully done!";
                mHandler.sendMessage(msg2);
                //Intent intent  = new Intent(  DeviceOTPActivity.this, MainActivity.class);
                Intent intent  = new Intent(  DeviceOTPActivity.this, BaseActivity.class);
                startActivity(intent);
            }
            else
            {
                progressDialog.dismiss();
                CustomUtility.isErrorDialog(DeviceOTPActivity.this,getString(R.string.error),getString(R.string.err_invaild_otp));
            }
        }

    }



    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(DeviceOTPActivity.this, mString, Toast.LENGTH_LONG).show();
        }
    };
}

