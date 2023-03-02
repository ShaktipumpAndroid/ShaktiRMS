
package com.shaktipumps.shakti_rms.bean.Nikola;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NikolaModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private NikolaResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public NikolaResponse getResponse() {
        return mResponse;
    }

    public void setResponse(NikolaResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
