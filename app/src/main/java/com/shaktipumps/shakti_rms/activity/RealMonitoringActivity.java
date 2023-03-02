package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.multidex.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.ProductStausBean.ProductStatusModel;
import com.shaktipumps.shakti_rms.bean.ProductStausBean.ProductStatusResponse;
import com.shaktipumps.shakti_rms.bean.RealMonitorBean.RealMonitorModelView;
import com.shaktipumps.shakti_rms.bean.RealMonitorBean.RealMonitorParameterModel;
import com.shaktipumps.shakti_rms.bean.RealMonitorBean.RealMonitorParameterResponse;
import com.shaktipumps.shakti_rms.bean.RealMonitorBean.RealMonitorvkResponse;
import com.shaktipumps.shakti_rms.bean.RealMonitoring;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.other.Utils;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.searchlist.RealMonitoringListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.GlobalMethod;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class RealMonitoringActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context;
    SharedPreferences pref;
    RealMonitoring realMonitoring;
    RealMonitoring realTime;
    RealMonitoringListViewAdapter adapter;
    ArrayList<RealMonitoring> arraylist;
    Timer timer;
    Boolean back_flag = false;
    ListView list;

    CustomUtility customutility;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;

    private UUID mMyUDID;
    boolean msg_flag;

    int loading = 0;
    String MUserId = "null", DeviceType = "null", DeviceNo = "null", Mobile = "null", CustomerName = "null", mModelType = "null", timezone = "null",
            RMSingalStr = "null", RMStatusOfProduct = "null", RMLatitude = "null", RMDate = " ";

    TextView real_monitor_icon, network_icon, real_monitor_name, frame_date_time, tv_device_number,
            tv_customer_name, tv_mobile_number;
    Drawable network_signal_image, real_monitor_image;
    private DatabaseHelperTeacher databaseHelperTeacher;
    private BaseRequest baseRequest;
    private LinearLayout lvlMainRealMonViewID;
    private DateFormat df;
    private String dateString;
    private ProductStatusResponse mProductStatusResponse;
    private Context mContext;
    LinearLayout lvlBackIconViewID;
    private List<ProductStatusResponse> mProductStatusResponseList;
    private boolean mCheckFirstDB;

    private ImageView imgShareIconID;

    private List<RealMonitorvkResponse> mRealMonitorvkResponse;
    private List<RealMonitorParameterResponse> mRealMonitorParameterResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_monitoring);

        mContext = this;
        timer = new Timer();
      /*  mToolbar = (Toolbar) findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_real_monitoring);*/
        lvlBackIconViewID = (LinearLayout) findViewById(R.id.lvlBackIconViewID);
        mProductStatusResponseList = new ArrayList<>();
        mRealMonitorvkResponse = new ArrayList<>();
        mRealMonitorParameterResponse = new ArrayList<>();

        databaseHelperTeacher = new DatabaseHelperTeacher(this);
        baseRequest = new BaseRequest(context);
        df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        dateString = df.format(Calendar.getInstance().getTime());
        real_monitor_icon = (TextView) findViewById(R.id.real_monitor_icon);
        network_icon = (TextView) findViewById(R.id.network_icon);
        real_monitor_name = (TextView) findViewById(R.id.real_monitor_name);
        frame_date_time = (TextView) findViewById(R.id.frame_date_time);
        tv_device_number = (TextView) findViewById(R.id.device_number);
        tv_customer_name = (TextView) findViewById(R.id.customer_name);
        imgShareIconID = (ImageView) findViewById(R.id.imgShareIconID);
        // tv_mobile_number= (TextView) findViewById(R.id.mobile_no);
        lvlMainRealMonViewID = (LinearLayout) findViewById(R.id.lvlMainRealMonViewID);
        lvlMainRealMonViewID.setVisibility(View.GONE);

        context = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        list = (ListView) findViewById(R.id.listview);

        arraylist = new ArrayList<RealMonitoring>();

        Bundle bundle = getIntent().getExtras();
        MUserId = bundle.getString("MUserId");
        DeviceType = bundle.getString("DeviceType");
        DeviceNo = bundle.getString("DeviceNo");
        CustomerName = bundle.getString("CustomerName");
        Mobile = bundle.getString("Mobile");
        mModelType = bundle.getString("ModelType");

        //  Log.d("intent" ,""+MUserId +"--"+DeviceType+"--"+DeviceNo);
        //  get_label_name();

        progressDialog = new ProgressDialog(RealMonitoringActivity.this);
        //progressDialog.setMessage("Loading..."); // Setting Message
       // progressDialog.setTitle("Please wait..."); // Setting Title

        String positiveText = mContext.getString(R.string.st_Loading);
        String positiveText1 = mContext.getString(R.string.st_Pleasewait);
        progressDialog.setMessage(positiveText); // Setting Message
        progressDialog.setTitle(positiveText1); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.hide(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        lvlBackIconViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer = null;
                finish();
            }
        });

        if (CustomUtility.isOnline(context)) {

           // callRealMParameterNameAPI();
           // callDeleteDeviceAPI();

            //setTimer();
           callProductStausAPI();
        } else {

            mProductStatusResponse = databaseHelperTeacher.getDeviceStatusListData(DeviceType, RMStatusOfProduct);
            //getDeviceStatusListData11

        }


        if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0)////////////////internet
        {

         /*   if (CustomUtility.isOnline(context)) {
                new Worker_label_name().execute();
                msg_flag = true;
                setTimer();
            }
            else
            {
                if(arraylist.size() > 0 )
                    arraylist.clear();

                progressDialog.dismiss();

                arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);

                if(arraylist.size() > 0) {

                    if (adapter != null)
                        adapter = null;

                    // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
                    adapter = new RealMonitoringListViewAdapter(getApplicationContext(), arraylist);

                    list.setAdapter(adapter);

                }
            }*/
        } else {
      /*      if (CustomUtility.isOnline(context)) {
                new Worker_label_name().execute();
                msg_flag = true;
                setTimer();
            }
            else
            {
                progressDialog.dismiss();

                if(arraylist.size() > 0 )
                    arraylist.clear();

                arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);
                if(arraylist.size() > 0) {

                    if (adapter != null)
                        adapter = null;
                    // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
                    adapter = new RealMonitoringListViewAdapter(getApplicationContext(), arraylist);
                    list.setAdapter(adapter);

                }
            }*/

        }



        imgShareIconID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    share_file();    ///this is for android 10 , 11 and above version.
                }
                else
                {
                    share_file();
                }
              /*  if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    check_Permission(mContext);
                } else {
                    share_file();
                }*/
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            timer.cancel();
            timer = null;
            back_flag = true;
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*try {
           // finish();
            timer.cancel();
            timer = null;
            back_flag = true;
           // finish();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
 /*   @Override
    protected void onStop() {
        super.onStop();
        try {
            // finish();
          //  timer.cancel();
          //  timer = null;
            //back_flag = true;
            // finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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
            case R.id.action_share_file:
                //Check if permission is granted or not
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    share_file();    ///this is for android 10 , 11 and above version.
                }
                else
                {
                    share_file();
                }


                /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    check_Permission(this);
                } else {
                    share_file();
                }*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    public void setTimer() {
        //  timer = new Timer();
        //Set the schedule function and rate
        System.out.println("bbb==>"+mModelType);

        timer.scheduleAtFixedRate(
                new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(
                                new Runnable() {

                                    @Override
                                    public void run() {
                                       new Worker().execute();
                                       // System.out.println("VikasVV1==>"+mModelType);
                                    //    callDeleteDeviceAPI();
                                        mProductStatusResponse = databaseHelperTeacher.getDeviceStatusListData(DeviceType, RMStatusOfProduct);
                                        lvlMainRealMonViewID.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                }
                ,
                0,
//                2500);     //500 = 1  second
                5000);     //500 = 5  second
    }

    private void callRealMParameterNameAPI() {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    RealMonitorParameterModel mRealMonitorParameterModel = gson.fromJson(Json, RealMonitorParameterModel.class);

                    getRealMParameterNameDataResponse(mRealMonitorParameterModel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                if (arraylist.size() > 0)
                    arraylist.clear();

                arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                if (arraylist.size() > 0)
                    arraylist.clear();

                arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });



        JsonObject jsonObject = new JsonObject();
        try {

            jsonObject.addProperty("MUserId", MUserId);
            jsonObject.addProperty("DeviceType", DeviceType);
            System.out.println("VikasVV1==>"+jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPostRealStartStop(1, jsonObject, NewSolarVFD.REAL_MONITORING1);/////

    }

    private void getRealMParameterNameDataResponse(RealMonitorParameterModel mRealMonitorParameterModel) {

        if(mRealMonitorParameterModel.getStatus())
        {

            if(mRealMonitorParameterResponse.size() >0)
                mRealMonitorParameterResponse.clear();
            mRealMonitorParameterResponse = mRealMonitorParameterModel.getResponse();




            for (int i = 0; i < mRealMonitorParameterResponse.size(); i++) {

                realMonitoring = new RealMonitoring();
                realMonitoring.setMPName(mRealMonitorParameterResponse.get(i).getMPName());
                realMonitoring.setUnit(mRealMonitorParameterResponse.get(i).getUnit());
                realMonitoring.setIndex(mRealMonitorParameterResponse.get(i).getMPIndex());
                realMonitoring.setDivisible(mRealMonitorParameterResponse.get(i).getDivisible());
                //     Log.d("obj_index", "" + jo.getString("MPIndex"));
                arraylist.add(realMonitoring);
                progressDialog.dismiss();
            }
            loading = 1;

            if (arraylist.size() > 0) {
                if (adapter != null)
                    adapter = null;
                runOnUiThread(

                        new Runnable() {

                            @Override
                            public void run() {
                                //  list.setAdapter(adapter);

                                adapter = new RealMonitoringListViewAdapter(context, arraylist);
                                list.setAdapter(adapter);

                            }
                        });
            }

        }

        msg_flag = true;
        setTimer();

    }

    public void get_label_name() {



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        param.add(new BasicNameValuePair("DeviceType", DeviceType));
        param.add(new BasicNameValuePair("MUserId", MUserId));

        if (CustomUtility.isOnline(context)) {
            try {
                String obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.HOST_NAME3 + "DataMonitor", param);
                //  Log.d("obj_real_name", "" + obj);
                System.out.println("objobjvk==>>"+NewSolarVFD.REAL_MONITORING);
                if (obj != null) {
                    progressDialog.dismiss();
                    JSONArray ja = new JSONArray(obj);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        realMonitoring = new RealMonitoring();
                        realMonitoring.setMPName(jo.getString("MPName"));
                        realMonitoring.setUnit(jo.getString("Unit"));
                        realMonitoring.setIndex(Integer.parseInt(jo.getString("MPIndex")));
                        realMonitoring.setDivisible(Float.parseFloat(jo.getString("Divisible")));
                        //     Log.d("obj_index", "" + jo.getString("MPIndex"));
                        arraylist.add(realMonitoring);
                        progressDialog.dismiss();
                    }
                    loading = 1;
                } else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                Log.d("exce", "" + e);
            }
        } else {
            progressDialog.dismiss();
            //   CustomUtility.isErrorDialog(context,"Error","No Internet Connection");
            ////////////vikas testing data
            if (arraylist.size() > 0)
                arraylist.clear();

            arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);
           /* if(arraylist.size() > 0) {
                if (adapter != null
                    adapter = null;
                // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
                adapter = new RealMonitoringListViewAdapter(getApplicationContext(), arraylist);
                list.setAdapter(adapter);
            }
            else {

            }*/

        }
    }

    private class Worker extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;
            String[] real_time_monitoring = new String[27];
            // ArrayList real_time_monitoring = new String[27];

            try {
                //  Log.d("real_time1", "" + DeviceNo + "--" +MUserId);
                timezone = pref.getString("key_time_zone_city", "null");
                // String url =  "http://111.118.249.180:1112/Home/DataMonitor?"+ "DeviceNo=" + DeviceNo + "&UserId=" +MUserId + "&DeviceType=" +DeviceType + "&TimeZones=" + timezone ;
                String url = NewSolarVFD.HOST_NAME3 + "DataMonitor" + "?" + "DeviceNo=" + DeviceNo + "&UserId=" + MUserId + "&DeviceType=" + DeviceType + "&TimeZones=" + timezone;
                System.out.println("urlurl==>"+url);
                String obj = CustomHttpClient.executeHttpGet(url);
                //    Log.d("real_time", "" + obj);

                if (obj != null) {
                    JSONArray ja = new JSONArray(obj);

                    for (int i = 0; i < ja.length(); i++) {
                        //   Log.d("real_array", "" + ja.length());
                        JSONObject jo = ja.getJSONObject(i);
                        // Log.d("worker1", "" +jo);
//                        if(! jo.getString("RMLatitude").equalsIgnoreCase("D"))
//                        {
                        real_time_monitoring[1] = jo.getString("RMLatitude");
                        real_time_monitoring[2] = jo.getString("RMLongitude");
                        real_time_monitoring[3] = jo.getString("RMDayOfDate");
                        real_time_monitoring[4] = jo.getString("RMMonthOfDate");
                        real_time_monitoring[5] = jo.getString("RMYearOfDate");
                        real_time_monitoring[6] = jo.getString("RMHourOfTime");
                        real_time_monitoring[7] = jo.getString("RMMinOfTime");
                        real_time_monitoring[8] = jo.getString("RMStatusOfProduct");
                        real_time_monitoring[9] = jo.getString("RMOperatingFreq");
                        real_time_monitoring[10] = jo.getString("RMOperatingVolt");
                        real_time_monitoring[11] = jo.getString("RMMotorCurr");
                        real_time_monitoring[12] = jo.getString("RMRPM");
                        real_time_monitoring[13] = jo.getString("RMLPM");
                        real_time_monitoring[14] = jo.getString("RMPVVolt");
                        real_time_monitoring[15] = jo.getString("RMPVCurr");
                        real_time_monitoring[16] = jo.getString("RMFaultBit");
                        real_time_monitoring[17] = jo.getString("RMINVModTemp");
                        real_time_monitoring[18] = jo.getString("RMHSTemp");
                        real_time_monitoring[19] = jo.getString("RMDSPTemp");
                        real_time_monitoring[20] = jo.getString("RMSingalStr");
                        real_time_monitoring[21] = jo.getString("R1");
                        real_time_monitoring[22] = jo.getString("R2");
                        real_time_monitoring[23] = jo.getString("R3");
                        real_time_monitoring[24] = jo.getString("R4");
                        real_time_monitoring[25] = jo.getString("R5");
//                        real_time_monitoring[26] =  jo.getString("RMRemark");
                        //  String mmStringRMRK = real_time_monitoring[26].replace(",","-");
                        //  String[] mRemarkSeprateValue = mmStringRMRK.split("-");
                        String[] mRemarkSeprateValue = jo.getString("RMRemark").split("," + "");
                        real_time_monitoring = Arrays.copyOf(real_time_monitoring, 26 + mRemarkSeprateValue.length);

                        for (int j = 0; j < mRemarkSeprateValue.length; j++) {
                            int addedIndex = 26 + j;
                            real_time_monitoring[addedIndex] = mRemarkSeprateValue[j];
                        }
                        RMSingalStr = jo.getString("RMSingalStr");
                        RMStatusOfProduct = jo.getString("RMStatusOfProduct");
                        RMDate = jo.getString("RMDate");
                        RMLatitude = jo.getString("RMLatitude");
                    }

                    // update list view header to value
                    boolean mCheckFirstReal;

                    for (int i = 0; i < arraylist.size(); i++) {
                        //  Log.d("array_list",""+ arraylist.get(i).getIndex());
                        realTime = new RealMonitoring();
                        realTime.setMPName(arraylist.get(i).getMPName());
                        realTime.setUnit(arraylist.get(i).getUnit());
                        realTime.setIndex(arraylist.get(i).getIndex());
                        realTime.setDivisible(arraylist.get(i).getDivisible());
                        realTime.setValue(real_time_monitoring[arraylist.get(i).getIndex()]);

                        if (i == 0) {
                            mCheckFirstReal = true;
                        } else {
                            mCheckFirstReal = false;
                        }
                        arraylist.set(i, realTime);

                        try {
                            databaseHelperTeacher.insertDeviceRealMonitoringParameterListData("" + arraylist.get(i).getIndex(), arraylist.get(i).getMPName(), arraylist.get(i).getUnit(), real_time_monitoring[arraylist.get(i).getIndex()], DeviceType, arraylist.get(i).getDivisible(), mCheckFirstReal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.i("SomeTag", System.currentTimeMillis() / 1000L
//                    + " post execute \n" + result);

            try {
// ** add new code 6.6.2018
                adapter.notifyDataSetChanged();
// ** add new code 6.6.2018
                //    Log.d("icon",""+RMSingalStr+"--"+RMStatusOfProduct);

                if (RMSingalStr.equalsIgnoreCase("0.00")) {
                    network_signal_image = context.getResources().getDrawable(R.mipmap.network_0);
                    network_icon.setCompoundDrawablesWithIntrinsicBounds(null, null, network_signal_image, null);
                }

                if (RMSingalStr.equalsIgnoreCase("1.00")) {
                    network_signal_image = context.getResources().getDrawable(R.mipmap.network_1);
                    network_icon.setCompoundDrawablesWithIntrinsicBounds(null, null, network_signal_image, null);
                }

                if (RMSingalStr.equalsIgnoreCase("2.00")) {
                    network_signal_image = context.getResources().getDrawable(R.mipmap.network_2);
                    network_icon.setCompoundDrawablesWithIntrinsicBounds(null, null, network_signal_image, null);
                }

                if (RMSingalStr.equalsIgnoreCase("3.00")) {
                    network_signal_image = context.getResources().getDrawable(R.mipmap.network_3);
                    network_icon.setCompoundDrawablesWithIntrinsicBounds(null, null, network_signal_image, null);
                }

                if (RMSingalStr.equalsIgnoreCase("4.00")) {
                    network_signal_image = context.getResources().getDrawable(R.mipmap.network_4);
                    network_icon.setCompoundDrawablesWithIntrinsicBounds(null, null, network_signal_image, null);
                }

                frame_date_time.setText(RMDate);
                frame_date_time.setTextColor(context.getResources().getColor(R.color.shakti_blue));

                tv_device_number.setText(DeviceNo);
                tv_device_number.setTextColor(context.getResources().getColor(R.color.shakti_blue));

                //   tv_mobile_number.setText(Mobile);
                //  tv_mobile_number.setTextColor( context .getResources().getColor(R.color.shakti_blue));

                tv_customer_name.setText(CustomerName);
                tv_customer_name.setTextColor(context.getResources().getColor(R.color.shakti_blue));

                if (mProductStatusResponse != null) {
                    try {
                        System.out.println("vikas ==  not null");
                        GradientDrawable bgShape = (GradientDrawable) real_monitor_icon.getBackground();

                        real_monitor_name.setTextColor(context.getResources().getColor(R.color.shakti_blue));
                        System.out.println("vikas = mProductStatusResponse.getSColor() ==  " + mProductStatusResponse.getSName());
                        System.out.println("vikas = mProductStatusResponse.getSColor() ==  " + mProductStatusResponse.getSColor());
                        bgShape.setColor(Color.parseColor(mProductStatusResponse.getSColor()));

                        real_monitor_name.setText(mProductStatusResponse.getSName());


                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }

                } else {

                    System.out.println("vikas == null");
                    GradientDrawable bgShape = (GradientDrawable) real_monitor_icon.getBackground();
                    bgShape.setColor(Color.parseColor(mProductStatusResponse.getSColor()));
                    real_monitor_name.setText(mProductStatusResponse.getSName());
                    real_monitor_name.setTextColor(context.getResources().getColor(R.color.shakti_blue));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // this.dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void callDeleteDeviceAPI() {
        timezone = pref.getString("key_time_zone_city", "null");

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {

                    JSONObject jo = new JSONObject(Json);

                    String mStatus = jo.getString("status");
                    final String mMessage = jo.getString("message");
                    String jo11 = jo.getString("response");
                    System.out.println("jo11==>>"+jo11);
                    if (mStatus.equalsIgnoreCase("true")) {

                        if(mRealMonitorvkResponse.size()>0)
                            mRealMonitorvkResponse.clear();

                        JSONArray ja = new JSONArray(jo11);
                        // JSONObject jo = ja.getJSONObject(0);

                        System.out.println("ja==>>"+ja.get(0));


                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject join = ja.getJSONObject(i);
                            RealMonitorvkResponse mmRealMonitorvkResponse = new RealMonitorvkResponse();

                            mmRealMonitorvkResponse.setRMId(join.getString("RMId"));
                            mmRealMonitorvkResponse.setRMUserId(join.getString("RMUserId"));
                            mmRealMonitorvkResponse.setRMDeviceNo(join.getString("RMDeviceNo"));
                            mmRealMonitorvkResponse.setRMFrameNo(join.getString("RMFrameNo"));
                            mmRealMonitorvkResponse.setRMLatitude(join.getString("RMLatitude"));
                            mmRealMonitorvkResponse.setRMLongitude(join.getString("RMLongitude"));
                            mmRealMonitorvkResponse.setRMDayOfDate(join.getString("RMDayOfDate"));
                            mmRealMonitorvkResponse.setRMMonthOfDate(join.getString("RMMonthOfDate"));
                            mmRealMonitorvkResponse.setRMYearOfDate(join.getString("RMYearOfDate"));
                            mmRealMonitorvkResponse.setRMHourOfTime(join.getString("RMHourOfTime"));
                            mmRealMonitorvkResponse.setRMMinOfTime(join.getString("RMMinOfTime"));
                            mmRealMonitorvkResponse.setRMStatusOfProduct(join.getString("RMStatusOfProduct"));
                            mmRealMonitorvkResponse.setRMOperatingFreq(join.getString("RMOperatingFreq"));
                            mmRealMonitorvkResponse.setRMOperatingVolt(join.getString("RMOperatingVolt"));
                            mmRealMonitorvkResponse.setRMMotorCurr(join.getString("RMMotorCurr"));
                            mmRealMonitorvkResponse.setRMRPM(join.getString("RMRPM"));
                            mmRealMonitorvkResponse.setRMLPM(join.getString("RMLPM"));
                            mmRealMonitorvkResponse.setRMPVVolt(join.getString("RMPVVolt"));
                            mmRealMonitorvkResponse.setRMPVCurr(join.getString("RMPVCurr"));

                            mmRealMonitorvkResponse.setRMFaultBit(join.getString("RMFaultBit"));
                            mmRealMonitorvkResponse.setRMINVModTemp(join.getString("RMINVModTemp"));
                            mmRealMonitorvkResponse.setRMHSTemp(join.getString("RMHSTemp"));
                            mmRealMonitorvkResponse.setRMDSPTemp(join.getString("RMDSPTemp"));
                            mmRealMonitorvkResponse.setRMSingalStr(join.getString("RMSingalStr"));
                            mmRealMonitorvkResponse.setR1(join.getString("R1"));
                            mmRealMonitorvkResponse.setR2(join.getString("R2"));
                            mmRealMonitorvkResponse.setR3(join.getString("R3"));

                            mmRealMonitorvkResponse.setR4(join.getString("R4"));
                            mmRealMonitorvkResponse.setR5(join.getString("R5"));
                            mmRealMonitorvkResponse.setRMRemark(join.getString("RMRemark"));
                            mmRealMonitorvkResponse.setRMDate(join.getString("RMDate"));
                            mmRealMonitorvkResponse.setRMSystem(join.getString("RMSystem"));
                            mmRealMonitorvkResponse.setRMDate(join.getString("RMDate"));
                           // mmRealMonitorvkResponse.setDongleNo(join.getString("DongleNo"));

                            mRealMonitorvkResponse.add(mmRealMonitorvkResponse);

                        }


                        String[] real_time_monitoring = new String[27];
                      /*  if(mRealMonitorvkResponse.size()> 0)
                            mRealMonitorvkResponse.clear();

                        mRealMonitorvkResponse = mRealMonitorModelView.getResponse();*/

                        for (int i = 0; i < mRealMonitorvkResponse.size(); i++) {

                            real_time_monitoring[1] = mRealMonitorvkResponse.get(i).getRMLatitude();
                            real_time_monitoring[2] = mRealMonitorvkResponse.get(i).getRMLongitude();
                            real_time_monitoring[3] = mRealMonitorvkResponse.get(i).getRMDayOfDate();
                            real_time_monitoring[4] = mRealMonitorvkResponse.get(i).getRMMonthOfDate();
                            real_time_monitoring[5] = mRealMonitorvkResponse.get(i).getRMYearOfDate();
                            real_time_monitoring[6] = mRealMonitorvkResponse.get(i).getRMHourOfTime();
                            real_time_monitoring[7] = mRealMonitorvkResponse.get(i).getRMMinOfTime();
                            real_time_monitoring[8] = mRealMonitorvkResponse.get(i).getRMStatusOfProduct();
                            real_time_monitoring[9] = mRealMonitorvkResponse.get(i).getRMOperatingFreq();
                            real_time_monitoring[10] = mRealMonitorvkResponse.get(i).getRMOperatingVolt();
                            real_time_monitoring[11] = mRealMonitorvkResponse.get(i).getRMMotorCurr();
                            real_time_monitoring[12] = mRealMonitorvkResponse.get(i).getRMRPM();
                            real_time_monitoring[13] = mRealMonitorvkResponse.get(i).getRMLPM();
                            real_time_monitoring[14] = mRealMonitorvkResponse.get(i).getRMPVVolt();
                            real_time_monitoring[15] = mRealMonitorvkResponse.get(i).getRMPVCurr();
                            real_time_monitoring[16] = mRealMonitorvkResponse.get(i).getRMFaultBit();
                            real_time_monitoring[17] = mRealMonitorvkResponse.get(i).getRMINVModTemp();
                            real_time_monitoring[18] = mRealMonitorvkResponse.get(i).getRMHSTemp();
                            real_time_monitoring[19] = mRealMonitorvkResponse.get(i).getRMDSPTemp();
                            real_time_monitoring[20] = mRealMonitorvkResponse.get(i).getRMSingalStr();
                            real_time_monitoring[21] = mRealMonitorvkResponse.get(i).getR1();
                            real_time_monitoring[22] = mRealMonitorvkResponse.get(i).getR2();
                            real_time_monitoring[23] = mRealMonitorvkResponse.get(i).getR3();
                            real_time_monitoring[24] = mRealMonitorvkResponse.get(i).getR4();
                            real_time_monitoring[25] = mRealMonitorvkResponse.get(i).getR5();
//                        real_time_monitoring[26] =  jo.getString("RMRemark");
                            //  String mmStringRMRK = real_time_monitoring[26].replace(",","-");
                            //  String[] mRemarkSeprateValue = mmStringRMRK.split("-");
                            //  String[] mRemarkSeprateValue = jo.getString("RMRemark").split("," + "");
                            String[] mRemarkSeprateValue = mRealMonitorvkResponse.get(i).getRMRemark().split("," + "");
                            real_time_monitoring = Arrays.copyOf(real_time_monitoring, 26 + mRemarkSeprateValue.length);

                            for (int j = 0; j < mRemarkSeprateValue.length; j++) {
                                int addedIndex = 26 + j;
                                real_time_monitoring[addedIndex] = mRemarkSeprateValue[j];
                            }
                            RMSingalStr = mRealMonitorvkResponse.get(i).getRMSingalStr();
                            RMStatusOfProduct = mRealMonitorvkResponse.get(i).getRMStatusOfProduct();
                            RMDate = mRealMonitorvkResponse.get(i).getRMDate();
                            RMLatitude = mRealMonitorvkResponse.get(i).getRMLatitude();

                        }

                        boolean mCheckFirstReal;

                        for (int i = 0; i < arraylist.size(); i++) {
                            //  Log.d("array_list",""+ arraylist.get(i).getIndex());
                            realTime = new RealMonitoring();
                            realTime.setMPName(arraylist.get(i).getMPName());
                            realTime.setUnit(arraylist.get(i).getUnit());
                            realTime.setIndex(arraylist.get(i).getIndex());
                            realTime.setDivisible(arraylist.get(i).getDivisible());
                            realTime.setValue(real_time_monitoring[arraylist.get(i).getIndex()]);

                            if (i == 0) {
                                mCheckFirstReal = true;
                            } else {
                                mCheckFirstReal = false;
                            }
                            arraylist.set(i, realTime);

                            try {
                                databaseHelperTeacher.insertDeviceRealMonitoringParameterListData("" + arraylist.get(i).getIndex(), arraylist.get(i).getMPName(), arraylist.get(i).getUnit(), real_time_monitoring[arraylist.get(i).getIndex()], DeviceType, arraylist.get(i).getDivisible(), mCheckFirstReal);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }



                    }
                    else
                    {
                        Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();
                        baseRequest.hideLoader();
                    }

              /*      Gson gson = new Gson();
                    //////////////add model class here

                    RealMonitorModelView mRealMonitorModelView = gson.fromJson(Json, RealMonitorModelView.class);
                     getRealMonitorDataResponse(mRealMonitorModelView);*/

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

        Map<String, String> wordsByKey = new HashMap<>();

        wordsByKey.put("UserId", MUserId);
        wordsByKey.put("DeviceNo", DeviceNo);
        wordsByKey.put("DeviceType", DeviceType);
        wordsByKey.put("TimeZones", timezone);
        // wordsByKey.put("ClientId", ClientId);
        //   wordsByKey.put("IMEI","38648723487236487264");

      //  String url = NewSolarVFD.REAL_MONITORING + "?" + "DeviceNo=" + DeviceNo + "&UserId=" + MUserId + "&DeviceType=" + DeviceType + "&TimeZones=" + timezone;
        System.out.println("VikasVV1==>"+wordsByKey);


        baseRequest.callAPIGETRealStartStop(1, wordsByKey, NewSolarVFD.REAL_MONITORING1);/////
      //  baseRequest.callAPIGETRealMonitor(1, wordsByKey, NewSolarVFD.REAL_MONITORING_VK);/////

    }

    private void getRealMonitorDataResponse(RealMonitorModelView mRealMonitorModelView) {

        if(mRealMonitorModelView.getStatus())
        {
            String[] real_time_monitoring = new String[27];
            if(mRealMonitorvkResponse.size()> 0)
                mRealMonitorvkResponse.clear();

            mRealMonitorvkResponse = mRealMonitorModelView.getResponse();

            for (int i = 0; i < mRealMonitorvkResponse.size(); i++) {

                real_time_monitoring[1] = mRealMonitorvkResponse.get(i).getRMLatitude();
                real_time_monitoring[2] = mRealMonitorvkResponse.get(i).getRMLongitude();
                real_time_monitoring[3] = mRealMonitorvkResponse.get(i).getRMDayOfDate();
                real_time_monitoring[4] = mRealMonitorvkResponse.get(i).getRMMonthOfDate();
                real_time_monitoring[5] = mRealMonitorvkResponse.get(i).getRMYearOfDate();
                real_time_monitoring[6] = mRealMonitorvkResponse.get(i).getRMHourOfTime();
                real_time_monitoring[7] = mRealMonitorvkResponse.get(i).getRMMinOfTime();
                real_time_monitoring[8] = mRealMonitorvkResponse.get(i).getRMStatusOfProduct();
                real_time_monitoring[9] = mRealMonitorvkResponse.get(i).getRMOperatingFreq();
                real_time_monitoring[10] = mRealMonitorvkResponse.get(i).getRMOperatingVolt();
                real_time_monitoring[11] = mRealMonitorvkResponse.get(i).getRMMotorCurr();
                real_time_monitoring[12] = mRealMonitorvkResponse.get(i).getRMRPM();
                real_time_monitoring[13] = mRealMonitorvkResponse.get(i).getRMLPM();
                real_time_monitoring[14] = mRealMonitorvkResponse.get(i).getRMPVVolt();
                real_time_monitoring[15] = mRealMonitorvkResponse.get(i).getRMPVCurr();
                real_time_monitoring[16] = mRealMonitorvkResponse.get(i).getRMFaultBit();
                real_time_monitoring[17] = mRealMonitorvkResponse.get(i).getRMINVModTemp();
                real_time_monitoring[18] = mRealMonitorvkResponse.get(i).getRMHSTemp();
                real_time_monitoring[19] = mRealMonitorvkResponse.get(i).getRMDSPTemp();
                real_time_monitoring[20] = mRealMonitorvkResponse.get(i).getRMSingalStr();
                real_time_monitoring[21] = mRealMonitorvkResponse.get(i).getR1();
                real_time_monitoring[22] = mRealMonitorvkResponse.get(i).getR2();
                real_time_monitoring[23] = mRealMonitorvkResponse.get(i).getR3();
                real_time_monitoring[24] = mRealMonitorvkResponse.get(i).getR4();
                real_time_monitoring[25] = mRealMonitorvkResponse.get(i).getR5();
//                        real_time_monitoring[26] =  jo.getString("RMRemark");
                //  String mmStringRMRK = real_time_monitoring[26].replace(",","-");
                //  String[] mRemarkSeprateValue = mmStringRMRK.split("-");
              //  String[] mRemarkSeprateValue = jo.getString("RMRemark").split("," + "");
                String[] mRemarkSeprateValue = mRealMonitorvkResponse.get(i).getRMRemark().split("," + "");
                real_time_monitoring = Arrays.copyOf(real_time_monitoring, 26 + mRemarkSeprateValue.length);

                for (int j = 0; j < mRemarkSeprateValue.length; j++) {
                    int addedIndex = 26 + j;
                    real_time_monitoring[addedIndex] = mRemarkSeprateValue[j];
                }
                RMSingalStr = mRealMonitorvkResponse.get(i).getRMSingalStr();
                RMStatusOfProduct = mRealMonitorvkResponse.get(i).getRMStatusOfProduct();
                RMDate = mRealMonitorvkResponse.get(i).getRMDate();
                RMLatitude = mRealMonitorvkResponse.get(i).getRMLatitude();

            }

            boolean mCheckFirstReal;

            for (int i = 0; i < arraylist.size(); i++) {
                //  Log.d("array_list",""+ arraylist.get(i).getIndex());
                realTime = new RealMonitoring();
                realTime.setMPName(arraylist.get(i).getMPName());
                realTime.setUnit(arraylist.get(i).getUnit());
                realTime.setIndex(arraylist.get(i).getIndex());
                realTime.setDivisible(arraylist.get(i).getDivisible());
                realTime.setValue(real_time_monitoring[arraylist.get(i).getIndex()]);

                if (i == 0) {
                    mCheckFirstReal = true;
                } else {
                    mCheckFirstReal = false;
                }
                arraylist.set(i, realTime);

                try {
                    databaseHelperTeacher.insertDeviceRealMonitoringParameterListData("" + arraylist.get(i).getIndex(), arraylist.get(i).getMPName(), arraylist.get(i).getUnit(), real_time_monitoring[arraylist.get(i).getIndex()], DeviceType, arraylist.get(i).getDivisible(), mCheckFirstReal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }






    @Override
    public void onBackPressed() {
        //  ExitFromRealMonitoring(context);

       // finish();
        timer.cancel();
        timer = null;
        back_flag = true;
        finish();
    }



    private class Worker_label_name extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            String data = null;
            try {

                get_label_name();

                if (arraylist.size() > 0) {

                    if (adapter != null)
                        adapter = null;
                    runOnUiThread(

                            new Runnable() {

                                @Override
                                public void run() {
                                    //  list.setAdapter(adapter);

                                    adapter = new RealMonitoringListViewAdapter(context, arraylist);
                                    list.setAdapter(adapter);

                                }
                            });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.i("SomeTag", System.currentTimeMillis() / 1000L
//                    + " post execute \n" + result);
            progressDialog.dismiss();
        }

    }

    public static void check_Permission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionsIntent.WRITE_EXTERNAL_STORAGE_PERMISSION);

        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionsIntent.WRITE_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    public void share_file() {

        try {
           /* File cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "Shakti_RMS/Real_Time_Data");

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }*/

            File cacheDir = GlobalMethod.commonDocumentDirPathphoto("Shakti_RMS/Real_Time_Data");
           /* String path =
                    android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Shakti_RMS/Real_Time_Data"  + "/" +
                            String.valueOf(System.currentTimeMillis())+".jpg";*/
            String path =
                    GlobalMethod.commonDocumentDirPathphotoPath() + "/" + "Shakti_RMS/Real_Time_Data"  + "/" +
                            String.valueOf(System.currentTimeMillis())+".jpg";
            Utils.savePic(Utils.takeScreenShot(this), path);

            File dir = new File(path);

            Uri photoURI = FileProvider.getUriForFile(RealMonitoringActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    dir);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Shakti RMS Real Time Data");
            // shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
            shareIntent.putExtra(Intent.EXTRA_STREAM,  photoURI  );

            shareIntent.setType("image/jpeg");

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(shareIntent, "Share"));

//*********************************************************************************
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }




    private void callProductStausAPI() {


        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    ProductStatusModel mProductStatusModel = gson.fromJson(Json, ProductStatusModel.class);
                    getProductStatusResponse(mProductStatusModel);

                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                progressDialog.dismiss();
                System.out.println("message====>>>" + message);
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            // jsonObject.addProperty("MUserId", MUserId);
            // jsonObject.addProperty("DeviceType", mModelType);///Model type value
            jsonObject.addProperty("DeviceType", DeviceType);

            ///Model type value
            // jsonObject.addProperty("DeviceNo", vDeviceNo);
            //   jsonObject.addProperty("DeviceNo", "");
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        System.out.println("VikasVV1==>" + jsonObject);
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_PRODUCT_STATUS);/////
    }

    private void getProductStatusResponse(ProductStatusModel mProductStatusModel) {
        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mProductStatusModel.getStatus()) {
            if (mProductStatusResponseList.size() > 0)
                mProductStatusResponseList.clear();
            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mProductStatusResponseList = mProductStatusModel.getResponse();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();

            for (int i = 0; i < mProductStatusResponseList.size(); i++) {

                if (i == 0) {
                    mCheckFirstDB = true;
                } else {
                    mCheckFirstDB = false;
                }
                databaseHelperTeacher.insertDeviceStatusListData(mProductStatusResponseList.get(i).getSMode(), mProductStatusResponseList.get(i).getSName(), mProductStatusResponseList.get(i).getSColor(), mProductStatusResponseList.get(i).getDeviceType(), "1", "1", "1", mCheckFirstDB);
            }

            if (CustomUtility.isOnline(context)) {
                //new Worker_label_name().execute();
                callRealMParameterNameAPI();
               // msg_flag = true;
              //  setTimer();
            } else {
                if (arraylist.size() > 0)
                    arraylist.clear();

                progressDialog.dismiss();
                arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);
                if (arraylist.size() > 0) {
                    if (adapter != null)
                        adapter = null;
                    // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
                    adapter = new RealMonitoringListViewAdapter(getApplicationContext(), arraylist);
                    list.setAdapter(adapter);
                }
            }
        }
    }

}




