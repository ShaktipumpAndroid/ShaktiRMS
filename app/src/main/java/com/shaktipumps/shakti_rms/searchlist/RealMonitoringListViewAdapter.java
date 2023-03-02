package com.shaktipumps.shakti_rms.searchlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.RealMonitoring;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shakti on 19-Apr-18.
 */
public class RealMonitoringListViewAdapter extends BaseAdapter {


Context mContext ;
    String otp_status = "false", sms_url = "null", otp = "";
    private ProgressDialog progressDialog;
    String name_gps = "null" , start = "null",stop = "null", userid = "null" ,DeviceNo = "null" ,DeviceType = "null",
    Mobile = "null",MUserId = "null", MDeviceId = "null";

    LayoutInflater inflater;
    private List<RealMonitoring> realMonitoringList = null;
    private ArrayList<RealMonitoring> arraylist;

    public RealMonitoringListViewAdapter(Context context, List<RealMonitoring> realMonitoringList) {
        mContext = context;
   //     Log.d("bhupendra",""+realMonitoringList.size());
        this.realMonitoringList = realMonitoringList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<RealMonitoring>();
        this.arraylist.addAll(realMonitoringList);

    }

    public class ViewHolder {
        TextView MPName,
                Unit,
                Value;
    }

    @Override
    public int getCount() {
        return realMonitoringList.size();
    }

    @Override
    public RealMonitoring getItem(int position) {
        return realMonitoringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.real_monitoring_search_listview, null);
            // Locate the TextViews in listview_item.xml
            // holder.customer_number = (TextView) view.findViewById(R.id.customer_number );
            holder.MPName = (TextView) view.findViewById(R.id.parameter_name);
            holder.Value = (TextView) view.findViewById(R.id.parameter_value);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        String parameter_name = realMonitoringList.get(position).getMPName() + " ( "+
                                realMonitoringList.get(position).getUnit() + " ) :";
        holder.MPName.setText(parameter_name);
        holder.Value.setText(realMonitoringList.get(position).getValue());

        //holder.Value.setTextColor( mContext .getResources().getColor(R.color.msg_success_color));
        holder.Value.setTextColor( mContext .getResources().getColor(R.color.shakti_blue));


        return view;
    }
}