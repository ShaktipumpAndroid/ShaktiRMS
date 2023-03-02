package com.shaktipumps.shakti_rms.fragment.Uspc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.UspcEnergy.UspcEnrgyResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import java.util.ArrayList;

public class DashboardFragmentUspcMotor extends Fragment {

    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;



private UspcEnrgyResponse mUspcEnrgyResponse;
    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;

    ArrayList<BarEntry> bargroup1 = new ArrayList<>();

    SharedPreferences pref;

    private BarChart chartGridEnergyID;

    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID, txtTodayEnergyUnitID;
    private TextView txtTodayRuningHourNameID, txtTodayRuningHourValueID, txtTodayHRUnitID;
    private TextView txtTotalRuningHourNameID, txtTotalRuningHourValueID, txtTotalHRUnitID;

    private TextView txtTotalEnergyNameID, txtTotalEnergyValueID, txtTotalEnergyUnitID;
    private TextView txtTotalFlowNameID, txtTotalFlowValueID, txtTotalFlowUnitID;
    private TextView txtTodayFlowNameID, txtTodayFlowValueID, txtTodayFlowUnitID;
    private TextView txtDateHeadingGraphID;
    private LinearLayout lrlGraphViewDataID;

    public DashboardFragmentUspcMotor() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public DashboardFragmentUspcMotor(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, UspcEnrgyResponse mUspcEnrgyResponse) {
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
        mView = inflater.inflate(R.layout.fragment_dashboard_klp_motor, container, false);
        mActivity = getActivity();

        initView();
        return mView;
    }

    private void initView() {

        lrlGraphViewDataID =  mView.findViewById(R.id.lrlGraphViewDataID) ;
        rlvMonthViewID =  mView.findViewById(R.id.rlvMonthViewID) ;
        rlvDayViewID =  mView.findViewById(R.id.rlvDayViewID) ;
        txtDateHeadingGraphID =  mView.findViewById(R.id.txtDateHeadingGraphID) ;

        txtTotalEnergyNameID = (TextView) mView.findViewById(R.id.txtTotalEnergyNameID);
        txtTotalEnergyValueID = (TextView) mView.findViewById(R.id.txtTotalEnergyValueID);
        txtTotalEnergyUnitID = (TextView) mView.findViewById(R.id.txtTotalEnergyUnitID);

        txtTodayEnergyNameID = (TextView) mView.findViewById(R.id.txtTodayEnergyNameID) ;
        txtTodayEnergyValueID = (TextView) mView.findViewById(R.id.txtTodayEnergyValueID) ;
        txtTodayEnergyUnitID = (TextView) mView.findViewById(R.id.txtTodayEnergyUnitID) ;

        txtTotalRuningHourNameID = (TextView) mView.findViewById(R.id.txtTotalRuningHourNameID) ;
        txtTotalRuningHourValueID = (TextView) mView.findViewById(R.id.txtTotalRuningHourValueID) ;
        txtTotalHRUnitID = (TextView) mView.findViewById(R.id.txtTotalHRUnitID) ;

        txtTodayRuningHourNameID = (TextView) mView.findViewById(R.id.txtTodayRuningHourNameID) ;
        txtTodayRuningHourValueID = (TextView) mView.findViewById(R.id.txtTodayRuningHourValueID) ;
        txtTodayHRUnitID = (TextView) mView.findViewById(R.id.txtTodayHRUnitID) ;


        txtTotalFlowNameID = (TextView) mView.findViewById(R.id.txtTotalFlowNameID) ;
        txtTotalFlowValueID = (TextView) mView.findViewById(R.id.txtTotalFlowValueID) ;
        txtTotalFlowUnitID = (TextView) mView.findViewById(R.id.txtTotalFlowUnitID) ;

        txtTodayFlowNameID = (TextView) mView.findViewById(R.id.txtTodayFlowNameID) ;
        txtTodayFlowValueID = (TextView) mView.findViewById(R.id.txtTodayFlowValueID) ;
        txtTodayFlowUnitID = (TextView) mView.findViewById(R.id.txtTodayFlowUnitID) ;

        try {
            txtTotalEnergyNameID.setText(mUspcEnrgyResponse.getTOTPEnergyName());
            txtTotalEnergyValueID.setText(mUspcEnrgyResponse.getTOTPEnergyValue());
            txtTotalEnergyUnitID.setText(mUspcEnrgyResponse.getTOTPEnergyUnit());

            String sName = mUspcEnrgyResponse.getTDPEnergyName();
            String sValue = mUspcEnrgyResponse.getTDPEnergyValue();
            String sUnit = mUspcEnrgyResponse.getTDPEnergyUnit();

            txtTodayEnergyNameID.setText(sName);
            txtTodayEnergyValueID.setText(sValue);
            txtTodayEnergyUnitID.setText(sUnit);

            txtTotalRuningHourNameID.setText(mUspcEnrgyResponse.getTOTPRHRName());
            txtTotalRuningHourValueID.setText(mUspcEnrgyResponse.getTOTPRHRValue());
            txtTotalHRUnitID.setText(mUspcEnrgyResponse.getTOTPRHRUnit());

            txtTodayRuningHourNameID.setText(mUspcEnrgyResponse.getTDPRHRName());
            txtTodayRuningHourValueID.setText(mUspcEnrgyResponse.getTDPRHRValue());
            txtTodayHRUnitID.setText(mUspcEnrgyResponse.getTDPRHRUnit());

            txtTotalFlowNameID.setText(mUspcEnrgyResponse.getTOTFlowName());
            txtTotalFlowValueID.setText(mUspcEnrgyResponse.getTOTFlowValue());
           // txtTotalFlowUnitID.setText(mKLPTotEnergyResponse.getTOTMFlowUnit());
            txtTotalFlowUnitID.setText(mUspcEnrgyResponse.getTOTFlowUnit());

            txtTodayFlowNameID.setText(mUspcEnrgyResponse.getTDFlowName());
            txtTodayFlowValueID.setText(mUspcEnrgyResponse.getTDFlowValue());
            txtTodayFlowUnitID.setText(mUspcEnrgyResponse.getTDFlowUnit());

        } catch (Exception e) {
            e.printStackTrace();
        }

        chartGridEnergyID = (BarChart) mView.findViewById(R.id.chartGridEnergyID);

        baseRequest = new BaseRequest(mActivity);


        bargroup1.add(new BarEntry(8f, 0));
        bargroup1.add(new BarEntry(2f, 1));
        bargroup1.add(new BarEntry(5f, 2));
        bargroup1.add(new BarEntry(20f, 3));
        bargroup1.add(new BarEntry(15f, 4));
        bargroup1.add(new BarEntry(19f, 5));

        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Energy");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


// creating dataset for Bar Group 2

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

       /* ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);*/


        changeButtonVisibilityRLV(true, 1.0f,rlvDayViewID);
        changeButtonVisibilityRLV(true, 0.5f,rlvMonthViewID);

        setAllEventClick();
        //  setPieChart();
    }

    private void setAllEventClick() {

        rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);

                txtDateHeadingGraphID.setText("HR");
                // txtSetValueHead();
                //callEnergyAndGraphAPI("Day");

            }
        });

        rlvMonthViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvMonthViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvDayViewID);

                txtDateHeadingGraphID.setText("DATE");
                // txtSetValueHead();

                // callEnergyAndGraphAPI("Month");
                /* data = new BarData(getXAxisValues(), getDataSet());
                chart.setData(data);
                chart.setDescription("Energy Graph");
                chart.animateXY(2000, 2000);
                chart.invalidate();*/
            }
        });

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

}
