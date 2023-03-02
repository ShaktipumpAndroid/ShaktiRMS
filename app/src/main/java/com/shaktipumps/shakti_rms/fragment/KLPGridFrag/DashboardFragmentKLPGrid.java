package com.shaktipumps.shakti_rms.fragment.KLPGridFrag;

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
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.KLPGrid.KLPGridGraphModelResponse;
import com.shaktipumps.shakti_rms.bean.KLPGrid.KLPGridGraphModelView;
import com.shaktipumps.shakti_rms.bean.KLPGrid.KLPGridModelResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentKLPGrid extends Fragment {
    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;

    private KLPGridModelResponse mKLPGridModelResponse;
    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    ArrayList<BarEntry> bargroup1 = new ArrayList<>();
    SharedPreferences pref;
   // private BarChart chartGridEnergyID;

    private TextView txtTotalGridEnergyNameID, txtTotalGridEnergyVlaueID, txtTotalGridUnitNameID;

    private TextView txtTotalGridRuningUnitNameID, txtTotalGridRuningValueID, txtTotalGridRuningNameID;
    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID, txtTodayEnergyUnitID;
    private TextView txtTodayRuningHourNameID, txtTodayRuningHourValueID, txtTodayRunningHourUnitID;

    private RelativeLayout rvlTodayGridRunninghourID, rvlTodayGridEnrgyID;
    private RelativeLayout rvlTotalGridEnrgyID, rvlTotalGridRunninghourID;

    //BarChart chart;
   LineChart chart;

    List<KLPGridGraphModelResponse> mKLPGridGraphModelResponse;

    private LinearLayout lrlGraphViewDataID;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;
    //private BarData data;
    private LineData data;

    private TextView txtDateHeadingGraphID;
    private TextView txtTotalEnergyValueID;
    private TextView txtMonthlyGridEnergyNameID;
    private TextView txtMonthlyGridEnergyVlaueID;
    private TextView txtMonthlyGridUnitNameID;

    public DashboardFragmentKLPGrid() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public DashboardFragmentKLPGrid(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, KLPGridModelResponse mKLPGridModelResponse) {
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
        this.mKLPGridModelResponse = mKLPGridModelResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // mView = inflater.inflate(R.layout.fragment_dashboard_gridtie_seventeen, container, false);
        mView = inflater.inflate(R.layout.fragment_dashboard_gridtie_klp, container, false);
        mActivity = getActivity();
        baseRequest = new BaseRequest(mActivity);
        mKLPGridGraphModelResponse = new ArrayList<>();
        initView();
        return mView;
    }

    private void initView() {

        rvlTodayGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridRunninghourID);
        rvlTodayGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridEnrgyID);
        rvlTotalGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTotalGridRunninghourID);
        rvlTotalGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTotalGridEnrgyID);

        txtDateHeadingGraphID = (TextView) mView.findViewById(R.id.txtDateHeadingGraphID);
        txtTotalEnergyValueID = (TextView) mView.findViewById(R.id.txtTotalEnergyValueID);

        txtMonthlyGridEnergyNameID = (TextView) mView.findViewById(R.id.txtMonthlyGridEnergyNameID);
        txtMonthlyGridEnergyVlaueID = (TextView) mView.findViewById(R.id.txtMonthlyGridEnergyVlaueID);
        txtMonthlyGridUnitNameID = (TextView) mView.findViewById(R.id.txtMonthlyGridUnitNameID);

        txtTotalGridRuningNameID = (TextView) mView.findViewById(R.id.txtTotalGridRuningNameID);
        txtTotalGridRuningValueID = (TextView) mView.findViewById(R.id.txtTotalGridRuningValueID);
        txtTotalGridRuningUnitNameID = (TextView) mView.findViewById(R.id.txtTotalGridRuningUnitNameID);

        txtTotalGridEnergyNameID = (TextView) mView.findViewById(R.id.txtTotalGridEnergyNameID);
        txtTotalGridEnergyVlaueID = (TextView) mView.findViewById(R.id.txtTotalGridEnergyVlaueID);
        txtTotalGridUnitNameID = (TextView) mView.findViewById(R.id.txtTotalGridUnitNameID);

        txtTodayEnergyNameID = (TextView) mView.findViewById(R.id.txtTodayEnergyNameID);
        txtTodayEnergyValueID = (TextView) mView.findViewById(R.id.txtTodayEnergyValueID);
        txtTodayEnergyUnitID = (TextView) mView.findViewById(R.id.txtTodayEnergyUnitID);

        txtTodayRuningHourNameID = (TextView) mView.findViewById(R.id.txtTodayRuningHourNameID);
        txtTodayRuningHourValueID = (TextView) mView.findViewById(R.id.txtTodayRuningHourValueID);
        txtTodayRunningHourUnitID = (TextView) mView.findViewById(R.id.txtTodayRunningHourUnitID);

        lrlGraphViewDataID = (LinearLayout) mView.findViewById(R.id.lrlGraphViewDataID);
        rlvMonthViewID = (RelativeLayout) mView.findViewById(R.id.rlvMonthViewID);
        rlvDayViewID = (RelativeLayout) mView.findViewById(R.id.rlvDayViewID);

        //chartMotorEnergyID = (BarChart) mView.findViewById(R.id.chartMotorEnergyID) ;
        //chart = (BarChart) mView.findViewById(R.id.chartGridEnergyID);
        chart = (LineChart) mView.findViewById(R.id.chartGridEnergyID);

        try {
            //if(mOldKLPResponse.getTOTGRHName() != "NULL")
            // txtTotalGridRuningNameID.setText(mKLPGridModelResponse.getTOTGRHName());
            // this is only for  new changes
            txtTotalEnergyValueID.setText(mKLPGridModelResponse.getTOTGEnergyValue());

            txtMonthlyGridEnergyNameID.setText(mKLPGridModelResponse.getTOTMGEnergyName());
            txtMonthlyGridEnergyVlaueID.setText(mKLPGridModelResponse.getTOTMGEnergyValue());
            txtMonthlyGridUnitNameID.setText(mKLPGridModelResponse.getTOTMGEnergyUnit());

            //end or new changes
            txtTotalGridRuningValueID.setText(mKLPGridModelResponse.getTOTGRHValue());
            txtTotalGridRuningUnitNameID.setText(mKLPGridModelResponse.getTOTGRHUnit());

            //   txtTotalGridEnergyNameID.setText(mKLPGridModelResponse.getTOTGEnergyName());
            txtTotalGridEnergyVlaueID.setText(mKLPGridModelResponse.getTOTGEnergyValue());
            txtTotalGridUnitNameID.setText(mKLPGridModelResponse.getTOTGEnergyUnit());

            // txtTodayEnergyNameID.setText(mKLPGridModelResponse.getTDGEnergyName());
            txtTodayEnergyValueID.setText(mKLPGridModelResponse.getTDGEnergyValue());
            txtTodayEnergyUnitID.setText(mKLPGridModelResponse.getTDGEnergyUnit());

            //  txtTodayRuningHourNameID.setText(mKLPGridModelResponse.getTDGRHName());
            txtTodayRuningHourValueID.setText(mKLPGridModelResponse.getTDGRHValue());
            txtTodayRunningHourUnitID.setText(mKLPGridModelResponse.getTDGRHUnit());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //  chartGridEnergyID = (BarChart) mView.findViewById(R.id.chartGridEnergyID);

        setAllEventClick();
        rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);

                txtDateHeadingGraphID.setText(getString(R.string.st_HR));
                // txtSetValueHead();
                callEnergyAndGraphAPI("Day");

            }
        });

        rlvMonthViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeButtonVisibilityRLV(true, 1.0f, rlvMonthViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvDayViewID);

                txtDateHeadingGraphID.setText(getString(R.string.st_DATE));
                // txtSetValueHead();
                callEnergyAndGraphAPI("Month");

            }
        });


        changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
        changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);
        callEnergyAndGraphAPI("Day");

    }

 /*   private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < mKLPGridGraphModelResponse.size(); i++) {
            try {
                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                //  BarEntry v1e1 = new BarEntry(mKLPGridGraphModelResponse.get(i).getTotalGridEnergy(), i); // Jan
                BarEntry v1e1 = new BarEntry(mKLPGridGraphModelResponse.get(i).getTOTALGSCEnergy(), i); // Jan
                valueSet1.add(v1e1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);


        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < mKLPGridGraphModelResponse.size(); i++) {

            try {
                xAxis.add(mKLPGridGraphModelResponse.get(i).getMDate());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }*/

    private ArrayList<LineDataSet> getDataSet() {
        ArrayList<LineDataSet> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();


        for (int i = 0; i < mKLPGridGraphModelResponse.size(); i++) {

            try {
                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                //  BarEntry v1e1 = new BarEntry(mKLPGridGraphModelResponse.get(i).getTotalGridEnergy(), i); // Jan
                Entry v1e1 = new Entry(mKLPGridGraphModelResponse.get(i).getTOTALGSCEnergy(), i); // Jan
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

        for (int i = 0; i < mKLPGridGraphModelResponse.size(); i++) {

            try {
                xAxis.add(mKLPGridGraphModelResponse.get(i).getMDate());
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }

        return xAxis;
    }

    private void setAllEventClick() {

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

                    KLPGridGraphModelView mKLPGridGraphModelView = gson.fromJson(Json, KLPGridGraphModelView.class);
                    //  getTotalEndAndConsuptionsResponse(mShimaModel);
                    getGraphAPIResponse(mKLPGridGraphModelView);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                chart.clear();
                if (mKLPGridGraphModelResponse.size() > 0) {
                    mKLPGridGraphModelResponse.clear();
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

        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_TOTAL_KLP_GRID_GRAPH_ENERGY);/////
    }

    private void getGraphAPIResponse(KLPGridGraphModelView mKLPGridGraphModelView) {

        if (mKLPGridGraphModelView.getStatus()) {
            if (mKLPGridGraphModelResponse != null)
                mKLPGridGraphModelResponse.clear();

            if (mKLPGridGraphModelResponse.size() > 0)
                mKLPGridGraphModelResponse.clear();

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mKLPGridGraphModelResponse = mKLPGridGraphModelView.getResponse();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();

            //data = new BarData(getXAxisValues(), getDataSet());
            data = new LineData(getXAxisValues(), getDataSet());

            chart.setData(data);
          //  chart.setData(data);

            chart.setDescription("Energy Graph");
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }


    }

}
