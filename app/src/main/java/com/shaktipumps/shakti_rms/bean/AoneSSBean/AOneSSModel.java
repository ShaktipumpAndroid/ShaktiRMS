
package com.shaktipumps.shakti_rms.bean.AoneSSBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AOneSSModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private AOneSSResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public AOneSSResponse getResponse() {
        return mResponse;
    }

    public void setResponse(AOneSSResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
