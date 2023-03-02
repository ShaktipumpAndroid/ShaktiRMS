
package com.shaktipumps.shakti_rms.bean.SAJ;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SAJModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private SAJResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public SAJResponse getResponse() {
        return mResponse;
    }

    public void setResponse(SAJResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
