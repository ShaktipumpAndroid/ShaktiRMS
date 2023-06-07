package com.shaktipumps.shakti_rms.searchlist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoAOneSS;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoElite;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoKLP;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoKLPGrid;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoKUSPC;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoNandi;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoNandiMicro;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoNikola;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoOLDKLP;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoPureGridTie;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoSAJ;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoShima;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoShimaTwoO;
import com.shaktipumps.shakti_rms.activity.ActivityDeviceDetailsInfoVeichi;
import com.shaktipumps.shakti_rms.activity.MainActivity;
import com.shaktipumps.shakti_rms.activity.PaymentOptionActivity;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.MapBean.MapModelView;
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.other.PermissionsIntent;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.rmslocaldb.DatabaseHelperTeacher;
import com.shaktipumps.shakti_rms.webservice.CustomHttpClient;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shakti on 19-Apr-18.
 */
public class CustomerGPSListViewAdapter extends BaseAdapter {

    Context mContext;
    String mMessage;
    int clientid = 0;
    private ProgressDialog progressDialog;

    String start = "null", stop = "null", userid = "null", DeviceNo = "null", DeviceType = "null",
            MUserId = "null", MDeviceId = "null", CustomerName = "null", Mobile = "null", otp = "null", sms_url = "null", delete_url = "null";

    Typeface font;

    int mmCheckStart = 0;
    int mmCheckStop = 0;

    SharedPreferences pref;

    private static int currentPosition = -1;

    final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
    LayoutInflater inflater;
    private List<Customer_GPS_Search> customerSearchesList = null;
    private ArrayList<Customer_GPS_Search> arraylist;
    CountDownTimer yourCountDownTimer;
    private BaseRequest baseRequest;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;

    private UUID mMyUDID;

    int mPostionFinal = 0;

    private static boolean success = false;
    private static Workbook wb = null;
    private static CellStyle cs = null;
    private static Sheet sheet1 = null;
    private static Row row;

    private static Cell c = null;

    private boolean mBoolflag = false;
    private DatabaseHelperTeacher databaseHelperTeacher;

    public CustomerGPSListViewAdapter(Context context, List<Customer_GPS_Search> customerSearchesList) {
        mContext = context;
        this.customerSearchesList = customerSearchesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Customer_GPS_Search>();
        this.arraylist.addAll(customerSearchesList);

        baseRequest = new BaseRequest(context);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        databaseHelperTeacher = new DatabaseHelperTeacher(context);/////////////////AUTHModelData base

    }

    public class ViewHolder {
        TextView customer_name,
                deviceNo,
                start_gps,
                stop_gps,
                device_name_valuetxt,
        //  deleteDevice ,
        device_setting,
                get_direction,
                fault_report,

        real_monitoring;

        RelativeLayout rlvCustomerNameID, rlvDeviceNumberID, rlvDeviceNameID;

        RelativeLayout rlvSideSetingAndDeletePopupID;
        RelativeLayout rlvDeviceImageContaonerHomeID;

        TextView txtSettingParametrID, txtDeleteParametrID, txtRenevBTNID;

        LinearLayout rlvDeviceBTNViewID;
        RelativeLayout rlvDeviceNameViewID, rlvEXPLayerID, rlvCrdInnerViewID;

        CardView OrderFiltercardViewID, OrderFiltercardViewID1, cardPaymentLayerID;

        ImageView product_image, deleteDevice, imgDevicePicID, imgMoterStatusID;

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

            pref = mContext.getSharedPreferences("MyPref", 0);
            clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0

            if (clientid == 9999) {
                clientid = 0;
            }
            //   font = Typeface.createFromAsset(   mContext.getAssets() ,"fonts/verdana.ttf");

