package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.AllPopupUtil;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.GlobalClass.UtilMethod;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.Bluetooth_DeviceLists_Activity;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.DaynamicButton.DynamicBTNModel;
import com.shaktipumps.shakti_rms.bean.DaynamicButton.DynamicBTNResponse;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphResponse;
import com.shaktipumps.shakti_rms.model.TotalEnergyConsuptinModel.TotalEndAndConsuptionsResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class ActivityDeviceDetailsInfoElite extends AppCompatActivity {
    private CardView cardview;
    private TextView textview;
    private Context mContext;
    private DatabaseHelperTeacher mDatabaseHelperTeacher;
    private List<DynamicBTNResponse> mDynamicBTNResponse;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    private int pp = 0;
    private boolean mCheckFirst;

    private TextView txtBluetoothId, txtOtherId;
    private ViewFlipper flvViewFlipperID;
    private BaseRequest baseRequest;

    private TotalEndAndConsuptionsResponse mTotalEndAndConsuptionsResponse;
    private EnergyGraphResponse mEnergyGraphResponse;

    String modeBusCommand = null;//write

    char mCRCFinalValue;

    private int check_self_click = 0;

    private int mPosition = 0;

    private String mImageUrl = null;

    private CircleImageView imgDeviceImageID;
    private TextView txtHeaderID;
    private ImageView imgHeaderID;

    private String vMUserId,
            vDeviceType, vDeviceNo, vStartdate, isLoginCheck, isPumpCheck, vMDeviceId;

    // private TextView txtTotalEnergyValueID, txtConsumptionValueID;
    private UUID mMyUDID;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;

    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();


    int clientid = 0;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", MDeviceId = "null", CustomerName = "null", mModelType = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";


    private ImageView imgData_ExtractionID, product_image, imgBTAndInternetToggleID;
    private TextView start_gps, stop_gps, real_monitoring;
    private List<Customer_GPS_Search> customerSearchesList = null;
    private boolean mCheckBT = false;

    private RelativeLayout rlvBackViewID;

    private ImageView imgInternetToggleID, imgBTToggleID;

    private Switch switch1;

    private View mView;

    private PieChart pieChartID;
    private TextView device_setting;
    private TextView txtDataExtractionID;

    private InputStream iStream = null;

    private ProgressDialog progressDialog;

    int mmCheckStart = 0;
    int mmCheckStop = 0;
    private CountDownTimer yourCountDownTimer;

    // private ImageView imgMoterStatusID;


    private CircleButton imgMoterStatusID, txtDeleteButtonID;
    private TextView txtMotorStatusIID;

    // private TextView txtCurrentfaultCountID;
    //  private TextView txtTotalfaultCountID;


    float total_fault;
    int current_fault;

    String StatusMEssage = "";
    String StatusMEssageAlert = "";

    CardView crdSettingViewID, crdRealViewID, crdStopViewID, crdStartViewID;
    //CardView crdDataExcViewID;

    LinearLayout rlvStartStopBottomViewID, rlvStartStopViewID;

    private String versionName = "0.0";
    private TextView txtAppUserNameID, txtAppVersionID;
    private RelativeLayout rlvSlideMenuViewID;
    private RelativeLayout rlvMenuOptionHomeViewID, rlvMenuOptionChangePassViewID, rlvMenuOptionAddDeviceViewID, rlvMenuOptionFaultReportViewID, rlvMenuOptionDataReportViewID, rlvMenuOptionLogoutViewID;
    private LinearLayout lvlStartStopInnerMainViewID;

    private Activity mActivity;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details_info_elite);
        mContext = this;
        mActivity = this;

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        initView();
    }


    @Override
    public void onDestroy() {
        try {
            progressDialog.dismiss();

            if (progressDialog != null)
                progressDialog = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    private void SideMenuInitView(int OEM_Check) {
        rlvSlideMenuViewID = (RelativeLayout) findViewById(R.id.rlvSlideMenuViewID);
        rlvMenuOptionHomeViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionHomeViewID);
        rlvMenuOptionAddDeviceViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionAddDeviceViewID);
        rlvMenuOptionChangePassViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionChangePassViewID);
       /* rlvMenuOptionFaultReportViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionFaultReportViewID);
        rlvMenuOptionDataReportViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionDataReportViewID);*/
        rlvMenuOptionLogoutViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionLogoutViewID);

        txtDeleteButtonID = (CircleButton) findViewById(R.id.txtDeleteButtonID);
        txtAppUserNameID = (TextView) findViewById(R.id.txtAppUserNameID);
        txtAppVersionID = (TextView) findViewById(R.id.txtAppVersionID);
        String user_name = "Welcome,  " + pref.getString("key_login_username", " ");
        txtAppUserNameID.setText(user_name);
        String version = "App Version: " + versionName;
       // txtAppVersionID.setText(version);
        txtAppVersionID.setText(getString(R.string.MenuAppVersion)+""+NewSolarVFD.versionNameForAll);

        if (OEM_Check == 0) {
            rlvMenuOptionAddDeviceViewID.setVisibility(View.VISIBLE);
        } else {
            rlvMenuOptionAddDeviceViewID.setVisibility(View.GONE);
        }

        rlvSlideMenuViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionHomeViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                mIntent.putExtra("mPos", mPosition);
               mContext.startActivity(mIntent);*/

                // callTotalEnergyAndConjuptionAPI();
                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionAddDeviceViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(mContext, AddDevice.class);
                    startActivity(intent);

                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionChangePassViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);
                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

/*        rlvMenuOptionFaultReportViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FaultReportActivity.class);
                intent.putExtra("from_date", "");
                intent.putExtra("to_date", "");
                startActivity(intent);
                //  overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionDataReportViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DataReportActivity.class);
                startActivity(intent);

                //  overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });*/

        rlvMenuOptionLogoutViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                //   overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });
    }


    public  void  logout(){

        new AlertDialog.Builder(this)

                .setTitle(getString(R.string.st_LogoutAlert))
                .setMessage(getString(R.string.st_DoyouwanttoLogoutthisapplication))
                .setPositiveButton(getString(R.string.st_Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        editor.putString("key_login", "N");
                        editor.putString("key_OTP", "9999");
                        editor.putString("key_mobile_number", "9999999999");

                        editor.putString("key_otp_for_user", "9999");
                        editor.putString("key_mparentid", "9999");
                        editor.putString("key_muserid","9999");
                        // editor.putString("key_clientid","9999");
                        editor.putString("key_clientid","0");
                        editor.putString("key_login_username", "Invalid User");
                        editor.putString("key_clientid_for_map","9999");
                        editor.putString("key_clientid_for_data_report","9999");
                        SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "0");

//                        editor.putString("key_time_zone_city","Invalid" );
//                        editor.putString("key_time_zone_short","Invalid");
//                        editor.putString("key_time_zone_long", "Invalid");
//                        editor.putString("key_time_zone_change", "Invalid");

                        editor.commit();

                        finish();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        //Intent intent = new Intent(MainActivity.this, ActivitySelectionDataWay.class);
                        startActivity(intent);

                        dialog.cancel();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(getString(R.string.st_No), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                        dialog.cancel();
                        dialog.dismiss();

                    }
                })
                .show();

    }

    private void initView() {

        MUserId = "";
        DeviceNo = "";
        DeviceType = "";
        CustomerName = "";
        Mobile = "";

        if (SharedPreferencesUtil.getData(mContext, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE) == null || SharedPreferencesUtil.getData(mContext, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase(""))
        {
            SharedPreferencesUtil.setData(mContext, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE, "en");
            SharedPreferencesUtil.setData(mContext, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_SAVE, "English");

            com.shaktipumps.shakti_rms.webservice.Constant.setLocale(mContext, "en");
        }
        else
        {
            String hhhhh = SharedPreferencesUtil.getData(mContext, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE);
            com.shaktipumps.shakti_rms.webservice.Constant.setLocale(mContext, hhhhh);
        }

        mDynamicBTNResponse = new ArrayList<>();

        mDatabaseHelperTeacher = new DatabaseHelperTeacher(this);
        baseRequest = new BaseRequest(this);
        Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;
        Constant.BT_DEVICE_NAME = "";
        Constant.BT_DEVICE_MAC_ADDRESS = "";
        mPosition = getIntent().getIntExtra("mPos", 0);
        customerSearchesList = (List<Customer_GPS_Search>) getIntent().getSerializableExtra("mDeviceDetail");
        vDeviceNo = customerSearchesList.get(mPosition).getDeviceNo();

        //MUserId = customerSearchesList.get(mPosition).getMUserId();
        try {
            MUserId = pref.getString("key_muserid", "invalid_muserid");
            clientid = Integer.parseInt(pref.getString("key_clientid", "0"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (clientid == 9999) {
            clientid = 0;
        }

        if (clientid > 0) {
            // single_user_displayView(0);
            SideMenuInitView(1);
            txtDeleteButtonID.setVisibility(View.GONE);
            crdSettingViewID.setVisibility(View.VISIBLE);
            rlvStartStopBottomViewID.setWeightSum(2f);

        } else {
            //org_user_displayView(0);
            SideMenuInitView(0);
            txtDeleteButtonID.setVisibility(View.VISIBLE);
            crdSettingViewID.setVisibility(View.GONE);
            rlvStartStopBottomViewID.setWeightSum(1f);
        }

       /* if (clientid == 0)
        {
            txtDeleteButtonID.setVisibility(View.VISIBLE);
        }
        else
        {
            txtDeleteButtonID.setVisibility(View.GONE);
        }*/

        DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        DeviceType = customerSearchesList.get(mPosition).getDeviceType();
        CustomerName = customerSearchesList.get(mPosition).getCustomer_name();
        Mobile = customerSearchesList.get(mPosition).getMobile();
        isLoginCheck = customerSearchesList.get(mPosition).getIsLogin();
        mModelType = customerSearchesList.get(mPosition).getModelType();
        isPumpCheck = customerSearchesList.get(mPosition).getPumpStatus();
        mImageUrl = customerSearchesList.get(mPosition).getDeviceImage();

        imgInternetToggleID = (ImageView) findViewById(R.id.imgInternetToggleID);
        imgBTToggleID = (ImageView) findViewById(R.id.imgBTToggleID);
        switch1 = (Switch) findViewById(R.id.switch1);
        lvlStartStopInnerMainViewID = (LinearLayout) findViewById(R.id.lvlStartStopInnerMainViewID);

        imgDeviceImageID = (CircleImageView) findViewById(R.id.imgDeviceImageID);
        txtHeaderID = (TextView) findViewById(R.id.txtHeaderID);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);


        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        txtHeaderID.setText(customerSearchesList.get(mPosition).getDeviceNo());


        if (Constant.CHECK_BACK_MENU_VIEW_ICON == 1) {
            imgHeaderID.setImageResource(R.drawable.icn_menu);
        } else {
            imgHeaderID.setImageResource(R.drawable.icn_back);
        }


        try {
            if(mImageUrl == null || mImageUrl.equalsIgnoreCase(""))
            {
            }else
            {
                Picasso.with(this).load(mImageUrl).placeholder(R.drawable.logo).into(imgDeviceImageID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // vMUserId = customerSearchesList.get(mPosition).getMUserId();
        vMUserId = pref.getString("key_muserid", "invalid_muserid");
        vDeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        vDeviceType = customerSearchesList.get(mPosition).getDeviceType();
        vMDeviceId = customerSearchesList.get(mPosition).getMDeviceId();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);


        product_image = (ImageView) findViewById(R.id.product_image);

        try {
            Picasso.with(this).load(mImageUrl).placeholder(R.drawable.logo).into(product_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  pieChartID = (PieChart) findViewById(R.id.pieChartID);

        //txtTotalfaultCountID.setText("Total Fault "+total_fault);
        start_gps = (TextView) findViewById(R.id.start_gps);
        stop_gps = (TextView) findViewById(R.id.stop_gps);


        rlvStartStopBottomViewID = (LinearLayout) findViewById(R.id.rlvStartStopBottomViewID);
        rlvStartStopViewID = (LinearLayout) findViewById(R.id.rlvStartStopViewID);


        crdSettingViewID = (CardView) findViewById(R.id.crdSettingViewID);
        crdRealViewID = (CardView) findViewById(R.id.crdRealViewID);
        //  crdDataExcViewID = (CardView) findViewById(R.id.crdDataExcViewID);
        crdStartViewID = (CardView) findViewById(R.id.crdStartViewID);
        crdStopViewID = (CardView) findViewById(R.id.crdStopViewID);


        txtDeleteButtonID = (CircleButton) findViewById(R.id.txtDeleteButtonID);
        imgMoterStatusID = (CircleButton) findViewById(R.id.imgMoterStatusID);
        txtMotorStatusIID = (TextView) findViewById(R.id.txtMotorStatusIID);

        device_setting = (TextView) findViewById(R.id.device_setting);
        real_monitoring = (TextView) findViewById(R.id.real_monitoring);
        //  txtDataExtractionID = (TextView) findViewById(R.id.txtDataExtractionID);

        try {
            if (isLoginCheck.equalsIgnoreCase("true")) {
                if (isPumpCheck.equalsIgnoreCase("true")) {
                    // imgMoterStatusID.setImageResource(R.mipmap.green_ball);//////////////
                    txtMotorStatusIID.setText("Start");
                    txtMotorStatusIID.setTextColor(getResources().getColor(R.color.white));
                } else {
                    txtMotorStatusIID.setText("Stop");
                    txtMotorStatusIID.setTextColor(getResources().getColor(R.color.white));
                    //  imgMoterStatusID.setImageResource(R.mipmap.red_ball); //////////////
                }
            } else {
                txtMotorStatusIID.setText("Disconnected");
                txtMotorStatusIID.setTextColor(getResources().getColor(R.color.white));
                // imgMoterStatusID.setImageResource(R.mipmap.grey_ball);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        txtDeleteButtonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //   setingAnddeletFunction(mContext, customerSearchesList, position);
                backConfirmPopupID(mContext, customerSearchesList, mPosition);

                //    delete_from_server();
            }
        });


        clickEventOnBTN();

        setAllEventClick();

        if (CustomUtility.isOnline(mContext)) {
            callDynamicBTNAPI();
        } else {
            mDynamicBTNResponse = mDatabaseHelperTeacher.getDeviceSTART_STOPDATA(vDeviceType);
            if (mDynamicBTNResponse.size() > 0)
                CreateCardViewProgrammatically();
        }

    }

    public void delete_from_server() {

        ArrayList<String> al;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);
        al = new ArrayList<>();

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();
        param.clear();

        delete_url = NewSolarVFD.DELETE_DEVICE + "?" + "MUserId=" + MUserId + "&" + "MDeviceId=" + vMDeviceId;
/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(mContext, "", "Please wait !");
        //Log.d("delete_otp1",MuserId +"--"+  MDeviceId +"--"+ mobile_number +"otp"+Delete_OTP );
        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(mContext)) {

                    try {

                        String obj = CustomHttpClient.executeHttpGet(delete_url);

                        //  Log.d("update_otp",""+ obj) ;

                        //   Log.d("delete_obj", "" + obj);
                        if (obj.equalsIgnoreCase("Successfully")) {

/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/******************************************************************************************/
                            progressDialog.dismiss();

                            Message msg2 = new Message();
                            msg2.obj = "Device Delete Successfully .";
                            mHandler.sendMessage(msg2);

                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.dismiss();
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_connection));
                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }

                } else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(mContext, "Error", "No Internet Connection");
                }

            }

        }.start();
    }


    private void callDynamicBTNAPI() {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    //TotalEndAndConsuptionsModelView mTotalEndAndConsuptionsModelView = gson.fromJson(Json, TotalEndAndConsuptionsModelView.class);
                    //  getTotalEndAndConsuptionsResponse(mTotalEndAndConsuptionsModelView);

                    DynamicBTNModel mDynamicBTNModel = gson.fromJson(Json, DynamicBTNModel.class);
                    getDynamicBTNResponse(mDynamicBTNModel);
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
            // jsonObject.addProperty("MUserId", vMUserId);
            jsonObject.addProperty("DeviceType", DeviceType);///Model type value
            //jsonObject.addProperty("DeviceType", vDeviceType);
            // jsonObject.addProperty("DeviceNo", vDeviceNo);
            //   jsonObject.addProperty("DeviceNo", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_PRODUCT_DYNAMIC_BTN);/////
    }

    private void getDynamicBTNResponse(DynamicBTNModel mDynamicBTNModel) {
        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mDynamicBTNModel.getStatus()) {
            if (mDynamicBTNResponse != null)
                mDynamicBTNResponse = null;

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mDynamicBTNResponse = mDynamicBTNModel.getResponse();

            if (mDynamicBTNResponse.size() > 0) {
                for (int i = 0; i < mDynamicBTNResponse.size(); i++) {

                    mDatabaseHelperTeacher.insertDeviceSTART_STOPData(MUserId, vMDeviceId, DeviceNo, DeviceType, mDynamicBTNResponse, i, mCheckFirst);

                }
            }


            CreateCardViewProgrammatically();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();
            // mDatabaseHelperTeacher.insertDeviceSHIMAData(MUserId, MDeviceId, DeviceNo, DeviceType,mShimaResponse,mCheckFirst);


        }
    }


    public void CreateCardViewProgrammatically() {


        for (int i = 0; i < mDynamicBTNResponse.size(); i++) {


            cardview = new CardView(mContext);

            /*layoutparams = new ViewGroup.LayoutParams(
                    100,
                    50
            );*/

            int mmWidth = UtilMethod.getDeviceHeightWidth(mContext, true);
            RelativeLayout.LayoutParams layoutparams;
            if(mmWidth > 780)
            {
                layoutparams = new RelativeLayout.LayoutParams(
                        UtilMethod.getDeviceHeightWidth(mContext, true) / 2 - 20, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            else
            {
                layoutparams = new RelativeLayout.LayoutParams(
                        UtilMethod.getDeviceHeightWidth(mContext, true) / 2 - 30, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        /* layoutparams = new RelativeLayout.LayoutParams(
                    UtilMethod.getDeviceHeightWidth(mContext, true) / 2 - 32, ViewGroup.LayoutParams.MATCH_PARENT);
*/
        /*  layoutparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );*/

            cardview.setLayoutParams(layoutparams);
            cardview.setRadius(12);


            //  cardview.setCardBackgroundColor(Color.MAGENTA);
            cardview.setCardBackgroundColor(Color.TRANSPARENT);
            cardview.setMaxCardElevation(15);

            if (i == 0) {
                layoutparams.setMargins((int) getResources().getDimension(R.dimen._4sdp), 0, 0, (int) getResources().getDimension(R.dimen._1sdp));
            } else {
                layoutparams.setMargins((int) getResources().getDimension(R.dimen._11sdp), 0, 0, (int) getResources().getDimension(R.dimen._1sdp));
            }

            cardview.setId(i + 1);
            cardview.setTag(mDynamicBTNResponse.get(i).getSno());

            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int iii = v.getId();
                    pp = iii - 1;

                    if (pp == 0) {
                        check_self_click = 1;////start
                    } else {
                        check_self_click = 2;////stop
                    }

                    start = mDynamicBTNResponse.get(pp).getData();
                    stop = mDynamicBTNResponse.get(pp).getOldData();

                    char[] datar = new char[4];
                    int a = 0;
                    try {
                        String string1 = mDynamicBTNResponse.get(pp).getData().replace(".0", "");
                        // String  ssssss = mDynamicBTNResponse.get(pp).getData().split(".0")
                        //  a = Integer.parseInt(mDynamicBTNResponse.get(pp).getData());
                        int aa = Integer.parseInt(string1);
                        a = Float.floatToIntBits((float) aa);

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    //  int a = Integer.valueOf(mDynamicBTNResponse.get(pp).getData());
                    //  int a= (int) edtValueFloat;
                    datar[0] = (char) (a & 0x000000FF);
                    datar[1] = (char) ((a & 0x0000FF00) >> 8);
                    datar[2] = (char) ((a & 0x00FF0000) >> 16);
                    datar[3] = (char) ((a & 0xFF000000) >> 24);
                    int crc = CRC16_MODBUS(datar, 4);
                    char reciverbyte1 = (char) ((crc >> 8) & 0x00FF);
                    char reciverbyte2 = (char) (crc & 0x00FF);

                    mCRCFinalValue = (char) (reciverbyte1 + reciverbyte2);

                    String v1 = String.format("%02x", (0xff & datar[0]));
                    String v2 = String.format("%02x", (0xff & datar[1])); //String v2 =Integer.toHexString(datar[1]);
                    String v3 = String.format("%02x", (0xff & datar[2]));
                    String v4 = String.format("%02x", (0xff & datar[3]));
                    String v5 = Integer.toHexString(mCRCFinalValue);

                    modeBusCommand = null;//write
                    try {
                        String mMOBADDRESS = "";
                        String mMobADR = mDynamicBTNResponse.get(pp).getBTAddress();
                        if (!mMobADR.equalsIgnoreCase("")) {
                            int mLenth = mMobADR.length();
                            if (mLenth == 1) {
                                mMOBADDRESS = "000" + mMobADR;
                            } else if (mLenth == 2) {
                                mMOBADDRESS = "00" + mMobADR;
                            } else if (mLenth == 3) {
                                mMOBADDRESS = "0" + mMobADR;
                            } else {
                                mMOBADDRESS = mMobADR;
                            }
                        } else {
                            Toast.makeText(mContext, "MOB address not found!", Toast.LENGTH_SHORT).show();
                        }
                        modeBusCommand = "0106" + mMOBADDRESS + v1 + v2 + v3 + v4 + v5;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (start.equalsIgnoreCase("1") || start.equalsIgnoreCase("1.0")) {
                        StatusMEssage = getString(R.string.st_Doyouwanttostartdevice);
                        StatusMEssageAlert = getString(R.string.st_StartAlert);
                    } else {
                        StatusMEssage = getString(R.string.st_Doyouwanttostopdevice);
                        StatusMEssageAlert = getString(R.string.st_StopAlert);
                    }
                    //  String modeBusCommand = "0103"+mSettingModelResponse.get(pp).getMobBTAddress()+v1+v2+v3+v4+v5;//write
                    System.out.println("modeBusCommand==>>vvv=>>modeBusCommand==>>" + modeBusCommand);

                    if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {

                        if (CustomUtility.isOnline(mActivity)) {
                            //  DeviceNo = "";
                            //  DeviceType = "";

                            new AlertDialog.Builder(mActivity)
                                    .setTitle(StatusMEssageAlert)


                                    .setMessage(StatusMEssage)
                                    .setPositiveButton(getString(R.string.st_Yes), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            baseRequest.showLoader();
                                            progressDialog = ProgressDialog.show(mActivity, "", getString(R.string.st_Pleasewait));
                                            //   DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
                                            //   DeviceType = customerSearchesList.get(mPosition).getDeviceType();
                                            //  userid = customerSearchesList.get(mPosition).getMUserId();
                                            //  start = "1";
                                            //   stop = "0";
                                            // changeButtonVisibility(false, 0.05f,  start_gps);
                                            callURL(DeviceNo, start, stop, MUserId, DeviceType);
                                            dialog.cancel();
                                            dialog.dismiss();

                                        }
                                    })

                                    .setNegativeButton(getString(R.string.st_No), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // user doesn't want to logout
                                            dialog.cancel();
                                            dialog.dismiss();

                                        }
                                    })
                                    .show();
                        }//////////////////internet nahi hai
                        else {
                            Toast.makeText(mActivity, getString(R.string.st_Pleasecheckinternetconnection), Toast.LENGTH_SHORT).show();
                        }

                    }//////////////else
                    else {

                        new AlertDialog.Builder(mActivity)
                                .setTitle(StatusMEssageAlert)
                                .setMessage(StatusMEssage)
                                .setPositiveButton(getString(R.string.st_Yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        baseRequest.showLoader();
                                        //  progressDialog = ProgressDialog.show(mActivity, "", "Please Wait..");
                                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                        if (mBluetoothAdapter.isEnabled()) {
                                            if (AllPopupUtil.pairedDeviceListGloable(mActivity)) {

                                                if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                                    //    new BluetoothCommunicationForMotorStart().execute(":TURNON#", ":TURNON#", "START");
                                                    if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                                        //changeButtonVisibility(false, 0.05f, start_gps);
                                                        //new BluetoothCommunicationForMotorStart().execute("0106000A0000803FAED7", ":TURNON#", "START");
                                                        new BluetoothCommunicationForMotorStart().execute(modeBusCommand, ":TURNON#", "START");
                                                    } else {
                                                        Toast.makeText(mContext, getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Intent intent = new Intent(mActivity, PairedDeviceActivity.class);
                                                    mActivity.startActivity(intent);
                                                }
                                                ///////////////write the query
                                                //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                                            } else {
                                                // AllPopupUtil.btPopupCreateShow(mActivity);
                                                // AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                                startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                            }
                                        } else {
                                            //  AllPopupUtil.btPopupCreateShow(mActivity);
                                            // AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                            startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                        }
                                        dialog.cancel();
                                        dialog.dismiss();

                                    }
                                })

                                .setNegativeButton(getString(R.string.st_No), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // user doesn't want to logout
                                        dialog.cancel();
                                        dialog.dismiss();

                                    }
                                })
                                .show();


                    }
                    
                    
                /*    int iii = v.getId();
                    pp = iii - 1;

                    start = mDynamicBTNResponse.get(pp).getData();
                    stop = mDynamicBTNResponse.get(pp).getOldData();

                    if (start.equalsIgnoreCase("1") || start.equalsIgnoreCase("1.0")) {
                        StatusMEssage = "Do you want to start device ?";
                        StatusMEssageAlert = "Start Alert!";
                    } else {
                        StatusMEssage = "Do you want to stop device ?";
                        StatusMEssageAlert = "Stop Alert!";
                    }

                    //  String modeBusCommand = "0103"+mSettingModelResponse.get(pp).getMobBTAddress()+v1+v2+v3+v4+v5;//write
                    //    System.out.println("modeBusCommand==>>vvv=>>modeBusCommand==>>" + modeBusCommand);


                    if (CustomUtility.isOnline(mContext)) {
                        //  DeviceNo = "";
                        //  DeviceType = "";

                        new AlertDialog.Builder(mContext)
                                .setTitle(StatusMEssageAlert)
                                .setMessage(StatusMEssage)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        baseRequest.showLoader();
                                        progressDialog = ProgressDialog.show(mContext, "", "Please Wait..");
                                        //   DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
                                        //   DeviceType = customerSearchesList.get(mPosition).getDeviceType();
                                        //  userid = customerSearchesList.get(mPosition).getMUserId();
                                        //  start = "1";
                                        //   stop = "0";
                                        // changeButtonVisibility(false, 0.05f,  start_gps);
                                        callURL(DeviceNo, start, stop, MUserId, DeviceType);
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
                    }//////////////////internet nahi hai
                    else {
                        Toast.makeText(mContext, "Please check internet connection!!", Toast.LENGTH_SHORT).show();
                    }
*/
                    //////////////else


                }
            });

            // cardview.setMaxCardElevation(6);

            RelativeLayout mRLVBoxLayout = new RelativeLayout(mContext);
            RelativeLayout.LayoutParams mRLVBoxLayoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            // mRLVBoxLayout.setBackgroundResource(R.drawable.bg_round_corner_transparent_blue);
            mRLVBoxLayout.setBackgroundColor(Color.parseColor(mDynamicBTNResponse.get(i).getBColor()));


            mRLVBoxLayout.setLayoutParams(mRLVBoxLayoutParams);


            textview = new TextView(mContext);
            RelativeLayout.LayoutParams mRLVBoxLayoutParams1 = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            mRLVBoxLayoutParams1.addRule(Gravity.CENTER);
            textview.setLayoutParams(mRLVBoxLayoutParams1);
            textview.setText(mDynamicBTNResponse.get(i).getButtonText());
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen._8ssp));
            textview.setTextColor(Color.WHITE);
            // Typeface face= Typeface.createFromAsset(mContext.getAssets(),"font/open_sans_semibold.ttf.ttf");
            textview.setTypeface(null, Typeface.BOLD);
            // textview.setPadding(25,25,25,25);
            textview.setGravity(Gravity.CENTER);
            // textview.setFon
            textview.setTextColor(Color.WHITE);
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            mRLVBoxLayout.addView(textview);
            cardview.addView(mRLVBoxLayout);

            lvlStartStopInnerMainViewID.addView(cardview);

        }

    }


    public static int CRC16_MODBUS(char[] buf, int len) {

        int crc = 0xFFFF;
        int pos = 0, i = 0;
        for (pos = 0; pos < len; pos++) {
            crc ^= (int) buf[pos];    // XOR byte into least sig. byte of crc

            for (i = 8; i != 0; i--) {    // Loop over each bit
                if ((crc & 0x0001) != 0) {      // If the LSB is set
                    crc >>= 1;                    // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else                            // Else LSB is not set
                    crc >>= 1;                    // Just shift right
            }
        }

        return crc;
    }

    private void clickEventOnBTN() {

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    imgBTToggleID.setImageResource(R.drawable.iv_bluetooth_selected);
                    imgInternetToggleID.setImageResource(R.drawable.iv_connection_unselected);

                    Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 1;
                    if (mModelType.equalsIgnoreCase("8")) {
                        // Intent ii = new Intent(mContext, Options_Activity.class);
                        //  mContext.startActivity(ii);
                        SharedPreferences sharedPref = mContext.getSharedPreferences("Pref", 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("user_comm_option", "B");
                        editor.apply();
                        Intent i = new Intent(mContext, Bluetooth_DeviceLists_Activity.class);
                        startActivity(i);

                    } else {
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter.isEnabled()) {
                            if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                    Intent intent = new Intent(mContext, PairedDeviceActivity.class);
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
                        }
                    }
                    // switch1.setChecked(false);
                } else {
                    imgBTToggleID.setImageResource(R.drawable.iv_bluetooth_unselected);
                    imgInternetToggleID.setImageResource(R.drawable.iv_connection_selected);

                    Constant.BT_DEVICE_NAME = "";
                    Constant.BT_DEVICE_MAC_ADDRESS = "";
                    Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;
                    // switch1.setChecked(true);
                }
                //NewSolarVFD.popupDialodCustom(mContext);

            }
        });
        //imgInternetToggleID, imgBTToggleID;
        /*rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                if (Constant.CHECK_BACK_MENU_VIEW_ICON == 1) {
                    rlvSlideMenuViewID.setVisibility(View.VISIBLE);
                    overridePendingTransition(R.anim.lefr_to_right, R.anim.right_to_left);
                } else {
                    finish();
                }


            }
        });
    }

    /////////////////////////////////////////////////////////////////

    private void setAllEventClick() {

        start_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {

                    if (CustomUtility.isOnline(mContext)) {
                        //  DeviceNo = "";
                        //  DeviceType = "";
                        if (permission_validation()) {
                            new AlertDialog.Builder(mContext)
                                    .setTitle("Start Alert !")
                                    .setMessage("Do you want to start device ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            progressDialog = ProgressDialog.show(mContext, "", "Please Wait..");
                                            //   DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
                                            //   DeviceType = customerSearchesList.get(mPosition).getDeviceType();
                                            //  userid = customerSearchesList.get(mPosition).getMUserId();
                                            start = "1";
                                            stop = "0";
                                            callURL(DeviceNo, start, stop, MUserId, DeviceType);
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
                    }//////////////////internet nahi hai
                    else {

                        Toast.makeText(mContext, "Please check internet connection!!", Toast.LENGTH_SHORT).show();
                    }

                }//////////////else
                else {

                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                            if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                //    new BluetoothCommunicationForMotorStart().execute(":TURNON#", ":TURNON#", "START");
                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                    new BluetoothCommunicationForMotorStart().execute("0106000A0000803FAED7", ":TURNON#", "START");
                                } else {
                                    Toast.makeText(mContext, getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Intent intent = new Intent(mContext, PairedDeviceActivity.class);
                                startActivity(intent);
                            }
                            ///////////////write the query
                            //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                        } else {
                            // AllPopupUtil.btPopupCreateShow(mContext);
                            // AllPopupUtil.BT_OR_Internet_SelectionFun(mContext);
                            startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                        }
                    } else {
                        //  AllPopupUtil.btPopupCreateShow(mContext);
                        // AllPopupUtil.BT_OR_Internet_SelectionFun(mContext);
                        startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                }
            }
        });

        stop_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                    if (CustomUtility.isOnline(mContext)) {
                        //  DeviceNo = "";
                        //  DeviceType = "";

                        if (permission_validation()) {

                            new AlertDialog.Builder(mContext)

                                    .setTitle("Stop Alert !")
                                    .setMessage("Do you want to stop device ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            progressDialog = ProgressDialog.show(mContext, "", "Please Wait..");

                                            //  DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
                                            //  DeviceType = customerSearchesList.get(mPosition).getDeviceType();
                                           /* start = "0";
                                            stop = "1";*/
                                            //  userid = customerSearchesList.get(mPosition).getMUserId();
                                            callURL(DeviceNo, start, stop, MUserId, DeviceType);
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

                    }//////////else part
                    else {
                    }
                } else {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        if (AllPopupUtil.pairedDeviceListGloable(mContext)) {
                            ///////////////write the query
                            // new BluetoothCommunicationForMotorStart().execute(":TURNON#", ":TURNON#", "START");

                            if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                //new BluetoothCommunicationForMotorStop().execute(":TURNOFF#", ":TURNOFF#", "STOP");
                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                    new BluetoothCommunicationForMotorStop().execute("0106000A000000002AC6", ":TURNOFF#", "STOP");
                                } else {
                                    Toast.makeText(mContext, getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Intent intent = new Intent(mContext, PairedDeviceActivity.class);
                                startActivity(intent);
                            }
                            //  new BluetoothCommunicationForMotorStop().execute(":TURNOFF#", ":TURNOFF#", "STOP");
                        } else {
                            // AllPopupUtil.btPopupCreateShow(mContext);
                            //AllPopupUtil.BT_OR_Internet_SelectionFun(mContext);
                            startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                        }
                    } else {
                        //  AllPopupUtil.btPopupCreateShow(mContext);
                        //  AllPopupUtil.BT_OR_Internet_SelectionFun(mContext);
                        startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                }
            }
        });

       /* txtDataExtractionID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                    if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                        Intent intentP = new Intent(mContext, GetBTDATAListActivity.class);
                        intentP.putExtra("BtNameHead", Constant.BT_DEVICE_NAME);
                        intentP.putExtra("BtMacAddressHead", Constant.BT_DEVICE_MAC_ADDRESS);
                        startActivity(intentP);
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intentppp = new Intent(mContext, PairedDeviceActivity.class);
                    startActivity(intentppp);
                }
            }
        });*/

        real_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                    showMonitoring();
                } else {

                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                            if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                Intent intent = new Intent(mContext, PairedDeviceActivity.class);
                                startActivity(intent);

                            } else {


                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                    if (mModelType.equalsIgnoreCase("1")) {
                                        Intent intent = new Intent(mContext, RealMonitoringBTShimhaActivity.class);
                                        intent.putExtra("MUserId", MUserId);
                                        intent.putExtra("DeviceType", DeviceType);
                                        intent.putExtra("DeviceNo", DeviceNo);
                                        intent.putExtra("Mobile", Mobile);
                                        intent.putExtra("CustomerName", CustomerName);
                                        intent.putExtra("ModelType", mModelType);

                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(mContext, RealMonitoringBTShimhaActivity.class);
                                        intent.putExtra("MUserId", MUserId);
                                        intent.putExtra("DeviceType", DeviceType);
                                        intent.putExtra("DeviceNo", DeviceNo);
                                        intent.putExtra("Mobile", Mobile);
                                        intent.putExtra("CustomerName", CustomerName);
                                        intent.putExtra("ModelType", mModelType);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(mContext, getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                }
                            }
                            ///////////////write the query
                            //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                        } else {
                            // AllPopupUtil.btPopupCreateShow(mContext);
                            startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                        }
                    } else {
                        //  AllPopupUtil.btPopupCreateShow(mContext);
                        startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                    /////////////////add bluetooth cod here
                }

            }
        });

        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMap();
            }
        });


        device_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  holder.rlvSideSetingAndDeletePopupID.setVisibility(View.GONE);
                // String
                /*if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0)
                {
                    try {

                        //Intent intent = new Intent(mContext, GritGraphActivity.class);
                        Intent intent = new Intent(mContext, DeviceSettingActivity.class);
                        intent.putExtra("MUserId", MUserId);
                        intent.putExtra("DeviceType", DeviceType);
                        intent.putExtra("DeviceNo", DeviceNo);
                        startActivity(intent);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }////////////else part of condition*/


                if(NewSolarVFD.MOTOR_ON_OFF_CHECK == 1) {
                    Toast.makeText(mContext,"One's motor is running so on that setting is disable.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //  holder.rlvSideSetingAndDeletePopupID.setVisibility(View.GONE);
                    if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                        try {
                            Intent intent = new Intent(mActivity, DeviceSettingActivity.class);
                            intent.putExtra("MUserId", MUserId);
                            intent.putExtra("DeviceType", DeviceType);
                            intent.putExtra("DeviceNo", DeviceNo);
                            mActivity.startActivity(intent);

                       /* if (SharedPreferencesUtil.getData(mActivity, Constant.CHECK_APP_DEVICE_TYPE).equalsIgnoreCase("1")) {

                            //Intent intent = new Intent(mActivity, GritGraphActivity.class);
                            Intent intent = new Intent(mActivity, DeviceSettingActivity.class);
                            intent.putExtra("MUserId", MUserId);
                            intent.putExtra("DeviceType", DeviceType);
                            intent.putExtra("DeviceNo", DeviceNo);
                            mActivity.startActivity(intent);

                        } else if (SharedPreferencesUtil.getData(mActivity, Constant.CHECK_APP_DEVICE_TYPE).equalsIgnoreCase("2")) {
                            Snackbar snackbar = Snackbar.make(view, "This device is not permited to setting screen", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }////////////else part of condition
                    else {
                        try {

                            String ssssssss = SharedPreferencesUtil.getData(mActivity, Constant.CHECK_APP_DEVICE_TYPE);
                            if (ssssssss.equalsIgnoreCase("1")) {

                                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                if (mBluetoothAdapter.isEnabled()) {
                                    if (AllPopupUtil.pairedDeviceListGloable(mActivity)) {

                                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                            Intent intent = new Intent(mActivity, PairedDeviceActivity.class);
                                            mActivity.startActivity(intent);

                                        } else {
                                            //  Toast.makeText(mActivity, "Constant.BT_DEVICE_NAME==>> "+Constant.BT_DEVICE_NAME+"\n DeviceNo===>> "+DeviceNo, Toast.LENGTH_LONG).show();
                                            if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                                if (mModelType.equalsIgnoreCase("1")) {
                                                    Intent intent = new Intent(mContext, DeviceSettingBTShimhaActivity.class);
                                                    intent.putExtra("MUserId", MUserId);
                                                    intent.putExtra("DeviceType", DeviceType);
                                                    intent.putExtra("DeviceNo", DeviceNo);
                                                    startActivity(intent);
                                                } else {
                                                    Intent intent = new Intent(mContext, DeviceSettingBTShimhaActivity.class);
                                                    intent.putExtra("MUserId", MUserId);
                                                    intent.putExtra("DeviceType", DeviceType);
                                                    intent.putExtra("DeviceNo", DeviceNo);
                                                    startActivity(intent);
                                                }
                                        /*    Intent intent = new Intent(mActivity, DeviceSettingBTShimhaActivity.class);
                                            intent.putExtra("MUserId", MUserId);
                                            intent.putExtra("DeviceType", DeviceType);
                                            intent.putExtra("DeviceNo", DeviceNo);
                                            mActivity.startActivity(intent);*/
                                            } else {
                                                Toast.makeText(mContext, getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        ///////////////write the query

                                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                                    } else {
                                        // AllPopupUtil.btPopupCreateShow(mActivity);
                                        // AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                       startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                    }
                                } else {
                                    //  AllPopupUtil.btPopupCreateShow(mActivity);
                                    //AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                    startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                }

                            } else if (SharedPreferencesUtil.getData(mActivity, Constant.CHECK_APP_DEVICE_TYPE).equalsIgnoreCase("2")) {
                                Snackbar snackbar = Snackbar.make(view, "This device is not permited to setting screen", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }





            }
        });

    }


    public boolean permission_validation() {

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            check_Permission(mContext);
            //  CustomUtility.isErrorDialog(mContext,getString(R.string.error),getString(R.string.err_app_permission));
            Toast.makeText(mContext, R.string.err_app_permission, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }


    public void backConfirmPopupID(final Context context, final List<Customer_GPS_Search> customerSearchesList, final int pos) {
        ////////////////////////////
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.alertDialogDelete);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //  builder.setTitle(getString(R.string.Back_head));
        builder.setMessage(mContext.getString(R.string.delete_msg_text));

        builder.setCancelable(false);
        String positiveText = mContext.getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss alert dialog, update preferences with game score and restart play fragment

                        MUserId = "";
                        DeviceNo = "";
                        DeviceType = "";
                        MDeviceId = "";

                        //MUserId = customerSearchesList.get(pos).getMUserId();
                        MUserId = pref.getString("key_muserid", "invalid_muserid");
                        DeviceNo = customerSearchesList.get(pos).getDeviceNo();
                        DeviceType = customerSearchesList.get(pos).getDeviceType();
                        CustomerName = customerSearchesList.get(pos).getCustomer_name();
                        // Mobile = customerSearchesList.get(position).getMobile();
                        MDeviceId = customerSearchesList.get(pos).getMDeviceId();

                        //** this mobile no is enter by user when new account open
                        Mobile = pref.getString("key_mobile_number", "invalid_mobno");

                        if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                            //  delete_device_confirmation();
                            delete_from_server();

                        } else {
                            /////////////////add bluetooth cod here
                        }
                        // Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;/////////this is for internet
                        dialog.dismiss();

                        Log.d("myTag", "positive button clicked");
                        dialog.dismiss();
                    }
                });

        String negativeText = mContext.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss dialog, start counter again
                        dialog.dismiss();
                        Log.d("myTag", "negative button clicked");
                    }
                });

        AlertDialog dialog = builder.create();
