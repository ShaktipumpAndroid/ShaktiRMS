package com.shaktipumps.shakti_rms.searchlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.FaultDetailActivity;
import com.shaktipumps.shakti_rms.bean.FaultDetail;
import com.shaktipumps.shakti_rms.bean.FaultSummary;
import com.shaktipumps.shakti_rms.other.CustomUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shakti on 19-Apr-18.
 */
public class FaultDetailListViewAdapter extends BaseAdapter {


Context mContext ;
    String otp_status = "false", sms_url = "null", otp = "";
    private ProgressDialog progressDialog;
    String DeviceType = "null", FaultBit = "null";

    LayoutInflater inflater;
    private List<FaultDetail> faultDetailSearchesList = null;
    private ArrayList<FaultDetail> arraylist;

    public FaultDetailListViewAdapter(Context context, List<FaultDetail> faultDetails) {
        mContext = context;
        this.faultDetailSearchesList = faultDetails;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<FaultDetail>();
        this.arraylist.addAll(faultDetailSearchesList);



    }

    public class ViewHolder {
        TextView faultName;
    }

    @Override
    public int getCount() {
        return faultDetailSearchesList.size();
    }

    @Override
    public FaultDetail getItem(int position) {
        return faultDetailSearchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fault_detail_search_listview, null);

            holder.faultName = (TextView) view.findViewById(R.id.faultName);

            view.setTag(holder);
        }

        else

        {
            holder = (ViewHolder) view.getTag();
        }


        holder.faultName.setText(faultDetailSearchesList.get(position).getFaultName());

//
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
//                DeviceType = faultDetailSearchesList.get(position).getFaultName() ;
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
//                                intent.putExtra("FaultBit",   FaultBit);
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
//            }
//        });

        return view;
    }

    // Filter Class
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());

        faultDetailSearchesList.clear();
        if (charText.length() == 0) {
            faultDetailSearchesList.addAll(arraylist);
        }
        else
        {
            for (FaultDetail cs : arraylist)
            {
                if (cs.getFaultName().toLowerCase(Locale.getDefault()).contains(charText) )
                {
                    faultDetailSearchesList.add(cs);
                }
            }
        }
        notifyDataSetChanged();
    }





}