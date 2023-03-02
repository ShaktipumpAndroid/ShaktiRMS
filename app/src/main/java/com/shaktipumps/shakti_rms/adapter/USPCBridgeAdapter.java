package com.shaktipumps.shakti_rms.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.UtilMethod;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.StartStopBean.StartStopModelView;
import com.shaktipumps.shakti_rms.bean.StartStopBean.StartStopVkResponse;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModelResponse;
import com.shaktipumps.shakti_rms.bean.UspcEnergy.UspcEnrgyResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

/**
 * Created by votive on 26/04/18.
 */

public class USPCBridgeAdapter extends RecyclerView.Adapter<USPCBridgeAdapter.ViewHolder> {

    private BluetoothSocket btSocket;
    private BluetoothAdapter myBluetooth;
    float mTotalTimeFloatData;
    private UUID mMyUDID;
    private InputStream iStream;
    char mCRCFinalValue;
    // private List<MenuModelResponse> menuModelsList;
    private List<String> nameList;
    private Context mContext;
    private List<Customer_GPS_Search> customerSearchesList = null;
    private List<UspcModelResponse> mUspcModelResponse = null;

    private BaseRequest baseRequest;



    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    String[] mSNameArray = {"Aata Chakki", "Cold Storage Refrigerator", "Motor-Thresher Set", "Motor-Pump Set", "Motor-Fodder Cutting Set"};
    int[] mimgNameArray = {R.drawable.chakki_set, R.drawable.deep_freser, R.drawable.thresher_set, R.drawable.pump_set, R.drawable.cutting_set};

    int mPosition;
    private Context mActivity;

    private String mDeviceNo;
    private String muserid;
    private String mDeviceType;

    private String mModelType;
    private UspcEnrgyResponse mUspcEnrgyResponse;
    private List<StartStopVkResponse> mStartStopVkResponse;


    // public USPCBridgeAdapter(List<MenuModelResponse> menuModelsList, Activity mContext)
    public USPCBridgeAdapter(Context mContext, Activity mActivity, List<Customer_GPS_Search> customerSearchesList, List<UspcModelResponse> mUspcModelResponse, int mPosition, String MUserId, String DeviceNo, String mModelType, String DeviceType, UspcEnrgyResponse mUspcEnrgyResponse) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.customerSearchesList = customerSearchesList;
        this.mUspcModelResponse = mUspcModelResponse;
        this.mPosition = mPosition;
        this.muserid = MUserId;
        this.mDeviceNo = DeviceNo;
        this.mDeviceType = DeviceType;
        this.mModelType = mModelType;
        this.mUspcEnrgyResponse = mUspcEnrgyResponse;


        baseRequest = new BaseRequest(mActivity);

