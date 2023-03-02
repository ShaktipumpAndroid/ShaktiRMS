package com.shaktipumps.shakti_rms.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

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

import static java.lang.Thread.sleep;


public class DeviceSettingBTAdapter extends RecyclerView.Adapter<DeviceSettingBTAdapter.ViewHolder> {

    private Context mContext;
    private Activity mActivity;


    private List<SettingModelResponse> mSettingModelResponse;

    String MUserId = "null", DeviceType = "null", DeviceNo = "null";
    private ProgressDialog progressDialog;
    String speed_mode = "null";

    boolean read_only = true;
    private int edtValue = 2;
    private String old_data = "1";

    private BluetoothSocket btSocket;
    private BluetoothAdapter myBluetooth;
   // private int mTotalTimeInt = 0;

    private UUID mMyUDID;

    int mmCheckStart = 0;
    int mmCheckStop = 0;
    private InputStream iStream;
    private BaseRequest baseRequest;

    char mCRCFinalValue;
    char mCRCFinalValueWrite;

    float mTotalTimeFloatData;
    //private HomeUserNameClickListener mHomeUserNameClickListener;

    public DeviceSettingBTAdapter(Context mContext, Activity mActivity, List<SettingModelResponse> mSettingModelResponse, String DeviceType, String DeviceNo, String MUserId) {

        this.mSettingModelResponse = mSettingModelResponse;
        // this.mHomeUserNameClickListener = mHomeUserNameClickListener;
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.DeviceType = DeviceType;
        this.MUserId = MUserId;
        this.DeviceNo = DeviceNo;
        baseRequest = new BaseRequest(mContext);
    }

