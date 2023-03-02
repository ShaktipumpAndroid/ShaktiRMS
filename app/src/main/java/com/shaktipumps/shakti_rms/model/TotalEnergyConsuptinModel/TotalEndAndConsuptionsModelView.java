
package com.shaktipumps.shakti_rms.model.TotalEnergyConsuptinModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TotalEndAndConsuptionsModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private TotalEndAndConsuptionsResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public TotalEndAndConsuptionsResponse getResponse() {
        return mResponse;
    }

    public void setResponse(TotalEndAndConsuptionsResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
