package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
//import com.payu.india.Extras.PayUSdkDetails;
//import com.payu.india.Payu.Payu;
//import com.payu.india.Payu.PayuConstants;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.adapter.PaymentPlanAdapter;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.PaymentBean.PaymentModelView;
import com.shaktipumps.shakti_rms.bean.PaymentBean.PaymentPlanResponse;
import com.shaktipumps.shakti_rms.bean.PaymentBean.PayuBizResponse;
import com.shaktipumps.shakti_rms.bean.PaymentSuccessResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentOptionActivity extends AppCompatActivity {

    private String mPayuBizResponseString;
    private  RelativeLayout rlvBackViewID;
    private  ImageView imgHeaderID;
    private TextView txtHeaderID;
    private PayuBizResponse mPayuBizResponse;

    private DatabaseHelperTeacher databaseHelperTeacher;

    private List<PaymentPlanResponse> mPaymentPlanResponse = null;

    private List<Customer_GPS_Search> customerSearchesList = null;
    private int mPosition;
    private String vDeviceNo;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    int clientid = 0 ;
    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    float total_fault;
    int current_fault;
    private BaseRequest baseRequest;
    private Context mContext;
    private RecyclerView mMenuCardRecyclerView;

    private RecyclerView.Adapter recyclerViewAdapter;

    //PayUSdkDetails payUSdkDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        mContext = this;
        //TODO Must write below code in your activity to set up initial context for PayU
     //   Payu.setInstance(this);
        databaseHelperTeacher = new DatabaseHelperTeacher(mContext);///////
        initView();
    }

    private void initView() {

        try {
            //mPayuBizResponse = new mPayuBizResponse();

            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();

           //  payUSdkDetails = new PayUSdkDetails();
              MUserId = pref.getString("key_muserid", "invalid_muserid");
            clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (  clientid == 9999)
        {
            clientid = 0 ;
        }

        mPaymentPlanResponse = new ArrayList<>();

        mPosition = getIntent().getIntExtra("mPos",0);
        customerSearchesList = (List<Customer_GPS_Search>) getIntent().getSerializableExtra("mDeviceDetail");
        vDeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        MDeviceId = customerSearchesList.get(mPosition).getMDeviceId();

        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);
        txtHeaderID = (TextView) findViewById(R.id.txtHeaderID);

        baseRequest = new BaseRequest(mContext);
        mMenuCardRecyclerView = (RecyclerView) findViewById(R.id.mMenuCardRecyclerView);
        mMenuCardRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //txtHeaderID.setText(customerSearchesList.get(mPosition).getDeviceNo());

        setClickEvent();
        callPAymentOptionListAPI();
       // setDataAdapter();
    }

    private void setClickEvent() {

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                    finish();
            }
        });
    }

    private void callPAymentOptionListAPI() {
        if (CustomUtility.isOnline(mContext)){
            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        baseRequest.hideLoader();
                        Gson gson = new Gson();
                        //////////////add model class here
                        PaymentModelView mPaymentModelView = gson.fromJson(Json, PaymentModelView.class);
                        getUSPCListResponse(mPaymentModelView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            Map<String, String> wordsByKey = new HashMap<>();;

            wordsByKey.put("PlanId", "0");

            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            // baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_DEVICE_SETTING);/////
            baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.PAYMENT_PLAN_API);/////

        }
        else
        {
            //   mSettingModelResponse = databaseHelperTeacher.getDevicePARAMeterListData(DeviceType);

          /*  if(mSettingModelResponse.size() > 0){
                addDynamicViewPro(mSettingModelResponse);
            }
*/
        }
    }

    private void getUSPCListResponse(PaymentModelView mPaymentModelView) {

        if (mPaymentModelView.getStatus())
        {

            if(mPaymentPlanResponse != null && mPaymentPlanResponse.size() > 0)
                mPaymentPlanResponse.clear();

            mPaymentPlanResponse = mPaymentModelView.getResponse();


            boolean mCheckFirstDB;

            for (int i = 0; i < mPaymentPlanResponse.size(); i++) {

                if(i == 0)
                {
                    mCheckFirstDB = true;
                }
                else
                {
                    mCheckFirstDB = false;
                }

                //DeviceTyape = DeviceType;
                databaseHelperTeacher.insertPaymentOPTIONListData( DeviceNo,  DeviceType,  MDeviceId,  MUserId,  mPaymentPlanResponse.get(i).getPlanId(),  mPaymentPlanResponse.get(i).getPlanDuration(),  mPaymentPlanResponse.get(i).getPlanAmount(),  mPaymentPlanResponse.get(i).getPaymentStatus(),  mPaymentPlanResponse.get(i).getPlanDescription(),  mCheckFirstDB);

            }
            //  mClientToken = mPaymentTokenResponse.getToken();
            //  onBraintreeSubmit();
            setDataAdapter();
        }
    }
    private void setDataAdapter(){

        if (recyclerViewAdapter != null)
            recyclerViewAdapter = null;

        //   recyclerViewAdapter = new MenuAdapter(mMenuResponseList, mActivity);

      //  recyclerViewAdapter = new PaymentPlanAdapter(mContext,  payUSdkDetails, customerSearchesList, mPaymentPlanResponse, mPosition, MUserId, DeviceNo, mModelType , DeviceType);
       // recyclerViewAdapter = new PaymentPlanAdapter(mContext);
        mMenuCardRecyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
      /*  if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if (data != null) {

                //mPayuBizResponseString = data.getStringExtra("payu_response");
                 Gson gson = new Gson();
                 mPayuBizResponse = gson.fromJson(data.getStringExtra("payu_response"), PayuBizResponse.class);
                callPAymentTRNSSaveAPI();
                System.out.println("Payment_Response=="+data.getStringExtra("payu_response"));
              //  Toast.makeText(this, "Payment_Response=="+data.getStringExtra("result"), Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, getString(R.string.could_not_receive_data), Toast.LENGTH_LONG).show();
                //callPAymentTRNSSaveAPI();
            }*/
        // }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void callPAymentTRNSSaveAPI() {

        if (CustomUtility.isOnline(mContext)){
            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        baseRequest.hideLoader();
                        Gson gson = new Gson();
                        //////////////add model class here
                        PaymentSuccessResponse mPaymentSuccessResponse = gson.fromJson(Json, PaymentSuccessResponse.class);
                      //  getUSPCListResponse(mPaymentModelView);
                        if(mPaymentSuccessResponse.getStatus())
                        {
                            Toast.makeText(mContext,mPaymentSuccessResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("userId", MUserId);
                 jsonObject.addProperty("did", MDeviceId);///Model type value
                jsonObject.addProperty("planId", NewSolarVFD.PAYMENT_PLAN_ID);///Model type value
                jsonObject.addProperty("TransactionId", mPayuBizResponse.getTxnid());
                //   jsonObject.addProperty("DeviceNo", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("jsonObject=pu=>>"+jsonObject);
// {"userId":"5","did":"572","planId":"1","TransactionId":"0000"}
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.SAVE_TRANSACTION);/////
            // baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_DEVICE_SETTING);/////
            //baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.PAYMENT_PLAN_API);/////
        }
        else
        {
            //   mSettingModelResponse = databaseHelperTeacher.getDevicePARAMeterListData(DeviceType);
        }
    }

}
