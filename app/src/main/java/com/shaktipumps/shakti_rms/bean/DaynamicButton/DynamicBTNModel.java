
package com.shaktipumps.shakti_rms.bean.DaynamicButton;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class DynamicBTNModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<DynamicBTNResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<DynamicBTNResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<DynamicBTNResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
