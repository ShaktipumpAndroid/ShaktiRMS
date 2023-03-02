
package com.shaktipumps.shakti_rms.bean.UspcEnergy;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UspcEnrgyModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private UspcEnrgyResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public UspcEnrgyResponse getResponse() {
        return mResponse;
    }

    public void setResponse(UspcEnrgyResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
