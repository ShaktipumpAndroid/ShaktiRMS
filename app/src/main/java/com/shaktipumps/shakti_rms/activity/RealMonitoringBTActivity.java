package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.shaktipumps.shakti_rms.BuildConfig;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.RealMonitoring;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.other.Utils;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.searchlist.RealMonitoringListViewAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class RealMonitoringBTActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context ;
    SharedPreferences pref ;
    RealMonitoring realMonitoring ;
    RealMonitoring realTime ;

    RealMonitoringListViewAdapter adapter;

    ArrayList<String> mBTParameterList ;
    ArrayList<RealMonitoring> arraylist ;
    Timer timer ;
    Boolean back_flag = false ;

    ListView list;

    CustomUtility  customutility ;

    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;
    private InputStream iStream;
    private UUID mMyUDID;

    float mTotalTimeFloatData;
    boolean msg_flag ;

    int loading = 0;
    String  MUserId = "null" ,DeviceType = "null" ,DeviceNo = "null",Mobile="null",CustomerName="null" ,timezone = "null",
            RMSingalStr = "null",RMStatusOfProduct = "null" , RMLatitude = "null",RMDate = " " ;

    TextView real_monitor_icon,network_icon,real_monitor_name ,frame_date_time,tv_device_number,
              tv_customer_name,tv_mobile_number;
    Drawable network_signal_image,real_monitor_image;

    private DatabaseHelperTeacher databaseHelperTeacher;
    private BaseRequest baseRequest;
    private  DateFormat df;
    private  String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_monitoring_bt);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_real_monitoring);

        databaseHelperTeacher = new DatabaseHelperTeacher(this);
        baseRequest = new BaseRequest(context);

        mBTParameterList = new ArrayList<>();
        arraylist = new ArrayList<RealMonitoring>();

         df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
         dateString = df.format(Calendar.getInstance().getTime());

        real_monitor_icon = (TextView) findViewById(R.id.real_monitor_icon);
        network_icon = (TextView) findViewById(R.id.network_icon);
        real_monitor_name = (TextView) findViewById(R.id.real_monitor_name);
        frame_date_time = (TextView) findViewById(R.id.frame_date_time);
        tv_device_number= (TextView) findViewById(R.id.device_number);
        tv_customer_name= (TextView) findViewById(R.id.customer_name);
        tv_mobile_number= (TextView) findViewById(R.id.mobile_no);

        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        list = (ListView) findViewById(R.id.listview);



        Bundle bundle = getIntent().getExtras();
        MUserId = bundle.getString("MUserId");
        DeviceType = bundle.getString("DeviceType");
        DeviceNo = bundle.getString("DeviceNo");
        CustomerName = bundle.getString("CustomerName");
        Mobile = bundle.getString("Mobile");

        tv_device_number.setText(DeviceNo);
        tv_device_number.setTextColor( context .getResources().getColor(R.color.shakti_blue));

        frame_date_time.setText(dateString);
        frame_date_time.setTextColor( context .getResources().getColor(R.color.shakti_blue));

        tv_mobile_number.setText(Mobile);
        tv_mobile_number.setTextColor( context .getResources().getColor(R.color.shakti_blue));

        tv_customer_name.setText(CustomerName);
        tv_customer_name.setTextColor( context .getResources().getColor(R.color.shakti_blue));

      //  Log.d("intent" ,""+MUserId +"--"+DeviceType+"--"+DeviceNo);
        //  get_label_name();
        progressDialog = new ProgressDialog(RealMonitoringBTActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        progressDialog.dismiss();

        arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);

        if (adapter != null)
            adapter = null;
        // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
        adapter = new RealMonitoringListViewAdapter(getApplicationContext(), arraylist);
        list.setAdapter(adapter);

       // setTimer();

       // new BluetoothCommunicationForDynamicParameterRealTime().execute("01030F10001EC713", "Real Monitor", "OK");

        if(arraylist.size() > 0 )
            arraylist.clear();

        arraylist = databaseHelperTeacher.getDevicePARAMeterRealTimeListData(DeviceType);

        new BluetoothCommunicationForDynamicParameterRealTime().execute("01030F10001EC713", "Real Monitor", "OK");

        if(arraylist.size() > 0) {

            if (adapter != null)
                adapter = null;
            // recyclerViewAdapter = new HomeOrderAdapter("sss",getActivity());
            adapter = new RealMonitoringListViewAdapter(getApplicationContext(), arraylist);
            list.setAdapter(adapter);
        }
        setTimer();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share_file:
                //Check if permission is granted or not

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    check_Permission(this);
                }
                else {
                    share_file();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }


    public void setTimer() {

        timer = new Timer();
        //Set the schedule function and rate
        timer.scheduleAtFixedRate(
                new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(
                                new Runnable() {

                                    @Override
                                    public void run() {

                                        df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                                        dateString = df.format(Calendar.getInstance().getTime());
                                        frame_date_time.setText(dateString);
                                        new BluetoothCommunicationForDynamicParameterRealTime().execute("01030f100000C841147F", "Real Monitor", "OK");
                                    }
                                });
                    }
                }
                ,
                0,
