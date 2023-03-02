package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

public class ChangePasswordActivity extends AppCompatActivity {

    private Context mContext = null;

    private Toolbar mToolbar;

    private TextView txtNextBTNID;
    private EditText edtConfirmPassID;

    private BaseRequest baseRequest;

    private String mUsernameStr;

    private EditText edtUserNameID;
    private EditText edtnewPassID;
    private EditText edtOldPassID;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    String mUsernamePrefSTR;


    private String mConfirmStr;
    private String mOldPassStr;
    private String mNewPassStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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

        baseRequest = new BaseRequest(this);

        //key_login_username
        mUsernamePrefSTR = pref.getString("key_login_username", "login_status");

        getSupportActionBar().setTitle(R.string.action_chnagePassword);

        txtNextBTNID = (TextView) findViewById(R.id.txtNextBTNID);
        edtConfirmPassID = (EditText) findViewById(R.id.edtConfirmPassID);

        edtOldPassID = (EditText) findViewById(R.id.edtOldPassID);

        edtnewPassID = (EditText) findViewById(R.id.edtnewPassID);


        txtNextBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkValidation()) {
                    callSendOTPAPI();
                }
            }
        });

    }

    private Boolean checkValidation() {

        mOldPassStr = edtOldPassID.getText().toString().trim();
        mNewPassStr = edtnewPassID.getText().toString().trim();
        mConfirmStr = edtConfirmPassID.getText().toString().trim();

        try {
            if (mOldPassStr.isEmpty()) {
                Toast.makeText(mContext, "Please enter old password!.", Toast.LENGTH_SHORT).show();
                return false;
            } else if (mNewPassStr.isEmpty()) {
                Toast.makeText(mContext, "Please enter password!", Toast.LENGTH_SHORT).show();
                return false;
            } else if (mConfirmStr.isEmpty()) {
                Toast.makeText(mContext, "Please enter confirm password!", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!mNewPassStr.equals(mConfirmStr)) {
                Toast.makeText(mContext, "Password not match!", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
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
            jsonObject.addProperty("NewPassword", mNewPassStr);
            jsonObject.addProperty("OldPassword", mOldPassStr);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.CHANGE_PASSWORD_API);/////
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE
        if (Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE == 2) {
            finish();
        } else {

        }
    }

}
