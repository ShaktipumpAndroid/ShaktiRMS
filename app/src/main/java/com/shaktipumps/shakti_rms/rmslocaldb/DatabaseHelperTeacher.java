package com.shaktipumps.shakti_rms.rmslocaldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.shaktipumps.shakti_rms.bean.AoneSSBean.AOneSSResponse;
import com.shaktipumps.shakti_rms.bean.Customer_GPS_Search;
import com.shaktipumps.shakti_rms.bean.DaynamicButton.DynamicBTNResponse;
import com.shaktipumps.shakti_rms.bean.KLPBean.KLPTotEnergyResponse;
import com.shaktipumps.shakti_rms.bean.KLPGrid.KLPGridModelResponse;
import com.shaktipumps.shakti_rms.bean.KLPHybride.KLPHybrideResponse;
import com.shaktipumps.shakti_rms.bean.Nikola.NikolaResponse;
import com.shaktipumps.shakti_rms.bean.OLD_klp.OldKLPResponse;
import com.shaktipumps.shakti_rms.bean.PaymentBean.PaymentPlanResponse;
import com.shaktipumps.shakti_rms.bean.ProductStausBean.ProductStatusResponse;
import com.shaktipumps.shakti_rms.bean.RealMonitoring;
import com.shaktipumps.shakti_rms.bean.SAJ.SAJResponse;
import com.shaktipumps.shakti_rms.bean.ShimaBean.ShimaResponse;
import com.shaktipumps.shakti_rms.bean.Uspc.UspcModelResponse;
import com.shaktipumps.shakti_rms.bean.UspcEnergy.UspcEnrgyResponse;
import com.shaktipumps.shakti_rms.bean.ViechiBean.ViechiDataResponse;
import com.shaktipumps.shakti_rms.model.SettingModel.SettingModelResponse;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Sayem on 12/5/2017.
 */
public class DatabaseHelperTeacher extends SQLiteOpenHelper {

    private OldKLPResponse mOldKLPResponse;
    private KLPGridModelResponse mKLPGridModelResponse;
    private SAJResponse mSAJResponse;
    private KLPTotEnergyResponse mKLPTotEnergyResponse;
    private KLPHybrideResponse mKLPHybrideResponse;
    private NikolaResponse mNikolaResponse;
    private ShimaResponse mShimaResponse;
    private UspcEnrgyResponse mUspcEnrgyResponse;
    private DynamicBTNResponse mDynamicBTNResponse;
    private ViechiDataResponse mViechiDataResponse;
    private AOneSSResponse mAOneSSResponse;

    public static String DATABASE_NAME_RMS = "rms_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "teachers";

    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "name";
    private static final String KEY_COURSE = "course";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    ////////////********************************Table name  Login*********************///////////////////////
    private static final String TABLE_USER_LOGIN = "userlogin";

    /////////////****************************Login AUTHModelData parameter*******************//////////
    private static final String LOGIN_KEY_IDD = "idd";
    private static final String LOGIN_KEY_ID = "id";
    private static final String LOGIN_USER_NAME = "username";
    private static final String LOGIN_USER_PASSWORD = "password";
    private static final String LOGIN_USER_ID = "userid";
    private static final String LOGIN_PARENT_ID = "parentid";
    private static final String LOGIN_MOBILE_NO = "phone";
    private static final String LOGIN_CLIENT_ID = "clientid";
    private static final String IS_LOGIN = "isLogin";
    private static final String LOGIN_STATUS = "status";
    private static final String LOGIN_ACTIVE = "active";

    private static final String LOGIN_TEST1 = "LoginTest1";
    private static final String LOGIN_TEST2 = "LoginTest2";
    private static final String LOGIN_TEST3 = "LoginTest3";
    private static final String LOGIN_TEST4 = "LoginTest4";
    ///*********************************end ****************//////////

    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE "
            + TABLE_USER_LOGIN + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LOGIN_USER_ID + " TEXT NOT NULL, " +
            LOGIN_PARENT_ID + " TEXT NOT NULL, " +
            LOGIN_USER_NAME + " TEXT NOT NULL, " +
            LOGIN_USER_PASSWORD + " TEXT NOT NULL, " +
            LOGIN_MOBILE_NO + " VARCHAR, " +
            LOGIN_CLIENT_ID + " TEXT NOT NULL, " +
            IS_LOGIN + " TEXT NOT NULL, " +
            LOGIN_STATUS + " TEXT NOT NULL, " +
            LOGIN_ACTIVE + " TEXT NOT NULL, " +
            LOGIN_TEST1 + " VARCHAR, " +
            LOGIN_TEST2 + " VARCHAR, " +
            LOGIN_TEST3 + " VARCHAR " +
            "); ";


    ////////////////////this is for deviec list

    private ArrayList<PaymentPlanResponse> mPaymentPlanResponseList;
    PaymentPlanResponse mPaymentPlanResponse;
    //private Customer_GPS_Search customer_gps;
    private static final String PLAN_LIST_PAYMENT_NO = "DeviceNo";
    private static final String PLAN_LIST_PAYMENT_DTYPE = "DeviceType";
    private static final String PLAN_LIST_PAYMENT_MDID = "MDeviceId";
    private static final String PLAN_LIST_PAYMENT_MUSERID = "MUserId";
    private static final String PLAN_LIST_PAYMENT_PLANID = "PlanId";
    private static final String PLAN_LIST_PAYMENT_PLANDURATION = "PlanDuration";
    private static final String PLAN_LIST_PAYMENT_AMOUNT = "PlanAmount";
    private static final String PLAN_LIST_PAYMENT_PLANDECREPTION = "PlanDescription";
    private static final String PLAN_LIST_PAYMENT_STATUS = "Status";

    private static final String PLAN_LIST_PAYMENT_TEST1 = "Test1";
    private static final String PLAN_LIST_PAYMENT_TEST2 = "Test2";
    private static final String PLAN_LIST_PAYMENT_TEST3 = "Test3";
    private static final String PLAN_LIST_PAYMENT_TEST4 = "Test4";
    private static final String PLAN_LIST_TABLE_NAME = "paymentoption";////////////////table name
    // private static final String DEVICE_LIST_TABLE_NAME = "devicelist";

    private static final String CREATE_TABLE_PLAN_LIST = "CREATE TABLE "
            + PLAN_LIST_TABLE_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PLAN_LIST_PAYMENT_NO + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_DTYPE + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_MDID + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_MUSERID + " VARCHAR, " +
            PLAN_LIST_PAYMENT_PLANID + " VARCHAR, " +
            PLAN_LIST_PAYMENT_PLANDURATION + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_AMOUNT + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_PLANDECREPTION + " VARCHAR, " +
            PLAN_LIST_PAYMENT_STATUS + " VARCHAR, " +
            PLAN_LIST_PAYMENT_TEST1 + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_TEST2 + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_TEST4 + " TEXT NOT NULL, " +
            PLAN_LIST_PAYMENT_TEST3 + " TEXT NOT NULL " +

            "); ";


