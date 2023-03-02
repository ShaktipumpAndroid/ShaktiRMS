package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.UtilMethod;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.model.UploadModel.ProfileUpdateModel;
import com.shaktipumps.shakti_rms.other.CustomUtility;
import com.shaktipumps.shakti_rms.retrofit.ApiClient;
import com.shaktipumps.shakti_rms.retrofit.ApiInterface;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.webservice.GlobalMethod;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Thread.sleep;


public class SimhaTwoODATAExtractionActivity extends AppCompatActivity {

    private LinearLayout lvlMainDataViewContaonerId;
    private CardView cardFaultsID, cardOneYeardongleID, cardFifteenDaysID, cardFiveYearID, donglecard15thD;
    private int mCheckBackBTNStatus = 0;
    public static final String UPLOAD_URL = "http://internetfaqs.net/AndroidPdfUpload/upload.php";
    private String mDateCommanString = "";
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    int klkl = 0;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Uri to store the image uri
    private static List<String> mFileNameList;
    private static List<String> mFilePathList;
    // private static HSSFWorkbook wb;
    private Context mContext;

    private List<String> mMonthHeaderList;
    private List<String> mDayHeaderList;

    private List<String> mDayDataList;
    // private List<int[]> mDayDataList;
    private List<Integer> mDayDataListInt;

    TableLayout mTableData;
    TableLayout mTableDataHeader;

    private int mVandA = 0;
    private RecyclerView rclSettingListViewID;

    private LinearLayoutManager lLayout;
    private RecyclerView.Adapter recyclerViewAdapter;
    private BaseRequest baseRequest;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    private TextView txtDoanloadFileBTNID;
    private TextView txtUploadBTNID;
    private TextView txtdongle15thD;
    private TextView txtfaultExtractionID;
    private TextView txtPlusZommBTNID;
    private TextView txtMinusZommBTNID;
    private RelativeLayout rlvdongleOneYearBTNID, rlvYearBTNID, rlvDayBTNID, rlvFualtBTNID, rlvdongle15thD;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;

    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;
    InputStream iStream;
    private String mBtNameHead;
    private String mDeviceType;
    private String mModelType;

    private UUID mMyUDID;
    int mPostionFinal = 0;

    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    private String mBtMacAddressHead;
    private String mCommanCommandForAll;

    private static boolean success = false;
    private static Workbook wb = null;
    private static CellStyle cs = null;
    private static Sheet sheet1 = null;
    private static Row row;
    private static Cell c = null;
    private boolean mBoolflag = false;
    private boolean mBoolflagCheck = false;
    int kk = 0;
    int mvDay = 0;
    int mvMonth = 0;
    int mvYear = 0;
    int mvHournew = 0;
    int mvMinutenew = 0;
    String mvFaultnew = "";

    private static Workbook wb1 = null;
    private static CellStyle cs1 = null;
    private static Sheet sheet11 = null;
    private static Row row1;
    private static Cell c1 = null;
    private boolean mBoolflag1 = false;
    private int mCheckCLICKDayORMonth = 0;
    int kk1 = 0;
    /////////////////day
    boolean vkFinalcheck = false;

    int mvDay1 = 0;
    int mvMonth1 = 0;
    int mvYear1 = 0;
    //float value for read
    int mvTotalEnergy = 0;
    int mvTotalFlow = 0;
    int mvTotalTime = 0;

    /////////////this is for months
    float fvTotalEnergy = 0;
    float fvTotalFlow = 0;
    float fvTotalTime = 0;
    int mmCount = 0;

    String mvHour;
    String mvMinute;
    String mvNo_of_Start;
    String mvFrequency;
    String mvMotor_Voltage;
    String mvMotor_Current;
    String mvRPM;
    String mvLPM;
    String mvPVVoltage;
    String mvPVCurrent;
    String mvFault;
    String mvInvTemp;
    float fvFrequency = 0;
    float fvRMSVoltage = 0;
    float fvOutputCurrent = 0;
    float fvLPM = 0;
    float fvPVVoltage = 0;
    float fvPVCurrent = 0;
    float fvInvTemp = 0;

    String filePath;
    File file;
    private byte[] image;
    private String fileActualPath;
    private int mLengthCount;

    int[] mTotalTime;
    int pp = 0;

    RelativeLayout rlvBackViewID;
    private String headerLenght = "";
    private String headerLenghtDAy = "";
    private String headerLenghtMonth = "";
    private String headerLenghtDayDongle = "";
    private String headerLenghtMonthDongle = "";
    private String headerLenghtFalt = "";

    private RelativeLayout rlvLoadingViewID;
    private TextView txtHeadingLabelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_btdatalist_shimhatwozero);
        mContext = this;
        initView();
    }

    private void initView() {
        //setGIFLoadingView(mContext);
        //ShowLoadingView();
     //   takePermissions();
        System.out.println("Build.VERSION.SDK_INT==>>" + Build.VERSION.SDK_INT + "Build.VERSION_CODES.Q==>>" + Build.VERSION_CODES.Q);

        baseRequest = new BaseRequest(this);
        mMonthHeaderList = new ArrayList<>();
        mDayHeaderList = new ArrayList<>();
        mDayDataList = new ArrayList<>();

        mModelType = getIntent().getStringExtra("ModelType");
        mDeviceType = getIntent().getStringExtra("DeviceType");
        mBtNameHead = getIntent().getStringExtra("BtNameHead");
        mBtMacAddressHead = getIntent().getStringExtra("BtMacAddressHead");
        //  rclSettingListViewID = (RecyclerView) findViewById(R.id.rclSettingListViewID);
        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        rlvYearBTNID = (RelativeLayout) findViewById(R.id.rlvYearBTNID);
        rlvdongleOneYearBTNID = (RelativeLayout) findViewById(R.id.rlvdongleOneYearBTNID);
        rlvDayBTNID = (RelativeLayout) findViewById(R.id.rlvDayBTNID);
        rlvFualtBTNID = (RelativeLayout) findViewById(R.id.rlvFualtBTNID);

        rlvdongle15thD = (RelativeLayout) findViewById(R.id.rlvdongle15thD);
        donglecard15thD = (CardView) findViewById(R.id.donglecard15thD);
        cardFaultsID = (CardView) findViewById(R.id.cardFaultsID);
        cardOneYeardongleID = (CardView) findViewById(R.id.cardOneYeardongleID);
        cardFifteenDaysID = (CardView) findViewById(R.id.cardFifteenDaysID);
        cardFiveYearID = (CardView) findViewById(R.id.cardFiveYearID);
        lvlMainDataViewContaonerId = (LinearLayout) findViewById(R.id.lvlMainDataViewContaonerId);

        mTableData = (TableLayout) findViewById(R.id.table);
        mTableDataHeader = (TableLayout) findViewById(R.id.table1);
        txtUploadBTNID = (TextView) findViewById(R.id.txtUploadBTNID);
        txtdongle15thD = (TextView) findViewById(R.id.txtdongle15thD);
        txtfaultExtractionID = (TextView) findViewById(R.id.txtfaultExtractionID);
        //txtDoanloadFileBTNID = (TextView) findViewById(R.id.txtDoanloadFileBTNID);
        txtMinusZommBTNID = (TextView) findViewById(R.id.txtMinusZommBTNID);
        txtPlusZommBTNID = (TextView) findViewById(R.id.txtPlusZommBTNID);

        rlvLoadingViewID = (RelativeLayout) findViewById(R.id.rlvLoadingViewID);
        txtHeadingLabelID = (TextView) findViewById(R.id.txtHeadingLabelID);
        //  changeButtonVisibility(false, 0.5f, txtDoanloadFileBTNID);
        changeButtonVisibility(false, 0.5f, txtUploadBTNID);
        //  changeButtonVisibility(false, 0.5f, txtUploadBTNID);
        // lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        //  rclSettingListViewID.setNestedScrollingEnabled(false);
        //  rclSettingListViewID.setLayoutManager(lLayout);
        //...
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(BTReceiver, filter);
        setClickEventListner();
        //    saveExcelFile(mContext, "VikasTest.xls",mPostionFinal);

        if (mModelType.equalsIgnoreCase("17")) {

            cardOneYeardongleID.setVisibility(View.VISIBLE);//VISIBLE
            cardFiveYearID.setVisibility(View.VISIBLE);
            cardFifteenDaysID.setVisibility(View.VISIBLE);
            cardFaultsID.setVisibility(View.VISIBLE);//VISIBLE
            donglecard15thD.setVisibility(View.GONE);//VISIBLE
            //lvlMainDataViewContaonerId.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 4f));
            lvlMainDataViewContaonerId.setWeightSum(4f);
            //lvlMainDataViewContaonerId.setWeightSum(2f);
            mCommanCommandForAll = "";
        } else if (mModelType.equalsIgnoreCase("49")) {

            cardOneYeardongleID.setVisibility(View.GONE);//VISIBLE
            cardFiveYearID.setVisibility(View.GONE);
            cardFifteenDaysID.setVisibility(View.GONE);
            cardFaultsID.setVisibility(View.GONE);
            donglecard15thD.setVisibility(View.GONE);
            // lvlMainDataViewContaonerId.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
            lvlMainDataViewContaonerId.setWeightSum(1f);

            mCommanCommandForAll = "";
        } else if (mModelType.equalsIgnoreCase("1")) {

            cardOneYeardongleID.setVisibility(View.GONE);//VISIBLE
            cardFiveYearID.setVisibility(View.VISIBLE);
            cardFifteenDaysID.setVisibility(View.VISIBLE);
            cardFaultsID.setVisibility(View.GONE);
            donglecard15thD.setVisibility(View.GONE);
            //lvlMainDataViewContaonerId.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3f));
            lvlMainDataViewContaonerId.setWeightSum(2f);
            // lvlMainDataViewContaonerId.setWeightSum(3f);

            mCommanCommandForAll = "";
        } else if (mModelType.equalsIgnoreCase("87") || mModelType.equalsIgnoreCase("95")) {

            cardOneYeardongleID.setVisibility(View.VISIBLE);//VISIBLE
            cardFiveYearID.setVisibility(View.VISIBLE);
            cardFifteenDaysID.setVisibility(View.VISIBLE);
            cardFaultsID.setVisibility(View.VISIBLE);
            donglecard15thD.setVisibility(View.VISIBLE);
            //lvlMainDataViewContaonerId.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3f));
            // lvlMainDataViewContaonerId.setWeightSum(5f);
            // lvlMainDataViewContaonerId.setWeightSum(5f);
            // lvlMainDataViewContaonerId.setWeightSum(3f);

            mCommanCommandForAll = "";
        } else if (mModelType.equalsIgnoreCase("8")) {

            cardOneYeardongleID.setVisibility(View.GONE);
            cardFiveYearID.setVisibility(View.GONE);
            cardFifteenDaysID.setVisibility(View.GONE);
            cardFaultsID.setVisibility(View.GONE);
            donglecard15thD.setVisibility(View.GONE);
            // lvlMainDataViewContaonerId.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 4f));
            lvlMainDataViewContaonerId.setWeightSum(4f);

            mCommanCommandForAll = "";
        }
        //txtfaultExtractionID


    }


    //The BroadcastReceiver that listens for bluetooth broadcasts
    private final BroadcastReceiver BTReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                //Do something if connected
                Toast.makeText(getApplicationContext(), "BT Connected", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                //Do something if disconnected
                rlvLoadingViewID.setVisibility(View.GONE);
                finish();
                Toast.makeText(getApplicationContext(), "BT Disconnected", Toast.LENGTH_SHORT).show();
            }
            //else if...
        }
    };

    private void setClickEventListner() {

      /*  txtDoanloadFileBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, "Your File is download Successfully.", Toast.LENGTH_SHORT).show();
                //  changeButtonVisibility(false, 0.5f, txtDoanloadFileBTNID);
                changeButtonVisibility(true, 1.0f, txtUploadBTNID);
            }
        });
*/

        rlvLoadingViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBackBTNStatus == 0) {
                    finish();
                } else {
                    backConfirmPopupID();
                }

            }
        });

        txtUploadBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestStoragePermission();
                UploadFileToServerOption(mContext);
                //showFileChooser();
                // fileUplaodToServer(mContext);
                //AllPopupUtil.fileUplaodToServer(mContext);
            }
        });


        rlvdongleOneYearBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mMyUDID = "00001101-0000-1000-8000-M0100000000E";
                //  mMyUDID = UUID.fromString("00001101-0000-1000-8000-M0100000000E");
                try {
                    // btSocket = null;
                    mTableData.removeAllViews();
                    mTableDataHeader.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kk = 0;
                mmCount = 0;
                mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//////////////this is fixed for blue tooth deviceee data
                mBoolflag = false;
                mPostionFinal = 0;
                mCheckCLICKDayORMonth = 0;
                if (mMonthHeaderList.size() > 0)
                    mMonthHeaderList.clear();

                rlvLoadingViewID.setVisibility(View.VISIBLE);

                txtHeadingLabelID.setText("Dongle 5 Years data tranfer on bluetooth. Please wait..");
                new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
            }
        });

//////////////////15 day's data
        rlvYearBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mMyUDID = "00001101-0000-1000-8000-M0100000000E";
                //  mMyUDID = UUID.fromString("00001101-0000-1000-8000-M0100000000E");
                kk = 0;
                mmCount = 0;
                try {
                    //  btSocket = null;
                    mTableData.removeAllViews();
                    mTableDataHeader.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                mPostionFinal = 0;
                mBoolflag = false;
                mCheckCLICKDayORMonth = 1;/////////months
                // new BluetoothCommunicationForFirstActivity().execute("M0000000000E", "M0000000000E", "START");
                //    new BluetoothCommunicationForFirstActivity().execute(":MDATA#", ":MDATA#", "START");
                if (mMonthHeaderList.size() > 0)
                    mMonthHeaderList.clear();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        rlvLoadingViewID.setVisibility(View.VISIBLE);
                    }
                });

                txtHeadingLabelID.setText("1 Year data tranfer on bluetooth. Please wait..");
                new BluetoothCommunicationGetMonthParameter().execute(":MLENGTH#", ":MDATA#", "START");
            }
        });

        rlvDayBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // btSocket = null;
                    mTableData.removeAllViews();
                    mTableDataHeader.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kk = 0;
                mmCount = 0;
                mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//////////////this is fixed for blue tooth deviceee data
                mBoolflag = false;
                mPostionFinal = 0;
                mCheckCLICKDayORMonth = 0;
                if (mMonthHeaderList.size() > 0)
                    mMonthHeaderList.clear();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        rlvLoadingViewID.setVisibility(View.VISIBLE);
                    }
                });

                txtHeadingLabelID.setText("5 Years dara tranfer on bluetooth. Please wait..");
                new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
            }
        });


