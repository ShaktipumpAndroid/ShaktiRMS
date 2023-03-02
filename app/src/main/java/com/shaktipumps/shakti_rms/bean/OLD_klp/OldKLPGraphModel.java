
package com.shaktipumps.shakti_rms.bean.OLD_klp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class OldKLPGraphModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<OldKLPGraphResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<OldKLPGraphResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<OldKLPGraphResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
