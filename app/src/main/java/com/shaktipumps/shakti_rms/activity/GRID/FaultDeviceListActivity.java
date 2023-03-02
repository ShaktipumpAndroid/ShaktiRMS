package com.shaktipumps.shakti_rms.activity.GRID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.adapter.DeviceFaultParametrAdapter;
import com.shaktipumps.shakti_rms.bean.GetPlant.FaultRecordMoel;
import com.shaktipumps.shakti_rms.bean.GetPlant.FaultRecordResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class FaultDeviceListActivity extends AppCompatActivity {
    private Context mContext;

    private RelativeLayout rlvBackViewID;
    private RecyclerView rclyFoultListView;
    private DeviceFaultParametrAdapter mDeviceFaultParametrAdapter;
    private BaseRequest baseRequest;

    private List<FaultRecordResponse> mFaultRecordResponse;

    private Intent myIntent;
    private String mPlantID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faolt_device_list);
        mContext = this;
        initView();
    }

    private void initView() {

        myIntent = getIntent(); // gets the previously created intent
        mPlantID = myIntent.getStringExtra("PlantID"); // will return "FirstKeyValue"

        baseRequest = new BaseRequest(this);
        mFaultRecordResponse = new ArrayList<>();

        rlvBackViewID = findViewById(R.id.rlvBackViewID);
        rclyFoultListView = findViewById(R.id.rclyFoultListView);

        rclyFoultListView.setLayoutManager(new LinearLayoutManager(this));




        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        callGetFaultListAPI();
    }

    private void callGetFaultListAPI() {

        baseRequest.showLoader();

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        FaultRecordMoel mFaultRecordMoel = gson.fromJson(Json, FaultRecordMoel.class);
                        getGetFaultList(mFaultRecordMoel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    baseRequest.hideLoader();
                    // new HomeFragment.Worker().execute();
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    baseRequest.hideLoader();
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                // jsonObject.addProperty("id", mUserID);
                //  jsonObject.addProperty("plantid", "1");
                jsonObject.addProperty("id", mPlantID);
                //    jsonObject.addProperty("plantid", "1");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.KLP_GRIDTIE_FAULT_LIST_API);/////

        }
        else
        {
            baseRequest.hideLoader();
        }
    }

    private void getGetFaultList(FaultRecordMoel mFaultRecordMoel) {

        if (mFaultRecordMoel.getStatus()) {

            if(mFaultRecordResponse != null && mFaultRecordResponse.size() > 0)
                mFaultRecordResponse.clear();

            mFaultRecordResponse = mFaultRecordMoel.getResponse();//setDataAdapter();

            mDeviceFaultParametrAdapter = new DeviceFaultParametrAdapter(mContext, mFaultRecordResponse);
            rclyFoultListView.setAdapter(mDeviceFaultParametrAdapter);

            baseRequest.hideLoader();

        }
        else
        {Toast.makeText(mContext, mFaultRecordMoel.getMessage(), Toast.LENGTH_LONG).show();

            baseRequest.hideLoader();
        }

    }

}
