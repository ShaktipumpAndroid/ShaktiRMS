
package com.shaktipumps.shakti_rms.bean.UserBeanOTP;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DeviceAuthOTPModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private DeviceAuthResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public DeviceAuthResponse getResponse() {
        return mResponse;
    }

    public void setResponse(DeviceAuthResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