    public DeviceSettingBTAdapter(String SSS, Context mContext) {
        // this.galleryModelsList = galleryModelsList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.device_setting_item, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //changeButtonVisibility(false,0.5f, holder);
        holder.txtTittleNameID.setText(mSettingModelResponse.get(position).getMPName());
        holder.txtUnitNameID.setText(mSettingModelResponse.get(position).getUnit());

        holder.txtReadBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDeviceMode(mSettingModelResponse.get(position).getAddress(), holder);

                char[] datar=new char[4];
                int a=Float.floatToIntBits((float) edtValue);
                datar[0]=(char)(a & 0x000000FF);
                datar[1]=(char)((a & 0x0000FF00)>>8);
                datar[2]=(char)((a & 0x00FF0000)>>16);
                datar[3]=(char)((a & 0xFF000000)>>24);
                int crc=CRC16_MODBUS(datar, 4);
                char reciverbyte1=(char)((crc>>8) & 0x00FF);
                char reciverbyte2=(char)(crc & 0x00FF);

                mCRCFinalValue = (char) (reciverbyte1 + reciverbyte2);

                String v1 =String.format("%02x", (0xff & datar[0]));
                String v2 =String.format("%02x", (0xff & datar[1])); //String v2 =Integer.toHexString(datar[1]);
                String v3 =String.format("%02x", (0xff & datar[2]));
                String v4 =String.format("%02x", (0xff & datar[3]));
                String v5 =Integer.toHexString(mCRCFinalValue);

                String modeBusCommand = "0103"+mSettingModelResponse.get(position).getMobBTAddress()+v1+v2+v3+v4+v5;//write

                new BluetoothCommunicationForDynamicParameterRead().execute(modeBusCommand, modeBusCommand, "OK");

                changeButtonVisibility(true, 1.0f, holder);

            }
        });


        holder.txtSetBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtValue = Integer.parseInt(holder.edtVoltValueID.getText().toString().trim());
                if (edtValue > mSettingModelResponse.get(position).getPMin() && edtValue < mSettingModelResponse.get(position).getPMax()) {
                  /*  setDeviceMode(mSettingModelResponse.get(position).getAddress());*/

                    changeButtonVisibility(true, 1.0f, holder);
                    char[] datar=new char[4];
                    int a=Float.floatToIntBits((float) edtValue);
                    datar[0]=(char)(a & 0x000000FF);
                    datar[1]=(char)((a & 0x0000FF00)>>8);
                    datar[2]=(char)((a & 0x00FF0000)>>16);
                    datar[3]=(char)((a & 0xFF000000)>>24);
                    int crc=CRC16_MODBUS(datar, 4);
                    char reciverbyte1=(char)((crc>>8) & 0x00FF);
                     char reciverbyte2=(char)(crc & 0x00FF);

                    mCRCFinalValue = (char) (reciverbyte1 + reciverbyte2);

                    //String modeBusCommand = "0106"+Integer.toHexString(mSettingModelResponse.get(position).getMobBTAddress())+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(reciverbyte1)+Integer.toHexString(reciverbyte2);//write
                    String modeBusCommand = "0106"+mSettingModelResponse.get(position).getMobBTAddress()+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(mCRCFinalValue);//write

                    //  String modeBusCommand1 = "0103"+mSettingModelResponse.get(position).getMobBTAddress()+""+"crc";
                    new BluetoothCommunicationForDynamicParameterWrite().execute(modeBusCommand, modeBusCommand, "OK");

                   /* char[] datar=new char[4];
                    int a=Float.floatToIntBits((float) edtValue);
                    datar[0]=(char)(a & 0x000000FF);
                    datar[1]=(char)((a & 0x0000FF00)>>8);
                    datar[2]=(char)((a & 0x00FF0000)>>16);
                    datar[3]=(char)((a & 0xFF000000)>>24);
                    int crc=CRC16_MODBUS(datar, 4);
                    char reciverbyte1=(char)((crc>>8) & 0x00FF);
                    char reciverbyte2=(char)(crc & 0x00FF);

                    String modeBusCommand = "0106"+Integer.toHexString(mSettingModelResponse.get(position).getMobBTAddress())+Integer.toHexString(datar[0])+Integer.toHexString(datar[1])+Integer.toHexString(datar[2])+Integer.toHexString(datar[3])+Integer.toHexString(reciverbyte1)+Integer.toHexString(reciverbyte2);//write

                    new BluetoothCommunicationForDynamicParameterRead().execute(modeBusCommand, modeBusCommand, "OK");*/

                } else {
                    Toast.makeText(mContext, "Please enter value between min=" + mSettingModelResponse.get(position).getPMin() + " Max=" + mSettingModelResponse.get(position).getPMax(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        // return galleryModelsList.size();
        if (mSettingModelResponse != null && mSettingModelResponse.size() > 0)
            return mSettingModelResponse.size();
        else
            return 0;
        // return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTittleNameID, txtReadBTNID, txtSetBTNID, txtUnitNameID, txtMessageNumberID, txtMessageNameID;
        public EditText edtVoltValueID;

        public RelativeLayout rlvNotifyItemMainViewID;

        public ViewHolder(View v) {

            super(v);
            txtMessageNameID = (TextView) v.findViewById(R.id.txtMessageNameID);
            txtMessageNumberID = (TextView) v.findViewById(R.id.txtMessageNumberID);
            txtTittleNameID = (TextView) v.findViewById(R.id.txtTittleNameID);
            txtReadBTNID = (TextView) v.findViewById(R.id.txtReadBTNID);
            txtSetBTNID = (TextView) v.findViewById(R.id.txtSetBTNID);
            txtUnitNameID = (TextView) v.findViewById(R.id.txtUnitNameID);
            edtVoltValueID = (EditText) v.findViewById(R.id.edtVoltValueID);
        }
    }

    private void changeButtonVisibility(boolean state, float alphaRate, DeviceSettingBTAdapter.ViewHolder holder) {
        holder.txtSetBTNID.setEnabled(state);
        holder.txtSetBTNID.setAlpha(alphaRate);
    }

    public void getDeviceMode(String address, final ViewHolder holder) {

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        if (CustomUtility.isOnline(mContext)) {

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
                obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                if (!TextUtils.isEmpty(obj)) {

                    progressDialog.dismiss();

                    JSONArray ja = new JSONArray(obj);

                    //Log.d("Speed", obj );

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jo = ja.getJSONObject(i);

                        //success message
                        if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                            speed_mode = jo.getString("Longitude");
                            holder.edtVoltValueID.setText(jo.getString("Latitude"));
                        }

                        // disconnect message
                        if (jo.getString("Result").equalsIgnoreCase("D")) {

                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                        }

                        // command failed message
                        if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_read_mode_fail));
                        }
                    }
                }

            } catch (Exception e) {

                progressDialog.dismiss();
                Log.d("exce", "" + e);
            }
        } else {

            progressDialog.dismiss();
            Message msg = new Message();
            msg.obj = "Please on internet ( Mobile data )";
            mHandler.sendMessage(msg);

        }
//
//            }
//        }).start();

