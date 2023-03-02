package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.adapter.ExpandableListAdapter;
import com.shaktipumps.shakti_rms.bean.FaultSummary;
import com.shaktipumps.shakti_rms.bean.Org_Client;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.searchlist.FaultListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FaultReportActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context ;
    int loading=0;
    SharedPreferences pref ;
    FaultListViewAdapter adapter;
    ArrayList<FaultSummary> arraylist ;
    ListView list;
    EditText editsearch,editText_from_date,editText_to_date;
    DatePickerDialog datePickerDialog;
    FaultSummary faultSummary ;
    TextView btn_get_detail ;
    String from_date = "",to_date= "";
    private TextInputLayout input_layout_to_date,input_layout_from_date;

    int clientid = 0 ,ParentId = 0,SupParentId = 0,first_level = 0,SupClientId = 0;
    TextView previous_level ;
;
    String clientName="null" ;

    ExpandableListAdapter  orglistAdapter;
    ExpandableListView orgListView;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> topChild ;
    ArrayList<Org_Client> clientArray ;
    Org_Client  org_client;

    Uri u1  =   null;
    String excelHeader = "",excelData = "" ,CustomerName="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_report);

        FaultReportActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        orgListView = (ExpandableListView) findViewById(R.id.orglistview);
        previous_level = (TextView) findViewById(R.id.previous_level);

        clientArray  = new ArrayList<Org_Client>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        topChild = new ArrayList<String>();


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.fault_report);


//        editText_from_date = (EditText) findViewById(R.id.editText_from_date);
//        editText_to_date = (EditText) findViewById(R.id.editText_to_date);
//        btn_get_detail= (TextView) findViewById(R.id.btn_get_detail);
//
//        input_layout_to_date = (TextInputLayout) findViewById(R.id.input_layout_to_date);
//        input_layout_from_date = (TextInputLayout) findViewById(R.id.input_layout_from_date);


        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


        clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ; // if invalid use 0

        first_level =  clientid ;


        list = (ListView) findViewById(R.id.listview);

        arraylist = new ArrayList<FaultSummary>();





        progressDialog = new ProgressDialog(FaultReportActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        new Worker().execute();





//*****************************  organisation login ******************
        previous_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SupClientId != first_level)
                {

                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setTitle("Please wait..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);


                    clientid = SupParentId;
                    new Worker().execute();

                }
                else
                {
                    previous_level.setVisibility(View.INVISIBLE);
                }

            }
        });



//*****************************  organisation login ******************

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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


        param.add(new BasicNameValuePair("MUserId", pref.getString("key_muserid", "invalid_muserid")));
