package com.shaktipumps.shakti_rms.searchlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.DeviceSettingActivity;
import com.shaktipumps.shakti_rms.activity.FaultReportActivity;
import com.shaktipumps.shakti_rms.activity.TimeZoneActivity;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.TimeZoneName;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shakti on 12-Apr-19.
 */
public class TimeZoneListViewAdapter extends BaseAdapter {

    Context mContext ;
    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
    LayoutInflater inflater;
    private List<TimeZoneName> timeZoneNames = null;
    private ArrayList<TimeZoneName> arraylist;

    public  TimeZoneListViewAdapter(Context context, List<TimeZoneName> timeZoneNames) {
        mContext = context;
        this.timeZoneNames = timeZoneNames;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<TimeZoneName>();
        this.arraylist.addAll(timeZoneNames);

    }

    public class ViewHolder {
        TextView time_zone_city,
                 time_zone_short,
                  time_zone_long ;




    ImageView product_image;

}

    @Override
    public int getCount() {
        return timeZoneNames.size();
    }

    @Override
    public TimeZoneName getItem(int position) {
        return timeZoneNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();


//            pref = mContext.getSharedPreferences("MyPref",0 );
//            clientid = Integer.parseInt(pref.getString("key_clientid", "0")) ; // if invalid use 0





            //   font = Typeface.createFromAsset(   mContext.getAssets() ,"fonts/verdana.ttf");


            view = inflater.inflate(R.layout.login_time_zone, null);
            // Locate the TextViews in listview_item.xml
            // holder.customer_number = (TextView) view.findViewById(R.id.customer_number );
            holder.time_zone_city = (TextView) view.findViewById(R.id.time_zone_city);
            holder.time_zone_short = (TextView) view.findViewById(R.id.time_zone_short);
            holder.time_zone_long = (TextView) view.findViewById(R.id.time_zone_long);


            view.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }


        holder.time_zone_city.setText(timeZoneNames.get(position).getTimezone_city());
        holder.time_zone_short.setText(timeZoneNames.get(position).getTimezone_short());
        holder.time_zone_long.setText(timeZoneNames.get(position).getTimezone_long());
        // holder.customer_name.setTypeface(font);







        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, TimeZoneActivity.class);

                ((Activity)mContext).startActivityForResult(intent, 555);

//               Intent intent = new Intent(mContext, TimeZoneActivity.class);
//                ((Activity)mContext).finish();
//                mContext.startActivity(intent);
//

            }
        });









        return view;
    }





}
