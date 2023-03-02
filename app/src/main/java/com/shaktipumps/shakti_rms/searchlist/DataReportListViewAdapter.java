package com.shaktipumps.shakti_rms.searchlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.DataReportDetailActivity;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataReportListViewAdapter extends BaseAdapter {
Context mContext ;
    private ProgressDialog progressDialog;
    String DeviceNo = "null" ,DeviceType = "null",
            MUserId = "null"  ,MDeviceId = "null",CustomerName="null";
    SharedPreferences pref ;
    int org_clientid = 0 ;
    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
    LayoutInflater inflater;
    private List<Customer_GPS_Search> dataReportSearchesList = null;
    private ArrayList<Customer_GPS_Search> arraylist;

    public DataReportListViewAdapter(Context context, List<Customer_GPS_Search> dataReportSearchesList) {
        mContext = context;
        this.dataReportSearchesList = dataReportSearchesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Customer_GPS_Search>();
        this.arraylist.addAll(dataReportSearchesList);

    }

    public class ViewHolder {
        TextView customer_name, deviceNo, start_gps, stop_gps, get_direction, fault_report, real_monitoring;
        ImageView  product_image;
    }

    @Override
    public int getCount() {
        return dataReportSearchesList.size();
    }

    @Override
    public Customer_GPS_Search getItem(int position) {
        return dataReportSearchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_data_report_search_listview, null);
            holder.customer_name = (TextView) view.findViewById(R.id.customer_name);
            holder.deviceNo = (TextView) view.findViewById(R.id.deviceNo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.customer_name.setText(dataReportSearchesList.get(position).getCustomer_name());
        holder.deviceNo.setText(dataReportSearchesList.get(position).getDeviceNo());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MUserId      =  dataReportSearchesList.get(position).getMUserId();
                DeviceType   =  dataReportSearchesList.get(position).getDeviceType();
                DeviceNo     =  dataReportSearchesList.get(position).getDeviceNo();
                CustomerName =  dataReportSearchesList.get(position).getCustomer_name();
                // check org client id
                pref = mContext.getSharedPreferences("MyPref",0 );
                org_clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ; // if invalid use 0

                Intent intent = new Intent(mContext,DataReportDetailActivity.class);
                intent.putExtra("MUserId",MUserId );
                intent.putExtra("DeviceType",DeviceType );
                intent.putExtra("DeviceNo",DeviceNo );
                intent.putExtra("CustomerName",CustomerName );
                intent.putExtra("from_date","" );
                intent.putExtra("from_date","" );
                intent.putExtra("periodically","0" );

                if (org_clientid != 0)
                {
                    // pass client id of selected client of org selection . this id is set in data report
                    intent.putExtra("ClientId",pref.getString("key_clientid_for_data_report", "0") );
                }
                else
                {
                    // pass client id 0 in case of single user login
                    intent.putExtra("ClientId","0" );
                }
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());

        dataReportSearchesList.clear();
        if (charText.length() == 0) {
            dataReportSearchesList.addAll(arraylist);
        }
        else
        {
            for (Customer_GPS_Search cs : arraylist)
            {
                if (cs.getCustomer_name().toLowerCase(Locale.getDefault()).contains(charText)||
                    cs.getDeviceNo().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    dataReportSearchesList.add(cs);
                }
            }
        }
        notifyDataSetChanged();
    }
}