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
import com.shaktipumps.shakti_rms.activity.FaultDetailActivity;
import com.shaktipumps.shakti_rms.activity.MapsActivity;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.FaultSummary;
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
public class FaultListViewAdapter extends BaseAdapter {


Context mContext ;

    private ProgressDialog progressDialog;
    String DeviceType = "null", FaultBit = "null", faultDate="null",deviceNo="null" ;

    LayoutInflater inflater;
    private List<FaultSummary> faultSummarySearchesList = null;
    private ArrayList<FaultSummary> arraylist;

    public FaultListViewAdapter(Context context, List<FaultSummary> faultSummary) {
        mContext = context;
        this.faultSummarySearchesList = faultSummary;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<FaultSummary>();
        this.arraylist.addAll(faultSummarySearchesList);



    }

    public class ViewHolder {
        TextView faultCount,
                faultDate,
                faultBit,
                deviceNo;




    }

    @Override
    public int getCount() {
        return faultSummarySearchesList.size();
    }

    @Override
    public FaultSummary getItem(int position) {
        return faultSummarySearchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fault_summary_search_listview, null);
            // Locate the TextViews in listview_item.xml
            // holder.customer_number = (TextView) view.findViewById(R.id.customer_number );
            holder.faultCount = (TextView) view.findViewById(R.id.fault_count);
            holder.deviceNo = (TextView) view.findViewById(R.id.device_number);
            holder.faultDate = (TextView) view.findViewById(R.id.fault_date);
            holder.faultBit = (TextView) view.findViewById(R.id.fault_name);



            view.setTag(holder);


        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.faultDate.setText(faultSummarySearchesList.get(position).getDate());
        holder.deviceNo.setText(faultSummarySearchesList.get(position).getDeviceNo());
        holder.faultCount.setText(faultSummarySearchesList.get(position).getFalutCount());
        holder.faultBit.setText(faultSummarySearchesList.get(position).getFaultBit());




// code comment for 2.2 app version fault name will show on first page

//         view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                progressDialog = new ProgressDialog(mContext);
//                progressDialog.setMessage("Loading..."); // Setting Message
//                progressDialog.setTitle("Please wait..."); // Setting Title
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//                progressDialog.show(); // Display Progress Dialog
//                progressDialog.setCancelable(false);
//
//
//                DeviceType = faultSummarySearchesList.get(position).getDeviceType() ;
//                FaultBit   = faultSummarySearchesList.get(position).getFaultBit() ;
//                faultDate = faultSummarySearchesList.get(position).getDate();
//                deviceNo  = faultSummarySearchesList.get(position).getDeviceNo();
//
//
//                new Thread(new Runnable() {
//                    public void run() {
//                        try {
//
//
//                            if (CustomUtility.isOnline(  mContext))
//                            {
//                                progressDialog.dismiss();
//                                Intent intent = new Intent(mContext, FaultDetailActivity.class);
//                                intent.putExtra("DeviceType",DeviceType );
//                                intent.putExtra("FaultBit",  FaultBit);
//                                intent.putExtra("faultDate",faultDate);
//                                intent.putExtra("deviceNo", deviceNo);
//
//
//                                mContext.startActivity(intent);
//
//                            }
//                            else
//                            {
//                                progressDialog.dismiss();
//                                CustomUtility.isErrorDialog(  mContext, "Error", "No Internet Connection");
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
//
//
//
//
//            }
//        });
//








        return view;
    }

    // Filter Class
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());

        faultSummarySearchesList.clear();
        if (charText.length() == 0) {
            faultSummarySearchesList.addAll(arraylist);
        }
        else
        {
            for (FaultSummary cs : arraylist)
            {
                if (cs.getDeviceNo().toLowerCase(Locale.getDefault()).contains(charText) ||
                    cs.getDate().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    faultSummarySearchesList.add(cs);
                }
            }
        }
        notifyDataSetChanged();
    }





}