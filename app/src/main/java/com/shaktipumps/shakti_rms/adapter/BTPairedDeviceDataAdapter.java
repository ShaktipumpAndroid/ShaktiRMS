package com.shaktipumps.shakti_rms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.R;

import java.util.List;


public class BTPairedDeviceDataAdapter extends RecyclerView.Adapter<BTPairedDeviceDataAdapter.ViewHolder> {

    private Context mContext;

    private List mDeviceNameList;
    private List mDeviceMACAddressList;
    //private HomeUserNameClickListener mHomeUserNameClickListener;

    public BTPairedDeviceDataAdapter(Context mContext, List mDeviceNameList, List mDeviceMACAddressList) {

        this.mDeviceNameList = mDeviceNameList;
        this.mDeviceMACAddressList = mDeviceMACAddressList;
        // this.mHomeUserNameClickListener = mHomeUserNameClickListener;
        this.mContext = mContext;

    }

    public BTPairedDeviceDataAdapter(String SSS, Context mContext) {
        // this.galleryModelsList = galleryModelsList;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.bt_paired_device_data_item, parent, false);
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

        //changeButtonVisibility(false,0.5f, holder);
        holder.txtBTNameID.setText(mDeviceNameList.get(position).toString());
        holder.txtBTMACAddressID.setText(mDeviceMACAddressList.get(position).toString());

        holder.txtBTMACAddressID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  changeButtonVisibility(true,1.0f, holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        // return galleryModelsList.size();
        if (mDeviceMACAddressList != null && mDeviceMACAddressList.size() > 0)
            return mDeviceMACAddressList.size();
        else
            return 0;
         // return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtBTNameID, txtBTMACAddressID;


        public ViewHolder(View v) {

            super(v);


            txtBTNameID = (TextView) v.findViewById(R.id.txtBTNameID);
            txtBTMACAddressID = (TextView) v.findViewById(R.id.txtBTMACAddressID);







        }
    }



   /* private void changeButtonVisibility(boolean state, float alphaRate, BTPairedDeviceAdapter.ViewHolder holder) {
        holder.txtSetBTNID.setEnabled(state);
        holder.txtSetBTNID.setAlpha(alphaRate);
    }*/
}


