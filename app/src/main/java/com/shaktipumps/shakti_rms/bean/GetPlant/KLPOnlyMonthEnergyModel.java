
package com.shaktipumps.shakti_rms.bean.GetPlant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPOnlyMonthEnergyModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private KLPOnlyMonthEnergyResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public KLPOnlyMonthEnergyResponse getResponse() {
        return mResponse;
    }

    public void setResponse(KLPOnlyMonthEnergyResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
