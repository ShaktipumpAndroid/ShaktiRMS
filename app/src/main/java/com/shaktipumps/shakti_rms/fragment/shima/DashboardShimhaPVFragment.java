package com.shaktipumps.shakti_rms.fragment.shima;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.ShimaBean.ShimaResponse;
import com.shaktipumps.shakti_rms.bean.Simha_graph.SimhaGraphModel;
import com.shaktipumps.shakti_rms.bean.Simha_graph.SimhaGraphViewResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class DashboardShimhaPVFragment extends Fragment {


    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;

    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    SharedPreferences pref;

    private TextView txtTotalEnergyUnitID, txtTotalEnergyNameID, txtTotalEnergyValueID;
    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID, txtTodayEnergyUnitID;
    private TextView txtTotalFlawNameID, txtTotalFlawValueID, txtTotalFlawUnitID;

    private RelativeLayout rlvTodayMainViewID, rlvTotalMainViewID;

    private ShimaResponse mShimaResponse;

    ArrayList<BarEntry> bargroup1 = new ArrayList<>();

    //private BarChart chartShimaPvEnergyID;
    private LineChart chartShimaPvEnergyID;
    private BarChart BarChartDemo;

    private TextView txtDateHeadingGraphID, txtEnergyHeadingGraphID;
    BarChart chart;
    List<SimhaGraphViewResponse> mSimhaGraphViewResponse;


    private LinearLayout lrlGraphViewDataID;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;
    private  BarData data;

    public DashboardShimhaPVFragment() {
        // Required empty public constructor

    }

    @SuppressLint("ValidFragment")
  //  public DashboardNandiPVFragment(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, ShimaResponse mShimaResponse, List<SimhaGraphViewResponse> mSimhaGraphViewResponse, TextView txtDateHeadingGraphID, TextView txtEnergyHeadingGraphID) {
    public DashboardShimhaPVFragment(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, ShimaResponse mShimaResponse) {
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
        this.mShimaResponse = mShimaResponse;


    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_shima_pv_dashboard, container, false);
        mActivity = getActivity();

        baseRequest = new BaseRequest(mActivity);
        mSimhaGraphViewResponse = new ArrayList<>();


        initView();
        return mView;
    }

    private void initView() {

        txtDateHeadingGraphID = (TextView) mView.findViewById(R.id.txtDateHeadingGraphID) ;
        txtEnergyHeadingGraphID = (TextView) mView.findViewById(R.id.txtEnergyHeadingGraphID) ;


        txtTotalEnergyValueID = (TextView) mView.findViewById(R.id.txtTotalEnergyValueID);

        txtTotalEnergyNameID = (TextView) mView.findViewById(R.id.txtTotalEnergyNameID);
        txtTotalEnergyUnitID = (TextView) mView.findViewById(R.id.txtTotalEnergyUnitID);

        txtTodayEnergyNameID = (TextView) mView.findViewById(R.id.txtTodayEnergyNameID);
        txtTodayEnergyValueID = (TextView) mView.findViewById(R.id.txtTodayEnergyValueID);
        txtTodayEnergyUnitID = (TextView) mView.findViewById(R.id.txtTodayEnergyUnitID);

        rlvTodayMainViewID = (RelativeLayout) mView.findViewById(R.id.rlvTodayMainViewID);
        rlvTotalMainViewID = (RelativeLayout) mView.findViewById(R.id.rlvTotalMainViewID);

        lrlGraphViewDataID = (LinearLayout) mView.findViewById(R.id.lrlGraphViewDataID);
        rlvMonthViewID = (RelativeLayout) mView.findViewById(R.id.rlvMonthViewID);
        rlvDayViewID = (RelativeLayout) mView.findViewById(R.id.rlvDayViewID);


        //  chartShimaPvEnergyID = (BarChart) mView.findViewById(R.id.chartShimaPvEnergyID) ;
//        chartShimaPvEnergyID = (LineChart) mView.findViewById(R.id.chartShimaPvEnergyID) ;
        // BarChartDemo = (BarChart) mView.findViewById(R.id.chartShimaPvEnergyID) ;



        rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);

                txtDateHeadingGraphID.setText("Hr");
               // txtSetValueHead();
                callEnergyAndGraphAPI("Day");

            }
        });

        rlvMonthViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvMonthViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvDayViewID);

                txtDateHeadingGraphID.setText("DATE");
               // txtSetValueHead();

                callEnergyAndGraphAPI("Month");
                /* data = new BarData(getXAxisValues(), getDataSet());
                chart.setData(data);
                chart.setDescription("Energy Graph");
                chart.animateXY(2000, 2000);
                chart.invalidate();*/
            }
        });



        try {


            txtTotalEnergyValueID.setText(mShimaResponse.getTOTPVEnergyValue());
          //  txtTotalEnergyNameID.setText(mShimaResponse.getTOTPVEnergyName());
            txtTotalEnergyUnitID.setText(mShimaResponse.getTOTPVEnergyUnit());

           // txtTodayEnergyNameID.setText(mShimaResponse.getTDPVEnergyName());
            txtTodayEnergyValueID.setText(mShimaResponse.getTDPVEnergyValue());
            txtTodayEnergyUnitID.setText(mShimaResponse.getTDPVEnergyUnit());


        } catch (Exception e) {
            e.printStackTrace();
        }

        // BarChart chart = new BarChart(mActivity);
        setAllEventClick();

        changeButtonVisibilityRLV(true, 1.0f,rlvDayViewID);
        changeButtonVisibilityRLV(true, 0.5f,rlvMonthViewID);
        callEnergyAndGraphAPI("Day");



        chart = (BarChart) mView.findViewById(R.id.chartShimaPvEnergyID);
        /* data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Energy Graph");
        chart.animateXY(2000, 2000);
        chart.invalidate();*/
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < mSimhaGraphViewResponse.size(); i++) {

            try {

                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                 BarEntry v1e1 = new BarEntry(mSimhaGraphViewResponse.get(i).getEnergy(), i); // Jan
               // BarEntry v1e1 = new BarEntry(mSimhaGraphViewResponse.get(i).getFlow(), i); // Jan
                valueSet1.add(v1e1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

      //  BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
         barDataSet1.setColor(Color.rgb(0, 155, 0));
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);


        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();


        for (int i = 0; i < mSimhaGraphViewResponse.size(); i++) {

            try {
                xAxis.add(mSimhaGraphViewResponse.get(i).getMDate());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }



    private void changeButtonVisibilityRLV(boolean state, float alphaRate, RelativeLayout txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
        //  hideBTN();
    }

    private void hideBTN() {


        rlvMonthViewID.setVisibility(View.GONE);
        rlvDayViewID.setVisibility(View.GONE);
        lrlGraphViewDataID.setVisibility(View.GONE);
    }

    private void showBTN() {


        rlvMonthViewID.setVisibility(View.VISIBLE);
        rlvDayViewID.setVisibility(View.VISIBLE);
        lrlGraphViewDataID.setVisibility(View.VISIBLE);
    }

    private void callEnergyAndGraphAPI(String typeSelect) {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    //TotalEndAndConsuptionsModelView mTotalEndAndConsuptionsModelView = gson.fromJson(Json, TotalEndAndConsuptionsModelView.class);
                    //  getTotalEndAndConsuptionsResponse(mTotalEndAndConsuptionsModelView);

                    SimhaGraphModel mSimhaGraphModel = gson.fromJson(Json, SimhaGraphModel.class);
                    //  getTotalEndAndConsuptionsResponse(mShimaModel);
                    getGraphAPIResponse(mSimhaGraphModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

chart.clear();
                if(mSimhaGraphViewResponse.size() > 0)
                {
                    mSimhaGraphViewResponse.clear();
                }

                //Toast.makeText(mActivity, "Vikas graph"+message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {

                Toast.makeText(mActivity, getString(R.string.st_Pleasecheckinternetconnection), Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            //  jsonObject.addProperty("MUserId", vMUserId);
            jsonObject.addProperty("graphType", typeSelect);

            // jsonObject.addProperty("DeviceType", vDeviceType);///Model type value
            jsonObject.addProperty("deviceType", DeviceType);
            jsonObject.addProperty("deviceNo", DeviceNo);
            //   jsonObject.addProperty("DeviceNo", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////

        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_SIMHA_GRAPH_API);/////
    }

    private void getGraphAPIResponse(SimhaGraphModel mSimhaGraphModel) {

        if (mSimhaGraphModel.getStatus())
        {
            if(mSimhaGraphViewResponse != null )
                mSimhaGraphViewResponse.clear();

            if(mSimhaGraphViewResponse.size() > 0)
                mSimhaGraphViewResponse.clear();

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mSimhaGraphViewResponse = mSimhaGraphModel.getResponse();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();

            ArrayList<BarDataSet> dataSetPERA = getDataSet();
            ArrayList<String> xxAxix = getXAxisValues();

            data = new BarData(xxAxix, dataSetPERA);
           // data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            chart.setDescription("Flow Graph");
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }


    }


    private void getEntries() {
        lineEntries = new ArrayList<>();

        for (int i = 0; i < mSimhaGraphViewResponse.size() ; i++) {

            try {
                String spsp = "05.2020";
                int jj = 1+i;
                //lineEntries.add(new Entry((float)i, mSimha ]GraphViewResponse.get(i).getEnergy()));
                lineEntries.add(new Entry(mSimhaGraphViewResponse.get(i).getEnergy(), Integer.parseInt(mSimhaGraphViewResponse.get(i).getMDate())));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /*lineEntries.add(new Entry(0f, 0));
        lineEntries.add(new Entry(6f, 1));
        lineEntries.add(new Entry(3f, 1));
        lineEntries.add(new Entry(8f, 3));
        lineEntries.add(new Entry(5f, 4));
        lineEntries.add(new Entry(7f, 3));*/
    }

    private void setAllEventClick() {


        rlvTodayMainViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlvTotalMainViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

}
