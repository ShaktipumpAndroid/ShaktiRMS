package com.shaktipumps.shakti_rms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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


public class BluetoothDataExtrctionVicheeActivity extends AppCompatActivity {


    private LinearLayout lvlMainDataViewContaonerId;
    private CardView cardFaultsID, cardOneYearID, cardFifteenDaysID, cardFiveYearID, cardCMDDongleID;
    private int mCheckBackBTNStatus = 0;
    public static final String UPLOAD_URL = "http://internetfaqs.net/AndroidPdfUpload/upload.php";
    private String mDateCommanString = "";
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

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
    private TextView txtfaultExtractionID;
    private TextView txtPlusZommBTNID;
    private TextView txtMinusZommBTNID;
    private RelativeLayout rlvOneYearBTNID, rlvYearBTNID, rlvDayBTNID, rlvFualtBTNID;
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
    private int jaiPOS = 0;
    private int chr1 = 1;
    private int chr2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_data_vichee);
        mContext = this;
        initView();
    }

    private void initView() {

        //setGIFLoadingView(mContext);
        //ShowLoadingView();

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
        rlvOneYearBTNID = (RelativeLayout) findViewById(R.id.rlvOneYearBTNID);
        rlvDayBTNID = (RelativeLayout) findViewById(R.id.rlvDayBTNID);
        rlvFualtBTNID = (RelativeLayout) findViewById(R.id.rlvFualtBTNID);

        cardFaultsID = (CardView) findViewById(R.id.cardFaultsID);
        cardOneYearID = (CardView) findViewById(R.id.cardOneYearID);
        cardFifteenDaysID = (CardView) findViewById(R.id.cardFifteenDaysID);
        cardFiveYearID = (CardView) findViewById(R.id.cardFiveYearID);
        cardCMDDongleID = (CardView) findViewById(R.id.cardCMDDongleID);
        lvlMainDataViewContaonerId = (LinearLayout) findViewById(R.id.lvlMainDataViewContaonerId);

        mTableData = (TableLayout) findViewById(R.id.table);
        mTableDataHeader = (TableLayout) findViewById(R.id.table1);
        txtUploadBTNID = (TextView) findViewById(R.id.txtUploadBTNID);
        txtfaultExtractionID = (TextView) findViewById(R.id.txtfaultExtractionID);
        // txtDoanloadFileBTNID = (TextView) findViewById(R.id.txtDoanloadFileBTNID);
        txtMinusZommBTNID = (TextView) findViewById(R.id.txtMinusZommBTNID);
        txtPlusZommBTNID = (TextView) findViewById(R.id.txtPlusZommBTNID);
        //  changeButtonVisibility(false, 0.5f, txtDoanloadFileBTNID);
        changeButtonVisibility(false, 0.5f, txtUploadBTNID);
        //  changeButtonVisibility(false, 0.5f, txtUploadBTNID);
        // lLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        //  rclSettingListViewID.setNestedScrollingEnabled(false);
        //  rclSettingListViewID.setLayoutManager(lLayout);
        setClickEventListner();
        //    saveExcelFile(mContext, "VikasTest.xls",mPostionFinal);


     if (mModelType.equalsIgnoreCase("6")) {

            cardOneYearID.setVisibility(View.GONE);//VISIBLE
            cardCMDDongleID.setVisibility(View.GONE);//VISIBLE
            cardFiveYearID.setVisibility(View.VISIBLE);
            cardFifteenDaysID.setVisibility(View.VISIBLE);
            cardFaultsID.setVisibility(View.GONE);
            //lvlMainDataViewContaonerId.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3f));
          //  lvlMainDataViewContaonerId.setWeightSum(2f);
           lvlMainDataViewContaonerId.setWeightSum(2f);

            mCommanCommandForAll = "";
        }
        //txtfaultExtractionID


    }

    private void setClickEventListner() {


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

                UploadFileToServerOption(mContext);

            }
        });

      /*  txtPlusZommBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                mTableData.startAnimation(animZoomIn);
            }
        });
        txtMinusZommBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
                mTableData.startAnimation(animZoomIn);
            }
        });*/




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
                new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");

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

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForFirstActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            kk = 0;
            mBoolflag = false;
            baseRequest.showLoader();
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
                        sleep(1100);
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

                               // File file = new File(mContext.getExternalFilesDir(null), "1_Year_" + mBtNameHead + ".xls");
                                File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "1_Year_" + mBtNameHead + ".xls");
                                // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                                FileOutputStream os = null;
                                System.out.println("vikas--11==>");

                                changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                //baseRequest.hideLoader();
                                try {
                                    os = new FileOutputStream(file);
                                    wb.write(os);
                                    Log.w("FileUtils", "Writing file" + file);
                                    success = true;
                                } catch (IOException e1) {

                                    Log.w("FileUtils", "Error writing " + file, e);
                                } catch (Exception e2) {
                                    Log.w("FileUtils", "Failed to save file", e);
                                } finally {
                                    try {
                                        os = new FileOutputStream(file);
                                        wb.write(os);
                                        if (null != os)
                                            os.close();
                                    } catch (Exception ex) {
                                        System.out.println("vikas--12==>");
                                        // baseRequest.hideLoader();
                                        ex.printStackTrace();
                                    }
                                }
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

                            System.out.println("kkvk++ ==>> " + kk);
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
//BIKPG0109D
                            if ((mDay == 255) && (mMonth == 255) && (mYear == 255)) {
                                baseRequest.hideLoader();
                               // if(jaiPOS == 0)
                               // if(jaiPOS == 0)
                                {
                                 //   jaiPOS = 1;
                                 //   File file = new File(mContext.getExternalFilesDir(null), "1_Year_" + mBtNameHead + ".xls");
                                    File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "1_Year_" + mBtNameHead + ".xls");
                                    // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                                    FileOutputStream os = null;
                                    System.out.println("vikas--4==>4");

                                    changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
                                            // baseRequest.hideLoader();
                                            System.out.println("vikas--5==>5");
                                            // baseRequest.hideLoader();
                                            ex.printStackTrace();
                                        }
                                    }
                                     break;
                                }
                            }

                        } else {

                           // File file = new File(mContext.getExternalFilesDir(null), "1_Year_" + mBtNameHead + ".xls");
                            File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "1_Year_" + mBtNameHead + ".xls");
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;

                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
                                    baseRequest.hideLoader();
                                    System.out.println("vikas--5==>5");
                                    // baseRequest.hideLoader();
                                    ex.printStackTrace();
                                }
                            }
                            // myBluetooth.disable();

                            baseRequest.hideLoader();
                            // Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //  if((mDay == 255) && (mMonth == 255) && (mYear == 255) && (mHour == 255) && (mMinut == 255) && (mStatus == 255))
                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 0) && (mYear == 0))) {
                            // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
                           // File file = new File(mContext.getExternalFilesDir(null), "1_Year_" + mBtNameHead + ".xls");
                            File file = new File(GlobalMethod.commonDocumentDirPath("ShaktiExtractionFile"), "1_Year_" + mBtNameHead + ".xls");
                            FileOutputStream os = null;
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
                                    baseRequest.hideLoader();
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
                                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                              baseRequest.hideLoader();
                                            e.printStackTrace();
                                        }

                                       /* sheet1.setColumnWidth(j, (10 * 200));
                                        fFrequency = mTotalTime[j];

                                        c = row.createCell(j);
                                        c.setCellValue("" + fFrequency);
                                        c.setCellStyle(cs);*/
                                    }
                                } catch (NumberFormatException e) {
                                    changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                    e.printStackTrace();
                                          baseRequest.hideLoader();
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


                                if (((mvDay == 255) && (mvMonth == 255) && (mvYear == 255)) || ((mvDay == 0) && (mvMonth == 0) && (mvYear == 0))) {

                                    baseRequest.showLoader();
                                } else {
                                    BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ///  addHeadersMonths();
                                            addHeadersMonths();

                                            addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        }
                                    });
                                }

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
                                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (NumberFormatException e) {
                                    changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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

                            if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                                baseRequest.showLoader();

                            } else if ((mDay != 255) && (mMonth != 255) && (mYear != 255)) {
                                BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // addHeadersDay();
                                        // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                                        addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                    }
                                });
                            }

                            mPostionFinal++;
                        }
                    }
                    while (iStream.available() > 0) {
                        int djdjd = iStream.read();
                    }
                }
            } catch (Exception e) {
                changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
            baseRequest.hideLoader();
            super.onPostExecute(result);
            //btSocket = null;
        }
    }


    //////////////////////////////////
    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDayDataActivity extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        private int bytesRead;

        @Override
        protected void onPreExecute() {
            mBoolflag = false;
            baseRequest.showLoader();
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
                            File file_day = new File(mContext.getExternalFilesDir(null), "5_Years_" + mBtNameHead + ".xls");
                            FileOutputStream os = null;

                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);

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
                          //  changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                            //  Toast.makeText(mContext, "Process completed..1", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) || (mMonth == 0) || (mYear == 0))) {

                            File file_day = new File(mContext.getExternalFilesDir(null), "5_Years_" + mBtNameHead + ".xls");
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xlsx");
                            FileOutputStream os = null;
                            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
                           // changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
                                        float fffff = fTotalEnergy;
                                       /* if (mMonthHeaderList.size() - 1 == j)
                                        {
                                           // fffff = (float) (fTotalEnergy * 1.67);
                                            fffff = (float) (fTotalEnergy);
                                        } else {
                                            fffff = fTotalEnergy;
                                        }*/
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
                                    changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                     baseRequest.hideLoader();
                                    e.printStackTrace();
                                }


                                // if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 255) && (mYear == 255)))
                                // if ((mDay == 0) || (mMonth == 0) || (mYear == 0))
                                if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {
                                    baseRequest.showLoader();
                                } else if (((mDay != 255) && (mMonth != 255) && (mYear != 255))) {
                                    BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            addHeadersDay();
                                            //addDataDay(mPostionFinal + 1,mDayDataList);
                                            addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                            //  addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                        }
                                    });
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
                                        float fffff = fTotalEnergy;
                                       /* if (mMonthHeaderList.size() - 1 == j) {
                                            fffff = (float) (fTotalEnergy * 1.67);
                                        } else {
                                            fffff = fTotalEnergy;
                                        }*/
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
                                    changeButtonVisibility(true, 1.0f, txtUploadBTNID);
                                    // baseRequest.hideLoader();
                                    e.printStackTrace();
                                }

                            }

                            //if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || (mDay == 0) && (mMonth == 255) && (mYear == 255))
                            if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {
                                baseRequest.showLoader();
                            } else if (((mDay != 255) && mMonth != 255 && (mYear != 255))) {
                                BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // addHeadersDay();
                                        // addDataDay(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                        // addDataDay(mPostionFinal + 1,mDayDataList);
                                        addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                    }
                                });
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
                changeButtonVisibility(true, 1.0f, txtUploadBTNID);
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
            changeButtonVisibility(true, 1.0f, txtUploadBTNID);
            baseRequest.hideLoader();
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
            baseRequest.showLoader();
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
                            mTotalTime = new int[10];
                            mTotalTime[0] = mDay;
                            mTotalTime[1] = mMonth;
                            mTotalTime[2] = mYear;
                            mTotalTime[3] = bytesReaded[3];
                            mTotalTime[4] = bytesReaded[4];
                            mTotalTime[5] = //(char)bytesReaded[5]+(char)bytesReaded[5]+(char)bytesReaded[5]+(char)bytesReaded[5]+(char)bytesReaded[5];


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

                            String addStringName = "";
                            for (int k = 5; k < mLengthCount; k++) {
                                addStringName = "" + addStringName + (char) bytesReaded[k];
                            }
                            //mvFaultnew = ""+((char)bytesReaded[5]+(char)bytesReaded[6]+(char)bytesReaded[7]+(char)bytesReaded[8]+(char)bytesReaded[9]);
                            mvFaultnew = addStringName;

                        } else {
                            // baseRequest.hideLoader();
                            // File file_day = new File(mContext.getExternalFilesDir(null), "Day_" + mBtNameHead + ".xls");
                            File file_day = new File(mContext.getExternalFilesDir(null), "Fault_" + mBtNameHead + ".xls");
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

                        if (((mDay == 0) && (mMonth == 0) && (mYear == 0))) {

                            File file_day = new File(mContext.getExternalFilesDir(null), "Fault_" + mBtNameHead + ".xls");
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


                                // if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || ((mDay == 0) && (mMonth == 255) && (mYear == 255)))
                                // if ((mDay == 0) || (mMonth == 0) || (mYear == 0))
                                if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {
                                    baseRequest.showLoader();
                                } else if (((mDay != 0) && (mMonth != 0) && (mYear != 0))) {
                                    BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            addHeadersFault();
                                            //addDataDay(mPostionFinal + 1,mDayDataList);
                                            //  addDataFault(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);
                                            addDataFault(mPostionFinal, mvDay, mvMonth, mvYear, mvHournew, mvMinutenew, mvFaultnew);
                                            //  addDataDay(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                        }
                                    });
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
                                        fTotalEnergy = (((float) mTotalTime[j]) / ((float) mmIntt));

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

                            //if (((mDay == 255) && (mMonth == 255) && (mYear == 255)) || (mDay == 0) && (mMonth == 255) && (mYear == 255))
                            if (((mDay == 0) || (mMonth == 0) || (mYear == 0))) {
                                baseRequest.showLoader();
                            } else if (((mDay != 255) && mMonth != 255 && (mYear != 255))) {
                                BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // addHeadersDay();
                                        // addDataDay(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", fvTotalEnergy, fvTotalFlow, fvTotalTime);
                                        // addDataDay(mPostionFinal + 1,mDayDataList);
                                        //   addDataFault(mPostionFinal, mvDay + "", mvMonth + "", mvYear + "", mTotalTime);

                                        addDataFault(mPostionFinal, mvDay, mvMonth, mvYear, mvHournew, mvMinutenew, mvFaultnew);

                                    }
                                });
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
            baseRequest.hideLoader();
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
                        float ddddd = fffff;

                     /*   if (mMonthHeaderList.size() - 1 == i) {
                            ddddd = (float) (fffff * 1.67);
                        } else {
                            ddddd = fffff;
                        }*/
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
            int i =0;

            for ( i = 0; i < mMonthHeaderList.size(); i++) {

                //String mStringStarRep = mMonthHeaderList.get(i).replace("*","VICKY01");
                // String [] mStringSplitStart = mStringStarRep.split("VICKY01");
                String[] mStringSplitStart = mMonthHeaderList.get(i).split("-");

                if(i == 0)
                {
                    tr1.addView(getTextView1Col(0, "DATE", Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                    i = 2;
                }
                else
                {
                    tr1.addView(getTextViewCol(0, mStringSplitStart[0], Color.WHITE, Typeface.NORMAL, getResources().getColor(R.color.blue_xle_header)));
                }

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


                    if(i == 0)
                    {
                        tr.addView(getTextView1(counter, mDateCommanString, Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.white)));
                        i = 2;

                    }
                    else
                    {
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

                        k ++;

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
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            if (!Constant.isLoding) {
                baseRequest.showLoader();
            }

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
                       /* GetBTDATAListActivity.this.runOnUiThread(new Runnable() {
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


                                if (!mS[i].trim().equalsIgnoreCase("") || !mS[i].trim().equalsIgnoreCase(null)) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                } else {
                                    BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new BluetoothDataExtrctionVicheeActivity.BluetoothCommunicationGetFaultParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                                        }
                                    });

                                }
                            }
                        } else {
                            BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
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
                new BluetoothCommunicationForFaultDataActivity().execute(":FDATA#", ":FDATA#", "START");
            } else {
                BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothDataExtrctionVicheeActivity.BluetoothCommunicationGetFaultParameter().execute(":FLENGTH#", ":FLENGTH#", "OKAY");
                    }
                });
            }

            super.onPostExecute(result);

        }
    }


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

            if (!Constant.isLoding) {
                baseRequest.showLoader();
            }

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
                       /* GetBTDATAListActivity.this.runOnUiThread(new Runnable() {
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


                                if (!mS[i].trim().equalsIgnoreCase("") || !mS[i].trim().equalsIgnoreCase(null)) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                } else {
                                    BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                                        }
                                    });

                                }
                            }
                        } else {
                            BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
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
                BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothDataExtrctionVicheeActivity.BluetoothCommunicationGetDayParameter().execute(":DLENGTH#", ":DLENGTH#", "OKAY");
                    }
                });
            }
            super.onPostExecute(result);

        }
    }

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

            if (!Constant.isLoding) {
                baseRequest.showLoader();
            }

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

                                if (!mS[i].trim().equalsIgnoreCase("") || !mS[i].trim().equalsIgnoreCase(null)) {
                                    if (i == 0) {
                                        //mLengthCount = Integer.parseInt(mS[i]);
                                        mLengthCount = Integer.valueOf(mS[i]);
                                    } else {
                                        mMonthHeaderList.add(mS[i]);
                                    }
                                }
                            }
                        } else {
                            BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
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
                jaiPOS = 0;
                new BluetoothCommunicationForFirstActivity().execute(":MDATA#", ":MDATA#", "START");
            } else {
                BluetoothDataExtrctionVicheeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BluetoothDataExtrctionVicheeActivity.BluetoothCommunicationGetMonthParameter().execute(":MLENGTH#", ":MLENGTH#", "OKAY");
                    }
                });
            }
        }
    }

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


    private void UploadFileToServerOption(final Context context) {
        // custom dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.alertDialog);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //  builder.setTitle(getString(R.string.Back_head));
        builder.setMessage(getString(R.string.file_upload_text));

        String positiveText = getString(R.string.text_15_Days);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {/////Change Day to month
                          //  filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Month_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                            filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/Month_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                            // Log.d("picUri", picUri.toString());
                            Log.d("filePath", filePath);

                         //  String[] mDataNameString = filePath.split("files/");
                            String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
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

        String negativeText = getString(R.string.text_5_Years);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        try {///Change month to day
                             // filePath = "/storage/emulated/0/Android/data/com.shaktipumps.shakti_rms/files/Day_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                            filePath = "/storage/emulated/0/Documents/ShaktiExtractionFile/Day_" + mBtNameHead + ".xls";//Month_26-0018-0-18-03-19-0.xls";
                            // Log.d("picUri", picUri.toString());
                            Log.d("filePath", filePath);
                            //String[] mDataNameString = filePath.split("files/");
                            String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
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
    }


    public void GetProfileUpdate_Task(String deviceno, String type) {

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
            //RequestBody type1 = RequestBody.create(MediaType.parse("multipart/form-data"), "Day");

            Call<ProfileUpdateModel> call = apiService.getProfileUpdateData(deviceno1, type1, body);

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

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            //filePath = data.getData().toString();
            filePath = getPath(data.getData());

            // Log.d("picUri", picUri.toString());
            Log.d("filePath", filePath);

           /// String[] mDataNameString = filePath.split("files/");
            String[] mDataNameString = filePath.split("ShaktiExtractionFile/");
            String[] mDataNameString1 = mDataNameString[1].split(".xls");
            String[] mDataNameString2 = mDataNameString1[0].split("_");

            GetProfileUpdate_Task(mDataNameString2[1], mDataNameString2[0]);
            // GetProfileUpdate_Task("04-0018-0-18-03-19-0", "Day");
            //filePath = data.getData();
        }
    }

}
