package com.shaktipumps.shakti_rms.activity;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.multidex.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.plattysoft.leonids.ParticleSystem;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.CheckUpdateModel.CheckAppUpdateModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.Constant;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends Activity implements AnimationListener {

    Vibrator vibe;

    private static final int REQUEST_CODE_PERMISSION = 2;
    Intent i ;
    private static int SPLASH_TIME_OUT = 1000;

    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    String current_date = "null" ,current_time;
    Context mContex ;
    // Animation
    Animation anim;
    private BaseRequest baseRequest;
    private String mCurrentMobileVer;
    private String mOldMobileVer;
    //private GifImageView gifImageView;
    Animation animZoomin;

    ImageView imgLogoID;


    @Override
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContex = this;
       // commonDocumentDirPath("VikasFile");
       // FirebaseApp.initializeApp(this);
        /*try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        baseRequest = new BaseRequest(this);
        imgLogoID = (ImageView) findViewById(R.id.imgLogoID);
        System.out.println("mobvr=="+BuildConfig.VERSION_NAME);
/*        gifImageView = (GifImageView) findViewById(R.id.gifImage);
        gifImageView.setGifImageResource(R.drawable.filetranfer);*/
        mOldMobileVer = BuildConfig.VERSION_NAME;




        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        animZoomin = AnimationUtils.loadAnimation(mContex,
                R.anim.anim_up );

        animZoomin.setAnimationListener(this);

        imgLogoID.startAnimation(animZoomin);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        if(checkPermission()){
            openNextActivity();
        }
        else {
            requestPermission();
        }
//vikas end


/*Intent i = new Intent(SplashActivity.this, AddPlantAndDeviceOPtion.class);
startActivity(i);*/
        }

    private void openNextActivity()     {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    SharedPreferencesUtil.setData(mContex, Constant.USER_APP_OPEN_STATUS, "true");
                    String mmCheckLogin = SharedPreferencesUtil.getData(mContex, Constant.USER_LOGIN_STATUS);
                    String mmCheckLogin_Welcom = SharedPreferencesUtil.getData(mContex, Constant.USER_APP_OPEN_STATUS);
                    if (SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE) == null || SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase(""))
                    {
                        SharedPreferencesUtil.setData(mContex, Constant.LANGUAGE_NAME_CODE, "en");
                        SharedPreferencesUtil.setData(mContex, Constant.LANGUAGE_NAME_SAVE, "English");

                        Constant.setLocale(mContex, "en");
                    }
                    else
                    {
                        String hhhhh = SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE);
                        Constant.setLocale(mContex, hhhhh);
                    }

                    if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y"))
                    {
                        //  i = new Intent(SplashActivity.this, BaseActivity.class);
                        i = new Intent(SplashActivity.this, MainActivity.class);
                        // i = new Intent(SplashActivity.this, WalthroughActivity.class);
                    }
                    else
                    {
                        //SplashActivity.this.finish();
                        i = new Intent(SplashActivity.this, LoginActivity.class);
                        //i = new Intent(SplashActivity.this, WalthroughActivity.class);
                    }

                    startActivity(i);
                    finish();
                } catch (Exception e) {

                    i = new Intent(SplashActivity.this, LoginActivity.class);
                    // i = new Intent(SplashActivity.this, WalthroughActivity.class);
                    startActivity(i);
                    finish();
                    e.printStackTrace();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public static File commonDocumentDirPath(String FolderName)
    {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + FolderName);
        }
        else
        {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + FolderName);
        }

        // Make sure the path directory exists.
        if (!dir.exists())
        {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success)
            {
                dir = null;
            }
        }
        return dir;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
    }


    private void getCheckAppUpdateModelViewResponse(CheckAppUpdateModelView mCheckAppUpdateModelView) {

        if (mCheckAppUpdateModelView.getStatus()) {
            float mCurrentMobileVer = mCheckAppUpdateModelView.getResponse().getMobVersion();
            //if(Float.parseFloat(mCurrentMobileVer) > Float.parseFloat(mOldMobileVer))
            if(mCurrentMobileVer > Float.parseFloat(mOldMobileVer))
            {
                forceFullyUpdateAppFromStore();
               /* final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }*/
            }
            else {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {

                            SharedPreferencesUtil.setData(mContex, Constant.USER_APP_OPEN_STATUS, "true");
                            String mmCheckLogin = SharedPreferencesUtil.getData(mContex, Constant.USER_LOGIN_STATUS);
                            String mmCheckLogin_Welcom = SharedPreferencesUtil.getData(mContex, Constant.USER_APP_OPEN_STATUS);
                            if (SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE) == null || SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase(""))
                            {
                                SharedPreferencesUtil.setData(mContex, Constant.LANGUAGE_NAME_CODE, "en");
                                SharedPreferencesUtil.setData(mContex, Constant.LANGUAGE_NAME_SAVE, "English");

                                Constant.setLocale(mContex, "en");
                            }
                            else
                            {
                                Constant.setLocale(mContex, SharedPreferencesUtil.getData(mContex, Constant.LANGUAGE_NAME_CODE));
                            }

                            if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y"))
                            {
                              //  i = new Intent(SplashActivity.this, BaseActivity.class);//correct
                                i = new Intent(SplashActivity.this, MainActivity.class);//correct
                            }
                            else
                            {
                                //SplashActivity.this.finish();
                               i = new Intent(SplashActivity.this, LoginActivity.class);///currect
                             //  i = new Intent(SplashActivity.this, WalthroughActivity.class);///currect

                            }
                            startActivity(i);
                            SplashActivity.this.finish();
                        } catch (Exception e) {
                              i = new Intent(SplashActivity.this, LoginActivity.class);///currect
                             // i = new Intent(SplashActivity.this, WalthroughActivity.class);///currect

                            startActivity(i);
                            SplashActivity.this.finish();
                            e.printStackTrace();
                        }
                    }
                }, SPLASH_TIME_OUT);
            }
        }
    }


    public void forceFullyUpdateAppFromStore() {
        ////////////////////////////
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(mContex, R.style.alertDialogForceUpdate);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.force_fully_update_heading_text));
        builder.setMessage(getString(R.string.force_fully_update_text));

        String positiveText = getString(R.string.Update_text);
        builder.setPositiveButton(positiveText,
                (dialog, which) -> {


                  //  deleteCache(mContex);
                    // dismiss alert dialog, update preferences with game score and restart play fragment
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        File myDb = mContex.getDatabasePath(DatabaseHelperTeacher.DATABASE_NAME_RMS);
                        Boolean isDelete = myDb.delete();
                        if(isDelete)
                        {
                            Toast.makeText(mContex, "Database deleted.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        File myDb = mContex.getDatabasePath(DatabaseHelperTeacher.DATABASE_NAME_RMS);
                        Boolean isDelete = myDb.delete();
                        if(isDelete)
                        {
                            Toast.makeText(mContex, "Database deleted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                   // finish();
                   // Log.d("myTag", "positive button clicked");
                  // dialog.dismiss();
                });

        String negativeText = getString(R.string.No_thanks_text);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss dialog, start counter again
                        finish();
                        dialog.dismiss();
                        Log.d("myTag", "negative button clicked");
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y"))
                                    {
                                      //  i = new Intent(SplashActivity.this, BaseActivity.class);
                                           i = new Intent(SplashActivity.this, MainActivity.class);
                                    }
                                    else
                                    {
                                        //SplashActivity.this.finish();
                                        i = new Intent(SplashActivity.this, LoginActivity.class);

                                        // i = new Intent(SplashActivity.this, WalthroughActivity.class);
                                    }

                                    startActivity(i);
                                    SplashActivity.this.finish();
                                } catch (Exception e) {


                                    i = new Intent(SplashActivity.this, LoginActivity.class);
                                    //  i = new Intent(SplashActivity.this, WalthroughActivity.class);

                                    startActivity(i);
                                    SplashActivity.this.finish();
                                    e.printStackTrace();
                                }

                            }
                        }, SPLASH_TIME_OUT);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private boolean checkPermission() {
        int FineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int CoarseLocation = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int Bluetooth = ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH);
        int PhoneState = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int Camera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int ReadExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int WriteExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ReadMediaImages = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_IMAGES);
        int ReadMediaAudio = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_AUDIO);
        int BluetoothConnect =  ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH_CONNECT);
        int BluetoothScan =  ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH_SCAN);


        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return CoarseLocation == PackageManager.PERMISSION_GRANTED && Bluetooth == PackageManager.PERMISSION_GRANTED
                    && Camera == PackageManager.PERMISSION_GRANTED  && ReadMediaImages == PackageManager.PERMISSION_GRANTED
                    && ReadMediaAudio == PackageManager.PERMISSION_GRANTED && BluetoothConnect == PackageManager.PERMISSION_GRANTED
                    && BluetoothScan == PackageManager.PERMISSION_GRANTED;
        }else  if  ( SDK_INT == Build.VERSION_CODES.S)  {
            return  Camera == PackageManager.PERMISSION_GRANTED
                    && CoarseLocation == PackageManager.PERMISSION_GRANTED  && PhoneState == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED   && BluetoothScan == PackageManager.PERMISSION_GRANTED
                    && BluetoothConnect == PackageManager.PERMISSION_GRANTED;
        } else  if  (SDK_INT == Build.VERSION_CODES.R)  {
            return  Camera == PackageManager.PERMISSION_GRANTED
                    && CoarseLocation == PackageManager.PERMISSION_GRANTED  && PhoneState == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED;
        }else {
            return FineLocation == PackageManager.PERMISSION_GRANTED && CoarseLocation == PackageManager.PERMISSION_GRANTED
                    && Bluetooth == PackageManager.PERMISSION_GRANTED && PhoneState == PackageManager.PERMISSION_GRANTED
                    && Camera == PackageManager.PERMISSION_GRANTED && ReadExternalStorage == PackageManager.PERMISSION_GRANTED
                    && WriteExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,  Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.BLUETOOTH ,
                            Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION);
        } else if  ( SDK_INT == Build.VERSION_CODES.S )  {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,  Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_CONNECT , Manifest.permission.BLUETOOTH_SCAN },
                    REQUEST_CODE_PERMISSION);
        }else if  ( SDK_INT == Build.VERSION_CODES.R)  {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,},
                    REQUEST_CODE_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0) {

                    if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                        boolean CoarseLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean  Camera = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadMediaImages = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadMediaAudio = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothConnect = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothScan =grantResults[6] == PackageManager.PERMISSION_GRANTED;

                        if (BluetoothConnect && BluetoothScan && CoarseLocationAccepted && Bluetooth  && Camera && ReadMediaImages && ReadMediaAudio) {
                            // perform action when allow permission success
                            openNextActivity();
                        }else {
                            requestPermission();
                        }
                    } else  if ( SDK_INT == Build.VERSION_CODES.S ) {
                        boolean Camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean AccessCoarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothConnect = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        boolean BluetoothScan = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadPhoneState = grantResults[5] == PackageManager.PERMISSION_GRANTED;

                        if (Camera && ReadPhoneState && AccessCoarseLocation && Bluetooth &&  BluetoothConnect && BluetoothScan) {
                            openNextActivity();
                        } else {
                            requestPermission();
                        }
                    }else  if (  SDK_INT == Build.VERSION_CODES.R) {
                        boolean Camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean AccessCoarseLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean  ReadPhoneState = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                        if (Camera && ReadPhoneState && AccessCoarseLocation && Bluetooth) {
                            openNextActivity();
                        } else {
                            requestPermission();
                        }
                    }else  if ( SDK_INT <= Build.VERSION_CODES.Q) {
                        boolean FineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean CoarseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean Bluetooth = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean ReadPhoneState = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                        boolean Camera = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                        boolean ReadPhoneStorage = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                        boolean WritePhoneStorage = grantResults[6] == PackageManager.PERMISSION_GRANTED;


                        if(FineLocationAccepted && CoarseLocationAccepted && Bluetooth && ReadPhoneState && Camera && ReadPhoneStorage && WritePhoneStorage ){
                            openNextActivity();
                        }else {
                            requestPermission();
                        }
                    }
                }
                break;
        }
    }
}