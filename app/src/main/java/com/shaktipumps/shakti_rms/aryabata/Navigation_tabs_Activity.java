package com.shaktipumps.shakti_rms.aryabata;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth_response;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.CustomShowcaseView;

import static com.shaktipumps.shakti_rms.aryabata.Bluetooth_DeviceLists_Activity.EXTRA_ADDRESS;
import static com.shaktipumps.shakti_rms.aryabata.Bluetooth_DeviceLists_Activity.EXTRA_NAME;


public class Navigation_tabs_Activity extends AppCompatActivity implements View.OnClickListener {
    public String address = "";
    public String name = "";
    MaterialFancyButton materialFancyButton;
    MaterialFancyButton materialFancyButton1;
    MaterialFancyButton materialFancyButton2;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ShowcaseView showcaseView;
    private int counter = 0;
    private Context CURRENT_CONTEXT;

    private void setAlpha(float alpha, View... views) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }

    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(materialFancyButton1), true);
                showcaseView.setContentTitle("Parameters");
                showcaseView.setContentText("Setup Parameters,Perform GET and SET Functions in this Screen.");
                break;

            case 1:
                showcaseView.setShowcase(new ViewTarget(materialFancyButton2), true);
                showcaseView.setContentTitle("AUTHModelData");
                showcaseView.setContentText("Real Time AUTHModelData Monitoring screen");
                break;


            case 2:
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Ready to Rock!");
                showcaseView.setContentText("Go Ahead.!");
                showcaseView.setButtonText("Close");
                setAlpha(0.4f, materialFancyButton, materialFancyButton1, materialFancyButton2);
                break;
            case 3:
                showcaseView.hide();
                setAlpha(1.0f, materialFancyButton, materialFancyButton1, materialFancyButton2);
                break;

        }
        counter++;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tabs_screen);

        address = getIntent().getStringExtra(EXTRA_ADDRESS); //receive the address of the bluetooth device
        name = getIntent().getStringExtra(EXTRA_NAME); //receive the address of the bluetooth device
        CURRENT_CONTEXT = getApplicationContext();

        SharedPreferences sharedPref = CURRENT_CONTEXT.getSharedPreferences("Pref", 0);
        String user_auth_string = sharedPref.getString("user_auth", "");
        if (user_auth_string.length() > 0) {
            final User_auth user_auth = User_auth_response.parseJSONForSingle(user_auth_string);
            materialFancyButton = (MaterialFancyButton) findViewById(R.id.tab1);
            if (!user_auth.isPerformActions()) {
                materialFancyButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }
            TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            title.setTextSize(getResources().getDimension(R.dimen.title_Font_Size));
            Resources resources = getResources();

            showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(materialFancyButton))
                    .setOnClickListener(this)
                    .singleShot(2)
                    .setContentTitlePaint(title)
                    .setStyle(R.style.CustomShowcaseTheme_red)
                    .setShowcaseDrawer(new CustomShowcaseView(resources.getDimension(R.dimen.custom_showcase_width), resources.getDimension(R.dimen.custom_showcase_height), resources.getColor(R.color.colorAccentLight1), resources.getColor(R.color.colorAccentLight1)))
                    .build();

            showcaseView.setButtonText(getString(R.string.next));
            showcaseView.setContentTitle("Operations");
            showcaseView.setContentText("Perform Operations on Device like setting and getting ID,Time,Date,Start etc..");


            materialFancyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (user_auth.isPerformActions()) {
                        Intent intent = new Intent(Navigation_tabs_Activity.this, Operations_GetSet_Activity.class);
                        intent.putExtra(EXTRA_ADDRESS, address);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Don't have Permission to perform this action!", Toast.LENGTH_LONG).show();
                    }
                }
            });


            materialFancyButton1 = (MaterialFancyButton) findViewById(R.id.tab2);
            if (!user_auth.isSetParameters()) {
                materialFancyButton1.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }
            materialFancyButton1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (user_auth.isSetParameters()) {
                        Intent intent = new Intent(Navigation_tabs_Activity.this, Parameters_GetSet_Activity.class);
                        intent.putExtra(EXTRA_ADDRESS, address);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Don't have Permission to perform this action!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            materialFancyButton2 = (MaterialFancyButton) findViewById(R.id.tab3);
            if (!user_auth.isViewParameters()) {
                materialFancyButton2.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }
            materialFancyButton2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (user_auth.isViewParameters()) {
                        Intent intent = new Intent(Navigation_tabs_Activity.this, DataParameters_Activity.class);
                        intent.putExtra(EXTRA_ADDRESS, address);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Don't have Permission to perform this action!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        /*android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);*/
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
                Intent j = new Intent(Navigation_tabs_Activity.this, SettingsActivity.class);
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
        exit();
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
