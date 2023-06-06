package com.shaktipumps.shakti_rms.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.AllPopupUtil;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.Options_Activity;
import com.shaktipumps.shakti_rms.bean.LoginVK.LoginModelVk;
import com.shaktipumps.shakti_rms.bean.LoginVK.LoginResponse;
import com.shaktipumps.shakti_rms.bean.TimeZoneName;
import com.shaktipumps.shakti_rms.bean.UserBeanOTP.UserRegistratinModel;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.searchlist.TimeZoneListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    ListView lv_timezone;
    private TextInputLayout inputLayoutUserName, inputLayoutPassword;
    private EditText inputUserName, inputPassword;
    private ProgressDialog progressDialog;
    private TextView btn_login,  btn_new_account, btn_forgot_password;
    private ImageView txtGetBTDATABTNID1;
    String username = "null", password = "null", login_string = "null";
    final ArrayList<NameValuePair> sms_param = new ArrayList<NameValuePair>();

    String login = "null", active = "null", status = "null", mobileNo = "null", muserid = "null", mparentid = "null", clientid = "null";
    Context mContext;
    TimeZoneListViewAdapter adapter;
    ArrayList<TimeZoneName> arraylist;
    TimeZoneName timeZoneName;
    Locale myLocale;

    private  LoginResponse mLoginResponse;
    private int mCheck_app_type = 0;

    private DatabaseHelperTeacher databaseHelperTeacher;
    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Activity mActivity;
    private BaseRequest baseRequest;

    private boolean mCheckPasswordMask = false;

    private ImageView imgPasswordMaskID;
    private RelativeLayout rlvBTA1SSViewID, rlvBTDebugViewID, rlvBTExtractionViewID, rlvMAinTrnsViewID, rlvPopupViewID;
    private ImageView imgBTDebugID, imgA1SSBTID,imgBTExtrnID;
    TextView txtCancelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        LoginActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContext = this;
        mContext = this;
        mActivity = this;
        baseRequest = new BaseRequest(this);

        mCheck_app_type = getIntent().getIntExtra("APP_TYPE_CHECK", 1);

        databaseHelperTeacher = new DatabaseHelperTeacher(this);/////////////////AUTHModelData base
        inputLayoutUserName = (TextInputLayout) findViewById(R.id.input_layout_user_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputUserName = (EditText) findViewById(R.id.et_user_name);
        inputPassword = (EditText) findViewById(R.id.et_password);
        btn_login = (TextView) findViewById(R.id.btn_login);
        btn_new_account = (TextView) findViewById(R.id.btn_new_account);
        txtGetBTDATABTNID1 = (ImageView) findViewById(R.id.txtGetBTDATABTNID1);
        btn_forgot_password = (TextView) findViewById(R.id.btn_forgot_password);
        txtCancelID = (TextView) findViewById(R.id.txtCancelID);

        imgPasswordMaskID = (ImageView) findViewById(R.id.imgPasswordMaskID);

        rlvPopupViewID = (RelativeLayout) findViewById(R.id.rlvPopupViewID);
        rlvBTDebugViewID = (RelativeLayout) findViewById(R.id.rlvBTDebugViewID);

        rlvBTA1SSViewID = (RelativeLayout) findViewById(R.id.rlvBTA1SSViewID);

        rlvMAinTrnsViewID = (RelativeLayout) findViewById(R.id.rlvMAinTrnsViewID);
        rlvBTExtractionViewID = (RelativeLayout) findViewById(R.id.rlvBTExtractionViewID);
        imgBTDebugID = (ImageView) findViewById(R.id.imgBTDebugID);
        imgBTExtrnID = (ImageView) findViewById(R.id.imgBTExtrnID);
        imgA1SSBTID = (ImageView) findViewById(R.id.imgA1SSBTID);

        lv_timezone = (ListView) findViewById(R.id.lv_timezone);
        arraylist = new ArrayList<TimeZoneName>();

//        btnforgot = (TextView) findViewById(R.id.tv_forgot);
        inputUserName.addTextChangedListener(new MyTextWatcher(inputUserName));
        // inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        SetTimeZone();

////****************************************************************


        imgPasswordMaskID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rlvMAinTrnsViewID.setVisibility(View.GONE);
             //   rlvMAinTrnsViewID.setVisibility(View.GONE);

                if(mCheckPasswordMask)
                {
                    System.out.println("right");
                    mCheckPasswordMask = false;
                  //  inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                  //  inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgPasswordMaskID.setImageResource(R.drawable.ic_eye_gray);
                   // imgPasswordMaskID.setImageResource(R.drawable.ic_select_icon);
                }
                else
                {
                    System.out.println("wrong");
                    mCheckPasswordMask = true;
                    //inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                   // inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgPasswordMaskID.setImageResource(R.drawable.ic_eye_blue);
                    //imgPasswordMaskID.setImageResource(R.drawable.ic_unselect_icon);

                }
            }
        });


        txtCancelID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rlvMAinTrnsViewID.setVisibility(View.GONE);
                rlvMAinTrnsViewID.setVisibility(View.GONE);

            }
        });

        rlvPopupViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rlvMAinTrnsViewID.setVisibility(View.GONE);

            }
        });
        rlvMAinTrnsViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlvMAinTrnsViewID.setVisibility(View.GONE);

            }
        });

        rlvBTDebugViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBTExtrnID.setImageDrawable(getResources().getDrawable(R.drawable.uncheck_ring));
                imgBTDebugID.setImageDrawable(getResources().getDrawable(R.drawable.check_ring));
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {
                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                            Intent intent = new Intent(mContext, PairedDeviceActivityDebugNologin.class);
                            mContext.startActivity(intent);
                        }
                        else
                        {
                            // Intent intent = new Intent(mContext, GetBTDATAListActivity.class);
                            //Intent intent = new Intent(mContext, ShaktiTerminalActivity.class);
                            Intent intent = new Intent(mContext, GetBTDebugDataActivity.class);
                            intent.putExtra("BtNameHead",Constant.BT_DEVICE_NAME );
                            intent.putExtra("BtMacAddressHead",Constant.BT_DEVICE_MAC_ADDRESS );
                            mContext.startActivity(intent);
                            //((Activity)mContext).finish();
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                }
            }
        });

        rlvBTA1SSViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgA1SSBTID.setImageDrawable(getResources().getDrawable(R.drawable.check_ring));
                imgBTExtrnID.setImageDrawable(getResources().getDrawable(R.drawable.uncheck_ring));
                imgBTDebugID.setImageDrawable(getResources().getDrawable(R.drawable.uncheck_ring));
                //BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                          //  Intent intent = new Intent(mContext, Activity_pairdevice_for_mqtt.class);
                            Intent intent = new Intent(mContext, AoneSSPairedDeviceActivitynoLogin.class);
                            mContext.startActivity(intent);
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                }
                /*if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                            Intent intent = new Intent(mContext, PairedDeviceActivityDebugNologin.class);
                            mContext.startActivity(intent);
                        }
                        else
                        {
                            // Intent intent = new Intent(mContext, GetBTDATAListActivity.class);
                            //Intent intent = new Intent(mContext, ShaktiTerminalActivity.class);
                            Intent intent = new Intent(mContext, GetBTDebugDataActivity.class);
                            intent.putExtra("BtNameHead",Constant.BT_DEVICE_NAME );
                            intent.putExtra("BtMacAddressHead",Constant.BT_DEVICE_MAC_ADDRESS );
                            mContext.startActivity(intent);
                            //((Activity)mContext).finish();
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                }
*/

            }
        });
        rlvBTExtractionViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBTExtrnID.setImageDrawable(getResources().getDrawable(R.drawable.check_ring));
                imgBTDebugID.setImageDrawable(getResources().getDrawable(R.drawable.uncheck_ring));
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                            Intent intent = new Intent(mContext, PairedDeviceActivityNologin.class);
                            mContext.startActivity(intent);
                        }
                        else
                        {
                            //Constant.BT_DEVICE_NAME = mDeviceNameList.get(position).toString();
                            //Constant.BT_DEVICE_MAC_ADDRESS = mDeviceMACAddressList.get(position).toString();
                            // Intent intent = new Intent(mContext, GetBTDATAListActivity.class);
                            Intent intent = new Intent(mContext, GetBTDATAWithoutLoginActivity.class);
                            intent.putExtra("BtNameHead",Constant.BT_DEVICE_NAME);
                            intent.putExtra("BtMacAddressHead",Constant.BT_DEVICE_MAC_ADDRESS);
                            mContext.startActivity(intent);
                          //  ((Activity)mContext).finish();
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                   // check_Permission(mContext);
                    checkAndRequestPermissions();

                } else {
                    submitForm();
                }*/
        /*        if (!checkAndRequestPermissions()) {
                   // check_Permission(mContext);
                    checkAndRequestPermissions();

                } else {
                    submitForm();
                }*/
       /* if(checkAndRequestPermissions())
        {
            submitForm();
        }*/
                submitForm();
            }
        });


        txtGetBTDATABTNID1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rlvMAinTrnsViewID.setVisibility(View.VISIBLE);

              /*  BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {
                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                            Intent intent = new Intent(mContext, PairedDeviceActivityNologin.class);
                            mContext.startActivity(intent);
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                }*/
            }

        });


        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             /*if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) { check_Permission(mContext);
                } else {
                    Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                    startActivity(intent);
                }*/
                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        });


        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE = 0;

                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);

            }
        });

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_user_name:

                    break;

