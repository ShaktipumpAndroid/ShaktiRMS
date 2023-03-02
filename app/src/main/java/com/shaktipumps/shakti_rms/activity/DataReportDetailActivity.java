package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class DataReportDetailActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context ;
    DatePickerDialog datePickerDialog;
    private TextInputLayout input_layout_to_date,input_layout_from_date;
    EditText editsearch,editText_from_date,editText_to_date;
    String DeviceNo = "null" ,DeviceType = "null",
            MUserId = "null"  ,MDeviceId = "null",CustomerName="null",ClientId = "null";

    TextView btn_get_detail,tv_customer_name, tv_device_number ;
    int loading=0;
    SharedPreferences pref ;
    TableRow row ;
    CheckBox cb_periodically ;
    String from_date = "",to_date= "" , periodically = "0",timezone = "null" ;
    String obj ;

    String excelHeader = "",excelData = "" ;

    Uri u1  =   null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_report_detail);



        DataReportDetailActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);



        editText_from_date = (EditText) findViewById(R.id.editText_from_date);
        editText_to_date = (EditText) findViewById(R.id.editText_to_date);
        btn_get_detail= (TextView) findViewById(R.id.btn_get_detail);

        tv_customer_name = (TextView) findViewById(R.id.tv_customer_name);
//        tv_device_number = (TextView) findViewById(R.id.tv_device_number);




        input_layout_to_date = (TextInputLayout) findViewById(R.id.input_layout_to_date);
        input_layout_from_date = (TextInputLayout) findViewById(R.id.input_layout_from_date);
        //cb_periodically = (CheckBox) findViewById(R.id.cb_periodically);




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_data_report);


        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        MUserId = bundle.getString("MUserId");
        DeviceType = bundle.getString("DeviceType");
        DeviceNo = bundle.getString("DeviceNo");
        CustomerName = bundle.getString("CustomerName");
        from_date = bundle.getString("from_date");
        to_date   = bundle.getString("to_date");
        periodically =  bundle.getString("periodically") ;
        ClientId = bundle.getString("ClientId");


        tv_customer_name.setText(CustomerName);

       // tv_device_number.setText(DeviceNo);


        progressDialog = new ProgressDialog(DataReportDetailActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


      //  Log.d("detail",MUserId);




        new Worker().execute();





//***************************** date from ***************************************************
        editText_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // calender class's instance and get current date , month and year from calender
                final Calendar cal_from = Calendar.getInstance();
                int mYear = cal_from.get(Calendar.YEAR); // current year
                int mMonth = cal_from.get(Calendar.MONTH); // current month
                int mDay = cal_from.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(DataReportDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
//                                editText_from_date.setText(dayOfMonth + "."
//                                        + ( monthOfYear + 1) + "." + year);

                                editText_from_date.setText(( monthOfYear + 1)  + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() );
                }


                datePickerDialog.show();
            }
        });




//***************************** date TO ***************************************************
        editText_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(DataReportDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
//                                editText_to_date.setText(dayOfMonth + "."
//                                        + ( monthOfYear + 1) + "." + year);

                                editText_to_date.setText(( monthOfYear + 1)  + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() );
                }
                datePickerDialog.show();
            }
        });





    // get details
        btn_get_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitForm()) {





                    //    Log.d("chk1",""+cb_periodically.isChecked() )     ;

//                    if (  cb_periodically.isChecked()  )
//                    {
//                        periodically = "1" ;
//                    }
//                    else
//                    {
//                        periodically = "0" ;
//                    }



                    periodically = "0" ;


                    from_date = editText_from_date.getText().toString();
                    to_date = editText_to_date.getText().toString();

                    DataReportDetailActivity.this.finish();
                    Intent intent = new Intent(    context , DataReportDetailActivity.class);

                    intent.putExtra("MUserId",MUserId );
                    intent.putExtra("DeviceType",DeviceType );
                    intent.putExtra("DeviceNo",DeviceNo );
                    intent.putExtra("CustomerName",CustomerName );
                    intent.putExtra("from_date",from_date  );
                    intent.putExtra("to_date",   to_date);
                    intent.putExtra("periodically", periodically);
                    intent.putExtra("ClientId", ClientId);

                    context .startActivity(intent);

                }
            }
        });










    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                onBackPressed();

                return true;



            case R.id.action_share_file:


                //Check if permission is granted or not

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    check_Permission(this);
                }
                else
                {
                    share_file();

                }

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void  get_device() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);


        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();


        if (CustomUtility.isOnline(context))
        {

        try {

            timezone = pref.getString("key_time_zone_city","null") ;

            String url = NewSolarVFD.DATA_REPORT + "?MUserId=" + MUserId
                    + "&StartDate=" + from_date
                    + "&EndDate=" + to_date
                    + "&DeviceNo=" + DeviceNo +
                    "&DeviceType=" + DeviceType +
                    "&ClientId=" + ClientId +
                    "&Periodically=" + periodically +
                    "&TimeZones=" + timezone ;

              Log.d("data_report_detail", "" +  url);

            obj = CustomHttpClient.executeHttpGet(url);

           //   Log.d("data_report", "" + obj);

            if (obj != null) {


                runOnUiThread(
                        new Runnable() {

                            @Override
                            public void run()

                            {


                                try {
                                    progressDialog.dismiss();

                                    TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout);


                                    // Add header row

                                    TableRow rowHeader = new TableRow(context);
                                    rowHeader.setBackgroundColor(Color.parseColor("#8b8b8c"));

                                    rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                            TableLayout.LayoutParams.WRAP_CONTENT));


                                    JSONArray ja = new JSONArray(obj);
//


                                    int data = 0;

                                    for (int i = 0; i < ja.length(); i++) {

                                        if (i > 0) {
                                            row = new TableRow(context);
                                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                                    TableLayout.LayoutParams.WRAP_CONTENT));

                                        }


                                        JSONObject jo = ja.getJSONObject(i);

                                        Iterator<String> keys = jo.keys();
                                        data = 0;
                                        while (keys.hasNext()) {

                                            String keyValue = (String) keys.next();
                                            String valueString = jo.getString(keyValue);
                                            data = data + 1;
                                            //      Log.d("data_report2", "" + valueString +"--"+ i);
                                            if (i == 0) {
                                                TextView tv = new TextView(context);
                                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                        TableRow.LayoutParams.WRAP_CONTENT));
                                                tv.setGravity(Gravity.CENTER);
                                                tv.setTextColor(Color.parseColor("#FFFFFF"));
                                                tv.setPadding(50, 5, 5, 5);
                                                tv.setTextSize(14);
                                                tv.setText(valueString);
                                                rowHeader.addView(tv);

            // ************************ set excel header ****************************************//
                                               //  \"Age\"
                                   excelHeader =  excelHeader +  "\"" + valueString + "\"" + ","  ;
           // ************************ set excel header ****************************************//
                                            }

                                            if (i > 0) {
                                                TextView tv = new TextView(context);
                                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                        TableRow.LayoutParams.WRAP_CONTENT));
                                                tv.setGravity(Gravity.CENTER);
                                                tv.setTextSize(14);
                                                // tv.setPadding(5, 5, 5, 5);
                                                tv.setPadding(40, 30, 10, 5);
                                                tv.setText(valueString);
                                                row.addView(tv);
            // ************************ set excel data ****************************************//
                                   excelData =  excelData +  "\"" + valueString + "\"" + ","  ;

           // ************************ set excel header ****************************************//
                                            }
                                        }
