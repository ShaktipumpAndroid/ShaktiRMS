package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.WelcomeModel.WelcomeModelResponse;
import com.shaktipumps.shakti_rms.model.WelcomeModel.WelcomeModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelectionDataWay extends AppCompatActivity {

    private Context mContext;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private TextView txtBluetoothId, txtOtherId;
    private ViewFlipper flvViewFlipperID;
    private BaseRequest baseRequest;

    private List<WelcomeModelResponse> mWelcomeModelResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_data_way);

        mContext = this;

        initView();
    }

    private void initView() {


        baseRequest = new BaseRequest(this);

        mWelcomeModelResponse = new ArrayList<>();

        txtBluetoothId = (TextView) findViewById(R.id.txtBluetoothId);
        txtOtherId = (TextView) findViewById(R.id.txtOtherId);

        flvViewFlipperID = (ViewFlipper)findViewById(R.id.flvViewFlipperID);

        flvViewFlipperID.setFlipInterval(3000); //set 1 seconds for interval time
        flvViewFlipperID.startFlipping();

        pref = getApplicationContext().getSharedPreferences("MyPrefAR", MODE_PRIVATE);
        editor = pref.edit();

        clickEventOnBTN();

        callSettingParameterValueAPI();
    }



    private void clickEventOnBTN() {

        txtBluetoothId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(mContext, LoginActivity.class));
              // startActivity(new Intent(mContext, PairedDeviceActivity.class));

              /*  Intent intent = new Intent(mContext,GetBTDATAListActivity.class);
                  intent.putExtra("BtNameHead","jfkjhfjh");
                intent.putExtra("BtMacAddressHead","kjkjahjkhajha");
                 startActivity(intent); */

              //  editor.putString("CHECK_APP_LOGIN", "2");
              //  SharedPreferencesUtil.setData(mContext, Constant.CHECK_APP_DEVICE_TYPE, "2");

                Intent intent = new Intent(mContext,LoginActivity.class);
                  intent.putExtra("APP_TYPE_CHECK",2);
                  NewSolarVFD.APP_TYPE_CHECKVK = "2";

                 startActivity(intent);
            }
        });


        txtOtherId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   editor.putString("CHECK_APP_LOGIN", "1");
               // SharedPreferencesUtil.setData(mContext, Constant.CHECK_APP_DEVICE_TYPE, "1");

                Intent intent = new Intent(mContext,LoginActivity.class);
                intent.putExtra("APP_TYPE_CHECK",1);
                NewSolarVFD.APP_TYPE_CHECKVK = "1";
                startActivity(intent);

             //   startActivity(new Intent(mContext, LoginActivity.class));

               // startActivity(new Intent(mContext, Login_Activity.class));
            }
        });


    }





    private void callSettingParameterValueAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    WelcomeModelView mWelcomeModelView = gson.fromJson(Json, WelcomeModelView.class);
                    getWelcomeImageListResponse(mWelcomeModelView);
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

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("DeviceTypeID", "");


        } catch (Exception e) {
            e.printStackTrace();
        }
        //baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.ORG_GET_WELCOME_IMAGE);/////
    }

    private void getWelcomeImageListResponse(WelcomeModelView mWelcomeModelView) {

        // if (!mSettingModelView.getStatus().equalsIgnoreCase("") && !mSettingModelView.getStatus().equalsIgnoreCase("null") && mSettingModelView.getStatus().equalsIgnoreCase("true"))
        if (mWelcomeModelView.getStatus())
        {

            if(mWelcomeModelResponse != null && mWelcomeModelResponse.size() > 0)
                mWelcomeModelResponse.clear();

            mWelcomeModelResponse = mWelcomeModelView.getResponse();

            for (int i = 0; i < mWelcomeModelResponse.size() ; i++) {

                ImageView iv = new ImageView(getApplicationContext());

                // Set an image for ImageView
               // iv.setImageDrawable(getDrawable(R.drawable.animal));

                Picasso.with(this)
                        .load(NewSolarVFD.HOST_NAME2+mWelcomeModelResponse.get(i).getImgUrl())

                        .into(iv);

                // Create layout parameters for ImageView
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                // Add layout parameters to ImageView
                iv.setLayoutParams(lp);

                // Finally, add the ImageView to layout
                flvViewFlipperID.addView(iv);

            }


          /*  recyclerViewAdapter = new DeviceSettingAdapter(mContext, mSettingModelResponse,DeviceType,DeviceNo,MUserId);

            rclSettingListViewID.setAdapter(recyclerViewAdapter);
*/
            //  mClientToken = mPaymentTokenResponse.getToken();
            //  onBraintreeSubmit();
        }
    }

}
