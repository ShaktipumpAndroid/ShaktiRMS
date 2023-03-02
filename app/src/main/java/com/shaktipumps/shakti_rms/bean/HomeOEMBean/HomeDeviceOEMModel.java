
package com.shaktipumps.shakti_rms.bean.HomeOEMBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class HomeDeviceOEMModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private HomeDeviceOEResponse mResponse;
    @SerializedName("status")
    private boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public HomeDeviceOEResponse getResponse() {
        return mResponse;
    }

    public void setResponse(HomeDeviceOEResponse response) {
        mResponse = response;
    }

    public boolean getStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

}
