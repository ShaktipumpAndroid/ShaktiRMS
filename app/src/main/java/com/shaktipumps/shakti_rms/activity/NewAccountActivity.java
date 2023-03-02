package com.shaktipumps.shakti_rms.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
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
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.UserBeanOTP.UserRegistratinModel;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewAccountActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ProgressDialog progressDialog;
    private TextView btn_new_account;
    String otp = "null";
    private TextInputLayout input_layout_user_lastname,input_layout_user_firstname,inputLayoutUserName, inputLayoutPassword,inputLayoutConfirmPassword,
                           inputLayoutController,inputLayoutPhone,inputLayoutAddress;
    private EditText et_user_lastname,et_user_firstname,inputUserName, inputPassword,inputConfirmPassword,inputController,inputPhone,inputAddress;
    String   username       = "null" ,
            lastname       = "null" ,
            firstname       = "null" ,
            password        = "null" ,
            confirmPassword = "null",
            phone_no        = "null" ,
         //   controller      = "null" ,
            address         = "null";
    SharedPreferences.Editor editor;
    SharedPreferences pref ;
    String otp_status = "false",MuserId_status = "null", sms_url = "null",IsExist = "null",MobileNo_status = "null";
    Context mContext ;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        baseRequest = new BaseRequest(this);

        try {
            NewAccountActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            btn_new_account = (TextView) findViewById(R.id.btn_sign_up);

            inputLayoutUserName = (TextInputLayout) findViewById(R.id.input_layout_user_name);
            input_layout_user_firstname = (TextInputLayout) findViewById(R.id.input_layout_user_firstname);
            input_layout_user_lastname = (TextInputLayout) findViewById(R.id.input_layout_user_lastname);
            inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

            inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);
            // inputLayoutController = (TextInputLayout) findViewById(R.id.input_layout_controller_id);
            inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone_no);
            inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);


            inputUserName        = (EditText) findViewById(R.id.et_user_name);
            et_user_firstname        = (EditText) findViewById(R.id.et_user_firstname);
            et_user_lastname        = (EditText) findViewById(R.id.et_user_lastname);
            inputPassword        = (EditText) findViewById(R.id.et_password);
            inputConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
            //  inputController      = (EditText) findViewById(R.id.et_controller_id);
            inputPhone           = (EditText) findViewById(R.id.et_phone_no);
            inputAddress         = (EditText) findViewById(R.id.et_address);


            setSupportActionBar(mToolbar);


            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.action_new_account);

            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();

            mContext = this;


            btn_new_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    submitForm();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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


    /**********************************************************************************************
     *                Validating form
     *********************************************************************************************/
    private void submitForm() {

        if (!validateOnline()) {
            return;
        }


        if (!validateFirstName()) {
            return;
        }

        if (!validateLastName()) {
            return;
        }

        if (!validateUserName()) {
            return;
        }


        if (!validatePassword()) {
            return;
        }

        if (!validateConfirmPassword()) {
            return;
        }

//        if (!validateCotroller()) {
//            return;
//        }

        if (!validatePhone()) {
            return;
        }

        if (!validateAddress()) {
            return;
        }



        new AlertDialog.Builder(mContext)
                .setTitle("Alert !")
                .setMessage("Do you want to save data ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        serverLogin();

                        dialog.cancel();
                        dialog.dismiss();

                    }
                })


                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                        dialog.cancel();
                        dialog.dismiss();

                    }
                })
                .show();


    }

    private boolean validateUserName() {
        try {
            if (inputUserName.getText().toString().isEmpty()) {
                inputLayoutUserName.setError(getString(R.string.err_msg_name));
                requestFocus(inputUserName);
                return false;
            } else {
                inputLayoutUserName.setErrorEnabled(false);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    private boolean validateFirstName() {
        if (et_user_firstname.getText().toString().isEmpty()) {
            input_layout_user_firstname.setError(getString(R.string.err_msg_first_name));
            requestFocus(et_user_firstname);
            return false;
        } else {
            input_layout_user_firstname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLastName() {
        if (et_user_lastname.getText().toString().isEmpty()) {
            input_layout_user_lastname.setError(getString(R.string.err_msg_last_name));
            requestFocus(et_user_lastname);
            return false;
        } else {
            input_layout_user_lastname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean  validateOnline(){
        if (CustomUtility.isOnline(NewAccountActivity.this)) {
            return true;
        }

        CustomUtility.isErrorDialog(NewAccountActivity.this,getString(R.string.error),getString(R.string. err_internet));

        return false ;
    }




    private boolean validatePassword() {
        if (inputPassword.getText().toString().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePhone() {
        if (inputPhone.getText().toString().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone_no));
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }


        int len = inputPhone.getText().toString().trim().length();
        if (len < 10)
        {
            inputLayoutPhone.setError(getString(R.string.err_msg_valid_phone_no));
            requestFocus(inputPhone);
            return false;
        }


        return true;
    }

    private boolean validateConfirmPassword()
    {
        try {
            if (inputConfirmPassword.getText().toString().isEmpty()) {
                inputLayoutConfirmPassword.setError(getString(R.string.err_msg_confirm_password));
                requestFocus(inputConfirmPassword);
                return false;
            } else {
                inputLayoutConfirmPassword.setErrorEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (!inputConfirmPassword.getText().toString().equalsIgnoreCase(inputPassword.getText().toString()))
            {
                inputLayoutConfirmPassword.setError(getString(R.string.err_msg_password_match));
                requestFocus(inputConfirmPassword);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

//    private boolean validateCotroller() {
//        if (inputController.getText().toString().isEmpty()) {
//            inputLayoutController.setError(getString(R.string.err_msg_controller));
//            requestFocus(inputController);
//            return false;
//        } else {
//            inputLayoutController.setErrorEnabled(false);
//        }
//
//        return true;
//    }

    private boolean validateAddress() {
        if (inputAddress.getText().toString().isEmpty()) {
            inputLayoutAddress.setError(getString(R.string.err_msg_address));
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        return true;
    }




    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private  void serverLogin()
    {

        ArrayList<String> al;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        al = new ArrayList<>();

        lastname = et_user_lastname.getText().toString();
        firstname = et_user_firstname.getText().toString();
        username = inputUserName.getText().toString();
        password = inputPassword.getText().toString();
        confirmPassword = inputConfirmPassword.getText().toString();
       // controller = inputController.getText().toString();
        phone_no = inputPhone.getText().toString();
        address = inputAddress.getText().toString();

        NewSolarVFD.USERNAME_RG = username;
        NewSolarVFD.Password_RG = password;



        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();

        param.clear();
        otp_param.clear();

//        param.add(new BasicNameValuePair("UserName", username));
//        param.add(new BasicNameValuePair("Password", password));


        param.add(new BasicNameValuePair("MParentId","0"));
        param.add(new BasicNameValuePair("MUserId","0"));
        param.add(new BasicNameValuePair("firstName",  firstname));
        param.add(new BasicNameValuePair("lastName",  lastname));
        param.add(new BasicNameValuePair("MUserName",  username));
        param.add(new BasicNameValuePair("MPassword", password));
        param.add(new BasicNameValuePair("MobileNo",  phone_no));
        param.add(new BasicNameValuePair("MAddress",  address));
        param.add(new BasicNameValuePair("Status",  "true"));



        otp = ""+((int)(Math.random()*9000)+1000);

//        try {

/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(NewAccountActivity.this, "", "Please wait !");

        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(NewAccountActivity.this))
                {

                    try {

                        String obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.LOGIN_USER_REGISTRATION, param);


                        Log.d("obj22", "" + obj);
                        if (obj.equalsIgnoreCase(""))
                        {

/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/******************************************************************************************/

                           JSONObject jresponse = new JSONObject(obj);
//
         //Log.d("obj3", "" + jresponse.getString("MUserId") +"--"+ otp );



                            // userId_status != null i.e new user is successfully created on server
                            MuserId_status = jresponse.getString("MUserId") ;
                            MobileNo_status = jresponse.getString("MobileNo") ;

                            IsExist = jresponse.getString("IsExist") ;

                            if ( !MobileNo_status.equalsIgnoreCase("Already Exist"))
                            {

                            if (IsExist.equalsIgnoreCase("false"))
                            {

                            if(!MuserId_status.equalsIgnoreCase("null"))
                            {

                                /*// send otp to server
                                otp_param.add(new BasicNameValuePair("MUserId", "" + jresponse.getString("MUserId")));
                                otp_param.add(new BasicNameValuePair("OTP", otp));

                                String otp_return = CustomHttpClient.executeHttpPost1(NewSolarVFD.UPDATE_USER_OTP, otp_param);

                                JSONObject otp_json = new JSONObject(otp_return);

                                otp_status = otp_json.getString("Status");

                                // otp_status = true i.e otp is save on server database
                                if (otp_status.equalsIgnoreCase("true"))
                                {
                                   // Log.d("otp_status", "" +   otp_status    );

                                    //**** get sms string ************************
                                    String sms_obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.SMS_URL,param) ;

                                    JSONObject sms_response = new JSONObject(sms_obj);

                                    sms_url = sms_response.getString("SMS_URL");

                                    //**** end sms string ************************

                                    if (!sms_url.equalsIgnoreCase("null"))
                                    {
                                        // Log.d("send_sms1", "" +   sms_url    );

                                        String send_sms = sms_url.replaceAll("<reci_addr>", phone_no).trim();

                                        String msg = "Shakti Remote Monitoring User Verification OTP is" + "  " + otp;

                                        // Log.d("send_sms2", "" +     msg    );

                                        msg = URLEncoder.encode(msg);

                                        //  Log.d("send_sms3", "" +     msg    );

                                        send_sms = send_sms.replaceAll("<message>", msg).trim();

                                     //   Log.d("send_sms4", "" +     send_sms   );
                                        // send sms
                                        String sms_return = CustomHttpClient.executeHttpGet(send_sms);

                                        // set otp to shared preference
                                        editor.putString("key_OTP", otp);
                                        editor.putString("key_muserid",MuserId_status);
                                        editor.putString("key_login_username", username);
                                        editor.putString("key_mobile_number", phone_no);

                                        editor.commit();

//                                        Log.d("sms_send_return", "" + sms_return);

                                        progressDialog.dismiss();
                                        NewAccountActivity.this.finish();

                                        Intent intent = new Intent(NewAccountActivity.this, UserOTPActivity.class);
                                        startActivity(intent);

                                    }
                                }*/

                                callSendOTPAPI();
                                progressDialog.dismiss();

                            }

                        }

                            else
                            {

                                progressDialog.dismiss();
                                CustomUtility.isErrorDialog(NewAccountActivity.this,getString(R.string.error),getString(R.string.err_user_name));

                            }


                            }
                            else
                            {

                                progressDialog.dismiss();
                                CustomUtility.isErrorDialog(NewAccountActivity.this,getString(R.string.error),getString(R.string.err_Mobile_no_exist));

                            }
                        }
                        else
                        {
                            progressDialog.dismiss();
                            CustomUtility.isErrorDialog(NewAccountActivity.this,getString(R.string.error),getString(R.string.err_connection));


                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(NewAccountActivity.this,getString(R.string.error),getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }


                }

                else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(NewAccountActivity.this,"Error","No Internet Connection");


//                    Message   msg2 = new Message();
//                    msg2.obj = "No Internet Connection";
//                    mHandler.sendMessage(msg2);

                }


            }

        }.start();
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(NewAccountActivity.this, mString, Toast.LENGTH_LONG).show();
        }
    };


    private void callSendOTPAPI() {
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

                        Toast.makeText(mContext, mUserRegistratinModel.getMessage(), Toast.LENGTH_SHORT).show();
                        editor.putString("key_mobile_number", mUserRegistratinModel.getResponse().getMobileNo());
                        editor.putString("key_muserid", mUserRegistratinModel.getResponse().getMUserId());
                        editor.putString("key_OTP", mUserRegistratinModel.getResponse().getOTP());
                        editor.commit();

                        NewAccountActivity.this.finish();

                        Intent intent = new Intent(NewAccountActivity.this, UserOTPActivity.class);
                        startActivity(intent);
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
            jsonObject.addProperty("MUserId", MuserId_status);
            //jsonObject.addProperty("MDeviceId", MDeviceId);
            jsonObject.addProperty("MobileNo", MobileNo_status);


        } catch (Exception e) {
            e.printStackTrace();
        }
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////
        //baseRequest.callAPIPostLocal(1, jsonObject, NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////
    }

}
