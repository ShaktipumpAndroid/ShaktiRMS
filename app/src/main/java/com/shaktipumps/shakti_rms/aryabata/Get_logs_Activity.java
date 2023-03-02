package com.shaktipumps.shakti_rms.aryabata;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.aryabata.classes.Data_Parameters;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.CustomShowcaseView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Get_logs_Activity extends AppCompatActivity implements View.OnClickListener {

    public String name = null;
    BluetoothSocket btSocket = null;
    String address = null;
    TextView txtTemperature;
    TextView txtFlow;
    TextView txtActiveEnergy;
    TextView txtReActiveEnergy;
    TextView txtRMSVoltageA,txtRMSVoltageB,txtRMSVoltageC;
    TextView txtFreqency;
    TextView txtRMSCurrentA,txtRMSCurrentB,txtRMSCurrentC;
    TextView txtActivePower;
    TextView txtReactivePower;
    TextView txtApparentPower;
    TextView txtPowerFactor;
    TextView refreshTime;
    EditText logNumber;

    ImageView f0, f1, f2, f3, f4, f5, f6, f7, f8, s0, s1, s2, f9, f10, f11, f12, f13, f14, f15,f2img,f1img;
    TextView fd0, fd1, fd2, fd3, fd4, fd5, fd6, fd7, fd8, fd9, fd10, fd11, fd12, fd13, fd14, fd15, sd0, sd1, sd2,f2desc,f1desc;

    MaterialFancyButton materialFancyButton;
    private Context context;
    private ShowcaseView showcaseView, showcaseView1;
    private int counter = 0;


    private ImageView imgBackBTNID;
  //  private ImageView imgRefreshBTNID;
    private ImageView imgSettingBTNID;
    private TextView txtTopHEadingID;


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
                Resources resources = getResources();
                showcaseView.hide();
                setAlpha(1.0f, materialFancyButton, logNumber);
                showcaseView1 = new ShowcaseView.Builder(this)
                        .setTarget(new ViewTarget(logNumber))
                        .setOnClickListener(this)
                        .setContentTitlePaint(title)
                        .setStyle(R.style.CustomShowcaseTheme_red)
                        .setShowcaseDrawer(new CustomShowcaseView(resources.getDimension(R.dimen.custom_showcase_width2), resources.getDimension(R.dimen.custom_showcase_height2), resources.getColor(R.color.colorAccentLight1), resources.getColor(R.color.colorAccentLight1))).build();
                showcaseView1.setContentTitle("Get Logs");
                showcaseView1.setContentText("Get Logs button enables user to get logs of his desiic_red_no by giving log input number.");
                break;

            case 1:
                showcaseView1.setTarget(Target.NONE);
                showcaseView1.setContentTitle("Ready to Rock!");
                showcaseView1.setContentText("Go Ahead.!");
                showcaseView1.setButtonText("Close");
                setAlpha(0.4f, materialFancyButton, logNumber);
                break;
            case 2:
                showcaseView1.hide();
                setAlpha(1.0f, materialFancyButton, logNumber);
                break;

        }
        counter++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_logs);
        materialFancyButton = (MaterialFancyButton) findViewById(R.id.getlog);
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(materialFancyButton))
                .setOnClickListener(this)
                .setStyle(R.style.CustomShowcaseTheme2)
                .singleShot(6)
                .withMaterialShowcase()
                .build();
        showcaseView.setButtonText(getString(R.string.next));
        showcaseView.setContentTitle("Get Logs");
        showcaseView.setContentText("Get Logs button enables user to get logs by giving log input number.");

       /* android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        logNumber = (EditText) findViewById(R.id.editgetlog);

        materialFancyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String logNo = logNumber.getText().toString();
                if (logNo.length() == 0) {
                    Toast.makeText(context, "Log Number Requiic_red_no!", Toast.LENGTH_LONG).show();
                    return;
                }
                Integer IlogNo = Integer.parseInt(logNo);
                if (IlogNo <= 0 || IlogNo > 40000) {
                    Toast.makeText(context, "Not a Valid Log Number!", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new BluetoothCommunicationGetLOGData().execute();
                        }
                    });
                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        txtTemperature = (TextView) findViewById(R.id.txtTemperature);
        txtFlow = (TextView) findViewById(R.id.txtFlow);
        txtActiveEnergy = (TextView) findViewById(R.id.txtActiveEnergy);
        txtReActiveEnergy = (TextView) findViewById(R.id.txtReActiveEnergy);
        txtRMSVoltageA = (TextView) findViewById(R.id.txtRMSVoltageA);
        txtRMSVoltageB = (TextView) findViewById(R.id.txtRMSVoltageB);
        txtRMSVoltageC = (TextView) findViewById(R.id.txtRMSVoltageC);


        txtFreqency = (TextView) findViewById(R.id.txtFreqency);
        txtRMSCurrentA = (TextView) findViewById(R.id.txtRMSCurrentA);
        txtRMSCurrentB = (TextView) findViewById(R.id.txtRMSCurrentB);
        txtRMSCurrentC = (TextView) findViewById(R.id.txtRMSCurrentC);

        txtActivePower = (TextView) findViewById(R.id.txtActivePower);
        txtReactivePower = (TextView) findViewById(R.id.txtReactivePower);
        txtApparentPower = (TextView) findViewById(R.id.txtApparentPower);
        txtPowerFactor = (TextView) findViewById(R.id.txtPowerFactor);
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
        f9 = (ImageView) findViewById(R.id.fbit9img);

        f10 = (ImageView) findViewById(R.id.fbit10img);
        f11 = (ImageView) findViewById(R.id.fbit11img);
        f12 = (ImageView) findViewById(R.id.fbit12img);
        f13 = (ImageView) findViewById(R.id.fbit13img);
        f14 = (ImageView) findViewById(R.id.fbit14img);
        f15 = (ImageView) findViewById(R.id.fbit15img);


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
        f2img= (ImageView) findViewById(R.id.f2img);
        f1img= (ImageView) findViewById(R.id.f1img);


        fd10 = (TextView) findViewById(R.id.fbit10desc);
        fd11 = (TextView) findViewById(R.id.fbit11desc);
        fd12 = (TextView) findViewById(R.id.fbit12desc);
        fd13 = (TextView) findViewById(R.id.fbit13desc);
        fd14 = (TextView) findViewById(R.id.fbit14desc);
        fd15 = (TextView) findViewById(R.id.fbit15desc);
        f2desc = (TextView) findViewById(R.id.f2desc);
        f1desc = (TextView) findViewById(R.id.f1desc);


        s0 = (ImageView) findViewById(R.id.sbit0img);
        s1 = (ImageView) findViewById(R.id.sbit1img);
        s2 = (ImageView) findViewById(R.id.sbit2img);
        sd0 = (TextView) findViewById(R.id.sbit0desc);
        sd1 = (TextView) findViewById(R.id.sbit1desc);
        sd2 = (TextView) findViewById(R.id.sbit2desc);

        SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences("Pref", 0);
        address = SharedPreferences.getString(Bluetooth_DeviceLists_Activity.EXTRA_ADDRESS, "");
        name = SharedPreferences.getString(Bluetooth_DeviceLists_Activity.EXTRA_NAME, "");

        context = getApplicationContext();


        imgBackBTNID = (ImageView) findViewById(R.id.imgBackBTNID);
      //  imgRefreshBTNID = (ImageView) findViewById(R.id.imgRefreshBTNID);
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

       /* imgRefreshBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataParameters_Activity.BluetoothCommunicationGetData().execute();
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
                Intent k = new Intent(Get_logs_Activity.this, SettingsActivity.class);
                startActivityForResult(k, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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

    public void restoredefauts() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                refreshTime.setText(R.string.placeholder_short);
                txtTemperature.setText(R.string.placeholder_short);
                txtFlow.setText(R.string.placeholder_short);
                txtActiveEnergy.setText(R.string.placeholder_short);
                txtReActiveEnergy.setText(R.string.placeholder_short);
                txtRMSVoltageA.setText(R.string.placeholder_short);
                txtRMSVoltageB.setText(R.string.placeholder_short);
                txtRMSVoltageC.setText(R.string.placeholder_short);

                txtFreqency.setText(R.string.placeholder_short);
                txtRMSCurrentA.setText(R.string.placeholder_short);
                txtRMSCurrentB.setText(R.string.placeholder_short);
                txtRMSCurrentC.setText(R.string.placeholder_short);

                txtActivePower.setText(R.string.placeholder_short);
                txtReactivePower.setText(R.string.placeholder_short);
                txtApparentPower.setText(R.string.placeholder_short);
                txtPowerFactor.setText(R.string.placeholder_short);

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


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetLOGData extends AsyncTask<Void, Void, Data_Parameters> {

        boolean getLogTime = true;
        ProgressDialog pd;
        String cDateTime;
        String logNo;
        boolean logExists = true;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Get_logs_Activity.this);
            pd.setCancelable(false);
            Utils.ShowProgressDialog(Get_logs_Activity.this, pd, "Loading...");
            restoredefauts();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("DefaultLocale")
        private boolean GetLogTime() {
            try {
                logNo = logNumber.getText().toString();
                Integer IlogNo = Integer.parseInt(logNo);
                logNo = String.format("%05d", IlogNo);
                byte[] getDataRequest = (":GETLOGTIME=" + logNo + "#").getBytes(StandardCharsets.US_ASCII);
                try {
                    sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                btSocket.getOutputStream().write(getDataRequest);
                byte[] readBytes = new byte[50];
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
                if (available <= 0) return false;
                int bytesRead = iStream.read(readBytes);
                if (bytesRead == 19) {
                    String text = new String(Utils.Trim(readBytes), StandardCharsets.US_ASCII).trim();
                    if (text.startsWith(":") && text.endsWith("#")) {

                        String day = text.substring(1, 3);
                        String month = text.substring(4, 6);
                        String year = text.substring(7, 9);
                        String sec = text.substring(10, 12);
                        String min = text.substring(13, 15);
                        String hour = text.substring(16, 18);

                        int iDay = Integer.parseInt(day);
                        int iMonth = Integer.parseInt(month);
                        int iYear = Integer.parseInt(year);
                        int iSec = Integer.parseInt(sec);
                        int iMin = Integer.parseInt(min);
                        int iHour = Integer.parseInt(hour);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(iYear, iMonth, iDay, iHour, iMin, iSec);
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                        cDateTime = format.format(calendar.getTime());
                        return true;
                    } else {
                        return false;
                    }
                } else if (bytesRead == 7) {
                    if (Objects.requireNonNull(Utils.BytesToString(readBytes)).equals(":ERROR#")) {
                        logExists = false;
                    }
                    return false;
                } else {
                    return false;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;
            }
        }

        //sample bytes
        //new byte[]{0x03,0x2E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7B, (byte)0xD4, 0x7A, 0x43, (byte)0xE1, 0x7A, 0x47, 0x42, 0x6E, 0x12, 0x03, 0x3B, 0x00, 0x00, 0x00, 0x00, (byte)0xEB, 0x11, (byte)0x83, 0x42, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F}

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
                    boolean isValidLogTime;
                    if (getLogTime) {
                        isValidLogTime = GetLogTime();
                        if (!isValidLogTime) {
                            return null;
                        }
                    }
                    byte[] getDataRequest = (":GETLOG=" + logNo + "#").getBytes(StandardCharsets.US_ASCII);
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
                        Data_Parameters parameters = Data_Parameters.ParseObject(readBytes);
                        if (parameters == null) return null;
                        if (cDateTime.length() > 0) parameters.setCurrentDateTime(cDateTime);
                        return parameters;
                    } else if (bytesRead == 7) {
                        if (Objects.requireNonNull(Utils.BytesToString(readBytes)).equals(":ERROR#")) {
                            logExists = false;
                        }
                        return null;
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
            if (fresult == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Utils.HideProgressDialog(Get_logs_Activity.this, pd);
                            if (logExists) {
                                Toast.makeText(Get_logs_Activity.this, "IMPROPER RESPONSE", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Get_logs_Activity.this, "No LOG at a specified Log Number", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(Get_logs_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                final Data_Parameters finalFresult = fresult;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        refreshTime.setText(finalFresult.getCurrentDateTime());
                        txtTemperature.setText(String.valueOf(finalFresult.getTemperature()));
                        txtFlow.setText(String.valueOf(finalFresult.getFlow()));
                        txtActiveEnergy.setText(String.valueOf(finalFresult.getActiveEnergy()));
                        txtReActiveEnergy.setText(String.valueOf(finalFresult.getReactiveEnergy()));
                        txtRMSVoltageA.setText(String.valueOf(finalFresult.getRmsVoltageA()));
                        txtRMSVoltageB.setText(String.valueOf(finalFresult.getRmsVoltageB()));
                        txtRMSVoltageC.setText(String.valueOf(finalFresult.getRmsVoltageC()));

                        txtFreqency.setText(String.valueOf(finalFresult.getFrequency()));
                        txtRMSCurrentA.setText(String.valueOf(finalFresult.getRmsCurrentA()));
                        txtRMSCurrentB.setText(String.valueOf(finalFresult.getRmsCurrentB()));
                        txtRMSCurrentC.setText(String.valueOf(finalFresult.getRmsCurrentC()));

                        txtActivePower.setText(String.valueOf(finalFresult.getActivePower()));
                        txtReactivePower.setText(String.valueOf(finalFresult.getReactivePower()));
                        txtApparentPower.setText(String.valueOf(finalFresult.getApparentPower()));
                        txtPowerFactor.setText(String.valueOf(finalFresult.getPowerFactor()));



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


                        Utils.HideProgressDialog(Get_logs_Activity.this, pd);
                    }
                });
            }
        }
    }
}