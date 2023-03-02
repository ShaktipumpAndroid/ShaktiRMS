package com.shaktipumps.shakti_rms.aryabata;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.aryabata.classes.Data_Parameters;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Thread.sleep;

public class DataParameters_Activity extends AppCompatActivity {

    static boolean inRefresh = false;
    public String name = null;
    BluetoothSocket btSocket = null;
    String address = null;
    TextView txttemperature;
    TextView txtflow;
    TextView txtactiveenergy;
    TextView txtreactiveeneergy;
    TextView txtrmsvoltageA, txtrmsvoltageB, txtrmsvoltageC;
    TextView txtfreqency;
    TextView txtrmscurrentA, txtrmscurrentB, txtrmscurrentC;
    TextView txtactivepower;
    TextView txtreactivepower;
    TextView txtapparentpower;
    TextView txtpowerfactor;
    ImageView f0, f1, f2, f3, f4, f5, f6, f7, f8, s0, s1, s2, f9, f10, f11, f12, f13, f14, f15,f2img,f1img;
    TextView fd0, fd1, fd2, fd3, fd4, fd5, fd6, fd7, fd8, fd9, fd10, fd11, fd12, fd13, fd14, fd15, sd0, sd1, sd2,f2desc,f1desc;

    TextView refreshTime;
    Handler getDataRequestHandler = new Handler();
    private Context CURRENT_CONTEXT;


    private ImageView imgBackBTNID;
    private ImageView imgRefreshBTNID;
    private ImageView imgSettingBTNID;
    private TextView txtTopHEadingID;


