package com.shaktipumps.shakti_rms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
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
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.Options_Activity;
import com.shaktipumps.shakti_rms.bean.UserBeanOTP.UserRegistratinModel;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;

public class UserOTPActivity extends AppCompatActivity {

    private TextView btn_send_otp,btn_verify_otp;
    private TextInputLayout inputLayoutOTP;
    private EditText inputOTP;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    private Toolbar mToolbar;
    private Context mContext;
    String otp = "null",sms_url = "null",phone_no = "null";

    String mUserID = "";

    String otp_status = "false" ;
    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    private ProgressDialog progressDialog;
    private BaseRequest baseRequest;

    private DatabaseHelperTeacher databaseHelperTeacher;
    int counterTM = 20;
    Timer T=new Timer();
    private CountDownTimer countDownTimer;
    private TextView txtTimerCounterID;
    private  String login_string;

    String login = "null", active = "null", status = "null", mobileNo = "null", muserid = "null", mparentid = "null", clientid = "null";

    private int mCheck_app_type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        UserOTPActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        baseRequest = new BaseRequest(this);

        setSupportActionBar(mToolbar);
        databaseHelperTeacher = new DatabaseHelperTeacher(this);/////////////////AUTHModelData base
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_verify_account);

        inputLayoutOTP  = (TextInputLayout) findViewById(R.id.input_layout_otp);
        inputOTP        = (EditText) findViewById(R.id.et_otp);
        btn_send_otp    = (TextView) findViewById(R.id.btn_send_otp);
        btn_verify_otp  = (TextView) findViewById(R.id.btn_verify_otp);
        txtTimerCounterID  = (TextView) findViewById(R.id.txtTimerCounterID);

        txtTimerCounterID.setVisibility(View.GONE);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP();
            }
        });



        btn_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  reSendOTP();
                callSendOTPAPI();
                changeButtonVisibilityRLV(false, 0.5f,btn_send_otp);

                startTimer(30000);

            }
        });
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

       // Log.d("key_otp",inputOTP.getText().toString()+"--"+pref.getString("key_OTP", "invalid_otp")+"--"+pref.getString("key_otp_for_user", "invalid_otp_user")+"--"+ pref.getString("key_login_username", "invalid_login_username"));

        if (inputOTP.getText().toString().equalsIgnoreCase(pref.getString("key_OTP", "invalid_otp")))
        {
            updateStatusOTP();
        }
         else
        {
            CustomUtility.isErrorDialog(UserOTPActivity.this,getString(R.string.error),getString(R.string.err_invaild_otp));
        }
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

    private void reSendOTP() {

        otp = ""+((int)(Math.random()*9000)+1000);

        progressDialog = ProgressDialog.show(UserOTPActivity.this, "", "Please wait !");

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();

        param.clear();
        otp_param.clear();

       // otp_param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));
       // otp_param.add(new BasicNameValuePair("OTP", otp));

     //   Log.d("update_otp3",""+ pref.getString("key_muserid", "invalid_muserid")) ;

        phone_no = pref.getString("key_mobile_number", "invalid_mobile_no") ;


        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(UserOTPActivity.this)) {

                    try     {


                        /*String otp_return = CustomHttpClient.executeHttpPost1(NewSolarVFD.UPDATE_USER_OTP, otp_param);

                        JSONObject otp_json = new JSONObject(otp_return);

                        otp_status = otp_json.getString("Status");

                        // otp_status = true i.e otp is save on server database
                        if (otp_status.equalsIgnoreCase("true"))
                        {
        //**  set otp to shared preference
                            editor.putString("key_OTP", otp);
                            editor.commit();

                            param.clear();

                            //**** get sms string ************************
                            String sms_obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.SMS_URL, param);


                            JSONObject sms_response = new JSONObject(sms_obj);

                            sms_url = sms_response.getString("SMS_URL");
                            //**** end sms string ************************

                            if (!sms_url.equalsIgnoreCase("null"))
                            {
                                String send_sms = sms_url.replaceAll("<reci_addr>", phone_no).trim();

                                String msg = "Shakti Remote Monitoring Verification OTP is" + "  " + otp;

                                msg = URLEncoder.encode(msg);

                                send_sms = send_sms.replaceAll("<message>", msg).trim();

                                // send sms
                                String sms_return = CustomHttpClient.executeHttpGet(send_sms);

                             //   Log.d("sms_send_return", "" +     send_sms + "--"+sms_return +"--"+    otp_status );
                                // set otp to shared preference
                                editor.putString("key_OTP", otp);
                                editor.putString("key_otp_for_user", pref.getString("key_login_username", "invalid_login_username"));

                                editor.commit();

                             //   Log.d("sms_send_return", "" + sms_return);

                                progressDialog.dismiss();
                                UserOTPActivity.this.finish();

                                Intent intent = new Intent(UserOTPActivity.this, UserOTPActivity.class);
                                startActivity(intent);

                            }

                        }*/

                        callSendOTPAPI();

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(UserOTPActivity.this,getString(R.string.error),getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }

                }

                else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(UserOTPActivity.this,"Error","No Internet Connection");
//                    Message   msg2 = new Message();
//                    msg2.obj = "No Internet Connection";
//                    mHandler.sendMessage(msg2);

                }
            }

        }.start();

    }

    public void updateStatusOTP() {
        {

            ArrayList<String> al;

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);

            al = new ArrayList<>();

            final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();

            param.clear();
            otp_param.clear();

            otp_param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));
            otp_param.add(new BasicNameValuePair("OTP", pref.getString("key_OTP", "invalid_otp")));

            sms_url =   NewSolarVFD.UPDATE_USER_OTP +"?"+ "MUserId=" + pref.getString("key_muserid", "invalid_muserid") + "&"+ "OTP="+ pref.getString("key_OTP", "invalid_otp") ;

