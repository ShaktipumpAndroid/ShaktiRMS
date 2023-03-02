package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class LatLongForHaredaServerActivity extends AppCompatActivity {

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


    ImageView imgShaktiLogoID;

    private RelativeLayout rlvBackViewID;
    private ImageView imgHeaderID;
    private ImageView imgInternetToggleID, imgBTToggleID;

    private EditText edtLatitudeID, edtLongitudeID;

    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    String mLatitude = "";
    String mLongitude = "";

    int mLatLength = 0;
    int mLongLength = 0;

    String mLatStr = "";
    String mLongStr = "";

    private TextView txtLongitudeSetBTNID;
    private TextView txtLatitudeSetBTNID;
    private TextView txtGetLatitudeID;
    private TextView txtGetLongitudeID;
    private TextView txtGetLatLongBTNID;

    private String mSetLat = "";
    private String mSetLong = "";
    private String mSetLatLong = "";


    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                //  Logger.d(String.format("%f, %f", location.getLatitude(), location.getLongitude()));
                //  drawMarker(location);
                mLocationManager.removeUpdates(mLocationListener);

            } else {
                // Logger.d("Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private LocationManager mLocationManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_lat_long_hareda_server);

        mContext = this;

        mMonthHeaderList = new ArrayList<>();

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {


        baseRequest = new BaseRequest(this);

        rlvEditConfirmPassViewID = (RelativeLayout) findViewById(R.id.rlvEditConfirmPassViewID);
        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);
        edtLatitudeID = (EditText) findViewById(R.id.edtLatitudeID);
        edtLongitudeID = (EditText) findViewById(R.id.edtLongitudeID);


        txtGetLatLongBTNID = (TextView) findViewById(R.id.txtGetLatLongBTNID);
        txtGetLatitudeID = (TextView) findViewById(R.id.txtGetLatitudeID);
        txtGetLongitudeID = (TextView) findViewById(R.id.txtGetLongitudeID);
        txtLongitudeSetBTNID = (TextView) findViewById(R.id.txtLongitudeSetBTNID);
        txtLatitudeSetBTNID = (TextView) findViewById(R.id.txtLatitudeSetBTNID);

        imgInternetToggleID = (ImageView) findViewById(R.id.imgInternetToggleID);
        imgBTToggleID = (ImageView) findViewById(R.id.imgBTToggleID);
        switch1 = (Switch) findViewById(R.id.switch1);

        txtGetLatLongBTNID.setVisibility(View.VISIBLE);
        rlvEditConfirmPassViewID.setVisibility(View.GONE);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //key_login_username
        mUsernamePrefSTR = pref.getString("key_login_username", "login_status");

        imgShaktiLogoID = (ImageView) findViewById(R.id.imgShaktiLogoID);
        txtNextBTNID = (TextView) findViewById(R.id.txtNextBTNID);
        txtIMEINUMBERValueID = (TextView) findViewById(R.id.txtIMEINUMBERValueID);
        rlvEditConfirmPassViewID = (RelativeLayout) findViewById(R.id.rlvEditConfirmPassViewID);


        imgShaktiLogoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rlvEditConfirmPassViewID.setVisibility(View.GONE);
            }
        });
        clickEventOnBTN();

        getCurrentLocation();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)) {
            //Snackbar.make(mMapView, R.string.error_location_provider, Snackbar.LENGTH_INDEFINITE).show();
        } else {
            if (isNetworkEnabled) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

           /* if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }*/
        }
        if (location != null) {
            // Logger.d(String.format("getCurrentLocation(%f, %f)", location.getLatitude(),location.getLongitude()));
            //  drawMarker(location);

            System.out.println("location.getLatitude() == >> " + location.getLatitude() + "\n location.getLongitude() == >> " + location.getLongitude());

            edtLatitudeID.setText("" + location.getLatitude());
            edtLongitudeID.setText("" + location.getLongitude());
        }
    }

    private void clickEventOnBTN() {

        txtGetLatLongBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGetLatitudeID.setText("");
                txtGetLongitudeID.setText("");

                new BluetoothCommunicationGetLatitudeLongitude().execute(":GET LATLONG#", ":GET LATLONG#", "OKAY");

            }
        });


        txtLatitudeSetBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLatitude = edtLatitudeID.getText().toString().trim();

                mLatLength = mLatitude.length();

                if (mLatLength > 0 && mLatLength < 9) {
                    mLatStr = "0" + mLatLength;
                } else {
                    mLatStr = "" + mLatLength;
                }

                if (mLatitude.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter Latitude.", Toast.LENGTH_SHORT).show();
                } else {
                    // new BluetoothCommunicationGetIMEINUMBER().execute(":GET IMEI#", ":GET IMEI#", "OKAY");
                    new BluetoothCommunicationSetLatitude().execute(":LAT :" + mLatStr + "," + mLatitude + "#", ": Set Latitude#", "OKAY");
                    // new BluetoothCommunicationGetLongitude().execute(":LONG :"+mLongStr+","+mLongitude+"#", ": Set Longitude#", "OKAY");


                }


            }
        });

        txtLongitudeSetBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLongitude = edtLongitudeID.getText().toString().trim();


                mLongLength = mLongitude.length();


                if (mLongLength > 0 && mLongLength < 9) {
                    mLongStr = "0" + mLongLength;
                } else {
                    mLongStr = "" + mLongLength;
                }


                if (mLongitude.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter Longitude.", Toast.LENGTH_SHORT).show();
                } else {
                    // new BluetoothCommunicationGetIMEINUMBER().execute(":GET IMEI#", ":GET IMEI#", "OKAY");
                    //  new BluetoothCommunicationGetLatitude().execute(":LAT :"+mLatStr+"," +mLatitude+ "#", ": Set Latitude#", "OKAY");
                    new BluetoothCommunicationSetLongitude().execute(":LONG :" + mLongStr + "," + mLongitude + "#", ": Set Longitude#", "OKAY");


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
///edtLatitudeID, edtLongitudeID;

        txtNextBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSetLat = "";
                mSetLong = "";
                mSetLatLong = "";


                mLatitude = edtLatitudeID.getText().toString().trim();
                mLatLength = mLatitude.length();

                if (mLatLength > 0 && mLatLength < 10) {
                    mLatStr = "0" + mLatLength;
                } else if (mLatLength > 0) {
                    mLatStr = "" + mLatLength;
                } else {
                    mLatStr = "00";
                }

                System.out.println("Vikas_mLatStr == >> " + mLatStr);

               /* if (mLatitude.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter Latitude.", Toast.LENGTH_SHORT).show();
                }  else {
                    // new BluetoothCommunicationGetIMEINUMBER().execute(":GET IMEI#", ":GET IMEI#", "OKAY");
                    new BluetoothCommunicationGetLatitude().execute(":LAT :"+mLatStr+"," +mLatitude+ "#", ": Set Latitude#", "OKAY");
                    // new BluetoothCommunicationGetLongitude().execute(":LONG :"+mLongStr+","+mLongitude+"#", ": Set Longitude#", "OKAY");


                }*/
                //  mLongLength = mLongitude.length();

                mLongitude = edtLongitudeID.getText().toString().trim();
                mLongLength = mLongitude.length();

                if (mLongLength > 0 && mLongLength < 10) {
                    mLongStr = "0" + mLongLength;
                } else if (mLongLength > 0) {
                    mLongStr = "" + mLongLength;
                } else {
                    mLongStr = "00";
                }

                System.out.println("Vikas_mLongStr == >> " + mLongStr);

                if (mLatitude.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter Latitude.", Toast.LENGTH_SHORT).show();
                } else if (mLongitude.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter Longitude.", Toast.LENGTH_SHORT).show();
                } else {
                    // new BluetoothCommunicationGetIMEINUMBER().execute(":GET IMEI#", ":GET IMEI#", "OKAY");
                    new BluetoothCommunicationSetLatitude().execute(":LAT :" + mLatStr + "," + mLatitude + "#", ": Set Latitude#", "OKAY");
                    //  new BluetoothCommunicationGetLatitude().execute(":LAT :-10," +mLatitude+ "#", ": Set Latitude#", "OKAY");
                    // new BluetoothCommunicationGetLongitude().execute(":LONG :"+mLongStr+","+mLongitude+"#", ": Set Longitude#", "OKAY");
                }
                //
                //   rlvEditConfirmPassViewID.setVisibility(View.VISIBLE);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationSetLatitude extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;


        int iii = 0;

        @Override
        protected void onPreExecute() {

            mSetLat = "";
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            baseRequest.showLoader();
            super.onPreExecute();
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


                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {

                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //  baseRequest.hideLoader();
                        e1.printStackTrace();
                    }
                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";

                    System.out.println("iStream.available() vikas01==>>" + iStream.available());


                    while (true) {

                        // SS += (char) iStream.read();
                        int mCharOne = iStream.read();
                        //  int mCharTwo = iStream.read();
                        //  sssss = ""+iStream.read();
                        mSetLat = mSetLat + (char) mCharOne;
                        //  mSetLat = mSetLat + "" + SS;
                        //  break;

                        if (iStream.available() <= 0) {
                            break;
                        }

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                //    baseRequest.hideLoader();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    new BluetoothCommunicationSetLatitude().execute(":LAT :" + mLatStr + "," + mLatitude + "#", ": Set Latitude#", "OKAY");

                                }
                            }, 1000);

                    }
                });


                return false;
            }

            // baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            //baseRequest.hideLoader();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mSetLat != null && !mSetLat.equalsIgnoreCase("")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                new BluetoothCommunicationSetLongitude().execute(":LONG :" + mLongStr + "," + mLongitude + "#", ": Set Longitude#", "OKAY");

                            }
                        }, 2000);
                    } else {

                    }
                }
            });

           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    new BluetoothCommunicationSetLongitude().execute(":LONG :"+mLongStr+","+mLongitude+"#", ": Set Longitude#", "OKAY");

                }
            }, 3000);*/


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationSetLongitude extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;


        int iii = 0;

        @Override
        protected void onPreExecute() {

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            mSetLong = "";

            baseRequest.showLoader();

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                       /* try {
                            //Socket.close();
                           // btSocket = null;
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


                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {

                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //  baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";


                    System.out.println("iStream.available()==>>" + iStream.available());


                    int iiiii = 0;
                    while (true) {

                        int mCharOne = iStream.read();
                        //  int mCharTwo = iStream.read();
                        //  sssss = ""+iStream.read();
                        mSetLong = mSetLong + (char) mCharOne;
                        System.out.println("ssssssssssVikas ==>>" + mSetLong);

                        if (iStream.available() <= 0) {
                            break;
                        }


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


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mSetLong != null && !mSetLong.equalsIgnoreCase("")) {


                                System.out.println("mSetLong==>>" + mSetLong);
                                Toast.makeText(mContext, getString(R.string.st_GetLongitudeSuccess), Toast.LENGTH_SHORT).show();
                                baseRequest.hideLoader();

                    } else {

                    }
                }
            });


           /*     runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ///  addHeadersMonths();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String msms = SSS.trim();

                                System.out.println("msms==>>"+msms);
                                Toast.makeText(mContext, getString(R.string.st_GetLongitudeSuccess), Toast.LENGTH_SHORT).show();
                                txtGetLatLongBTNID.setVisibility(View.VISIBLE);
                                baseRequest.hideLoader();
                            }
                        }, 3000);


                    }
                });*/


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetLatitudeLongitude extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;



        @Override
        protected void onPreExecute() {

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            mSetLatLong = "";
            baseRequest.showLoader();

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                       /* try {
                            //Socket.close();
                           // btSocket = null;
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


                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {

                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //  baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String mSS = "";


                    System.out.println("iStream.available()==>>" + iStream.available());


                    int iiiii = 0;
                    while (true) {
//mSetLatLong
                        int mCharOne1 = iStream.read();
                        //  int mCharTwo = iStream.read();
                        //  sssss = ""+iStream.read();
                        mSetLatLong = mSetLatLong + (char) mCharOne1;
                        System.out.println("mSetLatLong ==>>" + mSetLatLong);

                        if (iStream.available() <= 0) {
                            break;
                        }
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

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ///  addHeadersMonths();
                    try {
                        System.out.println("GetSSS==>>" + mSetLatLong);
                        String[] mGetLatLong = mSetLatLong.split(",");
                        rlvEditConfirmPassViewID.setVisibility(View.VISIBLE);

                        // "Longitude Set Success"

                        String mPULat = mGetLatLong[0].replace("Longitude Set Success", "");
                        String mPULong = mGetLatLong[1].replace("Longitude Set Success", "");

                        txtGetLatitudeID.setText(getString(R.string.st_GetLatitude) + " " + mPULat);
                        txtGetLongitudeID.setText(getString(R.string.st_GetLongitude) + " " + mPULong);


                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    //  Toast.makeText(mContext, GetSSS, Toast.LENGTH_SHORT).show();
                }
            });

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
