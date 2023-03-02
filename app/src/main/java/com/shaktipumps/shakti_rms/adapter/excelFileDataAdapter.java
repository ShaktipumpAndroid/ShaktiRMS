package com.shaktipumps.shakti_rms.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import java.util.List;


public class excelFileDataAdapter extends RecyclerView.Adapter<excelFileDataAdapter.ViewHolder> {

    private Context mContext;

    private List<String> mFileNameList;
    private List<String> mFilePathList;
    private BaseRequest baseRequest;

    String MUserId = "null", DeviceType = "null", DeviceNo = "null";
    private ProgressDialog progressDialog;
    String speed_mode = "null";

    boolean read_only = true;
    private int edtValue = 2;
    private String old_data = "1";
    Dialog dialog;
    //private HomeUserNameClickListener mHomeUserNameClickListener;

    public excelFileDataAdapter(Context mContext, Dialog dialog,  List<String > mFileNameList, List<String > mFilePathList, String DeviceType, String DeviceNo, String MUserId) {

        this.mFileNameList = mFileNameList;
        this.mFilePathList = mFilePathList;
        //this.mHomeUserNameClickListener = mHomeUserNameClickListener;
        this.mContext = mContext;
        this.DeviceType = DeviceType;
        this.MUserId = MUserId;
        this.DeviceNo = DeviceNo;
        this.dialog = dialog;

        baseRequest = new BaseRequest(mContext);
    }

    public excelFileDataAdapter(String SSS, Context mContext) {
        // this.galleryModelsList = galleryModelsList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.file_upload_item, parent, false);
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
       // holder.txtTittleNameID.setText(mSettingModelResponse.get(position).getMPName());
       // holder.txtUnitNameID.setText(mSettingModelResponse.get(position).getUnit());

       holder.txtFileNameID.setText(mFileNameList.get(position));

        holder.txtFileNameID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        // return galleryModelsList.size();
        if (mFileNameList != null && mFileNameList.size() > 0)
            return mFileNameList.size();
        else
            return 0;
        // return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {




        public TextView txtFileNameID;

        public RelativeLayout rlvNotifyItemMainViewID;

        public ViewHolder(View v) {

            super(v);

            txtFileNameID = (TextView) v.findViewById(R.id.txtFileNameID);




           /* txtNotifyTittleID = v.findViewById(R.id.txtNotifyTittleID);
            txtNotifyByUserID = v.findViewById(R.id.txtNotifyByUserID);
            txtNotifyMinuteDayID = v.findViewById(R.id.txtNotifyMinuteDayID);
            rlvNotifyItemMainViewID = v.findViewById(R.id.rlvNotifyItemMainViewID);*/


        }
    }


    private void changeButtonVisibility(boolean state, float alphaRate, excelFileDataAdapter.ViewHolder holder) {
       // holder.txtSetBTNID.setEnabled(state);
     //   holder.txtSetBTNID.setAlpha(alphaRate);
    }









    /////////////////////////////////////////////







    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };


}