    Runnable getDataRequestRunnable = new Runnable() {
        @Override
        public void run() {
            if (!inRefresh) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new BluetoothCommunicationGetData().execute();
                        }
                    });
                } catch (Exception ex) {
                    Toast.makeText(CURRENT_CONTEXT, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            getDataRequestHandler.postDelayed(this, 60 * 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_data);

      /*  android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        txttemperature = (TextView) findViewById(R.id.txtTemperature);
        txtflow = (TextView) findViewById(R.id.txtFlow);
        txtactiveenergy = (TextView) findViewById(R.id.txtActiveEnergy);
        txtreactiveeneergy = (TextView) findViewById(R.id.txtReActiveEnergy);
        txtrmsvoltageA = (TextView) findViewById(R.id.txtRMSVoltageA);
        txtrmsvoltageB = (TextView) findViewById(R.id.txtRMSVoltageB);
        txtrmsvoltageC = (TextView) findViewById(R.id.txtRMSVoltageC);
        txtfreqency = (TextView) findViewById(R.id.txtFreqency);
        txtrmscurrentA = (TextView) findViewById(R.id.txtRMSCurrentA);
        txtrmscurrentB = (TextView) findViewById(R.id.txtRMSCurrentB);
        txtrmscurrentC = (TextView) findViewById(R.id.txtRMSCurrentC);
        txtactivepower = (TextView) findViewById(R.id.txtActivePower);
        txtreactivepower = (TextView) findViewById(R.id.txtReactivePower);
        txtapparentpower = (TextView) findViewById(R.id.txtApparentPower);
        txtpowerfactor = (TextView) findViewById(R.id.txtPowerFactor);
        refreshTime = (TextView) findViewById(R.id.refreshTime);

        f0 = (ImageView) findViewById(R.id.fbit0img);
        f1 = (ImageView) findViewById(R.id.fbit1img);
        f2 = (ImageView) findViewById(R.id.fbit2img);
        f3 = (ImageView) findViewById(R.id.fbit3img);
        f4 = (ImageView) findViewById(R.id.fbit4img);
        f5 = (ImageView) findViewById(R.id.fbit5img);
        f6 = (ImageView) findViewById(R.id.fbit6img);
        f7 = (ImageView) findViewById(R.id.fbit7img);
        f8 = (ImageView) findViewById(R.id.fbit8img);
        f8 = (ImageView) findViewById(R.id.fbit8img);
        f9 = (ImageView) findViewById(R.id.fbit9img);
        f10 = (ImageView) findViewById(R.id.fbit10img);
        f11 = (ImageView) findViewById(R.id.fbit11img);
        f12 = (ImageView) findViewById(R.id.fbit12img);
        f13 = (ImageView) findViewById(R.id.fbit13img);
        f14 = (ImageView) findViewById(R.id.fbit14img);
        f15 = (ImageView) findViewById(R.id.fbit15img);

        f2img = (ImageView) findViewById(R.id.f2img);
        f1img= (ImageView) findViewById(R.id.f1img);

        fd0 = (TextView) findViewById(R.id.fbit0desc);
        fd1 = (TextView) findViewById(R.id.fbit1desc);
        fd2 = (TextView) findViewById(R.id.fbit2desc);
        fd3 = (TextView) findViewById(R.id.fbit3desc);
        fd4 = (TextView) findViewById(R.id.fbit4desc);
        fd5 = (TextView) findViewById(R.id.fbit5desc);
        fd6 = (TextView) findViewById(R.id.fbit6desc);
        fd7 = (TextView) findViewById(R.id.fbit7desc);
        fd8 = (TextView) findViewById(R.id.fbit8desc);
        fd9 = (TextView) findViewById(R.id.fbit9desc);
        fd10 = (TextView) findViewById(R.id.fbit10desc);
        fd11 = (TextView) findViewById(R.id.fbit11desc);
        fd12 = (TextView) findViewById(R.id.fbit12desc);
        fd13 = (TextView) findViewById(R.id.fbit13desc);
        fd14 = (TextView) findViewById(R.id.fbit14desc);
        fd15 = (TextView) findViewById(R.id.fbit15desc);
        f2desc = (TextView) findViewById(R.id.f2desc);
        f1desc= (TextView) findViewById(R.id.f1desc);

        s0 = (ImageView) findViewById(R.id.sbit0img);
        s1 = (ImageView) findViewById(R.id.sbit1img);
        s2 = (ImageView) findViewById(R.id.sbit2img);
        sd0 = (TextView) findViewById(R.id.sbit0desc);
        sd1 = (TextView) findViewById(R.id.sbit1desc);
        sd2 = (TextView) findViewById(R.id.sbit2desc);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Pref", 0);
        address = sharedPref.getString(Bluetooth_DeviceLists_Activity.EXTRA_ADDRESS, "");
        name = sharedPref.getString(Bluetooth_DeviceLists_Activity.EXTRA_NAME, "");

        CURRENT_CONTEXT = getApplicationContext();
        getDataRequestHandler.postDelayed(getDataRequestRunnable, 0);


        imgBackBTNID = (ImageView) findViewById(R.id.imgBackBTNID);
        imgRefreshBTNID = (ImageView) findViewById(R.id.imgRefreshBTNID);
        imgSettingBTNID = (ImageView) findViewById(R.id.imgSettingBTNID);
        txtTopHEadingID = (TextView) findViewById(R.id.txtTopHEadingID);

        imgBackBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgSettingBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(j, 0);
            }
        });

        imgRefreshBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BluetoothCommunicationGetData().execute();
            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                inRefresh = true;
                new BluetoothCommunicationGetData().execute();
                break;
          /*  case R.id.action_settings:
                Intent k = new Intent(DataParameters_Activity.this, SettingsActivity.class);
                startActivityForResult(k, 0);
                break;*/
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void restoredefauts() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshTime.setText(R.string.placeholder_short);
                txttemperature.setText(R.string.placeholder_short);
                txtflow.setText(R.string.placeholder_short);
                txtactiveenergy.setText(R.string.placeholder_short);
                txtreactiveeneergy.setText(R.string.placeholder_short);
                txtrmsvoltageA.setText(R.string.placeholder_short);
                txtrmsvoltageB.setText(R.string.placeholder_short);
                txtrmsvoltageC.setText(R.string.placeholder_short);
                txtfreqency.setText(R.string.placeholder_short);
                txtrmscurrentA.setText(R.string.placeholder_short);
                txtrmscurrentB.setText(R.string.placeholder_short);
                txtrmscurrentC.setText(R.string.placeholder_short);
                txtactivepower.setText(R.string.placeholder_short);
                txtreactivepower.setText(R.string.placeholder_short);
                txtapparentpower.setText(R.string.placeholder_short);
                txtpowerfactor.setText(R.string.placeholder_short);

                s0.setImageResource(R.drawable.ic_nodata);
                sd0.setText(R.string.placeholder_long);
                s1.setImageResource(R.drawable.ic_nodata);
                sd1.setText(R.string.placeholder_long);
                s2.setImageResource(R.drawable.ic_nodata);
                sd2.setText(R.string.placeholder_long);
                f0.setImageResource(R.drawable.ic_nodata);
                fd0.setText(R.string.placeholder_long);
                f1.setImageResource(R.drawable.ic_nodata);
                fd1.setText(R.string.placeholder_long);
                f2.setImageResource(R.drawable.ic_nodata);
                fd2.setText(R.string.placeholder_long);
                f3.setImageResource(R.drawable.ic_nodata);
                fd3.setText(R.string.placeholder_long);
                f4.setImageResource(R.drawable.ic_nodata);
                fd4.setText(R.string.placeholder_long);
                f5.setImageResource(R.drawable.ic_nodata);
                fd5.setText(R.string.placeholder_long);
                f6.setImageResource(R.drawable.ic_nodata);
                fd6.setText(R.string.placeholder_long);
                f7.setImageResource(R.drawable.ic_nodata);
                fd7.setText(R.string.placeholder_long);
                f8.setImageResource(R.drawable.ic_nodata);
                fd8.setText(R.string.placeholder_long);
                f9.setImageResource(R.drawable.ic_nodata);
                fd9.setText(R.string.placeholder_long);
                f10.setImageResource(R.drawable.ic_nodata);
                fd10.setText(R.string.placeholder_long);
                f11.setImageResource(R.drawable.ic_nodata);
                fd11.setText(R.string.placeholder_long);
                f12.setImageResource(R.drawable.ic_nodata);
                fd12.setText(R.string.placeholder_long);
                f13.setImageResource(R.drawable.ic_nodata);
                fd13.setText(R.string.placeholder_long);
                f14.setImageResource(R.drawable.ic_nodata);
                fd14.setText(R.string.placeholder_long);
                f15.setImageResource(R.drawable.ic_nodata);
                fd15.setText(R.string.placeholder_long);
                f2img.setImageResource(R.drawable.ic_nodata);
                f2desc.setText(R.string.placeholder_long);
                f1img.setImageResource(R.drawable.ic_nodata);
                f1desc.setText(R.string.placeholder_long);

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (btSocket != null) {
            if (btSocket.isConnected()) {
                try {
                    btSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDataRequestHandler.removeCallbacks(getDataRequestRunnable);
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetData extends AsyncTask<Void, Void, Data_Parameters> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(DataParameters_Activity.this);
            pd.setCancelable(false);
            Utils.ShowProgressDialog(DataParameters_Activity.this, pd, "Loading...");
            restoredefauts();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Data_Parameters doInBackground(Void... requests) {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                        try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (btSocket == null) {
                    BluetoothAdapter myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice depositing = myBluetooth.getRemoteDevice(address);
                    btSocket = depositing.createRfcommSocketToServiceRecord(Utils.MyUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                }
                if (!btSocket.isConnected()) btSocket.connect();
                if (btSocket.isConnected()) {
                    byte[] getDataRequest = ":GETDATA#".getBytes(StandardCharsets.US_ASCII);
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    btSocket.getOutputStream().write(getDataRequest);
                    byte[] readBytes = new byte[100];
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    InputStream iStream = btSocket.getInputStream();
                    int retryCount = 0;
                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    int available;
                    while ((available = iStream.available()) == 0 && retryCount < maxRetryCount) {
                        retryCount++;
                        Thread.sleep(Utils.RETRY_WAIT_TIME);
                    }
                    if (available <= 0) return null;
                    int bytesRead = iStream.read(readBytes);
                    if (bytesRead == 66) {
                        return Data_Parameters.ParseObject(readBytes);
                    } else {
                        return null;
                    }
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Data_Parameters fresult) {
            super.onPostExecute(fresult);
            final Date currentTime = Calendar.getInstance().getTime();
            if (fresult == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Utils.HideProgressDialog(DataParameters_Activity.this, pd);
                            inRefresh = false;
                            Toast.makeText(DataParameters_Activity.this, "improper response", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(DataParameters_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                final Data_Parameters finalFresult = fresult;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshTime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                        txttemperature.setText(String.valueOf(finalFresult.getTemperature()));
                        txtflow.setText(String.valueOf(finalFresult.getFlow()));
                        txtactiveenergy.setText(String.valueOf(finalFresult.getActiveEnergy()));
                        txtreactiveeneergy.setText(String.valueOf(finalFresult.getReactiveEnergy()));
                        txtrmsvoltageA.setText(String.valueOf(finalFresult.getRmsVoltageA()));
                        txtrmsvoltageB.setText(String.valueOf(finalFresult.getRmsVoltageB()));
                        txtrmsvoltageC.setText(String.valueOf(finalFresult.getRmsVoltageC()));
                        txtfreqency.setText(String.valueOf(finalFresult.getFrequency()));
                        txtrmscurrentA.setText(String.valueOf(finalFresult.getRmsCurrentA()));
                        txtrmscurrentB.setText(String.valueOf(finalFresult.getRmsCurrentB()));
                        txtrmscurrentC.setText(String.valueOf(finalFresult.getRmsCurrentC()));
                        txtactivepower.setText(String.valueOf(finalFresult.getActivePower()));
                        txtreactivepower.setText(String.valueOf(finalFresult.getReactivePower()));
                        txtapparentpower.setText(String.valueOf(finalFresult.getApparentPower()));
                        txtpowerfactor.setText(String.valueOf(finalFresult.getPowerFactor()));

                        if (finalFresult.isStarterRunning()) {
                            s0.setImageResource(R.drawable.ic_green_yes);
                            sd0.setText("Starter Running");
                        } else {
                            s0.setImageResource(R.drawable.ic_red_no);
                            sd0.setText("Stopped");
                        }

                        if (finalFresult.isFault()) {
                            s1.setImageResource(R.drawable.ic_green_yes);
                            sd1.setText("Fault");
                        } else {
                            s1.setImageResource(R.drawable.ic_red_no);
                            sd1.setText("No Fault");
                        }

                        if (finalFresult.isAutoMode()) {
                            s2.setImageResource(R.drawable.ic_green_yes);
                            sd2.setText("Auto Mode");
                        } else {
                            s2.setImageResource(R.drawable.ic_red_no);
                            sd2.setText("Manual Mode");
                        }


                        if (finalFresult.isInputVoltageLow()) {
                            f0.setImageResource(R.drawable.ic_green_yes);
                            fd0.setText("Input Voltage Low");
                        } else {
                            f0.setImageResource(R.drawable.ic_red_no);
                            fd0.setText("Input Voltage Low-Error");
                        }

                        if (finalFresult.isInputVoltageHigh()) {
                            f1.setImageResource(R.drawable.ic_green_yes);
                            fd1.setText("Input Voltage High");
                        } else {
                            f1.setImageResource(R.drawable.ic_red_no);
                            fd1.setText("Input Voltage High-Error");
                        }

                        if (finalFresult.isOverCurrent()) {
                            f2.setImageResource(R.drawable.ic_green_yes);
                            fd2.setText("Over Current (Motor Stall)");
                        } else {
                            f2.setImageResource(R.drawable.ic_red_no);
                            fd2.setText("Over Current (Motor Stall)-Error");
                        }

                        if (finalFresult.isInputFrequencyLow()) {
                            f3.setImageResource(R.drawable.ic_green_yes);
                            fd3.setText("Input Frequency Low");
                        } else {
                            f3.setImageResource(R.drawable.ic_red_no);
                            fd3.setText("Input Frequency Low-Error");
                        }

                        if (finalFresult.isOverLoad()) {
                            f4.setImageResource(R.drawable.ic_green_yes);
                            fd4.setText("Over Load");
                        } else {
                            f4.setImageResource(R.drawable.ic_red_no);
                            fd4.setText("Over Load-Error");
                        }

                        if (finalFresult.isInputFrequencyHigh()) {
                            f5.setImageResource(R.drawable.ic_green_yes);
                            fd5.setText("Input Frequency High");
                        } else {
                            f5.setImageResource(R.drawable.ic_red_no);
                            fd5.setText("Input Frequency High-Error");
                        }

                        if (finalFresult.isStarterIdError()) {
                            f6.setImageResource(R.drawable.ic_green_yes);
                            fd6.setText("Starter Id Error");
                        } else {
                            f6.setImageResource(R.drawable.ic_red_no);
                            fd6.setText("Starter Id Error-Error");
                        }

                        if (finalFresult.isDryRunFault()) {
                            f7.setImageResource(R.drawable.ic_green_yes);
                            fd7.setText("Dry Run Fault");
                        } else {
                            f7.setImageResource(R.drawable.ic_red_no);
                            fd7.setText("Dry Run Fault-Error");
                        }

                        if (finalFresult.isSensorFault()) {
                            f8.setImageResource(R.drawable.ic_green_yes);
                            fd8.setText("Sensor Fault");
                        } else {
                            f8.setImageResource(R.drawable.ic_red_no);
                            fd8.setText("Sensor Fault-Error");
                        }


                        if (finalFresult.isTemperatureFault()) {
                            f9.setImageResource(R.drawable.ic_green_yes);
                            fd9.setText("Temperature Fault");
                        } else {
                            f9.setImageResource(R.drawable.ic_red_no);
                            fd9.setText("Temperature Fault-Error");
                        }

                        if (finalFresult.isAmbientTemperatureHigh()) {
                            f10.setImageResource(R.drawable.ic_green_yes);
                            fd10.setText("Ambient Temperature High");
                        } else {
                            f10.setImageResource(R.drawable.ic_red_no);
                            fd10.setText("Ambient Temperature High-Error");
                        }


                        if (finalFresult.isEarthFault()) {
                            f11.setImageResource(R.drawable.ic_green_yes);
                            fd11.setText("EARTH Fault");
                        } else {
                            f11.setImageResource(R.drawable.ic_red_no);
                            fd11.setText("EARTH Fault-Error");
                        }

                        if (finalFresult.isWaterEmpty()) {
                            f12.setImageResource(R.drawable.ic_green_yes);
                            fd12.setText("WATER Empty");
                        } else {
                            f12.setImageResource(R.drawable.ic_red_no);
                            fd12.setText("WATER Empty-Error");
                        }

                        if (finalFresult.isTooManyStarts()) {
                            f13.setImageResource(R.drawable.ic_green_yes);
                            fd13.setText("Too Many starts");
                        } else {
                            f13.setImageResource(R.drawable.ic_red_no);
                            fd13.setText("Too Many starts-Error");
                        }

                        if (finalFresult.isRtcError()) {
                            f14.setImageResource(R.drawable.ic_green_yes);
                            fd14.setText("RTC Error");
                        } else {
                            f14.setImageResource(R.drawable.ic_red_no);
                            fd14.setText("RTC Error-Error");
                        }

                        if (finalFresult.isVoltageUnBalance()) {
                            f15.setImageResource(R.drawable.ic_green_yes);
                            fd15.setText("VOLTAGE Unbalance");
                        } else {
                            f15.setImageResource(R.drawable.ic_red_no);
                            fd15.setText("VOLTAGE Unbalance-Error");
                        }


                        if (finalFresult.isCurrentUnBalance()) {
                            f2img.setImageResource(R.drawable.ic_green_yes);
                            f2desc.setText("Current Unbalance");
                        } else {
                            f2img.setImageResource(R.drawable.ic_red_no);
                            f2desc.setText("Current Unbalance-Error");
                        }


                        if (finalFresult.isCurrenLimit()) {
                            f2img.setImageResource(R.drawable.ic_green_yes);
                            f2desc.setText("Current Limit");
                        } else {
                            f2img.setImageResource(R.drawable.ic_red_no);
                            f2desc.setText("Current Limit-Error");
                        }

                        Utils.HideProgressDialog(DataParameters_Activity.this, pd);
                        inRefresh = false;
                    }
                });
            }
        }
    }
}
