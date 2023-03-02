package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.GlobalClass.AllPopupUtil;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class GETI_BT_MEINumberActivity extends AppCompatActivity {

    private Context mContext = null;

    private Toolbar mToolbar;

    private TextView txtNextBTNID, txtIMEINUMBERValueID;
    private EditText edtConfirmPassID;

    private UUID mMyUDID;
    int mPostionFinal = 0;
    private BaseRequest baseRequest;

    private String mUsernameStr;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;

    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;
    InputStream iStream;
    private String mBtNameHead;
    private String mDeviceType;
    private String mModelType;

    private int mLengthCount;

    private List<String> mMonthHeaderList;

    int[] mTotalTime;

    private Switch switch1;


private RelativeLayout rlvEditConfirmPassViewID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String mUsernamePrefSTR;

    private String mConfirmStr;
    private String mOldPassStr;
    private String mNewPassStr;

    ImageView imgShaktiLogoID;

    private RelativeLayout rlvBackViewID;
    private ImageView imgHeaderID;
    private ImageView imgInternetToggleID, imgBTToggleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bt_imeinumber);

        mContext = this;

        mMonthHeaderList = new ArrayList<>();

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        initView();
    }

    private void initView() {




        baseRequest = new BaseRequest(this);

        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);

        imgInternetToggleID = (ImageView) findViewById(R.id.imgInternetToggleID);
        imgBTToggleID = (ImageView) findViewById(R.id.imgBTToggleID);
        switch1 = (Switch) findViewById(R.id.switch1);

        //key_login_username
        mUsernamePrefSTR = pref.getString("key_login_username", "login_status");

        imgShaktiLogoID = (ImageView) findViewById(R.id.imgShaktiLogoID);
        txtNextBTNID = (TextView) findViewById(R.id.txtNextBTNID);
        txtIMEINUMBERValueID = (TextView) findViewById(R.id.txtIMEINUMBERValueID);
        rlvEditConfirmPassViewID = (RelativeLayout) findViewById(R.id.rlvEditConfirmPassViewID);

        txtNextBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") ||  Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("")) {
                            Intent intent = new Intent(mContext, PairedDeviceActivityIMEI.class);
                            mContext.startActivity(intent);
                        }
                        else
                        {
                            new BluetoothCommunicationGetIMEINUMBER().execute(":GET IMEI#", ":GET IMEI#", "OKAY");
                            rlvEditConfirmPassViewID.setVisibility(View.VISIBLE);
                        }

                       // Constant.BT_DEVICE_NAME = mDeviceNameList.get(position).toString();
                      //  Constant.BT_DEVICE_MAC_ADDRESS = mDeviceMACAddressList.get(position).toString();
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                }

              // new BluetoothCommunicationGetIMEINUMBER().execute(":GET IMEI#", ":GET IMEI#", "OKAY");
              //  rlvEditConfirmPassViewID.setVisibility(View.VISIBLE);
            }
        });

        imgShaktiLogoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlvEditConfirmPassViewID.setVisibility(View.GONE);
            }
        });
        clickEventOnBTN();

    }


    private void clickEventOnBTN() {

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    imgBTToggleID.setImageResource(R.drawable.iv_bluetooth_selected);
                    imgInternetToggleID.setImageResource(R.drawable.iv_connection_unselected);

                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter.isEnabled()) {
                            if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                                if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                                    Intent intent = new Intent(mContext, PairedDeviceActivityIMEI.class);
                                    mContext.startActivity(intent);
                                }
                                ///////////////write the query
                                //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                            } else {
                                // AllPopupUtil.btPopupCreateShow(mContext);
                                mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                            }
                        } else {
                            //  AllPopupUtil.btPopupCreateShow(mContext);
                            mContext.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                        }
                    // switch1.setChecked(false);
                }
                else
                {
                    imgBTToggleID.setImageResource(R.drawable.iv_bluetooth_unselected);
                    imgInternetToggleID.setImageResource(R.drawable.iv_connection_selected);

                    Constant.BT_DEVICE_NAME = "";
                    Constant.BT_DEVICE_MAC_ADDRESS = "";
                    Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;
                    // switch1.setChecked(true);
                }

            }
        });
        //imgInternetToggleID, imgBTToggleID;
        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetIMEINUMBER extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private int bytesRead;
        private String condition;
        private String override = null;
        String SSS = "";

        int iii = 0;

        @Override
        protected void onPreExecute() {

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            if (!Constant.isLoding) {
                baseRequest.showLoader();
            }

            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
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
                    } else {
                        btSocket = null;
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    btSocket = null;
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

              /*  if (btSocket == null) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }*/
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {

                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //  baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";


                    System.out.println("iStream.available()==>>" + iStream.available());

                    for (int i = 0; i < 10; i++) {
                        try {
                            bytesRead = iStream.read();
                        } catch (IOException e) {
                            System.out.println("vikas--2==>2");
                            //baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
int iiiii = 0;
                    while (iStream.available() > 0) {
                        if(iiiii < 15)
                        {
                            SS += (char) iStream.read();
                            iiiii++;
                        }
                        else
                        {

                            break;
                        }


                    }
//                   String SS =convertStreamToString();

                    if (SS.trim().equalsIgnoreCase("")) {

                    } else {

                         SSS = SSS+""+SS;
                        // String[] ppp = SS.split("UU");
                       // System.out.println("ppp==>>"+ppp[0]);
                       // txtIMEINUMBERValueID.setText(ppp[0]);
                        txtIMEINUMBERValueID.setText(SS);
                      //
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                baseRequest.hideLoader();
                return false;
            }

            //  baseRequest.hideLoader();
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


    @Override
    protected void onResume() {
        super.onResume();
        //Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE
        if (Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE == 2) {
            finish();
        } else {

        }
    }

}