            view = inflater.inflate(R.layout.customer_gps_search_listview, null);
            // Locate the TextViews in listview_item.xml
            // holder.customer_number = (TextView) view.findViewById(R.id.customer_number );
             holder.txtRenevBTNID = (TextView) view.findViewById(R.id.txtRenevBTNID );
            holder.rlvDeviceNameViewID = (RelativeLayout) view.findViewById(R.id.rlvDeviceNameViewID);
            holder.rlvEXPLayerID = (RelativeLayout) view.findViewById(R.id.rlvEXPLayerID);
            holder.rlvCrdInnerViewID = (RelativeLayout) view.findViewById(R.id.rlvCrdInnerViewID);
            holder.rlvDeviceBTNViewID = (LinearLayout) view.findViewById(R.id.rlvDeviceBTNViewID);
            holder.cardPaymentLayerID = (CardView) view.findViewById(R.id.cardPaymentLayerID);
            holder.OrderFiltercardViewID1 = (CardView) view.findViewById(R.id.OrderFiltercardViewID1);
            holder.OrderFiltercardViewID = (CardView) view.findViewById(R.id.OrderFiltercardViewID);
            holder.customer_name = (TextView) view.findViewById(R.id.customer_name);
            holder.deviceNo = (TextView) view.findViewById(R.id.deviceNo);
            holder.start_gps = (TextView) view.findViewById(R.id.start_gps);
            holder.stop_gps = (TextView) view.findViewById(R.id.stop_gps);
            holder.get_direction = (TextView) view.findViewById(R.id.gps_location);
            holder.product_image = (ImageView) view.findViewById(R.id.product_image);
            //holder.deleteDevice = (ImageView) view.findViewById(R.id.deleteDevice);
            holder.imgDevicePicID = (ImageView) view.findViewById(R.id.imgDevicePicID);
            holder.imgMoterStatusID = (ImageView) view.findViewById(R.id.imgMoterStatusID);
            holder.deleteDevice = (ImageView) view.findViewById(R.id.deleteDevice);
            holder.device_setting = (TextView) view.findViewById(R.id.device_setting);
            holder.device_name_valuetxt = (TextView) view.findViewById(R.id.device_name_valuetxt);
            // holder.fault_report = (TextView) view.findViewById(R.id.fault_report);
            holder.real_monitoring = (TextView) view.findViewById(R.id.real_monitoring);
            holder.txtSettingParametrID = (TextView) view.findViewById(R.id.txtSettingParametrID);
            holder.txtDeleteParametrID = (TextView) view.findViewById(R.id.txtDeleteParametrID);
            holder.rlvSideSetingAndDeletePopupID = (RelativeLayout) view.findViewById(R.id.rlvSideSetingAndDeletePopupID);
            holder.rlvDeviceImageContaonerHomeID = (RelativeLayout) view.findViewById(R.id.rlvDeviceImageContaonerHomeID);
            holder.rlvCustomerNameID = (RelativeLayout) view.findViewById(R.id.rlvCustomerNameID);
            holder.rlvDeviceNumberID = (RelativeLayout) view.findViewById(R.id.rlvDeviceNumberID);
            holder.rlvDeviceNameID = (RelativeLayout) view.findViewById(R.id.rlvDeviceNameID);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date currentDate1 = new Date();
        Date strDate1 = null;
        try {
            strDate1 = sdf.parse(customerSearchesList.get(position).getMobValidationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (clientid > 0) {
            holder.cardPaymentLayerID.setVisibility(View.GONE);
        }



        holder.txtRenevBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContext, PaymentOptionActivity.class);
                mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                mIntent.putExtra("mPos", position);
                mContext.startActivity(mIntent);

            }
        });

        holder.rlvDeviceNameViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String deviceModelType = customerSearchesList.get(position).getModelType();
                NewSolarVFD.DEVICE_NUMBER_PAYMENT = customerSearchesList.get(position).getDeviceNo();

                //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                Date currentDate = new Date();
                Date strDate = null;
                try {
                    strDate = sdf.parse(customerSearchesList.get(position).getMobValidationDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // if (clientid <= 0)
                if (clientid > 0)
                {
                    if ((deviceModelType.equalsIgnoreCase("17"))) {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoPureGridTie.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }else if ((deviceModelType.equalsIgnoreCase("71")) || (deviceModelType.equalsIgnoreCase("74"))) {
                   // Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
                    Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
                    mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                    // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                    mIntent.putExtra("mPos", position);
                    mContext.startActivity(mIntent);
                    }
                    else if ((deviceModelType.equalsIgnoreCase("69")))///1
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("82") || deviceModelType.equalsIgnoreCase("83"))//////6
                    {
                        //Intent mIntent = new Intent(mContext, USPCBridgeActivity.class);
                        // Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKUSPC.class);///original
                        //  Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoUSPC.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail",  (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if ((deviceModelType.equalsIgnoreCase("49")))
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("62")) {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("2")) {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("1"))
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if ( deviceModelType.equalsIgnoreCase("87"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("66"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("6A"))/////4
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("73"))/////nandi micro
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandiMicro.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("6"))//////6
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else if (deviceModelType.equalsIgnoreCase("7"))//////6
                    {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    }
                    else {
                        Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoAOneSS.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);


                    }
                }
                else {
                    //if()
                  /*  if (currentDate.after(strDate))
                    {
                        Intent mIntent = new Intent(mContext, EXPDummyPaymentActivity.class);
                        mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                        // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                        mIntent.putExtra("mPos", position);
                        mContext.startActivity(mIntent);
                    } else*/
                        {
                        if ((deviceModelType.equalsIgnoreCase("17"))) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoPureGridTie.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        }else if ((deviceModelType.equalsIgnoreCase("71")) || (deviceModelType.equalsIgnoreCase("74"))) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLPGrid.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if ((deviceModelType.equalsIgnoreCase("69")))///1
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKLP.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        }else if ((deviceModelType.equalsIgnoreCase("49"))) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNikola.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("62")) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoSAJ.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("2")) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoElite.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("1")) {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShima.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("87"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoShimaTwoO.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        }else if (deviceModelType.equalsIgnoreCase("66"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("6A"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandi.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        }else if (deviceModelType.equalsIgnoreCase("73"))/////4
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoNandiMicro.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("6"))//////6
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoVeichi.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("7"))//////6
                        {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoOLDKLP.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else if (deviceModelType.equalsIgnoreCase("82") || deviceModelType.equalsIgnoreCase("83"))//////6
                        //else if(deviceModelType.equalsIgnoreCase("69"))//////6
                        {
                            //Intent mIntent = new Intent(mContext, USPCBridgeActivity.class);
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoKUSPC.class);
                            //  Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoUSPC.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail",  (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);
                        } else {
                            Intent mIntent = new Intent(mContext, ActivityDeviceDetailsInfoAOneSS.class);
                            mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);

                           /* Intent mIntent = new Intent(mContext, Wifi_safe_Activity.class);
                            //Intent mIntent = new Intent(mContext, Wifi_Parameters_GetSet_Activity.class);
                           // mIntent.putExtra("mDeviceDetail", (Serializable) customerSearchesList);
                            // mIntent.putExtra("mDeviceDetail", (Serializable) arraylist);
                            //mIntent.putExtra("mPos", position);
                            mContext.startActivity(mIntent);*/
                        }
                    }
                }
            }
        });


        holder.rlvDeviceBTNViewID.setVisibility(View.GONE);

        if (position == 0) {
           // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.green_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.green_trans));

        } else if (position == 1) {
          //  holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.gray_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.gray_trans));
        } else if (position == 2) {
           // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.blue_trans));
        } else if (position == 3) {
          //  holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.red_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.red_trans));
        } else if (position == 4) {
           // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellow_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_trans));
        } else {
           // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.gray_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.gray_trans));
        }




        if (customerSearchesList.get(position).getIsLogin().equalsIgnoreCase("true")) {
            if (customerSearchesList.get(position).getPumpStatus().equalsIgnoreCase("true")) {
                holder.imgMoterStatusID.setImageResource(R.mipmap.green_ball);//////////////

                // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.green_trans));
                holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.green_new_san));

            } else {
                holder.imgMoterStatusID.setImageResource(R.mipmap.red_ball); //////////////
                // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.red_trans));
                holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.red_new_san));
            }
        } else {
            holder.imgMoterStatusID.setImageResource(R.mipmap.grey_ball);
            // holder.OrderFiltercardViewID.setCardBackgroundColor(mContext.getResources().getColor(R.color.gray_trans));
            holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.gray_trans));
           // holder.rlvCrdInnerViewID.setBackgroundColor(mContext.getResources().getColor(R.color.gray_new_san));
        }



        holder.customer_name.setText(customerSearchesList.get(position).getCustomer_name());
        // holder.customer_name.setTypeface(font);
        holder.deviceNo.setText(customerSearchesList.get(position).getDeviceNo());
        holder.device_name_valuetxt.setText(customerSearchesList.get(position).getTypeName());
        // holder.deviceNo.setTypeface(font);

        holder.product_image.setImageResource(R.drawable.logo_blue);

        // hide delete button form org dash board
        if (clientid != 0) {
            holder.deleteDevice.setVisibility(View.GONE);
        }
        try {
            Picasso.with(mContext).load(customerSearchesList.get(position).getDeviceImage()).placeholder(R.drawable.logo_blue).into(holder.imgDevicePicID);
        } catch (Exception e) {
            e.printStackTrace();
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        param.clear();


        holder.rlvSideSetingAndDeletePopupID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.rlvSideSetingAndDeletePopupID.setVisibility(View.GONE);
            }
        });


        holder.deleteDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // holder.rlvSideSetingAndDeletePopupID.setVisibility(View.VISIBLE);
                //   setingAnddeletFunction(mContext, customerSearchesList, position);
                backConfirmPopupID(mContext, customerSearchesList, position);
            }
        });

        // holder.deleteDevice.setOnClickListener(new View.OnClickListener() {
        holder.txtDeleteParametrID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.rlvSideSetingAndDeletePopupID.setVisibility(View.GONE);

                MUserId = "";
                DeviceNo = "";
                DeviceType = "";
                MDeviceId = "";

                MUserId = customerSearchesList.get(position).getMUserId();
                DeviceNo = customerSearchesList.get(position).getDeviceNo();
                DeviceType = customerSearchesList.get(position).getDeviceType();
                CustomerName = customerSearchesList.get(position).getCustomer_name();
                // Mobile = customerSearchesList.get(position).getMobile();
                MDeviceId = customerSearchesList.get(position).getMDeviceId();

                //** this mobile no is enter by user when new account open
                Mobile = pref.getString("key_mobile_number", "invalid_mobno");
                if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                    delete_device_confirmation();

                } else {
                    /////////////////add bluetooth cod here
                }
                // delete_device_confirmation();

            }
        });

        try {
            if (clientid > 0)
            {
                //holder.cardPaymentLayerID.setVisibility(View.GONE);
            }
            else
            {

               /* if(position == 0)
                {
                    callInsertAndUpdateAPI(position, customerSearchesList.get(position).getMUserId(), customerSearchesList.get(position).getDeviceNo());

                }*/
            //    callInsertAndUpdateAPI(position, customerSearchesList.get(position).getMUserId(), customerSearchesList.get(position).getDeviceNo());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // callInsertAndUpdateAPI(position, customerSearchesList.get(position).getMUserId(), customerSearchesList.get(position).getDeviceNo());


        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        customerSearchesList.clear();
        if (charText.length() == 0) {
            customerSearchesList.addAll(arraylist);
        } else {
            for (Customer_GPS_Search cs : arraylist) {
                if (cs.getCustomer_name().toLowerCase(Locale.getDefault()).contains(charText) ||
                        cs.getDeviceNo().toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    customerSearchesList.add(cs);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void callInsertAndUpdateAPI(final int pos, String mUserIDID, String mDeviceIDIID) {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    ForgotPassModelView mForgotPassModelView = gson.fromJson(Json, ForgotPassModelView.class);
                    if (mForgotPassModelView.getStatus()) {

                        // if(arraylist.size()-1 ==  pos)
                        //    Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                        ///password reset sucessfully
                        // Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE = 2;////finish old screen
                        // finish();

                    } else {
                        Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //  getDeviceSettingListResponse(mSettingModelView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("userId", mUserIDID);
            jsonObject.addProperty("deviceNo", mDeviceIDIID);
            jsonObject.addProperty("fcmToken", NewSolarVFD.FCM_TOKEN);
            jsonObject.addProperty("imei", NewSolarVFD.IMEI_NUMBER);



            System.out.println("RMSVIKAS   userId=" + mUserIDID + ", deviceNo=" + mDeviceIDIID + ", fcmToken=" + NewSolarVFD.FCM_TOKEN + ", imei=" + NewSolarVFD.IMEI_NUMBER);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("gggggg==>>"+jsonObject);
        //  baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.INSERTUPDATE_FCM_API);/////
        //baseRequest.callAPIPut(1, jsonObject, NewSolarVFD.ORG_RESET_FORGOTPASS);/////
    }

    public void delete_device_confirmation() {

        String msg;

        msg = "Do you want to delete device :" + DeviceNo;
        new AlertDialog.Builder(mContext)

                .setTitle("Delete Device Alert !")
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //************  code comment **********
                        // SendOTP();

                       // delete_from_server();
                        callDeleteDeviceAPI();
                      //  NewSolarVFD.callDeleteDeviceAPIForAll(baseRequest,mContext, MUserId, MDeviceId);
                        dialog.cancel();
                        dialog.dismiss();

                    }
                })


                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                        dialog.cancel();
                        dialog.dismiss();

                    }
                })
                .show();


    }


    private void callDeleteDeviceAPI() {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here

                    MapModelView mMapModelView = gson.fromJson(Json, MapModelView.class);

                    if(mMapModelView.getStatus())
                    {

                        Toast.makeText(mContext, mMapModelView.getMessage(), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mContext, MainActivity.class);
                         mContext.startActivity(intent);
                        ((Activity) mContext).finish();
                    }
                    else
                    {
                        Toast.makeText(mContext, mMapModelView.getMessage(), Toast.LENGTH_LONG).show();
                    }


                    JSONObject jo = new JSONObject(Json);

                    String mStatus = jo.getString("status");
                    final String mMessage = jo.getString("message");
                    String jo11 = jo.getString("response");
                    System.out.println("jo11==>>"+jo11);
                    if (mStatus.equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();
                        baseRequest.hideLoader();
                    }

                    // getUpdatedOTPResponse(mMapModelView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                baseRequest.hideLoader();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
                baseRequest.hideLoader();
            }
        });

        Map<String, String> wordsByKey = new HashMap<>();

        wordsByKey.put("MUserId", MUserId);
        wordsByKey.put("MDeviceId", MDeviceId);
       // wordsByKey.put("ClientId", ClientId);
        //   wordsByKey.put("IMEI","38648723487236487264");

        baseRequest.callAPIGET(1, wordsByKey, NewSolarVFD.DELETE_DEVICE1);/////

    }

    public void delete_from_server() {

        ArrayList<String> al;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        al = new ArrayList<>();

        final ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        final ArrayList<NameValuePair> otp_param = new ArrayList<NameValuePair>();
        param.clear();

        delete_url = NewSolarVFD.DELETE_DEVICE + "?" + "MUserId=" + MUserId + "&" + "MDeviceId=" + MDeviceId;
        System.out.println("baseURL==>>"+delete_url);
/******************************************************************************************/
/*                   server connection
/******************************************************************************************/
        progressDialog = ProgressDialog.show(mContext, "", "Please wait !");

        //Log.d("delete_otp1",MuserId +"--"+  MDeviceId +"--"+ mobile_number +"otp"+Delete_OTP );

        new Thread() {

            public void run() {

                if (CustomUtility.isOnline(mContext)) {

                    try {

                        String obj = CustomHttpClient.executeHttpGet(delete_url);
                        //  Log.d("update_otp",""+ obj) ;

                        //   Log.d("delete_obj", "" + obj);
                        if (obj.equalsIgnoreCase("Successfully")) {

/******************************************************************************************/
/*                       get JSONwebservice AUTHModelData
/******************************************************************************************/
                            progressDialog.dismiss();

                            Message msg2 = new Message();
                            msg2.obj = "Device Delete Successfully .";
                            mHandler.sendMessage(msg2);

                            ((Activity) mContext).finish();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);

                        } else {
                            progressDialog.dismiss();
                            CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_connection));
                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        CustomUtility.isErrorDialog(mContext, mContext.getString(R.string.error), mContext.getString(R.string.err_connection));
                        Log.d("exce", "" + e);
                    }

                } else {
                    progressDialog.dismiss();
                    CustomUtility.isErrorDialog(mContext, "Error", "No Internet Connection");
                }

            }

        }.start();
    }

    public static void check_Permission(final Context context) {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PermissionsIntent.READ_PHONE_STATE);

        } else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PermissionsIntent.READ_PHONE_STATE);
        }
    }

    @SuppressLint("HandlerLeak")
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String mString = (String) msg.obj;
            Toast.makeText(mContext, mString, Toast.LENGTH_LONG).show();
        }
    };


    public void backConfirmPopupID(final Context context, final List<Customer_GPS_Search> customerSearchesList, final int pos) {
        ////////////////////////////
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.alertDialogDelete);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //  builder.setTitle(getString(R.string.Back_head));
        builder.setMessage(mContext.getString(R.string.delete_msg_text));

        builder.setCancelable(false);
        String positiveText = mContext.getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss alert dialog, update preferences with game score and restart play fragment

                        MUserId = "";
                        DeviceNo = "";
                        DeviceType = "";
                        MDeviceId = "";

                        //MUserId = customerSearchesList.get(pos).getMUserId();
                        MUserId = pref.getString("key_muserid", "invalid_muserid");
                        DeviceNo = customerSearchesList.get(pos).getDeviceNo();
                        DeviceType = customerSearchesList.get(pos).getDeviceType();
                        CustomerName = customerSearchesList.get(pos).getCustomer_name();
                        // Mobile = customerSearchesList.get(position).getMobile();
                        MDeviceId = customerSearchesList.get(pos).getMDeviceId();

                        //** this mobile no is enter by user when new account open
                        Mobile = pref.getString("key_mobile_number", "invalid_mobno");

                        if (Constant.CHECK_FOR_WORK_WITH_BT_OR_IN == 0) {
                            //  delete_device_confirmation();

                            databaseHelperTeacher.deleteDeviceROWData(MDeviceId, MUserId);
                           // delete_from_server();
                            callDeleteDeviceAPI();
                          //  NewSolarVFD.callDeleteDeviceAPIForAll(baseRequest,mContext, MUserId, MDeviceId);

                        } else {
                            /////////////////add bluetooth cod here
                        }
                        // Constant.CHECK_FOR_WORK_WITH_BT_OR_IN = 0;/////////this is for internet
                        dialog.dismiss();

                        Log.d("myTag", "positive button clicked");
                        dialog.dismiss();
                    }
                });

        String negativeText = mContext.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss dialog, start counter again
                        dialog.dismiss();
                        Log.d("myTag", "negative button clicked");
                    }
                });

        AlertDialog dialog = builder.create();
// display dialog
        dialog.show();

    }

}