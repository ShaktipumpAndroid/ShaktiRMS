package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EXPDummyPaymentActivity extends AppCompatActivity {


    private Context mContext;
    private RelativeLayout rlvBackViewID;
    private ImageView imgHeaderID, imgProductImageID;
    private TextView txtHeaderID;
    private TextView txtSubscribeBTNID;
    private CircleImageView imgDeviceImageID;


    private List<Customer_GPS_Search> customerSearchesList = null;
    private int mPosition;
    private String vDeviceNo;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    int clientid = 0 ;
    String isLoginCheck, isPumpCheck, mImageUrl;
    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", mModelType = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expdummy_payment);
        mContext = this;
        initView();
    }

    private void initView() {

        try {
            MUserId = pref.getString("key_muserid", "invalid_muserid");
            clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (  clientid == 9999)
        {
            clientid = 0 ;
        }

        mPosition = getIntent().getIntExtra("mPos",0);
        customerSearchesList = (List<Customer_GPS_Search>) getIntent().getSerializableExtra("mDeviceDetail");
        vDeviceNo = customerSearchesList.get(mPosition).getDeviceNo();
        mImageUrl = customerSearchesList.get(mPosition).getDeviceImage();

        imgDeviceImageID = (CircleImageView) findViewById(R.id.imgDeviceImageID);
        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        imgHeaderID = (ImageView) findViewById(R.id.imgHeaderID);
        imgProductImageID = (ImageView) findViewById(R.id.imgProductImageID);
        txtHeaderID = (TextView) findViewById(R.id.txtHeaderID);
        txtSubscribeBTNID = (TextView) findViewById(R.id.txtSubscribeBTNID);

        txtHeaderID.setText(vDeviceNo);
        try {
            if (mImageUrl.equalsIgnoreCase("")) {

            } else {
                Picasso.with(mContext).load(mImageUrl).placeholder(R.drawable.logo).into(imgDeviceImageID);
                Picasso.with(mContext).load(mImageUrl).placeholder(R.drawable.logo).into(imgProductImageID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        setOnAllClickEvent();
        
    }

    private void setOnAllClickEvent() {

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                finish();
            }
        });

        txtSubscribeBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent mIntent = new Intent(mContext, PaymentOptionActivity.class);
                Intent mIntent = new Intent(mContext, PaymentOptionActivity.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", mPosition);
                startActivity(mIntent);
               // finish();
            }
        });
    }

}
