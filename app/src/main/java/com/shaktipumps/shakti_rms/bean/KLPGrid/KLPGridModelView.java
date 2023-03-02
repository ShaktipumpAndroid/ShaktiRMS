
package com.shaktipumps.shakti_rms.bean.KLPGrid;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class KLPGridModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private KLPGridModelResponse mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public KLPGridModelResponse getResponse() {
        return mResponse;
    }

    public void setResponse(KLPGridModelResponse response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
