package com.shaktipumps.shakti_rms.fragment.Klp;

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
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.KLPBean.KLPGraphModel;
import com.shaktipumps.shakti_rms.bean.KLPBean.KLPGraphResponse;
import com.shaktipumps.shakti_rms.bean.KLPBean.KLPTotEnergyResponse;
import com.shaktipumps.shakti_rms.bean.KLPHybride.KLPHybrideResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentKLPMotor extends Fragment {

    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;



private KLPTotEnergyResponse mKLPTotEnergyResponse;
private KLPHybrideResponse mKLPHybrideResponse;
    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;

    ArrayList<BarEntry> bargroup1 = new ArrayList<>();

    SharedPreferences pref;




    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID, txtTodayEnergyUnitID;
    private TextView txtTodayRuningHourNameID, txtTodayRuningHourValueID, txtTodayHRUnitID;
    private TextView txtTotalRuningHourNameID, txtTotalRuningHourValueID, txtTotalHRUnitID;

    private TextView txtTotalEnergyNameID, txtTotalEnergyValueID, txtTotalEnergyUnitID;
    private TextView txtTotalFlowNameID, txtTotalFlowValueID, txtTotalFlowUnitID;
    private TextView txtTodayFlowNameID, txtTodayFlowValueID, txtTodayFlowUnitID;

    private LinearLayout lrlGraphViewDataID;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;
    private BarData data;
    private BarChart chart;

    List<KLPGraphResponse> mKLPGraphResponse;

    private TextView txtDateHeadingGraphID, txtEnergyHeadingGraphID;


    public DashboardFragmentKLPMotor() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public DashboardFragmentKLPMotor(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, KLPHybrideResponse mKLPHybrideResponse) {
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
        this.mKLPHybrideResponse = mKLPHybrideResponse;
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
        mKLPGraphResponse = new ArrayList<>();
        baseRequest = new BaseRequest(mActivity);

        initView();
        return mView;
    }

    private void initView() {

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

        txtDateHeadingGraphID = (TextView) mView.findViewById(R.id.txtDateHeadingGraphID) ;
        txtEnergyHeadingGraphID = (TextView) mView.findViewById(R.id.txtEnergyHeadingGraphID) ;

        lrlGraphViewDataID = (LinearLayout) mView.findViewById(R.id.lrlGraphViewDataID);
        rlvMonthViewID = (RelativeLayout) mView.findViewById(R.id.rlvMonthViewID);
        rlvDayViewID = (RelativeLayout) mView.findViewById(R.id.rlvDayViewID);
        chart = (BarChart) mView.findViewById(R.id.chartGridEnergyID);


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

        baseRequest = new BaseRequest(mActivity);

        try {
          //  txtTotalEnergyNameID.setText(mKLPHybrideResponse.getTOTMEnergyName());
            txtTotalEnergyValueID.setText(mKLPHybrideResponse.getTOTMEnergyValue());
            txtTotalEnergyUnitID.setText(mKLPHybrideResponse.getTOTMEnergyUnit());

           // String sName = mKLPHybrideResponse.getTDMEnergyName();
            String sValue = mKLPHybrideResponse.getTDMEnergyValue();
            String sUnit = mKLPHybrideResponse.getTDMEnergyUnit();

          //  txtTodayEnergyNameID.setText(sName);
            txtTodayEnergyValueID.setText(sValue);
            txtTodayEnergyUnitID.setText(sUnit);

         //   txtTotalRuningHourNameID.setText(mKLPHybrideResponse.getTOTMRHName());
            txtTotalRuningHourValueID.setText(mKLPHybrideResponse.getTOTMRHValue());
            txtTotalHRUnitID.setText(mKLPHybrideResponse.getTOTMRHUnit());

          //  txtTodayRuningHourNameID.setText(mKLPHybrideResponse.getTDMRHName());
            txtTodayRuningHourValueID.setText(mKLPHybrideResponse.getTDMRHValue());
            txtTodayHRUnitID.setText(mKLPHybrideResponse.getTDMRHUnit());

           // txtTotalFlowNameID.setText(mKLPHybrideResponse.getTOTMFlowName());
            txtTotalFlowValueID.setText(mKLPHybrideResponse.getTOTMFlowValue());
           // txtTotalFlowUnitID.setText(mKLPHybrideResponse.getTOTMFlowUnit());
            txtTotalFlowUnitID.setText(mKLPHybrideResponse.getTDMFlowUnit());

            //txtTodayFlowNameID.setText(mKLPHybrideResponse.getTDMFlowName());
            txtTodayFlowValueID.setText(mKLPHybrideResponse.getTDMFlowValue());
            txtTodayFlowUnitID.setText(mKLPHybrideResponse.getTDMFlowUnit());

        } catch (Exception e) {
            e.printStackTrace();
        }

        setAllEventClick();

        rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);
                //txtDateHeadingGraphID.setText("HR");
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
               // txtDateHeadingGraphID.setText("DATE");

                txtDateHeadingGraphID.setText(getString(R.string.st_DATE));
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

        for (int i = 0; i < mKLPGraphResponse.size() ; i++) {

            try {

                //lineEntries.add(new Entry((float)i, mKLPGraphResponse.get(i).getEnergy()));
                BarEntry v1e1 = new BarEntry(mKLPGraphResponse.get(i).getTOTALENERGYVFD(), i); // Jan
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

        for (int i = 0; i < mKLPGraphResponse.size() ; i++) {

            try {
                xAxis.add(mKLPGraphResponse.get(i).getMDate());
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

                    KLPGraphModel mKLPGraphModel = gson.fromJson(Json, KLPGraphModel.class);
                    //  getTotalEndAndConsuptionsResponse(mShimaModel);
                    getGraphAPIResponse(mKLPGraphModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                chart.clear();
                if(mKLPGraphResponse.size() > 0)
                {
                    mKLPGraphResponse.clear();
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
        System.out.println("jsonObject_GRAPH==>>"+jsonObject);
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_KLP_SIXTYNINE_GRAPH_ENERGY);/////
    }

    private void getGraphAPIResponse(KLPGraphModel mKLPGraphModel) {

        if (mKLPGraphModel.getStatus())
        {
            if(mKLPGraphResponse != null )
                mKLPGraphResponse.clear();

            if(mKLPGraphResponse.size() > 0)
                mKLPGraphResponse.clear();

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mKLPGraphResponse = mKLPGraphModel.getResponse();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();

            data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            chart.setDescription("");
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }


    }


}
