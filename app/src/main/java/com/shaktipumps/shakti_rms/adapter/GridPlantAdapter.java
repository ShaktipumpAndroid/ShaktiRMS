package com.shaktipumps.shakti_rms.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.GRID.ActivityKLPGridDashbord;
import com.shaktipumps.shakti_rms.bean.GetPlant.GetPlantResponse;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import org.apache.http.NameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by votive on 26/04/18.
 */

public class GridPlantAdapter extends RecyclerView.Adapter<GridPlantAdapter.ViewHolder> {

    private BluetoothSocket btSocket;
    private BluetoothAdapter myBluetooth;
    float mTotalTimeFloatData;
    private UUID mMyUDID;
    private InputStream iStream;
    char mCRCFinalValue;
    // private List<MenuModelResponse> menuModelsList;
    private List<String> nameList;
    private Context mContext;


    private BaseRequest baseRequest;



    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    String[] mSNameArray = {"Aata Chakki", "Cold Storage Refrigerator", "Motor-Thresher Set", "Motor-Pump Set", "Motor-Fodder Cutting Set"};
    int[] mimgNameArray = {R.drawable.chakki_set, R.drawable.deep_freser, R.drawable.thresher_set, R.drawable.pump_set, R.drawable.cutting_set};

    int mPosition;
    private Context mActivity;

    private String mDeviceNo;
    private String muserid;
    private String mDeviceType;

    private String mModelType;
    private List<GetPlantResponse> mGetPlantResponse;


    // public USPCBridgeAdapter(List<MenuModelResponse> menuModelsList, Activity mContext)
    public GridPlantAdapter(Context mContext, Activity mActivity, List<GetPlantResponse> mGetPlantResponse) {
        this.mContext = mContext;
        this.mActivity = mActivity;

        this.mGetPlantResponse = mGetPlantResponse;

        baseRequest = new BaseRequest(mActivity);

    }


    @Override
    public GridPlantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.grid_plant_adapter_item, parent, false);

        final GridPlantAdapter.ViewHolder viewHolder1 = new GridPlantAdapter.ViewHolder(view1);


        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(GridPlantAdapter.ViewHolder holder, final int position) {

        /*   holder.rlvMenuMainView.getLayoutParams().width = UtilMethod.getDeviceHeightWidth(mContext, true)/2;*/
       // holder.lvlMainViewDataContainerID.getLayoutParams().height = UtilMethod.getDeviceHeightWidth(mContext, false) / 6 - 40;
        // holder.tvNameAndPriceTag.setText(mSNameArray[position]);
        // holder.mImgMenu.setImageResource(mimgNameArray[position]);
        holder.txtPlantNameValueID.setText(mGetPlantResponse.get(position).getPlantName());
        holder.txtPlantAddressValueID.setText(mGetPlantResponse.get(position).getPlantAddress());
        holder.lvlMainViewDataContainerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContext, ActivityKLPGridDashbord.class);
                mIntent.putExtra("PlantID", mGetPlantResponse.get(position).getPid());
                mIntent.putExtra("PlantName", mGetPlantResponse.get(position).getPlantName());
                mContext.startActivity(mIntent);
            }
        });
        //holder.mImgMenu.setImageResource(mimgNameArray[position]);
      /*  Picasso.with(mContext).load(mUspcModelResponse.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgOptionTypeID);*/
       /* int mFixedSelectedValue = 0;

        try {
            mFixedSelectedValue = Integer.parseInt(mGetPlantResponse.getUSPCAPPIDValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }*/
        // String mIndexSelectedValue = mUspcModelResponse.get(position).get;





    }

    @Override
    public int getItemCount() {
           return mGetPlantResponse.size();
        //return mSNameArray.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtPlantNameValueID;
        public TextView txtPlantAddressValueID;
        public RelativeLayout lvlMainViewDataContainerID;



        public ViewHolder(View v) {

            super(v);

            txtPlantNameValueID = (TextView) v.findViewById(R.id.txtPlantNameValueID);
            txtPlantAddressValueID = (TextView) v.findViewById(R.id.txtPlantAddressValueID);
            lvlMainViewDataContainerID = (RelativeLayout) v.findViewById(R.id.lvlMainViewDataContainerID);


        }
    }


}

