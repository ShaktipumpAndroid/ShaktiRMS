package com.shaktipumps.shakti_rms.retrofit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRequest extends BaseRequestParser {
    private Context mContext;
    private ApiInterface apiInterface;
    private ApiInterface apiInterface1;
    private RequestReciever requestReciever;
    private boolean runInBackground = false;
    private Dialog dialog;
    private   ProgressDialog progress;
    private View loaderView = null;
    private int APINumber_ = 1;

    public BaseRequest(Context context) {
        mContext = context;
        apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        apiInterface1 =
                ApiClient.getClientIMEI().create(ApiInterface.class);

        dialog = getProgressesDialog(context);
        progress = (ProgressDialog) getProgressesDialog1(context);
    }

    public void setBaseRequestListner(RequestReciever requestListner) {
        this.requestReciever = requestListner;

    }

    public Callback<JsonElement> responseCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
           // Toast.makeText(mContext, "TestVikas", Toast.LENGTH_SHORT).show();
            if (null != response.body()) {
                JsonElement jsonElement = (JsonElement) response.body();
                if (null != jsonElement) {
                   // Toast.makeText(mContext, "responseServer==>>"+responseServer, Toast.LENGTH_SHORT).show();
                    responseServer = jsonElement.toString();
                }
                //System.out.println("responseServer==>>"+responseServer);
                System.out.println("VikasVV1==>"+responseServer);
                System.out.println("testing==>" + response.body().toString());
            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");
            if (parseJson(responseServer)) {
                if (mResponseCode.equals("true")) {
                    if (null != requestReciever) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
                    }
                } else if (mResponseCode.equals("false")) {
                    if (null != requestReciever) {
                        requestReciever.onFailure(1, "" + mResponseCode, message);
                    }
                } else {
                    if (null != requestReciever) {
                        requestReciever.onFailure(1, "" + mResponseCode, message);
                    }
                }
            } else {
                if (null != requestReciever) {
                    requestReciever.onFailure(1, mResponseCode, responseServer);
                }
            }
           // hideLoader();
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            if (getConnectivityStatus(mContext) == TYPE_NOT_CONNECTED) {
                if (null != requestReciever) {
                    requestReciever.onNetworkFailure(APINumber_, "Network Error");
                }
            }
            hideLoader();
        }
    };

    public Callback<JsonElement> responseCallbackIMEI = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            if (null != response.body()) {
                JsonElement jsonElement = (JsonElement) response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }
                System.out.println("responseServer==>>"+responseServer);
            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");

            if (parseJson(responseServer)) {
                requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
            } else {
                if (null != requestReciever) {


                    requestReciever.onFailure(1, mResponseCode, responseServer);
                }
            }
            // hideLoader();
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            if (getConnectivityStatus(mContext) == TYPE_NOT_CONNECTED) {
                if (null != requestReciever) {
                    requestReciever.onNetworkFailure(APINumber_, "Network Error");
                }
            }
            hideLoader();
        }
    };

    public void callAPIPost(final int APINumber, JsonObject jsonObject, String remainingURL) {
        APINumber_ = APINumber;
        System.out.println("APINumber==>" + APINumber);
       // showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
       // Toast.makeText(mContext, "baseURL==>>"+baseURL, Toast.LENGTH_SHORT).show();
        System.out.println("VikasVV2==>" + baseURL);
        System.out.println("testing==>" + jsonObject.toString());
        Call<JsonElement> call = apiInterface.postData(baseURL, jsonObject);

        call.enqueue(responseCallback);
    }

    public void callAPIPostShimha2(final int APINumber, JsonObject jsonObject, String remainingURL) {
        APINumber_ = APINumber;
       // showLoader();
        String baseURL = "https://solar10.shaktisolarrms.com/RMSApp/" + remainingURL;
       // System.out.println("VikasVV3==>" + baseURL);
        Log.e("Anjali baseURL==>" , baseURL);
        System.out.println("Anjali baseURL==>" + baseURL);
       Log.e("Anjali Json testing==>" ,jsonObject.toString());
        Call<JsonElement> call = apiInterface.postData(baseURL, jsonObject);
        call.enqueue(responseCallback);
    }

    public void callAPIPostRealStartStop(final int APINumber, JsonObject jsonObject, String remainingURL) {
        APINumber_ = APINumber;
       // showLoader();
        String baseURL = NewSolarVFD.HOST_NAME3 + remainingURL;
        System.out.println("jsonObject_GRAPH==>>"+baseURL);
        //Toast.makeText(mContext, baseURL, Toast.LENGTH_SHORT).show();
        Call<JsonElement> call = apiInterface.postData(baseURL, jsonObject);
        call.enqueue(responseCallback);
    }

    public void callAPIPostIMEI(final int APINumber, JsonObject jsonObject, String remainingURL) {

        APINumber_ = APINumber;
        // showLoader();
      //  String baseURL = ApiClient.getClientIMEI().baseUrl().toString() + remainingURL;
       // String baseURL = "http://111.118.249.180:8888/" + remainingURL;
        //String baseURL = "http://111.118.249.180:8888/" + remainingURL;
        String baseURL = "http://111.118.249.180:8888/" + remainingURL;
        System.out.println("jsonObject_GRAPH==>>"+baseURL);
        Call<JsonElement> call = apiInterface1.postData(baseURL, jsonObject);
        call.enqueue(responseCallbackIMEI);
    }

    public void callAPIPostShri(final int APINumber, JsonObject jsonObject, String remainingURL) {

        APINumber_ = APINumber;
        // showLoader();
      //  String baseURL = ApiClient.getClientIMEI().baseUrl().toString() + remainingURL;
       // String baseURL = "http://111.118.249.180:8888/" + remainingURL;
        //String baseURL = "http://111.118.249.180:8888/" + remainingURL;
        String baseURL = "http://111.118.249.190:8086/RMSApp/" + remainingURL;
        System.out.println("jsonObject_GRAPH==>>"+baseURL);
        Call<JsonElement> call = apiInterface1.postData(baseURL, jsonObject);
        call.enqueue(responseCallbackIMEI);
    }

    public void callAPIGETShri(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
        //  showLoader();
        //  String baseURL = "http://111.118.249.180:1112/ShaktiHome/" + remainingURL;
        String baseURL = "http://111.118.249.190:8086/RMSApp/" + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);

        Call<JsonElement> call = apiInterface.postDataGET(baseURL, map);
        call.enqueue(responseCallbackIMEI);
    }

    public void callAPIGET(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
       showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map);
        call.enqueue(responseCallback);
    }

    public void callAPIGETRealStartStop(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
       showLoader();
        String baseURL =  NewSolarVFD.HOST_NAME3+remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("VikasVV4==>"+baseURL);
       // System.out.println("BaseReq INPUT URL : " + baseURL);
        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map);
        call.enqueue(responseCallback);
    }

    public void callAPIGETIMEI(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
      //  showLoader();
      //  String baseURL = "http://111.118.249.180:1112/ShaktiHome/" + remainingURL;
        String baseURL = "http://solar10.shaktisolarrms.com:1992/Home/" + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);

        Call<JsonElement> call = apiInterface.postDataGET(baseURL, map);
        call.enqueue(responseCallbackIMEI);
    }


    public void callAPIGETIMEIAuth(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
      // showLoader();
        String baseURL = ApiClientIMEI.getClientIMEI().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);

        Call<JsonElement> call = apiInterface.postDataGET(baseURL, map);
        call.enqueue(responseCallbackIMEI);
    }

    public void callAPIGET1(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
      //  showLoader();
        String baseURL = remainingURL;
        /*if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        */
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map);
        call.enqueue(responseCallback);
    }

    public void logFullResponse(String response, String inout) {
        final int chunkSize = 3000;
        if (null != response && response.length() > chunkSize) {
            int chunks = (int) Math.ceil((double) response.length()
                    / (double) chunkSize);

            for (int i = 1; i <= chunks; i++) {
                if (i != chunks) {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize, i
                                    * chunkSize));
                } else {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize,
                                    response.length()));
                }
            }
        } else {

            try {
                System.out.println("Shri==>>"+response);
                JSONObject jsonObject = new JSONObject(response);
                Log.d("BaseReq", inout + " : " + jsonObject.toString(jsonObject.length()));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("BaseReq", " logFullResponse=>  " + response);
            }
        }
    }

    private String readStreamFully(long len, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

   // public Dialog getProgressesDialog(Context ct)
    public ProgressDialog getProgressesDialog1(Context ct)
    {
       // Dialog dialog = null;
        try {
          /*  dialog = new Dialog(ct);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progress_dialog_loader);
//            // Set the progress dialog background color transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setIndeterminate(false);
            dialog.setCanceledOnTouchOutside(false);*/
            progress = new ProgressDialog(ct);
            progress.setTitle("Shakti RMS");
            progress.setMessage("Wait geting data from device...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            progress.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //loader_tv = (TextView)dialog.findViewById(R.id.loader_tv);
      /*  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);*/
        return progress;
    }

    public Dialog getProgressesDialog(Context ct)
    {
        Dialog dialog = null;
        try {
            dialog = new Dialog(ct);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progress_dialog_loader);
//            // Set the progress dialog background color transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          //  GifView pGif = (GifView) dialog.findViewById(R.id.progressBar);
           // pGif.setImageResource(R.drawable.loadingvk);
//            dialog.setIndeterminate(false);
            dialog.setCanceledOnTouchOutside(false);


        } catch (Exception e) {
            e.printStackTrace();
        }

        //loader_tv = (TextView)dialog.findViewById(R.id.loader_tv);
      /*  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);*/
        return dialog;
    }

    public void showLoader() {
        try {
            if (!runInBackground) {
                if (null != loaderView) {
                    loaderView.setVisibility(View.VISIBLE);
                } else if (null != dialog) {
                    dialog.show();
                   // progress.show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hideLoader() {
        try {
            if (!runInBackground) {
                if (null != loaderView) {
                    loaderView.setVisibility(View.GONE);
                } else if (null != dialog) {
                   dialog.dismiss();
                    //progress.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int TYPE_NOT_CONNECTED = 0;
    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;

    public int getConnectivityStatus(Context context) {
        if (null == context) {
            return TYPE_NOT_CONNECTED;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }
}
