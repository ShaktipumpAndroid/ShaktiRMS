package com.shaktipumps.shakti_rms.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.AddDeviceBean.AddDeviceModelView;
import com.shaktipumps.shakti_rms.bean.AddDeviceBean.AddDeviceResponse;
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Add_latitudeAndLongitude extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String otp_status = "false", mStringCheckLoc = "", mDeviceSerialNumber = "", MuserId = "null", sms_url = "null", Mobile_no = "null", MDeviceId = "null", MDId = "null", isvalid = "null";
    String otp = "null", device_number = "null";
    private ProgressDialog progressDialog;

    private Activity mActivity;
    private Context mContext;

    private TextView txtSubmiteBTNID;
    private WebView simpleWebView;
    private TextView edtSerialNumberID, edtMobileNumberID, txtLatLongInfoID;
    private EditText edtLatitudeID, edtLongitudeID;
    AddDeviceResponse mAddDeviceResponse;


    String mDeviceNo, mMobileNo, mLatitude, mLongitude;
    private String aStringCheck;

    public String fileName = "loc_lat_long.html";

    RelativeLayout rlvBackViewID;

    RelativeLayout rlcGetLatlongID;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_latitude_and_longitude);
        mContext = this;
        mActivity = this;
        baseRequest = new BaseRequest(this);
        mAddDeviceResponse = new AddDeviceResponse();
        initView();
    }

    private void initView() {

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        // simpleWebView = findViewById(R.id.simpleWebView);
        //rlcGetLatlongID = findViewById(R.id.rlcGetLatlongID);

        txtSubmiteBTNID = findViewById(R.id.txtSubmiteBTNID);

        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);

        edtSerialNumberID = findViewById(R.id.edtSerialNumberID);


        edtMobileNumberID = findViewById(R.id.edtMobileNumberID);
        txtLatLongInfoID = findViewById(R.id.txtLatLongInfoID);



        /*edtLatitudeID = findViewById(R.id.edtLatitudeID);

        edtLatitudeID.setText(mLatitude);*/

        /*edtLongitudeID = findViewById(R.id.edtLongitudeID);

        edtLongitudeID.setText(mLongitude);*/

        try {
            Bundle bundle = getIntent().getExtras();

            mMobileNo = bundle.getString("Mobile");
            MuserId = bundle.getString("MUserId");
            MDeviceId = bundle.getString("MDeviceId");
            mDeviceSerialNumber = bundle.getString("mSerialNumber");
            mStringCheckLoc = bundle.getString("StringCheckLoc");
            mLatitude = bundle.getString("Latitude");
            mLongitude = bundle.getString("Longitude");

            // mLatitude = "";
             //mLongitude = "";

            edtMobileNumberID.setText(mMobileNo);
            edtSerialNumberID.setText(mDeviceSerialNumber);

            if (mStringCheckLoc.equalsIgnoreCase("1")) {

                if ((!mLatitude.equalsIgnoreCase("")) || (!mLongitude.equalsIgnoreCase(""))) {
                   /// Toast.makeText(mActivity, "mLatitude hai", Toast.LENGTH_SHORT).show();
                    mStringCheckLoc = "1";
                    txtLatLongInfoID.setVisibility(View.GONE);
                } else {
                  //  Toast.makeText(mActivity, "mLatitude nahi hai", Toast.LENGTH_SHORT).show();
                    mStringCheckLoc = "0";
                    txtLatLongInfoID.setVisibility(View.VISIBLE);
                    // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://solar10.shaktisolarrms.com/RMSApp/getLocation.jsp"));
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://solar10.shaktisolarrms.com/RMSApp/getLocation.jsp?DeviceNo=" + mDeviceSerialNumber + "-0"));
                    // startActivity(browserIntent);
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    browserIntent.setPackage("com.android.chrome");
                    try {
                        mContext.startActivity(browserIntent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        browserIntent.setPackage(null);
                        mContext.startActivity(browserIntent);
                    }
                }
            } else {
                txtLatLongInfoID.setVisibility(View.GONE);
                mLatitude = "";
                mLongitude = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        txtSubmiteBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // displaying content in WebView from html file that stored in assets folder
               /* simpleWebView.setVisibility(View.VISIBLE);
                simpleWebView.getSettings().setJavaScriptEnabled(true);
                simpleWebView.setWebChromeClient(new WebChromeClient());
                simpleWebView.loadUrl("file:///android_asset/" + fileName);*/

                emptyValidation();
            }


        });

       /* rlcGetLatlongID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://solar10.shaktisolarrms.com/RMSApp/getLocation.jsp"));
                startActivity(browserIntent);
            }
        });*/


        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void emptyValidation() {

        try {
            mDeviceNo = edtSerialNumberID.getText().toString();
            mMobileNo = edtMobileNumberID.getText().toString();
           // mLatitude = edtLatitudeID.getText().toString();
           // mLongitude = edtLongitudeID.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


      //  serverLogin();

        callAddDeviceAPI();

        /*if(mLatitude.equalsIgnoreCase("") || mLatitude.equalsIgnoreCase("null"))
        {
            Toast.makeText(mActivity, "Please enter latitude", Toast.LENGTH_SHORT).show();
        }
        else if(mLongitude.equalsIgnoreCase("") || mLongitude.equalsIgnoreCase("null"))
        {
            Toast.makeText(mActivity, "Please enter latitude", Toast.LENGTH_SHORT).show();
        }
        else
        {
           // Toast.makeText(mActivity, "Add device successfully!", Toast.LENGTH_SHORT).show();
        }*/
    }


    private void serverLogin() {
        ArrayList<String> al;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();
        param.clear();
        otp_param.clear();

        param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));
        param.add(new BasicNameValuePair("DeviceNo", mDeviceNo));
        param.add(new BasicNameValuePair("MobileNo", mMobileNo));  // add in 2.4 version
        param.add(new BasicNameValuePair("Latitude", mLatitude));  // add in 2.4 version
        //param.add(new BasicNameValuePair("Latitude", "888.8888"));  // add in 2.4 version
        param.add(new BasicNameValuePair("Longitude", mLongitude));  // add in 2.4 version
        //param.add(new BasicNameValuePair("Longitude", "99.999"));  // add in 2.4 version
        param.add(new BasicNameValuePair("islocation", mStringCheckLoc)); // add in 2.4 version
      //  param.add(new BasicNameValuePair("PlantId", "0")); // add in 2.4 version
        Log.d("Latitude", "Latitude==>>" + mLatitude + "\nLongitude==>>" + mLongitude);
        otp = "" + ((int) (Math.random() * 9000) + 1000);
