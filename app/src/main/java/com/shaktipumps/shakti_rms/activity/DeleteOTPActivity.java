package com.shaktipumps.shakti_rms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class DeleteOTPActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView btn_send_otp,btn_verify_otp;
    private TextInputLayout inputLayoutOTP;
    private EditText inputOTP;
    Context mContext;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    String otp = "null",sms_url = "null",mobile_number = "null",MuserId = "null" ,MDeviceId = "null",Delete_OTP  = "null";

    String otp_status = "false" ;
    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        DeleteOTPActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mContext =  this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_verify_device);


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        inputLayoutOTP  = (TextInputLayout) findViewById(R.id.input_layout_otp);
        inputOTP        = (EditText) findViewById(R.id.et_otp);
        btn_send_otp    = (TextView) findViewById(R.id.btn_send_otp);
        btn_verify_otp  = (TextView) findViewById(R.id.btn_verify_otp);







        Bundle bundle = getIntent().getExtras();

       // mobile_number = bundle.getString("Mobile");  //**** this is register mobile mo

        //** this mobile no is enter by user when new account open
        mobile_number =  pref.getString("key_mobile_number","invalid_mobno");


        MuserId = bundle.getString("MUserId");
        MDeviceId = bundle.getString("MDeviceId");
        Delete_OTP  = bundle.getString("Delete_OTP");




     //   Log.d("delete_otp",MuserId +"--"+  MDeviceId +"--"+ mobile_number +"otp"+Delete_OTP );


        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP();

            }
        });




        btn_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reSendOTP();

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

    private void verifyOTP() {


        if (!validateOTP()) {
            return;
        }

          checkOTP();







    }



    private boolean validateOTP() {
        if (inputOTP.getText().toString().isEmpty()) {
            inputLayoutOTP.setError(getString(R.string.err_msg_otp));
            requestFocus(inputOTP);
            return false;
        } else {
            inputLayoutOTP.setErrorEnabled(false);
        }



        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void reSendOTP() {


        otp = ""+((int)(Math.random()*9000)+1000);

        Delete_OTP =   otp ;
        progressDialog = ProgressDialog.show(DeleteOTPActivity.this, "", "Please wait !");


        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();

        param.clear();
        otp_param.clear();



        otp_param.add(new BasicNameValuePair("MUserId", MuserId));
        otp_param.add(new BasicNameValuePair("OTP", otp));
        otp_param.add(new BasicNameValuePair("MDeviceId",MDeviceId ));

     //   Log.d("update_device",MuserId +"--"+otp +"--"+MDeviceId);

        //Log.d("delete_otp2",MuserId +"--"+  MDeviceId +"--"+ mobile_number +"otp"+Delete_OTP );


        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(DeleteOTPActivity.this)) {

                    try     {


                            param.clear();

                            //**** get sms string ************************
                            String sms_obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.SMS_URL, param);


                            JSONObject sms_response = new JSONObject(sms_obj);

                            sms_url = sms_response.getString("SMS_URL");


                            //**** end sms string ************************

                           //  Log.d("update_sms_url", sms_url );


                            if (!sms_url.equalsIgnoreCase("null"))
                            {

                                progressDialog.dismiss();

                             //   Log.d("intent1",MuserId +"--"+  MDeviceId +"--"+ mobile_number);

                                String send_sms = sms_url.replaceAll("<reci_addr>",mobile_number).trim();

                                String msg = "Shakti Remote Monitoring  Delete Device Verification OTP is" + "  " + otp;



                                msg = URLEncoder.encode(msg);

                                send_sms = send_sms.replaceAll("<message>", msg).trim();


                                // send sms
                                String sms_return = CustomHttpClient.executeHttpGet(send_sms);

                            //    Log.d("resend_delete_sms",send_sms);

                                progressDialog.dismiss();




                            }




                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(DeleteOTPActivity.this,getString(R.string.error),getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }


                }

                else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(DeleteOTPActivity.this,"Error","No Internet Connection");

                }


            }

        }.start();

    }


    public void checkOTP()
    {

        if (  Delete_OTP .equalsIgnoreCase( inputOTP.getText().toString()))
        {

           delete_from_server();

        }

        else
        {
            CustomUtility.isErrorDialog(DeleteOTPActivity.this,getString(R.string.error),getString(R.string.err_invaild_otp));
        }

    }


    public void  delete_from_server() {

        ArrayList<String> al;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        al = new ArrayList<>();

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();

        param.clear();



        sms_url =   NewSolarVFD.DELETE_DEVICE +"?"+ "MUserId=" + MuserId + "&"+ "MDeviceId="+ MDeviceId ;



/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(DeleteOTPActivity.this, "", "Please wait !");


        //Log.d("delete_otp1",MuserId +"--"+  MDeviceId +"--"+ mobile_number +"otp"+Delete_OTP );


        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(DeleteOTPActivity.this)) {

                    try {

                        String obj = CustomHttpClient.executeHttpGet(sms_url);

                        //  Log.d("update_otp",""+ obj) ;

                         //   Log.d("delete_obj", "" + obj);
                        if (obj.equalsIgnoreCase("Successfully"))
                        {

/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/******************************************************************************************/

                                DeleteOTPActivity.this.finish();
                                progressDialog.dismiss();



                                Message msg2 = new Message();
                                msg2.obj = "Device Delete Successfully .";
                                mHandler.sendMessage(msg2);


                            Intent intent = new Intent(DeleteOTPActivity.this, MainActivity.class);
                                startActivity(intent);




                        }
                        else
                        {
                            progressDialog.dismiss();
                            CustomUtility.isErrorDialog(DeleteOTPActivity.this,getString(R.string.error),getString(R.string.err_connection));


                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(DeleteOTPActivity.this,getString(R.string.error),getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }

                }

                else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(DeleteOTPActivity.this,"Error","No Internet Connection");

                }

            }

        }.start();
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(DeleteOTPActivity.this, mString, Toast.LENGTH_LONG).show();
        }
    };



}

