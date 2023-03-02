
package com.shaktipumps.shakti_rms.bean.PaymentBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class PaymentModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<PaymentPlanResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PaymentPlanResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<PaymentPlanResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