        mStartStopVkResponse = new ArrayList<>();


    }


    @Override
    public USPCBridgeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.menu_item_cardview, parent, false);

        final USPCBridgeAdapter.ViewHolder viewHolder1 = new USPCBridgeAdapter.ViewHolder(view1);


        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(USPCBridgeAdapter.ViewHolder holder, final int position) {

        /*   holder.rlvMenuMainView.getLayoutParams().width = UtilMethod.getDeviceHeightWidth(mContext, true)/2;*/
        holder.lvlMainViewDataContainerID.getLayoutParams().height = UtilMethod.getDeviceHeightWidth(mContext, false) / 6 - 40;
        // holder.tvNameAndPriceTag.setText(mSNameArray[position]);
        // holder.mImgMenu.setImageResource(mimgNameArray[position]);
        holder.txtOptionTypeID.setText(mUspcModelResponse.get(position).getTitle());
        //holder.mImgMenu.setImageResource(mimgNameArray[position]);
        Picasso.with(mContext).load(mUspcModelResponse.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgOptionTypeID);
        int mFixedSelectedValue = 0;

        try {
            mFixedSelectedValue = Integer.parseInt(mUspcEnrgyResponse.getUSPCAPPIDValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // String mIndexSelectedValue = mUspcModelResponse.get(position).get;

        if(mFixedSelectedValue-1 == position)
        {
            holder.txtOptionTypeID.setTextColor(mContext.getResources().getColor(R.color.white));
            System.out.println("position == >>true  "+position +" mFixedSelectedValue ="+mFixedSelectedValue);
            holder.lvlMainViewDataContainerID.setBackgroundColor(mContext.getResources().getColor(R.color.green_trans));
        }
        else
        {
            holder.txtOptionTypeID.setTextColor(mContext.getResources().getColor(R.color.black));
            System.out.println("position == >>false  "+position +" mFixedSelectedValue ="+mFixedSelectedValue);
        }

        holder.rlvMenuMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // int gggg = menuModelsList.get(position).getProductId();
                //Constant.MENU_ID_FOR_DETAILS = menuModelsList.get(position).getProductId();
                try {


                   /* Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoUSPC.class);
                    //mIntent.putExtra("menuID", gggg);
                   // mIntent.putExtra("mDeviceDetail",   mUspcModelResponse);
                    mIntent.putExtra("mDeviceDetail",  (Serializable) customerSearchesList);
                    mIntent.putExtra("mPos", position);
                    mIntent.putExtra("moldPos", mPosition);
                    // mIntent.putExtra("menuID", menuModelsList.get(position).getProductId());

                    mContext.startActivity(mIntent);*/

                    if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {

                        if (CustomUtility.isOnline(mContext)){
                            NewSolarVFD.USPC_SELECTION_CHECK = 1;
                        NewSolarVFD.USPC_SELECTION_NAME = mUspcModelResponse.get(position).getTitle();
                        NewSolarVFD.USPC_SELECTION_IMAGE = mUspcModelResponse.get(position).getImage();
                        baseRequest.showLoader();
                        callURL(mDeviceNo, muserid, mDeviceType, mUspcModelResponse, position, baseRequest);

                        int pppopopo = position + 1;
                        try {
                            mUspcEnrgyResponse.setUSPCAPPIDValue(String.valueOf(pppopopo));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        {
                            Toast.makeText(mContext,"Pleasde check internet connection!!",Toast.LENGTH_SHORT).show();
                        }
                        notifyDataSetChanged();
                    }else{
                    try {
                        baseRequest.showLoader();
                        //Toast.makeText(mContext, "jai hooo...==>>  "+pp, Toast.LENGTH_SHORT).show();
                        // edtValueFloat = Float.parseFloat(mEditTextList.get(pp).getText().toString().trim());
                        //  edtValue = Integer.parseInt(mEditTextList.get(pp).getText().toString().trim());

                        /*  setDeviceMode(mSettingModelResponse.get(position).getAddress());*/

                        //changeButtonVisibility(true, 1.0f, holder);
                        baseRequest.showLoader();
                        NewSolarVFD.USPC_SELECTION_CHECK = 1;
                        NewSolarVFD.USPC_SELECTION_NAME = mUspcModelResponse.get(position).getTitle();
                        NewSolarVFD.USPC_SELECTION_IMAGE = mUspcModelResponse.get(position).getImage();

                        char[] datar = new char[4];
                        // int a=Float.floatToIntBits((float) edtValue);
                        // int a = Float.floatToIntBits((float) mUspcModelResponse.get(position).getDisplayIndex());
                        int a = Float.floatToIntBits(Float.parseFloat(mUspcModelResponse.get(position).getDisplayIndex()));
                        datar[0] = (char) (a & 0x000000FF);
                        datar[1] = (char) ((a & 0x0000FF00) >> 8);
                        datar[2] = (char) ((a & 0x00FF0000) >> 16);
                        datar[3] = (char) ((a & 0xFF000000) >> 24);
                        int crc = CRC16_MODBUS(datar, 4);
                        char reciverbyte1 = (char) ((crc >> 8) & 0x00FF);
                        char reciverbyte2 = (char) (crc & 0x00FF);

                        mCRCFinalValue = (char) (reciverbyte1 + reciverbyte2);

                        String v1 = String.format("%02x", (0xff & datar[0]));
                        String v2 = String.format("%02x", (0xff & datar[1])); //String v2 =Integer.toHexString(datar[1]);
                        String v3 = String.format("%02x", (0xff & datar[2]));
                        String v4 = String.format("%02x", (0xff & datar[3]));
                        String v5 = String.format("%02x", (0xff & reciverbyte1));//Integer.toHexString(mCRCFinalValue);
                        String v6 = String.format("%02x", (0xff & reciverbyte2));
                        String mMOBADDRESS = "";

                        String mMobADR = mUspcModelResponse.get(position).getBTaddress();

                        if (!mMobADR.equalsIgnoreCase("")) {
                            int mLenth = mMobADR.length();
                            if (mLenth == 1) {
                                mMOBADDRESS = "000" + mMobADR;
                            }else if (mLenth == 2) {
                                mMOBADDRESS = "00" + mMobADR;
                            }else if (mLenth == 3) {
                                mMOBADDRESS = "0" + mMobADR;
                            } else {
                                mMOBADDRESS = mMobADR;
                            }

                            String modeBusCommand = "0106" + mMOBADDRESS + v1 + v2 + v3 + v4 + v5+v6;//write
                           // String modeBusCommand = "01060117"+ v1 + v2 + v3 + v4 + v5+v6;//write
Toast.makeText(mContext,"modeBusCommand==>>"+modeBusCommand,Toast.LENGTH_SHORT).show();
                            System.out.println("mTotalTime==>>vvv==>> " + modeBusCommand);

                            //  String modeBusCommand1 = "0103"+mSettingModelResponse.get(position).getMobBTAddress()+""+"crc";
                            new BluetoothCommunicationForDynamicParameterWrite().execute(modeBusCommand, modeBusCommand, "OK");

                        } else {
                            Toast.makeText(mContext, "MOB address not found!", Toast.LENGTH_SHORT).show();
                        }


                        //String modeBusCommand = "0106"+Integer.toHexString(mSettingModelResponse.get(position).getMobBTAddress())+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(reciverbyte1)+Integer.toHexString(reciverbyte2);//write
                        // String modeBusCommand = "0106"+mSettingModelResponse.get(i).getMobBTAddress()+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(mCRCFinalValue);//write
                        //   String modeBusCommand = "0106"+mSettingModelResponse.get(pp).getMobBTAddress()+v1+v2+v3+v4+v5;//write
                        notifyDataSetChanged();
                        baseRequest.hideLoader();

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        baseRequest.hideLoader();
                    }

                }/////


                } catch (Exception e) {
                    baseRequest.hideLoader();
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
           return mUspcModelResponse.size();
        //return mSNameArray.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imgOptionTypeID;
        public TextView txtOptionTypeID;
        public LinearLayout lvlMainViewDataContainerID;
        public RelativeLayout rlvMenuMainView;


        public ViewHolder(View v) {

            super(v);
            imgOptionTypeID = (CircleImageView) v.findViewById(R.id.imgOptionTypeID);
            txtOptionTypeID = (TextView) v.findViewById(R.id.txtOptionTypeID);
            lvlMainViewDataContainerID = (LinearLayout) v.findViewById(R.id.lvlMainViewDataContainerID);
            rlvMenuMainView = (RelativeLayout) v.findViewById(R.id.rlvMenuMainView);

        }
    }


    public void callURL11(final String DeviceNo, final String userid, final String DeviceType, final List<UspcModelResponse> mUspcModelResponse, final int pp, final BaseRequest baseRequest1) {

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
                       //  obj = CustomHttpClient.executeHttpPost1("http://111.118.249.180:1112/Home/DeviceSettingParam", param);
                        // obj = CustomHttpClient.executeHttpPost1("http://111.118.249.180:1112/ShaktiHome/DeviceSettingParam", param);
                         obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
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

                                    /*int pppopopo= pp + 1;
                                    mUspcEnrgyResponse.setUSPCAPPIDValue(pppopopo);*/

                                    //notifyDataSetChanged();
                                    baseRequest1.hideLoader();
                                    CustomUtility.isSuccessDialog(mContext, mContext.getString(R.string.success), mActivity.getString(R.string.parameter_set_successfully));
                                    //mUspcEnrgyResponse.getUSPCAPPIDValue();


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


    private void callURL(final String DeviceNo, final String userid, final String DeviceType, final List<UspcModelResponse> mUspcModelResponse, final int pp, final BaseRequest baseRequest1) {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    StartStopModelView mStartStopModelView = gson.fromJson(Json, StartStopModelView.class);

                    getStartStopDataResponse(mStartStopModelView);

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

            jsonObject.addProperty("address1", mUspcModelResponse.get(pp).getAddress());
            jsonObject.addProperty("did1", DeviceNo);
            jsonObject.addProperty("RW", "1");
            jsonObject.addProperty("data1", mUspcModelResponse.get(pp).getDisplayIndex());
            jsonObject.addProperty("OldData", "0");
            jsonObject.addProperty("UserId", userid);
            jsonObject.addProperty("DeviceType", DeviceType);
            jsonObject.addProperty("offset1", "1");
            jsonObject.addProperty("IPAddress", CustomUtility.getDeviceId(mContext));

            System.out.println("VikasVV1==>"+jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPostRealStartStop(1, jsonObject, NewSolarVFD.START_STOP_MOTOR1);/////

    }

    private void getStartStopDataResponse(StartStopModelView mStartStopModelView) {

        if(mStartStopModelView.getStatus())
        {

            if(mStartStopVkResponse.size() > 0)
                mStartStopVkResponse.clear();

            mStartStopVkResponse= mStartStopModelView.getResponse();


            //success message
            if (mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("2.0")) {


                CustomUtility.isSuccessDialog(mContext, mContext.getString(R.string.success), mActivity.getString(R.string.parameter_set_successfully));

                baseRequest.hideLoader();

            }

            // disconnect message
            if (mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("D")) {
                baseRequest.hideLoader();
                CustomUtility.isErrorDialog(mActivity, mActivity.getString(R.string.error), mActivity.getString(R.string.err_device_disconnect));
            }

            // command failed message
            if (!mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("2.0") && !mStartStopVkResponse.get(0).getResult().equalsIgnoreCase("D")) {
                baseRequest.hideLoader();
                CustomUtility.isErrorDialog(mActivity, mActivity.getString(R.string.error), mActivity.getString(R.string.err_connection));
            }



        }

    }

    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, "" + mString, Toast.LENGTH_LONG).show();
        }
    };




    /////////////////////////bt read write
    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDynamicParameterWrite extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        int bytesRead = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }


                    int[] bytesReaded = new int[4];

                    for (int i = 0; i < 6; i++) {
                        int mCharOne11 = iStream.read();
                    }

                    //int[] mTotalTime;
                    int mTotalTime;

                    int jjj = 0;
                    // mTotalTime = new int[1];
                    for (int i = 0; i < 1; i++) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i + 1] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i + 2] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i + 3] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            // int mCharThree = iStream.read();
                            //   int mCharFour = iStream.read();
