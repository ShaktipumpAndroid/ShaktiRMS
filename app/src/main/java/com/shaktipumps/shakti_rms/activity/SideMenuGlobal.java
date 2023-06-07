package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shaktipumps.shakti_rms.BuildConfig;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.List;

public class SideMenuGlobal extends AppCompatActivity {


    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Context mContext;
    private Activity mActivity;
    private String versionName = "0.0";
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private int clientid = 0 ;
    private String MUserId = "";

    private int OEM_Check = 0;
    private TextView txtAppUserNameID, txtAppVersionID;

    private RelativeLayout rlvMenuOptionRemoveDeviceViewID, rlvMenuOptionLogoutViewID, rlvSlideMenuViewID, rlvMenuOptionHomeViewID, rlvMenuOptionAddDeviceViewID, rlvMenuOptionChangePassViewID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu_global);
        mContext = this;
        mActivity = this;
        versionName = BuildConfig.VERSION_NAME;
        initView();
    }

    private void initView() {

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        try {
            MUserId = pref.getString("key_muserid", "invalid_muserid");
            clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (  clientid == 9999)
        {
            clientid = 0 ;
        }

        if (  clientid == 0)
        {
            // single_user_displayView(0);
            OEM_Check= 0;
           // txtDeleteButtonID.setVisibility(View.VISIBLE);
        }
        else
        {
            //org_user_displayView(0);
            OEM_Check= 1;
            //txtDeleteButtonID.setVisibility(View.GONE);
        }

        rlvMenuOptionRemoveDeviceViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionRemoveDeviceViewID);
        rlvSlideMenuViewID = (RelativeLayout) findViewById(R.id.rlvSlideMenuViewID);
        rlvMenuOptionHomeViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionHomeViewID);
        rlvMenuOptionAddDeviceViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionAddDeviceViewID);
        rlvMenuOptionChangePassViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionChangePassViewID);
       /* rlvMenuOptionFaultReportViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionFaultReportViewID);
        rlvMenuOptionDataReportViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionDataReportViewID);*/
        rlvMenuOptionLogoutViewID = (RelativeLayout) findViewById(R.id.rlvMenuOptionLogoutViewID);

        txtAppUserNameID = (TextView) findViewById(R.id.txtAppUserNameID);
        txtAppVersionID = (TextView) findViewById(R.id.txtAppVersionID);

       // String user_name = "Welcome,  " + pref.getString("key_login_username", " ");
        String user_name = getString(R.string.MenuWelcome)+",  " + pref.getString("key_login_username", " ");
        txtAppUserNameID.setText(user_name);

        String version = "App Version: " + versionName;

        //  txtAppVersionID.setText(version);
       // txtAppVersionID.setText("App Version: "+ NewSolarVFD.versionNameForAll);
        txtAppVersionID.setText(getString(R.string.MenuAppVersion)+": "+ NewSolarVFD.versionNameForAll);

        if(OEM_Check == 0)
        {
            rlvMenuOptionAddDeviceViewID.setVisibility(View.VISIBLE);
        }
        else
        {
            rlvMenuOptionAddDeviceViewID.setVisibility(View.GONE);
        }


        if(Constant.CHECK_COUNT_OF_DELETE == 1)
        {
            rlvMenuOptionRemoveDeviceViewID.setVisibility(View.VISIBLE);
        }
        else
        {
            rlvMenuOptionRemoveDeviceViewID.setVisibility(View.GONE);
        }

        rlvMenuOptionRemoveDeviceViewID.setVisibility(View.VISIBLE);
        rlvSlideMenuViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
                //finish();
            }
        });


        rlvMenuOptionHomeViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                mIntent.putExtra("mPos", mPosition);
                mContext.startActivity(mIntent);
*/
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                //callTotalEnergyAndConjuptionAPI();
                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
                finish();
            }
        });

        rlvMenuOptionAddDeviceViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    Intent intent = new Intent(mContext, AddDevice.class);
                    startActivity(intent);

                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionChangePassViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);

                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionRemoveDeviceViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, LanguageChangeActivity.class);
                startActivity(intent);

                // overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

      /*  rlvMenuOptionFaultReportViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FaultReportActivity.class);
                intent.putExtra("from_date","" );
                intent.putExtra("to_date",  "");
                startActivity(intent);
                //  overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });

        rlvMenuOptionDataReportViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(mContext, DataReportActivity.class);
                startActivity(intent);

                //  overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });*/

        rlvMenuOptionLogoutViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
                //   overridePendingTransition(R.anim.right_to_left, R.anim.lefr_to_right);
                rlvSlideMenuViewID.setVisibility(View.GONE);
            }
        });
    }


    public  void  logout(){

        new AlertDialog.Builder(this)

                .setTitle("Logout Alert !")
                .setMessage("Do you want to Logout this application ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        editor.putString("key_login", "N");
                        editor.putString("key_OTP", "9999");
                        editor.putString("key_mobile_number", "9999999999");

                        editor.putString("key_otp_for_user", "9999");
                        editor.putString("key_mparentid", "9999");
                        editor.putString("key_muserid","9999");
                        // editor.putString("key_clientid","9999");
                        editor.putString("key_clientid","0");
                        editor.putString("key_login_username", "Invalid User");
                        editor.putString("key_clientid_for_map","9999");
                        editor.putString("key_clientid_for_data_report","9999");
                        SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "0");

//                        editor.putString("key_time_zone_city","Invalid" );
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

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                        dialog.cancel();
                        dialog.dismiss();

                    }
                })
                .show();

    }
}
