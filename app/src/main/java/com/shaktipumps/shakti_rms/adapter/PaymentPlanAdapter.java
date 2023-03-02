package com.shaktipumps.shakti_rms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.GlobalClass.UtilMethod;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.PaymentBean.PaymentPlanResponse;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModelResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*import com.payu.india.Extras.PayUChecksum;
import com.payu.india.Extras.PayUSdkDetails;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.payu.payuui.Activity.PayUBaseActivity;*/

/**
 * Created by votive on 26/04/18.
 */

public class PaymentPlanAdapter extends RecyclerView.Adapter<PaymentPlanAdapter.ViewHolder> {

    // private List<MenuModelResponse> menuModelsList;
    private List<String> nameList;
    private Context mContext;
    private List<Customer_GPS_Search> customerSearchesList = null;
    private List<PaymentPlanResponse> mPaymentPlanResponse = null;

    private BaseRequest baseRequest;

    int mPRODUCTION_ENV = 0;
    int mMOBILE_STAGING_ENV = 1; // mobiletest.payu.in
    int mSTAGING_ENV = 2; // test.payu.in
    int mMOBILE_DEV_ENV = 3; // mobiledev.payu.in

    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    String[] mSNameArray = {"Aata Chakki", "Cold Storage Refrigerator", "Motor-Thresher Set", "Motor-Pump Set", "Motor-Fodder Cutting Set"};
    int[] mimgNameArray = {R.drawable.chakki_set, R.drawable.deep_freser, R.drawable.thresher_set, R.drawable.pump_set, R.drawable.cutting_set};

    int mPosition;
    private Context mActivity;

    private String mDeviceNo;
    private String muserid;
    private String mDeviceType;

    private String mModelType;
   // private PayUSdkDetails payUSdkDetails;

    // These will hold all the payment parameters
  //  private PaymentParams mPaymentParams;

    // This sets the configuration
   // private PayuConfig payuConfig;
   /* private String merchantKey ="gtKFFx"; ////////////////Testing data creadential
    private String merchantSalt = "eCwWELxi";*/

    private String merchantKey ="2vsZFF";/////////Live payment transaction
    private String merchantSalt = "fWQXFcws";
   // private String userCredentials ="9009555317";
    //private String userCredentials ="$h@kt1#umps83#@123";
    private String userCredentials ="2vsZFF:";
   // private PayUChecksum checksum;

    // public USPCBridgeAdapter(List<MenuModelResponse> menuModelsList, Activity mContext)
    /*public PaymentPlanAdapter(Context mContext, PayUSdkDetails payUSdkDetails, List<Customer_GPS_Search> customerSearchesList, List<PaymentPlanResponse> mPaymentPlanResponse, int mPosition, String MUserId, String DeviceNo, String mModelType, String DeviceType) {

        this.mContext = mContext;
        this.customerSearchesList = customerSearchesList;
        this.mPaymentPlanResponse = mPaymentPlanResponse;
        this.mPosition = mPosition;
        this.muserid = MUserId;
        this.mDeviceNo = DeviceNo;
        this.mDeviceType = DeviceType;
        this.mModelType = mModelType;
        this.payUSdkDetails = payUSdkDetails;
        baseRequest = new BaseRequest(mContext);

        //TODO Below are mandatory params for hash genetation
        mPaymentParams = new PaymentParams();

        userCredentials ="2vsZFF:"+muserid;

    }*/

