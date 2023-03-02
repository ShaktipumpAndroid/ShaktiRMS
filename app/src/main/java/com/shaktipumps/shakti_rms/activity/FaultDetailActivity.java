package com.shaktipumps.shakti_rms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.FaultDetail;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.searchlist.FaultDetailListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class FaultDetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context ;
    int loading=0;
    SharedPreferences pref ;
    FaultDetailListViewAdapter adapter;
    ArrayList<FaultDetail> arraylist ;
    ListView list;
    EditText editsearch;
    String DeviceType = "null", FaultBit = "null",faultDate="null",deviceNo="null",timezone = "null";
    TextView device_number,fault_date;
    FaultDetail faultDetail ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_detail);


        FaultDetailActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.fault_detail);



        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        list = (ListView) findViewById(R.id.listview);
        device_number = (TextView) findViewById(R.id.device_number);
        fault_date = (TextView) findViewById(R.id.fault_date);


        arraylist = new ArrayList<FaultDetail>();


        Bundle bundle = getIntent().getExtras();
        DeviceType= bundle.getString("DeviceType");
        FaultBit = bundle.getString("FaultBit");
        deviceNo = bundle.getString("deviceNo");
        faultDate = bundle.getString("faultDate");


        fault_date.setText( faultDate);
        device_number.setText( deviceNo );


        progressDialog = new ProgressDialog(FaultDetailActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);



//        new Thread(new Runnable() {
//            public void run() {
//
//                try {
//
//                    while(loading == 0 )
//                    {
//
//                        Thread.sleep(1000);
//
//                    }
//
//                    progressDialog.dismiss();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//


       // get_fault_detail();

        new Worker().execute();

//        adapter = new FaultDetailListViewAdapter(context,arraylist);
//
//        list.setAdapter(adapter);
//
//
//
//        // Locate the EditText in listview_main.xml
//        editsearch = (EditText) findViewById(R.id.search);
//
//        // Capture Text in EditText
//        editsearch.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
//                adapter.filter(text);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1,
//                                          int arg2, int arg3) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//                                      int arg3) {
//                // TODO Auto-generated method stub
//            }
//        });




    }

    public void  get_fault_detail() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);


        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        if (CustomUtility.isOnline(context)) {

            try {
                 timezone = pref.getString("key_time_zone_city","null") ;

                String URL = NewSolarVFD.FAULT_RECORD + "?FaultBit=" + FaultBit + "&DeviceType=" + DeviceType + "&TimeZones=" + timezone;

                String obj = CustomHttpClient.executeHttpGet(URL);


              //  Log.d("data_fault_details", "" + obj );

                if (obj != null) {
                    JSONArray ja = new JSONArray(obj);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        faultDetail = new FaultDetail();

                        faultDetail.setFaultName(jo.getString("FaultName"));
                        arraylist.add(faultDetail);
                    }


                    loading = 1;
                } else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));


                }


            } catch (Exception e) {
                progressDialog.dismiss();
                CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                Log.d("exce", "" + e);
            }
        }
            else
            {

                progressDialog.dismiss();

                CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_internet));


            }
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
        }
        return super.onOptionsItemSelected(item);
    }

    private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;

            try {

                get_fault_detail();

                runOnUiThread(


                        new Runnable() {

                            @Override
                            public void run()

                            {

        adapter = new FaultDetailListViewAdapter(context,arraylist);

        list.setAdapter(adapter);



        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


                            }
                        });



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
}
