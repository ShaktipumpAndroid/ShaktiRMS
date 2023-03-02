package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.Debug.DebugModel;
import com.shaktipumps.shakti_rms.other.Utils;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static java.lang.Thread.sleep;


public class GetBTDebugDataActivity extends AppCompatActivity {
    private BaseRequest baseRequest;
    private static Workbook wb = null;
    private static CellStyle cs = null;
    private static Sheet sheet1 = null;
    private static Row row;
    private static Cell c = null;

    private InputStream iStream = null;
    private UUID mMyUDID;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    RelativeLayout rlvDebugONBTNID, rlvDebugStartBTNID, rlvDebugStopBTNID;

    LinearLayout lvlMainTextContainerID;
    EditText edtPutCommandID;

    String AllCommomSTRContainer = "";
    private ScrollView scrlViewID;
    /////////////////day
    /////////////this is for months

    int[] mTotalTime;

    int pp = 1;

    String AllTextSTR = "";

    int jk = 0;

    RelativeLayout rlvBackViewID;
    private Context mContext;

    private String mModelType, mBtNameHead, mDeviceType, mBtMacAddressHead;
    private String MUserId = "null", MDeviceId = "null";

    private ImageView imgSendTextID;

    private ImageView imgBTShareFILEID;
    private ImageView imgBTUploadFILEID;
    private String allCammand = "AT+CPIN?";
    int kkkkkk;
    String kkkkkk1;
    int clientid = 0;
    String ssssss;
    //private String []  AllCommandArray ={"AT+CPIN?\r\n","AT+GSN\r\n","AT+CIMI\r\n","AT+QINISTAT\r\n","AT+CSQ\r\n","AT+CREG?\r\n","AT+CGREG?\r\n","AT+CGDCONT?\r\n","AT+QICSGP?\r\n"};
    private String[] AllCommandArray = {"AT+GSN\r\n", "AT+CIMI\r\n", "AT+QINISTAT\r\n", "AT+CSQ\r\n", "AT+CREG?\r\n", "AT+CGREG?\r\n", "AT+CGDCONT?\r\n", "AT+QICSGP?\r\n"};
    //private String []  AllCommandArray ={"AT+CPIN?","AT+GSN","AT+CIMI","AT+QINISTAT","AT+CSQ","AT+CREG?","AT+CGREG?","AT+CGDCONT?","AT+QICSGP?"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_btdata_debug);
        mContext = this;
        initView();
    }


    private void initView() {
        //setGIFLoadingView(mContext);
        //ShowLoadingView();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        baseRequest = new BaseRequest(mContext);


        mModelType = getIntent().getStringExtra("ModelType");
        mDeviceType = getIntent().getStringExtra("DeviceType");
        mBtNameHead = getIntent().getStringExtra("BtNameHead");
        mBtMacAddressHead = getIntent().getStringExtra("BtMacAddressHead");

        try {
            MUserId = pref.getString("key_muserid", "invalid_muserid");
            clientid = Integer.parseInt(pref.getString("key_clientid", "0"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //rclSettingListViewID = (RecyclerView) findViewById(R.id.rclSettingListViewID);
        scrlViewID = (ScrollView) findViewById(R.id.scrlViewID);
        imgSendTextID = (ImageView) findViewById(R.id.imgSendTextID);
        imgBTShareFILEID = (ImageView) findViewById(R.id.imgBTShareFILEID);
        imgBTUploadFILEID = (ImageView) findViewById(R.id.imgBTUploadFILEID);

        rlvBackViewID = (RelativeLayout) findViewById(R.id.rlvBackViewID);
        rlvDebugONBTNID = (RelativeLayout) findViewById(R.id.rlvDebugONBTNID);
        rlvDebugStartBTNID = (RelativeLayout) findViewById(R.id.rlvDebugStartBTNID);
        rlvDebugStopBTNID = (RelativeLayout) findViewById(R.id.rlvDebugStopBTNID);

        lvlMainTextContainerID = (LinearLayout) findViewById(R.id.lvlMainTextContainerID);
        edtPutCommandID = (EditText) findViewById(R.id.edtPutCommandID);

        setClickEventListner();
        //    saveExcelFile(mContext, "VikasTest.xls",mPostionFinal);
    }

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
        imgBTUploadFILEID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                SaveImage(AllCommomSTRContainer);
                baseRequest.showLoader();
                 callInsertAndUpdateDebugDataAPI(AllCommomSTRContainer);

            }
        });

        imgBTShareFILEID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                SaveImage(AllCommomSTRContainer);
                shareDATA(AllCommomSTRContainer);
            }
        });

        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        rlvDebugONBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  finish();
                pp++;
                lvlMainTextContainerID.addView(getTextViewTT(pp, ":AT DEBUG#"));

                // AllTextSTR = AllTextSTR +"\n :AT DEBUG#";
                AllCommomSTRContainer = AllCommomSTRContainer + "\n :AT DEBUG#";

                new BluetoothCommunicationForDebugON().execute(":AT DEBUG#", ":AT DEBUG#", "START");
            }
        });

        rlvDebugStartBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                //  new BluetoothCommunicationForDebugON().execute("AT+QPOWD+1", "AT+QPOWD+1", "Stop");
                lvlMainTextContainerID.addView(getTextViewTT(pp, "ATEO"));
                AllCommomSTRContainer = AllCommomSTRContainer + "\nATE0";
                // AllTextSTR = AllTextSTR +"\n"+edtPutCommandID.getText().toString();
                new BluetoothCommunicationForDebugStartOnes().execute("ATE0\r\n", "ATE0", "Start");
            }
        });

        rlvDebugStopBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                //  new BluetoothCommunicationForDebugON().execute("AT+QPOWD+1", "AT+QPOWD+1", "Stop");

                //lvlMainTextContainerID.addView(getTextViewTT(pp, "AT+QPOWD=1"));
                // AllTextSTR = AllTextSTR +"\n"+edtPutCommandID.getText().toString();

                new BluetoothCommunicationForDebugON().execute("AT+QPOWD=1\r\n", "AT+QPOWD=1", "Start");
            }
        });

        imgSendTextID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp++;
                if (!edtPutCommandID.getText().toString().isEmpty()) {
                  //  getTextViewTT(pp, edtPutCommandID.getText().toString());
                    lvlMainTextContainerID.addView(getTextViewTT(pp, edtPutCommandID.getText().toString()));
                    //AllTextSTR = AllTextSTR +"\n"+edtPutCommandID.getText().toString();

                    AllCommomSTRContainer = AllCommomSTRContainer +"\n"+edtPutCommandID.getText().toString();
                    new BluetoothCommunicationForDebugStartType().execute(edtPutCommandID.getText().toString()+"\r\n", edtPutCommandID.getText().toString(), "Start");
                  //  writeFileOnInternalStorage(mContext, "RMS_DOC_FILE.txt" ,AllCommomSTRContainer);
                  //  SaveImage(AllCommomSTRContainer);

                    //writeFileOnInternalStorage(mContext, "RMSFILE", AllTextSTR);
                }
                else {
                    Toast.makeText(mContext, "Please write the cammand!", Toast.LENGTH_SHORT).show();
                }
          //      SaveImage(AllCommomSTRContainer);
                // shareDATA(AllCommomSTRContainer);
                //baseRequest.showLoader();
               // callInsertAndUpdateDebugDataAPI(AllCommomSTRContainer);

            /*    if (!edtPutCommandID.getText().toString().isEmpty()) {
                    File file = new File(mContext.getFilesDir(), "text");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    try {
                        File gpxfile = new File(file, "Vikas_testing_text");
                        FileWriter writer = new FileWriter(gpxfile);
                        writer.append(edtPutCommandID.getText().toString());
                        writer.flush();
                        writer.close();
                       // Toast.makeText(mContext, "Saved your text", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });
//////////////////
    }

    private void SaveImage(String filename) {

        File file = new File(mContext.getExternalFilesDir(null), "DEBUG_" + mBtNameHead + ".txt");
        // File file = new File(mContext.getExternalFilesDir(null), "Month_" + mBtNameHead + ".xlsx");
        FileOutputStream os = null;
        System.out.println("vikas--4==>4");
        //baseRequest.hideLoader();
        try {
            os = new FileOutputStream(file);
            byte b[] = filename.getBytes();//converting string into byte array
            os.write(b);
            os.flush();
            //   wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            //success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                os = new FileOutputStream(file);
                //   wb.write(os);

                byte b[] = filename.getBytes();//converting string into byte array
                os.write(b);
                os.flush();
                //wb.write(os);
                if (null != os)
                    os.close();
            } catch (Exception ex) {
                System.out.println("vikas--5==>5");
                // baseRequest.hideLoader();
                ex.printStackTrace();
            }
        }

      /*  try {

            File myDir = new File(getCacheDir(), "folder");
            myDir.mkdir();

            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/MyFolder/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            File f = new File(rootPath + "mttext.txt");
            if (f.exists()) {
                f.delete();
            }
            f.mkdirs();
            f.createNewFile();

            FileOutputStream out = new FileOutputStream(f);
            out.write(Integer.parseInt(filename));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /* String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Shakti_MOBILE/Debug_Data");
        myDir.mkdirs();

        String fname = "RMS_DEBUG.txt";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
          //  finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(out);
            myOutWriter.append(filename);
            myOutWriter.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody) {
        /*File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/text/");
        dir.mkdir();
        File file = new File(dir, "Jaya.txt");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(Integer.parseInt(sBody));
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {

            File cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "Shakti_RMS/Debug_Data");

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            String path =
                    android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Shakti_RMS/Debug_Data" + "/" +
                            String.valueOf(System.currentTimeMillis()) + ".txt";
            Utils.saveDOC(path);
/*            File myFile = new File(Environment.getExternalStorageDirectory(), "VIKAS_file/VICKY_DATA.csv");
            if (!myFile.exists()) {
                myFile.mkdirs();
                myFile.createNewFile();
            }*/

            /*File f = new File("VIKAS_file/VICKY_DATA");
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            if (!f.exists())
                f.createNewFile();*/
            // File myFile = new File("/VIKAS_file/VICKY_DATA.txt");
            //myFile.createNewFile();
            // FileOutputStream fOut = new FileOutputStream(myFile);
           /* FileOutputStream fOut = new FileOutputStream(cacheDir);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(sBody);
            myOutWriter.close();
            fOut.close();*/
            Toast.makeText(mContext, "VIKAS_file saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private TextView getTextViewTT(int id, String title) {
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        tv.setLayoutParams(params);
        tv.setId(id);
        // tv.setText(title.toUpperCase());
        tv.setText(title);
        tv.setTextColor(getResources().getColor(R.color.white));

        tv.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
        tv.setBackgroundColor(getResources().getColor(R.color.black));
        tv.setGravity(Gravity.START);

        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    private TextView getTextViewTTppSingle(int id, String title) {
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        tv.setLayoutParams(params);
        tv.setId(id);
        // tv.setText(title.toUpperCase());
        tv.setText(title);
        tv.setTextColor(getResources().getColor(R.color.green));

        tv.setTextSize((int) getResources().getDimension(R.dimen._6ssp));
        tv.setBackgroundColor(getResources().getColor(R.color.black));
        tv.setGravity(Gravity.START);

        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    private TextView getTextViewTTpp(int id, String title) {
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, -35, 0, 20);
        tv.setLayoutParams(params);
        tv.setId(id);
        // tv.setText(title.toUpperCase());
        tv.setText(title);
        tv.setTextColor(getResources().getColor(R.color.green));

        tv.setTextSize((int) getResources().getDimension(R.dimen._7ssp));
        tv.setBackgroundColor(getResources().getColor(R.color.black));
        tv.setGravity(Gravity.START);

        // tv.setWidth(200);
        // tv.setOnClickListener(this);
        return tv;
    }

    //float sizeInPixels = getResources().getDimension(R.dimen.my_value);

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


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDebugON extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {

                //edtPutCommandID.setText("");
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
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                    myBluetooth.cancelDiscovery();
                }
//                if (btSocket == null) {
//                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
//                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
//                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
//                    myBluetooth.cancelDiscovery();
//                }
                if (!btSocket.isConnected())
                    btSocket.connect();//start connection


                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);

                    try {
                        btSocket.getOutputStream().write(STARTRequest);
                        sleep(1000);
                        iStream = btSocket.getInputStream();
                        while (true) {

                            try {
                                kkkkkk1 = (char) iStream.read() + "";

                                AllTextSTR = AllTextSTR + kkkkkk1;
                            } catch (IOException e) {

                                baseRequest.hideLoader();
                                e.printStackTrace();
                                break;
                            }

                        }


                        // AllTextSTR = AllTextSTR +"\n"+edtPutCommandID.getText().toString();

                        //  lvlMainTextContainerID.addView(getTextViewTT(pp, ""+iStream));


                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ///  addHeadersMonths();
                            // ssssss = ssssss +(char) kkkkkk;
                          //  AllCommomSTRContainer = AllCommomSTRContainer + "\n" + AllTextSTR;

                           // lvlMainTextContainerID.addView(getTextViewTT(pp, "\n" + AllTextSTR));
                          //  AllTextSTR = "";
                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                        }
                    });


                }
            } catch (Exception e) {

                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;

                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            scrlViewID.fullScroll(View.FOCUS_DOWN);
            baseRequest.hideLoader();


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDebugStartOnes extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {

            try {

                if (btSocket != null) {
                    if (btSocket.isConnected()) {

                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
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

                        while (true) {

                            try {
                                kkkkkk1 = (char) iStream.read() + "";

                                AllTextSTR = AllTextSTR + kkkkkk1;
                                AllTextSTR = AllTextSTR.replaceAll("[\r]", "");
                                AllTextSTR = AllTextSTR.replaceAll("[\n]", "");

                                if (iStream.available() == 0) {
                                    break;
                                }

                            } catch (IOException e) {
                                baseRequest.hideLoader();

                                e.printStackTrace();
                                break;
                            }

                            //ssssss = ssssss + () kkkkkk1;
                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();

                        e1.printStackTrace();

                    }

                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ///  addHeadersMonths();
                            AllCommomSTRContainer = AllCommomSTRContainer + "\n" + AllTextSTR;
                            lvlMainTextContainerID.addView(getTextViewTTppSingle(pp, "" + AllTextSTR));
                            AllTextSTR = "";
                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                        }
                    });*/
                }
            } catch (Exception e) {
                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            //allCammand = AllCommandArray[i];

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ///  addHeadersMonths();
                    AllCommomSTRContainer = AllCommomSTRContainer + "\n" + AllTextSTR;
                    lvlMainTextContainerID.addView(getTextViewTTppSingle(pp, "" + AllTextSTR));
                    lvlMainTextContainerID.addView(getTextViewTT(pp, "AT+CPIN?"));
                    AllTextSTR = "";
                    // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                }
            });


            AllCommomSTRContainer = AllCommomSTRContainer + "\n AT+CPIN?";
            new BluetoothCommunicationForDebugStart().execute("AT+CPIN?\r\n", "AT+CPIN?\r\n", "Start");
            scrlViewID.fullScroll(View.FOCUS_DOWN);
            baseRequest.hideLoader();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDebugStart extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
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
                        while (true) {
                            try {
                                kkkkkk1 = (char) iStream.read() + "";
                                AllTextSTR = AllTextSTR + kkkkkk1;
                                AllTextSTR = AllTextSTR.replaceAll("[\r]", "");
                                AllTextSTR = AllTextSTR.replaceAll("[\n]", "");
                                if (iStream.available() == 0) {
                                    break;
                                }
                            } catch (IOException e) {
                                baseRequest.hideLoader();
                                e.printStackTrace();
                                break;
                            }
                            //ssssss = ssssss + () kkkkkk1;
                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ///addHeadersMonths();
                            AllCommomSTRContainer = AllCommomSTRContainer + "\n" + AllTextSTR;
                            lvlMainTextContainerID.addView(getTextViewTTpp(pp, "" + AllTextSTR));
                            AllTextSTR = "";
                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                return false;
            }

            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            pp++;
               /* for (int i = 0; i < AllCommandArray.length; i++) {

                    allCammand = AllCommandArray[i];


                    new BluetoothCommunicationForDebugStart().execute(AllCommandArray[i], AllCommandArray[i], "Start");

                  //  jk++;

                    if(i >= AllCommandArray.length)
                    {
                        break;
                    }
                }*/





            if (jk < AllCommandArray.length) {
                try {
                    AllCommomSTRContainer = AllCommomSTRContainer + "\n" + AllCommandArray[jk];
                    lvlMainTextContainerID.addView(getTextViewTT(pp, "" + AllCommandArray[jk]));
                    new BluetoothCommunicationForDebugStart().execute(AllCommandArray[jk], AllCommandArray[jk], "Start");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            jk++;
            baseRequest.hideLoader();

            scrlViewID.fullScroll(View.FOCUS_DOWN);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationForDebugStartType extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public int RetryCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMyUDID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            baseRequest.showLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                    } else {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
                        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(Constant.BT_DEVICE_MAC_ADDRESS);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createRfcommSocketToServiceRecord(mMyUDID);//create a RFCOMM (SPP) connection
                        myBluetooth.cancelDiscovery();
                    }
                } else {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //   BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBtMacAddressHead);//connects to the device's address and checks if it's available
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
                        while (true) {
                            try {
                                kkkkkk1 = (char) iStream.read() + "";
                                AllTextSTR = AllTextSTR + kkkkkk1;
                                AllTextSTR = AllTextSTR.replaceAll("[\\r]", "");
                                AllTextSTR = AllTextSTR.replaceAll("[\\n]", "");
                                if (iStream.available() == 0) {
                                    break;
                                }
                            } catch (IOException e) {
                                baseRequest.hideLoader();
                                e.printStackTrace();
                                break;
                            }
                            //ssssss = ssssss + () kkkkkk1;
                        }

                    } catch (InterruptedException e1) {
                        baseRequest.hideLoader();
                        e1.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ///  addHeadersMonths();
                            AllCommomSTRContainer = AllCommomSTRContainer + "\n" + AllTextSTR;
                            lvlMainTextContainerID.addView(getTextViewTTppSingle(pp, "" + AllTextSTR));
                            AllTextSTR = "";
                            // addDataMonth(mPostionFinal + 1, mvDay + "", mvMonth + "", mvYear + "", mvHour, mvMinute, mvNo_of_Start, fvFrequency, fvRMSVoltage, fvOutputCurrent, mvRPM, fvLPM, fvPVVoltage, fvPVCurrent, mvFault, fvInvTemp);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                baseRequest.hideLoader();
                // btSocket = null;
                //   Toast.makeText(mActivity, "BT Connection lost..", Toast.LENGTH_SHORT).show();
                // myBluetooth.disable();
                return false;
            }
            baseRequest.hideLoader();
            return false;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            baseRequest.hideLoader();
            scrlViewID.fullScroll(View.FOCUS_DOWN);
        }
    }

    private void shareDATA(String filename) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        //  sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(filename));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, filename);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void callInsertAndUpdateDebugDataAPI(String AllCommomSTRContainerIN) {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    //ForgotPassModelView mDebugModelppp = gson.fromJson(Json, ForgotPassModelView.class);
                    DebugModel mDebugModel = gson.fromJson(Json, DebugModel.class);
                    baseRequest.hideLoader();
                    if (mDebugModel.getStatus()) {
                        // if(arraylist.size()-1 ==  pos)
                        //    Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
                        ///password reset sucessfully
                        // Constant.CHECK_FORGOT_PASS_COME_ONES_ORMORE = 2;////finish old screen
                        // finish();
                        Toast.makeText(mContext, mDebugModel.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, mDebugModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //  getDeviceSettingListResponse(mSettingModelView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int APINumber, String errorCode, String message) {
                baseRequest.hideLoader();
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkFailure(int APINumber, String message) {
                baseRequest.hideLoader();
                Toast.makeText(mContext, "Please check internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        JsonObject jsonObject = new JsonObject();
        try {
            ////Put input parameter here
            jsonObject.addProperty("DeviceNo", mBtNameHead);
            jsonObject.addProperty("Content", AllCommomSTRContainerIN);
            //  jsonObject.addProperty("fcmToken", NewSolarVFD.FCM_TOKEN);
            //  jsonObject.addProperty("imei", NewSolarVFD.IMEI_NUMBER);
            System.out.println("RMSVIKAS   Content=" + AllCommomSTRContainerIN + ", DeviceNo=" + mBtNameHead);
        } catch (Exception e) {
            baseRequest.hideLoader();
            e.printStackTrace();
        }
        //  baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.INSERT_DEBUG_DATA_API);/////
        //baseRequest.callAPIPut(1, jsonObject, NewSolarVFD.ORG_RESET_FORGOTPASS);/////
    }
}