// display dialog
        dialog.show();

    }


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

//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//            intent.setData(uri);
//            context.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
//
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//            intent.setData(uri);
//            context.startActivity(intent);
//

        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PermissionsIntent.READ_PHONE_STATE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForMotorStart extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

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
                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//                if (btSocket == null) {
//                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
//                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
//                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
//                    myBluetooth.cancelDiscovery();
//                }
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


                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (RetryCount != 0 && RetryCount > maxRetryCount) maxRetryCount = RetryCount;
                    mmCheckStart = 0;
                    int available;
                    int a;
                   /* while ((a = iStream.read()) > -1) {
                        baseRequest.hideLoader();
                        System.out.println("bytesReaded[i]=VIKAS_SHAKTI==>>>> " + a);
                    }*/
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {
                baseRequest.hideLoader();
                // btSocket = null;
                mmCheckStart = 1;
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
            if (mmCheckStart == 1) {
                Toast.makeText(mContext, "BT Connection intrupted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Motor start succesfully.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    ////////////////////bt stop command

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForMotorStop extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;


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
                      /*  try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }*/
                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
               /* if (btSocket == null) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }*/
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


                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (RetryCount != 0 && RetryCount > maxRetryCount) maxRetryCount = RetryCount;
                    int available;
                    mmCheckStart = 0;
                    /// baseRequest.hideLoader();
                    int a;
                  /*  while ((a = iStream.read()) > -1) {
                        System.out.println("bytesReaded[i]=VIKAS_SHAKTI==>>>> " + a);
                    }*/
                    //  System.out.println("bytesReaded[i]=VIKAS_SHAKTI==>>>> " + iStream);
                    //  int[] bytesReaded;
                    //  System.out.println("bytesReaded[i]=fsdfsdf==>>");
                }
            } catch (Exception e) {

                //   Toast.makeText(mContext, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                mmCheckStop = 1;
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
            if (mmCheckStop == 1) {
                Toast.makeText(mContext, "BT Connection intrupted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Motor stop succesfully.", Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void callURL(final String DeviceNo, final String start, final String stop, final String userid, final String DeviceType) {

        new Thread(new Runnable() {


            @Override
            public void run() {

                if (CustomUtility.isOnline(mActivity)) {

                    baseRequest.showLoader();

                    try {
                        String obj = "";
                        param.clear();

                       /* if (start.equalsIgnoreCase("1") || start.equalsIgnoreCase("1.0")) {
                            param.add(new BasicNameValuePair("address1", "1"));
                        } else {
                            param.add(new BasicNameValuePair("address1", "10"));
                        }*/

                        param.add(new BasicNameValuePair("address1", mDynamicBTNResponse.get(pp).getAddress()));

                        progressDialog.dismiss();
                        //param.add(new BasicNameValuePair("address1","10" ));
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "1"));
                        param.add(new BasicNameValuePair("data1", start));               // start = 1
                        param.add(new BasicNameValuePair("OldData", stop));
                        param.add(new BasicNameValuePair("UserId", userid));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        //  param.add(new BasicNameValuePair("offset1", "1"));
                        param.add(new BasicNameValuePair("offset1", mDynamicBTNResponse.get(pp).getOffset()));//param.addnew BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        // obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                        // obj = CustomHttpClient.executeHttpPost1("http://111.118.249.180:1112/Home/DeviceSettingParam", param);
                        //Log.d("start", obj +start+"--"+stop);
                        progressDialog.dismiss();

                        if (!org.apache.http.util.TextUtils.isEmpty(obj)) {

                            // yourCountDownTimer.cancel();
                            progressDialog.dismiss();
                            //   Log.d("start1", obj );
                            JSONArray ja = new JSONArray(obj);
                            //  Log.d("start12", obj );
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);

                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {

                                    changeButtonVisibility(true, 1.0f, stop_gps);
                                    CustomUtility.isSuccessDialog(mActivity, mActivity.getString(R.string.success), mDynamicBTNResponse.get(pp).getCmdMsg());
                                    mActivity.runOnUiThread(new Runnable() {
                                        public void run() {
                                            // UI code goes here
                                            String ssss = mDynamicBTNResponse.get(pp).getButtonText();
                                            txtMotorStatusIID.setText(ssss);

                                            if (check_self_click == 1) {
                                                NewSolarVFD.MOTOR_ON_OFF_CHECK = 1;
                                            } else {
                                                NewSolarVFD.MOTOR_ON_OFF_CHECK = 2;
                                            }

                                        }
                                    });

                                    baseRequest.hideLoader();
                                   /* if (start.equalsIgnoreCase("1")) {
                                        changeButtonVisibility(true, 1.0f, start_gps);
                                        CustomUtility.isSuccessDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.succ_device_start));

                                    } else {
                                        changeButtonVisibility(true, 1.0f, stop_gps);
                                        CustomUtility.isSuccessDialog(mActivity, mActivity.getString(R.string.success), mActivity.getString(R.string.succ_device_stop));

                                    }*/
                                }

                                // disconnect message
                                if (jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest.hideLoader();
                                    CustomUtility.isErrorDialog(mActivity, mActivity.getString(R.string.error), mActivity.getString(R.string.err_device_disconnect));
                                    if (start.equalsIgnoreCase("1")) {
                                        changeButtonVisibility(true, 1.0f, start_gps);

                                    } else {
                                        changeButtonVisibility(true, 1.0f, stop_gps);
                                    }

                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest.hideLoader();
                                    if (start.equalsIgnoreCase("1")) {
                                        changeButtonVisibility(true, 1.0f, start_gps);
                                        CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_start_command_fail));

                                    } else {

                                        changeButtonVisibility(true, 1.0f, stop_gps);
                                        CustomUtility.isErrorDialog(mContext, getString(R.string.error), getString(R.string.err_stop_command_fail));

                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        baseRequest.hideLoader();
                        // yourCountDownTimer.cancel();
                        if (start.equalsIgnoreCase("1")) {
                            changeButtonVisibility(true, 1.0f, start_gps);

                        } else {

                            changeButtonVisibility(true, 1.0f, stop_gps);

                        }
                        progressDialog.dismiss();
                        Log.d("exce", "" + e);
                    }
                } else {
                    baseRequest.hideLoader();
                    if (start.equalsIgnoreCase("1")) {
                        changeButtonVisibility(true, 1.0f, start_gps);

                    } else {

                        changeButtonVisibility(true, 1.0f, stop_gps);
                    }
                    // yourCountDownTimer.cancel();
                    progressDialog.dismiss();

                    Message msg = new Message();
                    msg.obj = "Please check internet connection!!";
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }


    private void changeButtonVisibility(boolean state, float alphaRate, TextView txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
    }

    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, "" + mString, Toast.LENGTH_LONG).show();
        }
    };

    public void showMap() {

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        new Thread(new Runnable() {
            public void run() {
                try {

                    if (CustomUtility.isOnline(mContext)) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(mContext, MapsActivity.class);
                        intent.putExtra("MUserId", MUserId);
                        intent.putExtra("MDeviceId", vMDeviceId);
                        intent.putExtra("isLoginCheck", isLoginCheck);
                        intent.putExtra("isPumpCheck", isPumpCheck);

                        //   this.isLoginCheck = isLoginCheck;
                        //  this.isPumpCheck = isPumpCheck;
                        intent.putExtra("ClientId", "0");
                        startActivity(intent);


                    } else {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(mContext, "Error", "No Internet Connection");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void showMonitoring() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        new Thread(new Runnable() {
            public void run() {
                try {
                    //  if (CustomUtility.isOnline(  mContext))
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(mContext, RealMonitoringActivity.class);
                        intent.putExtra("MUserId", MUserId);
                        intent.putExtra("DeviceType", DeviceType);
                        intent.putExtra("DeviceNo", DeviceNo);
                        intent.putExtra("Mobile", Mobile);
                        intent.putExtra("CustomerName", CustomerName);
                        intent.putExtra("ModelType", mModelType);

                        startActivity(intent);
                    }
                 /*  else
                   {
                       progressDialog.dismiss();
                       CustomUtility.isErrorDialog(  mContext, "Error", "No Internet Connection");

                   }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }



}
