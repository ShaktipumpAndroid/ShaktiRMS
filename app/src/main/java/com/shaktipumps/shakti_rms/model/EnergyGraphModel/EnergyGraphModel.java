
package com.shaktipumps.shakti_rms.model.EnergyGraphModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EnergyGraphModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private EnergyGraphResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public EnergyGraphResponse getResponse() {
        return mResponse;
    }

    public void setResponse(EnergyGraphResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