    public ArrayList<PaymentPlanResponse> getPaymentOPTIONListData() {
        mPaymentPlanResponseList = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;
        // int id=-1; //0
        String id = ""; //0
        String mCustomer_name = ""; //1
        String mDeviceNo = "";//2
        String mDeviceType = "";//3
        String mMDeviceId = "";//5
        String mMUserId = "";///6
        String mPlanId = "";///6
        String mPlanDescription = "";///7
        String mPlanDuration = "";///7
        String mPlanAMT = "";///7

        String mPlanStatus = "";///7
        String mISlogin = "";///7


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + PLAN_LIST_TABLE_NAME, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        mPaymentPlanResponse = new PaymentPlanResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        // mCustomer_name = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_NAME));
                        //  Customer_GPS_SearchList.set(PU,mCustomer_name);
                        mDeviceNo = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_NO));
                        //  Customer_GPS_SearchList.set()
                        mDeviceType = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_DTYPE));
                        mMDeviceId = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_MDID));
                        mMUserId = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_MUSERID));
                        mPlanId = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_PLANID));
                        mPlanDuration = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_PLANDURATION));
                        mPlanAMT = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_AMOUNT));
                        mPlanDescription = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_PLANDECREPTION));
                        mPlanStatus = cursor.getString(cursor.getColumnIndex(PLAN_LIST_PAYMENT_STATUS));

                        // mTypeName = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_TEST4));



                        // customer_gps.setCustomer_name(mCustomer_name);
                        mPaymentPlanResponse.setMUserId(mMUserId);
                        mPaymentPlanResponse.setMDeviceId(mMDeviceId);
                        mPaymentPlanResponse.setPlanId(mPlanId);
                        // mPaymentPlanResponse.setDe
                        mPaymentPlanResponse.setPlanAmount(mPlanAMT);
                        mPaymentPlanResponse.setPaymentStatus(mPlanStatus);
                        mPaymentPlanResponse.setPlanDescription(mPlanDescription);
                        mPaymentPlanResponse.setPlanDuration(mPlanDuration);


                        // customer_gps.setIsLogin(mISlogin);

                        mPaymentPlanResponseList.add(mPaymentPlanResponse);

                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            //  db.endTransaction();
            // End the transaction.
            if (db != null && db.inTransaction()) {
                //  db.endTransaction();
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        //  return mAllUserInfo;
        return mPaymentPlanResponseList;
    }

    ///////////////////////////////////////////////////////
    public long insertPaymentOPTIONListData(String DeviceNo, String DeviceType, String MDeviceId, String MUserId, String mPlanID, String mPlanDuration, String mPlanAMT, String mPlanStatus, String mPlanDescription, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (mCheckFirst) {
            //db.execSQL("TRUNCATE table" + DEVICE_LIST_TABLE_NAME);
            db.execSQL("delete from " + PLAN_LIST_TABLE_NAME);
            mCheckFirst = false;
        }

        // Creating content values
        ContentValues values = new ContentValues();

        values.put(PLAN_LIST_PAYMENT_NO, DeviceNo);
        values.put(PLAN_LIST_PAYMENT_DTYPE, DeviceType);
        values.put(PLAN_LIST_PAYMENT_MDID, MDeviceId);
        values.put(PLAN_LIST_PAYMENT_MUSERID, MUserId);
        values.put(PLAN_LIST_PAYMENT_PLANID, mPlanID);
        values.put(PLAN_LIST_PAYMENT_PLANDURATION, mPlanDuration);
        values.put(PLAN_LIST_PAYMENT_AMOUNT, mPlanAMT);
        values.put(PLAN_LIST_PAYMENT_PLANDECREPTION, mPlanDescription);
        values.put(PLAN_LIST_PAYMENT_STATUS, mPlanStatus);
        values.put(PLAN_LIST_PAYMENT_TEST1, "Vikas");
        values.put(PLAN_LIST_PAYMENT_TEST2, "Vikas");
        values.put(PLAN_LIST_PAYMENT_TEST3, "Vikas");
        values.put(PLAN_LIST_PAYMENT_TEST4, "Ganesh");

        //insert row in table
        long insert = db.insert(PLAN_LIST_TABLE_NAME, null, values);

        return insert;
    }





    ////////////////////this is for deviec list

   // private ArrayList<Customer_GPS_Search> Customer_GPS_SearchList;
    List<UspcModelResponse> mUspcModelResponselist;
    UspcModelResponse mUspcModelResponse1;
    //private Customer_GPS_Search customer_gps;
    private static final String OPTION_LIST_DEVICE_NO = "DeviceNo";
    private static final String OPTION_LIST_DEVICE_DTYPE = "DeviceType";
    private static final String OPTION_LIST_DEVICE_MDID = "MDeviceId";
    private static final String OPTION_LIST_DEVICE_MUSERID = "MUserId";
    private static final String OPTION_LIST_DEVICE_MODEID = "modid";
    private static final String OPTION_LIST_DEVICE_BG_COLOR = "bgColor";
    private static final String OPTION_LIST_DEVICE_BT_ADDRESS = "mBTaddress";
    private static final String OPTION_LIST_DEVICE_PRO_PRO_IMAGE = "image";
    private static final String OPTION_LIST_DEVICE_PRO_TITTLE = "title";
    private static final String OPTION_LIST_DEVICE_PRO_DISPLAYINDEX = "displayIndex";
    private static final String OPTION_LIST_DEVICE_PRO_ADDRESS = "address";
    private static final String OPTION_LIST_DEVICE_MODESTATUS = "status";


    private static final String OPTION_LIST_DEVICE_TEST1 = "Test1";
    private static final String OPTION_LIST_DEVICE_TEST2 = "Test2";
    private static final String OPTION_LIST_DEVICE_TEST3 = "Test3";
    private static final String OPTION_LIST_DEVICE_TEST4 = "Test4";
    private static final String USPC_OPTION_LIST_DEVICE_TABLE_NAME = "uspcoptionlist";////////////////table name
    // private static final String DEVICE_LIST_TABLE_NAME = "devicelist";

    private static final String CREATE_TABLE_USPC_OPTION_LIST_DEVICE = "CREATE TABLE "
            + USPC_OPTION_LIST_DEVICE_TABLE_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            OPTION_LIST_DEVICE_NO + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_DTYPE + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_MDID + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_MODEID + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_MUSERID + " VARCHAR, " +
            OPTION_LIST_DEVICE_PRO_PRO_IMAGE + " VARCHAR, " +
            OPTION_LIST_DEVICE_PRO_TITTLE + " VARCHAR, " +
            OPTION_LIST_DEVICE_PRO_DISPLAYINDEX + " VARCHAR, " +
            OPTION_LIST_DEVICE_PRO_ADDRESS + " VARCHAR, " +
            OPTION_LIST_DEVICE_MODESTATUS + " VARCHAR, " +
            OPTION_LIST_DEVICE_BG_COLOR + " VARCHAR, " +
            OPTION_LIST_DEVICE_BT_ADDRESS + " VARCHAR, " +
            OPTION_LIST_DEVICE_TEST1 + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_TEST2 + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_TEST4 + " TEXT NOT NULL, " +
            OPTION_LIST_DEVICE_TEST3 + " TEXT NOT NULL " +

            "); ";



    public List<UspcModelResponse> getUSPCOPTIONListData() {
        mUspcModelResponselist = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;
        // int id=-1; //0
        String id = ""; //0
        String mCustomer_name = ""; //1
        String mDeviceNo = "";//2
        String mDeviceType = "";//3
        String mMDeviceId = "";//5
        String mMUserId = "";///6
        String mPlanId = "";///6
        String mMODEId = "";///6

        String mProImage = "";///7
        String mTittle = "";///7
        String mDisplayIndex = "";///7
        String mAddress = "";///7
        String mPROStatus = "";///7
        String mBGColor = "";///7
        String mBTAddress = "";///7


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + USPC_OPTION_LIST_DEVICE_TABLE_NAME, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        mUspcModelResponse1 = new UspcModelResponse();
                      //  id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        mDeviceNo = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_NO));
                        //  Customer_GPS_SearchList.set()
                     //   mDeviceType = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_DTYPE));
                      //  mMDeviceId = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_MDID));
                     //   mMUserId = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_MUSERID));
                        mMODEId = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_MODEID));
                        mProImage = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_PRO_PRO_IMAGE));
                        mTittle = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_PRO_TITTLE));
                        mDisplayIndex = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_PRO_DISPLAYINDEX));
                        mAddress = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_PRO_ADDRESS));
                        mPROStatus = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_MODESTATUS));
                        mBGColor = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_BG_COLOR));
                        mBTAddress = cursor.getString(cursor.getColumnIndex(OPTION_LIST_DEVICE_BT_ADDRESS));

                       // customer_gps.setCustomer_name(mCustomer_name);
                        mUspcModelResponse1.setId(mMODEId);
                        mUspcModelResponse1.setTitle(mTittle);
                        mUspcModelResponse1.setImage(mProImage);
                        mUspcModelResponse1.setAddress(mAddress);
                        mUspcModelResponse1.setBgColor(mBGColor);
                        mUspcModelResponse1.setDisplayIndex(mDisplayIndex);
                        mUspcModelResponse1.setStatus(mPROStatus);
                        mUspcModelResponse1.setBTaddress(mBTAddress);
                       // mUspcModelResponse1.setDe
                       // customer_gps.setIsLogin(mISlogin);
                        mUspcModelResponselist.add(mUspcModelResponse1);

                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            //  db.endTransaction();
            // End the transaction.
            if (db != null && db.inTransaction()) {
                //  db.endTransaction();
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        //  return mAllUserInfo;
        return mUspcModelResponselist;
    }

    ///////////////////////////////////////////////////////
    public long insertUSPCOPTIONListData(String DeviceNo, String DeviceType, String MDeviceId, String MUserId, String mMODEID, String mImage, String mTittle, String mDisplayInedx, String mAddress,String mModeStatus, String mBGColor, String mBTaddress, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (mCheckFirst) {
            //db.execSQL("TRUNCATE table" + DEVICE_LIST_TABLE_NAME);
            db.execSQL("delete from " + USPC_OPTION_LIST_DEVICE_TABLE_NAME);
            mCheckFirst = false;
        }

        // Creating content values
        ContentValues values = new ContentValues();

        values.put(OPTION_LIST_DEVICE_NO, DeviceNo);
        values.put(OPTION_LIST_DEVICE_DTYPE, DeviceType);
        values.put(OPTION_LIST_DEVICE_MDID, MDeviceId);
        values.put(OPTION_LIST_DEVICE_MUSERID, MUserId);
        values.put(OPTION_LIST_DEVICE_MODEID, mMODEID);
        values.put(OPTION_LIST_DEVICE_PRO_PRO_IMAGE, mImage);
        values.put(OPTION_LIST_DEVICE_PRO_TITTLE, mTittle);
        values.put(OPTION_LIST_DEVICE_PRO_DISPLAYINDEX, mDisplayInedx);
        values.put(OPTION_LIST_DEVICE_PRO_ADDRESS, mAddress);
        values.put(OPTION_LIST_DEVICE_MODESTATUS, mModeStatus);
        values.put(OPTION_LIST_DEVICE_BG_COLOR, mBGColor);
        values.put(OPTION_LIST_DEVICE_BT_ADDRESS, mBTaddress);
        values.put(PLAN_LIST_PAYMENT_TEST1, "Vikas");
        values.put(PLAN_LIST_PAYMENT_TEST2, "Vikas");
        values.put(PLAN_LIST_PAYMENT_TEST3, "Vikas");
        values.put(PLAN_LIST_PAYMENT_TEST4, "Ganesh");








        //insert row in table
        long insert = db.insert(USPC_OPTION_LIST_DEVICE_TABLE_NAME, null, values);

        return insert;
    }






    ////////////////////this is for deviec list

    private ArrayList<Customer_GPS_Search> Customer_GPS_SearchList;

    private Customer_GPS_Search customer_gps;
    private static final String DEVICE_LIST_CUSTOMER_NAME = "customername";
    private static final String DEVICE_LIST_CUSTOMER_NO = "DeviceNo";
    private static final String DEVICE_LIST_CUSTOMER_DTYPE = "DeviceType";
    private static final String DEVICE_LIST_CUSTOMER_MDID = "MDeviceId";
    private static final String DEVICE_LIST_CUSTOMER_MUSERID = "MUserId";
    private static final String DEVICE_LIST_CUSTOMER_MOBILE = "Mobile";
    private static final String DEVICE_LIST_CUSTOMER_TYPENAME = "TypeName";
    private static final String DEVICE_LIST_CUSTOMER_MODELTYPE = "ModelType";
    private static final String DEVICE_LIST_CUSTOMER_PUMPSTATUS = "PumpStatus";
    private static final String DEVICE_LIST_CUSTOMER_ISLOGIN = "IsLogin";
    private static final String DEVICE_LIST_CUSTOMER_DEVICESTATUS = "DeviceStatus";
    private static final String DEVICE_LIST_CUSTOMER_TEST1 = "Test1";
    private static final String DEVICE_LIST_CUSTOMER_TEST2 = "Test2";
    private static final String DEVICE_LIST_CUSTOMER_TEST3 = "Test3";
    private static final String DEVICE_LIST_CUSTOMER_TEST4 = "Test4";
    private static final String DEVICE_LIST_TABLE_NAME = "devicelist";////////////////table name
    // private static final String DEVICE_LIST_TABLE_NAME = "devicelist";


    private static final String CREATE_TABLE_DEVICE_LIST = "CREATE TABLE "
            + DEVICE_LIST_TABLE_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_LIST_CUSTOMER_NAME + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_NO + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_DTYPE + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_MDID + " VARCHAR, " +
            DEVICE_LIST_CUSTOMER_MUSERID + " VARCHAR, " +
            DEVICE_LIST_CUSTOMER_MOBILE + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_TYPENAME + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_MODELTYPE + " VARCHAR, " +
            DEVICE_LIST_CUSTOMER_PUMPSTATUS + " VARCHAR, " +
            DEVICE_LIST_CUSTOMER_ISLOGIN + " VARCHAR, " +
            DEVICE_LIST_CUSTOMER_DEVICESTATUS + " VARCHAR, " +
            DEVICE_LIST_CUSTOMER_TEST1 + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_TEST2 + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_TEST4 + " TEXT NOT NULL, " +
            DEVICE_LIST_CUSTOMER_TEST3 + " TEXT NOT NULL " +
            "); ";

    ////////////////////this is for KUSPC type option list

    // private ArrayList<Customer_GPS_Search> Customer_GPS_SearchList;
    private List<UspcModelResponse> mUspcModelResponseList;

    /* "id": 4,
             "image": "http://localhost:8086/Home/images/USPCIMG/http://localhost:8086/Home/images/USPCIMG/Motor-Pump Set",
             "title": "Motor Pump Set",
             "bgColor": "white",
             "status": true
*/


    private ArrayList<SettingModelResponse> mSettingModelResponseList;
    private ArrayList<ProductStatusResponse> mProductStatusResponseList;
    // private List<ProductStatusResponse> mProductStatusResponse;

    private SettingModelResponse mSettingModelResponse;
    private ProductStatusResponse mProductStatusResponse;

    /////////////////////////////device parameter
    private static final String DEVICE_PARA_ADDRESS = "Address";
    private static final String DEVICE_PARA_DIVISIBLE = "Divisible";
    private static final String DEVICE_PARA_DEVICENO = "MDeviceNo";
    private static final String DEVICE_PARA_MPID = "MPId";
    private static final String DEVICE_PARA_MPINDEX = "MPIndex";
    private static final String DEVICE_PARA_MPNAME = "MPName";
    private static final String DEVICE_PARA_STATUS = "Status";
    private static final String DEVICE_PARA_UNIT = "Unit";
    private static final String DEVICE_PARA_PMIN = "PMin";
    private static final String DEVICE_PARA_PMAX = "PMax";
    private static final String DEVICE_PARA_MODADDRESS = "MODAddress";
    private static final String DEVICE_PARA_DEVICE_TYPE = "DeviceType";///offset
    private static final String DEVICE_PARA_DEVICE_TEST1 = "Offset";
    private static final String DEVICE_PARA_DEVICE_TEST2 = "ParaTest2";
    private static final String DEVICE_PARA_DEVICE_TEST3 = "ParaTest3";
    private static final String DEVICE_PARA_DEVICE_TEST4 = "ParaTest4";

    private static final String DEVICE_PARAMETER_TABLE_NAME = "deviceparameterlist";////////////////table name

    private static final String CREATE_TABLE_DEVICE_PARAMETER = "CREATE TABLE "
            + DEVICE_PARAMETER_TABLE_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_PARA_ADDRESS + " TEXT NOT NULL, " +
            DEVICE_PARA_DIVISIBLE + " TEXT NOT NULL, " +
            DEVICE_PARA_DEVICENO + " TEXT NOT NULL, " +
            DEVICE_PARA_MPID + " TEXT NOT NULL, " +
            DEVICE_PARA_MPINDEX + " VARCHAR, " +
            DEVICE_PARA_MPNAME + " VARCHAR, " +
            DEVICE_PARA_STATUS + " VARCHAR, " +
            DEVICE_PARA_UNIT + " VARCHAR, " +
            DEVICE_PARA_PMIN + " VARCHAR, " +
            DEVICE_PARA_PMAX + " VARCHAR, " +
            DEVICE_PARA_MODADDRESS + " VARCHAR, " +
            DEVICE_PARA_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_PARA_DEVICE_TEST1 + " VARCHAR, " +
            DEVICE_PARA_DEVICE_TEST2 + " VARCHAR, " +
            DEVICE_PARA_DEVICE_TEST3 + " VARCHAR, " +
            DEVICE_PARA_DEVICE_TEST4 + " VARCHAR " +
            "); ";


    /////////////////////////////device Status indicator
    private static final String DEVICE_STATUS_MODE = "SMode";
    private static final String DEVICE_STATUS_NAME = "SName";
    private static final String DEVICE_STATUS_COLOR = "SColor";
    private static final String DEVICE_STATUS_DEVICE_TYPE = "DeviceType";
    private static final String DEVICE_STATUS_TEST1 = "TestOne";
    private static final String DEVICE_STATUS_TEST2 = "TestTwo";
    private static final String DEVICE_STATUS_TEST3 = "TestThree";
    private static final String DEVICE_STATUS_TABLE_NAME = "devicestatustable";////////////////table name

    private static final String CREATE_TABLE_DEVICE_STATUS = "CREATE TABLE "
            + DEVICE_STATUS_TABLE_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_STATUS_MODE + " TEXT NOT NULL, " +
            DEVICE_STATUS_NAME + " TEXT NOT NULL, " +
            DEVICE_STATUS_COLOR + " TEXT NOT NULL, " +
            DEVICE_STATUS_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_STATUS_TEST1 + " VARCHAR, " +
            DEVICE_STATUS_TEST2 + " VARCHAR, " +
            DEVICE_STATUS_TEST3 + " VARCHAR " +
            "); ";

    ///////////////////////////////////Grid Tie//////////////

    private static final String DEVICE_KLPGRID_TotalFault = "TotalFault";
    private static final String DEVICE_KLPGRID_Fault = "Fault";
    private static final String DEVICE_KLPGRID_TOTGRHName = "TOTGRHName";
    private static final String DEVICE_KLPGRID_TOTGRHValue = "TOTGRHValue";
    private static final String DEVICE_KLPGRID_TOTGRHUnit = "TOTGRHUnit";
    private static final String DEVICE_KLPGRID_TDGRHName = "TDGRHName";
    private static final String DEVICE_KLPGRID_TDGRHValue = "TDGRHValue";
    private static final String DEVICE_KLPGRID_TDGRHUnit = "TDGRHUnit";
    private static final String DEVICE_KLPGRID_TOTGEnergyName = "TOTGEnergyName";

    private static final String DEVICE_KLPGRID_TOTMGEnergyName = "TOTMGEnergyName";
    private static final String DEVICE_KLPGRID_TOTMGEnergyValue = "TOTMGEnergyValue";
    private static final String DEVICE_KLPGRID_TOTMGEnergyUnit = "TOTMGEnergyUnit";

    private static final String DEVICE_KLPGRID_TOTGEnergyValue = "TOTGEnergyValue";
    private static final String DEVICE_KLPGRID_TOTGEnergyUnit = "TOTGEnergyUnit";
    private static final String DEVICE_KLPGRID_TDGEnergyName = "TDGEnergyName";
    private static final String DEVICE_KLPGRID_TDGEnergyValue = "TDGEnergyValue";
    private static final String DEVICE_KLPGRID_TDGEnergyUnit = "TDGEnergyUnit";
    private static final String DEVICE_KLPGRID_TEST1 = "klpgridtest1";
    private static final String DEVICE_KLPGRID_TEST2 = "klpgridtest2";
    private static final String DEVICE_KLPGRID_TEST3 = "klpgridtest3";
    private static final String DEVICE_KLPGRID_DEVICE_TYPE = "DeviceType";


    private static final String DEVICE_KLPGRID_TABLENAME = "deviceklpgridtableble";////////////////table name

    private static final String CREATE_TABLE_KLPGRID = "CREATE TABLE "
            + DEVICE_KLPGRID_TABLENAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_KLPGRID_TotalFault + " VARCHAR, " +
            DEVICE_KLPGRID_Fault + " VARCHAR, " +
            DEVICE_KLPGRID_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_KLPGRID_TOTGRHName + " VARCHAR, " +
            DEVICE_KLPGRID_TOTGRHValue + " VARCHAR, " +
            DEVICE_KLPGRID_TOTGRHUnit + " VARCHAR, " +
            DEVICE_KLPGRID_TDGRHName + " VARCHAR, " +
            DEVICE_KLPGRID_TDGRHValue + " VARCHAR, " +
            DEVICE_KLPGRID_TDGRHUnit + " VARCHAR, " +
            DEVICE_KLPGRID_TOTGEnergyName + " VARCHAR, " +

            DEVICE_KLPGRID_TOTMGEnergyName + " VARCHAR, " +
            DEVICE_KLPGRID_TOTMGEnergyValue + " VARCHAR, " +
            DEVICE_KLPGRID_TOTMGEnergyUnit + " VARCHAR, " +

            DEVICE_KLPGRID_TOTGEnergyValue + " VARCHAR, " +
            DEVICE_KLPGRID_TOTGEnergyUnit + " VARCHAR, " +
            DEVICE_KLPGRID_TDGEnergyName + " VARCHAR, " +
            DEVICE_KLPGRID_TDGEnergyValue + " VARCHAR, " +
            DEVICE_KLPGRID_TDGEnergyUnit + " VARCHAR, " +
            DEVICE_KLPGRID_TEST1 + " VARCHAR, " +
            DEVICE_KLPGRID_TEST2 + " VARCHAR, " +
            DEVICE_KLPGRID_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceKLPGRIDData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, KLPGridModelResponse mKLPGridModelResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_KLPGRID_TABLENAME + " where " + DEVICE_KLPGRID_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_KLPGRID_TotalFault, mKLPGridModelResponse.getTotalFault());
        values.put(DEVICE_KLPGRID_Fault, mKLPGridModelResponse.getTotalFault());
        values.put(DEVICE_KLPGRID_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_KLPGRID_TOTGRHName, mKLPGridModelResponse.getTOTGRHName());
        values.put(DEVICE_KLPGRID_TOTGRHValue, mKLPGridModelResponse.getTOTGRHValue());
        values.put(DEVICE_KLPGRID_TOTGRHUnit, mKLPGridModelResponse.getTOTGRHUnit());

        values.put(DEVICE_KLPGRID_TDGRHName, mKLPGridModelResponse.getTDGRHName());
        values.put(DEVICE_KLPGRID_TDGRHValue, mKLPGridModelResponse.getTDGRHValue());
        values.put(DEVICE_KLPGRID_TDGRHUnit, mKLPGridModelResponse.getTDGRHUnit());

        values.put(DEVICE_KLPGRID_TOTGEnergyName, mKLPGridModelResponse.getTOTGEnergyName());
        values.put(DEVICE_KLPGRID_TOTGEnergyValue, mKLPGridModelResponse.getTOTGEnergyValue());
        values.put(DEVICE_KLPGRID_TOTGEnergyUnit, mKLPGridModelResponse.getTOTGEnergyUnit());

        values.put(DEVICE_KLPGRID_TOTMGEnergyName, mKLPGridModelResponse.getTOTMGEnergyName());
        values.put(DEVICE_KLPGRID_TOTMGEnergyValue, mKLPGridModelResponse.getTOTMGEnergyValue());
        values.put(DEVICE_KLPGRID_TOTMGEnergyUnit, mKLPGridModelResponse.getTOTMGEnergyUnit());

        values.put(DEVICE_KLPGRID_TDGEnergyName, mKLPGridModelResponse.getTDGEnergyName());
        values.put(DEVICE_KLPGRID_TDGEnergyValue, mKLPGridModelResponse.getTDGEnergyValue());
        values.put(DEVICE_KLPGRID_TDGEnergyUnit, mKLPGridModelResponse.getTDGEnergyUnit());

        values.put(DEVICE_KLPGRID_TEST1, "one");
        values.put(DEVICE_KLPGRID_TEST2, "Two");
        values.put(DEVICE_KLPGRID_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_KLPGRID_TABLENAME, null, values);
        return insert;
    }


    public KLPGridModelResponse getDeviceKLPGRIDDATA(String DeviceType) {
        mKLPGridModelResponse = new KLPGridModelResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TOTGRHName, TOTGRHValue, TOTGRHUnit, TDGRHName,
                TDGRHValue, TDGRHUnit, TOTGEnergyName, TOTGEnergyValue, TOTGEnergyUnit,
                TDGEnergyName, TDGEnergyValue, TDGEnergyUnit, TOTMGEnergyName, TOTMGEnergyValue, TOTMGEnergyUnit, TOTMRHName, TOTMRHValue, TOTMRHUnit,
                TDMRHName, TDMRHValue, TDMRHUnit, TOTMEnergyName, TOTMEnergyValue, TOTMEnergyUnit, TDMEnergyName,
                TDMEnergyValue, TDMEnergyUnit, TOTMFlowName, TOTMFlowValue, TOTMFlowUnit,
                TDMFlowName, TDMFlowValue, TDMFlowUnit, TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_KLPGRID_TABLENAME + " where " + DEVICE_KLPGRID_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TotalFault));

                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_Fault));
                        TOTGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTGRHName));
                        TOTGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTGRHValue));
                        TOTGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTGRHUnit));
                        // mOldKLPTotEnergyResponse.setTotalFault(Integer.parseInt(TotalFault));

                        TDGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TDGRHName));
                        TDGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TDGRHValue));
                        TDGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TDGRHUnit));

                        TOTGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTGEnergyName));
                        TOTGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTGEnergyValue));
                        TOTGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTGEnergyUnit));

                        TOTMGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTMGEnergyName));
                        TOTMGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTMGEnergyValue));
                        TOTMGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TOTMGEnergyUnit));

                      //  values.put(DEVICE_KLPGRID_TOTMGEnergyName, mKLPGridModelResponse.getTOTMGEnergyName());
                      //  values.put(DEVICE_KLPGRID_TOTMGEnergyValue, mKLPGridModelResponse.getTOTMGEnergyValue());
                      //  values.put(DEVICE_KLPGRID_TOTMGEnergyUnit, mKLPGridModelResponse.getTOTMGEnergyUnit());

                        TDGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TDGEnergyName));
                        TDGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TDGEnergyValue));
                        TDGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_KLPGRID_TDGEnergyUnit));

                        //mOldKLPResponse.setTOTMFlow(TDMFlowUnit);

                        mKLPGridModelResponse.setTDGEnergyName(TDGEnergyName);
                        mKLPGridModelResponse.setTDGEnergyValue(TDGEnergyValue);
                        mKLPGridModelResponse.setTDGEnergyUnit(TDGEnergyUnit);

                        mKLPGridModelResponse.setTOTGEnergyName(TOTGEnergyName);
                        mKLPGridModelResponse.setTOTGEnergyValue(TOTGEnergyValue);
                        mKLPGridModelResponse.setTOTGEnergyUnit(TOTGEnergyUnit);

                        mKLPGridModelResponse.setTOTMGEnergyName(TOTMGEnergyName);
                        mKLPGridModelResponse.setTOTMGEnergyValue(TOTMGEnergyValue);
                        mKLPGridModelResponse.setTOTMGEnergyUnit(TOTMGEnergyUnit);

                        mKLPGridModelResponse.setTDGRHName(TDGRHName);
                        mKLPGridModelResponse.setTDGRHValue(TDGRHValue);
                        mKLPGridModelResponse.setTDGRHUnit(TDGRHUnit);

                        mKLPGridModelResponse.setTOTGRHName(TOTGRHName);
                        mKLPGridModelResponse.setTOTGRHValue(TOTGRHValue);
                        mKLPGridModelResponse.setTOTGRHUnit(TOTGRHUnit);

                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mKLPGridModelResponse;
    }







    /////////////////////////////device Status old klp indicator

    private static final String DEVICE_TotalFaultOldKLP = "TotalFault";
    private static final String DEVICE_FaultOldKLP = "Fault";
    private static final String DEVICE_TOTGRHNameOldKLP = "TOTGRHName";
    private static final String DEVICE_TOTGRHValueOldKLP = "TOTGRHValue";
    private static final String DEVICE_TOTGRHUnitOldKLP = "TOTGRHUnit";
    private static final String DEVICE_TDGRHNameOldKLP = "TDGRHName";
    private static final String DEVICE_TDGRHValueOldKLP = "TDGRHValue";
    private static final String DEVICE_TDGRHUnitOldKLP = "TDGRHUnit";
    private static final String DEVICE_TOTGEnergyNameOldKLP = "TOTGEnergyName";
    private static final String DEVICE_TOTGEnergyValueOldKLP = "TOTGEnergyValue";
    private static final String DEVICE_TOTGEnergyUnitOldKLP = "TOTGEnergyUnit";
    private static final String DEVICE_TDGEnergyNameOldKLP = "TDGEnergyName";
    private static final String DEVICE_TDGEnergyValueOldKLP = "TDGEnergyValue";
    private static final String DEVICE_TDGEnergyUnitOldKLP = "TDGEnergyUnit";
    private static final String DEVICE_TOTMRHNameOldKLP = "TOTMRHName";
    private static final String DEVICE_TOTMRHValueOldKLP = "TOTMRHValue";
    private static final String DEVICE_TOTMRHUnitOldKLP = "TOTMRHUnit";
    private static final String DEVICE_TDMRHNameOldKLP = "TDMRHName";
    private static final String DEVICE_TDMRHValueOldKLP = "TDMRHValue";
    private static final String DEVICE_TDMRHUnitOldKLP = "TDMRHUnit";
    private static final String DEVICE_TOTMFlowNameOldKLP = "TOTMFlowName";
    private static final String DEVICE_TOTMFlowValueOldKLP = "TOTMFlowValue";
    private static final String DEVICE_TOTMFlowUnitOldKLP = "TOTMFlowUnit";
    private static final String DEVICE_TDMFlowNameOldKLP = "TDMFlowName";
    private static final String DEVICE_TDMFlowValueOldKLP = "TDMFlowValue";
    private static final String DEVICE_TDMFlowUnitOldKLP = "TDMFlowUnit";
    private static final String DEVICE_OldKLP_DEVICE_TYPE = "DeviceType";

    private static final String DEVICE_OldKLP_TEST1 = "TestOne";
    private static final String DEVICE_OldKLP_TEST2 = "TestTwo";
    private static final String DEVICE_OldKLP_TEST3 = "TestThree";


    private static final String DEVICE_OldKLP_NAME = "deviceoldklptableble";////////////////table name

    private static final String CREATE_TABLE_OldKLP = "CREATE TABLE "
            + DEVICE_OldKLP_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultOldKLP + " VARCHAR, " +
            DEVICE_FaultOldKLP + " VARCHAR, " +
            DEVICE_OldKLP_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TOTGRHNameOldKLP + " VARCHAR, " +
            DEVICE_TOTGRHValueOldKLP + " VARCHAR, " +
            DEVICE_TOTGRHUnitOldKLP + " VARCHAR, " +
            DEVICE_TDGRHNameOldKLP + " VARCHAR, " +
            DEVICE_TDGRHValueOldKLP + " VARCHAR, " +
            DEVICE_TDGRHUnitOldKLP + " VARCHAR, " +
            DEVICE_TOTGEnergyNameOldKLP + " VARCHAR, " +
            DEVICE_TOTGEnergyValueOldKLP + " VARCHAR, " +
            DEVICE_TOTGEnergyUnitOldKLP + " VARCHAR, " +
            DEVICE_TDGEnergyNameOldKLP + " VARCHAR, " +
            DEVICE_TDGEnergyValueOldKLP + " VARCHAR, " +
            DEVICE_TDGEnergyUnitOldKLP + " VARCHAR, " +
            DEVICE_TOTMRHNameOldKLP + " VARCHAR, " +
            DEVICE_TOTMRHValueOldKLP + " VARCHAR, " +
            DEVICE_TOTMRHUnitOldKLP + " VARCHAR, " +
            DEVICE_TDMRHNameOldKLP + " VARCHAR, " +
            DEVICE_TDMRHValueOldKLP + " VARCHAR, " +
            DEVICE_TDMRHUnitOldKLP + " VARCHAR, " +
            DEVICE_TOTMFlowNameOldKLP + " VARCHAR, " +
            DEVICE_TOTMFlowValueOldKLP + " VARCHAR, " +
            DEVICE_TOTMFlowUnitOldKLP + " VARCHAR, " +
            DEVICE_TDMFlowNameOldKLP + " VARCHAR, " +
            DEVICE_TDMFlowValueOldKLP + " VARCHAR, " +
            DEVICE_TDMFlowUnitOldKLP + " VARCHAR, " +
            DEVICE_OldKLP_TEST1 + " VARCHAR, " +
            DEVICE_OldKLP_TEST2 + " VARCHAR, " +
            DEVICE_OldKLP_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceOldKLPData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, OldKLPResponse mOldKLPResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_OldKLP_NAME + " where " + DEVICE_OldKLP_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultOldKLP, mOldKLPResponse.getTotalFault());
        values.put(DEVICE_FaultOldKLP, mOldKLPResponse.getTotalFault());
        values.put(DEVICE_OldKLP_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TOTGRHNameOldKLP, mOldKLPResponse.getTOTGRHName());
        values.put(DEVICE_TOTGRHValueOldKLP, mOldKLPResponse.getTOTGRHValue());
        values.put(DEVICE_TOTGRHUnitOldKLP, mOldKLPResponse.getTOTGRHUnit());

        values.put(DEVICE_TDGRHNameOldKLP, mOldKLPResponse.getTDGRHName());
        values.put(DEVICE_TDGRHValueOldKLP, mOldKLPResponse.getTDGRHValue());
        values.put(DEVICE_TDGRHUnitOldKLP, mOldKLPResponse.getTDGRHUnit());

        values.put(DEVICE_TOTGEnergyNameOldKLP, mOldKLPResponse.getTOTGEnergyName());
        values.put(DEVICE_TOTGEnergyValueOldKLP, mOldKLPResponse.getTOTGEnergyValue());
        values.put(DEVICE_TOTGEnergyUnitOldKLP, mOldKLPResponse.getTOTGEnergyUnit());

        values.put(DEVICE_TDGEnergyNameOldKLP, mOldKLPResponse.getTDGEnergyName());
        values.put(DEVICE_TDGEnergyValueOldKLP, mOldKLPResponse.getTDGEnergyValue());
        values.put(DEVICE_TDGEnergyUnitOldKLP, mOldKLPResponse.getTDGEnergyUnit());

        values.put(DEVICE_TOTMRHNameOldKLP, mOldKLPResponse.getTOTMRHName());
        values.put(DEVICE_TOTMRHValueOldKLP, mOldKLPResponse.getTOTMRHValue());
        values.put(DEVICE_TOTMRHUnitOldKLP, mOldKLPResponse.getTOTMRHUnit());

        values.put(DEVICE_TDMRHNameOldKLP, mOldKLPResponse.getTDMRHName());
        values.put(DEVICE_TDMRHValueOldKLP, mOldKLPResponse.getTDMRHValue());
        values.put(DEVICE_TDMRHUnitOldKLP, mOldKLPResponse.getTDMRHUnit());


        values.put(DEVICE_TOTMFlowNameOldKLP, mOldKLPResponse.getTOTMFlowName());
        values.put(DEVICE_TOTMFlowValueOldKLP, mOldKLPResponse.getTOTMFlowValue());

        values.put(DEVICE_TDMFlowNameOldKLP, mOldKLPResponse.getTDMFlowName());
        values.put(DEVICE_TDMFlowValueOldKLP, mOldKLPResponse.getTDMFlowValue());
        values.put(DEVICE_TDMFlowUnitOldKLP, mOldKLPResponse.getTDMFlowUnit());

        values.put(DEVICE_OldKLP_TEST1, "one");
        values.put(DEVICE_OldKLP_TEST2, "Two");
        values.put(DEVICE_OldKLP_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_OldKLP_NAME, null, values);
        return insert;
    }


    public OldKLPResponse getDeviceOLDKLPDATA(String DeviceType) {
        mOldKLPResponse = new OldKLPResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TOTGRHName, TOTGRHValue, TOTGRHUnit, TDGRHName,
                TDGRHValue, TDGRHUnit, TOTGEnergyName, TOTGEnergyValue, TOTGEnergyUnit,
                TDGEnergyName, TDGEnergyValue, TDGEnergyUnit, TOTMRHName, TOTMRHValue, TOTMRHUnit,
                TDMRHName, TDMRHValue, TDMRHUnit, TOTMEnergyName, TOTMEnergyValue, TOTMEnergyUnit, TDMEnergyName,
                TDMEnergyValue, TDMEnergyUnit, TOTMFlowName, TOTMFlowValue, TOTMFlowUnit,
                TDMFlowName, TDMFlowValue, TDMFlowUnit, TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_OldKLP_NAME + " where " + DEVICE_OldKLP_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultOldKLP));
                        ;
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultOldKLP));
                        TOTGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHNameOldKLP));
                        TOTGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHValueOldKLP));
                        TOTGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHUnitOldKLP));
                        // mOldKLPTotEnergyResponse.setTotalFault(Integer.parseInt(TotalFault));

                        TDGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHNameOldKLP));
                        TDGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHValueOldKLP));
                        TDGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHUnitOldKLP));

                        TOTGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyNameOldKLP));
                        TOTGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyValueOldKLP));
                        TOTGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyUnitOldKLP));

                        TDGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyNameOldKLP));
                        TDGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyValueOldKLP));
                        TDGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyUnitOldKLP));

                        TOTMRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHNameOldKLP));
                        TOTMRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHValueOldKLP));
                        TOTMRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHUnitOldKLP));

                        TDMRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHNameOldKLP));
                        TDMRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHValueOldKLP));
                        TDMRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHUnitOldKLP));

                        TOTMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowNameOldKLP));
                        TOTMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowValueOldKLP));
                        //TOTMFlowUnit= cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowUnitOldKLP));

                        TDMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowNameOldKLP));
                        ;//DEVICE_TDMFlowNameKLP
                        TDMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowValueOldKLP));
                        ;
                        TDMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowUnitOldKLP));
                        ;

                        mOldKLPResponse.setTDMFlowName(TDMFlowName);
                        mOldKLPResponse.setTDMFlowValue(TDMFlowValue);
                        mOldKLPResponse.setTDMFlowUnit(TDMFlowUnit);

                        mOldKLPResponse.setTOTMFlowName(TOTMFlowName);
                        mOldKLPResponse.setTOTMFlowValue(TOTMFlowValue);
                        //mOldKLPResponse.setTOTMFlow(TDMFlowUnit);

                        mOldKLPResponse.setTDMRHName(TDMRHName);
                        mOldKLPResponse.setTDMRHValue(TDMRHValue);
                        mOldKLPResponse.setTDMRHUnit(TDMRHUnit);


                        mOldKLPResponse.setTDMRHName(TDMRHName);
                        mOldKLPResponse.setTDMRHValue(TDMRHValue);
                        mOldKLPResponse.setTDMRHUnit(TDMRHUnit);

                        mOldKLPResponse.setTOTMRHName(TOTMRHName);
                        mOldKLPResponse.setTOTMRHValue(TOTMRHValue);
                        mOldKLPResponse.setTOTMRHUnit(TOTMRHUnit);

                        mOldKLPResponse.setTDGEnergyName(TDGEnergyName);
                        mOldKLPResponse.setTDGEnergyValue(TDGEnergyValue);
                        mOldKLPResponse.setTDGEnergyUnit(TDGEnergyUnit);

                        mOldKLPResponse.setTOTGEnergyName(TOTGEnergyName);
                        mOldKLPResponse.setTOTGEnergyValue(TOTGEnergyValue);
                        mOldKLPResponse.setTOTGEnergyUnit(TOTGEnergyUnit);

                        mOldKLPResponse.setTDGRHName(TDGRHName);
                        mOldKLPResponse.setTDGRHValue(TDGRHValue);
                        mOldKLPResponse.setTDGRHUnit(TDGRHUnit);

                        mOldKLPResponse.setTOTGRHName(TOTGRHName);
                        mOldKLPResponse.setTOTGRHValue(TOTGRHValue);
                        mOldKLPResponse.setTOTGRHUnit(TOTGRHUnit);

                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mOldKLPResponse;
    }


    ////////////////////////////SAJ LOCL DB CODE

    /////////////////////////////device Status klp indicator
    private static final String DEVICE_TotalFaultSAJ = "TotalFault";
    private static final String DEVICE_FaultSAJ = "Fault";
    private static final String DEVICE_TOTGRHNameSAJ = "TOTGRHName";
    private static final String DEVICE_TOTGRHValueSAJ = "TOTGRHValue";
    private static final String DEVICE_TOTGRHUnitSAJ = "TOTGRHUnit";
    private static final String DEVICE_TDGRHNameSAJ = "TDGRHName";
    private static final String DEVICE_TDGRHValueSAJ = "TDGRHValue";
    private static final String DEVICE_TDGRHUnitSAJ = "TDGRHUnit";
    private static final String DEVICE_TOTGEnergyNameSAJ = "TOTGEnergyName";
    private static final String DEVICE_TOTGEnergyValueSAJ = "TOTGEnergyValue";
    private static final String DEVICE_TOTGEnergyUnitSAJ = "TOTGEnergyUnit";
    private static final String DEVICE_TDGEnergyNameSAJ = "TDGEnergyName";
    private static final String DEVICE_TDGEnergyValueSAJ = "TDGEnergyValue";
    private static final String DEVICE_TDGEnergyUnitSAJ = "TDGEnergyUnit";

    private static final String DEVICE_SAJ_DEVICE_TYPE = "DeviceType";

    private static final String DEVICE_SAJ_TEST1 = "TestOne";
    private static final String DEVICE_SAJ_TEST2 = "TestTwo";
    private static final String DEVICE_SAJ_TEST3 = "TestThree";

    private static final String DEVICE_SAJ_NAME = "devicesajble";////////////////table name


    private static final String CREATE_TABLE_SAJ = "CREATE TABLE "
            + DEVICE_SAJ_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultSAJ + " VARCHAR, " +
            DEVICE_FaultSAJ + " VARCHAR, " +
            DEVICE_SAJ_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TOTGRHNameSAJ + " VARCHAR, " +
            DEVICE_TOTGRHValueSAJ + " VARCHAR, " +
            DEVICE_TOTGRHUnitSAJ + " VARCHAR, " +
            DEVICE_TDGRHNameSAJ + " VARCHAR, " +
            DEVICE_TDGRHValueSAJ + " VARCHAR, " +
            DEVICE_TDGRHUnitSAJ + " VARCHAR, " +
            DEVICE_TOTGEnergyNameSAJ + " VARCHAR, " +
            DEVICE_TOTGEnergyValueSAJ + " VARCHAR, " +
            DEVICE_TOTGEnergyUnitSAJ + " VARCHAR, " +
            DEVICE_TDGEnergyNameSAJ + " VARCHAR, " +
            DEVICE_TDGEnergyValueSAJ + " VARCHAR, " +
            DEVICE_TDGEnergyUnitSAJ + " VARCHAR, " +
            DEVICE_SAJ_TEST1 + " VARCHAR, " +
            DEVICE_SAJ_TEST2 + " VARCHAR, " +
            DEVICE_SAJ_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceSAJData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, SAJResponse mSAJResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_SAJ_NAME + " where " + DEVICE_SAJ_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultSAJ, mSAJResponse.getTotalFault());
        values.put(DEVICE_FaultSAJ, mSAJResponse.getTotalFault());
        values.put(DEVICE_SAJ_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TOTGRHNameSAJ, mSAJResponse.getTOTGRHName());
        values.put(DEVICE_TOTGRHValueSAJ, mSAJResponse.getTOTGRHValue());
        values.put(DEVICE_TOTGRHUnitSAJ, mSAJResponse.getTOTGRHUnit());

        values.put(DEVICE_TDGRHNameSAJ, mSAJResponse.getTDGRHName());
        values.put(DEVICE_TDGRHValueSAJ, mSAJResponse.getTDGRHValue());
        values.put(DEVICE_TDGRHUnitSAJ, mSAJResponse.getTDGRHrUnit());

        values.put(DEVICE_TOTGEnergyNameSAJ, mSAJResponse.getTOTGEnergyName());
        values.put(DEVICE_TOTGEnergyValueSAJ, mSAJResponse.getTOTGEnergyValue());
        values.put(DEVICE_TOTGEnergyUnitSAJ, mSAJResponse.getTOTGEnergyUnit());

        values.put(DEVICE_TDGEnergyNameSAJ, mSAJResponse.getTDGEnergyName());
        values.put(DEVICE_TDGEnergyValueSAJ, mSAJResponse.getTDGEnergyValue());
        values.put(DEVICE_TDGEnergyUnitSAJ, mSAJResponse.getTDGEnergyUnit());


        values.put(DEVICE_SAJ_TEST1, "one");
        values.put(DEVICE_SAJ_TEST2, "Two");
        values.put(DEVICE_SAJ_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_SAJ_NAME, null, values);
        return insert;
    }


    public SAJResponse getDeviceSAJDATA(String DeviceType) {
        mSAJResponse = new SAJResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TOTGRHName, TOTGRHValue, TOTGRHUnit, TDGRHName,
                TDGRHValue, TDGRHUnit, TOTGEnergyName, TOTGEnergyValue, TOTGEnergyUnit,
                TDGEnergyName, TDGEnergyValue, TDGEnergyUnit;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_SAJ_NAME + " where " + DEVICE_SAJ_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultSAJ));
                        ;
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultSAJ));
                        TOTGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHNameSAJ));
                        TOTGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHValueSAJ));
                        TOTGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHUnitSAJ));
                        // mKLPTotEnergyResponse.setTotalFault(Integer.parseInt(TotalFault));

                        TDGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHNameSAJ));
                        TDGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHValueSAJ));
                        TDGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHUnitSAJ));

                        TOTGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyNameSAJ));
                        TOTGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyValueSAJ));
                        TOTGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyUnitSAJ));

                        TDGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyNameSAJ));
                        TDGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyValueSAJ));
                        TDGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyUnitSAJ));

                        mSAJResponse.setTDGEnergyName(TDGEnergyName);
                        mSAJResponse.setTDGEnergyValue(TDGEnergyValue);
                        mSAJResponse.setTDGEnergyUnit(TDGEnergyUnit);

                        mSAJResponse.setTOTGEnergyName(TOTGEnergyName);
                        mSAJResponse.setTOTGEnergyValue(TOTGEnergyValue);
                        mSAJResponse.setTOTGEnergyUnit(TOTGEnergyUnit);

                        mSAJResponse.setTDGRHName(TDGRHName);
                        mSAJResponse.setTDGRHValue(TDGRHValue);
                        mSAJResponse.setTDGRHrUnit(TDGRHUnit);

                        mSAJResponse.setTOTGRHName(TOTGRHName);
                        mSAJResponse.setTOTGRHValue(TOTGRHValue);
                        mSAJResponse.setTOTGRHUnit(TOTGRHUnit);

                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mSAJResponse;
    }


    /////////////////////////////device Status klp indicator
    private static final String DEVICE_TotalFaultKLP = "TotalFault";
    private static final String DEVICE_FaultKLP = "Fault";
    private static final String DEVICE_TOTGRHNameKLP = "TOTGRHName";
    private static final String DEVICE_TOTGRHValueKLP = "TOTGRHValue";
    private static final String DEVICE_TOTGRHUnitKLP = "TOTGRHUnit";
    private static final String DEVICE_TDGRHNameKLP = "TDGRHName";
    private static final String DEVICE_TDGRHValueKLP = "TDGRHValue";
    private static final String DEVICE_TDGRHUnitKLP = "TDGRHUnit";
    private static final String DEVICE_TOTGEnergyNameKLP = "TOTGEnergyName";
    private static final String DEVICE_TOTGEnergyValueKLP = "TOTGEnergyValue";
    private static final String DEVICE_TOTGEnergyUnitKLP = "TOTGEnergyUnit";
    private static final String DEVICE_TDGEnergyNameKLP = "TDGEnergyName";
    private static final String DEVICE_TDGEnergyValueKLP = "TDGEnergyValue";
    private static final String DEVICE_TDGEnergyUnitKLP = "TDGEnergyUnit";
    private static final String DEVICE_TOTMRHNameKLP = "TOTMRHName";
    private static final String DEVICE_TOTMRHValueKLP = "TOTMRHValue";
    private static final String DEVICE_TOTMRHUnitKLP = "TOTMRHUnit";
    private static final String DEVICE_TDMRHNameKLP = "TDMRHName";
    private static final String DEVICE_TDMRHValueKLP = "TDMRHValue";
    private static final String DEVICE_TDMRHUnitKLP = "TDMRHUnit";
    private static final String DEVICE_TOTMEnergyNameKLP = "TOTMEnergyName";
    private static final String DEVICE_TOTMEnergyValueKLP = "TOTMEnergyValue";
    private static final String DEVICE_TOTMEnergyUnitKLP = "TOTMEnergyUnit";
    private static final String DEVICE_TDMEnergyNameKLP = "TDMEnergyName";
    private static final String DEVICE_TDMEnergyValueKLP = "TDMEnergyValue";
    private static final String DEVICE_TDMEnergyUnitKLP = "TDMEnergyUnit";
    private static final String DEVICE_TOTMFlowNameKLP = "TOTMFlowName";
    private static final String DEVICE_TOTMFlowValueKLP = "TOTMFlowValue";
    private static final String DEVICE_TOTMFlowUnitKLP = "TOTMFlowUnit";
    private static final String DEVICE_TDMFlowNameKLP = "TDMFlowName";
    private static final String DEVICE_TDMFlowValueKLP = "TDMFlowValue";
    private static final String DEVICE_TDMFlowUnitKLP = "TDMFlowUnit";
    private static final String DEVICE_KLP_DEVICE_TYPE = "DeviceType";


    private static final String DEVICE_KLP_TEST1 = "TestOne";
    private static final String DEVICE_KLP_TEST2 = "TestTwo";
    private static final String DEVICE_KLP_TEST3 = "TestThree";

    private static final String DEVICE_KLP_NAME = "deviceklpble";////////////////table name


    private static final String CREATE_TABLE_KLP = "CREATE TABLE "
            + DEVICE_KLP_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultKLP + " VARCHAR, " +
            DEVICE_FaultKLP + " VARCHAR, " +
            DEVICE_KLP_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TOTGRHNameKLP + " VARCHAR, " +
            DEVICE_TOTGRHValueKLP + " VARCHAR, " +
            DEVICE_TOTGRHUnitKLP + " VARCHAR, " +
            DEVICE_TDGRHNameKLP + " VARCHAR, " +
            DEVICE_TDGRHValueKLP + " VARCHAR, " +
            DEVICE_TDGRHUnitKLP + " VARCHAR, " +
            DEVICE_TOTGEnergyNameKLP + " VARCHAR, " +
            DEVICE_TOTGEnergyValueKLP + " VARCHAR, " +
            DEVICE_TOTGEnergyUnitKLP + " VARCHAR, " +
            DEVICE_TDGEnergyNameKLP + " VARCHAR, " +
            DEVICE_TDGEnergyValueKLP + " VARCHAR, " +
            DEVICE_TDGEnergyUnitKLP + " VARCHAR, " +
            DEVICE_TOTMRHNameKLP + " VARCHAR, " +
            DEVICE_TOTMRHValueKLP + " VARCHAR, " +
            DEVICE_TOTMRHUnitKLP + " VARCHAR, " +
            DEVICE_TDMRHNameKLP + " VARCHAR, " +
            DEVICE_TDMRHValueKLP + " VARCHAR, " +
            DEVICE_TDMRHUnitKLP + " VARCHAR, " +
            DEVICE_TOTMEnergyNameKLP + " VARCHAR, " +
            DEVICE_TOTMEnergyValueKLP + " VARCHAR, " +
            DEVICE_TOTMEnergyUnitKLP + " VARCHAR, " +
            DEVICE_TDMEnergyNameKLP + " VARCHAR, " +
            DEVICE_TDMEnergyValueKLP + " VARCHAR, " +
            DEVICE_TDMEnergyUnitKLP + " VARCHAR, " +
            DEVICE_TOTMFlowNameKLP + " VARCHAR, " +
            DEVICE_TOTMFlowValueKLP + " VARCHAR, " +
            DEVICE_TOTMFlowUnitKLP + " VARCHAR, " +
            DEVICE_TDMFlowNameKLP + " VARCHAR, " +
            DEVICE_TDMFlowValueKLP + " VARCHAR, " +
            DEVICE_TDMFlowUnitKLP + " VARCHAR, " +
            DEVICE_KLP_TEST1 + " VARCHAR, " +
            DEVICE_KLP_TEST2 + " VARCHAR, " +
            DEVICE_KLP_TEST3 + " VARCHAR " +
            "); ";

    ////////////////////////////////insert Device status data

    public long insertDeviceKLPData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, KLPHybrideResponse mKLPHybrideResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_KLP_NAME + " where " + DEVICE_KLP_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultKLP, mKLPTotEnergyResponse.getTotalFault());
        values.put(DEVICE_FaultKLP, mKLPTotEnergyResponse.getTotalFault());
        values.put(DEVICE_KLP_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TOTGRHNameKLP, mKLPTotEnergyResponse.getTOTGRHName());
        values.put(DEVICE_TOTGRHValueKLP, mKLPTotEnergyResponse.getTOTGRHValue());
        values.put(DEVICE_TOTGRHUnitKLP, mKLPTotEnergyResponse.getTOTGRHUnit());

        values.put(DEVICE_TDGRHNameKLP, mKLPTotEnergyResponse.getTDGRHName());
        values.put(DEVICE_TDGRHValueKLP, mKLPTotEnergyResponse.getTDGRHValue());
        values.put(DEVICE_TDGRHUnitKLP, mKLPTotEnergyResponse.getTDGRHUnit());

        values.put(DEVICE_TOTGEnergyNameKLP, mKLPTotEnergyResponse.getTOTGEnergyName());
        values.put(DEVICE_TOTGEnergyValueKLP, mKLPTotEnergyResponse.getTOTGEnergyValue());
        values.put(DEVICE_TOTGEnergyUnitKLP, mKLPTotEnergyResponse.getTOTGEnergyUnit());

        values.put(DEVICE_TDGEnergyNameKLP, mKLPTotEnergyResponse.getTDGEnergyName());
        values.put(DEVICE_TDGEnergyValueKLP, mKLPTotEnergyResponse.getTDGEnergyValue());
        values.put(DEVICE_TDGEnergyUnitKLP, mKLPTotEnergyResponse.getTDGEnergyUnit());

        values.put(DEVICE_TOTMRHNameKLP, mKLPTotEnergyResponse.getTOTMRHName());
        values.put(DEVICE_TOTMRHValueKLP, mKLPTotEnergyResponse.getTOTMRHValue());
        values.put(DEVICE_TOTMRHUnitKLP, mKLPTotEnergyResponse.getTOTMRHUnit());

        values.put(DEVICE_TDMRHNameKLP, mKLPTotEnergyResponse.getTDMRHName());
        values.put(DEVICE_TDMRHValueKLP, mKLPTotEnergyResponse.getTDMRHValue());
        values.put(DEVICE_TDMRHUnitKLP, mKLPTotEnergyResponse.getTDMRHUnit());

        values.put(DEVICE_TOTMEnergyNameKLP, mKLPTotEnergyResponse.getTOTMEnergyName());
        values.put(DEVICE_TOTMEnergyValueKLP, mKLPTotEnergyResponse.getTOTMEnergyValue());
        values.put(DEVICE_TOTMEnergyUnitKLP, mKLPTotEnergyResponse.getTOTMEnergyUnit());

        values.put(DEVICE_TDMEnergyNameKLP, mKLPTotEnergyResponse.getTDMEnergyName());
        values.put(DEVICE_TDMEnergyValueKLP, mKLPTotEnergyResponse.getTDMEnergyValue());
        values.put(DEVICE_TDMEnergyUnitKLP, mKLPTotEnergyResponse.getTDMEnergyUnit());

        values.put(DEVICE_TOTMFlowNameKLP, mKLPTotEnergyResponse.getTOTMFlowName());
        values.put(DEVICE_TOTMFlowValueKLP, mKLPTotEnergyResponse.getTOTMFlowValue());

        values.put(DEVICE_TDMFlowNameKLP, mKLPTotEnergyResponse.getTDMFlowName());
        values.put(DEVICE_TDMFlowValueKLP, mKLPTotEnergyResponse.getTDMFlowValue());
        values.put(DEVICE_TDMFlowUnitKLP, mKLPTotEnergyResponse.getTDMFlowUnit());

        values.put(DEVICE_KLP_TEST1, "one");
        values.put(DEVICE_KLP_TEST2, "Two");
        values.put(DEVICE_KLP_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_KLP_NAME, null, values);
        return insert;
    }


    public long insertDeviceKLPHYBRIDeData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, KLPHybrideResponse mKLPHybrideResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_KLP_NAME + " where " + DEVICE_KLP_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        try {
            values.put(DEVICE_TotalFaultKLP, mKLPHybrideResponse.getTotalFault());
            values.put(DEVICE_FaultKLP, mKLPHybrideResponse.getTotalFault());
            values.put(DEVICE_KLP_DEVICE_TYPE, vDeviceType);

            values.put(DEVICE_TOTGRHNameKLP, mKLPHybrideResponse.getTOTGRHName());
            values.put(DEVICE_TOTGRHValueKLP, mKLPHybrideResponse.getTOTGRHValue());
            values.put(DEVICE_TOTGRHUnitKLP, mKLPHybrideResponse.getTOTGRHUnit());

            values.put(DEVICE_TDGRHNameKLP, mKLPHybrideResponse.getTDGRHName());
            values.put(DEVICE_TDGRHValueKLP, mKLPHybrideResponse.getTDGRHValue());
            values.put(DEVICE_TDGRHUnitKLP, mKLPHybrideResponse.getTDGRHUnit());

            values.put(DEVICE_TOTGEnergyNameKLP, mKLPHybrideResponse.getTOTGEnergyName());
            values.put(DEVICE_TOTGEnergyValueKLP, mKLPHybrideResponse.getTOTGEnergyValue());
            values.put(DEVICE_TOTGEnergyUnitKLP, mKLPHybrideResponse.getTOTGEnergyUnit());

            values.put(DEVICE_TDGEnergyNameKLP, mKLPHybrideResponse.getTDGEnergyName());
            values.put(DEVICE_TDGEnergyValueKLP, mKLPHybrideResponse.getTDGEnergyValue());
            values.put(DEVICE_TDGEnergyUnitKLP, mKLPHybrideResponse.getTDGEnergyUnit());

            values.put(DEVICE_TOTMRHNameKLP, mKLPHybrideResponse.getTOTMRHName());
            values.put(DEVICE_TOTMRHValueKLP, mKLPHybrideResponse.getTOTMRHValue());
            values.put(DEVICE_TOTMRHUnitKLP, mKLPHybrideResponse.getTOTMRHUnit());

            values.put(DEVICE_TDMRHNameKLP, mKLPHybrideResponse.getTDMRHName());
            values.put(DEVICE_TDMRHValueKLP, mKLPHybrideResponse.getTDMRHValue());
            values.put(DEVICE_TDMRHUnitKLP, mKLPHybrideResponse.getTDMRHUnit());

            values.put(DEVICE_TOTMEnergyNameKLP, mKLPHybrideResponse.getTOTMEnergyName());
            values.put(DEVICE_TOTMEnergyValueKLP, mKLPHybrideResponse.getTOTMEnergyValue());
            values.put(DEVICE_TOTMEnergyUnitKLP, mKLPHybrideResponse.getTOTMEnergyUnit());

            values.put(DEVICE_TDMEnergyNameKLP, mKLPHybrideResponse.getTDMEnergyName());
            values.put(DEVICE_TDMEnergyValueKLP, mKLPHybrideResponse.getTDMEnergyValue());
            values.put(DEVICE_TDMEnergyUnitKLP, mKLPHybrideResponse.getTDMEnergyUnit());

            values.put(DEVICE_TOTMFlowNameKLP, mKLPHybrideResponse.getTOTMFlowName());
            values.put(DEVICE_TOTMFlowValueKLP, mKLPHybrideResponse.getTOTMFlowValue());

            values.put(DEVICE_TDMFlowNameKLP, mKLPHybrideResponse.getTDMFlowName());
            values.put(DEVICE_TDMFlowValueKLP, mKLPHybrideResponse.getTDMFlowValue());
            values.put(DEVICE_TDMFlowUnitKLP, mKLPHybrideResponse.getTDMFlowUnit());

            values.put(DEVICE_KLP_TEST1, "one");
            values.put(DEVICE_KLP_TEST2, "Two");
            values.put(DEVICE_KLP_TEST3, "Three");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //insert row in table
        long insert = db.insert(DEVICE_KLP_NAME, null, values);
        return insert;
    }


    public KLPTotEnergyResponse getDeviceKLPDATA(String DeviceType) {
        mKLPTotEnergyResponse = new KLPTotEnergyResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TOTGRHName, TOTGRHValue, TOTGRHUnit, TDGRHName,
                TDGRHValue, TDGRHUnit, TOTGEnergyName, TOTGEnergyValue, TOTGEnergyUnit,
                TDGEnergyName, TDGEnergyValue, TDGEnergyUnit, TOTMRHName, TOTMRHValue, TOTMRHUnit,
                TDMRHName, TDMRHValue, TDMRHUnit, TOTMEnergyName, TOTMEnergyValue, TOTMEnergyUnit, TDMEnergyName,
                TDMEnergyValue, TDMEnergyUnit, TOTMFlowName, TOTMFlowValue, TOTMFlowUnit,
                TDMFlowName, TDMFlowValue, TDMFlowUnit, TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_KLP_NAME + " where " + DEVICE_KLP_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultKLP));
                        ;
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultKLP));
                        TOTGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHNameKLP));
                        TOTGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHValueKLP));
                        TOTGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHUnitKLP));
                        // mKLPTotEnergyResponse.setTotalFault(Integer.parseInt(TotalFault));

                        TDGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHNameKLP));
                        TDGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHValueKLP));
                        TDGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHUnitKLP));

                        TOTGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyNameKLP));
                        TOTGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyValueKLP));
                        TOTGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyUnitKLP));

                        TDGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyNameKLP));
                        TDGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyValueKLP));
                        TDGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyUnitKLP));

                        TOTMRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHNameKLP));
                        TOTMRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHValueKLP));
                        TOTMRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHUnitKLP));

                        TDMRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHNameKLP));
                        TDMRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHValueKLP));
                        TDMRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHUnitKLP));

                        TOTMEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyNameKLP));
                        TOTMEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyValueKLP));
                        TOTMEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyUnitKLP));

                        TDMEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyNameKLP));
                        TDMEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyValueKLP));
                        TDMEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyUnitKLP));

                        TOTMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowNameKLP));
                        TOTMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowValueKLP));
                        // TOTMFlowUnit= cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowUnitKLP));

                        TDMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowNameKLP));
                        ;//DEVICE_TDMFlowNameKLP
                        TDMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowValueKLP));
                        ;
                        TDMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowUnitKLP));
                        ;

                        mKLPTotEnergyResponse.setTDMFlowName(TDMFlowName);
                        mKLPTotEnergyResponse.setTDMFlowValue(TDMFlowValue);
                        mKLPTotEnergyResponse.setTDMFlowUnit(TDMFlowUnit);

                        mKLPTotEnergyResponse.setTOTMFlowName(TOTMFlowName);
                        mKLPTotEnergyResponse.setTOTMFlowValue(TOTMFlowValue);
                        //mKLPTotEnergyResponse.setTOTMFlow(TDMFlowUnit);

                        mKLPTotEnergyResponse.setTDMRHName(TDMRHName);
                        mKLPTotEnergyResponse.setTDMRHValue(TDMRHValue);
                        mKLPTotEnergyResponse.setTDMRHUnit(TDMRHUnit);

                        mKLPTotEnergyResponse.setTOTMEnergyName(TOTMEnergyName);
                        mKLPTotEnergyResponse.setTOTMEnergyValue(TOTMEnergyValue);
                        mKLPTotEnergyResponse.setTOTMEnergyUnit(TOTMEnergyUnit);

                        mKLPTotEnergyResponse.setTDMEnergyName(TDMEnergyName);
                        mKLPTotEnergyResponse.setTDMEnergyValue(TDMEnergyValue);
                        mKLPTotEnergyResponse.setTDMEnergyUnit(TDMEnergyUnit);

                        mKLPTotEnergyResponse.setTDMRHName(TDMRHName);
                        mKLPTotEnergyResponse.setTDMRHValue(TDMRHValue);
                        mKLPTotEnergyResponse.setTDMRHUnit(TDMRHUnit);

                        mKLPTotEnergyResponse.setTOTMRHName(TOTMRHName);
                        mKLPTotEnergyResponse.setTOTMRHValue(TOTMRHValue);
                        mKLPTotEnergyResponse.setTOTMRHUnit(TOTMRHUnit);

                        mKLPTotEnergyResponse.setTDGEnergyName(TDGEnergyName);
                        mKLPTotEnergyResponse.setTDGEnergyValue(TDGEnergyValue);
                        mKLPTotEnergyResponse.setTDGEnergyUnit(TDGEnergyUnit);

                        mKLPTotEnergyResponse.setTOTGEnergyName(TOTGEnergyName);
                        mKLPTotEnergyResponse.setTOTGEnergyValue(TOTGEnergyValue);
                        mKLPTotEnergyResponse.setTOTGEnergyUnit(TOTGEnergyUnit);

                        mKLPTotEnergyResponse.setTDGRHName(TDGRHName);
                        mKLPTotEnergyResponse.setTDGRHValue(TDGRHValue);
                        mKLPTotEnergyResponse.setTDGRHUnit(TDGRHUnit);

                        mKLPTotEnergyResponse.setTOTGRHName(TOTGRHName);
                        mKLPTotEnergyResponse.setTOTGRHValue(TOTGRHValue);
                        mKLPTotEnergyResponse.setTOTGRHUnit(TOTGRHUnit);

                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mKLPTotEnergyResponse;
    }

    public KLPHybrideResponse getDeviceKLPDATAHYBRIDE(String DeviceType) {
        mKLPHybrideResponse = new KLPHybrideResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TOTGRHName, TOTGRHValue, TOTGRHUnit, TDGRHName,
                TDGRHValue, TDGRHUnit, TOTGEnergyName, TOTGEnergyValue, TOTGEnergyUnit,
                TDGEnergyName, TDGEnergyValue, TDGEnergyUnit, TOTMRHName, TOTMRHValue, TOTMRHUnit,
                TDMRHName, TDMRHValue, TDMRHUnit, TOTMEnergyName, TOTMEnergyValue, TOTMEnergyUnit, TDMEnergyName,
                TDMEnergyValue, TDMEnergyUnit, TOTMFlowName, TOTMFlowValue, TOTMFlowUnit,
                TDMFlowName, TDMFlowValue, TDMFlowUnit, TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_KLP_NAME + " where " + DEVICE_KLP_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultKLP));
                        ;
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultKLP));
                        TOTGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHNameKLP));
                        TOTGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHValueKLP));
                        TOTGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHUnitKLP));
                        // mKLPTotEnergyResponse.setTotalFault(Integer.parseInt(TotalFault));

                        TDGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHNameKLP));
                        TDGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHValueKLP));
                        TDGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHUnitKLP));

                        TOTGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyNameKLP));
                        TOTGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyValueKLP));
                        TOTGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyUnitKLP));

                        TDGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyNameKLP));
                        TDGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyValueKLP));
                        TDGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyUnitKLP));

                        TOTMRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHNameKLP));
                        TOTMRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHValueKLP));
                        TOTMRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHUnitKLP));

                        TDMRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHNameKLP));
                        TDMRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHValueKLP));
                        TDMRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHUnitKLP));

                        TOTMEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyNameKLP));
                        TOTMEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyValueKLP));
                        TOTMEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyUnitKLP));

                        TDMEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyNameKLP));
                        TDMEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyValueKLP));
                        TDMEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyUnitKLP));

                        TOTMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowNameKLP));
                        TOTMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowValueKLP));
                        // TOTMFlowUnit= cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowUnitKLP));

                        TDMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowNameKLP));
                        ;//DEVICE_TDMFlowNameKLP
                        TDMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowValueKLP));
                        ;
                        TDMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowUnitKLP));
                        ;

                        mKLPHybrideResponse.setTDMFlowName(TDMFlowName);
                        mKLPHybrideResponse.setTDMFlowValue(TDMFlowValue);
                        mKLPHybrideResponse.setTDMFlowUnit(TDMFlowUnit);

                        mKLPHybrideResponse.setTOTMFlowName(TOTMFlowName);
                        mKLPHybrideResponse.setTOTMFlowValue(TOTMFlowValue);
                        //mKLPTotEnergyResponse.setTOTMFlow(TDMFlowUnit);

                        mKLPHybrideResponse.setTDMRHName(TDMRHName);
                        mKLPHybrideResponse.setTDMRHValue(TDMRHValue);
                        mKLPHybrideResponse.setTDMRHUnit(TDMRHUnit);

                        mKLPHybrideResponse.setTOTMEnergyName(TOTMEnergyName);
                        mKLPHybrideResponse.setTOTMEnergyValue(TOTMEnergyValue);
                        mKLPHybrideResponse.setTOTMEnergyUnit(TOTMEnergyUnit);

                        mKLPHybrideResponse.setTDMEnergyName(TDMEnergyName);
                        mKLPHybrideResponse.setTDMEnergyValue(TDMEnergyValue);
                        mKLPHybrideResponse.setTDMEnergyUnit(TDMEnergyUnit);

                        mKLPHybrideResponse.setTDMRHName(TDMRHName);
                        mKLPHybrideResponse.setTDMRHValue(TDMRHValue);
                        mKLPHybrideResponse.setTDMRHUnit(TDMRHUnit);

                        mKLPHybrideResponse.setTOTMRHName(TOTMRHName);
                        mKLPHybrideResponse.setTOTMRHValue(TOTMRHValue);
                        mKLPHybrideResponse.setTOTMRHUnit(TOTMRHUnit);

                        mKLPHybrideResponse.setTDGEnergyName(TDGEnergyName);
                        mKLPHybrideResponse.setTDGEnergyValue(TDGEnergyValue);
                        mKLPHybrideResponse.setTDGEnergyUnit(TDGEnergyUnit);

                        mKLPHybrideResponse.setTOTGEnergyName(TOTGEnergyName);
                        mKLPHybrideResponse.setTOTGEnergyValue(TOTGEnergyValue);
                        mKLPHybrideResponse.setTOTGEnergyUnit(TOTGEnergyUnit);

                        mKLPHybrideResponse.setTDGRHName(TDGRHName);
                        mKLPHybrideResponse.setTDGRHValue(TDGRHValue);
                        mKLPHybrideResponse.setTDGRHUnit(TDGRHUnit);

                        mKLPHybrideResponse.setTOTGRHName(TOTGRHName);
                        mKLPHybrideResponse.setTOTGRHValue(TOTGRHValue);
                        mKLPHybrideResponse.setTOTGRHUnit(TOTGRHUnit);

                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mKLPHybrideResponse;
    }


    /////////////////////////////device Status Nikola
    private static final String DEVICE_TotalFaultNikola = "TotalFault";
    private static final String DEVICE_FaultNikola = "Fault";
    private static final String DEVICE_TOTGRHNameNikola = "TOTGRHName";
    private static final String DEVICE_TOTGRHValueNikola = "TOTGRHValue";
    private static final String DEVICE_TOTGRHUnitNikola = "TOTGRHUnit";
    private static final String DEVICE_TDGRHNameNikola = "TDGRHName";
    private static final String DEVICE_TDGRHValueNikola = "TDGRHValue";
    private static final String DEVICE_TDGRHUnitNikola = "TDGRHUnit";
    private static final String DEVICE_TOTGEnergyNameNikola = "TOTGEnergyName";
    private static final String DEVICE_TOTGEnergyValueNikola = "TOTGEnergyValue";
    private static final String DEVICE_TOTGEnergyUnitNikola = "TOTGEnergyUnit";
    private static final String DEVICE_TDGEnergyNameNikola = "TDGEnergyName";
    private static final String DEVICE_TDGEnergyValueNikola = "TDGEnergyValue";
    private static final String DEVICE_TDGEnergyUnitNikola = "TDGEnergyUnit";

    private static final String DEVICE_TOTLEnergyNameNikola = "TOTLEnergyName";
    private static final String DEVICE_TOTLEnergyValueNikola = "TOTLEnergyValue";
    private static final String DEVICE_TOTLEnergyUnitNikola = "TOTLEnergyUnit";

    private static final String DEVICE_TDLEnergyNameNikola = "TDLEnergyName";
    private static final String DEVICE_TDLEnergyValueNikola = "TDLEnergyValue";
    private static final String DEVICE_TDLEnergyUnitNikola = "TDLEnergyUnit";

    private static final String DEVICE_TOTBRHNameNikola = "TOTBRHName";
    private static final String DEVICE_TOTBRHValueNikola = "TOTBRHValue";
    private static final String DEVICE_TOTBRHUnitNikola = "TOTBRHUnit";

    private static final String DEVICE_TDBRHNameNikola = "TDBRHName";
    private static final String DEVICE_TDBRHValueNikola = "TDBRHValue";
    private static final String DEVICE_TDBRHUnitNikola = "TDBRHUnit";

    private static final String DEVICE_Nikola_DEVICE_TYPE = "DeviceType";


    private static final String DEVICE_Nikola_TEST1 = "TestOne";
    private static final String DEVICE_Nikola_TEST2 = "TestTwo";
    private static final String DEVICE_Nikola_TEST3 = "TestThree";

    private static final String DEVICE_Nikola_NAME = "devicenikolable";////////////////table name

    private static final String CREATE_TABLE_Nikola = "CREATE TABLE "
            + DEVICE_Nikola_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultNikola + " VARCHAR, " +
            DEVICE_FaultNikola + " VARCHAR, " +
            DEVICE_Nikola_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TOTGRHNameNikola + " VARCHAR, " +
            DEVICE_TOTGRHValueNikola + " VARCHAR, " +
            DEVICE_TOTGRHUnitNikola + " VARCHAR, " +
            DEVICE_TDGRHNameNikola + " VARCHAR, " +
            DEVICE_TDGRHValueNikola + " VARCHAR, " +
            DEVICE_TDGRHUnitNikola + " VARCHAR, " +
            DEVICE_TOTGEnergyNameNikola + " VARCHAR, " +
            DEVICE_TOTGEnergyValueNikola + " VARCHAR, " +
            DEVICE_TOTGEnergyUnitNikola + " VARCHAR, " +
            DEVICE_TDGEnergyNameNikola + " VARCHAR, " +
            DEVICE_TDGEnergyValueNikola + " VARCHAR, " +
            DEVICE_TDGEnergyUnitNikola + " VARCHAR, " +
            DEVICE_TOTLEnergyNameNikola + " VARCHAR, " +//////////////////change karna hai
            DEVICE_TOTLEnergyValueNikola + " VARCHAR, " +
            DEVICE_TOTLEnergyUnitNikola + " VARCHAR, " +
            DEVICE_TDLEnergyNameNikola + " VARCHAR, " +
            DEVICE_TDLEnergyValueNikola + " VARCHAR, " +
            DEVICE_TDLEnergyUnitNikola + " VARCHAR, " +
            DEVICE_TOTBRHNameNikola + " VARCHAR, " +
            DEVICE_TOTBRHValueNikola + " VARCHAR, " +
            DEVICE_TOTBRHUnitNikola + " VARCHAR, " +
            DEVICE_TDBRHNameNikola + " VARCHAR, " +
            DEVICE_TDBRHValueNikola + " VARCHAR, " +
            DEVICE_TDBRHUnitNikola + " VARCHAR, " +
            DEVICE_Nikola_TEST1 + " VARCHAR, " +
            DEVICE_Nikola_TEST2 + " VARCHAR, " +
            DEVICE_Nikola_TEST3 + " VARCHAR " +
            "); ";


    ////////////////////////////////insert Device status data

    public long insertDeviceNikolaData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, NikolaResponse mNikolaResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_Nikola_NAME + " where " + DEVICE_Nikola_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultNikola, mNikolaResponse.getTotalFault());
        values.put(DEVICE_FaultNikola, mNikolaResponse.getTotalFault());
        values.put(DEVICE_Nikola_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TOTGRHNameNikola, mNikolaResponse.getTOTGRHName());
        values.put(DEVICE_TOTGRHValueNikola, mNikolaResponse.getTOTGRHValue());
        values.put(DEVICE_TOTGRHUnitNikola, mNikolaResponse.getTOTGRHUnit());

        values.put(DEVICE_TDGRHNameNikola, mNikolaResponse.getTDGRHName());
        values.put(DEVICE_TDGRHValueNikola, mNikolaResponse.getTDGRHValue());
        values.put(DEVICE_TDGRHUnitNikola, mNikolaResponse.getTDGRHrUnit());

        values.put(DEVICE_TOTGEnergyNameNikola, mNikolaResponse.getTOTGEnergyName());
        values.put(DEVICE_TOTGEnergyValueNikola, mNikolaResponse.getTOTGEnergyValue());
        values.put(DEVICE_TOTGEnergyUnitNikola, mNikolaResponse.getTOTGEnergyUnit());

        values.put(DEVICE_TDGEnergyNameNikola, mNikolaResponse.getTDGEnergyName());
        values.put(DEVICE_TDGEnergyValueNikola, mNikolaResponse.getTDGEnergyValue());
        values.put(DEVICE_TDGEnergyUnitNikola, mNikolaResponse.getTDGEnergyUnit());


        values.put(DEVICE_TOTLEnergyNameNikola, mNikolaResponse.getTOTLEnergyName());
        values.put(DEVICE_TOTLEnergyValueNikola, mNikolaResponse.getTOTLEnergyValue());
        values.put(DEVICE_TOTLEnergyUnitNikola, mNikolaResponse.getTOTLEnergyUnit());

        values.put(DEVICE_TDLEnergyNameNikola, mNikolaResponse.getTDLEnergyName());
        values.put(DEVICE_TDLEnergyValueNikola, mNikolaResponse.getTDLEnergyValue());
        values.put(DEVICE_TDLEnergyUnitNikola, mNikolaResponse.getTDLEnergyUnit());


        values.put(DEVICE_TOTBRHNameNikola, mNikolaResponse.getTOTBRHName());
        values.put(DEVICE_TOTBRHValueNikola, mNikolaResponse.getTOTBRHValue());
        values.put(DEVICE_TOTBRHUnitNikola, mNikolaResponse.getTOTBRHUnit());

        values.put(DEVICE_TDBRHNameNikola, mNikolaResponse.getTDBRHName());
        values.put(DEVICE_TDBRHValueNikola, mNikolaResponse.getTDBRHValue());
        values.put(DEVICE_TDBRHUnitNikola, mNikolaResponse.getTDBRHUnit());

        values.put(DEVICE_Nikola_TEST1, "one");
        values.put(DEVICE_Nikola_TEST2, "Two");
        values.put(DEVICE_Nikola_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_Nikola_NAME, null, values);
        return insert;
    }

    public NikolaResponse getDeviceNikolaDATA(String DeviceType) {
        mNikolaResponse = new NikolaResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TOTGRHName, TOTGRHValue, TOTGRHUnit, TDGRHName,
                TDGRHValue, TDGRHUnit, TOTGEnergyName, TOTGEnergyValue, TOTGEnergyUnit,
                TDGEnergyName, TDGEnergyValue, TDGEnergyUnit,
                TOTLEnergyName, TOTLEnergyValue, TOTLEnergyUnit,
                TDLEnergyName, TDLEnergyValue, TDLEnergyUnit,
                TOTBRHName, TOTBRHValue, TOTBRHUnit,
                TDBRHName, TDBRHValue, TDBRHUnit,
                TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_Nikola_NAME + " where " + DEVICE_Nikola_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultNikola));
                        ;
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultNikola));
                        TOTGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHNameNikola));
                        TOTGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHValueNikola));
                        TOTGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGRHUnitNikola));
                        // mKLPTotEnergyResponse.setTotalFault(Integer.parseInt(TotalFault));

                        TDGRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHNameNikola));
                        TDGRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHValueNikola));
                        TDGRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGRHUnitNikola));

                        TOTGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyNameNikola));
                        TOTGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyValueNikola));
                        TOTGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTGEnergyUnitNikola));

                        TDGEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyNameNikola));
                        TDGEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyValueNikola));
                        TDGEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDGEnergyUnitNikola));

                        TOTLEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTLEnergyNameNikola));
                        TOTLEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTLEnergyValueNikola));
                        TOTLEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTLEnergyUnitNikola));

                        TDLEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDLEnergyNameNikola));
                        TDLEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDLEnergyValueNikola));
                        TDLEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDLEnergyUnitNikola));

                        TOTBRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTBRHNameNikola));
                        TOTBRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTBRHValueNikola));
                        TOTBRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTBRHUnitNikola));

                        TDBRHName = cursor.getString(cursor.getColumnIndex(DEVICE_TDBRHNameNikola));
                        TDBRHValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDBRHValueNikola));
                        TDBRHUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDBRHUnitNikola));


                        mNikolaResponse.setTDBRHName(TDBRHName);
                        mNikolaResponse.setTDBRHValue(TDBRHValue);
                        mNikolaResponse.setTDBRHUnit(TDBRHUnit);

                        mNikolaResponse.setTOTBRHName(TOTBRHName);
                        mNikolaResponse.setTOTBRHValue(TOTBRHValue);
                        mNikolaResponse.setTOTBRHUnit(TOTBRHUnit);

                        mNikolaResponse.setTDLEnergyName(TDLEnergyName);
                        mNikolaResponse.setTDLEnergyValue(TDLEnergyValue);
                        mNikolaResponse.setTDLEnergyUnit(TDLEnergyUnit);

                        mNikolaResponse.setTOTLEnergyName(TOTLEnergyName);
                        mNikolaResponse.setTOTLEnergyValue(TOTLEnergyValue);
                        mNikolaResponse.setTOTLEnergyUnit(TOTLEnergyUnit);

                        mNikolaResponse.setTDGEnergyName(TDGEnergyName);
                        mNikolaResponse.setTDGEnergyValue(TDGEnergyValue);
                        mNikolaResponse.setTDGEnergyUnit(TDGEnergyUnit);

                        mNikolaResponse.setTOTGEnergyName(TOTGEnergyName);
                        mNikolaResponse.setTOTGEnergyValue(TOTGEnergyValue);
                        mNikolaResponse.setTOTGEnergyUnit(TOTGEnergyUnit);

                        mNikolaResponse.setTDGRHName(TDGRHName);
                        mNikolaResponse.setTDGRHValue(TDGRHValue);
                        mNikolaResponse.setTDGRHrUnit(TDGRHUnit);

                        mNikolaResponse.setTOTGRHName(TOTGRHName);
                        mNikolaResponse.setTOTGRHValue(TOTGRHValue);
                        mNikolaResponse.setTOTGRHUnit(TOTGRHUnit);

                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mNikolaResponse;
    }

    ///////////////////////////////shima device local data

    private static final String DEVICE_TotalFaultSHIMA = "TotalFault";
    private static final String DEVICE_FaultSHIMA = "Fault";
    private static final String DEVICE_TDMFlowNameSHIMA = "TDMFlowName";
    private static final String DEVICE_TDMFlowUnitSHIMA = "TDMFlowUnit";
    private static final String DEVICE_TDMFlowValueSHIMA = "TDMFlowValue";

    private static final String DEVICE_TDMRHrNameSHIMA = "TDMRHrName";
    private static final String DEVICE_TDMRHrUnitSHIMA = "TDMRHrUnit";
    private static final String DEVICE_TDMRHrValueSHIMA = "TDMRHrValue";

    private static final String DEVICE_TDPVEnergyNameSHIMA = "TDPVEnergyName";
    private static final String DEVICE_TDPVEnergyUnitSHIMA = "TDPVEnergyUnit";
    private static final String DEVICE_TDPVEnergyValueSHIMA = "TDPVEnergyValue";

    private static final String DEVICE_TOTMFlowNameSHIMA = "TOTMFlowName";
    private static final String DEVICE_TOTMFlowUnitSHIMA = "TOTMFlowUnit";
    private static final String DEVICE_TOTMFlowValueSHIMA = "TOTMFlowValue";


    private static final String DEVICE_TOTMRHrNameSHIMA = "TOTMRHrName";
    private static final String DEVICE_TOTMRHrUnitSHIMA = "TOTMRHrUnit";
    private static final String DEVICE_TOTMRHrValueSHIMA = "TOTMRHrValue";

    private static final String DEVICE_TOTPVEnergyNameSHIMA = "TOTPVEnergyName";
    private static final String DEVICE_TOTPVEnergyUnitSHIMA = "TOTPVEnergyUnit";
    private static final String DEVICE_TOTPVEnergyValueSHIMA = "TOTPVEnergyValue";

    private static final String DEVICE_SHIMA_DEVICE_TYPE = "DeviceType";

    private static final String DEVICE_SHIMA_TEST1 = "TestOne";
    private static final String DEVICE_SHIMA_TEST2 = "TestTwo";
    private static final String DEVICE_SHIMA_TEST3 = "TestThree";

    private static final String DEVICE_SHIMA_NAME = "deviceshima";////////////////table name

    private static final String CREATE_TABLE_SHIMA = "CREATE TABLE "
            + DEVICE_SHIMA_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultSHIMA + " VARCHAR, " +
            DEVICE_FaultSHIMA + " VARCHAR, " +
            DEVICE_SHIMA_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TDMFlowNameSHIMA + " VARCHAR, " +
            DEVICE_TDMFlowUnitSHIMA + " VARCHAR, " +
            DEVICE_TDMFlowValueSHIMA + " VARCHAR, " +
            DEVICE_TDMRHrNameSHIMA + " VARCHAR, " +
            DEVICE_TDMRHrUnitSHIMA + " VARCHAR, " +
            DEVICE_TDMRHrValueSHIMA + " VARCHAR, " +
            DEVICE_TDPVEnergyNameSHIMA + " VARCHAR, " +
            DEVICE_TDPVEnergyUnitSHIMA + " VARCHAR, " +
            DEVICE_TDPVEnergyValueSHIMA + " VARCHAR, " +
            DEVICE_TOTMFlowNameSHIMA + " VARCHAR, " +
            DEVICE_TOTMFlowUnitSHIMA + " VARCHAR, " +
            DEVICE_TOTMFlowValueSHIMA + " VARCHAR, " +
            DEVICE_TOTMRHrNameSHIMA + " VARCHAR, " +
            DEVICE_TOTMRHrUnitSHIMA + " VARCHAR, " +
            DEVICE_TOTMRHrValueSHIMA + " VARCHAR, " +
            DEVICE_TOTPVEnergyNameSHIMA + " VARCHAR, " +
            DEVICE_TOTPVEnergyUnitSHIMA + " VARCHAR, " +
            DEVICE_TOTPVEnergyValueSHIMA + " VARCHAR, " +
            DEVICE_SHIMA_TEST1 + " VARCHAR, " +
            DEVICE_SHIMA_TEST2 + " VARCHAR, " +
            DEVICE_SHIMA_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceSHIMAData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, ShimaResponse mShimaResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_SHIMA_NAME + " where " + DEVICE_SHIMA_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultSHIMA, mShimaResponse.getTotalFault());
        values.put(DEVICE_FaultSHIMA, mShimaResponse.getTotalFault());
        values.put(DEVICE_SHIMA_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TDMFlowNameSHIMA, mShimaResponse.getTDMFlowName());
        values.put(DEVICE_TDMFlowUnitSHIMA, mShimaResponse.getTDMFlowUnit());
        values.put(DEVICE_TDMFlowValueSHIMA, mShimaResponse.getTDMFlowValue());

        values.put(DEVICE_TDMRHrNameSHIMA, mShimaResponse.getTDMRHrName());
        values.put(DEVICE_TDMRHrUnitSHIMA, mShimaResponse.getTDMRHrUnit());
        values.put(DEVICE_TDMRHrValueSHIMA, mShimaResponse.getTDMRHrValue());


        values.put(DEVICE_TDPVEnergyNameSHIMA, mShimaResponse.getTDPVEnergyName());
        values.put(DEVICE_TDPVEnergyUnitSHIMA, mShimaResponse.getTDPVEnergyUnit());
        values.put(DEVICE_TDPVEnergyValueSHIMA, mShimaResponse.getTDPVEnergyValue());

        values.put(DEVICE_TOTMFlowNameSHIMA, mShimaResponse.getTOTMFlowName());
        values.put(DEVICE_TOTMFlowUnitSHIMA, mShimaResponse.getTOTMFlowUnit());
        values.put(DEVICE_TOTMFlowValueSHIMA, mShimaResponse.getTOTMFlowValue());

        values.put(DEVICE_TOTMRHrNameSHIMA, mShimaResponse.getTOTMRHrName());
        values.put(DEVICE_TOTMRHrUnitSHIMA, mShimaResponse.getTOTMRHrUnit());
        values.put(DEVICE_TOTMRHrValueSHIMA, mShimaResponse.getTOTMRHrValue());


        values.put(DEVICE_TOTPVEnergyNameSHIMA, mShimaResponse.getTOTPVEnergyName());
        values.put(DEVICE_TOTPVEnergyUnitSHIMA, mShimaResponse.getTOTPVEnergyUnit());
        values.put(DEVICE_TOTPVEnergyValueSHIMA, mShimaResponse.getTOTPVEnergyValue());

        values.put(DEVICE_SHIMA_TEST1, "one");
        values.put(DEVICE_SHIMA_TEST2, "Two");
        values.put(DEVICE_SHIMA_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_SHIMA_NAME, null, values);
        return insert;
    }

    public ShimaResponse getDeviceSHIMADATA(String DeviceType) {
        mShimaResponse = new ShimaResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TDMFlowName, TDMFlowUnit, TDMFlowValue, TDMRHrName, TDMRHrUnit, TDMRHrValue,
                TDPVEnergyName, TDPVEnergyUnit, TDPVEnergyValue, TOTMFlowName, TOTMFlowUnit, TOTMFlowValue,
                TOTMRHrName, TOTMRHrUnit, TOTMRHrValue, TOTPVEnergyName, TOTPVEnergyUnit, TOTPVEnergyValue,
                TestOne, TestTwo, TestThree;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_SHIMA_NAME + " where " + DEVICE_SHIMA_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultSHIMA));
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultSHIMA));

                        TOTMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowNameSHIMA));
                        TOTMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowValueSHIMA));
                        TOTMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowUnitSHIMA));
                        mShimaResponse.setTOTMFlowName(TOTMFlowName);
                        mShimaResponse.setTOTMFlowValue(TOTMFlowValue);
                        mShimaResponse.setTOTMFlowUnit(TOTMFlowUnit);

                        TDMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowNameSHIMA));//DEVICE_TDMFlowNameKLP
                        TDMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowValueSHIMA));
                        TDMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowUnitSHIMA));
                        mShimaResponse.setTDMFlowName(TDMFlowName);
                        mShimaResponse.setTDMFlowValue(TDMFlowValue);
                        mShimaResponse.setTDMFlowUnit(TDMFlowUnit);

                        TDMRHrName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHrNameSHIMA));
                        TDMRHrUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHrUnitSHIMA));
                        TDMRHrValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHrValueSHIMA));
                        mShimaResponse.setTDMRHrName(TDMRHrName);
                        mShimaResponse.setTDMRHrUnit(TDMRHrUnit);
                        mShimaResponse.setTDMRHrValue(TDMRHrValue);

                        TDPVEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDPVEnergyNameSHIMA));
                        TDPVEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDPVEnergyUnitSHIMA));
                        TDPVEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDPVEnergyValueSHIMA));
                        mShimaResponse.setTDPVEnergyName(TDPVEnergyName);
                        mShimaResponse.setTDPVEnergyUnit(TDPVEnergyUnit);
                        mShimaResponse.setTDPVEnergyValue(TDPVEnergyValue);

                        TOTMRHrName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHrNameSHIMA));
                        TOTMRHrUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHrUnitSHIMA));
                        TOTMRHrValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHrValueSHIMA));
                        mShimaResponse.setTOTMRHrName(TOTMRHrName);
                        mShimaResponse.setTOTMRHrUnit(TOTMRHrUnit);
                        mShimaResponse.setTOTMRHrValue(TOTMRHrValue);

                        TOTPVEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPVEnergyNameSHIMA));
                        TOTPVEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPVEnergyUnitSHIMA));
                        TOTPVEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPVEnergyValueSHIMA));
                        mShimaResponse.setTOTPVEnergyName(TOTPVEnergyName);
                        mShimaResponse.setTOTPVEnergyUnit(TOTPVEnergyUnit);
                        mShimaResponse.setTOTPVEnergyValue(TOTPVEnergyValue);
                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mShimaResponse;
    }

    /////////////////////Vikas KUSPC //////////////////////////////////////////////

    private static final String DEVICE_TotalFaultUSPC = "TotalFault";
    private static final String DEVICE_FaultUSPC = "Fault";

    private static final String DEVICE_TDUSPCHrUnitUSPC = "TDUSPCHrUnit";
    private static final String DEVICE_TDPEnergyNameUSPC = "TDPEnergyName";

    private static final String DEVICE_TDPEnergyUnitUSPC = "TDPEnergyUnit";
    private static final String DEVICE_TDPEnergyValueUSPC = "TDPEnergyValue";
    private static final String DEVICE_TDPRHRNameUSPC = "TDPRHRName";

    private static final String DEVICE_TDPRHRUnitUSPC = "TDPRHRUnit";
    private static final String DEVICE_TDPRHRValueUSPC = "TDPRHRValue";

    private static final String DEVICE_TDUSPCEnergyNameUSPC = "TDUSPCEnergyName";
    private static final String DEVICE_TDUSPCEnergyUnitUSPC = "TDUSPCEnergyUnit";

    private static final String DEVICE_TDUSPCEnergyValueUSPC = "TDUSPCEnergyValue";
    private static final String DEVICE_TDUSPCHrNameUSPC = "TDUSPCHrName";
    private static final String DEVICE_TDUSPCHrValueUSPC = "TDUSPCHrValue";

    private static final String DEVICE_TOTFlowNameUSPC = "TOTFlowName";
    private static final String DEVICE_TOTFlowUnitUSPC = "TOTFlowUnit";
    private static final String DEVICE_TOTFlowValueUSPC = "TOTFlowValue";

    private static final String DEVICE_TDFlowNameUSPC = "TDFlowName";
    private static final String DEVICE_TDFlowValueUSPC = "TDFlowValue";
    private static final String DEVICE_TDFlowUnitUSPC = "TDFlowUnit";

    private static final String DEVICE_TOTPEnergyNameUSPC = "TOTPEnergyName";
    private static final String DEVICE_TOTPEnergyUnitUSPC = "TOTPEnergyUnit";
    private static final String DEVICE_TOTPEnergyValueUSPC = "TOTPEnergyValue";

    private static final String DEVICE_TOTPRHRNameUSPC = "TOTPRHRName";
    private static final String DEVICE_TOTPRHRUnitUSPC = "TOTPRHRUnit";
    private static final String DEVICE_TOTPRHRValueUSPC = "TOTPRHRValue";

    private static final String DEVICE_TOTUSPCEnergyNameUSPC = "TOTUSPCEnergyName";
    private static final String DEVICE_TOTUSPCEnergyUnitUSPC = "TOTUSPCEnergyUnit";
    private static final String DEVICE_TOTUSPCEnergyValueUSPC = "TOTUSPCEnergyValue";

    private static final String DEVICE_TOTUSPCHrNameUSPC = "TOTUSPCHrName";
    private static final String DEVICE_TOTUSPCHrUnitUSPC = "TOTUSPCHrUnit";
    private static final String DEVICE_TOTUSPCHrValueUSPC = "TOTUSPCHrValue";

    private static final String DEVICE_USPC_APP_IDUSPC = "USPC_APP_ID";
    private static final String USPC_APP_IDImageUSPC = "USPC_APP_IDImage";
    private static final String USPC_APP_IDValueUSPC = "USPC_APP_IDValue";

    private static final String DEVICE_USPC_DEVICE_TYPE = "DeviceType";
    private static final String DEVICE_USPC_TEST1 = "TestOne";
    private static final String DEVICE_USPC_TEST2 = "TestTwo";
    private static final String DEVICE_USPC_TEST3 = "TestThree";

    private static final String DEVICE_USPC_NAME = "deviceUSPC";////////////////table name

    private static final String CREATE_TABLE_USPC = "CREATE TABLE "
            + DEVICE_USPC_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultUSPC + " VARCHAR, " +
            DEVICE_FaultUSPC + " VARCHAR, " +
            DEVICE_USPC_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TDUSPCHrUnitUSPC + " VARCHAR, " +
            DEVICE_TDPEnergyNameUSPC + " VARCHAR, " +
            DEVICE_TDPEnergyUnitUSPC + " VARCHAR, " +
            DEVICE_TDPEnergyValueUSPC + " VARCHAR, " +
            DEVICE_TDPRHRNameUSPC + " VARCHAR, " +
            DEVICE_TDPRHRUnitUSPC + " VARCHAR, " +
            DEVICE_TDPRHRValueUSPC + " VARCHAR, " +
            DEVICE_TDUSPCEnergyNameUSPC + " VARCHAR, " +
            DEVICE_TDUSPCEnergyUnitUSPC + " VARCHAR, " +
            DEVICE_TDUSPCEnergyValueUSPC + " VARCHAR, " +
            DEVICE_TDUSPCHrNameUSPC + " VARCHAR, " +
            DEVICE_TDUSPCHrValueUSPC + " VARCHAR, " +
            DEVICE_TOTFlowNameUSPC + " VARCHAR, " +
            DEVICE_TOTFlowUnitUSPC + " VARCHAR, " +
            DEVICE_TOTFlowValueUSPC + " VARCHAR, " +
            DEVICE_TDFlowNameUSPC + " VARCHAR, " +
            DEVICE_TDFlowValueUSPC + " VARCHAR, " +
            DEVICE_TDFlowUnitUSPC + " VARCHAR, " +
            DEVICE_TOTPEnergyNameUSPC + " VARCHAR, " +
            DEVICE_TOTPEnergyUnitUSPC + " VARCHAR, " +
            DEVICE_TOTPEnergyValueUSPC + " VARCHAR, " +
            DEVICE_TOTPRHRNameUSPC + " VARCHAR, " +
            DEVICE_TOTPRHRUnitUSPC + " VARCHAR, " +
            DEVICE_TOTPRHRValueUSPC + " VARCHAR, " +
            DEVICE_TOTUSPCEnergyNameUSPC + " VARCHAR, " +
            DEVICE_TOTUSPCEnergyUnitUSPC + " VARCHAR, " +
            DEVICE_TOTUSPCEnergyValueUSPC + " VARCHAR, " +
            DEVICE_TOTUSPCHrNameUSPC + " VARCHAR, " +
            DEVICE_TOTUSPCHrUnitUSPC + " VARCHAR, " +
            DEVICE_TOTUSPCHrValueUSPC + " VARCHAR, " +
            DEVICE_USPC_APP_IDUSPC + " VARCHAR, " +
            USPC_APP_IDImageUSPC + " VARCHAR, " +
            USPC_APP_IDValueUSPC + " VARCHAR, " +
            DEVICE_USPC_TEST1 + " VARCHAR, " +
            DEVICE_USPC_TEST2 + " VARCHAR, " +
            DEVICE_USPC_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceUSPCData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, UspcEnrgyResponse mUspcEnrgyResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_USPC_NAME + " where " + DEVICE_USPC_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultUSPC, "0");
        values.put(DEVICE_FaultUSPC, "0");
        values.put(DEVICE_USPC_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TDUSPCHrUnitUSPC, mUspcEnrgyResponse.getTDUSPCHrUnit());
        values.put(DEVICE_TDPEnergyNameUSPC, mUspcEnrgyResponse.getTDPEnergyName());

        values.put(DEVICE_TDPEnergyUnitUSPC, mUspcEnrgyResponse.getTDPEnergyUnit());
        values.put(DEVICE_TDPEnergyValueUSPC, mUspcEnrgyResponse.getTDPEnergyValue());

        values.put(DEVICE_TDPRHRNameUSPC, mUspcEnrgyResponse.getTDPRHRName());
        values.put(DEVICE_TDPRHRUnitUSPC, mUspcEnrgyResponse.getTDPRHRUnit());
        values.put(DEVICE_TDPRHRValueUSPC, mUspcEnrgyResponse.getTDPRHRValue());

        values.put(DEVICE_TDUSPCEnergyNameUSPC, mUspcEnrgyResponse.getTDUSPCEnergyName());
        values.put(DEVICE_TDUSPCEnergyUnitUSPC, mUspcEnrgyResponse.getTDUSPCEnergyUnit());
        values.put(DEVICE_TDUSPCEnergyValueUSPC, mUspcEnrgyResponse.getTDUSPCEnergyValue());

        values.put(DEVICE_TDUSPCHrNameUSPC, mUspcEnrgyResponse.getTDUSPCHrName());
        values.put(DEVICE_TDUSPCHrValueUSPC, mUspcEnrgyResponse.getTDUSPCHrValue());

        values.put(DEVICE_TOTFlowNameUSPC, mUspcEnrgyResponse.getTOTFlowName());
        values.put(DEVICE_TOTFlowUnitUSPC, mUspcEnrgyResponse.getTOTFlowUnit());
        values.put(DEVICE_TOTFlowValueUSPC, mUspcEnrgyResponse.getTOTFlowValue());

        values.put(DEVICE_TDFlowNameUSPC, mUspcEnrgyResponse.getTDFlowName());
        values.put(DEVICE_TDFlowValueUSPC, mUspcEnrgyResponse.getTDFlowValue());
        values.put(DEVICE_TDFlowUnitUSPC, mUspcEnrgyResponse.getTDFlowUnit());

        values.put(DEVICE_TOTPEnergyNameUSPC, mUspcEnrgyResponse.getTOTPEnergyName());
        values.put(DEVICE_TOTPEnergyUnitUSPC, mUspcEnrgyResponse.getTOTPEnergyUnit());
        values.put(DEVICE_TOTPEnergyValueUSPC, mUspcEnrgyResponse.getTOTPEnergyValue());

        values.put(DEVICE_TOTPRHRNameUSPC, mUspcEnrgyResponse.getTOTPRHRName());
        values.put(DEVICE_TOTPRHRUnitUSPC, mUspcEnrgyResponse.getTOTPRHRUnit());
        values.put(DEVICE_TOTPRHRValueUSPC, mUspcEnrgyResponse.getTOTPRHRValue());

        values.put(DEVICE_TOTUSPCEnergyNameUSPC, mUspcEnrgyResponse.getTOTUSPCEnergyName());
        values.put(DEVICE_TOTUSPCEnergyUnitUSPC, mUspcEnrgyResponse.getTOTUSPCEnergyUnit());
        values.put(DEVICE_TOTUSPCEnergyValueUSPC, mUspcEnrgyResponse.getTOTUSPCEnergyValue());

        values.put(DEVICE_TOTUSPCHrNameUSPC, mUspcEnrgyResponse.getTOTUSPCHrName());
        values.put(DEVICE_TOTUSPCHrUnitUSPC, mUspcEnrgyResponse.getTOTUSPCHrUnit());
        values.put(DEVICE_TOTUSPCHrValueUSPC, mUspcEnrgyResponse.getTOTUSPCHrValue());

        values.put(DEVICE_USPC_APP_IDUSPC, mUspcEnrgyResponse.getUSPCAPPID());
        values.put(USPC_APP_IDImageUSPC, mUspcEnrgyResponse.getUSPCAPPIDImage());
        values.put(USPC_APP_IDValueUSPC, mUspcEnrgyResponse.getUSPCAPPIDValue());

        values.put(DEVICE_USPC_TEST1, "one");
        values.put(DEVICE_USPC_TEST2, "Two");
        values.put(DEVICE_USPC_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_USPC_NAME, null, values);
        return insert;
    }

    public UspcEnrgyResponse getDeviceUSPCDATA(String DeviceType) {
        mUspcEnrgyResponse = new UspcEnrgyResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TestOne, TestTwo, TestThree;
        String TDUSPCHrUnit, TDPEnergyName, TDPEnergyUnit, TDPEnergyValue;
        String TOTFlowName, TOTFlowUnit, TOTFlowValue;
        String TDFlowName, TDFlowValue, TDFlowUnit;
        String TDUSPCHrName, TDUSPCHrValue;
        String TOTPEnergyName, TOTPEnergyUnit, TOTPEnergyValue;
        String USPC_APP_ID, USPC_APP_IDImage, USPC_APP_IDValue;
        String TOTUSPCHrName, TOTUSPCHrUnit, TOTUSPCHrValue;
        String TOTUSPCEnergyName, TOTUSPCEnergyUnit, TOTUSPCEnergyValue;
        String TOTPRHRName, TOTPRHRUnit, TOTPRHRValue;
        String TDPRHRName, TDPRHRUnit, TDPRHRValue, TDUSPCEnergyName, TDUSPCEnergyUnit, TDUSPCEnergyValue;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_USPC_NAME + " where " + DEVICE_USPC_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultUSPC));
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultUSPC));

                        TDPEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDPEnergyNameUSPC));
                        TDPEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDPEnergyUnitUSPC));
                        TDPEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDPEnergyValueUSPC));
                        mUspcEnrgyResponse.setTDPEnergyName(TDPEnergyName);
                        mUspcEnrgyResponse.setTDPEnergyUnit(TDPEnergyUnit);
                        mUspcEnrgyResponse.setTDPEnergyValue(TDPEnergyValue);

                        TDPRHRName = cursor.getString(cursor.getColumnIndex(DEVICE_TDPRHRNameUSPC));//DEVICE_TDMFlowNameKLP
                        TDPRHRUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDPRHRUnitUSPC));
                        TDPRHRValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDPRHRValueUSPC));
                        mUspcEnrgyResponse.setTDPRHRName(TDPRHRName);
                        mUspcEnrgyResponse.setTDPRHRUnit(TDPRHRUnit);
                        mUspcEnrgyResponse.setTDPRHRValue(TDPRHRValue);

                        TDUSPCEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDUSPCEnergyNameUSPC));
                        TDUSPCEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDUSPCEnergyUnitUSPC));
                        TDUSPCEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDUSPCEnergyValueUSPC));
                        mUspcEnrgyResponse.setTDUSPCEnergyName(TDUSPCEnergyName);
                        mUspcEnrgyResponse.setTDUSPCEnergyUnit(TDUSPCEnergyUnit);
                        mUspcEnrgyResponse.setTDUSPCEnergyValue(TDUSPCEnergyValue);

                        TDUSPCHrName = cursor.getString(cursor.getColumnIndex(DEVICE_TDUSPCHrNameUSPC));
                        TDUSPCHrValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDUSPCHrValueUSPC));
                        TDUSPCHrUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDUSPCHrUnitUSPC));
                        mUspcEnrgyResponse.setTDUSPCHrUnit(TDUSPCHrUnit);
                        mUspcEnrgyResponse.setTDUSPCHrName(TDUSPCHrName);
                        mUspcEnrgyResponse.setTDUSPCHrValue(TDUSPCHrValue);

                        TOTFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTFlowNameUSPC));
                        TOTFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTFlowUnitUSPC));
                        TOTFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTFlowValueUSPC));
                        mUspcEnrgyResponse.setTOTFlowName(TOTFlowName);
                        mUspcEnrgyResponse.setTOTFlowUnit(TOTFlowUnit);
                        mUspcEnrgyResponse.setTOTFlowValue(TOTFlowValue);

                        TDFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDFlowNameUSPC));
                        TDFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDFlowValueUSPC));
                        TDFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDFlowUnitUSPC));
                        mUspcEnrgyResponse.setTDFlowName(TDFlowName);
                        mUspcEnrgyResponse.setTDFlowValue(TDFlowValue);
                        mUspcEnrgyResponse.setTDFlowUnit(TDFlowUnit);

                        TOTPEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPEnergyNameUSPC));
                        TOTPEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPEnergyUnitUSPC));
                        TOTPEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPEnergyValueUSPC));
                        mUspcEnrgyResponse.setTOTPEnergyName(TOTPEnergyName);
                        mUspcEnrgyResponse.setTOTPEnergyUnit(TOTPEnergyUnit);
                        mUspcEnrgyResponse.setTOTPEnergyValue(TOTPEnergyValue);

                        TOTPRHRName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPRHRNameUSPC));
                        TOTPRHRUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPRHRUnitUSPC));
                        TOTPRHRValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPRHRValueUSPC));
                        mUspcEnrgyResponse.setTOTPRHRName(TOTPRHRName);
                        mUspcEnrgyResponse.setTOTPRHRUnit(TOTPRHRUnit);
                        mUspcEnrgyResponse.setTOTPRHRValue(TOTPRHRValue);

                        TOTUSPCEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTUSPCEnergyNameUSPC));
                        TOTUSPCEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTUSPCEnergyUnitUSPC));
                        TOTUSPCEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTUSPCEnergyValueUSPC));
                        mUspcEnrgyResponse.setTOTUSPCEnergyName(TOTUSPCEnergyName);
                        mUspcEnrgyResponse.setTOTUSPCEnergyUnit(TOTUSPCEnergyUnit);
                        mUspcEnrgyResponse.setTOTUSPCEnergyValue(TOTUSPCEnergyValue);

                        TOTUSPCHrName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTUSPCHrNameUSPC));
                        TOTUSPCHrUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTUSPCHrUnitUSPC));
                        TOTUSPCHrValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTUSPCHrValueUSPC));
                        mUspcEnrgyResponse.setTOTUSPCHrName(TOTUSPCHrName);
                        mUspcEnrgyResponse.setTOTUSPCHrUnit(TOTUSPCHrUnit);
                        mUspcEnrgyResponse.setTOTUSPCHrValue(TOTUSPCHrValue);

                        USPC_APP_ID = cursor.getString(cursor.getColumnIndex(DEVICE_USPC_APP_IDUSPC));
                        USPC_APP_IDImage = cursor.getString(cursor.getColumnIndex(USPC_APP_IDImageUSPC));
                        USPC_APP_IDValue = cursor.getString(cursor.getColumnIndex(USPC_APP_IDValueUSPC));
                        mUspcEnrgyResponse.setUSPCAPPID(USPC_APP_ID);
                        mUspcEnrgyResponse.setUSPCAPPIDImage(USPC_APP_IDImage);
                        mUspcEnrgyResponse.setUSPCAPPIDValue(USPC_APP_IDValue);
                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mUspcEnrgyResponse;
    }
    ////////////////////////////end of kuspc//////////////////////////////////////
    ///////////////////////////////AUTHModelData logger device local data

    private static final String DEVICE_TotalFaultDATALOger = "TotalFault";
    private static final String DEVICE_FaultDATALOger = "Fault";
    private static final String DEVICE_TDMFlowNameDATALOger = "TDMFlowName";
    private static final String DEVICE_TDMFlowUnitDATALOger = "TDMFlowUnit";
    private static final String DEVICE_TDMFlowValueDATALOger = "TDMFlowValue";

    private static final String DEVICE_TDMRHrNameDATALOger = "TDMRHrName";
    private static final String DEVICE_TDMRHrUnitDATALOger = "TDMRHrUnit";
    private static final String DEVICE_TDMRHrValueDATALOger = "TDMRHrValue";

    private static final String DEVICE_TDPVEnergyNameDATALOger = "TDPVEnergyName";
    private static final String DEVICE_TDPVEnergyUnitDATALOger = "TDPVEnergyUnit";
    private static final String DEVICE_TDPVEnergyValueDATALOger = "TDPVEnergyValue";

    private static final String DEVICE_TOTMFlowNameDATALOger = "TOTMFlowName";
    private static final String DEVICE_TOTMFlowUnitDATALOger = "TOTMFlowUnit";
    private static final String DEVICE_TOTMFlowValueDATALOger = "TOTMFlowValue";

    private static final String DEVICE_TOTMRHrNameDATALOger = "TOTMRHrName";
    private static final String DEVICE_TOTMRHrUnitDATALOger = "TOTMRHrUnit";
    private static final String DEVICE_TOTMRHrValueDATALOger = "TOTMRHrValue";

    private static final String DEVICE_TOTPVEnergyNameDATALOger = "TOTPVEnergyName";
    private static final String DEVICE_TOTPVEnergyUnitDATALOger = "TOTPVEnergyUnit";
    private static final String DEVICE_TOTPVEnergyValueDATALOger = "TOTPVEnergyValue";

    private static final String DEVICE_DATALOger_DEVICE_TYPE = "DeviceType";

    private static final String DEVICE_DATALOger_TEST1 = "TestOne";
    private static final String DEVICE_DATALOger_TEST2 = "TestTwo";
    private static final String DEVICE_DATALOger_TEST3 = "TestThree";

    private static final String DEVICE_DATALOger_NAME = "devicedatalogger";////////////////table name

    private static final String CREATE_TABLE_DATALOger = "CREATE TABLE "
            + DEVICE_DATALOger_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultDATALOger + " VARCHAR, " +
            DEVICE_FaultDATALOger + " VARCHAR, " +
            DEVICE_DATALOger_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TDMFlowNameDATALOger + " VARCHAR, " +
            DEVICE_TDMFlowUnitDATALOger + " VARCHAR, " +
            DEVICE_TDMFlowValueDATALOger + " VARCHAR, " +
            DEVICE_TDMRHrNameDATALOger + " VARCHAR, " +
            DEVICE_TDMRHrUnitDATALOger + " VARCHAR, " +
            DEVICE_TDMRHrValueDATALOger + " VARCHAR, " +
            DEVICE_TDPVEnergyNameDATALOger + " VARCHAR, " +
            DEVICE_TDPVEnergyUnitDATALOger + " VARCHAR, " +
            DEVICE_TDPVEnergyValueDATALOger + " VARCHAR, " +
            DEVICE_TOTMFlowNameDATALOger + " VARCHAR, " +
            DEVICE_TOTMFlowUnitDATALOger + " VARCHAR, " +
            DEVICE_TOTMFlowValueDATALOger + " VARCHAR, " +
            DEVICE_TOTMRHrNameDATALOger + " VARCHAR, " +
            DEVICE_TOTMRHrUnitDATALOger + " VARCHAR, " +
            DEVICE_TOTMRHrValueDATALOger + " VARCHAR, " +
            DEVICE_TOTPVEnergyNameDATALOger + " VARCHAR, " +
            DEVICE_TOTPVEnergyUnitDATALOger + " VARCHAR, " +
            DEVICE_TOTPVEnergyValueDATALOger + " VARCHAR, " +
            DEVICE_DATALOger_TEST1 + " VARCHAR, " +
            DEVICE_DATALOger_TEST2 + " VARCHAR, " +
            DEVICE_DATALOger_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceDATALOgerData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, ViechiDataResponse mViechiDataResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_DATALOger_NAME + " where " + DEVICE_DATALOger_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultDATALOger, mViechiDataResponse.getTotalFault());
        values.put(DEVICE_FaultDATALOger, mViechiDataResponse.getTotalFault());
        values.put(DEVICE_DATALOger_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TDMFlowNameDATALOger, mViechiDataResponse.getTDMFlowName());
        values.put(DEVICE_TDMFlowUnitDATALOger, mViechiDataResponse.getTDMFlowUnit());
        values.put(DEVICE_TDMFlowValueDATALOger, mViechiDataResponse.getTDMFlowValue());

        values.put(DEVICE_TDMRHrNameDATALOger, mViechiDataResponse.getTDMRHrName());
        values.put(DEVICE_TDMRHrUnitDATALOger, mViechiDataResponse.getTDMRHrUnit());
        values.put(DEVICE_TDMRHrValueDATALOger, mViechiDataResponse.getTDMRHrValue());

        values.put(DEVICE_TDPVEnergyNameDATALOger, mViechiDataResponse.getTDPVEnergyName());
        values.put(DEVICE_TDPVEnergyUnitDATALOger, mViechiDataResponse.getTDPVEnergyUnit());
        values.put(DEVICE_TDPVEnergyValueDATALOger, mViechiDataResponse.getTDPVEnergyValue());

        values.put(DEVICE_TOTMFlowNameDATALOger, mViechiDataResponse.getTOTMFlowName());
        values.put(DEVICE_TOTMFlowUnitDATALOger, mViechiDataResponse.getTOTMFlowUnit());
        values.put(DEVICE_TOTMFlowValueDATALOger, mViechiDataResponse.getTOTMFlowValue());

        values.put(DEVICE_TOTMRHrNameDATALOger, mViechiDataResponse.getTOTMRHrName());
        values.put(DEVICE_TOTMRHrUnitDATALOger, mViechiDataResponse.getTOTMRHrUnit());
        values.put(DEVICE_TOTMRHrValueDATALOger, mViechiDataResponse.getTOTMRHrValue());

        values.put(DEVICE_TOTPVEnergyNameDATALOger, mViechiDataResponse.getTOTPVEnergyName());
        values.put(DEVICE_TOTPVEnergyUnitDATALOger, mViechiDataResponse.getTOTPVEnergyUnit());
        values.put(DEVICE_TOTPVEnergyValueDATALOger, mViechiDataResponse.getTOTPVEnergyValue());

        values.put(DEVICE_DATALOger_TEST1, "one");
        values.put(DEVICE_DATALOger_TEST2, "Two");
        values.put(DEVICE_DATALOger_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_DATALOger_NAME, null, values);
        return insert;
    }

    public ViechiDataResponse getDeviceDATALOgerDATA(String DeviceType) {
        mViechiDataResponse = new ViechiDataResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TDMFlowName, TDMFlowUnit, TDMFlowValue, TDMRHrName, TDMRHrUnit, TDMRHrValue,
                TDPVEnergyName, TDPVEnergyUnit, TDPVEnergyValue, TOTMFlowName, TOTMFlowUnit, TOTMFlowValue,
                TOTMRHrName, TOTMRHrUnit, TOTMRHrValue, TOTPVEnergyName, TOTPVEnergyUnit, TOTPVEnergyValue,
                TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_DATALOger_NAME + " where " + DEVICE_DATALOger_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultDATALOger));

                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultDATALOger));

                        TOTMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowNameDATALOger));
                        TOTMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowValueDATALOger));
                        TOTMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowUnitDATALOger));
                        mViechiDataResponse.setTOTMFlowName(TOTMFlowName);
                        mViechiDataResponse.setTOTMFlowValue(TOTMFlowValue);
                        mViechiDataResponse.setTOTMFlowUnit(TOTMFlowUnit);

                        TDMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowNameDATALOger));//DEVICE_TDMFlowNameKLP
                        TDMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowValueDATALOger));
                        TDMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowUnitDATALOger));
                        mViechiDataResponse.setTDMFlowName(TDMFlowName);
                        mViechiDataResponse.setTDMFlowValue(TDMFlowValue);
                        mViechiDataResponse.setTDMFlowUnit(TDMFlowUnit);


                        TDMRHrName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHrNameDATALOger));
                        TDMRHrUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHrUnitDATALOger));
                        TDMRHrValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMRHrValueDATALOger));
                        mViechiDataResponse.setTDMRHrName(TDMRHrName);
                        mViechiDataResponse.setTDMRHrUnit(TDMRHrUnit);
                        mViechiDataResponse.setTDMRHrValue(TDMRHrValue);


                        TDPVEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDPVEnergyNameDATALOger));
                        TDPVEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDPVEnergyUnitDATALOger));
                        TDPVEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDPVEnergyValueDATALOger));
                        mViechiDataResponse.setTDPVEnergyName(TDPVEnergyName);
                        mViechiDataResponse.setTDPVEnergyUnit(TDPVEnergyUnit);
                        mViechiDataResponse.setTDPVEnergyValue(TDPVEnergyValue);


                        TOTMRHrName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHrNameDATALOger));
                        TOTMRHrUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHrUnitDATALOger));
                        TOTMRHrValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMRHrValueDATALOger));
                        mViechiDataResponse.setTOTMRHrName(TOTMRHrName);
                        mViechiDataResponse.setTOTMRHrUnit(TOTMRHrUnit);
                        mViechiDataResponse.setTOTMRHrValue(TOTMRHrValue);

                        TOTPVEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPVEnergyNameDATALOger));
                        TOTPVEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPVEnergyUnitDATALOger));
                        TOTPVEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTPVEnergyValueDATALOger));
                        mViechiDataResponse.setTOTPVEnergyName(TOTPVEnergyName);
                        mViechiDataResponse.setTOTPVEnergyUnit(TOTPVEnergyUnit);
                        mViechiDataResponse.setTOTPVEnergyValue(TOTPVEnergyValue);

                        //TestOne;
                        // TestTwo;
                        // Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mViechiDataResponse;
    }


    ///////////////////////////////AUTHModelData logger device local data

    private static final String DEVICE_TotalFaultAoneSs = "TotalFault";
    private static final String DEVICE_FaultAoneSs = "Fault";

    private static final String DEVICE_TDMFlowNameAoneSs = "TDMFlowName";
    private static final String DEVICE_TDMFlowUnitAoneSs = "TDMFlowUnit";
    private static final String DEVICE_TDMFlowValueAoneSs = "TDMFlowValue";


    private static final String DEVICE_TDMEnergyNameAoneSs = "TDPVEnergyName";
    private static final String DEVICE_TDMEnergyUnitAoneSs = "TDPVEnergyUnit";
    private static final String DEVICE_TDMEnergyValueAoneSs = "TDPVEnergyValue";

    private static final String DEVICE_TOTMFlowNameAoneSs = "TOTMFlowName";
    private static final String DEVICE_TOTMFlowUnitAoneSs = "TOTMFlowUnit";
    private static final String DEVICE_TOTMFlowValueAoneSs = "TOTMFlowValue";

    private static final String DEVICE_TOTMEnergyNameAoneSs = "TOTPVEnergyName";
    private static final String DEVICE_TOTMEnergyUnitAoneSs = "TOTPVEnergyUnit";
    private static final String DEVICE_TOTMEnergyValueAoneSs = "TOTPVEnergyValue";

    private static final String DEVICE_AoneSs_DEVICE_TYPE = "DeviceType";

    private static final String DEVICE_AoneSs_TEST1 = "TestOne";
    private static final String DEVICE_AoneSs_TEST2 = "TestTwo";
    private static final String DEVICE_AoneSs_TEST3 = "TestThree";

    private static final String DEVICE_AONESS_NAME = "deviceaoness";////////////////table name

    private static final String CREATE_TABLE_AONESS = "CREATE TABLE "
            + DEVICE_AONESS_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_TotalFaultAoneSs + " VARCHAR, " +
            DEVICE_FaultAoneSs + " VARCHAR, " +
            DEVICE_AoneSs_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_TDMFlowNameAoneSs + " VARCHAR, " +
            DEVICE_TDMFlowUnitAoneSs + " VARCHAR, " +
            DEVICE_TDMFlowValueAoneSs + " VARCHAR, " +
            DEVICE_TDMEnergyNameAoneSs + " VARCHAR, " +
            DEVICE_TDMEnergyUnitAoneSs + " VARCHAR, " +
            DEVICE_TDMEnergyValueAoneSs + " VARCHAR, " +
            DEVICE_TOTMFlowNameAoneSs + " VARCHAR, " +
            DEVICE_TOTMFlowUnitAoneSs + " VARCHAR, " +
            DEVICE_TOTMFlowValueAoneSs + " VARCHAR, " +
            DEVICE_TOTMEnergyNameAoneSs + " VARCHAR, " +
            DEVICE_TOTMEnergyUnitAoneSs + " VARCHAR, " +
            DEVICE_TOTMEnergyValueAoneSs + " VARCHAR, " +
            DEVICE_AoneSs_TEST1 + " VARCHAR, " +
            DEVICE_AoneSs_TEST2 + " VARCHAR, " +
            DEVICE_AoneSs_TEST3 + " VARCHAR " +
            "); ";


    public long insertDeviceA1ssData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, AOneSSResponse mAOneSSResponse, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("delete  from " + DEVICE_AONESS_NAME + " where " + DEVICE_AoneSs_DEVICE_TYPE + "='" + vDeviceType+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_TotalFaultAoneSs, mAOneSSResponse.getTotalFault());
        values.put(DEVICE_FaultAoneSs, mAOneSSResponse.getTotalFault());
        values.put(DEVICE_AoneSs_DEVICE_TYPE, vDeviceType);

        values.put(DEVICE_TDMFlowNameAoneSs, mAOneSSResponse.getTDMFlowName());
        values.put(DEVICE_TDMFlowUnitAoneSs, mAOneSSResponse.getTDMFlowUnit());
        values.put(DEVICE_TDMFlowValueAoneSs, mAOneSSResponse.getTDMFlowValue());


        values.put(DEVICE_TDMEnergyNameAoneSs, mAOneSSResponse.getTDMEnergyName());
        values.put(DEVICE_TDMEnergyUnitAoneSs, mAOneSSResponse.getTDMEnergyUnit());
        values.put(DEVICE_TDMEnergyValueAoneSs, mAOneSSResponse.getTDMEnergyValue());

        values.put(DEVICE_TOTMFlowNameAoneSs, mAOneSSResponse.getTOTMFlowName());
        values.put(DEVICE_TOTMFlowUnitAoneSs, mAOneSSResponse.getTOTMFlowUnit());
        values.put(DEVICE_TOTMFlowValueAoneSs, mAOneSSResponse.getTOTMFlowValue());


        values.put(DEVICE_TOTMEnergyNameAoneSs, mAOneSSResponse.getTOTMEnergyName());
        values.put(DEVICE_TOTMEnergyUnitAoneSs, mAOneSSResponse.getTOTMEnergyUnit());
        values.put(DEVICE_TOTMEnergyValueAoneSs, mAOneSSResponse.getTOTMEnergyValue());

        values.put(DEVICE_AoneSs_TEST1, "one");
        values.put(DEVICE_AoneSs_TEST2, "Two");
        values.put(DEVICE_AoneSs_TEST3, "Three");

        //insert row in table
        long insert = db.insert(DEVICE_AONESS_NAME, null, values);
        return insert;
    }

    public AOneSSResponse getDeviceA1ssDATA(String DeviceType) {
        mAOneSSResponse = new AOneSSResponse();
        // mViechiDataResponse = new ViechiDataResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String TotalFault, Fault, TDMFlowName, TDMFlowUnit, TDMFlowValue, TDMRHrName, TDMRHrUnit, TDMRHrValue,
                TDPVEnergyName, TDPVEnergyUnit, TDPVEnergyValue, TOTMFlowName, TOTMFlowUnit, TOTMFlowValue,
                TOTMRHrName, TOTMRHrUnit, TOTMRHrValue, TOTPVEnergyName, TOTPVEnergyUnit, TOTPVEnergyValue,
                TestOne, TestTwo, TestThree;


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_AONESS_NAME + " where " + DEVICE_AoneSs_DEVICE_TYPE + "=" + DeviceType, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));

                        TotalFault = cursor.getString(cursor.getColumnIndex(DEVICE_TotalFaultAoneSs));
                        ;
                        Fault = cursor.getString(cursor.getColumnIndex(DEVICE_FaultAoneSs));

                        TOTMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowNameAoneSs));
                        TOTMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowValueAoneSs));
                        TOTMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMFlowUnitAoneSs));
                        mAOneSSResponse.setTOTMFlowName(TOTMFlowName);
                        mAOneSSResponse.setTOTMFlowValue(TOTMFlowValue);
                        mAOneSSResponse.setTOTMFlowUnit(TOTMFlowUnit);

                        TDMFlowName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowNameAoneSs));//DEVICE_TDMFlowNameKLP
                        TDMFlowValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowValueAoneSs));
                        TDMFlowUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMFlowUnitAoneSs));
                        mAOneSSResponse.setTDMFlowName(TDMFlowName);
                        mAOneSSResponse.setTDMFlowValue(TDMFlowValue);
                        mAOneSSResponse.setTDMFlowUnit(TDMFlowUnit);


                        TDPVEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyNameAoneSs));
                        TDPVEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyUnitAoneSs));
                        TDPVEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TDMEnergyValueAoneSs));
                        mAOneSSResponse.setTDMEnergyName(TDPVEnergyName);
                        mAOneSSResponse.setTDMEnergyUnit(TDPVEnergyUnit);
                        mAOneSSResponse.setTDMEnergyValue(TDPVEnergyValue);


                        TOTPVEnergyName = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyNameAoneSs));
                        TOTPVEnergyUnit = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyUnitAoneSs));
                        TOTPVEnergyValue = cursor.getString(cursor.getColumnIndex(DEVICE_TOTMEnergyValueAoneSs));
                        mAOneSSResponse.setTOTMEnergyName(TOTPVEnergyName);
                        mAOneSSResponse.setTOTMEnergyUnit(TOTPVEnergyUnit);
                        mAOneSSResponse.setTOTMEnergyValue(TOTPVEnergyValue);

                        //TestOne;
                        // TestTwo;
                        // Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mAOneSSResponse;
    }


    ///////////////////////////////start stop command  data

    private static final String DEVICE_sno = "sno";
    private static final String DEVICE_buttonText = "buttonText";
    private static final String DEVICE_address = "address";
    private static final String DEVICE_BT_address = "btaddress";
    private static final String DEVICE_offset = "offset";
   // private static final String DEVICE_deviceType = "deviceType";
    private static final String DEVICE_deviceType = "DeviceType";
    private static final String DEVICE_data = "data";
    private static final String DEVICE_bColor = "bColor";
    private static final String DEVICE_cmdMsg = "cmdMsg";
    private static final String DEVICE_oldData = "oldData";
    private static final String DEVICE_START_STOP_TEST1 = "TestOne";
    private static final String DEVICE_START_STOP_TEST2 = "TestTwo";
    private static final String DEVICE_START_STOP_TEST3 = "TestThree";

    private static final String DEVICE_START_STOP_NAME = "devicestartstop";////////////////table name

    private static final String CREATE_TABLE_START_STOP = "CREATE TABLE "
            + DEVICE_START_STOP_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_sno + " VARCHAR, " +
            DEVICE_buttonText + " VARCHAR, " +
            DEVICE_deviceType + " TEXT NOT NULL, " +
            DEVICE_offset + " VARCHAR, " +
            DEVICE_address + " VARCHAR, " +
            DEVICE_BT_address + " VARCHAR, " +
            DEVICE_data + " VARCHAR, " +
            DEVICE_bColor + " VARCHAR, " +
            DEVICE_cmdMsg + " VARCHAR, " +
            DEVICE_oldData + " VARCHAR, " +
            DEVICE_START_STOP_TEST1 + " VARCHAR, " +
            DEVICE_START_STOP_TEST2 + " VARCHAR, " +
            DEVICE_START_STOP_TEST3 + " VARCHAR " +
            "); ";


    public void deleteStart_StopDATA(String vDeviceNo) {
        int hh;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete  from " + DEVICE_START_STOP_NAME + " where " + DEVICE_deviceType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AllDeviceDdelete(String mTableName, String mDeviceType) {
        int hh;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

                db.execSQL("delete  from " + mTableName + " where " + DEVICE_deviceType + "=" + mDeviceType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public long insertDeviceSTART_STOPData(String vMUserId, String vMDeviceId, String vDeviceNo, String vDeviceType, List<DynamicBTNResponse> mDynamicBTNResponse, int mPosition, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

       /* try {
            db.execSQL("delete  from " + DEVICE_START_STOP_NAME + " where " + DEVICE_deviceType + "=" + vDeviceType);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_sno, mDynamicBTNResponse.get(mPosition).getSno());
        values.put(DEVICE_buttonText, mDynamicBTNResponse.get(mPosition).getButtonText());
        values.put(DEVICE_deviceType, vDeviceType);

        values.put(DEVICE_offset, mDynamicBTNResponse.get(mPosition).getOffset());
        values.put(DEVICE_address, mDynamicBTNResponse.get(mPosition).getAddress());
        values.put(DEVICE_BT_address, mDynamicBTNResponse.get(mPosition).getBTAddress());
        values.put(DEVICE_data, mDynamicBTNResponse.get(mPosition).getData());

        values.put(DEVICE_bColor, mDynamicBTNResponse.get(mPosition).getBColor());
        values.put(DEVICE_cmdMsg, mDynamicBTNResponse.get(mPosition).getCmdMsg());
        values.put(DEVICE_oldData, mDynamicBTNResponse.get(mPosition).getOldData());
        values.put(DEVICE_START_STOP_TEST1, "one");
        values.put(DEVICE_START_STOP_TEST2, "two");
        values.put(DEVICE_START_STOP_TEST3, "three");
        //insert row in table
        long insert = db.insert(DEVICE_START_STOP_NAME, null, values);
        return insert;
    }

    public List<DynamicBTNResponse> getDeviceSTART_STOPDATA(String DeviceType) {


        List<DynamicBTNResponse> mDynamicBTNResponseList = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0
        String bColor, cmdMsg, oldData;
        String sno, buttonText, address,btaddress, offset, deviceType, data, TestOne, TestTwo, TestThree;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_START_STOP_NAME + " where " + DEVICE_deviceType + "='" + DeviceType+"'", null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (ccccc > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {

                        mDynamicBTNResponse = new DynamicBTNResponse();
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        // mKLPTotEnergyResponse = new KLPTotEnergyResponse();
                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        sno = cursor.getString(cursor.getColumnIndex(DEVICE_sno));
                        buttonText = cursor.getString(cursor.getColumnIndex(DEVICE_buttonText));
                        address = cursor.getString(cursor.getColumnIndex(DEVICE_address));
                        btaddress = cursor.getString(cursor.getColumnIndex(DEVICE_BT_address));
                        offset = cursor.getString(cursor.getColumnIndex(DEVICE_offset));
                        deviceType = cursor.getString(cursor.getColumnIndex(DEVICE_deviceType));
                        data = cursor.getString(cursor.getColumnIndex(DEVICE_data));
                        bColor = cursor.getString(cursor.getColumnIndex(DEVICE_bColor));
                        cmdMsg = cursor.getString(cursor.getColumnIndex(DEVICE_cmdMsg));
                        oldData = cursor.getString(cursor.getColumnIndex(DEVICE_oldData));

                        mDynamicBTNResponse.setSno(sno);
                        mDynamicBTNResponse.setButtonText(buttonText);
                        mDynamicBTNResponse.setAddress(address);
                        mDynamicBTNResponse.setOffset(offset);
                        mDynamicBTNResponse.setDeviceType(deviceType);
                        mDynamicBTNResponse.setData(data);
                        mDynamicBTNResponse.setBColor(bColor);
                        mDynamicBTNResponse.setCmdMsg(cmdMsg);
                        mDynamicBTNResponse.setCmdMsg(oldData);
                        mDynamicBTNResponse.setBTAddress(btaddress);

                        mDynamicBTNResponseList.add(mDynamicBTNResponse);
                        //TestOne;
                        //   TestTwo;
                        //  Three;
                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mDynamicBTNResponseList;
    }

    ////////////////////////////real monitoring

    private ArrayList<RealMonitoring> mRealMonitoringList;
    private RealMonitoring mRealMonitoring;

    /////////////////////////////device parameter
    private static final String DEVICE_REAL_PARA_MPINDEX = "MPIndexReal";
    private static final String DEVICE_REAL_PARA_MPNAME = "MPNameReal";

    private static final String DEVICE_REAL_PARA_UNIT = "UnitReal";
    private static final String DEVICE_REAL_PARA_VALUE = "ValueReal";
    private static final String DEVICE_REAL_PARA_DEVICE_TYPE = "MPDeviceTypeReal";

    private static final String DEVICE_REAL_PARA_DEVICE_TEST1 = "RealTest1";
    private static final String DEVICE_REAL_PARA_DEVICE_TEST2 = "RealTest2";
    private static final String DEVICE_REAL_PARA_DEVICE_TEST3 = "RealTest3";
    private static final String DEVICE_REAL_PARA_DEVICE_TEST4 = "RealTest4";

    private static final String DEVICE_REAL_PARAMETER_TABLE_NAME = "deviceparameterRealTimelist";////////////////table name

    private static final String CREATE_TABLE_DEVICE_REAL_PARAMETER = "CREATE TABLE "
            + DEVICE_REAL_PARAMETER_TABLE_NAME + "(" + LOGIN_KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_REAL_PARA_MPINDEX + " TEXT NOT NULL, " +
            DEVICE_REAL_PARA_MPNAME + " TEXT NOT NULL, " +
            DEVICE_REAL_PARA_UNIT + " TEXT NOT NULL, " +
            DEVICE_REAL_PARA_VALUE + " VARCHAR, " +
            DEVICE_REAL_PARA_DEVICE_TYPE + " TEXT NOT NULL, " +
            DEVICE_REAL_PARA_DEVICE_TEST1 + " VARCHAR, " +
            DEVICE_REAL_PARA_DEVICE_TEST2 + " VARCHAR, " +
            DEVICE_REAL_PARA_DEVICE_TEST3 + " VARCHAR, " +
            DEVICE_REAL_PARA_DEVICE_TEST4 + " VARCHAR " +
            "); ";


    public DatabaseHelperTeacher(Context context) {
        super(context, DATABASE_NAME_RMS, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEACHERS);
        db.execSQL(CREATE_TABLE_DEVICE_LIST);
        db.execSQL(CREATE_TABLE_USPC_OPTION_LIST_DEVICE);
        db.execSQL(CREATE_TABLE_PLAN_LIST);
        db.execSQL(CREATE_TABLE_DEVICE_PARAMETER);
        db.execSQL(CREATE_TABLE_DEVICE_REAL_PARAMETER);
        db.execSQL(CREATE_TABLE_DEVICE_STATUS);
        db.execSQL(CREATE_TABLE_KLP);
        db.execSQL(CREATE_TABLE_SHIMA);
        db.execSQL(CREATE_TABLE_USPC);
        db.execSQL(CREATE_TABLE_DATALOger);
        db.execSQL(CREATE_TABLE_START_STOP);
        db.execSQL(CREATE_TABLE_AONESS);
        db.execSQL(CREATE_TABLE_Nikola);
        db.execSQL(CREATE_TABLE_OldKLP);
        db.execSQL(CREATE_TABLE_KLPGRID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_TEACHERS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_DEVICE_LIST + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_USPC_OPTION_LIST_DEVICE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_PLAN_LIST + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_DEVICE_PARAMETER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_DEVICE_REAL_PARAMETER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_DEVICE_STATUS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_KLP + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_SHIMA + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_USPC + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_DATALOger + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_START_STOP + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_AONESS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_Nikola + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_OldKLP + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_KLPGRID + "'");
        //onCreate(db);
    }

    public long insertLoginData(String userid, String parentid, String username, String password, String phone, String clientid, String islogin, String status, String active) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(LOGIN_USER_ID, userid);
        values.put(LOGIN_PARENT_ID, parentid);
        values.put(LOGIN_USER_NAME, username);
        values.put(LOGIN_USER_PASSWORD, password);
        values.put(LOGIN_MOBILE_NO, phone);
        values.put(LOGIN_CLIENT_ID, clientid);
        values.put(IS_LOGIN, islogin);///7
        values.put(LOGIN_STATUS, status);
        values.put(LOGIN_ACTIVE, active);
      /*  values.put(LOGIN_TEST1, active);
        values.put(LOGIN_TEST2, active);
        values.put(LOGIN_TEST3, active);
        values.put(LOGIN_TEST4, active);*/
        //insert row in table
        long insert = db.insert(TABLE_USER_LOGIN, null, values);
        return insert;
    }

    public long updateLoginData(String userid, String parentid, String username, String password, String phone, String clientid, String islogin, String status, String active, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(LOGIN_USER_ID, userid);
        values.put(LOGIN_PARENT_ID, parentid);
        values.put(LOGIN_USER_NAME, username);
        values.put(LOGIN_USER_PASSWORD, password);
        values.put(LOGIN_MOBILE_NO, phone);
        values.put(LOGIN_CLIENT_ID, clientid);
        values.put(IS_LOGIN, islogin);///7
        values.put(LOGIN_STATUS, status);
        values.put(LOGIN_ACTIVE, active);
       /* values.put(LOGIN_TEST1, active);
        values.put(LOGIN_TEST2, active);
        values.put(LOGIN_TEST3, active);
        values.put(LOGIN_TEST4, active);*/
        //insert row in table
        // long insert = db.insert(TABLE_USER_LOGIN, null, values);
        return db.update(TABLE_USER_LOGIN, values, KEY_ID + " = ?", new String[]{id});

        // return insert;
    }

    public String checkUserLoginData(String username, String password) {
        String mAllUserInfo = null;
        // int id=-1; //0
        String id = ""; //0
        String mUserID = ""; //1
        String mParentID = "";//2
        String mUserName = "";//3
        String mUserPhone = "";//5
        String mClientID = "";///6
        String mISLogin = "";///7
        String mLoginStatus = "";///8
        String mLoginActive = "";///9

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userlogin WHERE " + LOGIN_USER_NAME + "=? AND " + LOGIN_USER_PASSWORD + "=?", new String[]{username, password});
        int ccccc = cursor.getCount();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            // id=cursor.getInt(0);
            id = cursor.getString(0);
            mUserID = cursor.getString(1);
            mParentID = cursor.getString(2);
            mUserName = cursor.getString(3);
            mUserPhone = cursor.getString(5);
            mClientID = cursor.getString(6);
            mISLogin = cursor.getString(7);
            mLoginStatus = cursor.getString(8);
            mLoginActive = cursor.getString(9);
            cursor.close();
        }

        mAllUserInfo = id + "SAK000IVS" + mUserID + "SAK000IVS" + mParentID + "SAK000IVS" + mUserName + "SAK000IVS" + mUserPhone + "SAK000IVS" + mClientID + "SAK000IVS" + mISLogin + "SAK000IVS" + mLoginStatus + "SAK000IVS" + mLoginActive;

        return mAllUserInfo;
    }

    public String checkUserAlreadyExist(String username, String password) {
        String mAllUserInfo = null;

        String id = ""; //0

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userlogin WHERE " + LOGIN_USER_NAME + "=? AND " + LOGIN_USER_PASSWORD + "=?", new String[]{username, password});
        int ccccc = cursor.getCount();
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            // id=cursor.getInt(0);
            id = cursor.getString(0);

            cursor.close();
            mAllUserInfo = id + "DHARSHANSIR000DHARSHANSIRUser already exist";
        } else {
            mAllUserInfo = id + "DHARSHANSIR111DHARSHANSIRUser not exist";
        }


        return mAllUserInfo;
    }

    ///*************************This is for device list ***************************/////
    public ArrayList<Customer_GPS_Search> getDeviceListData() {
        Customer_GPS_SearchList = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;
        // int id=-1; //0
        String id = ""; //0
        String mCustomer_name = ""; //1
        String mDeviceNo = "";//2
        String mDeviceType = "";//3
        String mMDeviceId = "";//5
        String mMUserId = "";///6
        String mMobile = "";///7
        String mTypeName = "";///7
        String mModelType = "";///7
        String mPumpStatus = "";///7
        String mISlogin = "";///7
        int mDeviceStatus ;///7


        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_LIST_TABLE_NAME, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        customer_gps = new Customer_GPS_Search();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        mCustomer_name = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_NAME));
                        //  Customer_GPS_SearchList.set(PU,mCustomer_name);
                        mDeviceNo = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_NO));
                        //  Customer_GPS_SearchList.set()
                        mDeviceType = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_DTYPE));
                        mMDeviceId = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_MDID));
                        mMUserId = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_MUSERID));
                        mMobile = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_MOBILE));
                        mTypeName = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_TYPENAME));
                        mModelType = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_MODELTYPE));
                        mPumpStatus = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_PUMPSTATUS));
                        mISlogin = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_ISLOGIN));
                        mDeviceStatus = cursor.getInt(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_DEVICESTATUS));
                        // mTypeName = cursor.getString(cursor.getColumnIndex(DEVICE_LIST_CUSTOMER_TEST4));

                        customer_gps.setCustomer_name(mCustomer_name);
                        customer_gps.setDeviceNo(mDeviceNo);
                        customer_gps.setDeviceType(mDeviceType);
                        customer_gps.setMDeviceId(mMDeviceId);
                        customer_gps.setMUserId(mMUserId);
                        customer_gps.setMobile(mMobile);
                        customer_gps.setTypeName(mTypeName);
                        customer_gps.setModelType(mModelType);
                        customer_gps.setPumpStatus(mPumpStatus);
                        customer_gps.setIsLogin(mISlogin);
                        customer_gps.setDeviceStatus(mDeviceStatus);

                        Customer_GPS_SearchList.add(customer_gps);

                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            //  db.endTransaction();
            // End the transaction.
            if (db != null && db.inTransaction()) {
                //  db.endTransaction();
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        //  return mAllUserInfo;
        return Customer_GPS_SearchList;
    }



    ///////////////////////////////////////////////////////
    public boolean deleteDeviceROWData(String MDeviceId, String MUserId) {

        SQLiteDatabase db = this.getWritableDatabase();

            //db.execSQL("TRUNCATE table" + DEVICE_LIST_TABLE_NAME);
        db.execSQL("delete from " + DEVICE_LIST_TABLE_NAME +" WHERE "+DEVICE_LIST_CUSTOMER_MDID+"="+MDeviceId+" and "+DEVICE_LIST_CUSTOMER_MUSERID+"="+MUserId);

        //insert row in table

        return true;
    }


    public long insertDeviceListData(String customername, String DeviceNo, String DeviceType, String MDeviceId, String MUserId, String Mobile, String TypeNAme, String ModelType, String PumpStaus, String IsLogin, int DeviceStatus, boolean mCheckFirst) {
  //  public long insertDeviceListData(String customername, String DeviceNo, String DeviceType, String MDeviceId, String MUserId, String Mobile, String TypeNAme, String ModelType, String PumpStaus, String IsLogin, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (mCheckFirst) {
            //db.execSQL("TRUNCATE table" + DEVICE_LIST_TABLE_NAME);
            db.execSQL("delete from " + DEVICE_LIST_TABLE_NAME);
            mCheckFirst = false;
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_LIST_CUSTOMER_NAME, customername);
        values.put(DEVICE_LIST_CUSTOMER_NO, DeviceNo);
        values.put(DEVICE_LIST_CUSTOMER_DTYPE, DeviceType);
        values.put(DEVICE_LIST_CUSTOMER_MDID, MDeviceId);
        values.put(DEVICE_LIST_CUSTOMER_MUSERID, MUserId);
        values.put(DEVICE_LIST_CUSTOMER_MOBILE, Mobile);
        values.put(DEVICE_LIST_CUSTOMER_TYPENAME, TypeNAme);
        values.put(DEVICE_LIST_CUSTOMER_MODELTYPE, ModelType);
        values.put(DEVICE_LIST_CUSTOMER_PUMPSTATUS, PumpStaus);
        values.put(DEVICE_LIST_CUSTOMER_ISLOGIN, IsLogin);
        values.put(DEVICE_LIST_CUSTOMER_DEVICESTATUS, DeviceStatus);
        values.put(DEVICE_LIST_CUSTOMER_TEST1, "Vikas");
        values.put(DEVICE_LIST_CUSTOMER_TEST2, "Vikas");
        values.put(DEVICE_LIST_CUSTOMER_TEST3, "Vikas");
        values.put(DEVICE_LIST_CUSTOMER_TEST4, "Ganesh");

        //insert row in table
        long insert = db.insert(DEVICE_LIST_TABLE_NAME, null, values);

        return insert;
    }

    ////////////////////////////////insert Device status data

    public long insertDeviceStatusListData(String stMode, String stName, String stColor, String stDeviceType, String testOne, String testTwo, String testThree, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        if (mCheckFirst) {//db.execSQL("TRUNCATE table" + DEVICE_LIST_TABLE_NAME);
            try {
                db.execSQL("delete  from " + DEVICE_STATUS_TABLE_NAME + " where " + DEVICE_STATUS_DEVICE_TYPE + "='" + stDeviceType+"'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_STATUS_MODE, stMode);
        values.put(DEVICE_STATUS_NAME, stName);
        values.put(DEVICE_STATUS_COLOR, stColor);
        values.put(DEVICE_STATUS_DEVICE_TYPE, stDeviceType);
        values.put(DEVICE_STATUS_TEST1, testOne);
        values.put(DEVICE_STATUS_TEST2, testTwo);
        values.put(DEVICE_STATUS_TEST3, testThree);

        /*private static final String DEVICE_STATUS_MODE = "SMode";
    private static final String DEVICE_STATUS_NAME = "SName";
    private static final String DEVICE_STATUS_COLOR = "SColor";
    private static final String DEVICE_STATUS_DEVICE_TYPE = "DeviceType";
    private static final String DEVICE_STATUS_TEST1 = "TestOne";
    private static final String DEVICE_STATUS_TEST2 = "TestTwo";
    private static final String DEVICE_STATUS_TEST3 = "TestThree";*/

        //insert row in table
        long insert = db.insert(DEVICE_STATUS_TABLE_NAME, null, values);
        return insert;
    }


    public ArrayList<ProductStatusResponse> getDeviceStatusListData11(String DeviceType) {
        mProductStatusResponseList = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String stCode, stName, stColor, stDeviceType, stTestOne, stTestTwo, stTestThree;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_STATUS_TABLE_NAME + " where " + DEVICE_STATUS_DEVICE_TYPE + "='" + DeviceType+"'", null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        mProductStatusResponse = new ProductStatusResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        stCode = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_MODE));
                        //  Customer_GPS_SearchList.set(PU,mCustomer_name);
                        stName = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_NAME));
                        //  Customer_GPS_SearchList.set()
                        stColor = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_COLOR));
                        stDeviceType = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_DEVICE_TYPE));
                        stTestOne = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_TEST1));
                        stTestTwo = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_TEST2));
                        stTestThree = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_TEST3));


                        mProductStatusResponse.setSMode(stCode);
                        mProductStatusResponse.setSName(stName);
                        mProductStatusResponse.setSColor(stColor);
                        mProductStatusResponse.setDeviceType(stDeviceType);

                        mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mProductStatusResponseList;
    }

    public ProductStatusResponse getDeviceStatusListData(String DeviceType, String stMode) {
        mProductStatusResponse = new ProductStatusResponse();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;
        int ssTmode = 0;
        try {

            if (!stMode.equalsIgnoreCase("null")) {
                ssTmode = (int) Float.parseFloat(stMode);
            } else {
                ssTmode = 0;
            }


//            ssTmode = (int) ssTmode;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // int id=-1; //0
        String id = ""; //0

        String stCode, stName, stColor, stDeviceType, stTestOne, stTestTwo, stTestThree;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_STATUS_TABLE_NAME + " where " + DEVICE_STATUS_DEVICE_TYPE + "='" + DeviceType + "' and " + DEVICE_STATUS_MODE + "=" + ssTmode, null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        mProductStatusResponse = new ProductStatusResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        stCode = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_MODE));
                        //  Customer_GPS_SearchList.set(PU,mCustomer_name);
                        stName = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_NAME));
                        //  Customer_GPS_SearchList.set()
                        stColor = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_COLOR));
                        stDeviceType = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_DEVICE_TYPE));
                        stTestOne = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_TEST1));
                        stTestTwo = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_TEST2));
                        stTestThree = cursor.getString(cursor.getColumnIndex(DEVICE_STATUS_TEST3));

                        mProductStatusResponse.setSMode(stCode);
                        mProductStatusResponse.setSName(stName);
                        mProductStatusResponse.setSColor(stColor);
                        mProductStatusResponse.setDeviceType(stDeviceType);

                        // mProductStatusResponseList.add(mProductStatusResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mProductStatusResponse;
    }

