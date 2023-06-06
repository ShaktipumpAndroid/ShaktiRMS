package com.shaktipumps.shakti_rms.webservice;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.model.ForgotOTPModel.ForgotPassModelView;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;
import com.shaktipumps.shakti_rms.retrofit.RequestReciever;

import java.io.File;

public class GlobalMethod {

    private Context mContext;
    private Activity mActivity;

    private String mUserID;

    BaseRequest baseRequest;

    public static File commonDocumentDirPathphoto(String FolderName)
    {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + FolderName);
            System.out.println("dir_ in  = "+dir);
        }
        else
        {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + FolderName);
            System.out.println("dir_ out  = "+dir);
        }

        // Make sure the path directory exists.
        if (!dir.exists())
        {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success)
            {
                dir = null;
            }
        }
        return dir;
    }

    public static String commonDocumentDirPathphotoPath()
    {
        String dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            System.out.println("dir_ in  = "+dir);
        }
        else
        {
            dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            System.out.println("dir_ out  = "+dir);
        }

        // Make sure the path directory exists.

        return dir;
    }

    public static File commonDocumentDirPath(String FolderName)
    {
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) , FolderName);
            System.out.println("dir_ in  = "+dir);

          //  File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FOLDER_NAME);
        }
        else
        {
            dir = new File(Environment.getExternalStorageDirectory() , FolderName);
            System.out.println("dir_ out  = "+dir);
        }

        // Make sure the path directory exists.
        if (!dir.exists())
        {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success)
            {
                dir = null;
            }
        }
        return dir;
    }


    public void callInsertAndUpdateAPI(String m_androidId,String UserID,String mDeviceNumber, String mFCMToken) {
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int APINumber, String Json, Object obj) {
                //  JSONArray arr = (JSONArray) obj;
                try {
                    Gson gson = new Gson();
                    //////////////add model class here
                    ForgotPassModelView mForgotPassModelView = gson.fromJson(Json, ForgotPassModelView.class);
                    if (mForgotPassModelView.getStatus()) {
                        Toast.makeText(mContext, mForgotPassModelView.getMessage(), Toast.LENGTH_SHORT).show();
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
            jsonObject.addProperty("userId", UserID);
            jsonObject.addProperty("deviceNo", mDeviceNumber);
            jsonObject.addProperty("fcmToken", mFCMToken);
            jsonObject.addProperty("imei", m_androidId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //  baseRequest.callAPIPost(1, jsonObject, Constant.GET_ALL_NOTIFICATION_LIST_API);/////
        baseRequest.callAPIPost(1, jsonObject, NewSolarVFD.INSERTUPDATE_FCM_API);/////
        //baseRequest.callAPIPut(1, jsonObject, NewSolarVFD.ORG_RESET_FORGOTPASS);/////
    }

}
