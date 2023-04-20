package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.BuildConfig;

import com.shaktipumps.shakti_rms.GlobalClass.AllPopupUtil;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*import androidx.appcompat.app.AppCompatActivity;*/

//import androidx.appcompat.app.AppCompatActivity;

//import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContex;
    Context mContext;
    Activity mActivity;
//import androidx.appcompat.app.AppCompatActivity;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    String versionName = "0.0";
    ProgressDialog progressDialog;

    private ArrayList<Customer_GPS_Search> arraylist;
    private DatabaseHelperTeacher databaseHelperTeacher;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean vCheckBtOrInternetOPSN = false;

    DrawerLayout drawer_layout;

    private MenuItem mInternet;
    private MenuItem mBluetooth;

    int clientid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mContex = this;
        mContext = this;
        mActivity = this;

    /*    databaseHelperTeacher = new DatabaseHelperTeacher(this);


        arraylist = new ArrayList<Customer_GPS_Search>();
        if (arraylist.size() > 0)
            arraylist.clear();

        try {
            arraylist = databaseHelperTeacher.getDeviceListData();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //  clientid == 0 means single user login

        /*if (arraylist.size() == 1) {
            Constant.CHECK_COUNT_OF_DELETE = 1;
            conditionFunctionNAvigation(arraylist);
            finishAffinity();

        } else*/ {
            drawer_layout.setVisibility(View.VISIBLE);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            drawerFragment = (FragmentDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);

            versionName = BuildConfig.VERSION_NAME;
            NewSolarVFD.versionNameForAll = BuildConfig.VERSION_NAME;

            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();

            clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0
            //Toast.makeText(this,"Welcome,  "+  pref.getString("key_login_username"," ") +  "\nApp Version " + versionName,Toast.LENGTH_LONG).show();

            TextView tv_customer_name = (TextView) findViewById(R.id.customer_name);

            String user_name = getString(R.string.MenuWelcome) + ",  " + pref.getString("key_login_username", " ");
            tv_customer_name.setText(user_name);

            TextView tv_app_version = (TextView) findViewById(R.id.app_version);
            // String version = "App Version: " + versionName;
            String version = getString(R.string.MenuAppVersion) + ": " + versionName;
            tv_app_version.setText(version);

            if (clientid == 9999) {
                clientid = 0;
            }

            if (clientid == 0) {
                single_user_displayView(0);
            } else {
                org_user_displayView(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            progressDialog.dismiss();

            if (progressDialog != null)
                progressDialog = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void conditionFunctionNAvigation(ArrayList<Customer_GPS_Search> arraylist) {
        Constant.CHECK_BACK_MENU_VIEW_ICON = 1;
        // String deviceModelType = arraylist.get(0).getDeviceType();
        String deviceModelType = arraylist.get(0).getModelType();

        System.out.println("deviceModelType==>> " + deviceModelType + "\n");
        if ((deviceModelType.equalsIgnoreCase("17")))///1
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoPureGridTie.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if ((deviceModelType.equalsIgnoreCase("69")))///1
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("66"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("73"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandiMicro.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }
        else if (deviceModelType.equalsIgnoreCase("95") || deviceModelType.equalsIgnoreCase("87"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if ((deviceModelType.equalsIgnoreCase("49")))////2
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("2"))/////3
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("1"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("7"))//////5
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("6"))//////6
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("62"))/////7
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if ((deviceModelType.equalsIgnoreCase("71"))) {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if ((deviceModelType.equalsIgnoreCase("69")))///1
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("66"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("73"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandiMicro.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("82") || deviceModelType.equalsIgnoreCase("83"))//////6
        //else if(deviceModelType.equalsIgnoreCase("69"))//////6
        {
            //Intent mIntent = new Intent(mContext, USPCBridgeActivity.class);
            // Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKUSPC.class);///original
            //  Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoUSPC.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            // mIntent.putExtra("mDeviceDetail",  (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else///////8
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoAOneSS.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }
        finishAffinity();
        // finish();
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        if (clientid == 9999) {
            clientid = 0;
        }
        if (clientid == 0) {
            single_user_displayView(position);
        } else {
            org_user_displayView(position);
        }
    }

    ////Single User menu view viaksssssssss
    private void single_user_displayView(int position) {
        Fragment fragment = null;
        // String title = getString(R.string.app_name);
        //String title = "shakti_white";

        Intent intent = null;
        Context ctx = null;

        switch (position) {

            case 0:
                fragment = new HomeFragment();
                //   title = getString(R.string.title_home);
                break;

            case 1:

                    Intent intent1 = new Intent(mContext, AddDevice.class);
                    startActivity(intent1);

                break;

          /*  case 2:
                intent = new Intent(MainActivity.this, FaultReportActivity.class);
                intent.putExtra("from_date", "");
                intent.putExtra("to_date", "");
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(MainActivity.this, DataReportActivity.class);
                startActivity(intent);
                break;*/
            case 2:
                Intent ii = new Intent(MainActivity.this, ChangePasswordActivity.class);
                // Intent ii = new Intent(MainActivity.this, WiFiActivity.class);
                startActivity(ii);

                break;
            case 3:

                Constant.BT_DEVICE_NAME = "";
                Constant.BT_DEVICE_MAC_ADDRESS = "";

                Intent iii = new Intent(MainActivity.this, GETI_BT_MEINumberActivity.class);
                startActivity(iii);
                /*  Intent ii = new Intent(this, TempActivity.class);
                  startActivity(ii);*/
               /* Intent ii = new Intent(this, GetLocationActivity.class);
                startActivity(ii);*/

                break;
            case 4:

                //Constant.BT_DEVICE_NAME = "";
                //  Constant.BT_DEVICE_MAC_ADDRESS = "";

                Intent iiii = new Intent(MainActivity.this, LanguageChangeActivity.class);
                startActivity(iiii);
                /*  Intent ii = new Intent(this, TempActivity.class);
                  startActivity(ii);*/
               /* Intent ii = new Intent(this, GetLocationActivity.class);
                startActivity(ii);*/

                break;
            case 5:
                /*  Intent ii = new Intent(this, TempActivity.class);
                  startActivity(ii);*/
               /* Intent ii = new Intent(this, GetLocationActivity.class);
                startActivity(ii);*/
                logout();
                break;
          /*  case 5:
                logout();
            break;*/
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("RMS APP");
        }
    }


    private void org_user_displayView(int position) {
        Fragment fragment = null;
        // String title = getString(R.string.app_name);
        //String title = "shakti_white";

        Intent intent = null;
        Context ctx = null;

        switch (position) {
            case 0:

                fragment = new HomeFragment();
                //   title = getString(R.string.title_home);
                break;

           /* case 1:
                intent = new Intent(MainActivity.this, FaultReportActivity.class);
                intent.putExtra("from_date", "");
                intent.putExtra("to_date", "");
                startActivity(intent);
                break;

            case 2:

                intent = new Intent(MainActivity.this, DataReportActivity.class);
//                intent.putExtra("from_date","" );
//                intent.putExtra("to_date",  "");
                startActivity(intent);

                break;*/
            case 1:
                Intent ii = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(ii);

                break;
            case 2:
                /*Intent ii1 = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(ii1);*/

                NewSolarVFD.CHECK_LAT_LONG_AND_MQTT = 0;

                Constant.BT_DEVICE_NAME = "";
                Constant.BT_DEVICE_MAC_ADDRESS = "";

                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                            Intent intent1 = new Intent(mContext, Activity_pairdevice_for_mqtt.class);
                            // Intent intent = new Intent(mContext, AoneSSPairedDeviceActivitynoLogin.class);
                            mContext.startActivity(intent1);
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                }

                break;

            case 3:
                Intent iii = new Intent(MainActivity.this, LanguageChangeActivity.class);
                startActivity(iii);

                break;
            case 4:

                  //  Intent iii1 = new Intent(this, LatLongForHaredaServerActivity.class);
                 //   startActivity(iii1);
                NewSolarVFD.CHECK_LAT_LONG_AND_MQTT = 1;
                Constant.BT_DEVICE_NAME = "";
                Constant.BT_DEVICE_MAC_ADDRESS = "";

                BluetoothAdapter mBluetoothAdapter1 = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter1.isEnabled()) {
                    if (AllPopupUtil.pairedDeviceListGloable(mContext)) {

                        if (Constant.BT_DEVICE_NAME.equalsIgnoreCase("") || Constant.BT_DEVICE_NAME.equalsIgnoreCase(null) || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase("") || Constant.BT_DEVICE_MAC_ADDRESS.equalsIgnoreCase(null)) {
                            Intent intent1 = new Intent(mContext, Activity_pairdevice_for_mqtt.class);
                            // Intent intent = new Intent(mContext, AoneSSPairedDeviceActivitynoLogin.class);
                            mContext.startActivity(intent1);
                        }
                        ///////////////write the query
                        //   new BluetoothCommunicationForMotorStop().execute(":TURNON#", ":TURNON#", "START");
                    } else {
                        // AllPopupUtil.btPopupCreateShow(mContext);
                        mContext.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                } else {
                    //  AllPopupUtil.btPopupCreateShow(mContext);
                    mContext.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                }

                break;

            case 5:

                logout();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            //  getSupportActionBar().setIcon(R.drawable.new_logo);

            getSupportActionBar().setTitle("RMS APP");

            getSupportActionBar();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //   mInternet = menu.getItem(1);
        // mBluetooth = menu.getItem(0);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.st_Pleasewait)); // Setting Message
        progressDialog.setTitle(getString(R.string.st_Loading)); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner

        progressDialog.setCancelable(false);


        int id = item.getItemId();

        switch (id) {

            case R.id.action_logout:
                logout();
                return true;

            case R.id.action_BT_Selection:
                // logout();

                //  AllPopupUtil.fileUplaodToServer(mContex);
                AllPopupUtil.BT_OR_Internet_SelectionFun(mContex);

                return true;

        /*    case R.id.action_download:

                MainActivity.this.finish();
                Intent intent  = new Intent(MainActivity.this, MainActivity.class);

                startActivity(intent);

                return true;

            case R.id.action_route_map_menu:

                 showMap();

                return true; */

               /* case R.id.action_route_bluetooth:

                    if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                       startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                    }

                    Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 1;/////////this is for internet
                    vCheckBtOrInternetOPSN = true;

                    mBluetooth.setIcon(R.drawable.iv_bluetooth_selected);

                    mInternet.setIcon(R.drawable.iv_connection_unselected);


                return true;

                case R.id.action_route_internet:

                    Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;/////////this is for internet
                    vCheckBtOrInternetOPSN = false;
                    mInternet.setIcon(R.drawable.iv_connection_selected);
                    mBluetooth.setIcon(R.drawable.iv_bluetooth_unselected);


                return true;*/

        }

        return super.onOptionsItemSelected(item);
    }


    public void logout() {

        new AlertDialog.Builder(this)

                .setTitle(getString(R.string.st_LogoutAlert))
                .setMessage(getString(R.string.st_DoyouwanttoLogoutthisapplication))
                .setPositiveButton(getString(R.string.st_Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        editor.putString("key_login", "N");
                        editor.putString("key_OTP", "9999");
                        editor.putString("key_mobile_number", "9999999999");

                        editor.putString("key_otp_for_user", "9999");
                        editor.putString("key_mparentid", "9999");
                        editor.putString("key_muserid", "9999");
                        // editor.putString("key_clientid","9999");
                        editor.putString("key_clientid", "0");
                        editor.putString("key_login_username", "Invalid User");
                        editor.putString("key_clientid_for_map", "9999");
                        editor.putString("key_clientid_for_data_report", "9999");
                        SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "0");

//                       editor.putString("key_time_zone_city","Invalid" );
//                        editor.putString("key_time_zone_short","Invalid");
//                        editor.putString("key_time_zone_long", "Invalid");
//                        editor.putString("key_time_zone_change", "Invalid");

                        editor.commit();

                        finish();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        //Intent intent = new Intent(MainActivity.this, ActivitySelectionDataWay.class);
                        startActivity(intent);

                        dialog.cancel();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(getString(R.string.st_No), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                        dialog.cancel();
                        dialog.dismiss();

                    }
                })
                .show();

    }


    @Override
    public void onBackPressed() {

        exit();
    }

    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.st_DoyouwanttoExit));
        builder.setPositiveButton(getString(R.string.st_Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.st_No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



}
