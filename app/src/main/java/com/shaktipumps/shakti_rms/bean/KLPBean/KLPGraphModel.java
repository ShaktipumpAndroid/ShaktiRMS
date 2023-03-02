
package com.shaktipumps.shakti_rms.bean.KLPBean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KLPGraphModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<KLPGraphResponse> mResponse;
    @SerializedName("status")
    private Boolean mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<KLPGraphResponse> getResponse() {
        return mResponse;
    }

    public void setResponse(List<KLPGraphResponse> response) {
        mResponse = response;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
