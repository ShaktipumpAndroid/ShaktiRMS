package com.shaktipumps.shakti_rms.activity;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    private final int REQUEST_CODE_PERMISSION = 123;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    ListView lv_timezone;
    private TextInputLayout inputLayoutUserName, inputLayoutPassword;
    private EditText inputUserName, inputPassword;
    private ProgressDialog progressDialog;
    private TextView btn_login, txtGetBTDATABTNID, btn_new_account, btn_forgot_password;
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


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        SetTimeZone();

        imgPasswordMaskID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckPasswordMask) {
                    System.out.println("right");
                    mCheckPasswordMask = false;
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgPasswordMaskID.setImageResource(R.drawable.ic_eye_gray);
                }
                else {
                    System.out.println("wrong");
                    mCheckPasswordMask = true;
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgPasswordMaskID.setImageResource(R.drawable.ic_eye_blue);

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
                            Intent intent = new Intent(mContext, GetBTDebugDataActivity.class);
                            intent.putExtra("BtNameHead",Constant.BT_DEVICE_NAME );
                            intent.putExtra("BtMacAddressHead",Constant.BT_DEVICE_MAC_ADDRESS );
                            mContext.startActivity(intent);
                       }
                    } else {
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
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
                            Intent intent = new Intent(mContext, GetBTDATAWithoutLoginActivity.class);
                            intent.putExtra("BtNameHead",Constant.BT_DEVICE_NAME);
                            intent.putExtra("BtMacAddressHead",Constant.BT_DEVICE_MAC_ADDRESS);
                            mContext.startActivity(intent);
                           }
                       } else {
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm();
            }
        });


        txtGetBTDATABTNID1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rlvMAinTrnsViewID.setVisibility(View.VISIBLE);

            }

        });


        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    /**********************************************************************************************
     *                Validating form
     *********************************************************************************************/
    private void submitForm() {

        if (!validateUserName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        username = inputUserName.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        if (checkPermission()) {
            callLoginAPI();
        }else {
            requestPermission();
        }

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

    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(LoginActivity.this, mString, Toast.LENGTH_LONG).show();
        }
    };


    public void SetTimeZone() {
        try {
            timeZoneName = new TimeZoneName();
            if (!pref.getString("key_time_zone_change", "not").equalsIgnoreCase("X")) {
                timeZoneName.setTimezone_city(TimeZone.getDefault().getID());
                timeZoneName.setTimezone_long(TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.LONG));
                timeZoneName.setTimezone_short(TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.SHORT));
                editor.putString("key_time_zone_city", TimeZone.getDefault().getID());
                editor.putString("key_time_zone_short", TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.SHORT));
                editor.putString("key_time_zone_long", TimeZone.getTimeZone(TimeZone.getDefault().getID()).getDisplayName(false, TimeZone.LONG));
                editor.putString("key_time_zone_change", "X");
                editor.commit();

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




    private void callSendOTPAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                try {
                    Gson gson = new Gson();

                    UserRegistratinModel mUserRegistratinModel = gson.fromJson(Json, UserRegistratinModel.class);

                    if (mUserRegistratinModel.getStatus()) {

                        Toast.makeText(mContext, mUserRegistratinModel.getMessage(), Toast.LENGTH_SHORT).show();
                        editor.putString("key_OTP", mUserRegistratinModel.getResponse().getOTP());
                        editor.commit();
                        finish();

                        Intent intent = new Intent(LoginActivity.this, UserOTPActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(mContext, mUserRegistratinModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }

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
            jsonObject.addProperty("MUserId", muserid);

            jsonObject.addProperty("MobileNo", mobileNo);


        } catch (Exception e) {
            e.printStackTrace();
        }

        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.SEND_USER_ACCOUNT_OTP);/////

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

    private boolean checkPermission() {
        int cameraPermission =
                ContextCompat.checkSelfPermission(LoginActivity.this, CAMERA);
        int writeExternalStorage =
                ContextCompat.checkSelfPermission(LoginActivity.this, WRITE_EXTERNAL_STORAGE);
        int ReadExternalStorage =
                ContextCompat.checkSelfPermission(LoginActivity.this, READ_EXTERNAL_STORAGE);
        int AccessCoarseLocation =
                ContextCompat.checkSelfPermission(LoginActivity.this, ACCESS_COARSE_LOCATION);
        int ReadMediaImage =
                ContextCompat.checkSelfPermission(LoginActivity.this, READ_MEDIA_IMAGES);
        int Bluetooth =
                ContextCompat.checkSelfPermission(LoginActivity.this, BLUETOOTH);
        int BluetoothConnect =
                ContextCompat.checkSelfPermission(LoginActivity.this, BLUETOOTH_CONNECT);
        int BluetoothScan =
                ContextCompat.checkSelfPermission(LoginActivity.this, BLUETOOTH_SCAN);
        int ReadPhoneState =
                ContextCompat.checkSelfPermission(LoginActivity.this, READ_PHONE_STATE);


        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return cameraPermission == PackageManager.PERMISSION_GRANTED
                    && AccessCoarseLocation == PackageManager.PERMISSION_GRANTED  && ReadMediaImage == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED && BluetoothConnect == PackageManager.PERMISSION_GRANTED
                    && BluetoothScan == PackageManager.PERMISSION_GRANTED;
        }else  if (SDK_INT >= Build.VERSION_CODES.R) {
            return cameraPermission == PackageManager.PERMISSION_GRANTED && writeExternalStorage == PackageManager.PERMISSION_GRANTED && AccessCoarseLocation == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED   && BluetoothScan == PackageManager.PERMISSION_GRANTED && ReadPhoneState == PackageManager.PERMISSION_GRANTED ;
        } else {
            return cameraPermission == PackageManager.PERMISSION_GRANTED && writeExternalStorage == PackageManager.PERMISSION_GRANTED
                    && ReadExternalStorage == PackageManager.PERMISSION_GRANTED && AccessCoarseLocation == PackageManager.PERMISSION_GRANTED;

        }

    }


    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,  Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.BLUETOOTH , Manifest.permission.BLUETOOTH_CONNECT
                            , Manifest.permission.BLUETOOTH_SCAN},
                    REQUEST_CODE_PERMISSION);
        } if (SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_CONNECT , Manifest.permission.BLUETOOTH_SCAN ,Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                           Manifest.permission.ACCESS_COARSE_LOCATION,},
                    REQUEST_CODE_PERMISSION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0) {
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    boolean ACCESSCAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessCoarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadMediaImage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean Bluetooth = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean BluetoothConnect = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean BluetoothScan = grantResults[5] == PackageManager.PERMISSION_GRANTED;

                    Log.e("ACCESSCAMERA=====>", String.valueOf(ACCESSCAMERA));
                    Log.e("AccessCoarseLocation===>", String.valueOf(AccessCoarseLocation));
                    Log.e("ReadMediaImage=====>", String.valueOf(ReadMediaImage));
                    Log.e("Bluetooth=====>", String.valueOf(Bluetooth));
                    Log.e("BluetoothConnect=====>", String.valueOf(BluetoothConnect));
                    Log.e("BluetoothScan=====>", String.valueOf(BluetoothScan));

                    if (ACCESSCAMERA && AccessCoarseLocation && ReadMediaImage && Bluetooth && BluetoothConnect && BluetoothScan) {

                        callLoginAPI();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.all_permission, Toast.LENGTH_LONG).show();
                    }
                }
                else  if (SDK_INT >= Build.VERSION_CODES.S) {
                    boolean ACCESSCAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage =
                            grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessCoarseLocation = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean Bluetooth = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean BluetoothConnect = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean BluetoothScan = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneState = grantResults[6] == PackageManager.PERMISSION_GRANTED;

                    /*boolean ACCESSCAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessCoarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadMediaImage = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean Bluetooth = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean BluetoothConnect = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean BluetoothScan = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneState = grantResults[7] == PackageManager.PERMISSION_GRANTED;
 */


                    Log.e("ACCESSCAMERA=====>", String.valueOf(ACCESSCAMERA));
                    Log.e("writeExternalStorage===>", String.valueOf(writeExternalStorage));
                    Log.e("AccessCoarseLocation=====>", String.valueOf(AccessCoarseLocation));
                    Log.e("Bluetooth=====>", String.valueOf(Bluetooth));
                    Log.e("ReadPhoneState=====>", String.valueOf(ReadPhoneState));
                    if (ACCESSCAMERA && writeExternalStorage && AccessCoarseLocation && Bluetooth && ReadPhoneState && BluetoothConnect && BluetoothScan) {
                        try {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                            startActivityForResult(intent, 2296);
                        } catch (Exception e) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivityForResult(intent, 2296);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.all_permission, Toast.LENGTH_LONG).show();
                    }

                } else {
                    boolean ACCESSCAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage =
                            grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStorage =
                            grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessCoarseLocation = grantResults[3] == PackageManager.PERMISSION_GRANTED;


                    Log.e("ACCESSCAMERA=====>", String.valueOf(ACCESSCAMERA));
                    Log.e("writeExternalStorage===>", String.valueOf(writeExternalStorage));
                    Log.e("ReadExternalStorage=====>", String.valueOf(ReadExternalStorage));
                    Log.e("AccessCoarseLocation=====>", String.valueOf(AccessCoarseLocation));

                    if (ACCESSCAMERA && writeExternalStorage && ReadExternalStorage  && AccessCoarseLocation) {
                        callLoginAPI();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.all_permission, Toast.LENGTH_LONG).show();
                    }

                }
            }


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                    callLoginAPI();
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}

