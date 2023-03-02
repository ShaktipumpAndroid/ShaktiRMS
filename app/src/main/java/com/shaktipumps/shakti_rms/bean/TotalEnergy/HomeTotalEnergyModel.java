
package com.shaktipumps.shakti_rms.bean.TotalEnergy;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class HomeTotalEnergyModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private HomeTotalEnergyResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public HomeTotalEnergyResponse getResponse() {
        return mResponse;
    }

    public void setResponse(HomeTotalEnergyResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