/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
            progressDialog = ProgressDialog.show(UserOTPActivity.this, "", "Please wait !");

            new Thread() {

                public void run() {

                    if (CustomUtility.isOnline(UserOTPActivity.this)) {

                        try {

                            String obj = CustomHttpClient.executeHttpGet(sms_url);

                            if (obj.equalsIgnoreCase(""))
                            {
/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/******************************************************************************************/

                                JSONObject jresponse = new JSONObject(obj);

                                otp_status = jresponse.getString("Status");

                                if (otp_status.equalsIgnoreCase("true"))
                                {

                                    UserOTPActivity.this.finish();
                                    progressDialog.dismiss();
                                // set login status
                                    editor.putString("key_login", "Y");
                                    editor.commit();

                                    SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "1");


                                  /*  Intent intent = new Intent(UserOTPActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();*/

                                    /////////////popopopppopopop

                                    serverLoginVK();

                                    finish();
                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(UserOTPActivity.this,getString(R.string.error),getString(R.string.err_invaild_otp));
                                }

                            }
                            else
                            {
                                progressDialog.dismiss();
                                CustomUtility.isErrorDialog(UserOTPActivity.this,getString(R.string.error),getString(R.string.err_connection));


                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                            CustomUtility.isErrorDialog(UserOTPActivity.this,getString(R.string.error),getString(R.string.err_connection));
                            Log.d("exce", "" + e);
                        }

                    }

                    else {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(UserOTPActivity.this,"Error","No Internet Connection");

                    }

                }

            }.start();
        }
    }


    private void serverLoginVK() {

        ArrayList<String> al;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        al = new ArrayList<>();

       // username = inputUserName.getText().toString();
      //  password = inputPassword.getText().toString();

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        param.add(new BasicNameValuePair("sms", "sms"));
//        try {
/******************************************************************************************/
/*                   server connection
/******************************************************************************************/

       // NewSolarVFD.USERNAME_RG = username;
       // NewSolarVFD.Password_RG = password;
        progressDialog = ProgressDialog.show(mContext, "", "Connecting to server..please wait !");
        // set login string
         login_string = NewSolarVFD.LOGIN_USER_REGISTRATION + "?MUserName=" +  NewSolarVFD.USERNAME_RG + "&MPassword=" +  NewSolarVFD.Password_RG;

        // Log.d("obj2", "" + login_string );

        new Thread() {
            public void run() {
                if (CustomUtility.isOnline(mContext)) {
                    try {
                        String obj = CustomHttpClient.executeHttpGet(login_string);

                        Log.d("obj_login", "" + obj);
                        if (obj.equalsIgnoreCase("")) {
/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/*****************************************************************************************/
                            JSONObject jresponse = new JSONObject(obj);
/******************************************************************************************/
/*                       Call DashBoard
/******************************************************************************************/
                            login = jresponse.getString("IsLogin");

                            status = jresponse.getString("Status");

                            if (login.equalsIgnoreCase("true"))   // password check
                            {

                                if (status.equalsIgnoreCase("true"))  // login status check
                                {

                                    active = jresponse.getString("Active");

                                    mobileNo = jresponse.getString("MobileNo");
                                    muserid = jresponse.getString("MUserId");
                                    mparentid = jresponse.getString("MParentId");
                                    clientid = jresponse.getString("ClientName");
                                    //  Log.d("login_clientid",clientid);

                                    editor.putString("key_login_username", NewSolarVFD.USERNAME_RG);
                                    editor.putString("key_mobile_number", mobileNo);
                                    editor.putString("key_muserid", muserid);
                                    editor.putString("key_mparentid", mparentid);
                                    editor.putString("key_clientid", clientid);

                                    String mLoginResponseData = databaseHelperTeacher.checkUserAlreadyExist(NewSolarVFD.USERNAME_RG, NewSolarVFD.Password_RG);

                                    String mInfoArray[] = mLoginResponseData.split("DHARSHANSIR");

                                    if (mInfoArray[1].equalsIgnoreCase("000")) {
                                        databaseHelperTeacher.updateLoginData(
                                                muserid,
                                                mparentid,
                                                NewSolarVFD.USERNAME_RG,
                                                NewSolarVFD.Password_RG,
                                                mobileNo,
                                                clientid,
                                                login,
                                                status,
                                                active,
                                                mInfoArray[0]);
                                    } else {
                                        databaseHelperTeacher.insertLoginData(
                                                muserid,
                                                mparentid,
                                                NewSolarVFD.USERNAME_RG,
                                                NewSolarVFD.Password_RG,
                                                mobileNo,
                                                clientid,
                                                login,
                                                status,
                                                active);
                                    }


                                    editor.commit();

                                    if (login.equalsIgnoreCase("true") && active.equalsIgnoreCase("true")) {

                                        editor.putString("key_login", "Y");
                                        editor.commit();

                                        progressDialog.dismiss();
                                        finish();
                                        //    String sssCheckValue = SharedPreferencesUtil.getData(LoginActivity.this, Constant.CHECK_APP_DEVICE_TYPE);
                                        if (NewSolarVFD.APP_TYPE_CHECKVK == "1")
                                        //  if(sssCheckValue.equalsIgnoreCase("1"))
                                        {
                                            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            Intent intent = new Intent(mContext, BaseActivity.class);
                                            SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "1");
                                            // Intent intent = new Intent(LoginActivity.this, PairedDeviceActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "2");
                                            Intent intent = new Intent(mContext, Options_Activity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {

                                        if (login.equalsIgnoreCase("true") && active.equalsIgnoreCase("false")) {
                                            progressDialog.dismiss();
                                            // send to otp screen
                                            callSendOTPAPI();
                                          /*  Intent intent = new Intent(LoginActivity.this, UserOTPActivity.class);
                                            startActivity(intent);*/
                                        }
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_user_block));
                                }

                            } else {
                                progressDialog.dismiss();
                                CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_invalid_user_password));

                            }
//                                    if (    login.equalsIgnoreCase("false") )
//                                    {
//                                            progressDialog.dismiss();
                            //                    CustomUtility.isErrorDialog(LoginActivity.this,getString(R.string.error),getString(R.string.err_invalid_user_password));

                        } else {
                            progressDialog.dismiss();

                            CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_connection));
                        }

                    } catch (Exception e) {

                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }

                } else {
//// for testing purpose
                    String mLoginResponseData = databaseHelperTeacher.checkUserLoginData(NewSolarVFD.USERNAME_RG, NewSolarVFD.Password_RG);


                    try {
                        String mInfoArray[] = mLoginResponseData.split("SAK000IVS");
                        System.out.println("mInfoArraymInfoArraymInfoArraymInfoArray>>" + mInfoArray[3]);

                        editor.putString("key_login_username", mInfoArray[3]);
                        editor.putString("key_mobile_number", mInfoArray[4]);
                        editor.putString("key_muserid", mInfoArray[1]);
                        editor.putString("key_mparentid", mInfoArray[2]);
                        editor.putString("key_clientid", mInfoArray[5]);

                        mobileNo = mInfoArray[4];
                        muserid = mInfoArray[1];


                        if (mInfoArray[6].equalsIgnoreCase("true") && mInfoArray[8].equalsIgnoreCase("true")) {

                            editor.putString("key_login", "Y");
                            editor.commit();

                            progressDialog.dismiss();

                            // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Intent intent = new Intent(mContext, BaseActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            if (mInfoArray[6].equalsIgnoreCase("true") && mInfoArray[8].equalsIgnoreCase("false")) {
                                progressDialog.dismiss();
                                // send to otp screen
                                callSendOTPAPI();
                                /*Intent intent = new Intent(LoginActivity.this, UserOTPActivity.class);
                                startActivity(intent);*/
                            }
                        }

                        // Toast.makeText(mContext, "mInfoArray [3] ==>" +mInfoArray [3], Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    progressDialog.dismiss();
                    //  CustomUtility.isErrorDialog(LoginActivity.this,"Error","No Internet Connection");

                    //  CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_internet));

//                    Message   msg2 = new Message();
//                    msg2.obj = "No Internet Connection";
//                    mHandler.sendMessage(msg2);

                }

            }

        }.start();
    }

    private void callSendOTPAPI() {
        phone_no = pref.getString("key_mobile_number", "invalid_mobile_no") ;
        mUserID = pref.getString("key_muserid", "invalid_muserid");
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    UserRegistratinModel mUserRegistratinModel = gson.fromJson(Json, UserRegistratinModel.class);

                    if (mUserRegistratinModel.getStatus()) {
                        /*Intent mIntent = new Intent(mContext,OTPForgotPasswordActivity.class);
                        mIntent.putExtra("USER_NAME",mUsernameStr);
                        startActivity(mIntent);*/

                        editor.putString("key_OTP", mUserRegistratinModel.getResponse().getOTP());
                        editor.commit();


                        Toast.makeText(mContext, mUserRegistratinModel.getMessage(), Toast.LENGTH_SHORT).show();

                     /*finish();

                        Intent intent = new Intent(mContext, UserOTPActivity.class);
                        startActivity(intent);*/
                        //  Log.d("intent1",MuserId +"--"+  MDeviceId +"--"+ Mobile_no);



                    } else {
                        Toast.makeText(mContext, mUserRegistratinModel.getMessage(), Toast.LENGTH_SHORT).show();
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
            jsonObject.addProperty("MUserId", mUserID);
            //jsonObject.addProperty("MDeviceId", MDeviceId);
            jsonObject.addProperty("MobileNo", phone_no);

        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject,  NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////
        //baseRequest.callAPIPostLocal(1, jsonObject, NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////
    }

}

