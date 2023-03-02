
package com.shaktipumps.shakti_rms.bean.ForgetOTPBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ForgotOTPPassModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private ForgotOTPPassResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public ForgotOTPPassResponse getResponse() {
        return mResponse;
    }

    public void setResponse(ForgotOTPPassResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
