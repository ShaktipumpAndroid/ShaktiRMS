package com.shaktipumps.shakti_rms.aryabata;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.material.snackbar.Snackbar;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.ActivitySelectionDataWay;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth_response;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;

import static com.shaktipumps.shakti_rms.aryabata.Bluetooth_DeviceLists_Activity.EXTRA_ADDRESS;
import static com.shaktipumps.shakti_rms.aryabata.Bluetooth_DeviceLists_Activity.EXTRA_NAME;


public class Dashboard_Activity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    public String address = "";
    public String name = "";
    ImageView op, pr, data1, logs;
   // LinearLayout lay1, lay2, lay3, lay4;
    RelativeLayout lay1, lay2, lay3, lay4;
    TextView txtop, txtop1, txtop2, txtop3;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ShowcaseView showcaseView;
    private int counter = 0;
    private Context CURRENT_CONTEXT;

    private ImageView imgBackBTNID;
    private ImageView imgLogoutBTNID;
  //  private ImageView imgSettingBTNID;
    private TextView txtTopHEadingID;

    private ViewFlipper flvViewFlipperID;


    private void setAlpha(float alpha, View... views) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }

    @Override
    public void onClick(View v) {

        TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(getResources().getDimension(R.dimen.title_Font_Size));

        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(pr), true);
                showcaseView.setContentTitle("Parameters");
                showcaseView.setContentText("Setup Parameters,Perform GET and SET Functions in this Screen.");
                setAlpha(0.4f, pr, data1, logs);
                break;

            case 1:
                showcaseView.setShowcase(new ViewTarget(data1), true);
                showcaseView.setContentTitle("AUTHModelData");
                showcaseView.setContentText("Real Time AUTHModelData Monitoring screen");
                setAlpha(0.4f, data1, logs);
                break;

            case 2:
                showcaseView.setShowcase(new ViewTarget(logs), true);
                showcaseView.setContentTitle("Get Logs");
                showcaseView.setContentText("Get Logs from Device,can fetch logs from 0-40000 by giving input of log no.");
                setAlpha(0.4f, logs);
                break;


            case 3:
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Ready to Rock!");
                showcaseView.setContentText("Go Ahead.!");
                showcaseView.setButtonText("Close");
                break;
            case 4:
                showcaseView.hide();
                break;
        }
        counter++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        pr = (ImageView) findViewById(R.id.pr);
        data1 = (ImageView) findViewById(R.id.data1);
        logs = (ImageView) findViewById(R.id.logs);

        address = getIntent().getStringExtra(EXTRA_ADDRESS); //receive the address of the bluetooth device
        name = getIntent().getStringExtra(EXTRA_NAME); //receive the name of the bluetooth device
        CURRENT_CONTEXT = getApplicationContext();


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(findViewById(R.id.op)))
                .setOnClickListener(this)
                .setStyle(R.style.CustomShowcaseTheme2)
                .singleShot(5)
                .withHoloShowcase()
                .build();
        showcaseView.setButtonText(getString(R.string.next));
        showcaseView.setContentTitle("Operations");
        showcaseView.setContentText("Perform Operations on Device like setting and getting ID,Time,Date,Start etc..");

        SharedPreferences sharedPref = CURRENT_CONTEXT.getSharedPreferences("Pref", 0);
        String user_auth_string = sharedPref.getString("user_auth", "");
       // if (user_auth_string.length() > 0)
        {
            final User_auth user_auth = User_auth_response.parseJSONForSingle(user_auth_string);
           // lay1 = (LinearLayout) findViewById(R.id.lay1);
            lay1 = (RelativeLayout) findViewById(R.id.lay1);
            txtop = (TextView) findViewById(R.id.txtop);
            txtop1 = (TextView) findViewById(R.id.txtop1);
            txtop2 = (TextView) findViewById(R.id.txtop2);
            txtop3 = (TextView) findViewById(R.id.txtop3);

            /*if (!user_auth.isPerformActions()) {
                lay1.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtop.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }*/
            TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            title.setTextSize(getResources().getDimension(R.dimen.title_Font_Size));

            lay1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /*if (user_auth.isPerformActions()) {
                        Intent intent = new Intent(Dashboard_Activity.this, Operations_GetSet_Activity.class);
                        intent.putExtra(EXTRA_ADDRESS, address);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Don't have Permission to perform this action!", Toast.LENGTH_LONG).show();
                    }*/
                    Intent intent = new Intent(Dashboard_Activity.this, Operations_GetSet_Activity.class);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    startActivity(intent);
                }
            });
            //lay2 = (LinearLayout) findViewById(R.id.lay2);
            lay2 = (RelativeLayout) findViewById(R.id.lay2);
           /* if (!user_auth.isSetParameters()) {
                lay2.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtop1.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }*/
            lay2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(Dashboard_Activity.this, Parameters_GetSet_Activity.class);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    startActivity(intent);
                }
            });

           // lay3 = (LinearLayout) findViewById(R.id.lay3);
            lay3 = (RelativeLayout) findViewById(R.id.lay3);
           /* if (!user_auth.isViewParameters()) {
                lay3.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtop2.setBackgroundColor(getResources().getColor(R.color.colorGray));

            }*/
            lay3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  /*  if (user_auth.isViewParameters()) {
                        Intent intent = new Intent(Dashboard_Activity.this, DataParameters_Activity.class);
                        intent.putExtra(EXTRA_ADDRESS, address);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Don't have Permission to perform this action!", Toast.LENGTH_LONG).show();
                    }*/

                    Intent intent = new Intent(Dashboard_Activity.this, DataParameters_Activity.class);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    startActivity(intent);
                }
            });

           // lay4 = (LinearLayout) findViewById(R.id.lay4);
            lay4 = (RelativeLayout) findViewById(R.id.lay4);
           /* if (!user_auth.isViewLogs()) {
                lay4.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtop3.setBackgroundColor(getResources().getColor(R.color.colorGray));

            }*/
            lay4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   /* if (user_auth.isViewLogs()) {
                        Intent intent = new Intent(Dashboard_Activity.this, Get_logs_Activity.class);
                        intent.putExtra(EXTRA_ADDRESS, address);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Don't have Permission to perform this action!", Toast.LENGTH_LONG).show();
                    }*/

                    Intent intent = new Intent(Dashboard_Activity.this, Get_logs_Activity.class);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    startActivity(intent);
                }
            });

        }
     //   android.support.v7.app.ActionBar actionBar = getSupportActionBar();
      //  assert actionBar != null;
    //    actionBar.setDisplayHomeAsUpEnabled(true);


        imgBackBTNID = (ImageView) findViewById(R.id.imgBackBTNID);
        imgLogoutBTNID = (ImageView) findViewById(R.id.imgLogoutBTNID);
     //   imgSettingBTNID = (ImageView) findViewById(R.id.imgSettingBTNID);
        txtTopHEadingID = (TextView) findViewById(R.id.txtTopHEadingID);

        imgBackBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*imgSettingBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Dashboard_Activity.this, SettingsActivity.class);
                startActivityForResult(j, 0);
            }
        });*/

        imgLogoutBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Pref", 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user_comm_option", "");
                editor.apply();
                SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "0");
                Snackbar snackbar = Snackbar.make(v, "Logout Successfully!", Snackbar.LENGTH_LONG);
                snackbar.show();
                Intent intent = new Intent(getApplicationContext(), ActivitySelectionDataWay.class);
                startActivity(intent);
                finish();
            }
        });

        flvViewFlipperID = (ViewFlipper)findViewById(R.id.flvViewFlipperID);

        flvViewFlipperID.setFlipInterval(3000); //set 1 seconds for interval time
        flvViewFlipperID.startFlipping();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navtabsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent j = new Intent(Dashboard_Activity.this, SettingsActivity.class);
                startActivityForResult(j, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
       // exit();
    }

    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