//  set header
                                      //  Log.d("excelHeader",excelHeader);
                                       // Log.d("excelData",excelData);

                                        if (i == 0) {
                                            tableLayout.addView(rowHeader);
                                        }
                                        if (i > 0) {
                                            // set row
                                            tableLayout.addView(row);
                                            //              draw seprator between rows
                                            View line = new View(context);
                                            line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1));
                                            line.setBackgroundColor(Color.parseColor("#8b8b8c"));
                                            tableLayout.addView(line);

                                            excelData = excelData +   "\n" ; // line break for excel
                                        }

                                    }

                                } catch (Exception e)

                                {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                                    Log.d("exce", "" + e);
                                }

                            }

        });
        } else {
                progressDialog.dismiss();
                CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
            }

        } catch (Exception e)
        {
            progressDialog.dismiss();
            CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
            Log.d("exce", "" + e);
        }
    }
        else
        {
            progressDialog.dismiss();
            CustomUtility.isErrorDialog(DataReportDetailActivity.this, getString(R.string.error), getString(R.string.err_internet));
        }
    }

    private boolean submitForm()
    {
        boolean value ;

        if ( ( editText_from_date.getText().toString().isEmpty() ))
        {

            input_layout_from_date.setError("Please Enter From Date");
            requestFocus(editText_from_date);
            return false;
        }
        else
        {
            input_layout_from_date.setErrorEnabled(false);
        }

        if ( ( editText_to_date.getText().toString().isEmpty() ))
        {

            input_layout_to_date.setError("Please Enter To Date");
            requestFocus(editText_to_date);
            return false;
        }
        else
        {
            input_layout_to_date.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            String data = null;
            try {

                get_device();

            } catch (Exception e) {
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.i("SomeTag", System.currentTimeMillis() / 1000L
//                    + " post execute \n" + result);
            progressDialog.dismiss();
        }
    }


    public void share_file() {


        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            //String combinedString = columnString + "\n" + dataString;

            String customer_name;

            customer_name = "\"" + "Customer Name :" + "\"," + "\"" + CustomerName + "\"";  // set customer name for excel

            String combinedString = "\n" + customer_name + "\n" + "\n" + excelHeader + "\n" + excelData;

            File file = null;
            File root = Environment.getExternalStorageDirectory();
            if (root.canWrite()) {
                File dir = new File(root.getAbsolutePath() + "/Shakti_RMS/Data_Report");
                dir.mkdirs();

                file = new File(dir, "Data_Report_"+String.valueOf(System.currentTimeMillis())+".csv");
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    out.write(combinedString.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                u1 = Uri.fromFile(file);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Shakti RMS AUTHModelData Report");

                sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                sendIntent.setType("text/html");
                startActivity(sendIntent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void check_Permission(final Context context)
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionsIntent.WRITE_EXTERNAL_STORAGE_PERMISSION);

        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionsIntent.WRITE_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    }