//                case R.id.et_password:
//
//                    break;

            }

        }
    }

    /**********************************************************************************************
     *                Validating form
     *********************************************************************************************/
    private void submitForm() {

//        if (!validateOnline()) {
//            return;
//        }

        if (!validateUserName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        username = inputUserName.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        callLoginAPI();
       // serverLogin();

    }

    private boolean validateUserName() {
        try {
            if (inputUserName.getText().toString().trim().isEmpty()) {
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

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    /**********************************************************************************************
     *                Server Login
     *********************************************************************************************/
    private void serverLogin() {

        ArrayList<String> al;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        al = new ArrayList<>();

        username = inputUserName.getText().toString();
        password = inputPassword.getText().toString();

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        param.add(new BasicNameValuePair("sms", "sms"));
//        try {
/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "Connecting to server..please wait !");
        // set login string
        login_string = NewSolarVFD.LOGIN_USER_REGISTRATION + "?MUserName=" + username + "&MPassword=" + password;

        // Log.d("obj2", "" + login_string );
        new Thread() {
            public void run() {
                if (CustomUtility.isOnline(LoginActivity.this)) {
                    try {
                        String obj = CustomHttpClient.executeHttpGet(login_string);

                        Log.d("obj_login", "" + obj);
                        if (obj != null) {
/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/*****************************************************************************************/
                            JSONObject jresponse = new JSONObject(obj);
                            System.out.println("jresponsenirmal==>>"+jresponse);
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

                                    editor.putString("key_login_username", username);
                                    editor.putString("key_mobile_number", mobileNo);
                                    editor.putString("key_muserid", muserid);
                                    editor.putString("key_mparentid", mparentid);
                                    editor.putString("key_clientid", clientid);

                                    String mLoginResponseData = databaseHelperTeacher.checkUserAlreadyExist(username, password);

                                    String mInfoArray[] = mLoginResponseData.split("DHARSHANSIR");

                                    if (mInfoArray[1].equalsIgnoreCase("000")) {
                                        databaseHelperTeacher.updateLoginData(
                                                muserid,
                                                mparentid,
                                                username,
                                                password,
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
                                                username,
                                                password,
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
                                        LoginActivity.this.finish();
                                        //    String sssCheckValue = SharedPreferencesUtil.getData(LoginActivity.this, Constant.CHECK_APP_DEVICE_TYPE);
                                        if (mCheck_app_type == 1)
                                        //  if(sssCheckValue.equalsIgnoreCase("1"))
                                        {
                                            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
                                            SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "1");
                                            // Intent intent = new Intent(LoginActivity.this, PairedDeviceActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "2");
                                            Intent intent = new Intent(LoginActivity.this, Options_Activity.class);
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
                                    CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_user_block));
                                }

                            } else {
                                progressDialog.dismiss();
                                CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_invalid_user_password));

                            }
//                                    if (    login.equalsIgnoreCase("false") )
//                                    {
//                                            progressDialog.dismiss();
                            //                    CustomUtility.isErrorDialog(LoginActivity.this,getString(R.string.error),getString(R.string.err_invalid_user_password));

                        } else {
                            progressDialog.dismiss();

                            CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_connection));
                        }

                    } catch (Exception e) {

                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }

                } else {
//// for testing purpose
                    String mLoginResponseData = databaseHelperTeacher.checkUserLoginData(username, password);


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
                            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
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

    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(LoginActivity.this, mString, Toast.LENGTH_LONG).show();
        }
    };


    public static void check_Permission(final Context context) {

//        ActivityCompat.requestPermissions((Activity) context,
//                new String[]{Manifest.permission.READ_PHONE_STATE},
//                PermissionsIntent.READ_PHONE_STATE);

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PermissionsIntent.READ_PHONE_STATE);


        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PermissionsIntent.READ_PHONE_STATE);
        }
    }


    private boolean checkAndRequestPermissions() {

        int LOCATION = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        // int CAMERAV = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

       /* if (CAMERAV != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }*/
        return true;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

            // if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(mContext, getResources().getString(R.string.Permission_granted_now_you_can_use_user_location_text), Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(mContext, getResources().getString(R.string.Oops_you_just_denied_the_permission_text), Toast.LENGTH_LONG).show();
                // checkAndRequestPermissions();
            }

            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(mContext, getResources().getString(R.string.Permission_granted_now_you_can_use_camera_text), Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(mContext, getResources().getString(R.string.Oops_you_just_denied_the_permission_text), Toast.LENGTH_LONG).show();
                // checkAndRequestPermissions();
            }
        }
    }

    public void SetTimeZone() {


        try {

            timeZoneName = new TimeZoneName();


//********************** set mobile default time zone ***********************

            if (!pref.getString("key_time_zone_change", "not").equalsIgnoreCase("X")) {
                timeZoneName.setTimezone_city(TimeZone.getDefault().getID());
                timeZoneName.setTimezone_long(TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.LONG));
                timeZoneName.setTimezone_short(TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.SHORT));


                editor.putString("key_time_zone_city", TimeZone.getDefault().getID());
                editor.putString("key_time_zone_short", TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.SHORT));
                editor.putString("key_time_zone_long", TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.LONG));
                editor.putString("key_time_zone_change", "X");

                editor.commit();


