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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.adapter.ExpandableListAdapter;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.Org_Client;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.searchlist.DataReportListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DataReportActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog progressDialog;
    Context context ;
    String from_date = "",to_date= "",clientName="null";
    Customer_GPS_Search customer_gps ;
    ArrayList<Customer_GPS_Search> arraylist ;
    ListView list;
    EditText editsearch;
    DataReportListViewAdapter   adapter;
    int loading=0;
    SharedPreferences pref ;

    ExpandableListAdapter orglistAdapter;
    ExpandableListView orgListView;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> topChild ;
    ArrayList<Org_Client> clientArray ;
    Org_Client  org_client;

    TextView previous_level ;
    int clientid = 0 ,ParentId = 0,SupParentId = 0,first_level = 0,SupClientId = 0;

    SharedPreferences.Editor editor;

    String url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_report);


        DataReportActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        getSupportActionBar().setTitle(R.string.action_data_report);


        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);



        editor = pref.edit();



        list = (ListView)findViewById(R.id.listview);

        arraylist = new ArrayList<Customer_GPS_Search>();


        clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ; // if invalid use 0

        first_level =  clientid ;



        progressDialog = new ProgressDialog(DataReportActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);



        //get_device();

        new Worker().execute();

//*****************************  organisation login ******************
        previous_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SupClientId != first_level)
                {


                    //   Log.d("user",""+SupClientId+"---"+first_level);


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


        if (CustomUtility.isOnline(context))
        {

            try {

              //  String url = NewSolarVFD.GET_DEVICE + "?MUserId=" + pref.getString("key_muserid", "invalid_muserid") + "&isActive=" + 1;

                if (clientid == 0)
                {
                    // single user login
                    url  = NewSolarVFD.GET_DEVICE +"?MUserId="+  pref.getString("key_muserid", "invalid_muserid")+
                            "&isActive="+ 1 ;

                }
                else
                {
                    // organisation login
                    url  = NewSolarVFD.ORG_GET_DEVICE  +"?MUserId="+  pref.getString("key_muserid", "invalid_muserid")+
                            "&ClientId="+ clientid ;

                }


                String obj = CustomHttpClient.executeHttpGet(url);


                  Log.d("data_response", "" + obj );
                  Log.d("data_response1", "" + url );


                if (obj != null) {


                    progressDialog.dismiss();




                    if  (  clientid == 0 ) // single user  data

                    {

                    JSONArray ja = new JSONArray(obj);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        customer_gps = new Customer_GPS_Search();
                        customer_gps.setCustomer_name( jo.has("CustomerName")?jo.getString("CustomerName"):"" );
                        customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                        customer_gps.setDeviceType(jo.getString("DeviceType"));

                        customer_gps.setMUserId(jo.getString("MUserId"));
                        customer_gps.setMobile(jo.getString("Mobile"));
                        customer_gps.setMDeviceId(jo.getString("MDeviceId"));

                        arraylist.add(customer_gps);

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


                        JSONArray ja_device = jsonObj.getJSONArray("Device");
                        for (int i = 0; i < ja_device.length(); i++)
                        {

                            JSONObject jo = ja_device.getJSONObject(i);

                            customer_gps = new Customer_GPS_Search();
                            customer_gps.setCustomer_name(jo.has("CustomerName")?jo.getString("CustomerName"):"");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType")) ;
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(jo.getString("MUserId"));
                            customer_gps.setMobile(jo.has("Mobile")?jo.getString("Mobile"):"");
                            arraylist.add(customer_gps);
                        }


                        JSONArray ja_pclient = jsonObj.getJSONArray("PClient");
                        for (int i = 0; i < ja_pclient.length(); i++)
                        {
                            JSONObject jo = ja_pclient.getJSONObject(i);
                            SupParentId =  Integer.parseInt(jo.getString("SupParentId"));
                            SupClientId=  Integer.parseInt(jo.getString("ClientId"));

                        }
                    }

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
            CustomUtility.isErrorDialog(DataReportActivity.this, getString(R.string.error), getString(R.string.err_internet));

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

                adapter = new   DataReportListViewAdapter(context,arraylist);

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


                              //set selected client id for org , data report detail list
                              editor.putString("key_clientid_for_data_report", String.valueOf(clientid));
                              editor.commit();
                              //set selected client id for org , data report detail list



                              if(SupClientId == first_level) {
                                  previous_level.setVisibility(View.INVISIBLE);
                              }
                              else
                              {
                                  previous_level.setVisibility(View.VISIBLE);
                              }




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
}
