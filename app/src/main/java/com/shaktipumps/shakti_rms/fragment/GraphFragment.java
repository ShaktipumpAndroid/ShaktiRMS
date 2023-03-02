package com.shaktipumps.shakti_rms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphEnergy;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphFlow;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphModel;
import com.shaktipumps.shakti_rms.model.EnergyGraphModel.EnergyGraphResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GraphFragment extends Fragment {

private Activity mActivity;
private View view;

//private LineChart chartEnergyID;
private BarChart chartEnergyID;
//private LineChart chartFlowID;
private BarChart chartFlowID;


    String mValueData = "date";

    // create BarEntry for Bar Group 1
    ArrayList<BarEntry> bargroup1 = new ArrayList<>();

    // create BarEntry for Bar Group 1
    ArrayList<BarEntry> bargroup2 = new ArrayList<>();

    private EnergyGraphResponse mEnergyGraphResponse;
    private List<EnergyGraphEnergy> mEnergyGraphEnergy;
    private List<EnergyGraphFlow> mEnergyGraphFlow;


    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";
    private BaseRequest baseRequest;


    public GraphFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public GraphFragment(String MUserId , String DeviceNo, String DeviceType, String CustomerName, String Mobile) {
        // Required empty public constructor
        this.MUserId = MUserId;
        this.DeviceNo = DeviceNo;
        this.DeviceType = DeviceType;
        this.CustomerName = CustomerName;
        this.Mobile = Mobile;
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_graph, container, false);

        mActivity = getActivity();
        baseRequest = new BaseRequest(getActivity());
        initView();
        return view;
    }

    private void initView() {


        mEnergyGraphEnergy = new ArrayList<>();
        mEnergyGraphFlow = new ArrayList<>();
      //  chartEnergyID = (LineChart) view.findViewById(R.id.chartEnergyID);
        chartEnergyID = (BarChart) view.findViewById(R.id.chartEnergyID);
      //  chartFlowID = (LineChart) view.findViewById(R.id.chartFlowID);
        chartFlowID = (BarChart) view.findViewById(R.id.chartFlowID);

       /* bargroup1.add(new BarEntry(8f, 0));
        bargroup1.add(new BarEntry(2f, 1));
        bargroup1.add(new BarEntry(5f, 2));
        bargroup1.add(new BarEntry(20f, 3));
        bargroup1.add(new BarEntry(15f, 4));
        bargroup1.add(new BarEntry(19f, 5));

        bargroup2.add(new BarEntry(6f, 0));
        bargroup2.add(new BarEntry(10f, 1));
        bargroup2.add(new BarEntry(5f, 2));
        bargroup2.add(new BarEntry(25f, 3));
        bargroup2.add(new BarEntry(4f, 4));
        bargroup2.add(new BarEntry(17f, 5));*/


      //  addFlowGraphFun();
        // addEnergyGraphFun();

        callEnergyGraphAPI();

    }

    private void callEnergyGraphAPI() {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    EnergyGraphModel mEnergyGraphModel = gson.fromJson(Json, EnergyGraphModel.class);
                    getTotalEnergyGraphResponse(mEnergyGraphModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {

                if(!message.equalsIgnoreCase("") && !message.equalsIgnoreCase(null))
                {
                    Toast.makeText(mActivity, " vikas gothi "+ message, Toast.LENGTH_LONG).show();
                    addFlowGraphFun();
                    addEnergyGraphFun();
                }



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
            //  jsonObject.addProperty("DeviceType", vDeviceType);
            Calendar c = Calendar.getInstance();
            System.out.println("Current time =&gt; "+c.getTime());

            //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
          //  Toast.makeText(mActivity, formattedDate, Toast.LENGTH_SHORT).show();
            jsonObject.addProperty("DeviceNo", DeviceNo);
            jsonObject.addProperty("SDate", mValueData);
            jsonObject.addProperty("startdate", formattedDate);
            jsonObject.addProperty("enddate", formattedDate);

            //  {"startdate":"2019-06-01","enddate":"2019-06-30","SDate":"month","DeviceNo":"04-0536-0-23-10-18-0"}

            //   jsonObject.addProperty("DeviceNo", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_DEVICE_ENERGY_GRAPH);/////
    }

    private void getTotalEnergyGraphResponse(EnergyGraphModel mEnergyGraphModel) {

        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mEnergyGraphModel.getStatus())
        {

            if(mEnergyGraphResponse != null )
                mEnergyGraphResponse = null;


            // mTotalEndAndConsuptionsModelView = mWelcomeModelView.getResponse();
            // mTotalEndAndConsuptionsResponse = mEnergyGraphModel.getResponse();

            if(mEnergyGraphEnergy != null && mEnergyGraphEnergy.size()> 0 )
            {
                mEnergyGraphEnergy.clear();
                mEnergyGraphFlow.clear();
            }

            mEnergyGraphEnergy = mEnergyGraphModel.getResponse().getEnergy();
            mEnergyGraphFlow = mEnergyGraphModel.getResponse().getFlow();

            for (int i = 0; i < mEnergyGraphEnergy.size(); i++) {



                bargroup1.add(new BarEntry(mEnergyGraphEnergy.get(i).getCEnergyF(), i));

            }

            for (int j = 0; j < mEnergyGraphFlow.size(); j++) {

                bargroup2.add(new BarEntry(mEnergyGraphFlow.get(j).getCFlowF(), j));

            }
          /*  bargroup1.add(new BarEntry(8f, 0));
            bargroup1.add(new BarEntry(2f, 1));
            bargroup1.add(new BarEntry(5f, 2));
            bargroup1.add(new BarEntry(20f, 3));
            bargroup1.add(new BarEntry(15f, 4));
            bargroup1.add(new BarEntry(19f, 5));
*/
         /*   bargroup2.add(new BarEntry(6f, 0));
            bargroup2.add(new BarEntry(10f, 1));
            bargroup2.add(new BarEntry(5f, 2));
            bargroup2.add(new BarEntry(25f, 3));
            bargroup2.add(new BarEntry(4f, 4));
            bargroup2.add(new BarEntry(17f, 5));
*/
            addFlowGraphFun();
            addEnergyGraphFun();

            // addEnergyGraphFun(mEnergyGraphEnergy);
            //   txtConsumptionValueID.setText(mTotalEndAndConsuptionsResponse.getEnergyconsumption());
            //  txtTotalEnergyValueID.setText(mTotalEndAndConsuptionsResponse.getEnergyGeneration());

        }
    }


    private void addEnergyGraphFun()
    {

        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Energy");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
       // barDataSet1.setColors(ColorTemplate.COLOR_NONE);

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

      /*  ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        BarData AUTHModelData = new BarData(dataSets);
        chartEnergyID.setData(AUTHModelData);*/

      /*  ArrayList<Entry> values = new ArrayList<>();
        //  for (int i = 0; i < mEnergyGraphEnergy.size(); i++)
        for (int i = 0; i < 10; i++)
        {
            //    mEnergyGraphEnergy
            if(i == 0)
            {
                // values.add(new Entry(0, mEnergyGraphEnergy.get(i).getCEnergyF()));
                values.add(new Entry(0, i+2));
                LineDataSet set1;
                if (chartEnergyID.getData() != null &&
                        chartEnergyID.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chartEnergyID.getData().getDataSetByIndex(0);
                    set1.setValues(values);
                    chartEnergyID.getData().notifyDataChanged();
                    chartEnergyID.notifyDataSetChanged();
                } else {
                    set1 = new LineDataSet(values, "Total Energy");
                    set1.setDrawIcons(false);
                    set1.enableDashedLine(10f, 5f, 0f);
                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(Color.BLUE);
                    set1.setCircleColor(Color.BLUE);
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);
                    set1.setFormLineWidth(1f);
                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    set1.setFormSize(15.f);
                    if (Utils.getSDKInt() >= 18) {
                        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.banner);
                        set1.setFillDrawable(drawable);
                    } else {
                        set1.setFillColor(Color.DKGRAY);
                    }
                    set1.setFillColor(Color.DKGRAY);
                    //////////////////
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    LineData data = new LineData(dataSets);
                    chartEnergyID.setData(data);
                }
            }
            else
            {
                values.add(new Entry( i-1, i+2));
                LineDataSet set1;
                if (chartEnergyID.getData() != null &&
                        chartEnergyID.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chartEnergyID.getData().getDataSetByIndex(0);
                    set1.setValues(values);
                    chartEnergyID.getData().notifyDataChanged();
                    chartEnergyID.notifyDataSetChanged();
                } else {
                    set1 = new LineDataSet(values, "Total Energy");
                    set1.setDrawIcons(false);
                    set1.enableDashedLine(10f, 5f, 0f);
                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(Color.DKGRAY);
                    set1.setCircleColor(Color.DKGRAY);
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);
                    set1.setFormLineWidth(1f);
                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    set1.setFormSize(15.f);
                    if (Utils.getSDKInt() >= 18) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.banner);
                        set1.setFillDrawable(drawable);
                    } else {
                        set1.setFillColor(Color.DKGRAY);
                    }
                    set1.setFillColor(Color.DKGRAY);
                    //////////////////
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    LineData data = new LineData(dataSets);
                    chartEnergyID.setData(data);
                }
            }
        }*/




    }


    private void addFlowGraphFun()
    {

        BarDataSet barDataSet1 = new BarDataSet(bargroup2, "FLow");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);



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

/*
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        BarData AUTHModelData = new BarData(dataSets);
        chartFlowID.setData(AUTHModelData);*/
// initialize the Bardata with argument labels and dataSet
       // BarData data;
       // data = new BarData(labels, dataSets);
      //  data = new BarData(labels, dataSets);

       // chartFlowID.setData(data);

        ArrayList<Entry> values = new ArrayList<>();

        //  for (int i = 0; i < mEnergyGraphEnergy.size(); i++)
      /*  for (int i = 0; i < 10; i++)
        {

            //    mEnergyGraphEnergy
            if(i == 0)
            {
                // values.add(new Entry(0, mEnergyGraphEnergy.get(i).getCEnergyF()));
                values.add(new Entry(0, i+5));

                LineDataSet set1;
                if (chartFlowID.getData() != null &&
                        chartFlowID.getData().getDataSetCount() > 0) {
                   /set1 = (LineDataSet) chartFlowID.getData().getDataSetByIndex(0);
                   // set1 = (LineDataSet) chartFlowID.getData().getDataSetByIndex(0);
                    set1.setValues(values);
                    chartFlowID.getData().notifyDataChanged();
                    chartFlowID.notifyDataSetChanged();
                } else {
                    set1 = new LineDataSet(values, "Total Flow");
                    set1.setDrawIcons(false);
                    set1.enableDashedLine(10f, 5f, 0f);
                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(Color.DKGRAY);
                    set1.setCircleColor(Color.DKGRAY);
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);
                    set1.setFormLineWidth(1f);
                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    set1.setFormSize(15.f);
                    if (Utils.getSDKInt() >= 18) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.banner);
                        set1.setFillDrawable(drawable);
                    } else {
                        set1.setFillColor(Color.DKGRAY);
                    }
                    set1.setFillColor(Color.DKGRAY);
                    //////////////////
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    LineData data = new LineData(dataSets);
                    chartFlowID.setData(data);
                }

            }
            else
            {
                values.add(new Entry( i-3, i+8));
                LineDataSet set1;
                if (chartFlowID.getData() != null &&
                        chartFlowID.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chartFlowID.getData().getDataSetByIndex(0);
                    set1.setValues(values);
                    chartFlowID.getData().notifyDataChanged();
                    chartFlowID.notifyDataSetChanged();
                } else {
                    set1 = new LineDataSet(values, "Total Flow");
                    set1.setDrawIcons(false);
                    set1.enableDashedLine(10f, 5f, 0f);
                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(Color.DKGRAY);
                    set1.setCircleColor(Color.DKGRAY);
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);
                    set1.setFormLineWidth(1f);
                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    set1.setFormSize(15.f);
                    if (Utils.getSDKInt() >= 18) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.banner);
                        set1.setFillDrawable(drawable);
                    } else {
                        set1.setFillColor(Color.DKGRAY);
                    }
                    set1.setFillColor(Color.DKGRAY);
                    //////////////////
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    LineData data = new LineData(dataSets);
                    chartFlowID.setData(data);
                }
            }
        }*/




    }


}