//           }
//       });

    }

    public void setDeviceMode(String address) {

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        //  String old_data = "null" ;
        //Log.d("speed_mode_param_value", speed_mode_param_value +"--"+  old_data) ;

        if (CustomUtility.isOnline(mContext)) {

            try {
                String obj = "";

                param.clear();

                param.add(new BasicNameValuePair("address1", address)); // speed mode

                param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                param.add(new BasicNameValuePair("RW", "1"));        // 1 = write
                param.add(new BasicNameValuePair("data1", "" + edtValue));     // 1 = auto 2 = manual
                param.add(new BasicNameValuePair("OldData", old_data));
                param.add(new BasicNameValuePair("UserId", MUserId));
                param.add(new BasicNameValuePair("DeviceType", DeviceType));

                //  param.add(new BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile

                obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                // Log.d("start", obj +start+"--"+stop);

// _rms D/Speend Mode: [{"Latitude":"2.0","Longitude":"MANUAL","Result":"2.0","CEnergyF":0.0,"CFlowF":0.0,"CTimeF":0.0}]
                if (!TextUtils.isEmpty(obj)) {

                    progressDialog.dismiss();

                    JSONArray ja = new JSONArray(obj);

                    // Log.d("Speed12", obj );

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        //success message
                        if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                            speed_mode = jo.getString("Longitude");
                        }

                        // disconnect message
                        if (jo.getString("Result").equalsIgnoreCase("D")) {
                            read_only = false;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));

                        }

                        // command failed message
                        if (!jo.getString("Result").equalsIgnoreCase("2.0") &&
                                !jo.getString("Result").equalsIgnoreCase("D")

                        ) {
                            read_only = false;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_set_mode_fail));

                        }
                    }

                }

            } catch (Exception e) {

                progressDialog.dismiss();
                Log.d("exce", "" + e);
            }

        } else {

            progressDialog.dismiss();

            Message msg = new Message();
            msg.obj = "Please on internet ( Mobile data )";
            mHandler.sendMessage(msg);

        }

//
//            }
//        }).start();

//           }
//       });

    }


    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };


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
                       /* try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }*/
                    }
                }
                else
                {
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
                        sleep(500);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int[] bytesReaded = new int[4];
                    int[] crc_bytesReaded = new int[2];

                    for (int i = 0; i < 6; i++) {
                        int mCharOne11 = iStream.read();
                    }

                    int mTotalTimeInt = 0;
                    for (int i = 0; i < 4; i++) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);

                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }

                    mTotalTimeInt = bytesReaded[0];
                    mTotalTimeInt |= bytesReaded[1] << 8;
                    mTotalTimeInt |= bytesReaded[2] << 16;
                    mTotalTimeInt |= bytesReaded[3] << 24;



                    System.out.println("mTotalTimeInt==>>JAI Hind==>>"+Float.intBitsToFloat(mTotalTimeInt));

                    mTotalTimeFloatData = Float.intBitsToFloat(mTotalTimeInt);

                    /*int mCrcCheck1 ;
                    int mCrcCheck2 ;

                    for (int i = 0; i < 2; i++) {
                         mCrcCheck1 = iStream.read();
                         mCrcCheck2 = iStream.read();

                       // crc_bytesReaded[i] = Integer.parseInt("" + (char) mCrcCheck1 + (char) mCrcCheck2, 16);

                    }*/


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
            if (mmCheckStart == 1) {
                //Toast.makeText(mContext, "BT Connection intrupted.", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(mContext, "Motor start succesfully.", Toast.LENGTH_SHORT).show();
            }

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
                }
                else
                {
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
                        sleep(200);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int[] bytesReaded = new int[4];

                    for (int i = 0; i < 6; i++) {

                    }
                    int mTotalTimeInt = 0;

                    for (int i = 0; i < 4; i++) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);

                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }

                    mTotalTimeInt = bytesReaded[0];
                    mTotalTimeInt |= bytesReaded[1] << 8;
                    mTotalTimeInt |= bytesReaded[2] << 16;
                    mTotalTimeInt |= bytesReaded[3] << 24;

                    System.out.println("mTotalTimeInt==>>JAI Hind==>>"+Float.intBitsToFloat(mTotalTimeInt));
                    mTotalTimeFloatData = Float.intBitsToFloat(mTotalTimeInt);



                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ///  addHeadersMonths();

                          //  holder.edtVoltValueID.setText(mTotalTimeFloatData);
                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                        }
                    });
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
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
            if (mmCheckStart == 1) {
                //Toast.makeText(mContext, "BT Connection intrupted.", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(mContext, "Motor start succesfully.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public static int CRC16_MODBUS(char []buf, int len)
    {

        int crc = 0xFFFF;
        int pos=0,i=0;
        for ( pos = 0; pos < len; pos++)
        {
            crc ^= (int)buf[pos];    // XOR byte into least sig. byte of crc

            for ( i = 8; i != 0; i--)
            {    // Loop over each bit
                if ((crc & 0x0001) != 0)
                {      // If the LSB is set
                    crc >>= 1;                    // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                }
                else                            // Else LSB is not set
                    crc >>= 1;                    // Just shift right
            }
        }

        return crc;
    }


}


