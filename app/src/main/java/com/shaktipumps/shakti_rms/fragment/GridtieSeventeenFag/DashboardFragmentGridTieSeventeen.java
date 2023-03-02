package com.shaktipumps.shakti_rms.fragment.GridtieSeventeenFag;

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
import com.shaktipumps.shakti_rms.bean.GritTieSeventeen.GridTieSeventeenGraphModel;
import com.shaktipumps.shakti_rms.bean.GritTieSeventeen.GridTieSeventeenGraphResponse;
import com.shaktipumps.shakti_rms.bean.GritTieSeventeen.GridTieSeventeenResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragmentGridTieSeventeen extends Fragment {

    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;



private GridTieSeventeenResponse mGridTieSeventeenResponse;
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

    private RelativeLayout rvlTotalGridEnrgyID, rvlTotalGridRunninghourID;

    BarChart chart;
    List<GridTieSeventeenGraphResponse> mGridTieSeventeenGraphResponse;


    private LinearLayout lrlGraphViewDataID;
    private RelativeLayout rlvMonthViewID;
    private RelativeLayout rlvDayViewID;
    private BarData data;


    private TextView txtDateHeadingGraphID;

    public DashboardFragmentGridTieSeventeen() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public DashboardFragmentGridTieSeventeen(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, GridTieSeventeenResponse mGridTieSeventeenResponse) {
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
        this.mGridTieSeventeenResponse = mGridTieSeventeenResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard_gridtie_seventeen, container, false);
        mActivity = getActivity();
        baseRequest = new BaseRequest(mActivity);
        mGridTieSeventeenGraphResponse = new ArrayList<>();
        initView();
        return mView;
    }

    private void initView() {

        rvlTodayGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridRunninghourID) ;
        rvlTodayGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTodayGridEnrgyID) ;
        rvlTotalGridRunninghourID = (RelativeLayout) mView.findViewById(R.id.rvlTotalGridRunninghourID) ;
        rvlTotalGridEnrgyID = (RelativeLayout) mView.findViewById(R.id.rvlTotalGridEnrgyID) ;

    txtDateHeadingGraphID = (TextView) mView.findViewById(R.id.txtDateHeadingGraphID) ;


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


        lrlGraphViewDataID = (LinearLayout) mView.findViewById(R.id.lrlGraphViewDataID);
        rlvMonthViewID = (RelativeLayout) mView.findViewById(R.id.rlvMonthViewID);
        rlvDayViewID = (RelativeLayout) mView.findViewById(R.id.rlvDayViewID);

        //chartMotorEnergyID = (BarChart) mView.findViewById(R.id.chartMotorEnergyID) ;
        chart = (BarChart) mView.findViewById(R.id.chartGridEnergyID) ;


        try {
            //if(mOldKLPResponse.getTOTGRHName() != "NULL")
            txtTotalGridRuningNameID.setText(mGridTieSeventeenResponse.getTOTGRHrName());
            txtTotalGridRuningValueID.setText(mGridTieSeventeenResponse.getTOTGRHrValue());
            txtTotalGridRuningUnitNameID.setText(mGridTieSeventeenResponse.getTOTGRHrUnit());

            txtTotalGridEnergyNameID.setText(mGridTieSeventeenResponse.getTOTPVEnergyName());
            txtTotalGridEnergyVlaueID.setText(mGridTieSeventeenResponse.getTOTPVEnergyValue());
            txtTotalGridUnitNameID.setText(mGridTieSeventeenResponse.getTOTPVEnergyUnit());

            txtTodayEnergyNameID.setText(mGridTieSeventeenResponse.getTDPVEnergyName());
            txtTodayEnergyValueID.setText(mGridTieSeventeenResponse.getTDPVEnergyValue());
            txtTodayEnergyUnitID.setText(mGridTieSeventeenResponse.getTDPVEnergyUnit());

            txtTodayRuningHourNameID.setText(mGridTieSeventeenResponse.getTDGRHrName());
            txtTodayRuningHourValueID.setText(mGridTieSeventeenResponse.getTDGRHrValue());
            txtTodayRunningHourUnitID.setText(mGridTieSeventeenResponse.getTDGRHrUnit());


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

            }
        });


        changeButtonVisibilityRLV(true, 1.0f,rlvDayViewID);
        changeButtonVisibilityRLV(true, 0.5f,rlvMonthViewID);
        callEnergyAndGraphAPI("Day");

    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();


        for (int i = 0; i < mGridTieSeventeenGraphResponse.size(); i++) {

            try {

                //lineEntries.add(new Entry((float)i, mSimhaGraphViewResponse.get(i).getEnergy()));
                BarEntry v1e1 = new BarEntry(mGridTieSeventeenGraphResponse.get(i).getTotalGridEnergy(), i); // Jan
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

        for (int i = 0; i < mGridTieSeventeenGraphResponse.size(); i++) {

            try {
                xAxis.add(mGridTieSeventeenGraphResponse.get(i).getMDate());
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

                    GridTieSeventeenGraphModel mGridTieSeventeenGraphModel = gson.fromJson(Json, GridTieSeventeenGraphModel.class);
                    //  getTotalEndAndConsuptionsResponse(mShimaModel);
                    getGraphAPIResponse(mGridTieSeventeenGraphModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                chart.clear();
                if(mGridTieSeventeenGraphResponse.size() > 0)
                {
                    mGridTieSeventeenGraphResponse.clear();
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

        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GRIDTIE_SEVENTEEN_GRAPH_ENERGY);/////
    }

    private void getGraphAPIResponse(GridTieSeventeenGraphModel mGridTieSeventeenGraphModel) {

        if (mGridTieSeventeenGraphModel.getStatus())
        {
            if(mGridTieSeventeenGraphResponse != null )
                mGridTieSeventeenGraphResponse.clear();

            if(mGridTieSeventeenGraphResponse.size() > 0)
                mGridTieSeventeenGraphResponse.clear();

            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            mGridTieSeventeenGraphResponse = mGridTieSeventeenGraphModel.getResponse();
            //total_fault = mTotalEndAndConsuptionsResponse.getTotalFault();

            data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            chart.setDescription("Energy Graph");
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }


    }



}