/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(mContext, "", "Please wait !");
        new Thread() {
            public void run() {
                if (CustomUtility.isOnline(mContext)) {
                    try {
                        String obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.GET_DEVICE, param);
                        Log.d("obj_device", "" + obj);
                        if (obj != null) {
                            // progressDialog.dismiss();
                            JSONObject jresponse = new JSONObject(obj);
                            // userId_status != null i.e new user is successfully created on server
                            MDId = jresponse.getString("MDId");
                            isvalid = jresponse.getString("isvalid");
                            MuserId = jresponse.getString("MUserId");
                            MDeviceId = jresponse.getString("MDeviceId");
                            // device is valid or not
                            if (isvalid.equalsIgnoreCase("true")) {
                                if (MDId.equalsIgnoreCase("0")) {
                                    //************ code comment in 2.4 app ****************************
                                    //     Mobile_no = jresponse.getString("Mobile");
                                    //************ code comment ****************************
                                    if (!MuserId.equalsIgnoreCase("null")) {


                                         callSendOTPAPI(); // comment by vikas 19-07
                                    }

                                } else {

                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_device_already_added));
                                }

                            } else {
                                if (MDId.equalsIgnoreCase("0")) {
                                    CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_invalid_device));
                                } else {

                                    if (!MuserId.equalsIgnoreCase("null")) {


                                        callSendOTPAPI(); // comment by vikas 19-07

                                    }
                                }
                                progressDialog.dismiss();
                            }
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
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(mContext, "Error", "No Internet Connection");


