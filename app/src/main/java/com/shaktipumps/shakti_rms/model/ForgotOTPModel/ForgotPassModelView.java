
package com.shaktipumps.shakti_rms.model.ForgotOTPModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ForgotPassModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private ForgotPassResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public ForgotPassResponse getResponse() {
        return mResponse;
    }

    public void setResponse(ForgotPassResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
