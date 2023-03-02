
package com.shaktipumps.shakti_rms.bean.userDeviceCheck;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserCheckDeviceModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private UserCheckDeviceResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public UserCheckDeviceResponse getResponse() {
        return mResponse;
    }

    public void setResponse(UserCheckDeviceResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
