
package com.shaktipumps.shakti_rms.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PaymentSuccessResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
