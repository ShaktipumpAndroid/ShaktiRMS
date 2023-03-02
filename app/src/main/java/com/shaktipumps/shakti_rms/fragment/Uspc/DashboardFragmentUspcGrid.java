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

public class DashboardFragmentUspcGrid extends Fragment {

    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;


private UspcEnrgyResponse mUspcEnrgyResponse;
    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    ArrayList<BarEntry> bargroup1 = new ArrayList<>();

    SharedPreferences pref;

    private BarChart chartGridEnergyID;

    private TextView txtTotalGridEnergyNameID, txtTotalGridEnergyVlaueID, txtTotalGridUnitNameID;

    private TextView txtTotalGridRuningUnitNameID, txtTotalGridRuningValueID, txtTotalGridRuningNameID;
    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID;
    private TextView txtTodayRuningHourNameID, txtTodayRuningHourValueID;
    private TextView txtTodayRunningHourUnitID, txtTodayEnergyUnitID;
    private TextView txtDateHeadingGraphID;

    private RelativeLayout rvlTodayGridRunninghourID, rvlTodayGridEnrgyID;
    private RelativeLayout rvlTotalGridRunninghourID, rvlTotalGridEnrgyID;

    private LinearLayout lrlGraphViewDataID;


    public DashboardFragmentUspcGrid() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public DashboardFragmentUspcGrid(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, UspcEnrgyResponse mUspcEnrgyResponse) {
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
        mView = inflater.inflate(R.layout.fragment_dashboard_klp_grid, container, false);
        mActivity = getActivity();

        initView();
        return mView;
    }

    private void initView() {


        rlvMonthViewID =  mView.findViewById(R.id.rlvMonthViewID) ;
        rlvDayViewID =  mView.findViewById(R.id.rlvDayViewID) ;
        rvlTodayGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridRunninghourID) ;
        rvlTodayGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridEnrgyID) ;

        rvlTotalGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTotalGridRunninghourID) ;
        rvlTotalGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTotalGridEnrgyID) ;

        txtTotalGridRuningNameID = (TextView) mView.findViewById(R.id.txtTotalGridRuningNameID) ;
        txtTotalGridRuningValueID = (TextView) mView.findViewById(R.id.txtTotalGridRuningValueID) ;
        txtTotalGridRuningUnitNameID = (TextView) mView.findViewById(R.id.txtTotalGridRuningUnitNameID) ;

        txtTotalGridEnergyNameID = (TextView) mView.findViewById(R.id.txtTotalGridEnergyNameID) ;
        txtTotalGridEnergyVlaueID = (TextView) mView.findViewById(R.id.txtTotalGridEnergyVlaueID) ;
        txtTotalGridUnitNameID = (TextView) mView.findViewById(R.id.txtTotalGridUnitNameID) ;

        txtTodayEnergyNameID = (TextView) mView.findViewById(R.id.txtTodayEnergyNameID) ;
        txtTodayEnergyValueID = (TextView) mView.findViewById(R.id.txtTodayEnergyValueID) ;
        txtTodayEnergyUnitID = (TextView) mView.findViewById(R.id.txtTodayEnergyUnitID) ;

        txtTodayRuningHourNameID = (TextView) mView.findViewById(R.id.txtTodayRuningHourNameID) ;
        txtTodayRuningHourValueID = (TextView) mView.findViewById(R.id.txtTodayRuningHourValueID) ;
        txtTodayRunningHourUnitID = (TextView) mView.findViewById(R.id.txtTodayRunningHourUnitID) ;


        txtDateHeadingGraphID = (TextView) mView.findViewById(R.id.txtDateHeadingGraphID) ;
        lrlGraphViewDataID =  mView.findViewById(R.id.lrlGraphViewDataID) ;

        try {
            //if(mKLPTotEnergyResponse.getTOTGRHName() != "NULL")
            txtTotalGridRuningNameID.setText(mUspcEnrgyResponse.getTOTUSPCHrName());
            txtTotalGridRuningValueID.setText(mUspcEnrgyResponse.getTOTUSPCHrValue());
            txtTotalGridRuningUnitNameID.setText(mUspcEnrgyResponse.getTOTUSPCHrUnit());

            txtTotalGridEnergyNameID.setText(mUspcEnrgyResponse.getTOTUSPCEnergyName());
            txtTotalGridEnergyVlaueID.setText(mUspcEnrgyResponse.getTOTUSPCEnergyValue());
            txtTotalGridUnitNameID.setText(mUspcEnrgyResponse.getTOTUSPCEnergyUnit());

            txtTodayEnergyNameID.setText(mUspcEnrgyResponse.getTDUSPCEnergyName());
            txtTodayEnergyValueID.setText(mUspcEnrgyResponse.getTDUSPCEnergyValue());
            txtTodayEnergyUnitID.setText(mUspcEnrgyResponse.getTDUSPCEnergyUnit());

            txtTodayRuningHourNameID.setText(mUspcEnrgyResponse.getTDUSPCHrName());
            txtTodayRuningHourValueID.setText(mUspcEnrgyResponse.getTDUSPCHrValue());
            txtTodayRunningHourUnitID.setText(mUspcEnrgyResponse.getTDUSPCHrUnit());


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