//                    Message   msg2 = new Message();
//                    msg2.obj = "No Internet Connection";
//                    mHandler.sendMessage(msg2);

                }


            }

        }.start();
    }


    private void callAddDeviceAPI() {

        if (CustomUtility.isOnline(mContext)){
            baseRequest.showLoader();

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        AddDeviceModelView mAddDeviceModelView = gson.fromJson(Json, AddDeviceModelView.class);
                        getAddDeviceResponse(mAddDeviceModelView);

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

                Log.d("Latitude", "Latitude==>>" + mLatitude + "\nLongitude==>>" + mLongitude);
                otp = "" + ((int) (Math.random() * 9000) + 1000);
                ////Put input parameter here
                jsonObject.addProperty("MUserId", pref.getString("key_muserid", "invalid_muserid"));
                jsonObject.addProperty("DeviceNo", mDeviceNo);
                jsonObject.addProperty("MobileNo", mMobileNo);
                jsonObject.addProperty("Latitude", mLatitude);
                jsonObject.addProperty("Longitude", mLongitude);
                jsonObject.addProperty("islocation", mStringCheckLoc);
                jsonObject.addProperty("PlantId", "0");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ADD_DEVICE_API_VK);/////

        }
        else
        {
            Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void getAddDeviceResponse(AddDeviceModelView mAddDeviceModelView) {

        if (mAddDeviceModelView.getStatus()) {
             mAddDeviceResponse = mAddDeviceModelView.getResponse();

            if (mAddDeviceResponse.getIsvalid()) {
                if (mAddDeviceResponse.getMDId().equalsIgnoreCase("0")) {
                    //************ code comment in 2.4 app ****************************
                    //     Mobile_no = jresponse.getString("Mobile");
                    //************ code comment ****************************
                   // if (!MuserId.equalsIgnoreCase("null"))
                    {
                       // callSendOTPAPI(); // comment by vikas 19-07
                        Intent intent  = new Intent(  mContext, BaseActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(mActivity, getString(R.string.err_device_already_added), Toast.LENGTH_SHORT).show();
                   // CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_device_already_added));
                }
            }
            else {
                if (mAddDeviceResponse.getMDId().equalsIgnoreCase("0")) {
                    Toast.makeText(mActivity, getString(R.string.err_invalid_device), Toast.LENGTH_SHORT).show();
                   // CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_invalid_device));
                } else {

                   /* if (!MuserId.equalsIgnoreCase("null"))
                    {


                        //callSendOTPAPI(); // comment by vikas 19-07
                        Intent intent  = new Intent(  mContext, BaseActivity.class);
                        startActivity(intent);
finish();
                    }
                    else
                    {
                        finish();
                    }*/
                    Intent intent  = new Intent(  mContext, BaseActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        }
        else
        {

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

                        Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, DeviceOTPActivity.class);
                        intent.putExtra("Mobile", mMobileNo);
                        intent.putExtra("MUserId", MuserId);
                        intent.putExtra("MDeviceId", MDeviceId);
                        //  Log.d("intent1",MuserId +"--"+  MDeviceId +"--"+ Mobile_no);
                        startActivity(intent);

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
            jsonObject.addProperty("MUserId", MuserId);
            jsonObject.addProperty("MDeviceId", MDeviceId);
            jsonObject.addProperty("MobileNo", mMobileNo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.SEND_ADD_DEVICE_OTP);/////
        //baseRequest.callAPIPostLocal(1, jsonObject, NewSolarVFD.SEND_ADD_DEVICE_OTP);/////
    }

}
