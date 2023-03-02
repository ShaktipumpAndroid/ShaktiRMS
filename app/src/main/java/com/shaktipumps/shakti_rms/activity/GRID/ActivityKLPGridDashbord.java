package com.shaktipumps.shakti_rms.activity.GRID;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.devlomi.circularstatusview.CircularStatusView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.adapter.ExpandableListAdapter;
import com.shaktipumps.shakti_rms.adapter.GRIDDeviceViewAdapter;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.GetPlant.KLPGRFDayModel;
import com.shaktipumps.shakti_rms.bean.GetPlant.KLPGRFDayResponse;
import com.shaktipumps.shakti_rms.bean.GetPlant.KLPGRFMothtModel;
import com.shaktipumps.shakti_rms.bean.GetPlant.KLPGRFMothtResponse;
import com.shaktipumps.shakti_rms.bean.GetPlant.KLPOnlyMonthEnergyModel;
import com.shaktipumps.shakti_rms.bean.GetPlant.KLPOnlyMonthEnergyResponse;
import com.shaktipumps.shakti_rms.bean.HomeDeviceListBean.HomeDeviceListModel;
import com.shaktipumps.shakti_rms.bean.HomeDeviceListBean.HomeDeviceListResponse;
import com.shaktipumps.shakti_rms.bean.KlpGridDashboardBean.KlpDashboardModelView;
import com.shaktipumps.shakti_rms.bean.KlpGridDashboardBean.KlpgridDashboardResponse;
import com.shaktipumps.shakti_rms.bean.Org_Client;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ActivityKLPGridDashbord extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, OnMapReadyCallback {

    //private LineData data;
    private BarData data;
    private LineData dataLine;
    private Context mContext;
    private TextView txttodayDateGraphID;
    private TextView txtTotalEnergyHeadID;
    private TextView txtTotalPlantsValueID;
    private TextView txtTotalErrorValueID;
    private TextView txtTodayEnergyValueID,txtTotalEnergyValueID,txtTodayHourValueIDB,txtTotalHourValueIDB, txtTodayEnergyUnitID, txtMontlyEnergyValueID, txtMontlyEnergyUnitID;
    private TextView txtTodayPowerValueIDB, txtTodayPowerUnitID, txtTotalPowerValueIDB, txtTotalPowerUnitID;

    private RelativeLayout rlvAddDEviceViewID;

    private RelativeLayout rlvChatBottomViewID;
    private ImageView imgMessageIconID;
    private TextView txtMessageHeadID;

    private RelativeLayout rlvPlantBottomViewID;
    private ImageView imgPlantIconID;
    private ImageView imgMapIconID;
    private TextView txtMapHeadID;
    private TextView txtPlantHeadID;

    private RelativeLayout rlvUserBottomViewID;
    private RelativeLayout rlvMapBottomViewID;
    private RelativeLayout rlvDashbordDeviceViewID;
    private RelativeLayout rlvDashbordMapViewID;
    private RelativeLayout rlvDashbordGraphViewID;
    private RelativeLayout rlvDashbordViewID;
    private ImageView imgUserIconID;
    private TextView txtUserHeadID;

    private CircularStatusView crlvTotalPlantID, crlvTotalErrorID;
    private BaseRequest baseRequest;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String mUserID;
    private KlpgridDashboardResponse mKlpgridDashboardResponse;

    private List<KLPGRFMothtResponse> mKLPGRFMothtResponse;
    private List<KLPGRFDayResponse> mKLPGRFDayResponse;
    private KLPOnlyMonthEnergyResponse mKLPOnlyMonthEnergyResponse;
    private Intent myIntent;
    private String mPlantID;
    private String mPlantName;
    private RelativeLayout rlvBackViewID;

    private ListView list;

    private Customer_GPS_Search customer_gps;
    private ArrayList<Customer_GPS_Search> arraylist;

    private GRIDDeviceViewAdapter    adapter;

    private ProgressDialog progressDialog;

    private int clientid = 0, ParentId = 0, SupParentId = 0, first_level = 0, SupClientId = 0;

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<String> topChild;
    private ArrayList<Org_Client> clientArray;
    private Org_Client org_client;

    private boolean expand = false;
    private ExpandableListView orgListView;
    private RelativeLayout org_layout;
    private TextView previous_level;
    private ExpandableListAdapter orglistAdapter;

    private String newVersion = "0.0", currentVersion = "0.0", MUserId = "null", clientName = "null", ParentName = "null";

    private RelativeLayout rlvDayViewID, rlvMonthViewID;
    private TextView txtTotalEnergyValueKLPID;
    private TextView txtHdrTextID;
    private TextView txtEnergyHeadingGraphID, txtDateHeadingGraphID, txtOflinePlantsValueID;
    //private LineChart chartGridEnergyID1;
        private LineChartView chartGridEnergyID1;
    private BarChart chartGridEnergyID;
    List<HomeDeviceListResponse> mHomeDeviceListResponse;
    private DatabaseHelperTeacher databaseHelperTeacher;

    private int backCountNumber = 1;///1- dasgborad; 2-graph, 3-device list;


    private DatePickerDialog dpd;
    Calendar now ;

    String mMinDay,mMinMonth,mMinYear;
    String mMaxDay,mMaxMonth,mMaxYear;

    int mDayMonthCheck = 0;
    private GoogleMap mMap;

    BitmapDescriptor icon;

  //  Calendar now = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klpgrid_dashbord);
        baseRequest = new BaseRequest(this);
        mKlpgridDashboardResponse = new KlpgridDashboardResponse();
        mKLPOnlyMonthEnergyResponse = new KLPOnlyMonthEnergyResponse();
        databaseHelperTeacher = new DatabaseHelperTeacher(this);/////////////////AUTHModelData base
        mHomeDeviceListResponse = new ArrayList<>();
        mKLPGRFMothtResponse = new ArrayList<>();
        mKLPGRFDayResponse = new ArrayList<>();
        mContext = this;
        now = Calendar.getInstance();
        initView();
    }

    private void initView() {


        chartGridEnergyID =  findViewById(R.id.chartGridEnergyID);
        chartGridEnergyID1 =  findViewById(R.id.chartGridEnergyID1);
        rlvDayViewID =  findViewById(R.id.rlvDayViewID);
        rlvMonthViewID =  findViewById(R.id.rlvMonthViewID);
        txtTotalEnergyValueKLPID =  findViewById(R.id.txtTotalEnergyValueKLPID);
        txtEnergyHeadingGraphID =  findViewById(R.id.txtEnergyHeadingGraphID);
        txtDateHeadingGraphID =  findViewById(R.id.txtDateHeadingGraphID);
        txtHdrTextID =  findViewById(R.id.txtHdrTextID);
        txtOflinePlantsValueID =  findViewById(R.id.txtOflinePlantsValueID);

        pref = mContext.getSharedPreferences("MyPref", 0);
        editor = pref.edit();

         myIntent = getIntent(); // gets the previously created intent
         mPlantID = myIntent.getStringExtra("PlantID"); // will return "FirstKeyValue"
         mPlantName = myIntent.getStringExtra("PlantName"); // will return "FirstKeyValue"
        txtHdrTextID.setText(mPlantName);
        try {
            clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0
        } catch (NumberFormatException e) {
            e.printStackTrace();
            clientid = 9999;
        }

        //  Log.d("check_id", ""+clientid);

        if (clientid == 9999) {
            clientid = 0;
        }

        clientArray = new ArrayList<Org_Client>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        topChild = new ArrayList<String>();
        arraylist = new ArrayList<Customer_GPS_Search>();

        list = findViewById(R.id.listview);
      //  listview.setLayoutManager(new LinearLayoutManager(mContext));

        previous_level =  findViewById(R.id.previous_level);
         orgListView =  findViewById(R.id.orglistview);

        org_layout =  findViewById(R.id.org_layout);
        rlvBackViewID = findViewById(R.id.rlvBackViewID);
        crlvTotalPlantID = findViewById(R.id.crlvTotalPlantID);
        crlvTotalErrorID = findViewById(R.id.crlvTotalErrorID);

        rlvAddDEviceViewID = findViewById(R.id.rlvAddDEviceViewID);

        txttodayDateGraphID = findViewById(R.id.txttodayDateGraphID);
        txtTotalEnergyHeadID = findViewById(R.id.txtTotalEnergyHeadID);
        txtTotalPlantsValueID = findViewById(R.id.txtTotalPlantsValueID);
        txtTotalErrorValueID = findViewById(R.id.txtTotalErrorValueID);

        txtTotalHourValueIDB = findViewById(R.id.txtTotalHourValueIDB);

        txtTodayHourValueIDB = findViewById(R.id.txtTodayHourValueIDB);
        txtTotalEnergyValueID = findViewById(R.id.txtTotalEnergyValueID);
        txtTodayEnergyValueID = findViewById(R.id.txtTodayEnergyValueID);
        txtTodayEnergyUnitID = findViewById(R.id.txtTodayEnergyUnitID);

        txtMontlyEnergyValueID = findViewById(R.id.txtMontlyEnergyValueID);
        txtMontlyEnergyUnitID = findViewById(R.id.txtMontlyEnergyUnitID);

        txtTodayPowerValueIDB = findViewById(R.id.txtTodayPowerValueIDB);
        txtTodayPowerUnitID = findViewById(R.id.txtTodayPowerUnitID);

        txtTotalPowerValueIDB = findViewById(R.id.txtTotalPowerValueIDB);
        txtTotalPowerUnitID = findViewById(R.id.txtTotalPowerUnitID);

        rlvMapBottomViewID = findViewById(R.id.rlvMapBottomViewID);

        rlvChatBottomViewID = findViewById(R.id.rlvChatBottomViewID);
        imgMessageIconID = findViewById(R.id.imgMessageIconID);
        txtMessageHeadID = findViewById(R.id.txtMessageHeadID);

        rlvUserBottomViewID = findViewById(R.id.rlvUserBottomViewID);
        rlvDashbordDeviceViewID = findViewById(R.id.rlvDashbordDeviceViewID);
        rlvDashbordMapViewID = findViewById(R.id.rlvDashbordMapViewID);
        rlvDashbordGraphViewID = findViewById(R.id.rlvDashbordGraphViewID);
        rlvDashbordViewID = findViewById(R.id.rlvDashbordViewID);
        imgUserIconID = findViewById(R.id.imgUserIconID);
        txtUserHeadID = findViewById(R.id.txtUserHeadID);

        rlvPlantBottomViewID = findViewById(R.id.rlvPlantBottomViewID);
        imgPlantIconID = findViewById(R.id.imgPlantIconID);
        imgMapIconID = findViewById(R.id.imgMapIconID);
        txtPlantHeadID = findViewById(R.id.txtPlantHeadID);
        txtMapHeadID = findViewById(R.id.txtMapHeadID);

        mUserID = pref.getString("key_muserid", "invalid_muserid");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        txttodayDateGraphID.setText(currentDate);

        clickEventAll();
        callGetPlantListCheckAPI();


       /* runOnUiThread(

                new Runnable() {
                    @Override
                    public void run() {

                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.getUiSettings().setZoomGesturesEnabled(true);
                        mMap.getUiSettings().setCompassEnabled(true);

                       // String title = "Device No :" + arraylist.get(i).getDeviceNo();
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_green);

                        try {
                            LatLng location;
                           *//* if (mKlpgridDashboardResponse.getLat() != null || !mKlpgridDashboardResponse.getLong().equalsIgnoreCase("")) {
                                location = new LatLng(Double.parseDouble(mKlpgridDashboardResponse.getLat()), Double.parseDouble(mKlpgridDashboardResponse.getLong()));
                            } else {
                                location = new LatLng(Double.parseDouble("22.627236"), Double.parseDouble("75.5810237"));

                            }*//*
                            location = new LatLng(Double.parseDouble("22.627236"), Double.parseDouble("75.5810237"));
                            // LatLng location = new LatLng(Double.parseDouble(arraylist.get(i).getLatitude()),Double.parseDouble(arraylist.get(i).getLongitude()));

                            MarkerOptions markerOptions = new MarkerOptions().position(location)
                                    .title(mPlantName)
                                    .snippet(mPlantID)
                                    .icon(icon);

                            mMap.addMarker(markerOptions);

                            float zoomLevel = 5;
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                     *//*   for (int i = 0; i < arraylist.size(); i++) {


                            if (arraylist.get(i).getIsLogin().equalsIgnoreCase("true") && arraylist.get(i).getPumpStatus().equalsIgnoreCase("true")) {
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_green);
                            } else if (arraylist.get(i).getIsLogin().equalsIgnoreCase("false") && arraylist.get(i).getPumpStatus().equalsIgnoreCase("false")) {
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_gray);
                            } else if (arraylist.get(i).getIsLogin().equalsIgnoreCase("true") && arraylist.get(i).getPumpStatus().equalsIgnoreCase("false")) {
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_blue);
                            } else if (arraylist.get(i).getIsLogin().equalsIgnoreCase("false") && arraylist.get(i).getPumpStatus().equalsIgnoreCase("true")) {
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_blue);
                            }


                            String title = "Device No :" + arraylist.get(i).getDeviceNo();


                            try {
                                LatLng location;
                                if (arraylist.get(i).getLatitude() != null || !arraylist.get(i).getLatitude().equalsIgnoreCase("")) {
                                    location = new LatLng(Double.parseDouble(arraylist.get(i).getLatitude()), Double.parseDouble(arraylist.get(i).getLongitude()));
                                } else {
                                    location = new LatLng(Double.parseDouble("22.627236"), Double.parseDouble("75.5810237"));

                                }
                                // LatLng location = new LatLng(Double.parseDouble(arraylist.get(i).getLatitude()),Double.parseDouble(arraylist.get(i).getLongitude()));

                                MarkerOptions markerOptions = new MarkerOptions().position(location)
                                        .title(arraylist.get(i).getCustomerName())
                                        .snippet(title)
                                        .icon(icon);

                                mMap.addMarker(markerOptions);

                                float zoomLevel = 5;
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }*//*
                    }
                });*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dpd = null;
    }


    private void clickEventAll() {

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                /// navigate device list
                if(backCountNumber == 1)
                {
                    finish();
                }
                else if(backCountNumber == 2)
                {
                    backCountNumber = 1;
                    rlvDashbordDeviceViewID.setVisibility(View.GONE);
                    rlvDashbordMapViewID.setVisibility(View.GONE);
                    rlvDashbordGraphViewID.setVisibility(View.GONE);
                    rlvDashbordViewID.setVisibility(View.VISIBLE);

                    imgPlantIconID.setImageResource(R.drawable.ic_home_ds_selected);
                    // txtPlantHeadID.setTextColor(R.color.chocolate);
                    txtPlantHeadID.setTextColor(getColor(R.color.colorBuleDark));

                    imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_unselected);
                    txtUserHeadID.setTextColor(getColor(R.color.white));

                    imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                    txtMessageHeadID.setTextColor(getColor(R.color.white));

                    imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                    txtMapHeadID.setTextColor(getColor(R.color.white));

                    rlvPlantBottomViewID.setBackgroundColor(Color.WHITE);
                    //  rlvUserBottomViewID.setBackgroundColor(Color.WHITE);
                    rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                    rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                    rlvUserBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                }
                else if(backCountNumber == 3)
                {
                    backCountNumber = 1;
                    rlvDashbordDeviceViewID.setVisibility(View.GONE);
                    rlvDashbordMapViewID.setVisibility(View.GONE);
                    rlvDashbordGraphViewID.setVisibility(View.GONE);
                    rlvDashbordViewID.setVisibility(View.VISIBLE);

                    imgPlantIconID.setImageResource(R.drawable.ic_home_ds_selected);
                    // txtPlantHeadID.setTextColor(R.color.chocolate);
                    txtPlantHeadID.setTextColor(getColor(R.color.colorBuleDark));

                    imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_unselected);
                    txtUserHeadID.setTextColor(getColor(R.color.white));

                    imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                    txtMessageHeadID.setTextColor(getColor(R.color.white));

                    imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                    txtMapHeadID.setTextColor(getColor(R.color.white));

                    rlvPlantBottomViewID.setBackgroundColor(Color.WHITE);
                    //  rlvUserBottomViewID.setBackgroundColor(Color.WHITE);
                    rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                    rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                    rlvUserBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                } else if(backCountNumber == 4)
                {
                    backCountNumber = 1;
                    rlvDashbordDeviceViewID.setVisibility(View.GONE);
                    rlvDashbordGraphViewID.setVisibility(View.GONE);
                    rlvDashbordViewID.setVisibility(View.VISIBLE);

                    imgPlantIconID.setImageResource(R.drawable.ic_home_ds_selected);
                    // txtPlantHeadID.setTextColor(R.color.chocolate);
                    txtPlantHeadID.setTextColor(getColor(R.color.colorBuleDark));

                    imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_unselected);
                    txtUserHeadID.setTextColor(getColor(R.color.white));

                    imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                    txtMessageHeadID.setTextColor(getColor(R.color.white));

                    imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                    txtMapHeadID.setTextColor(getColor(R.color.white));

                    rlvPlantBottomViewID.setBackgroundColor(Color.WHITE);
                  //  rlvUserBottomViewID.setBackgroundColor(Color.WHITE);
                    rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                    rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                    rlvUserBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                }
             //  finish();
            }
        });

 rlvAddDEviceViewID.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                /// navigate device list
                Intent mIntent = new Intent(mContext, AddDeviceINPlant.class);
                mIntent.putExtra("PlantID", mPlantID);
                startActivity(mIntent);

            }
        });

        crlvTotalPlantID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                rlvDashbordDeviceViewID.setVisibility(View.VISIBLE);
                rlvDashbordMapViewID.setVisibility(View.GONE);
                rlvDashbordGraphViewID.setVisibility(View.GONE);
                rlvDashbordViewID.setVisibility(View.GONE);

                rlvUserBottomViewID.setBackgroundColor(Color.WHITE);
                rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvPlantBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                imgPlantIconID.setImageResource(R.drawable.ic_home_ds_unselected);
                txtPlantHeadID.setTextColor(getColor(R.color.white));

                imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_selected);
                // txtUserHeadID.setTextColor(R.color.chocolate);
                txtUserHeadID.setTextColor(getColor(R.color.colorBuleDark));

                imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                txtMessageHeadID.setTextColor(getColor(R.color.white));

                imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                txtMapHeadID.setTextColor(getColor(R.color.white));

                if(arraylist.size() > 0)
                    arraylist.clear();
              //  new Worker().execute();
                callHomeDeviceListAPI();
                /// navigate device list
            }
        });
        crlvTotalErrorID.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContext, FaultDeviceListActivity.class);
                mIntent.putExtra("PlantID",mPlantID);
                startActivity(mIntent);
                /// navigate device list

            }
        });

           rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);

                txtDateHeadingGraphID.setText(getString(R.string.st_HR));
                // txtSetValueHead();
                System.out.println("vikas Start");
                txtTotalEnergyValueKLPID.setText(mKlpgridDashboardResponse.getTodaytotalgscenergy());
               // callGetPlantKLPGraphDayAPI();



            /*
            It is recommended to always create a new instance whenever you need to show a Dialog.
            The sample app is reusing them because it is useful when looking for regressions
            during testing
             */

              /*  dpd.setThemeDark(modeDarkDate.isChecked());
                dpd.vibrate(vibrateDate.isChecked());
                dpd.dismissOnPause(dismissDate.isChecked());
                dpd.showYearPickerFirst(showYearFirst.isChecked());
                dpd.setVersion(showVersion2.isChecked() ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
*/
                mDayMonthCheck = 1;
                String monthNumber  = (String) DateFormat.format("MM",   new Date()); // 06
                //   String year         = (String) DateFormat.format("yyyy", new Date()); // 2013
                String year         = (String) DateFormat.format("yy", new Date()); // 13
                System.out.println("mMonthV11==>>"+monthNumber+""+year);
                callGetPlantKLPGraphDayAPI(monthNumber+""+year, monthNumber+""+year);

                //showDatePickerDialog();

            }
        });

        rlvMonthViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeButtonVisibilityRLV(true, 1.0f, rlvMonthViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvDayViewID);

                txtDateHeadingGraphID.setText(getString(R.string.st_DATE));

                txtTotalEnergyValueKLPID.setText(mKLPOnlyMonthEnergyResponse.getTotalMonthEnergy());
               // callGetPlantKLPGraphMonthAPI();

                mDayMonthCheck = 2;
                showDatePickerDialog();

            }
        });

        rlvChatBottomViewID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                backCountNumber = 2;
                rlvDashbordDeviceViewID.setVisibility(View.GONE);
                rlvDashbordMapViewID.setVisibility(View.GONE);
                rlvDashbordViewID.setVisibility(View.GONE);
                rlvDashbordGraphViewID.setVisibility(View.VISIBLE);

                rlvChatBottomViewID.setBackgroundColor(Color.WHITE);
                rlvUserBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvPlantBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                imgPlantIconID.setImageResource(R.drawable.ic_home_ds_unselected);
                //txtPlantHeadID.setTextColor(R.color.colorGray);
                txtPlantHeadID.setTextColor(getColor(R.color.white));

                imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_unselected);
                txtUserHeadID.setTextColor(getColor(R.color.white));

                imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_selected);
              //  txtMessageHeadID.setTextColor(R.color.chocolate);
                txtMessageHeadID.setTextColor(getColor(R.color.colorBuleDark));

                imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                txtMapHeadID.setTextColor(getColor(R.color.white));

                changeButtonVisibilityRLV(true, 0.5f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 1.0f, rlvMonthViewID);

                String monthNumber  = (String) DateFormat.format("MM",   new Date()); // 06
             //   String year         = (String) DateFormat.format("yyyy", new Date()); // 2013
                String year         = (String) DateFormat.format("yy", new Date()); // 13
                System.out.println("mMonthV11==>>"+monthNumber+""+year);
                callGetPlantKLPGraphMonthAPI(monthNumber+""+year);

            }
        });

        rlvUserBottomViewID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                rlvUserBottomViewID.setBackgroundColor(Color.WHITE);
                rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvPlantBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                backCountNumber =3;
                rlvDashbordDeviceViewID.setVisibility(View.VISIBLE);
                rlvDashbordGraphViewID.setVisibility(View.GONE);
                rlvDashbordViewID.setVisibility(View.GONE);

                imgPlantIconID.setImageResource(R.drawable.ic_home_ds_unselected);
                txtPlantHeadID.setTextColor(getColor(R.color.white));

                imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_selected);
               // txtUserHeadID.setTextColor(R.color.chocolate);
                txtUserHeadID.setTextColor(getColor(R.color.colorBuleDark));

                imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                txtMessageHeadID.setTextColor(getColor(R.color.white));

                imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                txtMapHeadID.setTextColor(getColor(R.color.white));

                if(arraylist.size() > 0)
                    arraylist.clear();

              //  new Worker().execute();

                callHomeDeviceListAPI();

            }
        });

        rlvPlantBottomViewID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                rlvPlantBottomViewID.setBackgroundColor(Color.WHITE);
                rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvMapBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvUserBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                backCountNumber = 1;

                rlvDashbordDeviceViewID.setVisibility(View.GONE);
                rlvDashbordGraphViewID.setVisibility(View.GONE);
                rlvDashbordViewID.setVisibility(View.VISIBLE);

                imgPlantIconID.setImageResource(R.drawable.ic_home_ds_selected);
               // txtPlantHeadID.setTextColor(R.color.chocolate);
                txtPlantHeadID.setTextColor(getColor(R.color.colorBuleDark));

                imgMapIconID.setImageResource(R.drawable.ic_map_ds_unselected);
                txtMapHeadID.setTextColor(getColor(R.color.white));

                imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_unselected);
                txtUserHeadID.setTextColor(getColor(R.color.white));

                imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                txtMessageHeadID.setTextColor(getColor(R.color.white));

            }
        });

        rlvMapBottomViewID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                rlvMapBottomViewID.setBackgroundColor(Color.WHITE);
                rlvChatBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvPlantBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);
                rlvUserBottomViewID.setBackgroundResource(R.drawable.btn_grediant_drawable);

                backCountNumber = 4;

                rlvDashbordDeviceViewID.setVisibility(View.GONE);
                rlvDashbordGraphViewID.setVisibility(View.GONE);
                rlvDashbordViewID.setVisibility(View.GONE);
                rlvDashbordMapViewID.setVisibility(View.VISIBLE);

                imgMapIconID.setImageResource(R.drawable.ic_map_ds_selected);
                txtMapHeadID.setTextColor(getColor(R.color.colorBuleDark));


                imgPlantIconID.setImageResource(R.drawable.ic_home_ds_unselected);
               // txtPlantHeadID.setTextColor(R.color.chocolate);
                txtPlantHeadID.setTextColor(getColor(R.color.white));

                imgUserIconID.setImageResource(R.drawable.ic_inverter_ds_unselected);
                txtUserHeadID.setTextColor(getColor(R.color.white));

                imgMessageIconID.setImageResource(R.drawable.ic_graph_ds_unselected);
                txtMessageHeadID.setTextColor(getColor(R.color.white));

            }
        });

        previous_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SupClientId != first_level) {
                    //   Log.d("user",""+SupClientId+"---"+first_level);
                    try {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setMessage("Loading..."); // Setting Message
                        progressDialog.setTitle("Please wait..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    clientid = SupParentId;
                 //   new Worker().execute();

                } else {
                    previous_level.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    private void showDatePickerDialog() {
//Vihu

        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    ActivityKLPGridDashbord.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    ActivityKLPGridDashbord.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        dpd.setAccentColor(Color.parseColor("#034f84"));
        dpd.setTitle("DatePicker Title");
        dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.VERTICAL);

      /*  dpd.setOnCancelListener(dialog -> {
            Log.d("DatePickerDialog", "Dialog was cancelled");
            dpd = null;
        });*/
        //dpd.show(requireFragmentManager(), "Datepickerdialog");
        //dpd.getShowsDialog();
        dpd.show(getFragmentManager(), "DatePickerDialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(backCountNumber == 1)
        {
            finish();
        }
        else if(backCountNumber == 2)
        {
            backCountNumber = 1;
            rlvDashbordDeviceViewID.setVisibility(View.GONE);
            rlvDashbordGraphViewID.setVisibility(View.GONE);
            rlvDashbordViewID.setVisibility(View.VISIBLE);

            imgPlantIconID.setImageResource(R.drawable.ic_house_selected);
            // txtPlantHeadID.setTextColor(R.color.chocolate);
            txtPlantHeadID.setTextColor(getColor(R.color.chocolate));

            imgUserIconID.setImageResource(R.drawable.ic_user_unselected);
            txtUserHeadID.setTextColor(getColor(R.color.gray_trans));

            imgMessageIconID.setImageResource(R.drawable.ic_chat_unselected);
            txtMessageHeadID.setTextColor(getColor(R.color.gray_trans));
        }
        else if(backCountNumber == 3)
        {
            backCountNumber = 1;
            rlvDashbordDeviceViewID.setVisibility(View.GONE);
            rlvDashbordGraphViewID.setVisibility(View.GONE);
            rlvDashbordViewID.setVisibility(View.VISIBLE);

            imgPlantIconID.setImageResource(R.drawable.ic_house_selected);
            // txtPlantHeadID.setTextColor(R.color.chocolate);
            txtPlantHeadID.setTextColor(getColor(R.color.chocolate));

            imgUserIconID.setImageResource(R.drawable.ic_user_unselected);
            txtUserHeadID.setTextColor(getColor(R.color.gray_trans));

            imgMessageIconID.setImageResource(R.drawable.ic_chat_unselected);
            txtMessageHeadID.setTextColor(getColor(R.color.gray_trans));

        }
    }

    private void changeButtonVisibilityRLV(boolean state, float alphaRate, RelativeLayout txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
        //  hideBTN();
    }

    private void callGetPlantListCheckAPI() {

        baseRequest.showLoader();

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here
                        KlpDashboardModelView mKlpDashboardModelView = gson.fromJson(Json, KlpDashboardModelView.class);
                        getPlantListStatusCheck(mKlpDashboardModelView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    baseRequest.hideLoader();
                   // new HomeFragment.Worker().execute();
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    baseRequest.hideLoader();
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
               // jsonObject.addProperty("id", mUserID);
              //  jsonObject.addProperty("plantid", "1");
               jsonObject.addProperty("plantid", mPlantID);
                System.out.println("jsonObject_GRAPH==>>"+mPlantID);
            //    jsonObject.addProperty("plantid", "1");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.KLP_GRIDTIE_DASHBOARD_API);/////

        }
        else
        {
            baseRequest.hideLoader();
        }
    }

    private void getPlantListStatusCheck(KlpDashboardModelView mKlpDashboardModelView) {

        if (mKlpDashboardModelView.getStatus()) {

          /*  if(mGetPlantResponse != null && mGetPlantResponse.size() > 0)
                mGetPlantResponse.clear();*/

            mKlpgridDashboardResponse = mKlpDashboardModelView.getResponse();//setDataAdapter();

            txtTotalErrorValueID.setText(mKlpgridDashboardResponse.getFault());
            System.out.println("offline===>>>"+ mKlpgridDashboardResponse.getOfflinedevice());

            txtOflinePlantsValueID.setText(mKlpgridDashboardResponse.getOfflinedevice());

           /* if(mKlpgridDashboardResponse.getOfflinedevice().equalsIgnoreCase("null"))
            {
                txtOflinePlantsValueID.setText("0");
            }
            else
            {
                txtOflinePlantsValueID.setText(mKlpgridDashboardResponse.getOfflinedevice());
            }*/
          /*  if(mKlpgridDashboardResponse.getOfflinedevice().equalsIgnoreCase(null))
            {
                txtOflinePlantsValueID.setText("0");

            }
            else
            {
                txtOflinePlantsValueID.setText(mKlpgridDashboardResponse.getOfflinedevice());
            }*/

            txtTotalPlantsValueID.setText(mKlpgridDashboardResponse.getDevicecount());

            txtTodayHourValueIDB.setText(mKlpgridDashboardResponse.getTodaygschrs());

            txtTotalHourValueIDB.setText(mKlpgridDashboardResponse.getTotalgschrs());

            txtTodayEnergyValueID.setText(mKlpgridDashboardResponse.getTodaytotalgscenergy());

            txtTotalEnergyValueID.setText(mKlpgridDashboardResponse.getTgridpower());

            txtTodayPowerValueIDB.setText(mKlpgridDashboardResponse.getPowerfeeding());

            baseRequest.hideLoader();

        }
        else
        {Toast.makeText(mContext, mKlpDashboardModelView.getMessage(), Toast.LENGTH_LONG).show();

            baseRequest.hideLoader();
        }

        callGetPlantKLPMonthEnergyAPI();
    }

    private void callGetPlantKLPGraphMonthAPI(String montNumber) {

        baseRequest.showLoader();

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here
                        KLPGRFMothtModel mKLPGRFMothtModel = gson.fromJson(Json, KLPGRFMothtModel.class);
                        getPlantKLPGraphMonthResponse(mKLPGRFMothtModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    baseRequest.hideLoader();
                   // new HomeFragment.Worker().execute();
                    chartGridEnergyID.clear();
                   // chartGridEnergyID1.clear();

                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    baseRequest.hideLoader();
                    chartGridEnergyID.clear();
                   // chartGridEnergyID1.clear();
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
               // jsonObject.addProperty("id", mUserID);
              //  jsonObject.addProperty("plantid", "1");
               jsonObject.addProperty("plantid", mPlantID);
               jsonObject.addProperty("datatable", montNumber);
            //    jsonObject.addProperty("plantid", "1");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.KLP_GRIDTIE_MOTHS_GRAPH_API);/////

        }
        else
        {
            chartGridEnergyID.clear();
           // chartGridEnergyID1.clear();
            baseRequest.hideLoader();
        }
    }

    private void getPlantKLPGraphMonthResponse(KLPGRFMothtModel mKLPGRFMothtModel) {

        if (mKLPGRFMothtModel.getStatus()) {

          /*  if(mGetPlantResponse != null && mGetPlantResponse.size() > 0)
                mGetPlantResponse.clear();*/

            if(mKLPGRFMothtResponse !=null && mKLPGRFMothtResponse.size() > 0)
            {
                mKLPGRFMothtResponse.clear();
            }
            chartGridEnergyID.setVisibility(View.VISIBLE);
            chartGridEnergyID.getAxisRight().setEnabled(false);
            txttodayDateGraphID.setVisibility(View.GONE);

            chartGridEnergyID1.setVisibility(View.GONE);
            mKLPGRFMothtResponse = mKLPGRFMothtModel.getResponse();//setDataAdapter();

          //  data = new LineData(getXAxisValues(), getDataSet());
            data = new BarData(getXAxisValuesBar(), getDataSetBar());

            // chart.setData(data);
            chartGridEnergyID.setData(data);

            chartGridEnergyID.setDescription("Energy Graph");
            chartGridEnergyID.animateXY(2000, 2000);
            chartGridEnergyID.invalidate();


            baseRequest.hideLoader();

        }
        else
        {
            Toast.makeText(mContext, mKLPGRFMothtModel.getMessage(), Toast.LENGTH_LONG).show();

            baseRequest.hideLoader();
        }
    }


    private ArrayList<BarDataSet> getDataSetBar() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < mKLPGRFMothtResponse.size(); i++) {

            try {
                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                  BarEntry v1e1 = new BarEntry(mKLPGRFMothtResponse.get(i).getEnergy(), i); // Jan
              //  Entry v1e1 = new Entry(mKLPGRFMothtResponse.get(i).getEnergy(), i); // Jan
                valueSet1.add( v1e1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        //  barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);

        return dataSets;
    }
    private ArrayList<String> getXAxisValuesBar() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < mKLPGRFMothtResponse.size(); i++) {

            try {
                xAxis.add(mKLPGRFMothtResponse.get(i).getEdate());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }

    private ArrayList<LineDataSet> getDataSetLine() {
        ArrayList<LineDataSet> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();


        for (int i = 0; i < mKLPGRFMothtResponse.size(); i++) {

            try {
                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                //  BarEntry v1e1 = new BarEntry(mKLPGridGraphModelResponse.get(i).getTotalGridEnergy(), i); // Jan
                Entry v1e1 = new Entry(mKLPGRFMothtResponse.get(i).getEnergy(), i); // Jan
                valueSet1.add(v1e1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        LineDataSet barDataSet1 = new LineDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setFillColor(Color.rgb(133, 155, 143));

        // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);


        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < mKLPGRFMothtResponse.size(); i++) {

            try {
                xAxis.add(mKLPGRFMothtResponse.get(i).getEdate());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }

    private ArrayList<String> getXAxisValuesLine() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < mKLPGRFMothtResponse.size(); i++) {

            try {
                xAxis.add(mKLPGRFMothtResponse.get(i).getEdate());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }

    private void callGetPlantKLPGraphDayAPI(String mTableName, String mDayDate) {

        baseRequest.showLoader();

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here
                        KLPGRFDayModel mKLPGRFDayModel = gson.fromJson(Json, KLPGRFDayModel.class);
                        getPlantKLPGraphDayResponse(mKLPGRFDayModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    baseRequest.hideLoader();
                    // new HomeFragment.Worker().execute();
                    chartGridEnergyID.clear();
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    baseRequest.hideLoader();
                    chartGridEnergyID.clear();
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                // jsonObject.addProperty("id", mUserID);
                //  jsonObject.addProperty("plantid", "1");
                jsonObject.addProperty("plantid", mPlantID);
                jsonObject.addProperty("datatable", mTableName);
               // jsonObject.addProperty("date", mDayDate);
                //    jsonObject.addProperty("plantid", "1");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.KLP_GRIDTIE_DAY_GRAPH_API);/////

        }
        else
        {
            chartGridEnergyID.clear();
            baseRequest.hideLoader();
        }
    }

    private void getPlantKLPGraphDayResponse(KLPGRFDayModel mKLPGRFDayModel) {

        if (mKLPGRFDayModel.getStatus()) {

          /*  if(mGetPlantResponse != null && mGetPlantResponse.size() > 0)
                mGetPlantResponse.clear();*/

            if(mKLPGRFDayResponse !=null && mKLPGRFDayResponse.size() > 0)
            {
                mKLPGRFDayResponse.clear();
            }

            chartGridEnergyID.setVisibility(View.GONE);
            chartGridEnergyID1.setVisibility(View.VISIBLE);
            txttodayDateGraphID.setVisibility(View.VISIBLE);
            mKLPGRFDayResponse = mKLPGRFDayModel.getResponse();//setDataAdapter();
  /*          dataLine = new LineData(getXAxisValuesDay(), getDataSetDay());
           // data = new BarData(getXAxisValuesDay(), getDataSetDay());
           // data = new BarData(getXAxisValuesBarDay(), getDataSetBarDay());
            // chart.setData(data);
            chartGridEnergyID1.setData(dataLine);
            chartGridEnergyID1.setDescription("Energy Graph");
            chartGridEnergyID1.animateXY(2000, 2000);
            chartGridEnergyID1.invalidate();
            baseRequest.hideLoader();*/
            List yAxisValues = new ArrayList();
            List axisValues = new ArrayList();

           // Line line = new Line(yAxisValues);
            Line line = new Line(yAxisValues).setColor(Color.parseColor("#034f84"));

          //  for(int i = 0; i < axisData.length; i++)
            for(int i = 0; i < mKLPGRFDayResponse.size(); i++)
            {
                axisValues.add(i, new AxisValue(i).setLabel(mKLPGRFDayResponse.get(i).getHrs()));
            }

            //for (int i = 0; i < yAxisData.length; i++)
            for (int i = 0; i < mKLPGRFDayResponse.size(); i++)
            {
                //yAxisValues.add(new PointValue(i, yAxisData[i]));
                yAxisValues.add(new PointValue(i, mKLPGRFDayResponse.get(i).getEnergy()));
            }


            List lines = new ArrayList();
            lines.add(line);
            LineChartData data = new LineChartData();
            data.setLines(lines);


            Axis axis = new Axis();
            axis.setValues(axisValues);
            axis.setTextSize(12);
            axis.setTextColor(Color.parseColor("#034f84"));
            data.setAxisXBottom(axis);

            Axis yAxis = new Axis();
            yAxis.setTextColor(Color.parseColor("#034f84"));
            yAxis.setTextSize(12);
            data.setAxisYLeft(yAxis);

            chartGridEnergyID1.setLineChartData(data);
            chartGridEnergyID1.invalidate();
            baseRequest.hideLoader();

        }
        else
        {
            Toast.makeText(mContext, mKLPGRFDayModel.getMessage(), Toast.LENGTH_LONG).show();
            baseRequest.hideLoader();
        }
    }

    private void callGetPlantKLPMonthEnergyAPI() {

        baseRequest.showLoader();

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here
                        KLPOnlyMonthEnergyModel mKLPOnlyMonthEnergyModel = gson.fromJson(Json, KLPOnlyMonthEnergyModel.class);
                        getPlantKLPMonthEnergyResponse(mKLPOnlyMonthEnergyModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    baseRequest.hideLoader();
                    // new HomeFragment.Worker().execute();
                    chartGridEnergyID.clear();
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    baseRequest.hideLoader();
                    chartGridEnergyID.clear();
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                // jsonObject.addProperty("id", mUserID);
                //  jsonObject.addProperty("plantid", "1");
                jsonObject.addProperty("plantid", mPlantID);
                //    jsonObject.addProperty("plantid", "1");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.KLP_GRIDTIE_MONTH_ENERGY_API);/////

        }
        else
        {
            chartGridEnergyID.clear();
            baseRequest.hideLoader();
        }
    }

    private void getPlantKLPMonthEnergyResponse(KLPOnlyMonthEnergyModel mKLPOnlyMonthEnergyModel) {

        if (mKLPOnlyMonthEnergyModel.getStatus()) {

          /*  if(mGetPlantResponse != null && mGetPlantResponse.size() > 0)
                mGetPlantResponse.clear();*/



            mKLPOnlyMonthEnergyResponse = mKLPOnlyMonthEnergyModel.getResponse();//setDataAdapter();


            txtMontlyEnergyValueID.setText(mKLPOnlyMonthEnergyResponse.getTotalMonthEnergy());

            txtTotalEnergyValueKLPID.setText(mKLPOnlyMonthEnergyResponse.getTotalMonthEnergy());

            baseRequest.hideLoader();

        }
        else
        {
            Toast.makeText(mContext, mKLPOnlyMonthEnergyModel.getMessage(), Toast.LENGTH_LONG).show();

            baseRequest.hideLoader();
        }
    }

    private ArrayList<LineDataSet> getDataSetDay() {
        ArrayList<LineDataSet> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < mKLPGRFDayResponse.size(); i++) {
            try {
                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                //  BarEntry v1e1 = new BarEntry(mKLPGridGraphModelResponse.get(i).getTotalGridEnergy(), i); // Jan
                Entry v1e1 = new Entry(mKLPGRFDayResponse.get(i).getEnergy(), i); // Jan
                valueSet1.add(v1e1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        LineDataSet barDataSet1 = new LineDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setFillColor(Color.rgb(133, 155, 143));
        // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<String> getXAxisValuesDay() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < mKLPGRFDayResponse.size(); i++) {

            try {
                xAxis.add(mKLPGRFDayResponse.get(i).getHrs());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }

    private ArrayList<BarDataSet> getDataSetBarDay() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < mKLPGRFDayResponse.size(); i++) {

            try {
                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                BarEntry v1e1 = new BarEntry(mKLPGRFDayResponse.get(i).getEnergy(), i); // Jan
                //  Entry v1e1 = new Entry(mKLPGRFMothtResponse.get(i).getEnergy(), i); // Jan
                valueSet1.add( v1e1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        //  barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<String> getXAxisValuesBarDay() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < mKLPGRFDayResponse.size(); i++) {

            try {
                xAxis.add(mKLPGRFDayResponse.get(i).getHrs());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
///Vikas date vihu
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int numDigitsDay = String.valueOf(dayOfMonth).length();
        int numDigitsMonth = String.valueOf(monthOfYear).length();
     //   String substring = str.substring(Math.max(str.length() - 2, 0));
        ++monthOfYear;
        String mDayV = "";
        String mMonthV = "";
        if(numDigitsDay == 1)
        {
            mDayV = "0"+ dayOfMonth;
        }
        else
        {
            mDayV =  ""+dayOfMonth;
        }

        if(numDigitsMonth == 1)
        {
            mMonthV = "0"+ monthOfYear;
        }
        else
        {
            mMonthV =  ""+ monthOfYear;
        }
       // String date = "You picked the following date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        String date = "You picked the following date: "+mDayV+"/"+mMonthV+"/"+year;
        System.out.println("date==>>"+date);
        System.out.println("mMonthV==>>"+mMonthV+""+year);

        String oldstring = mMonthV+"-"+mDayV+"-"+year;
        String newstring;
        String str = String.valueOf(year);

        String lstchar = ""+str.charAt(str.length() - 1);
        String lndchar = ""+str.charAt(str.length() - 2);

        System.out.println("splitValue===>>"+lstchar+""+lndchar);
           /* Date dateold = new SimpleDateFormat("yyyy-MM-DD ").parse(oldstring);


             newstring = new SimpleDateFormat("yy-MM-DD").format(dateold);
             String [] splitValue =newstring.split("-");
            System.out.println("splitValue===>>"+splitValue[0]); // 2011-01-18*/


        if(mDayMonthCheck == 1)
        {
            String sstst = mMonthV+""+lndchar+""+lstchar;
            callGetPlantKLPGraphDayAPI(sstst, oldstring);
        }
        else if (mDayMonthCheck == 2)
        {
           // String sstst = mMonthV+""+lstchar+""+lndchar;
            String sstst = mMonthV+""+lndchar+""+lstchar;
            callGetPlantKLPGraphMonthAPI(sstst);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
      /*  progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);*/
        ///Vihuuu
    }

    private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;

            try {
                get_device();

                runOnUiThread(
                        new Runnable() {

                            @Override
                            public void run() {
                                baseRequest.showLoader();
                                try {

                                    progressDialog = new ProgressDialog(mContext);
                                    progressDialog.setMessage("Loading..."); // Setting Message
                                    progressDialog.setTitle("Please wait..."); // Setting Title
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                    progressDialog.show(); // Display Progress Dialog
                                    progressDialog.setCancelable(false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                adapter = new GRIDDeviceViewAdapter(mContext, arraylist);
                                list.setAdapter(adapter);

                                // Listview on child click listener
                                orgListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v,
                                                                int groupPosition, int childPosition, long id) {
                                        // TODO Auto-generated method stub

                                        clientid = Integer.parseInt(clientArray.get(childPosition).getClientId());
                                        clientName = clientArray.get(childPosition).getClientName();
                                        //set selected client id for org , show on map
                                        editor.putString("key_clientid_for_map", String.valueOf(clientid));
                                        editor.commit();
                                        if (SupClientId == first_level) {
                                            previous_level.setVisibility(View.INVISIBLE);
                                        } else {
                                            previous_level.setVisibility(View.VISIBLE);
                                        }
                                        try {
                                            progressDialog = new ProgressDialog(mContext);
                                            progressDialog.setMessage("Loading..."); // Setting Message
                                            progressDialog.setTitle("Please wait..."); // Setting Title
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                            progressDialog.show(); // Display Progress Dialog
                                            progressDialog.setCancelable(false);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                       // new HomeFragment.Worker().execute();
                                        if (SupClientId == first_level) {
                                            previous_level.setVisibility(View.INVISIBLE);
                                        } else {
                                            previous_level.setVisibility(View.VISIBLE);
                                        }
                                        return false;
                                    }
                                });

                                //********************************* end app version *********************************
                                progressDialog.dismiss();
                            }
                        });

            } catch (Exception e) {
                try {
                    progressDialog.dismiss();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
                baseRequest.hideLoader();
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void get_device() {

        String url = "null";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();


        if (CustomUtility.isOnline(mContext)) {
            try {
//********************************** check app version ********************************************
                //********************************** check logout session ********************************************
                param.clear();
                param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));

                String logout_status = CustomHttpClient.executeHttpPost1(NewSolarVFD.DELETE_DEVICE, param);

                JSONObject jo_logout = new JSONObject(logout_status);

                String isActive = (jo_logout.has("Active") ? jo_logout.getString("Active") : "true");

//************************************ get device details ******************************************
                if (clientid == 0) {
                    // single user login
                    url = NewSolarVFD.GET_DEVICE + "?MUserId=" + pref.getString("key_muserid", "invalid_muserid") +
                            "&isActive=" + 1 +"&PlantId="+mPlantID;
                } else {
                    // organisation login
                    url = NewSolarVFD.ORG_GET_DEVICE + "?MUserId=" + pref.getString("key_muserid", "invalid_muserid") +
                            "&ClientId=" + clientid;
                }

                String obj = CustomHttpClient.executeHttpGet(url);

                Log.d("home_url", "" + url);

                Log.d("home_obj", "" + obj);

                if (obj != null) {
                   runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // Stuff that updates the UI
                            try {
                                progressDialog.dismiss();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    if (clientid == 0) // single user  data
                    {
                        JSONArray ja = new JSONArray(obj);
                        boolean mCheckFirstDB;

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jo = ja.getJSONObject(i);
                            customer_gps = new Customer_GPS_Search();
                            customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType"));
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(mUserID);
                            customer_gps.setMobile(jo.has("Mobile") ? jo.getString("Mobile") : "");
                            customer_gps.setMobValidationDate(jo.has("MobValidationDate") ? jo.getString("MobValidationDate") : "");
                            customer_gps.setTypeName(jo.has("TypeName")? jo.getString("TypeName") : "");
                            customer_gps.setPumpStatus(jo.getString("PumpStatus"));
                            customer_gps.setIsLogin(jo.getString("IsLogin"));
                            //customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                            customer_gps.setDeviceStatus(jo.has("DeviceStatus") ? jo.getInt("DeviceStatus") : 0);
                            customer_gps.setDeviceImage(jo.has("DeviceImage") ? jo.getString("DeviceImage") : "");
                            //customer_gps.setDeviceImage(jo.getString("DeviceImage") ? jo.getString("DeviceImage") : "");
                            customer_gps.setModelType(jo.has("ModelType") ? jo.getString("ModelType") : "");
                            arraylist.add(customer_gps);

                            if (i == 0) {
                                mCheckFirstDB = true;
                            } else {
                                mCheckFirstDB = false;
                            }

                          //  databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"),jo.getInt("DeviceStatus"), mCheckFirstDB);
                            // databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);

                        }

                    } else   //  organisation data
                    {



                        JSONObject jsonObj = new JSONObject(obj);
                        JSONArray ja = jsonObj.getJSONArray("Client");

                        expand = true; // expand first level

                        arraylist.clear();
                        listDataHeader.clear();
                        topChild.clear();
                        listDataChild.clear();

                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);

                                org_client = new Org_Client();
                                org_client.setClientId(jo.getString("ClientId"));
                                org_client.setClientName(jo.has("ClientName") ? jo.getString("ClientName") : "");
                                org_client.setParentId(jo.getString("ParentId"));
                                org_client.setParentName(jo.getString("ParentId"));

                                clientArray.add(i, org_client);

                                if (i == 0) {
                                    listDataHeader.add(jo.has("ParentName") ? jo.getString("ParentName") : "");
                                    expand = true; // expand first level
                                }
                                topChild.add(jo.has("ClientName") ? jo.getString("ClientName") : "");
                            }
                        }

                        if (ja.length() == 0) {
                            listDataHeader.add(clientName);
                            expand = true; // expand first level
                        }

                        listDataChild.put(listDataHeader.get(0), topChild); // Header, Child data
                        orglistAdapter = new ExpandableListAdapter(mContext, listDataHeader, listDataChild);

                        JSONArray ja_device = jsonObj.getJSONArray("Device");
                        boolean mCheckFirstDB;
                        for (int i = 0; i < ja_device.length(); i++) {

                            JSONObject jo = ja_device.getJSONObject(i);
                            customer_gps = new Customer_GPS_Search();

                            customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType"));
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(mUserID);
                            //customer_gps.setTypeName(jo.getString("TypeName"));
                            customer_gps.setPumpStatus(jo.getString("PumpStatus"));
                            customer_gps.setIsLogin(jo.getString("IsLogin"));
                            //  customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                            customer_gps.setDeviceStatus(jo.has("DeviceStatus") ? jo.getInt("DeviceStatus") : 0);
                            customer_gps.setTypeName(jo.has("TypeName")? jo.getString("TypeName") : "");
                            customer_gps.setDeviceImage(jo.has("DeviceImage") ? jo.getString("DeviceImage") : "");
                            customer_gps.setMobile(jo.has("Mobile") ? jo.getString("Mobile") : "");
                            customer_gps.setMobValidationDate(jo.has("MobValidationDate") ? jo.getString("MobValidationDate") : "");
                            customer_gps.setModelType(jo.has("ModelType") ? jo.getString("ModelType") : "");

                            arraylist.add(customer_gps);

                            if (i == 0) {
                                mCheckFirstDB = true;
                            } else {
                                mCheckFirstDB = false;
                            }

                            //  databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);
                            //databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.has("TypeName")? jo.getString("TypeName") : "", jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), jo.getInt("DeviceStatus"),mCheckFirstDB);
                            //  databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.has("TypeName")? jo.getString("TypeName") : "", jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"),mCheckFirstDB);
                        }



                        JSONArray ja_pclient = jsonObj.getJSONArray("PClient");
                        for (int i = 0; i < ja_pclient.length(); i++) {

                            JSONObject jo = ja_pclient.getJSONObject(i);
                            SupParentId = Integer.parseInt(jo.getString("SupParentId"));
                            SupClientId = Integer.parseInt(jo.getString("ClientId"));
                        }
                    }
                } else {
                    ///vikas changes
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                /*txtAddDeviceMSGID.setText(getResources().getString(R.string.err_no_device_found));
                                txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                                txtAddDeviceMSGID.setVisibility(View.VISIBLE);
                                lvlListViewDataContainerID.setVisibility(View.GONE);*/
                                progressDialog.dismiss();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                            // Stuff that updates the UI
                        }
                    });
                    // CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
              /*  runOnUiThread(new Runnable() {
                    public void run() {
                        arraylist = databaseHelperTeacher.getDeviceListData();
                        // adapter = new CustomerGPSListViewAdapter(context, arraylist);
                        if (clientid > 0) {
                            Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                            adapter = new CustomerGPSListViewAdapter(context, arraylist);
                        }
                        else
                        {
                            if (arraylist.size() == 1) {

                                conditionFunctionNAvigation(arraylist);
                            } else {
                                Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                                adapter = new CustomerGPSListViewAdapter(context, arraylist);
                            }
                        }

                    }
                });*/

            }

        } else {
            progressDialog.dismiss();
         /* runOnUiThread(new Runnable() {
                public void run() {

                    arraylist = databaseHelperTeacher.getDeviceListData();
                    if (arraylist.size() == 1) {

                        conditionFunctionNAvigation(arraylist);
                    } else {
                        Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                        adapter = new CustomerGPSListViewAdapter(context, arraylist);
                    }

                }
            });*/

        }

    }

    private void callHomeDeviceListAPI() {

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        HomeDeviceListModel mHomeDeviceListModel = gson.fromJson(Json, HomeDeviceListModel.class);
                        getHomeDeviceListResponse(mHomeDeviceListModel);

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

           /* JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("DeviceTypeID", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.CHECK_USER_DEVICE_STATUS);/////*/

            Map<String, String> wordsByKey = new HashMap<>();

            wordsByKey.put("MUserId", mUserID);
            wordsByKey.put("isActive", "1");
            wordsByKey.put("PlantId", mPlantID);
            //   wordsByKey.put("IMEI","38648723487236487264");
            baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.HOME_DEVISE_LIST_API);/////

        }
        else
        {
        }
    }

    private void getHomeDeviceListResponse(HomeDeviceListModel mHomeDeviceListModel) {

        if (mHomeDeviceListModel.getStatus()) {

            if(mHomeDeviceListResponse.size() > 0)
                mHomeDeviceListResponse.clear();

            mHomeDeviceListResponse = mHomeDeviceListModel.getResponse();
            boolean mCheckFirstDB;
            for(int i = 0; i < mHomeDeviceListResponse.size(); i++) {

                customer_gps = new Customer_GPS_Search();
                customer_gps.setCustomer_name(mHomeDeviceListResponse.get(i).getCustomerName());
                customer_gps.setDeviceNo(mHomeDeviceListResponse.get(i).getDeviceNo());
                customer_gps.setDeviceType(mHomeDeviceListResponse.get(i).getDeviceType());
                customer_gps.setMDeviceId(mHomeDeviceListResponse.get(i).getMDeviceId());
                customer_gps.setMUserId(mUserID);
                customer_gps.setMobile(mHomeDeviceListResponse.get(i).getMobile());
                customer_gps.setMobValidationDate(mHomeDeviceListResponse.get(i).getMobValidationDate());
                customer_gps.setTypeName(mHomeDeviceListResponse.get(i).getTypeName());
                customer_gps.setPumpStatus(mHomeDeviceListResponse.get(i).getPumpStatus());
                customer_gps.setIsLogin(mHomeDeviceListResponse.get(i).getIsLogin());
                //customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                customer_gps.setDeviceStatus(mHomeDeviceListResponse.get(i).getDeviceStatus());
                customer_gps.setDeviceImage(mHomeDeviceListResponse.get(i).getDeviceImage());
                //customer_gps.setDeviceImage(jo.getString("DeviceImage") ? jo.getString("DeviceImage") : "");
                customer_gps.setModelType(mHomeDeviceListResponse.get(i).getModelType());
                arraylist.add(customer_gps);

                if (i == 0) {
                    mCheckFirstDB = true;
                } else {
                    mCheckFirstDB = false;
                }

                databaseHelperTeacher.insertDeviceListData(mHomeDeviceListResponse.get(i).getCustomerName(), mHomeDeviceListResponse.get(i).getDeviceNo(), mHomeDeviceListResponse.get(i).getDeviceType(),
                        mHomeDeviceListResponse.get(i).getMDeviceId(), mUserID, mHomeDeviceListResponse.get(i).getMobile(), mHomeDeviceListResponse.get(i).getTypeName(), mHomeDeviceListResponse.get(i).getModelType(),
                        mHomeDeviceListResponse.get(i).getPumpStatus(), mHomeDeviceListResponse.get(i).getIsLogin(),mHomeDeviceListResponse.get(i).getDeviceStatus(), mCheckFirstDB);
                // databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);



            }


            adapter = new GRIDDeviceViewAdapter(mContext, arraylist);
            list.setAdapter(adapter);
            baseRequest.hideLoader();


        }
        else
        {


        }
    }

}