//                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mTotalTime = bytesReaded[i];
                            System.out.println("mTotalTime==>>vvv1  " + jjj + " " + mTotalTime);
                            mTotalTime |= bytesReaded[i + 1] << 8;
                            System.out.println("mTotalTime==>>vvv2  " + jjj + " " + mTotalTime);
                            mTotalTime |= bytesReaded[i + 2] << 16;
                            System.out.println("mTotalTime==>>vvv3  " + jjj + " " + mTotalTime);
                            mTotalTime |= bytesReaded[i + 3] << 24;/* mTotalTime[jjj] = bytesReaded[i];
                            mTotalTime[jjj] |= bytesReaded[i + 1] << 8;
                            mTotalTime[jjj] |= bytesReaded[i + 2] << 16;
                            mTotalTime[jjj] |= bytesReaded[i + 3] << 24;*/

                            System.out.println("mTotalTime==>>vvv4  " + jjj + " " + Float.intBitsToFloat(mTotalTime));
                            //   mBTParameterList.add(Float.intBitsToFloat(mTotalTime[jjj])+"");
                            //arraylist.get(jjj).setValue(Float.intBitsToFloat(mTotalTime[jjj])+"");
                            mTotalTimeFloatData = 0;
                            mTotalTimeFloatData = Float.intBitsToFloat(mTotalTime);
                            // mTotalTimeFloatData = (float) mTotalTime;

                            jjj++;

                            for (int ii = 0; ii < 4; ii++) {
                                int mCharOne11 = iStream.read();
                            }

                            while (iStream.available() > 0) {
                                int mCharOne11 = iStream.read();
                            }

                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {
                baseRequest.hideLoader();
                //btSocket = null;
                //mmCheckStart = 1;
                //   Toast.makeText(mContext, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            baseRequest.hideLoader();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDynamicParameterRead extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        int bytesRead = 0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (!btSocket.isConnected()) {
                        btSocket.connect();//start connection
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();

                    if (!btSocket.isConnected()) {
                        btSocket.connect();//start connection
                    }
                }


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }


                    int[] bytesReaded = new int[4];

                    for (int i = 0; i < 6; i++) {
                        int mCharOne11 = iStream.read();
                    }

                    //int[] mTotalTime;
                    int mTotalTime;

                    int jjj = 0;
                    // mTotalTime = new int[1];
                    for (int i = 0; i < 1; i++) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i + 1] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i + 2] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i + 3] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            // int mCharThree = iStream.read();
                            //   int mCharFour = iStream.read();
