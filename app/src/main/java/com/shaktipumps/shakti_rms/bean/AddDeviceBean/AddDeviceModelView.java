
package com.shaktipumps.shakti_rms.bean.AddDeviceBean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AddDeviceModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private AddDeviceResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public AddDeviceResponse getResponse() {
        return mResponse;
    }

    public void setResponse(AddDeviceResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
