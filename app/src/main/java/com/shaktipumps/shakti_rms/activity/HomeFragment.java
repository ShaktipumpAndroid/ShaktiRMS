package com.shaktipumps.shakti_rms.activity;

/**
 * Created by shakti on 10/3/2016.
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.GRID.AddPlantAndDeviceOPtion;
import com.shaktipumps.shakti_rms.adapter.ExpandableListAdapter;
import com.shaktipumps.shakti_rms.adapter.GridPlantAdapter;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.GetPlant.GetPlantModelView;
import com.shaktipumps.shakti_rms.bean.GetPlant.GetPlantResponse;
import com.shaktipumps.shakti_rms.bean.HomeDeviceListBean.HomeDeviceListModel;
import com.shaktipumps.shakti_rms.bean.HomeDeviceListBean.HomeDeviceListResponse;
import com.shaktipumps.shakti_rms.bean.HomeOEMBean.Client;
import com.shaktipumps.shakti_rms.bean.HomeOEMBean.Device;
import com.shaktipumps.shakti_rms.bean.HomeOEMBean.HomeDeviceOEMModel;
import com.shaktipumps.shakti_rms.bean.HomeOEMBean.HomeDeviceOEResponse;
import com.shaktipumps.shakti_rms.bean.HomeOEMBean.PClient;
import com.shaktipumps.shakti_rms.bean.Org_Client;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.searchlist.CustomerGPSListViewAdapter;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.markushi.ui.CircleButton;


public class HomeFragment extends Fragment {

    private ProgressDialog progressDialog;
    private Context context;
    private Customer_GPS_Search customer_gps;
    private CustomerGPSListViewAdapter adapter;
    private ArrayList<Customer_GPS_Search> arraylist;
    private ListView list;
    private EditText editsearch;
    private LinearLayout app_version_layout;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // private TextView txtAddDeviceBTNID, txtAddDeviceMSGID;
    private TextView txtAddDeviceBTNID1, txtAddDeviceMSGID, txtAddDeviceMSGIDDD;
    // private ImageView txtAddDeviceBTNID;
    private CircleButton txtAddDeviceBTNID;
    private RelativeLayout rlvPlantMainContainerID, rlvMainContainerDeviceID;
    private LinearLayout lvlListViewDataContainerID;
    private String newVersion = "0.0", currentVersion = "0.0", MUserId = "null", clientName = "null", ParentName = "null";

    private int clientid = 0, ParentId = 0, SupParentId = 0, first_level = 0, SupClientId = 0;

    private ExpandableListAdapter orglistAdapter;
    private ExpandableListView orgListView;

    private boolean expand = false;
    private RelativeLayout org_layout;

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<String> topChild;
    private ArrayList<Org_Client> clientArray;
    private Org_Client org_client;
    private TextView previous_level;
    private DatabaseHelperTeacher databaseHelperTeacher;

    private Context mContext = null;

    private Activity mActivity;
    private RelativeLayout rlvDevicesViewID;
    private RelativeLayout rlvPlantViewID;

    private String mUserID;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private RecyclerView mMenuCardRecyclerView;
    private BaseRequest baseRequest;

    /////////////////vikas Code

    private RecyclerView.Adapter recyclerViewAdapter;

    private List<GetPlantResponse> mGetPlantResponse;

    HomeDeviceOEResponse mHomeDeviceOEResponse;
    List<Device> mDevice;
    List<PClient> mPClient;
    List<Client> mClient;


    List<HomeDeviceListResponse> mHomeDeviceListResponse;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this.getActivity();
        mContext = this.getActivity();
        mActivity = this.getActivity();

        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //   new Worker().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mGetPlantResponse = new ArrayList<>();
        mHomeDeviceListResponse = new ArrayList<>();
        mHomeDeviceOEResponse = new HomeDeviceOEResponse();
        mDevice = new ArrayList<>();
        mPClient = new ArrayList<>();
        mClient = new ArrayList<>();

        list = (ListView) rootView.findViewById(R.id.listview);
        baseRequest = new BaseRequest(mActivity);
        mMenuCardRecyclerView = (RecyclerView) rootView.findViewById(R.id.mMenuCardRecyclerView);
        mMenuCardRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        databaseHelperTeacher = new DatabaseHelperTeacher(getActivity());/////////////////AUTHModelData base
        arraylist = new ArrayList<Customer_GPS_Search>();
        // Locate the EditText in listview_main.xml

        rlvPlantMainContainerID = (RelativeLayout) rootView.findViewById(R.id.rlvPlantMainContainerID);
        rlvMainContainerDeviceID = (RelativeLayout) rootView.findViewById(R.id.rlvMainContainerDeviceID);
        rlvDevicesViewID = (RelativeLayout) rootView.findViewById(R.id.rlvDevicesViewID);
        rlvPlantViewID = (RelativeLayout) rootView.findViewById(R.id.rlvPlantViewID);

        editsearch = (EditText) rootView.findViewById(R.id.search);
        app_version_layout = (LinearLayout) rootView.findViewById(R.id.app_version_layout);
        orgListView = (ExpandableListView) rootView.findViewById(R.id.orglistview);

        org_layout = (RelativeLayout) rootView.findViewById(R.id.org_layout);

        txtAddDeviceMSGID = (TextView) rootView.findViewById(R.id.txtAddDeviceMSGID);
        txtAddDeviceMSGIDDD = (TextView) rootView.findViewById(R.id.txtAddDeviceMSGIDDD);
      //  txtAddDeviceBTNID = (CircleButton) rootView.findViewById(R.id.txtAddDeviceBTNID);
        //txtAddDeviceBTNID = (ImageView) rootView.findViewById(R.id.txtAddDeviceBTNID);
        txtAddDeviceBTNID = (CircleButton) rootView.findViewById(R.id.txtAddDeviceBTNID);
       checkAndRequestPermissions();

        rlvPlantViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlvPlantViewID.setBackgroundResource(R.drawable.right_round_selected);
                rlvDevicesViewID.setBackgroundResource(R.drawable.left_round_unselected);

                list.setVisibility(View.GONE);

                mMenuCardRecyclerView.setVisibility(View.VISIBLE);

                rlvMainContainerDeviceID.setVisibility(View.GONE);
                rlvPlantMainContainerID.setVisibility(View.VISIBLE);
            }
        });


        rlvDevicesViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlvPlantViewID.setBackgroundResource(R.drawable.right_round_unselected);
                rlvDevicesViewID.setBackgroundResource(R.drawable.left_round_selected);

                list.setVisibility(View.VISIBLE);
                mMenuCardRecyclerView.setVisibility(View.GONE);

                rlvMainContainerDeviceID.setVisibility(View.VISIBLE);
                rlvPlantMainContainerID.setVisibility(View.GONE);
            }
        });

        txtAddDeviceBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (checkAndRequestPermissions())
                {
                    //Intent intent = new Intent(getActivity(), AddDevice.class);
                        Intent intent = new Intent(getActivity(), AddPlantAndDeviceOPtion.class);
                        startActivity(intent);
                  //  Intent intent = new Intent(getActivity(), AddDevice.class);
                  //  startActivity(intent);
                }
               /* else
                {
                    if (checkAndRequestPermissions())
                    {
                            Intent intent = new Intent(getActivity(), AddDevice.class);
                            startActivity(intent);
                        //Intent intent = new Intent(getActivity(), AddDevice.class);
                        //  startActivity(intent);
                    }
                }*/
                /*Intent intent = new Intent(getActivity(), AddDevice.class);
                startActivity(intent);*/
            }
        });
        lvlListViewDataContainerID = (LinearLayout) rootView.findViewById(R.id.lvlListViewDataContainerID);

        previous_level = (TextView) rootView.findViewById(R.id.previous_level);

        clientArray = new ArrayList<Org_Client>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        topChild = new ArrayList<String>();

        currentVersion = BuildConfig.VERSION_NAME;

        pref = context.getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        mUserID = pref.getString("key_muserid", "invalid_muserid");
        // check org client id
        clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0

        //  Log.d("check_id", ""+clientid);

        if (clientid == 9999) {
            clientid = 0;
        }

        if (clientid == 0)
        {
            NewSolarVFD.OEM_NORMAL_USER_CHECK = 0;
            txtAddDeviceBTNID.setVisibility(View.VISIBLE);
        }
        else
        {
            NewSolarVFD.OEM_NORMAL_USER_CHECK = 1;
            txtAddDeviceBTNID.setVisibility(View.GONE);
        }

        // Log.d("check_id1", ""+clientid);
        //set selected client id for org , show on map
        editor.putString("key_clientid_for_map", String.valueOf(clientid));
        editor.commit();
        //set selected client id for org , show on map

        Log.d("home_clientid", String.valueOf(clientid));

        first_level = clientid;

        //  organisation login show org list

        if (clientid != 0) {

            org_layout.setVisibility(View.VISIBLE);

        }

        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setTitle("Please wait..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        //    progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);

          //  callGetPlantListCheckAPI();
            callHomeDeviceListAPI();
           // new Worker().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(NewSolarVFD.USER_LIST_PAGE_SHOW_FLAG)
        {
            rlvPlantViewID.setBackgroundResource(R.drawable.right_round_selected);
            rlvDevicesViewID.setBackgroundResource(R.drawable.left_round_unselected);

            list.setVisibility(View.GONE);
            mMenuCardRecyclerView.setVisibility(View.VISIBLE);

            rlvMainContainerDeviceID.setVisibility(View.GONE);
            rlvPlantMainContainerID.setVisibility(View.VISIBLE);
        }
        else
        {
            rlvPlantViewID.setBackgroundResource(R.drawable.right_round_unselected);
            rlvDevicesViewID.setBackgroundResource(R.drawable.left_round_selected);

            rlvMainContainerDeviceID.setVisibility(View.VISIBLE);
            rlvPlantMainContainerID.setVisibility(View.GONE);

            list.setVisibility(View.VISIBLE);
            mMenuCardRecyclerView.setVisibility(View.GONE);
        }


        lvlListViewDataContainerID.setVisibility(View.VISIBLE);

        app_version_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
//*****************************  organisation login ******************
        previous_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SupClientId != first_level) {

                    //   Log.d("user",""+SupClientId+"---"+first_level);
                    try {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Loading..."); // Setting Message
                        progressDialog.setTitle("Please wait..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    clientid = SupParentId;
                   // new Worker().execute();

                } else {
                    previous_level.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Log.d("life","onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Log.d("life","onDetach");
    }

    //    @Override
    public void onResume() {
        super.onResume();
      /*  if (!CustomUtility.isOnline(context)) {

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    try {
                        if (adapter != null)
                            adapter = null;

                        arraylist = databaseHelperTeacher.getDeviceListData();

                        if (arraylist.size() == 1) {



                            conditionFunctionNAvigation(arraylist);
                        } else {
                            Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                            adapter = new CustomerGPSListViewAdapter(context, arraylist);
                        }
                        // adapter = new CustomerGPSListViewAdapter(context, arraylist);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/
        //adapter.notifyDataSetChanged();
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(context, mString, Toast.LENGTH_LONG).show();
        }
    };


   /* private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {

            String data = null;

            try {
                get_device();

                getActivity().runOnUiThread(

                        new Runnable() {

                            @Override
                            public void run() {

                                if (clientid > 0) {
                                    Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                                    adapter = new CustomerGPSListViewAdapter(context, arraylist);
                                    list.setAdapter(adapter);
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

                                    if (clientid != 0 && expand == true) {
                                        //  orgListView.expandGroup(0); //expand first leve
                                    }
                                    // Listview Group click listener
                                    orgListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                        @Override
                                        public boolean onGroupClick(ExpandableListView parent, View v,
                                                                    int groupPosition, long id) {
                                            return false;
                                        }
                                    });

                                    // Listview Group expanded listener
                                    orgListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                                        @Override
                                        public void onGroupExpand(int groupPosition) {
                                            if (SupClientId == first_level) {
                                                previous_level.setVisibility(View.INVISIBLE);
                                            } else {
                                                previous_level.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    });

                                    // Listview Group collasped listener
                                    orgListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                                        @Override
                                        public void onGroupCollapse(int groupPosition) {
                                        }
                                    });
                                    // Listview on child click listener
                                    orgListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                        @Override
                                        public boolean onChildClick(ExpandableListView parent, View v,
                                                                    int groupPosition, int childPosition, long id) {
                                            // TODO Auto-generated method stub
                                            clientid = Integer.parseInt(clientArray.get(childPosition).getClientId());
                                            clientName = clientArray.get(childPosition).getClientName();
                                            //set selected client id for org , show on map
                                            editor.putString("key_clientid_for_map", String.valueOf(clientid));
                                            editor.commit();
                                            if (SupClientId == first_level) {
                                                previous_level.setVisibility(View.INVISIBLE);
                                            } else {
                                                previous_level.setVisibility(View.VISIBLE);
                                            }
                                            try {
                                                progressDialog = new ProgressDialog(context);
                                                progressDialog.setMessage("Loading..."); // Setting Message
                                                progressDialog.setTitle("Please wait..."); // Setting Title
                                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                                progressDialog.show(); // Display Progress Dialog
                                                progressDialog.setCancelable(false);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            new Worker().execute();
                                            if (SupClientId == first_level) {
                                                previous_level.setVisibility(View.INVISIBLE);
                                            } else {
                                                previous_level.setVisibility(View.VISIBLE);
                                            }
                                            return false;
                                        }
                                    });
                                } else {
                                    */



    /*if (arraylist.size() == 1) {

                                        conditionFunctionNAvigation(arraylist);
                                    } else*//* {
                                        Constant.CHECK_BACK_MENU_VIEW_ICON = 0;

                                        adapter = new CustomerGPSListViewAdapter(context, arraylist);

                                        list.setAdapter(adapter);
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

                                        if (clientid != 0 && expand == true) {
                                            //  orgListView.expandGroup(0); //expand first leve
                                        }

                                        // Listview Group click listener
                                        orgListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                            @Override
                                            public boolean onGroupClick(ExpandableListView parent, View v,
                                                                        int groupPosition, long id) {
                                                return false;
                                            }
                                        });

                                        // Listview Group expanded listener
                                        orgListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                                            @Override
                                            public void onGroupExpand(int groupPosition) {
//
                                                if (SupClientId == first_level) {
                                                    previous_level.setVisibility(View.INVISIBLE);
                                                } else {
                                                    previous_level.setVisibility(View.VISIBLE);
                                                }

                                            }
                                        });

                                        // Listview Group collasped listener
                                        orgListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                                            @Override
                                            public void onGroupCollapse(int groupPosition) {
                                            }
                                        });
                                        // Listview on child click listener
                                        orgListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                            @Override
                                            public boolean onChildClick(ExpandableListView parent, View v,
                                                                        int groupPosition, int childPosition, long id) {
                                                // TODO Auto-generated method stub

                                                clientid = Integer.parseInt(clientArray.get(childPosition).getClientId());
                                                clientName = clientArray.get(childPosition).getClientName();
                                                //set selected client id for org , show on map
                                                editor.putString("key_clientid_for_map", String.valueOf(clientid));
                                                editor.commit();
                                                if (SupClientId == first_level) {
                                                    previous_level.setVisibility(View.INVISIBLE);
                                                } else {
                                                    previous_level.setVisibility(View.VISIBLE);
                                                }
                                                try {
                                                    progressDialog = new ProgressDialog(context);
                                                    progressDialog.setMessage("Loading..."); // Setting Message
                                                    progressDialog.setTitle("Please wait..."); // Setting Title
                                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                                    progressDialog.show(); // Display Progress Dialog
                                                    progressDialog.setCancelable(false);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                new Worker().execute();
                                                if (SupClientId == first_level) {
                                                    previous_level.setVisibility(View.INVISIBLE);
                                                } else {
                                                    previous_level.setVisibility(View.VISIBLE);
                                                }
                                                return false;
                                            }
                                        });
                                        // 7869915389 hemant service
                                    }////////end if else
                                }


                                //********************************* end app version *********************************

                            }
                        });

            } catch (Exception e) {
                try {
                    progressDialog.dismiss();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.i("SomeTag", System.currentTimeMillis() / 1000L
//                    + " post execute \n" + result);
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
     */

    public void get_device() {

        String url = "null";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.clear();

        if(arraylist.size()> 0)
        {
            arraylist.clear();
        }
        if (CustomUtility.isOnline(context)) {
            try {
//********************************** check app version ********************************************
                //********************************** check logout session ********************************************

//************************************ get device details ******************************************


                    // organisation login
                    url = NewSolarVFD.ORG_GET_DEVICE + "?MUserId=" + pref.getString("key_muserid", "invalid_muserid") +
                            "&ClientId=" + clientid;


                String obj = CustomHttpClient.executeHttpGet(url);

                Log.d("home_url", "" + url);

                Log.d("home_obj", "" + obj);

                if (obj != null) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // Stuff that updates the UI
                            try {
                                progressDialog.dismiss();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    if (clientid == 0) // single user  data
                    {
                        JSONArray ja = new JSONArray(obj);
                        boolean mCheckFirstDB;

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jo = ja.getJSONObject(i);
                            customer_gps = new Customer_GPS_Search();
                            customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType"));
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(mUserID);
                            customer_gps.setMobile(jo.has("Mobile") ? jo.getString("Mobile") : "");
                            customer_gps.setMobValidationDate(jo.has("MobValidationDate") ? jo.getString("MobValidationDate") : "");
                            customer_gps.setTypeName(jo.has("TypeName")? jo.getString("TypeName") : "");
                            customer_gps.setPumpStatus(jo.getString("PumpStatus"));
                            customer_gps.setIsLogin(jo.getString("IsLogin"));
                            //customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                            customer_gps.setDeviceStatus(jo.has("DeviceStatus") ? jo.getInt("DeviceStatus") : 0);
                            customer_gps.setDeviceImage(jo.has("DeviceImage") ? jo.getString("DeviceImage") : "");
                            //customer_gps.setDeviceImage(jo.getString("DeviceImage") ? jo.getString("DeviceImage") : "");
                            customer_gps.setModelType(jo.has("ModelType") ? jo.getString("ModelType") : "");
                            arraylist.add(customer_gps);

                            if (i == 0) {
                                mCheckFirstDB = true;
                            } else {
                                mCheckFirstDB = false;
                            }

                            databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"),jo.getInt("DeviceStatus"), mCheckFirstDB);
                           // databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);

                        }
                        if (ja.length() < 1) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    txtAddDeviceMSGIDDD.setVisibility(View.VISIBLE);
                                    txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                                }
                            });


                        }
                    }
                    else   //  organisation data
                    {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // Stuff that updates the UI
                                txtAddDeviceBTNID.setVisibility(View.GONE);
                            }
                        });

                        JSONObject jsonObj = new JSONObject(obj);
                        JSONArray ja = jsonObj.getJSONArray("Client");

                        expand = true; // expand first level

                        arraylist.clear();
                        listDataHeader.clear();
                        topChild.clear();
                        listDataChild.clear();

                        if (ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);

                                org_client = new Org_Client();
                                org_client.setClientId(jo.getString("ClientId"));
                                org_client.setClientName(jo.has("ClientName") ? jo.getString("ClientName") : "");
                                org_client.setParentId(jo.getString("ParentId"));
                                org_client.setParentName(jo.getString("ParentId"));

                                clientArray.add(i, org_client);

                                if (i == 0) {
                                    listDataHeader.add(jo.has("ParentName") ? jo.getString("ParentName") : "");
                                    expand = true; // expand first level
                                }
                                topChild.add(jo.has("ClientName") ? jo.getString("ClientName") : "");
                            }
                        }

                        if (ja.length() == 0) {
                            listDataHeader.add(clientName);
                            expand = true; // expand first level
                        }

                        listDataChild.put(listDataHeader.get(0), topChild); // Header, Child data
                        orglistAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

                        JSONArray ja_device = jsonObj.getJSONArray("Device");
                        boolean mCheckFirstDB;
                        for (int i = 0; i < ja_device.length(); i++) {

                            JSONObject jo = ja_device.getJSONObject(i);
                            customer_gps = new Customer_GPS_Search();
                            customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                            customer_gps.setDeviceNo(jo.getString("DeviceNo"));
                            customer_gps.setDeviceType(jo.getString("DeviceType"));
                            customer_gps.setMDeviceId(jo.getString("MDeviceId"));
                            customer_gps.setMUserId(mUserID);
                            //customer_gps.setTypeName(jo.getString("TypeName"));
                            customer_gps.setPumpStatus(jo.getString("PumpStatus"));
                            customer_gps.setIsLogin(jo.getString("IsLogin"));
                          //  customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                           customer_gps.setDeviceStatus(jo.has("DeviceStatus") ? jo.getInt("DeviceStatus") : 0);
                            customer_gps.setTypeName(jo.has("TypeName")? jo.getString("TypeName") : "");
                            customer_gps.setDeviceImage(jo.has("DeviceImage") ? jo.getString("DeviceImage") : "");
                            customer_gps.setMobile(jo.has("Mobile") ? jo.getString("Mobile") : "");
                            customer_gps.setMobValidationDate(jo.has("MobValidationDate") ? jo.getString("MobValidationDate") : "");
                            customer_gps.setModelType(jo.has("ModelType") ? jo.getString("ModelType") : "");

                            arraylist.add(customer_gps);

                            if (i == 0) {
                                mCheckFirstDB = true;
                            } else {
                                mCheckFirstDB = false;
                            }

                          //  databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);
                            databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.has("TypeName")? jo.getString("TypeName") : "", jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), jo.getInt("DeviceStatus"),mCheckFirstDB);
                          //  databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.has("TypeName")? jo.getString("TypeName") : "", jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"),mCheckFirstDB);
                        }


                    }
                } else {
                    ///vikas changes
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                txtAddDeviceMSGIDDD.setText(getResources().getString(R.string.err_no_device_found));
                                txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                                txtAddDeviceMSGIDDD.setVisibility(View.VISIBLE);
                                lvlListViewDataContainerID.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                            // Stuff that updates the UI
                        }
                    });
                    // CustomUtility.isErrorDialog(context, getString(R.string.error), getString(R.string.err_connection));
                }

            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        }


                });

            }

        } else {


}

        }




    private void callHomeDeviceListClientAPI() {

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        HomeDeviceOEMModel mHomeDeviceOEMModel = gson.fromJson(Json, HomeDeviceOEMModel.class);
                        getHomeDeviceListClientResponse(mHomeDeviceOEMModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {

                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            txtAddDeviceMSGIDDD.setVisibility(View.VISIBLE);
                            txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                        }
                    });


                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

           /* JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("DeviceTypeID", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.CHECK_USER_DEVICE_STATUS);/////*/



                Map<String, String> wordsByKey = new HashMap<>();

                wordsByKey.put("MUserId", mUserID);

                wordsByKey.put("ClientId", clientid+"");
                //   wordsByKey.put("IMEI","38648723487236487264");

                baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.ORG_GET_DEVICE1);/////


        }
        else
        {

        }
    }

    private void getHomeDeviceListClientResponse(HomeDeviceOEMModel mHomeDeviceOEMModel) {

        if (mHomeDeviceOEMModel.getStatus()) {

           /* if(mHomeDeviceListResponse.size() > 0)
                mHomeDeviceListResponse.clear();*/

            if(mClient.size() > 0)
                mClient.clear();

            if(mPClient.size() > 0)
                mPClient.clear();

            if(mDevice.size() > 0)
                mDevice.clear();

            mHomeDeviceOEResponse=mHomeDeviceOEMModel.getResponse() ;
            mDevice= mHomeDeviceOEResponse.getDevice();
            mPClient= mHomeDeviceOEResponse.getPClient();
            mClient= mHomeDeviceOEResponse.getClient();

            expand = true; // expand first level

            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    txtAddDeviceBTNID.setVisibility(View.GONE);
                }
            });

            for (int i = 0; i < mClient.size(); i++) {

                org_client = new Org_Client();
                org_client.setClientId(mClient.get(i).getClientId());
                org_client.setClientName(mClient.get(i).getClientName());
               // org_client.setClientName(mClient.get(i).getClientName() ? jo.getString("ClientName") : "");
                org_client.setParentId(mClient.get(i).getParentId());
                org_client.setParentName(mClient.get(i).getParentName());

                clientArray.add(i, org_client);

                if (i == 0) {
                  //  listDataHeader.add(jo.has("ParentName") ? jo.getString("ParentName") : "");
                    listDataHeader.add(mClient.get(i).getParentName());
                    expand = true; // expand first level
                }
               // topChild.add(jo.has("ClientName") ? jo.getString("ClientName") : "");
                topChild.add(mClient.get(i).getParentName());
            }

            if (mClient.size() == 0) {
                listDataHeader.add(clientName);
                expand = true; // expand first level
            }

            listDataChild.put(listDataHeader.get(0), topChild); // Header, Child data
            orglistAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);
            /////22222222222222222222
            boolean mCheckFirstDB;
            for (int i = 0; i < mDevice.size(); i++) {

                customer_gps = new Customer_GPS_Search();
               // customer_gps.setCustomer_name(jo.has("CustomerName") ? jo.getString("CustomerName") : "");
                customer_gps.setCustomer_name(mDevice.get(i).getCustomerName());
                customer_gps.setDeviceNo(mDevice.get(i).getDeviceNo());
                customer_gps.setDeviceType(mDevice.get(i).getDeviceType());
                customer_gps.setMDeviceId(mDevice.get(i).getMDeviceId());
                customer_gps.setMUserId(mUserID);
                //customer_gps.setTypeName(jo.getString("TypeName"));
                customer_gps.setPumpStatus(mDevice.get(i).getPumpStatus());
                customer_gps.setIsLogin(mDevice.get(i).getIsLogin());
                //  customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
               //customer_gps.setDeviceStatus(jo.has("DeviceStatus") ? jo.getInt("DeviceStatus") : 0);
                customer_gps.setDeviceStatus(mDevice.get(i).getDeviceStatus());
               // customer_gps.setTypeName(jo.has("TypeName")? jo.getString("TypeName") : "");
                customer_gps.setTypeName(mDevice.get(i).getTypeName());
                customer_gps.setDeviceImage(mDevice.get(i).getDeviceImage());
                customer_gps.setMobile(mDevice.get(i).getMobile());
               // customer_gps.setMobValidationDate(jo.has("MobValidationDate") ? jo.getString("MobValidationDate") : "");
                customer_gps.setModelType(mDevice.get(i).getModelType());

                arraylist.add(customer_gps);

                if (i == 0) {
                    mCheckFirstDB = true;
                } else {
                    mCheckFirstDB = false;
                }

                //  databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);
                databaseHelperTeacher.insertDeviceListData(mDevice.get(i).getCustomerName(), mDevice.get(i).getDeviceNo(), mDevice.get(i).getDeviceType(), mDevice.get(i).getMDeviceId(), mUserID, mDevice.get(i).getMobile(), mDevice.get(i).getTypeName(), mDevice.get(i).getModelType(), mDevice.get(i).getPumpStatus(), mDevice.get(i).getIsLogin(), mDevice.get(i).getDeviceStatus(),mCheckFirstDB);


            }

            if (mPClient.size() < 1) {
                txtAddDeviceBTNID.setVisibility(View.GONE);
                //   txtAddDeviceMSGID.setVisibility(View.VISIBLE);
                //  txtAddDeviceBTNID.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < mPClient.size(); i++) {

                SupParentId = Integer.parseInt(mPClient.get(i).getSupParentId());
                SupClientId = Integer.parseInt(mPClient.get(i).getClientId());

            }


            try {
                //  get_device();

                getActivity().runOnUiThread(

                        new Runnable() {

                            @Override
                            public void run() {


                                Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                                adapter = new CustomerGPSListViewAdapter(context, arraylist);
                                list.setAdapter(adapter);


                                //********************************* end app version *********************************

                            }
                        });

                baseRequest.hideLoader();

            } catch (Exception e) {

                baseRequest.hideLoader();

                e.printStackTrace();
            }

        }
        else
        {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    txtAddDeviceMSGIDDD.setVisibility(View.VISIBLE);
                    txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                    baseRequest.hideLoader();
                }
            });

        }
    }

    private void callHomeDeviceListAPI() {

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here

                        HomeDeviceListModel mHomeDeviceListModel = gson.fromJson(Json, HomeDeviceListModel.class);
                        getHomeDeviceListResponse(mHomeDeviceListModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {

                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            baseRequest.hideLoader();
                            // Stuff that updates the UI
                            txtAddDeviceMSGIDDD.setVisibility(View.VISIBLE);
                            txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                        }
                    });


                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    baseRequest.hideLoader();
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

           /* JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("DeviceTypeID", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.CHECK_USER_DEVICE_STATUS);/////*/


                Map<String, String> wordsByKey = new HashMap<>();

                wordsByKey.put("MUserId", mUserID);
                wordsByKey.put("isActive", "1");
                wordsByKey.put("PlantId", "0");
                //   wordsByKey.put("IMEI","38648723487236487264");

               // baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.GET_DEVICE1);/////
                baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.ADD_DEVICE_API_VK);/////

        }
        else
        {

        }
    }

    private void getHomeDeviceListResponse(HomeDeviceListModel mHomeDeviceListModel) {

        if (mHomeDeviceListModel.getStatus()) {

            baseRequest.hideLoader();

         if(mHomeDeviceListResponse.size() > 0)
            mHomeDeviceListResponse.clear();

          mHomeDeviceListResponse = mHomeDeviceListModel.getResponse();

            boolean mCheckFirstDB;
            for(int i = 0; i < mHomeDeviceListResponse.size(); i++) {


                customer_gps = new Customer_GPS_Search();
                customer_gps.setCustomer_name(mHomeDeviceListResponse.get(i).getCustomerName());
                customer_gps.setDeviceNo(mHomeDeviceListResponse.get(i).getDeviceNo());
                customer_gps.setDeviceType(mHomeDeviceListResponse.get(i).getDeviceType());
                customer_gps.setMDeviceId(mHomeDeviceListResponse.get(i).getMDeviceId());
                customer_gps.setMUserId(mUserID);
                customer_gps.setMobile(mHomeDeviceListResponse.get(i).getMobile());
                customer_gps.setMobValidationDate(mHomeDeviceListResponse.get(i).getMobValidationDate());
                customer_gps.setTypeName(mHomeDeviceListResponse.get(i).getTypeName());
                customer_gps.setPumpStatus(mHomeDeviceListResponse.get(i).getPumpStatus());
                customer_gps.setIsLogin(mHomeDeviceListResponse.get(i).getIsLogin());
                //customer_gps.setDeviceStatus(jo.getInt("DeviceStatus"));
                customer_gps.setDeviceStatus(mHomeDeviceListResponse.get(i).getDeviceStatus());
                customer_gps.setDeviceImage(mHomeDeviceListResponse.get(i).getDeviceImage());
                //customer_gps.setDeviceImage(jo.getString("DeviceImage") ? jo.getString("DeviceImage") : "");
                customer_gps.setModelType(mHomeDeviceListResponse.get(i).getModelType());
                arraylist.add(customer_gps);

                if (i == 0) {
                    mCheckFirstDB = true;
                } else {
                    mCheckFirstDB = false;
                }

                databaseHelperTeacher.insertDeviceListData(mHomeDeviceListResponse.get(i).getCustomerName(), mHomeDeviceListResponse.get(i).getDeviceNo(), mHomeDeviceListResponse.get(i).getDeviceType(),
                        mHomeDeviceListResponse.get(i).getMDeviceId(), mUserID, mHomeDeviceListResponse.get(i).getMobile(), mHomeDeviceListResponse.get(i).getTypeName(), mHomeDeviceListResponse.get(i).getModelType(),
                        mHomeDeviceListResponse.get(i).getPumpStatus(), mHomeDeviceListResponse.get(i).getIsLogin(),mHomeDeviceListResponse.get(i).getDeviceStatus(), mCheckFirstDB);
                // databaseHelperTeacher.insertDeviceListData(jo.getString("CustomerName"), jo.getString("DeviceNo"), jo.getString("DeviceType"), jo.getString("MDeviceId"), mUserID, jo.getString("Mobile"), jo.getString("TypeName"), jo.getString("ModelType"), jo.getString("PumpStatus"), jo.getString("IsLogin"), mCheckFirstDB);

            }


            try {
              //  get_device();

                getActivity().runOnUiThread(

                        new Runnable() {

                            @Override
                            public void run() {


                                Constant.CHECK_BACK_MENU_VIEW_ICON = 0;
                                adapter = new CustomerGPSListViewAdapter(context, arraylist);
                                list.setAdapter(adapter);


                                //********************************* end app version *********************************

                            }
                        });

                baseRequest.hideLoader();

            } catch (Exception e) {

                    baseRequest.hideLoader();

                e.printStackTrace();
            }




        }
        else
        {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    txtAddDeviceMSGIDDD.setVisibility(View.VISIBLE);
                    txtAddDeviceBTNID.setVisibility(View.VISIBLE);
                    baseRequest.hideLoader();
                }
            });

        }
    }
    

    @Override
    public void onDestroy() {
        try {
          /*  progressDialog.dismiss();

            if(progressDialog !=null)
                progressDialog = null;*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    private void conditionFunctionNAvigation(ArrayList<Customer_GPS_Search> arraylist) {
        Constant.CHECK_BACK_MENU_VIEW_ICON = 1;
       // String deviceModelType = arraylist.get(0).getDeviceType();
        String deviceModelType = arraylist.get(0).getModelType();

        System.out.println("deviceModelType==>> "+deviceModelType+"\n");

        if ((deviceModelType.equalsIgnoreCase("17")))///1
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }   else if ((deviceModelType.equalsIgnoreCase("69")))///1
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            startActivity(mIntent);
        }
        else if (deviceModelType.equalsIgnoreCase("66"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("73"))/////nandi micro
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandiMicro.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if ((deviceModelType.equalsIgnoreCase("49")))////2
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }
        else if (deviceModelType.equalsIgnoreCase("95") || deviceModelType.equalsIgnoreCase("87"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("2"))/////3
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("1"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }else if (deviceModelType.equalsIgnoreCase("97") || deviceModelType.equalsIgnoreCase("87"))/////4
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("7"))//////5
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("6"))//////6
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else if (deviceModelType.equalsIgnoreCase("62"))/////7
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        } else///////8
        {
            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoAOneSS.class);
            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
            mIntent.putExtra("mPos", 0);
            mContext.startActivity(mIntent);
        }

        getActivity().finish();
    }


    public void logout() {
        editor.putString("key_login", "N");
        editor.putString("key_OTP", "9999");
        editor.putString("key_mobile_number", "9999999999");

        editor.putString("key_otp_for_user", "9999");
        editor.putString("key_mparentid", "9999");
        editor.putString("key_muserid", "9999");
        // editor.putString("key_clientid","9999");
        editor.putString("key_clientid", "0");
        editor.putString("key_login_username", "Invalid User");
        editor.putString("key_clientid_for_map", "9999");
        editor.putString("key_clientid_for_data_report", "9999");


        editor.commit();
    }



    private boolean checkAndRequestPermissions() {
        int IMEI_NUM = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        int LOCATION = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int CAMERAV = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (IMEI_NUM != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (CAMERAV != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

//If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast

                Toast.makeText(mActivity, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {

//Displaying another toast if permission is not granted
                Toast.makeText(mActivity, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast

                Toast.makeText(mActivity, "Permission granted now you can use camera", Toast.LENGTH_LONG).show();
            } else {

//Displaying another toast if permission is not granted
                Toast.makeText(mActivity, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast

                Toast.makeText(mActivity, "Permission granted now you can use user location", Toast.LENGTH_LONG).show();
            } else {

//Displaying another toast if permission is not granted
                Toast.makeText(mActivity, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //////////////////////////////////////////Palnatttttt

    private void setDataAdapter(){
        if (recyclerViewAdapter != null)
            recyclerViewAdapter = null;

        //   recyclerViewAdapter = new MenuAdapter(mMenuResponseList, mActivity);
        recyclerViewAdapter = new GridPlantAdapter(mContext, mActivity, mGetPlantResponse);
        mMenuCardRecyclerView.setAdapter(recyclerViewAdapter);
    }


    private void callGetPlantListCheckAPI() {

        if (CustomUtility.isOnline(mContext)){

            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int APINumber, String Json, Object obj) {
                    //  JSONArray arr = (JSONArray) obj;
                    try {
                        Gson gson = new Gson();
                        //////////////add model class here
                        GetPlantModelView mGetPlantModelView = gson.fromJson(Json, GetPlantModelView.class);
                        getPlantListStatusCheck(mGetPlantModelView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int APINumber, String errorCode, String message) {

                    txtAddDeviceMSGID.setText(getResources().getString(R.string.err_no_Plant_found));
                    if (clientid == 0) // single user  data
                    {
                        callHomeDeviceListAPI();
                    }
                    else
                    {
                        callHomeDeviceListClientAPI();
                     //   new Worker().execute();
                    }
                       Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNetworkFailure(int APINumber, String message) {
                    Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }
            });

            JsonObject jsonObject = new JsonObject();
            try {
                ////Put input parameter here
                jsonObject.addProperty("id", mUserID);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
            //  baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////
            baseRequest.callAPIPostIMEI(1, jsonObject, NewSolarVFD.GET_PLANT_LIST_CHECK);/////

        }
        else
        {

        }
    }

    private void getPlantListStatusCheck(GetPlantModelView mGetPlantModelView) {

        if (mGetPlantModelView.getStatus()) {


                if(mGetPlantResponse != null && mGetPlantResponse.size() > 0)
                    mGetPlantResponse.clear();

            mGetPlantResponse = mGetPlantModelView.getResponse();

            setDataAdapter();

            if (clientid == 0) // single user  data
            {
            callHomeDeviceListAPI();
            }
            else
            {
               // new Worker().execute();
                callHomeDeviceListClientAPI();
            }
        }
        else
        {
            if (clientid == 0) // single user  data
            {
                callHomeDeviceListAPI();
            }
            else
            {
              // new Worker().execute();
                callHomeDeviceListClientAPI();
            }
        }
    }



}