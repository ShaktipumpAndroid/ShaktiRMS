
package com.shaktipumps.shakti_rms.bean.KLPHybride;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class KLPHybrideModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private KLPHybrideResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public KLPHybrideResponse getResponse() {
        return mResponse;
    }

    public void setResponse(KLPHybrideResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
