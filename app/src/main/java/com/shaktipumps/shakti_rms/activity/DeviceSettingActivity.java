package com.shaktipumps.shakti_rms.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelResponse;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceSettingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    Context mContext ;
    Activity mActivity ;
    SwitchCompat switchCompat ;
    String speed_mode_param_value  = "null" ;
    private ProgressDialog progressDialog;
    String speed_mode = "null";
  //  TextView read_speed_mode,change_speed_mode,speed_mode_type ;
    boolean read_only = true;
    boolean  speed_checked ;
    String  MUserId = "null" ,DeviceType = "null" ,DeviceNo = "null",Mobile="null",CustomerName="null",
            RMSingalStr = "null",RMStatusOfProduct = "null" , RMLatitude = "null",RMDate = " " ;

    private RecyclerView rclSettingListViewID;

    int kPosition = 0;

    private LinearLayoutManager lLayout;
    private RecyclerView.Adapter recyclerViewAdapter;
    private BaseRequest baseRequest;

    private List<SettingModelResponse> mSettingModelResponse;
    private DatabaseHelperTeacher databaseHelperTeacher;

    private BluetoothSocket btSocket;
    private BluetoothAdapter myBluetooth;

    private UUID mMyUDID;
    private InputStream iStream;

      private List<EditText> mEditTextList;
    private List<TextView> mTextViewSetIDtList;
    private List<TextView> mTextViewSetIDtListPOS;

    RelativeLayout iv_sub_linearlayout12;
    LinearLayout lvlMainParentLayoutID;

    RelativeLayout rlvMainDynamicViewID;

    CardView cardViewAddDynamicViewID;


    private int edtValue = 0;
    private float edtValueFloat = 0;
    private String old_data = "1";


    int i = 0;

    int mGlobalPosition = 0;

    ImageView imgRefreshiconID;
    LinearLayout lvlBackIconViewID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting);

        mEditTextList = new ArrayList<>();
        mTextViewSetIDtList = new ArrayList<>();
        mSettingModelResponse = new ArrayList<>();
        mTextViewSetIDtListPOS = new ArrayList<>();


        baseRequest = new BaseRequest(this);

        databaseHelperTeacher = new DatabaseHelperTeacher(this);

        rclSettingListViewID = (RecyclerView) findViewById(R.id.rclSettingListViewID);

        lvlBackIconViewID = (LinearLayout) findViewById(R.id.lvlBackIconViewID);
        imgRefreshiconID = (ImageView) findViewById(R.id.imgRefreshiconID);

        lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        rclSettingListViewID.setNestedScrollingEnabled(false);
        rclSettingListViewID.setLayoutManager(lLayout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mContext = this;
        mActivity = this;
        setSupportActionBar(mToolbar);


       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_device_setting);*/


        Bundle bundle = getIntent().getExtras();
        MUserId = bundle.getString("MUserId");
        DeviceType = bundle.getString("DeviceType");
        DeviceNo = bundle.getString("DeviceNo");

        initView();
       // Toast.makeText(mContext, "CustomUtility.getDeviceId(mContext)==>>"+CustomUtility.getDeviceId(mContext), Toast.LENGTH_SHORT).show();
        callSettingParameterValueAPI();

    }

    private void initView() {

        lvlMainParentLayoutID = (LinearLayout) findViewById(R.id.lvlMainParentLayoutID);

        imgRefreshiconID.setVisibility(View.GONE);
        lvlBackIconViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgRefreshiconID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseRequest.showLoader();
                mGlobalPosition = 0;
                getDeviceModeRefresh(mSettingModelResponse.get(mGlobalPosition).getAddress(),mSettingModelResponse.get(mGlobalPosition).getOffset());
            }
        });



    }

    @Override
    public void onDestroy() {
        try {
            progressDialog.dismiss();

            if(progressDialog !=null)
                progressDialog = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void   getDeviceMode(){

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();


                if (CustomUtility.isOnline(mContext)) {

                    try {
                        String obj = "";

                        param.clear();
                        param.add(new BasicNameValuePair("address1","2" )); // speed mode
                        param.add(new BasicNameValuePair("did1",DeviceNo )); // gps no
                        param.add(new BasicNameValuePair("RW","0" ));          // 0 = read
                        param.add(new BasicNameValuePair("data1","1" ));               // start = 1
                        param.add(new BasicNameValuePair("OldData","1" ));
                        param.add(new BasicNameValuePair("UserId",MUserId ));
                        param.add(new BasicNameValuePair("DeviceType",DeviceType ));
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                        if (!TextUtils.isEmpty(obj)) {

                            progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                              //Log.d("Speed", obj );
                            for (int i = 0; i < ja.length(); i++)
                            {
                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                if( jo.getString("Result").equalsIgnoreCase("2.0"))
                                {
                                    speed_mode = jo.getString("Longitude") ;
                                }

                                // disconnect message
                                if( jo.getString("Result").equalsIgnoreCase("D"))
                                {
                                    CustomUtility.isErrorDialog(mContext,mContext.getString(R.string.error),mContext.getString(R.string.err_device_disconnect));
                                }

                                // command failed message
                                if(  !jo.getString("Result").equalsIgnoreCase("2.0") &&
                                     !jo.getString("Result").equalsIgnoreCase("D"))
                                {
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error),mContext.getString(R.string.err_read_mode_fail));

                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        progressDialog.dismiss();
                        Log.d("exce", "" + e);
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);
                }

    }

    public  void   setDeviceMode(){

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        String old_data = "null" ;

        if (speed_mode.equalsIgnoreCase("AUTO"))
        {
            speed_mode_param_value = "2";
        }
            if (speed_mode.equalsIgnoreCase("MANUAL"))
        {
            speed_mode_param_value = "1";
        }

        if (speed_mode_param_value.equalsIgnoreCase("2"))
        {
            old_data = "1";
        }

        if (speed_mode_param_value.equalsIgnoreCase("1"))
        {
            old_data = "2";
        }
        //Log.d("speed_mode_param_value", speed_mode_param_value +"--"+  old_data) ;
        if (CustomUtility.isOnline(mContext)) {
            try {
                String obj = "";

                param.clear();
                param.add(new BasicNameValuePair("address1","2" )); // speed mode
                param.add(new BasicNameValuePair("did1",DeviceNo )); // gps no
                param.add(new BasicNameValuePair("RW","1" ));        // 1 = write
                param.add(new BasicNameValuePair("data1",  speed_mode_param_value ));     // 1 = auto 2 = manual
                param.add(new BasicNameValuePair("OldData", old_data ));
                param.add(new BasicNameValuePair("UserId",MUserId ));
                param.add(new BasicNameValuePair("DeviceType",DeviceType ));
                //  param.add(new BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                 param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile

                obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                if (!TextUtils.isEmpty(obj)) {

                    progressDialog.dismiss();
                    JSONArray ja = new JSONArray(obj);
                   // Log.d("Speed12", obj );
                    for (int i = 0; i < ja.length(); i++)
                    {
                        JSONObject jo = ja.getJSONObject(i);
                        //success message
                        if( jo.getString("Result").equalsIgnoreCase("2.0"))
                        {
                            speed_mode = jo.getString("Longitude") ;
                        }

                        // disconnect message
                        if( jo.getString("Result").equalsIgnoreCase("D"))
                        {
                            read_only = false ;
                            CustomUtility.isErrorDialog(mContext,mContext.getString(R.string.error),mContext.getString(R.string.err_device_disconnect));
                        }
                        // command failed message
                        if(  !jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D"))
                        {
                            read_only = false ;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_set_mode_fail));
                        }
                    }
                }
            }
            catch (Exception e) {

                progressDialog.dismiss();
                Log.d("exce", "" + e);
            }
        }
        else
        {
            progressDialog.dismiss();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);

        }
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };

    ////////////////vikas service calling new
    private void callSettingParameterValueAPI() {

        if (CustomUtility.isOnline(mContext)){

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    SettingModelView mSettingModelView = gson.fromJson(Json, SettingModelView.class);
                    getDeviceSettingListResponse(mSettingModelView);

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
            jsonObject.addProperty("DeviceTypeID", DeviceType);

            System.out.println("VikasVV1==>"+jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_DEVICE_SETTING);/////

    }
        else
        {
            mSettingModelResponse = databaseHelperTeacher.getDevicePARAMeterListData(DeviceType);

            if(mSettingModelResponse.size() > 0){
                addDynamicViewPro(mSettingModelResponse);
            }

        }
    }

    private void getDeviceSettingListResponse(SettingModelView mSettingModelView) {

        if (mSettingModelView.getStatus())
        {

            if(mSettingModelResponse != null && mSettingModelResponse.size() > 0)
                mSettingModelResponse.clear();

           // mSettingModelResponse = mSettingModelView.getResponse();
          mSettingModelResponse = mSettingModelView.getResponse();

            if(mSettingModelResponse.size() > 0){
                addDynamicViewPro(mSettingModelResponse);
            }

            boolean mCheckFirstDB;
            for (int i = 0; i < mSettingModelResponse.size(); i++) {

                if(i == 0)
                {
                    mCheckFirstDB = true;
                }
                else
                {
                    mCheckFirstDB = false;
                }
                String Address,  Divisible,  MDeviceNo,  MPId,  MPIndex,  MPName, Status, Unit, PMin, PMax, MODAddress, Offset;

                Address = mSettingModelResponse.get(i).getAddress();
                Divisible = String.valueOf(mSettingModelResponse.get(i).getDivisible());
                MDeviceNo = String.valueOf(mSettingModelResponse.get(i).getMDeviceNo());
              //  MDeviceNo = mSettingModelResponse.get(i).getMDeviceNo();
                MPId = mSettingModelResponse.get(i).getMPId();
                MPIndex = String.valueOf(mSettingModelResponse.get(i).getMPIndex());
                MPName = mSettingModelResponse.get(i).getMPName();
                Status = mSettingModelResponse.get(i).getStatus();
                Unit = mSettingModelResponse.get(i).getUnit();
                PMin = String.valueOf(mSettingModelResponse.get(i).getPMin());
                PMax = String.valueOf(mSettingModelResponse.get(i).getPMax());
                MODAddress = mSettingModelResponse.get(i).getMobBTAddress()+"";
                Offset = mSettingModelResponse.get(i).getOffset()+"";
                System.out.println("00Offset_out=="+Offset);
                //DeviceTyape = DeviceType;
                databaseHelperTeacher.insertDeviceParameterListData(Address, Divisible, MDeviceNo, MPId, MPIndex, MPName, Status, Unit, PMin, PMax, MODAddress, DeviceType, Offset, mCheckFirstDB);

            }
            //  mClientToken = mPaymentTokenResponse.getToken();
            //  onBraintreeSubmit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addDynamicViewPro(final List<SettingModelResponse> mSettingModelResponse) {

        try {
            if(mEditTextList.size() > 0)
            {
                mEditTextList.clear();
            }
            if(mTextViewSetIDtList.size() > 0)
            {
                mTextViewSetIDtList.clear();
            }
            if(mTextViewSetIDtListPOS.size() > 0)
            {
                mTextViewSetIDtListPOS.clear();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ///  addHeadersMonths();
                    lvlMainParentLayoutID.removeAllViews();
                    // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                }
            });


        } catch (Exception exp) {
            exp.printStackTrace();
        }

        for ( i = 0; i < mSettingModelResponse.size(); i++)
        //for (int i = 0; i < 10; i++)
        {

            cardViewAddDynamicViewID = new CardView(this);
            CardView.LayoutParams cardViewAddDynamicViewIDoutparams12 = new CardView.LayoutParams
                    ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);
            cardViewAddDynamicViewIDoutparams12.setMarginEnd((int) getResources().getDimension(R.dimen._5sdp));
            cardViewAddDynamicViewIDoutparams12.setMarginStart((int) getResources().getDimension(R.dimen._5sdp));
            cardViewAddDynamicViewIDoutparams12.setMargins((int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._2sdp));
            cardViewAddDynamicViewID.setCardBackgroundColor(getResources().getColor(R.color.white));
            cardViewAddDynamicViewID.setRadius(15);

           // cardViewAddDynamicViewID.setId(mIDIndex + 1);
            cardViewAddDynamicViewID.setElevation(10);
            cardViewAddDynamicViewID.setLayoutParams(cardViewAddDynamicViewIDoutparams12);

            //lvlMainParentLayoutID.add(cardViewAddDynamicViewID);

            rlvMainDynamicViewID = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlvMainDynamicViewIDParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen._80sdp));

          //  rlvMainDynamicViewIDParam.setMargins(10, 5, 10, 5);
            rlvMainDynamicViewIDParam.setMargins((int) getResources().getDimension(R.dimen._3sdp), (int) getResources().getDimension(R.dimen._3sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._5sdp));
            rlvMainDynamicViewIDParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            rlvMainDynamicViewID.setLayoutParams(rlvMainDynamicViewIDParam);

            // cardViewAddDynamicViewID.addView(rlvMainDynamicViewID);

            iv_sub_linearlayout12 = new RelativeLayout(this);
            RelativeLayout.LayoutParams iv_outparams12 = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.MATCH_PARENT);
            iv_outparams12.setMarginEnd((int) getResources().getDimension(R.dimen._3sdp));
            iv_outparams12.setMarginStart((int) getResources().getDimension(R.dimen._3sdp));
            iv_outparams12.setMargins((int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._2sdp));
           // iv_sub_linearlayout12.setBackgroundColor(getResources().getColor(R.color.white));
            //  iv_sub_linearlayout12.setOrientation(LinearLayout.HORIZONTAL);
            iv_sub_linearlayout12.setLayoutParams(iv_outparams12);

            TextView txtPeraNameID = new TextView(this);
            RelativeLayout.LayoutParams txtFromTextHeadParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen._25sdp));
            //txtFromTextHeadParam.setMarginStart(20);
            // txtFromTextHeadParam.setMargins(0, 5, 0, 5);
            txtFromTextHeadParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            txtPeraNameID.setGravity(Gravity.CENTER_HORIZONTAL);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
            // txtPeraNameID.setText(getResources().getString(R.string.Get_text));
            txtPeraNameID.setText(mSettingModelResponse.get(i).getMPName());
            /// txtPeraNameID.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.textsize));
            txtPeraNameID.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
            txtPeraNameID.setId(i+1);
            txtPeraNameID.setTypeface(null, Typeface.NORMAL);
            txtPeraNameID.setTextColor(getResources().getColor(R.color.blue_fb));
            txtPeraNameID.setLayoutParams(txtFromTextHeadParam);
            iv_sub_linearlayout12.addView(txtPeraNameID);

            RelativeLayout rlvMainViewLayoutIN = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlvMainParamIN = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._40sdp));
            rlvMainParamIN.setMargins((int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._1sdp));
            rlvMainParamIN.addRule(RelativeLayout.BELOW, txtPeraNameID.getId());
            rlvMainViewLayoutIN.setLayoutParams(rlvMainParamIN);
            iv_sub_linearlayout12.addView(rlvMainViewLayoutIN);

            TextView txtGetID = new TextView(this);
            RelativeLayout.LayoutParams txtGetIDParam = new RelativeLayout.LayoutParams
                    ((int) (int) getResources().getDimension(R.dimen._60sdp), (int) (int) getResources().getDimension(R.dimen._40sdp));
            //txtGetIDParam.setMarginStart(20);
            // txtGetIDParam.setMargins(0, 0, 0, 0);
            txtGetIDParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            txtGetID.setGravity(Gravity.CENTER);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
            txtGetID.setText(getResources().getString(R.string.Get_text));
            txtGetID.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
            txtGetID.setId(i+2);
            txtGetID.setTypeface(null, Typeface.NORMAL);

            txtGetID.setTextColor(getResources().getColor(R.color.white));
            txtGetID.setBackground(getResources().getDrawable(R.drawable.round_green_shape));
           // txtGetID.setBackground(getResources().getDrawable(R.drawable.blue_btn_round));
            txtGetID.setLayoutParams(txtGetIDParam);
            rlvMainViewLayoutIN.addView(txtGetID);

            txtGetID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int iii = v.getId();
                    int pp = iii-2;

                    mGlobalPosition = pp;

                    getDeviceMode(mSettingModelResponse.get(pp).getAddress(),mSettingModelResponse.get(pp).getOffset());
                  //  new BluetoothCommunicationForDynamicParameterRead().execute(modeBusCommand, modeBusCommand, "OK");
                }
            });


            TextView txtSetID = new TextView(this);
            RelativeLayout.LayoutParams txtSetIDParam = new RelativeLayout.LayoutParams
                    ((int)(int) getResources().getDimension(R.dimen._60sdp), (int) (int) getResources().getDimension(R.dimen._40sdp));
            //txtSetIDParam.setMarginStart(20);
            // txtSetIDParam.setMargins(0, 0, 0, 0);
            txtSetIDParam.addRule(RelativeLayout.ALIGN_PARENT_END);
            txtSetID.setGravity(Gravity.CENTER);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
            txtSetID.setText(getResources().getString(R.string.Set_text));
            txtSetID.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
            txtSetID.setId(i+3);
           // txtGetID.setTag(i+3);
            txtSetID.setTypeface(null, Typeface.NORMAL);

            txtSetID.setTextColor(getResources().getColor(R.color.white));
          //  txtSetID.setBackground(getResources().getDrawable(R.drawable.blue_btn_round));
            txtSetID.setBackground(getResources().getDrawable(R.drawable.round_red_shape));
            txtSetID.setLayoutParams(txtSetIDParam);

            mTextViewSetIDtList.add(txtSetID);
            //if(mSettingModelResponse.get(i).getEditValue() != null && !mSettingModelResponse.get(i).getEditValue().equalsIgnoreCase(""))

            String sssss = String.valueOf(mSettingModelResponse.get(i).getEditValue());

            if(!sssss.equalsIgnoreCase("0.0") && String.valueOf(mSettingModelResponse.get(i).getEditValue()) != null && !String.valueOf(mSettingModelResponse.get(i).getEditValue()).equalsIgnoreCase(""))
            {
                changeButtonVisibility(true, 1.0f, mTextViewSetIDtList.get(i));
            }
            else
            {
                changeButtonVisibility(false, 0.5f, mTextViewSetIDtList.get(i));
            }
            rlvMainViewLayoutIN.addView(txtSetID);


            txtSetID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pp= 0;
                    try {
                        int iii = v.getId();
                         pp = iii-3;
                        mGlobalPosition = pp;
                       // Toast.makeText(mContext, "jai hooo...==>>  "+pp, Toast.LENGTH_SHORT).show();


                       // edtValue = Integer.parseInt(mEditTextList.get(pp).getText().toString().trim());
                        edtValueFloat = Float.parseFloat(mEditTextList.get(pp).getText().toString().trim());

                        if (edtValueFloat > mSettingModelResponse.get(pp).getPMin() && edtValueFloat < mSettingModelResponse.get(pp).getPMax()) {
                            /*  setDeviceMode(mSettingModelResponse.get(position).getAddress());*/
                            setDeviceMode(mSettingModelResponse.get(pp).getAddress(),mSettingModelResponse.get(pp).getOffset());
                            //changeButtonVisibility(true, 1.0f, holder);

                        } else {
                            Toast.makeText(mContext, "Please enter value between min=" + mSettingModelResponse.get(pp).getPMin() + " Max=" + mSettingModelResponse.get(pp).getPMax(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        edtValueFloat = mSettingModelResponse.get(pp).getEditValue();
                        //edtValue = Integer.parseInt(mEditTextList.get(pp).getText().toString().trim());
                        setDeviceMode(mSettingModelResponse.get(pp).getAddress(),mSettingModelResponse.get(pp).getOffset());
                        e.printStackTrace();
                    }
                }
            });


            RelativeLayout rlvEDITLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlvEDITParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) (int) getResources().getDimension(R.dimen._40sdp));
            //rlvEDITParam.setMargins(130, 1, 130, 1);

            //rlvEDITParam.addRule(RelativeLayout.LEFT_OF, txtGetID.getId());
          // rlvEDITParam.addRule(RelativeLayout.RIGHT_OF, txtSetID.getId());
            rlvEDITParam.setMarginStart((int) getResources().getDimension(R.dimen._65sdp));
            rlvEDITParam.setMarginEnd((int) getResources().getDimension(R.dimen._65sdp));
            rlvEDITParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlvEDITLayout.setLayoutParams(rlvEDITParam);
            rlvEDITLayout.setBackground(getResources().getDrawable(R.drawable.shapeedittxt));
            rlvMainViewLayoutIN.addView(rlvEDITLayout);
            //iv_sub_linearlayout12.addView(rlvEDITLayout);


            EditText edtValueID = new EditText(this);
            RelativeLayout.LayoutParams edtValueIDParam = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            //edtValueIDParam.setMarginStart(20);
            edtValueIDParam.addRule(RelativeLayout.ALIGN_PARENT_START);
            edtValueIDParam.setMargins((int) getResources().getDimension(R.dimen._5sdp), (int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._3sdp), (int) getResources().getDimension(R.dimen._1sdp));
            edtValueID.setGravity(Gravity.CENTER_VERTICAL);
            String ssssss = String.valueOf(mSettingModelResponse.get(i).getEditValue());

            if(!ssssss.equalsIgnoreCase("0.0") && String.valueOf(mSettingModelResponse.get(i).getEditValue()) != null && !String.valueOf(mSettingModelResponse.get(i).getEditValue()).equalsIgnoreCase(""))
            {
                edtValueID.setText(mSettingModelResponse.get(i).getEditValue()+"");
            }
            else
            {
                edtValueID.setText("");
            }
           // edtValueID.setText(mSettingModelResponse.get(i).getEditValue()+"");
            // edtValueID.setHint(getResources().getString(R.string.Get_text));

            edtValueID.setTextColor(getResources().getColor(R.color.black));
            edtValueID.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            edtValueID.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
            edtValueID.setId(i + 4);
            edtValueID.setMaxLines(1);
            edtValueID.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtValueID.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
            edtValueID.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

            edtValueID.setTypeface(null, Typeface.NORMAL);

            edtValueID.setTextColor(getResources().getColor(R.color.black));
            edtValueID.setLayoutParams(edtValueIDParam);
            rlvEDITLayout.addView(edtValueID);

            mEditTextList.add(edtValueID);


            TextView txtUNITID = new TextView(this);
            RelativeLayout.LayoutParams txtUNITIDParam = new RelativeLayout.LayoutParams
                    ((int) (int) getResources().getDimension(R.dimen._50sdp), (int) (int) RelativeLayout.LayoutParams.MATCH_PARENT);
            //txtUNITIDParam.setMarginStart(20);
            // txtUNITIDParam.setMargins(0, 0, 0, 0);
            txtUNITIDParam.addRule(RelativeLayout.ALIGN_PARENT_END);
            txtUNITID.setGravity(Gravity.CENTER);
            //   tv_Code.setText( mNRKProductDetailsVariantResponseListin.get(i).getVariantModalNumber());
            // txtUNITID.setText(getResources().getString(R.string.Set_text));
            txtUNITID.setText(mSettingModelResponse.get(i).getUnit());
            txtUNITID.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
            // txtGetID.setId(i + 5);

            txtUNITID.setTypeface(null, Typeface.NORMAL);
            //  tv_Code.setId(i + 2);
            txtUNITID.setTextColor(getResources().getColor(R.color.blue_fb));
            // txtUNITID.setBackground(getResources().getDrawable(R.drawable.blue_btn_round));
            txtUNITID.setLayoutParams(txtUNITIDParam);
            rlvEDITLayout.addView(txtUNITID);

            //mTagIDIndex++;
            rlvMainDynamicViewID.addView(iv_sub_linearlayout12);
            cardViewAddDynamicViewID.addView(rlvMainDynamicViewID);
            lvlMainParentLayoutID.addView(cardViewAddDynamicViewID);

        }
    }

    private void changeButtonVisibility(boolean state, float alphaRate, TextView text) {
        text.setEnabled(state);
        text.setAlpha(alphaRate);
    }

    public void getDeviceModeRefresh(final String address,  final int offsetValue)
    {
        // baseRequest.showLoader();
        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        new Thread(new Runnable() {


            @Override
            public void run() {

                if (CustomUtility.isOnline(mContext)) {
                    baseRequest.showLoader();
                    try {
                        String obj = "";
                        param.clear();
                        param.add(new BasicNameValuePair("address1", address)); // speed mode
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "0"));          // 0 = read
                        param.add(new BasicNameValuePair("data1", "1"));               // start = 1
                        param.add(new BasicNameValuePair("OldData", "1"));
                        param.add(new BasicNameValuePair("UserId", MUserId));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile
                        //param.add(new BasicNameValuePair("offset1", "1"));
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                        if (!TextUtils.isEmpty(obj)) {
                            // progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                            //Log.d("Speed", obj );
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                                  //  progressDialog.dismiss();
                                    //baseRequest.hideLoader();
                                    speed_mode = jo.getString("Longitude");
                                   final String mLat= jo.getString("Latitude");

                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mEditTextList.get(mGlobalPosition).setText(""+Float.parseFloat(mLat));
                                            changeButtonVisibility(true,1.0f,mTextViewSetIDtList.get(mGlobalPosition));
                                            //addDynamicViewPro(mSettingModelResponse);
                                           // mSettingModelResponse
                                            mGlobalPosition++;
                                            if(mSettingModelResponse != null && mGlobalPosition < mSettingModelResponse.size() )
                                            {
                                                new Handler().postDelayed(
                                                        new Runnable() {
                                                            public void run() {
                                                                baseRequest.showLoader();
                                                                // new DeviceSettingBTShimhaActivity.BluetoothCommunicationForDynamicParameterReadRefresh().execute(modeBusCommand, modeBusCommand, "OK");
                                                                //Log.i("tag","A Kiss after 5 seconds");
                                                                System.out.println("v++++++==>> "+ mGlobalPosition);
                                                                System.out.println("Address==>> "+ mSettingModelResponse.get(mGlobalPosition).getAddress()+"\n offset===>>>"+mSettingModelResponse.get(mGlobalPosition).getOffset());
                                                                System.out.println("offset===>>> "+mSettingModelResponse.get(mGlobalPosition).getOffset());

                                                                getDeviceModeRefresh(mSettingModelResponse.get(mGlobalPosition).getAddress(),mSettingModelResponse.get(mGlobalPosition).getOffset());

                                                            }
                                                        }, 2000);
                                            }
                                            else
                                            {
                                                baseRequest.hideLoader();
                                            }

                                      }
                                    });

                                }
                                // disconnect message
                                if (jo.getString("Result").equalsIgnoreCase("D")) {
                                  //  progressDialog.dismiss();
                                    baseRequest.hideLoader();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                                }
                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                   /// progressDialog.dismiss();
                                   // baseRequest.hideLoader();
                                   // CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_read_mode_fail));
                                    new Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    baseRequest.showLoader();
                                                    getDeviceModeRefresh(address,offsetValue);

                                                }
                                            }, 2000);
                                }
                            }
                        }
                    } catch (Exception e) {
                      //  progressDialog.dismiss();
                        baseRequest.hideLoader();
                        //  baseRequest.hideLoader();
                        e.printStackTrace();
                        Log.d("exce", "" + e);
                    }
                } else {
                    //yourCountDownTimer.cancel();
                    //progressDialog.dismiss();
                    baseRequest.hideLoader();
                    Message msg = new Message();
                    msg.obj = "Please check internet connection!";
                    mHandler.sendMessage(msg);
                }
            }
        }).start();

    }

    public void getDeviceMode(final String address,  final int offsetValue)
    {

         baseRequest.showLoader();
        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        new Thread(new Runnable() {


            @Override
            public void run() {

                if (CustomUtility.isOnline(mContext)) {
                    baseRequest.showLoader();
                    try {
                        String obj = "";
                        param.clear();
                        param.add(new BasicNameValuePair("address1", address)); // speed mode
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "0"));          // 0 = read
                        param.add(new BasicNameValuePair("data1", "1"));               // start = 1
                        param.add(new BasicNameValuePair("OldData", "1"));
                        param.add(new BasicNameValuePair("UserId", MUserId));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile
                        //param.add(new BasicNameValuePair("offset1", "1"));
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                        if (!TextUtils.isEmpty(obj)) {
                            // progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                            //Log.d("Speed", obj );
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                                  //  progressDialog.dismiss();
                                    baseRequest.hideLoader();
                                    speed_mode = jo.getString("Longitude");
                                   final String mLat= jo.getString("Latitude");

                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mEditTextList.get(mGlobalPosition).setText(""+Float.parseFloat(mLat));
                                            changeButtonVisibility(true,1.0f,mTextViewSetIDtList.get(mGlobalPosition));
                                            //addDynamicViewPro(mSettingModelResponse);
                                            baseRequest.hideLoader();
                                      }
                                    });

                                }
                                // disconnect message
                                if (jo.getString("Result").equalsIgnoreCase("D")) {
                                  //  progressDialog.dismiss();
                                    baseRequest.hideLoader();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                                    baseRequest.hideLoader();
                                }
                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                   /// progressDialog.dismiss();
                                    baseRequest.hideLoader();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_read_mode_fail));
                                    baseRequest.hideLoader();
                                }
                            }
                        }
                    } catch (Exception e) {
                      //  progressDialog.dismiss();
                        baseRequest.hideLoader();
                        //  baseRequest.hideLoader();
                        e.printStackTrace();
                        Log.d("exce", "" + e);
                    }
                } else {
                    //yourCountDownTimer.cancel();
                    //progressDialog.dismiss();
                    baseRequest.hideLoader();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);
                }
            }
        }).start();

    }

    public void setDeviceMode(final String address, final int offsetValue) {

      /*  progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
*/
         baseRequest.showLoader();
        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        new Thread(new Runnable() {

            @Override
            public void run() {

                if (CustomUtility.isOnline(mContext)) {
                    baseRequest.showLoader();
                    try {
                        String obj = "";
                        param.clear();
                        param.add(new BasicNameValuePair("address1", address)); // speed mode
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "1"));        // 1 = write
                       // param.add(new BasicNameValuePair("data1", "" + edtValue));     // 1 = auto 2 = manual
                        param.add(new BasicNameValuePair("data1", "" + edtValueFloat));     // 1 = auto 2 = manual
                        param.add(new BasicNameValuePair("OldData", old_data));
                        param.add(new BasicNameValuePair("UserId", MUserId));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        //  param.add(new BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile

                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                        // Log.d("start", obj +start+"--"+stop);
                        // _rms D/Speend Mode: [{"Latitude":"2.0","Longitude":"MANUAL","Result":"2.0","CEnergyF":0.0,"CFlowF":0.0,"CTimeF":0.0}]
                        if (!TextUtils.isEmpty(obj)) {
                            //   progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                            // Log.d("Speed12", obj );

                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                final String mLat= jo.getString("Latitude");
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                                   // progressDialog.dismiss();
                                    baseRequest.hideLoader();
                                 //   mSettingModelResponse.get(mGlobalPosition).setEditValue(Float.parseFloat(mEditTextList.get(mGlobalPosition).getText().toString().trim()));
                                    speed_mode = jo.getString("Longitude");

                                  //  CustomUtility.isSuccessDialog(mContext, mContext.getString(R.string.success), mContext.getString(R.string.parameter_set_successfully));
                                //    addDynamicViewPro(mSettingModelResponse);

                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mEditTextList.get(mGlobalPosition).setText(""+Float.parseFloat(mLat));
                                           // changeButtonVisibility(true,1.0f, mTextViewSetIDtList.get(mGlobalPosition));
                                  baseRequest.hideLoader();
                                        }
                                    });


                                }
                                else if (jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest.hideLoader();
                                    read_only = false;
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest.hideLoader();
                                    read_only = false;
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_set_mode_fail));
                                }
                            }
                              baseRequest.hideLoader();

                        }

                    } catch (Exception e) {
                      //  progressDialog.dismiss();
                        baseRequest.hideLoader();
                        // baseRequest.hideLoader();
                        Log.d("exce", "" + e);
                    }
                } else {
                    //yourCountDownTimer.cancel();
                   // progressDialog.dismiss();
                    baseRequest.hideLoader();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);
                }

            }
        }).start();

    }


}
