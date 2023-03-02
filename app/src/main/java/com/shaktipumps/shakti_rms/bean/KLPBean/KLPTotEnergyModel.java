
package com.shaktipumps.shakti_rms.bean.KLPBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPTotEnergyModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private KLPTotEnergyResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public KLPTotEnergyResponse getResponse() {
        return mResponse;
    }

    public void setResponse(KLPTotEnergyResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
