package com.shaktipumps.shakti_rms.activity.GRID;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.AddPlantModelView;
import com.shaktipumps.shakti_rms.bean.GetPlant.GetPlantModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.GPSTracker;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.GeocodingLocation;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceINPlant extends AppCompatActivity {

    private Context mContextt;
    private RelativeLayout rlvScanBTNID;
    private EditText txtQRCodeScanID;
    double inst_latitude_double,
            inst_longitude_double;
    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String otp = "null", device_number = "null";
    private int mDeviceTypeScan = 0;
    private BaseRequest baseRequest;
    private TextView txtAdd_Plant_and_DeviceID;


    private EditText edtPlantnameID, edtMobileNumberID, edtEnterAddressID;
    private EditText edtaddMobileNumberID, edtaddEnterAddressID;

    private LinearLayout lvlAddPlantViewID;
    private LinearLayout lvlAddDeviceViewID;
    private LinearLayout lvlAddPlantButtonViewID;
    private LinearLayout lvladddeviceButtonViewID;

    private String aStringCheck = "1";
    String otp_status = "false", MuserId = "null", sms_url = "null", Mobile_no = "null", MDeviceId = "null", MDId = "null", isvalid = "null";
    private String mLatitude;
    private String mLongitude;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private  String mUserid;


    private String mPlantName, mPlantMobile, mPlantAddress;
    private String mDeviceNo, mMobileNo, mAddress;

    private ProgressDialog progressDialog;
    private RelativeLayout rlvBackViewID;

    private Intent myIntent;
    private String mPlantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_in_plant);
        mContextt =  this;
        baseRequest = new BaseRequest(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        mUserid =  pref.getString("key_muserid", "invalid_muserid");
        initView();
    }

    private void initView() {

        myIntent = getIntent(); // gets the previously created intent
        mPlantID = myIntent.getStringExtra("PlantID"); // will return "FirstKeyValue"

        rlvBackViewID  = findViewById(R.id.rlvBackViewID);
        lvlAddPlantViewID  = findViewById(R.id.lvlAddPlantViewID);
        lvlAddDeviceViewID  = findViewById(R.id.lvlAddDeviceViewID);
        txtAdd_Plant_and_DeviceID  = findViewById(R.id.txtAdd_Plant_and_DeviceID);
        rlvScanBTNID  = findViewById(R.id.rlvScanBTNID);
        txtQRCodeScanID  = findViewById(R.id.txtQRCodeScanID);

        edtaddEnterAddressID  = findViewById(R.id.edtaddEnterAddressID);
        edtaddMobileNumberID  = findViewById(R.id.edtaddMobileNumberID);

        edtPlantnameID  = findViewById(R.id.edtPlantnameID);
        edtMobileNumberID  = findViewById(R.id.edtMobileNumberID);
        edtEnterAddressID  = findViewById(R.id.edtEnterAddressID);
        lvlAddPlantButtonViewID  = findViewById(R.id.lvlAddPlantButtonViewID);
        lvladddeviceButtonViewID  = findViewById(R.id.lvladddeviceButtonViewID);


        cleckEventAll();

        txtAdd_Plant_and_DeviceID.setText("Add Device");
        lvlAddDeviceViewID.setVisibility(View.VISIBLE);
        lvlAddPlantViewID.setVisibility(View.GONE);
       // getGpsLocation();
       // callGetPlantListCheckAPI();


    }

    private void cleckEventAll() {


        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        rlvScanBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScannQRcode();

            }
        });

        lvlAddPlantButtonViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAddress =  edtEnterAddressID.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(mAddress,
                        getApplicationContext(), new GeocoderHandler());


                if(checkPlantValidation())
                {
                    callAddPlantAPI();
                }

            }
        });
        lvladddeviceButtonViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          /*      mAddress =  edtEnterAddressID.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(mAddress,
                        getApplicationContext(), new GeocoderHandler());*/

                if(checkAddValidation())
                {
                    serverAddDevice();
                }

            }
        });

    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                  //  locationAddress = bundle.getString("address");
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            //latLongTV.setText(locationAddress);

            String [] ssss = locationAddress.split("SAN");

            mLatitude = ssss[0];
            mLongitude = ssss[1];

            //Toast.makeText(mContextt, "mLatitude==>>"+mLatitude+"\nmLongitude==>>"+mLongitude, Toast.LENGTH_SHORT).show();


        }
    }

    public void getGpsLocation() {
        GPSTracker gps = new GPSTracker(mContextt);

        if (gps.canGetLocation()) {
            inst_latitude_double = gps.getLatitude();
            inst_longitude_double = gps.getLongitude();
            if (inst_latitude_double == 0.0) {
                CustomUtility.ShowToast("Lat Long not captured, Please try again.", mContextt);
            } else {

                CustomUtility.ShowToast("Latitude:-" + inst_latitude_double + "     " + "Longitude:-" + inst_longitude_double, mContextt);

            }
        } else {
            gps.showSettingsAlert();
        }
    }

    private Boolean checkPlantValidation() {


       mDeviceNo =  txtQRCodeScanID.getText().toString();
       mMobileNo =  edtMobileNumberID.getText().toString();
       mAddress =  edtEnterAddressID.getText().toString();

       mPlantName =  edtPlantnameID.getText().toString();
        mPlantMobile = edtMobileNumberID.getText().toString();
        mPlantAddress = edtEnterAddressID.getText().toString();


        if(mPlantName.equalsIgnoreCase("") || mPlantName.equalsIgnoreCase("Null"))
        {
            Toast.makeText(mContextt, "Please enter plant name", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(mPlantMobile.equalsIgnoreCase("") || mPlantMobile.equalsIgnoreCase("Null"))
        {
            Toast.makeText(mContextt, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(mPlantAddress.equalsIgnoreCase("") || mPlantAddress.equalsIgnoreCase("Null"))
        {
            Toast.makeText(mContextt, "Please enter plant address", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
            return  true;
        }

    }

    private Boolean checkAddValidation() {


        mDeviceNo =  txtQRCodeScanID.getText().toString();
        mMobileNo =  edtaddMobileNumberID.getText().toString();
        mAddress =  edtaddEnterAddressID.getText().toString();

        mPlantName =  edtPlantnameID.getText().toString();
        mPlantMobile = edtMobileNumberID.getText().toString();
        mPlantAddress = edtEnterAddressID.getText().toString();


        if(mDeviceNo.equalsIgnoreCase("") || mDeviceNo.equalsIgnoreCase("Null"))
        {
            Toast.makeText(mContextt, "Please enter and scan bar code", Toast.LENGTH_SHORT).show();
            return  false;
        }
       /* else if(mMobileNo.equalsIgnoreCase("") || mMobileNo.equalsIgnoreCase("Null"))
        {
            Toast.makeText(mContextt, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if(mAddress.equalsIgnoreCase("") || mAddress.equalsIgnoreCase("Null"))
        {
            Toast.makeText(mContextt, "Please enter plant address", Toast.LENGTH_SHORT).show();
            return  false;
        }*/
        else
        {
            return  true;
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//            Toast.makeText(getApplicationContext(),scanResult.toString(),Toast.LENGTH_LONG).show();
            if (scanResult != null) {
                String scanContent = "null";
                scanContent = scanResult.getContents();
                String scanFormat = scanResult.getFormatName();


                if (scanResult.getRawBytes() != null) {

                    device_number = "";
                    device_number = scanContent.trim();

                    try {

                        String[] aaaSSS = device_number.split("-");
                        mDeviceTypeScan = Integer.parseInt(aaaSSS[0]);

                        if((mDeviceTypeScan != 70) && (mDeviceTypeScan != 71) && (mDeviceTypeScan != 74)){
                            Toast.makeText(mContextt, "Wrong device you try to add!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            txtQRCodeScanID.setText(device_number);

                           // callGetPlantListCheckAPI();
                        }




                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                  /* // Mobile_no = inputPhone.getText().toString();

                    Intent intent1 = new Intent(AddPlantAndDevice.this, Add_latitudeAndLongitude.class);
                    intent1.putExtra("Mobile", Mobile_no);
                    intent1.putExtra("MUserId", MuserId);
                    intent1.putExtra("MDeviceId", MDeviceId);
                    intent1.putExtra("mSerialNumber", device_number);
                    intent1.putExtra("Latitude", mLatitude);
                    intent1.putExtra("Longitude", mLongitude);
                    intent1.putExtra("StringCheckLoc", aStringCheck);

                    //  Log.d("intent1",MuserId +"--"+  MDeviceId +"--"+ Mobile_no);
                    startActivity(intent1);*/
                    // serverLogin();//vikasssss
                }
            } else {
                Log.d("result", "no scan ");
                Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();

            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received , Please Try again !", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }
    public void ScannQRcode() {

            try {
                IntentIntegrator integrator = new IntentIntegrator(AddDeviceINPlant.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan QR Code");
                integrator.setResultDisplayDuration(0);
               // integrator.setScanningRectangle(400, 400);
                integrator.setScanningRectangle(400, 400);
                // integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.initiateScan();
                //  integrator.addExtra("data", "hello");
                integrator.setLegacyCaptureLayout(1);
              //  integrator.setCaptureLayout(2);

            } catch (ActivityNotFoundException anfe) {
                Log.e("onCreate", "Scanner Not Found", anfe);
            }


    }

    public static void check_Permission(final Context context) {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA},
                    PermissionsIntent.REQUEST_CAMERA);


        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA},
                    PermissionsIntent.REQUEST_CAMERA);
        }
    }




    private void callGetPlantListCheckAPI() {

        if (CustomUtility.isOnline(mContextt)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        GetPlantModelView mGetPlantModelView = gson.fromJson(Json, GetPlantModelView.class);
                        getPlantListStatusCheck(mGetPlantModelView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {

                    txtAdd_Plant_and_DeviceID.setText("Add Plant");
                    lvlAddDeviceViewID.setVisibility(View.GONE);
                    lvlAddPlantViewID.setVisibility(View.VISIBLE);
                 //   Toast.makeText(mContextt, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContextt, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("id", mUserid);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
          //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////

        }
        else
        {

        }
    }

    private void getPlantListStatusCheck(GetPlantModelView mGetPlantModelView) {

        if (mGetPlantModelView.getStatus()) {

            txtAdd_Plant_and_DeviceID.setText("Add Device");
            lvlAddDeviceViewID.setVisibility(View.VISIBLE);
            lvlAddPlantViewID.setVisibility(View.GONE);
        }
    }



    private void callAddPlantAPI() {

        if (CustomUtility.isOnline(mContextt)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        AddPlantModelView mGetPlantModelView = gson.fromJson(Json, AddPlantModelView.class);
                        getAddPlantData(mGetPlantModelView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {

                   // txtAdd_Plant_and_DeviceID.setText("Add Plant");
                  //  lvlAddDeviceViewID.setVisibility(View.GONE);
                 //   lvlAddPlantViewID.setVisibility(View.VISIBLE);
                    Toast.makeText(mContextt, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContextt, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("plantName", mPlantName);
                jsonObject.addProperty("plantAddress", mAddress);
                jsonObject.addProperty("mUserId", mUserid);
                jsonObject.addProperty("latitude", mLatitude);
                jsonObject.addProperty("longitude", mLongitude);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
           // baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ADD_PLANT_NEW);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.ADD_PLANT_NEW);/////

        }
        else
        {

        }
    }

    private void getAddPlantData(AddPlantModelView mGetPlantModelView) {

        if (mGetPlantModelView.getStatus()) {

            txtAdd_Plant_and_DeviceID.setText("Add Device");
            lvlAddDeviceViewID.setVisibility(View.VISIBLE);
            lvlAddPlantViewID.setVisibility(View.GONE);
        }
    }

    private void serverAddDevice() {
        ArrayList<String> al;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
       // final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();
        param.clear();
      //  otp_param.clear();

        param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));
        param.add(new BasicNameValuePair("DeviceNo", mDeviceNo));
        param.add(new BasicNameValuePair("MobileNo", mMobileNo));  // add in 2.4 version
        param.add(new BasicNameValuePair("Latitude", mLatitude));  // add in 2.4 version
        //param.add(new BasicNameValuePair("Latitude", "888.8888"));  // add in 2.4 version
        param.add(new BasicNameValuePair("Longitude", mLongitude));  // add in 2.4 version
        //param.add(new BasicNameValuePair("Longitude", "99.999"));  // add in 2.4 version
        param.add(new BasicNameValuePair("islocation", "0")); // add in 2.4 version
        param.add(new BasicNameValuePair("PlantId", mPlantID)); // add in 2.4 version

        Log.d("Latitude", "Latitude==>>" + mLatitude + "\nLongitude==>>" + mLongitude);
      //  otp = "" + ((int) (Math.random() * 9000) + 1000);
/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(mContextt, "", "Please wait !");
        new Thread() {
            public void run() {
                if (CustomUtility.isOnline(mContextt)) {
                    try {
                        String obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.GET_DEVICE, param);
                        Log.d("obj_device", "" + obj);
                        if (obj != null) {
                            // progressDialog.dismiss();
                            JSONObject jresponse = new JSONObject(obj);
                            // userId_status != null i.e new user is successfully created on server
                            MDId = jresponse.getString("MDId");
                            isvalid = jresponse.getString("isvalid");
                            MuserId = jresponse.getString("MUserId");
                            MDeviceId = jresponse.getString("MDeviceId");
                            // device is valid or not
                            if (isvalid.equalsIgnoreCase("true")) {
                                if (MDId.equalsIgnoreCase("0")) {
                                    //************ code comment in 2.4 app ****************************
                                    //     Mobile_no = jresponse.getString("Mobile");
                                    //************ code comment ****************************
                                    if (!MuserId.equalsIgnoreCase("null")) {
                                        // send otp to server
                                        progressDialog.dismiss();
                                        CustomUtility.isSuccessDialog(mContextt, getString(R.string.success), getString(R.string.sucDevice_added_successfully));
                                        Intent mm = new Intent(mContextt, ActivityKLPGridDashbord.class);
                                        mm.putExtra("PlantID", mPlantID);
                                        startActivity(mm);
                                        finish();
                                        //callSendOTPAPI();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContextt, getString(R.string.error), getString(R.string.err_device_already_added));
                                }
                            } else {
                                if (MDId.equalsIgnoreCase("0")) {
                                    CustomUtility.isErrorDialog(mContextt, getString(R.string.error), getString(R.string.err_invalid_device));
                                } else {
                                    if (!MuserId.equalsIgnoreCase("null")) {

                                        Toast.makeText(mContextt, "Device added successfully!!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                   // finish();
                                    }
                                }
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            CustomUtility.isErrorDialog(mContextt, getString(R.string.error), getString(R.string.err_connection));
                        }
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(mContextt, getString(R.string.error), getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }
                } else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(mContextt, "Error", "No Internet Connection");
//                    Message   msg2 = new Message();
//                    msg2.obj = "No Internet Connection";
//                    mHandler.sendMessage(msg2);
                }

            }

        }.start();
    }


}
