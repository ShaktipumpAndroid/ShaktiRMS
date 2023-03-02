
package com.shaktipumps.shakti_rms.bean.KLPGrid;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class KLPGridGraphModelView {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<KLPGridGraphModelResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<KLPGridGraphModelResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<KLPGridGraphModelResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