//////////////////////vikas new code

        rlvdongle15thD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kk = 0;
                mmCount = 0;
                try {
                    //  btSocket = null;
                    mTableData.removeAllViews();
                    mTableDataHeader.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                mPostionFinal = 0;
                mBoolflag = false;
                mCheckCLICKDayORMonth = 1;/////////months
                // new BluetoothCommunicationForYdataEXTActivity().execute("M0000000000E", "M0000000000E", "START");
                //    new BluetoothCommunicationForYdataEXTActivity().execute(":YDATA#", ":YDATA#", "START");
                if (mMonthHeaderList.size() > 0)
                    mMonthHeaderList.clear();
                monthPopupOpen(mContext);

                // new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");

            }
        });


        rlvFualtBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // btSocket = null;
                    mTableData.removeAllViews();
                    mTableDataHeader.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kk = 0;
                mmCount = 0;
                mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//////////////this is fixed for blue tooth deviceee data
                mBoolflag = false;
                mPostionFinal = 0;
                mCheckCLICKDayORMonth = 0;
                if (mMonthHeaderList.size() > 0)
                    mMonthHeaderList.clear();

                new BluetoothCommunicationGetFaultParameter().execute(":FLENGTH#", ":FLENGTH#", "OKAY");

            }
        });

    }


    //////////////////vikas new code

    //////////////////vikas new code end


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForYdataEXTActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            kk = 0;
            mBoolflag = false;
            mPostionFinal = 0;
            //baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {

                // btSocket.close();
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection
                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);
                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(300);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        System.out.println("vikas--1==>1");
                        //baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    System.out.print("bytesRead==?");
                    for (int i = 0; i < 12; i++) {
                        try {
                            bytesRead = iStream.read();
                            System.out.print(bytesRead);
                        } catch (IOException e) {
                            System.out.println("vikas--2==>2" + bytesRead);
                            //baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
                    System.out.println();

                    int[] bytesReaded;
                    String spspsp = "";
                    int koko = 0;
                    int jjkk = 0;
                    int counter = 0;

                    while (true) {

                        bytesReaded = new int[mLengthCount];
                        int jk = 0;
                        int i = 0;
                        //  bytesReaded[i] = b[i];
                        int kp = 0;
                        System.out.print("spspsp==>>" + jjkk + " =");

                        // for (int j = 0; j < 128; j++)
                        for (int j = 0; j < 125; j++) {
                            //  bytesReaded[kp]=Math.abs(b[j]);jk
                            bytesReaded[kp] = iStream.read();
                            System.out.print(bytesReaded[kp] + " ");
                            kp++;
                            if ("TX".equalsIgnoreCase((char) bytesReaded[0] + "" + (char) bytesReaded[1])) {
                                baseRequest.hideLoader();
                                System.out.println("TX_COMPLETE_i==" + i);
                                mBoolflag = true;
                                break;
                            }
                        }
                        System.out.println();
                        if (bytesReaded[0] == 255 && bytesReaded[1] == 255) {

                            //baseRequest.hideLoader();
                            vkFinalcheck = true;
                            System.out.println("TX_COMPLETE_ghgi==" + i);
                            mBoolflag = true;
                            //break;
                            // continue;

                        }

                        jjkk++;
                        System.out.println("Main_while_i==" + jjkk + " = " + (char) bytesReaded[0] + ", " + (char) bytesReaded[1]);
                        if ("TX".equalsIgnoreCase((char) bytesReaded[0] + "" + (char) bytesReaded[1])) {
                            baseRequest.hideLoader();
                            System.out.println("TX_COMPLETE_i==" + i);
                            mBoolflag = true;
                            break;
                        } else {
                            jk = 0;
                            mTotalTime = new int[10];

                            for (int k = 0; k < 5; k++) {
                                //System.out.println("first_loop_i=="+k);
                                mTotalTime = Arrays.copyOf(mTotalTime, jk + 1);
                                long d;
                                mTotalTime[jk] = bytesReaded[k];

                                System.out.println("float_jk==" + jk + " " + Float.intBitsToFloat(mTotalTime[jk]));

                                jk++;

                            }

                            for (int k = 5; k < 125; ) {
                                //System.out.println("first_loop_i=="+k);
                                mTotalTime = Arrays.copyOf(mTotalTime, jk + 1);

                                mTotalTime[jk] = bytesReaded[k];
                                mTotalTime[jk] |= bytesReaded[k + 1] << 8;
                                mTotalTime[jk] |= bytesReaded[k + 2] << 16;
                                mTotalTime[jk] |= bytesReaded[k + 3] << 24;
                                System.out.println("float_jk==" + jk + " " + Float.intBitsToFloat(mTotalTime[jk]));

                                jk++;
                                k += 4;

                            }

                         /*   for (int k = 2; k < 114; ) {
                                //System.out.println("first_loop_i=="+k);
                                mTotalTime = Arrays.copyOf(mTotalTime, jk + 1);
                                long d;
                                mTotalTime[jk] = bytesReaded[k];
                                mTotalTime[jk] |= bytesReaded[k + 1] << 8;
                                mTotalTime[jk] |= bytesReaded[k + 2] << 16;
                                mTotalTime[jk] |= bytesReaded[k + 3] << 24;
                                System.out.println("float_jk==" + jk + " " + Float.intBitsToFloat(mTotalTime[jk]));

                                jk++;
                                k += 4;

                            }
                            for (int k = 114; k < 128; ) {
                                //bytesReaded[k] = iStream.read(b);
                                System.out.println("Second_loop==" + k);
                                mTotalTime = Arrays.copyOf(mTotalTime, jk + 1);
                                mTotalTime[jk] |= bytesReaded[k] << 8;
                                mTotalTime[jk] |= bytesReaded[k + 1];

                                jk++;
                                k += 2;

                            }*/
                        }


                        float fFrequency = 0;
                        //  wb = new HSSFWorkbook();

                        if (!mBoolflag) {
                            //  wb = new HSSFWorkbook();
                            kk++;
                            System.out.println("kk++ ==>> " + kk);
                            /*if(kk == 536)
                            {
                                System.out.println("kk++ ==>> " + kk);
                            }*/
                        } else {
                            System.out.println("vikas--n==>4");
                            // wb = new HSSFWorkbook();
                            File file = new File(mContext.getExternalFilesDir(null), "DongleMonth" + NewSolarVFD.mNumberOfMonth + "_" + mBtNameHead + ".xls");
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;
                            System.out.println("vikas--4==>4");

                            System.out.println("kkk++ ==>> " + kk);
                            //baseRequest.hideLoader();
                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                System.out.println("printStackTrace ---001");
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    System.out.println("vikas--5==>5");
                                    // baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            // myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            // Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            //  break;
                        }
                        //  if((mDay == 255) && (mMonth == 255) && (mYear == 255) && (mHour == 255) && (mMinut == 255) && (mStatus == 255))
                        //if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 0) && (mYear == 0)))
                        // saveExcelFile(mContext, "VikasTest.xls", mPostionFinal,mDay,mMonth,mYear,mHour,mMinut,mStatus,mFrequency,mRMSVoltage,mOutputCurrent,mRPM,mLPM,mPVVoltage,mPVCurrent,mFault,mInvTemp);

                        if (vkFinalcheck) {
                            System.out.println("Nothing do it ...");
                        } else {
                            if (mPostionFinal == 0) {
                                System.out.println("mPostionFinal==000 " + mPostionFinal);
                                //New Workbook
                                wb = new HSSFWorkbook();
                                //Cell style for header row
                                cs = wb.createCellStyle();
                                cs.setFillForegroundColor(HSSFColor.LIME.index);
                                cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                //New Sheet
                                sheet1 = wb.createSheet("myOrder");
                                row = sheet1.createRow(0);

                                for (int k = 0; k < mMonthHeaderList.size(); k++) {
                                    String[] mStringSplitStart = mMonthHeaderList.get(k).split("-");
                                    sheet1.setColumnWidth(k, (10 * 200));
                                    c = row.createCell(k);
                                    // c.setCellValue(mMonthHeaderList.get(k));
                                    c.setCellValue(mStringSplitStart[0]);
                                    System.out.println("HEADER+++>>> " + mStringSplitStart[0]);
                                    c.setCellStyle(cs);
                                }

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    // for (int j = 6; j < mMonthHeaderList.size(); j++) {
                                    row = sheet1.createRow(mPostionFinal + 1);
                                    for (int j = 0; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);
                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        mmIntt = Integer.parseInt(mStringSplitStart[1]);

                                        try {
                                            if (mmIntt == 1) {
                                                sheet1.setColumnWidth(j, (10 * 200));
                                                //  fFrequency = mTotalTime[j];
                                                if (j > 4) {
                                                    fFrequency = Float.intBitsToFloat(mTotalTime[j]) / ((float) mmIntt);
                                                } else {
                                                    fFrequency = mTotalTime[j];
                                                }
                                                c = row.createCell(j);
                                                c.setCellValue("" + fFrequency);
                                                c.setCellStyle(cs);
                                                System.out.println("fFrequency===>>>vk1==>>" + fFrequency);
                                                // tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            } else {
                                                sheet1.setColumnWidth(j, (10 * 200));
                                                fFrequency = mTotalTime[j];

                                                if (j > 4) {
                                                    fFrequency = Float.intBitsToFloat(mTotalTime[j]) / ((float) mmIntt);
                                                } else {
                                                    fFrequency = mTotalTime[j];
                                                }
                                                // float mmValue = (((float) mTotalTime[j]) / ((float) mmIntt));
                                                c = row.createCell(j);
                                                // c.setCellValue("" + fFrequency);
                                                c.setCellValue("" + fFrequency);
                                                c.setCellStyle(cs);
                                                System.out.println("mmValue===>>>vk1==>>" + fFrequency);
                                                //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            }
                                        } catch (Exception e) {
                                            //   baseRequest.hideLoader();
                                            e.printStackTrace();
                                        }
                                        /*sheet1.setColumnWidth(j, (10 * 200));
                                        fFrequency = mTotalTime[j];
                                        c = row.createCell(j);
                                        c.setCellValue("" + fFrequency);
                                        c.setCellStyle(cs);*/
                                    }

                                    mPostionFinal = mPostionFinal + 1;
                                } catch (NumberFormatException e) {
                                    System.out.println("printStackTrace++ ==>> ");
                                    e.printStackTrace();
                                    //      baseRequest.hideLoader();
                                }
                            } else {


                                System.out.println("mPostionFinal >= " + mPostionFinal);
                                // cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                row = sheet1.createRow(mPostionFinal + 1);

                             /*   try {
                                    for (int j = 0; j < mMonthHeaderList.size(); j++){

                                        sheet1.setColumnWidth(j, (10 * 200));
                                        fFrequency = mTotalTime[j];
                                        c = row.createCell(j);
                                        c.setCellValue("" + fFrequency);
                                        c.setCellStyle(cs);
                                    }
                                } catch (Exception e) {
                                    System.out.println("vikas--1o==>1o");
                                    e.printStackTrace();
                                }*/


                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    // for (int j = 6; j < mMonthHeaderList.size(); j++) {
                                    for (int j = 0; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);
                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        mmIntt = Integer.parseInt(mStringSplitStart[1]);

                                        try {
                                            if (mmIntt == 1) {
                                                sheet1.setColumnWidth(j, (10 * 200));
                                                //  if (j > 28)
                                                if (j > 4) {
                                                    fFrequency = Float.intBitsToFloat(mTotalTime[j]) / ((float) mmIntt);
                                                } else {
                                                    fFrequency = mTotalTime[j];
                                                }
                                                c = row.createCell(j);
                                                c.setCellValue("" + fFrequency);
                                                c.setCellStyle(cs);
                                                System.out.println("fFrequency===>>>vkkkk1==>>" + fFrequency);
                                                // tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            } else {
                                                sheet1.setColumnWidth(j, (10 * 200));
                                                // fFrequency = mTotalTime[j];
                                                if (j > 4) {
                                                    fFrequency = Float.intBitsToFloat(mTotalTime[j]) / ((float) mmIntt);
                                                } else {
                                                    fFrequency = mTotalTime[j];
                                                }
                                                //float mmValue = (((float) mTotalTime[j]) / ((float) mmIntt));
                                                c = row.createCell(j);
                                                // c.setCellValue("" + fFrequency);
                                                c.setCellValue("" + fFrequency);
                                                c.setCellStyle(cs);

                                                System.out.println("mmValue===>>>vkppp1==>>" + fFrequency);
                                                //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            }
                                        } catch (Exception e) {
                                            //   baseRequest.hideLoader();
                                            e.printStackTrace();
                                        }

                                        /*sheet1.setColumnWidth(j, (10 * 200));
                                        fFrequency = mTotalTime[j];

                                        c = row.createCell(j);
                                        c.setCellValue("" + fFrequency);
                                        c.setCellStyle(cs);*/
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("printStackTrace++ ==>> ");
                                    e.printStackTrace();
                                    //      baseRequest.hideLoader();
                                }
                            }


                            System.out.println("vikas--n==>4");
                            //   wb = new HSSFWorkbook();
                            File file = new File(mContext.getExternalFilesDir(null), "DongleMonth" + NewSolarVFD.mNumberOfMonth + "_" + mBtNameHead + ".xls");
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;
                            System.out.println("vikas--4==>4");

                            System.out.println("kkk++ ==>> " + kk);
                            //baseRequest.hideLoader();
                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                System.out.println("printStackTrace ---001");
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    System.out.println("vikas--5==>5");
                                    // baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            // myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);


                            mPostionFinal++;
                        }


                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("vikas--8==>8");
                // baseRequest.hideLoader();
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            // baseRequest.hideLoader();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    rlvLoadingViewID.setVisibility(View.GONE);
                }
            });

            super.onPostExecute(result);
            //btSocket = null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForONEYEARDATA extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            kk = 0;
            mBoolflag = false;
            //   baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                // btSocket.close();
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection
                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);
                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        System.out.println("vikas--1==>1");
                        //baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    for (int i = 0; i < 8; i++) {
                        int mCharOne1 = iStream.read();
                        System.out.println("vikas_king--3==>" + mCharOne1);
                    }


                    int[] bytesReaded;
                    //   while (iStream.available() > 0)
                    while (true) {
                        int mDay = 0;
                        int mMonth = 0;
                        int mYear = 0;
                        int mHour = 0;
                        int mMinut = 0;

                        float fFrequency = 0;


                        bytesReaded = new int[mLengthCount];
                        for (int i = 0; i < mLengthCount; i++) {
                            // Character mCharOne = (char) iStream.read();
                            //  Character mCharTwo = (char) iStream.read();
                            int mCharOne = 0;
                            int mCharTwo = 0;

                            try {
                                mCharOne = iStream.read();
                                mCharTwo = iStream.read();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                System.out.println("vikas--3==>" + mCharOne + "" + mCharTwo);
                                if ("TX".equalsIgnoreCase((char) mCharOne + "" + (char) mCharTwo)) {
                                    // baseRequest.hideLoader();
                                    mBoolflag = true;
                                    mBoolflagCheck = false;

                                } else {
//                                    if ("DA".equalsIgnoreCase((char) mCharOne + "" + (char) mCharTwo)) {
                                    String sssssss = "";
                                    int mCheckFloatvalue = 0;
                                    sssssss = String.valueOf((char) Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16));
                                    if ("D".equalsIgnoreCase(sssssss)) {
                                        mVandA = 0;
                                    }
                                    if (mVandA == 0) {
                                        mVandA = 1;
                                        sssssss = String.valueOf((char) Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16));
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        sssssss = sssssss + String.valueOf((char) Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16));
                                        System.out.println("sssssss===>>>" + sssssss);
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                    }

                                    if ("DA".equalsIgnoreCase(sssssss)) {
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        mDay = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        mMonth = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        mYear = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        mHour = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                                        mCharOne = iStream.read();
                                        mCharTwo = iStream.read();
                                        mMinut = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);

                                        mDateCommanString = mDay + "-" + mMonth + "-" + mYear;

                                        mBoolflagCheck = true;
                                        //
                                        break;
                                    } else {
                                        mBoolflagCheck = false;
                                        int kv = 0;
                                        kv++;
                                        if (i > 6) {
                                            kv = i - 1;
                                        }
                                        if (i == 6) {

                                            mCheckFloatvalue = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                                            mCharOne = iStream.read();
                                            mCharTwo = iStream.read();
                                            mCheckFloatvalue = mCheckFloatvalue + Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);
                                            System.out.println("sssssss===>>>" + mCheckFloatvalue);

                                            bytesReaded[i] = mCheckFloatvalue;// iStream.read();
                                            i = 7;
                                        } else {
                                            if (mCharOne == 0 || mCharTwo == 0) {
                                                bytesReaded[kv] = Integer.parseInt("" + mCharOne + mCharTwo, 16);// iStream.read();
                                            } else {
                                                bytesReaded[kv] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);// iStream.read();
                                            }
                                        }


                                    }
                                }
                            } catch (NumberFormatException e) {
                                baseRequest.hideLoader();
                                System.out.println("vikas--3==>N");
                                e.printStackTrace();
                            }
                        }

                        // if (!mBoolflag) {
                        if (mBoolflagCheck) {

                        } else {
                            if (!mBoolflag) {
                                kk++;

                                System.out.println("kk++ ==>> " + kk);

                                mTotalTime = new int[mLengthCount - 1];
                                // int i = 6;
                                // System.out.println("bytesReadednm==>> ");
                                // for (int k = 0; k < mLengthCount; k += 2)
                                int tt = 0;
                                for (int k = 0; k < mLengthCount - 1; k++) {
                                    //mTotalTime = Arrays.copyOf(mTotalTime, i + 1);
                              /*  mTotalTime[k] = bytesReaded[k] << 8;
                                mTotalTime[k] |= bytesReaded[k + 1]; */

                                    if (k == 6) {
                                        mTotalTime[tt] = bytesReaded[k] << 8;
                                        mTotalTime[tt] |= bytesReaded[k + 1];
                                        k = 7;
                                    } else {
                                        mTotalTime[tt] = bytesReaded[k];
                                    }

                                    //  mTotalTime[k] |= bytesReaded[k + 1];
                                    // i++;
                                    tt++;
                                }
                            } else {

                                //File file = new File(mContext.getExternalFilesDir(null), "one_year_data_" + mBtNameHead + ".xls");
                                File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "one_year_data_" + mBtNameHead + ".xls");
                                // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                                FileOutputStream os = null;
                                System.out.println("vikas--4==>4");
                                //baseRequest.hideLoader();
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    Log.w("FileUtils", "Writing file" + file);
                                    success = true;
                                } catch (IOException e) {
                                    Log.w("FileUtils", "Error writing " + file, e);
                                } catch (Exception e) {
                                    Log.w("FileUtils", "Failed to save file", e);
                                } finally {
                                    try {
                                        os = new FileOutputStream(file);
                                        wb.write(os);
                                        if (null != os)
                                            os.close();
                                    } catch (Exception ex) {
                                        System.out.println("vikas--5==>5");
                                        // baseRequest.hideLoader();
                                        ex.printStackTrace();
                                    }
                                }
                                // myBluetooth.disable();
                                //  changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                // Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        //  if((mDay == 255) && (mMonth == 255) && (mYear == 255) && (mHour == 255) && (mMinut == 255) && (mStatus == 255))
                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 0) && (mYear == 0))) {
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                           // File file = new File(mContext.getExternalFilesDir(null), "one_year_data_" + mBtNameHead + ".xls");
                            File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "one_year_data_" + mBtNameHead + ".xls");
                            FileOutputStream os = null;
                            //  File file = new File(mContext.getExternalFilesDir(null), "Month" + mBtNameHead + ".xls");
                            //   FileOutputStream os = null;
                            //    baseRequest.hideLoader();
                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            //  myBluetooth.disable();
                            // changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            // Toast.makeText(mContext, "Process completed..2", Toast.LENGTH_SHORT).show();
                        } else {
                            // saveExcelFile(mContext, "VikasTest.xls", mPostionFinal,mDay,mMonth,mYear,mHour,mMinut,mStatus,mFrequency,mRMSVoltage,mOutputCurrent,mRPM,mLPM,mPVVoltage,mPVCurrent,mFault,mInvTemp);
                            if (mPostionFinal == 0) {
                                //New Workbook
                                wb = new HSSFWorkbook();
                                //Cell style for header row
                                cs = wb.createCellStyle();
                                cs.setFillForegroundColor(HSSFColor.LIME.index);
                                cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                //New Sheet
                                sheet1 = wb.createSheet("one_year");
                                row = sheet1.createRow(0);
                                int kkp1 = 0;
                                for (int kkp = 0; kkp < mMonthHeaderList.size() - 2; kkp++) {

                                    String[] mStringSplitStart = mMonthHeaderList.get(kkp1).split("-");
                                    if (kkp == 0) {

                                        sheet1.setColumnWidth(kkp, (10 * 200));
                                        c = row.createCell(kkp);
                                        // c.setCellValue(mMonthHeaderList.get(k));
                                        c.setCellValue("DATE");
                                        c.setCellStyle(cs);
                                        kkp1 = 3;
                                    } else {
                                        sheet1.setColumnWidth(kkp, (10 * 200));
                                        c = row.createCell(kkp);
                                        // c.setCellValue(mMonthHeaderList.get(k));
                                        c.setCellValue(mStringSplitStart[0]);
                                        c.setCellStyle(cs);

                                        kkp1++;
                                    }
                                }

                                /*row = sheet1.createRow(mPostionFinal + 1);
                                c = row.createCell(0);
                                c.setCellValue("" + mDateCommanString);
                                c.setCellStyle(cs);*/

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    if (!mBoolflagCheck) {
                                        int jp = 0;
                                        for (int j = 3; j < mMonthHeaderList.size(); j++) {
                                            //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);

                                            String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                            int mmIntt = 1;
                                            try {
                                                mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                            } catch (NumberFormatException e) {

                                                //  mmIntt = Integer.parseInt(""+Float.valueOf(mStringSplitStart[1]));
                                                float ff = Float.valueOf(mStringSplitStart[1]);
                                                mmIntt = (int) ff;
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (jp == 0) {

                                                    row = sheet1.createRow(mPostionFinal + 1);
                                                    c = row.createCell(0);
                                                    c.setCellValue("" + mDateCommanString);
                                                    c.setCellStyle(cs);

                                                } else if (mmIntt == 1) {

                                                    sheet1.setColumnWidth(jp, (10 * 200));
                                                    fFrequency = mTotalTime[jp];
                                                    c = row.createCell(jp);
                                                    c.setCellValue("" + fFrequency);
                                                    c.setCellStyle(cs);
                                                    // tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                                } else if (mmIntt == 0) {

                                                    sheet1.setColumnWidth(jp, (10 * 200));
                                                    fFrequency = mTotalTime[jp];
                                                    float mmValue = (((float) mTotalTime[jp]));
                                                    c = row.createCell(jp);
                                                    // c.setCellValue("" + fFrequency);
                                                    c.setCellValue("" + mmValue);
                                                    c.setCellStyle(cs);
                                                    //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                                } else {


                                                    sheet1.setColumnWidth(jp, (10 * 200));
                                                    fFrequency = mTotalTime[jp];

                                                    float mmValue = (((float) mTotalTime[jp]) / ((float) mmIntt));

                                                    c = row.createCell(jp);
                                                    // c.setCellValue("" + fFrequency);
                                                    c.setCellValue("" + mmValue);
                                                    c.setCellStyle(cs);

                                                    //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                                }

                                            } catch (Exception e) {
                                                baseRequest.hideLoader();
                                                e.printStackTrace();
                                            }
                                            jp++;
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    baseRequest.hideLoader();
                                }

                                mvDay = mDay;
                                mvMonth = mMonth;
                                mvYear = mYear;


                                /*if (((mvDay == 255) && (mvMonth == 255) && (mvYear == 255)) || ((mvDay == 0) && (mvMonth == 255) && (mvYear == 255))) {

                                    baseRequest.showLoader();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ///  addHeadersMonths();
                                            addHeadersOneYear();

                                            //   addDataOneYear(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        }
                                    });
                                }*/

                            } else {
                                // cs.setFillPattern(HSSFCellStyle.NO_FILL);
                               /* row = sheet1.createRow(mPostionFinal + 1);

                                c = row.createCell(0);
                                c.setCellValue("" + mDateCommanString);
                                c.setCellStyle(cs);*/

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    if (!mBoolflagCheck) {
                                        int jpk = 0;
                                        for (int j = 3; j < mMonthHeaderList.size(); j++) {
                                            //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);

                                            String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                            int mmIntt = 1;
                                            try {
                                                mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                            } catch (NumberFormatException e) {

                                                //  mmIntt = Integer.parseInt(""+Float.valueOf(mStringSplitStart[1]));
                                                float ff = Float.valueOf(mStringSplitStart[1]);
                                                mmIntt = (int) ff;
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (jpk == 0) {

                                                    row = sheet1.createRow(mPostionFinal + 1);
                                                    c = row.createCell(0);
                                                    c.setCellValue("" + mDateCommanString);
                                                    c.setCellStyle(cs);

                                                } else if (mmIntt == 1) {
                                                    sheet1.setColumnWidth(jpk, (10 * 200));
                                                    fFrequency = mTotalTime[jpk];
                                                    c = row.createCell(jpk);
                                                    c.setCellValue("" + fFrequency);
                                                    c.setCellStyle(cs);
                                                    // tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                                } else if (mmIntt == 0) {


                                                    sheet1.setColumnWidth(jpk, (10 * 200));
                                                    fFrequency = mTotalTime[jpk];
                                                    float mmValue = (((float) mTotalTime[jpk]));
                                                    c = row.createCell(jpk);
                                                    // c.setCellValue("" + fFrequency);
                                                    c.setCellValue("" + mmValue);
                                                    c.setCellStyle(cs);

                                                    //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                                } else {


                                                    sheet1.setColumnWidth(jpk, (10 * 200));
                                                    fFrequency = mTotalTime[jpk];

                                                    float mmValue = (((float) mTotalTime[jpk]) / ((float) mmIntt));

                                                    c = row.createCell(jpk);
                                                    // c.setCellValue("" + fFrequency);
                                                    c.setCellValue("" + mmValue);
                                                    c.setCellStyle(cs);

                                                    //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                                }
                                                jpk++;
                                            } catch (Exception e) {
                                                //   baseRequest.hideLoader();
                                                e.printStackTrace();
                                            }

                                       /* sheet1.setColumnWidth(j, (10 * 200));
                                        fFrequency = mTotalTime[j];

                                        c = row.createCell(j);
                                        c.setCellValue("" + fFrequency);
                                        c.setCellStyle(cs);*/
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    //      baseRequest.hideLoader();
                                }


                            }

                            mvDay = mDay;
                            mvMonth = mMonth;
                            mvYear = mYear;

                        /*    if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                                baseRequest.showLoader();

                            } else*/
                            //if ((mDay != 255) && (mMonth != 255) && (mYear != 255))

                          /*  {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // addHeadersDay();
                                        // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        addDataOneYear(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                    }
                                });
                            }*/

                            mPostionFinal++;
                        }
                    }
                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("vikas--8==>8");
                baseRequest.hideLoader();
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            // baseRequest.hideLoader();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    rlvLoadingViewID.setVisibility(View.GONE);
                }
            });

            super.onPostExecute(result);
            //btSocket = null;
        }
    }

    //////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForCDataEXTActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            mBoolflag = false;
            //  baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection

                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    for (int i = 0; i < 12; i++) {
                        try {
                            bytesRead = iStream.read();
                        } catch (IOException e) {
                            // baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
                    int[] bytesReaded;

                    // while (iStream.available() > 0)
                    while (true) {
                        bytesReaded = new int[mLengthCount];
                        for (int i = 0; i < mLengthCount; i++) {
                            // Character mCharOne = (char) iStream.read();
                            //  Character mCharTwo = (char) iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();

                            try {
                                System.out.println("vikas--3==>i" + i + " =" + mCharOne + "==" + mCharTwo);
                                if ("TX".equalsIgnoreCase((char) mCharOne + "" + (char) mCharTwo)) {
                                   /* for (int j = 0; j < 11; j++) {
                                        int mCharOne1 = iStream.read();
                                    }*/
                                    mBoolflag = true;
                                    baseRequest.hideLoader();
                                    break;
                                } else {
                                    if (mCharOne == 0 || mCharTwo == 0) {
                                        //bytesReaded[i] = Integer.parseInt("" + (char) mCharOne +  (char) mCharTwo, 16);// iStream.
                                        bytesReaded[i] = Integer.parseInt("" + mCharOne + mCharTwo, 16);// iStream.read();
                                    } else {
                                        bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);// iStream.read();
                                    }
                                    //  bytesReaded[i] = Integer.parseInt("" + (char) iStream.read() + (char) iStream.read(), 16);// iStream.read();
                                    //  System.out.print("tarak mehta===>>> " + (char) bytesReaded[i]);
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        ////single value for read
                        int mDay = 0;
                        int mMonth = 0;
                        int mYear = 0;
                        //float value for read
                        int mTotalEnergy = 0;
                        float fTotalEnergy = 0;
                        int mTotalFlow = 0;
                        float fTotalFlow = 0;
                        float fTotalTime = 0;

                        if (!mBoolflag) {

                            kk++;
                            System.out.println("\nDay_vikas_kk++ ==>> " + kk);

                            mDay = bytesReaded[0];
                            mMonth = bytesReaded[1];
                            mYear = bytesReaded[2];
                            int a = 0;
                            mTotalTime = new int[10];
                            mTotalTime[0] = mDay;
                            mTotalTime[1] = mMonth;
                            mTotalTime[2] = mYear;

                            int i = 3;
                            for (int k = 3; k < mLengthCount - 3; k += 4)
                            // for (int k = 3; k < mLengthCount; k += 4)
                            {

                                mTotalTime = Arrays.copyOf(mTotalTime, i + 1);
                                mTotalTime[i] = bytesReaded[k];
                                mTotalTime[i] |= bytesReaded[k + 1] << 8;
                                mTotalTime[i] |= bytesReaded[k + 2] << 16;
                                mTotalTime[i] |= bytesReaded[k + 3] << 24;
                                System.out.println(Float.intBitsToFloat(mTotalTime[i]));
                                i++;
                            }

                            mvDay = mDay;
                            mvMonth = mMonth;
                            mvYear = mYear;
                            fvTotalEnergy = fTotalEnergy;
                            fvTotalFlow = fTotalFlow;
                            fvTotalTime = fTotalTime;
                        } else {

                            // baseRequest.hideLoader();
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");
                         //   File file_day = new File(mContext.getExternalFilesDir(null), "DongleDay_" + mBtNameHead + ".xls");
                            File file_day = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "DongleDay_" + mBtNameHead + ".xls");
                            FileOutputStream os = null;

                            try {
                                os = new FileOutputStream(file_day);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file_day);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file_day, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {

                                    os = new FileOutputStream(file_day);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            //   myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                          // File file_day = new File(mContext.getExternalFilesDir(null), "DongleDay_" + mBtNameHead + ".xls");
                            File file_day = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "DongleDay_" + mBtNameHead + ".xls");
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;
                            // baseRequest.hideLoader();
                            try {
                                if (mPostionFinal == 0) {
                                    wb = new HSSFWorkbook();
                                }
                                os = new FileOutputStream(file_day);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file_day);
                                success = true;
                                baseRequest.hideLoader();
                            } catch (IOException e) {
                                baseRequest.hideLoader();
                                Log.w("FileUtils", "Error writing " + file_day, e);
                            } catch (Exception e) {
                                baseRequest.hideLoader();
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {
                                    os = new FileOutputStream(file_day);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            //  myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  break;
                        } else {
                            if (mPostionFinal == 0) {

                                wb = new HSSFWorkbook();
                                //Cell style for header row
                                cs = wb.createCellStyle();
                                cs.setFillForegroundColor(HSSFColor.LIME.index);
                                cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                //New Sheet
                                sheet1 = wb.createSheet("myOrder");
                                row = sheet1.createRow(0);

                                for (int k = 0; k < mMonthHeaderList.size(); k++) {

                                    String[] mStringSplitStart = mMonthHeaderList.get(k).split("-");
                                    sheet1.setColumnWidth(k, (10 * 200));
                                    c = row.createCell(k);
                                    //  c.setCellValue(mMonthHeaderList.get(k));
                                    c.setCellValue(mStringSplitStart[0]);
                                    c.setCellStyle(cs);
                                }
                                row = sheet1.createRow(mPostionFinal + 1);

                                sheet1.setColumnWidth(0, (10 * 200));
                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(1, (10 * 200));
                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                c = row.createCell(2);
                                sheet1.setColumnWidth(2, (10 * 200));
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 3; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);

                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        try {
                                            mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }

                                        sheet1.setColumnWidth(j, (10 * 200));
                                        // fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);
                                        //  fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));
                                        // fTotalEnergy = (Float.intBitsToFloat( mTotalTime[j]) / mmIntt);
                                        fTotalEnergy = (Float.intBitsToFloat(mTotalTime[j]) / mmIntt);
                                        float fffff = 0;
                                        if (mMonthHeaderList.size() - 1 == j) {
                                            fffff = (float) (fTotalEnergy * 1.67);
                                        } else {
                                            fffff = fTotalEnergy;
                                        }
                                        //double fffff = fTotalEnergy*1.67;
                                        DecimalFormat df = new DecimalFormat("#");
                                        df.setMaximumFractionDigits(10);
                                        //System.out.println(df.format(fTotalEnergy));

                                        //System.out.println(df.format(fTotalEnergy));
                                        c = row.createCell(j);
                                        //c.setCellValue("'" + fTotalEnergy);
                                        c.setCellValue(df.format(fffff));
                                        c.setCellStyle(cs);



                                      /*  sheet1.setColumnWidth(j, (10 * 200));
                                        fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);

                                        c = row.createCell(j);
                                        c.setCellValue("" + fTotalEnergy);
                                        c.setCellStyle(cs);*/
                                    }
                                } catch (NumberFormatException e) {
                                    baseRequest.hideLoader();
                                    e.printStackTrace();
                                }


                                // if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 255) && (mYear == 255)))
                                // if ((mDay == 0) || (mMonth == 0) || (mYear == 0))
                             /*   if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {
                                    baseRequest.showLoader();
                                } else if (((mDay != 255) && (mMonth != 255) && (mYear != 255))) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            addHeadersDay();
                                            //addDataDay(mPostionFinal + 1,mDayDataList);
                                            addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                            //  addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                        }
                                    });
                                }*/

                            } else {
                                row = sheet1.createRow(mPostionFinal + 1);

                                sheet1.setColumnWidth(0, (10 * 200));
                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(1, (10 * 200));
                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(2, (10 * 200));
                                c = row.createCell(2);
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);


                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 3; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);
                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        try {
                                            mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                        sheet1.setColumnWidth(j, (10 * 200));
                                        // fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);
                                        //fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));
                                        fTotalEnergy = (Float.intBitsToFloat(mTotalTime[j]) / mmIntt);
                                        float fffff = 0;
                                        if (mMonthHeaderList.size() - 1 == j) {
                                            fffff = (float) (fTotalEnergy * 1.67);
                                        } else {
                                            fffff = fTotalEnergy;
                                        }
                                        //double fffff = fTotalEnergy*1.67;
                                        DecimalFormat df = new DecimalFormat("#");
                                        df.setMaximumFractionDigits(10);
                                        //System.out.println(df.format(fTotalEnergy));
                                        c = row.createCell(j);
                                        //  c.setCellType(String.);
                                        //c.setCellValue(String.valueOf(fTotalEnergy));
                                        // c.setCellValue(df.format(fTotalEnergy));
                                        c.setCellValue(df.format(fffff));

                                        c.setCellStyle(cs);
                                    }
                                } catch (NumberFormatException e) {
                                    // baseRequest.hideLoader();
                                    e.printStackTrace();
                                }

                            }

                            //if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || (mDay == 0) && (mMonth == 255) && (mYear == 255))
                           /* if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {
                                baseRequest.showLoader();
                            } else if (((mDay != 255) && mMonth != 255 && (mYear != 255))) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // addHeadersDay();
                                        // addDataDay(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                        // addDataDay(mPostionFinal + 1,mDayDataList);
                                        addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                    }
                                });
                            }*/
                            mPostionFinal++;
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
                // btSocket = null;
                //baseRequest.hideLoader();
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            // baseRequest.hideLoader();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    rlvLoadingViewID.setVisibility(View.GONE);
                }
            });

            super.onPostExecute(result);
            // btSocket = null;
            // addDataDay(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForFaultDataActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            mBoolflag = false;
            //  baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection

                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    for (int i = 0; i < 12; i++) {
                        try {
                            bytesRead = iStream.read();
                        } catch (IOException e) {
                            // baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
                    int[] bytesReaded;

                    // while (iStream.available() > 0)
                    while (true) {
                        bytesReaded = new int[mLengthCount];
                        for (int i = 0; i < mLengthCount; i++) {
                            // Character mCharOne = (char) iStream.read();
                            //  Character mCharTwo = (char) iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();

                            try {
                                System.out.println("vikas--3==>" + mCharOne + "" + mCharTwo);
                                if ("TX".equalsIgnoreCase((char) mCharOne + "" + (char) mCharTwo)) {
                                   /* for (int j = 0; j < 11; j++) {
                                        int mCharOne1 = iStream.read();
                                    }*/
                                    mBoolflag = true;
                                    baseRequest.hideLoader();
                                    break;
                                } else {
                                    if (mCharOne == 0 || mCharTwo == 0) {
                                        bytesReaded[i] = Integer.parseInt("" + mCharOne + mCharTwo, 16);// iStream.read();
                                    } else {
                                        bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);// iStream.read();
                                    }

                                    //  bytesReaded[i] = Integer.parseInt("" + (char) iStream.read() + (char) iStream.read(), 16);// iStream.read();
                                    //  System.out.print("tarak mehta===>>> " + (char) bytesReaded[i]);
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        ////single value for read
                        int mDay = 0;
                        int mMonth = 0;
                        int mYear = 0;

                        //float value for read
                        int mTotalEnergy = 0;
                        float fTotalEnergy = 0;

                        int mTotalFlow = 0;
                        float fTotalFlow = 0;

                        float fTotalTime = 0;

                        if (!mBoolflag) {

                            kk++;
                            System.out.println("\nDay_vikas_kk++ ==>> " + kk);


                            mDay = bytesReaded[0];
                            mMonth = bytesReaded[1];
                            mYear = bytesReaded[2];

                            int a = 0;
                            // mTotalTime = new int[10];
                            mTotalTime = new int[mLengthCount];
                            mTotalTime[0] = mDay;
                            mTotalTime[1] = mMonth;
                            mTotalTime[2] = mYear;
                            mTotalTime[3] = bytesReaded[3];
                            mTotalTime[4] = bytesReaded[4];
                            mTotalTime[5] = bytesReaded[5];
                            mTotalTime[6] = bytesReaded[6];
                            mTotalTime[7] = bytesReaded[7];
                            mTotalTime[8] = bytesReaded[8];
                            mTotalTime[9] = bytesReaded[9];
                            //  mTotalTime[5] = (char)bytesReaded[5]+(char)bytesReaded[6]+(char)bytesReaded[7]+(char)bytesReaded[8]+(char)bytesReaded[9];
                           /* int i = 3;
                            for (int k = 3; k < mLengthCount - 3; k += 4) {
                                mTotalTime = Arrays.copyOf(mTotalTime, i + 1);
                                mTotalTime[i] = bytesReaded[k];
                                mTotalTime[i] |= bytesReaded[k + 1] << 8;
                                mTotalTime[i] |= bytesReaded[k + 2] << 16;
                                mTotalTime[i] |= bytesReaded[k + 3] << 24;

                                System.out.println(Float.intBitsToFloat(mTotalTime[i]));
                                i++;
                            }*/

                            mvDay = mDay;
                            mvMonth = mMonth;
                            mvYear = mYear;
                            mvHournew = mTotalTime[3];
                            mvMinutenew = mTotalTime[4];
                            mvFaultnew = (char) mTotalTime[5] + "" + (char) mTotalTime[6] + "" + (char) mTotalTime[7] + "" + (char) mTotalTime[8] + "" + (char) mTotalTime[9];

                            String addStringName = "";
                            for (int k = 5; k < mLengthCount; k++) {
                                addStringName = "" + addStringName + (char) bytesReaded[k];
                            }
                            //mvFaultnew = ""+((char)bytesReaded[5]+(char)bytesReaded[6]+(char)bytesReaded[7]+(char)bytesReaded[8]+(char)bytesReaded[9]);
                            mvFaultnew = addStringName;

                        } else {

                            // baseRequest.hideLoader();
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");
                            //File file_day = new File(mContext.getExternalFilesDir(null), "Fault_" + mBtNameHead + ".xls");
                            File file_day = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "Fault_" + mBtNameHead + ".xls");
                            FileOutputStream os = null;

                            try {
                                os = new FileOutputStream(file_day);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file_day);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file_day, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {

                                    os = new FileOutputStream(file_day);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    //  baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            //   myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        // if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) || (mMonth == 0) || (mYear == 0)))

                        // if (((mDay == 0) && (mMonth == 0) && (mYear == 0)))
                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                          //  File file_day = new File(mContext.getExternalFilesDir(null), "Fault_" + mBtNameHead + ".xls");
                            File file_day = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "Fault_" + mBtNameHead + ".xls");
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;
                            // baseRequest.hideLoader();
                            try {
                                os = new FileOutputStream(file_day);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file_day);
                                success = true;
                            } catch (IOException e) {
                                // baseRequest.hideLoader();
                                Log.w("FileUtils", "Error writing " + file_day, e);
                            } catch (Exception e) {
                                //   baseRequest.hideLoader();
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {
                                    os = new FileOutputStream(file_day);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    //    baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            //  myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  break;
                        } else {
                            if (mPostionFinal == 0) {

                                wb = new HSSFWorkbook();
                                //Cell style for header row
                                cs = wb.createCellStyle();
                                cs.setFillForegroundColor(HSSFColor.LIME.index);
                                cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                //New Sheet
                                sheet1 = wb.createSheet("myOrder");
                                row = sheet1.createRow(0);

                                for (int k = 0; k < mMonthHeaderList.size(); k++) {

                                    String[] mStringSplitStart = mMonthHeaderList.get(k).split("-");

                                    sheet1.setColumnWidth(k, (10 * 200));
                                    c = row.createCell(k);
                                    //  c.setCellValue(mMonthHeaderList.get(k));
                                    c.setCellValue(mStringSplitStart[0]);
                                    c.setCellStyle(cs);

                                }


                                row = sheet1.createRow(mPostionFinal + 1);

                                sheet1.setColumnWidth(0, (10 * 200));
                                c = row.createCell(0);
                                c.setCellValue("" + mvDay);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(1, (10 * 200));
                                c = row.createCell(1);
                                c.setCellValue("" + mvMonth);
                                c.setCellStyle(cs);

                                c = row.createCell(2);
                                sheet1.setColumnWidth(2, (10 * 200));
                                c.setCellValue("" + mvYear);
                                c.setCellStyle(cs);

                                c = row.createCell(3);
                                sheet1.setColumnWidth(3, (10 * 200));
                                c.setCellValue("" + mvHournew);
                                c.setCellStyle(cs);

                                c = row.createCell(4);
                                sheet1.setColumnWidth(4, (10 * 200));
                                c.setCellValue("" + mvMinutenew);
                                c.setCellStyle(cs);

                                c = row.createCell(5);
                                sheet1.setColumnWidth(5, (10 * 200));
                                c.setCellValue("" + mvFaultnew);
                                c.setCellStyle(cs);

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 5; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);

                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        try {
                                            mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }


                                        sheet1.setColumnWidth(j, (10 * 200));
                                        // fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);
                                        // fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));

                                        c = row.createCell(j);
                                        //  c.setCellValue("" + fTotalEnergy);
                                        c.setCellValue("" + mvFaultnew);
                                        c.setCellStyle(cs);



                                      /*  sheet1.setColumnWidth(j, (10 * 200));
                                        fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);

                                        c = row.createCell(j);
                                        c.setCellValue("" + fTotalEnergy);
                                        c.setCellStyle(cs);*/
                                    }
                                } catch (NumberFormatException e) {
                                    // baseRequest.hideLoader();
                                    e.printStackTrace();
                                }


                            } else {
                                row = sheet1.createRow(mPostionFinal + 1);

                                sheet1.setColumnWidth(0, (10 * 200));
                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(1, (10 * 200));
                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(2, (10 * 200));
                                c = row.createCell(2);
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);

                                c = row.createCell(3);
                                sheet1.setColumnWidth(3, (10 * 200));
                                c.setCellValue("" + mvHournew);
                                c.setCellStyle(cs);

                                c = row.createCell(4);
                                sheet1.setColumnWidth(4, (10 * 200));
                                c.setCellValue("" + mvMinutenew);
                                c.setCellStyle(cs);

                                c = row.createCell(5);
                                sheet1.setColumnWidth(5, (10 * 200));
                                c.setCellValue("" + mvFaultnew);
                                c.setCellStyle(cs);


                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 5; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);
                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        try {
                                            mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }

                                        sheet1.setColumnWidth(j, (10 * 200));
                                        // fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);
                                        //    fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));

                                        c = row.createCell(j);
                                        //c.setCellValue("" + fTotalEnergy);
                                        c.setCellValue("" + mvFaultnew);
                                        c.setCellStyle(cs);
                                    }
                                } catch (NumberFormatException e) {
                                    // baseRequest.hideLoader();
                                    e.printStackTrace();
                                }
                            }


                            mPostionFinal++;
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
                // btSocket = null;
                //baseRequest.hideLoader();
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            //baseRequest.hideLoader();
            // baseRequest.hideLoader();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    rlvLoadingViewID.setVisibility(View.GONE);
                }
            });

            super.onPostExecute(result);
            // btSocket = null;
            // addDataDay(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
        }
    }


    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding((int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp));
        tv.setTextSize((int) getResources().getDimension(R.dimen._5ssp));
        tv.setBackgroundColor(bgColor);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(getLayoutParams());

        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    private TextView getTextViewCol(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding((int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp));
        tv.setTextSize((int) getResources().getDimension(R.dimen._5ssp));
        //tv.setBackgroundResource(bgColor);
        tv.setBackgroundColor(bgColor);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(getLayoutParamsCol());

        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    private TextView getTextView1(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding((int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp));
        tv.setTextSize((int) getResources().getDimension(R.dimen._5ssp));
        tv.setBackgroundColor(bgColor);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(getLayoutParams1());
        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    private TextView getTextView1Col(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setTextSize((int) getResources().getDimension(R.dimen._5ssp));
        tv.setPadding((int) getResources().getDimension(R.dimen._1sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._2sdp), (int) getResources().getDimension(R.dimen._3sdp));
        tv.setBackgroundColor(bgColor);
        // tv.setBackgroundResource(bgColor);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(getLayoutParams1Col());

        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    //float sizeInPixels = getResources().getDimension(R.dimen.my_value);

    @NonNull
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                (int) getResources().getDimension(R.dimen._152sdp),
                //120);
                (int) getResources().getDimension(R.dimen._40sdp));
        params.setMargins((int) getResources().getDimension(R.dimen._1sdp), 0, 0, (int) getResources().getDimension(R.dimen._1sdp));
        return params;
    }

    @NonNull
    private LayoutParams getLayoutParams1() {
        LayoutParams params = new LayoutParams(
                //125,
                (int) getResources().getDimension(R.dimen._80sdp),
                //120);
                (int) getResources().getDimension(R.dimen._40sdp));
        params.setMargins((int) getResources().getDimension(R.dimen._1sdp), 0, 0, (int) getResources().getDimension(R.dimen._1sdp));
        return params;
    }

    @NonNull
    private LayoutParams getLayoutParamsCol() {
        LayoutParams params = new LayoutParams(
                //  290,
                (int) getResources().getDimension(R.dimen._152sdp),
                //120);
                (int) getResources().getDimension(R.dimen._40sdp));
        params.setMargins((int) getResources().getDimension(R.dimen._1sdp), 0, 0, (int) getResources().getDimension(R.dimen._1sdp));
        return params;
    }

    @NonNull
    private LayoutParams getLayoutParams1Col() {
        LayoutParams params = new LayoutParams(
                //  125,
                //  120);

                (int) getResources().getDimension(R.dimen._80sdp),
                //120);
                (int) getResources().getDimension(R.dimen._40sdp));
        params.setMargins((int) getResources().getDimension(R.dimen._1sdp), 0, 0, (int) getResources().getDimension(R.dimen._1sdp));
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/

    public void addHeadersFault() {
        try {
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(getLayoutParams());

            for (int i = 0; i < mMonthHeaderList.size(); i++) {

                //String mStringStarRep = mMonthHeaderList.get(i).replace("*","VICKY01");
                //  String [] mStringSplitStart = mStringStarRep.split("VICKY01");
                String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");

                if (i < 3) {
                    tr1.addView(getTextView1Col(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                } else {
                    tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                }

                // tr1.addView(getTextView(0, mMonthHeaderList.get(i), Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_fb)));

            }
            mTableDataHeader.addView(tr1, getTblLayoutParams());

        } catch (Exception e) {
            // baseRequest.hideLoader();
            e.printStackTrace();
        }
    }

    // public void addDataFault(int counter, String Day, String Month, String Year, int[] mTotalTime)
    public void addDataFault(int counter, int Day, int Month, int Year, int mvHournew, int mvMinutenew, String mvFaultnew) {
        // int numCompanies = companies.length;
        try {
            if (((Day == 0) && (Month == 0) && (Year == 0))) {

            } else {

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());
                int mmIntt = 1;
                //  for (int i = 3; i < mTotalTime.length; i++)
                for (int i = 0; i < mMonthHeaderList.size(); i++) {

                    String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");
                    mmIntt = 1;
                    try {
                        mmIntt = Integer.parseInt(mStringSplitStart[1]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                   /* if (i < 3) {
                        tr.addView(getTextView1(counter, mTotalTime[i] / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                    } else {
                        //  tr.addView(getTextView(counter,  (float)mTotalTime[i]/mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        //tr.addView(getTextView(counter, Float.intBitsToFloat(mTotalTime[i] / mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        tr.addView(getTextView(counter, (((float) mTotalTime[i]) / ((float) mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                    }*/

                }
                tr.addView(getTextView1(counter, Day / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                tr.addView(getTextView1(counter, Month / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                tr.addView(getTextView1(counter, Year / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));

                tr.addView(getTextView(counter, mvHournew / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                tr.addView(getTextView(counter, mvMinutenew / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                tr.addView(getTextView(counter, mvFaultnew, Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));

                mTableData.addView(tr, getTblLayoutParams());
                mCheckBackBTNStatus = 1;
            }
        } catch (Exception e) {
            // baseRequest.hideLoader();
            e.printStackTrace();
        }
    }

//////////////////////////jain shab chnages_ 07-11-2020


    //////////////////////////////////
    public void addHeadersDay() {
        try {
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(getLayoutParams());

            for (int i = 0; i < mMonthHeaderList.size(); i++) {

                //String mStringStarRep = mMonthHeaderList.get(i).replace("*","VICKY01");
                //  String [] mStringSplitStart = mStringStarRep.split("VICKY01");
                String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");

                if (i < 3) {
                    tr1.addView(getTextView1Col(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                } else {
                    tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                }

                // tr1.addView(getTextView(0, mMonthHeaderList.get(i), Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_fb)));

            }
            mTableDataHeader.addView(tr1, getTblLayoutParams());

        } catch (Exception e) {
            // baseRequest.hideLoader();
            e.printStackTrace();
        }
    }


    //  public void addDataDay(int counter, String Day, String Month, String Year, float Total_Energy, float Total_Flow, float Total_Time)
    //  public void addDataDay(int counter, List<String> mDayDataList)
    public void addDataDay(int counter, String Day, String Month, String Year, int[] mTotalTime) {
        // int numCompanies = companies.length;
        try {
            if (((Day.equalsIgnoreCase("255")) && (Month.equalsIgnoreCase("255")) && (Year.equalsIgnoreCase("255"))) || ((Day.equalsIgnoreCase("0")) && (Month.equalsIgnoreCase("255")) && (Year.equalsIgnoreCase("255")))) {

            } else {

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());
                //  for (int i = 3; i < mTotalTime.length; i++)
                for (int i = 0; i < mMonthHeaderList.size(); i++) {

                    String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");
                    int mmIntt = 1;
                    try {
                        mmIntt = Integer.parseInt(mStringSplitStart[1]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    if (i < 3) {
                        tr.addView(getTextView1(counter, mTotalTime[i] / mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                    } else {

                        float fffff = Float.intBitsToFloat(mTotalTime[i]) / mmIntt;
                        float ddddd = 0;

                        if (mMonthHeaderList.size() - 1 == i) {
                            ddddd = (float) (fffff * 1.67);
                        } else {
                            ddddd = fffff;
                        }
                        //  tr.addView(getTextView(counter,  (float)mTotalTime[i]/mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        //  tr.addView(getTextView(counter, Float.intBitsToFloat(mTotalTime[i])/mmIntt + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        tr.addView(getTextView(counter, ddddd + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        // tr.addView(getTextView(counter, (((float) mTotalTime[i]) / ((float) mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                    }
                }
                mTableData.addView(tr, getTblLayoutParams());
                mCheckBackBTNStatus = 1;
            }
        } catch (Exception e) {
            // baseRequest.hideLoader();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (mCheckBackBTNStatus == 0) {
            finish();
        } else {
            backConfirmPopupID();
        }
        // super.onBackPressed();
    }

    public void addHeadersMonths() {
        try {
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(getLayoutParams());

            for (int i = 0; i < mMonthHeaderList.size(); i++) {

                //String mStringStarRep = mMonthHeaderList.get(i).replace("*","VICKY01");
                // String [] mStringSplitStart = mStringStarRep.split("VICKY01");
                String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");

                if (i < 6) {
                    tr1.addView(getTextView1Col(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                } else {
                    tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                }
                // tr1.addView(getTextView(0, mMonthHeaderList.get(i), Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_fb)));
            }
            mTableDataHeader.addView(tr1, getTblLayoutParams());
        } catch (Exception e) {
            //      baseRequest.hideLoader();
            e.printStackTrace();
        }
    }


    public void addDataMonth(int counter, String Day, String Month, String Year, int[] mTotalTime) {
        // int numCompanies = companies.length;
        try {
            if (((Day.equalsIgnoreCase("255")) && (Month.equalsIgnoreCase("255")) && (Year.equalsIgnoreCase("255"))) || ((Day.equalsIgnoreCase("0")) && (Month.equalsIgnoreCase("255")) && (Year.equalsIgnoreCase("255")))) {

            } else {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());
                //  for (int i = 3; i < mTotalTime.length; i++)
                for (int i = 0; i < mMonthHeaderList.size(); i++) {

                    String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");
                    int mmIntt = 1;
                    mmIntt = Integer.parseInt(mStringSplitStart[1]);

                    try {
                        if (mmIntt == 1) {
                            tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        } else {
                            tr.addView(getTextView(counter, ((((float) mTotalTime[i]) / ((float) mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            //tr.addView(getTextView(counter, (Float.intBitsToFloat(mTotalTime[i]) /  mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        }
                        /*if (i < 6) {
                            tr.addView(getTextView1(counter, (mTotalTime[i] / mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        } else {
                            if (mmIntt == 1) {
                                tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            } else {
                                tr.addView(getTextView(counter, ((((float) mTotalTime[i]) / ((float) mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                //tr.addView(getTextView(counter, (Float.intBitsToFloat(mTotalTime[i]) /  mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            }
                        }*/
                    } catch (Exception e) {
                        //   baseRequest.hideLoader();
                        e.printStackTrace();
                    }

                }
                mTableData.addView(tr, getTblLayoutParams());
                mCheckBackBTNStatus = 1;
            }
        } catch (Exception e) {
            //  baseRequest.hideLoader();
            e.printStackTrace();
        }
    }


    public void addHeadersOneYear() {
        try {
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(getLayoutParams());
            int i = 0;

            for (i = 0; i < mMonthHeaderList.size(); i++) {
                //String mStringStarRep = mMonthHeaderList.get(i).replace("*","VICKY01");
                // String [] mStringSplitStart = mStringStarRep.split("VICKY01");
                String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");

                tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));

                /*if(i == 0)
                {
                    tr1.addView(getTextView1Col(0, "DATE", Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                    i = 2;
                }
                else
                {
                    tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                }*/

               /* if (i < 1) {
                    tr1.addView(getTextView1Col(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                } else {
                    tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                }*/
                // tr1.addView(getTextView(0, mMonthHeaderList.get(i), Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_fb)));
            }
            mTableDataHeader.addView(tr1, getTblLayoutParams());
        } catch (Exception e) {
            //      baseRequest.hideLoader();
            e.printStackTrace();
        }
    }


    public void addDataOneYear(int counter, String Day, String Month, String Year, int[] mTotalTime) {
        // int numCompanies = companies.length;
        try {
            if (((Day.equalsIgnoreCase("255")) && (Month.equalsIgnoreCase("255")) && (Year.equalsIgnoreCase("255"))) || ((Day.equalsIgnoreCase("0")) && (Month.equalsIgnoreCase("255")) && (Year.equalsIgnoreCase("255")))) {

            } else {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(getLayoutParams());
                //  for (int i = 3; i < mTotalTime.length; i++)
                int k = 0;
                for (int i = 0; i < mMonthHeaderList.size(); i++) {

                    String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");
                    int mmIntt = 1;
                    try {
                        mmIntt = Integer.parseInt(mStringSplitStart[1]);
                    } catch (NumberFormatException e) {

                        //  mmIntt = Integer.parseInt(""+Float.valueOf(mStringSplitStart[1]));
                        float ff = Float.valueOf(mStringSplitStart[1]);
                        mmIntt = (int) ff;
                        e.printStackTrace();
                    }


                    if (i == 0) {
                        tr.addView(getTextView1(counter, mDateCommanString, Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        i = 2;

                    } else {
                        try {

                            if (mmIntt == 1) {
                                tr.addView(getTextView(counter, ((mTotalTime[k] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            } else {
                                tr.addView(getTextView(counter, ((((float) mTotalTime[k]) / ((float) mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                //tr.addView(getTextView(counter, (Float.intBitsToFloat(mTotalTime[i]) /  mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            }

                        } catch (Exception e) {
                            //   baseRequest.hideLoader();
                            e.printStackTrace();
                        }

                        k++;

                    }

                   /* try {
                        if (i < 6) {
                            tr.addView(getTextView1(counter, (mTotalTime[i] / mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        } else {
                            if (mmIntt == 1) {
                                tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            } else {
                                tr.addView(getTextView(counter, ((((float) mTotalTime[i]) / ((float) mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                //tr.addView(getTextView(counter, (Float.intBitsToFloat(mTotalTime[i]) /  mmIntt) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                            }
                        }
                    } catch (Exception e) {
                        //   baseRequest.hideLoader();
                        e.printStackTrace();
                    }
*/
                }
                mTableData.addView(tr, getTblLayoutParams());
                mCheckBackBTNStatus = 1;
            }
        } catch (Exception e) {
            //  baseRequest.hideLoader();
            e.printStackTrace();
        }
    }


    private void changeButtonVisibility(boolean state, float alphaRate, TextView txtDoanloadFileBTNID) {
        txtDoanloadFileBTNID.setEnabled(state);
        txtDoanloadFileBTNID.setAlpha(alphaRate);
    }

    /////////////////////////////
    //method to show file chooser
    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + File.separator + "Android/data/com.shaktipumps.shakti_rms/files" + File.separator);
        intent.setDataAndType(uri, "application/xls");
        //startActivity(Intent.createChooser(intent, "Open folder"));
        //startActivityForResult(Intent.createChooser(intent, "Select xls"), PICK_PDF_REQUEST);
        startActivityForResult(Intent.createChooser(intent, "Select xls"), PICK_PDF_REQUEST);
    }


    //handling the image chooser activity result


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetFaultParameter extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private int bytesRead;
        private String condition;
        private String override = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseRequest.showLoader();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

          /*  if (!Constant.isLoding) {
                baseRequest.showLoader();
            }
*/
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        btSocket = null;
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    btSocket = null;
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);


                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //   baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";

                    while (iStream.available() > 0) {
                        SS += (char) iStream.read();
                    }
//                   String SS =convertStreamToString();

                    if (SS.trim().equalsIgnoreCase("")) {
                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                            }
                        });*/
                    } else {
                        String SSS = SS.replace(",", "VIKASGOTHI");

                        String[] mS = SSS.split("VIKASGOTHI");

                        if (mS.length > 0) {

                            for (int i = 0; i < mS.length; i++) {


                                if (!mS[i].trim().equalsIgnoreCase("")) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new BluetoothCommunicationGetFaultParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                                        }
                                    });

                                }

                                headerLenghtFalt = "" + mMonthHeaderList.size();
                            }
                            System.out.println("headerLenghtFalt==>> " + headerLenghtFalt);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                                }
                            });
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }

                }
            } catch (Exception e) {

                baseRequest.hideLoader();
                return false;
            }

            //  baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {


            if (mMonthHeaderList.size() > 0) {
                new BluetoothCommunicationForFaultDataActivity().execute(":FDATA#", ":FDATA#", "START");
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothCommunicationGetFaultParameter().execute(":FLENGTH#", ":FLENGTH#", "OKAY");
                    }
                });
            }

            super.onPostExecute(result);

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetCLengthParameter extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private int bytesRead;
        private String condition;
        private String override = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

           /* if (!Constant.isLoding) {
                baseRequest.showLoader();
            }*/

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        btSocket = null;
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    btSocket = null;
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);


                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //   baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";

                    while (iStream.available() > 0) {
                        SS += (char) iStream.read();
                    }
//                   String SS =convertStreamToString();

                    if (SS.trim().equalsIgnoreCase("")) {
                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                            }
                        });*/
                    } else {
                        String SSS = SS.replace(",", "VIKASGOTHI");

                        String[] mS = SSS.split("VIKASGOTHI");

                        if (mS.length > 0) {

                            for (int i = 0; i < mS.length; i++) {


                                if (!mS[i].trim().equalsIgnoreCase("null")) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                                        }
                                    });

                                }

                            }
                            headerLenghtDayDongle = "" + mMonthHeaderList.size();
                            System.out.println("headerLenghtDayDongle==>> " + headerLenghtDayDongle);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                                }
                            });
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }

                }
            } catch (Exception e) {

                baseRequest.hideLoader();
                return false;
            }

            //  baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            if (mMonthHeaderList.size() > 0) {
                new BluetoothCommunicationForCDataEXTActivity().execute(":CDATA#", ":CDATA#", "START");
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                    }
                });
            }
            super.onPostExecute(result);

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetYLengthParameter extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private int bytesRead;
        private String condition;
        private String override = null;


        @Override
        protected void onPreExecute() {

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtHeadingLabelID.setText("Dongle 1 Year data tranfer on bluetooth. Please wait..");
                    rlvLoadingViewID.setVisibility(View.VISIBLE);
                }
            });

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                        try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    } else {
                        btSocket = null;
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    btSocket = null;
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

              /*  if (btSocket == null) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }*/
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {

                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //  baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";

                    System.out.println("iStream.available()==>>" + iStream.available());

                    while (iStream.available() > 0) {
                        SS += (char) iStream.read();
                    }
//                   String SS =convertStreamToString();

                    if (SS.trim().equalsIgnoreCase("")) {

                    } else {
                        String SSS = SS.replace(",", "VIKASGOTHI");
                        // String [] mS = SS.split(",");
                        String[] mS = SSS.split("VIKASGOTHI");

                        if (mS.length > 0) {

                            for (int i = 0; i < mS.length; i++) {

                                System.out.println("mSmSmS====>>" + mS[i]);

                                //if (!mS[i].trim().equalsIgnoreCase("") || !mS[i].trim().equalsIgnoreCase(null)) {
                                if (i == 0) {
                                    //mLengthCount = Integer.parseInt(mS[i]);
                                    mLengthCount = Integer.valueOf(mS[i]);
                                } else {
                                    mMonthHeaderList.add(mS[i]);
                                }
                            }
                            headerLenghtMonthDongle = "" + mMonthHeaderList.size();
                        }
                        System.out.println("headerLenghtMonthDongle==>> " + headerLenghtMonthDongle);
                       /* } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //new BluetoothCommunicationGetCLengthParameter().execute(":CLENGTH#", ":CLENGTH#", "OKAY");
                                }
                            });
                        }*/
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }

                }
            } catch (Exception e) {

                // baseRequest.hideLoader();
                return false;
            }

            //  baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            // baseRequest.hideLoader();
            if (mMonthHeaderList.size() > 0) {

                new BluetoothCommunicationForYdataEXTActivity().execute(":YDATA" + NewSolarVFD.mNumberOfMonth + "#", ":YDATA01#", "START");
                //  new BluetoothCommunicationForYdataEXTActivity().execute(":YDATA#", ":YDATA#", "START");
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "OKAY");
                    }
                });
            }
        }
    }

    //////////////////vikas new code 07-11-2020


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetDayParameter extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private int bytesRead;
        private String condition;
        private String override = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            /*if (!Constant.isLoding) {
                baseRequest.showLoader();
            }*/

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        btSocket = null;
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    btSocket = null;
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);


                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //   baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";

                    while (iStream.available() > 0) {
                        SS += (char) iStream.read();
                    }
