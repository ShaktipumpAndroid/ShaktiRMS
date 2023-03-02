package com.shaktipumps.shakti_rms.fragment.SAJ;

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
import com.shaktipumps.shakti_rms.bean.SAJ.SAJResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import java.util.ArrayList;

public class DashboardFragmentSAJMotor extends Fragment {

    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;



private SAJResponse mSAJResponse;
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
    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID, txtTodayEnergyUnitID;
    private TextView txtTodayRuningHourNameID, txtTodayRuningHourValueID,txtTodayRunningHourUnitID;

    private RelativeLayout rvlTodayGridRunninghourID, rvlTodayGridEnrgyID;
    private LinearLayout lvlTotalGridRunninghourID, lvlTotalGridEnrgyID;


    public DashboardFragmentSAJMotor() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public DashboardFragmentSAJMotor(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, SAJResponse mSAJResponse) {
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
        this.mSAJResponse = mSAJResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard_saj_motor, container, false);
        mActivity = getActivity();

        initView();
        return mView;
    }

    private void initView() {

        rvlTodayGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridRunninghourID) ;
        rvlTodayGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridEnrgyID) ;

        lvlTotalGridRunninghourID = (LinearLayout) mView.findViewById(R.id.lvlTotalGridRunninghourID) ;
        lvlTotalGridEnrgyID = (LinearLayout) mView.findViewById(R.id.lvlTotalGridEnrgyID) ;

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


        try {
            //if(mSAJResponse.getTOTGRHName() != "NULL")
            txtTotalGridRuningNameID.setText(mSAJResponse.getTOTGRHName());
            txtTotalGridRuningValueID.setText(mSAJResponse.getTOTGRHValue());
            txtTotalGridRuningUnitNameID.setText(mSAJResponse.getTOTGRHUnit());

            txtTotalGridEnergyNameID.setText(mSAJResponse.getTOTGEnergyName());
            txtTotalGridEnergyVlaueID.setText(mSAJResponse.getTOTGEnergyValue());
            txtTotalGridUnitNameID.setText(mSAJResponse.getTOTGEnergyUnit());

            txtTodayEnergyNameID.setText(mSAJResponse.getTDGEnergyName());
            txtTodayEnergyValueID.setText(mSAJResponse.getTDGEnergyValue());
            txtTodayEnergyUnitID.setText(mSAJResponse.getTDGEnergyUnit());

            txtTodayRuningHourNameID.setText(mSAJResponse.getTDGRHName());
            txtTodayRuningHourValueID.setText(mSAJResponse.getTDGRHValue());
            txtTodayRunningHourUnitID.setText(mSAJResponse.getTDGRHrUnit());

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



        setAllEventClick();
        //  setPieChart();
    }

    private void setAllEventClick() {


          }



}
