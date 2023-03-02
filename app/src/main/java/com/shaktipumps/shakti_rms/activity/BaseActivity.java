package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.multidex.BuildConfig;

import com.google.gson.Gson;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.Org_Client;
import com.shaktipumps.shakti_rms.bean.userDeviceCheck.UserCheckDeviceModel;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    Intent intent;
    private ArrayList<Customer_GPS_Search> arraylist;
    private DatabaseHelperTeacher databaseHelperTeacher;
    private Context mContext, mContex;
    private Activity mActivity;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    private String mUserID;

    private Customer_GPS_Search customer_gps;
    private boolean expand;

    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<String>> listDataChild ;
    private List<String> topChild = new ArrayList<>();;
    private ArrayList<Org_Client> clientArray = new ArrayList<>();;
    private Org_Client org_client;
    String m_androidId ="";
    private int clientid = 0, ParentId = 0, SupParentId = 0, first_level = 0, SupClientId = 0;

    private String newVersion = "0.0", currentVersion = "0.0", MUserId = "null", clientName = "null", ParentName = "null";
    private String token;
    private BaseRequest baseRequest;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = this;
        mContex = this;
        mActivity = this;
        String versionName = "0.0";
        //FirebaseApp.initializeApp(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        pref = mContext.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
     //   checkAndRequestPermissions();
        databaseHelperTeacher = new DatabaseHelperTeacher(this);/////////////////AUTHModelData base
        mUserID = pref.getString("key_muserid", "invalid_muserid");
        // check org client id
        clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0
        listDataChild = new HashMap<String, List<String>>();
         listDataHeader = new ArrayList<>();
        baseRequest = new BaseRequest(this);
        versionName = BuildConfig.VERSION_NAME;
        NewSolarVFD.versionNameForAll = BuildConfig.VERSION_NAME;


        topChild = new ArrayList<>();;
         arraylist = new ArrayList<Customer_GPS_Search>();


        //  Log.d("check_id", ""+clientid);

        if (clientid == 9999) {
            clientid = 0;
        }
        clientArray = new ArrayList<Org_Client>();

        try {
            NewSolarVFD.IMEI_NUMBER = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("NewSolarVFD.IMEI_NUMBER==>>"+NewSolarVFD.IMEI_NUMBER);

        /*try {
            FirebaseApp.initializeApp(this);
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                       // NewSolarVFD.FCM_TOKEN = task.getException().getMessage();
                        NewSolarVFD.FCM_TOKEN = Objects.requireNonNull(task.getException()).getMessage();
                        //Log.w("FCM TOKEN Failed", task.getException());
                        System.out.println("FCM jai task.getException()==>>"+task.getException());
                    } else {
                      //  NewSolarVFD.FCM_TOKEN = task.getResult().getToken();
                        NewSolarVFD.FCM_TOKEN = Objects.requireNonNull(task.getResult()).getToken();
                        //Log.i("FCM TOKEN", token);
                        System.out.println("FCM TOKEN==>>"+NewSolarVFD.FCM_TOKEN);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /* if (CustomUtility.isOnline(mContext)) {

            //callInsertAndUpdateAPI();

            new Worker().execute();
        }
        else
        {
            arraylist = databaseHelperTeacher.getDeviceListData();

           *//* if(arraylist.size() == 1) {
                conditionFunctionNAvigation(arraylist);
            }
            else {
                intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }*//*
            if(clientid > 0)
            {
                intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                System.out.println("arraylist.size()=>>"+arraylist.size());
                if(arraylist.size() == 1) {
                    Constant.CHECK_COUNT_OF_DELETE = 1;
                    conditionFunctionNAvigation(arraylist);

                }
                else {
                    intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }*/
        intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();

    }


    private void callCheckUserDeviceAPI() {

        if (CustomUtility.isOnline(mContex)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        UserCheckDeviceModel mUserCheckDeviceModel = gson.fromJson(Json, UserCheckDeviceModel.class);
                        getCheckUserDeviceResponse(mUserCheckDeviceModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {

                    Toast.makeText(mContex, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContex, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

           /* JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("DeviceTypeID", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.CHECK_USER_DEVICE_STATUS);/////*/

            Map<String, String> wordsByKey = new HashMap<>();

            wordsByKey.put("MUserId", mUserID);
            //   wordsByKey.put("IMEI","38648723487236487264");

            baseRequest.callAPIGETIMEI(1, wordsByKey, NewSolarVFD.CHECK_USER_DEVICE_STATUS);/////

        }
        else
        {

        }
    }

    private void getCheckUserDeviceResponse(UserCheckDeviceModel mUserCheckDeviceModel) {

        if (mUserCheckDeviceModel.getStatus()) {

            int mDeviceIDCheck = mUserCheckDeviceModel.getResponse().getDevice();
            int mPlantIDCheck = mUserCheckDeviceModel.getResponse().getPlantId();

            intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();



        }
    }




    private boolean checkAndRequestPermissions() {
        int IMEI_NUM = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);



        List<String> listPermissionsNeeded = new ArrayList<>();

        if (IMEI_NUM != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

            // if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(mContext, getResources().getString(R.string.Permission_granted_now_you_can_use_user_imeinumber_text), Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(mContext, getResources().getString(R.string.Oops_you_just_denied_the_permission_text), Toast.LENGTH_LONG).show();
                checkAndRequestPermissions();
            }

        } else {
            //permission_set_manually
            Toast.makeText(mContext, getResources().getString(R.string.permission_set_manually), Toast.LENGTH_LONG).show();
        }

    }





    private void conditionFunctionNAvigation(ArrayList<Customer_GPS_Search> arraylist)
    {
        Constant.CHECK_BACK_MENU_VIEW_ICON = 1;
        String deviceModelType = arraylist.get(0).getModelType();
        System.out.println("deviceModelType==>> "+deviceModelType+"\n");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate1 = new Date();
        Date strDate1 = null;
        try {
            String dddd = arraylist.get(0).getMobValidationDate();

            if((dddd !=null) && (!dddd.equalsIgnoreCase("null")))
            {
                strDate1 = sdf.parse(dddd);
            }
            else
            {

            }


           // strDate1 = sdf.parse(arraylist.get(0).getMobValidationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("deviceModelType====>>"+deviceModelType);
        if(clientid > 0)
        {
            if((deviceModelType.equalsIgnoreCase("17")))///1
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            } else if((deviceModelType.equalsIgnoreCase("49")) )////2
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }
            else if (deviceModelType.equalsIgnoreCase("95") || deviceModelType.equalsIgnoreCase("87"))/////4
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                mContext.startActivity(mIntent);
            }
            else if(deviceModelType.equalsIgnoreCase("2"))/////3
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            } else if(deviceModelType.equalsIgnoreCase("1"))/////4
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }else if(deviceModelType.equalsIgnoreCase("99"))/////4
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }
            else if (deviceModelType.equalsIgnoreCase("95") || deviceModelType.equalsIgnoreCase("87"))/////4
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                mContext.startActivity(mIntent);
            }else if(deviceModelType.equalsIgnoreCase("7"))//////5
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }else if(deviceModelType.equalsIgnoreCase("6"))//////6
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }else if(deviceModelType.equalsIgnoreCase("62"))/////7
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }
            else if ((deviceModelType.equalsIgnoreCase("71"))) {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                mContext.startActivity(mIntent);
            }else if ((deviceModelType.equalsIgnoreCase("69")))///1
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                mContext.startActivity(mIntent);
            }
            else if (deviceModelType.equalsIgnoreCase("66"))/////4
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                mContext.startActivity(mIntent);
            }else if (deviceModelType.equalsIgnoreCase("73"))/////nandi micro
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
            }
            else///////8
            {
                Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoAOneSS.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", 0);
                startActivity(mIntent);
            }
        }
        else
        {
            try {
                if((strDate1 !=null) && (!strDate1.equals("null"))) {
                    if (currentDate1.after(strDate1)) {
                        Intent mIntent = new Intent(mContext, EXPDummyPaymentActivity.class);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        mContext.startActivity(mIntent);
                    } else {
                        if ((deviceModelType.equalsIgnoreCase("17")))///pure grid tieeee
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoPureGridTie.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        }
                        else if ((deviceModelType.equalsIgnoreCase("69")))///1
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        }
                        else if (deviceModelType.equalsIgnoreCase("66"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            mContext.startActivity(mIntent);
                        }else if (deviceModelType.equalsIgnoreCase("73"))/////nandi micro
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandiMicro.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            mContext.startActivity(mIntent);
                        }else if (deviceModelType.equalsIgnoreCase("95") || deviceModelType.equalsIgnoreCase("87"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            mContext.startActivity(mIntent);
                        }else if ((deviceModelType.equalsIgnoreCase("49")))////2
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("2"))/////3
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("1"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("99"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("7"))//////5
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("6"))//////6
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        }else if (deviceModelType.equalsIgnoreCase("62"))/////7
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        }else if ((deviceModelType.equalsIgnoreCase("71"))) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            mContext.startActivity(mIntent);
                        }else if ((deviceModelType.equalsIgnoreCase("69")))///1
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            mContext.startActivity(mIntent);
                        }
                        else if (deviceModelType.equalsIgnoreCase("66"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            mContext.startActivity(mIntent);
                        }else if (deviceModelType.equalsIgnoreCase("73"))/////nandi micro
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
                        }else///////8
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoAOneSS.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", 0);
                            startActivity(mIntent);
                        }
                    }
                }
                else
                {
                    if ((deviceModelType.equalsIgnoreCase("17")))///1
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoPureGridTie.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("95") || deviceModelType.equalsIgnoreCase("87"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        mContext.startActivity(mIntent);
                    }else if ((deviceModelType.equalsIgnoreCase("49")))////2
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    } else if (deviceModelType.equalsIgnoreCase("2"))/////3
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    } else if (deviceModelType.equalsIgnoreCase("1"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    } else if (deviceModelType.equalsIgnoreCase("99"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    } else if (deviceModelType.equalsIgnoreCase("7"))//////5
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    } else if (deviceModelType.equalsIgnoreCase("6"))//////6
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    } else if (deviceModelType.equalsIgnoreCase("62"))/////7
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        startActivity(mIntent);
                    }else if ((deviceModelType.equalsIgnoreCase("71"))) {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        mContext.startActivity(mIntent);
                    }else if ((deviceModelType.equalsIgnoreCase("69")))///1
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("66"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", 0);
                        mContext.startActivity(mIntent);
                    }else if (deviceModelType.equalsIgnoreCase("73"))/////nandi micro
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
                        startActivity(mIntent);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //////////vikasssssssend

        //////////vikasssssssend

        finish();
    }



    private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;


            try {
                get_device();

                runOnUiThread(

                        new Runnable() {

                            @Override
                            public void run() {

                                if(clientid > 0)
                                {
                                    intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                if(arraylist.size() == 1) {
                                    //Constant.CHECK_COUNT_OF_DELETE = 1;
                                   // conditionFunctionNAvigation(arraylist);// chnage by vikas

                                    intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                }
                                //********************************* end app version *********************************
                            }
                        });

            } catch (Exception e) {

                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.i("SomeTag", System.currentTimeMillis() / 1000L
//                    + " post execute \n" + result);
        }

    }

    public void get_device() {


        String url = "null";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        if (CustomUtility.isOnline(mContext)) {
            try {
//********************************** check app version ********************************************
                //********************************** check logout session ********************************************
                param.clear();
                param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));

                String logout_status = CustomHttpClient.executeHttpPost1(NewSolarVFD.DELETE_DEVICE, param);

                JSONObject jo_logout = new JSONObject(logout_status);

                String isActive = (jo_logout.has("Active") ? jo_logout.getString("Active") : "true");

                if (isActive.equalsIgnoreCase("false")) {

                    logout();
                    System.exit(0); //
                }
//************************************ get device details ******************************************

                if (clientid == 0) {
                    // single user login
                    url = NewSolarVFD.GET_DEVICE + "?MUserId=" + pref.getString("key_muserid", "invalid_muserid") +
                            "&isActive=" + 1 +"&PlantId="+0;
                } else {
                    // organisation login
                    url = NewSolarVFD.ORG_GET_DEVICE + "?MUserId=" + pref.getString("key_muserid", "invalid_muserid") +
                            "&ClientId=" + clientid;
                }

                String obj = CustomHttpClient.executeHttpGet(url);

                Log.d("home_url", "" +  url);

                Log.d("home_obj", "" +  obj);

                if (obj != null) {

                    if (clientid == 0) // single user  data
                    {
                        JSONArray ja = new JSONArray(obj);
                        boolean mCheckFirstDB;

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jo = ja.getJSONObject(i);
                            customer_gps = new Customer_GPS_Search();
                            customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType"));
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(mUserID);
                            customer_gps.setMobile(jo.has("Mobile") ? jo.getString("Mobile") : "");
                            customer_gps.setTypeName(jo.getString("TypeName"));
                            customer_gps.setPumpStatus(jo.getString("PumpStatus"));
                            customer_gps.setIsLogin(jo.getString("IsLogin"));
                            customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                            customer_gps.setDeviceImage(jo.has("DeviceImage") ? jo.getString("DeviceImage"):"");
                            //customer_gps.setDeviceImage(jo.getString("DeviceImage") ? jo.getString("DeviceImage") : "");
                            customer_gps.setModelType(jo.has("ModelType") ? jo.getString("ModelType") : "");
                            arraylist.add(customer_gps);

                            if(i == 0)
                            {
                                mCheckFirstDB = true;
                            }
                            else
                            {
                                mCheckFirstDB = false;
                            }
                            databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"),  jo.getString("DeviceNo"),  jo.getString("DeviceType"),  jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"),jo.getInt("DeviceStatus"), mCheckFirstDB);
                           // databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"),  jo.getString("DeviceNo"),  jo.getString("DeviceType"),  jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);
                        }
                    }
                    else   //  organisation data
                    {
                        JSONObject jsonObj = new JSONObject(obj);
                        JSONArray ja = jsonObj.getJSONArray("Client");

                        expand = true; // expand first level

                        if(arraylist.size() > 0)
                            arraylist.clear();
                        if(listDataHeader.size() > 0)
                            listDataHeader.clear();
                        if(topChild.size() > 0)
                            topChild.clear();
                        if(listDataChild.size() > 0)
                            listDataChild.clear();
                        /*arraylist.clear();
                        listDataHeader.clear();
                        topChild.clear();
                        listDataChild.clear();*/

                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);

                                org_client = new Org_Client();
                                org_client.setClientId(jo.getString("ClientId"));
                                org_client.setClientName(jo.has("ClientName") ? jo.getString("ClientName") : "");
                                org_client.setParentId(jo.getString("ParentId"));
                                org_client.setParentName(jo.getString("ParentId"));

                                clientArray.add(i, org_client);

                                if (i == 0) {
                                    listDataHeader.add(jo.has("ParentName") ? jo.getString("ParentName") : "");
                                    expand = true; // expand first level
                                }
                                topChild.add(jo.has("ClientName") ? jo.getString("ClientName") : "");
                            }
                        }

                        if (ja.length() == 0) {
                            listDataHeader.add(clientName);
                            expand = true; // expand first level
                        }

                        listDataChild.put(listDataHeader.get(0), topChild); // Header, Child data
                        //orglistAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

                        JSONArray ja_device = jsonObj.getJSONArray("Device");
                        boolean mCheckFirstDB;
                        for (int i = 0; i < ja_device.length(); i++) {

                            JSONObject jo = ja_device.getJSONObject(i);
                            customer_gps = new Customer_GPS_Search();
                            customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType"));
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(mUserID);
                            customer_gps.setTypeName(jo.getString("TypeName"));
                            customer_gps.setPumpStatus(jo.getString("PumpStatus"));
                            customer_gps.setIsLogin(jo.getString("IsLogin"));
                            customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                            customer_gps.setDeviceImage(jo.has("DeviceImage") ?  jo.getString("DeviceImage") :"");
                            customer_gps.setMobile(jo.has("Mobile") ? jo.getString("Mobile") : "");
                            customer_gps.setModelType(jo.has("ModelType") ? jo.getString("ModelType") : "");

                            arraylist.add(customer_gps);

                            if(i == 0)
                            {
                                mCheckFirstDB = true;
                            }
                            else
                            {
                                mCheckFirstDB = false;
                            }

                           databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"),  jo.getString("DeviceNo"),  jo.getString("DeviceType"),  jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"),jo.getInt("DeviceStatus"), mCheckFirstDB);
                           // databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"),  jo.getString("DeviceNo"),  jo.getString("DeviceType"),  jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);
                        }

                        JSONArray ja_pclient = jsonObj.getJSONArray("PClient");
                        for (int i = 0; i < ja_pclient.length(); i++) {

                            JSONObject jo = ja_pclient.getJSONObject(i);
                            SupParentId = Integer.parseInt(jo.getString("SupParentId"));
                            SupClientId = Integer.parseInt(jo.getString("ClientId"));
                        }
                    }
                } else {
                    ///vikas changes
                    // CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                }

            } catch (Exception e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        arraylist = databaseHelperTeacher.getDeviceListData();
                        if(clientid > 0)
                        {
                            intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            if(arraylist.size() == 1) {

                                Constant.CHECK_COUNT_OF_DELETE = 1;
                                conditionFunctionNAvigation(arraylist);
                            }
                            else {
                                Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                                intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                       // arraylist = databaseHelperTeacher.getDeviceListData();
                        // adapter = new CustomerGPSListViewAdapter(context, arraylist);

                       /* if(arraylist.size() == 1)
                        {

                            conditionFunctionNAvigation(arraylist);
                        }
                        else
                        {
                            Constant.CHECK_BACK_MENU_VIEW_ICON = 0;


                        }*/


                    }
                });

            }

        } else {

            runOnUiThread(new Runnable() {
                public void run() {

                    arraylist = databaseHelperTeacher.getDeviceListData();
                    if(arraylist.size() == 1)
                    {
                        Constant.CHECK_COUNT_OF_DELETE = 1;
                        conditionFunctionNAvigation(arraylist);
                    }
                    else
                    {
                        Constant.CHECK_BACK_MENU_VIEW_ICON =0;
                    }
                }
            });

        }
//
//            }
//
//        }.start();
    }


    public void logout() {
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
        editor.commit();
    }


}