//                   String SS =convertStreamToString();

                    if (SS.trim().equalsIgnoreCase("")) {
                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                            }
                        });*/
                    } else {
                        String SSS = SS.replace(",", "VIKASGOTHI");

                        String[] mS = SSS.split("VIKASGOTHI");

                        if (mS.length > 0) {

                            for (int i = 0; i < mS.length; i++) {


                                if (!mS[i].trim().equalsIgnoreCase("")) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                                        }
                                    });
                                }
                                headerLenghtDAy = "" + mMonthHeaderList.size();


                            }

                            System.out.println("headerLenghtDAy==>> " + headerLenghtDAy);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                                }
                            });
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }

                }
            } catch (Exception e) {

                baseRequest.hideLoader();
                return false;
            }

            //  baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            if (mMonthHeaderList.size() > 0) {
                new BluetoothCommunicationForDayDataActivity().execute(":DDATA#", ":DDATA#", "START");
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                    }
                });
            }
            super.onPostExecute(result);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDayDataActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            mBoolflag = false;
            // baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection

                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    for (int i = 0; i < 12; i++) {
                        try {
                            bytesRead = iStream.read();
                        } catch (IOException e) {
                            // baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
                    int[] bytesReaded;

                    // while (iStream.available() > 0)
                    while (true) {
                        bytesReaded = new int[mLengthCount];
                        for (int i = 0; i < mLengthCount; i++) {
                            // Character mCharOne = (char) iStream.read();
                            //  Character mCharTwo = (char) iStream.read();
                            int mCharOne = iStream.read();
                            int mCharTwo = iStream.read();

                            try {
                                System.out.println("vikas--3==>i== " + mCharOne + "" + mCharTwo);
                                if ("TX".equalsIgnoreCase((char) mCharOne + "" + (char) mCharTwo)) {

                                   /* for (int j = 0; j < 11; j++) {
                                        int mCharOne1 = iStream.read();
                                    }*/

                                    mBoolflag = true;
                                    baseRequest.hideLoader();
                                    break;
                                } else {
                                    if (mCharOne == 0 || mCharTwo == 0) {
                                        //bytesReaded[i] = Integer.parseInt("" + (char) mCharOne +  (char) mCharTwo, 16);// iStream.
                                        bytesReaded[i] = Integer.parseInt("" + mCharOne + mCharTwo, 16);// iStream.read();
                                    } else {
                                        bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);// iStream.read();
                                    }

                                    //  bytesReaded[i] = Integer.parseInt("" + (char) iStream.read() + (char) iStream.read(), 16);// iStream.read();
                                    //  System.out.print("tarak mehta===>>> " + (char) bytesReaded[i]);
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        ////single value for read
                        int mDay = 0;
                        int mMonth = 0;
                        int mYear = 0;

                        //float value for read
                        int mTotalEnergy = 0;
                        float fTotalEnergy = 0;

                        int mTotalFlow = 0;
                        float fTotalFlow = 0;

                        float fTotalTime = 0;

                        if (!mBoolflag) {

                            kk++;
                            System.out.println("\nDay_vikas_kk++ ==>> " + kk);

                            mDay = bytesReaded[0];
                            mMonth = bytesReaded[1];
                            mYear = bytesReaded[2];

                            int a = 0;
                            mTotalTime = new int[10];
                            mTotalTime[0] = mDay;
                            mTotalTime[1] = mMonth;
                            mTotalTime[2] = mYear;

                            int i = 3;
                            for (int k = 3; k < mLengthCount - 3; k += 4) {

                                mTotalTime = Arrays.copyOf(mTotalTime, i + 1);

                                mTotalTime[i] = bytesReaded[k];
                                mTotalTime[i] |= bytesReaded[k + 1] << 8;
                                mTotalTime[i] |= bytesReaded[k + 2] << 16;
                                mTotalTime[i] |= bytesReaded[k + 3] << 24;

                                System.out.println(Float.intBitsToFloat(mTotalTime[i]));
                                i++;
                            }

                            mvDay = mDay;
                            mvMonth = mMonth;
                            mvYear = mYear;
                            fvTotalEnergy = fTotalEnergy;
                            fvTotalFlow = fTotalFlow;
                            fvTotalTime = fTotalTime;
                        } else {

                            // baseRequest.hideLoader();
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");
                            //File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");

                            File file_day = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "Day_" + mBtNameHead + ".xls");

                           // File file_day = new File(Arrays.toString(mContext.getExternalFilesDirs(null)), "Day_" + mBtNameHead + ".xls");

                                FileOutputStream os = null;

                                System.out.println("Arrays.toString(mContext.getExternalMediaDirs()_VIHAAN==>>" + Arrays.toString(mContext.getExternalMediaDirs()));

                                try {
                                    os = new FileOutputStream(file_day);
                                    wb.write(os);
                                    Log.w("FileUtils", "Writing file" + file_day);
                                    success = true;
                                } catch (IOException e) {
                                    Log.w("FileUtils", "Error writing " + file_day, e);
                                } catch (Exception e) {
                                    Log.w("FileUtils", "Failed to save file", e);
                                } finally {
                                    try {

                                        os = new FileOutputStream(file_day);
                                        wb.write(os);
                                        if (null != os)
                                            os.close();
                                    } catch (Exception ex) {
                                        baseRequest.hideLoader();
                                        ex.printStackTrace();
                                    }
                                }



                            //   myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xlsx");

                                ///this is for andorid 11 and higher version
                              //  File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");
                            File file_day = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "Day_" + mBtNameHead + ".xls");
                                // File file_day = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Day_" + mBtNameHead + ".xls");
                                FileOutputStream os = null;
                                // baseRequest.hideLoader();
                                try {
                                    if (mPostionFinal == 0) {
                                        wb = new HSSFWorkbook();
                                    }
                                    os = new FileOutputStream(file_day);
                                    wb.write(os);
                                    Log.w("FileUtils", "Writing file" + file_day);
                                    success = true;
                                    baseRequest.hideLoader();
                                } catch (IOException e) {
                                    baseRequest.hideLoader();
                                    Log.w("FileUtils", "Error writing " + file_day, e);
                                } catch (Exception e) {
                                    baseRequest.hideLoader();
                                    Log.w("FileUtils", "Failed to save file", e);
                                } finally {
                                    try {
                                        os = new FileOutputStream(file_day);
                                        wb.write(os);
                                        if (null != os)
                                            os.close();
                                    } catch (Exception ex) {
                                        baseRequest.hideLoader();
                                        ex.printStackTrace();
                                    }
                                }


                            //  myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  break;
                        } else {
                            if (mPostionFinal == 0) {

                                wb = new HSSFWorkbook();
                                //Cell style for header row
                                cs = wb.createCellStyle();
                                cs.setFillForegroundColor(HSSFColor.LIME.index);
                                cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                //New Sheet
                                sheet1 = wb.createSheet("myOrder");
                                row = sheet1.createRow(0);

                                for (int k = 0; k < mMonthHeaderList.size(); k++) {

                                    String[] mStringSplitStart = mMonthHeaderList.get(k).split("-");

                                    sheet1.setColumnWidth(k, (10 * 200));
                                    c = row.createCell(k);
                                    //  c.setCellValue(mMonthHeaderList.get(k));
                                    c.setCellValue(mStringSplitStart[0]);
                                    c.setCellStyle(cs);
                                }
                                row = sheet1.createRow(mPostionFinal + 1);

                                sheet1.setColumnWidth(0, (10 * 200));
                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(1, (10 * 200));
                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                c = row.createCell(2);
                                sheet1.setColumnWidth(2, (10 * 200));
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 3; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);

                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        try {
                                            mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }

                                        sheet1.setColumnWidth(j, (10 * 200));
                                        // fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);
                                        //  fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));
                                        // fTotalEnergy = (Float.intBitsToFloat( mTotalTime[j]) / mmIntt);
                                        fTotalEnergy = (Float.intBitsToFloat(mTotalTime[j]) / mmIntt);
                                        float fffff = 0;
                                        if (mMonthHeaderList.size() - 1 == j) {
                                            fffff = (float) (fTotalEnergy * 1.67);
                                        } else {
                                            fffff = fTotalEnergy;
                                        }
                                        //double fffff = fTotalEnergy*1.67;
                                        DecimalFormat df = new DecimalFormat("#");
                                        df.setMaximumFractionDigits(10);

                                        c = row.createCell(j);
                                        //c.setCellValue("'" + fTotalEnergy);
                                        c.setCellValue(df.format(fffff));
                                        c.setCellStyle(cs);


                                    }
                                } catch (NumberFormatException e) {
                                    baseRequest.hideLoader();
                                    e.printStackTrace();
                                }


                            } else {
                                row = sheet1.createRow(mPostionFinal + 1);

                                sheet1.setColumnWidth(0, (10 * 200));
                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(1, (10 * 200));
                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                sheet1.setColumnWidth(2, (10 * 200));
                                c = row.createCell(2);
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);


                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 3; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);
                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        try {
                                            mmIntt = Integer.parseInt(mStringSplitStart[1]);
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                        sheet1.setColumnWidth(j, (10 * 200));
                                        // fTotalEnergy = Float.intBitsToFloat(mTotalTime[j]);
                                        //fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));
                                        fTotalEnergy = (Float.intBitsToFloat(mTotalTime[j]) / mmIntt);
                                        float fffff = 0;
                                        if (mMonthHeaderList.size() - 1 == j) {
                                            fffff = (float) (fTotalEnergy * 1.67);
                                        } else {
                                            fffff = fTotalEnergy;
                                        }
                                        //double fffff = fTotalEnergy*1.67;
                                        DecimalFormat df = new DecimalFormat("#");
                                        df.setMaximumFractionDigits(10);
                                        //System.out.println(df.format(fTotalEnergy));
                                        c = row.createCell(j);

                                        c.setCellValue(df.format(fffff));

                                        c.setCellStyle(cs);
                                    }
                                } catch (NumberFormatException e) {
                                    // baseRequest.hideLoader();
                                    e.printStackTrace();
                                }

                            }


                            mPostionFinal++;
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }
                    // baseRequest.hideLoader();
                }
            } catch (Exception e) {

                e.printStackTrace();
                // btSocket = null;
                //baseRequest.hideLoader();
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            // baseRequest.hideLoader();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    rlvLoadingViewID.setVisibility(View.GONE);
                }
            });

            //txtHeadingLabelID.setText("Fault log AUTHModelData tranfer on bluetooth. Please wait..");
            super.onPostExecute(result);
            // btSocket = null;
            // addDataDay(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
        }
    }

    ////////////////////////////jain shab new data 07-11-12

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetMonthParameter extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private String response;
        private int bytesRead;
        private String condition;
        private String override = null;


        @Override
        protected void onPreExecute() {

            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

           /* if (!Constant.isLoding) {
                baseRequest.showLoader();
            }*/

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                       /* try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            baseRequest.hideLoader();
                            e.printStackTrace();
                        }*/
                    } else {
                        btSocket = null;
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    btSocket = null;
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }

              /*  if (btSocket == null) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }*/
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {

                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        //  baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    // final InputStream iStream = btSocket.getInputStream();

                    String SS = "";

                    System.out.println("iStream.available()==>>" + iStream.available());

                    while (iStream.available() > 0) {
                        SS += (char) iStream.read();
                    }
//                   String SS =convertStreamToString();

                    if (SS.trim().equalsIgnoreCase("")) {

                    } else {
                        String SSS = SS.replace(",", "VIKASGOTHI");
                        // String [] mS = SS.split(",");
                        String[] mS = SSS.split("VIKASGOTHI");

                        if (mS.length > 0) {

                            for (int i = 0; i < mS.length; i++) {

                                System.out.println("mSmSmS====>>" + mS[i]);

                                if (!mS[i].trim().equalsIgnoreCase("")) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                }
                                headerLenghtMonth = "" + mMonthHeaderList.size();
                            }

                            System.out.println("headerLenghtMonth==>> " + headerLenghtMonth);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                                }
                            });
                        }
                    }

                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }

                }
            } catch (Exception e) {

                // baseRequest.hideLoader();
                return false;
            }

            //  baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            // baseRequest.hideLoader();
            if (mMonthHeaderList.size() > 0) {
                new BluetoothCommunicationForFirstActivity().execute(":MDATA#", ":MDATA#", "START");
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothCommunicationGetMonthParameter().execute(":MLENGTH#", ":MLENGTH#", "OKAY");
                    }
                });
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForFirstActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            kk = 0;
            mBoolflag = false;
            //  baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                // btSocket.close();
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection
                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);
                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(400);
                        iStream = btSocket.getInputStream();
                    } catch (InterruptedException e1) {
                        System.out.println("vikas--1==>1");
                        //baseRequest.hideLoader();
                        e1.printStackTrace();
                    }
                    for (int i = 0; i < 12; i++) {
                        try {
                            bytesRead = iStream.read();
                        } catch (IOException e) {
                            System.out.println("vikas--2==>2");
                            //baseRequest.hideLoader();
                            e.printStackTrace();
                        }
                    }
                    int[] bytesReaded;
                    //   while (iStream.available() > 0)
                    while (true) {
                        bytesReaded = new int[mLengthCount];
                        for (int i = 0; i < mLengthCount; i++) {
                            // Character mCharOne = (char) iStream.read();
                            //  Character mCharTwo = (char) iStream.read();
                            int mCharOne = 0;
                            int mCharTwo = 0;
                            try {
                                mCharOne = iStream.read();
                                mCharTwo = iStream.read();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                System.out.println("vikas--3==>" + mCharOne + "" + mCharTwo);
                                if ("TX".equalsIgnoreCase((char) mCharOne + "" + (char) mCharTwo)) {

                                    baseRequest.hideLoader();
                                    mBoolflag = true;
                                    break;
                                } else {
                                    if (mCharOne == 0 || mCharTwo == 0) {
                                        bytesReaded[i] = Integer.parseInt("" + mCharOne + mCharTwo, 16);// iStream.read();
                                    } else {
                                        bytesReaded[i] = Integer.parseInt("" + (char) mCharOne + (char) mCharTwo, 16);// iStream.read();
                                    }
                                }
                            } catch (NumberFormatException e) {
                                baseRequest.hideLoader();
                                System.out.println("vikas--3==>N");
                                e.printStackTrace();
                            }
                        }

                        int mDay = 0;
                        int mMonth = 0;
                        int mYear = 0;
                        int mHour = 0;
                        int mMinut = 0;
                        int mStatus = 0;
                        int mRPM = 0;
                        int mFault = 0;

                        float fFrequency = 0;
                        float fRMSVoltage = 0;
                        float fOutputCurrent = 0;
                        float fLPM = 0;
                        float fPVVoltage = 0;
                        float fPVCurrent = 0;
                        float fInvTemp = 0;

                        if (!mBoolflag) {
                            kk++;

                            System.out.println("kk++ ==>> " + kk);
                            mDay = bytesReaded[0];
                            mMonth = bytesReaded[1];
                            mYear = bytesReaded[2];
                            mHour = bytesReaded[3];
                            mMinut = bytesReaded[4];
                            mStatus = bytesReaded[5];

                            mTotalTime = new int[10];

                            mTotalTime[0] = mDay;
                            mTotalTime[1] = mMonth;
                            mTotalTime[2] = mYear;
                            mTotalTime[3] = mHour;
                            mTotalTime[4] = mMinut;
                            mTotalTime[5] = mStatus;

                            int i = 6;
                            // System.out.println("bytesReadednm==>> ");
                            for (int k = 6; k < mLengthCount; k += 2) {
                                mTotalTime = Arrays.copyOf(mTotalTime, i + 1);
                                mTotalTime[i] = bytesReaded[k] << 8;
                                mTotalTime[i] |= bytesReaded[k + 1];

                                i++;
                            }
                        } else {

                            File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "Month_" + mBtNameHead + ".xls");

                           // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xls");
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;
                            System.out.println("vikas--4==>4");
                            //baseRequest.hideLoader();
                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    System.out.println("vikas--5==>5");
                                    // baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            // myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            // Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //  if((mDay == 255) && (mMonth == 255) && (mYear == 255) && (mHour == 255) && (mMinut == 255) && (mStatus == 255))
                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 0) && (mYear == 0))) {
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");

                            File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "Month_" + mBtNameHead + ".xls");

                          //  File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xls");
                            FileOutputStream os = null;
                            //  File file = new File(mContext.getExternalFilesDir(null), "Month" + mBtNameHead + ".xls");
                            //   FileOutputStream os = null;
                            //    baseRequest.hideLoader();
                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                Log.w("FileUtils", "Writing file" + file);
                                success = true;
                            } catch (IOException e) {
                                Log.w("FileUtils", "Error writing " + file, e);
                            } catch (Exception e) {
                                Log.w("FileUtils", "Failed to save file", e);
                            } finally {
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            //  myBluetooth.disable();
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            // Toast.makeText(mContext, "Process completed..2", Toast.LENGTH_SHORT).show();
                        } else {
                            // saveExcelFile(mContext, "VikasTest.xls", mPostionFinal,mDay,mMonth,mYear,mHour,mMinut,mStatus,mFrequency,mRMSVoltage,mOutputCurrent,mRPM,mLPM,mPVVoltage,mPVCurrent,mFault,mInvTemp);
                            if (mPostionFinal == 0) {
                                //New Workbook
                                wb = new HSSFWorkbook();
                                //Cell style for header row
                                cs = wb.createCellStyle();
                                cs.setFillForegroundColor(HSSFColor.LIME.index);
                                cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                //New Sheet
                                sheet1 = wb.createSheet("myOrder");
                                row = sheet1.createRow(0);

                                for (int k = 0; k < mMonthHeaderList.size(); k++) {

                                    String[] mStringSplitStart = mMonthHeaderList.get(k).split("-");

                                    sheet1.setColumnWidth(k, (10 * 200));
                                    c = row.createCell(k);
                                    // c.setCellValue(mMonthHeaderList.get(k));
                                    c.setCellValue(mStringSplitStart[0]);
                                    c.setCellStyle(cs);
                                }
                                /*
                                c = row.createCell(3);
                                c.setCellValue("Hour");
                                c.setCellStyle(cs);
                        */
                                row = sheet1.createRow(mPostionFinal + 1);

                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                c = row.createCell(2);
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);

                                c = row.createCell(3);
                                c.setCellValue("" + mHour);
                                c.setCellStyle(cs);

                                c = row.createCell(4);
                                c.setCellValue("" + mMinut);
                                c.setCellStyle(cs);

                                c = row.createCell(5);
                                c.setCellValue("" + mStatus);
                                c.setCellStyle(cs);

                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 6; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);


                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        mmIntt = Integer.parseInt(mStringSplitStart[1]);

                                        try {

                                            if (mmIntt == 1) {


                                                sheet1.setColumnWidth(j, (10 * 200));
                                                fFrequency = mTotalTime[j];

                                                c = row.createCell(j);
                                                c.setCellValue("" + fFrequency);
                                                c.setCellStyle(cs);

                                                // tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            } else {


                                                sheet1.setColumnWidth(j, (10 * 200));
                                                fFrequency = mTotalTime[j];

                                                float mmValue = (((float) mTotalTime[j]) / ((float) mmIntt));

                                                c = row.createCell(j);
                                                // c.setCellValue("" + fFrequency);
                                                c.setCellValue("" + mmValue);
                                                c.setCellStyle(cs);

                                                //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            }

                                        } catch (Exception e) {
                                            //   baseRequest.hideLoader();
                                            e.printStackTrace();
                                        }


                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    //      baseRequest.hideLoader();
                                }

                                mvDay = mDay;
                                mvMonth = mMonth;
                                mvYear = mYear;
                                mvHour = "" + mHour;
                                mvMinute = "" + mMinut;
                                mvNo_of_Start = "" + mStatus;

                                fvFrequency = fFrequency;
                                fvRMSVoltage = fRMSVoltage;
                                fvOutputCurrent = fOutputCurrent;
                                mvRPM = "" + mRPM;
                                fvLPM = fLPM;
                                fvPVVoltage = fPVVoltage;
                                fvPVCurrent = fPVCurrent;
                                mvFault = "" + mFault;
                                fvInvTemp = fInvTemp;

                                mvDay = mDay;
                                mvMonth = mMonth;
                                mvYear = mYear;
                                mvHour = "" + mHour;
                                mvMinute = "" + mMinut;
                                mvNo_of_Start = "" + mStatus;

                                fvFrequency = fFrequency;
                                fvRMSVoltage = fRMSVoltage;
                                fvOutputCurrent = fOutputCurrent;
                                mvRPM = "" + mRPM;
                                fvLPM = fLPM;
                                fvPVVoltage = fPVVoltage;
                                fvPVCurrent = fPVCurrent;
                                mvFault = "" + mFault;
                                fvInvTemp = fInvTemp;


                            } else {
                                // cs.setFillPattern(HSSFCellStyle.NO_FILL);
                                row = sheet1.createRow(mPostionFinal + 1);

                                c = row.createCell(0);
                                c.setCellValue("" + mDay);
                                c.setCellStyle(cs);

                                c = row.createCell(1);
                                c.setCellValue("" + mMonth);
                                c.setCellStyle(cs);

                                c = row.createCell(2);
                                c.setCellValue("" + mYear);
                                c.setCellStyle(cs);

                                c = row.createCell(3);
                                c.setCellValue("" + mHour);
                                c.setCellStyle(cs);

                                c = row.createCell(4);
                                c.setCellValue("" + mMinut);
                                c.setCellStyle(cs);

                                c = row.createCell(5);
                                c.setCellValue("" + mStatus);
                                c.setCellStyle(cs);


                                try {
                                    //  for (int j = 3; j < mLengthCount; j++)
                                    for (int j = 6; j < mMonthHeaderList.size(); j++) {
                                        //     fTotalEnergy = Float.intBitsToFloat(mDayDataList.get(i)[j]);


                                        String[] mStringSplitStart = mMonthHeaderList.get(j).split("-");
                                        int mmIntt = 1;
                                        mmIntt = Integer.parseInt(mStringSplitStart[1]);

                                        try {

                                            if (mmIntt == 1) {


                                                sheet1.setColumnWidth(j, (10 * 200));
                                                fFrequency = mTotalTime[j];

                                                c = row.createCell(j);
                                                c.setCellValue("" + fFrequency);
                                                c.setCellStyle(cs);

                                                // tr.addView(getTextView(counter, ((mTotalTime[i] / mmIntt)) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            } else {


                                                sheet1.setColumnWidth(j, (10 * 200));
                                                fFrequency = mTotalTime[j];

                                                float mmValue = (((float) mTotalTime[j]) / ((float) mmIntt));

                                                c = row.createCell(j);
                                                // c.setCellValue("" + fFrequency);
                                                c.setCellValue("" + mmValue);
                                                c.setCellStyle(cs);

                                                //  tr.addView(getTextView(counter, ( (((float)mTotalTime[i]) / ((float)mmIntt))) + "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                                            }

                                        } catch (Exception e) {
                                            //   baseRequest.hideLoader();
                                            e.printStackTrace();
                                        }

                                       /* sheet1.setColumnWidth(j, (10 * 200));
                                        fFrequency = mTotalTime[j];

                                        c = row.createCell(j);
                                        c.setCellValue("" + fFrequency);
                                        c.setCellStyle(cs);*/
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    //      baseRequest.hideLoader();
                                }


                            }

                            mvDay = mDay;
                            mvMonth = mMonth;
                            mvYear = mYear;
                            mvHour = "" + mHour;
                            mvMinute = "" + mMinut;
                            mvNo_of_Start = "" + mStatus;
                            fvFrequency = fFrequency;
                            fvRMSVoltage = fRMSVoltage;
                            fvOutputCurrent = fOutputCurrent;
                            mvRPM = "" + mRPM;
                            fvLPM = fLPM;
                            fvPVVoltage = fPVVoltage;
                            fvPVCurrent = fPVCurrent;
                            mvFault = "" + mFault;
                            fvInvTemp = fInvTemp;

                           /* if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                                baseRequest.showLoader();

                            } else if ((mDay != 255) && (mMonth != 255) && (mYear != 255)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // addHeadersDay();
                                        // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                    }
                                });
                            }*/

                            mPostionFinal++;
                        }
                    }
                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("vikas--8==>8");
                // baseRequest.hideLoader();
            }
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            // baseRequest.hideLoader();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    rlvLoadingViewID.setVisibility(View.GONE);
                }
            });

            super.onPostExecute(result);
            //btSocket = null;
        }
    }

    //////////////////////////////////


    //////////////////////////////end 2020
    public void backConfirmPopupID() {
        ////////////////////////////
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.alertDialog);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //  builder.setTitle(getString(R.string.Back_head));
        builder.setMessage(getString(R.string.go_back_text));

        String positiveText = getString(android.R.string.ok);

        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss alert dialog, update preferences with game score and restart play fragment

                        finish();
                        Log.d("myTag", "positive button clicked");
                        dialog.dismiss();
                    }
                });

        String negativeText = getString(android.R.string.cancel);

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

    public void UploadFileToServerOption(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.upload_alert_dialog);

        TextView text_dialog = (TextView) dialog.findViewById(R.id.text_dialog);


        Button btnDayWiseDataUPLID = (Button) dialog.findViewById(R.id.btnDayWiseDataUPLID);
        Button btnMonthWiseDataUPLID = (Button) dialog.findViewById(R.id.btnMonthWiseDataUPLID);
        Button btnFaultWiseDataUPLID = (Button) dialog.findViewById(R.id.btnFaultWiseDataUPLID);
        Button btnDongleDayWiseDataUPLID = (Button) dialog.findViewById(R.id.btnDongleDayWiseDataUPLID);
        Button btnDongleMonthWiseDataUPLID = (Button) dialog.findViewById(R.id.btnDongleMonthWiseDataUPLID);

        btnDayWiseDataUPLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                 //   filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Month_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/Month_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    // Log.d("picUri", picUri.toString());
                    Log.d("filePath", filePath);
                  //  String[] mDataNameString = filePath.split("files/");
                    String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
                    String[] mDataNameString1 = mDataNameString[1].split(".xls");
                    String[] mDataNameString2 = mDataNameString1[0].split("_");
                    GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0], headerLenghtDAy);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btnMonthWiseDataUPLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/Day_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
               //     filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Day_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    // Log.d("picUri", picUri.toString());
                    Log.d("filePath", filePath);
                   // String[] mDataNameString = filePath.split("files/");
                    String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
                    String[] mDataNameString1 = mDataNameString[1].split(".xls");
                    String[] mDataNameString2 = mDataNameString1[0].split("_");
                    GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0], headerLenghtMonth);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btnFaultWiseDataUPLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/Fault_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    //filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Fault_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    // Log.d("picUri", picUri.toString());
                    Log.d("filePath", filePath);

                  //  String[] mDataNameString = filePath.split("files/");
                    String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
                    String[] mDataNameString1 = mDataNameString[1].split(".xls");
                    String[] mDataNameString2 = mDataNameString1[0].split("_");

                    GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0], headerLenghtFalt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btnDongleDayWiseDataUPLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                  //  filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/DongleDay_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/DongleDay_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    // Log.d("picUri", picUri.toString());
                    Log.d("filePath", filePath);

                   // String[] mDataNameString = filePath.split("files/");
                    String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
                    String[] mDataNameString1 = mDataNameString[1].split(".xls");
                    String[] mDataNameString2 = mDataNameString1[0].split("_");

                    GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0], headerLenghtDayDongle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btnDongleMonthWiseDataUPLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    monthPopupUpoadFile(mContext);
                  /*  filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/DongleMonth_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    // Log.d("picUri", picUri.toString());
                    Log.d("filePath", filePath);
                    String[] mDataNameString = filePath.split("files/");
                    String[] mDataNameString1 = mDataNameString[1].split(".xls");
                    String[] mDataNameString2 = mDataNameString1[0].split("_");
                    filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/DongleMonth"+NewSolarVFD.mNumberOfMonth+"_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                    GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0], headerLenghtMonthDongle);*/
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    /*private void UploadFileToServerOption111(final Context context) {
        // custom dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.alertDialog);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //  builder.setTitle(getString(R.string.Back_head));
        builder.setMessage(getString(R.string.file_upload_text));
        // String positiveText = getString(R.string.text_15_Days);
        String positiveText = getString(R.string.text_5_Years);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Day_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                            // Log.d("picUri", picUri.toString());
                            Log.d("filePath", filePath);

                            String[] mDataNameString = filePath.split("files/");
                            String[] mDataNameString1 = mDataNameString[1].split(".xls");
                            String[] mDataNameString2 = mDataNameString1[0].split("_");

                            GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("myTag", "positive button clicked");
                        dialog.dismiss();
                    }
                });

        // String negativeText = getString(R.string.text_5_Years);
        String negativeText = getString(R.string.text_15_Days);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Month_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                            // Log.d("picUri", picUri.toString());
                            Log.d("filePath", filePath);
                            String[] mDataNameString = filePath.split("files/");
                            String[] mDataNameString1 = mDataNameString[1].split(".xls");
                            String[] mDataNameString2 = mDataNameString1[0].split("_");
                            GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0]);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("myTag", "negative button clicked");
                    }
                });

        AlertDialog dialog = builder.create();
// display dialog
        dialog.show();
    }*/

    public void GetProfileUpdate_Task(String deviceno, String type, String len) {

        if (CustomUtility.isOnline(mContext)) {
            baseRequest.showLoader();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            RequestBody fbody;
            MultipartBody.Part body = null;
            Log.e("fileActualPath", "& " + filePath);
            if (!UtilMethod.isStringNullOrBlank(filePath)) {
                file = new File(filePath);
                // fbody = RequestBody.create(MediaType.parse("xls/*"), file);
                fbody = RequestBody.create(MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), file);
                body = MultipartBody.Part.createFormData("fname", file.getName(), fbody);
            }
            RequestBody deviceno1 = RequestBody.create(MediaType.parse("multipart/form-data"), deviceno);
            RequestBody type1 = RequestBody.create(MediaType.parse("multipart/form-data"), type);
            RequestBody headerLenght1 = RequestBody.create(MediaType.parse("multipart/form-data"), len);
            // RequestBody lenCount = RequestBody.create(MediaType.parse("multipart/form-data"), lenCount);
            //Call<ProfileUpdateModel> call = apiService.getProfileUpdateData(deviceno1, type1, body);
            Call<ProfileUpdateModel> call = apiService.getProfileUpdateDatanew(deviceno1, type1, headerLenght1, body);

            call.enqueue(new Callback<ProfileUpdateModel>() {
                @Override
                public void onResponse(Call<ProfileUpdateModel> call, retrofit2.Response<ProfileUpdateModel> response) {
                    try {
                        ProfileUpdateModel dashResponse = response.body();
                        Log.e("status", "** " + dashResponse);
                        if (dashResponse.getStatus().equalsIgnoreCase("true")) {
                            Toast.makeText(mContext, dashResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, dashResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        baseRequest.hideLoader();
                    } catch (Exception e) {
                        baseRequest.hideLoader();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProfileUpdateModel> call, @NonNull Throwable t) {
                    try {
                        baseRequest.hideLoader();
                        Toast.makeText(mContext, "File upload faild.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        baseRequest.hideLoader();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            baseRequest.hideLoader();
            Toast.makeText(mContext, "Please check internet connection.", Toast.LENGTH_SHORT).show();
        }
    }


    /*New code for permission in android 11*/
    public void takePermissions() {

        if(isPermissionsGranted()){
            Toast.makeText(mContext, "Permission already Granted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            takePermission();
        }


    }

    private boolean isPermissionsGranted() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            // android 11
            return Environment.isExternalStorageManager();
        } else {
            int readEnternalStoragePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
            return readEnternalStoragePermission == PackageManager.PERMISSION_GRANTED;
        }

    }

    private void takePermission()
    {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getApplicationContext())));
                startActivityForResult(intent, 100);
            }catch (Exception exception)
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 100);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }

    }


    /*End New code for permission in android 11*/


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.permission_set_manually), Toast.LENGTH_LONG).show();
        }

        if(grantResults.length > 0)
        {
            boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if(readExternalStorage){
                Toast.makeText(mContext, "Read permissin is granted in android 10 or below", Toast.LENGTH_SHORT).show();
            }
            else
            {
                takePermission();
            }
        }
    }

    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==  RESULT_OK) {
            if (requestCode == PICK_PDF_REQUEST && data != null && data.getData() != null) {
                //filePath = data.getData().toString();
                filePath = getPath(data.getData());
                // Log.d("picUri", picUri.toString());
                Log.d("filePath", filePath);
               // String[] mDataNameString = filePath.split("files/");
                String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
                String[] mDataNameString1 = mDataNameString[1].split(".xls");
                String[] mDataNameString2 = mDataNameString1[0].split("_");
                //  GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0]);
                // GetProfileUpdate_Task("04-0018-0-18-03-19-0", "Day");
                //filePath = data.getData();
            }
            if(requestCode == 100)
            {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                    if(Environment.isExternalStorageManager())
                    {
                        Toast.makeText(mContext, "Permission granted in andorid 11 and above!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        takePermission();
                    }
                }

            }
        }
    }


    public void monthPopupOpen(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.month_popup_view);
        dialog.setCancelable(true);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        // text.setText(msg);

        final TextView txtOneID = (TextView) dialog.findViewById(R.id.txtOneID);
        final TextView txtTwoID = (TextView) dialog.findViewById(R.id.txtTwoID);
        final TextView txtThreeID = (TextView) dialog.findViewById(R.id.txtThreeID);
        final TextView txtFourID = (TextView) dialog.findViewById(R.id.txtFourID);
        final TextView txtFiveID = (TextView) dialog.findViewById(R.id.txtFiveID);
        final TextView txtSixID = (TextView) dialog.findViewById(R.id.txtSixID);
        final TextView txtSevenID = (TextView) dialog.findViewById(R.id.txtSevenID);
        final TextView txtEightID = (TextView) dialog.findViewById(R.id.txtEightID);
        final TextView txtNineID = (TextView) dialog.findViewById(R.id.txtNineID);
        final TextView txtTenID = (TextView) dialog.findViewById(R.id.txtTenID);
        final TextView txtElevenID = (TextView) dialog.findViewById(R.id.txtElevenID);
        final TextView txtTwowellID = (TextView) dialog.findViewById(R.id.txtTwowellID);


        txtOneID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtOneID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtTwoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtTwoID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtThreeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtThreeID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtFourID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtFourID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtFiveID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtFiveID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtSixID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtSixID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtSevenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtSevenID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtEightID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtEightID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtNineID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtNineID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        txtTenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtTenID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });
        txtElevenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtElevenID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });
        txtTwowellID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtTwowellID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void monthPopupUpoadFile(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.month_popup_view);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        // text.setText(msg);

        final TextView txtOneID = (TextView) dialog.findViewById(R.id.txtOneID);
        final TextView txtTwoID = (TextView) dialog.findViewById(R.id.txtTwoID);
        final TextView txtThreeID = (TextView) dialog.findViewById(R.id.txtThreeID);
        final TextView txtFourID = (TextView) dialog.findViewById(R.id.txtFourID);
        final TextView txtFiveID = (TextView) dialog.findViewById(R.id.txtFiveID);
        final TextView txtSixID = (TextView) dialog.findViewById(R.id.txtSixID);
        final TextView txtSevenID = (TextView) dialog.findViewById(R.id.txtSevenID);
        final TextView txtEightID = (TextView) dialog.findViewById(R.id.txtEightID);
        final TextView txtNineID = (TextView) dialog.findViewById(R.id.txtNineID);
        final TextView txtTenID = (TextView) dialog.findViewById(R.id.txtTenID);
        final TextView txtElevenID = (TextView) dialog.findViewById(R.id.txtElevenID);
        final TextView txtTwowellID = (TextView) dialog.findViewById(R.id.txtTwowellID);


        txtOneID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtOneID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                // new BluetoothCommunicationGetYLengthParameter().execute(":YLENGTH#", ":YLENGTH#", "START");

                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtTwoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtTwoID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtThreeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtThreeID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtFourID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtFourID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtFiveID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtFiveID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtSixID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtSixID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtSevenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtSevenID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtEightID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtEightID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtNineID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtNineID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        txtTenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtTenID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });
        txtElevenID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtElevenID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });
        txtTwowellID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idd = "" + txtTwowellID.getText();
                NewSolarVFD.mNumberOfMonth = idd;
                fetchFileFunction();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void fetchFileFunction() {

        try {
           // filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/DongleMonth_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
            filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/DongleMonth_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
            // Log.d("picUri", picUri.toString());
            Log.d("filePath", filePath);
           // String[] mDataNameString = filePath.split("files/");
            String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
            String[] mDataNameString1 = mDataNameString[1].split(".xls");
            String[] mDataNameString2 = mDataNameString1[0].split("_");
          //  filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/DongleMonth" + NewSolarVFD.mNumberOfMonth + "_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
            filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/DongleMonth" + NewSolarVFD.mNumberOfMonth + "_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
            GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0], headerLenghtMonthDongle);
            //dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
