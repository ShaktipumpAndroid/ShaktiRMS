package com.shaktipumps.shakti_rms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.adapter.USPCBridgeAdapter;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModel;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModelResponse;
import com.shaktipumps.shakti_rms.bean.UspcEnergy.UspcEnrgyResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationOptionUSPCFragment extends Fragment {
    String modeBusCommand = null;//write
    private Activity mActivity;
    private View mView;
    private BaseRequest baseRequest;
    private int pp = 0;

    private InputStream iStream = null;

    private CountDownTimer yourCountDownTimer;
    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    private TextView txtMotorStatusIID;

    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";
    float total_fault;
    int current_fault;

    SharedPreferences pref;

    private Context mContext;
    private DatabaseHelperTeacher mDatabaseHelperTeacher;
    List<UspcModelResponse> mUspcModelResponse;

    private UspcEnrgyResponse mUspcEnrgyResponse = null;
    private RecyclerView mMenuRecyclerView = null;
    private RecyclerView mMenuCardRecyclerView = null;

    private RecyclerView.Adapter recyclerViewAdapter;
    private DatabaseHelperTeacher databaseHelperTeacher;
    private boolean mCheckFirst;
    private int  mPosition;
    private List<Customer_GPS_Search> customerSearchesList = null;
    public ApplicationOptionUSPCFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public ApplicationOptionUSPCFragment(String MUserId, String MDeviceId, String DeviceNo, String DeviceType, String CustomerName, String Mobile, float total_fault, int current_fault, String isLoginCheck, String isPumpCheck, String mImageUrl, String mModelType, TextView txtMotorStatusIID, List<Customer_GPS_Search> customerSearchesList , int mPosition, UspcEnrgyResponse mUspcEnrgyResponse) {
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
        this.txtMotorStatusIID = txtMotorStatusIID;
        this.customerSearchesList = customerSearchesList;
        this.mPosition = mPosition;
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
        mView = inflater.inflate(R.layout.fragment_config_uspc, container, false);
        mActivity = getActivity();
        mContext = getActivity();
        databaseHelperTeacher = new DatabaseHelperTeacher(getActivity());///////
        initView();
        return mView;
    }

    private void initView() {

        mUspcModelResponse = new ArrayList<>();
      /*  GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
        baseRequest = new BaseRequest(mActivity);
        mMenuCardRecyclerView = (RecyclerView) mView.findViewById(R.id.mMenuCardRecyclerView);
        mMenuCardRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //mMenuCardRecyclerView.setAdapter(recyclerViewAdapter);
        if (CustomUtility.isOnline(mContext)) {
            callUSPCListAPI();
        }
        else
        {
            mUspcModelResponse = databaseHelperTeacher.getUSPCOPTIONListData();
            setDataAdapter();
        }
    }

    private void callUSPCListAPI() {

        if (CustomUtility.isOnline(mContext)){
            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        baseRequest.hideLoader();
                        Gson gson = new Gson();
                        //////////////add model class here
                        UspcModel mUspcModel = gson.fromJson(Json, UspcModel.class);
                        getUSPCListResponse(mUspcModel);

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

            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            // baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_DEVICE_SETTING);/////
            baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.USPCLIST_API);/////

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

    private void getUSPCListResponse(UspcModel mUspcModel) {

        if (mUspcModel.getStatus())
        {

            if(mUspcModelResponse != null && mUspcModelResponse.size() > 0)
                mUspcModelResponse.clear();

            mUspcModelResponse = mUspcModel.getResponse();



            boolean mCheckFirstDB;
            for (int i = 0; i < mUspcModelResponse.size(); i++) {

                if(i == 0)
                {
                    mCheckFirstDB = true;
                }
                else
                {
                    mCheckFirstDB = false;
                }
                String Address,  Divisible,  MDeviceNo,  MPId,  MPIndex,  MPName, Status, Unit, PMin, PMax, MODAddress, Offset;


                //DeviceTyape = DeviceType;
                //databaseHelperTeacher.insertDeviceParameterListData(Address, Divisible, MDeviceNo, MPId, MPIndex, MPName, Status, Unit, PMin, PMax, MODAddress, DeviceType, Offset, mCheckFirstDB);

                databaseHelperTeacher.insertUSPCOPTIONListData( DeviceNo,  DeviceType,  MDeviceId,  MUserId,  mUspcModelResponse.get(i).getId(),  mUspcModelResponse.get(i).getImage(),  mUspcModelResponse.get(i).getTitle(),  mUspcModelResponse.get(i).getDisplayIndex(),  mUspcModelResponse.get(i).getAddress(), mUspcModelResponse.get(i).getStatus(),  mUspcModelResponse.get(i).getBgColor(), mUspcModelResponse.get(i).getBTaddress(),  mCheckFirstDB);

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


        this.MUserId = MUserId;
        this.DeviceNo = DeviceNo;
        this.mModelType = mModelType;
        this.DeviceType = DeviceType;

        recyclerViewAdapter = new USPCBridgeAdapter(mContext, mActivity, customerSearchesList, mUspcModelResponse, mPosition, MUserId, DeviceNo, mModelType , DeviceType, mUspcEnrgyResponse);
        mMenuCardRecyclerView.setAdapter(recyclerViewAdapter);

    }

    ////////////////////bt stop command

}
