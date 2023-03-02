package com.shaktipumps.shakti_rms.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    int height, width;
    int speed = 300000, duration = 5;
    Vibrator vibe;
    int myNum;

    Intent i ;
    // Splash screen timer
    //private static int SPLASH_TIME_OUT = 10000;
    private static int SPLASH_TIME_OUT = 1000;
    ImageView imageView;
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


       // deleteCache(mContex);

        //clearApplicationData();
    //    deleteAppData();
                /******* Create SharedPreferences *******/

                /*imgLogoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                particle(v);
            }
        });*/
///////////vikas start
      /*  if (CustomUtility.isOnline(mContex)) {


           callSettingParameterValueAPI();/////////original code

        }
        else*/
        {
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
//vikas end


/*Intent i = new Intent(SplashActivity.this, AddPlantAndDeviceOPtion.class);
startActivity(i);*/
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

    private void callSettingParameterValueAPIVikaas() {

        if (CustomUtility.isOnline(mContex)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        /*CheckAppUpdateModelView mCheckAppUpdateModelView = gson.fromJson(Json, CheckAppUpdateModelView.class);
                        getCheckAppUpdateModelViewResponse(mCheckAppUpdateModelView);*/

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

            Map<String, String> wordsByKey = new HashMap<>();;

           // wordsByKey.put("PlanId", "0");
            wordsByKey.put("fromDate", "2020-01-02");
            wordsByKey.put("toDate", "2020-01-02");

            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            // baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_DEVICE_SETTING);/////
            baseRequest.callAPIGET(1, wordsByKey, "Hourly");/////

        }
        else
        {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y"))
                        {
                            //i = new Intent(SplashActivity.this, BaseActivity.class);
                            i = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        else
                        {
                            //SplashActivity.this.finish();
                            i = new Intent(SplashActivity.this, LoginActivity.class);
                          //  i = new Intent(SplashActivity.this, WalthroughActivity.class);
                        }

                        startActivity(i);
                        SplashActivity.this.finish();
                    } catch (Exception e) {
                        i = new Intent(SplashActivity.this, LoginActivity.class);
                       // i = new Intent(SplashActivity.this, WalthroughActivity.class);
                        startActivity(i);
                        SplashActivity.this.finish();
                        e.printStackTrace();
                    }

                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void callSettingParameterValueAPI() {

        if (CustomUtility.isOnline(mContex)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        CheckAppUpdateModelView mCheckAppUpdateModelView = gson.fromJson(Json, CheckAppUpdateModelView.class);
                        getCheckAppUpdateModelViewResponse(mCheckAppUpdateModelView);

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

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("DeviceTypeID", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_MOBILE_VERSION);/////

        }
        else
        {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y"))
                        {
                            //i = new Intent(SplashActivity.this, BaseActivity.class);
                            i = new Intent(SplashActivity.this, MainActivity.class);
                           // i = new Intent(SplashActivity.this, WalthroughActivity.class);
                        }
                        else
                        {
                            //SplashActivity.this.finish();
                            i = new Intent(SplashActivity.this, LoginActivity.class);
                          //  i = new Intent(SplashActivity.this, WalthroughActivity.class);
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
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


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
                          /*  new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (pref.getString("key_login", "login_status").equalsIgnoreCase("Y"))
                                        {
                                            i = new Intent(SplashActivity.this, BaseActivity.class);
                                            //   i = new Intent(SplashActivity.this, WalthroughActivity.class);
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
                            }, SPLASH_TIME_OUT);*/

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
                    }
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
// display dialog
        dialog.show();

    }


   /* public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }*/

    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        } }

   /* public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }*/

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }


    private void particle(View view) {

        new ParticleSystem(this, 50, R.drawable.snowflake, 1000)
                .setSpeedRange(0.1f, 0.25f)
                .emit(view, 100);



    }

}