package com.shaktipumps.shakti_rms.aryabata;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.material.snackbar.Snackbar;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.ActivitySelectionDataWay;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.CustomShowcaseView;


public class Options_Activity extends AppCompatActivity implements View.OnClickListener {
    MaterialFancyButton bluetoothButton;
    //MaterialFancyButton usbButton;
    MaterialFancyButton usbButton,
            logout_id;
    Context c;
    ActionBar actionBar;
    private ShowcaseView showcaseView;
    private int counter = 0;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    private void setAlpha(float alpha, View... views) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }

    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(usbButton), true);
                showcaseView.setContentTitle("USB");
                showcaseView.setContentText("Connect your device to Usb controller for Operations,Make it default operation,later you can change it in Settings");
                break;
            case 1:
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Ready to Rock!");
                showcaseView.setContentText("Go Ahead and get connected");
                showcaseView.setButtonText("Close");
                setAlpha(0.4f, bluetoothButton, usbButton);
                break;
            case 2:
                showcaseView.hide();
                setAlpha(1.0f, bluetoothButton, usbButton);
                break;
        }
        counter++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_tabs_screen);

        c = getApplicationContext();

        TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(getResources().getDimension(R.dimen.title_Font_Size));

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        bluetoothButton = (MaterialFancyButton) findViewById(R.id.btn_bluetooth);

        Resources resources = getResources();

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(bluetoothButton))
                .setOnClickListener(this)
                .singleShot(1)
                .setContentTitlePaint(title)
                .setStyle(R.style.CustomShowcaseTheme_red)
                .setShowcaseDrawer(new CustomShowcaseView(resources.getDimension(R.dimen.custom_showcase_width), resources.getDimension(R.dimen.custom_showcase_height), resources.getColor(R.color.colorAccentLight1), resources.getColor(R.color.colorAccentLight1)))
                .build();

        showcaseView.setButtonText(getString(R.string.next));
        showcaseView.setContentTitle("Bluetooth");
        showcaseView.setContentText("Connection via Bluetooth, Make sure you are connected to device manually first, Make it default operation,later you can change it in Settings");

        SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
        String user_comm_option = sharedPref.getString("user_comm_option", "");

        if (user_comm_option.length() > 0) {
            if (user_comm_option.equals("B")) {
                Intent i = new Intent(c, Bluetooth_DeviceLists_Activity.class);
                startActivity(i);
            }
        }

        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user_comm_option", "B");
                editor.apply();
                Intent i = new Intent(c, Bluetooth_DeviceLists_Activity.class);
                startActivity(i);
            }
        });

        usbButton = (MaterialFancyButton) findViewById(R.id.usb);

        logout_id = (MaterialFancyButton) findViewById(R.id.logout_id);

      /*  usbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user_comm_option", "U");
                editor.apply();
                Snackbar snackbar = Snackbar.make(v, "This Functionality is Disabled", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });*/

        logout_id.setOnClickListener(new View.OnClickListener() {
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

                SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
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

       /* actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);*/
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
                Intent k = new Intent(Options_Activity.this, SettingsActivity.class);
                startActivityForResult(k, 0);
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

       // exit();
        finish();
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