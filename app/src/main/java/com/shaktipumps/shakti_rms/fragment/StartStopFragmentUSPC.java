package com.shaktipumps.shakti_rms.fragment;

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
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.AllPopupUtil;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.GlobalClass.UtilMethod;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.DeviceSettingActivity;
import com.shaktipumps.shakti_rms.activity.DeviceSettingBTShimhaActivity;
import com.shaktipumps.shakti_rms.activity.MapsActivity;
import com.shaktipumps.shakti_rms.activity.PairedDeviceActivity;
import com.shaktipumps.shakti_rms.activity.RealMonitoringActivity;
import com.shaktipumps.shakti_rms.activity.RealMonitoringBTShimhaActivity;
import com.shaktipumps.shakti_rms.activity.SimhaTwoODATAExtractionActivity;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.bean.DaynamicButton.DynamicBTNModel;
import com.shaktipumps.shakti_rms.bean.DaynamicButton.DynamicBTNResponse;
import com.shaktipumps.shakti_rms.bean.StartStopBean.StartStopModelView;
import com.shaktipumps.shakti_rms.bean.StartStopBean.StartStopVkResponse;
import com.shaktipumps.shakti_rms.bean.UspcEnergy.UspcEnrgyResponse;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class StartStopFragmentUSPC extends Fragment {
    String modeBusCommand = null;//write
    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;
    private int pp = 0;
    TextView tvComonID;
    private ImageView product_image;
    private CircleImageView imgStartDeviceID;
    private PieChart pieChartID;
    private TextView start_gps, stop_gps, device_setting, real_monitoring, txtDataExtractionID;
    private String vMUserId,
            vDeviceType, vDeviceNo, vStartdate;
    private InputStream iStream = null;
    private UUID mMyUDID;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;
    private ProgressDialog progressDialog;
    private int mmCheckStart = 0;
    private int mmCheckStop = 0;
    private CountDownTimer yourCountDownTimer;

    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    private ImageView imgMoterStatusID;
    private TextView txtMotorStatusIID;

    String StatusMEssage = "";
    String StatusMEssageAlert = "";
    //private TextView txtCurrentfaultCountID;
    private TextView txtTotalfaultCountID;
    int start_comon_check = 0;
    private int check_self_click = 0;

    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    char mCRCFinalValue;

    SharedPreferences pref;

    CardView crdSettingViewID, crdRealViewID, crdDataExcViewID, crdStopViewID, crdStartViewID;

    LinearLayout rlvStartStopBottomViewID, rlvStartStopViewID;
    private Context mContext;

    private DatabaseHelperTeacher mDatabaseHelperTeacher;
    private List<DynamicBTNResponse> mDynamicBTNResponse;

    private LinearLayout lvlStartStopInnerMainViewID;

    private Button button;
    private Context context;
    private CardView cardview;
  //  private ViewGroup.LayoutParams layoutparams;
    private TextView textview;
    private RelativeLayout relativeLayout;


    private boolean mCheckFirst;
    private UspcEnrgyResponse mUspcEnrgyResponse;

    private List<StartStopVkResponse> mStartStopVkResponse;

    public StartStopFragmentUSPC() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public StartStopFragmentUSPC(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, TextView txtMotorStatusIID, UspcEnrgyResponse mUspcEnrgyResponse) {
        // Required empty public constructor
        this.MUserId = MUserId;
        this.DeviceNo = DeviceNo;
        this.mModelType = mModelType;
        this.DeviceType = DeviceType;
        this.CustomerName = CustomerName;
        this.Mobile = Mobile;
        this.total_fault = total_fault;
        this.current_fault = current_fault;
        this.isLoginCheck = isLoginCheck;
        this.isPumpCheck = isPumpCheck;
        this.mImageUrl = mImageUrl;
        this.MDeviceId = MDeviceId;
        this.txtMotorStatusIID = txtMotorStatusIID;
        this.mUspcEnrgyResponse = mUspcEnrgyResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_start_stop_uspc, container, false);
        mActivity = getActivity();
        mContext = getActivity();

        initView();
        return mView;
    }

    private void initView() {

        baseRequest = new BaseRequest(mActivity);
        mDynamicBTNResponse = new ArrayList<>();
        mStartStopVkResponse = new ArrayList<>();

        mDatabaseHelperTeacher = new DatabaseHelperTeacher(mActivity);

        product_image = (ImageView) mView.findViewById(R.id.product_image);
        imgStartDeviceID = (CircleImageView) mView.findViewById(R.id.imgStartDeviceID);
        //  pieChartID = (PieChart) mView.findViewById(R.id.pieChartID);
        // txtCurrentfaultCountID = (TextView) mView.findViewById(R.id.txtCurrentfaultCountID);
        txtTotalfaultCountID = (TextView) mView.findViewById(R.id.txtTotalfaultCountID);

        //   txtCurrentfaultCountID.setText("Current Fault " + current_fault);
        //txtTotalfaultCountID.setText("Total Fault "+total_fault);
        start_gps = (TextView) mView.findViewById(R.id.start_gps);
        stop_gps = (TextView) mView.findViewById(R.id.stop_gps);

        lvlStartStopInnerMainViewID = (LinearLayout) mView.findViewById(R.id.lvlStartStopInnerMainViewID);

        rlvStartStopBottomViewID = (LinearLayout) mView.findViewById(R.id.rlvStartStopBottomViewID);
        rlvStartStopViewID = (LinearLayout) mView.findViewById(R.id.rlvStartStopViewID);

        crdSettingViewID = (CardView) mView.findViewById(R.id.crdSettingViewID);
        crdRealViewID = (CardView) mView.findViewById(R.id.crdRealViewID);
        crdDataExcViewID = (CardView) mView.findViewById(R.id.crdDataExcViewID);
        crdStartViewID = (CardView) mView.findViewById(R.id.crdStartViewID);
        crdStopViewID = (CardView) mView.findViewById(R.id.crdStopViewID);

       // imgMoterStatusID = (ImageView) mView.findViewById(R.id.imgMoterStatusID);
       // txtMotorStatusIID = (TextView) mView.findViewById(R.id.txtMotorStatusIID);

        device_setting = (TextView) mView.findViewById(R.id.device_setting);
        real_monitoring = (TextView) mView.findViewById(R.id.real_monitoring);
        txtDataExtractionID = (TextView) mView.findViewById(R.id.txtDataExtractionID);

        if (SharedPreferencesUtil.getData(mActivity, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE) == null || SharedPreferencesUtil.getData(mActivity, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase(""))
        {
            SharedPreferencesUtil.setData(mActivity, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE, "en");
            SharedPreferencesUtil.setData(mActivity, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_SAVE, "English");

            com.shaktipumps.shakti_rms.webservice.Constant.setLocale(mActivity, "en");
        }
        else
        {
            String hhhhh = SharedPreferencesUtil.getData(mActivity, com.shaktipumps.shakti_rms.webservice.Constant.LANGUAGE_NAME_CODE);
            com.shaktipumps.shakti_rms.webservice.Constant.setLocale(mActivity, hhhhh);
        }
        //product_image

        if (NewSolarVFD.OEM_NORMAL_USER_CHECK > 0) {
            if (mModelType.equalsIgnoreCase("8") || mModelType.equalsIgnoreCase("2")) {
                //  changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                rlvStartStopBottomViewID.setWeightSum(2f);
                crdDataExcViewID.setVisibility(View.GONE);
            } else if (mModelType.equalsIgnoreCase("49")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.GONE);
                rlvStartStopBottomViewID.setWeightSum(2f);
                rlvStartStopViewID.setWeightSum(2f);
                crdStopViewID.setVisibility(View.GONE);
                crdStartViewID.setVisibility(View.GONE);
                //changeButtonVisibility(false, 0.5f, start_gps);
                //  changeButtonVisibility(false, 0.5f, stop_gps);

                // txtMotorStatusIID.setVisibility(View.GONE);
                // imgMoterStatusID.setVisibility(View.GONE);
            } else if (mModelType.equalsIgnoreCase("17")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.VISIBLE);
                rlvStartStopBottomViewID.setWeightSum(3f);

            } else if (mModelType.equalsIgnoreCase("6")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.VISIBLE);
                rlvStartStopBottomViewID.setWeightSum(3f);

            }else if (mModelType.equalsIgnoreCase("1")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.VISIBLE);
                rlvStartStopBottomViewID.setWeightSum(3f);

            }



        } else {
            //rlvStartStopBottomViewID.setWeightSum(2f);

            if (mModelType.equalsIgnoreCase("8") || mModelType.equalsIgnoreCase("2")) {
                //  changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                rlvStartStopBottomViewID.setWeightSum(1f);
                crdDataExcViewID.setVisibility(View.GONE);
                crdSettingViewID.setVisibility(View.GONE);
            } else if (mModelType.equalsIgnoreCase("49")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.GONE);
                crdSettingViewID.setVisibility(View.GONE);
                rlvStartStopBottomViewID.setWeightSum(1f);
                rlvStartStopViewID.setWeightSum(2f);
                crdStopViewID.setVisibility(View.GONE);
                crdStartViewID.setVisibility(View.GONE);
                //changeButtonVisibility(false, 0.5f, start_gps);
                //  changeButtonVisibility(false, 0.5f, stop_gps);

                // txtMotorStatusIID.setVisibility(View.GONE);
                // imgMoterStatusID.setVisibility(View.GONE);
            } else if (mModelType.equalsIgnoreCase("17")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.VISIBLE);
                crdSettingViewID.setVisibility(View.GONE);
                rlvStartStopBottomViewID.setWeightSum(2f);

            } else if (mModelType.equalsIgnoreCase("6")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.VISIBLE);
                crdSettingViewID.setVisibility(View.GONE);
                rlvStartStopBottomViewID.setWeightSum(2f);

            }else if (mModelType.equalsIgnoreCase("1")) {
                //changeButtonVisibility(false, 0.5f, txtDataExtractionID);
                crdDataExcViewID.setVisibility(View.VISIBLE);
                crdSettingViewID.setVisibility(View.GONE);
                rlvStartStopBottomViewID.setWeightSum(2f);

            }

        }

        try {
            if (mImageUrl.equalsIgnoreCase("")) {

            } else {
                Picasso.with(getActivity()).load(mImageUrl).placeholder(R.drawable.logo).into(product_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String  mOnMachineImage= mUspcEnrgyResponse.getUSPCAPPIDImage();


        try {
            if (mUspcEnrgyResponse.getUSPCAPPIDImage().isEmpty()) {

            } else {
                System.out.println("mOnMachineImage==>>"+mOnMachineImage);
                Picasso.with(getActivity()).load(mOnMachineImage).placeholder(R.drawable.logo).into(imgStartDeviceID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setAllEventClick();

        if(CustomUtility.isOnline(mContext)){
            callDynamicBTNAPI();
        }else{
            mDynamicBTNResponse = mDatabaseHelperTeacher.getDeviceSTART_STOPDATA(DeviceType);
            if(mDynamicBTNResponse.size() > 0)
            CreateCardViewProgrammatically();
        }
      //  callDynamicBTNAPI();

        //  setPieChart();
    }


    private void setAllEventClick() {

        txtDataExtractionID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                                /*Intent intent = new Intent(mContext, PairedDeviceActivity.class);
                                mContext.startActivity(intent);*/

                            if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("")) {
                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {

                                        Intent intentP = new Intent(getActivity(), SimhaTwoODATAExtractionActivity.class);
                                        intentP.putExtra("BtNameHead", Constant.BT_DEVICE_NAME);
                                        intentP.putExtra("DeviceType", DeviceType);
                                        intentP.putExtra("ModelType", mModelType);
                                        intentP.putExtra("BtMacAddressHead", Constant.BT_DEVICE_MAC_ADDRESS);
                                        startActivity(intentP);

                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Intent intentppp = new Intent(getActivity(), PairedDeviceActivity.class);
                                startActivity(intentppp);
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

                   /* if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo))
                        {
                            if (mModelType.equalsIgnoreCase("6")) {
                                Intent intentP = new Intent(getActivity(), BluetoothDataExtrctionVicheeActivity.class);
                                intentP.putExtra("BtNameHead", Constant.BT_DEVICE_NAME);
                                intentP.putExtra("DeviceType", DeviceType);
                                intentP.putExtra("ModelType", mModelType);
                                intentP.putExtra("BtMacAddressHead", Constant.BT_DEVICE_MAC_ADDRESS);
                                startActivity(intentP);
                            } else {
                                Intent intentP = new Intent(getActivity(), GetBTDATAListActivity.class);
                                intentP.putExtra("BtNameHead", Constant.BT_DEVICE_NAME);
                                intentP.putExtra("DeviceType", DeviceType);
                                intentP.putExtra("ModelType", mModelType);
                                intentP.putExtra("BtMacAddressHead", Constant.BT_DEVICE_MAC_ADDRESS);
                                startActivity(intentP);
                            }

                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intentppp = new Intent(getActivity(), PairedDeviceActivity.class);
                        startActivity(intentppp);
                    }*/
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        real_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                    showMonitoring();
                } else {

                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        if (AllPopupUtil.pairedDeviceListGloable(mActivity)) {

                            if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                Intent intent = new Intent(mActivity, PairedDeviceActivity.class);
                                mActivity.startActivity(intent);

                            } else {

                                /*Intent intent = new Intent(getContext(), RealMonitoringBTShimhaActivity.class);
                                intent.putExtra("MUserId", MUserId);
                                intent.putExtra("DeviceType", DeviceType);
                                intent.putExtra("DeviceNo", DeviceNo);
                                intent.putExtra("Mobile", Mobile);
                                intent.putExtra("CustomerName", CustomerName);
                                intent.putExtra("ModelType", mModelType);
                                getActivity().startActivity(intent);*/
                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                    if (mModelType.equalsIgnoreCase("1")) {
                                        Intent intent = new Intent(getContext(), RealMonitoringBTShimhaActivity.class);
                                        intent.putExtra("MUserId", MUserId);
                                        intent.putExtra("DeviceType", DeviceType);
                                        intent.putExtra("DeviceNo", DeviceNo);
                                        intent.putExtra("Mobile", Mobile);
                                        intent.putExtra("CustomerName", CustomerName);
                                        intent.putExtra("ModelType", mModelType);
                                        getActivity().startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getContext(), RealMonitoringBTShimhaActivity.class);
                                        intent.putExtra("MUserId", MUserId);
                                        intent.putExtra("DeviceType", DeviceType);
                                        intent.putExtra("DeviceNo", DeviceNo);
                                        intent.putExtra("Mobile", Mobile);
                                        intent.putExtra("CustomerName", CustomerName);
                                        intent.putExtra("ModelType", mModelType);
                                        getActivity().startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                }
                            }
                            ///////////////write the query
                            //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                        } else {
                            // AllPopupUtil.btPopupCreateShow(mActivity);
                            getActivity().startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                        }
                    } else {
                        //  AllPopupUtil.btPopupCreateShow(mActivity);
                        getActivity().startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                    /////////////////add bluetooth cod here
                }

            }
        });

       /* product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMap();
            }
        });*/


        device_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                                Intent intent = new Intent(getContext(), DeviceSettingBTShimhaActivity.class);
                                                intent.putExtra("MUserId", MUserId);
                                                intent.putExtra("DeviceType", DeviceType);
                                                intent.putExtra("DeviceNo", DeviceNo);
                                                getActivity().startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(getContext(), DeviceSettingBTShimhaActivity.class);
                                                intent.putExtra("MUserId", MUserId);
                                                intent.putExtra("DeviceType", DeviceType);
                                                intent.putExtra("DeviceNo", DeviceNo);
                                                getActivity().startActivity(intent);
                                            }
                                        /*    Intent intent = new Intent(mActivity, DeviceSettingBTShimhaActivity.class);
                                            intent.putExtra("MUserId", MUserId);
                                            intent.putExtra("DeviceType", DeviceType);
                                            intent.putExtra("DeviceNo", DeviceNo);
                                            mActivity.startActivity(intent);*/
                                        } else {
                                            Toast.makeText(getContext(), getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    ///////////////write the query

                                    //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                                } else {
                                    // AllPopupUtil.btPopupCreateShow(mActivity);
                                    // AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                    getActivity().startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                }
                            } else {
                                //  AllPopupUtil.btPopupCreateShow(mActivity);
                                //AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                getActivity().startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
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
        });

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


    public void CreateCardViewProgrammatically() {


        for (int i = 0; i < mDynamicBTNResponse.size(); i++) {


            cardview = new CardView(mContext);


            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(
                    UtilMethod.getDeviceHeightWidth(mContext, true) / 2-28, ViewGroup.LayoutParams.MATCH_PARENT);

            cardview.setLayoutParams(layoutparams);
            cardview.setRadius(20);

            //  cardview.setCardBackgroundColor(Color.MAGENTA);
            cardview.setCardBackgroundColor(Color.TRANSPARENT);
            cardview.setMaxCardElevation(30);

            layoutparams.setMargins((int) getResources().getDimension(R.dimen._5sdp), 0, (int) getResources().getDimension(R.dimen._3sdp), (int) getResources().getDimension(R.dimen._1sdp));
           // cardview.setId(i + 1);
            cardview.setTag(mDynamicBTNResponse.get(i).getSno());



            // cardview.setMaxCardElevation(6);

            RelativeLayout mRLVBoxLayout = new RelativeLayout(mActivity);
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
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen._6ssp));
            textview.setTextColor(Color.WHITE);
           // Typeface face= Typeface.createFromAsset(mContext.getAssets(),"font/open_sans_semibold.ttf.ttf");
            textview.setTypeface(null, Typeface.BOLD);
            // textview.setPadding(25,25,25,25);
            textview.setGravity(Gravity.CENTER);
           // textview.setFon
            textview.setId(i + 1);
            textview.setTextColor(Color.WHITE);
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            if(i == 0)
                textview.setText(R.string.st_Start);
            else
                textview.setText(R.string.st_Stop);


            mRLVBoxLayout.addView(textview);
            cardview.addView(mRLVBoxLayout);



            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int iii = v.getId();
                    pp = iii-1;
                    start_comon_check = pp;
                    if (pp == 0) {
                        check_self_click = 1;////start
                    } else {
                        check_self_click = 2;////stop
                        tvComonID = (TextView) v.findViewById(pp-1);
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
                            }else if (mLenth == 2) {
                                mMOBADDRESS = "00" + mMobADR;
                            }else if (mLenth == 3) {
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
                        StatusMEssage = "Do you want to start device ?";
                        StatusMEssageAlert = "Start Alert!";
                    } else {
                        StatusMEssage = "Do you want to stop device ?";
                        StatusMEssageAlert = "Stop Alert!";
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
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            baseRequest.showLoader();
                                            progressDialog = ProgressDialog.show(mActivity, "", "Please Wait..");
                                            //   DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
                                            //   DeviceType = customerSearchesList.get(mPosition).getDeviceType();
                                            //  userid = customerSearchesList.get(mPosition).getMUserId();
                                            //  start = "1";
                                            //   stop = "0";
                                            // changeButtonVisibility(false, 0.05f,  start_gps);
                                           // callURL(DeviceNo, start, stop, MUserId, DeviceType);
                                            if (android.os.Build.VERSION.SDK_INT > 28){
                                                NewSolarVFD.HOST_NAME= "http://solar10.shaktisolarrms.com/RMSApp1/";
                                                NewSolarVFD.HOST_NAME2 = "https://solar10.shaktisolarrms.com/RMSApp1/";
                                                NewSolarVFD.HOST_NAME3 = "https://solar10.shaktisolarrms.com/Home/";
                                                callURL(DeviceNo, start, stop, MUserId, DeviceType);


                                            }
                                            else
                                            {
                                                NewSolarVFD.HOST_NAME = "http://solar10.shaktisolarrms.com/RMSApp1/";
                                                NewSolarVFD.HOST_NAME2 = "https://solar10.shaktisolarrms.com/RMSApp1/";
                                                NewSolarVFD.HOST_NAME3 = "http://solar10.shaktisolarrms.com/Home/";
                                                callURL1(DeviceNo, start, stop, MUserId, DeviceType);

                                            }
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
                            Toast.makeText(mActivity, "Please check internet connection!!", Toast.LENGTH_SHORT).show();
                        }

                    }//////////////else
                    else {

                        new AlertDialog.Builder(mActivity)
                                .setTitle(StatusMEssageAlert)
                                .setMessage(StatusMEssage)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        baseRequest.showLoader();
                                        //  progressDialog = ProgressDialog.show(mActivity, "", "Please Wait..");
                                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                        if (mBluetoothAdapter.isEnabled()) {
                                            if (AllPopupUtil.pairedDeviceListGloable(mActivity)) {

                                                if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                                    //    new BluetoothCommunicationForMotorStart().execute(":TURNON#", ":TURNON#", "START");
                                                    if (Constant.BT_DEVICE_NAME.equalsIgnoreCase(DeviceNo)) {
                                                        changeButtonVisibility(false, 0.05f, start_gps);
                                                        //new BluetoothCommunicationForMotorStart().execute("0106000A0000803FAED7", ":TURNON#", "START");
                                                        new BluetoothCommunicationForMotorStart().execute(modeBusCommand, ":TURNON#", "START");
                                                    } else {
                                                        Toast.makeText(getContext(), getResources().getString(R.string.bt_not_permitted_msg), Toast.LENGTH_SHORT).show();
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
                                                getActivity().startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                            }
                                        } else {
                                            //  AllPopupUtil.btPopupCreateShow(mActivity);
                                            // AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                                            getActivity().startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                        }
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

                }
            });

            lvlStartStopInnerMainViewID.addView(cardview);

        }

    }

    public boolean permission_validation() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            check_Permission(getActivity());
            //  CustomUtility.isErrorDialog(mActivity,mActivity.getString(R.string.error),mActivity.getString(R.string.err_app_permission));
            Toast.makeText(getActivity(), R.string.err_app_permission, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

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
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (RetryCount != 0 && RetryCount > maxRetryCount) {
                        maxRetryCount = RetryCount;
                    }
                    mmCheckStart = 0;

                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
                mmCheckStart = 1;
                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
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
                new BluetoothCommunicationForMotorStart().execute(modeBusCommand, ":TURNON#", "START");
               // Toast.makeText(getActivity(), "BT Connection intrupted.", Toast.LENGTH_SHORT).show();
            } else {
               /* StatusMEssageAlert = "Status Alert!";
                if (start.equalsIgnoreCase("1") || start.equalsIgnoreCase("1.0")) {
                   // Toast.makeText(getActivity(), "Motor start succesfully.", Toast.LENGTH_SHORT).show();
                    StatusMEssage = "Motor start succesfully!";

                } else {
                    //Toast.makeText(getActivity(), "Motor start succesfully.", Toast.LENGTH_SHORT).show();
                    StatusMEssage = "Motor sti succesfully!";
                }*/

                CustomUtility.isSuccessDialog(mActivity, mActivity.getString(R.string.success),mDynamicBTNResponse.get(pp).getCmdMsg());
                mActivity.runOnUiThread(new Runnable(){
                    public void run() {
                        // UI code goes here
                        try {
                            String ssss = mDynamicBTNResponse.get(pp).getButtonText();
                            txtMotorStatusIID.setText(ssss);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                baseRequest.hideLoader();
            }
        }
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        try {
            if(NewSolarVFD.USPC_SELECTION_CHECK == 1)
            {
                if (NewSolarVFD.USPC_SELECTION_IMAGE.isEmpty()) {

                } else {
                    System.out.println("mOnMachineImage==>>"+NewSolarVFD.USPC_SELECTION_CHECK);
                    Picasso.with(getActivity()).load(NewSolarVFD.USPC_SELECTION_CHECK).placeholder(R.drawable.logo).into(imgStartDeviceID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();

        try {
            if(NewSolarVFD.USPC_SELECTION_CHECK == 1)
            {
                if (NewSolarVFD.USPC_SELECTION_IMAGE.isEmpty()) {

                } else {
                    System.out.println("mOnMachineImage==>>"+NewSolarVFD.USPC_SELECTION_CHECK);
                    Picasso.with(getActivity()).load(NewSolarVFD.USPC_SELECTION_IMAGE).placeholder(R.drawable.logo).into(imgStartDeviceID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btSocket = null;
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
            // baseRequest.showLoader();
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
                        sleep(1000);
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

                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(mActivity, "BT Connection intrupted.", Toast.LENGTH_SHORT).show();
                new BluetoothCommunicationForMotorStart().execute(modeBusCommand, ":TURNON#", "START");
            } else {
                Toast.makeText(mActivity, "Motor stop succesfully.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callURL(final String DeviceNo, final String start, final String stop, final String userid, final String DeviceType) {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    StartStopModelView mStartStopModelView = gson.fromJson(Json, StartStopModelView.class);

                    getStartStopDataResponse(mStartStopModelView);

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

            jsonObject.addProperty("address1", mDynamicBTNResponse.get(pp).getAddress());
            jsonObject.addProperty("did1", DeviceNo);
            jsonObject.addProperty("RW", "1");
            jsonObject.addProperty("data1", start);
            jsonObject.addProperty("OldData", stop);
            jsonObject.addProperty("UserId", userid);
            jsonObject.addProperty("DeviceType", DeviceType);
            jsonObject.addProperty("offset1", mDynamicBTNResponse.get(pp).getOffset());
            jsonObject.addProperty("IPAddress", CustomUtility.getDeviceId(getContext()));

            System.out.println("VikasVV1==>"+jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPostRealStartStop(1, jsonObject, NewSolarVFD.START_STOP_MOTOR1);/////

    }

    private void getStartStopDataResponse(StartStopModelView mStartStopModelView) {

        if(mStartStopModelView.getStatus())
        {

            if(mStartStopVkResponse.size() > 0)
                mStartStopVkResponse.clear();

            mStartStopVkResponse= mStartStopModelView.getResponse();


            //success message
            if (mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("2.0")) {

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

            }

            // disconnect message
            if (mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("D")) {
                baseRequest.hideLoader();
                CustomUtility.isErrorDialog(mActivity, mActivity.getString(R.string.error), mActivity.getString(R.string.err_device_disconnect));
                if (start.equalsIgnoreCase("1")) {
                    changeButtonVisibility(true, 1.0f, start_gps);

                } else {
                    changeButtonVisibility(true, 1.0f, stop_gps);
                }
            }

            // command failed message
            if (!mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("2.0") && !mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("D")) {
                baseRequest.hideLoader();
                if (start.equalsIgnoreCase("1")) {
                    changeButtonVisibility(true, 1.0f, start_gps);
                    CustomUtility.isErrorDialog(getContext(), getActivity().getString(R.string.error), getActivity().getString(R.string.err_start_command_fail));
                } else {
                    changeButtonVisibility(true, 1.0f, stop_gps);
                    CustomUtility.isErrorDialog(getContext(), getActivity().getString(R.string.error), getActivity().getString(R.string.err_stop_command_fail));

                }
            }



        }

    }

    public void callURL1(final String DeviceNo, final String start, final String stop, final String userid, final String DeviceType) {

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
                        //mDynamicBTNResponse.get(pp).

                       /* if (start.equalsIgnoreCase("1") || start.equalsIgnoreCase("1.0")) {
                            param.add(new BasicNameValuePair("address1", "501"));
                        } else {
                            param.add(new BasicNameValuePair("address1", "501"));
                        }*/

                        progressDialog.dismiss();
                        //param.add(new BasicNameValuePair("address1","10" ));
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "1"));
                        param.add(new BasicNameValuePair("data1", start));               // start = 1
                        param.add(new BasicNameValuePair("OldData", stop));
                        param.add(new BasicNameValuePair("UserId", userid));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                    //    param.add(new BasicNameValuePair("offset1", "1"));
                        param.add(new BasicNameValuePair("offset1", mDynamicBTNResponse.get(pp).getOffset()));
                        //  param.addnew BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(getContext()))); // IMEI no of mobile
                      //  obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.HOST_NAME3 + "DeviceSettingParam", param);
                        //obj = CustomHttpClient.executeHttpPost1("http://111.118.249.180:1112/Home/DeviceSettingParam", param);
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
                                    CustomUtility.isSuccessDialog(mActivity, mActivity.getString(R.string.success),mDynamicBTNResponse.get(pp).getCmdMsg());
                                    mActivity.runOnUiThread(new Runnable(){
                                        public void run() {
                                            // UI code goes here
                                            String ssss = mDynamicBTNResponse.get(pp).getButtonText();
                                            txtMotorStatusIID.setText(ssss);
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
                                 //   if (start.equalsIgnoreCase("1"))
                                    if (start_comon_check == 0)
                                    {
                                        changeButtonVisibility(true, 1.0f, start_gps);

                                    } else {
                                        changeButtonVisibility(true, 1.0f, stop_gps);
                                    }

                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest.hideLoader();
                                  //  if (start.equalsIgnoreCase("1"))
                                    if (start_comon_check == 0)
                                    {
                                        changeButtonVisibility(true, 1.0f, start_gps);
                                        CustomUtility.isErrorDialog(getContext(), getActivity().getString(R.string.error), getActivity().getString(R.string.err_start_command_fail));

                                    } else {

                                        changeButtonVisibility(true, 1.0f, stop_gps);
                                        CustomUtility.isErrorDialog(getContext(), getActivity().getString(R.string.error), getActivity().getString(R.string.err_stop_command_fail));

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

    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(getContext(), "" + mString, Toast.LENGTH_LONG).show();
        }
    };

    public void showMap() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        new Thread(new Runnable() {
            public void run() {
                try {

                    if (CustomUtility.isOnline(getContext())) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(getContext(), MapsActivity.class);
                        intent.putExtra("MUserId", MUserId);
                        intent.putExtra("MDeviceId", MDeviceId);
                        intent.putExtra("isLoginCheck", isLoginCheck);
                        intent.putExtra("isPumpCheck", isPumpCheck);

                        //   this.isLoginCheck = isLoginCheck;
                        //  this.isPumpCheck = isPumpCheck;
                        intent.putExtra("ClientId", "0");
                        getActivity().startActivity(intent);


                    } else {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(getContext(), "Error", "No Internet Connection");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void showMonitoring() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        new Thread(new Runnable() {
            public void run() {
                try {
                    //  if (CustomUtility.isOnline(  mActivity))
                    {
                        progressDialog.dismiss();

                        Intent intent = new Intent(getContext(), RealMonitoringActivity.class);
                        intent.putExtra("MUserId", MUserId);
                        intent.putExtra("DeviceType", DeviceType);
                        intent.putExtra("DeviceNo", DeviceNo);
                        intent.putExtra("Mobile", Mobile);
                        intent.putExtra("CustomerName", CustomerName);
                        intent.putExtra("ModelType", mModelType);

                        getActivity().startActivity(intent);
                    }
                 /*  else
                   {
                       progressDialog.dismiss();
                       CustomUtility.isErrorDialog(  mActivity, "Error", "No Internet Connection");

                   }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

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

            if(mDynamicBTNResponse.size() > 0)
            {
                mDatabaseHelperTeacher.deleteStart_StopDATA(DeviceType);

                for (int i = 0; i < mDynamicBTNResponse.size(); i++) {

                    mDatabaseHelperTeacher.insertDeviceSTART_STOPData( MUserId,  MDeviceId,  DeviceNo,  DeviceType, mDynamicBTNResponse, i, mCheckFirst);

                }
            }

            CreateCardViewProgrammatically();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();
            // mDatabaseHelperTeacher.insertDeviceSHIMAData(MUserId, MDeviceId, DeviceNo, DeviceType,mShimaResponse,mCheckFirst);

        }
    }

    private void changeButtonVisibility(boolean state, float alphaRate, TextView txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
    }

}