 public PaymentPlanAdapter(Context mContext) {

        this.mContext = mContext;

        baseRequest = new BaseRequest(mContext);

        //TODO Below are mandatory params for hash genetation
    //    mPaymentParams = new PaymentParams();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.payment_option_item_view, parent, false);

        final ViewHolder viewHolder1 = new ViewHolder(view1);


        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        /*   holder.rlvMenuMainView.getLayoutParams().width = UtilMethod.getDeviceHeightWidth(mContext, true)/2;*/
        holder.lvlMainViewDataContainerID.getLayoutParams().height = UtilMethod.getDeviceHeightWidth(mContext, false) / 6 - 40;
        // holder.tvNameAndPriceTag.setText(mSNameArray[position]);
        // holder.mImgMenu.setImageResource(mimgNameArray[position]);

        holder.txtPayDescNameID.setText(mPaymentPlanResponse.get(position).getPlanDescription());
        holder.txtPaymentValueID.setText(mPaymentPlanResponse.get(position).getPlanAmount());
        holder.txtPayEXPHEADID.setText(mPaymentPlanResponse.get(position).getPlanDuration()+" Day's");

        //holder.mImgMenu.setImageResource(mimgNameArray[position]);



        holder.rlvMenuMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int gggg = menuModelsList.get(position).getProductId();
                //Constant.MENU_ID_FOR_DETAILS = menuModelsList.get(position).getProductId();
                try {
                   // baseRequest.showLoader();
                   // callURL(mDeviceNo, muserid, mDeviceType, mPaymentPlanResponse, position, baseRequest);

                  /*  Intent mIntent = new Intent(mContext, StartPaymentActivity.class);

                   // mIntent.putExtra("mDeviceDetail",  (Serializable) mPaymentPlanResponse);
                  //  mIntent.putExtra("mPos", position);
                    mIntent.putExtra("planAmt", mPaymentPlanResponse.get(position).getPlanAmount());
                    mIntent.putExtra("planDESC", mPaymentPlanResponse.get(position).getPlanDescription());
                    mIntent.putExtra("planID", mPaymentPlanResponse.get(position).getPlanId());
                    mIntent.putExtra("planDuration", mPaymentPlanResponse.get(position).getPlanDuration());
                    // mIntent.putExtra("menuID", menuModelsList.get(position).getProductId());
                   Intent mIntent = new Intent(mContext, StartPaymentActivity.class);
                    mContext.startActivity(mIntent);*/

                    NewSolarVFD.PAYMENT_PLAN_ID = mPaymentPlanResponse.get(position).getPlanId();
                   // onClickEventItem(mPaymentPlanResponse,position);
                    /*Intent mIntent = new Intent(mContext, PaymentStartActivityPayu.class);
                    mIntent.putExtra("mPlanAmt", mPaymentPlanResponse.get(position).getPlanAmount());
                    mIntent.putExtra("mPlanDESC", mPaymentPlanResponse.get(position).getPlanDescription());
                    mIntent.putExtra("mPlanID", mPaymentPlanResponse.get(position).getPlanId());
                    mIntent.putExtra("mPlanDuration", mPaymentPlanResponse.get(position).getPlanDuration());
                    mContext.startActivity(mIntent);*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //   return txtcolorList.size();
        return mPaymentPlanResponse.size();
       // return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPaymentValueID, txtPayDescNameID, txtPayEXPHEADID;
        public LinearLayout lvlMainViewDataContainerID;
        public RelativeLayout rlvMenuMainView;

        public ViewHolder(View v) {

            super(v);

            txtPaymentValueID = (TextView) v.findViewById(R.id.txtPaymentValueID);
            txtPayDescNameID = (TextView) v.findViewById(R.id.txtPayDescNameID);
            txtPayEXPHEADID = (TextView) v.findViewById(R.id.txtPayEXPHEADID);

            lvlMainViewDataContainerID = (LinearLayout) v.findViewById(R.id.lvlMainViewDataContainerID);
            rlvMenuMainView = (RelativeLayout) v.findViewById(R.id.rlvMenuMainView);

        }
    }


    public void callURL(final String DeviceNo, final String userid, final String DeviceType, final List<UspcModelResponse> mUspcModelResponse, final int pp, final BaseRequest baseRequest1) {

        baseRequest1.showLoader();

        new Thread(new Runnable() {


            @Override
            public void run() {

                if (CustomUtility.isOnline(mContext)) {

                    baseRequest1.showLoader();

                    try {
                        String obj = "";
                        param.clear();
                        String ssssssss = mUspcModelResponse.get(pp).getAddress();
                        System.out.println("ssssssss====>> " + ssssssss);
                        param.add(new BasicNameValuePair("address1", ssssssss));

                        //param.add(new BasicNameValuePair("address1","10" ));
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "1"));
                        param.add(new BasicNameValuePair("data1", mUspcModelResponse.get(pp).getDisplayIndex()));               // start = 1
                        param.add(new BasicNameValuePair("OldData", "0"));
                        param.add(new BasicNameValuePair("UserId", userid));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        //    param.add(new BasicNameValuePair("offset1", "1"));
                        param.add(new BasicNameValuePair("offset1", "1"));
                        //  param.addnew BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                       // obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                         obj = CustomHttpClient.executeHttpPost1("http://111.118.249.180:1112/Home/DeviceSettingParam", param);
                        //Log.d("start", obj +start+"--"+stop);


                        if (!TextUtils.isEmpty(obj)) {

                            // yourCountDownTimer.cancel();

                            //   Log.d("start1", obj );
                            JSONArray ja = new JSONArray(obj);

                            //  Log.d("start12", obj );
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);

                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {

                                    baseRequest1.hideLoader();
                                    CustomUtility.isSuccessDialog(mContext, mContext.getString(R.string.success), mActivity.getString(R.string.parameter_set_successfully));
                                    /*mActivity.runOnUiThread(new Runnable(){
                                        public void run() {
                                            // UI code goes here
                                            //String ssss = mUspcModelResponse.get(pp).getButtonText();
                                        }
                                    });*/


                                }

                                // disconnect message
                                if (jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest1.hideLoader();
                                    CustomUtility.isErrorDialog(mActivity, mActivity.getString(R.string.error), mActivity.getString(R.string.err_device_disconnect));

                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    baseRequest1.hideLoader();
                                    CustomUtility.isErrorDialog(mActivity, mActivity.getString(R.string.error), mActivity.getString(R.string.err_connection));

                                }
                            }

                            baseRequest1.hideLoader();
                        }

                    } catch (Exception e) {
                        baseRequest1.hideLoader();
                        // yourCountDownTimer.cancel();

                        Log.d("exce", "" + e);
                    }
                } else {
                    baseRequest1.hideLoader();


                    Message msg = new Message();
                    msg.obj = "Please check internet connection!!";
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, "" + mString, Toast.LENGTH_LONG).show();
        }
    };








    /******************************
     * Client hash generation
     ***********************************/
    // Do not use this, you may use this only for testing.
    // lets generate hashes.
    // This should be done from server side..

    /**
     * This method adds the Payuhashes and other required params to intent and launches the PayuBaseActivity.java
     *
     * @param payuHashes it contains all the hashes generated from merchant server
     */



}

