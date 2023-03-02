package com.shaktipumps.shakti_rms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.MapBean.MapDeatialsResponse;
import com.shaktipumps.shakti_rms.bean.MapBean.MapModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String MUserId = "null", MDeviceId = "null";
    String isLoginCheck = "null", isPumpCheck = "null";

    Context context;
    Context mContext;
    ProgressDialog progressDialog;

    private ImageView imgBackBTNID;

    BitmapDescriptor icon;


    SharedPreferences pref;
    int loading = 0;
    String ClientId;

    BaseRequest baseRequest;

    List<MapDeatialsResponse> mMapDeatialsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = this;
        mContext = this;
        baseRequest = new BaseRequest(this);

        imgBackBTNID = (ImageView) findViewById(R.id.imgBackBTNID);

        Bundle bundle = getIntent().getExtras();
        MUserId = bundle.getString("MUserId");
        MDeviceId = bundle.getString("MDeviceId");
        isLoginCheck = bundle.getString("isLoginCheck");
        isPumpCheck = bundle.getString("isPumpCheck");
        ClientId = bundle.getString("ClientId");     // this client id is taken from home layout
        // selected org cleint

        mMapDeatialsResponse = new ArrayList<>();

        imgBackBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        callGetLAtLongAPI();
    //    new Worker().execute();

//***************************************************************
    }


    private void callGetLAtLongAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    MapModelView mMapModelView = gson.fromJson(Json, MapModelView.class);
                    getUpdatedOTPResponse(mMapModelView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        Map<String, String> wordsByKey = new HashMap<>();

        wordsByKey.put("MUserId", MUserId);
        wordsByKey.put("DeviceId", MDeviceId);
        wordsByKey.put("ClientId", ClientId);
        //   wordsByKey.put("IMEI","38648723487236487264");

        baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.DEVICE_DETAILS1);/////

    }

    private void getUpdatedOTPResponse(MapModelView mMapModelView) {

        if(mMapModelView.getStatus())
        {

            if(mMapDeatialsResponse.size() >0)
                mMapDeatialsResponse.clear();

            mMapDeatialsResponse = mMapModelView.getResponse();


            runOnUiThread(
                    new Runnable() {

                        @Override
                        public void run() {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.getUiSettings().setZoomControlsEnabled(true);
                            mMap.getUiSettings().setZoomGesturesEnabled(true);
                            mMap.getUiSettings().setCompassEnabled(true);




                            for (int i = 0; i < mMapDeatialsResponse.size(); i++) {

                                if (mMapDeatialsResponse.get(i).getIsLogin().equalsIgnoreCase("true") && mMapDeatialsResponse.get(i).getPumpStatus().equalsIgnoreCase("true")) {
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_green);
                                } else if (mMapDeatialsResponse.get(i).getIsLogin().equalsIgnoreCase("false") && mMapDeatialsResponse.get(i).getPumpStatus().equalsIgnoreCase("false")) {
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_gray);
                                } else if (mMapDeatialsResponse.get(i).getIsLogin().equalsIgnoreCase("true") && mMapDeatialsResponse.get(i).getPumpStatus().equalsIgnoreCase("false")) {
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_blue);
                                } else if (mMapDeatialsResponse.get(i).getIsLogin().equalsIgnoreCase("false") && mMapDeatialsResponse.get(i).getPumpStatus().equalsIgnoreCase("true")) {
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.solar_blue);
                                }

                                String title = "Device No :" + mMapDeatialsResponse.get(i).getDeviceNo();
                                //String title = "Device No :" + arraylist.get(i).getDeviceNo();

                                try {
                                    LatLng location;
                                    if (mMapDeatialsResponse.get(i).getLatitude() != null || !mMapDeatialsResponse.get(i).getLatitude().equalsIgnoreCase("")) {
                                        location = new LatLng(Double.parseDouble(mMapDeatialsResponse.get(i).getLatitude()), Double.parseDouble(mMapDeatialsResponse.get(i).getLongitude()));
                                    } else {
                                        location = new LatLng(Double.parseDouble("22.627236"), Double.parseDouble("75.5810237"));

                                    }
                                    // LatLng location = new LatLng(Double.parseDouble(arraylist.get(i).getLatitude()),Double.parseDouble(arraylist.get(i).getLongitude()));

                                    MarkerOptions markerOptions = new MarkerOptions().position(location)
                                            .title(mMapDeatialsResponse.get(i).getCustomerName())
                                            .snippet(title)
                                            .icon(icon);

                                    mMap.addMarker(markerOptions);

                                    float zoomLevel = 5;
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });



        }

    }









}
