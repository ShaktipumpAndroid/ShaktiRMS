package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.BuildConfig;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.AoneSSBean.AOneSSModel;
import com.shaktipumps.shakti_rms.bean.AoneSSBean.AOneSSResponse;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.ShimaBean.ShimaModel;
import com.shaktipumps.shakti_rms.bean.ShimaBean.ShimaResponse;
import com.shaktipumps.shakti_rms.fragment.DashboardVFDFragment;
import com.shaktipumps.shakti_rms.fragment.StartStopFragment;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphEnergy;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphFlow;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphResponse;
import com.shaktipumps.shakti_rms.model.TotalEnergyConsuptinModel.TotalEndAndConsuptionsResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.CustomViewPager;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityDeviceDetailsInfoAOneSS extends AppCompatActivity {


    private  int PagePositionIndex = 1;
    private Context mContext;
    private Activity mActivity;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private AOneSSResponse mAOneSSResponse;

    private TextView txtBluetoothId, txtOtherId;
    private ViewFlipper flvViewFlipperID;
    private BaseRequest baseRequest;

    private ShimaResponse mShimaResponse;
    private TotalEndAndConsuptionsResponse mTotalEndAndConsuptionsResponse;
    private EnergyGraphResponse mEnergyGraphResponse;

    private List<EnergyGraphEnergy> mEnergyGraphEnergy;
    private List<EnergyGraphFlow> mEnergyGraphFlow;

    private int mPosition = 0;

    private  String mImageUrl = null;

    private CircleImageView imgDeviceImageID;
    private TextView txtHeaderID;

    private String vMUserId,
            vDeviceType, vDeviceNo,vStartdate, isLoginCheck, isPumpCheck, vMDeviceId;

    // private TextView txtTotalEnergyValueID, txtConsumptionValueID;
    private UUID mMyUDID;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;
    private ProgressDialog progressDialog;
    private CountDownTimer yourCountDownTimer;

    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", MDeviceId = "null", CustomerName = "null", mModelType = "null",Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";
    int clientid = 0 ;

    private int total_fault , current_fault ;

    int mmCheckStart = 0;
    int mmCheckStop = 0;
    private ImageView start_gps, stop_gps,imgData_ExtractionID, product_image, real_monitoring, imgBTAndInternetToggleID;

    private List<Customer_GPS_Search> customerSearchesList = null;
    private boolean mCheckBT = false;

    private RadioGroup radioGroupGraphID;
    private RadioButton radioDay;
    private RadioButton radioMonth;
    private RadioButton radioYear;

    private RelativeLayout rlvBackViewID;
    private ImageView imgHeaderID;

    private PieChart pieChartID;
    private LineChart chartEnergyID;
    private LineChart chartFlowID;

    private int  ioio = 0;
    String mValueData = "date";

    // private TextView txtDataID,txtGraphID;
    private ImageView imgVFDId,txtGraphID;
    private String TAG;

    private ViewPagerAdapter adapter;
    // private ViewPager viewPager;
    private CustomViewPager viewPager;
    private int mSwipePosition = 1;

   // private ImageView imgInternetToggleID, imgBTToggleID;
    private ImageView imgINT_BT_HTPTID;

    //  private TextView txtTotalEnergyHeadingID, txtConsumptionHeadingID;

   // private Switch switch1;
    private int mTotalTimeInt = 0;

    private TextView txtSettingID, txtVfdID;
    private String versionName = "0.0";
    private TextView txtAppUserNameID, txtAppVersionID;
    private RelativeLayout rlvSlideMenuViewID;
    private RelativeLayout rlvMenuOptionRemoveDeviceViewID;

    private RelativeLayout rlvMenuOptionHomeViewID, rlvMenuOptionAddDeviceViewID, rlvMenuOptionChangePassViewID, rlvMenuOptionFaultReportViewID, rlvMenuOptionDataReportViewID,rlvMenuOptionLogoutViewID;

    private SwipeRefreshLayout swipeRefresh;
    private  RelativeLayout rlvTabControlID,  rlvTabMotorID;

    private CircleButton imgMoterStatusID,txtDeleteButtonID;
    private TextView txtMotorStatusIID;

    private DatabaseHelperTeacher mDatabaseHelperTeacher;
    private boolean mCheckFirst;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private DatabaseHelperTeacher databaseHelperTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details_info_a_one_ss);
        mContext = this;
        mActivity = this;
        mEnergyGraphEnergy = new ArrayList<>();
        mEnergyGraphFlow = new ArrayList<>();
        databaseHelperTeacher = new DatabaseHelperTeacher(mContext);/////////////////AUTHModelData base
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewPager.setCurrentItem(mSwipePosition);

        try {
            if(mCheckBT)
            {
                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                    if(ioio== 0)
                    {
                        ioio = 1;
                        Intent intent = new Intent(mContext, PairedDeviceActivity.class);
                        mContext.startActivity(intent);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        baseRequest = new BaseRequest(this);
        Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;
        Constant.BT_DEVICE_NAME = "";
        Constant.BT_DEVICE_MAC_ADDRESS = "";
        mPosition = getIntent().getIntExtra("mPos",0);
        customerSearchesList = (List<Customer_GPS_Search>) getIntent().getSerializableExtra("mDeviceDetail");
        vDeviceNo = customerSearchesList.get(mPosition).getDeviceNo();

        mDatabaseHelperTeacher = new DatabaseHelperTeacher(this);
        versionName = BuildConfig.VERSION_NAME;
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        //MUserId = customerSearchesList.get(mPosition).getMUserId();
        try {
            MUserId = pref.getString("key_muserid", "invalid_muserid");
            clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (  clientid == 9999)
        {
            clientid = 0 ;
        }

        DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        DeviceType = customerSearchesList.get(mPosition).getDeviceType();
        CustomerName = customerSearchesList.get(mPosition).getCustomer_name();
        Mobile = customerSearchesList.get(mPosition).getMobile();
        isLoginCheck = customerSearchesList.get(mPosition).getIsLogin();
        mModelType = customerSearchesList.get(mPosition).getModelType();
        isPumpCheck = customerSearchesList.get(mPosition).getPumpStatus();
        mImageUrl = customerSearchesList.get(mPosition).getDeviceImage();

        imgINT_BT_HTPTID = (ImageView) findViewById(R.id.imgINT_BT_HTPTID);

       /* imgInternetToggleID = (ImageView) findViewById(R.id.imgInternetToggleID);
        imgBTToggleID = (ImageView) findViewById(R.id.imgBTToggleID);
        switch1 = (Switch) findViewById(R.id.switch1);*/

        txtDeleteButtonID = (CircleButton) findViewById(R.id.txtDeleteButtonID);
        imgMoterStatusID = (CircleButton) findViewById(R.id.imgMoterStatusID);
        txtMotorStatusIID = (TextView) findViewById(R.id.txtMotorStatusIID);


        if (  clientid == 0)
        {
            // single_user_displayView(0);
            SideMenuInitView(0);
            txtDeleteButtonID.setVisibility(View.VISIBLE);
        }
        else
        {
            //org_user_displayView(0);
            SideMenuInitView(1);
            txtDeleteButtonID.setVisibility(View.GONE);
        }

        try {
            if (isLoginCheck.equalsIgnoreCase("true")) {
                if (isPumpCheck.equalsIgnoreCase("true")) {
                    //     imgMoterStatusID.setImageResource(R.mipmap.green_ball);//////////////
                    txtMotorStatusIID.setText("Start");
                    //  txtMotorStatusIID.setTextColor(getResources().getColor(R.color.green));
                } else {
                    txtMotorStatusIID.setText("Stop");
                    //  txtMotorStatusIID.setTextColor(getResources().getColor(R.color.red));
                    // imgMoterStatusID.setImageResource(R.mipmap.red_ball); //////////////
                }
            } else {
                txtMotorStatusIID.setText("Disconnected");
                //  txtMotorStatusIID.setTextColor(getResources().getColor(R.color.gray));
                // imgMoterStatusID.setImageResource(R.mipmap.grey_ball);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(0);


        imgDeviceImageID = (CircleImageView) findViewById(R.id.imgDeviceImageID);
        txtHeaderID = (TextView) findViewById(R.id.txtHeaderID);




        rlvTabMotorID = (RelativeLayout) findViewById(R.id.rlvTabMotorID);
        rlvTabControlID = (RelativeLayout) findViewById(R.id.rlvTabControlID);


        txtSettingID = (TextView) findViewById(R.id.txtSettingID);
        txtVfdID = (TextView) findViewById(R.id.txtVfdID);



        //txtTotalEnergyHeadingID = (TextView) findViewById(R.id.txtTotalEnergyHeadingID);
        // txtConsumptionHeadingID = (TextView) findViewById(R.id.txtConsumptionHeadingID);

        imgVFDId = (ImageView) findViewById(R.id.imgVFDId);

        txtGraphID = (ImageView) findViewById(R.id.txtGraphID);







        rlvTabMotorID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgVFDId.setImageResource(R.drawable.iv_vfd_motor_blue);



                //  txtDataID.setTextColor(getResources().getColor(R.color.white));
                txtGraphID.setImageResource(R.drawable.settings_unselected);


                txtVfdID.setTextColor(getResources().getColor(R.color.blue_fb));


                txtSettingID.setTextColor(getResources().getColor(R.color.dark_bg_grey));
                //  txtGraphID.setTextColor(getResources().getColor(R.color.black));

                PagePositionIndex =0;
                viewPager.setCurrentItem(0);
            }
        });
        rlvTabControlID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGraphID.setImageResource(R.drawable.settings_selected);

                //  txtGraphID.setTextColor(getResources().getColor(R.color.white));
                imgVFDId.setImageResource(R.drawable.iv_vfd_motor_gray);



                txtVfdID.setTextColor(getResources().getColor(R.color.dark_bg_grey));

                txtSettingID.setTextColor(getResources().getColor(R.color.blue_fb));
                //   txtDataID.setTextColor(getResources().getColor(R.color.black));

                PagePositionIndex =1;
                viewPager.setCurrentItem(1);
            }
        });

        //  txtTotalEnergyValueID = (TextView) findViewById(R.id.txtTotalEnergyValueID);
        //  txtConsumptionValueID = (TextView) findViewById(R.id.txtConsumptionValueID);

        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);
        txtHeaderID.setText(customerSearchesList.get(mPosition).getDeviceNo());

        if(Constant.CHECK_BACK_MENU_VIEW_ICON == 1)
        {
            imgHeaderID.setImageResource(R.drawable.icn_menu);
        }
        else
        {
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
        mModelType = customerSearchesList.get(mPosition).getModelType();
        vMDeviceId = customerSearchesList.get(mPosition).getMDeviceId();





        txtDeleteButtonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backConfirmPopupID(mContext, customerSearchesList, mPosition);

                //    delete_from_server();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        param.clear();

        clickEventOnBTN();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                PagePositionIndex = viewPager.getCurrentItem();
                //viewPager.getVisibility();

                swipeRefresh.setRefreshing(true);
                //  callTotalEnergyAndConjuptionAPI();
                if(CustomUtility.isOnline(mContext)){
                    callTotalEnergyAndConjuptionAPI();
                }else{
                    swipeRefresh.setRefreshing(false);
                    mAOneSSResponse = mDatabaseHelperTeacher.getDeviceA1ssDATA(vDeviceType);
                }
            }
        });

        if(CustomUtility.isOnline(mContext)){
            callTotalEnergyAndConjuptionAPI();
        }else{

            //swipeRefresh.setRefreshing(false);
            mAOneSSResponse = mDatabaseHelperTeacher.getDeviceA1ssDATA(vDeviceType);

            if (viewPager != null) {

                if (adapter != null)
                    adapter = null;
                //   adapter = new ViewPagerAdapter(getChildFragmentManager());
                adapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);

                PageListener pageListener = new PageListener();
                viewPager.setOnPageChangeListener(pageListener);
                // viewPager.setCurrentItem(0);

                viewPager.setCurrentItem(PagePositionIndex);
            }
        }
        // callTotalEnergyAndConjuptionAPI();



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


    private void SideMenuInitView(int OEM_Check)
    {
        rlvMenuOptionRemoveDeviceViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionRemoveDeviceViewID);
        rlvSlideMenuViewID = (RelativeLayout) findViewById(R.id.rlvSlideMenuViewID);
        rlvMenuOptionHomeViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionHomeViewID);
        rlvMenuOptionAddDeviceViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionAddDeviceViewID);
        rlvMenuOptionChangePassViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionChangePassViewID);
       /* rlvMenuOptionFaultReportViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionFaultReportViewID);
        rlvMenuOptionDataReportViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionDataReportViewID);*/
        rlvMenuOptionLogoutViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionLogoutViewID);

        txtAppUserNameID = (TextView) findViewById(R.id.txtAppUserNameID);
        txtAppVersionID = (TextView) findViewById(R.id.txtAppVersionID);

        String user_name = "Welcome,  " + pref.getString("key_login_username", " ");
        txtAppUserNameID.setText(user_name);

        String version = "App Version: " + versionName;

      //  txtAppVersionID.setText(version);
        txtAppVersionID.setText("App Version: "+NewSolarVFD.versionNameForAll);

        if(OEM_Check == 0)
        {
            rlvMenuOptionAddDeviceViewID.setVisibility(View.VISIBLE);
        }
        else
        {
            rlvMenuOptionAddDeviceViewID.setVisibility(View.GONE);
        }


        if(Constant.CHECK_COUNT_OF_DELETE == 1)
        {
            rlvMenuOptionRemoveDeviceViewID.setVisibility(View.VISIBLE);
        }
        else
        {
            rlvMenuOptionRemoveDeviceViewID.setVisibility(View.GONE);
        }

        rlvMenuOptionRemoveDeviceViewID.setVisibility(View.GONE);
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
                mContext.startActivity(mIntent);