///////////////para meter name list

    public long insertDeviceParameterListData(String Address, String Divisible, String MDeviceNo, String MPId, String MPIndex, String MPName, String Status, String Unit, String PMin, String PMax, String MODAddress, String DeviceTyape, String Offset, boolean mCheckFirst) {

        SQLiteDatabase db = this.getWritableDatabase();

        if (mCheckFirst) {//db.execSQL("TRUNCATE table" + DEVICE_LIST_TABLE_NAME);
            try {
                db.execSQL("delete  from " + DEVICE_PARAMETER_TABLE_NAME + " where " + DEVICE_PARA_DEVICE_TYPE + "=" + DeviceTyape);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_PARA_ADDRESS, Address);
        values.put(DEVICE_PARA_DIVISIBLE, Divisible);
        values.put(DEVICE_PARA_DEVICENO, MDeviceNo);
        values.put(DEVICE_PARA_MPID, MPId);
        values.put(DEVICE_PARA_MPINDEX, MPIndex);
        values.put(DEVICE_PARA_MPNAME, MPName);
        values.put(DEVICE_PARA_STATUS, Status);
        values.put(DEVICE_PARA_UNIT, Unit);
        values.put(DEVICE_PARA_PMIN, PMin);
        values.put(DEVICE_PARA_PMAX, PMax);
        values.put(DEVICE_PARA_MODADDRESS, MODAddress);
        values.put(DEVICE_PARA_DEVICE_TYPE, DeviceTyape);
        values.put(DEVICE_PARA_DEVICE_TEST1, Offset);
        System.out.println("Offset_insert="+Offset);
        //values.put(DEVICE_PARA_DEVICE_TEST1, DeviceTyape);
        values.put(DEVICE_PARA_DEVICE_TEST2, DeviceTyape);
        values.put(DEVICE_PARA_DEVICE_TEST3, DeviceTyape);
        values.put(DEVICE_PARA_DEVICE_TEST4, DeviceTyape);

        //insert row in table
        long insert = db.insert(DEVICE_PARAMETER_TABLE_NAME, null, values);
        return insert;
    }

    public ArrayList<SettingModelResponse> getDevicePARAMeterListData(String DeviceType) {
        mSettingModelResponseList = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;

        // int id=-1; //0
        String id = ""; //0

        String Address, Divisible, MDeviceNo, MPId, MPIndex, MPName, Status, Unit, PMin, PMax, MODAddress, Offset, deviceType;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME + " where " + DEVICE_PARA_DEVICE_TYPE + "='" + DeviceType+"'", null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        //cursor.moveToFirst();
                        // id=cursor.getInt(0);
                        mSettingModelResponse = new SettingModelResponse();

                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        Address = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_ADDRESS));
                        //  Customer_GPS_SearchList.set(PU,mCustomer_name);
                        Divisible = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_DIVISIBLE));
                        //  Customer_GPS_SearchList.set()
                        MDeviceNo = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_DEVICENO));
                        MPId = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_MPID));
                        MPIndex = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_MPINDEX));
                        MPName = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_MPNAME));
                        Status = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_STATUS));
                        Unit = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_UNIT));
                        PMin = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_PMIN));
                        PMax = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_PMAX));
                        MODAddress = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_MODADDRESS));
                        deviceType = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_DEVICE_TYPE));
                        Offset = cursor.getString(cursor.getColumnIndex(DEVICE_PARA_DEVICE_TEST1));

                        System.out.println("Offset_get=="+Offset);
                        mSettingModelResponse.setAddress(Address);
                      //  mSettingModelResponse.setDivisible(Long.parseLong(Divisible));
                        mSettingModelResponse.setDivisible(Divisible);
                        mSettingModelResponse.setMDeviceNo(MDeviceNo);
                       // mSettingModelResponse.setMDeviceNo(Long.parseLong(MDeviceNo));
                        mSettingModelResponse.setMPId(MPId);
                      //  mSettingModelResponse.setMPIndex(Long.parseLong(MPIndex));
                        mSettingModelResponse.setMPIndex(MPIndex);
                        mSettingModelResponse.setMPName(MPName);
                        mSettingModelResponse.setStatus(Status);
                        mSettingModelResponse.setUnit(Unit);
                        mSettingModelResponse.setPMin(Integer.parseInt(PMin));
                        mSettingModelResponse.setPMax(Integer.parseInt(PMax));
                        mSettingModelResponse.setMPName(MPName);
                        mSettingModelResponse.setMobBTAddress(MODAddress);

                        try {
                            mSettingModelResponse.setOffset(Integer.parseInt(Offset));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        mSettingModelResponseList.add(mSettingModelResponse);
                        cursor.moveToNext();
                    }
                }

            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            if (db != null && db.inTransaction()) {
                db.close();
            }
            // Close database
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        return mSettingModelResponseList;
    }

    /////////////////////////Real monitoring data
    ///////////////////////////////////////////////////////

    public long insertDeviceRealMonitoringParameterListData(String MPIndexReal, String MPNameReal, String MPUnitReal, String MPValueReal, String MPDeviceTypeReal, float divisiblefactor, boolean mCheckFirstReal) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (mCheckFirstReal) {
            try {
                db.execSQL("delete  from " + DEVICE_REAL_PARAMETER_TABLE_NAME + " where " + DEVICE_REAL_PARA_DEVICE_TYPE + "='" + MPDeviceTypeReal+"'");
            } catch (SQLException e) {
                System.out.println("eeeeee==>> " + e);

                e.printStackTrace();

            }
        }

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(DEVICE_REAL_PARA_MPINDEX, MPIndexReal);
        values.put(DEVICE_REAL_PARA_MPNAME, MPNameReal);
        values.put(DEVICE_REAL_PARA_UNIT, MPUnitReal);
        values.put(DEVICE_REAL_PARA_VALUE, MPValueReal);
        values.put(DEVICE_REAL_PARA_DEVICE_TYPE, MPDeviceTypeReal);
        values.put(DEVICE_REAL_PARA_DEVICE_TEST1, divisiblefactor);
        //insert row in table
        //   long insert = db.insert(DEVICE_LIST_TABLE_NAME, null, values);
        long insert = db.insert(DEVICE_REAL_PARAMETER_TABLE_NAME, null, values);

        return insert;
    }

    public ArrayList<RealMonitoring> getDevicePARAMeterRealTimeListData(String DeviceTypeReal) {
        mRealMonitoringList = new ArrayList<>();
        //customer_gps = new Customer_GPS_Search();
        String mAllUserInfo = null;
        // int id=-1; //0
        String id = ""; //0

        String MPIndexReal,
                MPNameReal,
                UnitReal,
                ValueReal,
                Divisible,
                DebviceTypeReal;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            // Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_PARAMETER_TABLE_NAME +" where "+DEVICE_PARA_DEVICE_TYPE+"=?", new String[]{ DeviceType });
            //Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_REAL_PARAMETER_TABLE_NAME + " where " + DEVICE_REAL_PARA_DEVICE_TYPE + "=" + DeviceTypeReal, null);
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEVICE_REAL_PARAMETER_TABLE_NAME + " where " + DEVICE_REAL_PARA_DEVICE_TYPE + "='" + DeviceTypeReal+"'", null);
            int ccccc = cursor.getCount();
            int PU = 0;
            if (ccccc > 0) {
                if (cursor.moveToFirst()) {
                    //  for(PU = 0 ; PU < ccccc ; PU++)
                    while (!cursor.isAfterLast()) {
                        mRealMonitoring = new RealMonitoring();
                        id = cursor.getString(cursor.getColumnIndex(LOGIN_KEY_ID));
                        MPIndexReal = cursor.getString(cursor.getColumnIndex(DEVICE_REAL_PARA_MPINDEX));
                        //  Customer_GPS_SearchList.set(PU,mCustomer_name);
                        MPNameReal = cursor.getString(cursor.getColumnIndex(DEVICE_REAL_PARA_MPNAME));
                        //  Customer_GPS_SearchList.set()
                        UnitReal = cursor.getString(cursor.getColumnIndex(DEVICE_REAL_PARA_UNIT));
                        ValueReal = cursor.getString(cursor.getColumnIndex(DEVICE_REAL_PARA_VALUE));
                        Divisible = cursor.getString(cursor.getColumnIndex(DEVICE_REAL_PARA_DEVICE_TEST1));
                        mRealMonitoring.setValue(ValueReal);
                        mRealMonitoring.setUnit(UnitReal);
                        mRealMonitoring.setIndex(Integer.parseInt(MPIndexReal));
                        mRealMonitoring.setDivisible(Float.parseFloat(Divisible));
                        mRealMonitoring.setMPName(MPNameReal);
                        mRealMonitoringList.add(mRealMonitoring);
                        cursor.moveToNext();
                    }
                }
            }////////////addd chaech
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            //  db.endTransaction();
            // End the transaction.
            if (db != null && db.inTransaction()) {

                db.close();
            }
        }
        //mAllUserInfo = id+"SAK000IVS"+mUserID+"SAK000IVS"+mParentID+"SAK000IVS"+mUserName+"SAK000IVS"+mUserPhone+"SAK000IVS"+mClientID+"SAK000IVS"+mISLogin+"SAK000IVS"+mLoginStatus+"SAK000IVS"+mLoginActive;
        //  return mAllUserInfo;
        return mRealMonitoringList;
    }
}
