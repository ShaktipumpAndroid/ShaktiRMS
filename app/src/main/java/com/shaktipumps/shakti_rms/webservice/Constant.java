package com.shaktipumps.shakti_rms.webservice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Constant {

    private Context mContectt;
    public static  String LANGUAGE_NAME_SAVE = "SelectLanguage";
    public static  String LANGUAGE_NAME_CODE = "SelectLanguageCode";
    public static  int FIRST_TIME_CHECK_LANGUAGE= 11;

    private static final String TAG = "Shakti";

    public static  String SELECTED_LANGUAGE_NEWSCREEN = "en";//////bottom
    public static  String SELECTED_LANGUAGE_NEWSCREEN_NAME = "English";//////bottom
    public static  String USER_LOGIN_STATUS = "false";
    public static  String USER_APP_OPEN_STATUS = "false";

    public static void setLocale(Context mContext, String mSelectedLanguage)
    {
        try {
            //setContentView(R.layout.activation_pending_search_listview);
            Locale locale = new Locale(mSelectedLanguage);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            //  Configuration config = getResources().getConfiguration();
            config.setLayoutDirection(new Locale(mSelectedLanguage));
            config.locale = locale;
            mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());

            Log.e(TAG, "238 : setLocale: " + mSelectedLanguage);
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("fa"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());*/
    }
}