//        param.add(new BasicNameValuePair("EndDate", to_date));
//        param.add(new BasicNameValuePair("StartDate", from_date));

        param.add(new BasicNameValuePair("ClientId", Integer.toString(clientid)));




        if (CustomUtility.isOnline(context))
        {

        try {
            String  obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.FAULT_RECORD, param);

//            Log.d("data_fault1", "" + obj +  pref.getString("key_muserid", "invalid_muserid")
//                             +to_date+"--"+from_date+"--"+clientid
//                                );


          //  Log.d("data_fault", "" + obj );


            if (obj != null)
            {
                progressDialog.dismiss();

                if  (  clientid == 0 ) // single user  data
                {


                   // excelHeader =  "\"" + "DeviceNo" + "\"" + ","  ;
                    excelHeader =   "\""  + "Device No"   + "\"" +  "," +
                                    "\""  + "Date"        + "\"" +  ","+
                                    "\""  + "Falut Count" + "\"" +  "," +
                                    "\""  + "Fault Name"  + "\"" +  "\n" ;


                    JSONArray ja = new JSONArray(obj);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        faultSummary = new FaultSummary();
                        faultSummary.setDeviceNo(jo.getString("DeviceNo"));
                        faultSummary.setFalutCount(jo.getString("FalutCount"));
                        faultSummary.setDate(jo.getString("Date"));
                        faultSummary.setFaultBit(jo.getString("FaultBit"));
                        faultSummary.setDeviceType(jo.getString("DeviceType"));

                        arraylist.add(faultSummary);


                        excelData  =  excelData +  "\""  + jo.getString("DeviceNo")   + "\""  +  "," +
                                        "\"" + jo.getString("Date")       + "\""  +  "," +
                                        "\"" + jo.getString("FalutCount") + "\""  +  "," +
                                        "\"" + jo.getString("FaultBit")   + "\""  +  "\n" ;


                    }
                }

                else   //  organisation data
                {

                    JSONObject jsonObj = new JSONObject(obj);
                    JSONArray ja = jsonObj.getJSONArray("Client");


                    arraylist.clear();
                    listDataHeader.clear();
                    topChild.clear();
                    listDataChild.clear();



                    if (ja.length() > 0)
                    {





                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jo = ja.getJSONObject(i);


                            org_client = new Org_Client();
                            org_client.setClientId(jo.getString("ClientId"));
                            org_client.setClientName(jo.has("ClientName")?jo.getString("ClientName"):"" );
                            org_client.setParentId(jo.getString("ParentId"));
                            org_client.setParentName(jo.getString("ParentId"));

                            clientArray.add(i, org_client);


                            if (i == 0)
                            {
                                listDataHeader.add(jo.has("ParentName")?jo.getString("ParentName"):"");
                            }

                            topChild.add(jo.has("ClientName")?jo.getString("ClientName"):"");
                        }

                    }


                    if (ja.length() == 0)
                    {
                        listDataHeader.add(clientName );

                    }

                    listDataChild.put(listDataHeader.get(0), topChild); // Header, Child data
                    orglistAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);




                    excelHeader =   "\""  + "Device No"   + "\"" +  "," +
                            "\""  + "Date"        + "\"" +  ","+
                            "\""  + "Falut Count" + "\"" +  "," +
                            "\""  + "Fault Name"  + "\"" +  "\n" ;



                    JSONArray ja_device = jsonObj.getJSONArray("Fault");
                    for (int i = 0; i < ja_device.length(); i++)
                    {
                        JSONObject jo = ja_device.getJSONObject(i);
                        faultSummary = new FaultSummary();
                        faultSummary.setDeviceNo(jo.getString("DeviceNo"));
                        faultSummary.setFalutCount(jo.getString("FalutCount"));
                        faultSummary.setDate(jo.getString("Date"));
                        faultSummary.setFaultBit(jo.getString("FaultBit"));
                        faultSummary.setDeviceType(jo.getString("DeviceType"));
                        arraylist.add(faultSummary);



                        excelData  =  excelData +  "\""  + jo.getString("DeviceNo")   + "\""  +  "," +
                                "\"" + jo.getString("Date")       + "\""  +  "," +
                                "\"" + jo.getString("FalutCount") + "\""  +  "," +
                                "\"" + jo.getString("FaultBit")   + "\""  +  "\n" ;


                    }








                    JSONArray ja_pclient = jsonObj.getJSONArray("PClient");
                    for (int i = 0; i < ja_pclient.length(); i++)
                    {

                        JSONObject jo = ja_pclient.getJSONObject(i);
                        SupParentId =  Integer.parseInt(jo.getString("SupParentId"));
                        SupClientId=  Integer.parseInt(jo.getString("ClientId"));

                    }

                }




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

        else

        {
            progressDialog.dismiss();
            CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_internet));
        }

    }
    private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;

            try {

                get_device();

                runOnUiThread(


                        new Runnable() {

                            @Override
                            public void run()

                            {


                                adapter = new FaultListViewAdapter(context,arraylist);

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


    //********************* organization selection ***********************************



                                orgListView.setAdapter(orglistAdapter);

                                // Listview Group click listener
                                orgListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                    @Override
                                    public boolean onGroupClick(ExpandableListView parent, View v,
                                                                int groupPosition, long id) {
                                        // Toast.makeText(getApplicationContext(),
                                        // "Group Clicked " + listDataHeader.get(groupPosition),
                                        // Toast.LENGTH_SHORT).show();




                                        return false;
                                    }
                                });

                                // Listview Group expanded listener
                                orgListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                                    @Override
                                    public void onGroupExpand(int groupPosition) {
//                                        Toast.makeText(context,
//                                                listDataHeader.get(groupPosition) + " Expanded",
//                                                Toast.LENGTH_SHORT).show();


                                        if(SupClientId == first_level) {
                                            previous_level.setVisibility(View.INVISIBLE);
                                        }
                                        else
                                        {
                                            previous_level.setVisibility(View.VISIBLE);
                                        }




                                    }
                                });

                                // Listview Group collasped listener
                                orgListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                                    @Override
                                    public void onGroupCollapse(int groupPosition) {

//                                        Toast.makeText(context,
//                                                listDataHeader.get(groupPosition) + " Collapsed",
//                                                Toast.LENGTH_SHORT).show();

//
//                                        if(SupClientId == first_level) {
//                                            previous_level.setVisibility(View.INVISIBLE);
//                                        }
//                                        else
//                                        {
//                                            previous_level.setVisibility(View.VISIBLE);
//                                        }
                                    }
                                });

                                // Listview on child click listener
                                orgListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v,
                                                                int groupPosition, int childPosition, long id) {
                                        // TODO Auto-generated method stub



                                        clientid = Integer.parseInt(clientArray.get(childPosition).getClientId()) ;
                                        clientName = clientArray.get(childPosition).getClientName();


//
//
//                                        Toast.makeText(
//                                                context,
//                                                listDataHeader.get(groupPosition)
//                                                        + " : "
//                                                        + listDataChild.get(
//                                                        listDataHeader.get(groupPosition)).get(
//                                                        childPosition)+  clientArray.get(childPosition).getClientId(), Toast.LENGTH_SHORT)
//                                                .show();


                                        if(SupClientId == first_level) {
                                            previous_level.setVisibility(View.INVISIBLE);
                                        }
                                        else
                                        {
                                            previous_level.setVisibility(View.VISIBLE);
                                        }

//
//                                        Intent intent  = new Intent(context, MainActivity.class);
//
//                                        startActivity(intent);
//


                                        progressDialog = new ProgressDialog(context);
                                        progressDialog.setMessage("Loading..."); // Setting Message
                                        progressDialog.setTitle("Please wait..."); // Setting Title
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                        progressDialog.show(); // Display Progress Dialog
                                        progressDialog.setCancelable(false);



                                        new Worker().execute();



                                        if(SupClientId == first_level) {
                                            previous_level.setVisibility(View.INVISIBLE);
                                        }
                                        else
                                        {
                                            previous_level.setVisibility(View.VISIBLE);
                                        }


                                        return false;
                                    }
                                });



//
//                                if(SupClientId != first_level) {
//                                    previous_level.setVisibility(View.GONE);
//
//                                }
////

                                //********************* organization selection ***********************************



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


    public void share_file() {


        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            //String combinedString = columnString + "\n" + dataString;

            String customer_name;


          //  customer_name = "\"" + "Customer Name :" + "\"," + "\"" + CustomerName + "\"";  // set customer name for excel

          //  String combinedString = "\n" + customer_name + "\n" + "\n" + excelHeader + "\n" + excelData;
            String combinedString =  "\n" + "\n" + excelHeader + "\n" + excelData;

            File file = null;
            File root = Environment.getExternalStorageDirectory();
            if (root.canWrite()) {
                File dir = new File(root.getAbsolutePath() + "/Shakti_RMS/Fault_Report");
                dir.mkdirs();

                file = new File(dir, "Fault_Report_"+String.valueOf(System.currentTimeMillis())+".csv");
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
}
