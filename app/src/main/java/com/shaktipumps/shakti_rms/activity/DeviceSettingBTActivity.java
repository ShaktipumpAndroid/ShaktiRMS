package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelResponse;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class DeviceSettingBTActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    Context mContext;
    SwitchCompat switchCompat;
    String speed_mode_param_value = "null";
    private ProgressDialog progressDialog;
    String speed_mode = "null";
    //  TextView read_speed_mode,change_speed_mode,speed_mode_type ;
    boolean read_only = true;
    boolean speed_checked;
    String MUserId = "null", DeviceType = "null", DeviceNo = "null", Mobile = "null", CustomerName = "null",
            RMSingalStr = "null", RMStatusOfProduct = "null", RMLatitude = "null", RMDate = " ";

    private RecyclerView rclSettingListViewID;

    private LinearLayoutManager lLayout;
    private RecyclerView.Adapter recyclerViewAdapter;
    private BaseRequest baseRequest;

    private List<SettingModelResponse> mSettingModelResponse;
    private List<EditText> mEditTextList;
    private List<TextView> mTextViewSetIDtList;
    private DatabaseHelperTeacher databaseHelperTeacher;

    private BluetoothSocket btSocket;
    private BluetoothAdapter myBluetooth;

    private UUID mMyUDID;
    private InputStream iStream;
    private Activity mActivity;

    RelativeLayout iv_sub_linearlayout12;
    LinearLayout lvlMainParentLayoutID;

    RelativeLayout rlvMainDynamicViewID;


    private int edtValue = 0;
    private float edtValueFloat = 0;
    private String old_data = "1";

    float mTotalTimeFloatData;
    char mCRCFinalValue;
    char mCRCFinalValueWrite;
    int i = 0;

    int mGlobalPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting);

        baseRequest = new BaseRequest(this);
        databaseHelperTeacher = new DatabaseHelperTeacher(this);
        rclSettingListViewID = (RecyclerView) findViewById(R.id.rclSettingListViewID);
        lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        mEditTextList = new ArrayList<>();
        mTextViewSetIDtList = new ArrayList<>();
        mSettingModelResponse = new ArrayList<>();

        rclSettingListViewID.setNestedScrollingEnabled(false);
        rclSettingListViewID.setLayoutManager(lLayout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mContext = this;
        mActivity = this;
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_device_settingBT);

        Bundle bundle = getIntent().getExtras();
        MUserId = bundle.getString("MUserId");
        DeviceType = bundle.getString("DeviceType");
        DeviceNo = bundle.getString("DeviceNo");
        initView();

        // Toast.makeText(mContext, "CustomUtility.getDeviceId(mContext)==>>"+CustomUtility.getDeviceId(mContext), Toast.LENGTH_SHORT).show();
        //callSettingParameterValueAPI();

        //addDynamicViewPro(mSettingModelResponse);

    }

    private void initView() {

        lvlMainParentLayoutID = (LinearLayout) findViewById(R.id.lvlMainParentLayoutID);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getDeviceMode() {

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        if (CustomUtility.isOnline(mContext)) {
            try {
                String obj = "";

                param.clear();

                param.add(new BasicNameValuePair("address1", "2")); // speed mode
                param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                param.add(new BasicNameValuePair("RW", "0"));          // 0 = read
                param.add(new BasicNameValuePair("data1", "1"));               // start = 1
                param.add(new BasicNameValuePair("OldData", "1"));
                param.add(new BasicNameValuePair("UserId", MUserId));
                param.add(new BasicNameValuePair("DeviceType", DeviceType));
                param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile

                obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                if (!TextUtils.isEmpty(obj)) {

                    progressDialog.dismiss();
                    JSONArray ja = new JSONArray(obj);

                    //Log.d("Speed", obj );
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        //success message
                        if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                            speed_mode = jo.getString("Longitude");
                        }

                        // disconnect message
                        if (jo.getString("Result").equalsIgnoreCase("D")) {
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                        }

                        // command failed message
                        if (!jo.getString("Result").equalsIgnoreCase("2.0") &&
                                !jo.getString("Result").equalsIgnoreCase("D")
                        ) {
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_read_mode_fail));
                        }
                    }
                }

            } catch (Exception e) {
                progressDialog.dismiss();
                Log.d("exce", "" + e);
            }
        } else {
            progressDialog.dismiss();
            Message msg = new Message();
            msg.obj = "Please on internet ( Mobile data )";
            mHandler.sendMessage(msg);
        }
//
//            }
//        }).start();

