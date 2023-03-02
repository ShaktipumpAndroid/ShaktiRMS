
package com.shaktipumps.shakti_rms.model.CheckUpdateModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CheckAppUpdateModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private CheckMobileupdateResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public CheckMobileupdateResponse getResponse() {
        return mResponse;
    }

    public void setResponse(CheckMobileupdateResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
