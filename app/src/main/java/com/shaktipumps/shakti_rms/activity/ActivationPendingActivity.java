package com.shaktipumps.shakti_rms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.searchlist.ActivationPendingListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.NameValuePair;

public class ActivationPendingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context ;
    Customer_GPS_Search customer_gps ;
    ActivationPendingListViewAdapter adapter;
    ArrayList<Customer_GPS_Search> arraylist ;
    ListView list;
    EditText editsearch;
    int loading=0;
    SharedPreferences pref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_pending);

        ActivationPendingActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_activation_pending);



        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);



        list = (ListView) findViewById(R.id.listview);

        arraylist = new ArrayList<Customer_GPS_Search>();




        progressDialog = new ProgressDialog(ActivationPendingActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);



        new Thread(new Runnable() {
            public void run() {


                try {

                    while(loading == 0 )
                    {

                       Thread.sleep(1000);

                    }

                    progressDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();





        get_device();




        adapter = new ActivationPendingListViewAdapter(context,arraylist);

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
    public void  get_device() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        //if (CustomUtility.isOnline(context)) {

            try {

                String url = NewSolarVFD.GET_DEVICE +"?MUserId="+ pref.getString("key_muserid", "invalid_muserid") +"&isActive="+0 ;
                String obj = CustomHttpClient.executeHttpGet(url);
                   Log.d("data_activation", "" + obj);

                if (obj != null)
                {
                    JSONArray ja =new JSONArray(obj);

                    for (int i = 0; i < ja.length(); i++)
                    {
                        JSONObject jo = ja.getJSONObject(i);
                        customer_gps = new Customer_GPS_Search();
                        customer_gps.setCustomer_name(jo.getString("CustomerName"));
                        customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                        customer_gps.setDeviceType(jo.getString("DeviceType"));
                        customer_gps.setMUserId(jo.getString("MUserId"));
                        customer_gps.setMobile(jo.getString("Mobile"));
                        customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                        arraylist.add(customer_gps);

                    }

                   loading = 1 ;
                }
                else
                {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(context,getString(R.string.error),getString(R.string.err_connection));
                }

            } catch (Exception e) {
                progressDialog.dismiss();
                CustomUtility.isErrorDialog(context,getString(R.string.error),getString(R.string.err_connection));
                Log.d("exce", "" + e);
            }

        }

//        else {
//            progressDialog.dismiss();
//            CustomUtility.isErrorDialog(context,"Error","No Internet Connection");
//
//        }


}
