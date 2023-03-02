package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.ProductStausBean.ProductStatusResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.GPSTracker;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class AddDevice extends AppCompatActivity implements LocationListener {

    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    TextView tvLatitude, tvLongitude, tvTime;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;

    private Toolbar mToolbar;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, inputPhone, et_Serial_no;
    TextView btn_save;

    double inst_latitude_double,
            inst_longitude_double;

    private TextInputLayout inputLayoutPhone;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String otp_status = "false", MuserId = "null", sms_url = "null", Mobile_no = "null", MDeviceId = "null", MDId = "null", isvalid = "null";
    String otp = "null", device_number = "null";
    private ProgressDialog progressDialog;
    private Activity mActivity;
    private Context mContext;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String mLatitude;
    private String mLongitude;


    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    //private ProductStatusResponse mProductStatusResponse;

    private List<ProductStatusResponse> mProductStatusResponse;

    private int mDeviceTypeScan = 0;
    private BaseRequest baseRequest;

    private DatabaseHelperTeacher databaseHelperTeacher;
    private boolean mCheckFirstDB;
    String mUsernamePrefSTR1;
    private RelativeLayout rlvCheckboxlayoutID;
    private ImageView imgCheckBoxID;
    private TextView txtCheckboxID;
    private TextView txtSubmiteBUTTONID;
    private Boolean aBooleanCheck = true;
    private String aStringCheck = "1";

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mContext = this;
        mActivity = this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_add_device);

        databaseHelperTeacher = new DatabaseHelperTeacher(mContext);///////////

        pref = mContext.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        try {
            locationManager = (LocationManager) mContext.getSystemService(Service.LOCATION_SERVICE);
            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsToRequest = findUnAskedPermissions(permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_save = (TextView) findViewById(R.id.btn_save);
        inputPhone = (EditText) findViewById(R.id.et_phone_no);
        et_Serial_no = (EditText) findViewById(R.id.et_Serial_no);

        rlvCheckboxlayoutID = (RelativeLayout) findViewById(R.id.rlvCheckboxlayoutID);
        imgCheckBoxID = (ImageView) findViewById(R.id.imgCheckBoxID);
        txtCheckboxID = (TextView) findViewById(R.id.txtCheckboxID);
        txtSubmiteBUTTONID = (TextView) findViewById(R.id.txtSubmiteBUTTONID);

        baseRequest = new BaseRequest(mContext);
        mProductStatusResponse = new ArrayList<>();
        mUsernamePrefSTR1 = pref.getString("key_mobile_number", "login_status");

        if (mUsernamePrefSTR1 != null && !mUsernamePrefSTR1.equalsIgnoreCase("null") && !mUsernamePrefSTR1.equalsIgnoreCase("")) {
            inputPhone.setText(mUsernamePrefSTR1);
        } else {
            inputPhone.setText("");
        }
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone_no);



        //startLocationUpdates();

        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }

            // get location
           // getLocation();
            getGpsLocation();
        }



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm();

              /*  if (mLongitude != null && !mLongitude.equalsIgnoreCase("null") && !mLongitude.equalsIgnoreCase("")) {
                    submitForm();
                } else {
                    startLocationUpdates();
                }*/

                //submitForm();

            }
        });

        rlvCheckboxlayoutID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aBooleanCheck) {
                    aStringCheck = "0";
                    aBooleanCheck = false;
                    imgCheckBoxID.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_gray));
                    txtCheckboxID.setTextColor(getResources().getColor(R.color.gray));
                } else {
                    aStringCheck = "1";
                    aBooleanCheck = true;
                    imgCheckBoxID.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_blue));
                    txtCheckboxID.setTextColor(getResources().getColor(R.color.blue_fb));
                }
                //  aBooleanCheck

            }
        });


        txtSubmiteBUTTONID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // et_Serial_no

                device_number = et_Serial_no.getText().toString().trim();
                Mobile_no = inputPhone.getText().toString().trim();

                if(Mobile_no.equalsIgnoreCase("") || Mobile_no.equalsIgnoreCase("null") )
                {
                    Toast.makeText(mActivity, "Please enter mobile number..", Toast.LENGTH_SHORT).show();
                }
                else if(device_number.equalsIgnoreCase("") || device_number.equalsIgnoreCase("null") )
                {
                    Toast.makeText(mActivity, "Please enter device number..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent1 = new Intent(AddDevice.this, Add_latitudeAndLongitude.class);
                    intent1.putExtra("Mobile", Mobile_no);
                    intent1.putExtra("MUserId", MuserId);
                    intent1.putExtra("MDeviceId", MDeviceId);
                    intent1.putExtra("mSerialNumber", device_number);
                    intent1.putExtra("Latitude", mLatitude);
                    intent1.putExtra("Longitude", mLongitude);
                    intent1.putExtra("StringCheckLoc", aStringCheck);
                    startActivity(intent1);
                }

            }
        });

        // checkLocation();//
    }

    public static String createString(String strCharacter){

        StringBuilder sbString =
                new StringBuilder(8);

        for(int i=0; i < 8; i++){
            sbString.append(strCharacter.charAt(i));
        }

        return sbString.toString();
    }
    public void getGpsLocation() {
        GPSTracker gps = new GPSTracker(mContext);

        if (gps.canGetLocation()) {
            inst_latitude_double = gps.getLatitude();
            inst_longitude_double = gps.getLongitude();
            if (inst_latitude_double == 0.0) {
                CustomUtility.ShowToast("Lat Long not captured, Please try again.", mContext);
            } else {
                mLatitude =createString(String.valueOf(inst_latitude_double));
                mLongitude =createString(String.valueOf(inst_longitude_double));
                //CustomUtility.ShowToast("Latitude:-" + mLatitude + "     " + "Longitude:-" + mLongitude, mContext);

            }
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void submitForm() {

       /* if (!validatePhone()) {
            return;
        }*/
        if (!validateOnline()) {
            return;
        }
        scan_qr_code();

    }

    private boolean validateOnline() {
        if (CustomUtility.isOnline(AddDevice.this)) {
            return true;
        }
        CustomUtility.isErrorDialog(AddDevice.this, mContext.getString(R.string.error), mContext.getString(R.string.err_internet));
        return false;
    }

    private boolean validateDeviceNumber() {

        if (
                editText1.getText().toString().trim().isEmpty() ||
                        editText2.getText().toString().trim().isEmpty() ||
                        editText3.getText().toString().trim().isEmpty() ||
                        editText4.getText().toString().trim().isEmpty() ||
                        editText5.getText().toString().trim().isEmpty() ||
                        editText6.getText().toString().trim().isEmpty()
        ) {
            CustomUtility.isErrorDialog(AddDevice.this, mContext.getString(R.string.error), mContext.getString(R.string.err_device_number));
            return false;
        }

        return true;
    }



    private boolean validatePhone() {
        if (inputPhone.getText().toString().isEmpty()) {
            inputLayoutPhone.setError(mContext.getString(R.string.err_reg_mobile_no12));
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }


        int len = inputPhone.getText().toString().trim().length();
        if (len < 10) {
            inputLayoutPhone.setError(mContext.getString(R.string.err_msg_valid_phone_no1));
            requestFocus(inputPhone);
            return false;
        }else if(!NewSolarVFD.emailValidator(inputPhone.getText().toString().trim()))
        {
            inputLayoutPhone.setError(mContext.getString(R.string.err_msg_valid_phone_no1));
            requestFocus(inputPhone);
            return false;
        }


        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void scan_qr_code() {
      //  if (checkAndRequestPermissions())
        {
            try {
                IntentIntegrator integrator = new IntentIntegrator(mActivity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan QR Code");
                integrator.setResultDisplayDuration(1);
                integrator.setScanningRectangle(400, 400);

                // integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.initiateScan();
                //  integrator.addExtra("data", "hello");
                integrator.setLegacyCaptureLayout(1);

            } catch (ActivityNotFoundException anfe) {
                Log.e("onCreate", "Scanner Not Found", anfe);
            }

            //////////new code

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


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//            Toast.makeText(mContext,scanResult.toString(),Toast.LENGTH_LONG).show();
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

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    Mobile_no = inputPhone.getText().toString();

                    Intent intent1 = new Intent(AddDevice.this, Add_latitudeAndLongitude.class);
                    intent1.putExtra("Mobile", Mobile_no);
                    intent1.putExtra("MUserId", MuserId);
                    intent1.putExtra("MDeviceId", MDeviceId);
                    intent1.putExtra("mSerialNumber", device_number);
                    intent1.putExtra("Latitude", mLatitude);
                    intent1.putExtra("Longitude", mLongitude);
                    intent1.putExtra("StringCheckLoc", aStringCheck);

                    //  Log.d("intent1",MuserId +"--"+  MDeviceId +"--"+ Mobile_no);
                    startActivity(intent1);
                    // serverLogin();//vikasssss
                }
            } else {
                Log.d("result", "no scan ");
                Toast toast = Toast.makeText(mContext, "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();

            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(mContext, "No scan data received , Please Try again !", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    ////////////////////85858585


    //This method will be called when the user will tap on allow or deny



    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        //getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        try {
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private boolean checkAndRequestPermissions() {
        int IMEI_NUM = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        int LOCATION = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int CAMERAV = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (IMEI_NUM != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (CAMERAV != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

//If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                canGetLocation = true;
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                canGetLocation = false;
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                canGetLocation = true;
                Toast.makeText(this, "Permission granted now you can use camera", Toast.LENGTH_LONG).show();
            } else {
                canGetLocation = false;
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                canGetLocation = true;
                Toast.makeText(this, "Permission granted now you can use user location", Toast.LENGTH_LONG).show();
            } else {
                canGetLocation = false;
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }


        }
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
       // tvLatitude.setText(Double.toString(loc.getLatitude()));
        mLatitude=Double.toString(loc.getLatitude());
       // tvLongitude.setText(Double.toString(loc.getLongitude()));
        mLongitude = Double.toString(loc.getLongitude());

       // Toast.makeText(this, "mLatitude==>>"+mLatitude+"mLongitude==>>"+mLongitude, Toast.LENGTH_SHORT).show();

        //tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
