package com.shaktipumps.shakti_rms.aryabata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.aryabata.classes.Calibrations_Parameters;
import com.shaktipumps.shakti_rms.aryabata.classes.EditTextDecimalFilter;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.CustomShowcaseView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Operations_GetSet_Activity extends AppCompatActivity implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {


    private CountDownTimer countDownTimer;
    private TextView txtTimerCounterID;
    public String address;
    public String name;
    BluetoothAdapter myBluetooth;
    BluetoothSocket btSocket;
    ProgressDialog pd;
    TextView txtstart, txtgetidtime, txtclearenergydata, txtclearlog;
    EditText edittxtstart, editcalibration;
    TextView textstop, txtcallibration;
    EditText Edittextstop, edittextmodel;
    TextView txtreset, txtidstatus, txterrorinsetid, txtgetime, txtgetimeerror, txtcalibration, txtcallibrationtime;
    EditText edittxtreset, editclearlog, editclearenergy, editgetid, editdate,edittextbatch;
    TextView txtgedatetime, txtgetdateerror;
    EditText edittime, txtdate;
    MaterialFancyButton materialFancyButton, setcalibration;
    MaterialFancyButton materialFancyButton1;
    MaterialFancyButton materialFancyButton2;
    MaterialFancyButton date, getdatepicker, setdate, settime;
    MaterialFancyButton time, setidbutton, getidbutton, getcleardatabutton, getclearlogbutton;
    MaterialFancyButton autoset;
    EditText edittxtautoset;
    TextView txtautoset;
    TimePickerDialog timePickerDialog;
    int CalendarHour, CalendarMinute;
    private Calendar calendar;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog2;

    private int Year, Month, Day;
    private int Year1, Month1, Day1;

    private Calendar calendar1;
    private String mDateEnglishOnly;
    private String mDateEnglishOnly1;
    String mSelectedDate;


    int c_Year, c_Month, c_Day;

    AppCompatSpinner materialDesignSpinner,calibratespinner;
    private ShowcaseView showcaseView, showcaseView1;
    private int counter = 0;
    private Context CURRENT_CONTEXT;
    private DecimalFormat decimalFormatThreeTwo = new DecimalFormat("000.00");


    private int mDateSelectionCheck = 0;

    private ImageView imgBackBTNID;
    private ImageView imgRefreshBTNID;
    private ImageView imgSettingBTNID;
    private TextView txtTopHEadingID;
    private ImageView imgStartBTNID, imgStopBTNID;
    private TextView txtStartStopResultID,txtStartStopResultDateTimeID;

    private RelativeLayout rlvTimerCounterViewID;


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
                showcaseView.setShowcase(new ViewTarget(date), true);
                showcaseView.setContentTitle("Pick Date");
                showcaseView.setContentText("Click To select Date from date picker");
                break;

            case 1:
                showcaseView.setShowcase(new ViewTarget(time), true);
                showcaseView.setContentTitle("Pick Time");
                showcaseView.setContentText("Click To select Time from time picker");
                break;

            case 2:
                Resources resources = getResources();
                showcaseView.hide();
                setAlpha(1.0f, materialDesignSpinner, date, time, setidbutton);
                showcaseView1 = new ShowcaseView.Builder(this)
                        .setTarget(new ViewTarget(materialDesignSpinner))
                        .setOnClickListener(this)
                        .setContentTitlePaint(title)
                        .setStyle(R.style.CustomShowcaseTheme_red)
                        .setShowcaseDrawer(new CustomShowcaseView(resources.getDimension(R.dimen.custom_showcase_width2), resources.getDimension(R.dimen.custom_showcase_height2), resources.getColor(R.color.colorAccentLight1), resources.getColor(R.color.colorAccentLight1))).build();
                showcaseView1.setContentTitle("Spinner");
                showcaseView1.setContentText("Select Model from Spinner to Set ID");
                break;

            case 3:
                showcaseView1.setTarget(Target.NONE);
                showcaseView1.setContentTitle("Ready to Rock!");
                showcaseView1.setContentText("Go Ahead.!");
                showcaseView1.setButtonText("Close");
                setAlpha(0.4f, materialDesignSpinner, date, time, setidbutton);
                break;
            case 4:
                showcaseView1.hide();
                setAlpha(1.0f, materialDesignSpinner, date, time, setidbutton);
                break;

        }
        counter++;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_operations);

        setidbutton = (MaterialFancyButton) findViewById(R.id.setidb);

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(setidbutton))
                .setOnClickListener(this)
                .setStyle(R.style.CustomShowcaseTheme2)
                .withMaterialShowcase()
                .singleShot(3)
                .build();

        showcaseView.setButtonText(getString(R.string.next));
        showcaseView.setContentTitle("Set Button");
        showcaseView.setContentText("Click to Set data");

        calendar1 = Calendar.getInstance();
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Year1 = calendar.get(Calendar.YEAR);
        Month1 = calendar.get(Calendar.MONTH);
        Day1 = calendar.get(Calendar.DAY_OF_MONTH);


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Pref", 0);
        address = sharedPref.getString(Bluetooth_DeviceLists_Activity.EXTRA_ADDRESS, "");
        name = sharedPref.getString(Bluetooth_DeviceLists_Activity.EXTRA_NAME, "");
        CURRENT_CONTEXT = this;
        rlvTimerCounterViewID = (RelativeLayout) findViewById(R.id.rlvTimerCounterViewID);
        txtTimerCounterID = (TextView) findViewById(R.id.txtTimerCounterID);
        txtstart = (TextView) findViewById(R.id.txtstart);
        edittxtstart = (EditText) findViewById(R.id.edittxtstart);
        textstop = (TextView) findViewById(R.id.textstop);
        Edittextstop = (EditText) findViewById(R.id.Edittextstop);
        txtreset = (TextView) findViewById(R.id.txtreset);
        edittxtreset = (EditText) findViewById(R.id.edittxtreset);

        edittxtautoset = (EditText) findViewById(R.id.edittxtautoset);
        txtautoset = (TextView) findViewById(R.id.txtautoset);

        txtgetime = (TextView) findViewById(R.id.txtgetime);
        settime = (MaterialFancyButton) findViewById(R.id.setitime);

        txtgetimeerror = (TextView) findViewById(R.id.txtgetimeerror);

        Switch simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
       final TextView switchStatus = (TextView) findViewById(R.id.switchStatus);



        editgetid = (EditText) findViewById(R.id.editgetid);//vikas
        txtgetidtime = (TextView) findViewById(R.id.txtgetidtime);

    editdate = (EditText) findViewById(R.id.editdate);

        editclearlog = (EditText) findViewById(R.id.editclearlog);
        editclearenergy = (EditText) findViewById(R.id.editclearenergydata);

        edittextmodel = (EditText) findViewById(R.id.edittextmodel);
        edittextbatch = (EditText) findViewById(R.id.edittextbatch);
        //edittextbatch.setRawInputType(Configuration.KEYBOARD_QWERTY);

        txtidstatus = (TextView) findViewById(R.id.txtidstatus);

        txtgedatetime = (TextView) findViewById(R.id.txtgedatetime);
        txtgetdateerror = (TextView) findViewById(R.id.txtgetdateerror);
        edittime = (EditText) findViewById(R.id.edittime);
        txtdate = (EditText) findViewById(R.id.txtdate);
        txtreset = (TextView) findViewById(R.id.txtreset);

        date = (MaterialFancyButton) findViewById(R.id.datepicker);
        txterrorinsetid = (TextView) findViewById(R.id.txterrorinsetid);

        calendar = Calendar.getInstance();

        c_Year = calendar.get(Calendar.YEAR);
        c_Month = calendar.get(Calendar.MONTH);
        c_Day = calendar.get(Calendar.DAY_OF_MONTH);

        time = (MaterialFancyButton) findViewById(R.id.timepicker);
        calendar = Calendar.getInstance();


        getidbutton = (MaterialFancyButton) findViewById(R.id.getid);
        getcleardatabutton = (MaterialFancyButton) findViewById(R.id.getclearenergydata);
        getclearlogbutton = (MaterialFancyButton) findViewById(R.id.getclearlog);
        setdate = (MaterialFancyButton) findViewById(R.id.setdate);
        getdatepicker = (MaterialFancyButton) findViewById(R.id.getdatepicker1);





        txtclearenergydata = (TextView) findViewById(R.id.txtclearenergydata);
        txtclearlog = (TextView) findViewById(R.id.txtclearlog);

        materialFancyButton = (MaterialFancyButton) findViewById(R.id.start);
        materialFancyButton1 = (MaterialFancyButton) findViewById(R.id.stop);
        materialFancyButton2 = (MaterialFancyButton) findViewById(R.id.reset);


        imgStartBTNID = (ImageView) findViewById(R.id.imgStartBTNID);
        imgStopBTNID = (ImageView) findViewById(R.id.imgStopBTNID);
        txtStartStopResultID = (TextView) findViewById(R.id.txtStartStopResultID);
        txtStartStopResultDateTimeID = (TextView) findViewById(R.id.txtStartStopResultDateTimeID);

        autoset = (MaterialFancyButton) findViewById(R.id.autoset);

        editcalibration = (EditText) findViewById(R.id.editcalibration);
        editcalibration.setFilters(new InputFilter[]{new EditTextDecimalFilter(3, 2)});

        setcalibration = (MaterialFancyButton) findViewById(R.id.setcalibration);
        txtcalibration = (TextView) findViewById(R.id.txtcalibration);
        calibratespinner = (AppCompatSpinner) findViewById(R.id.calibratespinner);
        txtcallibrationtime = (TextView) findViewById(R.id.txtcalibrationtime);


        ArrayAdapter<String> calibrate_dataArrayAdapter = new ArrayAdapter<>(CURRENT_CONTEXT, R.layout.operations_spinner_item, Objects.requireNonNull(Calibrations_Parameters.DefaultStrings()));
        calibratespinner.setAdapter(calibrate_dataArrayAdapter);
        calibrate_dataArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        materialDesignSpinner = (AppCompatSpinner) findViewById(R.id.modelspinner);
         final ArrayAdapter<String> spinnerCountnameArrayAdapter = new ArrayAdapter<>(this, R.layout.operations_spinner_item, getResources().getStringArray(R.array.Bluetooth_Names));

        spinnerCountnameArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        final ArrayAdapter<String> spinnerthreephaseArrayAdapter = new ArrayAdapter<>(this, R.layout.operations_spinner_item, getResources().getStringArray(R.array.ThreePhase_Bluetooth_Names));
        spinnerthreephaseArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        simpleSwitch.setChecked(true);

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){

                    switchStatus.setText("Three Phase");

                    materialDesignSpinner.setAdapter(spinnerthreephaseArrayAdapter);


                }else{
                    switchStatus.setText("Single Phase");

                    materialDesignSpinner.setAdapter(spinnerCountnameArrayAdapter);

                }

            }
        });


        if(simpleSwitch.isChecked()){

            switchStatus.setText("Three Phase");

            materialDesignSpinner.setAdapter(spinnerthreephaseArrayAdapter);

        }
        else {
            switchStatus.setText("Single Phase");

            materialDesignSpinner.setAdapter(spinnerCountnameArrayAdapter);


        }


        setcalibration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String parameter = calibratespinner.getSelectedItem().toString();
                if (parameter.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Parameter should be selected!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String val = editcalibration.getText().toString();
                if (val.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Value for the Parameter is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Float fVal = Float.parseFloat(val);
                Calibrations_Parameters cp = Calibrations_Parameters.GetMatchingRecordForTheName(parameter);
                if (cp == null) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Parameter should be selected!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String request = ":CALIB," + String.format("%02d", cp.getParamId()) + "," + decimalFormatThreeTwo.format(fVal) + "#";

                BluetoothCommunicationForFirstActivity activity = new BluetoothCommunicationForFirstActivity();

                activity.RetryCount = 3000;
                activity.execute(request, ":OK#", "CALIBRATE");

//                Byte bParameter = cp.getParamId().byteValue();
//                byte[] bytes = Utils.Float2ByteArray(fVal);
//                byte[] requestBytes = {0x06, 0x07, bParameter, bytes[0], bytes[1], bytes[2], bytes[3]};
//                Integer crc = Utils.Calculated_CRC(requestBytes, (byte) requestBytes.length);
//                byte[] crcBytes = Utils.Int2ByteArray2(crc);
//                byte[] finalRequestBytes = {requestBytes[0], requestBytes[1], requestBytes[2], requestBytes[3], requestBytes[4], requestBytes[5], requestBytes[6], crcBytes[0], crcBytes[1]};
//                new BluetoothCommunicationForFirstActivityWithByteArray().execute(finalRequestBytes, ":OK#".getBytes(StandardCharsets.US_ASCII), "CALIBRATE".getBytes(StandardCharsets.US_ASCII));

            }
        });

        setidbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String modelID = materialDesignSpinner.getSelectedItem().toString();
                if (modelID.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Model should be selected!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String serialNumber = edittextmodel.getText().toString();
                if (serialNumber.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Serial Number is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (serialNumber.length() < 4) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Serial Number should contain 4 chars!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String batchNumber = edittextbatch.getText().toString();
                if (batchNumber.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Batch Number is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (batchNumber.length() < 2) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Batch Number should contain 2 chars!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String date = txtdate.getText().toString();
                if (date.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Date should be selected!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String dateArray[] = date.split("-");

                String modelSelectionId = "1";
                switch (modelID) {
                    case "SKU1":
                        modelSelectionId = "1";
                        break;
                    case "SKU2":
                        modelSelectionId = "2";
                        break;
                    case "SKU3":
                        modelSelectionId = "3";
                        break;
                    case "SKU1A":
                        modelSelectionId = "4";
                        break;
                    case "SKU1B":
                        modelSelectionId = "5";
                        break;
                    case "SKU2A":
                        modelSelectionId = "6";
                        break;
                    case "SKU2B":
                        modelSelectionId = "7";
                        break;


                }
                @SuppressLint("DefaultLocale")
                String request = ":ID," + modelSelectionId + "," + serialNumber + batchNumber + "," + String.format("%02d", Integer.parseInt(dateArray[0])) + "," + String.format("%02d", Integer.parseInt(dateArray[1])) + "," + String.format("%02d", Integer.parseInt(dateArray[2])) + "#";
                new BluetoothCommunicationForFirstActivity().execute(request, ":OK#", "SETID");
            }
        });


        settime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, getString(R.string.LoadingMessage));
                String time = edittime.getText().toString();
                if (time.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Time should be selected!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String timeArray[] = time.split(":");
                @SuppressLint("DefaultLocale")
                String request = ":SETTIME,00," + String.format("%02d", Integer.parseInt(timeArray[1])) + "," + String.format("%02d", Integer.parseInt(timeArray[0])) + "#";
                new BluetoothCommunicationForFirstActivity().execute(request, ":OK#", "SETTIME");
            }
        });

        setdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String date = editdate.getText().toString();
                if (date.length() == 0) {
                    Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
                    Toast.makeText(CURRENT_CONTEXT, "Date should be selected!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String dateArray[] = date.split("-");
                @SuppressLint("DefaultLocale")
                String request = ":SETDATE," + String.format("%02d", Integer.parseInt(dateArray[0])) + "," + String.format("%02d", Integer.parseInt(dateArray[1])) + "," + String.format("%02d", Integer.parseInt(dateArray[2])) + "#";
                new BluetoothCommunicationForFirstActivity().execute(request, ":OK#", "SETDATE");
            }
        });

        getidbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":GETID#", ":ID-", "GETID", "override");
            }
        });

        getcleardatabutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":CLR ENERGY#", ":OK#", "CLEARDATA");
            }
        });

        getclearlogbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":CLR LOG#", ":OK#", "CLEARLOG");
            }
        });


        materialFancyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":START#", ":START OK#", "START");
            }
        });

        imgStartBTNID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":START#", ":START OK#", "START");
            }
        });


        imgStopBTNID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":STOP#", ":STOP OK#", "STOP");
            }
        });

        materialFancyButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":STOP#", ":STOP OK#", "STOP");
            }
        });
        materialFancyButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":RESET FAULT#", ":RESET OK#", "RESET");
            }
        });
        autoset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationForFirstActivity().execute(":AUTOSET#", ":OK#", "AUTOSET");
            }
        });





      /*  final DatePickerDialog.OnDateSetListener iddateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                String strDate = format.format(calendar.getTime());
                txtdate.setText(strDate);
            }

            *//*@Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                String strDate = format.format(calendar.getTime());
                txtdate.setText(strDate);
            }*//*
        };*/

       /* final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                String strDate = format.format(calendar.getTime());
                editdate.setText(strDate);
            }

           *//* @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
                String strDate = format.format(calendar.getTime());
                editdate.setText(strDate);
            }*//*
        };*/

        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mDateSelectionCheck = 1;

                ChooseDate();

               /* datePickerDialog = DatePickerDialog.newInstance(iddateListener, c_Year, c_Month, c_Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#ea920d"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");*//*datePickerDialog = DatePickerDialog.newInstance(iddateListener, c_Year, c_Month, c_Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#ea920d"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");*/
            }
        });


        getdatepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mDateSelectionCheck = 2;

                ChooseDate();

               /* datePickerDialog2 = DatePickerDialog.newInstance(dateListener, c_Year, c_Month, c_Day);
                datePickerDialog2.setThemeDark(false);
                datePickerDialog2.showYearPickerFirst(false);
                datePickerDialog2.setAccentColor(Color.parseColor("#ea920d"));
                datePickerDialog2.setTitle("Select Date From DatePickerDialog");
                datePickerDialog2.show(getFragmentManager(), "DatePickerDialog");*/
            }
        });


       /* final TimePickerDialog.OnTimeSetListener time1Listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = hourOfDay + ":" + minute;
                edittime.setText(selectedTime);
            }

           *//* @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                String selectedTime = hourOfDay + ":" + minute;
                edittime.setText(selectedTime);
            }*//*
        };*/

        time.setOnClickListener(new View.OnClickListener() {//865420
            public void onClick(View v) {
               /* CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = TimePickerDialog.newInstance(time1Listener, CalendarHour, CalendarMinute, true);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.setAccentColor("#ea920d");
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(Operations_GetSet_Activity.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });
                timePickerDialog.show(getFragmentManager(), "Material Design TimePicker Dialog");*/

                chooseTimePicker();
            }
        });

        pd = new ProgressDialog(CURRENT_CONTEXT);
        pd.setCancelable(false);

       /* android.support.v7.app.ActionBar actionBar = getSupportActionBar();
       assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);*/

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
                if (Reconnect()) {
                    Toast.makeText(CURRENT_CONTEXT, "Bluetooth Re-connected Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "Bluetooth Re-connection Failed!, Please Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private void startTimer(long totalTimeCountInMilliseconds) {
        txtTimerCounterID.setVisibility(View.VISIBLE);
        //btn_send_otp.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

//
                txtTimerCounterID.setVisibility(View.VISIBLE);
                rlvTimerCounterViewID.setVisibility(View.VISIBLE);
                txtTimerCounterID.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                txtTimerCounterID.setText("Time up!");
                txtTimerCounterID.setVisibility(View.GONE);
                rlvTimerCounterViewID.setVisibility(View.GONE);
                changeButtonVisibilityRLV(true, 1.0f,imgStartBTNID);

                // textViewShowTime.setVisibility(View.VISIBLE);
                // buttonStartTime.setVisibility(View.VISIBLE);
                //  buttonStopTime.setVisibility(View.GONE);
                //  edtTimerValue.setVisibility(View.VISIBLE);
            }

        }.start();

    }


    private void changeButtonVisibilityRLV(boolean state, float alphaRate, ImageView txtDataExtractionID) {
        txtDataExtractionID.setEnabled(state);
        txtDataExtractionID.setAlpha(alphaRate);
        //  hideBTN();
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
                if (Reconnect()) {
                    Toast.makeText(CURRENT_CONTEXT, "Bluetooth Re-connected Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "Bluetooth Re-connection Failed!, Please Try again", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_settings:
                Intent j = new Intent(Operations_GetSet_Activity.this, SettingsActivity.class);
                startActivityForResult(j, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public boolean Reconnect() {
        try {
            if (btSocket != null) {
                if (btSocket.isConnected()) {
                    btSocket.close();
                }
            }
            myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
            BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
            btSocket = dispositivo.createRfcommSocketToServiceRecord(Utils.MyUUID);//create a RFCOMM (SPP) connection
            myBluetooth.cancelDiscovery();
            btSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void ChooseDate() {
        try {
            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);
            datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
            datePickerDialog.showYearPickerFirst(false);
            datePickerDialog.setMinDate(calendar);
            //datePickerDialog.setMaxDate(calendar);
            datePickerDialog.setThemeDark(false);
            datePickerDialog.setAccentColor(Color.parseColor("#2b7ab9"));
            datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        try {
            String mMonthnameCommon = "";
            String mMonthname = "";
            int myear = year;
            int mmonth = monthOfYear + 1;
            int mday = dayOfMonth;
            String YEAR = String.valueOf(myear);
            String mmMotnthNum = "";

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
            String strDate = format.format(calendar.getTime());

            if(mDateSelectionCheck == 1)
            {
                txtdate.setText(strDate);
            }
            else if(mDateSelectionCheck == 2)
            {
                editdate.setText(strDate);
            }


        }catch (Exception e)
        {e.printStackTrace();}

    }

    private void chooseTimePicker() {
        try {
            Calendar now = Calendar.getInstance();
            int mmTime = 0;
            int mmMinut = 0;
            int mmSecond = 0;

            com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);/////////
            tpd.setAccentColor(Color.parseColor("#2b7ab9"));
            tpd.setMinTime(mmTime, mmMinut, mmSecond);

            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Log.d("TimePicker", "Dialog was cancelled");
                }
            });
            tpd.show(getFragmentManager(), "Timepickerdialog");


        } catch (Exception e) {
            e.printStackTrace();
        }



    }



    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        try {

            //String eventTime = hourString + ":" + minuteString;
            String selectedTime = hourOfDay + ":" + minute;
            edittime.setText(selectedTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForFirstActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private String condition;
        private String override = null;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            condition = requests[2];
            if (requests.length > 3) {
                override = requests[3];
            }
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
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(Utils.MyUUID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection
                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    btSocket.getOutputStream().write(STARTRequest);
                    byte[] readBytes = new byte[50];
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    final InputStream iStream = btSocket.getInputStream();
                    int retryCount = 0;
                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (RetryCount != 0 && RetryCount > maxRetryCount) maxRetryCount = RetryCount;
                    int available;

                    while ((available = iStream.available()) == 0 && retryCount < maxRetryCount) {
                        retryCount++;
                        Thread.sleep(Utils.RETRY_WAIT_TIME);
                    }

                    if (available <= 0) return false;

                    int bytesRead = iStream.read(readBytes);
                    if (bytesRead > 2) {
                        String str = new String(Utils.Trim(readBytes), StandardCharsets.US_ASCII);

                       // Toast.makeText(CURRENT_CONTEXT, "str>>>  "+str, Toast.LENGTH_SHORT).show();
                        if (str.contains(requests[1])) {
                            if (override == null) {
                                response = requests[1];
                                return true;
                            }
                            response = str.substring(str.indexOf(requests[1]), str.indexOf("#") + 1);
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
            } catch (Exception e) {
                btSocket = null;
                return false;
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            Date currentTime = Calendar.getInstance().getTime();
            Resources resources = getResources();
            switch (condition) {
                case "START":
                    if (result) {
                       /* edittxtstart.setText(response);

                    edittxtstart.setTextColor(resources.getColor(R.color.colorSucessGreen));
                       txtstart.setTextColor(resources.getColor(R.color.colorSucessGreen));*/

                        txtStartStopResultID.setText(response);
                        txtStartStopResultID.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtStartStopResultDateTimeID.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                      /*  edittxtstart.setText(R.string.ERROR);
                        edittxtstart.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtstart.setTextColor(resources.getColor(R.color.colorErrorRed));*/


                        txtStartStopResultID.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtStartStopResultDateTimeID.setTextColor(resources.getColor(R.color.colorErrorRed));

                        txtStartStopResultID.setText(R.string.ERROR);

                    }
                    txtStartStopResultDateTimeID.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    //txtstart.setText(DateFormat.getDateTimeInstance().format(currentTime));

                    /*saveName();
                    displayName();*/

                    break;
                case "STOP":
                    if (result) {
                        /*Edittextstop.setText(response);
                        Edittextstop.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        textstop.setTextColor(resources.getColor(R.color.colorSucessGreen));*/

                        txtStartStopResultID.setText(response);
                        txtStartStopResultID.setTextColor(resources.getColor(R.color.black));
                        txtStartStopResultDateTimeID.setTextColor(resources.getColor(R.color.black));

                        changeButtonVisibilityRLV(false, 0.5f,imgStartBTNID);
                     //   rlvTimerCounterViewID.setVisibility(View.VISIBLE);
                        startTimer(30000);

                    } else {
                      /*  Edittextstop.setText(R.string.ERROR);
                        Edittextstop.setTextColor(resources.getColor(R.color.colorErrorRed));
                        textstop.setTextColor(resources.getColor(R.color.colorErrorRed));*/

                        txtStartStopResultID.setText(R.string.ERROR);
                        txtStartStopResultID.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtStartStopResultDateTimeID.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                   // textstop.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    txtStartStopResultDateTimeID.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "RESET":
                    if (result) {
                        edittxtreset.setText(response);
                        edittxtreset.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtreset.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        edittxtreset.setText(R.string.ERROR);
                        txtreset.setTextColor(resources.getColor(R.color.colorErrorRed));
                        edittxtreset.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtreset.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "SETID":
                    if (result) {
                        txterrorinsetid.setText(response);
                        txterrorinsetid.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtidstatus.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txterrorinsetid.setText(R.string.ERROR);

                        txterrorinsetid.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtidstatus.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtidstatus.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "GETID":
                    if (result) {
                        editgetid.setText(response);
                        editgetid.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtgetidtime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        editgetid.setText(R.string.ERROR);
                        editgetid.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtgetidtime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtgetidtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "CLEARDATA":
                    if (result) {
                        editclearenergy.setText(response);
                        editclearenergy.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtclearenergydata.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        editclearenergy.setText(R.string.ERROR);
                        editclearenergy.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtclearenergydata.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtclearenergydata.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "CLEARLOG":
                    if (result) {
                        editclearlog.setText(response);
                        editclearlog.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtclearlog.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        editclearlog.setText(R.string.ERROR);
                        editclearlog.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtclearlog.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtclearlog.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "SETTIME":
                    if (result) {
                        txtgetimeerror.setText(response);
                        txtgetimeerror.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtgetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtgetimeerror.setText(R.string.ERROR);
                        txtgetimeerror.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtgetime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtgetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "SETDATE":
                    if (result) {
                        txtgetdateerror.setText(response);
                        txtgetdateerror.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtgedatetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtgetdateerror.setText(R.string.ERROR);
                        txtgetdateerror.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtgedatetime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtgedatetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "CALIBRATE":
                    if (result) {
                        txtcalibration.setText(response);
                        txtcalibration.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtcallibrationtime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtcalibration.setText(R.string.ERROR);
                        txtcalibration.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtcallibrationtime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtcallibrationtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "AUTOSET":
                    if (result) {
                        edittxtautoset.setText(response);
                        edittxtautoset.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtautoset.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        edittxtautoset.setText(R.string.ERROR);
                        edittxtautoset.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtautoset.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtautoset.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
            }
            Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
        }
    }

//    @SuppressLint("StaticFieldLeak")
//    private class BluetoothCommunicationForFirstActivityWithByteArray extends AsyncTask<byte[], Void, Boolean>  // UI thread
//    {
//        private String response;
//        private byte[] condition;
//        private byte[] override = null;
//
//        @Override
//        protected Boolean doInBackground(byte[]... requests) //while the progress dialog is shown, the connection is done in background
//        {
//            condition = requests[2];
//            if (requests.length > 3) {
//                override = requests[3];
//            }
//            try {
//                if (btSocket == null) {
//                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
//                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createRfcommSocketToServiceRecord(Utils.MyUUID);//create a RFCOMM (SPP) connection
//                    myBluetooth.cancelDiscovery();
//                }
//                if (!btSocket.isConnected()) btSocket.connect();//start connection
//                if (btSocket.isConnected()) {
//                    byte[] STARTRequest = requests[0];
//                    try {
//                        sleep(10);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    btSocket.getOutputStream().write(STARTRequest);
//                    byte[] readBytes = new byte[50];
//                    try {
//                        sleep(200);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//
//                    InputStream iStream = btSocket.getInputStream();
//                    int retryCount = 0;
//                    int maxRetryCount = 1000; // leads to a timeout of 10 seconds
//                    int available;
//
//                    while ((available = iStream.available()) == 0 && retryCount < maxRetryCount) {
//                        retryCount++;
//                        sleep(10);
//                    }
//
//                    if (available <= 0) return false;
//
//                    int bytesRead = iStream.read(readBytes);
//                    if (bytesRead > 2) {
//                        String str = new String(Utils.Trim(readBytes), StandardCharsets.US_ASCII);
//                        String expectedResponse = Utils.BytesToString(requests[1]);
//                        if (str.contains(expectedResponse)) {
//                            if (override == null) {
//                                response = expectedResponse;
//                                return true;
//                            }
//                            response = str.substring(str.indexOf(expectedResponse), str.indexOf("#") + 1);
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                    return false;
//                }
//            } catch (Exception e) {
//                btSocket = null;
//                return false;
//            }
//            return false;
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Override
//        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
//        {
//            super.onPostExecute(result);
//            Date currentTime = Calendar.getInstance().getTime();
//            String conditionAString = Utils.BytesToString(condition);
//            switch (conditionAString) {
//                case "CALIBRATE":
//                    if (result) {
//                        txtcalibration.setText(response);
//                        txtcalibration.setTextColor(resources.getColor(R.color.colorSucessGreen));
//                        txtcallibrationtime.setTextColor(resources.getColor(R.color.colorSucessGreen));
//                    } else {
//                        txtcalibration.setText(R.string.ERROR);
//                        txtcalibration.setTextColor(resources.getColor(R.color.colorErrorRed));
//                        txtcallibrationtime.setTextColor(resources.getColor(R.color.colorErrorRed));
//                    }
//                    txtcallibrationtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
//                    break;
//            }
//            Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
//        }
//    }
}