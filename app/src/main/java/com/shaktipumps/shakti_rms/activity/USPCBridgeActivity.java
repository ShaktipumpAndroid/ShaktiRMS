package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModel;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModelResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class USPCBridgeActivity extends AppCompatActivity {

    private ImageView mIvPrevious = null;
    private ImageView mIvNext = null;
    private ViewFlipper mViewFlipper = null;
    private RecyclerView mMenuRecyclerView = null;
    private RecyclerView mMenuCardRecyclerView = null;
    private Context mContext;
    private RecyclerView.Adapter recyclerViewAdapter;
    private BaseRequest baseRequest;
    private LinearLayout rlvDataContainer;

    private RelativeLayout rlvBackViewID;
    private ImageView imgHeaderID;

    private CircleImageView imgDeviceImageID;
    private TextView txtHeaderID;

    private List<String> nameList;

    String [] mSNameArray = {"Aata Chakki", "Cold Storage Refrigerator", "Motor-Thresher Set", "Motor-Pump Set", "Motor-Fodder Cutting Set"};
    int [] mimgNameArray = {R.drawable.chakki_set, R.drawable.deep_freser, R.drawable.thresher_set,R.drawable.pump_set,R.drawable.cutting_set};

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private List<Customer_GPS_Search> customerSearchesList = null;
    private int mPosition = 0;

    private String vMUserId,
            vDeviceType, vDeviceNo,vStartdate, isLoginCheck, isPumpCheck, vMDeviceId;
    private  String mImageUrl = null;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", MDeviceId = "null", CustomerName = "null", mModelType = "null",Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    int clientid = 0 ;

    List<UspcModelResponse> mUspcModelResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uspcbridge);
        mContext = this;
        nameList = new ArrayList<>();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        initView();

    }

    private void initView() {
        baseRequest = new BaseRequest(mContext);


        mUspcModelResponse = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // lvlmCardHorizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


        rlvDataContainer = (LinearLayout) findViewById(R.id.rlvDataContainer);
        rlvDataContainer = (LinearLayout) findViewById(R.id.rlvDataContainer);
        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        mMenuCardRecyclerView = (RecyclerView) findViewById(R.id.mMenuCardRecyclerView);
        mMenuCardRecyclerView.setLayoutManager(gridLayoutManager);
        /*mMenuCardRecyclerView.getLayoutParams().width = Validation.getDeviceHeightWidth(getActivity(), true);
        mMenuCardRecyclerView.getLayoutParams().height = Validation.getDeviceHeightWidth(getActivity(), false)/4+60;*/

        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        mIvPrevious = (ImageView) findViewById(R.id.mIvPrevious);
        mIvNext = (ImageView) findViewById(R.id.mIvNext);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);
        imgDeviceImageID = (CircleImageView) findViewById(R.id.imgDeviceImageID);
        txtHeaderID = (TextView) findViewById(R.id.txtHeaderID);

        mPosition = getIntent().getIntExtra("mPos",0);
        customerSearchesList = (List<Customer_GPS_Search>) getIntent().getSerializableExtra("mDeviceDetail");
        vDeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        DeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        DeviceType = customerSearchesList.get(mPosition).getDeviceType();
        CustomerName = customerSearchesList.get(mPosition).getCustomer_name();
        Mobile = customerSearchesList.get(mPosition).getMobile();
        isLoginCheck = customerSearchesList.get(mPosition).getIsLogin();
        mModelType = customerSearchesList.get(mPosition).getModelType();
        isPumpCheck = customerSearchesList.get(mPosition).getPumpStatus();
        mImageUrl = customerSearchesList.get(mPosition).getDeviceImage();

        //MUserId = customerSearchesList.get(mPosition).getMUserId();
        try {
            MUserId = pref.getString("key_muserid", "invalid_muserid");

            clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (  clientid == 9999)
        {
            clientid = 0 ;
        }

        /*for (int i = 0; i <mSNameArray.length ; i++) {

            nameList.add(mSNameArray[i]);
            imgList.add(mimgNameArray[i]);
        }
*/

        txtHeaderID.setText(customerSearchesList.get(mPosition).getDeviceNo());

        if(Constant.CHECK_BACK_MENU_VIEW_ICON == 1)
        {
            imgHeaderID.setImageResource(R.drawable.icn_menu);
        }
        else
        {
            imgHeaderID.setImageResource(R.drawable.icn_back);
        }

        if(mImageUrl.equalsIgnoreCase(""))
        {

        }else
        {
            Picasso.with(this).load(mImageUrl).placeholder(R.drawable.logo).into(imgDeviceImageID);
        }



        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                if(Constant.CHECK_BACK_MENU_VIEW_ICON == 1)
                {
                    //rlvSlideMenuViewID.setVisibility(View.VISIBLE);
                    overridePendingTransition(R.anim.lefr_to_right, R.anim.right_to_left);
                }
                else
                {
                    finish();
                }
            }
        });

        callUSPCListAPI();
       // setData();
    }

    public void setData() {

        for (int i = 0; i < mimgNameArray.length; i++)
        {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
           /* Picasso.with(mContext).load(mMenuSliderList.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);*/
             imageView.setImageResource(mimgNameArray[i]);
            //  imageView.setBackgroundResource(mIconInt[i]);
            mViewFlipper.addView(imageView);


        }
        mViewFlipper.startFlipping();
        mIvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFlipper.showPrevious();

            }
        });

        mIvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewFlipper.showNext();
            }
        });


       // setDataAdapter();

    }

    private void setDataAdapter() {

            if (recyclerViewAdapter != null)
                recyclerViewAdapter = null;

         //   recyclerViewAdapter = new MenuAdapter(mMenuResponseList, mActivity);
          /*  recyclerViewAdapter = new USPCBridgeAdapter(mContext, customerSearchesList, mUspcModelResponse, mPosition);
            mMenuCardRecyclerView.setAdapter(recyclerViewAdapter);*/

    }


    ////////////////vikas service calling new
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

            }
            //  mClientToken = mPaymentTokenResponse.getToken();
            //  onBraintreeSubmit();

            setDataAdapter();

        }


    }





}