//           }
//       });

    }

    public void setDeviceMode() {

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        String old_data = "null";

        if (speed_mode.equalsIgnoreCase("AUTO")) {
            speed_mode_param_value = "2";
        }

        if (speed_mode.equalsIgnoreCase("MANUAL")) {
            speed_mode_param_value = "1";
        }

        if (speed_mode_param_value.equalsIgnoreCase("2")) {
            old_data = "1";
        }

        if (speed_mode_param_value.equalsIgnoreCase("1")) {
            old_data = "2";
        }

        //Log.d("speed_mode_param_value", speed_mode_param_value +"--"+  old_data) ;


        if (CustomUtility.isOnline(mContext)) {


            try {
                String obj = "";

                param.clear();

                param.add(new BasicNameValuePair("address1", "2")); // speed mode

                param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                param.add(new BasicNameValuePair("RW", "1"));        // 1 = write
                param.add(new BasicNameValuePair("data1", speed_mode_param_value));     // 1 = auto 2 = manual
                param.add(new BasicNameValuePair("OldData", old_data));
                param.add(new BasicNameValuePair("UserId", MUserId));
                param.add(new BasicNameValuePair("DeviceType", DeviceType));


                //  param.add(new BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile

                obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                // Log.d("start", obj +start+"--"+stop);

// _rms D/Speend Mode: [{"Latitude":"2.0","Longitude":"MANUAL","Result":"2.0","CEnergyF":0.0,"CFlowF":0.0,"CTimeF":0.0}]
// _rms D/Speend Mode: [{"Latitude":"2.0","Longitude":"MANUAL","Result":"2.0","CEnergyF":0.0,"CFlowF":0.0,"CTimeF":0.0}]


                if (!TextUtils.isEmpty(obj)) {

                    progressDialog.dismiss();

                    JSONArray ja = new JSONArray(obj);

                    // Log.d("Speed12", obj );

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        //success message
                        if (jo.getString("Result").equalsIgnoreCase("2.0")) {

                            speed_mode = jo.getString("Longitude");


                        }


                        // disconnect message
                        if (jo.getString("Result").equalsIgnoreCase("D")) {
                            read_only = false;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));

                        }


                        // command failed message
                        if (!jo.getString("Result").equalsIgnoreCase("2.0") &&
                                !jo.getString("Result").equalsIgnoreCase("D")

                        ) {
                            read_only = false;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_set_mode_fail));
//                                    if (start.equalsIgnoreCase("1"))
//                                    {
//                                        CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_start_command_fail));
//
//                                    }
//                                    else
//                                    {
//                                        CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_stop_command_fail));
//
//                                    }


                        }


                    }


                }

            } catch (Exception e) {

                progressDialog.dismiss();
                Log.d("exce", "" + e);
            }

        } else {

            progressDialog.dismiss();

            Message msg = new Message();
            msg.obj = "Please on internet ( Mobile data )";
            mHandler.sendMessage(msg);

        }

//
//            }
//        }).start();

//           }
//       });

    }

    private class ReadDeviceMode extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;

            try {

                getDeviceMode();

                runOnUiThread(


                        new Runnable() {

                            @Override
                            public void run() {

                                //   Log.d("speed_mode",speed_mode);

                                if (speed_mode.equalsIgnoreCase("MANUAL")) {

                                 /*   speed_mode_type.setText("Manual");
                                    change_speed_mode.setVisibility(View.VISIBLE);*/

//                                    switchCompat.setText("Manual");
//                                    switchCompat.setVisibility(View.VISIBLE);
//                                    switchCompat.setChecked(true);
                                }
//
                                if (speed_mode.equalsIgnoreCase("AUTO")) {

                                   /* speed_mode_type.setText("Auto");
                                    change_speed_mode.setVisibility(View.VISIBLE);*/

//                                    switchCompat.setText("Auto");
//                                    switchCompat.setVisibility(View.VISIBLE);
//                                    switchCompat.setChecked(false);
                                }


                            }
                        });


            } catch (Exception e) {

            }
            return data;
        }

//        protected void onPreExecute() {
//
//
//            progressDialog = new ProgressDialog(DeviceSettingActivity.this);
//            progressDialog.setMessage("Loading..."); // Setting Message
//            progressDialog.setTitle("Please wait..."); // Setting Title
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//            progressDialog.show(); // Display Progress Dialog
//            progressDialog.setCancelable(false);
//
////            progressDialog = ProgressDialog.show(mContext, "", "Please Wait..");
//
//        }

