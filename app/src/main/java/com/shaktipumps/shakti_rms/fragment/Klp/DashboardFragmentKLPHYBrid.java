package com.shaktipumps.shakti_rms.fragment.Klp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
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

public class DashboardFragmentKLPHYBrid extends Fragment {

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

    private BarChart chartGridEnergyID;

    private TextView txtTotalGridEnergyNameID, txtTotalGridEnergyVlaueID, txtTotalGridUnitNameID;

    private TextView txtTotalGridRuningUnitNameID, txtTotalGridRuningValueID, txtTotalGridRuningNameID;
    private TextView txtTodayEnergyNameID, txtTodayEnergyValueID;
    private TextView txtTodayRuningHourNameID, txtTodayRuningHourValueID;
    private TextView txtTodayRunningHourUnitID, txtTodayEnergyUnitID;

    private RelativeLayout rvlTodayGridRunninghourID, rvlTodayGridEnrgyID;
    private RelativeLayout rvlTotalGridRunninghourID, rvlTotalGridEnrgyID;

    private LinearLayout lrlGraphViewDataID;
    private RelativeLayout rlvGridpowerID;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;
    private BarData data;
    private BarChart chart;
    private WebView webViewChartID ;
    private WebSettings webSettings;

    List<KLPGraphResponse> mKLPGraphResponse;

    private TextView txtDateHeadingGraphID, txtEnergyHeadingGraphID;
    public DashboardFragmentKLPHYBrid() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public DashboardFragmentKLPHYBrid(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, KLPHybrideResponse mKLPHybrideResponse) {
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
        mView = inflater.inflate(R.layout.fragment_dashboard_klp_hybrid, container, false);
        mActivity = getActivity();
        baseRequest = new BaseRequest(mActivity);
        mKLPGraphResponse = new ArrayList<>();
        initView();
        return mView;
    }

    private void initView() {


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
        txtEnergyHeadingGraphID = (TextView) mView.findViewById(R.id.txtEnergyHeadingGraphID) ;

        lrlGraphViewDataID = (LinearLayout) mView.findViewById(R.id.lrlGraphViewDataID);
        rlvMonthViewID = (RelativeLayout) mView.findViewById(R.id.rlvMonthViewID);
        rlvGridpowerID = (RelativeLayout) mView.findViewById(R.id.rlvGridpowerID);
        rlvDayViewID = (RelativeLayout) mView.findViewById(R.id.rlvDayViewID);
        chart = (BarChart) mView.findViewById(R.id.chartGridEnergyID);
        webViewChartID = (WebView) mView.findViewById(R.id.webViewChartID);

         webSettings = webViewChartID.getSettings();
        webSettings.setJavaScriptEnabled(true);

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
            //if(mKLPTotEnergyResponse.getTOTGRHName() != "NULL")
           // txtTotalGridRuningNameID.setText(mKLPHybrideResponse.getTOTGRHName());
            txtTotalGridRuningValueID.setText(mKLPHybrideResponse.getTOTGRHValue());
            txtTotalGridRuningUnitNameID.setText(mKLPHybrideResponse.getTOTGRHUnit());

          //  txtTotalGridEnergyNameID.setText(mKLPHybrideResponse.getTOTGEnergyName());
            txtTotalGridEnergyVlaueID.setText(mKLPHybrideResponse.getTOTGEnergyValue());
            txtTotalGridUnitNameID.setText(mKLPHybrideResponse.getTOTGEnergyUnit());

           // txtTodayEnergyNameID.setText(mKLPHybrideResponse.getTDGEnergyName());
            txtTodayEnergyValueID.setText(mKLPHybrideResponse.getTDGEnergyValue());
            txtTodayEnergyUnitID.setText(mKLPHybrideResponse.getTDGEnergyUnit());

           // txtTodayRuningHourNameID.setText(mKLPHybrideResponse.getTDGRHName());
            txtTodayRuningHourValueID.setText(mKLPHybrideResponse.getTDGRHValue());
            txtTodayRunningHourUnitID.setText(mKLPHybrideResponse.getTDGRHUnit());


        } catch (Exception e) {
            e.printStackTrace();
        }


        chartGridEnergyID = (BarChart) mView.findViewById(R.id.chartGridEnergyID);



        setAllEventClick();
        rlvDayViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvDayViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvGridpowerID);
                chartGridEnergyID.setVisibility(View.VISIBLE);
                webViewChartID.setVisibility(View.GONE);
                //txtDateHeadingGraphID.setText("Hr");

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
                changeButtonVisibilityRLV(true, 0.5f, rlvGridpowerID);

             //  txtDateHeadingGraphID.setText("DATE");

                txtDateHeadingGraphID.setText(getString(R.string.st_DATE));
                // txtSetValueHead();
               callEnergyAndGraphAPI("Month");
                chartGridEnergyID.setVisibility(View.VISIBLE);
                webViewChartID.setVisibility(View.GONE);
              //  webViewChartID.loadUrl("https://solar10.shaktisolarrms.com/RMSApp/GridGraph.jsp?deviceNo=70-0003-0-01-01-20-0&deviceType=70");

            }
        });
        rlvGridpowerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, rlvGridpowerID);
                changeButtonVisibilityRLV(true, 0.5f, rlvMonthViewID);
                changeButtonVisibilityRLV(true, 0.5f, rlvDayViewID);

               txtDateHeadingGraphID.setText(getString(R.string.st_DATE));
                // txtSetValueHead();
              // callEnergyAndGraphAPI("Month");
               // chartGridEnergyID.setVisibility(View.VISIBLE);
                webViewChartID.setVisibility(View.VISIBLE);
                webViewChartID.loadUrl("https://solar10.shaktisolarrms.com/RMSApp/GridGraph.jsp?deviceNo=70-0003-0-01-01-20-0&deviceType=70");

            }
        });


        changeButtonVisibilityRLV(true, 1.0f,rlvDayViewID);
        changeButtonVisibilityRLV(true, 0.5f,rlvMonthViewID);
        changeButtonVisibilityRLV(true, 0.5f,rlvGridpowerID);
        callEnergyAndGraphAPI("Day");
        //  setPieChart();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < mKLPGraphResponse.size() ; i++) {
            try {
                //lineEntries.add(new Entry((float)i, mKLPGraphResponse.get(i).getEnergy()));
                BarEntry v1e1 = new BarEntry(mKLPGraphResponse.get(i).getTOTALGSCEnergy(), i); // Jan
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
        System.out.println("jsonObject_GRAPH11==>>"+jsonObject);
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
