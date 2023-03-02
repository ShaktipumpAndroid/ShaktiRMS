package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.IMEI.IMEINEERAJModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class BTDemoForMQTT extends AppCompatActivity {

    private TextView txtMQTTBTNID, txtTCPBTNID;
    private TextView txtIMEIshowID, txtIMEIBTNID;
    private TextView txtClientshowID, txtClientBTNID, txtClientGETBTNID;

    private TextView txtUsernameShowID, txtUsernameBTNID, txtUsernameGETBTNID;
    private TextView txtPasswordShowID, txtPasswordBTNID, txtPasswordGETBTNID;
    private Context mActivity;
    private BaseRequest baseRequest;

    private String vMUserId,
            vDeviceType, vDeviceNo, vStartdate;
    private InputStream iStream = null;
    private UUID mMyUDID;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;
    private ProgressDialog progressDialog;
    private int mmCheckStart = 0;
    private int mmCheckStop = 0;
    private String ssssss = "";
    private String sssss = "";

    private String MQTT_TO_TCP = "";
    private String MQTT_TO_TCP1 = "";
    private String MQTT_TO_TCP11 = "";

    String filePath;
    File file;
    private byte[] image;
    private String fileActualPath;
    private int mLengthCount;

    Context mContext;

    String mClientID = "";
    String mUsername = "";
    String mPassword = "";

    int mClientCheck = 0;
    int mUsernameCheck = 0;
    int mPasswordCheck = 0;

    private RelativeLayout rlvBackViewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btdemo_for_mqtt);

        mActivity = this;
        mContext = this;
        initView();
    }

    private void initView() {

        // Constant.BT_DEVICE_NAME = mDeviceNameList.get(position).toString();
        //  Constant.BT_DEVICE_MAC_ADDRESS = mDeviceMACAddressList.get(position).toString();
        baseRequest = new BaseRequest(this);
        //  txtBTDATASHOWID = (TextView) findViewById(R.id.txtBTDATASHOWID);
        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        txtIMEIshowID = (TextView) findViewById(R.id.txtIMEIshowID);
        txtIMEIBTNID = (TextView) findViewById(R.id.txtIMEIBTNID);

        txtClientBTNID = (TextView) findViewById(R.id.txtClientBTNID);
        txtClientGETBTNID = (TextView) findViewById(R.id.txtClientGETBTNID);
        txtClientshowID = (TextView) findViewById(R.id.txtClientshowID);

        txtUsernameBTNID = (TextView) findViewById(R.id.txtUsernameBTNID);
        txtUsernameGETBTNID = (TextView) findViewById(R.id.txtUsernameGETBTNID);
        txtUsernameShowID = (TextView) findViewById(R.id.txtUsernameShowID);


        txtPasswordBTNID = (TextView) findViewById(R.id.txtPasswordBTNID);
        txtPasswordGETBTNID = (TextView) findViewById(R.id.txtPasswordGETBTNID);
        txtPasswordShowID = (TextView) findViewById(R.id.txtPasswordShowID);


        txtMQTTBTNID = (TextView) findViewById(R.id.txtMQTTBTNID);
        txtTCPBTNID = (TextView) findViewById(R.id.txtTCPBTNID);


        txtIMEIshowID.setVisibility(View.GONE);
        txtClientshowID.setVisibility(View.GONE);
        txtUsernameShowID.setVisibility(View.GONE);
        txtPasswordShowID.setVisibility(View.GONE);

        changeButtonVisibilityRLV(true, 1.0f, txtClientGETBTNID);
        changeButtonVisibilityRLV(true, 1.0f, txtUsernameGETBTNID);
        changeButtonVisibilityRLV(true, 1.0f, txtPasswordGETBTNID);


        changeButtonVisibilityRLV(true, 0.5f, txtClientBTNID);
        changeButtonVisibilityRLV(true, 0.5f, txtUsernameBTNID);
        changeButtonVisibilityRLV(true, 0.5f, txtPasswordBTNID);

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        txtTCPBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonVisibilityRLV(true, 1.0f, txtMQTTBTNID);
                changeButtonVisibilityRLV(true, 0.5f, txtTCPBTNID);

                MQTT_TO_TCP = "TCP";
                new BluetoothCommunicationSwitchTCPTOMQTT().execute(":SWAP:0#", ":SWAP:0#", ":SWAP:0#");
            }
        });


        txtMQTTBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeButtonVisibilityRLV(true, 1.0f, txtTCPBTNID);
                changeButtonVisibilityRLV(true, 0.5f, txtMQTTBTNID);

                //  BluetoothCommunicationSwitchTCPTOMQTT

                MQTT_TO_TCP = "MQTT";
                new BluetoothCommunicationSwitchTCPTOMQTT().execute(":SWAP:1#", ":SWAP:1#", ":SWAP:1#");
            }
        });


        txtIMEIBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ssssss = "";

                String ssspp = ":GET IMEI#";

                new BluetoothCommunicationGetIMEINumber().execute(ssspp, ssspp, "ok");

                // callTOSendAuthkenAPI();
            }
        });


        txtClientBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ssssss = "";
                mClientCheck = 0;

                String  mClientIDStr;
                int mClientIDLen = 0;
                 mClientIDLen = mClientID.length();

                if(mClientIDLen > 0 && mClientIDLen < 10)
                {
                    mClientIDStr = "0"+mClientIDLen;
                }
                else if(mClientIDLen > 0)
                {
                    mClientIDStr = ""+mClientIDLen;
                }
                else
                {
                    mClientIDStr = "00";
                }



               // final String ClientCommand = ":CLIENTID :" + mClientID.length() + "," + mClientID;
                final String ClientCommand = ":CLIENTID :" + mClientIDStr + "," + mClientID;

                //   txtClientBTNID.setText(getString(R.string.st_Pleasewait));
                new BluetoothCommunicationmClientID().execute(ClientCommand, ClientCommand, ClientCommand);


            }
        });

        txtClientGETBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ssssss = "";

                mClientCheck = 1;
                final String ClientCommand = ":GET CLIENTID#";

                //txtClientGETBTNID.setText(getString(R.string.st_Pleasewait));
                new BluetoothCommunicationmClientID().execute(ClientCommand, ClientCommand, ClientCommand);
            }
        });


        txtUsernameBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                String  mUsernameStr;
                int mUsernameLen = 0;
                mUsernameLen = mUsername.length();

                if(mUsernameLen > 0 && mUsernameLen < 10)
                {
                    mUsernameStr = "0"+mUsernameLen;
                }
                else if(mUsernameLen > 0)
                {
                    mUsernameStr = ""+mUsernameLen;
                }
                else
                {
                    mUsernameStr = "00";
                }


               // final String ClientCommand = ":MQUSER :" + mUsername.length() + "," + mUsername;
                final String ClientCommand = ":MQUSER :" + mUsernameStr + "," + mUsername;

                mUsernameCheck = 0;
                // new BluetoothCommunicationmUsername().execute(ClientCommand, ClientCommand, ClientCommand);
                //   txtUsernameBTNID.setText(getString(R.string.st_Pleasewait));
                new BluetoothCommunicationmUsername().execute(ClientCommand, ClientCommand, ClientCommand);

            }
        });

        txtUsernameGETBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final String ClientCommand = ":GET MQUSER#";

                mUsernameCheck = 1;
                // txtUsernameGETBTNID.setText(getString(R.string.st_Pleasewait));
                new BluetoothCommunicationmUsername().execute(ClientCommand, ClientCommand, ClientCommand);
                // new BluetoothCommunicationmUsername().execute(ClientCommand, ClientCommand, ClientCommand);

            }
        });

        txtPasswordBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ssssss = "";
                String sLength = "";

                mPasswordCheck = 0;
                int iii = mPassword.length();
                if (iii < 9) {
                    sLength = "0" + iii;
                } else {
                    sLength = "" + iii;
                }

                String  mPasswordStr;
                int mPasswordLen = 0;
                mPasswordLen = mPassword.length();

                if(mPasswordLen > 0 && mPasswordLen < 10)
                {
                    mPasswordStr = "0"+mPasswordLen;
                }
                else if(mPasswordLen > 0)
                {
                    mPasswordStr = ""+mPasswordLen;
                }
                else
                {
                    mPasswordStr = "00";
                }


             //   final String ClientCommand = ":MQPASS :" + sLength + "," + mPassword;
                final String ClientCommand = ":MQPASS :" + mPasswordStr + "," + mPassword;
                // Toast.makeText(mActivity, ssssss, Toast.LENGTH_SHORT).show();

                new BluetoothCommunicationmPassword().execute(ClientCommand, ClientCommand, ClientCommand);

            }
        });

        txtPasswordGETBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ssssss = "";
                String sLength = "";

                mPasswordCheck = 1;

                final String ClientCommand = ":GET MQPASS#";
                // Toast.makeText(mActivity, ssssss, Toast.LENGTH_SHORT).show();
                new BluetoothCommunicationmPassword().execute(ClientCommand, ClientCommand, ClientCommand);
                /*final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                    }
                }, 1000);*/
            }
        });
        // initClickViewID();
    }


 /*   @RequiresApi(api = Build.VERSION_CODES.O)
    private  String readPemFile(File f) throws IOException {

        try (InputStream is = Files.newInputStream(f.toPath());
             DataInputStream dis = new DataInputStream(is))
        {
            //byte[] bytes = new byte[(int) f.length()];
            byte[] bytes = new byte[dis.available()];
           // byte[] bytes = new byte[(int) f.length()];
            System.out.println("bytesbytes==>>>>"+bytes.length);
           // dis.readFully(bytes);
            dis.read(bytes);
            System.out.println("new String(bytes)==>>"+bytes);
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();

//ByteBuffer.wrap simply wraps the byte array, it does not allocate new memory for it
            ByteBuffer srcBuffer = ByteBuffer.wrap(bytes);
//Now, we decode our srcBuffer into a new CharBuffer (yes, new memory allocated here, no can do)
            CharBuffer resBuffer = decoder.decode(srcBuffer);

//CharBuffer implements CharSequence interface, which StringBuilder fully support in it's methods
            StringBuilder yourStringBuilder = new StringBuilder(resBuffer);
            String s=new String();
           // s.replace("\n","");
           // yourStringBuilder.replace()
            for(byte b : bytes)
            {
                System.out.print((char)b);
                if((char)b!='\n' && (char)b!='\r') {
                    s += (char) b;
                }
                if(s.length()==98){
                    s="CA"+s;
                    System.out.println("new String(s)==>>"+s);
                    new BluetoothCommunicationForMotorStart().execute(s, "Vikas", "DEMO");
                    s="";
                }
            }
if(!s.equals("")) {
    new BluetoothCommunicationForMotorStart().execute("CA"+s, "Vikas", "DEMO");
}
            return new String(bytes);
        } catch (InvalidPathException e) {
            throw new IOException(e);
        }
    }

    private void initClickViewID() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            if (AllPopupUtil.pairedDeviceListGloable(mActivity)) {

                if (!Constant.BT_DEVICE_NAME.equalsIgnoreCase("") && !Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") && !Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                    //    new BluetoothCommunicationForMotorStart().execute(":TURNON#", ":TURNON#", "START");


                        new BluetoothCommunicationForMotorStart().execute(":YDATA#", ":YDATA#", "START");
                     }
                } else {
                    Intent intent = new Intent(mActivity, PairedDeviceActivity.class);
                    mActivity.startActivity(intent);
                }
                ///////////////write the query
                //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
            } else {
                // AllPopupUtil.btPopupCreateShow(mActivity);
                // AllPopupUtil.BT_OR_Internet_SelectionFun(mActivity);
                startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
            }



    }*/


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationSwitchTCPTOMQTT extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MQTT_TO_TCP1 = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                MQTT_TO_TCP1 = "";
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();


                        while (true) {

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            MQTT_TO_TCP1 = MQTT_TO_TCP1 + (char) mCharOne;
                            System.out.println("ssssssssssVikas ==>>" + ssssss);

                            if (iStream.available() <= 0) {
                                break;
                            }

                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();

                return false;
            }


            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            Toast.makeText(mActivity, MQTT_TO_TCP1, Toast.LENGTH_SHORT).show();

            // MQTT_TO_TCP
            // baseRequest.hideLoader();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationSwitchTCPTOMQTT1 extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MQTT_TO_TCP11 = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                MQTT_TO_TCP11 = "";
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();


                        while (true) {

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            MQTT_TO_TCP11 = MQTT_TO_TCP11 + (char) mCharOne;
                            System.out.println("ssssssssssVikas ==>>" + ssssss);

                            if (iStream.available() <= 0) {
                                break;
                            }

                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();

                return false;
            }


            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            Toast.makeText(mActivity, MQTT_TO_TCP11, Toast.LENGTH_SHORT).show();

            // MQTT_TO_TCP
            // baseRequest.hideLoader();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetIMEINumber extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ssssss = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();

                        for (int i = 0; i < 9; i++) {
                            int mCharOne1 = iStream.read();
                        }

                        while (true) {

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            ssssss = ssssss + (char) mCharOne;
                            System.out.println("ssssssssssVikas ==>>" + ssssss);

                            if (iStream.available() <= 0) {
                                break;
                            }


                        }


                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
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

            // Toast.makeText(mActivity, "1", Toast.LENGTH_SHORT).show();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // txtBTDATASHOWID.setText("");

                    if (ssssss != null && !ssssss.equalsIgnoreCase("")) {
                       /* changeButtonVisibilityRLV(true, 1.0f, txtClientBTNID);
                        changeButtonVisibilityRLV(true, 1.0f, txtUsernameBTNID);
                        changeButtonVisibilityRLV(true, 1.0f, txtPasswordBTNID);*/

                        txtIMEIshowID.setText(ssssss);
                        txtIMEIshowID.setVisibility(View.VISIBLE);

                        callTOSendAuthkenAPI();
                    } else {

                    }

                    //  addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                }
            });


        }
    }



    private void callTOSendAuthkenAPI() {

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    // swipeRefresh.setRefreshing(false);
                    IMEINEERAJModelView mAUTHModelView = gson.fromJson(Json, IMEINEERAJModelView.class);
                    getAuthResponse(mAUTHModelView);


                    /*AUTHModelView mAUTHModelView = gson.fromJson(Json, AUTHModelView.class);
                    getAuthResponse(mAUTHModelView);*/

                } catch (Exception e) {
                    //  swipeRefresh.setRefreshing(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                //swipeRefresh.setRefreshing(false);

                Toast.makeText(mContext, "IMEI Number not register.", Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                // swipeRefresh.setRefreshing(false);
                Toast.makeText(mContext, getString(R.string.st_Pleasecheckinternetconnection), Toast.LENGTH_LONG).show();
            }
        });

        Map<String, String> wordsByKey = new HashMap<>();

        wordsByKey.put("IMEI", ssssss.trim());
        //   wordsByKey.put("IMEI","38648723487236487264");

        baseRequest.callAPIGETIMEI(1, wordsByKey, NewSolarVFD.IMEI_SEND_AUTH_TOKEN_NEERAJ);/////
    }

    ///neerajjj
    private void getAuthResponse(IMEINEERAJModelView mAUTHModelView) {

        //  String sss1= mAUTHModelView.getData().getClientId();
        // String sss2= mAUTHModelView.getData().getUsername();
        //  String sss3= mAUTHModelView.getData().getPassword();

        mClientID = mAUTHModelView.getClientId();
        mUsername = mAUTHModelView.getUsername();
        mPassword = mAUTHModelView.getPassword();

        if(mClientID !=null || mUsername !=null || mPassword !=null)
        {
            changeButtonVisibilityRLV(true, 1.0f, txtClientBTNID);
            changeButtonVisibilityRLV(true, 1.0f, txtUsernameBTNID);
            changeButtonVisibilityRLV(true, 1.0f, txtPasswordBTNID);
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationmClientID extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ssssss = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();


                        while (true) {

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            ssssss = ssssss + (char) mCharOne;
                            System.out.println("ssssssssssVikas ==>>" + ssssss);


                            if (iStream.available() <= 0) {
                                break;
                            }

                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();

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


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mClientCheck == 0) {
                        // txtClientBTNID.setText(getString(R.string.st_SETCLIENTID));
                        Toast.makeText(mActivity, getString(R.string.st_SetClientIDSuccess), Toast.LENGTH_SHORT).show();
                        changeButtonVisibilityRLV(true, 1.0f, txtClientGETBTNID);
                    } else {
                        // txtClientGETBTNID.setText(getString(R.string.st_GETCLIENTID));
                        txtClientshowID.setVisibility(View.VISIBLE);
                        txtClientshowID.setText(ssssss);

                    }
                }
            });

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationmUsername extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ssssss = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();


                        while (true) {

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            ssssss = ssssss + (char) mCharOne;
                            System.out.println("ssssssssssVikas ==>>" + ssssss);

                            if (iStream.available() <= 0) {
                                break;
                            }

                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();

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


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mUsernameCheck == 0) {
                        //  txtUsernameBTNID.setText(getString(R.string.st_SETUSERNAME));
                        Toast.makeText(mActivity, getString(R.string.st_SetUsernameSuccess), Toast.LENGTH_SHORT).show();
                        changeButtonVisibilityRLV(true, 1.0f, txtUsernameGETBTNID);
                    } else {
                        /////////////   txtUsernameGETBTNID.setText(getString(R.string.st_GETUSERNAME));
                        txtUsernameShowID.setVisibility(View.VISIBLE);
                        txtUsernameShowID.setText(ssssss);

                    }
                }
            });


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationmPassword extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ssssss = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();

                        while (true) {

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            ssssss = ssssss + (char) mCharOne;
                            System.out.println("ssssssssssVikas ==>>" + ssssss);


                            if (iStream.available() <= 0) {
                                break;
                            }
                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                }
            } catch (Exception e) {

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
            super.onPostExecute(result);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mPasswordCheck == 0) {
                        // txtPasswordBTNID.setText(getString(R.string.st_SETPassword));

                        Toast.makeText(mActivity, getString(R.string.st_SetPasswordSuccess), Toast.LENGTH_SHORT).show();
                        changeButtonVisibilityRLV(true, 1.0f, txtPasswordGETBTNID);
                    } else {
                        // txtPasswordGETBTNID.setText(getString(R.string.st_GETUSERNAME));
                        txtPasswordShowID.setText(ssssss);
                        txtPasswordShowID.setVisibility(View.VISIBLE);
                    }
                }
            });


            // baseRequest.hideLoader();
        }
    }

   /*  private void getAuthResponse(AUTHModelView mAUTHModelView) {

        String sss1= mAUTHModelView.getData().getClientId();
        String sss2= mAUTHModelView.getData().getUsername();
        String sss3= mAUTHModelView.getData().getPassword();

        System.out.println("vihaansss1==>>"+sss1+"\nsss2==>>"+sss2+"\nsss3"+sss1);


    }
*/

    // changeButtonVisibilityRLV(true, 1.0f,rlvDayViewID);
    //  changeButtonVisibilityRLV(true, 0.5f,rlvMonthViewID);

    private void changeButtonVisibilityRLV(boolean state, float alphaRate, TextView txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
        //  hideBTN();
    }

}
