
package com.shaktipumps.shakti_rms.bean.UserBeanOTP;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserRegistratinModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private UserRegistratinResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public UserRegistratinResponse getResponse() {
        return mResponse;
    }

    public void setResponse(UserRegistratinResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
