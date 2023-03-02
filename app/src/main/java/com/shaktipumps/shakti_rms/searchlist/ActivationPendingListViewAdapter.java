package com.shaktipumps.shakti_rms.searchlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.DeviceOTPActivity;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




/**
 * Created by shakti on 19-Apr-18.
 */
public class ActivationPendingListViewAdapter extends BaseAdapter {


Context mContext ;
    String otp_status = "false", sms_url = "null", otp = "";
    private ProgressDialog progressDialog;
    String name_gps = "null" , start = "null",stop = "null", userid = "null" ,DeviceNo = "null" ,DeviceType = "null",
    Mobile = "null",MUserId = "null", MDeviceId = "null";

    LayoutInflater inflater;
    private List<Customer_GPS_Search> customerSearchesList = null;
    private ArrayList<Customer_GPS_Search> arraylist;

    public ActivationPendingListViewAdapter(Context context, List<Customer_GPS_Search> customerSearchesList) {
        mContext = context;
        this.customerSearchesList = customerSearchesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Customer_GPS_Search>();
        this.arraylist.addAll(customerSearchesList);



    }

    public class ViewHolder {
        TextView customer_name,
                deviceNo;



    }

    @Override
    public int getCount() {
        return customerSearchesList.size();
    }

    @Override
    public Customer_GPS_Search getItem(int position) {
        return customerSearchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activation_pending_search_listview, null);
            // Locate the TextViews in listview_item.xml
            // holder.customer_number = (TextView) view.findViewById(R.id.customer_number );
            holder.customer_name = (TextView) view.findViewById(R.id.customer_name);
            holder.deviceNo = (TextView) view.findViewById(R.id.deviceNo);


            view.setTag(holder);


        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.customer_name.setText(customerSearchesList.get(position).getCustomer_name());
        holder.deviceNo.setText(customerSearchesList.get(position).getDeviceNo());

        DeviceNo = customerSearchesList.get(position).getDeviceNo();
        DeviceType = customerSearchesList.get(position).getDeviceType();
        Mobile = customerSearchesList.get(position).getMobile();
        MUserId = customerSearchesList.get(position).getMUserId();
        MDeviceId = customerSearchesList.get(position).getMDeviceId();




         view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                reSendOTP();



            }
        });









        return view;
    }

    // Filter Class
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());

        customerSearchesList.clear();
        if (charText.length() == 0) {
            customerSearchesList.addAll(arraylist);
        }
        else
        {
            for (Customer_GPS_Search cs : arraylist)
            {
                if (cs.getCustomer_name().toLowerCase(Locale.getDefault()).contains(charText) ||
                    cs.getDeviceNo().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    customerSearchesList.add(cs);
                }
            }
        }
        notifyDataSetChanged();
    }


    private void reSendOTP() {



        otp = ""+((int)(Math.random()*9000)+1000);

        progressDialog = ProgressDialog.show(mContext, "", "Please wait !");


        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();

        param.clear();
        otp_param.clear();



        otp_param.add(new BasicNameValuePair("MUserId", MUserId));
        otp_param.add(new BasicNameValuePair("OTP", otp));
        otp_param.add(new BasicNameValuePair("MDeviceId",MDeviceId ));

        //   Log.d("update_device",MuserId +"--"+otp +"--"+MDeviceId);


        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(mContext)) {

                    try     {


                        String otp_return = CustomHttpClient.executeHttpPost1(NewSolarVFD.UPDATE_DEVICE_OTP, otp_param);
                        //    Log.d("update_device",otp_return);

                        JSONObject otp_json = new JSONObject(otp_return);



                        //  Log.d("update_device",otp_return);


                        otp_status = otp_json.getString("Status");

                        // otp_status = true i.e otp is save on server database
                        if (otp_status.equalsIgnoreCase("true"))
                        {



                            param.clear();

                            //**** get sms string ************************
                            String sms_obj = CustomHttpClient.executeHttpPost1(NewSolarVFD.SMS_URL, param);


                            JSONObject sms_response = new JSONObject(sms_obj);

                            sms_url = sms_response.getString("SMS_URL");


                            //**** end sms string ************************


                            if (!sms_url.equalsIgnoreCase("null"))
                            {

                                String send_sms = sms_url.replaceAll("<reci_addr>",Mobile).trim();

                                String msg = "Shakti Remote Monitoring Verification OTP is" + "  " + otp;



                                msg = URLEncoder.encode(msg);

                                send_sms = send_sms.replaceAll("<message>", msg).trim();


                                // send sms
                                String sms_return = CustomHttpClient.executeHttpGet(send_sms);






                                Intent intent = new Intent(mContext, DeviceOTPActivity.class);
                                intent.putExtra("Mobile",Mobile );
                                intent.putExtra("MUserId",MUserId );
                                intent.putExtra("MDeviceId",MDeviceId );
                                mContext.startActivity(intent);


                                progressDialog.dismiss();



                            }

                        }


                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(mContext,mContext.getString(R.string.error),mContext.getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }


                }

                else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(mContext,"Error","No Internet Connection");

                }


            }

        }.start();

    }


}