//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
////            Log.i("SomeTag", System.currentTimeMillis() / 1000L
////                    + " post execute \n" + result);
//            //progressDialog.dismiss();
//        }


    }


    private class SetDeviceMode extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;

            try {


                setDeviceMode();


                runOnUiThread(


                        new Runnable() {

                            @Override
                            public void run() {

                                Log.d("speed_mode", speed_mode);

                                if (speed_mode.equalsIgnoreCase("MANUAL")) {


                                  /*  speed_mode_type.setText("Manual");
                                    change_speed_mode.setVisibility(View.VISIBLE);*/
//                                    switchCompat.setText("Manual");
//                                    switchCompat.setVisibility(View.VISIBLE);
//

                                    //switchCompat.setChecked(true);
                                }
//
                                if (speed_mode.equalsIgnoreCase("AUTO")) {

                                   /* speed_mode_type.setText("Auto");
                                    change_speed_mode.setVisibility(View.VISIBLE);*/

//                                    switchCompat.setText("Auto");
//                                    switchCompat.setVisibility(View.VISIBLE);
                                    // switchCompat.setChecked(false);
                                }


                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();

            }
            return data;
        }

//        protected void onPreExecute() {
//
//
//            progressDialog = new ProgressDialog(DeviceSettingActivity.this);
//            progressDialog.setMessage("Loading..."); // Setting Message
//            progressDialog.setTitle("Please wait..."); // Setting Title
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//            progressDialog.show(); // Display Progress Dialog
//            progressDialog.setCancelable(false);
//
////            progressDialog = ProgressDialog.show(mContext, "", "Please Wait..");
//
//        }

//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
////            Log.i("SomeTag", System.currentTimeMillis() / 1000L
////                    + " post execute \n" + result);
//            //progressDialog.dismiss();
//        }


    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };


    ////////////////vikas service calling new


    private void callSettingParameterValueAPI() {

        if (CustomUtility.isOnline(mContext)) {

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        SettingModelView mSettingModelView = gson.fromJson(Json, SettingModelView.class);
                        getDeviceSettingListResponse(mSettingModelView);
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
                jsonObject.addProperty("DeviceTypeID", DeviceType);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_DEVICE_SETTING);/////

        } else {
            mSettingModelResponse = databaseHelperTeacher.getDevicePARAMeterListData(DeviceType);

            addDynamicViewPro(mSettingModelResponse);
            /*if (mSettingModelResponse.size() > 0) {

                if (recyclerViewAdapter != null)
                    recyclerViewAdapter = null;

                // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
                //  recyclerViewAdapter = new DeviceSettingAdapter(mContext, mSettingModelResponse, DeviceType, DeviceNo, MUserId);
                ////vijay dinanath chouhan
                recyclerViewAdapter = new DeviceSettingBTAdapter(mContext, mActivity, mSettingModelResponse, DeviceType, DeviceNo, MUserId);

                rclSettingListViewID.setAdapter(recyclerViewAdapter);
            } else {

            }*/
        }
    }

    private void getDeviceSettingListResponse(SettingModelView mSettingModelView) {

        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mSettingModelView.getStatus()) {

            if (mSettingModelResponse != null && mSettingModelResponse.size() > 0)
                mSettingModelResponse.clear();

            mSettingModelResponse = mSettingModelView.getResponse();

            addDynamicViewPro(mSettingModelResponse);

         /*   if (recyclerViewAdapter != null)
                recyclerViewAdapter = null;

            // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
            recyclerViewAdapter = new DeviceSettingBTAdapter(mContext, mActivity, mSettingModelResponse, DeviceType, DeviceNo, MUserId);

            rclSettingListViewID.setAdapter(recyclerViewAdapter);*/
            boolean mCheckFirstDB;
            for (int i = 0; i < mSettingModelResponse.size(); i++) {
                if (i == 0) {
                    mCheckFirstDB = true;
                } else {
                    mCheckFirstDB = false;
                }
                String Address, Divisible, MDeviceNo, MPId, MPIndex, MPName, offset, Status, Unit, PMin, PMax, MODAddress;
                Address = mSettingModelResponse.get(i).getAddress();
                Divisible = String.valueOf(mSettingModelResponse.get(i).getDivisible());
                MDeviceNo = String.valueOf(mSettingModelResponse.get(i).getMDeviceNo());
                //  MDeviceNo = mSettingModelResponse.get(i).getMDeviceNo();
                MPId = mSettingModelResponse.get(i).getMPId();
                MPIndex = String.valueOf(mSettingModelResponse.get(i).getMPIndex());
                MPName = mSettingModelResponse.get(i).getMPName();
                Status = mSettingModelResponse.get(i).getStatus();
                Unit = mSettingModelResponse.get(i).getUnit();
                PMin = String.valueOf(mSettingModelResponse.get(i).getPMin());
                PMax = String.valueOf(mSettingModelResponse.get(i).getPMax());
                MODAddress = mSettingModelResponse.get(i).getAddress();
                offset = mSettingModelResponse.get(i).getAddress();
                //DeviceTyape = DeviceType;
              //  databaseHelperTeacher.insertDeviceParameterListData(Address, Divisible, MDeviceNo, MPId, MPIndex, MPName, Status, Unit, PMin, PMax, MODAddress, DeviceType, offset, mCheckFirstDB);

            }

            //  mClientToken = mPaymentTokenResponse.getToken();
            //  onBraintreeSubmit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addDynamicViewPro(final List<SettingModelResponse> mSettingModelResponse) {

        try {
            if(mEditTextList.size() > 0)
            {
                mEditTextList.clear();
            }
            if(mTextViewSetIDtList.size() > 0)
            {
                mTextViewSetIDtList.clear();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ///  addHeadersMonths();
                    lvlMainParentLayoutID.removeAllViews();
                    // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                }
            });


        } catch (Exception exp) {
            exp.printStackTrace();
        }

        for ( i = 0; i < mSettingModelResponse.size(); i++)
        //for (int i = 0; i < 10; i++)
        {
            rlvMainDynamicViewID = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlvMainDynamicViewIDParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,  (int) getResources().getDimension(R.dimen._165sdp));

            rlvMainDynamicViewIDParam.setMargins((int) getResources().getDimension(R.dimen._10sdp), (int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._10sdp), (int) getResources().getDimension(R.dimen._5sdp));
            rlvMainDynamicViewIDParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            rlvMainDynamicViewID.setLayoutParams(rlvMainDynamicViewIDParam);

            // cardViewAddDynamicViewID.addView(rlvMainDynamicViewID);
            iv_sub_linearlayout12 = new RelativeLayout(this);
            RelativeLayout.LayoutParams iv_outparams12 = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.MATCH_PARENT);
            iv_outparams12.setMarginEnd((int) getResources().getDimension(R.dimen._5sdp));
            iv_outparams12.setMarginStart((int) getResources().getDimension(R.dimen._5sdp));
            iv_outparams12.setMargins(0, (int) getResources().getDimension(R.dimen._2sdp), 0, (int) getResources().getDimension(R.dimen._2sdp));
            iv_sub_linearlayout12.setBackgroundColor(getResources().getColor(R.color.white));
            //  iv_sub_linearlayout12.setOrientation(LinearLayout.HORIZONTAL);
            iv_sub_linearlayout12.setLayoutParams(iv_outparams12);


            TextView txtPeraNameID = new TextView(this);
            RelativeLayout.LayoutParams txtFromTextHeadParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) (int) getResources().getDimension(R.dimen._40sdp));
            //txtFromTextHeadParam.setMarginStart(20);
           // txtFromTextHeadParam.setMargins(0, 5, 0, 5);
            txtFromTextHeadParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            txtPeraNameID.setGravity(Gravity.CENTER_HORIZONTAL);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
           // txtPeraNameID.setText(getResources().getString(R.string.Get_text));
            txtPeraNameID.setText(mSettingModelResponse.get(i).getMPName());
            /// txtPeraNameID.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.textsize));
            txtPeraNameID.setTextSize((getResources().getDimension(R.dimen._14sdp)));
            txtPeraNameID.setId(i + 1);
            txtPeraNameID.setTypeface(null, Typeface.BOLD);

            txtPeraNameID.setTextColor(getResources().getColor(R.color.blue_fb));
            txtPeraNameID.setLayoutParams(txtFromTextHeadParam);
            iv_sub_linearlayout12.addView(txtPeraNameID);


            RelativeLayout rlvMainViewLayoutIN = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlvMainParamIN = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.MATCH_PARENT);
            rlvMainParamIN.setMargins(0, (int) getResources().getDimension(R.dimen._5sdp), 0, 0);
            rlvMainParamIN.addRule(RelativeLayout.BELOW, txtPeraNameID.getId());
            rlvMainViewLayoutIN.setLayoutParams(rlvMainParamIN);
            iv_sub_linearlayout12.addView(rlvMainViewLayoutIN);


            TextView txtGetID = new TextView(this);
            RelativeLayout.LayoutParams txtGetIDParam = new RelativeLayout.LayoutParams
                    ((int) getResources().getDimension(R.dimen._120sdp), (int) getResources().getDimension(R.dimen._70sdp));
            //txtGetIDParam.setMarginStart(20);
            // txtGetIDParam.setMargins(0, 0, 0, 0);
            txtGetIDParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            txtGetID.setGravity(Gravity.CENTER);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
            txtGetID.setText(getResources().getString(R.string.Get_text));
            //txtGetID.setTextSize((float) 14);
            txtGetID.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen._12sdp));
            txtGetID.setId(i + 2);
            txtGetID.setTypeface(null, Typeface.NORMAL);

            txtGetID.setTextColor(getResources().getColor(R.color.white));
            //txtGetID.setBackground(getResources().getDrawable(R.drawable.blue_btn_round));
            txtGetID.setBackground(getResources().getDrawable(R.drawable.round_green_shape));
            txtGetID.setLayoutParams(txtGetIDParam);
            rlvMainViewLayoutIN.addView(txtGetID);


            txtGetID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int iii = v.getId();
                    int pp = iii-2;

                    mGlobalPosition = pp;
                   // Toast.makeText(mContext, "jai hooo...==>>  "+pp, Toast.LENGTH_SHORT).show();

                    int offsetValue = mSettingModelResponse.get(pp).getOffset();
                    String mStringCeck= mEditTextList.get(pp).getText().toString().trim();
                    if(!mStringCeck.equalsIgnoreCase("") && !mStringCeck.equalsIgnoreCase(null))
                    {
                        edtValueFloat = Float.parseFloat(mEditTextList.get(pp).getText().toString().trim());
                    }
                    else
                    {
                        edtValueFloat = Float.parseFloat(offsetValue+"");
                    }

                    char[] datar=new char[2];
                    //int a=Float.floatToIntBits((float) edtValueFloat);
                    int a= (int) edtValueFloat;
                    datar[0]=(char)(a & 0x000000FF);
                    datar[1]=(char)((a & 0x0000FF00)>>8);
                   // datar[2]=(char)((a & 0x00FF0000)>>16);
                   // datar[3]=(char)((a & 0xFF000000)>>24);
                    int crc=CRC16_MODBUS(datar, 2);
                    char reciverbyte1=(char)((crc>>8) & 0x00FF);
                    char reciverbyte2=(char)(crc & 0x00FF);

                    mCRCFinalValue = (char) (reciverbyte1 + reciverbyte2);

                 //   String v1 =String.valueOf(datar[0]);
                   // String v2 =String.valueOf(datar[1]); //String v2 =Integer.toHexString(datar[1]);
                   // String v3 =String.valueOf(datar[2]);
                   // String v4 =String.valueOf(datar[3]);

                 //  String v1 =String.format("%02x", (0xff & datar[0]));
                 //  String v2 =String.format("%02x", (0xff & datar[1]));

                   String v1 =String.valueOf(datar[0]);
                   String v2 =String.valueOf(datar[1]);
                   //String v2 =String.format("%02x", (0xff & datar[1]));

                    /* String v1 =String.format("%02x", (0xff & datar[0]));
                    String v2 =String.format("%02x", (0xff & datar[1])); //String v2 =Integer.toHexString(datar[1]);
                    String v3 =String.format("%02x", (0xff & datar[2]));
                    String v4 =String.format("%02x", (0xff & datar[3]));*/

                    String v5 =Integer.toHexString(mCRCFinalValue);

                  //  String modeBusCommand = "0103"+mSettingModelResponse.get(pp).getMobBTAddress()+v1+v2+v3+v4+v5;//write
                    String mMOBADDRESS = "";
                    String mMobADR= mSettingModelResponse.get(pp).getMobBTAddress();
                    if(!mMobADR.equalsIgnoreCase(""))
                    {
                       int mLenth = mMobADR.length();
                       if(mLenth == 1)
                       {
                           mMOBADDRESS = "000"+mMobADR;
                       }else if(mLenth == 2)
                       {
                           mMOBADDRESS = "00"+mMobADR;
                       }else if(mLenth == 3)
                       {
                           mMOBADDRESS = "0"+mMobADR;
                       }
                       else
                       {
                           mMOBADDRESS = mMobADR;
                       }
                    }
                    else
                    {
                        Toast.makeText(mContext, "MOB address not found!", Toast.LENGTH_SHORT).show();
                    }

                    String modeBusCommand = "0103"+mMOBADDRESS+v1+v2+v5;//write
                    Toast.makeText(mContext, "modeBusCommand get==>>"+modeBusCommand, Toast.LENGTH_SHORT).show();



                    new BluetoothCommunicationForDynamicParameterRead().execute(modeBusCommand, modeBusCommand, "OK");

                }
            });

            TextView txtSetID = new TextView(this);
            RelativeLayout.LayoutParams txtSetIDParam = new RelativeLayout.LayoutParams
                    ((int) getResources().getDimension(R.dimen._120sdp), (int) getResources().getDimension(R.dimen._70sdp));
            //txtSetIDParam.setMarginStart(20);
            // txtSetIDParam.setMargins(0, 0, 0, 0);
            txtSetIDParam.addRule(RelativeLayout.ALIGN_PARENT_END);
            txtSetID.setGravity(Gravity.CENTER);
                        //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
            txtSetID.setText(getResources().getString(R.string.Set_text));
          //  txtSetID.setTextSize((float) 14);
            txtSetID.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen._12sdp));
            txtSetID.setId(i + 3);
            txtSetID.setTypeface(null, Typeface.NORMAL);

            txtSetID.setTextColor(getResources().getColor(R.color.white));
          //  txtSetID.setBackground(getResources().getDrawable(R.drawable.blue_btn_round));
            txtSetID.setBackground(getResources().getDrawable(R.drawable.round_red_shape));
            txtSetID.setLayoutParams(txtSetIDParam);

            mTextViewSetIDtList.add(txtSetID);
            //if(mSettingModelResponse.get(i).getEditValue() != null && !mSettingModelResponse.get(i).getEditValue().equalsIgnoreCase(""))
            String sssss1 = String.valueOf(mSettingModelResponse.get(i).getEditValue());

            if(!sssss1.equalsIgnoreCase("0.0") && sssss1.equalsIgnoreCase(""))
            {
                changeButtonVisibility(true, 1.0f, mTextViewSetIDtList.get(i));
            }
            else
            {
                changeButtonVisibility(false, 0.5f, mTextViewSetIDtList.get(i));
            }
            rlvMainViewLayoutIN.addView(txtSetID);


            txtSetID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int iii = v.getId();
                    int pp = iii-3;
                //    Toast.makeText(mContext, "jai hooo...==>>  "+pp, Toast.LENGTH_SHORT).show();

                    String mStringCeck= mEditTextList.get(pp).getText().toString().trim();

                    if(!mStringCeck.equalsIgnoreCase("") && !mStringCeck.equalsIgnoreCase("0.0") && !mStringCeck.equalsIgnoreCase(null))
                    {
                        edtValueFloat = Float.parseFloat(mEditTextList.get(pp).getText().toString().trim());
                    }
                    else
                    {
                        edtValueFloat = Float.parseFloat(mSettingModelResponse.get(pp).getOffset()+"");
                    }
                 //   edtValueFloat = Float.parseFloat(mEditTextList.get(pp).getText().toString().trim());
                    //  edtValue = Integer.parseInt(mEditTextList.get(pp).getText().toString().trim());
                    if (edtValueFloat > mSettingModelResponse.get(pp).getPMin() && edtValueFloat < mSettingModelResponse.get(pp).getPMax()) {
                        /*  setDeviceMode(mSettingModelResponse.get(position).getAddress());*/
                        //changeButtonVisibility(true, 1.0f, holder);
                        char[] datar=new char[2];
                        int a=Float.floatToIntBits((float) edtValue);
                        datar[0]=(char)(a & 0x000000FF);
                        datar[1]=(char)((a & 0x0000FF00)>>8);
                      //  datar[2]=(char)((a & 0x00FF0000)>>16);
                       // datar[3]=(char)((a & 0xFF000000)>>24);
                        int crc=CRC16_MODBUS(datar, 2);

                        /*char[] datar=new char[4];
                        int a=Float.floatToIntBits((float) edtValue);
                        datar[0]=(char)(a & 0x000000FF);
                        datar[1]=(char)((a & 0x0000FF00)>>8);
                        datar[2]=(char)((a & 0x00FF0000)>>16);
                        datar[3]=(char)((a & 0xFF000000)>>24);
                        int crc=CRC16_MODBUS(datar, 4);*/

                        char reciverbyte1=(char)((crc>>8) & 0x00FF);
                        char reciverbyte2=(char)(crc & 0x00FF);

                        mCRCFinalValue = (char) (reciverbyte1 + reciverbyte2);

                      /*  String v1 =String.format("%02x", (0xff & datar[0]));
                        String v2 =String.format("%02x", (0xff & datar[1])); //String v2 =Integer.toHexString(datar[1]);
                        String v3 =String.format("%02x", (0xff & datar[2]));
                        String v4 =String.format("%02x", (0xff & datar[3]));*/

                        String v1 =String.valueOf(datar[0]);
                        String v2 =String.valueOf(datar[1]); //String v2 =Integer.toHexString(datar[1]);
                      //  String v3 =String.valueOf(datar[2]);
                      //  String v4 =String.valueOf(datar[3]);
                        String v5 =Integer.toHexString(mCRCFinalValue);

                        //String modeBusCommand = "0106"+Integer.toHexString(mSettingModelResponse.get(position).getMobBTAddress())+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(reciverbyte1)+Integer.toHexString(reciverbyte2);//write
                       // String modeBusCommand = "0106"+mSettingModelResponse.get(i).getMobBTAddress()+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(mCRCFinalValue);//write

                        String mMOBADDRESS = "";
                        String mMobADR= mSettingModelResponse.get(pp).getMobBTAddress();
                        if(!mMobADR.equalsIgnoreCase(""))
                        {
                            int mLenth = mMobADR.length();
                            if(mLenth == 1)
                            {
                                mMOBADDRESS = "000"+mMobADR;
                            }else if(mLenth == 2)
                            {
                                mMOBADDRESS = "00"+mMobADR;
                            }else if(mLenth == 3)
                            {
                                mMOBADDRESS = "0"+mMobADR;
                            }
                            else
                            {
                                mMOBADDRESS = mMobADR;
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "MOB address not found!", Toast.LENGTH_SHORT).show();
                        }

                        String modeBusCommand = "0106"+mMOBADDRESS+v1+v2+v5;//write
                        Toast.makeText(mContext, "modeBusCommand write==>>"+modeBusCommand, Toast.LENGTH_SHORT).show();
                        //  String modeBusCommand1 = "0103"+mSettingModelResponse.get(position).getMobBTAddress()+""+"crc";
                        new BluetoothCommunicationForDynamicParameterWrite().execute(modeBusCommand, modeBusCommand, "OK");

                    } else {
                        Toast.makeText(mContext, "Please enter value between min=" + mSettingModelResponse.get(pp).getPMin() + " Max=" + mSettingModelResponse.get(pp).getPMax(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            RelativeLayout rlvEDITLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlvEDITParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._75sdp));
            //rlvEDITParam.setMargins(130, 1, 130, 1);
            rlvEDITParam.setMarginStart((int) getResources().getDimension(R.dimen._130sdp));
            rlvEDITParam.setMarginEnd((int) getResources().getDimension(R.dimen._130sdp));
            rlvEDITParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlvEDITLayout.setLayoutParams(rlvEDITParam);
            rlvEDITLayout.setBackground(getResources().getDrawable(R.drawable.shapeedittxt));
            iv_sub_linearlayout12.addView(rlvEDITLayout);


            EditText edtValueID = new EditText(this);
            RelativeLayout.LayoutParams edtValueIDParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) (int) getResources().getDimension(R.dimen._72sdp));
            //edtValueIDParam.setMarginStart(20);

            edtValueIDParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            edtValueIDParam.setMargins((int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._3sdp), 0);
            edtValueID.setGravity(Gravity.CENTER_VERTICAL);
           // edtValueID.setText(mSettingModelResponse.get(i).getEditValue()+"");
           // edtValueID.setHint(getResources().getString(R.string.Get_text));
            String sssss = String.valueOf(mSettingModelResponse.get(i).getEditValue());

            if(!sssss.equalsIgnoreCase("0.0") && String.valueOf(mSettingModelResponse.get(i).getEditValue()) != null && !String.valueOf(mSettingModelResponse.get(i).getEditValue()).equalsIgnoreCase(""))
            {
                edtValueID.setText(mSettingModelResponse.get(i).getEditValue()+"");
            }
            else
            {
                edtValueID.setText("");
            }


            edtValueID.setTextColor(getResources().getColor(R.color.black));
            edtValueID.setBackgroundColor(getResources().getColor(R.color.colorWhite));
          //  edtValueID.setTextSize((float) 14);
            edtValueID.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen._6ssp));
            edtValueID.setId(i + 4);
            edtValueID.setMaxLines(1);
           // edtValueID.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtValueID.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
            edtValueID.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

            edtValueID.setTypeface(null, Typeface.NORMAL);

            edtValueID.setTextColor(getResources().getColor(R.color.black));
            edtValueID.setLayoutParams(edtValueIDParam);
            rlvEDITLayout.addView(edtValueID);

            mEditTextList.add(edtValueID);


            TextView txtUNITID = new TextView(this);
            RelativeLayout.LayoutParams txtUNITIDParam = new RelativeLayout.LayoutParams
                    ((int) getResources().getDimension(R.dimen._140sdp), (int) getResources().getDimension(R.dimen._72sdp));
            //txtUNITIDParam.setMarginStart(20);
            // txtUNITIDParam.setMargins(0, 0, 0, 0);
            txtUNITIDParam.addRule(RelativeLayout.ALIGN_PARENT_END);
            txtUNITID.setGravity(Gravity.CENTER);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
           // txtUNITID.setText(getResources().getString(R.string.Set_text));
            txtUNITID.setText(mSettingModelResponse.get(i).getUnit());
            txtUNITID.setTextSize((getResources().getDimension(R.dimen._12sdp)));
           // txtGetID.setId(i + 5);
            txtUNITID.setTypeface(null, Typeface.NORMAL);
            //  tv_Code.setId(i + 2);
            txtUNITID.setTextColor(getResources().getColor(R.color.blue_fb));
           // txtUNITID.setBackground(getResources().getDrawable(R.drawable.blue_btn_round));
            txtUNITID.setLayoutParams(txtUNITIDParam);
            rlvEDITLayout.addView(txtUNITID);

            //mTagIDIndex++;
            rlvMainDynamicViewID.addView(iv_sub_linearlayout12);
            lvlMainParentLayoutID.addView(rlvMainDynamicViewID);

        }

    }


    /////////////////////////bt read write
    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDynamicParameterWrite extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        int bytesRead = 0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                       /* try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }*/
                    }
                }
                else
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(500);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int[] bytesReaded = new int[4];
                    int[] crc_bytesReaded = new int[2];

                    for (int i = 0; i < 6; i++) {
                        int mCharOne11 = iStream.read();
                    }

                    int mTotalTimeInt = 0;
                    for (int i = 0; i < 4; i++) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);

                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }

                    mTotalTimeInt = bytesReaded[0];
                    mTotalTimeInt |= bytesReaded[1] << 8;
                    mTotalTimeInt |= bytesReaded[2] << 16;
                    mTotalTimeInt |= bytesReaded[3] << 24;


                    System.out.println("mTotalTimeInt==>>JAI Hind==>>"+Float.intBitsToFloat(mTotalTimeInt));

                    mTotalTimeFloatData = Float.intBitsToFloat(mTotalTimeInt);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                           /* mSettingModelResponse.get(mGlobalPosition).setEditValue(Float.parseFloat(mEditTextList.get(mGlobalPosition).getText().toString().trim()));
                            addDynamicViewPro(mSettingModelResponse);*/

                            mEditTextList.get(mGlobalPosition).setText(""+mTotalTimeFloatData);
                            changeButtonVisibility(true,1.0f, mTextViewSetIDtList.get(mGlobalPosition));

                        }
                    });

                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {
                baseRequest.hideLoader();
                //btSocket = null;
                //mmCheckStart = 1;
                //   Toast.makeText(mContext, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            baseRequest.hideLoader();

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDynamicParameterRead extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        int bytesRead = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (!btSocket.isConnected()) {
                        btSocket.connect();//start connection
                    }
                }
                else
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();

                    if (!btSocket.isConnected()) {
                        btSocket.connect();//start connection
                    }
                }


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int[] bytesReaded = new int[2];

                    for (int i = 0; i < 6; i++) {

                    }
                    int mTotalTime = 0;

                    for (int i = 0; i < 1; i++) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            System.out.println("bytesReaded[0]==>>vvv1  ii=>>"+i+" "+bytesReaded[i]);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i+1] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            System.out.println("bytesReaded[1]==>>vvv1  ii=>>"+i+" "+bytesReaded[i]);


                            mTotalTime = bytesReaded[i];
                            System.out.println("mTotalTime==>>vvv1  ii=>>"+i+" "+mTotalTime);
                            mTotalTime |= bytesReaded[i + 1] << 8;
                            System.out.println("mTotalTime==>>vvv2  ii=>>"+i+" "+mTotalTime);

                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }

                    mTotalTimeFloatData = 0;
                    mTotalTimeFloatData = mTotalTime;

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //changeButtonVisibility(true, 1.0f, mEditTextList.get(mGlobalPosition));
                            mSettingModelResponse.get(mGlobalPosition).setEditValue(mTotalTimeFloatData);
                            // addDynamicViewPro(mSettingModelResponse);
                            mEditTextList.get(mGlobalPosition).setText(""+mTotalTimeFloatData);
                            changeButtonVisibility(true,1.0f, mTextViewSetIDtList.get(mGlobalPosition));
                        }
                    });


                    for (int ii = 0; ii < 4; ii++) {
                        int mCharOne11 = iStream.read();
                    }

                    while (iStream.available() > 0) {
                        int mCharOne11 = iStream.read();
                    }
                   /* mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mEditTextList.get(mGlobalPosition).setText(""+mTotalTimeFloatData);
                            changeButtonVisibility(true,1.0f, mTextViewSetIDtList.get(mGlobalPosition));
                        }
                    });*/
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();

                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            baseRequest.hideLoader();

        }
    }

    public static int CRC16_MODBUS(char []buf, int len)
    {

        int crc = 0xFFFF;
        int pos=0,i=0;
        for ( pos = 0; pos < len; pos++)
        {
            crc ^= (int)buf[pos];    // XOR byte into least sig. byte of crc

            for ( i = 8; i != 0; i--)
            {    // Loop over each bit
                if ((crc & 0x0001) != 0)
                {      // If the LSB is set
                    crc >>= 1;                    // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                }
                else                            // Else LSB is not set
                    crc >>= 1;                    // Just shift right
            }
        }

        return crc;
    }

    private void changeButtonVisibility(boolean state, float alphaRate, TextView text) {
        text.setEnabled(state);
        text.setAlpha(alphaRate);
    }

}
