package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.GlobalClass.AllPopupUtil;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class BTDemoBigDataActivity extends AppCompatActivity {

    private TextView txtBTDATASHOWID, txtBTNID, txtBTNClearID;
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
    private String ssssss =  "";
    private String sssss= "";

    String filePath;
    File file;
    private byte[] image;
    private String fileActualPath;
    private int mLengthCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btdemo_big_data);

        mActivity = this;
        initView();

    }

    private void initView() {

       // Constant.BT_DEVICE_NAME = mDeviceNameList.get(position).toString();
      //  Constant.BT_DEVICE_MAC_ADDRESS = mDeviceMACAddressList.get(position).toString();
        baseRequest = new BaseRequest(this);
      //  txtBTDATASHOWID = (TextView) findViewById(R.id.txtBTDATASHOWID);
        txtBTDATASHOWID = (TextView) findViewById(R.id.txtBTDATASHOWID);
        txtBTNID = (TextView) findViewById(R.id.txtBTNID);
        txtBTNClearID = (TextView) findViewById(R.id.txtBTNClearID);

        txtBTDATASHOWID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  initClickViewID();

                /*String ssspp = "{'CLIENTID':'JHJHJDASKJHDKJASDSD'," +
                        "'MUSER':'VIKAS'," +
                        "'MPASS':'123456'}";



                new BluetoothCommunicationForMotorStart().execute(ssspp, ssspp, "DEMO");*/
            }
        });

        txtBTNID.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
              //  initClickViewID();

                try {
                    filePath = "/storage/emulated/0/Android/data/client.pem";//Month_26-0018-0-18-03-19-0.xls";
                    // Log.d("picUri", picUri.toString());
                    Log.d("filePath", filePath);

                    File mFile = new File(filePath);

                    readPemFile(mFile);
                    System.out.println(mFile.getAbsolutePath()+"==>>");

                   // String[] mDataNameString = filePath.split("files/");
                 //   String[] mDataNameString1 = mDataNameString[1].split(".xls");
                 //   String[] mDataNameString2 = mDataNameString1[0].split("_");

                  ///  GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0],headerLenghtDAy);
                } catch (Exception e) {
                    e.printStackTrace();
                }

               /* String ssspp = "{'CLIENTID':'JHJHJDASKJHDKJASDSD'," +
                        "'MUSER':'VIKAS'," +
                        "'MPASS':'123456'}";



                new BluetoothCommunicationForMotorStart().execute(ssspp, ssspp, "DEMO");*/
            }
        });

       // initClickViewID();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
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



    }


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForMotorStart extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                       /* try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }*/
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
//                if (btSocket == null) {
//                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
//                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
//                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
//                    myBluetooth.cancelDiscovery();
//                }
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1500);
                        iStream = btSocket.getInputStream();

                        while (true){

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            ssssss = ssssss+mCharOne;
                            System.out.println("ssssssssssVikas ==>>"+ssssss);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                               // txtBTDATASHOWID.setText("");

                                txtBTDATASHOWID.setText(ssssss);
                                          //  addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                            }
                        });

                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (RetryCount != 0 && RetryCount > maxRetryCount) maxRetryCount = RetryCount;
                    mmCheckStart = 0;
                    int available;
                    int a;
                   /* while ((a = iStream.read()) > -1) {
                        baseRequest.hideLoader();
                        System.out.println("bytesReaded[i]=VIKAS_SHAKTI==>>>> " + a);
                    }*/
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
                mmCheckStart = 1;
                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
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
    private class BluetoothCommunicationForDemoJsonSend extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                       /* try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }*/
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
//                if (btSocket == null) {
//                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
//                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
//                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
//                    myBluetooth.cancelDiscovery();
//                }
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(500);
                        iStream = btSocket.getInputStream();

                        while (true){

                            int mCharOne = iStream.read();
                            //  int mCharTwo = iStream.read();
                            //  sssss = ""+iStream.read();
                            ssssss = ssssss+mCharOne;
                            System.out.println("ssssssssssVikas ==>>"+ssssss);




                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    // txtBTDATASHOWID.setText("");

                                    txtBTDATASHOWID.setText(ssssss);
                                    //  addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                }
                            });

                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (RetryCount != 0 && RetryCount > maxRetryCount) maxRetryCount = RetryCount;
                    mmCheckStart = 0;
                    int available;
                    int a;
                   /* while ((a = iStream.read()) > -1) {
                        baseRequest.hideLoader();
                        System.out.println("bytesReaded[i]=VIKAS_SHAKTI==>>>> " + a);
                    }*/
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
                mmCheckStart = 1;
                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
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
}