//********************** end set mobile default time zone ***********************
            } else {

                timeZoneName.setTimezone_city(pref.getString("key_time_zone_city", ""));
                timeZoneName.setTimezone_long(pref.getString("key_time_zone_long", ""));
                timeZoneName.setTimezone_short(pref.getString("key_time_zone_short", ""));

            }


            arraylist.add(timeZoneName);
            adapter = new TimeZoneListViewAdapter(mContext, arraylist);

            lv_timezone.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //  Log.d("requestCode",""+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 555) {
            ((Activity) mContext).finish();
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(intent);
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

                    UserRegistratinModel mUserRegistratinModel = gson.fromJson(Json, UserRegistratinModel.class);

                    if (mUserRegistratinModel.getStatus()) {
                        /*Intent mIntent = new Intent(mContext,OTPForgotPasswordActivity.class);
                        mIntent.putExtra("USER_NAME",mUsernameStr);
                        startActivity(mIntent);*/

                        Toast.makeText(mContext, mUserRegistratinModel.getMessage(), Toast.LENGTH_SHORT).show();

                        editor.putString("key_OTP", mUserRegistratinModel.getResponse().getOTP());
                        editor.commit();

                        finish();

                        Intent intent = new Intent(LoginActivity.this, UserOTPActivity.class);
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
            jsonObject.addProperty("MUserId", muserid);
            //jsonObject.addProperty("MDeviceId", MDeviceId);
            jsonObject.addProperty("MobileNo", mobileNo);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////
        //baseRequest.callAPIPostLocal(1, jsonObject, NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////
    }



    public void ShowHidePass(View view){

        /*if(view.getId()==R.id.show_pass_btn){

            if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView(view)).setImageResource(R.drawable.hide_password);

                //Show Password
                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.show_password);

                //Hide Password
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }*/
    }



    private void callLoginAPI() {

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        LoginModelVk mLoginModelVk = gson.fromJson(Json, LoginModelVk.class);
                        getLoginResponse(mLoginModelVk);

                    } catch (Exception e) {
                        baseRequest.hideLoader();
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


            Map<String, String> wordsByKey = new HashMap<>();


            wordsByKey.put("MUserName", username);
            wordsByKey.put("MPassword", password);
            //   wordsByKey.put("IMEI","38648723487236487264");

            baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.LOGIN_USER_REGISTRATION1);/////

        }
        else
        {
            Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void getLoginResponse(LoginModelVk mLoginModelVk) {

        if (mLoginModelVk.getStatus()) {
            baseRequest.hideLoader();

            mLoginResponse =mLoginModelVk.getResponse();

            login = mLoginResponse.getIsLogin();

            status = mLoginResponse.getStatus();

            if (login.equalsIgnoreCase("true"))   // password check
            {

                if (status.equalsIgnoreCase("true"))  // login status check
                {

                    active = mLoginResponse.getActive();

                    mobileNo = mLoginResponse.getMobileNo();
                    muserid = mLoginResponse.getMUserId();
                    mparentid = mLoginResponse.getMParentId();
                    clientid = mLoginResponse.getClientName();
                    //  Log.d("login_clientid",clientid);

                    editor.putString("key_login_username", username);
                    editor.putString("key_mobile_number", mobileNo);
                    editor.putString("key_muserid", muserid);
                    editor.putString("key_mparentid", mparentid);
                    editor.putString("key_clientid", clientid);

                    String mLoginResponseData = databaseHelperTeacher.checkUserAlreadyExist(username, password);

                    String mInfoArray[] = mLoginResponseData.split("DHARSHANSIR");

                    if (mInfoArray[1].equalsIgnoreCase("000")) {
                        databaseHelperTeacher.updateLoginData(
                                muserid,
                                mparentid,
                                username,
                                password,
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
                                username,
                                password,
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

                        baseRequest.hideLoader();
                        LoginActivity.this.finish();
                        //    String sssCheckValue = SharedPreferencesUtil.getData(LoginActivity.this, Constant.CHECK_APP_DEVICE_TYPE);
                        if (mCheck_app_type == 1)
                        //  if(sssCheckValue.equalsIgnoreCase("1"))
                        {
                            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
                            SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "1");
                            // Intent intent = new Intent(LoginActivity.this, PairedDeviceActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "2");
                            Intent intent = new Intent(LoginActivity.this, Options_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {

                        if (login.equalsIgnoreCase("true") && active.equalsIgnoreCase("false")) {
                         //   progressDialog.dismiss();
                            baseRequest.hideLoader();

                            // send to otp screen

                            callSendOTPAPI();
                                          /*  Intent intent = new Intent(LoginActivity.this, UserOTPActivity.class);
                                            startActivity(intent);*/

                        }
                    }
                } else {
                    baseRequest.hideLoader();
                    CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_user_block));
                }

            } else {
                baseRequest.hideLoader();
                CustomUtility.isErrorDialog(LoginActivity.this, getString(R.string.error), getString(R.string.err_invalid_user_password));

            }


        }
    }

}