*/
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                //callTotalEnergyAndConjuptionAPI();
                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
                finish();
            }
        });

        rlvMenuOptionAddDeviceViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkAndRequestPermissions())
                {
                    Intent intent = new Intent(mContext, AddDevice.class);
                    startActivity(intent);
                }
                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
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

      /*  rlvMenuOptionFaultReportViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FaultReportActivity.class);
                intent.putExtra("from_date","" );
                intent.putExtra("to_date",  "");
                startActivity(intent);
                //  overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionDataReportViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(mContext, DataReportActivity.class);
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

    private void clickEventOnBTN() {

        imgINT_BT_HTPTID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewSolarVFD.popupDialodCustom(mContext, mModelType);
            }
        });

   /*     switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //NewSolarVFD.popupDialodCustom(mContext);

                try {
                    if(isChecked)
                    {
                        imgBTToggleID.setImageResource(R.drawable.iv_bluetooth_selected);
                        imgInternetToggleID.setImageResource(R.drawable.iv_connection_unselected);

                        Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 1;
                        if(mModelType.equalsIgnoreCase("8"))
                        {
                            // Intent ii = new Intent(mContext, Options_Activity.class);
                            //  mContext.startActivity(ii);
                            SharedPreferences sharedPref = mContext.getSharedPreferences("Pref", 0);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("user_comm_option", "B");
                            editor.apply();
                            Intent i = new Intent(mContext, Bluetooth_DeviceLists_Activity.class);
                            startActivity(i);

                        }
                        else
                        {
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
                    }
                    else
                    {
                        imgBTToggleID.setImageResource(R.drawable.iv_bluetooth_unselected);
                        imgInternetToggleID.setImageResource(R.drawable.iv_connection_selected);

                        Constant.BT_DEVICE_NAME = "";
                        Constant.BT_DEVICE_MAC_ADDRESS = "";
                        Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;
                        // switch1.setChecked(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });*/
        //imgInternetToggleID, imgBTToggleID;
        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                if(Constant.CHECK_BACK_MENU_VIEW_ICON == 1)
                {
                    rlvSlideMenuViewID.setVisibility(View.VISIBLE);
                    overridePendingTransition(R.anim.lefr_to_right, R.anim.right_to_left);
                }
                else
                {
                    finish();
                }

              /*  Intent mIntent = new Intent(mContext, SideMenuGlobal.class);
                startActivity(mIntent);*/

            }
        });


    }

    private void callTotalEnergyAndConjuptionAPI() {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    swipeRefresh.setRefreshing(false);
                    AOneSSModel mAOneSSModel = gson.fromJson(Json, AOneSSModel.class);
                    getAOneSSResponse(mAOneSSModel);
                } catch (Exception e) {
                    swipeRefresh.setRefreshing(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("MUserId", vMUserId);
            jsonObject.addProperty("DeviceType", vDeviceType);
            jsonObject.addProperty("DeviceNo", vDeviceNo);
            //   jsonObject.addProperty("DeviceNo", "");

        } catch (Exception e) {
            swipeRefresh.setRefreshing(false);
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_TOTAL_A1SS_ENERGY);/////
    }

    @Override
    public void onDestroy() {
        try {
            progressDialog.dismiss();

            if(progressDialog !=null)
                progressDialog = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void getAOneSSResponse(AOneSSModel mAOneSSModel) {
        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mAOneSSModel.getStatus())
        {
            swipeRefresh.setRefreshing(false);
            if(mAOneSSResponse != null )
                mAOneSSResponse = null;

            // mAOneSSModel = mWelcomeModelView.getResponse();
            mAOneSSResponse = mAOneSSModel.getResponse();

            //total_fault = mAOneSSResponse.getTotalFault();
            current_fault = mAOneSSResponse.getFault();

            if(mAOneSSResponse != null ){
                mDatabaseHelperTeacher.insertDeviceA1ssData(vMUserId, vMDeviceId, vDeviceNo, vDeviceType,mAOneSSResponse,mCheckFirst);
            }

            if (viewPager != null) {

                if (adapter != null)
                    adapter = null;
                //   adapter = new ViewPagerAdapter(getChildFragmentManager());
                adapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);

                PageListener pageListener = new PageListener();
                viewPager.setOnPageChangeListener(pageListener);
                // PagePositionIndex = viewPager.getCurrentItem();
                viewPager.setCurrentItem(PagePositionIndex);

            }
        }
    }

    private void callTotalEnergyAndConjuptionAPI1() {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    //TotalEndAndConsuptionsModelView mTotalEndAndConsuptionsModelView = gson.fromJson(Json, TotalEndAndConsuptionsModelView.class);
                    //  getTotalEndAndConsuptionsResponse(mTotalEndAndConsuptionsModelView);
                    swipeRefresh.setRefreshing(false);
                    ShimaModel mShimaModel = gson.fromJson(Json, ShimaModel.class);
                    getTotalEndAndConsuptionsResponse(mShimaModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("MUserId", vMUserId);
            jsonObject.addProperty("DeviceType", vDeviceType);///Model type value
            //jsonObject.addProperty("DeviceType", vDeviceType);
            jsonObject.addProperty("DeviceNo", vDeviceNo);
            //   jsonObject.addProperty("DeviceNo", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_TOTAL_SHIMA_ENERGY);/////
    }

    private void getTotalEndAndConsuptionsResponse(ShimaModel mShimaModel) {
        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mShimaModel.getStatus())
        {
            if(mShimaResponse != null )
                mShimaResponse = null;

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mShimaResponse = mShimaModel.getResponse();

            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();
            current_fault = mShimaResponse.getTotalFault();

            if (isLoginCheck.equalsIgnoreCase("true")) {
                if (isPumpCheck.equalsIgnoreCase("true")) {
                    //     imgMoterStatusID.setImageResource(R.mipmap.green_ball);//////////////
                    txtMotorStatusIID.setText("Start");
                    //  txtMotorStatusIID.setTextColor(getResources().getColor(R.color.green));
                } else {
                    txtMotorStatusIID.setText("Stop");
                    //  txtMotorStatusIID.setTextColor(getResources().getColor(R.color.red));
                    // imgMoterStatusID.setImageResource(R.mipmap.red_ball); //////////////
                }
            } else {
                txtMotorStatusIID.setText("Disconnected");
                //  txtMotorStatusIID.setTextColor(getResources().getColor(R.color.gray));
                // imgMoterStatusID.setImageResource(R.mipmap.grey_ball);
            }
            mDatabaseHelperTeacher.insertDeviceSHIMAData(vMUserId, vMDeviceId, vDeviceNo, vDeviceType,mShimaResponse,mCheckFirst);

            if (viewPager != null) {

                if (adapter != null)
                    adapter = null;
                //   adapter = new ViewPagerAdapter(getChildFragmentManager());
                adapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);

                PageListener pageListener = new PageListener();
                viewPager.setOnPageChangeListener(pageListener);

                //if(PagePositionIndex = )
                viewPager.setCurrentItem(PagePositionIndex);

            }

        }
    }


    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, ""+mString, Toast.LENGTH_LONG).show();
        }
    };

    /////////////////////////////////////////////////////////////////

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private int COUNT = 2;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {

                case 0:
                    fragment = callFragVFD(fragment);
                    //fragment = callFrag(fragment);
                    break;
                case 1:
                    fragment = callFrag(fragment);
                    //fragment = callFrag(fragment);
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + (position + 1);
        }
    }


    private Fragment callFragVFD(Fragment fragment1) {
        try {

            txtGraphID.setImageResource(R.drawable.settings_unselected);
            imgVFDId.setImageResource(R.drawable.iv_vfd_motor_blue);

            txtVfdID.setTextColor(getResources().getColor(R.color.blue_fb));

            txtSettingID.setTextColor(getResources().getColor(R.color.dark_bg_grey));

            mSwipePosition = 0;
            //callOrderListAPI(mPageNumber = 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragment1 = new DashboardVFDFragment(vMUserId, vMDeviceId, vDeviceNo, vDeviceType, CustomerName, Mobile, total_fault, current_fault, isLoginCheck, isPumpCheck, mImageUrl, mModelType, mAOneSSResponse);
        return fragment1;
    }


    private Fragment callFrag(Fragment fragment1) {
        try {

            txtGraphID.setImageResource(R.drawable.settings_selected);
            imgVFDId.setImageResource(R.drawable.iv_vfd_motor_gray);




            txtVfdID.setTextColor(getResources().getColor(R.color.dark_bg_grey));

            txtSettingID.setTextColor(getResources().getColor(R.color.blue_fb));

            mSwipePosition = 1;
            //callOrderListAPI(mPageNumber = 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragment1 = new StartStopFragment(vMUserId, vMDeviceId, vDeviceNo, vDeviceType, CustomerName, Mobile, total_fault, current_fault, isLoginCheck, isPumpCheck, mImageUrl, mModelType, txtMotorStatusIID);
        return fragment1;
    }




    private class PageListener extends CustomViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);

            if (position == 0){
                try {

                    txtGraphID.setImageResource(R.drawable.settings_unselected);
                    //  txtGraphID.setTextColor(getResources().getColor(R.color.white));
                    imgVFDId.setImageResource(R.drawable.iv_vfd_motor_blue);
                    txtVfdID.setTextColor(getResources().getColor(R.color.blue_fb));
                    txtSettingID.setTextColor(getResources().getColor(R.color.dark_bg_grey));

                    mSwipePosition = 0;

                    //callOrderListAPI(mPageNumber = 1);
                    viewPager.setCurrentItem(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {

                    txtGraphID.setImageResource(R.drawable.settings_selected);
                    //  txtGraphID.setTextColor(getResources().getColor(R.color.white));
                    imgVFDId.setImageResource(R.drawable.iv_vfd_motor_gray);

                    txtVfdID.setTextColor(getResources().getColor(R.color.dark_bg_grey));

                    txtSettingID.setTextColor(getResources().getColor(R.color.blue_fb));

                    mSwipePosition = 1;
                    //callOrderListAPI(mPageNumber = 1);
                    viewPager.setCurrentItem(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //currentPage = position;
        }
    }


    private void changeButtonVisibility(boolean state, float alphaRate, TextView txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
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

                            databaseHelperTeacher.deleteDeviceROWData(MDeviceId, MUserId);
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


    private boolean checkAndRequestPermissions() {

        int LOCATION = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int CAMERAV = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        if (CAMERAV != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            // if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
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

}