//                2500);     //1000 = 1  second
                10000);     //1000 = 10  second
    }

    @Override
    protected void onResume() {
        setTimer();
        super.onResume();
    }

    @Override
    public void onBackPressed() {

      ExitFromRealMonitoring(context);
    }

    private  void ExitFromRealMonitoring(final Context context){

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.bt_popup_layout_view);
        //dialog.setTitle("");
        dialog.setCancelable(false);
        // set the custom dialog components - text, image and button
        TextView txtBTHeadingTopID = (TextView) dialog.findViewById(R.id.txtBTHeadingTopID);
        TextView txtBTDisableBTNID = (TextView) dialog.findViewById(R.id.txtBTDisableBTNID);
        TextView txtBTEnableBTNID = (TextView) dialog.findViewById(R.id.txtBTEnableBTNID);


        // if button is clicked, close the custom dialog
        txtBTDisableBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_flag  = false;
                dialog.dismiss();
            }
        });

        txtBTEnableBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    finish();
                    timer.cancel();
                    back_flag  = true;

                    dialog.dismiss();
                    //RealMonitoringActivity.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        dialog.show();

    }

    public static void check_Permission(final Context context)
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionsIntent.WRITE_EXTERNAL_STORAGE_PERMISSION);


        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionsIntent.WRITE_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    public void share_file() {

        try {

            File cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "Shakti_RMS/Real_Time_Data");

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            String path =
                    android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Shakti_RMS/Real_Time_Data"  + "/" + String.valueOf(System.currentTimeMillis())+".jpg";

            Utils.savePic(Utils.takeScreenShot(this), path);

            File dir = new File(path);

            Uri photoURI = FileProvider.getUriForFile(RealMonitoringBTActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    dir);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Shakti RMS Real Time AUTHModelData");
           // shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
            shareIntent.putExtra(Intent.EXTRA_STREAM,  photoURI  );

            shareIntent.setType("image/jpeg");

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(shareIntent, "Share"));

//*********************************************************************************
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDynamicParameterRealTime extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        int bytesRead = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    iStream = null;
            if(mBTParameterList.size() > 0)
            {
                mBTParameterList.clear();
            }
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

                    int[] bytesReaded = new int[160];

                    for (int i = 0; i < 6; i++) {
                        int mCharOne11 = iStream.read();
                    }
                    int[] mTotalTime;
                    int mTotalTimeInt = 0;
                    int jjj = 0;
                    mTotalTime = new int[25];
                    for (int i = 0; i < 160; i+= 4) {
                        try {
                            //bytesRead = iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();
                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i+1] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i+2] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mCharOne = iStream.read();
                            mCharTwo = iStream.read();
                            bytesReaded[i+3] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            // int mCharThree = iStream.read();
                            //   int mCharFour = iStream.read();

//                            bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                            mTotalTime[jjj] = bytesReaded[i];
                            mTotalTime[jjj] |= bytesReaded[i + 1] << 8;
                            mTotalTime[jjj] |= bytesReaded[i + 2] << 16;
                            mTotalTime[jjj] |= bytesReaded[i + 3] << 24;

                            System.out.println("mTotalTime==>>  "+jjj+" "+Float.intBitsToFloat(mTotalTime[jjj]));
                            mBTParameterList.add(Float.intBitsToFloat(mTotalTime[jjj])+"");
                            //arraylist.get(jjj).setValue(Float.intBitsToFloat(mTotalTime[jjj])+"");
                            jjj++;

                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }

                    while (iStream.available() > 0) {
                        int mCharOne11 = iStream.read();
                    }
//                    System.out.println("mTotalTimeInt==>>JAI Hind==>>"+Float.intBitsToFloat(mTotalTimeInt));
//                    mTotalTimeFloatData = Float.intBitsToFloat(mTotalTimeInt);
                }
            } catch (Exception e) {
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

            if  ( RMSingalStr.equalsIgnoreCase("0.00"))
            {
                network_signal_image = context.getResources().getDrawable( R.mipmap.network_0 );
                network_icon.setCompoundDrawablesWithIntrinsicBounds( null, null,network_signal_image, null);
            }

            if  ( RMSingalStr.equalsIgnoreCase("1.00"))
            {
                network_signal_image = context.getResources().getDrawable( R.mipmap.network_1 );
                network_icon.setCompoundDrawablesWithIntrinsicBounds( null, null,network_signal_image, null);
            }

            if  ( RMSingalStr.equalsIgnoreCase("2.00"))
            {
                network_signal_image = context.getResources().getDrawable( R.mipmap.network_2 );
                network_icon.setCompoundDrawablesWithIntrinsicBounds( null, null,network_signal_image, null);
            }

            if ( RMSingalStr.equalsIgnoreCase("3.00"))
            {
                network_signal_image = context.getResources().getDrawable( R.mipmap.network_3 );
                network_icon.setCompoundDrawablesWithIntrinsicBounds( null, null,network_signal_image, null);
            }


            if  ( RMSingalStr.equalsIgnoreCase("4.00"))
            {
                network_signal_image = context.getResources().getDrawable( R.mipmap.network_4 );
                network_icon.setCompoundDrawablesWithIntrinsicBounds( null, null,network_signal_image, null);
            }


            try {
                for (int i = 0; i < arraylist.size(); i++) {

                    arraylist.get(i).setValue(mBTParameterList.get(arraylist.get(i).getIndex()-1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            baseRequest.hideLoader();
            // setTimer();
        }
    }

}




