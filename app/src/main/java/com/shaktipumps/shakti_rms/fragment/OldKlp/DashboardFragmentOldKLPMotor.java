package com.shaktipumps.shakti_rms.fragment.OldKlp;

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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.OLD_klp.OldKLPGraphModel;
import com.shaktipumps.shakti_rms.bean.OLD_klp.OldKLPGraphResponse;
import com.shaktipumps.shakti_rms.bean.OLD_klp.OldKLPResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentOldKLPMotor extends Fragment {

    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;

    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    SharedPreferences pref;




    private TextView txtTotalFlowUnitNameID, txtTotalFlowNameID, txtTotalFlowValueID;
    private TextView txtTotalRHNameID, txtTotalHRValueID, txtTotalHRUnitID;

    private TextView txtTodayFlowNameID, txtTodayFlowValueID, txtTodayFlowUnitID;

    private TextView txtTodayHRNameID, txtTodayHRValueID,txtTodayHRUnitID;


    ArrayList<BarEntry> bargroup1 = new ArrayList<>();

    private OldKLPResponse mOldKLPResponse;

    private LinearLayout lrlGraphViewDataID;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;
    private BarData data;
    private BarChart chart;

    List<OldKLPGraphResponse> mOldKLPGraphResponse;

    private TextView txtDateHeadingGraphID, txtEnergyHeadingGraphID;

    public DashboardFragmentOldKLPMotor() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public DashboardFragmentOldKLPMotor(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, OldKLPResponse OldKLPResponse) {
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
        this.mOldKLPResponse = mOldKLPResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_oldklp_motor_dashboard, container, false);
        mActivity = getActivity();
        mOldKLPGraphResponse = new ArrayList<>();
        baseRequest = new BaseRequest(mActivity);

        initView();
        return mView;
    }

    private void initView() {

        txtTotalFlowNameID = (TextView) mView.findViewById(R.id.txtTotalFlowNameID) ;
        txtTotalFlowValueID = (TextView) mView.findViewById(R.id.txtTotalFlowValueID) ;
        txtTotalFlowUnitNameID = (TextView) mView.findViewById(R.id.txtTotalFlowUnitNameID) ;

        txtTotalRHNameID = (TextView) mView.findViewById(R.id.txtTotalRHNameID) ;
        txtTotalHRValueID = (TextView) mView.findViewById(R.id.txtTotalHRValueID) ;
        txtTotalHRUnitID = (TextView) mView.findViewById(R.id.txtTotalHRUnitID) ;

        txtTodayFlowNameID = (TextView) mView.findViewById(R.id.txtTodayFlowNameID) ;
        txtTodayFlowValueID = (TextView) mView.findViewById(R.id.txtTodayFlowValueID) ;
        txtTodayFlowUnitID = (TextView) mView.findViewById(R.id.txtTodayFlowUnitID) ;

        txtTodayHRValueID = (TextView) mView.findViewById(R.id.txtTodayHRValueID) ;
        txtTodayHRNameID = (TextView) mView.findViewById(R.id.txtTodayHRNameID) ;
        txtTodayHRUnitID = (TextView) mView.findViewById(R.id.txtTodayHRUnitID) ;


        //chartMotorEnergyID = (BarChart) mView.findViewById(R.id.chartMotorEnergyID) ;

        txtDateHeadingGraphID = (TextView) mView.findViewById(R.id.txtDateHeadingGraphID) ;
        txtEnergyHeadingGraphID = (TextView) mView.findViewById(R.id.txtEnergyHeadingGraphID) ;

        lrlGraphViewDataID = (LinearLayout) mView.findViewById(R.id.lrlGraphViewDataID);
        rlvMonthViewID = (RelativeLayout) mView.findViewById(R.id.rlvMonthViewID);
        rlvDayViewID = (RelativeLayout) mView.findViewById(R.id.rlvDayViewID);
        chart = (BarChart) mView.findViewById(R.id.chartGridEnergyID);



        baseRequest = new BaseRequest(mActivity);


        try {




            txtTotalFlowNameID.setText(mOldKLPResponse.getTOTMFlowName());
            txtTotalFlowValueID.setText(mOldKLPResponse.getTOTMFlowValue());
            txtTotalFlowUnitNameID.setText(mOldKLPResponse.getTOTMFlowUnit());

            txtTodayFlowNameID.setText(mOldKLPResponse.getTDMFlowName());
            txtTodayFlowValueID.setText(mOldKLPResponse.getTDMFlowValue());
            txtTodayFlowUnitID.setText(mOldKLPResponse.getTDMFlowUnit());

            txtTotalRHNameID.setText(mOldKLPResponse.getTOTMRHName());
            txtTotalHRValueID.setText(mOldKLPResponse.getTOTMRHValue());
            txtTotalHRUnitID.setText(mOldKLPResponse.getTOTMRHUnit());


            txtTodayHRNameID.setText(mOldKLPResponse.getTDGRHName());
            txtTodayHRValueID.setText(mOldKLPResponse.getTDGRHValue());
            txtTodayHRUnitID.setText(mOldKLPResponse.getTDGRHUnit());

        } catch (Exception e) {
            e.printStackTrace();
        }




        setAllEventClick();

        rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);

                txtDateHeadingGraphID.setText("HR");
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


        changeButtonVisibilityRLV(true, 1.0f,rlvDayViewID);
        changeButtonVisibilityRLV(true, 0.5f,rlvMonthViewID);
        callEnergyAndGraphAPI("Day");

        //  setPieChart();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();




        for (int i = 0; i < mOldKLPGraphResponse.size() ; i++) {

            try {

                //lineEntries.add(new Entry((float)i, mKLPGraphResponse.get(i).getEnergy()));
                BarEntry v1e1 = new BarEntry(mOldKLPGraphResponse.get(i).getFlow(), i); // Jan
                valueSet1.add(v1e1);


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
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
       /* xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");*/

        for (int i = 0; i < mOldKLPGraphResponse.size() ; i++) {

            try {
                xAxis.add(mOldKLPGraphResponse.get(i).getMDate());
            }
            catch (Exception eee)
            {
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

                    OldKLPGraphModel mOldKLPGraphModel = gson.fromJson(Json, OldKLPGraphModel.class);
                    //  getTotalEndAndConsuptionsResponse(mShimaModel);
                    getGraphAPIResponse(mOldKLPGraphModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                chart.clear();
                if(mOldKLPGraphResponse.size() > 0)
                {
                    mOldKLPGraphResponse.clear();
                }

                //Toast.makeText(mActivity, "Vikas graph"+message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {



                Toast.makeText(mActivity, "Please check internet connection!", Toast.LENGTH_LONG).show();
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

        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_OLD_KLP_SEVEN_GRAPH_ENERGY);/////
    }

    private void getGraphAPIResponse(OldKLPGraphModel mOldKLPGraphModel) {

        if (mOldKLPGraphModel.getStatus())
        {
            if(mOldKLPGraphResponse != null )
                mOldKLPGraphResponse.clear();

            if(mOldKLPGraphResponse.size() > 0)
                mOldKLPGraphResponse.clear();

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mOldKLPGraphResponse = mOldKLPGraphModel.getResponse();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();

            data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            chart.setDescription("");
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }


    }



}