//                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mTotalTime = bytesReaded[i];
                            System.out.println("mTotalTime==>>vvv1  " + jjj + " " + mTotalTime);
                            mTotalTime |= bytesReaded[i + 1] << 8;
                            System.out.println("mTotalTime==>>vvv2  " + jjj + " " + mTotalTime);
                            mTotalTime |= bytesReaded[i + 2] << 16;
                            System.out.println("mTotalTime==>>vvv3  " + jjj + " " + mTotalTime);
                            mTotalTime |= bytesReaded[i + 3] << 24;/* mTotalTime[jjj] = bytesReaded[i];
                            mTotalTime[jjj] |= bytesReaded[i + 1] << 8;
                            mTotalTime[jjj] |= bytesReaded[i + 2] << 16;
                            mTotalTime[jjj] |= bytesReaded[i + 3] << 24;*/

                            System.out.println("mTotalTime==>>vvv4  " + jjj + " " + mTotalTime);
                            //   mBTParameterList.add(Float.intBitsToFloat(mTotalTime[jjj])+"");
                            //arraylist.get(jjj).setValue(Float.intBitsToFloat(mTotalTime[jjj])+"");
                            mTotalTimeFloatData = 0;
                            mTotalTimeFloatData = Float.intBitsToFloat(mTotalTime);

                           /* mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //changeButtonVisibility(true, 1.0f, mEditTextList.get(mGlobalPosition));
                                    mSettingModelResponse.get(mGlobalPosition).setEditValue(mTotalTimeFloatData);
                                    // addDynamicViewPro(mSettingModelResponse);
                                    mEditTextList.get(mGlobalPosition).setText("" + mTotalTimeFloatData);
                                    System.out.println("mGlobalPosition==>>" + mGlobalPosition + "\nmTotalTimeFloatData==>>" + mTotalTimeFloatData);
                                    changeButtonVisibility(true, 1.0f, mTextViewSetIDtList.get(mGlobalPosition));
                                }
                            });*/

                            jjj++;

                            for (int ii = 0; ii < 4; ii++) {
                                int mCharOne11 = iStream.read();
                            }


                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }

                    while (iStream.available() > 0) {
                        int mCharOne11 = iStream.read();
                    }

                }
            } catch (Exception e) {
                baseRequest.hideLoader();
                e.printStackTrace();
                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            baseRequest.hideLoader();
            super.onPostExecute(result);

        }
    }

    public static int CRC16_MODBUS(char[] buf, int len) {

        int crc = 0xFFFF;
        int pos = 0, i = 0;
        for (pos = 0; pos < len; pos++) {
            crc ^= (int) buf[pos];    // XOR byte into least sig. byte of crc

            for (i = 8; i != 0; i--) {    // Loop over each bit
                if ((crc & 0x0001) != 0) {      // If the LSB is set
                    crc >>= 1;                    // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else                            // Else LSB is not set
                    crc >>= 1;                    // Just shift right
            }
        }

        return crc;
    }



}

