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
import com.shaktipumps.shakti_rms.bean.GetPlant.FaultRecordResponse;
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


public class DeviceFaultParametrAdapter extends RecyclerView.Adapter<DeviceFaultParametrAdapter.ViewHolder> {
    private Context mContext;
private List<FaultRecordResponse> mFaultRecordResponse;


    public DeviceFaultParametrAdapter(Context mContext, List<FaultRecordResponse> mFaultRecordResponse) {
        // this.galleryModelsList = galleryModelsList;
        this.mContext = mContext;
        this.mFaultRecordResponse = mFaultRecordResponse;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.foult_item_row, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.txtDFoultValueID.setText(mFaultRecordResponse.get(position).getFaultName());
        holder.txtDNValueID.setText(mFaultRecordResponse.get(position).getDeviceNo());
        holder.txtDDateValueID.setText(mFaultRecordResponse.get(position).getFaultDate());
        holder.txtDtimeValueID.setText(mFaultRecordResponse.get(position).getFaultTime());


    }

    @Override
    public int getItemCount() {
        // return galleryModelsList.size();
        if (mFaultRecordResponse != null && mFaultRecordResponse.size() > 0)
            return mFaultRecordResponse.size();
        else
            return 0;
        // return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtDFoultValueID, txtDNValueID, txtDDateValueID, txtDtimeValueID;


        public RelativeLayout rlvNotifyItemMainViewID;

        public ViewHolder(View v) {

            super(v);

            txtDFoultValueID = (TextView) v.findViewById(R.id.txtDFoultValueID);
            txtDNValueID =  (TextView) v.findViewById(R.id.txtDNValueID);
            txtDDateValueID =  (TextView) v.findViewById(R.id.txtDDateValueID);
            txtDtimeValueID =   (TextView) v.findViewById(R.id.txtDtimeValueID);


          }
    }





    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };


}


