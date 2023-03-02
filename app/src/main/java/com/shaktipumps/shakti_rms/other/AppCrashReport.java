package com.shaktipumps.shakti_rms.other;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;
import com.shaktipumps.shakti_rms.R;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


/**
 * Created by shakti on 31-May-18.
 */
@ReportsCrashes(mailTo = "support.app@shaktipumps.com, vikas.gothi@shaktipumps.com",
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text) //you get to define resToastText


public class AppCrashReport extends MultiDexApplication {

    private static Context context;

    AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();
      /*  try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        MultiDex.install(this);
        appEnvironment = AppEnvironment.SANDBOX;
    }


    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA

      /*  try {
            ACRA.init(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/

        context = getApplicationContext();
       // mInstance = this;
       // MultiDex.install(this);
      //  Firebase.setAndroidContext(this);
     //   vkAccessTokenTracker.startTracking();
    //    VKSdk.initialize(getApplicationContext() );

    }

}
