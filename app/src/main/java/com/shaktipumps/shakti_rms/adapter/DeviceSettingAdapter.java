package com.shaktipumps.shakti_rms.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelResponse;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DeviceSettingAdapter extends RecyclerView.Adapter<DeviceSettingAdapter.ViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<SettingModelResponse> mSettingModelResponse;
    private BaseRequest baseRequest;

    String MUserId = "null", DeviceType = "null", DeviceNo = "null";
    private ProgressDialog progressDialog;
    String speed_mode = "null";
    boolean read_only = true;

    private ArrayList <String> mEditValueIDList = new ArrayList<>();

    int mClickPosition = -1;
    private int edtValue = 2;
    private String old_data = "1";
    String mLat;
    //private HomeUserNameClickListener mHomeUserNameClickListener;
    public DeviceSettingAdapter(Context mContext, Activity mActivity, List<SettingModelResponse> mSettingModelResponse, String DeviceType, String DeviceNo, String MUserId) {

        this.mSettingModelResponse = mSettingModelResponse;
        // this.mHomeUserNameClickListener = mHomeUserNameClickListener;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.DeviceType = DeviceType;
        this.MUserId = MUserId;
        this.DeviceNo = DeviceNo;

        baseRequest = new BaseRequest(mContext);

        for (int i = 0; i <mSettingModelResponse.size() ; i++) {

            mEditValueIDList.add("0");

        }

    }

    public DeviceSettingAdapter(String SSS, Context mContext) {
        // this.galleryModelsList = galleryModelsList;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.device_setting_item, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // holder.rlvHomeMainView.getLayoutParams().width = Validation.getDeviceHeightWidth(mContext, true)/2;
        //  holder.rlvHomeMainView.getLayoutParams().height = Validation.getDeviceHeightWidth(mContext, false)/3+30;

       /* holder.txtNotifyTittleID.setText("");
        holder.txtNotifyByUserID.setText("");
        holder.txtNotifyMinuteDayID.setText("");*/

        /*if ((position % 2) == 0) {
            // number is even
            holder.rlvNotifyItemMainViewID.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));

        } else {
            // number is odd

            holder.rlvNotifyItemMainViewID.setBackgroundColor(mContext.getResources().getColor(R.color.allREview__color));
        }*/

        changeButtonVisibility(false,0.5f, holder);

        try {
            if(!mEditValueIDList.get(position).equalsIgnoreCase("0"))
            {
                holder.edtVoltValueID.setText(mEditValueIDList.get(position));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.txtTittleNameID.setText(mSettingModelResponse.get(position).getMPName());
        holder.txtUnitNameID.setText(mSettingModelResponse.get(position).getUnit());

        mSettingModelResponse.get(position).getEditValue();



        holder.txtReadBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   baseRequest.showLoader();
                mClickPosition = position;

              //  mSettingModelResponse.get(position).getOffset();
               getDeviceMode(mSettingModelResponse.get(position).getAddress(), holder, holder.edtVoltValueID, mSettingModelResponse.get(position).getOffset());
               // changeButtonVisibility(true, 1.0f, holder);

            }
        });


        holder.txtSetBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseRequest.showLoader();
                mClickPosition = position;
                try {
                    edtValue = Integer.parseInt(holder.edtVoltValueID.getText().toString().trim());
                } catch (NumberFormatException e) {
                    baseRequest.hideLoader();
                    e.printStackTrace();
                }

                if (edtValue > mSettingModelResponse.get(position).getPMin() && edtValue < mSettingModelResponse.get(position).getPMax()) {

                    setDeviceMode(mSettingModelResponse.get(position).getAddress(), mSettingModelResponse.get(position).getOffset());
                    //
                    // changeButtonVisibility(true, 1.0f, holder);
                } else {
                   // baseRequest.hideLoader();
                    Toast.makeText(mContext, "Please enter value between min=" + mSettingModelResponse.get(position).getPMin() + " Max=" + mSettingModelResponse.get(position).getPMax(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.edtVoltValueID.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // doSomething();

                String vvvv = holder.edtVoltValueID.getText().toString().trim();

                if(vvvv.equalsIgnoreCase("") || vvvv.equalsIgnoreCase(null))
                {
                    changeButtonVisibility(false, 0.5f, holder);
                }
               /* else
                {
                    changeButtonVisibility(true, 1.0f, holder);
                }*/

            }
        });

    }

    @Override
    public int getItemCount() {
        // return galleryModelsList.size();
        if (mSettingModelResponse != null && mSettingModelResponse.size() > 0)
            return mSettingModelResponse.size();
        else
            return 0;
        // return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtTittleNameID, txtReadBTNID, txtSetBTNID, txtUnitNameID, txtMessageNumberID, txtMessageNameID;
        public EditText edtVoltValueID;

        public RelativeLayout rlvNotifyItemMainViewID;

        public ViewHolder(View v) {

            super(v);

            txtMessageNameID = (TextView) v.findViewById(R.id.txtMessageNameID);
            txtMessageNumberID = (TextView) v.findViewById(R.id.txtMessageNumberID);
            txtTittleNameID = (TextView) v.findViewById(R.id.txtTittleNameID);
            txtReadBTNID = (TextView) v.findViewById(R.id.txtReadBTNID);
            txtSetBTNID = (TextView) v.findViewById(R.id.txtSetBTNID);
            txtUnitNameID = (TextView) v.findViewById(R.id.txtUnitNameID);

            edtVoltValueID = (EditText) v.findViewById(R.id.edtVoltValueID);

          }
    }


    private void changeButtonVisibility(boolean state, float alphaRate, DeviceSettingAdapter.ViewHolder holder) {
        holder.txtSetBTNID.setEnabled(state);
        holder.txtSetBTNID.setAlpha(alphaRate);
    }


    public void getDeviceMode(final String address, final ViewHolder holder, final EditText edtVoltValueID, final int offsetValue)
    {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

      // baseRequest.showLoader();
        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        new Thread(new Runnable() {


            @Override
            public void run() {

                if (CustomUtility.isOnline(mContext)) {
                    try {
                        String obj = "";
                        param.clear();
                        param.add(new BasicNameValuePair("address1", address)); // speed mode
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "0"));          // 0 = read
                        param.add(new BasicNameValuePair("data1", "1"));               // start = 1
                        param.add(new BasicNameValuePair("OldData", "1"));
                        param.add(new BasicNameValuePair("UserId", MUserId));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile
                        //param.add(new BasicNameValuePair("offset1", "1"));
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                        if (!TextUtils.isEmpty(obj)) {
                            // progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                            //Log.d("Speed", obj );

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {

                                    progressDialog.dismiss();
                                    speed_mode = jo.getString("Longitude");
                                     mLat= jo.getString("Latitude");

                                    mEditValueIDList.set(mClickPosition,mLat);
                                    // holder.edtVoltValueID.setText(mLat);
                                    notifyDataSetChanged();

                                  /*  mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ///  addHeadersMonths();
                                         //   if
                                            mEditValueIDList.set(mClickPosition,mLat);
                                           // holder.edtVoltValueID.setText(mLat);
                                            notifyDataSetChanged();
                                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        }
                                    });
*/
                                 //   edtVoltValueID.setText(jo.getString("Latitude"));
                                    changeButtonVisibility(true, 1.0f, holder);
                                    // baseRequest.hideLoader();
                                }
                                // disconnect message
                                if (jo.getString("Result").equalsIgnoreCase("D")) {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_read_mode_fail));
                                }
                            }
                        }
                    } catch (Exception e) {
                          progressDialog.dismiss();
                        //  baseRequest.hideLoader();
                        e.printStackTrace();
                        Log.d("exce", "" + e);
                    }
                } else {
                    //yourCountDownTimer.cancel();
                    progressDialog.dismiss();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);
                }

            }
        }).start();

    }

    /*public void getDeviceMode(final String address, final ViewHolder holder, final EditText edtVoltValueID, final int offsetValue) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
      // baseRequest.showLoader();
        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (CustomUtility.isOnline(mContext)) {
                    try {
                        String obj = "";
                        param.clear();
                        param.add(new BasicNameValuePair("address1", address)); // speed mode
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "0"));          // 0 = read
                        param.add(new BasicNameValuePair("data1", "1"));               // start = 1
                        param.add(new BasicNameValuePair("OldData", "1"));
                        param.add(new BasicNameValuePair("UserId", MUserId));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile
                        //param.add(new BasicNameValuePair("offset1", "1"));
                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);

                        if (!TextUtils.isEmpty(obj)) {
                            // progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                            //Log.d("Speed", obj );

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                                    progressDialog.dismiss();
                                    speed_mode = jo.getString("Longitude");
                                     mLat= jo.getString("Latitude");
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ///  addHeadersMonths();
                                         //   if

                                            holder.edtVoltValueID.setText(mLat);
                                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        }
                                    });

                                 //   edtVoltValueID.setText(jo.getString("Latitude"));
                                    changeButtonVisibility(true, 1.0f, holder);
                                    // baseRequest.hideLoader();
                                }
                                // disconnect message
                                if (jo.getString("Result").equalsIgnoreCase("D")) {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    progressDialog.dismiss();
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_read_mode_fail));
                                }
                            }
                        }

                    } catch (Exception e) {
                          progressDialog.dismiss();
                        //  baseRequest.hideLoader();
                        e.printStackTrace();
                        Log.d("exce", "" + e);
                    }
                } else {
                    //yourCountDownTimer.cancel();
                    progressDialog.dismiss();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);

                }

            }
        }).start();

    }*/

    public void setDeviceMode(final String address, final int offsetValue) {

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

      // baseRequest.showLoader();
        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        new Thread(new Runnable() {

            @Override
            public void run() {

                if (CustomUtility.isOnline(mContext)) {

                    try {
                        String obj = "";
                        param.clear();
                        param.add(new BasicNameValuePair("address1", address)); // speed mode
                        param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                        param.add(new BasicNameValuePair("RW", "1"));        // 1 = write
                        param.add(new BasicNameValuePair("data1", "" + edtValue));     // 1 = auto 2 = manual
                        param.add(new BasicNameValuePair("OldData", old_data));
                        param.add(new BasicNameValuePair("UserId", MUserId));
                        param.add(new BasicNameValuePair("DeviceType", DeviceType));
                        //  param.add(new BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                        param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                        param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile

                        obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                        // Log.d("start", obj +start+"--"+stop);
                        // _rms D/Speend Mode: [{"Latitude":"2.0","Longitude":"MANUAL","Result":"2.0","CEnergyF":0.0,"CFlowF":0.0,"CTimeF":0.0}]
                        if (!TextUtils.isEmpty(obj)) {
                            //   progressDialog.dismiss();
                            JSONArray ja = new JSONArray(obj);
                            // Log.d("Speed12", obj );

                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                //success message
                                if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                                    progressDialog.dismiss();
                                    speed_mode = jo.getString("Longitude");
                                    CustomUtility.isSuccessDialog(mContext, mContext.getString(R.string.success), mContext.getString(R.string.parameter_set_successfully));

                                }
                                else if (jo.getString("Result").equalsIgnoreCase("D")) {
                                    progressDialog.dismiss();
                                    read_only = false;
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));

                                }

                                // command failed message
                                if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                                    progressDialog.dismiss();
                                    read_only = false;
                                    CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_set_mode_fail));

                                }
                            }
                            //  baseRequest.hideLoader();
                            progressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        // baseRequest.hideLoader();
                        Log.d("exce", "" + e);
                    }
                } else {
                    //yourCountDownTimer.cancel();
                    progressDialog.dismiss();
                    Message msg = new Message();
                    msg.obj = "Please on internet ( Mobile data )";
                    mHandler.sendMessage(msg);

                }

            }
        }).start();

    }

    /////////////////////////////////////////////




   /* public void setDeviceMode1(String address, final int offsetValue) {

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        //  String old_data = "null" ;
        //Log.d("speed_mode_param_value", speed_mode_param_value +"--"+  old_data) ;

        //baseRequest.showLoader();

        if (CustomUtility.isOnline(mContext)) {

            try {
                String obj = "";
                param.clear();
                param.add(new BasicNameValuePair("address1", address)); // speed mode
                param.add(new BasicNameValuePair("did1", DeviceNo)); // gps no
                param.add(new BasicNameValuePair("RW", "1"));        // 1 = write
                param.add(new BasicNameValuePair("data1", "" + edtValue));     // 1 = auto 2 = manual
                param.add(new BasicNameValuePair("OldData", old_data));
                param.add(new BasicNameValuePair("UserId", MUserId));
                param.add(new BasicNameValuePair("DeviceType", DeviceType));
                //  param.add(new BasicNameValuePair("IPAddress", "1454832434343645")); // IMEI no of mobile
                param.add(new BasicNameValuePair("IPAddress", CustomUtility.getDeviceId(mContext))); // IMEI no of mobile
                param.add(new BasicNameValuePair("offset1", offsetValue+"")); // IMEI no of mobile

                obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.START_STOP_MOTOR, param);
                // Log.d("start", obj +start+"--"+stop);
                // _rms D/Speend Mode: [{"Latitude":"2.0","Longitude":"MANUAL","Result":"2.0","CEnergyF":0.0,"CFlowF":0.0,"CTimeF":0.0}]
                if (!TextUtils.isEmpty(obj)) {
                 //   progressDialog.dismiss();
                    JSONArray ja = new JSONArray(obj);
                    // Log.d("Speed12", obj );

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);

                        //success message
                        if (jo.getString("Result").equalsIgnoreCase("2.0")) {
                            progressDialog.dismiss();
                            speed_mode = jo.getString("Longitude");
                            CustomUtility.isSuccessDialog(mContext, mContext.getString(R.string.success), mContext.getString(R.string.parameter_set_successfully));

                        }

                        // disconnect message
                        if (jo.getString("Result").equalsIgnoreCase("D")) {
                            read_only = false;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_device_disconnect));
                            progressDialog.dismiss();

                        }

                        // command failed message
                        if (!jo.getString("Result").equalsIgnoreCase("2.0") && !jo.getString("Result").equalsIgnoreCase("D")) {
                            read_only = false;
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_set_mode_fail));
                            progressDialog.dismiss();

                        }
                    }
                  //  baseRequest.hideLoader();
                }

            } catch (Exception e) {
                progressDialog.dismiss();
               // baseRequest.hideLoader();
                Log.d("exce", "" + e);
            }

        } else {
            progressDialog.dismiss();
          //  baseRequest.hideLoader();
            Message msg = new Message();
            msg.obj = "Please on internet ( Mobile data )";
            mHandler.sendMessage(msg);
        }


//            }
//        }).start();

//           }
//       });

    }*/


    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };


}


