
package com.shaktipumps.shakti_rms.bean.OLD_klp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OldKLPModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private OldKLPResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public OldKLPResponse getResponse() {
        return mResponse;
    }

    public void setResponse(OldKLPResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
